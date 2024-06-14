package ai.inno.clever.rest.pipeline;

import ai.inno.clever.domain.CollectionContainer;
import ai.inno.clever.domain.FactoringRequest;
import ai.inno.clever.domain.conf.RbroConfigurationProperties;
import ai.inno.clever.domain.rbrotest.DebtorInformationRecalculate;
import ai.inno.clever.domain.rbrotest.RecalculateLimitsDTO;
import ai.inno.clever.repository.CollectionContainerRepository;
import ai.inno.clever.service.CollectionContainerService;
import ai.inno.clever.service.FactoringRequestService;
import ai.inno.clever.service.PipelineService;
import ai.innoclever.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/recalculate-limits")
public class RecalculateLimitsCollector {
    private final Logger log = LoggerFactory.getLogger(RecalculateLimitsCollector.class);

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    FactoringRequestService factoringRequestService;

    @Autowired
    private CollectionContainerService collectionContainerService;

    @Autowired
    private CollectionContainerRepository collectionContainerRepository;

    @Autowired
    private RbroConfigurationProperties rbroConfigurationProperties;

    @Autowired
    PipelineService pipelineService;

    @PostMapping("/debtors")
    public ResponseEntity<CollectionContainer> recalculateDebtors(
        @RequestBody RecalculateLimitsDTO recalculateLimits) {

        List<CollectionContainer> executionSettingsCollection
            = collectionContainerRepository.findAllByFactoringRequestIdAndType(recalculateLimits.getFactoringRequestId(), "EXECUTION_SETTINGS");
        log.info("executionSettingsCollection: {}",executionSettingsCollection);

        CollectionContainer collectionContainer = new CollectionContainer();
        FactoringRequest factoringRequest = new FactoringRequest();
        log.info("recalculateLimits: {}",recalculateLimits);
        if (null != executionSettingsCollection && !executionSettingsCollection.isEmpty()) {
            collectionContainer = executionSettingsCollection.get(0);

            factoringRequest.setId(recalculateLimits.getFactoringRequestId());
            Map<String, Object> values = collectionContainer.getValues();
            values.put("debtor_limits_settings", recalculateLimits.getDebtorInformationRecalculateList());

            log.info("recalculateLimits.getAdherentLimitsSettings(): {}", recalculateLimits.getAdherentLimitsSettings());

            values.put("adherent_limits_settings", recalculateLimits.getAdherentLimitsSettings());

            log.info("Execution Settings: values: {}", values);
            collectionContainer.setValues(values);
            collectionContainerService.save(collectionContainer);
        } else {
            factoringRequest.setId(recalculateLimits.getFactoringRequestId());
            collectionContainer.setFactoringRequest(factoringRequest);
            collectionContainer.setType("EXECUTION_SETTINGS");
            Map<String, Object> values = new HashMap<>();
            values.put("debtor_limits_settings", recalculateLimits.getDebtorInformationRecalculateList());
            values.put("adherent_limits_settings", recalculateLimits.getAdherentLimitsSettings());
            log.info("Execution Settings 2: values: {}", values);

            collectionContainer.setValues(values);

            collectionContainerService.save(collectionContainer);
        }
        List<String> recourseEligibilityNeeded = rbroConfigurationProperties.getEligibilityChecksRecourseNeeded();
        List<String> nonRecourseEligibilityNeeded = rbroConfigurationProperties.getEligibilityChecksNonRecourseNeeded();
        List<CollectionContainer> collectionContainerEligibilityRecourse = collectionContainerService
            .findByFactoringRequestIdAndType(factoringRequest.getId(), "TOTAL_ELIGIBILITY_CHECK_REGRESSIVE");
        List<CollectionContainer> collectionContainerEligibilityNonRecourse = collectionContainerService
            .findByFactoringRequestIdAndType(factoringRequest.getId(), "TOTAL_ELIGIBILITY_CHECK");
        for (CollectionContainer collectionContainerEligibility : collectionContainerEligibilityRecourse) {
            List<Map<String, Object>> filteredEligibility = filterEligibilityChecks(recourseEligibilityNeeded, collectionContainerEligibility);
            log.info("filteredEligibilityRecourse: {}", filteredEligibility);
            collectionContainerService.save(collectionContainerEligibility);
        }

        for (CollectionContainer collectionContainerEligibility : collectionContainerEligibilityNonRecourse) {
            List<Map<String, Object>> filteredEligibility = filterEligibilityChecks(nonRecourseEligibilityNeeded, collectionContainerEligibility);
            log.info("filteredEligibilityNonRecourse: {}", filteredEligibility);
            collectionContainerService.save(collectionContainerEligibility);
        }
        try {
            pipelineService.startUpdateFlow("updateLimitsFlow", factoringRequest.getId());
        } catch (Exception e) {
            log.error("Error updating Limits, starting update workflow");
        }

        return ResponseEntity
            .ok()
            .body(collectionContainer);
    }

//    @PostMapping("/debtors")
//    public ResponseEntity<CollectionContainer> recalculateDebtors(
//        @RequestBody RecalculateLimitsDTO recalculateLimits) {
//        String apiKey = applicationProperties.getApiKeys().get("collectorsApiKey");
//
//        List<CollectionContainer> executionSettingsCollection
//            = collectionContainerRepository.findAllByFactoringRequestIdAndType(recalculateLimits.getFactoringRequestId(), "EXECUTION_SETTINGS");
//        log.info("executionSettingsCollection: {}",executionSettingsCollection);
//
//        CollectionContainer collectionContainer = new CollectionContainer();
//        FactoringRequest factoringRequest = new FactoringRequest();
//        log.info("recalculateLimits: {}",recalculateLimits);
//        if (null != executionSettingsCollection && !executionSettingsCollection.isEmpty()) {
//            collectionContainer = executionSettingsCollection.get(0);
//            log.info("collectionContainer: {}",collectionContainer);
//            List<DebtorInformationRecalculate> debtorInformationList = convertToRecalculateLimitsDTO(collectionContainer.getValues());
//            log.info("debtorInformationList: {}", debtorInformationList);
//            List<DebtorInformationRecalculate> toAddList = new ArrayList<>();
//            for (DebtorInformationRecalculate debtorInformationFromContainer : debtorInformationList) {
//                boolean contains = false;
//                DebtorInformationRecalculate toAdd = null;
//                for (DebtorInformationRecalculate debtorInformationFromApi : recalculateLimits.getDebtorInformationRecalculateList()) {
//                    log.info("debtorInformationFromApi: {}", debtorInformationFromApi);
//
//                    if (Objects.equals(debtorInformationFromApi.getCui(), debtorInformationFromContainer.getCui())
//                        && debtorInformationFromApi.getProduct().equals(debtorInformationFromContainer.getProduct())) {
//                        debtorInformationFromContainer.setMax_perc_assigned(debtorInformationFromApi.getMax_perc_assigned());
//                        contains = true;
//                        toAdd = debtorInformationFromApi;
//                    }
//                    if (!contains) {
//                        toAddList.add(toAdd);
//                    }  else{
//                        debtorInformationList.remove(toAdd);
//                        debtorInformationList.add(toAdd);
//                    }
//                }
//
////
//            }
//            debtorInformationList.addAll(toAddList);
//            factoringRequest.setId(recalculateLimits.getFactoringRequestId());
////            executionSettingsCollection.get(0).setFactoringRequest(factoringRequest);
////            executionSettingsCollection.get(0).setType("EXECUTION_SETTINGS");
//            Map<String, Object> values = new HashMap<>();
//            values.put("debtor_limits_settings", debtorInformationList);
//            values.put("adherent_limits_settings", recalculateLimits.getAdherentLimitsSettings());
//
//            log.info("Execution Settings: values: {}", values);
//            collectionContainer.setValues(values);
//            collectionContainerService.save(collectionContainer);
//        } else {
//            factoringRequest.setId(recalculateLimits.getFactoringRequestId());
//            collectionContainer.setFactoringRequest(factoringRequest);
//            collectionContainer.setType("EXECUTION_SETTINGS");
//            Map<String, Object> values = new HashMap<>();
//            values.put("debtor_limits_settings", recalculateLimits.getDebtorInformationRecalculateList());
//            values.put("adherent_limits_settings", recalculateLimits.getAdherentLimitsSettings());
//            log.info("Execution Settings 2: values: {}", values);
//
//            collectionContainer.setValues(values);
//
//            collectionContainerService.save(collectionContainer);
//        }
//        List<String> recourseEligibilityNeeded = rbroConfigurationProperties.getEligibilityChecksRecourseNeeded();
//        List<String> nonRecourseEligibilityNeeded = rbroConfigurationProperties.getEligibilityChecksNonRecourseNeeded();
//        List<CollectionContainer> collectionContainerEligibilityRecourse = collectionContainerService
//            .findByFactoringRequestIdAndType(factoringRequest.getId(), "TOTAL_ELIGIBILITY_CHECK_REGRESSIVE");
//        List<CollectionContainer> collectionContainerEligibilityNonRecourse = collectionContainerService
//            .findByFactoringRequestIdAndType(factoringRequest.getId(), "TOTAL_ELIGIBILITY_CHECK");
//        for (CollectionContainer collectionContainerEligibility : collectionContainerEligibilityRecourse) {
//            List<Map<String, Object>> filteredEligibility = filterEligibilityChecks(recourseEligibilityNeeded, collectionContainerEligibility);
//            log.info("filteredEligibilityRecourse: {}", filteredEligibility);
//            collectionContainerService.save(collectionContainerEligibility);
//        }
//
//        for (CollectionContainer collectionContainerEligibility : collectionContainerEligibilityNonRecourse) {
//            List<Map<String, Object>> filteredEligibility = filterEligibilityChecks(nonRecourseEligibilityNeeded, collectionContainerEligibility);
//            log.info("filteredEligibilityNonRecourse: {}", filteredEligibility);
//            collectionContainerService.save(collectionContainerEligibility);
//        }
//        try {
//            pipelineService.startUpdateFlow("updateLimitsFlow", factoringRequest.getId());
//        } catch (Exception e) {
//            log.error("Error updating Limits, starting update workflow");
//        }
//
//        return ResponseEntity
//            .ok()
//            .body(collectionContainer);
//    }

    private List<DebtorInformationRecalculate> convertToRecalculateLimitsDTO(Map<String, Object> values) {
        List<DebtorInformationRecalculate> recalculateLimitsList = new ArrayList<>();

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof DebtorInformationRecalculate) {
                recalculateLimitsList.add((DebtorInformationRecalculate) value);
            } else if (value instanceof List) {
                List<Map<String, Object>> list = (List<Map<String, Object>>) value;
                for (Map<String, Object> item : list) {
                    if (item instanceof DebtorInformationRecalculate) {
                        recalculateLimitsList.add((DebtorInformationRecalculate) item);
                    }
                }
            }
        }

        return recalculateLimitsList;
    }

    public List<Map<String, Object>> filterEligibilityChecks(List<String> validNames, CollectionContainer collectionContainer) {
        List<Map<String, Object>> eligibilityChecks = (List<Map<String, Object>>) collectionContainer.getValues().get("eligibility_checks");
        if (eligibilityChecks != null) {
            Iterator<Map<String, Object>> iterator = eligibilityChecks.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> eligibilityCheck = iterator.next();
                String name = (String) eligibilityCheck.get("name");
                if (!validNames.contains(name)) {
                    iterator.remove();
                }
            }
        }
        return eligibilityChecks;
    }

}

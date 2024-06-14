package ai.inno.clever.rest.pipeline;

import ai.inno.clever.domain.CollectionContainer;
import ai.inno.clever.repository.CollectionContainerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/update-is-report-data-fix")
public class DataFixController {

    private final CollectionContainerRepository repository;

    private final Logger log = LoggerFactory.getLogger(DataFixController.class);


    public DataFixController(CollectionContainerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("is-report")
    public void updateIsReportField() {
        List<CollectionContainer> containersToUpdate = this.repository.findAllByType("COMPANY_PEP_CHECK");
        for (CollectionContainer collectionContainer : containersToUpdate) {
            this.updateIsReportField(collectionContainer);
            repository.save(collectionContainer);
        }
    }


    @GetMapping("/insolvency-proceedings")
    public void updateInsolvencyProceedings() {

        List<CollectionContainer> containersToUpdate = this.repository.findAllByType("ADHERENT_SURVEY");
        for (CollectionContainer collectionContainer : containersToUpdate) {
//            log.info("finished update");

            if (this.copyInsolvencyProceeding(collectionContainer))
                repository.save(collectionContainer);
//            log.info("finished save");

        }

    }

    @GetMapping("/group-flags")
    public void updateGroupFlags() {

        List<CollectionContainer> containersToUpdate = this.repository.findAllByType("RELATIONS_GRAPH");
        for (CollectionContainer collectionContainer : containersToUpdate) {
//            log.info("finished update");

            if (this.updateEligibilityGroupFlag(collectionContainer))
                repository.save(collectionContainer);
//            log.info("finished save");

        }

    }

    @GetMapping("/an")
    public void updateAnField() {
        List<CollectionContainer> containersToUpdate = this.repository.findAllByType("FINANCIAL_DETERIORATION");
        for (CollectionContainer collectionContainer : containersToUpdate) {
//            log.info("Before update an: {}", collectionContainer);
            this.updateAn(collectionContainer);
//            log.info("After update an: {}", collectionContainer);

            repository.save(collectionContainer);
        }
    }

    public boolean copyInsolvencyProceeding(CollectionContainer collectionContainer) {
        String keyToCheck = "insolvency_proceeding_bpi";
        String newKey = "under_insolvency_proceedings_bpi";
//        log.info("Am intrat in copyInsolv cu collectia: {}", collectionContainer);
        if (collectionContainer.getValues() != null && collectionContainer.getValues().containsKey("eligibility_checks")) {
            List<Map<String, Object>> eligibilityChecks = (List<Map<String, Object>>) collectionContainer.getValues().get("eligibility_checks");

            if (eligibilityChecks != null) {
                for (Map<String, Object> check : eligibilityChecks) {
                    if (check != null && check.containsKey("name") && check.get("name").equals(keyToCheck)) {
                        if (eligibilityChecks.stream().noneMatch(c -> c != null && c.containsKey("name") && c.get("name").equals(newKey))) {
                            Map<String, Object> clonedCheck = new ObjectMapper().convertValue(check, Map.class);
                            clonedCheck.put("name", newKey);
                            eligibilityChecks.add(clonedCheck);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public CollectionContainer updateAn(CollectionContainer collectionContainer) {
        List<Map<String, Object>> root = (List<Map<String, Object>>) collectionContainer.getValues().get("root");

        if (collectionContainer.getValues().get("An") != null && null != collectionContainer.getValues().get("An")) {
            if (collectionContainer.getValues().get("An").toString().length() > 3) {
                String an = ((String) collectionContainer.getValues().get("An")).substring(0, 4);
                collectionContainer.getValues().put("An", Integer.valueOf(an));
            }
        }

        if (root != null) {
            for (Map<String, Object> rootToFix : root) {
                if (rootToFix != null && null != rootToFix.get("An")) {
                    if (rootToFix.get("An").toString().length() > 3) {
                        String an = ((String) rootToFix.get("An")).substring(0, 4);
                        rootToFix.put("An", Integer.valueOf(an));
                    }
                }
            }
        }

        return collectionContainer;
    }

    public boolean updateEligibilityGroupFlag(CollectionContainer collectionContainer) {
        //   log.info("state 1: {}", this);
        Object directEligibilityInfoObj = collectionContainer.getValues().get("direct_eligibility_info");
        if (directEligibilityInfoObj instanceof Map) {
    //        log.info("directEligibilityInfoObj instanceof List");
            Map<String, Object> direct_eligibility_info = (Map<String, Object>) directEligibilityInfoObj;
            //     log.info("state 2, {}", direct_eligibility_info);
            Object groupFlagObj = direct_eligibility_info.get("group_flag");
        if(null != groupFlagObj) {
            if (!(groupFlagObj instanceof Boolean)) {
                log.debug("Before update group_flag: {}", collectionContainer);
                direct_eligibility_info.put("group_flag", false);
                log.debug("After update group_flag: {}", collectionContainer);
                return true;
            }
        }
//                for (Map<String, Object> directEligibilityInfo : direct_eligibility_info) {
//                    //      log.info("state 4");
//
//                    if (directEligibilityInfo != null) {
//                        //    log.info("state 5");
//                        log.info("directEligibilityInfo != null");
//
//                        if (!(directEligibilityInfo.get("group_flag") instanceof Boolean)) {
//                            log.info("Before update group_flag: {}", collectionContainer);
//
//                            //      log.info("Group flags not Boolean {}", this);
//                            directEligibilityInfo.put("group_flag", false);
//
//                            log.info("After update group_flag: {}", collectionContainer);
//
//                            //          log.info("state 7");
//                            return true;
//
//                        }
//                    }
//
//                }
        }
//        else {
//            //       log.warn("direct_eligibility_info is not a List: {}", directEligibilityInfoObj);
//        }
        //       log.info("state Return");
        return false;
    }

    public CollectionContainer updateIsReportField(CollectionContainer collectionContainer) { //2022-03-15T08:26:22.021Z
        List<Map<String, Object>> pepChecks = (List<Map<String, Object>>) collectionContainer.getValues().get("pep_checks");

        if (pepChecks != null) {
            for (Map<String, Object> pepCheck : pepChecks) {
                if (pepCheck != null && null != pepCheck.get("isReport")) {
                    if ((pepCheck.get("isReport") instanceof Boolean)) {
                        boolean isReport = (boolean) pepCheck.get("isReport");
                        if (!isReport) { //restart
                            pepCheck.put("isReport", "false");
                        } else if (isReport) {
                            pepCheck.put("isReport", "true");
                        }
                    }
                    if (!(pepCheck.get("isReport") instanceof Boolean)) {
                        String isReport = pepCheck.get("isReport").toString();
                        if ("False".equalsIgnoreCase(isReport)) {
                            pepCheck.put("isReport", "false");
                        } else if ("True".equalsIgnoreCase(isReport)) {
                            pepCheck.put("isReport", "true");
                        }
                    }
                }
            }
        }

        return collectionContainer;
    }
}

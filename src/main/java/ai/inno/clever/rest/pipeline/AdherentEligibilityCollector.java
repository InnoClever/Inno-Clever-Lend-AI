package ai.inno.clever.rest.pipeline;

import ai.inno.clever.domain.CollectionContainer;
import ai.inno.clever.repository.CollectionContainerRepository;
import ai.inno.clever.service.CollectionContainerService;
import ai.inno.clever.service.FactoringRequestService;
import ai.inno.clever.service.dto.AdherentEligibilityChecks;
import ai.innoclever.config.ApplicationProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/adherent-eligibility-collector")
public class AdherentEligibilityCollector {

    @Autowired
    FactoringRequestService factoringRequestService;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    RestTemplate restTemplate;

    private final Logger log = LoggerFactory.getLogger(AdherentEligibilityCollector.class);
    private final CollectionContainerService collectionContainerService;

    private final CollectionContainerRepository collectionContainerRepository;

    public AdherentEligibilityCollector(
        CollectionContainerService collectionContainerService,
        CollectionContainerRepository collectionContainerRepository
    ) {
        this.collectionContainerService = collectionContainerService;
        this.collectionContainerRepository = collectionContainerRepository;
    }

    @PostMapping("/get-data")
    public ResponseEntity<CollectionContainer> getAdherentEligibility(@RequestBody String requestData, HttpServletRequest request) {
        log.info("Request incoming ip {}", request.getRequestURI());
        log.info("Request adherent for executing task: {}.", requestData);

        // requestData  = ensureQuotes(requestData, "requestId");


        //   AdherentEligibilityCollector.CollectorTask task = new Gson().fromJson(requestData, AdherentEligibilityCollector.CollectorTask.class);
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> taskMap = new Gson().fromJson(requestData, type);

        String taxCode = taskMap.get("taxCode");
        String requestId = taskMap.get("requestId");

        log.info("getAdherentEligibility with taxCode: {} and requestId: {} and the map: {}", taxCode, requestId, taskMap);

        List<CollectionContainer> adherentSurveyCollection
            = collectionContainerRepository.findAllByFactoringRequestIdAndType(requestId, "ADHERENT_SURVEY");

        AdherentEligibilityChecks
                adherentEligibilityChecks = collectionContainerService.getAdherentEligibilityChecks(requestId, taxCode);
        List<Map<String, Object>> eligibilityChecks = new ArrayList<>();

        Map<String, Object> eligibilityCheck1 = new HashMap<>();
        eligibilityCheck1.put("name", "positive_equity");
        eligibilityCheck1.put("value", adherentEligibilityChecks.isPositiveEquity());
        eligibilityCheck1.put("weight", 1);
        eligibilityCheck1.put("observations", new ArrayList<>());
        eligibilityChecks.add(eligibilityCheck1);

        Map<String, Object> eligibilityCheck2 = new HashMap<>();
        eligibilityCheck2.put("name", "positive_payment_incidents");
        eligibilityCheck2.put("value", adherentEligibilityChecks.isPositivePaymentIncidents());
        eligibilityCheck2.put("weight", 1);
        eligibilityCheck2.put("observations", new ArrayList<>());
        eligibilityChecks.add(eligibilityCheck2);

        Map<String, Object> eligibilityCheck3 = new HashMap<>();
        eligibilityCheck3.put("name", "outstanding_debts");
        eligibilityCheck3.put("value", adherentEligibilityChecks.isOutstandingDebts());
        eligibilityCheck3.put("weight", 1);
        eligibilityCheck3.put("observations", new ArrayList<>());
        eligibilityChecks.add(eligibilityCheck3);

        Map<String, Object> eligibilityCheck4 = new HashMap<>();
        eligibilityCheck4.put("name", "positive_net_profit_ym1");
        eligibilityCheck4.put("value", adherentEligibilityChecks.isPositiveNetProfitYm1());
        eligibilityCheck4.put("weight", 1);
        eligibilityCheck4.put("observations", new ArrayList<>());
        eligibilityChecks.add(eligibilityCheck4);

        Map<String, Object> eligibilityCheck5 = new HashMap<>();
        eligibilityCheck5.put("name", "insolvency_proceeding_bpi");
        eligibilityCheck5.put("value", adherentEligibilityChecks.isUnderInsolvencyProceedingsBpi());
        eligibilityCheck5.put("weight", 1);
        eligibilityCheck5.put("observations", new ArrayList<>());
        eligibilityChecks.add(eligibilityCheck5);

        Map<String, Object> values = adherentSurveyCollection.get(0).getValues();
        values.put("eligibility_checks", eligibilityChecks);
        adherentSurveyCollection.get(0).setValues(values);

        return ResponseEntity
            .ok(collectionContainerService.save(adherentSurveyCollection.get(0)));
    }

    public String ensureQuotes(String input, String fieldToCheck) {
        if (input != null && input.contains("\"" + fieldToCheck + "\"")) {
            String pattern = "\"" + fieldToCheck + "\"\\s*:\\s*\"?([^,}\"]+)\"?";
            return input.replaceAll(pattern, "\"" + fieldToCheck + "\":\"$1\"");
        }
        return input;
    }

    //restart
    private static class CollectorTask {

        @JsonProperty("taxCode")
        private String taxCode;

        @JsonProperty("requestId")
        private String requestId;

        public String getTaxCode() {
            return taxCode;
        }

        public void setTaxCode(String taxCode) {
            this.taxCode = taxCode;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        @Override
        public String toString() {
            return "CollectorTask{" +
                ", taxCode='" + taxCode + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
        }
    }
}

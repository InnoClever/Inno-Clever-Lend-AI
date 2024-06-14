package ai.inno.clever.rest.pipeline;

import ai.inno.clever.domain.conf.RbroConfigurationProperties;
import ai.inno.clever.domain.conf.RbroConfigurationPropertiesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/configuration")
@ConfigurationProperties
public class RbroConfigurationController {

    private final Logger log = LoggerFactory.getLogger(RbroConfigurationController.class);

    @Autowired
    private RbroConfigurationProperties configurationProperties;


    @GetMapping("/get-configuration")
    public ResponseEntity<RbroConfigurationPropertiesDTO> getConfiguration() {

        RbroConfigurationPropertiesDTO properties = getConfigurationPropertiesDTO(configurationProperties);

        return ResponseEntity.ok(properties);
    }

    private RbroConfigurationPropertiesDTO getConfigurationPropertiesDTO(
            RbroConfigurationProperties rbroConfigurationProperties){
        RbroConfigurationPropertiesDTO dto = new RbroConfigurationPropertiesDTO();
        dto.setBankConnectionsHidden(rbroConfigurationProperties.isBankConnectionsHidden());
        dto.setAispConnectionsHidden(rbroConfigurationProperties.isAispConnectionsHidden());
        dto.setEligibilityChecksNonRecourseNeeded(rbroConfigurationProperties.getEligibilityChecksNonRecourseNeeded());
        dto.setRestrictiveGroup(rbroConfigurationProperties.isRestrictiveGroup());
        dto.setEligibilityChecksRecourseNeeded(rbroConfigurationProperties.getEligibilityChecksRecourseNeeded());
        dto.setShowReports(rbroConfigurationProperties.isShowReports());
        return dto;
    }

//    @GetMapping("/is-aisp-hidden")
//    public ResponseEntity<Boolean> isAispConnectionsHidden() {
//        return ResponseEntity.ok(configurationProperties.isAispConnectionsHidden());
//    }
//
//    @GetMapping("/is-bank-hidden")
//    public ResponseEntity<Boolean> isBankConnectionsHidden() {
//        return ResponseEntity.ok(configurationProperties.isBankConnectionsHidden());
//    }

//    @GetMapping("/eligibility/non-recourse/needed")
//    public ResponseEntity<List<String>> getNonRecourseFactoringEligibility() {
//        return ResponseEntity.ok(configurationProperties.getEligibilityChecksNonRecourseNeeded());
//    }
//
//    @GetMapping("/eligibility/recourse/needed")
//    public ResponseEntity<List<String>> getRecourseFactoringEligibility() {
//        return ResponseEntity.ok(configurationProperties.getEligibilityChecksRecourseNeeded());
//    }
}

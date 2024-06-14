package ai.inno.clever.rest.pipeline;

import ai.innoclever.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kibana")
public class KibanaController {

    @Autowired
    ApplicationProperties applicationProperties;


    @GetMapping("/url")
    public ResponseEntity<String> getDashboardUrl() {
        String url = applicationProperties.getKibanaDashboardUrl();
        String response = "{ \"url\": \"" + url + "\"}";
        return ResponseEntity
            .ok(response);
    }

}

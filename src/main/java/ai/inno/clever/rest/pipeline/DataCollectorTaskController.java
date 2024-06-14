package ai.inno.clever.rest.pipeline;

import ai.inno.clever.service.dto.MLDataCollectorService;
import ai.innoclever.config.ApplicationProperties;
import ai.innoclever.service.mail.EmailService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/flowable")
public class DataCollectorTaskController {

    private final Logger log = LoggerFactory.getLogger(DataCollectorTaskController.class);

    @Autowired
    ApplicationProperties applicationProperties;


    @Autowired
    EmailService emailService;


    @Autowired
    MLDataCollectorService collectorService;


    @PostMapping("/collect-data")
    public ResponseEntity<String> completeDataCollectorTask(@RequestBody String requestData, HttpServletRequest request)
        throws URISyntaxException, IllegalAccessException, IOException {
        log.debug("Request incoming ip {}", request.getRequestURI());
        log.info("Request for executing task: {}.", requestData);

        MLDataCollectorService.CollectorTask task = new Gson().fromJson(requestData, MLDataCollectorService.CollectorTask.class);

        String collectorUrl = applicationProperties.getDataCollectorsWs().get(task.getCollector());
        if (StringUtils.isEmpty(collectorUrl)) {
            throw new IllegalArgumentException("Unknown collector:   " + task.getCollector());
        }
        if (collectorUrl.startsWith("/")) {
            collectorUrl = applicationProperties.getDataCollectorsBaseUrl() + collectorUrl;
        }

        URI uri = new URI(collectorUrl);
//restart
        collectorService.collect(task, uri);

        return ResponseEntity
            .ok(requestData);
    }

    @PostMapping("/abort-flow")
    public ResponseEntity<String> abortFlow(@RequestBody String requestData, HttpServletRequest request) {

        MLDataCollectorService.CollectorTask task = new Gson().fromJson(requestData, MLDataCollectorService.CollectorTask.class);
        log.debug("Cancel process with ID: " + task.getRequestId());
        collectorService.abortFlow(task);

        return ResponseEntity
            .ok(requestData);
    }

    @PostMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestBody String requestData, HttpServletRequest request) {

        MLDataCollectorService.FlowStatus status = new Gson().fromJson(requestData, MLDataCollectorService.FlowStatus.class);
        log.info("Finished process with ID: " + status.getRequestId());

        collectorService.updateStatus(status);
        //restart
        return ResponseEntity
            .ok(requestData);
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody String requestData, HttpServletRequest request)
        throws MessagingException {
        log.info("Request for executing task: {}.", requestData);

        EmailTask task = new Gson().fromJson(requestData, EmailTask.class);

        if (task.getEmailType().equals("BANK_CONNECT")) {
            emailService.sendSimpleMessage(
                task.getEmail(),
                "Connect to a Bank",
                String.format("Please connect to a bank <a href=\"%s/bank-connect?code=%s\">here</a>.",
                    applicationProperties.getDataCollectorsCallbackUrl(),
                    task.getRequestId()
                ));
        }

        return ResponseEntity
            .ok(requestData);
    }



    private static class EmailTask {

        private String requestId;
        private String email;
        private String emailType;
        private String taskId;

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmailType() {
            return emailType;
        }

        public void setEmailType(String emailType) {
            this.emailType = emailType;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }
    }
}

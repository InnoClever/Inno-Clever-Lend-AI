package ai.inno.clever.service.dto;

import ai.inno.clever.domain.CollectionContainer;
import ai.inno.clever.domain.FactoringRequest;
import ai.inno.clever.domain.User;
import ai.inno.clever.domain.enumeration.CollectionType;
import ai.inno.clever.domain.enumeration.CollectorStatus;
import ai.inno.clever.domain.enumeration.Status;
import ai.inno.clever.service.*;
import ai.inno.clever.service.dto.core.AdherentDTO;
import ai.inno.clever.service.events.error.exceptions.ResourceNotFoundException;
import ai.inno.clever.service.feign.CoreService;
import ai.innoclever.config.ApplicationProperties;
import ai.innoclever.service.mail.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@Service
public class MLDataCollectorService {

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    FactoringRequestService factoringRequestService;

    @Autowired
    CollectionContainerService collectionContainerService;

    @Autowired
    CollectionContainerCallbackService collectionContainerCallbackService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EmailService emailService;

    @Autowired
    InAppCall inAppCall;

    @Autowired
    CoreService coreService;

    @Autowired
    UserService userService;

    private final Logger log = LoggerFactory.getLogger(MLDataCollectorService.class);

    public void abortFlow(CollectorTask task) {
        FactoringRequest factoringRequest = factoringRequestService.findOne(task.getRequestId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "FactoringRequest with ID: " + task.getRequestId() + " could not be found!"));
        factoringRequest.setStatus(Status.ERROR);

        factoringRequestService.save(factoringRequest);
    }

    public void updateStatus(FlowStatus status) {
        FactoringRequest factoringRequest = factoringRequestService.findOne(status.getRequestId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "FactoringRequest with ID: " + status.getRequestId() + " could not be found!"));
        factoringRequest.setStatus(Status.PENDING);
        factoringRequestService.save(factoringRequest);

        if (status.finished) {
            notifyFinish(factoringRequest);
        }

    }

    private void notifyFinish(FactoringRequest factoringRequest) {
        try {
            AdherentDTO adherentDTO = coreService.getAdherentV1(factoringRequest.getAdherentId());
            Map<String, Object> model = new HashMap<>();
            model.put("company_name", adherentDTO.getCompanyName());
            Set<String> notificationChannels = new HashSet<>();
            notificationChannels.add("NOTIFICATION");
            //  notificationChannels.add("MAIL");
            inAppCall.sendNotification(model, factoringRequest.getOwnerLogin(),
                "factoring-request-waiting-approval", "Waiting Approval", notificationChannels,
                "back-office/" + adherentDTO.getId() + "/view");

            User user = userService.getUserWithAuthoritiesByLogin(factoringRequest.getOwnerLogin())
                .orElse(null);
            if (user != null) {
                notificationChannels = new HashSet<>();
                //notificationChannels.add("NOTIFICATION");
                notificationChannels.add("MAIL");
                String url =
                    applicationProperties.getDataCollectorsCallbackUrl() + "/back-office/" + adherentDTO.getId() + "/view";
                model.put("redirect_url", url);
                inAppCall.sendNotification(model, user.getEmail(),
                    "factoring-request-waiting-approval", "Waiting Approval", notificationChannels,
                    null);
            }
        } catch (Exception ex) {
            log.warn("Error sending inApp notifications.", ex);
        }
    }

    @Async
    public void collect(CollectorTask task, URI uri) {

        // Delete current collection
        List<CollectionContainer> collectionContainerList =
            collectionContainerService.findByFactoringRequestIdAndTaskId(task.getRequestId(), task.getTaskId());
        collectionContainerService.deleteAll(collectionContainerList);

        CollectorRequest collectorRequest = new CollectorRequest();
        collectorRequest.setCui(task.getTaxCode());
        collectorRequest.setRequestId(task.getRequestId());
        collectorRequest.setTaskId(task.getTaskId());
        collectorRequest.setCallbackUrl(
            this.applicationProperties.getDataCollectorsCallbackUrl() + "/services/rbro/api/data-collector");

        HttpEntity<CollectorRequest> collectorRequestHttpEntity = new HttpEntity<>(collectorRequest);
        MultiValueMap<String, String> paramsHeader = new LinkedMultiValueMap<String, String>();

        //TODO: extract  this key in properties
        paramsHeader.add("api_key", "oNmi6-88kIo-Jy6cD-2gG7U-gh37h");
        //String url, @Nullable Object request, Class<T> responseType, Map<String, ?> uriVariables
        log.info("Sending collector: {} request to: {}. Request: {}", collectorRequest, uri.toString());
        ResponseEntity<String> result = null;
        for (int retryCount = 0; retryCount < 3; retryCount++) {
            try {
                result =
                    restTemplate.postForEntity(uri.toString(), collectorRequestHttpEntity, String.class, paramsHeader);
                break;
            } catch (Exception exception) {
                log.error("Error occurred while sending collector request (Retry {}/3): {}", retryCount + 1, exception);
                if (retryCount == 2) {
                    handleCollectorError(exception, task);
                } else {
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        log.info("Execution result: {}.", result);

    }

    private void handleCollectorError(Exception exception, CollectorTask task) {
        CollectorResponse collectorResponse = new CollectorResponse();
        TaskResult taskResult = new TaskResult();
        taskResult.setStatus(CollectorStatus.ERROR);
        taskResult.setType(CollectionType.PIPELINE_ERROR.toString());
        taskResult.setErrorMsg(exception.getMessage());
        taskResult.setErrorStackTrace(Arrays.toString(exception.getStackTrace()));
        HashMap<String, Object> error = new HashMap<>();
        error.put("errorMsg", exception.getMessage());
        error.put("errorStackTrace", Arrays.toString(exception.getStackTrace()));
        error.put("taskId", task.getTaskId());
        error.put("type", "ERROR");
        taskResult.setData(error);
        collectorResponse.setCollections(List.of(taskResult));
        try {
            collectionContainerCallbackService.completeDataCollectorTask(task.requestId, task.getTaskId(),
                collectorResponse);
        } catch (Exception e) {
            log.error("Error in MLDataCollectorService:", e);
        }
    }

    public static class CollectorTask {

        private String collector;
        private String taxCode;
        private String requestId;
        private String taskId;

        public String getCollector() {
            return collector;
        }

        public void setCollector(String collector) {
            this.collector = collector;
        }

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

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        @Override
        public String toString() {
            return "CollectorTask{" +
                "collector='" + collector + '\'' +
                ", taxCode='" + taxCode + '\'' +
                ", requestId='" + requestId + '\'' +
                ", taskId='" + taskId + '\'' +
                '}';
        }
    }

    public static class FlowStatus {

        private boolean finished;
        private String requestId;

        public boolean isFinished() {
            return finished;
        }

        public void setFinished(boolean finished) {
            this.finished = finished;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        @Override
        public String toString() {
            return "FlowStatus{" +
                "finished=" + finished +
                ", requestId='" + requestId + '\'' +
                '}';
        }
    }
}

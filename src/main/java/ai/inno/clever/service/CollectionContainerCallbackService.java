package ai.inno.clever.service;

import ai.inno.clever.domain.CollectionContainer;
import ai.inno.clever.domain.FactoringRequest;
import ai.inno.clever.domain.conf.RbroConfigurationProperties;
import ai.inno.clever.domain.enumeration.CollectionType;
import ai.inno.clever.service.dto.TaskResult;
import ai.inno.clever.service.events.error.exceptions.ActionNotPermittedException;
import ai.inno.clever.service.events.error.exceptions.PipelineException;
import ai.inno.clever.service.events.error.exceptions.ResourceNotFoundException;
import ai.inno.clever.service.flowable.FlowableService;
import ai.inno.clever.service.flowable.entities.FlowableTaskDTO;
import ai.inno.clever.service.flowable.entities.TaskImportance;
import ai.innoclever.config.ApplicationProperties;
import ai.innoclever.service.mail.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CollectionContainerCallbackService {

    @Autowired
    EmailService emailService;

    @Autowired
    RbroConfigurationProperties properties;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    FlowableService flowableService;

    @Autowired
    private FactoringRequestService factoringRequestService;

    @Autowired
    private CollectionContainerService collectionContainerService;

    private final Logger log = LoggerFactory.getLogger(CollectionContainerCallbackService.class);

    @Async
    public void completeDataCollectorTask(
        String requestId,
        String taskId,
        CollectorResponse collectorResponse) throws Exception {

        log.info("completeDataCollectorTask {}-{}", requestId, taskId);
        FactoringRequest factoringRequest = factoringRequestService.findOne(requestId).orElseThrow(
            () -> new ResourceNotFoundException("FactoringRequest with ID: " + requestId + " could not be found!"));

        collectorResponse.getCollections().forEach(data -> {
            handleCollection(data, factoringRequest, taskId);
        });
        if(!taskId.contains("None")) {
            completeTask(factoringRequest.getProcessId(), taskId);
        }

    }

    @Async
    public void ongoingDataCollectorWithTaskId(
        String requestId,
        String taskId,
        CollectorResponse collectorResponse) {

        log.info("ongoingDataCollectorWithTaskId {}-{}", requestId, taskId);
        FactoringRequest factoringRequest = factoringRequestService.findOne(requestId).orElseThrow(
            () -> new ResourceNotFoundException("FactoringRequest with ID: " + requestId + " could not be found!"));

        collectorResponse.getCollections().forEach(data -> {
            handleCollection(data, factoringRequest, taskId);
        });
    }

    @Async
    public void ongoingDataCollector(String requestId, CollectorResponse collectorResponse) {

        log.info("ongoingDataCollector {}", requestId);

        FactoringRequest factoringRequest = factoringRequestService.findOne(requestId).orElseThrow(
            () -> new ResourceNotFoundException("FactoringRequest with ID: " + requestId + " could not be found!"));

        String taskId = "None";
        collectorResponse.getCollections().forEach(data -> {
            handleCollection(data, factoringRequest, taskId);
        });

    }



    private void handleCollection(TaskResult data, FactoringRequest factoringRequest, String taskId) {
        switch (data.getStatus()) {
            case OK: {
                if (data.getData() != null && data.getData().size() > 0) {
                    CollectionContainer collectionContainer = new CollectionContainer();
                    collectionContainer.setFactoringRequest(factoringRequest);
                    collectionContainer.setType(data.getType());
                    collectionContainer.setTaskId(taskId);
                    collectionContainer.setValues(data.getData());
                    collectionContainerService.save(collectionContainer);
                    if ("ELIGIBILITY_CHECK".equals(data.getType())) {
                        log.info("Saving ELIGIBILITY_CHECK_REGRESSIVE");
                        CollectionContainer collectionContainerEligibility = new CollectionContainer();
                        collectionContainerEligibility.setFactoringRequest(factoringRequest);
                        collectionContainerEligibility.setType("ELIGIBILITY_CHECK_REGRESSIVE");
                        collectionContainerEligibility.setTaskId(taskId);
                        collectionContainerEligibility.setValues(data.getData());
                        collectionContainerService.save(collectionContainerEligibility);
                    }
                    if ("TOTAL_ELIGIBILITY_CHECK".equals(data.getType())) {
                        log.info("Saving TOTAL_ELIGIBILITY_CHECK_REGRESSIVE");
                        CollectionContainer collectionContainerEligibility = new CollectionContainer();
                        collectionContainerEligibility.setFactoringRequest(factoringRequest);
                        collectionContainerEligibility.setType("TOTAL_ELIGIBILITY_CHECK_REGRESSIVE");
                        collectionContainerEligibility.setTaskId(taskId);
                        collectionContainerEligibility.setValues(data.getData());
                        collectionContainerService.save(collectionContainerEligibility);
                    }
                }
                break;
            }
            case WARNING: {
                if (data.getData() != null && data.getData().size() > 0) {
                    CollectionContainer collectionContainer = new CollectionContainer();
                    collectionContainer.setFactoringRequest(factoringRequest);
                    collectionContainer.setType(data.getType());
                    collectionContainer.setTaskId(taskId);
                    collectionContainer.setValues(data.getData());
                    collectionContainerService.save(collectionContainer);
                    log.info("Saved container: " + collectionContainer.toString());
                }
                // Save errorMessage and stackTrace
                CollectionContainer collectionContainer = new CollectionContainer();
                collectionContainer.setFactoringRequest(factoringRequest);
                collectionContainer.setType(CollectionType.PIPELINE_ERROR.toString());
                collectionContainer.setTaskId(taskId);
                Map<String, Object> error = new HashMap<>();
                error.put("errorMsg", data.getErrorMsg());
                error.put("errorStackTrace", data.getErrorStackTrace());
                error.put("taskId", taskId); // save taskId of currentTask
                error.put("type", "WARNING");
                collectionContainer.setValues(error);
                collectionContainerService.save(collectionContainer);
                log.info("Saved WARNING: " + collectionContainer.toString());
                break;
            }
            case ERROR: {
                // Save errorMessage and stackTrace
                if (data.getData() != null && data.getData().size() > 0) {
                    CollectionContainer collectionContainer = new CollectionContainer();
                    collectionContainer.setFactoringRequest(factoringRequest);
                    collectionContainer.setType(data.getType());
                    collectionContainer.setTaskId(taskId);
                    collectionContainer.setValues(data.getData());
                    collectionContainerService.save(collectionContainer);
                    log.info("Saved container: " + collectionContainer.toString());
                }
                // Save errorMessage and stackTrace
                CollectionContainer collectionContainer = new CollectionContainer();
                collectionContainer.setFactoringRequest(factoringRequest);
                collectionContainer.setType(CollectionType.PIPELINE_ERROR.toString());
                collectionContainer.setTaskId(taskId);
                Map<String, Object> error = new HashMap<>();
                error.put("errorMsg", data.getErrorMsg());//restart
                error.put("errorStackTrace", data.getErrorStackTrace());
                error.put("taskId", taskId); // save taskId of currentTask
                error.put("type", "ERROR");
                collectionContainer.setValues(error);
                collectionContainerService.save(collectionContainer);
                log.info("Saved ERROR: " + collectionContainer.toString());

                // Ask flowable if the task is critical or not please
                // git commit
                FlowableTaskDTO task = flowableService
                    .getProcessInstanceTaskWithKey(factoringRequest.getProcessId(), taskId, false);

                if (checkIfNeedToStop(task.getProcessDefinitionId(), taskId)) {
                    //                    factoringRequest.setStatus(Status.ERROR);
                    //                    factoringRequest.setMsg(data.getErrorMsg());
                    //                    factoringRequestService.save(factoringRequest);
                    //
                    //
                    collectionContainer = new CollectionContainer();
                    collectionContainer.setFactoringRequest(factoringRequest);
                    collectionContainer.setType(CollectionType.PIPELINE_ERROR.toString());
                    collectionContainer.setTaskId(taskId);
                    error = new HashMap<>();
                    error.put("errorMsg", "The pipeline received an ERROR at a CRITICAL task: " + taskId + ".");
                    error.put("taskId", taskId); // save taskId of currentTask
                    error.put("type", "CRITICAL_ERROR");
                    collectionContainer.setValues(error);

                    try {
                        for (String email : properties.getCriticalMails()) {
                            emailService.sendSimpleMessage(
                                email,
                                "Critical Error factoring request " + factoringRequest.getId(),
                                String.format("Task: " + taskId +
                                        "</br>Content: " + data.getErrorMsg() +
                                        "</br>To see the request click " + "<a href=\"%s/back-office/%s/view\">here</a>.",
                                    applicationProperties.getDataCollectorsCallbackUrl(),
                                    factoringRequest.getAdherentId()
                                ));
                        }
                    } catch (Exception e) {
                        log.error("Cannot send email: ", e);
                    }
                    collectionContainerService.save(collectionContainer);
                    // TODO: 10/12/2021 Stop flow in flowable engine also
                    // ...
                    throw new PipelineException(
                        "The pipeline received an ERROR at a CRITICAL task. The sent error was saved successfully!");
                }
                break;
            }
            default:
                throw new ActionNotPermittedException(
                    "You cannot perform this action with status: " + data.getStatus());
        }

        log.debug("-----------------------------------------------------------");
    }



    private boolean checkIfNeedToStop(String processId, String taskId) {
        TaskImportance taskImportance = flowableService.getTaskImportanceDetails(processId, taskId).getImportance();
        if (taskImportance == TaskImportance.CRITICAL) {
            return true;
        }
        return false;
    }

    private void completeTask(String processId, String taskId) throws Exception {

        try {
            flowableService.completeTask(processId, taskId);
        } catch (Exception ex) {
            log.warn("Error completing task: {}", taskId, ex);
        }
    }

}

package ai.inno.clever.service;

import ai.inno.clever.domain.CollectionContainer;
import ai.inno.clever.domain.Company;
import ai.inno.clever.domain.FactoringRequest;
import ai.inno.clever.domain.conf.RbroConfigurationProperties;
import ai.inno.clever.domain.enumeration.Status;
import ai.inno.clever.service.dto.AdminUserDTO;
import ai.inno.clever.service.dto.SignTaskDTO;
import ai.inno.clever.service.dto.aisp.AispDTO;
import ai.inno.clever.service.dto.core.AdherentDTO;
import ai.inno.clever.service.dto.core.CompleteSurveyBody;
import ai.inno.clever.service.dto.core.FileInfoDTO;
import ai.inno.clever.service.dto.core.enumeration.EntityType;
import ai.inno.clever.service.events.error.exceptions.ActionNotPermittedException;
import ai.inno.clever.service.events.error.exceptions.ResourceNotFoundException;
import ai.inno.clever.service.feign.CoreService;
import ai.inno.clever.service.feign.Psd2Service;
import ai.inno.clever.service.flowable.FlowableService;
import ai.inno.clever.service.flowable.entities.*;
import ai.innoclever.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ai.innoclever.service.utils.CSVToAISP.csvToAISP;

@Service
public class PipelineService {

    private final Logger log = LoggerFactory.getLogger(PipelineService.class);


    @Autowired
    FlowableService flowableService;

    @Autowired
    CompanyService companyService;

    @Autowired
    FactoringRequestService factoringRequestService;

    @Autowired
    CollectionContainerService collectionContainerService;

    @Autowired
    LocalDataCollector localDataCollector;

    @Autowired
    ProviderCallService providerCallService;

    @Autowired
    Psd2Service psd2Service;

    @Autowired
    CoreService coreService;

    @Autowired
    UserService userService;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    RbroConfigurationProperties configurationProperties;

    public FlowableTaskListDTO getTasks(String requestId) {
        FactoringRequest factoringRequest = factoringRequestService.findOne(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("FactoringRequest with ID " + requestId + " was not found!"));
        return flowableService.getTasksForProcess(factoringRequest.getProcessId(), false);
    }

    public FactoringRequest startFlow(String pipelineName, AdherentDTO adherent) throws Exception {
        if (pipelineName == null) {
            throw new ActionNotPermittedException("PipelineName should not be null!");
        }

        Company company = companyService
            .findByCui(adherent.getTaxCode())
            .orElseGet(() -> createAndSaveCompany(adherent.getTaxCode()));

        FactoringRequest factoringRequest = createFactoringRequest(adherent, company);

        AdminUserDTO loggedInUser;
        try {
            loggedInUser = userService.getLoggedInUser();
            factoringRequest.setOwnerLogin(loggedInUser.getLogin());

            setRequesterData(factoringRequest, loggedInUser);
        } catch (Exception e) {
            log.warn("Could not retrieve user", e);
        }

        Variables variables = createFlowableVariables(adherent, factoringRequest);

        FlowableProcessInstanceDTO
                flowableProcessInstance = flowableService.startProcessInstanceByKey(pipelineName, variables);

        factoringRequest.setProcessId(flowableProcessInstance.getId());
        factoringRequest = factoringRequestService.save(factoringRequest);
        if (configurationProperties.isRestrictiveGroup()) {
            executionSettingsRestriciveGroup(factoringRequest);
        }

        executionSettingsAispCheck(factoringRequest);

        return factoringRequest;
    }

    //restart
    private void setRequesterData(FactoringRequest factoringRequest, AdminUserDTO loggedInUser) {
        if (factoringRequest.getRequesterEmail() == null) {
            if (loggedInUser.getEmail() != null) {
                factoringRequest.setRequesterEmail(loggedInUser.getEmail());
            }
        }
        if (factoringRequest.getRequesterLogin() == null) {
            if (loggedInUser.getLogin() != null) {
                factoringRequest.setRequesterLogin(loggedInUser.getLogin());
            }
        }

        if (factoringRequest.getRequesterFullName() == null) {
            if (loggedInUser.getLastName() != null && loggedInUser.getLastName() != null) {
                factoringRequest.setRequesterFullName(loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
            }
        }
    }

    private void executionSettingsRestriciveGroup(FactoringRequest factoringRequest) {
        CollectionContainer collectionContainer = new CollectionContainer();
        collectionContainer.setFactoringRequest(factoringRequest);
        collectionContainer.setType("EXECUTION_SETTINGS");
        Map<String, Object> values = new HashMap<>();
        Map<String, Object> group_structure = new HashMap<>();
        group_structure.put("method", "direct");
        Map<String, Object> graph = new HashMap<>();
        values.put("group_structure", group_structure);
        graph.put("history_relations", false);
        values.put("graph", graph);
        collectionContainer.setValues(values);
        collectionContainerService.save(collectionContainer);
    }

    private void executionSettingsAispCheck(FactoringRequest factoringRequest) {
        CollectionContainer collectionContainer = new CollectionContainer();
        collectionContainer.setFactoringRequest(factoringRequest);
        collectionContainer.setType("EXECUTION_SETTINGS");
        Map<String, Object> values = new HashMap<>();
        values.put("aispCheck", configurationProperties.isAispChecks());
        collectionContainer.setValues(values);
        collectionContainerService.save(collectionContainer);
    }

    public FactoringRequest startUpdateFlow(String pipelineName, String factoringRequestId) throws Exception {
        if (pipelineName == null) {
            throw new ActionNotPermittedException("PipelineName should not be null!");
        }

        //restart
        // Create Factoring request
        FactoringRequest factoringRequest = factoringRequestService.findOne(factoringRequestId).orElseThrow(() -> new IllegalArgumentException("Factpring request not found: " + factoringRequestId));
        //        // Create ProviderServiceCalls
        //        createProvierServiceCalls(factoringRequest);
        // Flowable variables

        factoringRequest.setOwnerLogin(userService.getLoggedInUser().getLogin());

        AdherentDTO adherent = coreService.getAdherent(factoringRequest.getAdherentId());

        Variables variables = createFlowableVariables(adherent, factoringRequest);

        FlowableProcessInstanceDTO
                flowableProcessInstance = flowableService.startProcessInstanceByKey(pipelineName, variables);

        log.info("New Process ID: {}", flowableProcessInstance.getId());

        factoringRequest.setProcessId(flowableProcessInstance.getId());
        factoringRequest.setStatus(Status.IN_PROGRESS);

        factoringRequest = factoringRequestService.save(factoringRequest);

        return factoringRequest;
    }

    public FlowableStatusDTO getFactoringRequestFlowStatus(String factoringRequestId) {
        FactoringRequest
                factoringRequest = factoringRequestService.findOne(factoringRequestId).orElseThrow(() -> new ResourceNotFoundException("FactoringRequest with Id: " + factoringRequestId + " was not found!"));
        log.debug("Getting status for: FR: {} - PI: {}", factoringRequestId, factoringRequest.getProcessId());
        FlowableStatusDTO status = this.flowableService.getProcessStatus(factoringRequest.getProcessId());
        if (status != null) {
            status.setRequestId(factoringRequest.getId());
            return status;
        } else {
            return null;
        }
    }

    public FactoringRequest completeSurveyTask(String requestId, CompleteSurveyBody body) throws Exception {
        return this.addSurveyCollectionAndCompleteSurveyTask(requestId, body);
    }

    private FactoringRequest addSurveyCollectionAndCompleteSurveyTask(String requestId, CompleteSurveyBody body) throws Exception {
        log.info("addSurveyCollectionAndCompleteSurveyTask {}", requestId);
        FactoringRequest factoringRequest = factoringRequestService.findOne(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("FactoringRequest with ID " + requestId + " was not founnd!"));

        FlowableTaskDTO surveyTask = this.flowableService.getTasksForProcess(factoringRequest.getProcessId(), false).getData()
            .stream()
            .filter(task -> task.getTaskDefinitionKey().equalsIgnoreCase("surveyTask")) // TODO: remove hardcoded
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("SurveyTask for factoring request " + requestId + " was not founnd!"));

        AdherentDTO adherent = body.getAdherent();

        String taskId = surveyTask.getId();
        Variables variables = new Variables();
        FlowableVariableDTO taxCodeVariable = new FlowableVariableDTO();
        taxCodeVariable.setName("taxCode");
        taxCodeVariable.setValue(adherent.getTaxCode());
        variables.add(taxCodeVariable);

        FlowableVariableDTO emailVariable = new FlowableVariableDTO();
        emailVariable.setName("email");
        emailVariable.setValue(adherent.getEmail());
        variables.add(emailVariable);

        log.info("before - createSurveyDataCollector {}", requestId);
        localDataCollector.createSurveyDataCollector(factoringRequest, adherent);
        log.info("after - createSurveyDataCollector {}", requestId);
//        localDataCollector.addAISPCollection(factoringRequest);
        if (!configurationProperties.isBankConnectionsHidden()) {
            if (body.getAispType().equalsIgnoreCase("UPLOAD_AISP")) {
                CollectionContainer collectionContainer = new CollectionContainer();
                collectionContainer.setFactoringRequest(factoringRequest);
                collectionContainer.setType("AISP_COLLECTION");
                try {
                    AispDTO aisp = new AispDTO();

                    // Get BANK FILES
                    List<FileInfoDTO> fileInfos = coreService.getFileInfos(applicationProperties.getApiKeys().get("microserviceKey"), EntityType.ADHERENT, adherent.getId(), "BANK_TRANSACTIONS");
                    fileInfos.forEach(fileInfo -> {
                        log.debug(fileInfo.getBizType() + " " + fileInfo.getOriginalFilename() + " " + fileInfo.getContentType());
                        InputStream fileStream = new ByteArrayInputStream(fileInfo.getFile());
                        AispDTO importedAisp = csvToAISP(fileStream);
                        aisp.addBanks(importedAisp.getBanks());
                    });


                    HashMap<String, Object> values = new HashMap<>();
                    values.put("banks", aisp.getBanks());
                    collectionContainer.setValues(values);
                } catch (Exception e) {
                    log.error("Error while geting UPLOAD AISP", e);

                }

                log.debug("ADD AISP_COLLECTION------------------------------------\n\n\n");
                log.info(collectionContainer.getValues().toString());
//restart
                collectionContainerService.save(collectionContainer);
            }
            if (body.getAispType().equalsIgnoreCase("BANK_CONNECTION")) {
                localDataCollector.addAISPCollection(adherent.getOwnerId(), factoringRequest);
            }
        }
        this.flowableService.updateProcessInstanceVariables(factoringRequest.getProcessId(), variables);

        log.debug("before -  this.flowableService.completeTask {}", taskId);
        this.flowableService.completeTask(taskId);
        log.debug("after -  this.flowableService.completeTask {}", taskId);

        factoringRequest.setStatus(Status.IN_PROGRESS);
        return this.factoringRequestService.save(factoringRequest);
    }

    public FactoringRequest signTask(SignTaskDTO signTaskDTO) throws Exception {
        FactoringRequest factoringRequest = factoringRequestService.findOne(signTaskDTO.getRequestId())
            .orElseThrow(() -> new ResourceNotFoundException("FactoringRequest with ID " + signTaskDTO.getRequestId() + " was not founnd!"));

        FlowableTaskDTO gdprSignTask = this.flowableService.getTasksForProcess(factoringRequest.getProcessId(), false).getData()
            .stream()
            .filter(task -> task.getTaskDefinitionKey().equalsIgnoreCase(signTaskDTO.getTaskId())) // TODO: remove hardcoded
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Wait Gtc Sign Task for factoring request " + signTaskDTO.getRequestId() + " was not founnd!"));

        String taskId = gdprSignTask.getId();

        if (signTaskDTO.getTaskId().equalsIgnoreCase("waitBankConnect")) {
            AispDTO aisp = psd2Service.getAisp(applicationProperties.getApiKeys().get("microserviceKey"), factoringRequest.getId());

            HashMap<String, Object> values = new HashMap<>();
            values.put("banks", aisp.getBanks());

            CollectionContainer collectionContainer = new CollectionContainer();
            collectionContainer.setFactoringRequest(factoringRequest);
            collectionContainer.setType("AISP_COLLECTION");
            collectionContainer.setValues(values);
            collectionContainerService.save(collectionContainer);
        }


        if (signTaskDTO.getVariables() != null && signTaskDTO.getVariables().size() > 0) {
            Variables variables = new Variables();
            signTaskDTO.getVariables().forEach((key, value) -> {
                FlowableVariableDTO var = new FlowableVariableDTO();
                var.setName(key);
                var.setValue(value);
                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                    var.setType("boolean");
                    var = new FlowableVariableDTO(key, Boolean.valueOf(value));
                }
                variables.add(var);
            });

            this.flowableService.updateProcessInstanceVariables(factoringRequest.getProcessId(), variables);
            this.flowableService.completeTaskWithVariables(taskId, variables);
        } else {
            this.flowableService.completeTask(taskId);
        }


        return factoringRequest;
    }


    private Company createAndSaveCompany(String fiscalCode) {
        Company company = new Company();
        company.setCui(fiscalCode);
        return companyService.save(company);
    }

    private Variables createFlowableVariables(AdherentDTO adherent, FactoringRequest factoringRequest) {
        Variables variables = new Variables();

        FlowableVariableDTO taxCodeVariable = new FlowableVariableDTO();
        taxCodeVariable.setName("taxCode");
        taxCodeVariable.setValue(adherent.getTaxCode());
        variables.add(taxCodeVariable);

        FlowableVariableDTO tokenVariable = new FlowableVariableDTO();
        tokenVariable.setName("token");
        tokenVariable.setValue(coreService.getToken());
        variables.add(tokenVariable);

        FlowableVariableDTO requestIdVariable = new FlowableVariableDTO();
        requestIdVariable.setName("requestId");
        requestIdVariable.setValue(factoringRequest.getId());
        variables.add(requestIdVariable);

        return variables;
    }
//
//    private void createProvierServiceCalls(FactoringRequest factoringRequest) {
//        ProviderServiceCalls providerServiceCalls = new ProviderServiceCalls();
//        providerServiceCalls.setFactoringRequestId(factoringRequest.getId());
//        providerCallService.save(providerServiceCalls);
//    }

    private FactoringRequest createFactoringRequest(AdherentDTO adherent, Company company) {
        FactoringRequest factoringRequest = new FactoringRequest();
        factoringRequest.setCompany(company);
        factoringRequest.setDate(Instant.now());
        factoringRequest.setAdherentId(adherent.getId());
        factoringRequest.setStatus(Status.NEW);
        factoringRequest = factoringRequestService.save(factoringRequest);
        return factoringRequest;
    }


}

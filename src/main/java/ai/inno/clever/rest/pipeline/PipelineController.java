package ai.inno.clever.rest.pipeline;

import ai.inno.clever.domain.FactoringRequest;
import ai.inno.clever.domain.enumeration.Status;
import ai.inno.clever.service.FactoringRequestService;
import ai.inno.clever.service.PipelineService;
import ai.inno.clever.service.dto.SignTaskDTO;
import ai.inno.clever.service.dto.core.AdherentDTO;
import ai.inno.clever.service.dto.core.CompleteSurveyBody;
import ai.inno.clever.service.flowable.entities.FlowableStatusDTO;
import ai.inno.clever.service.flowable.entities.FlowableTaskListDTO;
import ai.innoclever.config.ApplicationProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.http.auth.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import java.util.Optional;

@RestController
@RequestMapping("/api/pipeline")
public class PipelineController {
    private final Logger log = LoggerFactory.getLogger(PipelineController.class);

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    FactoringRequestService factoringRequestService;

    public static final String RBRO_FLOW = "rbroFlow";


    @PostMapping("/start/{pipelineName}")
    public ResponseEntity<FactoringRequest> startFlow(@PathVariable(name = "pipelineName", required = false) String pipelineName, @RequestBody(required = false) AdherentDTO adherent) throws Exception {
        if (pipelineName == null) {
            pipelineName = RBRO_FLOW;
        }
        FactoringRequest factoringRequest = pipelineService.startFlow(pipelineName, adherent);
        return ResponseEntity
            .ok(factoringRequest);
    }


    @PostMapping("/complete-survey/{requestId}")
    public ResponseEntity<FactoringRequest> completeSurveyTask(
        @PathVariable("requestId") String requestId,
        @RequestBody(required = false) AdherentDTO adherent
    ) throws Exception {
        log.info("Complete survey {}.", requestId);
        CompleteSurveyBody body = new CompleteSurveyBody();
        body.setAdherent(adherent);
        body.setAispType("NO_DATA"); // change in what you receive from core
        FactoringRequest factoringRequest = pipelineService.completeSurveyTask(requestId, body);

        return ResponseEntity
            .ok(factoringRequest);
    }

    @PostMapping("/microservice/start/{pipelineName}")
    public ResponseEntity<FactoringRequest> startFlowFromMicroservice(@RequestHeader("microservice_key") String microserviceKey, @PathVariable(name = "pipelineName", required = false) String pipelineName, @RequestBody(required = false) AdherentDTO adherent) throws Exception {
        if (!applicationProperties.getApiKeys().get("microserviceKey").equalsIgnoreCase(microserviceKey)) {
            throw new AuthenticationException("Header: microservice_key not valid!");
        }
        if (pipelineName == null) {
            pipelineName = "rbroFlow";
        }
        FactoringRequest factoringRequest = pipelineService.startFlow(pipelineName, adherent);
        return ResponseEntity
            .ok(factoringRequest);
    }


    @PostMapping("/microservice/start-update/{pipelineName}/{factoringRequestId}")
    public ResponseEntity<FactoringRequest> startUpdateFlow(@RequestHeader("microservice_key") String microserviceKey,
        @PathVariable(name = "pipelineName", required = true) String pipelineName,
        @PathVariable(name = "factoringRequestId", required = true) String factoringRequestId) throws Exception {
        if (!applicationProperties.getApiKeys().get("microserviceKey").equalsIgnoreCase(microserviceKey)) {
            throw new AuthenticationException("Header: microservice_key not valid!");
        }

        FactoringRequest factoringRequest = pipelineService.startUpdateFlow(pipelineName, factoringRequestId);
        return ResponseEntity
            .ok(factoringRequest);
    }

    @PostMapping("/microservice/complete-survey/{requestId}")
    public ResponseEntity<FactoringRequest> completeSurveyTaskMicroservice( //restart
        @RequestHeader("microservice_key") String microserviceKey,
        @PathVariable("requestId") String requestId,
        @RequestBody(required = false) CompleteSurveyBody body
    ) throws Exception {
        if (!applicationProperties.getApiKeys().get("microserviceKey").equalsIgnoreCase(microserviceKey)) {
            throw new AuthenticationException("Header: microservice_key not valid!");
        }
        if(!StringUtils.isNumeric(body.getAdherent().getTaxCode())){
            throw new BadRequestException("TaxCode should be numeric only!");
        }
        FactoringRequest factoringRequest = pipelineService.completeSurveyTask(requestId, body);

        return ResponseEntity
            .ok(factoringRequest);
    }

    @PostMapping("/microservice/sign-task")
    public ResponseEntity<FactoringRequest> signTask(
        @RequestHeader("microservice_key") String microserviceKey,
        @RequestBody SignTaskDTO signTask
    ) throws Exception {
        if (!applicationProperties.getApiKeys().get("microserviceKey").equalsIgnoreCase(microserviceKey)) {
            throw new AuthenticationException("Header: microservice_key not valid!");
        }

        log.debug(signTask.toString());

        FactoringRequest factoringRequest = pipelineService.signTask(signTask);
        return ResponseEntity
            .ok(factoringRequest);
    }

    @GetMapping("/status/{requestId}")
    public ResponseEntity<FlowableStatusDTO> getFactoringRequestStatus(@PathVariable("requestId") String requestId) {
        Optional<FactoringRequest> factoringRequestOptional = factoringRequestService.findOne(requestId);
        if (factoringRequestOptional.isPresent()) {
            FlowableStatusDTO status = pipelineService.getFactoringRequestFlowStatus(factoringRequestOptional.get().getId());
            FactoringRequest factoringRequest = factoringRequestService.findOne(requestId).get();
            if (status != null){
            if (status.getPercentage() >= 100 && factoringRequest.getStatus() == Status.IN_PROGRESS) {
                status.setPercentage(99);
            }
            status.setFactoringRequest(factoringRequest);
            return ResponseEntity
                .ok(status);
        }else{
                status = new FlowableStatusDTO();
                status.setFactoringRequest(factoringRequest);
                status.setRequestId(factoringRequest.getId());
                status.setPercentage(100);
                status.setMessage("10 - DB cleanup");
                return ResponseEntity.ok(status);
            }

    }     return ResponseEntity
            .notFound().build();
    }

    @GetMapping("/microservice/status/{requestId}")
    public ResponseEntity<FlowableStatusDTO> getFactoringRequestStatusByApiKey(@RequestHeader("api_key") String apiKey, @PathVariable("requestId") String requestId) throws AuthenticationException {
        if (!applicationProperties.getApiKeys().get("testKey").equalsIgnoreCase(apiKey)) {
            throw new AuthenticationException("Header: microservice_key not valid!");
        }
        Optional<FactoringRequest> factoringRequestOptional = factoringRequestService.findOne(requestId);
        if(factoringRequestOptional.isPresent()){
            FlowableStatusDTO status = pipelineService.getFactoringRequestFlowStatus(factoringRequestOptional.get().getId());
            FactoringRequest factoringRequest = factoringRequestService.findOne(requestId).get();
            if(status.getPercentage() == 100 && factoringRequest.getStatus() == Status.IN_PROGRESS){
                status.setPercentage(status.getPercentage()-1);
            }
            status.setFactoringRequest(factoringRequest);
            return ResponseEntity
                .ok(status);
        }
        return ResponseEntity
            .notFound().build();
    }

    @GetMapping("/tasks/{requestId}")
    public ResponseEntity<FlowableTaskListDTO> getTasks(@PathVariable("requestId") String requestId) {
        FlowableTaskListDTO tasks = pipelineService.getTasks(requestId);
        return ResponseEntity
            .ok(tasks);
    }



    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        log.error("Returning HTTP 400 Bad Request", e);
    }

}

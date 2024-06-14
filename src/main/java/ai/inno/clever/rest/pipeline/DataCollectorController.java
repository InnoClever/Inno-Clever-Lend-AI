package ai.inno.clever.rest.pipeline;

import ai.inno.clever.domain.CollectionContainer;
import ai.inno.clever.domain.FactoringRequest;
import ai.inno.clever.service.*;
import ai.inno.clever.service.dto.core.AdherentDTO;
import ai.inno.clever.service.feign.CoreService;
import ai.inno.clever.service.flowable.FlowableService;
import ai.innoclever.config.ApplicationProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.auth.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/data-collector")
public class DataCollectorController {

    public static final String RBRO_FLOW = "rbroFlow";
    private final Logger log = LoggerFactory.getLogger(DataCollectorController.class);

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    FlowableService flowableService;

    @Autowired
    CollectionContainerService collectionContainerService;

    @Autowired
    FactoringRequestService factoringRequestService;

    @Autowired
    UserService userService;

    @Autowired
    private CoreService coreService;

    @Autowired
    private CollectionContainerCallbackService collectionContainerCallbackService;

//restart

    @PostMapping("/complete/{requestId}/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> completeDataCollectorTask(
        @RequestHeader("api_key") String headerApiKey,
        @PathVariable("requestId") String requestId,
        @PathVariable("taskId") String taskId,
        @RequestBody(required = false) CollectorResponse collectorResponse,
        HttpServletRequest request) throws Exception {

        if (!applicationProperties.getApiKeys().get("collectorsApiKey").equalsIgnoreCase(headerApiKey)) {
            throw new AuthenticationException("Api_key not valid!");
        }

        collectionContainerCallbackService.completeDataCollectorTask(requestId, taskId, collectorResponse);


        return ResponseEntity
            .ok(null);
    }

    @PostMapping("/ongoing/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> ongoingDataCollector(
        @RequestHeader("api_key") String headerApiKey,
        @PathVariable("requestId") String requestId,
        @RequestBody(required = false) CollectorResponse collectorResponse,
        HttpServletRequest request) throws URISyntaxException, AuthenticationException, JsonProcessingException {

        if (!applicationProperties.getApiKeys().get("collectorsApiKey").equalsIgnoreCase(headerApiKey)) {
            throw new AuthenticationException("api_key not valid!");
        }

        collectionContainerCallbackService.ongoingDataCollector(requestId, collectorResponse);

        return ResponseEntity
            .ok(null);
    }

    @PostMapping("/ongoing/{requestId}/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> ongoingDataCollectorWithTaskId(
        @RequestHeader("api_key") String headerApiKey,
        @PathVariable("requestId") String requestId,
        @PathVariable(value = "taskId") String taskId,
        @RequestBody(required = false) CollectorResponse collectorResponse,
        HttpServletRequest request) throws AuthenticationException {

        if (!applicationProperties.getApiKeys().get("collectorsApiKey").equalsIgnoreCase(headerApiKey)) {
            throw new AuthenticationException("api_key not valid!");
        }

        collectionContainerCallbackService.ongoingDataCollectorWithTaskId(requestId, taskId, collectorResponse);

        return ResponseEntity
            .ok(null);
    }

    @PostMapping("/start-pipeline")
    public ResponseEntity<String> startPipeline(
        @RequestHeader("api_key") String headerApiKey,
        @PathVariable(name = "pipelineName", required = false) String pipelineName,
        @RequestBody AdherentDTO adherent) throws Exception {
        if (!applicationProperties.getApiKeys().get("collectorsApiKey").equalsIgnoreCase(headerApiKey)) {
            throw new AuthenticationException("api_key not valid!");
        }
        if (StringUtils.isEmpty(pipelineName)) {
            pipelineName = RBRO_FLOW;
        }

        log.info("Start pipeline: {}.", pipelineName);
        FactoringRequest factoringRequest = pipelineService.startFlow(pipelineName, adherent);

        String response = String.format("{\n\t\"requestId\": %s\n}", factoringRequest.getId());

        return ResponseEntity
            .ok()
            .body(response);
    }

    @GetMapping("/collection-data/{requestId}")
    public ResponseEntity<List<CollectionContainer>> getCollectionData(
        @RequestHeader("api_key") String headerApiKey,
        @PathVariable("requestId") String requestId,
        @RequestParam(required = false, defaultValue = "") String type
    )
        throws AuthenticationException {
        if (!applicationProperties.getApiKeys().get("collectorsApiKey").equalsIgnoreCase(headerApiKey) &&
            !applicationProperties.getApiKeys().get("testKey").equalsIgnoreCase(headerApiKey)) {
            throw new AuthenticationException("api_key not valid!");
        }

        List<CollectionContainer> data = StringUtils.isNotEmpty(type) ?
            this.collectionContainerService.findByFactoringRequestIdAndType(requestId, type)
            : this.collectionContainerService.findByFactoringRequestId(requestId);

        return ResponseEntity
            .ok(data);
    }

    @GetMapping("/collection-containers-filter")
    public ResponseEntity<List<CollectionContainer>> getAllCollectionContainersMultipleFilter(
        @RequestHeader(value = "api_key", required = false) String headerApiKey,
        @RequestParam(required = false) Map<String, String> queryParams) throws AuthenticationException {
        if (StringUtils.isNotEmpty(headerApiKey))
            if (!applicationProperties.getApiKeys().get("collectorsApiKey").equalsIgnoreCase(headerApiKey) &&
                !applicationProperties.getApiKeys().get("testKey").equalsIgnoreCase(headerApiKey)) {
                throw new AuthenticationException("Header api_key not valid!");
            }
        if (StringUtils.isEmpty(headerApiKey) && SecurityContextHolder.getContext().getAuthentication().getPrincipal()
            .toString().equalsIgnoreCase("anonymousUser")) {
            throw new AuthenticationException("User not authorised!");
        }
        if (StringUtils.isEmpty(headerApiKey) &&
            (!userService.getLoggedInUser().getAuthorities().contains("ROLE_ADMIN")
                && !userService.getLoggedInUser().getAuthorities().contains("ROLE_CREDIT_OFFICER"))) {
            throw new AuthenticationException("User not authorised!");
        }

        log.debug("REST request to get a page query of CollectionContainers");
        List<CollectionContainer> results = collectionContainerService.findByFieldNameAndFieldValue(queryParams);
        return ResponseEntity.ok().body(results);
    }



    // Ask flowable if the task is critical or not


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handle(HttpMessageNotReadableException e) {
        log.error("Returning HTTP 400 Bad Request", e);
        return ResponseEntity.badRequest().body(e.toString());
    }
}

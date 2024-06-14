package ai.inno.clever.rest.pipeline;

import ai.inno.clever.service.FactoringRequestService;
import ai.inno.clever.service.dto.graphs.GraphRequest;
import ai.inno.clever.service.dto.graphs.GraphType;
import ai.inno.clever.service.dto.graphs.Graphs;
import ai.innoclever.config.ApplicationProperties;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/api/graphs")
public class GraphsController {
    private final Logger log = LoggerFactory.getLogger(GraphsController.class);

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    FactoringRequestService factoringRequestService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Gson gson;

    @GetMapping(value = "/info/{requestId}")
    public ResponseEntity<Graphs> getGraphInfo(
        @PathVariable("requestId") String requestId) throws URISyntaxException {

        String graphInfoURL = applicationProperties.getGraphInfoUrl();

        URI uri = new URI(graphInfoURL);


        GraphRequest body = new GraphRequest();
        body.setRequestId(requestId);
        body.setCallbackUrl(applicationProperties.getDataCollectorsCallbackUrl() + applicationProperties.getDataCollectorCallbackPath());

        HttpEntity<GraphRequest> graphRequestHttpEntity = new HttpEntity<>(body);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, graphRequestHttpEntity, String.class);
        Type type = new TypeToken<Graphs>() {
        }.getType();
        Graphs response = gson.fromJson(result.getBody(), type);


        return ResponseEntity
            .ok(response);
    }

    @GetMapping(value = "/generate/{requestId}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getGraph(
        @PathVariable("requestId") String requestId,
        @RequestParam Map<String, String> dataQuery) throws URISyntaxException {

//        log.debug("debtorCUI: {}", debtorCui);
        log.debug("DataQuery: {}", dataQuery);
        URI uri = null;
        if (dataQuery.get("graphName").equalsIgnoreCase(GraphType.RELATIONS.name())) {
            String relationsGraphUrl = applicationProperties.getGraphRelationsUrl();
            uri = new URI(relationsGraphUrl);
        } else if (dataQuery.get("graphName").equalsIgnoreCase(GraphType.BUSINESS_GROUPS.name())) {
            String businessGroupsUrl = applicationProperties.getGraphBusinessGroupsUrl();
            uri = new URI(businessGroupsUrl);
        }

        if(!dataQuery.containsKey("pathId")){
            dataQuery.put("pathId", "0");
        }

        dataQuery.put("requestId", requestId);
        dataQuery.put("callbackUrl", String.format("%s%s",applicationProperties.getDataCollectorsCallbackUrl(), applicationProperties.getDataCollectorCallbackPath()));

        HttpEntity<Map<String, String>> graphRequestHttpEntity = new HttpEntity<>(dataQuery);

        log.debug("BODY: {}", graphRequestHttpEntity.getBody());

        ResponseEntity<String> result = restTemplate.postForEntity(uri, graphRequestHttpEntity, String.class);
        return ResponseEntity
            .ok(result.getBody());
    }


    @GetMapping(value = "/relations/{requestId}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getRelationGraph(
        @PathVariable("requestId") String requestId,
        @RequestParam("debtorCUI") String debtorCui,
        @RequestParam(value = "pathId", required = false) String pathId) throws URISyntaxException {

        String relationsGraphUrl = applicationProperties.getGraphRelationsUrl();

        URI uri = new URI(relationsGraphUrl);


        GraphRequest body = new GraphRequest();
        body.setDebtorCUI(debtorCui);
        body.setRequestId(requestId);
        body.setCallbackUrl(applicationProperties.getDataCollectorsCallbackUrl() + applicationProperties.getDataCollectorCallbackPath());
        if (pathId != null) {
            body.setPathId(pathId);
        }

        HttpEntity<GraphRequest> graphRequestHttpEntity = new HttpEntity<>(body);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, graphRequestHttpEntity, String.class);
        return ResponseEntity
            .ok(result.getBody());
    }

    @GetMapping(value = "/business-groups/{requestId}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getBusinessGroups(
        @PathVariable("requestId") String requestId,
        @RequestParam("debtorCUI") String debtorCui,
        @RequestParam(value = "pathId", required = false) String pathId) throws URISyntaxException {

        String businessGroupsUrl = applicationProperties.getGraphBusinessGroupsUrl();

        URI uri = new URI(businessGroupsUrl);


        GraphRequest body = new GraphRequest();
        body.setDebtorCUI(debtorCui);
        body.setRequestId(requestId);
        body.setCallbackUrl(applicationProperties.getDataCollectorsCallbackUrl() + applicationProperties.getDataCollectorCallbackPath());
        if (pathId != null) {
            body.setPathId(pathId);
        }

        HttpEntity<GraphRequest> graphRequestHttpEntity = new HttpEntity<>(body);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, graphRequestHttpEntity, String.class);
        return ResponseEntity
            .ok(result.getBody());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        log.error("Returning HTTP 400 Bad Request", e);
    }

}

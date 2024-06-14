package ai.inno.clever.rest;

import ai.inno.clever.domain.FactoringRequest;
import ai.inno.clever.domain.ProviderCall;
import ai.inno.clever.repository.ProviderCallRepository;
import ai.inno.clever.rest.errors.BadRequestAlertException;
import ai.inno.clever.service.PipelineService;
import ai.inno.clever.service.ProviderCallService;
import ai.innoclever.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing {@link FactoringRequest}.
 */
@RestController
@RequestMapping("/api")
public class ProviderServiceCallsResource {

    private final Logger log = LoggerFactory.getLogger(ProviderServiceCallsResource.class);

    private static final String ENTITY_NAME = "rbroProviderServiceCalls";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    private final ProviderCallService providerCallService;

    private final ProviderCallRepository providerCallRepository;


    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    PipelineService pipelineService;

    public ProviderServiceCallsResource(
        ProviderCallService providerCallService,
        ProviderCallRepository providerCallRepository
    ) {
        this.providerCallService = providerCallService;
        this.providerCallRepository = providerCallRepository;
    }

    /**
     * {@code POST  /provider-calls : Create a new ProviderServiceCalls.
     *
     * @param ProviderServiceCalls the ProviderServiceCalls to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ProviderServiceCalls, or with status {@code 400 (Bad Request)} if the ProviderServiceCalls has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/provider-calls")
    public ResponseEntity<ProviderCall> createProviderServiceCalls(@RequestBody ProviderCall providerServiceCalls)
        throws URISyntaxException {
        log.debug("REST request to save ProviderServiceCalls : {}", providerServiceCalls);
        if (providerServiceCalls.getId() != null) {
            throw new BadRequestAlertException("A new ProviderServiceCalls cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProviderCall result = providerCallService.save(providerServiceCalls);
        return ResponseEntity
            .created(new URI("/api/provider-service-calls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }


    @PostMapping("/provider-calls/register")
    public ResponseEntity<List<ProviderCall>> registerServiceCall(
        @RequestHeader("microservice_key") String microserviceKey,
        @RequestBody @Valid List<ProviderCall> providerCalls) {
        log.debug("REST request to save a list of ProviderServiceCalls with length: " + providerCalls.size());

        providerCalls.forEach(providerCall -> {
            if(providerCall.getFactoringRequestId() == null){
                providerCall.setFactoringRequestId(ProviderCallService.UNASIGNED_CALLS);
            }
            providerCall.setId(UUID.randomUUID().toString());
            providerCall.setTimestamp(Instant.now());
            providerCallService.save(providerCall);
        });

        return ResponseEntity.ok().body(providerCalls);
    }


    /**
     * {@code PUT  /provider-calls/:id} : Updates an existing ProviderServiceCalls.
     *
     * @param id                   the id of the ProviderServiceCalls to save.
     * @param providerServiceCalls the ProviderServiceCalls to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ProviderServiceCalls,
     * or with status {@code 400 (Bad Request)} if the ProviderServiceCalls is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ProviderServiceCalls couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/provider-calls/{id}")
    public ResponseEntity<ProviderCall> updateProviderServiceCalls(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProviderCall providerServiceCalls
    ) throws URISyntaxException {
        log.debug("REST request to update FactoringRequest : {}, {}", id, providerServiceCalls);
        if (providerServiceCalls.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, providerServiceCalls.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!providerCallRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProviderCall result = providerCallService.save(providerServiceCalls);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, providerServiceCalls.getId()))
            .body(result);
    }

    /**
     * {@code GET  /provider-calls} : get all the ProviderServiceCalls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ProviderServiceCalls in body.
     */
    @GetMapping("/provider-calls")
    public ResponseEntity<List<ProviderCall>> getAllFactoringRequestsServiceCalls(Pageable pageable) {
        log.debug("REST request to get a page of ProviderServiceCalls");
        Page<ProviderCall> page = providerCallService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/provider-calls/microservice/{requestId}")
    public ResponseEntity<List<ProviderCall>> getByRequestId(
        @RequestHeader("microservice_key") String microserviceKey,
        @PathVariable("requestId") String requestId) {
        log.debug("REST request to get a page of ProviderServiceCalls");
        List<ProviderCall> providerCalls = providerCallService.findByFactoringRequestId(requestId);
        return ResponseEntity.ok(providerCalls);
    }


    /**
     * {@code GET  /provider-calls/:id} : get the "id" ProviderServiceCalls.
     *
     * @param id the id of the ProviderServiceCalls to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ProviderServiceCalls, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/provider-calls/{id}")
    public ResponseEntity<ProviderCall> getProviderServiceCalls(@PathVariable String id) {
        log.debug("REST request to get ProviderServiceCalls : {}", id);
        Optional<ProviderCall> factoringRequest = providerCallService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factoringRequest);
    }

    /**
     * {@code DELETE  /provider-calls/:id} : delete the "id" ProviderServiceCalls.
     *
     * @param id the id of the ProviderServiceCalls to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/provider-calls/{id}")
    public ResponseEntity<Void> deleteFactoringRequestServiecCalls(@PathVariable String id) {
        log.debug("REST request to delete ProviderServiceCalls : {}", id);
        providerCallService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/provider-calls?query=:query} : search for the ProviderServiceCalls corresponding
     * to the query.
     *
     * @param query    the query of the ProviderServiceCalls search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/provider-calls")
    public ResponseEntity<List<ProviderCall>> searchFactoringRequestsServiceCalls(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FactoringRequests for query {}", query);
        Page<ProviderCall> page = providerCallService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        log.error("Returning HTTP 400 Bad Request", e);
    }
}

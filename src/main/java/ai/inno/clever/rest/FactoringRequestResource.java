package ai.inno.clever.rest;

import ai.inno.clever.domain.FactoringRequest;
import ai.inno.clever.domain.enumeration.Status;
import ai.inno.clever.repository.FactoringRequestRepository;
import ai.inno.clever.rest.errors.BadRequestAlertException;
import ai.inno.clever.service.FactoringRequestService;
import ai.inno.clever.service.PipelineService;
import ai.innoclever.config.ApplicationProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.http.auth.AuthenticationException;
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

import javax.ws.rs.BadRequestException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link FactoringRequest}.
 */
@RestController
@RequestMapping("/api")
public class FactoringRequestResource {

    private final Logger log = LoggerFactory.getLogger(FactoringRequestResource.class);

    private static final String ENTITY_NAME = "rbroFactoringRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FactoringRequestService factoringRequestService;

    private final FactoringRequestRepository factoringRequestRepository;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    PipelineService pipelineService;

    public FactoringRequestResource(
        FactoringRequestService factoringRequestService,
        FactoringRequestRepository factoringRequestRepository
    ) {
        this.factoringRequestService = factoringRequestService;
        this.factoringRequestRepository = factoringRequestRepository;
    }

    /**
     * {@code POST  /factoring-requests} : Create a new factoringRequest.
     *
     * @param factoringRequest the factoringRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new factoringRequest, or with status {@code 400 (Bad Request)} if the factoringRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/factoring-requests")
    public ResponseEntity<FactoringRequest> createFactoringRequest(@RequestBody FactoringRequest factoringRequest)
        throws URISyntaxException {
        log.debug("REST request to save FactoringRequest : {}", factoringRequest);
        if (factoringRequest.getId() != null) {
            throw new BadRequestAlertException("A new factoringRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FactoringRequest result = factoringRequestService.save(factoringRequest);
        return ResponseEntity
            .created(new URI("/api/factoring-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    @PostMapping("/factoring-requests/{id}/decision")
    public ResponseEntity<FactoringRequest> decideRequest(
        @PathVariable(value = "id", required = true) final String id,
        @RequestBody HashMap<String, String> decision)
        throws URISyntaxException {
        log.debug("REST request to decide FactoringRequest : {}", decision);
        FactoringRequest result = null;
        Optional<FactoringRequest> factoringRequestOptional = factoringRequestService.findOne(id);
        if (factoringRequestOptional.isPresent()) {
            FactoringRequest factoringRequest = factoringRequestOptional.get();

            if (factoringRequest.getStatus() == Status.FINISHED) {

                if (decision.get("decision").equalsIgnoreCase("APPROVE")) {
                    factoringRequest.setStatus(Status.APPROVED);
                } else {
                    factoringRequest.setStatus(Status.REJECTED);
                }
                result = factoringRequestService.save(factoringRequest);
            } else {
                throw new BadRequestAlertException("Factoring Request ", ENTITY_NAME, "notFinished");
            }
        } else {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /factoring-requests/:id} : Updates an existing factoringRequest.
     *
     * @param id               the id of the factoringRequest to save.
     * @param factoringRequest the factoringRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factoringRequest,
     * or with status {@code 400 (Bad Request)} if the factoringRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the factoringRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/factoring-requests/{id}")
    public ResponseEntity<FactoringRequest> updateFactoringRequest(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody FactoringRequest factoringRequest
    ) throws URISyntaxException {
        log.debug("REST request to update FactoringRequest : {}, {}", id, factoringRequest);
        if (factoringRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factoringRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factoringRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        factoringRequest.getDebtors().forEach(debtorDTO -> {
            if (!StringUtils.isNumeric(debtorDTO.getTaxCode())) {
                throw new BadRequestException("TaxCode should be numeric only!");
            }
        });

        FactoringRequest result = factoringRequestService.save(factoringRequest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factoringRequest.getId()))
            .body(result);
    }

    @PutMapping("/factoring-requests/microservice/{id}")
    public ResponseEntity<FactoringRequest> updateFactoringRequestMicroservice(
        @PathVariable(value = "id", required = false) final String id,
        @RequestHeader("microservice_key") String testKey,
        @RequestBody FactoringRequest factoringRequest
    ) throws AuthenticationException {
        log.debug("REST request to update FactoringRequest : {}, {}", id, factoringRequest);
        if (factoringRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factoringRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factoringRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        if (!applicationProperties.getApiKeys().get("testKey").equalsIgnoreCase(testKey) && !applicationProperties.getApiKeys().get("microserviceKey").equalsIgnoreCase(testKey)) {
            throw new AuthenticationException("Header: api_key not valid!");
        }

        factoringRequest.getDebtors().forEach(debtorDTO -> {
            if (!StringUtils.isNumeric(debtorDTO.getTaxCode())) {
                throw new BadRequestException("TaxCode should be numeric only!");
            }
        });

        FactoringRequest result = factoringRequestService.save(factoringRequest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factoringRequest.getId()))
            .body(result);
    }


    /**
     * {@code PATCH  /factoring-requests/:id} : Partial updates given fields of an existing factoringRequest, field will ignore if it is null
     *
     * @param id               the id of the factoringRequest to save.
     * @param factoringRequest the factoringRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factoringRequest,
     * or with status {@code 400 (Bad Request)} if the factoringRequest is not valid,
     * or with status {@code 404 (Not Found)} if the factoringRequest is not found,
     * or with status {@code 500 (Internal Server Error)} if the factoringRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/factoring-requests/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FactoringRequest> partialUpdateFactoringRequest(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody FactoringRequest factoringRequest
    ) throws URISyntaxException {
        log.debug("REST request to partial update FactoringRequest partially : {}, {}", id, factoringRequest);
        if (factoringRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factoringRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factoringRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FactoringRequest> result = factoringRequestService.partialUpdate(factoringRequest);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factoringRequest.getId())
        );
    }

    /**
     * {@code GET  /factoring-requests} : get all the factoringRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of factoringRequests in body.
     */
    @GetMapping("/factoring-requests")
    public ResponseEntity<List<FactoringRequest>> getAllFactoringRequests(Pageable pageable) {
        log.debug("REST request to get a page of FactoringRequests");
        Page<FactoringRequest> page = factoringRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/factoring-requests/microservice/{requestId}")
    public ResponseEntity<FactoringRequest> getByRequestId(
    //    @RequestHeader("microservice_key") String  microserviceKey,
        @PathVariable("requestId") String requestId, Pageable pageable) {
        log.debug("REST request to get a page of FactoringRequests");
        Optional<FactoringRequest> factoringRequest = factoringRequestService.findOne(requestId);
        return ResponseUtil.wrapOrNotFound(factoringRequest);
    }

    @GetMapping("/factoring-requests/adherent/{adherentId}")
    public ResponseEntity<List<FactoringRequest>> getByRequestId(
        @PathVariable("adherentId") Long adherentId,
        Pageable pageable
    ) {
        log.debug("REST request to get a page of FactoringRequests");
        List<FactoringRequest> factoringRequestList = factoringRequestService.findByAdherentId(adherentId);

        return ResponseEntity.ok().body(factoringRequestList);
    }

    /**
     * {@code GET  /factoring-requests/:id} : get the "id" factoringRequest.
     *
     * @param id the id of the factoringRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the factoringRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/factoring-requests/{id}")
    public ResponseEntity<FactoringRequest> getFactoringRequest(@PathVariable String id) {
        log.debug("REST request to get FactoringRequest : {}", id);
        Optional<FactoringRequest> factoringRequest = factoringRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factoringRequest);
    }

    /**
     * {@code DELETE  /factoring-requests/:id} : delete the "id" factoringRequest.
     *
     * @param id the id of the factoringRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/factoring-requests/{id}")
    public ResponseEntity<Void> deleteFactoringRequest(@PathVariable String id) {
        log.debug("REST request to delete FactoringRequest : {}", id);
        factoringRequestService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/factoring-requests?query=:query} : search for the factoringRequest corresponding
     * to the query.
     *
     * @param query    the query of the factoringRequest search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/factoring-requests")
    public ResponseEntity<List<FactoringRequest>> searchFactoringRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FactoringRequests for query {}", query);
        Page<FactoringRequest> page = factoringRequestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        log.error("Returning HTTP 400 Bad Request", e);
    }
}

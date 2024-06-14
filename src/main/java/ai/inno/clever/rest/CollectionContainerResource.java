package ai.inno.clever.rest;

import ai.inno.clever.domain.CollectionContainer;
import ai.inno.clever.repository.CollectionContainerRepository;
import ai.inno.clever.rest.errors.BadRequestAlertException;
import ai.inno.clever.security.AuthoritiesConstants;
import ai.inno.clever.service.CollectionContainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link CollectionContainer}.
 */
@RestController
@RequestMapping("/api")
public class CollectionContainerResource {

    private final Logger log = LoggerFactory.getLogger(CollectionContainerResource.class);

    private static final String ENTITY_NAME = "rbroCollectionContainer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollectionContainerService collectionContainerService;

    private final CollectionContainerRepository collectionContainerRepository;

    public CollectionContainerResource(
        CollectionContainerService collectionContainerService,
        CollectionContainerRepository collectionContainerRepository
    ) {
        this.collectionContainerService = collectionContainerService;
        this.collectionContainerRepository = collectionContainerRepository;
    }

    /**
     * {@code POST  /collection-containers} : Create a new collectionContainer.
     *
     * @param collectionContainer the collectionContainer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collectionContainer, or with status {@code 400 (Bad Request)} if the collectionContainer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collection-containers")
    public ResponseEntity<CollectionContainer> createCollectionContainer(@RequestBody CollectionContainer collectionContainer)
        throws URISyntaxException {
        log.debug("REST request to save CollectionContainer : {}", collectionContainer);
        if (collectionContainer.getId() != null) {
            throw new BadRequestAlertException("A new collectionContainer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollectionContainer result = collectionContainerService.save(collectionContainer);
        return ResponseEntity
            .created(new URI("/api/collection-containers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    @PostMapping("/collection-containers/generate-new-sublimits")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<CollectionContainer>> createCollectionContainer()
        throws URISyntaxException {
        log.debug("REST request to generate new sublimits CollectionContainer");
        List<CollectionContainer> returnList = new ArrayList<>();
        List<CollectionContainer> results = collectionContainerService.findByType("SUBLIMITS");
        try {
            for (CollectionContainer collectionContainer : results) {
                CollectionContainer collectionContainerNonRecourse = (CollectionContainer) collectionContainer.clone();
                collectionContainerNonRecourse.setType("SUBLIMITS_NO_RECOURSE");
                CollectionContainer collectionContainerRecourse = (CollectionContainer) collectionContainer.clone();
                collectionContainerRecourse.setType("SUBLIMITS_RECOURSE");

                CollectionContainer collectionContainerNonRecourseInsurance = (CollectionContainer) collectionContainer.clone();
                collectionContainerNonRecourseInsurance.setType("SUBLIMITS_NO_RECOURSE_INSURANCE");


                collectionContainerNonRecourse = collectionContainerService.save(collectionContainerNonRecourse);

                collectionContainerRecourse = collectionContainerService.save(collectionContainerRecourse);

                collectionContainerNonRecourseInsurance = collectionContainerService.save(collectionContainerNonRecourseInsurance);

                returnList.add(collectionContainerRecourse);

                returnList.add(collectionContainerNonRecourseInsurance);

                returnList.add(collectionContainerNonRecourse);

            }
        } catch (Exception e) {
            log.error("Cannot clone and save the new sublimits: ", e);
        }
        return ResponseEntity.ok(returnList);
    }

    /**
     * {@code PUT  /collection-containers/:id} : Updates an existing collectionContainer.
     *
     * @param id                  the id of the collectionContainer to save.
     * @param collectionContainer the collectionContainer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collectionContainer,
     * or with status {@code 400 (Bad Request)} if the collectionContainer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collectionContainer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collection-containers/{id}")
    public ResponseEntity<CollectionContainer> updateCollectionContainer(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody CollectionContainer collectionContainer
    ) throws URISyntaxException {
        log.debug("REST request to update CollectionContainer : {}, {}", id, collectionContainer);
        if (collectionContainer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collectionContainer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collectionContainerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CollectionContainer result = collectionContainerService.save(collectionContainer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, collectionContainer.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /collection-containers/:id} : Partial updates given fields of an existing collectionContainer, field will ignore if it is null
     *
     * @param id                  the id of the collectionContainer to save.
     * @param collectionContainer the collectionContainer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collectionContainer,
     * or with status {@code 400 (Bad Request)} if the collectionContainer is not valid,
     * or with status {@code 404 (Not Found)} if the collectionContainer is not found,
     * or with status {@code 500 (Internal Server Error)} if the collectionContainer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/collection-containers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CollectionContainer> partialUpdateCollectionContainer(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody CollectionContainer collectionContainer
    ) throws URISyntaxException {
        log.debug("REST request to partial update CollectionContainer partially : {}, {}", id, collectionContainer);
        if (collectionContainer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collectionContainer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collectionContainerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CollectionContainer> result = collectionContainerService.partialUpdate(collectionContainer);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, collectionContainer.getId())
        );
    }

    /**
     * {@code GET  /collection-containers} : get all the collectionContainers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collectionContainers in body.
     */
    @GetMapping("/collection-containers")
    public ResponseEntity<List<CollectionContainer>> getAllCollectionContainers(Pageable pageable) {
//        log.debug("REST request to get a page of CollectionContainers");
        Page<CollectionContainer> page = collectionContainerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /collection-containers/:id} : get the "id" collectionContainer.
     *
     * @param id the id of the collectionContainer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collectionContainer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collection-containers/{id}")
    public ResponseEntity<CollectionContainer> getCollectionContainer(@PathVariable String id) {
//        log.debug("REST request to get CollectionContainer : {}", id);
        Optional<CollectionContainer> collectionContainer = collectionContainerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collectionContainer);
    }

    /**
     * {@code DELETE  /collection-containers/:id} : delete the "id" collectionContainer.
     *
     * @param id the id of the collectionContainer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collection-containers/{id}")
    public ResponseEntity<Void> deleteCollectionContainer(@PathVariable String id) {
        log.debug("REST request to delete CollectionContainer : {}", id);
        collectionContainerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/collection-containers?query=:query} : search for the collectionContainer corresponding
     * to the query.
     *
     * @param query    the query of the collectionContainer search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/collection-containers")
    public ResponseEntity<List<CollectionContainer>> searchCollectionContainers(@RequestParam String query, Pageable pageable) {
//        log.debug("REST request to search for a page of CollectionContainers for query {}", query);
        Page<CollectionContainer> page = collectionContainerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}

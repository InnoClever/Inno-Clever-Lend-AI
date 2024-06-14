package ai.inno.clever.service;

import ai.inno.clever.domain.FactoringRequest;
import ai.inno.clever.repository.FactoringRequestRepository;
import ai.inno.clever.repository.search.FactoringRequestSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link FactoringRequest}.
 */
@Service
public class FactoringRequestService {

    private final Logger log = LoggerFactory.getLogger(FactoringRequestService.class);

    private final FactoringRequestRepository factoringRequestRepository;

    private final FactoringRequestSearchRepository factoringRequestSearchRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Autowired
    PipelineService pipelineService;

    public FactoringRequestService(
        FactoringRequestRepository factoringRequestRepository,
        FactoringRequestSearchRepository factoringRequestSearchRepository
    ) {
        this.factoringRequestRepository = factoringRequestRepository;
        this.factoringRequestSearchRepository = factoringRequestSearchRepository;
    }

    /**
     * Save a factoringRequest.
     *
     * @param factoringRequest the entity to save.
     * @return the persisted entity.
     */
    public FactoringRequest save(FactoringRequest factoringRequest) {
        log.debug("Request to save FactoringRequest : {}", factoringRequest);
        FactoringRequest result = factoringRequestRepository.save(factoringRequest);
        factoringRequestSearchRepository.save(result);
        return result;
    }

    /**
     * Partially update a factoringRequest.
     *
     * @param factoringRequest the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FactoringRequest> partialUpdate(FactoringRequest factoringRequest) {
        log.debug("Request to partially update FactoringRequest : {}", factoringRequest);

        return factoringRequestRepository
            .findById(factoringRequest.getId())
            .map(
                existingFactoringRequest -> {
                    if (factoringRequest.getDate() != null) {
                        existingFactoringRequest.setDate(factoringRequest.getDate());
                    }
                    if (factoringRequest.getStatus() != null) {
                        existingFactoringRequest.setStatus(factoringRequest.getStatus());
                    }
                    if (factoringRequest.getProgress() != null) {
                        existingFactoringRequest.setProgress(factoringRequest.getProgress());
                    }
                    if (factoringRequest.getMsg() != null) {
                        existingFactoringRequest.setMsg(factoringRequest.getMsg());
                    }

                    return existingFactoringRequest;
                }
            )
            .map(factoringRequestRepository::save)
            .map(
                savedFactoringRequest -> {
                    factoringRequestSearchRepository.save(savedFactoringRequest);

                    return savedFactoringRequest;
                }
            );
    }

    /**
     * Get all the factoringRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<FactoringRequest> findAll(Pageable pageable) {
        log.debug("Request to get all FactoringRequests");
        return factoringRequestRepository.findAll(pageable);
    }

    /**
     * Get one factoringRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<FactoringRequest> findOne(String id) {
        log.debug("Request to get FactoringRequest : {}", id);
        return factoringRequestRepository.findById(id);
    }

    public List<FactoringRequest> findByAdherentId(Long adherentId) {
        log.debug("Request to get FactoringRequest for adherent with id: {}", adherentId);
        return factoringRequestRepository.findByAdherentId(adherentId, Sort.by(Sort.Direction.DESC, "seqId"));
    }

    /**
     * Delete the factoringRequest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete FactoringRequest : {}", id);
        factoringRequestRepository.deleteById(id);
        factoringRequestSearchRepository.deleteById(id);
    }

    /**
     * Search for the factoringRequest corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<FactoringRequest> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FactoringRequests for query {}", query);
        return factoringRequestSearchRepository.search(queryStringQuery(query), pageable);
    }

//    public FactoringRequest updateFactoringRequestStatus(FlowableStatusDTO status, FactoringRequest factoringRequest) {
//        // Update Status
//        if (factoringRequest.getStatus() == Status.IN_PROGRESS && status.getPercentage() == 100) {
//            factoringRequest.setStatus(Status.PENDING);
//            factoringRequest = factoringRequestRepository.save(factoringRequest);
//        }
//        return factoringRequest;
//    }
}

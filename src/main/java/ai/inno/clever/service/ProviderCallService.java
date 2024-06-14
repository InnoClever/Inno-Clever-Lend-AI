package ai.inno.clever.service;

import ai.inno.clever.domain.CollectionContainer;
import ai.inno.clever.domain.ProviderCall;
import ai.inno.clever.repository.ProviderCallRepository;
import ai.inno.clever.repository.search.ProviderCallSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;


/**
 * Service Implementation for managing {@link CollectionContainer}.
 */
@Service
public class ProviderCallService {

    private final Logger log = LoggerFactory.getLogger(ProviderCallService.class);

    private final ProviderCallRepository providerCallRepository;

    private final ProviderCallSearchRepository providerCallSearchRepository;

    public static final String UNASIGNED_CALLS = "UNASIGNED_CALLS";

    @Autowired
    private MongoOperations mongoOps;

    public ProviderCallService(
        ProviderCallRepository providerCallRepository,
        ProviderCallSearchRepository providerCallsSearchRepository
    ) {
        this.providerCallRepository = providerCallRepository;
        this.providerCallSearchRepository = providerCallsSearchRepository;
    }

    /**
     * Save a collectionContainer.
     *
     * @param providerCall the entity to save.
     * @return the persisted entity.
     */
    public ProviderCall save(ProviderCall providerCall) {
        log.debug("Request to save ProviderCall : {}", providerCall);
        ProviderCall result = providerCallRepository.save(providerCall);
        providerCallSearchRepository.save(result);
        return result;
    }

    /**
     * Partially update a collectionContainer.
     *
     * @param providerCall the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProviderCall> partialUpdate(ProviderCall providerCall) {
        log.debug("Request to partially update ProviderCall : {}", providerCall);

        return providerCallRepository
            .findById(providerCall.getId())
            .map(providerCallRepository::save)
            .map(
                savedCollectionContainer -> {
                    providerCallSearchRepository.save(savedCollectionContainer);
                    return savedCollectionContainer;
                }
            );
    }

    /**
     * Get all the collectionContainers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ProviderCall> findAll(Pageable pageable) {
        log.debug("Request to get all ProviderCall");
        return providerCallRepository.findAll(pageable);
    }

    /**
     * Get one collectionContainer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ProviderCall> findOne(String id) {
        log.debug("Request to get ProviderCall : {}", id);
        return providerCallRepository.findById(id);
    }

    public List<ProviderCall> findByFactoringRequestId(String factoringRequestId) {
        log.debug("Request to get ProviderCall : {}", factoringRequestId);
        return providerCallRepository.findByFactoringRequestId(factoringRequestId);
    }


    /**
     * Delete the collectionContainer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ProviderCall : {}", id);
        providerCallRepository.deleteById(id);
        providerCallSearchRepository.deleteById(id);
    }

    /**
     * Search for the collectionContainer corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ProviderCall> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProviderCall for query {}", query);
        return providerCallSearchRepository.search(queryStringQuery(query), pageable);
    }
}

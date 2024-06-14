package ai.inno.clever.service;

import ai.inno.clever.domain.CollectionContainer;
import ai.inno.clever.domain.FactoringRequest;
import ai.inno.clever.repository.CollectionContainerRepository;
import ai.inno.clever.repository.FactoringRequestRepository;
import ai.inno.clever.repository.search.CollectionContainerSearchRepository;
import ai.inno.clever.repository.search.FactoringRequestSearchRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.elasticsearch.ResourceAlreadyExistsException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsAction;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.PutIndexTemplateRequest;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ElasticsearchIndexService {

    private static final Lock reindexLock = new ReentrantLock();


    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final CollectionContainerRepository collectionContainerRepository;

    private final CollectionContainerSearchRepository collectionContainerSearchRepository;

    private final FactoringRequestRepository factoringRequestRepository;

    private final FactoringRequestSearchRepository factoringRequestSearchRepository;

    @Autowired
    RestHighLevelClient client;


    public ElasticsearchIndexService(CollectionContainerRepository collectionContainerRepositor,
                                     CollectionContainerSearchRepository collectionContainerSearchRepository,
                                     FactoringRequestRepository factoringRequestRepository,
                                     FactoringRequestSearchRepository factoringRequestSearchRepository) {
        this.collectionContainerRepository = collectionContainerRepositor;
        this.collectionContainerSearchRepository = collectionContainerSearchRepository;
        this.factoringRequestRepository = factoringRequestRepository;
        this.factoringRequestSearchRepository = factoringRequestSearchRepository;
    }

    @Async
    public void reindexAll() {
        if (reindexLock.tryLock()) {
            try {
                reindexForClass(CollectionContainer.class, collectionContainerRepository, collectionContainerSearchRepository);
                reindexForClass(FactoringRequest.class, factoringRequestRepository, factoringRequestSearchRepository);
                log.info("Elasticsearch: Successfully performed reindexing");
            } catch (IOException e) {
                log.error("Error during reindexing", e);
            } finally {
                reindexLock.unlock();
            }
        } else {
            log.info("Elasticsearch: Concurrent reindexing attempt");
        }
    }

    @SuppressWarnings("unchecked")

    private <T, ID extends Serializable> void reindexForClass(
        Class<T> entityClass,
        MongoRepository<T, ID> jpaRepository,
        ElasticsearchRepository<T, ID> elasticsearchRepository
    ) throws IOException {
        String indexName = entityClass.getSimpleName().toLowerCase(Locale.ROOT);

        // Delete the existing index
        try {
            DeleteIndexRequest deleteRequest = new DeleteIndexRequest(indexName);
            client.indices().delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (Exception ex) {
            log.warn("Error deleting index during reindexing", ex);
        }

        // Create a new index
        try {
            CreateIndexRequest createRequest = new CreateIndexRequest(indexName);
            client.indices().create(createRequest, RequestOptions.DEFAULT);
        } catch (ResourceAlreadyExistsException e) {
            log.warn("Index already exists during reindexing", e);
        }

        // Configure and create the index template
        PutIndexTemplateRequest putRequest = new PutIndexTemplateRequest(indexName);
        putRequest.patterns(Collections.singletonList(indexName));

        // Set other template settings, mappings, etc.
        putRequest.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 1)
            // Add more settings as needed
        );

        // Put the template
        client.indices().putTemplate(putRequest, RequestOptions.DEFAULT);
        updateTotalFieldsLimit(indexName, 10000);
        // Reindex the data
        if (jpaRepository.count() > 0) {
            List<Method> relationshipGetters = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.getType().equals(Set.class))
                .filter(field -> field.getAnnotation(JsonIgnore.class) == null)
                .map(field -> {
                    try {
                        return new PropertyDescriptor(field.getName(), entityClass).getReadMethod();
                    } catch (IntrospectionException e) {
                        log.error("Error retrieving getter for class {}, field {}. Field will NOT be indexed", indexName, field.getName(), e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            int size = 100;
            for (int i = 0; i <= jpaRepository.count() / size; i++) {
                Pageable page = PageRequest.of(i, size);
                log.info("Indexing page {} of {}, size {}, page to index {}", i, jpaRepository.count() / size, size,page);
                Page<T> results = jpaRepository.findAll(page);
                log.info("Reindex Results: {}", results);
                results.map(result -> {
//                    log.info("result: {}", result);

                    for (Method method : relationshipGetters) {
                        try {
//                            log.info("Before Try");
//                            log.info("Method Results: {}", method);

                            // Assuming that the getter returns a Collection type
                            Collection<?> collection = (Collection<?>) method.invoke(result);

                            if (collection != null) {
                                log.info("Collection Size: {}", collection.size());
                            }

                            log.info("Invoke Results: {}", method);
                        } catch (Exception ex) {
                            log.error(ex.getMessage());
                        }
                    }
                    return result;
                });
                elasticsearchRepository.saveAll(results.getContent());
            }
        }

        log.info("Elasticsearch: Indexed all rows for {}", entityClass.getSimpleName());
    }

    private void updateTotalFieldsLimit(String indexName, int totalFieldsLimit) throws IOException {
        UpdateSettingsAction updateSettingsAction = UpdateSettingsAction.INSTANCE;
        UpdateSettingsRequest updateSettingsRequest = new UpdateSettingsRequest(indexName);

        Settings settings = Settings.builder()
            .put("index.mapping.total_fields.limit", totalFieldsLimit)  // Adjust the limit as needed
            .build();

        updateSettingsRequest.settings(settings);

        try {
            AcknowledgedResponse response = client.indices().putSettings(updateSettingsRequest, RequestOptions.DEFAULT);
            if (response.isAcknowledged()) {
                log.info("Index settings updated successfully");
            } else {
                log.error("Failed to update index settings");
            }
        } catch (IOException e) {
            log.error("Error updating index settings", e);
        }
    }
}

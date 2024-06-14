package ai.inno.clever.repository.search;

import ai.inno.clever.domain.FactoringRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FactoringRequest} entity.
 */
public interface FactoringRequestSearchRepository extends ElasticsearchRepository<FactoringRequest, String> {}

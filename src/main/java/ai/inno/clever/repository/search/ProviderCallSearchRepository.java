package ai.inno.clever.repository.search;

import ai.inno.clever.domain.CollectionContainer;
import ai.inno.clever.domain.ProviderCall;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CollectionContainer} entity.
 */
public interface ProviderCallSearchRepository extends ElasticsearchRepository<ProviderCall, String> {}

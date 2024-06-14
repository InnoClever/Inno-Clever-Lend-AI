package ai.inno.clever.repository.search;

import ai.inno.clever.domain.Company;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Company} entity.
 */
public interface CompanySearchRepository extends ElasticsearchRepository<Company, String> {}

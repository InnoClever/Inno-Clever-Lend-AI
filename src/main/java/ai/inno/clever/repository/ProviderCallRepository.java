package ai.inno.clever.repository;

import ai.inno.clever.domain.ProviderCall;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the CollectionContainer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProviderCallRepository extends MongoRepository<ProviderCall, String> {
    List<ProviderCall> findByFactoringRequestId(String factoringRequestId);
}

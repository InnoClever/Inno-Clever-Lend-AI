package ai.inno.clever.repository;

import ai.inno.clever.domain.FactoringRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the FactoringRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactoringRequestRepository extends MongoRepository<FactoringRequest, String> {
    List<FactoringRequest> findByAdherentId(Long adherentId, Sort sort);
}

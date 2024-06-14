package ai.inno.clever.repository;

import ai.inno.clever.domain.CollectionContainer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the CollectionContainer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectionContainerRepository extends MongoRepository<CollectionContainer, String> {
    List<CollectionContainer> findAllByFactoringRequestId(String factoringRequestId);
    List<CollectionContainer> findAllByFactoringRequestIdAndType(String factoringRequestId, String type);
    List<CollectionContainer> findAllByFactoringRequestIdAndTaskId(String factoringRequestId, String taskId);
    List<CollectionContainer> findAllByType(String type);

    //    List<CollectionContainer> findByValuesFieldInAndValuesValueIn(String fieldName, String value);
//    @Query("{'attributes.age' : ?0}")
//    List<CollectionContainer> findByValuesFieldInAndValuesValueIn(Set<String> fields, Set<String> values);
}

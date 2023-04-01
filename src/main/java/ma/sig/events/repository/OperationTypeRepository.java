package ma.sig.events.repository;

import ma.sig.events.domain.OperationType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OperationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Long>, JpaSpecificationExecutor<OperationType> {}

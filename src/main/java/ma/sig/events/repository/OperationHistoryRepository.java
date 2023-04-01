package ma.sig.events.repository;

import ma.sig.events.domain.OperationHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OperationHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationHistoryRepository extends JpaRepository<OperationHistory, Long>, JpaSpecificationExecutor<OperationHistory> {}

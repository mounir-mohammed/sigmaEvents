package ma.sig.events.repository;

import ma.sig.events.domain.LogHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LogHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogHistoryRepository extends JpaRepository<LogHistory, Long>, JpaSpecificationExecutor<LogHistory> {}

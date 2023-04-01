package ma.sig.events.repository;

import ma.sig.events.domain.CheckAccreditationHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CheckAccreditationHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckAccreditationHistoryRepository
    extends JpaRepository<CheckAccreditationHistory, Long>, JpaSpecificationExecutor<CheckAccreditationHistory> {}

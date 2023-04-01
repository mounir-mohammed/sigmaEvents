package ma.sig.events.repository;

import ma.sig.events.domain.CheckAccreditationReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CheckAccreditationReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckAccreditationReportRepository
    extends JpaRepository<CheckAccreditationReport, Long>, JpaSpecificationExecutor<CheckAccreditationReport> {}

package ma.sig.events.repository;

import ma.sig.events.domain.PrintingCentre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PrintingCentre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrintingCentreRepository extends JpaRepository<PrintingCentre, Long>, JpaSpecificationExecutor<PrintingCentre> {}

package ma.sig.events.repository;

import ma.sig.events.domain.PrintingServer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PrintingServer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrintingServerRepository extends JpaRepository<PrintingServer, Long>, JpaSpecificationExecutor<PrintingServer> {}

package ma.sig.events.repository;

import ma.sig.events.domain.PrintingModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PrintingModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrintingModelRepository extends JpaRepository<PrintingModel, Long>, JpaSpecificationExecutor<PrintingModel> {}

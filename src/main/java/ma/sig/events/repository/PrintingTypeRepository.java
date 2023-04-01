package ma.sig.events.repository;

import ma.sig.events.domain.PrintingType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PrintingType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrintingTypeRepository extends JpaRepository<PrintingType, Long>, JpaSpecificationExecutor<PrintingType> {}

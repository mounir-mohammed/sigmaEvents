package ma.sig.events.repository;

import ma.sig.events.domain.InfoSuppType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InfoSuppType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoSuppTypeRepository extends JpaRepository<InfoSuppType, Long>, JpaSpecificationExecutor<InfoSuppType> {}

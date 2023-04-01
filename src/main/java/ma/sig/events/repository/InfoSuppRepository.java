package ma.sig.events.repository;

import ma.sig.events.domain.InfoSupp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InfoSupp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoSuppRepository extends JpaRepository<InfoSupp, Long>, JpaSpecificationExecutor<InfoSupp> {}

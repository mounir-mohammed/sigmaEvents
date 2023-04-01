package ma.sig.events.repository;

import ma.sig.events.domain.Civility;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Civility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CivilityRepository extends JpaRepository<Civility, Long>, JpaSpecificationExecutor<Civility> {}

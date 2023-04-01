package ma.sig.events.repository;

import ma.sig.events.domain.Cloning;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cloning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CloningRepository extends JpaRepository<Cloning, Long>, JpaSpecificationExecutor<Cloning> {}

package ma.sig.events.repository;

import ma.sig.events.domain.Attachement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Attachement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachementRepository extends JpaRepository<Attachement, Long>, JpaSpecificationExecutor<Attachement> {}

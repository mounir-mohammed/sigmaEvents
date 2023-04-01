package ma.sig.events.repository;

import ma.sig.events.domain.Organiz;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Organiz entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizRepository extends JpaRepository<Organiz, Long>, JpaSpecificationExecutor<Organiz> {}

package ma.sig.events.repository;

import ma.sig.events.domain.Sexe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sexe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SexeRepository extends JpaRepository<Sexe, Long>, JpaSpecificationExecutor<Sexe> {}

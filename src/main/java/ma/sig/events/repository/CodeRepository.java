package ma.sig.events.repository;

import ma.sig.events.domain.Code;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Code entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodeRepository extends JpaRepository<Code, Long>, JpaSpecificationExecutor<Code> {}

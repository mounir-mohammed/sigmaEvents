package ma.sig.events.repository;

import ma.sig.events.domain.CodeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CodeType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodeTypeRepository extends JpaRepository<CodeType, Long>, JpaSpecificationExecutor<CodeType> {}

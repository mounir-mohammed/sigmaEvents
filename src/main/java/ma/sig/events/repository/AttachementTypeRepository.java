package ma.sig.events.repository;

import ma.sig.events.domain.AttachementType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AttachementType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachementTypeRepository extends JpaRepository<AttachementType, Long>, JpaSpecificationExecutor<AttachementType> {}

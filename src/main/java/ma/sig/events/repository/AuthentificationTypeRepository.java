package ma.sig.events.repository;

import ma.sig.events.domain.AuthentificationType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AuthentificationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthentificationTypeRepository
    extends JpaRepository<AuthentificationType, Long>, JpaSpecificationExecutor<AuthentificationType> {}

package ma.sig.events.repository;

import ma.sig.events.domain.EventControl;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventControl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventControlRepository extends JpaRepository<EventControl, Long>, JpaSpecificationExecutor<EventControl> {}

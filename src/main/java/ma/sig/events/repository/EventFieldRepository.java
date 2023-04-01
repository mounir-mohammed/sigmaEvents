package ma.sig.events.repository;

import ma.sig.events.domain.EventField;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventField entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventFieldRepository extends JpaRepository<EventField, Long>, JpaSpecificationExecutor<EventField> {}

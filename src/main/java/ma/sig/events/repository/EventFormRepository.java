package ma.sig.events.repository;

import ma.sig.events.domain.EventForm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventFormRepository extends JpaRepository<EventForm, Long>, JpaSpecificationExecutor<EventForm> {}

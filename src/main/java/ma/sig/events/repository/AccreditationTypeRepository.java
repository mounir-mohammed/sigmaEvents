package ma.sig.events.repository;

import java.util.List;
import ma.sig.events.domain.AccreditationType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AccreditationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccreditationTypeRepository extends JpaRepository<AccreditationType, Long>, JpaSpecificationExecutor<AccreditationType> {
    List<AccreditationType> findByEventEventId(Long eventId);
}

package ma.sig.events.repository;

import java.util.List;
import ma.sig.events.domain.Area;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Area entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AreaRepository extends JpaRepository<Area, Long>, JpaSpecificationExecutor<Area> {
    List<Area> findByEventEventId(Long eventId);
}

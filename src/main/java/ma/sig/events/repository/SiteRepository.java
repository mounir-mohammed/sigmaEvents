package ma.sig.events.repository;

import java.util.List;
import ma.sig.events.domain.Site;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Site entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteRepository extends JpaRepository<Site, Long>, JpaSpecificationExecutor<Site> {
    List<Site> findByEventEventId(Long eventId);
}

package ma.sig.events.repository;

import ma.sig.events.domain.DayPassInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DayPassInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DayPassInfoRepository extends JpaRepository<DayPassInfo, Long>, JpaSpecificationExecutor<DayPassInfo> {}

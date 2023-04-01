package ma.sig.events.repository;

import ma.sig.events.domain.PhotoArchive;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PhotoArchive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotoArchiveRepository extends JpaRepository<PhotoArchive, Long>, JpaSpecificationExecutor<PhotoArchive> {}

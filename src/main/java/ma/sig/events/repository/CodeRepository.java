package ma.sig.events.repository;

import java.util.Optional;
import javax.persistence.LockModeType;
import ma.sig.events.domain.Code;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Code entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodeRepository extends JpaRepository<Code, Long>, JpaSpecificationExecutor<Code> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Code> findTopByEventEventIdAndCodeUsedAndCodeStat(Long eventId, boolean codeUsed, boolean codeStat);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Code> findTopByCodeForEntityAndCodeEntityValueAndEventEventIdAndCodeUsedAndCodeStat(
        String entity,
        String value,
        Long eventId,
        boolean codeUsed,
        boolean codeStat
    );
}

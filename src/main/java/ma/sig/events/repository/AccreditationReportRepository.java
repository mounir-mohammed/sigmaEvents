package ma.sig.events.repository;

import java.util.List;
import ma.sig.events.domain.Accreditation;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccreditationReportRepository extends JpaRepository<Accreditation, Long> {
    @Query("SELECT COUNT(a) FROM Accreditation a WHERE a.event.id = :eventId AND a.accreditationStat = true")
    Long countByEventId(@Param("eventId") Long eventId);

    @Query(
        "SELECT COUNT(a) FROM Accreditation a WHERE a.event.id = :eventId AND a.accreditationStat = true AND a.accreditationPrintStat = true"
    )
    Long countPrintedByEventId(@Param("eventId") Long eventId);

    @Query(
        "SELECT COUNT(a) FROM Accreditation a WHERE a.event.id = :eventId AND a.accreditationStat = true AND (a.accreditationPrintStat IS NULL OR a.accreditationPrintStat = false)"
    )
    Long countInProgressByEventId(@Param("eventId") Long eventId);

    @Query(
        "SELECT CAST(a.accreditationPrintDate AS date), COUNT(a) " +
        "FROM Accreditation a " +
        "WHERE a.event.id = :eventId " +
        "AND a.accreditationPrintDate IS NOT NULL " +
        "AND a.accreditationStat = true " +
        "AND a.accreditationPrintStat = true " +
        "GROUP BY CAST(a.accreditationPrintDate AS date) " +
        "ORDER BY CAST(a.accreditationPrintDate AS date)"
    )
    List<Object[]> countPrintedByDay(@Param("eventId") Long eventId);

    @Query(
        "SELECT a.status.statusName, COUNT(a) FROM Accreditation a WHERE a.event.id = :eventId AND a.accreditationStat = true GROUP BY a.status.statusName"
    )
    List<Object[]> countByStatus(@Param("eventId") Long eventId);

    @Query(
        "SELECT a.category.categoryName, COUNT(a) FROM Accreditation a WHERE a.event.id = :eventId AND a.accreditationStat = true AND a.accreditationPrintStat = true GROUP BY a.category.categoryName"
    )
    List<Object[]> countByCategory(@Param("eventId") Long eventId);

    @Query(
        "SELECT a.fonction.fonctionName, COUNT(a) FROM Accreditation a WHERE a.event.id = :eventId AND a.accreditationStat = true AND a.accreditationPrintStat = true GROUP BY a.fonction.fonctionName"
    )
    List<Object[]> countByFunction(@Param("eventId") Long eventId);

    @Query(
        "SELECT s.siteName, COUNT(a) FROM Accreditation a JOIN a.sites s WHERE a.event.id = :eventId AND a.accreditationStat = true AND a.accreditationPrintStat = true GROUP BY s.siteName"
    )
    List<Object[]> countBySite(@Param("eventId") Long eventId);

    @Query(
        "SELECT a.organiz.organizName, COUNT(a) FROM Accreditation a WHERE a.event.id = :eventId AND a.accreditationStat = true AND a.accreditationPrintStat = true GROUP BY a.organiz.organizName"
    )
    List<Object[]> countByOrganization(@Param("eventId") Long eventId);

    @Query(
        "SELECT a.accreditationType.accreditationTypeValue, COUNT(a) FROM Accreditation a WHERE a.event.id = :eventId AND a.accreditationStat = true AND a.accreditationPrintStat = true GROUP BY a.accreditationType.accreditationTypeValue"
    )
    List<Object[]> countByType(@Param("eventId") Long eventId);

    @Query(
        "SELECT a.nationality.nationalityValue, COUNT(a) FROM Accreditation a WHERE a.event.id = :eventId AND a.accreditationStat = true AND a.accreditationPrintStat = true GROUP BY a.nationality.nationalityValue"
    )
    List<Object[]> countByNationality(@Param("eventId") Long eventId);
}

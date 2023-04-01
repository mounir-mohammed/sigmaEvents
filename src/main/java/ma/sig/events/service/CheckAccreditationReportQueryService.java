package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.CheckAccreditationReport;
import ma.sig.events.repository.CheckAccreditationReportRepository;
import ma.sig.events.service.criteria.CheckAccreditationReportCriteria;
import ma.sig.events.service.dto.CheckAccreditationReportDTO;
import ma.sig.events.service.mapper.CheckAccreditationReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CheckAccreditationReport} entities in the database.
 * The main input is a {@link CheckAccreditationReportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckAccreditationReportDTO} or a {@link Page} of {@link CheckAccreditationReportDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckAccreditationReportQueryService extends QueryService<CheckAccreditationReport> {

    private final Logger log = LoggerFactory.getLogger(CheckAccreditationReportQueryService.class);

    private final CheckAccreditationReportRepository checkAccreditationReportRepository;

    private final CheckAccreditationReportMapper checkAccreditationReportMapper;

    public CheckAccreditationReportQueryService(
        CheckAccreditationReportRepository checkAccreditationReportRepository,
        CheckAccreditationReportMapper checkAccreditationReportMapper
    ) {
        this.checkAccreditationReportRepository = checkAccreditationReportRepository;
        this.checkAccreditationReportMapper = checkAccreditationReportMapper;
    }

    /**
     * Return a {@link List} of {@link CheckAccreditationReportDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckAccreditationReportDTO> findByCriteria(CheckAccreditationReportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckAccreditationReport> specification = createSpecification(criteria);
        return checkAccreditationReportMapper.toDto(checkAccreditationReportRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckAccreditationReportDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckAccreditationReportDTO> findByCriteria(CheckAccreditationReportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckAccreditationReport> specification = createSpecification(criteria);
        return checkAccreditationReportRepository.findAll(specification, page).map(checkAccreditationReportMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckAccreditationReportCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckAccreditationReport> specification = createSpecification(criteria);
        return checkAccreditationReportRepository.count(specification);
    }

    /**
     * Function to convert {@link CheckAccreditationReportCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CheckAccreditationReport> createSpecification(CheckAccreditationReportCriteria criteria) {
        Specification<CheckAccreditationReport> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getCheckAccreditationReportId() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getCheckAccreditationReportId(),
                            CheckAccreditationReport_.checkAccreditationReportId
                        )
                    );
            }
            if (criteria.getCheckAccreditationReportDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCheckAccreditationReportDescription(),
                            CheckAccreditationReport_.checkAccreditationReportDescription
                        )
                    );
            }
            if (criteria.getCheckAccreditationReportParams() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCheckAccreditationReportParams(),
                            CheckAccreditationReport_.checkAccreditationReportParams
                        )
                    );
            }
            if (criteria.getCheckAccreditationReportAttributs() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCheckAccreditationReportAttributs(),
                            CheckAccreditationReport_.checkAccreditationReportAttributs
                        )
                    );
            }
            if (criteria.getCheckAccreditationReportStat() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCheckAccreditationReportStat(),
                            CheckAccreditationReport_.checkAccreditationReportStat
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventId(),
                            root -> root.join(CheckAccreditationReport_.event, JoinType.LEFT).get(Event_.eventId)
                        )
                    );
            }
            if (criteria.getCheckAccreditationHistoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCheckAccreditationHistoryId(),
                            root ->
                                root
                                    .join(CheckAccreditationReport_.checkAccreditationHistory, JoinType.LEFT)
                                    .get(CheckAccreditationHistory_.checkAccreditationHistoryId)
                        )
                    );
            }
        }
        return specification;
    }
}

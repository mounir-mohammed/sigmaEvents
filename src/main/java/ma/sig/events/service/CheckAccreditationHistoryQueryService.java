package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.CheckAccreditationHistory;
import ma.sig.events.repository.CheckAccreditationHistoryRepository;
import ma.sig.events.service.criteria.CheckAccreditationHistoryCriteria;
import ma.sig.events.service.dto.CheckAccreditationHistoryDTO;
import ma.sig.events.service.mapper.CheckAccreditationHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CheckAccreditationHistory} entities in the database.
 * The main input is a {@link CheckAccreditationHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckAccreditationHistoryDTO} or a {@link Page} of {@link CheckAccreditationHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckAccreditationHistoryQueryService extends QueryService<CheckAccreditationHistory> {

    private final Logger log = LoggerFactory.getLogger(CheckAccreditationHistoryQueryService.class);

    private final CheckAccreditationHistoryRepository checkAccreditationHistoryRepository;

    private final CheckAccreditationHistoryMapper checkAccreditationHistoryMapper;

    public CheckAccreditationHistoryQueryService(
        CheckAccreditationHistoryRepository checkAccreditationHistoryRepository,
        CheckAccreditationHistoryMapper checkAccreditationHistoryMapper
    ) {
        this.checkAccreditationHistoryRepository = checkAccreditationHistoryRepository;
        this.checkAccreditationHistoryMapper = checkAccreditationHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link CheckAccreditationHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckAccreditationHistoryDTO> findByCriteria(CheckAccreditationHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckAccreditationHistory> specification = createSpecification(criteria);
        return checkAccreditationHistoryMapper.toDto(checkAccreditationHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckAccreditationHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckAccreditationHistoryDTO> findByCriteria(CheckAccreditationHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckAccreditationHistory> specification = createSpecification(criteria);
        return checkAccreditationHistoryRepository.findAll(specification, page).map(checkAccreditationHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckAccreditationHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckAccreditationHistory> specification = createSpecification(criteria);
        return checkAccreditationHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link CheckAccreditationHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CheckAccreditationHistory> createSpecification(CheckAccreditationHistoryCriteria criteria) {
        Specification<CheckAccreditationHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getCheckAccreditationHistoryId() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getCheckAccreditationHistoryId(),
                            CheckAccreditationHistory_.checkAccreditationHistoryId
                        )
                    );
            }
            if (criteria.getCheckAccreditationHistoryReadedCode() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCheckAccreditationHistoryReadedCode(),
                            CheckAccreditationHistory_.checkAccreditationHistoryReadedCode
                        )
                    );
            }
            if (criteria.getCheckAccreditationHistoryUserId() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getCheckAccreditationHistoryUserId(),
                            CheckAccreditationHistory_.checkAccreditationHistoryUserId
                        )
                    );
            }
            if (criteria.getCheckAccreditationHistoryResult() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCheckAccreditationHistoryResult(),
                            CheckAccreditationHistory_.checkAccreditationHistoryResult
                        )
                    );
            }
            if (criteria.getCheckAccreditationHistoryError() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCheckAccreditationHistoryError(),
                            CheckAccreditationHistory_.checkAccreditationHistoryError
                        )
                    );
            }
            if (criteria.getCheckAccreditationHistoryDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getCheckAccreditationHistoryDate(),
                            CheckAccreditationHistory_.checkAccreditationHistoryDate
                        )
                    );
            }
            if (criteria.getCheckAccreditationHistoryLocalisation() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCheckAccreditationHistoryLocalisation(),
                            CheckAccreditationHistory_.checkAccreditationHistoryLocalisation
                        )
                    );
            }
            if (criteria.getCheckAccreditationHistoryIpAdresse() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCheckAccreditationHistoryIpAdresse(),
                            CheckAccreditationHistory_.checkAccreditationHistoryIpAdresse
                        )
                    );
            }
            if (criteria.getCheckAccreditationParams() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCheckAccreditationParams(),
                            CheckAccreditationHistory_.checkAccreditationParams
                        )
                    );
            }
            if (criteria.getCheckAccreditationAttributs() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCheckAccreditationAttributs(),
                            CheckAccreditationHistory_.checkAccreditationAttributs
                        )
                    );
            }
            if (criteria.getCheckAccreditationHistoryStat() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCheckAccreditationHistoryStat(),
                            CheckAccreditationHistory_.checkAccreditationHistoryStat
                        )
                    );
            }
            if (criteria.getCheckAccreditationReportId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCheckAccreditationReportId(),
                            root ->
                                root
                                    .join(CheckAccreditationHistory_.checkAccreditationReports, JoinType.LEFT)
                                    .get(CheckAccreditationReport_.checkAccreditationReportId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventId(),
                            root -> root.join(CheckAccreditationHistory_.event, JoinType.LEFT).get(Event_.eventId)
                        )
                    );
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(CheckAccreditationHistory_.accreditation, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
        }
        return specification;
    }
}

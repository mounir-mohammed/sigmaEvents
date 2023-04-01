package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.LogHistory;
import ma.sig.events.repository.LogHistoryRepository;
import ma.sig.events.service.criteria.LogHistoryCriteria;
import ma.sig.events.service.dto.LogHistoryDTO;
import ma.sig.events.service.mapper.LogHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link LogHistory} entities in the database.
 * The main input is a {@link LogHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LogHistoryDTO} or a {@link Page} of {@link LogHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LogHistoryQueryService extends QueryService<LogHistory> {

    private final Logger log = LoggerFactory.getLogger(LogHistoryQueryService.class);

    private final LogHistoryRepository logHistoryRepository;

    private final LogHistoryMapper logHistoryMapper;

    public LogHistoryQueryService(LogHistoryRepository logHistoryRepository, LogHistoryMapper logHistoryMapper) {
        this.logHistoryRepository = logHistoryRepository;
        this.logHistoryMapper = logHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link LogHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LogHistoryDTO> findByCriteria(LogHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LogHistory> specification = createSpecification(criteria);
        return logHistoryMapper.toDto(logHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LogHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LogHistoryDTO> findByCriteria(LogHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LogHistory> specification = createSpecification(criteria);
        return logHistoryRepository.findAll(specification, page).map(logHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LogHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LogHistory> specification = createSpecification(criteria);
        return logHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link LogHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LogHistory> createSpecification(LogHistoryCriteria criteria) {
        Specification<LogHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getLogHistory() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLogHistory(), LogHistory_.logHistory));
            }
            if (criteria.getLogHistoryDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLogHistoryDescription(), LogHistory_.logHistoryDescription));
            }
            if (criteria.getLogHistoryParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogHistoryParams(), LogHistory_.logHistoryParams));
            }
            if (criteria.getLogHistoryAttributs() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLogHistoryAttributs(), LogHistory_.logHistoryAttributs));
            }
            if (criteria.getLogHistoryStat() != null) {
                specification = specification.and(buildSpecification(criteria.getLogHistoryStat(), LogHistory_.logHistoryStat));
            }
        }
        return specification;
    }
}

package ma.sig.events.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.OperationHistory;
import ma.sig.events.repository.OperationHistoryRepository;
import ma.sig.events.security.AuthoritiesConstants;
import ma.sig.events.security.SecurityUtils;
import ma.sig.events.service.criteria.OperationHistoryCriteria;
import ma.sig.events.service.dto.OperationHistoryDTO;
import ma.sig.events.service.mapper.OperationHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.LongFilter;

/**
 * Service for executing complex queries for {@link OperationHistory} entities in the database.
 * The main input is a {@link OperationHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OperationHistoryDTO} or a {@link Page} of {@link OperationHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OperationHistoryQueryService extends QueryService<OperationHistory> {

    private final Logger log = LoggerFactory.getLogger(OperationHistoryQueryService.class);

    private final OperationHistoryRepository operationHistoryRepository;

    private final OperationHistoryMapper operationHistoryMapper;

    private final UserService userService;

    public OperationHistoryQueryService(
        OperationHistoryRepository operationHistoryRepository,
        OperationHistoryMapper operationHistoryMapper,
        UserService userService
    ) {
        this.operationHistoryRepository = operationHistoryRepository;
        this.operationHistoryMapper = operationHistoryMapper;
        this.userService = userService;
    }

    /**
     * Return a {@link List} of {@link OperationHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OperationHistoryDTO> findByCriteria(OperationHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OperationHistory> specification = createSpecification(criteria);
        return operationHistoryMapper.toDto(operationHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OperationHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OperationHistoryDTO> findByCriteria(OperationHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OperationHistory> specification = createSpecification(criteria);
        return operationHistoryRepository.findAll(specification, page).map(operationHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OperationHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OperationHistory> specification = createSpecification(criteria);
        return operationHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link OperationHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OperationHistory> createSpecification(OperationHistoryCriteria criteria) {
        Specification<OperationHistory> specification = Specification.where(null);
        //ADD FILTER START
        Optional<User> currentUser = null;
        try {
            if (!SecurityUtils.hasCurrentUserAnyOfAuthorities(AuthoritiesConstants.ADMIN)) {
                currentUser = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get().toString());
                if (currentUser.isPresent() && currentUser.get().getPrintingCentre().getEvent().getEventId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                new LongFilter().setEquals(currentUser.get().getPrintingCentre().getEvent().getEventId()),
                                root -> root.join(OperationHistory_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(
                        new LongFilter().setEquals(0L),
                        root -> root.join(OperationHistory_.event, JoinType.LEFT).get(Event_.eventId)
                    )
                );
        }
        //ADD FILTER END
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getOperationHistoryId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getOperationHistoryId(), OperationHistory_.operationHistoryId));
            }
            if (criteria.getOperationHistoryDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getOperationHistoryDescription(), OperationHistory_.operationHistoryDescription)
                    );
            }
            if (criteria.getOperationHistoryDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getOperationHistoryDate(), OperationHistory_.operationHistoryDate));
            }
            if (criteria.getOperationHistoryUserID() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getOperationHistoryUserID(), OperationHistory_.operationHistoryUserID)
                    );
            }
            if (criteria.getOperationHistoryOldValue() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getOperationHistoryOldValue(), OperationHistory_.operationHistoryOldValue)
                    );
            }
            if (criteria.getOperationHistoryNewValue() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getOperationHistoryNewValue(), OperationHistory_.operationHistoryNewValue)
                    );
            }
            if (criteria.getOperationHistoryOldId() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getOperationHistoryOldId(), OperationHistory_.operationHistoryOldId)
                    );
            }
            if (criteria.getOperationHistoryNewId() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getOperationHistoryNewId(), OperationHistory_.operationHistoryNewId)
                    );
            }
            if (criteria.getOperationHistoryImportedFile() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getOperationHistoryImportedFile(), OperationHistory_.operationHistoryImportedFile)
                    );
            }
            if (criteria.getOperationHistoryImportedFilePath() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getOperationHistoryImportedFilePath(),
                            OperationHistory_.operationHistoryImportedFilePath
                        )
                    );
            }
            if (criteria.getOperationHistoryParams() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getOperationHistoryParams(), OperationHistory_.operationHistoryParams)
                    );
            }
            if (criteria.getOperationHistoryAttributs() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getOperationHistoryAttributs(), OperationHistory_.operationHistoryAttributs)
                    );
            }
            if (criteria.getOperationHistoryStat() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getOperationHistoryStat(), OperationHistory_.operationHistoryStat));
            }
            if (criteria.getTypeoperationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTypeoperationId(),
                            root -> root.join(OperationHistory_.typeoperation, JoinType.LEFT).get(OperationType_.operationTypeId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventId(),
                            root -> root.join(OperationHistory_.event, JoinType.LEFT).get(Event_.eventId)
                        )
                    );
            }
        }
        return specification;
    }
}

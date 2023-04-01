package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.EventControl;
import ma.sig.events.repository.EventControlRepository;
import ma.sig.events.service.criteria.EventControlCriteria;
import ma.sig.events.service.dto.EventControlDTO;
import ma.sig.events.service.mapper.EventControlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EventControl} entities in the database.
 * The main input is a {@link EventControlCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EventControlDTO} or a {@link Page} of {@link EventControlDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventControlQueryService extends QueryService<EventControl> {

    private final Logger log = LoggerFactory.getLogger(EventControlQueryService.class);

    private final EventControlRepository eventControlRepository;

    private final EventControlMapper eventControlMapper;

    public EventControlQueryService(EventControlRepository eventControlRepository, EventControlMapper eventControlMapper) {
        this.eventControlRepository = eventControlRepository;
        this.eventControlMapper = eventControlMapper;
    }

    /**
     * Return a {@link List} of {@link EventControlDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EventControlDTO> findByCriteria(EventControlCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EventControl> specification = createSpecification(criteria);
        return eventControlMapper.toDto(eventControlRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EventControlDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EventControlDTO> findByCriteria(EventControlCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EventControl> specification = createSpecification(criteria);
        return eventControlRepository.findAll(specification, page).map(eventControlMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventControlCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EventControl> specification = createSpecification(criteria);
        return eventControlRepository.count(specification);
    }

    /**
     * Function to convert {@link EventControlCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EventControl> createSpecification(EventControlCriteria criteria) {
        Specification<EventControl> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getControlId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getControlId(), EventControl_.controlId));
            }
            if (criteria.getControlName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getControlName(), EventControl_.controlName));
            }
            if (criteria.getControlDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getControlDescription(), EventControl_.controlDescription));
            }
            if (criteria.getControlType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getControlType(), EventControl_.controlType));
            }
            if (criteria.getControlValueString() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getControlValueString(), EventControl_.controlValueString));
            }
            if (criteria.getControlValueLong() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getControlValueLong(), EventControl_.controlValueLong));
            }
            if (criteria.getControlValueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getControlValueDate(), EventControl_.controlValueDate));
            }
            if (criteria.getControlParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getControlParams(), EventControl_.controlParams));
            }
            if (criteria.getControlAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getControlAttributs(), EventControl_.controlAttributs));
            }
            if (criteria.getControlValueStat() != null) {
                specification = specification.and(buildSpecification(criteria.getControlValueStat(), EventControl_.controlValueStat));
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(EventControl_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
            if (criteria.getEventFieldId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventFieldId(),
                            root -> root.join(EventControl_.eventField, JoinType.LEFT).get(EventField_.fieldId)
                        )
                    );
            }
        }
        return specification;
    }
}

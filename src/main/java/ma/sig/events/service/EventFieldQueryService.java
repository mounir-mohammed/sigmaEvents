package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.EventField;
import ma.sig.events.repository.EventFieldRepository;
import ma.sig.events.service.criteria.EventFieldCriteria;
import ma.sig.events.service.dto.EventFieldDTO;
import ma.sig.events.service.mapper.EventFieldMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EventField} entities in the database.
 * The main input is a {@link EventFieldCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EventFieldDTO} or a {@link Page} of {@link EventFieldDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventFieldQueryService extends QueryService<EventField> {

    private final Logger log = LoggerFactory.getLogger(EventFieldQueryService.class);

    private final EventFieldRepository eventFieldRepository;

    private final EventFieldMapper eventFieldMapper;

    public EventFieldQueryService(EventFieldRepository eventFieldRepository, EventFieldMapper eventFieldMapper) {
        this.eventFieldRepository = eventFieldRepository;
        this.eventFieldMapper = eventFieldMapper;
    }

    /**
     * Return a {@link List} of {@link EventFieldDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EventFieldDTO> findByCriteria(EventFieldCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EventField> specification = createSpecification(criteria);
        return eventFieldMapper.toDto(eventFieldRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EventFieldDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EventFieldDTO> findByCriteria(EventFieldCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EventField> specification = createSpecification(criteria);
        return eventFieldRepository.findAll(specification, page).map(eventFieldMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventFieldCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EventField> specification = createSpecification(criteria);
        return eventFieldRepository.count(specification);
    }

    /**
     * Function to convert {@link EventFieldCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EventField> createSpecification(EventFieldCriteria criteria) {
        Specification<EventField> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getFieldId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldId(), EventField_.fieldId));
            }
            if (criteria.getFieldName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldName(), EventField_.fieldName));
            }
            if (criteria.getFieldCategorie() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldCategorie(), EventField_.fieldCategorie));
            }
            if (criteria.getFieldDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldDescription(), EventField_.fieldDescription));
            }
            if (criteria.getFieldType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldType(), EventField_.fieldType));
            }
            if (criteria.getFieldParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldParams(), EventField_.fieldParams));
            }
            if (criteria.getFieldAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldAttributs(), EventField_.fieldAttributs));
            }
            if (criteria.getFieldStat() != null) {
                specification = specification.and(buildSpecification(criteria.getFieldStat(), EventField_.fieldStat));
            }
            if (criteria.getEventControlId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventControlId(),
                            root -> root.join(EventField_.eventControls, JoinType.LEFT).get(EventControl_.controlId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(EventField_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
            if (criteria.getEventFormId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventFormId(),
                            root -> root.join(EventField_.eventForm, JoinType.LEFT).get(EventForm_.formId)
                        )
                    );
            }
        }
        return specification;
    }
}

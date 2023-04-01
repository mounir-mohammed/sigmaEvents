package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.EventForm;
import ma.sig.events.repository.EventFormRepository;
import ma.sig.events.service.criteria.EventFormCriteria;
import ma.sig.events.service.dto.EventFormDTO;
import ma.sig.events.service.mapper.EventFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EventForm} entities in the database.
 * The main input is a {@link EventFormCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EventFormDTO} or a {@link Page} of {@link EventFormDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventFormQueryService extends QueryService<EventForm> {

    private final Logger log = LoggerFactory.getLogger(EventFormQueryService.class);

    private final EventFormRepository eventFormRepository;

    private final EventFormMapper eventFormMapper;

    public EventFormQueryService(EventFormRepository eventFormRepository, EventFormMapper eventFormMapper) {
        this.eventFormRepository = eventFormRepository;
        this.eventFormMapper = eventFormMapper;
    }

    /**
     * Return a {@link List} of {@link EventFormDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EventFormDTO> findByCriteria(EventFormCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EventForm> specification = createSpecification(criteria);
        return eventFormMapper.toDto(eventFormRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EventFormDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EventFormDTO> findByCriteria(EventFormCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EventForm> specification = createSpecification(criteria);
        return eventFormRepository.findAll(specification, page).map(eventFormMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventFormCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EventForm> specification = createSpecification(criteria);
        return eventFormRepository.count(specification);
    }

    /**
     * Function to convert {@link EventFormCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EventForm> createSpecification(EventFormCriteria criteria) {
        Specification<EventForm> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getFormId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFormId(), EventForm_.formId));
            }
            if (criteria.getFormName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormName(), EventForm_.formName));
            }
            if (criteria.getFormDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormDescription(), EventForm_.formDescription));
            }
            if (criteria.getFormParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormParams(), EventForm_.formParams));
            }
            if (criteria.getFormAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormAttributs(), EventForm_.formAttributs));
            }
            if (criteria.getFormStat() != null) {
                specification = specification.and(buildSpecification(criteria.getFormStat(), EventForm_.formStat));
            }
            if (criteria.getEventFieldId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventFieldId(),
                            root -> root.join(EventForm_.eventFields, JoinType.LEFT).get(EventField_.fieldId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(EventForm_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
        }
        return specification;
    }
}

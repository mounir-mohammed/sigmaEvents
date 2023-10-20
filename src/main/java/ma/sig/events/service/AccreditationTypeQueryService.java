package ma.sig.events.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.AccreditationType;
import ma.sig.events.repository.AccreditationTypeRepository;
import ma.sig.events.security.AuthoritiesConstants;
import ma.sig.events.security.SecurityUtils;
import ma.sig.events.service.criteria.AccreditationTypeCriteria;
import ma.sig.events.service.dto.AccreditationTypeDTO;
import ma.sig.events.service.mapper.AccreditationTypeMapper;
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
 * Service for executing complex queries for {@link AccreditationType} entities in the database.
 * The main input is a {@link AccreditationTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccreditationTypeDTO} or a {@link Page} of {@link AccreditationTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccreditationTypeQueryService extends QueryService<AccreditationType> {

    private final Logger log = LoggerFactory.getLogger(AccreditationTypeQueryService.class);

    private final AccreditationTypeRepository accreditationTypeRepository;

    private final AccreditationTypeMapper accreditationTypeMapper;

    private final UserService userService;

    public AccreditationTypeQueryService(
        AccreditationTypeRepository accreditationTypeRepository,
        AccreditationTypeMapper accreditationTypeMapper,
        UserService userService
    ) {
        this.accreditationTypeRepository = accreditationTypeRepository;
        this.accreditationTypeMapper = accreditationTypeMapper;
        this.userService = userService;
    }

    /**
     * Return a {@link List} of {@link AccreditationTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccreditationTypeDTO> findByCriteria(AccreditationTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AccreditationType> specification = createSpecification(criteria);
        return accreditationTypeMapper.toDto(accreditationTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccreditationTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccreditationTypeDTO> findByCriteria(AccreditationTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AccreditationType> specification = createSpecification(criteria);
        return accreditationTypeRepository.findAll(specification, page).map(accreditationTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccreditationTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AccreditationType> specification = createSpecification(criteria);
        return accreditationTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link AccreditationTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AccreditationType> createSpecification(AccreditationTypeCriteria criteria) {
        Specification<AccreditationType> specification = Specification.where(null);
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
                                root -> root.join(AccreditationType_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(
                        new LongFilter().setEquals(0L),
                        root -> root.join(AccreditationType_.event, JoinType.LEFT).get(Event_.eventId)
                    )
                );
        }
        //ADD FILTER END

        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getAccreditationTypeId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAccreditationTypeId(), AccreditationType_.accreditationTypeId));
            }
            if (criteria.getAccreditationTypeValue() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAccreditationTypeValue(), AccreditationType_.accreditationTypeValue)
                    );
            }
            if (criteria.getAccreditationTypeAbreviation() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getAccreditationTypeAbreviation(),
                            AccreditationType_.accreditationTypeAbreviation
                        )
                    );
            }
            if (criteria.getAccreditationTypeDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getAccreditationTypeDescription(),
                            AccreditationType_.accreditationTypeDescription
                        )
                    );
            }
            if (criteria.getAccreditationTypeParams() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAccreditationTypeParams(), AccreditationType_.accreditationTypeParams)
                    );
            }
            if (criteria.getAccreditationTypeAttributs() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAccreditationTypeAttributs(), AccreditationType_.accreditationTypeAttributs)
                    );
            }
            if (criteria.getAccreditationTypeStat() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getAccreditationTypeStat(), AccreditationType_.accreditationTypeStat));
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(AccreditationType_.accreditations, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
            if (criteria.getPrintingModelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrintingModelId(),
                            root -> root.join(AccreditationType_.printingModel, JoinType.LEFT).get(PrintingModel_.printingModelId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventId(),
                            root -> root.join(AccreditationType_.event, JoinType.LEFT).get(Event_.eventId)
                        )
                    );
            }
        }
        return specification;
    }
}

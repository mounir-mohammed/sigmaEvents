package ma.sig.events.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Organiz;
import ma.sig.events.repository.OrganizRepository;
import ma.sig.events.security.AuthoritiesConstants;
import ma.sig.events.security.SecurityUtils;
import ma.sig.events.service.criteria.OrganizCriteria;
import ma.sig.events.service.dto.OrganizDTO;
import ma.sig.events.service.mapper.OrganizMapper;
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
 * Service for executing complex queries for {@link Organiz} entities in the database.
 * The main input is a {@link OrganizCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrganizDTO} or a {@link Page} of {@link OrganizDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrganizQueryService extends QueryService<Organiz> {

    private final Logger log = LoggerFactory.getLogger(OrganizQueryService.class);

    private final OrganizRepository organizRepository;

    private final OrganizMapper organizMapper;

    private final UserService userService;

    public OrganizQueryService(OrganizRepository organizRepository, OrganizMapper organizMapper, UserService userService) {
        this.organizRepository = organizRepository;
        this.organizMapper = organizMapper;
        this.userService = userService;
    }

    /**
     * Return a {@link List} of {@link OrganizDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrganizDTO> findByCriteria(OrganizCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Organiz> specification = createSpecification(criteria);
        return organizMapper.toDto(organizRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrganizDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganizDTO> findByCriteria(OrganizCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Organiz> specification = createSpecification(criteria);
        return organizRepository.findAll(specification, page).map(organizMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrganizCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Organiz> specification = createSpecification(criteria);
        return organizRepository.count(specification);
    }

    /**
     * Function to convert {@link OrganizCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Organiz> createSpecification(OrganizCriteria criteria) {
        Specification<Organiz> specification = Specification.where(null);
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
                                root -> root.join(Organiz_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(new LongFilter().setEquals(0L), root -> root.join(Organiz_.event, JoinType.LEFT).get(Event_.eventId))
                );
        }
        //ADD FILTER END
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getOrganizId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrganizId(), Organiz_.organizId));
            }
            if (criteria.getOrganizName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganizName(), Organiz_.organizName));
            }
            if (criteria.getOrganizDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganizDescription(), Organiz_.organizDescription));
            }
            if (criteria.getOrganizTel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganizTel(), Organiz_.organizTel));
            }
            if (criteria.getOrganizFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganizFax(), Organiz_.organizFax));
            }
            if (criteria.getOrganizEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganizEmail(), Organiz_.organizEmail));
            }
            if (criteria.getOrganizAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganizAdresse(), Organiz_.organizAdresse));
            }
            if (criteria.getOrganizParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganizParams(), Organiz_.organizParams));
            }
            if (criteria.getOrganizAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganizAttributs(), Organiz_.organizAttributs));
            }
            if (criteria.getOrganizStat() != null) {
                specification = specification.and(buildSpecification(criteria.getOrganizStat(), Organiz_.organizStat));
            }
            if (criteria.getPrintingCentreId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrintingCentreId(),
                            root -> root.join(Organiz_.printingCentres, JoinType.LEFT).get(PrintingCentre_.printingCentreId)
                        )
                    );
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(Organiz_.accreditations, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
            if (criteria.getCountryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCountryId(),
                            root -> root.join(Organiz_.country, JoinType.LEFT).get(Country_.countryId)
                        )
                    );
            }
            if (criteria.getCityId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCityId(), root -> root.join(Organiz_.city, JoinType.LEFT).get(City_.cityId))
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(Organiz_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
        }
        return specification;
    }

    public Optional<OrganizDTO> findByIdCheckEvent(Long id) {
        log.debug("find OrganizDTO by id and check event : {}", id);
        final Specification<Organiz> specification = createEventSpecification(id);
        return organizRepository.findOne(specification).map(organizMapper::toDto);
    }

    private Specification<Organiz> createEventSpecification(Long id) {
        Specification<Organiz> specification = Specification.where(null);
        specification = specification.and(buildSpecification(new LongFilter().setEquals(id), Organiz_.organizId));
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
                                root -> root.join(Organiz_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(new LongFilter().setEquals(0L), root -> root.join(Organiz_.event, JoinType.LEFT).get(Event_.eventId))
                );
        }

        return specification;
    }
}

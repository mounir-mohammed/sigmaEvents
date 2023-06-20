package ma.sig.events.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Attachement;
import ma.sig.events.repository.AttachementRepository;
import ma.sig.events.security.AuthoritiesConstants;
import ma.sig.events.security.SecurityUtils;
import ma.sig.events.service.criteria.AttachementCriteria;
import ma.sig.events.service.dto.AttachementDTO;
import ma.sig.events.service.mapper.AttachementMapper;
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
 * Service for executing complex queries for {@link Attachement} entities in the database.
 * The main input is a {@link AttachementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AttachementDTO} or a {@link Page} of {@link AttachementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttachementQueryService extends QueryService<Attachement> {

    private final Logger log = LoggerFactory.getLogger(AttachementQueryService.class);

    private final AttachementRepository attachementRepository;

    private final AttachementMapper attachementMapper;

    private final UserService userService;

    public AttachementQueryService(
        AttachementRepository attachementRepository,
        AttachementMapper attachementMapper,
        UserService userService
    ) {
        this.attachementRepository = attachementRepository;
        this.attachementMapper = attachementMapper;
        this.userService = userService;
    }

    /**
     * Return a {@link List} of {@link AttachementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AttachementDTO> findByCriteria(AttachementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Attachement> specification = createSpecification(criteria);
        return attachementMapper.toDto(attachementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AttachementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AttachementDTO> findByCriteria(AttachementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Attachement> specification = createSpecification(criteria);
        return attachementRepository.findAll(specification, page).map(attachementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttachementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Attachement> specification = createSpecification(criteria);
        return attachementRepository.count(specification);
    }

    /**
     * Function to convert {@link AttachementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Attachement> createSpecification(AttachementCriteria criteria) {
        Specification<Attachement> specification = Specification.where(null);

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
                                root -> root.join(Attachement_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(
                        new LongFilter().setEquals(0L),
                        root -> root.join(Attachement_.event, JoinType.LEFT).get(Event_.eventId)
                    )
                );
        }
        //ADD FILTER END

        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getAttachementId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttachementId(), Attachement_.attachementId));
            }
            if (criteria.getAttachementName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttachementName(), Attachement_.attachementName));
            }
            if (criteria.getAttachementPath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttachementPath(), Attachement_.attachementPath));
            }
            if (criteria.getAttachementDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAttachementDescription(), Attachement_.attachementDescription));
            }
            if (criteria.getAttachementParams() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAttachementParams(), Attachement_.attachementParams));
            }
            if (criteria.getAttachementAttributs() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAttachementAttributs(), Attachement_.attachementAttributs));
            }
            if (criteria.getAttachementStat() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachementStat(), Attachement_.attachementStat));
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(Attachement_.accreditations, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
            if (criteria.getAttachementTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttachementTypeId(),
                            root -> root.join(Attachement_.attachementType, JoinType.LEFT).get(AttachementType_.attachementTypeId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(Attachement_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
        }
        return specification;
    }

    public Optional<AttachementDTO> findByIdCheckEvent(Long id) {
        log.debug("find AttachementDTO by id and check event : {}", id);
        final Specification<Attachement> specification = createEventSpecification(id);
        return attachementRepository.findOne(specification).map(attachementMapper::toDto);
    }

    private Specification<Attachement> createEventSpecification(Long id) {
        Specification<Attachement> specification = Specification.where(null);
        specification = specification.and(buildSpecification(new LongFilter().setEquals(id), Attachement_.attachementId));
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
                                root -> root.join(Attachement_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(
                        new LongFilter().setEquals(0L),
                        root -> root.join(Attachement_.event, JoinType.LEFT).get(Event_.eventId)
                    )
                );
        }

        return specification;
    }
}

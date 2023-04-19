package ma.sig.events.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.InfoSupp;
import ma.sig.events.repository.InfoSuppRepository;
import ma.sig.events.security.AuthoritiesConstants;
import ma.sig.events.security.SecurityUtils;
import ma.sig.events.service.criteria.InfoSuppCriteria;
import ma.sig.events.service.dto.InfoSuppDTO;
import ma.sig.events.service.mapper.InfoSuppMapper;
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
 * Service for executing complex queries for {@link InfoSupp} entities in the database.
 * The main input is a {@link InfoSuppCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InfoSuppDTO} or a {@link Page} of {@link InfoSuppDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InfoSuppQueryService extends QueryService<InfoSupp> {

    private final Logger log = LoggerFactory.getLogger(InfoSuppQueryService.class);

    private final InfoSuppRepository infoSuppRepository;

    private final InfoSuppMapper infoSuppMapper;

    private final UserService userService;

    public InfoSuppQueryService(InfoSuppRepository infoSuppRepository, InfoSuppMapper infoSuppMapper, UserService userService) {
        this.infoSuppRepository = infoSuppRepository;
        this.infoSuppMapper = infoSuppMapper;
        this.userService = userService;
    }

    /**
     * Return a {@link List} of {@link InfoSuppDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InfoSuppDTO> findByCriteria(InfoSuppCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InfoSupp> specification = createSpecification(criteria);
        return infoSuppMapper.toDto(infoSuppRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InfoSuppDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InfoSuppDTO> findByCriteria(InfoSuppCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InfoSupp> specification = createSpecification(criteria);
        return infoSuppRepository.findAll(specification, page).map(infoSuppMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InfoSuppCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InfoSupp> specification = createSpecification(criteria);
        return infoSuppRepository.count(specification);
    }

    /**
     * Function to convert {@link InfoSuppCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InfoSupp> createSpecification(InfoSuppCriteria criteria) {
        Specification<InfoSupp> specification = Specification.where(null);
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
                                root -> root.join(InfoSupp_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(
                        new LongFilter().setEquals(0L),
                        root -> root.join(InfoSupp_.event, JoinType.LEFT).get(Event_.eventId)
                    )
                );
        }
        //ADD FILTER END
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getInfoSuppId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInfoSuppId(), InfoSupp_.infoSuppId));
            }
            if (criteria.getInfoSuppName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInfoSuppName(), InfoSupp_.infoSuppName));
            }
            if (criteria.getInfoSuppDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getInfoSuppDescription(), InfoSupp_.infoSuppDescription));
            }
            if (criteria.getInfoSuppParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInfoSuppParams(), InfoSupp_.infoSuppParams));
            }
            if (criteria.getInfoSuppAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInfoSuppAttributs(), InfoSupp_.infoSuppAttributs));
            }
            if (criteria.getInfoSuppStat() != null) {
                specification = specification.and(buildSpecification(criteria.getInfoSuppStat(), InfoSupp_.infoSuppStat));
            }
            if (criteria.getInfoSuppTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInfoSuppTypeId(),
                            root -> root.join(InfoSupp_.infoSuppType, JoinType.LEFT).get(InfoSuppType_.infoSuppTypeId)
                        )
                    );
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(InfoSupp_.accreditation, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(InfoSupp_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
        }
        return specification;
    }
}

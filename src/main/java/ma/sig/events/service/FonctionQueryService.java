package ma.sig.events.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Fonction;
import ma.sig.events.repository.FonctionRepository;
import ma.sig.events.security.AuthoritiesConstants;
import ma.sig.events.security.SecurityUtils;
import ma.sig.events.service.criteria.FonctionCriteria;
import ma.sig.events.service.dto.FonctionDTO;
import ma.sig.events.service.mapper.FonctionMapper;
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
 * Service for executing complex queries for {@link Fonction} entities in the database.
 * The main input is a {@link FonctionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FonctionDTO} or a {@link Page} of {@link FonctionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FonctionQueryService extends QueryService<Fonction> {

    private final Logger log = LoggerFactory.getLogger(FonctionQueryService.class);

    private final FonctionRepository fonctionRepository;

    private final FonctionMapper fonctionMapper;

    private final UserService userService;

    public FonctionQueryService(FonctionRepository fonctionRepository, FonctionMapper fonctionMapper, UserService userService) {
        this.fonctionRepository = fonctionRepository;
        this.fonctionMapper = fonctionMapper;
        this.userService = userService;
    }

    /**
     * Return a {@link List} of {@link FonctionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FonctionDTO> findByCriteria(FonctionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fonction> specification = createSpecification(criteria);
        return fonctionMapper.toDto(fonctionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FonctionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FonctionDTO> findByCriteria(FonctionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fonction> specification = createSpecification(criteria);
        return fonctionRepository.findAll(specification, page).map(fonctionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FonctionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fonction> specification = createSpecification(criteria);
        return fonctionRepository.count(specification);
    }

    /**
     * Function to convert {@link FonctionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Fonction> createSpecification(FonctionCriteria criteria) {
        Specification<Fonction> specification = Specification.where(null);
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
                                root -> root.join(Fonction_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(
                        new LongFilter().setEquals(0L),
                        root -> root.join(Fonction_.event, JoinType.LEFT).get(Event_.eventId)
                    )
                );
        }
        //ADD FILTER END
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getFonctionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFonctionId(), Fonction_.fonctionId));
            }
            if (criteria.getFonctionName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFonctionName(), Fonction_.fonctionName));
            }
            if (criteria.getFonctionAbreviation() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFonctionAbreviation(), Fonction_.fonctionAbreviation));
            }
            if (criteria.getFonctionColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFonctionColor(), Fonction_.fonctionColor));
            }
            if (criteria.getFonctionDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFonctionDescription(), Fonction_.fonctionDescription));
            }
            if (criteria.getFonctionParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFonctionParams(), Fonction_.fonctionParams));
            }
            if (criteria.getFonctionAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFonctionAttributs(), Fonction_.fonctionAttributs));
            }
            if (criteria.getFonctionStat() != null) {
                specification = specification.and(buildSpecification(criteria.getFonctionStat(), Fonction_.fonctionStat));
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(Fonction_.accreditations, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
            if (criteria.getAreaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAreaId(), root -> root.join(Fonction_.areas, JoinType.LEFT).get(Area_.areaId))
                    );
            }
            if (criteria.getCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoryId(),
                            root -> root.join(Fonction_.category, JoinType.LEFT).get(Category_.categoryId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(Fonction_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
        }
        return specification;
    }
}

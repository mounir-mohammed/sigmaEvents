package ma.sig.events.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Site;
import ma.sig.events.repository.SiteRepository;
import ma.sig.events.security.AuthoritiesConstants;
import ma.sig.events.security.SecurityUtils;
import ma.sig.events.service.criteria.SiteCriteria;
import ma.sig.events.service.dto.SiteDTO;
import ma.sig.events.service.mapper.SiteMapper;
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
 * Service for executing complex queries for {@link Site} entities in the database.
 * The main input is a {@link SiteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SiteDTO} or a {@link Page} of {@link SiteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SiteQueryService extends QueryService<Site> {

    private final Logger log = LoggerFactory.getLogger(SiteQueryService.class);

    private final SiteRepository siteRepository;

    private final SiteMapper siteMapper;

    private final UserService userService;

    public SiteQueryService(SiteRepository siteRepository, SiteMapper siteMapper, UserService userService) {
        this.siteRepository = siteRepository;
        this.siteMapper = siteMapper;
        this.userService = userService;
    }

    /**
     * Return a {@link List} of {@link SiteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SiteDTO> findByCriteria(SiteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Site> specification = createSpecification(criteria);
        return siteMapper.toDto(siteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SiteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteDTO> findByCriteria(SiteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Site> specification = createSpecification(criteria);
        return siteRepository.findAll(specification, page).map(siteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SiteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Site> specification = createSpecification(criteria);
        return siteRepository.count(specification);
    }

    /**
     * Function to convert {@link SiteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Site> createSpecification(SiteCriteria criteria) {
        Specification<Site> specification = Specification.where(null);
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
                                root -> root.join(Site_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(new LongFilter().setEquals(0L), root -> root.join(Site_.event, JoinType.LEFT).get(Event_.eventId))
                );
        }
        //ADD FILTER END
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getSiteId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSiteId(), Site_.siteId));
            }
            if (criteria.getSiteName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiteName(), Site_.siteName));
            }
            if (criteria.getSiteColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiteColor(), Site_.siteColor));
            }
            if (criteria.getSiteAbreviation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiteAbreviation(), Site_.siteAbreviation));
            }
            if (criteria.getSiteDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiteDescription(), Site_.siteDescription));
            }
            if (criteria.getSiteAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiteAdresse(), Site_.siteAdresse));
            }
            if (criteria.getSiteEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiteEmail(), Site_.siteEmail));
            }
            if (criteria.getSiteTel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiteTel(), Site_.siteTel));
            }
            if (criteria.getSiteFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiteFax(), Site_.siteFax));
            }
            if (criteria.getSiteResponsableName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiteResponsableName(), Site_.siteResponsableName));
            }
            if (criteria.getSiteParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiteParams(), Site_.siteParams));
            }
            if (criteria.getSiteAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiteAttributs(), Site_.siteAttributs));
            }
            if (criteria.getSiteStat() != null) {
                specification = specification.and(buildSpecification(criteria.getSiteStat(), Site_.siteStat));
            }
            if (criteria.getCityId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCityId(), root -> root.join(Site_.city, JoinType.LEFT).get(City_.cityId))
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(Site_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(Site_.accreditations, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
        }
        return specification;
    }
}

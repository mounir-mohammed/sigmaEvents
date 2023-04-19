package ma.sig.events.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.PhotoArchive;
import ma.sig.events.repository.PhotoArchiveRepository;
import ma.sig.events.security.AuthoritiesConstants;
import ma.sig.events.security.SecurityUtils;
import ma.sig.events.service.criteria.PhotoArchiveCriteria;
import ma.sig.events.service.dto.PhotoArchiveDTO;
import ma.sig.events.service.mapper.PhotoArchiveMapper;
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
 * Service for executing complex queries for {@link PhotoArchive} entities in the database.
 * The main input is a {@link PhotoArchiveCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PhotoArchiveDTO} or a {@link Page} of {@link PhotoArchiveDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PhotoArchiveQueryService extends QueryService<PhotoArchive> {

    private final Logger log = LoggerFactory.getLogger(PhotoArchiveQueryService.class);

    private final PhotoArchiveRepository photoArchiveRepository;

    private final PhotoArchiveMapper photoArchiveMapper;

    private final UserService userService;

    public PhotoArchiveQueryService(
        PhotoArchiveRepository photoArchiveRepository,
        PhotoArchiveMapper photoArchiveMapper,
        UserService userService
    ) {
        this.photoArchiveRepository = photoArchiveRepository;
        this.photoArchiveMapper = photoArchiveMapper;
        this.userService = userService;
    }

    /**
     * Return a {@link List} of {@link PhotoArchiveDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PhotoArchiveDTO> findByCriteria(PhotoArchiveCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PhotoArchive> specification = createSpecification(criteria);
        return photoArchiveMapper.toDto(photoArchiveRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PhotoArchiveDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PhotoArchiveDTO> findByCriteria(PhotoArchiveCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PhotoArchive> specification = createSpecification(criteria);
        return photoArchiveRepository.findAll(specification, page).map(photoArchiveMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PhotoArchiveCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PhotoArchive> specification = createSpecification(criteria);
        return photoArchiveRepository.count(specification);
    }

    /**
     * Function to convert {@link PhotoArchiveCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PhotoArchive> createSpecification(PhotoArchiveCriteria criteria) {
        Specification<PhotoArchive> specification = Specification.where(null);
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
                                root -> root.join(PhotoArchive_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(
                        new LongFilter().setEquals(0L),
                        root -> root.join(PhotoArchive_.event, JoinType.LEFT).get(Event_.eventId)
                    )
                );
        }
        //ADD FILTER END
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getPhotoArchiveId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPhotoArchiveId(), PhotoArchive_.photoArchiveId));
            }
            if (criteria.getPhotoArchiveName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhotoArchiveName(), PhotoArchive_.photoArchiveName));
            }
            if (criteria.getPhotoArchivePath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhotoArchivePath(), PhotoArchive_.photoArchivePath));
            }
            if (criteria.getPhotoArchiveDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getPhotoArchiveDescription(), PhotoArchive_.photoArchiveDescription)
                    );
            }
            if (criteria.getPhotoArchiveParams() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPhotoArchiveParams(), PhotoArchive_.photoArchiveParams));
            }
            if (criteria.getPhotoArchiveAttributs() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPhotoArchiveAttributs(), PhotoArchive_.photoArchiveAttributs));
            }
            if (criteria.getPhotoArchiveStat() != null) {
                specification = specification.and(buildSpecification(criteria.getPhotoArchiveStat(), PhotoArchive_.photoArchiveStat));
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(PhotoArchive_.accreditation, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(PhotoArchive_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
        }
        return specification;
    }
}

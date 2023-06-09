package ma.sig.events.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Category;
import ma.sig.events.repository.CategoryRepository;
import ma.sig.events.security.AuthoritiesConstants;
import ma.sig.events.security.SecurityUtils;
import ma.sig.events.service.criteria.CategoryCriteria;
import ma.sig.events.service.dto.CategoryDTO;
import ma.sig.events.service.mapper.CategoryMapper;
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
 * Service for executing complex queries for {@link Category} entities in the database.
 * The main input is a {@link CategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoryDTO} or a {@link Page} of {@link CategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoryQueryService extends QueryService<Category> {

    private final Logger log = LoggerFactory.getLogger(CategoryQueryService.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final UserService userService;

    public CategoryQueryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.userService = userService;
    }

    /**
     * Return a {@link List} of {@link CategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> findByCriteria(CategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Category> specification = createSpecification(criteria);
        return categoryMapper.toDto(categoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findByCriteria(CategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Category> specification = createSpecification(criteria);
        return categoryRepository.findAll(specification, page).map(categoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Category> specification = createSpecification(criteria);
        return categoryRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Category> createSpecification(CategoryCriteria criteria) {
        Specification<Category> specification = Specification.where(null);
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
                                root -> root.join(Category_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(
                        new LongFilter().setEquals(0L),
                        root -> root.join(Category_.event, JoinType.LEFT).get(Event_.eventId)
                    )
                );
        }
        //ADD FILTER END
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCategoryId(), Category_.categoryId));
            }
            if (criteria.getCategoryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoryName(), Category_.categoryName));
            }
            if (criteria.getCategoryAbreviation() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCategoryAbreviation(), Category_.categoryAbreviation));
            }
            if (criteria.getCategoryColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoryColor(), Category_.categoryColor));
            }
            if (criteria.getCategoryDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCategoryDescription(), Category_.categoryDescription));
            }
            if (criteria.getCategoryParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoryParams(), Category_.categoryParams));
            }
            if (criteria.getCategoryAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoryAttributs(), Category_.categoryAttributs));
            }
            if (criteria.getCategoryStat() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryStat(), Category_.categoryStat));
            }
            if (criteria.getFonctionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFonctionId(),
                            root -> root.join(Category_.fonctions, JoinType.LEFT).get(Fonction_.fonctionId)
                        )
                    );
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(Category_.accreditations, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
            if (criteria.getPrintingModelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrintingModelId(),
                            root -> root.join(Category_.printingModel, JoinType.LEFT).get(PrintingModel_.printingModelId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(Category_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
        }
        return specification;
    }

    public Optional<CategoryDTO> findByIdCheckEvent(Long id) {
        log.debug("find CategoryDTO by id and check event : {}", id);
        final Specification<Category> specification = createEventSpecification(id);
        return categoryRepository.findOne(specification).map(categoryMapper::toDto);
    }

    private Specification<Category> createEventSpecification(Long id) {
        Specification<Category> specification = Specification.where(null);
        specification = specification.and(buildSpecification(new LongFilter().setEquals(id), Category_.categoryId));
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
                                root -> root.join(Category_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(
                        new LongFilter().setEquals(0L),
                        root -> root.join(Category_.event, JoinType.LEFT).get(Event_.eventId)
                    )
                );
        }
        return specification;
    }
}

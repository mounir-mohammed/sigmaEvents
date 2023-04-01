package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.PrintingModel;
import ma.sig.events.repository.PrintingModelRepository;
import ma.sig.events.service.criteria.PrintingModelCriteria;
import ma.sig.events.service.dto.PrintingModelDTO;
import ma.sig.events.service.mapper.PrintingModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PrintingModel} entities in the database.
 * The main input is a {@link PrintingModelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrintingModelDTO} or a {@link Page} of {@link PrintingModelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrintingModelQueryService extends QueryService<PrintingModel> {

    private final Logger log = LoggerFactory.getLogger(PrintingModelQueryService.class);

    private final PrintingModelRepository printingModelRepository;

    private final PrintingModelMapper printingModelMapper;

    public PrintingModelQueryService(PrintingModelRepository printingModelRepository, PrintingModelMapper printingModelMapper) {
        this.printingModelRepository = printingModelRepository;
        this.printingModelMapper = printingModelMapper;
    }

    /**
     * Return a {@link List} of {@link PrintingModelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrintingModelDTO> findByCriteria(PrintingModelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PrintingModel> specification = createSpecification(criteria);
        return printingModelMapper.toDto(printingModelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PrintingModelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrintingModelDTO> findByCriteria(PrintingModelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PrintingModel> specification = createSpecification(criteria);
        return printingModelRepository.findAll(specification, page).map(printingModelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PrintingModelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PrintingModel> specification = createSpecification(criteria);
        return printingModelRepository.count(specification);
    }

    /**
     * Function to convert {@link PrintingModelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PrintingModel> createSpecification(PrintingModelCriteria criteria) {
        Specification<PrintingModel> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getPrintingModelId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrintingModelId(), PrintingModel_.printingModelId));
            }
            if (criteria.getPrintingModelName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingModelName(), PrintingModel_.printingModelName));
            }
            if (criteria.getPrintingModelFile() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingModelFile(), PrintingModel_.printingModelFile));
            }
            if (criteria.getPrintingModelPath() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingModelPath(), PrintingModel_.printingModelPath));
            }
            if (criteria.getPrintingModelDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getPrintingModelDescription(), PrintingModel_.printingModelDescription)
                    );
            }
            if (criteria.getPrintingModelParams() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingModelParams(), PrintingModel_.printingModelParams));
            }
            if (criteria.getPrintingModelAttributs() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getPrintingModelAttributs(), PrintingModel_.printingModelAttributs)
                    );
            }
            if (criteria.getPrintingModelStat() != null) {
                specification = specification.and(buildSpecification(criteria.getPrintingModelStat(), PrintingModel_.printingModelStat));
            }
            if (criteria.getPrintingCentreId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrintingCentreId(),
                            root -> root.join(PrintingModel_.printingCentres, JoinType.LEFT).get(PrintingCentre_.printingCentreId)
                        )
                    );
            }
            if (criteria.getAccreditationTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationTypeId(),
                            root -> root.join(PrintingModel_.accreditationTypes, JoinType.LEFT).get(AccreditationType_.accreditationTypeId)
                        )
                    );
            }
            if (criteria.getCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoryId(),
                            root -> root.join(PrintingModel_.categories, JoinType.LEFT).get(Category_.categoryId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventId(),
                            root -> root.join(PrintingModel_.event, JoinType.LEFT).get(Event_.eventId)
                        )
                    );
            }
        }
        return specification;
    }
}

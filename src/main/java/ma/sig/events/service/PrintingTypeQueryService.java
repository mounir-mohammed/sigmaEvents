package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.PrintingType;
import ma.sig.events.repository.PrintingTypeRepository;
import ma.sig.events.service.criteria.PrintingTypeCriteria;
import ma.sig.events.service.dto.PrintingTypeDTO;
import ma.sig.events.service.mapper.PrintingTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PrintingType} entities in the database.
 * The main input is a {@link PrintingTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrintingTypeDTO} or a {@link Page} of {@link PrintingTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrintingTypeQueryService extends QueryService<PrintingType> {

    private final Logger log = LoggerFactory.getLogger(PrintingTypeQueryService.class);

    private final PrintingTypeRepository printingTypeRepository;

    private final PrintingTypeMapper printingTypeMapper;

    public PrintingTypeQueryService(PrintingTypeRepository printingTypeRepository, PrintingTypeMapper printingTypeMapper) {
        this.printingTypeRepository = printingTypeRepository;
        this.printingTypeMapper = printingTypeMapper;
    }

    /**
     * Return a {@link List} of {@link PrintingTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrintingTypeDTO> findByCriteria(PrintingTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PrintingType> specification = createSpecification(criteria);
        return printingTypeMapper.toDto(printingTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PrintingTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrintingTypeDTO> findByCriteria(PrintingTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PrintingType> specification = createSpecification(criteria);
        return printingTypeRepository.findAll(specification, page).map(printingTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PrintingTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PrintingType> specification = createSpecification(criteria);
        return printingTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link PrintingTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PrintingType> createSpecification(PrintingTypeCriteria criteria) {
        Specification<PrintingType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getPrintingTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrintingTypeId(), PrintingType_.printingTypeId));
            }
            if (criteria.getPrintingTypeValue() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingTypeValue(), PrintingType_.printingTypeValue));
            }
            if (criteria.getPrintingTypeDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getPrintingTypeDescription(), PrintingType_.printingTypeDescription)
                    );
            }
            if (criteria.getPrintingTypeParams() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingTypeParams(), PrintingType_.printingTypeParams));
            }
            if (criteria.getPrintingTypeAttributs() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingTypeAttributs(), PrintingType_.printingTypeAttributs));
            }
            if (criteria.getPrintingTypeStat() != null) {
                specification = specification.and(buildSpecification(criteria.getPrintingTypeStat(), PrintingType_.printingTypeStat));
            }
            if (criteria.getPrintingCentreId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrintingCentreId(),
                            root -> root.join(PrintingType_.printingCentres, JoinType.LEFT).get(PrintingCentre_.printingCentreId)
                        )
                    );
            }
        }
        return specification;
    }
}

package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.PrintingCentre;
import ma.sig.events.repository.PrintingCentreRepository;
import ma.sig.events.service.criteria.PrintingCentreCriteria;
import ma.sig.events.service.dto.PrintingCentreDTO;
import ma.sig.events.service.mapper.PrintingCentreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PrintingCentre} entities in the database.
 * The main input is a {@link PrintingCentreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrintingCentreDTO} or a {@link Page} of {@link PrintingCentreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrintingCentreQueryService extends QueryService<PrintingCentre> {

    private final Logger log = LoggerFactory.getLogger(PrintingCentreQueryService.class);

    private final PrintingCentreRepository printingCentreRepository;

    private final PrintingCentreMapper printingCentreMapper;

    public PrintingCentreQueryService(PrintingCentreRepository printingCentreRepository, PrintingCentreMapper printingCentreMapper) {
        this.printingCentreRepository = printingCentreRepository;
        this.printingCentreMapper = printingCentreMapper;
    }

    /**
     * Return a {@link List} of {@link PrintingCentreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrintingCentreDTO> findByCriteria(PrintingCentreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PrintingCentre> specification = createSpecification(criteria);
        return printingCentreMapper.toDto(printingCentreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PrintingCentreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrintingCentreDTO> findByCriteria(PrintingCentreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PrintingCentre> specification = createSpecification(criteria);
        return printingCentreRepository.findAll(specification, page).map(printingCentreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PrintingCentreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PrintingCentre> specification = createSpecification(criteria);
        return printingCentreRepository.count(specification);
    }

    /**
     * Function to convert {@link PrintingCentreCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PrintingCentre> createSpecification(PrintingCentreCriteria criteria) {
        Specification<PrintingCentre> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getPrintingCentreId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPrintingCentreId(), PrintingCentre_.printingCentreId));
            }
            if (criteria.getPrintingCentreDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getPrintingCentreDescription(), PrintingCentre_.printingCentreDescription)
                    );
            }
            if (criteria.getPrintingCentreName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingCentreName(), PrintingCentre_.printingCentreName));
            }
            if (criteria.getPrintingCentreAdresse() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingCentreAdresse(), PrintingCentre_.printingCentreAdresse));
            }
            if (criteria.getPrintingCentreEmail() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingCentreEmail(), PrintingCentre_.printingCentreEmail));
            }
            if (criteria.getPrintingCentreTel() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingCentreTel(), PrintingCentre_.printingCentreTel));
            }
            if (criteria.getPrintingCentreFax() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingCentreFax(), PrintingCentre_.printingCentreFax));
            }
            if (criteria.getPrintingCentreResponsableName() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getPrintingCentreResponsableName(), PrintingCentre_.printingCentreResponsableName)
                    );
            }
            if (criteria.getPrintingParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrintingParams(), PrintingCentre_.printingParams));
            }
            if (criteria.getPrintingAttributs() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingAttributs(), PrintingCentre_.printingAttributs));
            }
            if (criteria.getPrintingCentreStat() != null) {
                specification = specification.and(buildSpecification(criteria.getPrintingCentreStat(), PrintingCentre_.printingCentreStat));
            }
            if (criteria.getCityId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCityId(), root -> root.join(PrintingCentre_.city, JoinType.LEFT).get(City_.cityId))
                    );
            }
            if (criteria.getCountryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCountryId(),
                            root -> root.join(PrintingCentre_.country, JoinType.LEFT).get(Country_.countryId)
                        )
                    );
            }
            if (criteria.getOrganizId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganizId(),
                            root -> root.join(PrintingCentre_.organiz, JoinType.LEFT).get(Organiz_.organizId)
                        )
                    );
            }
            if (criteria.getPrintingTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrintingTypeId(),
                            root -> root.join(PrintingCentre_.printingType, JoinType.LEFT).get(PrintingType_.printingTypeId)
                        )
                    );
            }
            if (criteria.getPrintingServerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrintingServerId(),
                            root -> root.join(PrintingCentre_.printingServer, JoinType.LEFT).get(PrintingServer_.printingServerId)
                        )
                    );
            }
            if (criteria.getPrintingModelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrintingModelId(),
                            root -> root.join(PrintingCentre_.printingModel, JoinType.LEFT).get(PrintingModel_.printingModelId)
                        )
                    );
            }
            if (criteria.getLanguageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLanguageId(),
                            root -> root.join(PrintingCentre_.language, JoinType.LEFT).get(Language_.languageId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventId(),
                            root -> root.join(PrintingCentre_.event, JoinType.LEFT).get(Event_.eventId)
                        )
                    );
            }
        }
        return specification;
    }
}

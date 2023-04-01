package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.PrintingServer;
import ma.sig.events.repository.PrintingServerRepository;
import ma.sig.events.service.criteria.PrintingServerCriteria;
import ma.sig.events.service.dto.PrintingServerDTO;
import ma.sig.events.service.mapper.PrintingServerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PrintingServer} entities in the database.
 * The main input is a {@link PrintingServerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrintingServerDTO} or a {@link Page} of {@link PrintingServerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrintingServerQueryService extends QueryService<PrintingServer> {

    private final Logger log = LoggerFactory.getLogger(PrintingServerQueryService.class);

    private final PrintingServerRepository printingServerRepository;

    private final PrintingServerMapper printingServerMapper;

    public PrintingServerQueryService(PrintingServerRepository printingServerRepository, PrintingServerMapper printingServerMapper) {
        this.printingServerRepository = printingServerRepository;
        this.printingServerMapper = printingServerMapper;
    }

    /**
     * Return a {@link List} of {@link PrintingServerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrintingServerDTO> findByCriteria(PrintingServerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PrintingServer> specification = createSpecification(criteria);
        return printingServerMapper.toDto(printingServerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PrintingServerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrintingServerDTO> findByCriteria(PrintingServerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PrintingServer> specification = createSpecification(criteria);
        return printingServerRepository.findAll(specification, page).map(printingServerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PrintingServerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PrintingServer> specification = createSpecification(criteria);
        return printingServerRepository.count(specification);
    }

    /**
     * Function to convert {@link PrintingServerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PrintingServer> createSpecification(PrintingServerCriteria criteria) {
        Specification<PrintingServer> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getPrintingServerId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPrintingServerId(), PrintingServer_.printingServerId));
            }
            if (criteria.getPrintingServerName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingServerName(), PrintingServer_.printingServerName));
            }
            if (criteria.getPrintingServerDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getPrintingServerDescription(), PrintingServer_.printingServerDescription)
                    );
            }
            if (criteria.getPrintingServerHost() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingServerHost(), PrintingServer_.printingServerHost));
            }
            if (criteria.getPrintingServerPort() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingServerPort(), PrintingServer_.printingServerPort));
            }
            if (criteria.getPrintingServerDns() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingServerDns(), PrintingServer_.printingServerDns));
            }
            if (criteria.getPrintingServerProxy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingServerProxy(), PrintingServer_.printingServerProxy));
            }
            if (criteria.getPrintingServerParam1() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingServerParam1(), PrintingServer_.printingServerParam1));
            }
            if (criteria.getPrintingServerParam2() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingServerParam2(), PrintingServer_.printingServerParam2));
            }
            if (criteria.getPrintingServerParam3() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingServerParam3(), PrintingServer_.printingServerParam3));
            }
            if (criteria.getPrintingServerStat() != null) {
                specification = specification.and(buildSpecification(criteria.getPrintingServerStat(), PrintingServer_.printingServerStat));
            }
            if (criteria.getPrintingServerParams() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrintingServerParams(), PrintingServer_.printingServerParams));
            }
            if (criteria.getPrintingServerAttributs() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getPrintingServerAttributs(), PrintingServer_.printingServerAttributs)
                    );
            }
            if (criteria.getPrintingCentreId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrintingCentreId(),
                            root -> root.join(PrintingServer_.printingCentres, JoinType.LEFT).get(PrintingCentre_.printingCentreId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventId(),
                            root -> root.join(PrintingServer_.event, JoinType.LEFT).get(Event_.eventId)
                        )
                    );
            }
        }
        return specification;
    }
}

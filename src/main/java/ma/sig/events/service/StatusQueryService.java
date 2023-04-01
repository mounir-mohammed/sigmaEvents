package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Status;
import ma.sig.events.repository.StatusRepository;
import ma.sig.events.service.criteria.StatusCriteria;
import ma.sig.events.service.dto.StatusDTO;
import ma.sig.events.service.mapper.StatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Status} entities in the database.
 * The main input is a {@link StatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StatusDTO} or a {@link Page} of {@link StatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StatusQueryService extends QueryService<Status> {

    private final Logger log = LoggerFactory.getLogger(StatusQueryService.class);

    private final StatusRepository statusRepository;

    private final StatusMapper statusMapper;

    public StatusQueryService(StatusRepository statusRepository, StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    /**
     * Return a {@link List} of {@link StatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StatusDTO> findByCriteria(StatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Status> specification = createSpecification(criteria);
        return statusMapper.toDto(statusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StatusDTO> findByCriteria(StatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Status> specification = createSpecification(criteria);
        return statusRepository.findAll(specification, page).map(statusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Status> specification = createSpecification(criteria);
        return statusRepository.count(specification);
    }

    /**
     * Function to convert {@link StatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Status> createSpecification(StatusCriteria criteria) {
        Specification<Status> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatusId(), Status_.statusId));
            }
            if (criteria.getStatusName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusName(), Status_.statusName));
            }
            if (criteria.getStatusAbreviation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusAbreviation(), Status_.statusAbreviation));
            }
            if (criteria.getStatusColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusColor(), Status_.statusColor));
            }
            if (criteria.getStatusDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusDescription(), Status_.statusDescription));
            }
            if (criteria.getStatusUserCanPrint() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusUserCanPrint(), Status_.statusUserCanPrint));
            }
            if (criteria.getStatusUserCanUpdate() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusUserCanUpdate(), Status_.statusUserCanUpdate));
            }
            if (criteria.getStatusUserCanValidate() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusUserCanValidate(), Status_.statusUserCanValidate));
            }
            if (criteria.getStatusParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusParams(), Status_.statusParams));
            }
            if (criteria.getStatusAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusAttributs(), Status_.statusAttributs));
            }
            if (criteria.getStatusStat() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusStat(), Status_.statusStat));
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(Status_.accreditations, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
        }
        return specification;
    }
}

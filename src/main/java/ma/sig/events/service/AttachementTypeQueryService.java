package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.AttachementType;
import ma.sig.events.repository.AttachementTypeRepository;
import ma.sig.events.service.criteria.AttachementTypeCriteria;
import ma.sig.events.service.dto.AttachementTypeDTO;
import ma.sig.events.service.mapper.AttachementTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AttachementType} entities in the database.
 * The main input is a {@link AttachementTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AttachementTypeDTO} or a {@link Page} of {@link AttachementTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttachementTypeQueryService extends QueryService<AttachementType> {

    private final Logger log = LoggerFactory.getLogger(AttachementTypeQueryService.class);

    private final AttachementTypeRepository attachementTypeRepository;

    private final AttachementTypeMapper attachementTypeMapper;

    public AttachementTypeQueryService(AttachementTypeRepository attachementTypeRepository, AttachementTypeMapper attachementTypeMapper) {
        this.attachementTypeRepository = attachementTypeRepository;
        this.attachementTypeMapper = attachementTypeMapper;
    }

    /**
     * Return a {@link List} of {@link AttachementTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AttachementTypeDTO> findByCriteria(AttachementTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AttachementType> specification = createSpecification(criteria);
        return attachementTypeMapper.toDto(attachementTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AttachementTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AttachementTypeDTO> findByCriteria(AttachementTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AttachementType> specification = createSpecification(criteria);
        return attachementTypeRepository.findAll(specification, page).map(attachementTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttachementTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AttachementType> specification = createSpecification(criteria);
        return attachementTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link AttachementTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AttachementType> createSpecification(AttachementTypeCriteria criteria) {
        Specification<AttachementType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getAttachementTypeId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAttachementTypeId(), AttachementType_.attachementTypeId));
            }
            if (criteria.getAttachementTypeName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAttachementTypeName(), AttachementType_.attachementTypeName));
            }
            if (criteria.getAttachementTypeDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAttachementTypeDescription(), AttachementType_.attachementTypeDescription)
                    );
            }
            if (criteria.getAttachementTypeParams() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAttachementTypeParams(), AttachementType_.attachementTypeParams)
                    );
            }
            if (criteria.getAttachementTypeAttributs() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAttachementTypeAttributs(), AttachementType_.attachementTypeAttributs)
                    );
            }
            if (criteria.getAttachementTypeStat() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getAttachementTypeStat(), AttachementType_.attachementTypeStat));
            }
            if (criteria.getAttachementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttachementId(),
                            root -> root.join(AttachementType_.attachements, JoinType.LEFT).get(Attachement_.attachementId)
                        )
                    );
            }
        }
        return specification;
    }
}

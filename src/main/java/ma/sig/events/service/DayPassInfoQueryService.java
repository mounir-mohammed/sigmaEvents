package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.DayPassInfo;
import ma.sig.events.repository.DayPassInfoRepository;
import ma.sig.events.service.criteria.DayPassInfoCriteria;
import ma.sig.events.service.dto.DayPassInfoDTO;
import ma.sig.events.service.mapper.DayPassInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link DayPassInfo} entities in the database.
 * The main input is a {@link DayPassInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DayPassInfoDTO} or a {@link Page} of {@link DayPassInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DayPassInfoQueryService extends QueryService<DayPassInfo> {

    private final Logger log = LoggerFactory.getLogger(DayPassInfoQueryService.class);

    private final DayPassInfoRepository dayPassInfoRepository;

    private final DayPassInfoMapper dayPassInfoMapper;

    public DayPassInfoQueryService(DayPassInfoRepository dayPassInfoRepository, DayPassInfoMapper dayPassInfoMapper) {
        this.dayPassInfoRepository = dayPassInfoRepository;
        this.dayPassInfoMapper = dayPassInfoMapper;
    }

    /**
     * Return a {@link List} of {@link DayPassInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DayPassInfoDTO> findByCriteria(DayPassInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DayPassInfo> specification = createSpecification(criteria);
        return dayPassInfoMapper.toDto(dayPassInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DayPassInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DayPassInfoDTO> findByCriteria(DayPassInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DayPassInfo> specification = createSpecification(criteria);
        return dayPassInfoRepository.findAll(specification, page).map(dayPassInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DayPassInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DayPassInfo> specification = createSpecification(criteria);
        return dayPassInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link DayPassInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DayPassInfo> createSpecification(DayPassInfoCriteria criteria) {
        Specification<DayPassInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getDayPassInfoId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDayPassInfoId(), DayPassInfo_.dayPassInfoId));
            }
            if (criteria.getDayPassInfoName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDayPassInfoName(), DayPassInfo_.dayPassInfoName));
            }
            if (criteria.getDayPassDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDayPassDescription(), DayPassInfo_.dayPassDescription));
            }
            if (criteria.getDayPassInfoCreationDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDayPassInfoCreationDate(), DayPassInfo_.dayPassInfoCreationDate));
            }
            if (criteria.getDayPassInfoUpdateDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDayPassInfoUpdateDate(), DayPassInfo_.dayPassInfoUpdateDate));
            }
            if (criteria.getDayPassInfoCreatedByuser() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getDayPassInfoCreatedByuser(), DayPassInfo_.dayPassInfoCreatedByuser)
                    );
            }
            if (criteria.getDayPassInfoDateStart() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDayPassInfoDateStart(), DayPassInfo_.dayPassInfoDateStart));
            }
            if (criteria.getDayPassInfoDateEnd() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDayPassInfoDateEnd(), DayPassInfo_.dayPassInfoDateEnd));
            }
            if (criteria.getDayPassInfoNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDayPassInfoNumber(), DayPassInfo_.dayPassInfoNumber));
            }
            if (criteria.getDayPassParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDayPassParams(), DayPassInfo_.dayPassParams));
            }
            if (criteria.getDayPassAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDayPassAttributs(), DayPassInfo_.dayPassAttributs));
            }
            if (criteria.getDayPassInfoStat() != null) {
                specification = specification.and(buildSpecification(criteria.getDayPassInfoStat(), DayPassInfo_.dayPassInfoStat));
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(DayPassInfo_.accreditations, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(DayPassInfo_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
        }
        return specification;
    }
}

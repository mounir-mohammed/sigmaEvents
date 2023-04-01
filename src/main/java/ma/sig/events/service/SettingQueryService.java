package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Setting;
import ma.sig.events.repository.SettingRepository;
import ma.sig.events.service.criteria.SettingCriteria;
import ma.sig.events.service.dto.SettingDTO;
import ma.sig.events.service.mapper.SettingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Setting} entities in the database.
 * The main input is a {@link SettingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SettingDTO} or a {@link Page} of {@link SettingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SettingQueryService extends QueryService<Setting> {

    private final Logger log = LoggerFactory.getLogger(SettingQueryService.class);

    private final SettingRepository settingRepository;

    private final SettingMapper settingMapper;

    public SettingQueryService(SettingRepository settingRepository, SettingMapper settingMapper) {
        this.settingRepository = settingRepository;
        this.settingMapper = settingMapper;
    }

    /**
     * Return a {@link List} of {@link SettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SettingDTO> findByCriteria(SettingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Setting> specification = createSpecification(criteria);
        return settingMapper.toDto(settingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SettingDTO> findByCriteria(SettingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Setting> specification = createSpecification(criteria);
        return settingRepository.findAll(specification, page).map(settingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SettingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Setting> specification = createSpecification(criteria);
        return settingRepository.count(specification);
    }

    /**
     * Function to convert {@link SettingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Setting> createSpecification(SettingCriteria criteria) {
        Specification<Setting> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getSettingId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSettingId(), Setting_.settingId));
            }
            if (criteria.getSettingParentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSettingParentId(), Setting_.settingParentId));
            }
            if (criteria.getSettingType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSettingType(), Setting_.settingType));
            }
            if (criteria.getSettingNameClass() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSettingNameClass(), Setting_.settingNameClass));
            }
            if (criteria.getSettingDataType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSettingDataType(), Setting_.settingDataType));
            }
            if (criteria.getSettingDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSettingDescription(), Setting_.settingDescription));
            }
            if (criteria.getSettingValueString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSettingValueString(), Setting_.settingValueString));
            }
            if (criteria.getSettingValueLong() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSettingValueLong(), Setting_.settingValueLong));
            }
            if (criteria.getSettingValueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSettingValueDate(), Setting_.settingValueDate));
            }
            if (criteria.getSettingValueBoolean() != null) {
                specification = specification.and(buildSpecification(criteria.getSettingValueBoolean(), Setting_.settingValueBoolean));
            }
            if (criteria.getSettingParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSettingParams(), Setting_.settingParams));
            }
            if (criteria.getSettingAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSettingAttributs(), Setting_.settingAttributs));
            }
            if (criteria.getSettingStat() != null) {
                specification = specification.and(buildSpecification(criteria.getSettingStat(), Setting_.settingStat));
            }
            if (criteria.getLanguageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLanguageId(),
                            root -> root.join(Setting_.language, JoinType.LEFT).get(Language_.languageId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(Setting_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
        }
        return specification;
    }
}

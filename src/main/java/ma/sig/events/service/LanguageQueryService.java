package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Language;
import ma.sig.events.repository.LanguageRepository;
import ma.sig.events.service.criteria.LanguageCriteria;
import ma.sig.events.service.dto.LanguageDTO;
import ma.sig.events.service.mapper.LanguageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Language} entities in the database.
 * The main input is a {@link LanguageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LanguageDTO} or a {@link Page} of {@link LanguageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LanguageQueryService extends QueryService<Language> {

    private final Logger log = LoggerFactory.getLogger(LanguageQueryService.class);

    private final LanguageRepository languageRepository;

    private final LanguageMapper languageMapper;

    public LanguageQueryService(LanguageRepository languageRepository, LanguageMapper languageMapper) {
        this.languageRepository = languageRepository;
        this.languageMapper = languageMapper;
    }

    /**
     * Return a {@link List} of {@link LanguageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LanguageDTO> findByCriteria(LanguageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Language> specification = createSpecification(criteria);
        return languageMapper.toDto(languageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LanguageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LanguageDTO> findByCriteria(LanguageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Language> specification = createSpecification(criteria);
        return languageRepository.findAll(specification, page).map(languageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LanguageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Language> specification = createSpecification(criteria);
        return languageRepository.count(specification);
    }

    /**
     * Function to convert {@link LanguageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Language> createSpecification(LanguageCriteria criteria) {
        Specification<Language> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getLanguageId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLanguageId(), Language_.languageId));
            }
            if (criteria.getLanguageCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguageCode(), Language_.languageCode));
            }
            if (criteria.getLanguageName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguageName(), Language_.languageName));
            }
            if (criteria.getLanguageDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLanguageDescription(), Language_.languageDescription));
            }
            if (criteria.getLanguageParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguageParams(), Language_.languageParams));
            }
            if (criteria.getLanguageAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguageAttributs(), Language_.languageAttributs));
            }
            if (criteria.getLanguageStat() != null) {
                specification = specification.and(buildSpecification(criteria.getLanguageStat(), Language_.languageStat));
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(Language_.events, JoinType.LEFT).get(Event_.eventId))
                    );
            }
            if (criteria.getSettingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSettingId(),
                            root -> root.join(Language_.settings, JoinType.LEFT).get(Setting_.settingId)
                        )
                    );
            }
            if (criteria.getPrintingCentreId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrintingCentreId(),
                            root -> root.join(Language_.printingCentres, JoinType.LEFT).get(PrintingCentre_.printingCentreId)
                        )
                    );
            }
        }
        return specification;
    }
}

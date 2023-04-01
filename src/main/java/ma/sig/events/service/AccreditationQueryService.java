package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Accreditation;
import ma.sig.events.repository.AccreditationRepository;
import ma.sig.events.service.criteria.AccreditationCriteria;
import ma.sig.events.service.dto.AccreditationDTO;
import ma.sig.events.service.mapper.AccreditationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Accreditation} entities in the database.
 * The main input is a {@link AccreditationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccreditationDTO} or a {@link Page} of {@link AccreditationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccreditationQueryService extends QueryService<Accreditation> {

    private final Logger log = LoggerFactory.getLogger(AccreditationQueryService.class);

    private final AccreditationRepository accreditationRepository;

    private final AccreditationMapper accreditationMapper;

    public AccreditationQueryService(AccreditationRepository accreditationRepository, AccreditationMapper accreditationMapper) {
        this.accreditationRepository = accreditationRepository;
        this.accreditationMapper = accreditationMapper;
    }

    /**
     * Return a {@link List} of {@link AccreditationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccreditationDTO> findByCriteria(AccreditationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Accreditation> specification = createSpecification(criteria);
        return accreditationMapper.toDto(accreditationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccreditationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccreditationDTO> findByCriteria(AccreditationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Accreditation> specification = createSpecification(criteria);
        return accreditationRepository.findAll(specification, page).map(accreditationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccreditationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Accreditation> specification = createSpecification(criteria);
        return accreditationRepository.count(specification);
    }

    /**
     * Function to convert {@link AccreditationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Accreditation> createSpecification(AccreditationCriteria criteria) {
        Specification<Accreditation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getAccreditationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccreditationId(), Accreditation_.accreditationId));
            }
            if (criteria.getAccreditationFirstName() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAccreditationFirstName(), Accreditation_.accreditationFirstName)
                    );
            }
            if (criteria.getAccreditationSecondName() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAccreditationSecondName(), Accreditation_.accreditationSecondName)
                    );
            }
            if (criteria.getAccreditationLastName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAccreditationLastName(), Accreditation_.accreditationLastName));
            }
            if (criteria.getAccreditationBirthDay() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAccreditationBirthDay(), Accreditation_.accreditationBirthDay));
            }
            if (criteria.getAccreditationSexe() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAccreditationSexe(), Accreditation_.accreditationSexe));
            }
            if (criteria.getAccreditationOccupation() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAccreditationOccupation(), Accreditation_.accreditationOccupation)
                    );
            }
            if (criteria.getAccreditationDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAccreditationDescription(), Accreditation_.accreditationDescription)
                    );
            }
            if (criteria.getAccreditationEmail() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAccreditationEmail(), Accreditation_.accreditationEmail));
            }
            if (criteria.getAccreditationTel() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAccreditationTel(), Accreditation_.accreditationTel));
            }
            if (criteria.getAccreditationCinId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAccreditationCinId(), Accreditation_.accreditationCinId));
            }
            if (criteria.getAccreditationPasseportId() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAccreditationPasseportId(), Accreditation_.accreditationPasseportId)
                    );
            }
            if (criteria.getAccreditationCartePresseId() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAccreditationCartePresseId(), Accreditation_.accreditationCartePresseId)
                    );
            }
            if (criteria.getAccreditationCarteProfessionnelleId() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getAccreditationCarteProfessionnelleId(),
                            Accreditation_.accreditationCarteProfessionnelleId
                        )
                    );
            }
            if (criteria.getAccreditationCreationDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getAccreditationCreationDate(), Accreditation_.accreditationCreationDate)
                    );
            }
            if (criteria.getAccreditationUpdateDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getAccreditationUpdateDate(), Accreditation_.accreditationUpdateDate)
                    );
            }
            if (criteria.getAccreditationCreatedByuser() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAccreditationCreatedByuser(), Accreditation_.accreditationCreatedByuser)
                    );
            }
            if (criteria.getAccreditationDateStart() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAccreditationDateStart(), Accreditation_.accreditationDateStart));
            }
            if (criteria.getAccreditationDateEnd() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAccreditationDateEnd(), Accreditation_.accreditationDateEnd));
            }
            if (criteria.getAccreditationPrintStat() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getAccreditationPrintStat(), Accreditation_.accreditationPrintStat));
            }
            if (criteria.getAccreditationPrintNumber() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getAccreditationPrintNumber(), Accreditation_.accreditationPrintNumber)
                    );
            }
            if (criteria.getAccreditationParams() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAccreditationParams(), Accreditation_.accreditationParams));
            }
            if (criteria.getAccreditationAttributs() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAccreditationAttributs(), Accreditation_.accreditationAttributs)
                    );
            }
            if (criteria.getAccreditationStat() != null) {
                specification = specification.and(buildSpecification(criteria.getAccreditationStat(), Accreditation_.accreditationStat));
            }
            if (criteria.getPhotoArchiveId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPhotoArchiveId(),
                            root -> root.join(Accreditation_.photoArchives, JoinType.LEFT).get(PhotoArchive_.photoArchiveId)
                        )
                    );
            }
            if (criteria.getInfoSuppId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInfoSuppId(),
                            root -> root.join(Accreditation_.infoSupps, JoinType.LEFT).get(InfoSupp_.infoSuppId)
                        )
                    );
            }
            if (criteria.getNoteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getNoteId(), root -> root.join(Accreditation_.notes, JoinType.LEFT).get(Note_.noteId))
                    );
            }
            if (criteria.getCheckAccreditationHistoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCheckAccreditationHistoryId(),
                            root ->
                                root
                                    .join(Accreditation_.checkAccreditationHistories, JoinType.LEFT)
                                    .get(CheckAccreditationHistory_.checkAccreditationHistoryId)
                        )
                    );
            }
            if (criteria.getSiteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSiteId(), root -> root.join(Accreditation_.sites, JoinType.LEFT).get(Site_.siteId))
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventId(),
                            root -> root.join(Accreditation_.event, JoinType.LEFT).get(Event_.eventId)
                        )
                    );
            }
            if (criteria.getCivilityId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCivilityId(),
                            root -> root.join(Accreditation_.civility, JoinType.LEFT).get(Civility_.civilityId)
                        )
                    );
            }
            if (criteria.getSexeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSexeId(), root -> root.join(Accreditation_.sexe, JoinType.LEFT).get(Sexe_.sexeId))
                    );
            }
            if (criteria.getNationalityId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNationalityId(),
                            root -> root.join(Accreditation_.nationality, JoinType.LEFT).get(Nationality_.nationalityId)
                        )
                    );
            }
            if (criteria.getCountryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCountryId(),
                            root -> root.join(Accreditation_.country, JoinType.LEFT).get(Country_.countryId)
                        )
                    );
            }
            if (criteria.getCityId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCityId(), root -> root.join(Accreditation_.city, JoinType.LEFT).get(City_.cityId))
                    );
            }
            if (criteria.getCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoryId(),
                            root -> root.join(Accreditation_.category, JoinType.LEFT).get(Category_.categoryId)
                        )
                    );
            }
            if (criteria.getFonctionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFonctionId(),
                            root -> root.join(Accreditation_.fonction, JoinType.LEFT).get(Fonction_.fonctionId)
                        )
                    );
            }
            if (criteria.getOrganizId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganizId(),
                            root -> root.join(Accreditation_.organiz, JoinType.LEFT).get(Organiz_.organizId)
                        )
                    );
            }
            if (criteria.getAccreditationTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationTypeId(),
                            root -> root.join(Accreditation_.accreditationType, JoinType.LEFT).get(AccreditationType_.accreditationTypeId)
                        )
                    );
            }
            if (criteria.getStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStatusId(),
                            root -> root.join(Accreditation_.status, JoinType.LEFT).get(Status_.statusId)
                        )
                    );
            }
            if (criteria.getAttachementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttachementId(),
                            root -> root.join(Accreditation_.attachement, JoinType.LEFT).get(Attachement_.attachementId)
                        )
                    );
            }
            if (criteria.getCodeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCodeId(), root -> root.join(Accreditation_.code, JoinType.LEFT).get(Code_.codeId))
                    );
            }
            if (criteria.getDayPassInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDayPassInfoId(),
                            root -> root.join(Accreditation_.dayPassInfo, JoinType.LEFT).get(DayPassInfo_.dayPassInfoId)
                        )
                    );
            }
        }
        return specification;
    }
}

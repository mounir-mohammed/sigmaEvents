package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Event;
import ma.sig.events.repository.EventRepository;
import ma.sig.events.service.criteria.EventCriteria;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.mapper.EventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Event} entities in the database.
 * The main input is a {@link EventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EventDTO} or a {@link Page} of {@link EventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventQueryService extends QueryService<Event> {

    private final Logger log = LoggerFactory.getLogger(EventQueryService.class);

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    public EventQueryService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    /**
     * Return a {@link List} of {@link EventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EventDTO> findByCriteria(EventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Event> specification = createSpecification(criteria);
        return eventMapper.toDto(eventRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EventDTO> findByCriteria(EventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Event> specification = createSpecification(criteria);
        return eventRepository.findAll(specification, page).map(eventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Event> specification = createSpecification(criteria);
        return eventRepository.count(specification);
    }

    /**
     * Function to convert {@link EventCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Event> createSpecification(EventCriteria criteria) {
        Specification<Event> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getEventId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEventId(), Event_.eventId));
            }
            if (criteria.getEventName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventName(), Event_.eventName));
            }
            if (criteria.getEventColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventColor(), Event_.eventColor));
            }
            if (criteria.getEventDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventDescription(), Event_.eventDescription));
            }
            if (criteria.getEventAbreviation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventAbreviation(), Event_.eventAbreviation));
            }
            if (criteria.getEventdateStart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEventdateStart(), Event_.eventdateStart));
            }
            if (criteria.getEventdateEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEventdateEnd(), Event_.eventdateEnd));
            }
            if (criteria.getEventPrintingModelId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEventPrintingModelId(), Event_.eventPrintingModelId));
            }
            if (criteria.getEventWithPhoto() != null) {
                specification = specification.and(buildSpecification(criteria.getEventWithPhoto(), Event_.eventWithPhoto));
            }
            if (criteria.getEventNoCode() != null) {
                specification = specification.and(buildSpecification(criteria.getEventNoCode(), Event_.eventNoCode));
            }
            if (criteria.getEventCodeNoFilter() != null) {
                specification = specification.and(buildSpecification(criteria.getEventCodeNoFilter(), Event_.eventCodeNoFilter));
            }
            if (criteria.getEventCodeByTypeAccreditation() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getEventCodeByTypeAccreditation(), Event_.eventCodeByTypeAccreditation));
            }
            if (criteria.getEventCodeByTypeCategorie() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getEventCodeByTypeCategorie(), Event_.eventCodeByTypeCategorie));
            }
            if (criteria.getEventCodeByTypeFonction() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getEventCodeByTypeFonction(), Event_.eventCodeByTypeFonction));
            }
            if (criteria.getEventCodeByTypeOrganiz() != null) {
                specification = specification.and(buildSpecification(criteria.getEventCodeByTypeOrganiz(), Event_.eventCodeByTypeOrganiz));
            }
            if (criteria.getEventDefaultPrintingLanguage() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getEventDefaultPrintingLanguage(), Event_.eventDefaultPrintingLanguage));
            }
            if (criteria.getEventPCenterPrintingLanguage() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getEventPCenterPrintingLanguage(), Event_.eventPCenterPrintingLanguage));
            }
            if (criteria.getEventImported() != null) {
                specification = specification.and(buildSpecification(criteria.getEventImported(), Event_.eventImported));
            }
            if (criteria.getEventArchived() != null) {
                specification = specification.and(buildSpecification(criteria.getEventArchived(), Event_.eventArchived));
            }
            if (criteria.getEventArchiveFileName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getEventArchiveFileName(), Event_.eventArchiveFileName));
            }
            if (criteria.getEventURL() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventURL(), Event_.eventURL));
            }
            if (criteria.getEventDomaine() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventDomaine(), Event_.eventDomaine));
            }
            if (criteria.getEventSousDomaine() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventSousDomaine(), Event_.eventSousDomaine));
            }
            if (criteria.getEventCloned() != null) {
                specification = specification.and(buildSpecification(criteria.getEventCloned(), Event_.eventCloned));
            }
            if (criteria.getEventParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventParams(), Event_.eventParams));
            }
            if (criteria.getEventAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventAttributs(), Event_.eventAttributs));
            }
            if (criteria.getEventStat() != null) {
                specification = specification.and(buildSpecification(criteria.getEventStat(), Event_.eventStat));
            }
            if (criteria.getEventFormId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventFormId(),
                            root -> root.join(Event_.eventForms, JoinType.LEFT).get(EventForm_.formId)
                        )
                    );
            }
            if (criteria.getEventFieldId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventFieldId(),
                            root -> root.join(Event_.eventFields, JoinType.LEFT).get(EventField_.fieldId)
                        )
                    );
            }
            if (criteria.getEventControlId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventControlId(),
                            root -> root.join(Event_.eventControls, JoinType.LEFT).get(EventControl_.controlId)
                        )
                    );
            }
            if (criteria.getAreaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAreaId(), root -> root.join(Event_.areas, JoinType.LEFT).get(Area_.areaId))
                    );
            }
            if (criteria.getFonctionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFonctionId(),
                            root -> root.join(Event_.fonctions, JoinType.LEFT).get(Fonction_.fonctionId)
                        )
                    );
            }
            if (criteria.getCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoryId(),
                            root -> root.join(Event_.categories, JoinType.LEFT).get(Category_.categoryId)
                        )
                    );
            }
            if (criteria.getPrintingModelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrintingModelId(),
                            root -> root.join(Event_.printingModels, JoinType.LEFT).get(PrintingModel_.printingModelId)
                        )
                    );
            }
            if (criteria.getCodeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCodeId(), root -> root.join(Event_.codes, JoinType.LEFT).get(Code_.codeId))
                    );
            }
            if (criteria.getInfoSuppId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInfoSuppId(),
                            root -> root.join(Event_.infoSupps, JoinType.LEFT).get(InfoSupp_.infoSuppId)
                        )
                    );
            }
            if (criteria.getAttachementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttachementId(),
                            root -> root.join(Event_.attachements, JoinType.LEFT).get(Attachement_.attachementId)
                        )
                    );
            }
            if (criteria.getOrganizId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganizId(),
                            root -> root.join(Event_.organizs, JoinType.LEFT).get(Organiz_.organizId)
                        )
                    );
            }
            if (criteria.getPhotoArchiveId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPhotoArchiveId(),
                            root -> root.join(Event_.photoArchives, JoinType.LEFT).get(PhotoArchive_.photoArchiveId)
                        )
                    );
            }
            if (criteria.getSiteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSiteId(), root -> root.join(Event_.sites, JoinType.LEFT).get(Site_.siteId))
                    );
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(Event_.accreditations, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
            if (criteria.getNoteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getNoteId(), root -> root.join(Event_.notes, JoinType.LEFT).get(Note_.noteId))
                    );
            }
            if (criteria.getOperationHistoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOperationHistoryId(),
                            root -> root.join(Event_.operationHistories, JoinType.LEFT).get(OperationHistory_.operationHistoryId)
                        )
                    );
            }
            if (criteria.getPrintingCentreId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrintingCentreId(),
                            root -> root.join(Event_.printingCentres, JoinType.LEFT).get(PrintingCentre_.printingCentreId)
                        )
                    );
            }
            if (criteria.getSettingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSettingId(),
                            root -> root.join(Event_.settings, JoinType.LEFT).get(Setting_.settingId)
                        )
                    );
            }
            if (criteria.getPrintingServerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrintingServerId(),
                            root -> root.join(Event_.printingServers, JoinType.LEFT).get(PrintingServer_.printingServerId)
                        )
                    );
            }
            if (criteria.getCheckAccreditationHistoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCheckAccreditationHistoryId(),
                            root ->
                                root
                                    .join(Event_.checkAccreditationHistories, JoinType.LEFT)
                                    .get(CheckAccreditationHistory_.checkAccreditationHistoryId)
                        )
                    );
            }
            if (criteria.getCheckAccreditationReportId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCheckAccreditationReportId(),
                            root ->
                                root
                                    .join(Event_.checkAccreditationReports, JoinType.LEFT)
                                    .get(CheckAccreditationReport_.checkAccreditationReportId)
                        )
                    );
            }
            if (criteria.getAccreditationTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationTypeId(),
                            root -> root.join(Event_.accreditationTypes, JoinType.LEFT).get(AccreditationType_.accreditationTypeId)
                        )
                    );
            }
            if (criteria.getDayPassInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDayPassInfoId(),
                            root -> root.join(Event_.dayPassInfos, JoinType.LEFT).get(DayPassInfo_.dayPassInfoId)
                        )
                    );
            }
            if (criteria.getLanguageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLanguageId(),
                            root -> root.join(Event_.language, JoinType.LEFT).get(Language_.languageId)
                        )
                    );
            }
        }
        return specification;
    }
}

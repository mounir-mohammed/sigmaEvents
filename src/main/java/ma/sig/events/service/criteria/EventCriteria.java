package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Event} entity. This class is used
 * in {@link ma.sig.events.web.rest.EventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter eventId;

    private StringFilter eventName;

    private StringFilter eventColor;

    private StringFilter eventDescription;

    private StringFilter eventAbreviation;

    private ZonedDateTimeFilter eventdateStart;

    private ZonedDateTimeFilter eventdateEnd;

    private LongFilter eventPrintingModelId;

    private BooleanFilter eventWithPhoto;

    private BooleanFilter eventNoCode;

    private BooleanFilter eventCodeNoFilter;

    private BooleanFilter eventCodeByTypeAccreditation;

    private BooleanFilter eventCodeByTypeCategorie;

    private BooleanFilter eventCodeByTypeFonction;

    private BooleanFilter eventCodeByTypeOrganiz;

    private BooleanFilter eventDefaultPrintingLanguage;

    private BooleanFilter eventPCenterPrintingLanguage;

    private BooleanFilter eventImported;

    private BooleanFilter eventArchived;

    private StringFilter eventArchiveFileName;

    private StringFilter eventURL;

    private StringFilter eventDomaine;

    private StringFilter eventSousDomaine;

    private BooleanFilter eventCloned;

    private StringFilter eventParams;

    private StringFilter eventAttributs;

    private BooleanFilter eventStat;

    private LongFilter eventFormId;

    private LongFilter eventFieldId;

    private LongFilter eventControlId;

    private LongFilter areaId;

    private LongFilter fonctionId;

    private LongFilter categoryId;

    private LongFilter printingModelId;

    private LongFilter codeId;

    private LongFilter infoSuppId;

    private LongFilter attachementId;

    private LongFilter organizId;

    private LongFilter photoArchiveId;

    private LongFilter siteId;

    private LongFilter accreditationId;

    private LongFilter noteId;

    private LongFilter operationHistoryId;

    private LongFilter printingCentreId;

    private LongFilter settingId;

    private LongFilter printingServerId;

    private LongFilter checkAccreditationHistoryId;

    private LongFilter checkAccreditationReportId;

    private LongFilter accreditationTypeId;

    private LongFilter dayPassInfoId;

    private LongFilter languageId;

    private Boolean distinct;

    public EventCriteria() {}

    public EventCriteria(EventCriteria other) {
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.eventName = other.eventName == null ? null : other.eventName.copy();
        this.eventColor = other.eventColor == null ? null : other.eventColor.copy();
        this.eventDescription = other.eventDescription == null ? null : other.eventDescription.copy();
        this.eventAbreviation = other.eventAbreviation == null ? null : other.eventAbreviation.copy();
        this.eventdateStart = other.eventdateStart == null ? null : other.eventdateStart.copy();
        this.eventdateEnd = other.eventdateEnd == null ? null : other.eventdateEnd.copy();
        this.eventPrintingModelId = other.eventPrintingModelId == null ? null : other.eventPrintingModelId.copy();
        this.eventWithPhoto = other.eventWithPhoto == null ? null : other.eventWithPhoto.copy();
        this.eventNoCode = other.eventNoCode == null ? null : other.eventNoCode.copy();
        this.eventCodeNoFilter = other.eventCodeNoFilter == null ? null : other.eventCodeNoFilter.copy();
        this.eventCodeByTypeAccreditation = other.eventCodeByTypeAccreditation == null ? null : other.eventCodeByTypeAccreditation.copy();
        this.eventCodeByTypeCategorie = other.eventCodeByTypeCategorie == null ? null : other.eventCodeByTypeCategorie.copy();
        this.eventCodeByTypeFonction = other.eventCodeByTypeFonction == null ? null : other.eventCodeByTypeFonction.copy();
        this.eventCodeByTypeOrganiz = other.eventCodeByTypeOrganiz == null ? null : other.eventCodeByTypeOrganiz.copy();
        this.eventDefaultPrintingLanguage = other.eventDefaultPrintingLanguage == null ? null : other.eventDefaultPrintingLanguage.copy();
        this.eventPCenterPrintingLanguage = other.eventPCenterPrintingLanguage == null ? null : other.eventPCenterPrintingLanguage.copy();
        this.eventImported = other.eventImported == null ? null : other.eventImported.copy();
        this.eventArchived = other.eventArchived == null ? null : other.eventArchived.copy();
        this.eventArchiveFileName = other.eventArchiveFileName == null ? null : other.eventArchiveFileName.copy();
        this.eventURL = other.eventURL == null ? null : other.eventURL.copy();
        this.eventDomaine = other.eventDomaine == null ? null : other.eventDomaine.copy();
        this.eventSousDomaine = other.eventSousDomaine == null ? null : other.eventSousDomaine.copy();
        this.eventCloned = other.eventCloned == null ? null : other.eventCloned.copy();
        this.eventParams = other.eventParams == null ? null : other.eventParams.copy();
        this.eventAttributs = other.eventAttributs == null ? null : other.eventAttributs.copy();
        this.eventStat = other.eventStat == null ? null : other.eventStat.copy();
        this.eventFormId = other.eventFormId == null ? null : other.eventFormId.copy();
        this.eventFieldId = other.eventFieldId == null ? null : other.eventFieldId.copy();
        this.eventControlId = other.eventControlId == null ? null : other.eventControlId.copy();
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.fonctionId = other.fonctionId == null ? null : other.fonctionId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.printingModelId = other.printingModelId == null ? null : other.printingModelId.copy();
        this.codeId = other.codeId == null ? null : other.codeId.copy();
        this.infoSuppId = other.infoSuppId == null ? null : other.infoSuppId.copy();
        this.attachementId = other.attachementId == null ? null : other.attachementId.copy();
        this.organizId = other.organizId == null ? null : other.organizId.copy();
        this.photoArchiveId = other.photoArchiveId == null ? null : other.photoArchiveId.copy();
        this.siteId = other.siteId == null ? null : other.siteId.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.noteId = other.noteId == null ? null : other.noteId.copy();
        this.operationHistoryId = other.operationHistoryId == null ? null : other.operationHistoryId.copy();
        this.printingCentreId = other.printingCentreId == null ? null : other.printingCentreId.copy();
        this.settingId = other.settingId == null ? null : other.settingId.copy();
        this.printingServerId = other.printingServerId == null ? null : other.printingServerId.copy();
        this.checkAccreditationHistoryId = other.checkAccreditationHistoryId == null ? null : other.checkAccreditationHistoryId.copy();
        this.checkAccreditationReportId = other.checkAccreditationReportId == null ? null : other.checkAccreditationReportId.copy();
        this.accreditationTypeId = other.accreditationTypeId == null ? null : other.accreditationTypeId.copy();
        this.dayPassInfoId = other.dayPassInfoId == null ? null : other.dayPassInfoId.copy();
        this.languageId = other.languageId == null ? null : other.languageId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EventCriteria copy() {
        return new EventCriteria(this);
    }

    public LongFilter getEventId() {
        return eventId;
    }

    public LongFilter eventId() {
        if (eventId == null) {
            eventId = new LongFilter();
        }
        return eventId;
    }

    public void setEventId(LongFilter eventId) {
        this.eventId = eventId;
    }

    public StringFilter getEventName() {
        return eventName;
    }

    public StringFilter eventName() {
        if (eventName == null) {
            eventName = new StringFilter();
        }
        return eventName;
    }

    public void setEventName(StringFilter eventName) {
        this.eventName = eventName;
    }

    public StringFilter getEventColor() {
        return eventColor;
    }

    public StringFilter eventColor() {
        if (eventColor == null) {
            eventColor = new StringFilter();
        }
        return eventColor;
    }

    public void setEventColor(StringFilter eventColor) {
        this.eventColor = eventColor;
    }

    public StringFilter getEventDescription() {
        return eventDescription;
    }

    public StringFilter eventDescription() {
        if (eventDescription == null) {
            eventDescription = new StringFilter();
        }
        return eventDescription;
    }

    public void setEventDescription(StringFilter eventDescription) {
        this.eventDescription = eventDescription;
    }

    public StringFilter getEventAbreviation() {
        return eventAbreviation;
    }

    public StringFilter eventAbreviation() {
        if (eventAbreviation == null) {
            eventAbreviation = new StringFilter();
        }
        return eventAbreviation;
    }

    public void setEventAbreviation(StringFilter eventAbreviation) {
        this.eventAbreviation = eventAbreviation;
    }

    public ZonedDateTimeFilter getEventdateStart() {
        return eventdateStart;
    }

    public ZonedDateTimeFilter eventdateStart() {
        if (eventdateStart == null) {
            eventdateStart = new ZonedDateTimeFilter();
        }
        return eventdateStart;
    }

    public void setEventdateStart(ZonedDateTimeFilter eventdateStart) {
        this.eventdateStart = eventdateStart;
    }

    public ZonedDateTimeFilter getEventdateEnd() {
        return eventdateEnd;
    }

    public ZonedDateTimeFilter eventdateEnd() {
        if (eventdateEnd == null) {
            eventdateEnd = new ZonedDateTimeFilter();
        }
        return eventdateEnd;
    }

    public void setEventdateEnd(ZonedDateTimeFilter eventdateEnd) {
        this.eventdateEnd = eventdateEnd;
    }

    public LongFilter getEventPrintingModelId() {
        return eventPrintingModelId;
    }

    public LongFilter eventPrintingModelId() {
        if (eventPrintingModelId == null) {
            eventPrintingModelId = new LongFilter();
        }
        return eventPrintingModelId;
    }

    public void setEventPrintingModelId(LongFilter eventPrintingModelId) {
        this.eventPrintingModelId = eventPrintingModelId;
    }

    public BooleanFilter getEventWithPhoto() {
        return eventWithPhoto;
    }

    public BooleanFilter eventWithPhoto() {
        if (eventWithPhoto == null) {
            eventWithPhoto = new BooleanFilter();
        }
        return eventWithPhoto;
    }

    public void setEventWithPhoto(BooleanFilter eventWithPhoto) {
        this.eventWithPhoto = eventWithPhoto;
    }

    public BooleanFilter getEventNoCode() {
        return eventNoCode;
    }

    public BooleanFilter eventNoCode() {
        if (eventNoCode == null) {
            eventNoCode = new BooleanFilter();
        }
        return eventNoCode;
    }

    public void setEventNoCode(BooleanFilter eventNoCode) {
        this.eventNoCode = eventNoCode;
    }

    public BooleanFilter getEventCodeNoFilter() {
        return eventCodeNoFilter;
    }

    public BooleanFilter eventCodeNoFilter() {
        if (eventCodeNoFilter == null) {
            eventCodeNoFilter = new BooleanFilter();
        }
        return eventCodeNoFilter;
    }

    public void setEventCodeNoFilter(BooleanFilter eventCodeNoFilter) {
        this.eventCodeNoFilter = eventCodeNoFilter;
    }

    public BooleanFilter getEventCodeByTypeAccreditation() {
        return eventCodeByTypeAccreditation;
    }

    public BooleanFilter eventCodeByTypeAccreditation() {
        if (eventCodeByTypeAccreditation == null) {
            eventCodeByTypeAccreditation = new BooleanFilter();
        }
        return eventCodeByTypeAccreditation;
    }

    public void setEventCodeByTypeAccreditation(BooleanFilter eventCodeByTypeAccreditation) {
        this.eventCodeByTypeAccreditation = eventCodeByTypeAccreditation;
    }

    public BooleanFilter getEventCodeByTypeCategorie() {
        return eventCodeByTypeCategorie;
    }

    public BooleanFilter eventCodeByTypeCategorie() {
        if (eventCodeByTypeCategorie == null) {
            eventCodeByTypeCategorie = new BooleanFilter();
        }
        return eventCodeByTypeCategorie;
    }

    public void setEventCodeByTypeCategorie(BooleanFilter eventCodeByTypeCategorie) {
        this.eventCodeByTypeCategorie = eventCodeByTypeCategorie;
    }

    public BooleanFilter getEventCodeByTypeFonction() {
        return eventCodeByTypeFonction;
    }

    public BooleanFilter eventCodeByTypeFonction() {
        if (eventCodeByTypeFonction == null) {
            eventCodeByTypeFonction = new BooleanFilter();
        }
        return eventCodeByTypeFonction;
    }

    public void setEventCodeByTypeFonction(BooleanFilter eventCodeByTypeFonction) {
        this.eventCodeByTypeFonction = eventCodeByTypeFonction;
    }

    public BooleanFilter getEventCodeByTypeOrganiz() {
        return eventCodeByTypeOrganiz;
    }

    public BooleanFilter eventCodeByTypeOrganiz() {
        if (eventCodeByTypeOrganiz == null) {
            eventCodeByTypeOrganiz = new BooleanFilter();
        }
        return eventCodeByTypeOrganiz;
    }

    public void setEventCodeByTypeOrganiz(BooleanFilter eventCodeByTypeOrganiz) {
        this.eventCodeByTypeOrganiz = eventCodeByTypeOrganiz;
    }

    public BooleanFilter getEventDefaultPrintingLanguage() {
        return eventDefaultPrintingLanguage;
    }

    public BooleanFilter eventDefaultPrintingLanguage() {
        if (eventDefaultPrintingLanguage == null) {
            eventDefaultPrintingLanguage = new BooleanFilter();
        }
        return eventDefaultPrintingLanguage;
    }

    public void setEventDefaultPrintingLanguage(BooleanFilter eventDefaultPrintingLanguage) {
        this.eventDefaultPrintingLanguage = eventDefaultPrintingLanguage;
    }

    public BooleanFilter getEventPCenterPrintingLanguage() {
        return eventPCenterPrintingLanguage;
    }

    public BooleanFilter eventPCenterPrintingLanguage() {
        if (eventPCenterPrintingLanguage == null) {
            eventPCenterPrintingLanguage = new BooleanFilter();
        }
        return eventPCenterPrintingLanguage;
    }

    public void setEventPCenterPrintingLanguage(BooleanFilter eventPCenterPrintingLanguage) {
        this.eventPCenterPrintingLanguage = eventPCenterPrintingLanguage;
    }

    public BooleanFilter getEventImported() {
        return eventImported;
    }

    public BooleanFilter eventImported() {
        if (eventImported == null) {
            eventImported = new BooleanFilter();
        }
        return eventImported;
    }

    public void setEventImported(BooleanFilter eventImported) {
        this.eventImported = eventImported;
    }

    public BooleanFilter getEventArchived() {
        return eventArchived;
    }

    public BooleanFilter eventArchived() {
        if (eventArchived == null) {
            eventArchived = new BooleanFilter();
        }
        return eventArchived;
    }

    public void setEventArchived(BooleanFilter eventArchived) {
        this.eventArchived = eventArchived;
    }

    public StringFilter getEventArchiveFileName() {
        return eventArchiveFileName;
    }

    public StringFilter eventArchiveFileName() {
        if (eventArchiveFileName == null) {
            eventArchiveFileName = new StringFilter();
        }
        return eventArchiveFileName;
    }

    public void setEventArchiveFileName(StringFilter eventArchiveFileName) {
        this.eventArchiveFileName = eventArchiveFileName;
    }

    public StringFilter getEventURL() {
        return eventURL;
    }

    public StringFilter eventURL() {
        if (eventURL == null) {
            eventURL = new StringFilter();
        }
        return eventURL;
    }

    public void setEventURL(StringFilter eventURL) {
        this.eventURL = eventURL;
    }

    public StringFilter getEventDomaine() {
        return eventDomaine;
    }

    public StringFilter eventDomaine() {
        if (eventDomaine == null) {
            eventDomaine = new StringFilter();
        }
        return eventDomaine;
    }

    public void setEventDomaine(StringFilter eventDomaine) {
        this.eventDomaine = eventDomaine;
    }

    public StringFilter getEventSousDomaine() {
        return eventSousDomaine;
    }

    public StringFilter eventSousDomaine() {
        if (eventSousDomaine == null) {
            eventSousDomaine = new StringFilter();
        }
        return eventSousDomaine;
    }

    public void setEventSousDomaine(StringFilter eventSousDomaine) {
        this.eventSousDomaine = eventSousDomaine;
    }

    public BooleanFilter getEventCloned() {
        return eventCloned;
    }

    public BooleanFilter eventCloned() {
        if (eventCloned == null) {
            eventCloned = new BooleanFilter();
        }
        return eventCloned;
    }

    public void setEventCloned(BooleanFilter eventCloned) {
        this.eventCloned = eventCloned;
    }

    public StringFilter getEventParams() {
        return eventParams;
    }

    public StringFilter eventParams() {
        if (eventParams == null) {
            eventParams = new StringFilter();
        }
        return eventParams;
    }

    public void setEventParams(StringFilter eventParams) {
        this.eventParams = eventParams;
    }

    public StringFilter getEventAttributs() {
        return eventAttributs;
    }

    public StringFilter eventAttributs() {
        if (eventAttributs == null) {
            eventAttributs = new StringFilter();
        }
        return eventAttributs;
    }

    public void setEventAttributs(StringFilter eventAttributs) {
        this.eventAttributs = eventAttributs;
    }

    public BooleanFilter getEventStat() {
        return eventStat;
    }

    public BooleanFilter eventStat() {
        if (eventStat == null) {
            eventStat = new BooleanFilter();
        }
        return eventStat;
    }

    public void setEventStat(BooleanFilter eventStat) {
        this.eventStat = eventStat;
    }

    public LongFilter getEventFormId() {
        return eventFormId;
    }

    public LongFilter eventFormId() {
        if (eventFormId == null) {
            eventFormId = new LongFilter();
        }
        return eventFormId;
    }

    public void setEventFormId(LongFilter eventFormId) {
        this.eventFormId = eventFormId;
    }

    public LongFilter getEventFieldId() {
        return eventFieldId;
    }

    public LongFilter eventFieldId() {
        if (eventFieldId == null) {
            eventFieldId = new LongFilter();
        }
        return eventFieldId;
    }

    public void setEventFieldId(LongFilter eventFieldId) {
        this.eventFieldId = eventFieldId;
    }

    public LongFilter getEventControlId() {
        return eventControlId;
    }

    public LongFilter eventControlId() {
        if (eventControlId == null) {
            eventControlId = new LongFilter();
        }
        return eventControlId;
    }

    public void setEventControlId(LongFilter eventControlId) {
        this.eventControlId = eventControlId;
    }

    public LongFilter getAreaId() {
        return areaId;
    }

    public LongFilter areaId() {
        if (areaId == null) {
            areaId = new LongFilter();
        }
        return areaId;
    }

    public void setAreaId(LongFilter areaId) {
        this.areaId = areaId;
    }

    public LongFilter getFonctionId() {
        return fonctionId;
    }

    public LongFilter fonctionId() {
        if (fonctionId == null) {
            fonctionId = new LongFilter();
        }
        return fonctionId;
    }

    public void setFonctionId(LongFilter fonctionId) {
        this.fonctionId = fonctionId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public LongFilter categoryId() {
        if (categoryId == null) {
            categoryId = new LongFilter();
        }
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getPrintingModelId() {
        return printingModelId;
    }

    public LongFilter printingModelId() {
        if (printingModelId == null) {
            printingModelId = new LongFilter();
        }
        return printingModelId;
    }

    public void setPrintingModelId(LongFilter printingModelId) {
        this.printingModelId = printingModelId;
    }

    public LongFilter getCodeId() {
        return codeId;
    }

    public LongFilter codeId() {
        if (codeId == null) {
            codeId = new LongFilter();
        }
        return codeId;
    }

    public void setCodeId(LongFilter codeId) {
        this.codeId = codeId;
    }

    public LongFilter getInfoSuppId() {
        return infoSuppId;
    }

    public LongFilter infoSuppId() {
        if (infoSuppId == null) {
            infoSuppId = new LongFilter();
        }
        return infoSuppId;
    }

    public void setInfoSuppId(LongFilter infoSuppId) {
        this.infoSuppId = infoSuppId;
    }

    public LongFilter getAttachementId() {
        return attachementId;
    }

    public LongFilter attachementId() {
        if (attachementId == null) {
            attachementId = new LongFilter();
        }
        return attachementId;
    }

    public void setAttachementId(LongFilter attachementId) {
        this.attachementId = attachementId;
    }

    public LongFilter getOrganizId() {
        return organizId;
    }

    public LongFilter organizId() {
        if (organizId == null) {
            organizId = new LongFilter();
        }
        return organizId;
    }

    public void setOrganizId(LongFilter organizId) {
        this.organizId = organizId;
    }

    public LongFilter getPhotoArchiveId() {
        return photoArchiveId;
    }

    public LongFilter photoArchiveId() {
        if (photoArchiveId == null) {
            photoArchiveId = new LongFilter();
        }
        return photoArchiveId;
    }

    public void setPhotoArchiveId(LongFilter photoArchiveId) {
        this.photoArchiveId = photoArchiveId;
    }

    public LongFilter getSiteId() {
        return siteId;
    }

    public LongFilter siteId() {
        if (siteId == null) {
            siteId = new LongFilter();
        }
        return siteId;
    }

    public void setSiteId(LongFilter siteId) {
        this.siteId = siteId;
    }

    public LongFilter getAccreditationId() {
        return accreditationId;
    }

    public LongFilter accreditationId() {
        if (accreditationId == null) {
            accreditationId = new LongFilter();
        }
        return accreditationId;
    }

    public void setAccreditationId(LongFilter accreditationId) {
        this.accreditationId = accreditationId;
    }

    public LongFilter getNoteId() {
        return noteId;
    }

    public LongFilter noteId() {
        if (noteId == null) {
            noteId = new LongFilter();
        }
        return noteId;
    }

    public void setNoteId(LongFilter noteId) {
        this.noteId = noteId;
    }

    public LongFilter getOperationHistoryId() {
        return operationHistoryId;
    }

    public LongFilter operationHistoryId() {
        if (operationHistoryId == null) {
            operationHistoryId = new LongFilter();
        }
        return operationHistoryId;
    }

    public void setOperationHistoryId(LongFilter operationHistoryId) {
        this.operationHistoryId = operationHistoryId;
    }

    public LongFilter getPrintingCentreId() {
        return printingCentreId;
    }

    public LongFilter printingCentreId() {
        if (printingCentreId == null) {
            printingCentreId = new LongFilter();
        }
        return printingCentreId;
    }

    public void setPrintingCentreId(LongFilter printingCentreId) {
        this.printingCentreId = printingCentreId;
    }

    public LongFilter getSettingId() {
        return settingId;
    }

    public LongFilter settingId() {
        if (settingId == null) {
            settingId = new LongFilter();
        }
        return settingId;
    }

    public void setSettingId(LongFilter settingId) {
        this.settingId = settingId;
    }

    public LongFilter getPrintingServerId() {
        return printingServerId;
    }

    public LongFilter printingServerId() {
        if (printingServerId == null) {
            printingServerId = new LongFilter();
        }
        return printingServerId;
    }

    public void setPrintingServerId(LongFilter printingServerId) {
        this.printingServerId = printingServerId;
    }

    public LongFilter getCheckAccreditationHistoryId() {
        return checkAccreditationHistoryId;
    }

    public LongFilter checkAccreditationHistoryId() {
        if (checkAccreditationHistoryId == null) {
            checkAccreditationHistoryId = new LongFilter();
        }
        return checkAccreditationHistoryId;
    }

    public void setCheckAccreditationHistoryId(LongFilter checkAccreditationHistoryId) {
        this.checkAccreditationHistoryId = checkAccreditationHistoryId;
    }

    public LongFilter getCheckAccreditationReportId() {
        return checkAccreditationReportId;
    }

    public LongFilter checkAccreditationReportId() {
        if (checkAccreditationReportId == null) {
            checkAccreditationReportId = new LongFilter();
        }
        return checkAccreditationReportId;
    }

    public void setCheckAccreditationReportId(LongFilter checkAccreditationReportId) {
        this.checkAccreditationReportId = checkAccreditationReportId;
    }

    public LongFilter getAccreditationTypeId() {
        return accreditationTypeId;
    }

    public LongFilter accreditationTypeId() {
        if (accreditationTypeId == null) {
            accreditationTypeId = new LongFilter();
        }
        return accreditationTypeId;
    }

    public void setAccreditationTypeId(LongFilter accreditationTypeId) {
        this.accreditationTypeId = accreditationTypeId;
    }

    public LongFilter getDayPassInfoId() {
        return dayPassInfoId;
    }

    public LongFilter dayPassInfoId() {
        if (dayPassInfoId == null) {
            dayPassInfoId = new LongFilter();
        }
        return dayPassInfoId;
    }

    public void setDayPassInfoId(LongFilter dayPassInfoId) {
        this.dayPassInfoId = dayPassInfoId;
    }

    public LongFilter getLanguageId() {
        return languageId;
    }

    public LongFilter languageId() {
        if (languageId == null) {
            languageId = new LongFilter();
        }
        return languageId;
    }

    public void setLanguageId(LongFilter languageId) {
        this.languageId = languageId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EventCriteria that = (EventCriteria) o;
        return (
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(eventName, that.eventName) &&
            Objects.equals(eventColor, that.eventColor) &&
            Objects.equals(eventDescription, that.eventDescription) &&
            Objects.equals(eventAbreviation, that.eventAbreviation) &&
            Objects.equals(eventdateStart, that.eventdateStart) &&
            Objects.equals(eventdateEnd, that.eventdateEnd) &&
            Objects.equals(eventPrintingModelId, that.eventPrintingModelId) &&
            Objects.equals(eventWithPhoto, that.eventWithPhoto) &&
            Objects.equals(eventNoCode, that.eventNoCode) &&
            Objects.equals(eventCodeNoFilter, that.eventCodeNoFilter) &&
            Objects.equals(eventCodeByTypeAccreditation, that.eventCodeByTypeAccreditation) &&
            Objects.equals(eventCodeByTypeCategorie, that.eventCodeByTypeCategorie) &&
            Objects.equals(eventCodeByTypeFonction, that.eventCodeByTypeFonction) &&
            Objects.equals(eventCodeByTypeOrganiz, that.eventCodeByTypeOrganiz) &&
            Objects.equals(eventDefaultPrintingLanguage, that.eventDefaultPrintingLanguage) &&
            Objects.equals(eventPCenterPrintingLanguage, that.eventPCenterPrintingLanguage) &&
            Objects.equals(eventImported, that.eventImported) &&
            Objects.equals(eventArchived, that.eventArchived) &&
            Objects.equals(eventArchiveFileName, that.eventArchiveFileName) &&
            Objects.equals(eventURL, that.eventURL) &&
            Objects.equals(eventDomaine, that.eventDomaine) &&
            Objects.equals(eventSousDomaine, that.eventSousDomaine) &&
            Objects.equals(eventCloned, that.eventCloned) &&
            Objects.equals(eventParams, that.eventParams) &&
            Objects.equals(eventAttributs, that.eventAttributs) &&
            Objects.equals(eventStat, that.eventStat) &&
            Objects.equals(eventFormId, that.eventFormId) &&
            Objects.equals(eventFieldId, that.eventFieldId) &&
            Objects.equals(eventControlId, that.eventControlId) &&
            Objects.equals(areaId, that.areaId) &&
            Objects.equals(fonctionId, that.fonctionId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(printingModelId, that.printingModelId) &&
            Objects.equals(codeId, that.codeId) &&
            Objects.equals(infoSuppId, that.infoSuppId) &&
            Objects.equals(attachementId, that.attachementId) &&
            Objects.equals(organizId, that.organizId) &&
            Objects.equals(photoArchiveId, that.photoArchiveId) &&
            Objects.equals(siteId, that.siteId) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(noteId, that.noteId) &&
            Objects.equals(operationHistoryId, that.operationHistoryId) &&
            Objects.equals(printingCentreId, that.printingCentreId) &&
            Objects.equals(settingId, that.settingId) &&
            Objects.equals(printingServerId, that.printingServerId) &&
            Objects.equals(checkAccreditationHistoryId, that.checkAccreditationHistoryId) &&
            Objects.equals(checkAccreditationReportId, that.checkAccreditationReportId) &&
            Objects.equals(accreditationTypeId, that.accreditationTypeId) &&
            Objects.equals(dayPassInfoId, that.dayPassInfoId) &&
            Objects.equals(languageId, that.languageId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            eventId,
            eventName,
            eventColor,
            eventDescription,
            eventAbreviation,
            eventdateStart,
            eventdateEnd,
            eventPrintingModelId,
            eventWithPhoto,
            eventNoCode,
            eventCodeNoFilter,
            eventCodeByTypeAccreditation,
            eventCodeByTypeCategorie,
            eventCodeByTypeFonction,
            eventCodeByTypeOrganiz,
            eventDefaultPrintingLanguage,
            eventPCenterPrintingLanguage,
            eventImported,
            eventArchived,
            eventArchiveFileName,
            eventURL,
            eventDomaine,
            eventSousDomaine,
            eventCloned,
            eventParams,
            eventAttributs,
            eventStat,
            eventFormId,
            eventFieldId,
            eventControlId,
            areaId,
            fonctionId,
            categoryId,
            printingModelId,
            codeId,
            infoSuppId,
            attachementId,
            organizId,
            photoArchiveId,
            siteId,
            accreditationId,
            noteId,
            operationHistoryId,
            printingCentreId,
            settingId,
            printingServerId,
            checkAccreditationHistoryId,
            checkAccreditationReportId,
            accreditationTypeId,
            dayPassInfoId,
            languageId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventCriteria{" +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (eventName != null ? "eventName=" + eventName + ", " : "") +
            (eventColor != null ? "eventColor=" + eventColor + ", " : "") +
            (eventDescription != null ? "eventDescription=" + eventDescription + ", " : "") +
            (eventAbreviation != null ? "eventAbreviation=" + eventAbreviation + ", " : "") +
            (eventdateStart != null ? "eventdateStart=" + eventdateStart + ", " : "") +
            (eventdateEnd != null ? "eventdateEnd=" + eventdateEnd + ", " : "") +
            (eventPrintingModelId != null ? "eventPrintingModelId=" + eventPrintingModelId + ", " : "") +
            (eventWithPhoto != null ? "eventWithPhoto=" + eventWithPhoto + ", " : "") +
            (eventNoCode != null ? "eventNoCode=" + eventNoCode + ", " : "") +
            (eventCodeNoFilter != null ? "eventCodeNoFilter=" + eventCodeNoFilter + ", " : "") +
            (eventCodeByTypeAccreditation != null ? "eventCodeByTypeAccreditation=" + eventCodeByTypeAccreditation + ", " : "") +
            (eventCodeByTypeCategorie != null ? "eventCodeByTypeCategorie=" + eventCodeByTypeCategorie + ", " : "") +
            (eventCodeByTypeFonction != null ? "eventCodeByTypeFonction=" + eventCodeByTypeFonction + ", " : "") +
            (eventCodeByTypeOrganiz != null ? "eventCodeByTypeOrganiz=" + eventCodeByTypeOrganiz + ", " : "") +
            (eventDefaultPrintingLanguage != null ? "eventDefaultPrintingLanguage=" + eventDefaultPrintingLanguage + ", " : "") +
            (eventPCenterPrintingLanguage != null ? "eventPCenterPrintingLanguage=" + eventPCenterPrintingLanguage + ", " : "") +
            (eventImported != null ? "eventImported=" + eventImported + ", " : "") +
            (eventArchived != null ? "eventArchived=" + eventArchived + ", " : "") +
            (eventArchiveFileName != null ? "eventArchiveFileName=" + eventArchiveFileName + ", " : "") +
            (eventURL != null ? "eventURL=" + eventURL + ", " : "") +
            (eventDomaine != null ? "eventDomaine=" + eventDomaine + ", " : "") +
            (eventSousDomaine != null ? "eventSousDomaine=" + eventSousDomaine + ", " : "") +
            (eventCloned != null ? "eventCloned=" + eventCloned + ", " : "") +
            (eventParams != null ? "eventParams=" + eventParams + ", " : "") +
            (eventAttributs != null ? "eventAttributs=" + eventAttributs + ", " : "") +
            (eventStat != null ? "eventStat=" + eventStat + ", " : "") +
            (eventFormId != null ? "eventFormId=" + eventFormId + ", " : "") +
            (eventFieldId != null ? "eventFieldId=" + eventFieldId + ", " : "") +
            (eventControlId != null ? "eventControlId=" + eventControlId + ", " : "") +
            (areaId != null ? "areaId=" + areaId + ", " : "") +
            (fonctionId != null ? "fonctionId=" + fonctionId + ", " : "") +
            (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            (printingModelId != null ? "printingModelId=" + printingModelId + ", " : "") +
            (codeId != null ? "codeId=" + codeId + ", " : "") +
            (infoSuppId != null ? "infoSuppId=" + infoSuppId + ", " : "") +
            (attachementId != null ? "attachementId=" + attachementId + ", " : "") +
            (organizId != null ? "organizId=" + organizId + ", " : "") +
            (photoArchiveId != null ? "photoArchiveId=" + photoArchiveId + ", " : "") +
            (siteId != null ? "siteId=" + siteId + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (noteId != null ? "noteId=" + noteId + ", " : "") +
            (operationHistoryId != null ? "operationHistoryId=" + operationHistoryId + ", " : "") +
            (printingCentreId != null ? "printingCentreId=" + printingCentreId + ", " : "") +
            (settingId != null ? "settingId=" + settingId + ", " : "") +
            (printingServerId != null ? "printingServerId=" + printingServerId + ", " : "") +
            (checkAccreditationHistoryId != null ? "checkAccreditationHistoryId=" + checkAccreditationHistoryId + ", " : "") +
            (checkAccreditationReportId != null ? "checkAccreditationReportId=" + checkAccreditationReportId + ", " : "") +
            (accreditationTypeId != null ? "accreditationTypeId=" + accreditationTypeId + ", " : "") +
            (dayPassInfoId != null ? "dayPassInfoId=" + dayPassInfoId + ", " : "") +
            (languageId != null ? "languageId=" + languageId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

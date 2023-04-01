package ma.sig.events.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Event} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventDTO implements Serializable {

    private Long eventId;

    @NotNull
    @Size(min = 5, max = 100)
    private String eventName;

    @Size(max = 100)
    private String eventColor;

    @Size(max = 200)
    private String eventDescription;

    @Size(max = 10)
    private String eventAbreviation;

    @NotNull
    private ZonedDateTime eventdateStart;

    @NotNull
    private ZonedDateTime eventdateEnd;

    private Long eventPrintingModelId;

    @Lob
    private byte[] eventLogo;

    private String eventLogoContentType;

    @Lob
    private byte[] eventBannerCenter;

    private String eventBannerCenterContentType;

    @Lob
    private byte[] eventBannerRight;

    private String eventBannerRightContentType;

    @Lob
    private byte[] eventBannerLeft;

    private String eventBannerLeftContentType;

    @Lob
    private byte[] eventBannerBas;

    private String eventBannerBasContentType;
    private Boolean eventWithPhoto;

    private Boolean eventNoCode;

    private Boolean eventCodeNoFilter;

    private Boolean eventCodeByTypeAccreditation;

    private Boolean eventCodeByTypeCategorie;

    private Boolean eventCodeByTypeFonction;

    private Boolean eventCodeByTypeOrganiz;

    private Boolean eventDefaultPrintingLanguage;

    private Boolean eventPCenterPrintingLanguage;

    private Boolean eventImported;

    private Boolean eventArchived;

    private String eventArchiveFileName;

    private String eventURL;

    private String eventDomaine;

    private String eventSousDomaine;

    private Boolean eventCloned;

    private String eventParams;

    private String eventAttributs;

    private Boolean eventStat;

    private LanguageDTO language;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventColor() {
        return eventColor;
    }

    public void setEventColor(String eventColor) {
        this.eventColor = eventColor;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventAbreviation() {
        return eventAbreviation;
    }

    public void setEventAbreviation(String eventAbreviation) {
        this.eventAbreviation = eventAbreviation;
    }

    public ZonedDateTime getEventdateStart() {
        return eventdateStart;
    }

    public void setEventdateStart(ZonedDateTime eventdateStart) {
        this.eventdateStart = eventdateStart;
    }

    public ZonedDateTime getEventdateEnd() {
        return eventdateEnd;
    }

    public void setEventdateEnd(ZonedDateTime eventdateEnd) {
        this.eventdateEnd = eventdateEnd;
    }

    public Long getEventPrintingModelId() {
        return eventPrintingModelId;
    }

    public void setEventPrintingModelId(Long eventPrintingModelId) {
        this.eventPrintingModelId = eventPrintingModelId;
    }

    public byte[] getEventLogo() {
        return eventLogo;
    }

    public void setEventLogo(byte[] eventLogo) {
        this.eventLogo = eventLogo;
    }

    public String getEventLogoContentType() {
        return eventLogoContentType;
    }

    public void setEventLogoContentType(String eventLogoContentType) {
        this.eventLogoContentType = eventLogoContentType;
    }

    public byte[] getEventBannerCenter() {
        return eventBannerCenter;
    }

    public void setEventBannerCenter(byte[] eventBannerCenter) {
        this.eventBannerCenter = eventBannerCenter;
    }

    public String getEventBannerCenterContentType() {
        return eventBannerCenterContentType;
    }

    public void setEventBannerCenterContentType(String eventBannerCenterContentType) {
        this.eventBannerCenterContentType = eventBannerCenterContentType;
    }

    public byte[] getEventBannerRight() {
        return eventBannerRight;
    }

    public void setEventBannerRight(byte[] eventBannerRight) {
        this.eventBannerRight = eventBannerRight;
    }

    public String getEventBannerRightContentType() {
        return eventBannerRightContentType;
    }

    public void setEventBannerRightContentType(String eventBannerRightContentType) {
        this.eventBannerRightContentType = eventBannerRightContentType;
    }

    public byte[] getEventBannerLeft() {
        return eventBannerLeft;
    }

    public void setEventBannerLeft(byte[] eventBannerLeft) {
        this.eventBannerLeft = eventBannerLeft;
    }

    public String getEventBannerLeftContentType() {
        return eventBannerLeftContentType;
    }

    public void setEventBannerLeftContentType(String eventBannerLeftContentType) {
        this.eventBannerLeftContentType = eventBannerLeftContentType;
    }

    public byte[] getEventBannerBas() {
        return eventBannerBas;
    }

    public void setEventBannerBas(byte[] eventBannerBas) {
        this.eventBannerBas = eventBannerBas;
    }

    public String getEventBannerBasContentType() {
        return eventBannerBasContentType;
    }

    public void setEventBannerBasContentType(String eventBannerBasContentType) {
        this.eventBannerBasContentType = eventBannerBasContentType;
    }

    public Boolean getEventWithPhoto() {
        return eventWithPhoto;
    }

    public void setEventWithPhoto(Boolean eventWithPhoto) {
        this.eventWithPhoto = eventWithPhoto;
    }

    public Boolean getEventNoCode() {
        return eventNoCode;
    }

    public void setEventNoCode(Boolean eventNoCode) {
        this.eventNoCode = eventNoCode;
    }

    public Boolean getEventCodeNoFilter() {
        return eventCodeNoFilter;
    }

    public void setEventCodeNoFilter(Boolean eventCodeNoFilter) {
        this.eventCodeNoFilter = eventCodeNoFilter;
    }

    public Boolean getEventCodeByTypeAccreditation() {
        return eventCodeByTypeAccreditation;
    }

    public void setEventCodeByTypeAccreditation(Boolean eventCodeByTypeAccreditation) {
        this.eventCodeByTypeAccreditation = eventCodeByTypeAccreditation;
    }

    public Boolean getEventCodeByTypeCategorie() {
        return eventCodeByTypeCategorie;
    }

    public void setEventCodeByTypeCategorie(Boolean eventCodeByTypeCategorie) {
        this.eventCodeByTypeCategorie = eventCodeByTypeCategorie;
    }

    public Boolean getEventCodeByTypeFonction() {
        return eventCodeByTypeFonction;
    }

    public void setEventCodeByTypeFonction(Boolean eventCodeByTypeFonction) {
        this.eventCodeByTypeFonction = eventCodeByTypeFonction;
    }

    public Boolean getEventCodeByTypeOrganiz() {
        return eventCodeByTypeOrganiz;
    }

    public void setEventCodeByTypeOrganiz(Boolean eventCodeByTypeOrganiz) {
        this.eventCodeByTypeOrganiz = eventCodeByTypeOrganiz;
    }

    public Boolean getEventDefaultPrintingLanguage() {
        return eventDefaultPrintingLanguage;
    }

    public void setEventDefaultPrintingLanguage(Boolean eventDefaultPrintingLanguage) {
        this.eventDefaultPrintingLanguage = eventDefaultPrintingLanguage;
    }

    public Boolean getEventPCenterPrintingLanguage() {
        return eventPCenterPrintingLanguage;
    }

    public void setEventPCenterPrintingLanguage(Boolean eventPCenterPrintingLanguage) {
        this.eventPCenterPrintingLanguage = eventPCenterPrintingLanguage;
    }

    public Boolean getEventImported() {
        return eventImported;
    }

    public void setEventImported(Boolean eventImported) {
        this.eventImported = eventImported;
    }

    public Boolean getEventArchived() {
        return eventArchived;
    }

    public void setEventArchived(Boolean eventArchived) {
        this.eventArchived = eventArchived;
    }

    public String getEventArchiveFileName() {
        return eventArchiveFileName;
    }

    public void setEventArchiveFileName(String eventArchiveFileName) {
        this.eventArchiveFileName = eventArchiveFileName;
    }

    public String getEventURL() {
        return eventURL;
    }

    public void setEventURL(String eventURL) {
        this.eventURL = eventURL;
    }

    public String getEventDomaine() {
        return eventDomaine;
    }

    public void setEventDomaine(String eventDomaine) {
        this.eventDomaine = eventDomaine;
    }

    public String getEventSousDomaine() {
        return eventSousDomaine;
    }

    public void setEventSousDomaine(String eventSousDomaine) {
        this.eventSousDomaine = eventSousDomaine;
    }

    public Boolean getEventCloned() {
        return eventCloned;
    }

    public void setEventCloned(Boolean eventCloned) {
        this.eventCloned = eventCloned;
    }

    public String getEventParams() {
        return eventParams;
    }

    public void setEventParams(String eventParams) {
        this.eventParams = eventParams;
    }

    public String getEventAttributs() {
        return eventAttributs;
    }

    public void setEventAttributs(String eventAttributs) {
        this.eventAttributs = eventAttributs;
    }

    public Boolean getEventStat() {
        return eventStat;
    }

    public void setEventStat(Boolean eventStat) {
        this.eventStat = eventStat;
    }

    public LanguageDTO getLanguage() {
        return language;
    }

    public void setLanguage(LanguageDTO language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventDTO)) {
            return false;
        }

        EventDTO eventDTO = (EventDTO) o;
        if (this.eventId == null) {
            return false;
        }
        return Objects.equals(this.eventId, eventDTO.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.eventId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventDTO{" +
            "eventId=" + getEventId() +
            ", eventName='" + getEventName() + "'" +
            ", eventColor='" + getEventColor() + "'" +
            ", eventDescription='" + getEventDescription() + "'" +
            ", eventAbreviation='" + getEventAbreviation() + "'" +
            ", eventdateStart='" + getEventdateStart() + "'" +
            ", eventdateEnd='" + getEventdateEnd() + "'" +
            ", eventPrintingModelId=" + getEventPrintingModelId() +
            ", eventLogo='" + getEventLogo() + "'" +
            ", eventBannerCenter='" + getEventBannerCenter() + "'" +
            ", eventBannerRight='" + getEventBannerRight() + "'" +
            ", eventBannerLeft='" + getEventBannerLeft() + "'" +
            ", eventBannerBas='" + getEventBannerBas() + "'" +
            ", eventWithPhoto='" + getEventWithPhoto() + "'" +
            ", eventNoCode='" + getEventNoCode() + "'" +
            ", eventCodeNoFilter='" + getEventCodeNoFilter() + "'" +
            ", eventCodeByTypeAccreditation='" + getEventCodeByTypeAccreditation() + "'" +
            ", eventCodeByTypeCategorie='" + getEventCodeByTypeCategorie() + "'" +
            ", eventCodeByTypeFonction='" + getEventCodeByTypeFonction() + "'" +
            ", eventCodeByTypeOrganiz='" + getEventCodeByTypeOrganiz() + "'" +
            ", eventDefaultPrintingLanguage='" + getEventDefaultPrintingLanguage() + "'" +
            ", eventPCenterPrintingLanguage='" + getEventPCenterPrintingLanguage() + "'" +
            ", eventImported='" + getEventImported() + "'" +
            ", eventArchived='" + getEventArchived() + "'" +
            ", eventArchiveFileName='" + getEventArchiveFileName() + "'" +
            ", eventURL='" + getEventURL() + "'" +
            ", eventDomaine='" + getEventDomaine() + "'" +
            ", eventSousDomaine='" + getEventSousDomaine() + "'" +
            ", eventCloned='" + getEventCloned() + "'" +
            ", eventParams='" + getEventParams() + "'" +
            ", eventAttributs='" + getEventAttributs() + "'" +
            ", eventStat='" + getEventStat() + "'" +
            ", language=" + getLanguage() +
            "}";
    }
}

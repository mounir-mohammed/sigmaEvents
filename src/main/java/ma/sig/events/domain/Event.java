package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "event_name", length = 100, nullable = false)
    private String eventName;

    @Size(max = 100)
    @Column(name = "event_color", length = 100)
    private String eventColor;

    @Size(max = 200)
    @Column(name = "event_description", length = 200)
    private String eventDescription;

    @Size(max = 10)
    @Column(name = "event_abreviation", length = 10)
    private String eventAbreviation;

    @NotNull
    @Column(name = "eventdate_start", nullable = false)
    private ZonedDateTime eventdateStart;

    @NotNull
    @Column(name = "eventdate_end", nullable = false)
    private ZonedDateTime eventdateEnd;

    @Column(name = "event_printing_model_id")
    private Long eventPrintingModelId;

    @Lob
    @Column(name = "event_logo")
    private byte[] eventLogo;

    @Column(name = "event_logo_content_type")
    private String eventLogoContentType;

    @Lob
    @Column(name = "event_banner_center")
    private byte[] eventBannerCenter;

    @Column(name = "event_banner_center_content_type")
    private String eventBannerCenterContentType;

    @Lob
    @Column(name = "event_banner_right")
    private byte[] eventBannerRight;

    @Column(name = "event_banner_right_content_type")
    private String eventBannerRightContentType;

    @Lob
    @Column(name = "event_banner_left")
    private byte[] eventBannerLeft;

    @Column(name = "event_banner_left_content_type")
    private String eventBannerLeftContentType;

    @Lob
    @Column(name = "event_banner_bas")
    private byte[] eventBannerBas;

    @Column(name = "event_banner_bas_content_type")
    private String eventBannerBasContentType;

    @Column(name = "event_with_photo")
    private Boolean eventWithPhoto;

    @Column(name = "event_no_code")
    private Boolean eventNoCode;

    @Column(name = "event_code_no_filter")
    private Boolean eventCodeNoFilter;

    @Column(name = "event_code_by_type_accreditation")
    private Boolean eventCodeByTypeAccreditation;

    @Column(name = "event_code_by_type_categorie")
    private Boolean eventCodeByTypeCategorie;

    @Column(name = "event_code_by_type_fonction")
    private Boolean eventCodeByTypeFonction;

    @Column(name = "event_code_by_type_organiz")
    private Boolean eventCodeByTypeOrganiz;

    @Column(name = "event_default_printing_language")
    private Boolean eventDefaultPrintingLanguage;

    @Column(name = "event_p_center_printing_language")
    private Boolean eventPCenterPrintingLanguage;

    @Column(name = "event_imported")
    private Boolean eventImported;

    @Column(name = "event_archived")
    private Boolean eventArchived;

    @Column(name = "event_archive_file_name")
    private String eventArchiveFileName;

    @Column(name = "event_url")
    private String eventURL;

    @Column(name = "event_domaine")
    private String eventDomaine;

    @Column(name = "event_sous_domaine")
    private String eventSousDomaine;

    @Column(name = "event_cloned")
    private Boolean eventCloned;

    @Column(name = "event_params")
    private String eventParams;

    @Column(name = "event_attributs")
    private String eventAttributs;

    @Column(name = "event_stat")
    private Boolean eventStat;

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eventFields", "event" }, allowSetters = true)
    private Set<EventForm> eventForms = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eventControls", "event", "eventForm" }, allowSetters = true)
    private Set<EventField> eventFields = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "event", "eventField" }, allowSetters = true)
    private Set<EventControl> eventControls = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "event", "fonctions" }, allowSetters = true)
    private Set<Area> areas = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditations", "areas", "category", "event" }, allowSetters = true)
    private Set<Fonction> fonctions = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fonctions", "accreditations", "printingModel", "event" }, allowSetters = true)
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "printingCentres", "accreditationTypes", "categories", "event" }, allowSetters = true)
    private Set<PrintingModel> printingModels = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditations", "codeType", "event" }, allowSetters = true)
    private Set<Code> codes = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "infoSuppType", "accreditation", "event" }, allowSetters = true)
    private Set<InfoSupp> infoSupps = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditations", "attachementType", "event" }, allowSetters = true)
    private Set<Attachement> attachements = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "printingCentres", "accreditations", "country", "city", "event" }, allowSetters = true)
    private Set<Organiz> organizs = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditation", "event" }, allowSetters = true)
    private Set<PhotoArchive> photoArchives = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "city", "event", "accreditations" }, allowSetters = true)
    private Set<Site> sites = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "photoArchives",
            "infoSupps",
            "notes",
            "checkAccreditationHistories",
            "sites",
            "event",
            "civility",
            "sexe",
            "nationality",
            "country",
            "city",
            "category",
            "fonction",
            "organiz",
            "accreditationType",
            "status",
            "attachement",
            "code",
            "dayPassInfo",
        },
        allowSetters = true
    )
    private Set<Accreditation> accreditations = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditation", "event" }, allowSetters = true)
    private Set<Note> notes = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "typeoperation", "event" }, allowSetters = true)
    private Set<OperationHistory> operationHistories = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "city", "country", "organiz", "printingType", "printingServer", "printingModel", "language", "event" },
        allowSetters = true
    )
    private Set<PrintingCentre> printingCentres = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "language", "event" }, allowSetters = true)
    private Set<Setting> settings = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "printingCentres", "event" }, allowSetters = true)
    private Set<PrintingServer> printingServers = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "checkAccreditationReports", "event", "accreditation" }, allowSetters = true)
    private Set<CheckAccreditationHistory> checkAccreditationHistories = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "event", "checkAccreditationHistory" }, allowSetters = true)
    private Set<CheckAccreditationReport> checkAccreditationReports = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditations", "printingModel", "event" }, allowSetters = true)
    private Set<AccreditationType> accreditationTypes = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditations", "event" }, allowSetters = true)
    private Set<DayPassInfo> dayPassInfos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "events", "settings", "printingCentres" }, allowSetters = true)
    private Language language;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getEventId() {
        return this.eventId;
    }

    public Event eventId(Long eventId) {
        this.setEventId(eventId);
        return this;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return this.eventName;
    }

    public Event eventName(String eventName) {
        this.setEventName(eventName);
        return this;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventColor() {
        return this.eventColor;
    }

    public Event eventColor(String eventColor) {
        this.setEventColor(eventColor);
        return this;
    }

    public void setEventColor(String eventColor) {
        this.eventColor = eventColor;
    }

    public String getEventDescription() {
        return this.eventDescription;
    }

    public Event eventDescription(String eventDescription) {
        this.setEventDescription(eventDescription);
        return this;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventAbreviation() {
        return this.eventAbreviation;
    }

    public Event eventAbreviation(String eventAbreviation) {
        this.setEventAbreviation(eventAbreviation);
        return this;
    }

    public void setEventAbreviation(String eventAbreviation) {
        this.eventAbreviation = eventAbreviation;
    }

    public ZonedDateTime getEventdateStart() {
        return this.eventdateStart;
    }

    public Event eventdateStart(ZonedDateTime eventdateStart) {
        this.setEventdateStart(eventdateStart);
        return this;
    }

    public void setEventdateStart(ZonedDateTime eventdateStart) {
        this.eventdateStart = eventdateStart;
    }

    public ZonedDateTime getEventdateEnd() {
        return this.eventdateEnd;
    }

    public Event eventdateEnd(ZonedDateTime eventdateEnd) {
        this.setEventdateEnd(eventdateEnd);
        return this;
    }

    public void setEventdateEnd(ZonedDateTime eventdateEnd) {
        this.eventdateEnd = eventdateEnd;
    }

    public Long getEventPrintingModelId() {
        return this.eventPrintingModelId;
    }

    public Event eventPrintingModelId(Long eventPrintingModelId) {
        this.setEventPrintingModelId(eventPrintingModelId);
        return this;
    }

    public void setEventPrintingModelId(Long eventPrintingModelId) {
        this.eventPrintingModelId = eventPrintingModelId;
    }

    public byte[] getEventLogo() {
        return this.eventLogo;
    }

    public Event eventLogo(byte[] eventLogo) {
        this.setEventLogo(eventLogo);
        return this;
    }

    public void setEventLogo(byte[] eventLogo) {
        this.eventLogo = eventLogo;
    }

    public String getEventLogoContentType() {
        return this.eventLogoContentType;
    }

    public Event eventLogoContentType(String eventLogoContentType) {
        this.eventLogoContentType = eventLogoContentType;
        return this;
    }

    public void setEventLogoContentType(String eventLogoContentType) {
        this.eventLogoContentType = eventLogoContentType;
    }

    public byte[] getEventBannerCenter() {
        return this.eventBannerCenter;
    }

    public Event eventBannerCenter(byte[] eventBannerCenter) {
        this.setEventBannerCenter(eventBannerCenter);
        return this;
    }

    public void setEventBannerCenter(byte[] eventBannerCenter) {
        this.eventBannerCenter = eventBannerCenter;
    }

    public String getEventBannerCenterContentType() {
        return this.eventBannerCenterContentType;
    }

    public Event eventBannerCenterContentType(String eventBannerCenterContentType) {
        this.eventBannerCenterContentType = eventBannerCenterContentType;
        return this;
    }

    public void setEventBannerCenterContentType(String eventBannerCenterContentType) {
        this.eventBannerCenterContentType = eventBannerCenterContentType;
    }

    public byte[] getEventBannerRight() {
        return this.eventBannerRight;
    }

    public Event eventBannerRight(byte[] eventBannerRight) {
        this.setEventBannerRight(eventBannerRight);
        return this;
    }

    public void setEventBannerRight(byte[] eventBannerRight) {
        this.eventBannerRight = eventBannerRight;
    }

    public String getEventBannerRightContentType() {
        return this.eventBannerRightContentType;
    }

    public Event eventBannerRightContentType(String eventBannerRightContentType) {
        this.eventBannerRightContentType = eventBannerRightContentType;
        return this;
    }

    public void setEventBannerRightContentType(String eventBannerRightContentType) {
        this.eventBannerRightContentType = eventBannerRightContentType;
    }

    public byte[] getEventBannerLeft() {
        return this.eventBannerLeft;
    }

    public Event eventBannerLeft(byte[] eventBannerLeft) {
        this.setEventBannerLeft(eventBannerLeft);
        return this;
    }

    public void setEventBannerLeft(byte[] eventBannerLeft) {
        this.eventBannerLeft = eventBannerLeft;
    }

    public String getEventBannerLeftContentType() {
        return this.eventBannerLeftContentType;
    }

    public Event eventBannerLeftContentType(String eventBannerLeftContentType) {
        this.eventBannerLeftContentType = eventBannerLeftContentType;
        return this;
    }

    public void setEventBannerLeftContentType(String eventBannerLeftContentType) {
        this.eventBannerLeftContentType = eventBannerLeftContentType;
    }

    public byte[] getEventBannerBas() {
        return this.eventBannerBas;
    }

    public Event eventBannerBas(byte[] eventBannerBas) {
        this.setEventBannerBas(eventBannerBas);
        return this;
    }

    public void setEventBannerBas(byte[] eventBannerBas) {
        this.eventBannerBas = eventBannerBas;
    }

    public String getEventBannerBasContentType() {
        return this.eventBannerBasContentType;
    }

    public Event eventBannerBasContentType(String eventBannerBasContentType) {
        this.eventBannerBasContentType = eventBannerBasContentType;
        return this;
    }

    public void setEventBannerBasContentType(String eventBannerBasContentType) {
        this.eventBannerBasContentType = eventBannerBasContentType;
    }

    public Boolean getEventWithPhoto() {
        return this.eventWithPhoto;
    }

    public Event eventWithPhoto(Boolean eventWithPhoto) {
        this.setEventWithPhoto(eventWithPhoto);
        return this;
    }

    public void setEventWithPhoto(Boolean eventWithPhoto) {
        this.eventWithPhoto = eventWithPhoto;
    }

    public Boolean getEventNoCode() {
        return this.eventNoCode;
    }

    public Event eventNoCode(Boolean eventNoCode) {
        this.setEventNoCode(eventNoCode);
        return this;
    }

    public void setEventNoCode(Boolean eventNoCode) {
        this.eventNoCode = eventNoCode;
    }

    public Boolean getEventCodeNoFilter() {
        return this.eventCodeNoFilter;
    }

    public Event eventCodeNoFilter(Boolean eventCodeNoFilter) {
        this.setEventCodeNoFilter(eventCodeNoFilter);
        return this;
    }

    public void setEventCodeNoFilter(Boolean eventCodeNoFilter) {
        this.eventCodeNoFilter = eventCodeNoFilter;
    }

    public Boolean getEventCodeByTypeAccreditation() {
        return this.eventCodeByTypeAccreditation;
    }

    public Event eventCodeByTypeAccreditation(Boolean eventCodeByTypeAccreditation) {
        this.setEventCodeByTypeAccreditation(eventCodeByTypeAccreditation);
        return this;
    }

    public void setEventCodeByTypeAccreditation(Boolean eventCodeByTypeAccreditation) {
        this.eventCodeByTypeAccreditation = eventCodeByTypeAccreditation;
    }

    public Boolean getEventCodeByTypeCategorie() {
        return this.eventCodeByTypeCategorie;
    }

    public Event eventCodeByTypeCategorie(Boolean eventCodeByTypeCategorie) {
        this.setEventCodeByTypeCategorie(eventCodeByTypeCategorie);
        return this;
    }

    public void setEventCodeByTypeCategorie(Boolean eventCodeByTypeCategorie) {
        this.eventCodeByTypeCategorie = eventCodeByTypeCategorie;
    }

    public Boolean getEventCodeByTypeFonction() {
        return this.eventCodeByTypeFonction;
    }

    public Event eventCodeByTypeFonction(Boolean eventCodeByTypeFonction) {
        this.setEventCodeByTypeFonction(eventCodeByTypeFonction);
        return this;
    }

    public void setEventCodeByTypeFonction(Boolean eventCodeByTypeFonction) {
        this.eventCodeByTypeFonction = eventCodeByTypeFonction;
    }

    public Boolean getEventCodeByTypeOrganiz() {
        return this.eventCodeByTypeOrganiz;
    }

    public Event eventCodeByTypeOrganiz(Boolean eventCodeByTypeOrganiz) {
        this.setEventCodeByTypeOrganiz(eventCodeByTypeOrganiz);
        return this;
    }

    public void setEventCodeByTypeOrganiz(Boolean eventCodeByTypeOrganiz) {
        this.eventCodeByTypeOrganiz = eventCodeByTypeOrganiz;
    }

    public Boolean getEventDefaultPrintingLanguage() {
        return this.eventDefaultPrintingLanguage;
    }

    public Event eventDefaultPrintingLanguage(Boolean eventDefaultPrintingLanguage) {
        this.setEventDefaultPrintingLanguage(eventDefaultPrintingLanguage);
        return this;
    }

    public void setEventDefaultPrintingLanguage(Boolean eventDefaultPrintingLanguage) {
        this.eventDefaultPrintingLanguage = eventDefaultPrintingLanguage;
    }

    public Boolean getEventPCenterPrintingLanguage() {
        return this.eventPCenterPrintingLanguage;
    }

    public Event eventPCenterPrintingLanguage(Boolean eventPCenterPrintingLanguage) {
        this.setEventPCenterPrintingLanguage(eventPCenterPrintingLanguage);
        return this;
    }

    public void setEventPCenterPrintingLanguage(Boolean eventPCenterPrintingLanguage) {
        this.eventPCenterPrintingLanguage = eventPCenterPrintingLanguage;
    }

    public Boolean getEventImported() {
        return this.eventImported;
    }

    public Event eventImported(Boolean eventImported) {
        this.setEventImported(eventImported);
        return this;
    }

    public void setEventImported(Boolean eventImported) {
        this.eventImported = eventImported;
    }

    public Boolean getEventArchived() {
        return this.eventArchived;
    }

    public Event eventArchived(Boolean eventArchived) {
        this.setEventArchived(eventArchived);
        return this;
    }

    public void setEventArchived(Boolean eventArchived) {
        this.eventArchived = eventArchived;
    }

    public String getEventArchiveFileName() {
        return this.eventArchiveFileName;
    }

    public Event eventArchiveFileName(String eventArchiveFileName) {
        this.setEventArchiveFileName(eventArchiveFileName);
        return this;
    }

    public void setEventArchiveFileName(String eventArchiveFileName) {
        this.eventArchiveFileName = eventArchiveFileName;
    }

    public String getEventURL() {
        return this.eventURL;
    }

    public Event eventURL(String eventURL) {
        this.setEventURL(eventURL);
        return this;
    }

    public void setEventURL(String eventURL) {
        this.eventURL = eventURL;
    }

    public String getEventDomaine() {
        return this.eventDomaine;
    }

    public Event eventDomaine(String eventDomaine) {
        this.setEventDomaine(eventDomaine);
        return this;
    }

    public void setEventDomaine(String eventDomaine) {
        this.eventDomaine = eventDomaine;
    }

    public String getEventSousDomaine() {
        return this.eventSousDomaine;
    }

    public Event eventSousDomaine(String eventSousDomaine) {
        this.setEventSousDomaine(eventSousDomaine);
        return this;
    }

    public void setEventSousDomaine(String eventSousDomaine) {
        this.eventSousDomaine = eventSousDomaine;
    }

    public Boolean getEventCloned() {
        return this.eventCloned;
    }

    public Event eventCloned(Boolean eventCloned) {
        this.setEventCloned(eventCloned);
        return this;
    }

    public void setEventCloned(Boolean eventCloned) {
        this.eventCloned = eventCloned;
    }

    public String getEventParams() {
        return this.eventParams;
    }

    public Event eventParams(String eventParams) {
        this.setEventParams(eventParams);
        return this;
    }

    public void setEventParams(String eventParams) {
        this.eventParams = eventParams;
    }

    public String getEventAttributs() {
        return this.eventAttributs;
    }

    public Event eventAttributs(String eventAttributs) {
        this.setEventAttributs(eventAttributs);
        return this;
    }

    public void setEventAttributs(String eventAttributs) {
        this.eventAttributs = eventAttributs;
    }

    public Boolean getEventStat() {
        return this.eventStat;
    }

    public Event eventStat(Boolean eventStat) {
        this.setEventStat(eventStat);
        return this;
    }

    public void setEventStat(Boolean eventStat) {
        this.eventStat = eventStat;
    }

    public Set<EventForm> getEventForms() {
        return this.eventForms;
    }

    public void setEventForms(Set<EventForm> eventForms) {
        if (this.eventForms != null) {
            this.eventForms.forEach(i -> i.setEvent(null));
        }
        if (eventForms != null) {
            eventForms.forEach(i -> i.setEvent(this));
        }
        this.eventForms = eventForms;
    }

    public Event eventForms(Set<EventForm> eventForms) {
        this.setEventForms(eventForms);
        return this;
    }

    public Event addEventForm(EventForm eventForm) {
        this.eventForms.add(eventForm);
        eventForm.setEvent(this);
        return this;
    }

    public Event removeEventForm(EventForm eventForm) {
        this.eventForms.remove(eventForm);
        eventForm.setEvent(null);
        return this;
    }

    public Set<EventField> getEventFields() {
        return this.eventFields;
    }

    public void setEventFields(Set<EventField> eventFields) {
        if (this.eventFields != null) {
            this.eventFields.forEach(i -> i.setEvent(null));
        }
        if (eventFields != null) {
            eventFields.forEach(i -> i.setEvent(this));
        }
        this.eventFields = eventFields;
    }

    public Event eventFields(Set<EventField> eventFields) {
        this.setEventFields(eventFields);
        return this;
    }

    public Event addEventField(EventField eventField) {
        this.eventFields.add(eventField);
        eventField.setEvent(this);
        return this;
    }

    public Event removeEventField(EventField eventField) {
        this.eventFields.remove(eventField);
        eventField.setEvent(null);
        return this;
    }

    public Set<EventControl> getEventControls() {
        return this.eventControls;
    }

    public void setEventControls(Set<EventControl> eventControls) {
        if (this.eventControls != null) {
            this.eventControls.forEach(i -> i.setEvent(null));
        }
        if (eventControls != null) {
            eventControls.forEach(i -> i.setEvent(this));
        }
        this.eventControls = eventControls;
    }

    public Event eventControls(Set<EventControl> eventControls) {
        this.setEventControls(eventControls);
        return this;
    }

    public Event addEventControl(EventControl eventControl) {
        this.eventControls.add(eventControl);
        eventControl.setEvent(this);
        return this;
    }

    public Event removeEventControl(EventControl eventControl) {
        this.eventControls.remove(eventControl);
        eventControl.setEvent(null);
        return this;
    }

    public Set<Area> getAreas() {
        return this.areas;
    }

    public void setAreas(Set<Area> areas) {
        if (this.areas != null) {
            this.areas.forEach(i -> i.setEvent(null));
        }
        if (areas != null) {
            areas.forEach(i -> i.setEvent(this));
        }
        this.areas = areas;
    }

    public Event areas(Set<Area> areas) {
        this.setAreas(areas);
        return this;
    }

    public Event addArea(Area area) {
        this.areas.add(area);
        area.setEvent(this);
        return this;
    }

    public Event removeArea(Area area) {
        this.areas.remove(area);
        area.setEvent(null);
        return this;
    }

    public Set<Fonction> getFonctions() {
        return this.fonctions;
    }

    public void setFonctions(Set<Fonction> fonctions) {
        if (this.fonctions != null) {
            this.fonctions.forEach(i -> i.setEvent(null));
        }
        if (fonctions != null) {
            fonctions.forEach(i -> i.setEvent(this));
        }
        this.fonctions = fonctions;
    }

    public Event fonctions(Set<Fonction> fonctions) {
        this.setFonctions(fonctions);
        return this;
    }

    public Event addFonction(Fonction fonction) {
        this.fonctions.add(fonction);
        fonction.setEvent(this);
        return this;
    }

    public Event removeFonction(Fonction fonction) {
        this.fonctions.remove(fonction);
        fonction.setEvent(null);
        return this;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        if (this.categories != null) {
            this.categories.forEach(i -> i.setEvent(null));
        }
        if (categories != null) {
            categories.forEach(i -> i.setEvent(this));
        }
        this.categories = categories;
    }

    public Event categories(Set<Category> categories) {
        this.setCategories(categories);
        return this;
    }

    public Event addCategory(Category category) {
        this.categories.add(category);
        category.setEvent(this);
        return this;
    }

    public Event removeCategory(Category category) {
        this.categories.remove(category);
        category.setEvent(null);
        return this;
    }

    public Set<PrintingModel> getPrintingModels() {
        return this.printingModels;
    }

    public void setPrintingModels(Set<PrintingModel> printingModels) {
        if (this.printingModels != null) {
            this.printingModels.forEach(i -> i.setEvent(null));
        }
        if (printingModels != null) {
            printingModels.forEach(i -> i.setEvent(this));
        }
        this.printingModels = printingModels;
    }

    public Event printingModels(Set<PrintingModel> printingModels) {
        this.setPrintingModels(printingModels);
        return this;
    }

    public Event addPrintingModel(PrintingModel printingModel) {
        this.printingModels.add(printingModel);
        printingModel.setEvent(this);
        return this;
    }

    public Event removePrintingModel(PrintingModel printingModel) {
        this.printingModels.remove(printingModel);
        printingModel.setEvent(null);
        return this;
    }

    public Set<Code> getCodes() {
        return this.codes;
    }

    public void setCodes(Set<Code> codes) {
        if (this.codes != null) {
            this.codes.forEach(i -> i.setEvent(null));
        }
        if (codes != null) {
            codes.forEach(i -> i.setEvent(this));
        }
        this.codes = codes;
    }

    public Event codes(Set<Code> codes) {
        this.setCodes(codes);
        return this;
    }

    public Event addCode(Code code) {
        this.codes.add(code);
        code.setEvent(this);
        return this;
    }

    public Event removeCode(Code code) {
        this.codes.remove(code);
        code.setEvent(null);
        return this;
    }

    public Set<InfoSupp> getInfoSupps() {
        return this.infoSupps;
    }

    public void setInfoSupps(Set<InfoSupp> infoSupps) {
        if (this.infoSupps != null) {
            this.infoSupps.forEach(i -> i.setEvent(null));
        }
        if (infoSupps != null) {
            infoSupps.forEach(i -> i.setEvent(this));
        }
        this.infoSupps = infoSupps;
    }

    public Event infoSupps(Set<InfoSupp> infoSupps) {
        this.setInfoSupps(infoSupps);
        return this;
    }

    public Event addInfoSupp(InfoSupp infoSupp) {
        this.infoSupps.add(infoSupp);
        infoSupp.setEvent(this);
        return this;
    }

    public Event removeInfoSupp(InfoSupp infoSupp) {
        this.infoSupps.remove(infoSupp);
        infoSupp.setEvent(null);
        return this;
    }

    public Set<Attachement> getAttachements() {
        return this.attachements;
    }

    public void setAttachements(Set<Attachement> attachements) {
        if (this.attachements != null) {
            this.attachements.forEach(i -> i.setEvent(null));
        }
        if (attachements != null) {
            attachements.forEach(i -> i.setEvent(this));
        }
        this.attachements = attachements;
    }

    public Event attachements(Set<Attachement> attachements) {
        this.setAttachements(attachements);
        return this;
    }

    public Event addAttachement(Attachement attachement) {
        this.attachements.add(attachement);
        attachement.setEvent(this);
        return this;
    }

    public Event removeAttachement(Attachement attachement) {
        this.attachements.remove(attachement);
        attachement.setEvent(null);
        return this;
    }

    public Set<Organiz> getOrganizs() {
        return this.organizs;
    }

    public void setOrganizs(Set<Organiz> organizs) {
        if (this.organizs != null) {
            this.organizs.forEach(i -> i.setEvent(null));
        }
        if (organizs != null) {
            organizs.forEach(i -> i.setEvent(this));
        }
        this.organizs = organizs;
    }

    public Event organizs(Set<Organiz> organizs) {
        this.setOrganizs(organizs);
        return this;
    }

    public Event addOrganiz(Organiz organiz) {
        this.organizs.add(organiz);
        organiz.setEvent(this);
        return this;
    }

    public Event removeOrganiz(Organiz organiz) {
        this.organizs.remove(organiz);
        organiz.setEvent(null);
        return this;
    }

    public Set<PhotoArchive> getPhotoArchives() {
        return this.photoArchives;
    }

    public void setPhotoArchives(Set<PhotoArchive> photoArchives) {
        if (this.photoArchives != null) {
            this.photoArchives.forEach(i -> i.setEvent(null));
        }
        if (photoArchives != null) {
            photoArchives.forEach(i -> i.setEvent(this));
        }
        this.photoArchives = photoArchives;
    }

    public Event photoArchives(Set<PhotoArchive> photoArchives) {
        this.setPhotoArchives(photoArchives);
        return this;
    }

    public Event addPhotoArchive(PhotoArchive photoArchive) {
        this.photoArchives.add(photoArchive);
        photoArchive.setEvent(this);
        return this;
    }

    public Event removePhotoArchive(PhotoArchive photoArchive) {
        this.photoArchives.remove(photoArchive);
        photoArchive.setEvent(null);
        return this;
    }

    public Set<Site> getSites() {
        return this.sites;
    }

    public void setSites(Set<Site> sites) {
        if (this.sites != null) {
            this.sites.forEach(i -> i.setEvent(null));
        }
        if (sites != null) {
            sites.forEach(i -> i.setEvent(this));
        }
        this.sites = sites;
    }

    public Event sites(Set<Site> sites) {
        this.setSites(sites);
        return this;
    }

    public Event addSite(Site site) {
        this.sites.add(site);
        site.setEvent(this);
        return this;
    }

    public Event removeSite(Site site) {
        this.sites.remove(site);
        site.setEvent(null);
        return this;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setEvent(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setEvent(this));
        }
        this.accreditations = accreditations;
    }

    public Event accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public Event addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setEvent(this);
        return this;
    }

    public Event removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setEvent(null);
        return this;
    }

    public Set<Note> getNotes() {
        return this.notes;
    }

    public void setNotes(Set<Note> notes) {
        if (this.notes != null) {
            this.notes.forEach(i -> i.setEvent(null));
        }
        if (notes != null) {
            notes.forEach(i -> i.setEvent(this));
        }
        this.notes = notes;
    }

    public Event notes(Set<Note> notes) {
        this.setNotes(notes);
        return this;
    }

    public Event addNote(Note note) {
        this.notes.add(note);
        note.setEvent(this);
        return this;
    }

    public Event removeNote(Note note) {
        this.notes.remove(note);
        note.setEvent(null);
        return this;
    }

    public Set<OperationHistory> getOperationHistories() {
        return this.operationHistories;
    }

    public void setOperationHistories(Set<OperationHistory> operationHistories) {
        if (this.operationHistories != null) {
            this.operationHistories.forEach(i -> i.setEvent(null));
        }
        if (operationHistories != null) {
            operationHistories.forEach(i -> i.setEvent(this));
        }
        this.operationHistories = operationHistories;
    }

    public Event operationHistories(Set<OperationHistory> operationHistories) {
        this.setOperationHistories(operationHistories);
        return this;
    }

    public Event addOperationHistory(OperationHistory operationHistory) {
        this.operationHistories.add(operationHistory);
        operationHistory.setEvent(this);
        return this;
    }

    public Event removeOperationHistory(OperationHistory operationHistory) {
        this.operationHistories.remove(operationHistory);
        operationHistory.setEvent(null);
        return this;
    }

    public Set<PrintingCentre> getPrintingCentres() {
        return this.printingCentres;
    }

    public void setPrintingCentres(Set<PrintingCentre> printingCentres) {
        if (this.printingCentres != null) {
            this.printingCentres.forEach(i -> i.setEvent(null));
        }
        if (printingCentres != null) {
            printingCentres.forEach(i -> i.setEvent(this));
        }
        this.printingCentres = printingCentres;
    }

    public Event printingCentres(Set<PrintingCentre> printingCentres) {
        this.setPrintingCentres(printingCentres);
        return this;
    }

    public Event addPrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.add(printingCentre);
        printingCentre.setEvent(this);
        return this;
    }

    public Event removePrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.remove(printingCentre);
        printingCentre.setEvent(null);
        return this;
    }

    public Set<Setting> getSettings() {
        return this.settings;
    }

    public void setSettings(Set<Setting> settings) {
        if (this.settings != null) {
            this.settings.forEach(i -> i.setEvent(null));
        }
        if (settings != null) {
            settings.forEach(i -> i.setEvent(this));
        }
        this.settings = settings;
    }

    public Event settings(Set<Setting> settings) {
        this.setSettings(settings);
        return this;
    }

    public Event addSetting(Setting setting) {
        this.settings.add(setting);
        setting.setEvent(this);
        return this;
    }

    public Event removeSetting(Setting setting) {
        this.settings.remove(setting);
        setting.setEvent(null);
        return this;
    }

    public Set<PrintingServer> getPrintingServers() {
        return this.printingServers;
    }

    public void setPrintingServers(Set<PrintingServer> printingServers) {
        if (this.printingServers != null) {
            this.printingServers.forEach(i -> i.setEvent(null));
        }
        if (printingServers != null) {
            printingServers.forEach(i -> i.setEvent(this));
        }
        this.printingServers = printingServers;
    }

    public Event printingServers(Set<PrintingServer> printingServers) {
        this.setPrintingServers(printingServers);
        return this;
    }

    public Event addPrintingServer(PrintingServer printingServer) {
        this.printingServers.add(printingServer);
        printingServer.setEvent(this);
        return this;
    }

    public Event removePrintingServer(PrintingServer printingServer) {
        this.printingServers.remove(printingServer);
        printingServer.setEvent(null);
        return this;
    }

    public Set<CheckAccreditationHistory> getCheckAccreditationHistories() {
        return this.checkAccreditationHistories;
    }

    public void setCheckAccreditationHistories(Set<CheckAccreditationHistory> checkAccreditationHistories) {
        if (this.checkAccreditationHistories != null) {
            this.checkAccreditationHistories.forEach(i -> i.setEvent(null));
        }
        if (checkAccreditationHistories != null) {
            checkAccreditationHistories.forEach(i -> i.setEvent(this));
        }
        this.checkAccreditationHistories = checkAccreditationHistories;
    }

    public Event checkAccreditationHistories(Set<CheckAccreditationHistory> checkAccreditationHistories) {
        this.setCheckAccreditationHistories(checkAccreditationHistories);
        return this;
    }

    public Event addCheckAccreditationHistory(CheckAccreditationHistory checkAccreditationHistory) {
        this.checkAccreditationHistories.add(checkAccreditationHistory);
        checkAccreditationHistory.setEvent(this);
        return this;
    }

    public Event removeCheckAccreditationHistory(CheckAccreditationHistory checkAccreditationHistory) {
        this.checkAccreditationHistories.remove(checkAccreditationHistory);
        checkAccreditationHistory.setEvent(null);
        return this;
    }

    public Set<CheckAccreditationReport> getCheckAccreditationReports() {
        return this.checkAccreditationReports;
    }

    public void setCheckAccreditationReports(Set<CheckAccreditationReport> checkAccreditationReports) {
        if (this.checkAccreditationReports != null) {
            this.checkAccreditationReports.forEach(i -> i.setEvent(null));
        }
        if (checkAccreditationReports != null) {
            checkAccreditationReports.forEach(i -> i.setEvent(this));
        }
        this.checkAccreditationReports = checkAccreditationReports;
    }

    public Event checkAccreditationReports(Set<CheckAccreditationReport> checkAccreditationReports) {
        this.setCheckAccreditationReports(checkAccreditationReports);
        return this;
    }

    public Event addCheckAccreditationReport(CheckAccreditationReport checkAccreditationReport) {
        this.checkAccreditationReports.add(checkAccreditationReport);
        checkAccreditationReport.setEvent(this);
        return this;
    }

    public Event removeCheckAccreditationReport(CheckAccreditationReport checkAccreditationReport) {
        this.checkAccreditationReports.remove(checkAccreditationReport);
        checkAccreditationReport.setEvent(null);
        return this;
    }

    public Set<AccreditationType> getAccreditationTypes() {
        return this.accreditationTypes;
    }

    public void setAccreditationTypes(Set<AccreditationType> accreditationTypes) {
        if (this.accreditationTypes != null) {
            this.accreditationTypes.forEach(i -> i.setEvent(null));
        }
        if (accreditationTypes != null) {
            accreditationTypes.forEach(i -> i.setEvent(this));
        }
        this.accreditationTypes = accreditationTypes;
    }

    public Event accreditationTypes(Set<AccreditationType> accreditationTypes) {
        this.setAccreditationTypes(accreditationTypes);
        return this;
    }

    public Event addAccreditationType(AccreditationType accreditationType) {
        this.accreditationTypes.add(accreditationType);
        accreditationType.setEvent(this);
        return this;
    }

    public Event removeAccreditationType(AccreditationType accreditationType) {
        this.accreditationTypes.remove(accreditationType);
        accreditationType.setEvent(null);
        return this;
    }

    public Set<DayPassInfo> getDayPassInfos() {
        return this.dayPassInfos;
    }

    public void setDayPassInfos(Set<DayPassInfo> dayPassInfos) {
        if (this.dayPassInfos != null) {
            this.dayPassInfos.forEach(i -> i.setEvent(null));
        }
        if (dayPassInfos != null) {
            dayPassInfos.forEach(i -> i.setEvent(this));
        }
        this.dayPassInfos = dayPassInfos;
    }

    public Event dayPassInfos(Set<DayPassInfo> dayPassInfos) {
        this.setDayPassInfos(dayPassInfos);
        return this;
    }

    public Event addDayPassInfo(DayPassInfo dayPassInfo) {
        this.dayPassInfos.add(dayPassInfo);
        dayPassInfo.setEvent(this);
        return this;
    }

    public Event removeDayPassInfo(DayPassInfo dayPassInfo) {
        this.dayPassInfos.remove(dayPassInfo);
        dayPassInfo.setEvent(null);
        return this;
    }

    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Event language(Language language) {
        this.setLanguage(language);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return eventId != null && eventId.equals(((Event) o).eventId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "eventId=" + getEventId() +
            ", eventName='" + getEventName() + "'" +
            ", eventColor='" + getEventColor() + "'" +
            ", eventDescription='" + getEventDescription() + "'" +
            ", eventAbreviation='" + getEventAbreviation() + "'" +
            ", eventdateStart='" + getEventdateStart() + "'" +
            ", eventdateEnd='" + getEventdateEnd() + "'" +
            ", eventPrintingModelId=" + getEventPrintingModelId() +
            ", eventLogo='" + getEventLogo() + "'" +
            ", eventLogoContentType='" + getEventLogoContentType() + "'" +
            ", eventBannerCenter='" + getEventBannerCenter() + "'" +
            ", eventBannerCenterContentType='" + getEventBannerCenterContentType() + "'" +
            ", eventBannerRight='" + getEventBannerRight() + "'" +
            ", eventBannerRightContentType='" + getEventBannerRightContentType() + "'" +
            ", eventBannerLeft='" + getEventBannerLeft() + "'" +
            ", eventBannerLeftContentType='" + getEventBannerLeftContentType() + "'" +
            ", eventBannerBas='" + getEventBannerBas() + "'" +
            ", eventBannerBasContentType='" + getEventBannerBasContentType() + "'" +
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
            "}";
    }
}

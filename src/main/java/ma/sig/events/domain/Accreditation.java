package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Accreditation.
 */
@Entity
@Table(name = "accreditation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Accreditation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accreditation_id")
    private Long accreditationId;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "accreditation_first_name", length = 50, nullable = false)
    private String accreditationFirstName;

    @Size(max = 50)
    @Column(name = "accreditation_second_name", length = 50)
    private String accreditationSecondName;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "accreditation_last_name", length = 50, nullable = false)
    private String accreditationLastName;

    @NotNull
    @Column(name = "accreditation_birth_day", nullable = false)
    private LocalDate accreditationBirthDay;

    @Column(name = "accreditation_sexe")
    private String accreditationSexe;

    @Column(name = "accreditation_occupation")
    private String accreditationOccupation;

    @Column(name = "accreditation_description")
    private String accreditationDescription;

    @Column(name = "accreditation_email")
    private String accreditationEmail;

    @Column(name = "accreditation_tel")
    private String accreditationTel;

    @Lob
    @Column(name = "accreditation_photo")
    private byte[] accreditationPhoto;

    @Column(name = "accreditation_photo_content_type")
    private String accreditationPhotoContentType;

    @Column(name = "accreditation_cin_id")
    private String accreditationCinId;

    @Column(name = "accreditation_passeport_id")
    private String accreditationPasseportId;

    @Column(name = "accreditation_carte_presse_id")
    private String accreditationCartePresseId;

    @Column(name = "accreditation_carte_professionnelle_id")
    private String accreditationCarteProfessionnelleId;

    @Column(name = "accreditation_creation_date")
    private ZonedDateTime accreditationCreationDate;

    @Column(name = "accreditation_update_date")
    private ZonedDateTime accreditationUpdateDate;

    @Column(name = "accreditation_created_byuser")
    private String accreditationCreatedByuser;

    @Column(name = "accreditation_updated_byuser")
    private String accreditationUpdatedByuser;

    @Column(name = "accreditation_printed_byuser")
    private String accreditationPrintedByuser;

    @Column(name = "accreditation_print_date")
    private ZonedDateTime accreditationPrintDate;

    @Column(name = "accreditation_date_start")
    private ZonedDateTime accreditationDateStart;

    @Column(name = "accreditation_date_end")
    private ZonedDateTime accreditationDateEnd;

    @Column(name = "accreditation_print_stat")
    private Boolean accreditationPrintStat;

    @Column(name = "accreditation_print_number")
    private Long accreditationPrintNumber;

    @Column(name = "accreditation_params")
    private String accreditationParams;

    @Column(name = "accreditation_attributs")
    private String accreditationAttributs;

    @Column(name = "accreditation_stat")
    private Boolean accreditationStat;

    @Column(name = "accreditation_activated")
    private Boolean accreditationActivated;

    @OneToMany(mappedBy = "accreditation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditation", "event" }, allowSetters = true)
    private Set<PhotoArchive> photoArchives = new HashSet<>();

    @OneToMany(mappedBy = "accreditation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "infoSuppType", "accreditation", "event" }, allowSetters = true)
    private Set<InfoSupp> infoSupps = new HashSet<>();

    @OneToMany(mappedBy = "accreditation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditation", "event" }, allowSetters = true)
    private Set<Note> notes = new HashSet<>();

    @OneToMany(mappedBy = "accreditation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "checkAccreditationReports", "event", "accreditation" }, allowSetters = true)
    private Set<CheckAccreditationHistory> checkAccreditationHistories = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_accreditation__site",
        joinColumns = @JoinColumn(name = "accreditation_accreditation_id"),
        inverseJoinColumns = @JoinColumn(name = "site_site_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "city", "event", "accreditations" }, allowSetters = true)
    private Set<Site> sites = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "eventForms",
            "eventFields",
            "eventControls",
            "areas",
            "fonctions",
            "categories",
            "printingModels",
            "codes",
            "infoSupps",
            "attachements",
            "organizs",
            "photoArchives",
            "sites",
            "accreditations",
            "notes",
            "operationHistories",
            "printingCentres",
            "settings",
            "printingServers",
            "checkAccreditationHistories",
            "checkAccreditationReports",
            "accreditationTypes",
            "dayPassInfos",
            "language",
        },
        allowSetters = true
    )
    private Event event;

    @ManyToOne
    @JsonIgnoreProperties(value = { "accreditations" }, allowSetters = true)
    private Civility civility;

    @ManyToOne
    @JsonIgnoreProperties(value = { "accreditations" }, allowSetters = true)
    private Sexe sexe;

    @ManyToOne
    @JsonIgnoreProperties(value = { "accreditations" }, allowSetters = true)
    private Nationality nationality;

    @ManyToOne
    @JsonIgnoreProperties(value = { "printingCentres", "cities", "organizs", "accreditations" }, allowSetters = true)
    private Country country;

    @ManyToOne
    @JsonIgnoreProperties(value = { "printingCentres", "sites", "organizs", "accreditations", "country" }, allowSetters = true)
    private City city;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fonctions", "accreditations", "printingModel", "event" }, allowSetters = true)
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties(value = { "accreditations", "areas", "category", "event" }, allowSetters = true)
    private Fonction fonction;

    @ManyToOne
    @JsonIgnoreProperties(value = { "printingCentres", "accreditations", "country", "city", "event" }, allowSetters = true)
    private Organiz organiz;

    @ManyToOne
    @JsonIgnoreProperties(value = { "accreditations", "printingModel", "event" }, allowSetters = true)
    private AccreditationType accreditationType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "accreditations" }, allowSetters = true)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "accreditations", "attachementType", "event" }, allowSetters = true)
    private Attachement attachement;

    @ManyToOne
    @JsonIgnoreProperties(value = { "accreditations", "codeType", "event" }, allowSetters = true)
    private Code code;

    @ManyToOne
    @JsonIgnoreProperties(value = { "accreditations", "event" }, allowSetters = true)
    private DayPassInfo dayPassInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getAccreditationId() {
        return this.accreditationId;
    }

    public Accreditation accreditationId(Long accreditationId) {
        this.setAccreditationId(accreditationId);
        return this;
    }

    public void setAccreditationId(Long accreditationId) {
        this.accreditationId = accreditationId;
    }

    public String getAccreditationFirstName() {
        return this.accreditationFirstName;
    }

    public Accreditation accreditationFirstName(String accreditationFirstName) {
        this.setAccreditationFirstName(accreditationFirstName);
        return this;
    }

    public void setAccreditationFirstName(String accreditationFirstName) {
        this.accreditationFirstName = accreditationFirstName;
    }

    public String getAccreditationSecondName() {
        return this.accreditationSecondName;
    }

    public Accreditation accreditationSecondName(String accreditationSecondName) {
        this.setAccreditationSecondName(accreditationSecondName);
        return this;
    }

    public void setAccreditationSecondName(String accreditationSecondName) {
        this.accreditationSecondName = accreditationSecondName;
    }

    public String getAccreditationLastName() {
        return this.accreditationLastName;
    }

    public Accreditation accreditationLastName(String accreditationLastName) {
        this.setAccreditationLastName(accreditationLastName);
        return this;
    }

    public void setAccreditationLastName(String accreditationLastName) {
        this.accreditationLastName = accreditationLastName;
    }

    public LocalDate getAccreditationBirthDay() {
        return this.accreditationBirthDay;
    }

    public Accreditation accreditationBirthDay(LocalDate accreditationBirthDay) {
        this.setAccreditationBirthDay(accreditationBirthDay);
        return this;
    }

    public void setAccreditationBirthDay(LocalDate accreditationBirthDay) {
        this.accreditationBirthDay = accreditationBirthDay;
    }

    public String getAccreditationSexe() {
        return this.accreditationSexe;
    }

    public Accreditation accreditationSexe(String accreditationSexe) {
        this.setAccreditationSexe(accreditationSexe);
        return this;
    }

    public void setAccreditationSexe(String accreditationSexe) {
        this.accreditationSexe = accreditationSexe;
    }

    public String getAccreditationOccupation() {
        return this.accreditationOccupation;
    }

    public Accreditation accreditationOccupation(String accreditationOccupation) {
        this.setAccreditationOccupation(accreditationOccupation);
        return this;
    }

    public void setAccreditationOccupation(String accreditationOccupation) {
        this.accreditationOccupation = accreditationOccupation;
    }

    public String getAccreditationDescription() {
        return this.accreditationDescription;
    }

    public Accreditation accreditationDescription(String accreditationDescription) {
        this.setAccreditationDescription(accreditationDescription);
        return this;
    }

    public void setAccreditationDescription(String accreditationDescription) {
        this.accreditationDescription = accreditationDescription;
    }

    public String getAccreditationEmail() {
        return this.accreditationEmail;
    }

    public Accreditation accreditationEmail(String accreditationEmail) {
        this.setAccreditationEmail(accreditationEmail);
        return this;
    }

    public void setAccreditationEmail(String accreditationEmail) {
        this.accreditationEmail = accreditationEmail;
    }

    public String getAccreditationTel() {
        return this.accreditationTel;
    }

    public Accreditation accreditationTel(String accreditationTel) {
        this.setAccreditationTel(accreditationTel);
        return this;
    }

    public void setAccreditationTel(String accreditationTel) {
        this.accreditationTel = accreditationTel;
    }

    public byte[] getAccreditationPhoto() {
        return this.accreditationPhoto;
    }

    public Accreditation accreditationPhoto(byte[] accreditationPhoto) {
        this.setAccreditationPhoto(accreditationPhoto);
        return this;
    }

    public void setAccreditationPhoto(byte[] accreditationPhoto) {
        this.accreditationPhoto = accreditationPhoto;
    }

    public String getAccreditationPhotoContentType() {
        return this.accreditationPhotoContentType;
    }

    public Accreditation accreditationPhotoContentType(String accreditationPhotoContentType) {
        this.accreditationPhotoContentType = accreditationPhotoContentType;
        return this;
    }

    public void setAccreditationPhotoContentType(String accreditationPhotoContentType) {
        this.accreditationPhotoContentType = accreditationPhotoContentType;
    }

    public String getAccreditationCinId() {
        return this.accreditationCinId;
    }

    public Accreditation accreditationCinId(String accreditationCinId) {
        this.setAccreditationCinId(accreditationCinId);
        return this;
    }

    public void setAccreditationCinId(String accreditationCinId) {
        this.accreditationCinId = accreditationCinId;
    }

    public String getAccreditationPasseportId() {
        return this.accreditationPasseportId;
    }

    public Accreditation accreditationPasseportId(String accreditationPasseportId) {
        this.setAccreditationPasseportId(accreditationPasseportId);
        return this;
    }

    public void setAccreditationPasseportId(String accreditationPasseportId) {
        this.accreditationPasseportId = accreditationPasseportId;
    }

    public String getAccreditationCartePresseId() {
        return this.accreditationCartePresseId;
    }

    public Accreditation accreditationCartePresseId(String accreditationCartePresseId) {
        this.setAccreditationCartePresseId(accreditationCartePresseId);
        return this;
    }

    public void setAccreditationCartePresseId(String accreditationCartePresseId) {
        this.accreditationCartePresseId = accreditationCartePresseId;
    }

    public String getAccreditationCarteProfessionnelleId() {
        return this.accreditationCarteProfessionnelleId;
    }

    public Accreditation accreditationCarteProfessionnelleId(String accreditationCarteProfessionnelleId) {
        this.setAccreditationCarteProfessionnelleId(accreditationCarteProfessionnelleId);
        return this;
    }

    public void setAccreditationCarteProfessionnelleId(String accreditationCarteProfessionnelleId) {
        this.accreditationCarteProfessionnelleId = accreditationCarteProfessionnelleId;
    }

    public ZonedDateTime getAccreditationCreationDate() {
        return this.accreditationCreationDate;
    }

    public Accreditation accreditationCreationDate(ZonedDateTime accreditationCreationDate) {
        this.setAccreditationCreationDate(accreditationCreationDate);
        return this;
    }

    public void setAccreditationCreationDate(ZonedDateTime accreditationCreationDate) {
        this.accreditationCreationDate = accreditationCreationDate;
    }

    public ZonedDateTime getAccreditationUpdateDate() {
        return this.accreditationUpdateDate;
    }

    public Accreditation accreditationUpdateDate(ZonedDateTime accreditationUpdateDate) {
        this.setAccreditationUpdateDate(accreditationUpdateDate);
        return this;
    }

    public void setAccreditationUpdateDate(ZonedDateTime accreditationUpdateDate) {
        this.accreditationUpdateDate = accreditationUpdateDate;
    }

    public String getAccreditationCreatedByuser() {
        return this.accreditationCreatedByuser;
    }

    public Accreditation accreditationCreatedByuser(String accreditationCreatedByuser) {
        this.setAccreditationCreatedByuser(accreditationCreatedByuser);
        return this;
    }

    public void setAccreditationCreatedByuser(String accreditationCreatedByuser) {
        this.accreditationCreatedByuser = accreditationCreatedByuser;
    }

    public String getAccreditationUpdatedByuser() {
        return accreditationUpdatedByuser;
    }

    public void setAccreditationUpdatedByuser(String accreditationUpdatedByuser) {
        this.accreditationUpdatedByuser = accreditationUpdatedByuser;
    }

    public String getAccreditationPrintedByuser() {
        return accreditationPrintedByuser;
    }

    public void setAccreditationPrintedByuser(String accreditationPrintedByuser) {
        this.accreditationPrintedByuser = accreditationPrintedByuser;
    }

    public ZonedDateTime getAccreditationPrintDate() {
        return accreditationPrintDate;
    }

    public void setAccreditationPrintDate(ZonedDateTime accreditationPrintDate) {
        this.accreditationPrintDate = accreditationPrintDate;
    }

    public Accreditation accreditationPrintedByuser(String accreditationPrintedByuser) {
        this.setAccreditationPrintedByuser(accreditationPrintedByuser);
        return this;
    }

    public Accreditation accreditationUpdatedByuser(String accreditationUpdatedByuser) {
        this.setAccreditationUpdatedByuser(accreditationUpdatedByuser);
        return this;
    }

    public Accreditation accreditationPrintDate(ZonedDateTime accreditationPrintDate) {
        this.setAccreditationPrintDate(accreditationPrintDate);
        return this;
    }

    public ZonedDateTime getAccreditationDateStart() {
        return this.accreditationDateStart;
    }

    public Accreditation accreditationDateStart(ZonedDateTime accreditationDateStart) {
        this.setAccreditationDateStart(accreditationDateStart);
        return this;
    }

    public void setAccreditationDateStart(ZonedDateTime accreditationDateStart) {
        this.accreditationDateStart = accreditationDateStart;
    }

    public ZonedDateTime getAccreditationDateEnd() {
        return this.accreditationDateEnd;
    }

    public Accreditation accreditationDateEnd(ZonedDateTime accreditationDateEnd) {
        this.setAccreditationDateEnd(accreditationDateEnd);
        return this;
    }

    public void setAccreditationDateEnd(ZonedDateTime accreditationDateEnd) {
        this.accreditationDateEnd = accreditationDateEnd;
    }

    public Boolean getAccreditationPrintStat() {
        return this.accreditationPrintStat;
    }

    public Accreditation accreditationPrintStat(Boolean accreditationPrintStat) {
        this.setAccreditationPrintStat(accreditationPrintStat);
        return this;
    }

    public void setAccreditationPrintStat(Boolean accreditationPrintStat) {
        this.accreditationPrintStat = accreditationPrintStat;
    }

    public Long getAccreditationPrintNumber() {
        return this.accreditationPrintNumber;
    }

    public Accreditation accreditationPrintNumber(Long accreditationPrintNumber) {
        this.setAccreditationPrintNumber(accreditationPrintNumber);
        return this;
    }

    public void setAccreditationPrintNumber(Long accreditationPrintNumber) {
        this.accreditationPrintNumber = accreditationPrintNumber;
    }

    public String getAccreditationParams() {
        return this.accreditationParams;
    }

    public Accreditation accreditationParams(String accreditationParams) {
        this.setAccreditationParams(accreditationParams);
        return this;
    }

    public void setAccreditationParams(String accreditationParams) {
        this.accreditationParams = accreditationParams;
    }

    public String getAccreditationAttributs() {
        return this.accreditationAttributs;
    }

    public Accreditation accreditationAttributs(String accreditationAttributs) {
        this.setAccreditationAttributs(accreditationAttributs);
        return this;
    }

    public void setAccreditationAttributs(String accreditationAttributs) {
        this.accreditationAttributs = accreditationAttributs;
    }

    public Boolean getAccreditationStat() {
        return this.accreditationStat;
    }

    public Accreditation accreditationStat(Boolean accreditationStat) {
        this.setAccreditationStat(accreditationStat);
        return this;
    }

    public void setAccreditationActivated(Boolean accreditationActivated) {
        this.accreditationActivated = accreditationActivated;
    }

    public Boolean getAccreditationActivated() {
        return this.accreditationActivated;
    }

    public Accreditation accreditationActivated(Boolean accreditationActivated) {
        this.setAccreditationActivated(accreditationActivated);
        return this;
    }

    public void setAccreditationStat(Boolean accreditationStat) {
        this.accreditationStat = accreditationStat;
    }

    public Set<PhotoArchive> getPhotoArchives() {
        return this.photoArchives;
    }

    public void setPhotoArchives(Set<PhotoArchive> photoArchives) {
        if (this.photoArchives != null) {
            this.photoArchives.forEach(i -> i.setAccreditation(null));
        }
        if (photoArchives != null) {
            photoArchives.forEach(i -> i.setAccreditation(this));
        }
        this.photoArchives = photoArchives;
    }

    public Accreditation photoArchives(Set<PhotoArchive> photoArchives) {
        this.setPhotoArchives(photoArchives);
        return this;
    }

    public Accreditation addPhotoArchive(PhotoArchive photoArchive) {
        this.photoArchives.add(photoArchive);
        photoArchive.setAccreditation(this);
        return this;
    }

    public Accreditation removePhotoArchive(PhotoArchive photoArchive) {
        this.photoArchives.remove(photoArchive);
        photoArchive.setAccreditation(null);
        return this;
    }

    public Set<InfoSupp> getInfoSupps() {
        return this.infoSupps;
    }

    public void setInfoSupps(Set<InfoSupp> infoSupps) {
        if (this.infoSupps != null) {
            this.infoSupps.forEach(i -> i.setAccreditation(null));
        }
        if (infoSupps != null) {
            infoSupps.forEach(i -> i.setAccreditation(this));
        }
        this.infoSupps = infoSupps;
    }

    public Accreditation infoSupps(Set<InfoSupp> infoSupps) {
        this.setInfoSupps(infoSupps);
        return this;
    }

    public Accreditation addInfoSupp(InfoSupp infoSupp) {
        this.infoSupps.add(infoSupp);
        infoSupp.setAccreditation(this);
        return this;
    }

    public Accreditation removeInfoSupp(InfoSupp infoSupp) {
        this.infoSupps.remove(infoSupp);
        infoSupp.setAccreditation(null);
        return this;
    }

    public Set<Note> getNotes() {
        return this.notes;
    }

    public void setNotes(Set<Note> notes) {
        if (this.notes != null) {
            this.notes.forEach(i -> i.setAccreditation(null));
        }
        if (notes != null) {
            notes.forEach(i -> i.setAccreditation(this));
        }
        this.notes = notes;
    }

    public Accreditation notes(Set<Note> notes) {
        this.setNotes(notes);
        return this;
    }

    public Accreditation addNote(Note note) {
        this.notes.add(note);
        note.setAccreditation(this);
        return this;
    }

    public Accreditation removeNote(Note note) {
        this.notes.remove(note);
        note.setAccreditation(null);
        return this;
    }

    public Set<CheckAccreditationHistory> getCheckAccreditationHistories() {
        return this.checkAccreditationHistories;
    }

    public void setCheckAccreditationHistories(Set<CheckAccreditationHistory> checkAccreditationHistories) {
        if (this.checkAccreditationHistories != null) {
            this.checkAccreditationHistories.forEach(i -> i.setAccreditation(null));
        }
        if (checkAccreditationHistories != null) {
            checkAccreditationHistories.forEach(i -> i.setAccreditation(this));
        }
        this.checkAccreditationHistories = checkAccreditationHistories;
    }

    public Accreditation checkAccreditationHistories(Set<CheckAccreditationHistory> checkAccreditationHistories) {
        this.setCheckAccreditationHistories(checkAccreditationHistories);
        return this;
    }

    public Accreditation addCheckAccreditationHistory(CheckAccreditationHistory checkAccreditationHistory) {
        this.checkAccreditationHistories.add(checkAccreditationHistory);
        checkAccreditationHistory.setAccreditation(this);
        return this;
    }

    public Accreditation removeCheckAccreditationHistory(CheckAccreditationHistory checkAccreditationHistory) {
        this.checkAccreditationHistories.remove(checkAccreditationHistory);
        checkAccreditationHistory.setAccreditation(null);
        return this;
    }

    public Set<Site> getSites() {
        return this.sites;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
    }

    public Accreditation sites(Set<Site> sites) {
        this.setSites(sites);
        return this;
    }

    public Accreditation addSite(Site site) {
        this.sites.add(site);
        site.getAccreditations().add(this);
        return this;
    }

    public Accreditation removeSite(Site site) {
        this.sites.remove(site);
        site.getAccreditations().remove(this);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Accreditation event(Event event) {
        this.setEvent(event);
        return this;
    }

    public Civility getCivility() {
        return this.civility;
    }

    public void setCivility(Civility civility) {
        this.civility = civility;
    }

    public Accreditation civility(Civility civility) {
        this.setCivility(civility);
        return this;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Accreditation sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public Nationality getNationality() {
        return this.nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Accreditation nationality(Nationality nationality) {
        this.setNationality(nationality);
        return this;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Accreditation country(Country country) {
        this.setCountry(country);
        return this;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Accreditation city(City city) {
        this.setCity(city);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Accreditation category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Fonction getFonction() {
        return this.fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public Accreditation fonction(Fonction fonction) {
        this.setFonction(fonction);
        return this;
    }

    public Organiz getOrganiz() {
        return this.organiz;
    }

    public void setOrganiz(Organiz organiz) {
        this.organiz = organiz;
    }

    public Accreditation organiz(Organiz organiz) {
        this.setOrganiz(organiz);
        return this;
    }

    public AccreditationType getAccreditationType() {
        return this.accreditationType;
    }

    public void setAccreditationType(AccreditationType accreditationType) {
        this.accreditationType = accreditationType;
    }

    public Accreditation accreditationType(AccreditationType accreditationType) {
        this.setAccreditationType(accreditationType);
        return this;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Accreditation status(Status status) {
        this.setStatus(status);
        return this;
    }

    public Attachement getAttachement() {
        return this.attachement;
    }

    public void setAttachement(Attachement attachement) {
        this.attachement = attachement;
    }

    public Accreditation attachement(Attachement attachement) {
        this.setAttachement(attachement);
        return this;
    }

    public Code getCode() {
        return this.code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public Accreditation code(Code code) {
        this.setCode(code);
        return this;
    }

    public DayPassInfo getDayPassInfo() {
        return this.dayPassInfo;
    }

    public void setDayPassInfo(DayPassInfo dayPassInfo) {
        this.dayPassInfo = dayPassInfo;
    }

    public Accreditation dayPassInfo(DayPassInfo dayPassInfo) {
        this.setDayPassInfo(dayPassInfo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accreditation)) {
            return false;
        }
        return accreditationId != null && accreditationId.equals(((Accreditation) o).accreditationId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Accreditation{" +
            "accreditationId=" + getAccreditationId() +
            ", accreditationFirstName='" + getAccreditationFirstName() + "'" +
            ", accreditationSecondName='" + getAccreditationSecondName() + "'" +
            ", accreditationLastName='" + getAccreditationLastName() + "'" +
            ", accreditationBirthDay='" + getAccreditationBirthDay() + "'" +
            ", accreditationSexe='" + getAccreditationSexe() + "'" +
            ", accreditationOccupation='" + getAccreditationOccupation() + "'" +
            ", accreditationDescription='" + getAccreditationDescription() + "'" +
            ", accreditationEmail='" + getAccreditationEmail() + "'" +
            ", accreditationTel='" + getAccreditationTel() + "'" +
            ", accreditationPhoto='" + getAccreditationPhoto() + "'" +
            ", accreditationPhotoContentType='" + getAccreditationPhotoContentType() + "'" +
            ", accreditationCinId='" + getAccreditationCinId() + "'" +
            ", accreditationPasseportId='" + getAccreditationPasseportId() + "'" +
            ", accreditationCartePresseId='" + getAccreditationCartePresseId() + "'" +
            ", accreditationCarteProfessionnelleId='" + getAccreditationCarteProfessionnelleId() + "'" +
            ", accreditationCreationDate='" + getAccreditationCreationDate() + "'" +
            ", accreditationUpdateDate='" + getAccreditationUpdateDate() + "'" +
            ", accreditationPrintDate='" + getAccreditationPrintDate() + "'" +
            ", accreditationCreatedByuser='" + getAccreditationCreatedByuser() + "'" +
            ", accreditationUpdatedByuser='" + getAccreditationUpdatedByuser() + "'" +
            ", accreditationPrintedByuser='" + getAccreditationPrintedByuser() + "'" +
            ", accreditationDateStart='" + getAccreditationDateStart() + "'" +
            ", accreditationDateEnd='" + getAccreditationDateEnd() + "'" +
            ", accreditationPrintStat='" + getAccreditationPrintStat() + "'" +
            ", accreditationPrintNumber=" + getAccreditationPrintNumber() +
            ", accreditationParams='" + getAccreditationParams() + "'" +
            ", accreditationAttributs='" + getAccreditationAttributs() + "'" +
            ", accreditationStat='" + getAccreditationStat() + "'" +
            ", accreditationActivated='" + getAccreditationActivated() + "'" +
            "}";
    }
}

package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PrintingCentre.
 */
@Entity
@Table(name = "printing_centre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrintingCentre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "printing_centre_id")
    private Long printingCentreId;

    @Size(max = 200)
    @Column(name = "printing_centre_description", length = 200)
    private String printingCentreDescription;

    @NotNull
    @Size(max = 50)
    @Column(name = "printing_centre_name", length = 50, nullable = false)
    private String printingCentreName;

    @Lob
    @Column(name = "printing_centre_logo")
    private byte[] printingCentreLogo;

    @Column(name = "printing_centre_logo_content_type")
    private String printingCentreLogoContentType;

    @Column(name = "printing_centre_adresse")
    private String printingCentreAdresse;

    @Column(name = "printing_centre_email")
    private String printingCentreEmail;

    @Column(name = "printing_centre_tel")
    private String printingCentreTel;

    @Column(name = "printing_centre_fax")
    private String printingCentreFax;

    @Column(name = "printing_centre_responsable_name")
    private String printingCentreResponsableName;

    @Column(name = "printing_params")
    private String printingParams;

    @Column(name = "printing_attributs")
    private String printingAttributs;

    @Column(name = "printing_centre_stat")
    private Boolean printingCentreStat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "printingCentres", "sites", "organizs", "accreditations", "country" }, allowSetters = true)
    private City city;

    @ManyToOne
    @JsonIgnoreProperties(value = { "printingCentres", "cities", "organizs", "accreditations" }, allowSetters = true)
    private Country country;

    @ManyToOne
    @JsonIgnoreProperties(value = { "printingCentres", "accreditations", "country", "city", "event" }, allowSetters = true)
    private Organiz organiz;

    @ManyToOne
    @JsonIgnoreProperties(value = { "printingCentres" }, allowSetters = true)
    private PrintingType printingType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "printingCentres", "event" }, allowSetters = true)
    private PrintingServer printingServer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "printingCentres", "accreditationTypes", "categories", "event" }, allowSetters = true)
    private PrintingModel printingModel;

    @ManyToOne
    @JsonIgnoreProperties(value = { "events", "settings", "printingCentres" }, allowSetters = true)
    private Language language;

    @OneToMany(mappedBy = "printingCentre")
    @JsonIgnoreProperties(allowSetters = true)
    private Set<User> users = new HashSet<>();

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getPrintingCentreId() {
        return this.printingCentreId;
    }

    public PrintingCentre printingCentreId(Long printingCentreId) {
        this.setPrintingCentreId(printingCentreId);
        return this;
    }

    public void setPrintingCentreId(Long printingCentreId) {
        this.printingCentreId = printingCentreId;
    }

    public String getPrintingCentreDescription() {
        return this.printingCentreDescription;
    }

    public PrintingCentre printingCentreDescription(String printingCentreDescription) {
        this.setPrintingCentreDescription(printingCentreDescription);
        return this;
    }

    public void setPrintingCentreDescription(String printingCentreDescription) {
        this.printingCentreDescription = printingCentreDescription;
    }

    public String getPrintingCentreName() {
        return this.printingCentreName;
    }

    public PrintingCentre printingCentreName(String printingCentreName) {
        this.setPrintingCentreName(printingCentreName);
        return this;
    }

    public void setPrintingCentreName(String printingCentreName) {
        this.printingCentreName = printingCentreName;
    }

    public byte[] getPrintingCentreLogo() {
        return this.printingCentreLogo;
    }

    public PrintingCentre printingCentreLogo(byte[] printingCentreLogo) {
        this.setPrintingCentreLogo(printingCentreLogo);
        return this;
    }

    public void setPrintingCentreLogo(byte[] printingCentreLogo) {
        this.printingCentreLogo = printingCentreLogo;
    }

    public String getPrintingCentreLogoContentType() {
        return this.printingCentreLogoContentType;
    }

    public PrintingCentre printingCentreLogoContentType(String printingCentreLogoContentType) {
        this.printingCentreLogoContentType = printingCentreLogoContentType;
        return this;
    }

    public void setPrintingCentreLogoContentType(String printingCentreLogoContentType) {
        this.printingCentreLogoContentType = printingCentreLogoContentType;
    }

    public String getPrintingCentreAdresse() {
        return this.printingCentreAdresse;
    }

    public PrintingCentre printingCentreAdresse(String printingCentreAdresse) {
        this.setPrintingCentreAdresse(printingCentreAdresse);
        return this;
    }

    public void setPrintingCentreAdresse(String printingCentreAdresse) {
        this.printingCentreAdresse = printingCentreAdresse;
    }

    public String getPrintingCentreEmail() {
        return this.printingCentreEmail;
    }

    public PrintingCentre printingCentreEmail(String printingCentreEmail) {
        this.setPrintingCentreEmail(printingCentreEmail);
        return this;
    }

    public void setPrintingCentreEmail(String printingCentreEmail) {
        this.printingCentreEmail = printingCentreEmail;
    }

    public String getPrintingCentreTel() {
        return this.printingCentreTel;
    }

    public PrintingCentre printingCentreTel(String printingCentreTel) {
        this.setPrintingCentreTel(printingCentreTel);
        return this;
    }

    public void setPrintingCentreTel(String printingCentreTel) {
        this.printingCentreTel = printingCentreTel;
    }

    public String getPrintingCentreFax() {
        return this.printingCentreFax;
    }

    public PrintingCentre printingCentreFax(String printingCentreFax) {
        this.setPrintingCentreFax(printingCentreFax);
        return this;
    }

    public void setPrintingCentreFax(String printingCentreFax) {
        this.printingCentreFax = printingCentreFax;
    }

    public String getPrintingCentreResponsableName() {
        return this.printingCentreResponsableName;
    }

    public PrintingCentre printingCentreResponsableName(String printingCentreResponsableName) {
        this.setPrintingCentreResponsableName(printingCentreResponsableName);
        return this;
    }

    public void setPrintingCentreResponsableName(String printingCentreResponsableName) {
        this.printingCentreResponsableName = printingCentreResponsableName;
    }

    public String getPrintingParams() {
        return this.printingParams;
    }

    public PrintingCentre printingParams(String printingParams) {
        this.setPrintingParams(printingParams);
        return this;
    }

    public void setPrintingParams(String printingParams) {
        this.printingParams = printingParams;
    }

    public String getPrintingAttributs() {
        return this.printingAttributs;
    }

    public PrintingCentre printingAttributs(String printingAttributs) {
        this.setPrintingAttributs(printingAttributs);
        return this;
    }

    public void setPrintingAttributs(String printingAttributs) {
        this.printingAttributs = printingAttributs;
    }

    public Boolean getPrintingCentreStat() {
        return this.printingCentreStat;
    }

    public PrintingCentre printingCentreStat(Boolean printingCentreStat) {
        this.setPrintingCentreStat(printingCentreStat);
        return this;
    }

    public void setPrintingCentreStat(Boolean printingCentreStat) {
        this.printingCentreStat = printingCentreStat;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public PrintingCentre city(City city) {
        this.setCity(city);
        return this;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public PrintingCentre country(Country country) {
        this.setCountry(country);
        return this;
    }

    public Organiz getOrganiz() {
        return this.organiz;
    }

    public void setOrganiz(Organiz organiz) {
        this.organiz = organiz;
    }

    public PrintingCentre organiz(Organiz organiz) {
        this.setOrganiz(organiz);
        return this;
    }

    public PrintingType getPrintingType() {
        return this.printingType;
    }

    public void setPrintingType(PrintingType printingType) {
        this.printingType = printingType;
    }

    public PrintingCentre printingType(PrintingType printingType) {
        this.setPrintingType(printingType);
        return this;
    }

    public PrintingServer getPrintingServer() {
        return this.printingServer;
    }

    public void setPrintingServer(PrintingServer printingServer) {
        this.printingServer = printingServer;
    }

    public PrintingCentre printingServer(PrintingServer printingServer) {
        this.setPrintingServer(printingServer);
        return this;
    }

    public PrintingModel getPrintingModel() {
        return this.printingModel;
    }

    public void setPrintingModel(PrintingModel printingModel) {
        this.printingModel = printingModel;
    }

    public PrintingCentre printingModel(PrintingModel printingModel) {
        this.setPrintingModel(printingModel);
        return this;
    }

    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public PrintingCentre language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public PrintingCentre event(Event event) {
        this.setEvent(event);
        return this;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        if (this.users != null) {
            this.users.forEach(i -> i.setPrintingCentre(null));
        }
        if (users != null) {
            users.forEach(i -> i.setPrintingCentre(this));
        }
        this.users = users;
    }

    public PrintingCentre users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public PrintingCentre addUser(User user) {
        this.users.add(user);
        user.setPrintingCentre(this);
        return this;
    }

    public PrintingCentre removeUser(User user) {
        this.users.remove(user);
        user.setPrintingCentre(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrintingCentre)) {
            return false;
        }
        return printingCentreId != null && printingCentreId.equals(((PrintingCentre) o).printingCentreId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrintingCentre{" +
            "printingCentreId=" + getPrintingCentreId() +
            ", printingCentreDescription='" + getPrintingCentreDescription() + "'" +
            ", printingCentreName='" + getPrintingCentreName() + "'" +
            ", printingCentreLogo='" + getPrintingCentreLogo() + "'" +
            ", printingCentreLogoContentType='" + getPrintingCentreLogoContentType() + "'" +
            ", printingCentreAdresse='" + getPrintingCentreAdresse() + "'" +
            ", printingCentreEmail='" + getPrintingCentreEmail() + "'" +
            ", printingCentreTel='" + getPrintingCentreTel() + "'" +
            ", printingCentreFax='" + getPrintingCentreFax() + "'" +
            ", printingCentreResponsableName='" + getPrintingCentreResponsableName() + "'" +
            ", printingParams='" + getPrintingParams() + "'" +
            ", printingAttributs='" + getPrintingAttributs() + "'" +
            ", printingCentreStat='" + getPrintingCentreStat() + "'" +
            "}";
    }
}

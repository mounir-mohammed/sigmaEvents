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
 * A Organiz.
 */
@Entity
@Table(name = "organiz")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Organiz implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organiz_id")
    private Long organizId;

    @NotNull
    @Size(max = 50)
    @Column(name = "organiz_name", length = 50, nullable = false)
    private String organizName;

    @Size(max = 200)
    @Column(name = "organiz_description", length = 200)
    private String organizDescription;

    @Lob
    @Column(name = "organiz_logo")
    private byte[] organizLogo;

    @Column(name = "organiz_logo_content_type")
    private String organizLogoContentType;

    @Column(name = "organiz_tel")
    private String organizTel;

    @Column(name = "organiz_fax")
    private String organizFax;

    @Column(name = "organiz_email")
    private String organizEmail;

    @Column(name = "organiz_adresse")
    private String organizAdresse;

    @Column(name = "organiz_params")
    private String organizParams;

    @Column(name = "organiz_attributs")
    private String organizAttributs;

    @Column(name = "organiz_stat")
    private Boolean organizStat;

    @OneToMany(mappedBy = "organiz")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "city", "country", "organiz", "printingType", "printingServer", "printingModel", "language", "event" },
        allowSetters = true
    )
    private Set<PrintingCentre> printingCentres = new HashSet<>();

    @OneToMany(mappedBy = "organiz")
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

    @ManyToOne
    @JsonIgnoreProperties(value = { "printingCentres", "cities", "organizs", "accreditations" }, allowSetters = true)
    private Country country;

    @ManyToOne
    @JsonIgnoreProperties(value = { "printingCentres", "sites", "organizs", "accreditations", "country" }, allowSetters = true)
    private City city;

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

    public Long getOrganizId() {
        return this.organizId;
    }

    public Organiz organizId(Long organizId) {
        this.setOrganizId(organizId);
        return this;
    }

    public void setOrganizId(Long organizId) {
        this.organizId = organizId;
    }

    public String getOrganizName() {
        return this.organizName;
    }

    public Organiz organizName(String organizName) {
        this.setOrganizName(organizName);
        return this;
    }

    public void setOrganizName(String organizName) {
        this.organizName = organizName;
    }

    public String getOrganizDescription() {
        return this.organizDescription;
    }

    public Organiz organizDescription(String organizDescription) {
        this.setOrganizDescription(organizDescription);
        return this;
    }

    public void setOrganizDescription(String organizDescription) {
        this.organizDescription = organizDescription;
    }

    public byte[] getOrganizLogo() {
        return this.organizLogo;
    }

    public Organiz organizLogo(byte[] organizLogo) {
        this.setOrganizLogo(organizLogo);
        return this;
    }

    public void setOrganizLogo(byte[] organizLogo) {
        this.organizLogo = organizLogo;
    }

    public String getOrganizLogoContentType() {
        return this.organizLogoContentType;
    }

    public Organiz organizLogoContentType(String organizLogoContentType) {
        this.organizLogoContentType = organizLogoContentType;
        return this;
    }

    public void setOrganizLogoContentType(String organizLogoContentType) {
        this.organizLogoContentType = organizLogoContentType;
    }

    public String getOrganizTel() {
        return this.organizTel;
    }

    public Organiz organizTel(String organizTel) {
        this.setOrganizTel(organizTel);
        return this;
    }

    public void setOrganizTel(String organizTel) {
        this.organizTel = organizTel;
    }

    public String getOrganizFax() {
        return this.organizFax;
    }

    public Organiz organizFax(String organizFax) {
        this.setOrganizFax(organizFax);
        return this;
    }

    public void setOrganizFax(String organizFax) {
        this.organizFax = organizFax;
    }

    public String getOrganizEmail() {
        return this.organizEmail;
    }

    public Organiz organizEmail(String organizEmail) {
        this.setOrganizEmail(organizEmail);
        return this;
    }

    public void setOrganizEmail(String organizEmail) {
        this.organizEmail = organizEmail;
    }

    public String getOrganizAdresse() {
        return this.organizAdresse;
    }

    public Organiz organizAdresse(String organizAdresse) {
        this.setOrganizAdresse(organizAdresse);
        return this;
    }

    public void setOrganizAdresse(String organizAdresse) {
        this.organizAdresse = organizAdresse;
    }

    public String getOrganizParams() {
        return this.organizParams;
    }

    public Organiz organizParams(String organizParams) {
        this.setOrganizParams(organizParams);
        return this;
    }

    public void setOrganizParams(String organizParams) {
        this.organizParams = organizParams;
    }

    public String getOrganizAttributs() {
        return this.organizAttributs;
    }

    public Organiz organizAttributs(String organizAttributs) {
        this.setOrganizAttributs(organizAttributs);
        return this;
    }

    public void setOrganizAttributs(String organizAttributs) {
        this.organizAttributs = organizAttributs;
    }

    public Boolean getOrganizStat() {
        return this.organizStat;
    }

    public Organiz organizStat(Boolean organizStat) {
        this.setOrganizStat(organizStat);
        return this;
    }

    public void setOrganizStat(Boolean organizStat) {
        this.organizStat = organizStat;
    }

    public Set<PrintingCentre> getPrintingCentres() {
        return this.printingCentres;
    }

    public void setPrintingCentres(Set<PrintingCentre> printingCentres) {
        if (this.printingCentres != null) {
            this.printingCentres.forEach(i -> i.setOrganiz(null));
        }
        if (printingCentres != null) {
            printingCentres.forEach(i -> i.setOrganiz(this));
        }
        this.printingCentres = printingCentres;
    }

    public Organiz printingCentres(Set<PrintingCentre> printingCentres) {
        this.setPrintingCentres(printingCentres);
        return this;
    }

    public Organiz addPrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.add(printingCentre);
        printingCentre.setOrganiz(this);
        return this;
    }

    public Organiz removePrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.remove(printingCentre);
        printingCentre.setOrganiz(null);
        return this;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setOrganiz(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setOrganiz(this));
        }
        this.accreditations = accreditations;
    }

    public Organiz accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public Organiz addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setOrganiz(this);
        return this;
    }

    public Organiz removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setOrganiz(null);
        return this;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Organiz country(Country country) {
        this.setCountry(country);
        return this;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Organiz city(City city) {
        this.setCity(city);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Organiz event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organiz)) {
            return false;
        }
        return organizId != null && organizId.equals(((Organiz) o).organizId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organiz{" +
            "organizId=" + getOrganizId() +
            ", organizName='" + getOrganizName() + "'" +
            ", organizDescription='" + getOrganizDescription() + "'" +
            ", organizLogo='" + getOrganizLogo() + "'" +
            ", organizLogoContentType='" + getOrganizLogoContentType() + "'" +
            ", organizTel='" + getOrganizTel() + "'" +
            ", organizFax='" + getOrganizFax() + "'" +
            ", organizEmail='" + getOrganizEmail() + "'" +
            ", organizAdresse='" + getOrganizAdresse() + "'" +
            ", organizParams='" + getOrganizParams() + "'" +
            ", organizAttributs='" + getOrganizAttributs() + "'" +
            ", organizStat='" + getOrganizStat() + "'" +
            "}";
    }
}

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
 * A Site.
 */
@Entity
@Table(name = "site")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "site_id")
    private Long siteId;

    @NotNull
    @Size(max = 50)
    @Column(name = "site_name", length = 50, nullable = false)
    private String siteName;

    @Size(max = 100)
    @Column(name = "site_color", length = 100)
    private String siteColor;

    @Size(max = 10)
    @Column(name = "site_abreviation", length = 10)
    private String siteAbreviation;

    @Size(max = 200)
    @Column(name = "site_description", length = 200)
    private String siteDescription;

    @Lob
    @Column(name = "site_logo")
    private byte[] siteLogo;

    @Column(name = "site_logo_content_type")
    private String siteLogoContentType;

    @Column(name = "site_adresse")
    private String siteAdresse;

    @Column(name = "site_email")
    private String siteEmail;

    @Column(name = "site_tel")
    private String siteTel;

    @Column(name = "site_fax")
    private String siteFax;

    @Column(name = "site_responsable_name")
    private String siteResponsableName;

    @Column(name = "site_params")
    private String siteParams;

    @Column(name = "site_attributs")
    private String siteAttributs;

    @Column(name = "site_stat")
    private Boolean siteStat;

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

    @ManyToMany(mappedBy = "sites")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getSiteId() {
        return this.siteId;
    }

    public Site siteId(Long siteId) {
        this.setSiteId(siteId);
        return this;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return this.siteName;
    }

    public Site siteName(String siteName) {
        this.setSiteName(siteName);
        return this;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteColor() {
        return this.siteColor;
    }

    public Site siteColor(String siteColor) {
        this.setSiteColor(siteColor);
        return this;
    }

    public void setSiteColor(String siteColor) {
        this.siteColor = siteColor;
    }

    public String getSiteAbreviation() {
        return this.siteAbreviation;
    }

    public Site siteAbreviation(String siteAbreviation) {
        this.setSiteAbreviation(siteAbreviation);
        return this;
    }

    public void setSiteAbreviation(String siteAbreviation) {
        this.siteAbreviation = siteAbreviation;
    }

    public String getSiteDescription() {
        return this.siteDescription;
    }

    public Site siteDescription(String siteDescription) {
        this.setSiteDescription(siteDescription);
        return this;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public byte[] getSiteLogo() {
        return this.siteLogo;
    }

    public Site siteLogo(byte[] siteLogo) {
        this.setSiteLogo(siteLogo);
        return this;
    }

    public void setSiteLogo(byte[] siteLogo) {
        this.siteLogo = siteLogo;
    }

    public String getSiteLogoContentType() {
        return this.siteLogoContentType;
    }

    public Site siteLogoContentType(String siteLogoContentType) {
        this.siteLogoContentType = siteLogoContentType;
        return this;
    }

    public void setSiteLogoContentType(String siteLogoContentType) {
        this.siteLogoContentType = siteLogoContentType;
    }

    public String getSiteAdresse() {
        return this.siteAdresse;
    }

    public Site siteAdresse(String siteAdresse) {
        this.setSiteAdresse(siteAdresse);
        return this;
    }

    public void setSiteAdresse(String siteAdresse) {
        this.siteAdresse = siteAdresse;
    }

    public String getSiteEmail() {
        return this.siteEmail;
    }

    public Site siteEmail(String siteEmail) {
        this.setSiteEmail(siteEmail);
        return this;
    }

    public void setSiteEmail(String siteEmail) {
        this.siteEmail = siteEmail;
    }

    public String getSiteTel() {
        return this.siteTel;
    }

    public Site siteTel(String siteTel) {
        this.setSiteTel(siteTel);
        return this;
    }

    public void setSiteTel(String siteTel) {
        this.siteTel = siteTel;
    }

    public String getSiteFax() {
        return this.siteFax;
    }

    public Site siteFax(String siteFax) {
        this.setSiteFax(siteFax);
        return this;
    }

    public void setSiteFax(String siteFax) {
        this.siteFax = siteFax;
    }

    public String getSiteResponsableName() {
        return this.siteResponsableName;
    }

    public Site siteResponsableName(String siteResponsableName) {
        this.setSiteResponsableName(siteResponsableName);
        return this;
    }

    public void setSiteResponsableName(String siteResponsableName) {
        this.siteResponsableName = siteResponsableName;
    }

    public String getSiteParams() {
        return this.siteParams;
    }

    public Site siteParams(String siteParams) {
        this.setSiteParams(siteParams);
        return this;
    }

    public void setSiteParams(String siteParams) {
        this.siteParams = siteParams;
    }

    public String getSiteAttributs() {
        return this.siteAttributs;
    }

    public Site siteAttributs(String siteAttributs) {
        this.setSiteAttributs(siteAttributs);
        return this;
    }

    public void setSiteAttributs(String siteAttributs) {
        this.siteAttributs = siteAttributs;
    }

    public Boolean getSiteStat() {
        return this.siteStat;
    }

    public Site siteStat(Boolean siteStat) {
        this.setSiteStat(siteStat);
        return this;
    }

    public void setSiteStat(Boolean siteStat) {
        this.siteStat = siteStat;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Site city(City city) {
        this.setCity(city);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Site event(Event event) {
        this.setEvent(event);
        return this;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.removeSite(this));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.addSite(this));
        }
        this.accreditations = accreditations;
    }

    public Site accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public Site addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.getSites().add(this);
        return this;
    }

    public Site removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.getSites().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Site)) {
            return false;
        }
        return siteId != null && siteId.equals(((Site) o).siteId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Site{" +
            "siteId=" + getSiteId() +
            ", siteName='" + getSiteName() + "'" +
            ", siteColor='" + getSiteColor() + "'" +
            ", siteAbreviation='" + getSiteAbreviation() + "'" +
            ", siteDescription='" + getSiteDescription() + "'" +
            ", siteLogo='" + getSiteLogo() + "'" +
            ", siteLogoContentType='" + getSiteLogoContentType() + "'" +
            ", siteAdresse='" + getSiteAdresse() + "'" +
            ", siteEmail='" + getSiteEmail() + "'" +
            ", siteTel='" + getSiteTel() + "'" +
            ", siteFax='" + getSiteFax() + "'" +
            ", siteResponsableName='" + getSiteResponsableName() + "'" +
            ", siteParams='" + getSiteParams() + "'" +
            ", siteAttributs='" + getSiteAttributs() + "'" +
            ", siteStat='" + getSiteStat() + "'" +
            "}";
    }
}

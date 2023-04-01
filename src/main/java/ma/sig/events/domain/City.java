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
 * A City.
 */
@Entity
@Table(name = "city")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long cityId;

    @NotNull
    @Size(max = 50)
    @Column(name = "city_name", length = 50, nullable = false)
    private String cityName;

    @NotNull
    @Column(name = "city_zip_code", nullable = false)
    private String cityZipCode;

    @Size(max = 10)
    @Column(name = "city_abreviation", length = 10)
    private String cityAbreviation;

    @Size(max = 200)
    @Column(name = "city_description", length = 200)
    private String cityDescription;

    @Column(name = "city_params")
    private String cityParams;

    @Column(name = "city_attributs")
    private String cityAttributs;

    @Column(name = "city_stat")
    private Boolean cityStat;

    @OneToMany(mappedBy = "city")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "city", "country", "organiz", "printingType", "printingServer", "printingModel", "language", "event" },
        allowSetters = true
    )
    private Set<PrintingCentre> printingCentres = new HashSet<>();

    @OneToMany(mappedBy = "city")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "city", "event", "accreditations" }, allowSetters = true)
    private Set<Site> sites = new HashSet<>();

    @OneToMany(mappedBy = "city")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "printingCentres", "accreditations", "country", "city", "event" }, allowSetters = true)
    private Set<Organiz> organizs = new HashSet<>();

    @OneToMany(mappedBy = "city")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getCityId() {
        return this.cityId;
    }

    public City cityId(Long cityId) {
        this.setCityId(cityId);
        return this;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public City cityName(String cityName) {
        this.setCityName(cityName);
        return this;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityZipCode() {
        return this.cityZipCode;
    }

    public City cityZipCode(String cityZipCode) {
        this.setCityZipCode(cityZipCode);
        return this;
    }

    public void setCityZipCode(String cityZipCode) {
        this.cityZipCode = cityZipCode;
    }

    public String getCityAbreviation() {
        return this.cityAbreviation;
    }

    public City cityAbreviation(String cityAbreviation) {
        this.setCityAbreviation(cityAbreviation);
        return this;
    }

    public void setCityAbreviation(String cityAbreviation) {
        this.cityAbreviation = cityAbreviation;
    }

    public String getCityDescription() {
        return this.cityDescription;
    }

    public City cityDescription(String cityDescription) {
        this.setCityDescription(cityDescription);
        return this;
    }

    public void setCityDescription(String cityDescription) {
        this.cityDescription = cityDescription;
    }

    public String getCityParams() {
        return this.cityParams;
    }

    public City cityParams(String cityParams) {
        this.setCityParams(cityParams);
        return this;
    }

    public void setCityParams(String cityParams) {
        this.cityParams = cityParams;
    }

    public String getCityAttributs() {
        return this.cityAttributs;
    }

    public City cityAttributs(String cityAttributs) {
        this.setCityAttributs(cityAttributs);
        return this;
    }

    public void setCityAttributs(String cityAttributs) {
        this.cityAttributs = cityAttributs;
    }

    public Boolean getCityStat() {
        return this.cityStat;
    }

    public City cityStat(Boolean cityStat) {
        this.setCityStat(cityStat);
        return this;
    }

    public void setCityStat(Boolean cityStat) {
        this.cityStat = cityStat;
    }

    public Set<PrintingCentre> getPrintingCentres() {
        return this.printingCentres;
    }

    public void setPrintingCentres(Set<PrintingCentre> printingCentres) {
        if (this.printingCentres != null) {
            this.printingCentres.forEach(i -> i.setCity(null));
        }
        if (printingCentres != null) {
            printingCentres.forEach(i -> i.setCity(this));
        }
        this.printingCentres = printingCentres;
    }

    public City printingCentres(Set<PrintingCentre> printingCentres) {
        this.setPrintingCentres(printingCentres);
        return this;
    }

    public City addPrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.add(printingCentre);
        printingCentre.setCity(this);
        return this;
    }

    public City removePrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.remove(printingCentre);
        printingCentre.setCity(null);
        return this;
    }

    public Set<Site> getSites() {
        return this.sites;
    }

    public void setSites(Set<Site> sites) {
        if (this.sites != null) {
            this.sites.forEach(i -> i.setCity(null));
        }
        if (sites != null) {
            sites.forEach(i -> i.setCity(this));
        }
        this.sites = sites;
    }

    public City sites(Set<Site> sites) {
        this.setSites(sites);
        return this;
    }

    public City addSite(Site site) {
        this.sites.add(site);
        site.setCity(this);
        return this;
    }

    public City removeSite(Site site) {
        this.sites.remove(site);
        site.setCity(null);
        return this;
    }

    public Set<Organiz> getOrganizs() {
        return this.organizs;
    }

    public void setOrganizs(Set<Organiz> organizs) {
        if (this.organizs != null) {
            this.organizs.forEach(i -> i.setCity(null));
        }
        if (organizs != null) {
            organizs.forEach(i -> i.setCity(this));
        }
        this.organizs = organizs;
    }

    public City organizs(Set<Organiz> organizs) {
        this.setOrganizs(organizs);
        return this;
    }

    public City addOrganiz(Organiz organiz) {
        this.organizs.add(organiz);
        organiz.setCity(this);
        return this;
    }

    public City removeOrganiz(Organiz organiz) {
        this.organizs.remove(organiz);
        organiz.setCity(null);
        return this;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setCity(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setCity(this));
        }
        this.accreditations = accreditations;
    }

    public City accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public City addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setCity(this);
        return this;
    }

    public City removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setCity(null);
        return this;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City country(Country country) {
        this.setCountry(country);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        return cityId != null && cityId.equals(((City) o).cityId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "City{" +
            "cityId=" + getCityId() +
            ", cityName='" + getCityName() + "'" +
            ", cityZipCode='" + getCityZipCode() + "'" +
            ", cityAbreviation='" + getCityAbreviation() + "'" +
            ", cityDescription='" + getCityDescription() + "'" +
            ", cityParams='" + getCityParams() + "'" +
            ", cityAttributs='" + getCityAttributs() + "'" +
            ", cityStat='" + getCityStat() + "'" +
            "}";
    }
}

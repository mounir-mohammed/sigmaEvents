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
 * A Country.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long countryId;

    @NotNull
    @Size(max = 50)
    @Column(name = "country_name", length = 50, nullable = false)
    private String countryName;

    @NotNull
    @Column(name = "country_code_alpha_2", nullable = false)
    private String countryCodeAlpha2;

    @Column(name = "country_code_alpha_3")
    private String countryCodeAlpha3;

    @Column(name = "country_tel_code")
    private String countryTelCode;

    @Size(max = 200)
    @Column(name = "country_description", length = 200)
    private String countryDescription;

    @Lob
    @Column(name = "country_flag")
    private byte[] countryFlag;

    @Column(name = "country_flag_content_type")
    private String countryFlagContentType;

    @Column(name = "country_params")
    private String countryParams;

    @Column(name = "country_attributs")
    private String countryAttributs;

    @Column(name = "country_stat")
    private Boolean countryStat;

    @OneToMany(mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "city", "country", "organiz", "printingType", "printingServer", "printingModel", "language", "event" },
        allowSetters = true
    )
    private Set<PrintingCentre> printingCentres = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "printingCentres", "sites", "organizs", "accreditations", "country" }, allowSetters = true)
    private Set<City> cities = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "printingCentres", "accreditations", "country", "city", "event" }, allowSetters = true)
    private Set<Organiz> organizs = new HashSet<>();

    @OneToMany(mappedBy = "country")
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

    public Long getCountryId() {
        return this.countryId;
    }

    public Country countryId(Long countryId) {
        this.setCountryId(countryId);
        return this;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public Country countryName(String countryName) {
        this.setCountryName(countryName);
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCodeAlpha2() {
        return this.countryCodeAlpha2;
    }

    public Country countryCodeAlpha2(String countryCodeAlpha2) {
        this.setCountryCodeAlpha2(countryCodeAlpha2);
        return this;
    }

    public void setCountryCodeAlpha2(String countryCodeAlpha2) {
        this.countryCodeAlpha2 = countryCodeAlpha2;
    }

    public String getCountryCodeAlpha3() {
        return this.countryCodeAlpha3;
    }

    public Country countryCodeAlpha3(String countryCodeAlpha3) {
        this.setCountryCodeAlpha3(countryCodeAlpha3);
        return this;
    }

    public void setCountryCodeAlpha3(String countryCodeAlpha3) {
        this.countryCodeAlpha3 = countryCodeAlpha3;
    }

    public String getCountryTelCode() {
        return this.countryTelCode;
    }

    public Country countryTelCode(String countryTelCode) {
        this.setCountryTelCode(countryTelCode);
        return this;
    }

    public void setCountryTelCode(String countryTelCode) {
        this.countryTelCode = countryTelCode;
    }

    public String getCountryDescription() {
        return this.countryDescription;
    }

    public Country countryDescription(String countryDescription) {
        this.setCountryDescription(countryDescription);
        return this;
    }

    public void setCountryDescription(String countryDescription) {
        this.countryDescription = countryDescription;
    }

    public byte[] getCountryFlag() {
        return this.countryFlag;
    }

    public Country countryFlag(byte[] countryFlag) {
        this.setCountryFlag(countryFlag);
        return this;
    }

    public void setCountryFlag(byte[] countryFlag) {
        this.countryFlag = countryFlag;
    }

    public String getCountryFlagContentType() {
        return this.countryFlagContentType;
    }

    public Country countryFlagContentType(String countryFlagContentType) {
        this.countryFlagContentType = countryFlagContentType;
        return this;
    }

    public void setCountryFlagContentType(String countryFlagContentType) {
        this.countryFlagContentType = countryFlagContentType;
    }

    public String getCountryParams() {
        return this.countryParams;
    }

    public Country countryParams(String countryParams) {
        this.setCountryParams(countryParams);
        return this;
    }

    public void setCountryParams(String countryParams) {
        this.countryParams = countryParams;
    }

    public String getCountryAttributs() {
        return this.countryAttributs;
    }

    public Country countryAttributs(String countryAttributs) {
        this.setCountryAttributs(countryAttributs);
        return this;
    }

    public void setCountryAttributs(String countryAttributs) {
        this.countryAttributs = countryAttributs;
    }

    public Boolean getCountryStat() {
        return this.countryStat;
    }

    public Country countryStat(Boolean countryStat) {
        this.setCountryStat(countryStat);
        return this;
    }

    public void setCountryStat(Boolean countryStat) {
        this.countryStat = countryStat;
    }

    public Set<PrintingCentre> getPrintingCentres() {
        return this.printingCentres;
    }

    public void setPrintingCentres(Set<PrintingCentre> printingCentres) {
        if (this.printingCentres != null) {
            this.printingCentres.forEach(i -> i.setCountry(null));
        }
        if (printingCentres != null) {
            printingCentres.forEach(i -> i.setCountry(this));
        }
        this.printingCentres = printingCentres;
    }

    public Country printingCentres(Set<PrintingCentre> printingCentres) {
        this.setPrintingCentres(printingCentres);
        return this;
    }

    public Country addPrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.add(printingCentre);
        printingCentre.setCountry(this);
        return this;
    }

    public Country removePrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.remove(printingCentre);
        printingCentre.setCountry(null);
        return this;
    }

    public Set<City> getCities() {
        return this.cities;
    }

    public void setCities(Set<City> cities) {
        if (this.cities != null) {
            this.cities.forEach(i -> i.setCountry(null));
        }
        if (cities != null) {
            cities.forEach(i -> i.setCountry(this));
        }
        this.cities = cities;
    }

    public Country cities(Set<City> cities) {
        this.setCities(cities);
        return this;
    }

    public Country addCity(City city) {
        this.cities.add(city);
        city.setCountry(this);
        return this;
    }

    public Country removeCity(City city) {
        this.cities.remove(city);
        city.setCountry(null);
        return this;
    }

    public Set<Organiz> getOrganizs() {
        return this.organizs;
    }

    public void setOrganizs(Set<Organiz> organizs) {
        if (this.organizs != null) {
            this.organizs.forEach(i -> i.setCountry(null));
        }
        if (organizs != null) {
            organizs.forEach(i -> i.setCountry(this));
        }
        this.organizs = organizs;
    }

    public Country organizs(Set<Organiz> organizs) {
        this.setOrganizs(organizs);
        return this;
    }

    public Country addOrganiz(Organiz organiz) {
        this.organizs.add(organiz);
        organiz.setCountry(this);
        return this;
    }

    public Country removeOrganiz(Organiz organiz) {
        this.organizs.remove(organiz);
        organiz.setCountry(null);
        return this;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setCountry(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setCountry(this));
        }
        this.accreditations = accreditations;
    }

    public Country accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public Country addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setCountry(this);
        return this;
    }

    public Country removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setCountry(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return countryId != null && countryId.equals(((Country) o).countryId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "countryId=" + getCountryId() +
            ", countryName='" + getCountryName() + "'" +
            ", countryCodeAlpha2='" + getCountryCodeAlpha2() + "'" +
            ", countryCodeAlpha3='" + getCountryCodeAlpha3() + "'" +
            ", countryTelCode='" + getCountryTelCode() + "'" +
            ", countryDescription='" + getCountryDescription() + "'" +
            ", countryFlag='" + getCountryFlag() + "'" +
            ", countryFlagContentType='" + getCountryFlagContentType() + "'" +
            ", countryParams='" + getCountryParams() + "'" +
            ", countryAttributs='" + getCountryAttributs() + "'" +
            ", countryStat='" + getCountryStat() + "'" +
            "}";
    }
}

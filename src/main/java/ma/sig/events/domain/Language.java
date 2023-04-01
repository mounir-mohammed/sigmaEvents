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
 * A Language.
 */
@Entity
@Table(name = "language")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Long languageId;

    @NotNull
    @Size(max = 50)
    @Column(name = "language_code", length = 50, nullable = false)
    private String languageCode;

    @NotNull
    @Size(max = 50)
    @Column(name = "language_name", length = 50, nullable = false)
    private String languageName;

    @Size(max = 200)
    @Column(name = "language_description", length = 200)
    private String languageDescription;

    @Column(name = "language_params")
    private String languageParams;

    @Column(name = "language_attributs")
    private String languageAttributs;

    @Column(name = "language_stat")
    private Boolean languageStat;

    @OneToMany(mappedBy = "language")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "language")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "language", "event" }, allowSetters = true)
    private Set<Setting> settings = new HashSet<>();

    @OneToMany(mappedBy = "language")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "city", "country", "organiz", "printingType", "printingServer", "printingModel", "language", "event" },
        allowSetters = true
    )
    private Set<PrintingCentre> printingCentres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getLanguageId() {
        return this.languageId;
    }

    public Language languageId(Long languageId) {
        this.setLanguageId(languageId);
        return this;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getLanguageCode() {
        return this.languageCode;
    }

    public Language languageCode(String languageCode) {
        this.setLanguageCode(languageCode);
        return this;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageName() {
        return this.languageName;
    }

    public Language languageName(String languageName) {
        this.setLanguageName(languageName);
        return this;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageDescription() {
        return this.languageDescription;
    }

    public Language languageDescription(String languageDescription) {
        this.setLanguageDescription(languageDescription);
        return this;
    }

    public void setLanguageDescription(String languageDescription) {
        this.languageDescription = languageDescription;
    }

    public String getLanguageParams() {
        return this.languageParams;
    }

    public Language languageParams(String languageParams) {
        this.setLanguageParams(languageParams);
        return this;
    }

    public void setLanguageParams(String languageParams) {
        this.languageParams = languageParams;
    }

    public String getLanguageAttributs() {
        return this.languageAttributs;
    }

    public Language languageAttributs(String languageAttributs) {
        this.setLanguageAttributs(languageAttributs);
        return this;
    }

    public void setLanguageAttributs(String languageAttributs) {
        this.languageAttributs = languageAttributs;
    }

    public Boolean getLanguageStat() {
        return this.languageStat;
    }

    public Language languageStat(Boolean languageStat) {
        this.setLanguageStat(languageStat);
        return this;
    }

    public void setLanguageStat(Boolean languageStat) {
        this.languageStat = languageStat;
    }

    public Set<Event> getEvents() {
        return this.events;
    }

    public void setEvents(Set<Event> events) {
        if (this.events != null) {
            this.events.forEach(i -> i.setLanguage(null));
        }
        if (events != null) {
            events.forEach(i -> i.setLanguage(this));
        }
        this.events = events;
    }

    public Language events(Set<Event> events) {
        this.setEvents(events);
        return this;
    }

    public Language addEvent(Event event) {
        this.events.add(event);
        event.setLanguage(this);
        return this;
    }

    public Language removeEvent(Event event) {
        this.events.remove(event);
        event.setLanguage(null);
        return this;
    }

    public Set<Setting> getSettings() {
        return this.settings;
    }

    public void setSettings(Set<Setting> settings) {
        if (this.settings != null) {
            this.settings.forEach(i -> i.setLanguage(null));
        }
        if (settings != null) {
            settings.forEach(i -> i.setLanguage(this));
        }
        this.settings = settings;
    }

    public Language settings(Set<Setting> settings) {
        this.setSettings(settings);
        return this;
    }

    public Language addSetting(Setting setting) {
        this.settings.add(setting);
        setting.setLanguage(this);
        return this;
    }

    public Language removeSetting(Setting setting) {
        this.settings.remove(setting);
        setting.setLanguage(null);
        return this;
    }

    public Set<PrintingCentre> getPrintingCentres() {
        return this.printingCentres;
    }

    public void setPrintingCentres(Set<PrintingCentre> printingCentres) {
        if (this.printingCentres != null) {
            this.printingCentres.forEach(i -> i.setLanguage(null));
        }
        if (printingCentres != null) {
            printingCentres.forEach(i -> i.setLanguage(this));
        }
        this.printingCentres = printingCentres;
    }

    public Language printingCentres(Set<PrintingCentre> printingCentres) {
        this.setPrintingCentres(printingCentres);
        return this;
    }

    public Language addPrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.add(printingCentre);
        printingCentre.setLanguage(this);
        return this;
    }

    public Language removePrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.remove(printingCentre);
        printingCentre.setLanguage(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Language)) {
            return false;
        }
        return languageId != null && languageId.equals(((Language) o).languageId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Language{" +
            "languageId=" + getLanguageId() +
            ", languageCode='" + getLanguageCode() + "'" +
            ", languageName='" + getLanguageName() + "'" +
            ", languageDescription='" + getLanguageDescription() + "'" +
            ", languageParams='" + getLanguageParams() + "'" +
            ", languageAttributs='" + getLanguageAttributs() + "'" +
            ", languageStat='" + getLanguageStat() + "'" +
            "}";
    }
}

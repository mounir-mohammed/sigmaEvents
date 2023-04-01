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
 * A Area.
 */
@Entity
@Table(name = "area")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id")
    private Long areaId;

    @NotNull
    @Size(max = 50)
    @Column(name = "area_name", length = 50, nullable = false)
    private String areaName;

    @Size(max = 10)
    @Column(name = "area_abreviation", length = 10)
    private String areaAbreviation;

    @Size(max = 100)
    @Column(name = "area_color", length = 100)
    private String areaColor;

    @Size(max = 200)
    @Column(name = "area_description", length = 200)
    private String areaDescription;

    @Lob
    @Column(name = "area_logo")
    private byte[] areaLogo;

    @Column(name = "area_logo_content_type")
    private String areaLogoContentType;

    @Column(name = "area_params")
    private String areaParams;

    @Column(name = "area_attributs")
    private String areaAttributs;

    @Column(name = "area_stat")
    private Boolean areaStat;

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

    @ManyToMany(mappedBy = "areas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditations", "areas", "category", "event" }, allowSetters = true)
    private Set<Fonction> fonctions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getAreaId() {
        return this.areaId;
    }

    public Area areaId(Long areaId) {
        this.setAreaId(areaId);
        return this;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public Area areaName(String areaName) {
        this.setAreaName(areaName);
        return this;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaAbreviation() {
        return this.areaAbreviation;
    }

    public Area areaAbreviation(String areaAbreviation) {
        this.setAreaAbreviation(areaAbreviation);
        return this;
    }

    public void setAreaAbreviation(String areaAbreviation) {
        this.areaAbreviation = areaAbreviation;
    }

    public String getAreaColor() {
        return this.areaColor;
    }

    public Area areaColor(String areaColor) {
        this.setAreaColor(areaColor);
        return this;
    }

    public void setAreaColor(String areaColor) {
        this.areaColor = areaColor;
    }

    public String getAreaDescription() {
        return this.areaDescription;
    }

    public Area areaDescription(String areaDescription) {
        this.setAreaDescription(areaDescription);
        return this;
    }

    public void setAreaDescription(String areaDescription) {
        this.areaDescription = areaDescription;
    }

    public byte[] getAreaLogo() {
        return this.areaLogo;
    }

    public Area areaLogo(byte[] areaLogo) {
        this.setAreaLogo(areaLogo);
        return this;
    }

    public void setAreaLogo(byte[] areaLogo) {
        this.areaLogo = areaLogo;
    }

    public String getAreaLogoContentType() {
        return this.areaLogoContentType;
    }

    public Area areaLogoContentType(String areaLogoContentType) {
        this.areaLogoContentType = areaLogoContentType;
        return this;
    }

    public void setAreaLogoContentType(String areaLogoContentType) {
        this.areaLogoContentType = areaLogoContentType;
    }

    public String getAreaParams() {
        return this.areaParams;
    }

    public Area areaParams(String areaParams) {
        this.setAreaParams(areaParams);
        return this;
    }

    public void setAreaParams(String areaParams) {
        this.areaParams = areaParams;
    }

    public String getAreaAttributs() {
        return this.areaAttributs;
    }

    public Area areaAttributs(String areaAttributs) {
        this.setAreaAttributs(areaAttributs);
        return this;
    }

    public void setAreaAttributs(String areaAttributs) {
        this.areaAttributs = areaAttributs;
    }

    public Boolean getAreaStat() {
        return this.areaStat;
    }

    public Area areaStat(Boolean areaStat) {
        this.setAreaStat(areaStat);
        return this;
    }

    public void setAreaStat(Boolean areaStat) {
        this.areaStat = areaStat;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Area event(Event event) {
        this.setEvent(event);
        return this;
    }

    public Set<Fonction> getFonctions() {
        return this.fonctions;
    }

    public void setFonctions(Set<Fonction> fonctions) {
        if (this.fonctions != null) {
            this.fonctions.forEach(i -> i.removeArea(this));
        }
        if (fonctions != null) {
            fonctions.forEach(i -> i.addArea(this));
        }
        this.fonctions = fonctions;
    }

    public Area fonctions(Set<Fonction> fonctions) {
        this.setFonctions(fonctions);
        return this;
    }

    public Area addFonction(Fonction fonction) {
        this.fonctions.add(fonction);
        fonction.getAreas().add(this);
        return this;
    }

    public Area removeFonction(Fonction fonction) {
        this.fonctions.remove(fonction);
        fonction.getAreas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Area)) {
            return false;
        }
        return areaId != null && areaId.equals(((Area) o).areaId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Area{" +
            "areaId=" + getAreaId() +
            ", areaName='" + getAreaName() + "'" +
            ", areaAbreviation='" + getAreaAbreviation() + "'" +
            ", areaColor='" + getAreaColor() + "'" +
            ", areaDescription='" + getAreaDescription() + "'" +
            ", areaLogo='" + getAreaLogo() + "'" +
            ", areaLogoContentType='" + getAreaLogoContentType() + "'" +
            ", areaParams='" + getAreaParams() + "'" +
            ", areaAttributs='" + getAreaAttributs() + "'" +
            ", areaStat='" + getAreaStat() + "'" +
            "}";
    }
}

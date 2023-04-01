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
 * A Fonction.
 */
@Entity
@Table(name = "fonction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fonction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fonction_id")
    private Long fonctionId;

    @NotNull
    @Size(max = 50)
    @Column(name = "fonction_name", length = 50, nullable = false)
    private String fonctionName;

    @Size(max = 10)
    @Column(name = "fonction_abreviation", length = 10)
    private String fonctionAbreviation;

    @Size(max = 100)
    @Column(name = "fonction_color", length = 100)
    private String fonctionColor;

    @Size(max = 200)
    @Column(name = "fonction_description", length = 200)
    private String fonctionDescription;

    @Lob
    @Column(name = "fonction_logo")
    private byte[] fonctionLogo;

    @Column(name = "fonction_logo_content_type")
    private String fonctionLogoContentType;

    @Column(name = "fonction_params")
    private String fonctionParams;

    @Column(name = "fonction_attributs")
    private String fonctionAttributs;

    @Column(name = "fonction_stat")
    private Boolean fonctionStat;

    @OneToMany(mappedBy = "fonction")
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

    @ManyToMany
    @JoinTable(
        name = "rel_fonction__area",
        joinColumns = @JoinColumn(name = "fonction_fonction_id"),
        inverseJoinColumns = @JoinColumn(name = "area_area_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "event", "fonctions" }, allowSetters = true)
    private Set<Area> areas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "fonctions", "accreditations", "printingModel", "event" }, allowSetters = true)
    private Category category;

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

    public Long getFonctionId() {
        return this.fonctionId;
    }

    public Fonction fonctionId(Long fonctionId) {
        this.setFonctionId(fonctionId);
        return this;
    }

    public void setFonctionId(Long fonctionId) {
        this.fonctionId = fonctionId;
    }

    public String getFonctionName() {
        return this.fonctionName;
    }

    public Fonction fonctionName(String fonctionName) {
        this.setFonctionName(fonctionName);
        return this;
    }

    public void setFonctionName(String fonctionName) {
        this.fonctionName = fonctionName;
    }

    public String getFonctionAbreviation() {
        return this.fonctionAbreviation;
    }

    public Fonction fonctionAbreviation(String fonctionAbreviation) {
        this.setFonctionAbreviation(fonctionAbreviation);
        return this;
    }

    public void setFonctionAbreviation(String fonctionAbreviation) {
        this.fonctionAbreviation = fonctionAbreviation;
    }

    public String getFonctionColor() {
        return this.fonctionColor;
    }

    public Fonction fonctionColor(String fonctionColor) {
        this.setFonctionColor(fonctionColor);
        return this;
    }

    public void setFonctionColor(String fonctionColor) {
        this.fonctionColor = fonctionColor;
    }

    public String getFonctionDescription() {
        return this.fonctionDescription;
    }

    public Fonction fonctionDescription(String fonctionDescription) {
        this.setFonctionDescription(fonctionDescription);
        return this;
    }

    public void setFonctionDescription(String fonctionDescription) {
        this.fonctionDescription = fonctionDescription;
    }

    public byte[] getFonctionLogo() {
        return this.fonctionLogo;
    }

    public Fonction fonctionLogo(byte[] fonctionLogo) {
        this.setFonctionLogo(fonctionLogo);
        return this;
    }

    public void setFonctionLogo(byte[] fonctionLogo) {
        this.fonctionLogo = fonctionLogo;
    }

    public String getFonctionLogoContentType() {
        return this.fonctionLogoContentType;
    }

    public Fonction fonctionLogoContentType(String fonctionLogoContentType) {
        this.fonctionLogoContentType = fonctionLogoContentType;
        return this;
    }

    public void setFonctionLogoContentType(String fonctionLogoContentType) {
        this.fonctionLogoContentType = fonctionLogoContentType;
    }

    public String getFonctionParams() {
        return this.fonctionParams;
    }

    public Fonction fonctionParams(String fonctionParams) {
        this.setFonctionParams(fonctionParams);
        return this;
    }

    public void setFonctionParams(String fonctionParams) {
        this.fonctionParams = fonctionParams;
    }

    public String getFonctionAttributs() {
        return this.fonctionAttributs;
    }

    public Fonction fonctionAttributs(String fonctionAttributs) {
        this.setFonctionAttributs(fonctionAttributs);
        return this;
    }

    public void setFonctionAttributs(String fonctionAttributs) {
        this.fonctionAttributs = fonctionAttributs;
    }

    public Boolean getFonctionStat() {
        return this.fonctionStat;
    }

    public Fonction fonctionStat(Boolean fonctionStat) {
        this.setFonctionStat(fonctionStat);
        return this;
    }

    public void setFonctionStat(Boolean fonctionStat) {
        this.fonctionStat = fonctionStat;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setFonction(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setFonction(this));
        }
        this.accreditations = accreditations;
    }

    public Fonction accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public Fonction addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setFonction(this);
        return this;
    }

    public Fonction removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setFonction(null);
        return this;
    }

    public Set<Area> getAreas() {
        return this.areas;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
    }

    public Fonction areas(Set<Area> areas) {
        this.setAreas(areas);
        return this;
    }

    public Fonction addArea(Area area) {
        this.areas.add(area);
        area.getFonctions().add(this);
        return this;
    }

    public Fonction removeArea(Area area) {
        this.areas.remove(area);
        area.getFonctions().remove(this);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Fonction category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Fonction event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fonction)) {
            return false;
        }
        return fonctionId != null && fonctionId.equals(((Fonction) o).fonctionId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fonction{" +
            "fonctionId=" + getFonctionId() +
            ", fonctionName='" + getFonctionName() + "'" +
            ", fonctionAbreviation='" + getFonctionAbreviation() + "'" +
            ", fonctionColor='" + getFonctionColor() + "'" +
            ", fonctionDescription='" + getFonctionDescription() + "'" +
            ", fonctionLogo='" + getFonctionLogo() + "'" +
            ", fonctionLogoContentType='" + getFonctionLogoContentType() + "'" +
            ", fonctionParams='" + getFonctionParams() + "'" +
            ", fonctionAttributs='" + getFonctionAttributs() + "'" +
            ", fonctionStat='" + getFonctionStat() + "'" +
            "}";
    }
}

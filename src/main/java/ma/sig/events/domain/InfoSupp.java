package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InfoSupp.
 */
@Entity
@Table(name = "info_supp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InfoSupp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_supp_id")
    private Long infoSuppId;

    @NotNull
    @Size(max = 50)
    @Column(name = "info_supp_name", length = 50, nullable = false)
    private String infoSuppName;

    @Size(max = 200)
    @Column(name = "info_supp_description", length = 200)
    private String infoSuppDescription;

    @Column(name = "info_supp_params")
    private String infoSuppParams;

    @Column(name = "info_supp_attributs")
    private String infoSuppAttributs;

    @Column(name = "info_supp_stat")
    private Boolean infoSuppStat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "infoSupps" }, allowSetters = true)
    private InfoSuppType infoSuppType;

    @ManyToOne
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
    private Accreditation accreditation;

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

    public Long getInfoSuppId() {
        return this.infoSuppId;
    }

    public InfoSupp infoSuppId(Long infoSuppId) {
        this.setInfoSuppId(infoSuppId);
        return this;
    }

    public void setInfoSuppId(Long infoSuppId) {
        this.infoSuppId = infoSuppId;
    }

    public String getInfoSuppName() {
        return this.infoSuppName;
    }

    public InfoSupp infoSuppName(String infoSuppName) {
        this.setInfoSuppName(infoSuppName);
        return this;
    }

    public void setInfoSuppName(String infoSuppName) {
        this.infoSuppName = infoSuppName;
    }

    public String getInfoSuppDescription() {
        return this.infoSuppDescription;
    }

    public InfoSupp infoSuppDescription(String infoSuppDescription) {
        this.setInfoSuppDescription(infoSuppDescription);
        return this;
    }

    public void setInfoSuppDescription(String infoSuppDescription) {
        this.infoSuppDescription = infoSuppDescription;
    }

    public String getInfoSuppParams() {
        return this.infoSuppParams;
    }

    public InfoSupp infoSuppParams(String infoSuppParams) {
        this.setInfoSuppParams(infoSuppParams);
        return this;
    }

    public void setInfoSuppParams(String infoSuppParams) {
        this.infoSuppParams = infoSuppParams;
    }

    public String getInfoSuppAttributs() {
        return this.infoSuppAttributs;
    }

    public InfoSupp infoSuppAttributs(String infoSuppAttributs) {
        this.setInfoSuppAttributs(infoSuppAttributs);
        return this;
    }

    public void setInfoSuppAttributs(String infoSuppAttributs) {
        this.infoSuppAttributs = infoSuppAttributs;
    }

    public Boolean getInfoSuppStat() {
        return this.infoSuppStat;
    }

    public InfoSupp infoSuppStat(Boolean infoSuppStat) {
        this.setInfoSuppStat(infoSuppStat);
        return this;
    }

    public void setInfoSuppStat(Boolean infoSuppStat) {
        this.infoSuppStat = infoSuppStat;
    }

    public InfoSuppType getInfoSuppType() {
        return this.infoSuppType;
    }

    public void setInfoSuppType(InfoSuppType infoSuppType) {
        this.infoSuppType = infoSuppType;
    }

    public InfoSupp infoSuppType(InfoSuppType infoSuppType) {
        this.setInfoSuppType(infoSuppType);
        return this;
    }

    public Accreditation getAccreditation() {
        return this.accreditation;
    }

    public void setAccreditation(Accreditation accreditation) {
        this.accreditation = accreditation;
    }

    public InfoSupp accreditation(Accreditation accreditation) {
        this.setAccreditation(accreditation);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public InfoSupp event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InfoSupp)) {
            return false;
        }
        return infoSuppId != null && infoSuppId.equals(((InfoSupp) o).infoSuppId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfoSupp{" +
            "infoSuppId=" + getInfoSuppId() +
            ", infoSuppName='" + getInfoSuppName() + "'" +
            ", infoSuppDescription='" + getInfoSuppDescription() + "'" +
            ", infoSuppParams='" + getInfoSuppParams() + "'" +
            ", infoSuppAttributs='" + getInfoSuppAttributs() + "'" +
            ", infoSuppStat='" + getInfoSuppStat() + "'" +
            "}";
    }
}

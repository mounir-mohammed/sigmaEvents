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
 * A AccreditationType.
 */
@Entity
@Table(name = "accreditation_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccreditationType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accreditation_type_id")
    private Long accreditationTypeId;

    @NotNull
    @Size(max = 50)
    @Column(name = "accreditation_type_value", length = 50, nullable = false)
    private String accreditationTypeValue;

    @Size(max = 10)
    @Column(name = "accreditation_type_abreviation", length = 10)
    private String accreditationTypeAbreviation;

    @Size(max = 200)
    @Column(name = "accreditation_type_description", length = 200)
    private String accreditationTypeDescription;

    @Column(name = "accreditation_type_params")
    private String accreditationTypeParams;

    @Column(name = "accreditation_type_attributs")
    private String accreditationTypeAttributs;

    @Column(name = "accreditation_type_stat")
    private Boolean accreditationTypeStat;

    @OneToMany(mappedBy = "accreditationType")
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
    @JsonIgnoreProperties(value = { "printingCentres", "accreditationTypes", "categories", "event" }, allowSetters = true)
    private PrintingModel printingModel;

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

    public Long getAccreditationTypeId() {
        return this.accreditationTypeId;
    }

    public AccreditationType accreditationTypeId(Long accreditationTypeId) {
        this.setAccreditationTypeId(accreditationTypeId);
        return this;
    }

    public void setAccreditationTypeId(Long accreditationTypeId) {
        this.accreditationTypeId = accreditationTypeId;
    }

    public String getAccreditationTypeValue() {
        return this.accreditationTypeValue;
    }

    public AccreditationType accreditationTypeValue(String accreditationTypeValue) {
        this.setAccreditationTypeValue(accreditationTypeValue);
        return this;
    }

    public void setAccreditationTypeValue(String accreditationTypeValue) {
        this.accreditationTypeValue = accreditationTypeValue;
    }

    public String getAccreditationTypeAbreviation() {
        return this.accreditationTypeAbreviation;
    }

    public AccreditationType accreditationTypeAbreviation(String accreditationTypeAbreviation) {
        this.setAccreditationTypeAbreviation(accreditationTypeAbreviation);
        return this;
    }

    public void setAccreditationTypeAbreviation(String accreditationTypeAbreviation) {
        this.accreditationTypeAbreviation = accreditationTypeAbreviation;
    }

    public String getAccreditationTypeDescription() {
        return this.accreditationTypeDescription;
    }

    public AccreditationType accreditationTypeDescription(String accreditationTypeDescription) {
        this.setAccreditationTypeDescription(accreditationTypeDescription);
        return this;
    }

    public void setAccreditationTypeDescription(String accreditationTypeDescription) {
        this.accreditationTypeDescription = accreditationTypeDescription;
    }

    public String getAccreditationTypeParams() {
        return this.accreditationTypeParams;
    }

    public AccreditationType accreditationTypeParams(String accreditationTypeParams) {
        this.setAccreditationTypeParams(accreditationTypeParams);
        return this;
    }

    public void setAccreditationTypeParams(String accreditationTypeParams) {
        this.accreditationTypeParams = accreditationTypeParams;
    }

    public String getAccreditationTypeAttributs() {
        return this.accreditationTypeAttributs;
    }

    public AccreditationType accreditationTypeAttributs(String accreditationTypeAttributs) {
        this.setAccreditationTypeAttributs(accreditationTypeAttributs);
        return this;
    }

    public void setAccreditationTypeAttributs(String accreditationTypeAttributs) {
        this.accreditationTypeAttributs = accreditationTypeAttributs;
    }

    public Boolean getAccreditationTypeStat() {
        return this.accreditationTypeStat;
    }

    public AccreditationType accreditationTypeStat(Boolean accreditationTypeStat) {
        this.setAccreditationTypeStat(accreditationTypeStat);
        return this;
    }

    public void setAccreditationTypeStat(Boolean accreditationTypeStat) {
        this.accreditationTypeStat = accreditationTypeStat;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setAccreditationType(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setAccreditationType(this));
        }
        this.accreditations = accreditations;
    }

    public AccreditationType accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public AccreditationType addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setAccreditationType(this);
        return this;
    }

    public AccreditationType removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setAccreditationType(null);
        return this;
    }

    public PrintingModel getPrintingModel() {
        return this.printingModel;
    }

    public void setPrintingModel(PrintingModel printingModel) {
        this.printingModel = printingModel;
    }

    public AccreditationType printingModel(PrintingModel printingModel) {
        this.setPrintingModel(printingModel);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public AccreditationType event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccreditationType)) {
            return false;
        }
        return accreditationTypeId != null && accreditationTypeId.equals(((AccreditationType) o).accreditationTypeId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccreditationType{" +
            "accreditationTypeId=" + getAccreditationTypeId() +
            ", accreditationTypeValue='" + getAccreditationTypeValue() + "'" +
            ", accreditationTypeAbreviation='" + getAccreditationTypeAbreviation() + "'" +
            ", accreditationTypeDescription='" + getAccreditationTypeDescription() + "'" +
            ", accreditationTypeParams='" + getAccreditationTypeParams() + "'" +
            ", accreditationTypeAttributs='" + getAccreditationTypeAttributs() + "'" +
            ", accreditationTypeStat='" + getAccreditationTypeStat() + "'" +
            "}";
    }
}

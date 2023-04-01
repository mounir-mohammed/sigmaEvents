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
 * A Status.
 */
@Entity
@Table(name = "status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long statusId;

    @NotNull
    @Size(max = 50)
    @Column(name = "status_name", length = 50, nullable = false)
    private String statusName;

    @Size(max = 10)
    @Column(name = "status_abreviation", length = 10)
    private String statusAbreviation;

    @Size(max = 100)
    @Column(name = "status_color", length = 100)
    private String statusColor;

    @Size(max = 200)
    @Column(name = "status_description", length = 200)
    private String statusDescription;

    @Column(name = "status_user_can_print")
    private Boolean statusUserCanPrint;

    @Column(name = "status_user_can_update")
    private Boolean statusUserCanUpdate;

    @Column(name = "status_user_can_validate")
    private Boolean statusUserCanValidate;

    @Column(name = "status_params")
    private String statusParams;

    @Column(name = "status_attributs")
    private String statusAttributs;

    @Column(name = "status_stat")
    private Boolean statusStat;

    @OneToMany(mappedBy = "status")
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

    public Long getStatusId() {
        return this.statusId;
    }

    public Status statusId(Long statusId) {
        this.setStatusId(statusId);
        return this;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public Status statusName(String statusName) {
        this.setStatusName(statusName);
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusAbreviation() {
        return this.statusAbreviation;
    }

    public Status statusAbreviation(String statusAbreviation) {
        this.setStatusAbreviation(statusAbreviation);
        return this;
    }

    public void setStatusAbreviation(String statusAbreviation) {
        this.statusAbreviation = statusAbreviation;
    }

    public String getStatusColor() {
        return this.statusColor;
    }

    public Status statusColor(String statusColor) {
        this.setStatusColor(statusColor);
        return this;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }

    public String getStatusDescription() {
        return this.statusDescription;
    }

    public Status statusDescription(String statusDescription) {
        this.setStatusDescription(statusDescription);
        return this;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Boolean getStatusUserCanPrint() {
        return this.statusUserCanPrint;
    }

    public Status statusUserCanPrint(Boolean statusUserCanPrint) {
        this.setStatusUserCanPrint(statusUserCanPrint);
        return this;
    }

    public void setStatusUserCanPrint(Boolean statusUserCanPrint) {
        this.statusUserCanPrint = statusUserCanPrint;
    }

    public Boolean getStatusUserCanUpdate() {
        return this.statusUserCanUpdate;
    }

    public Status statusUserCanUpdate(Boolean statusUserCanUpdate) {
        this.setStatusUserCanUpdate(statusUserCanUpdate);
        return this;
    }

    public void setStatusUserCanUpdate(Boolean statusUserCanUpdate) {
        this.statusUserCanUpdate = statusUserCanUpdate;
    }

    public Boolean getStatusUserCanValidate() {
        return this.statusUserCanValidate;
    }

    public Status statusUserCanValidate(Boolean statusUserCanValidate) {
        this.setStatusUserCanValidate(statusUserCanValidate);
        return this;
    }

    public void setStatusUserCanValidate(Boolean statusUserCanValidate) {
        this.statusUserCanValidate = statusUserCanValidate;
    }

    public String getStatusParams() {
        return this.statusParams;
    }

    public Status statusParams(String statusParams) {
        this.setStatusParams(statusParams);
        return this;
    }

    public void setStatusParams(String statusParams) {
        this.statusParams = statusParams;
    }

    public String getStatusAttributs() {
        return this.statusAttributs;
    }

    public Status statusAttributs(String statusAttributs) {
        this.setStatusAttributs(statusAttributs);
        return this;
    }

    public void setStatusAttributs(String statusAttributs) {
        this.statusAttributs = statusAttributs;
    }

    public Boolean getStatusStat() {
        return this.statusStat;
    }

    public Status statusStat(Boolean statusStat) {
        this.setStatusStat(statusStat);
        return this;
    }

    public void setStatusStat(Boolean statusStat) {
        this.statusStat = statusStat;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setStatus(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setStatus(this));
        }
        this.accreditations = accreditations;
    }

    public Status accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public Status addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setStatus(this);
        return this;
    }

    public Status removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setStatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Status)) {
            return false;
        }
        return statusId != null && statusId.equals(((Status) o).statusId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Status{" +
            "statusId=" + getStatusId() +
            ", statusName='" + getStatusName() + "'" +
            ", statusAbreviation='" + getStatusAbreviation() + "'" +
            ", statusColor='" + getStatusColor() + "'" +
            ", statusDescription='" + getStatusDescription() + "'" +
            ", statusUserCanPrint='" + getStatusUserCanPrint() + "'" +
            ", statusUserCanUpdate='" + getStatusUserCanUpdate() + "'" +
            ", statusUserCanValidate='" + getStatusUserCanValidate() + "'" +
            ", statusParams='" + getStatusParams() + "'" +
            ", statusAttributs='" + getStatusAttributs() + "'" +
            ", statusStat='" + getStatusStat() + "'" +
            "}";
    }
}

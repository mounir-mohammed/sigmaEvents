package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OperationHistory.
 */
@Entity
@Table(name = "operation_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperationHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_history_id")
    private Long operationHistoryId;

    @Size(max = 200)
    @Column(name = "operation_history_description", length = 200)
    private String operationHistoryDescription;

    @Column(name = "operation_history_date")
    private ZonedDateTime operationHistoryDate;

    @Column(name = "operation_history_user_id")
    private Long operationHistoryUserID;

    @Column(name = "operation_history_old_value")
    private String operationHistoryOldValue;

    @Column(name = "operation_history_new_value")
    private String operationHistoryNewValue;

    @Column(name = "operation_history_old_id")
    private Long operationHistoryOldId;

    @Column(name = "operation_history_new_id")
    private Long operationHistoryNewId;

    @Column(name = "operation_history_imported_file")
    private String operationHistoryImportedFile;

    @Column(name = "operation_history_imported_file_path")
    private String operationHistoryImportedFilePath;

    @Column(name = "operation_history_params")
    private String operationHistoryParams;

    @Column(name = "operation_history_attributs")
    private String operationHistoryAttributs;

    @Column(name = "operation_history_stat")
    private Boolean operationHistoryStat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "operationHistories" }, allowSetters = true)
    private OperationType typeoperation;

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

    public Long getOperationHistoryId() {
        return this.operationHistoryId;
    }

    public OperationHistory operationHistoryId(Long operationHistoryId) {
        this.setOperationHistoryId(operationHistoryId);
        return this;
    }

    public void setOperationHistoryId(Long operationHistoryId) {
        this.operationHistoryId = operationHistoryId;
    }

    public String getOperationHistoryDescription() {
        return this.operationHistoryDescription;
    }

    public OperationHistory operationHistoryDescription(String operationHistoryDescription) {
        this.setOperationHistoryDescription(operationHistoryDescription);
        return this;
    }

    public void setOperationHistoryDescription(String operationHistoryDescription) {
        this.operationHistoryDescription = operationHistoryDescription;
    }

    public ZonedDateTime getOperationHistoryDate() {
        return this.operationHistoryDate;
    }

    public OperationHistory operationHistoryDate(ZonedDateTime operationHistoryDate) {
        this.setOperationHistoryDate(operationHistoryDate);
        return this;
    }

    public void setOperationHistoryDate(ZonedDateTime operationHistoryDate) {
        this.operationHistoryDate = operationHistoryDate;
    }

    public Long getOperationHistoryUserID() {
        return this.operationHistoryUserID;
    }

    public OperationHistory operationHistoryUserID(Long operationHistoryUserID) {
        this.setOperationHistoryUserID(operationHistoryUserID);
        return this;
    }

    public void setOperationHistoryUserID(Long operationHistoryUserID) {
        this.operationHistoryUserID = operationHistoryUserID;
    }

    public String getOperationHistoryOldValue() {
        return this.operationHistoryOldValue;
    }

    public OperationHistory operationHistoryOldValue(String operationHistoryOldValue) {
        this.setOperationHistoryOldValue(operationHistoryOldValue);
        return this;
    }

    public void setOperationHistoryOldValue(String operationHistoryOldValue) {
        this.operationHistoryOldValue = operationHistoryOldValue;
    }

    public String getOperationHistoryNewValue() {
        return this.operationHistoryNewValue;
    }

    public OperationHistory operationHistoryNewValue(String operationHistoryNewValue) {
        this.setOperationHistoryNewValue(operationHistoryNewValue);
        return this;
    }

    public void setOperationHistoryNewValue(String operationHistoryNewValue) {
        this.operationHistoryNewValue = operationHistoryNewValue;
    }

    public Long getOperationHistoryOldId() {
        return this.operationHistoryOldId;
    }

    public OperationHistory operationHistoryOldId(Long operationHistoryOldId) {
        this.setOperationHistoryOldId(operationHistoryOldId);
        return this;
    }

    public void setOperationHistoryOldId(Long operationHistoryOldId) {
        this.operationHistoryOldId = operationHistoryOldId;
    }

    public Long getOperationHistoryNewId() {
        return this.operationHistoryNewId;
    }

    public OperationHistory operationHistoryNewId(Long operationHistoryNewId) {
        this.setOperationHistoryNewId(operationHistoryNewId);
        return this;
    }

    public void setOperationHistoryNewId(Long operationHistoryNewId) {
        this.operationHistoryNewId = operationHistoryNewId;
    }

    public String getOperationHistoryImportedFile() {
        return this.operationHistoryImportedFile;
    }

    public OperationHistory operationHistoryImportedFile(String operationHistoryImportedFile) {
        this.setOperationHistoryImportedFile(operationHistoryImportedFile);
        return this;
    }

    public void setOperationHistoryImportedFile(String operationHistoryImportedFile) {
        this.operationHistoryImportedFile = operationHistoryImportedFile;
    }

    public String getOperationHistoryImportedFilePath() {
        return this.operationHistoryImportedFilePath;
    }

    public OperationHistory operationHistoryImportedFilePath(String operationHistoryImportedFilePath) {
        this.setOperationHistoryImportedFilePath(operationHistoryImportedFilePath);
        return this;
    }

    public void setOperationHistoryImportedFilePath(String operationHistoryImportedFilePath) {
        this.operationHistoryImportedFilePath = operationHistoryImportedFilePath;
    }

    public String getOperationHistoryParams() {
        return this.operationHistoryParams;
    }

    public OperationHistory operationHistoryParams(String operationHistoryParams) {
        this.setOperationHistoryParams(operationHistoryParams);
        return this;
    }

    public void setOperationHistoryParams(String operationHistoryParams) {
        this.operationHistoryParams = operationHistoryParams;
    }

    public String getOperationHistoryAttributs() {
        return this.operationHistoryAttributs;
    }

    public OperationHistory operationHistoryAttributs(String operationHistoryAttributs) {
        this.setOperationHistoryAttributs(operationHistoryAttributs);
        return this;
    }

    public void setOperationHistoryAttributs(String operationHistoryAttributs) {
        this.operationHistoryAttributs = operationHistoryAttributs;
    }

    public Boolean getOperationHistoryStat() {
        return this.operationHistoryStat;
    }

    public OperationHistory operationHistoryStat(Boolean operationHistoryStat) {
        this.setOperationHistoryStat(operationHistoryStat);
        return this;
    }

    public void setOperationHistoryStat(Boolean operationHistoryStat) {
        this.operationHistoryStat = operationHistoryStat;
    }

    public OperationType getTypeoperation() {
        return this.typeoperation;
    }

    public void setTypeoperation(OperationType operationType) {
        this.typeoperation = operationType;
    }

    public OperationHistory typeoperation(OperationType operationType) {
        this.setTypeoperation(operationType);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public OperationHistory event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationHistory)) {
            return false;
        }
        return operationHistoryId != null && operationHistoryId.equals(((OperationHistory) o).operationHistoryId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationHistory{" +
            "operationHistoryId=" + getOperationHistoryId() +
            ", operationHistoryDescription='" + getOperationHistoryDescription() + "'" +
            ", operationHistoryDate='" + getOperationHistoryDate() + "'" +
            ", operationHistoryUserID=" + getOperationHistoryUserID() +
            ", operationHistoryOldValue='" + getOperationHistoryOldValue() + "'" +
            ", operationHistoryNewValue='" + getOperationHistoryNewValue() + "'" +
            ", operationHistoryOldId=" + getOperationHistoryOldId() +
            ", operationHistoryNewId=" + getOperationHistoryNewId() +
            ", operationHistoryImportedFile='" + getOperationHistoryImportedFile() + "'" +
            ", operationHistoryImportedFilePath='" + getOperationHistoryImportedFilePath() + "'" +
            ", operationHistoryParams='" + getOperationHistoryParams() + "'" +
            ", operationHistoryAttributs='" + getOperationHistoryAttributs() + "'" +
            ", operationHistoryStat='" + getOperationHistoryStat() + "'" +
            "}";
    }
}

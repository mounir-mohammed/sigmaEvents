package ma.sig.events.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.OperationHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperationHistoryDTO implements Serializable {

    private Long operationHistoryId;

    @Size(max = 200)
    private String operationHistoryDescription;

    private ZonedDateTime operationHistoryDate;

    private Long operationHistoryUserID;

    private String operationHistoryOldValue;

    private String operationHistoryNewValue;

    private Long operationHistoryOldId;

    private Long operationHistoryNewId;

    private String operationHistoryImportedFile;

    private String operationHistoryImportedFilePath;

    private String operationHistoryParams;

    private String operationHistoryAttributs;

    private Boolean operationHistoryStat;

    private OperationTypeDTO typeoperation;

    private EventDTO event;

    public Long getOperationHistoryId() {
        return operationHistoryId;
    }

    public void setOperationHistoryId(Long operationHistoryId) {
        this.operationHistoryId = operationHistoryId;
    }

    public String getOperationHistoryDescription() {
        return operationHistoryDescription;
    }

    public void setOperationHistoryDescription(String operationHistoryDescription) {
        this.operationHistoryDescription = operationHistoryDescription;
    }

    public ZonedDateTime getOperationHistoryDate() {
        return operationHistoryDate;
    }

    public void setOperationHistoryDate(ZonedDateTime operationHistoryDate) {
        this.operationHistoryDate = operationHistoryDate;
    }

    public Long getOperationHistoryUserID() {
        return operationHistoryUserID;
    }

    public void setOperationHistoryUserID(Long operationHistoryUserID) {
        this.operationHistoryUserID = operationHistoryUserID;
    }

    public String getOperationHistoryOldValue() {
        return operationHistoryOldValue;
    }

    public void setOperationHistoryOldValue(String operationHistoryOldValue) {
        this.operationHistoryOldValue = operationHistoryOldValue;
    }

    public String getOperationHistoryNewValue() {
        return operationHistoryNewValue;
    }

    public void setOperationHistoryNewValue(String operationHistoryNewValue) {
        this.operationHistoryNewValue = operationHistoryNewValue;
    }

    public Long getOperationHistoryOldId() {
        return operationHistoryOldId;
    }

    public void setOperationHistoryOldId(Long operationHistoryOldId) {
        this.operationHistoryOldId = operationHistoryOldId;
    }

    public Long getOperationHistoryNewId() {
        return operationHistoryNewId;
    }

    public void setOperationHistoryNewId(Long operationHistoryNewId) {
        this.operationHistoryNewId = operationHistoryNewId;
    }

    public String getOperationHistoryImportedFile() {
        return operationHistoryImportedFile;
    }

    public void setOperationHistoryImportedFile(String operationHistoryImportedFile) {
        this.operationHistoryImportedFile = operationHistoryImportedFile;
    }

    public String getOperationHistoryImportedFilePath() {
        return operationHistoryImportedFilePath;
    }

    public void setOperationHistoryImportedFilePath(String operationHistoryImportedFilePath) {
        this.operationHistoryImportedFilePath = operationHistoryImportedFilePath;
    }

    public String getOperationHistoryParams() {
        return operationHistoryParams;
    }

    public void setOperationHistoryParams(String operationHistoryParams) {
        this.operationHistoryParams = operationHistoryParams;
    }

    public String getOperationHistoryAttributs() {
        return operationHistoryAttributs;
    }

    public void setOperationHistoryAttributs(String operationHistoryAttributs) {
        this.operationHistoryAttributs = operationHistoryAttributs;
    }

    public Boolean getOperationHistoryStat() {
        return operationHistoryStat;
    }

    public void setOperationHistoryStat(Boolean operationHistoryStat) {
        this.operationHistoryStat = operationHistoryStat;
    }

    public OperationTypeDTO getTypeoperation() {
        return typeoperation;
    }

    public void setTypeoperation(OperationTypeDTO typeoperation) {
        this.typeoperation = typeoperation;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationHistoryDTO)) {
            return false;
        }

        OperationHistoryDTO operationHistoryDTO = (OperationHistoryDTO) o;
        if (this.operationHistoryId == null) {
            return false;
        }
        return Objects.equals(this.operationHistoryId, operationHistoryDTO.operationHistoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.operationHistoryId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationHistoryDTO{" +
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
            ", typeoperation=" + getTypeoperation() +
            ", event=" + getEvent() +
            "}";
    }
}

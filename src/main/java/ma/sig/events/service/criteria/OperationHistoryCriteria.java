package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.OperationHistory} entity. This class is used
 * in {@link ma.sig.events.web.rest.OperationHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /operation-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperationHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter operationHistoryId;

    private StringFilter operationHistoryDescription;

    private ZonedDateTimeFilter operationHistoryDate;

    private LongFilter operationHistoryUserID;

    private StringFilter operationHistoryOldValue;

    private StringFilter operationHistoryNewValue;

    private LongFilter operationHistoryOldId;

    private LongFilter operationHistoryNewId;

    private StringFilter operationHistoryImportedFile;

    private StringFilter operationHistoryImportedFilePath;

    private StringFilter operationHistoryParams;

    private StringFilter operationHistoryAttributs;

    private BooleanFilter operationHistoryStat;

    private LongFilter typeoperationId;

    private LongFilter eventId;

    private Boolean distinct;

    public OperationHistoryCriteria() {}

    public OperationHistoryCriteria(OperationHistoryCriteria other) {
        this.operationHistoryId = other.operationHistoryId == null ? null : other.operationHistoryId.copy();
        this.operationHistoryDescription = other.operationHistoryDescription == null ? null : other.operationHistoryDescription.copy();
        this.operationHistoryDate = other.operationHistoryDate == null ? null : other.operationHistoryDate.copy();
        this.operationHistoryUserID = other.operationHistoryUserID == null ? null : other.operationHistoryUserID.copy();
        this.operationHistoryOldValue = other.operationHistoryOldValue == null ? null : other.operationHistoryOldValue.copy();
        this.operationHistoryNewValue = other.operationHistoryNewValue == null ? null : other.operationHistoryNewValue.copy();
        this.operationHistoryOldId = other.operationHistoryOldId == null ? null : other.operationHistoryOldId.copy();
        this.operationHistoryNewId = other.operationHistoryNewId == null ? null : other.operationHistoryNewId.copy();
        this.operationHistoryImportedFile = other.operationHistoryImportedFile == null ? null : other.operationHistoryImportedFile.copy();
        this.operationHistoryImportedFilePath =
            other.operationHistoryImportedFilePath == null ? null : other.operationHistoryImportedFilePath.copy();
        this.operationHistoryParams = other.operationHistoryParams == null ? null : other.operationHistoryParams.copy();
        this.operationHistoryAttributs = other.operationHistoryAttributs == null ? null : other.operationHistoryAttributs.copy();
        this.operationHistoryStat = other.operationHistoryStat == null ? null : other.operationHistoryStat.copy();
        this.typeoperationId = other.typeoperationId == null ? null : other.typeoperationId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OperationHistoryCriteria copy() {
        return new OperationHistoryCriteria(this);
    }

    public LongFilter getOperationHistoryId() {
        return operationHistoryId;
    }

    public LongFilter operationHistoryId() {
        if (operationHistoryId == null) {
            operationHistoryId = new LongFilter();
        }
        return operationHistoryId;
    }

    public void setOperationHistoryId(LongFilter operationHistoryId) {
        this.operationHistoryId = operationHistoryId;
    }

    public StringFilter getOperationHistoryDescription() {
        return operationHistoryDescription;
    }

    public StringFilter operationHistoryDescription() {
        if (operationHistoryDescription == null) {
            operationHistoryDescription = new StringFilter();
        }
        return operationHistoryDescription;
    }

    public void setOperationHistoryDescription(StringFilter operationHistoryDescription) {
        this.operationHistoryDescription = operationHistoryDescription;
    }

    public ZonedDateTimeFilter getOperationHistoryDate() {
        return operationHistoryDate;
    }

    public ZonedDateTimeFilter operationHistoryDate() {
        if (operationHistoryDate == null) {
            operationHistoryDate = new ZonedDateTimeFilter();
        }
        return operationHistoryDate;
    }

    public void setOperationHistoryDate(ZonedDateTimeFilter operationHistoryDate) {
        this.operationHistoryDate = operationHistoryDate;
    }

    public LongFilter getOperationHistoryUserID() {
        return operationHistoryUserID;
    }

    public LongFilter operationHistoryUserID() {
        if (operationHistoryUserID == null) {
            operationHistoryUserID = new LongFilter();
        }
        return operationHistoryUserID;
    }

    public void setOperationHistoryUserID(LongFilter operationHistoryUserID) {
        this.operationHistoryUserID = operationHistoryUserID;
    }

    public StringFilter getOperationHistoryOldValue() {
        return operationHistoryOldValue;
    }

    public StringFilter operationHistoryOldValue() {
        if (operationHistoryOldValue == null) {
            operationHistoryOldValue = new StringFilter();
        }
        return operationHistoryOldValue;
    }

    public void setOperationHistoryOldValue(StringFilter operationHistoryOldValue) {
        this.operationHistoryOldValue = operationHistoryOldValue;
    }

    public StringFilter getOperationHistoryNewValue() {
        return operationHistoryNewValue;
    }

    public StringFilter operationHistoryNewValue() {
        if (operationHistoryNewValue == null) {
            operationHistoryNewValue = new StringFilter();
        }
        return operationHistoryNewValue;
    }

    public void setOperationHistoryNewValue(StringFilter operationHistoryNewValue) {
        this.operationHistoryNewValue = operationHistoryNewValue;
    }

    public LongFilter getOperationHistoryOldId() {
        return operationHistoryOldId;
    }

    public LongFilter operationHistoryOldId() {
        if (operationHistoryOldId == null) {
            operationHistoryOldId = new LongFilter();
        }
        return operationHistoryOldId;
    }

    public void setOperationHistoryOldId(LongFilter operationHistoryOldId) {
        this.operationHistoryOldId = operationHistoryOldId;
    }

    public LongFilter getOperationHistoryNewId() {
        return operationHistoryNewId;
    }

    public LongFilter operationHistoryNewId() {
        if (operationHistoryNewId == null) {
            operationHistoryNewId = new LongFilter();
        }
        return operationHistoryNewId;
    }

    public void setOperationHistoryNewId(LongFilter operationHistoryNewId) {
        this.operationHistoryNewId = operationHistoryNewId;
    }

    public StringFilter getOperationHistoryImportedFile() {
        return operationHistoryImportedFile;
    }

    public StringFilter operationHistoryImportedFile() {
        if (operationHistoryImportedFile == null) {
            operationHistoryImportedFile = new StringFilter();
        }
        return operationHistoryImportedFile;
    }

    public void setOperationHistoryImportedFile(StringFilter operationHistoryImportedFile) {
        this.operationHistoryImportedFile = operationHistoryImportedFile;
    }

    public StringFilter getOperationHistoryImportedFilePath() {
        return operationHistoryImportedFilePath;
    }

    public StringFilter operationHistoryImportedFilePath() {
        if (operationHistoryImportedFilePath == null) {
            operationHistoryImportedFilePath = new StringFilter();
        }
        return operationHistoryImportedFilePath;
    }

    public void setOperationHistoryImportedFilePath(StringFilter operationHistoryImportedFilePath) {
        this.operationHistoryImportedFilePath = operationHistoryImportedFilePath;
    }

    public StringFilter getOperationHistoryParams() {
        return operationHistoryParams;
    }

    public StringFilter operationHistoryParams() {
        if (operationHistoryParams == null) {
            operationHistoryParams = new StringFilter();
        }
        return operationHistoryParams;
    }

    public void setOperationHistoryParams(StringFilter operationHistoryParams) {
        this.operationHistoryParams = operationHistoryParams;
    }

    public StringFilter getOperationHistoryAttributs() {
        return operationHistoryAttributs;
    }

    public StringFilter operationHistoryAttributs() {
        if (operationHistoryAttributs == null) {
            operationHistoryAttributs = new StringFilter();
        }
        return operationHistoryAttributs;
    }

    public void setOperationHistoryAttributs(StringFilter operationHistoryAttributs) {
        this.operationHistoryAttributs = operationHistoryAttributs;
    }

    public BooleanFilter getOperationHistoryStat() {
        return operationHistoryStat;
    }

    public BooleanFilter operationHistoryStat() {
        if (operationHistoryStat == null) {
            operationHistoryStat = new BooleanFilter();
        }
        return operationHistoryStat;
    }

    public void setOperationHistoryStat(BooleanFilter operationHistoryStat) {
        this.operationHistoryStat = operationHistoryStat;
    }

    public LongFilter getTypeoperationId() {
        return typeoperationId;
    }

    public LongFilter typeoperationId() {
        if (typeoperationId == null) {
            typeoperationId = new LongFilter();
        }
        return typeoperationId;
    }

    public void setTypeoperationId(LongFilter typeoperationId) {
        this.typeoperationId = typeoperationId;
    }

    public LongFilter getEventId() {
        return eventId;
    }

    public LongFilter eventId() {
        if (eventId == null) {
            eventId = new LongFilter();
        }
        return eventId;
    }

    public void setEventId(LongFilter eventId) {
        this.eventId = eventId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OperationHistoryCriteria that = (OperationHistoryCriteria) o;
        return (
            Objects.equals(operationHistoryId, that.operationHistoryId) &&
            Objects.equals(operationHistoryDescription, that.operationHistoryDescription) &&
            Objects.equals(operationHistoryDate, that.operationHistoryDate) &&
            Objects.equals(operationHistoryUserID, that.operationHistoryUserID) &&
            Objects.equals(operationHistoryOldValue, that.operationHistoryOldValue) &&
            Objects.equals(operationHistoryNewValue, that.operationHistoryNewValue) &&
            Objects.equals(operationHistoryOldId, that.operationHistoryOldId) &&
            Objects.equals(operationHistoryNewId, that.operationHistoryNewId) &&
            Objects.equals(operationHistoryImportedFile, that.operationHistoryImportedFile) &&
            Objects.equals(operationHistoryImportedFilePath, that.operationHistoryImportedFilePath) &&
            Objects.equals(operationHistoryParams, that.operationHistoryParams) &&
            Objects.equals(operationHistoryAttributs, that.operationHistoryAttributs) &&
            Objects.equals(operationHistoryStat, that.operationHistoryStat) &&
            Objects.equals(typeoperationId, that.typeoperationId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            operationHistoryId,
            operationHistoryDescription,
            operationHistoryDate,
            operationHistoryUserID,
            operationHistoryOldValue,
            operationHistoryNewValue,
            operationHistoryOldId,
            operationHistoryNewId,
            operationHistoryImportedFile,
            operationHistoryImportedFilePath,
            operationHistoryParams,
            operationHistoryAttributs,
            operationHistoryStat,
            typeoperationId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationHistoryCriteria{" +
            (operationHistoryId != null ? "operationHistoryId=" + operationHistoryId + ", " : "") +
            (operationHistoryDescription != null ? "operationHistoryDescription=" + operationHistoryDescription + ", " : "") +
            (operationHistoryDate != null ? "operationHistoryDate=" + operationHistoryDate + ", " : "") +
            (operationHistoryUserID != null ? "operationHistoryUserID=" + operationHistoryUserID + ", " : "") +
            (operationHistoryOldValue != null ? "operationHistoryOldValue=" + operationHistoryOldValue + ", " : "") +
            (operationHistoryNewValue != null ? "operationHistoryNewValue=" + operationHistoryNewValue + ", " : "") +
            (operationHistoryOldId != null ? "operationHistoryOldId=" + operationHistoryOldId + ", " : "") +
            (operationHistoryNewId != null ? "operationHistoryNewId=" + operationHistoryNewId + ", " : "") +
            (operationHistoryImportedFile != null ? "operationHistoryImportedFile=" + operationHistoryImportedFile + ", " : "") +
            (operationHistoryImportedFilePath != null ? "operationHistoryImportedFilePath=" + operationHistoryImportedFilePath + ", " : "") +
            (operationHistoryParams != null ? "operationHistoryParams=" + operationHistoryParams + ", " : "") +
            (operationHistoryAttributs != null ? "operationHistoryAttributs=" + operationHistoryAttributs + ", " : "") +
            (operationHistoryStat != null ? "operationHistoryStat=" + operationHistoryStat + ", " : "") +
            (typeoperationId != null ? "typeoperationId=" + typeoperationId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

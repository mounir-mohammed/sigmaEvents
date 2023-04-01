package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.OperationType} entity. This class is used
 * in {@link ma.sig.events.web.rest.OperationTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /operation-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperationTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter operationTypeId;

    private StringFilter operationTypeValue;

    private StringFilter operationTypeDescription;

    private StringFilter operationTypeParams;

    private StringFilter operationTypeAttributs;

    private BooleanFilter operationTypeStat;

    private LongFilter operationHistoryId;

    private Boolean distinct;

    public OperationTypeCriteria() {}

    public OperationTypeCriteria(OperationTypeCriteria other) {
        this.operationTypeId = other.operationTypeId == null ? null : other.operationTypeId.copy();
        this.operationTypeValue = other.operationTypeValue == null ? null : other.operationTypeValue.copy();
        this.operationTypeDescription = other.operationTypeDescription == null ? null : other.operationTypeDescription.copy();
        this.operationTypeParams = other.operationTypeParams == null ? null : other.operationTypeParams.copy();
        this.operationTypeAttributs = other.operationTypeAttributs == null ? null : other.operationTypeAttributs.copy();
        this.operationTypeStat = other.operationTypeStat == null ? null : other.operationTypeStat.copy();
        this.operationHistoryId = other.operationHistoryId == null ? null : other.operationHistoryId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OperationTypeCriteria copy() {
        return new OperationTypeCriteria(this);
    }

    public LongFilter getOperationTypeId() {
        return operationTypeId;
    }

    public LongFilter operationTypeId() {
        if (operationTypeId == null) {
            operationTypeId = new LongFilter();
        }
        return operationTypeId;
    }

    public void setOperationTypeId(LongFilter operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public StringFilter getOperationTypeValue() {
        return operationTypeValue;
    }

    public StringFilter operationTypeValue() {
        if (operationTypeValue == null) {
            operationTypeValue = new StringFilter();
        }
        return operationTypeValue;
    }

    public void setOperationTypeValue(StringFilter operationTypeValue) {
        this.operationTypeValue = operationTypeValue;
    }

    public StringFilter getOperationTypeDescription() {
        return operationTypeDescription;
    }

    public StringFilter operationTypeDescription() {
        if (operationTypeDescription == null) {
            operationTypeDescription = new StringFilter();
        }
        return operationTypeDescription;
    }

    public void setOperationTypeDescription(StringFilter operationTypeDescription) {
        this.operationTypeDescription = operationTypeDescription;
    }

    public StringFilter getOperationTypeParams() {
        return operationTypeParams;
    }

    public StringFilter operationTypeParams() {
        if (operationTypeParams == null) {
            operationTypeParams = new StringFilter();
        }
        return operationTypeParams;
    }

    public void setOperationTypeParams(StringFilter operationTypeParams) {
        this.operationTypeParams = operationTypeParams;
    }

    public StringFilter getOperationTypeAttributs() {
        return operationTypeAttributs;
    }

    public StringFilter operationTypeAttributs() {
        if (operationTypeAttributs == null) {
            operationTypeAttributs = new StringFilter();
        }
        return operationTypeAttributs;
    }

    public void setOperationTypeAttributs(StringFilter operationTypeAttributs) {
        this.operationTypeAttributs = operationTypeAttributs;
    }

    public BooleanFilter getOperationTypeStat() {
        return operationTypeStat;
    }

    public BooleanFilter operationTypeStat() {
        if (operationTypeStat == null) {
            operationTypeStat = new BooleanFilter();
        }
        return operationTypeStat;
    }

    public void setOperationTypeStat(BooleanFilter operationTypeStat) {
        this.operationTypeStat = operationTypeStat;
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
        final OperationTypeCriteria that = (OperationTypeCriteria) o;
        return (
            Objects.equals(operationTypeId, that.operationTypeId) &&
            Objects.equals(operationTypeValue, that.operationTypeValue) &&
            Objects.equals(operationTypeDescription, that.operationTypeDescription) &&
            Objects.equals(operationTypeParams, that.operationTypeParams) &&
            Objects.equals(operationTypeAttributs, that.operationTypeAttributs) &&
            Objects.equals(operationTypeStat, that.operationTypeStat) &&
            Objects.equals(operationHistoryId, that.operationHistoryId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            operationTypeId,
            operationTypeValue,
            operationTypeDescription,
            operationTypeParams,
            operationTypeAttributs,
            operationTypeStat,
            operationHistoryId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationTypeCriteria{" +
            (operationTypeId != null ? "operationTypeId=" + operationTypeId + ", " : "") +
            (operationTypeValue != null ? "operationTypeValue=" + operationTypeValue + ", " : "") +
            (operationTypeDescription != null ? "operationTypeDescription=" + operationTypeDescription + ", " : "") +
            (operationTypeParams != null ? "operationTypeParams=" + operationTypeParams + ", " : "") +
            (operationTypeAttributs != null ? "operationTypeAttributs=" + operationTypeAttributs + ", " : "") +
            (operationTypeStat != null ? "operationTypeStat=" + operationTypeStat + ", " : "") +
            (operationHistoryId != null ? "operationHistoryId=" + operationHistoryId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

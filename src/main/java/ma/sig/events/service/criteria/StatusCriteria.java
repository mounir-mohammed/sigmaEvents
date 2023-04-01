package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Status} entity. This class is used
 * in {@link ma.sig.events.web.rest.StatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter statusId;

    private StringFilter statusName;

    private StringFilter statusAbreviation;

    private StringFilter statusColor;

    private StringFilter statusDescription;

    private BooleanFilter statusUserCanPrint;

    private BooleanFilter statusUserCanUpdate;

    private BooleanFilter statusUserCanValidate;

    private StringFilter statusParams;

    private StringFilter statusAttributs;

    private BooleanFilter statusStat;

    private LongFilter accreditationId;

    private Boolean distinct;

    public StatusCriteria() {}

    public StatusCriteria(StatusCriteria other) {
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.statusName = other.statusName == null ? null : other.statusName.copy();
        this.statusAbreviation = other.statusAbreviation == null ? null : other.statusAbreviation.copy();
        this.statusColor = other.statusColor == null ? null : other.statusColor.copy();
        this.statusDescription = other.statusDescription == null ? null : other.statusDescription.copy();
        this.statusUserCanPrint = other.statusUserCanPrint == null ? null : other.statusUserCanPrint.copy();
        this.statusUserCanUpdate = other.statusUserCanUpdate == null ? null : other.statusUserCanUpdate.copy();
        this.statusUserCanValidate = other.statusUserCanValidate == null ? null : other.statusUserCanValidate.copy();
        this.statusParams = other.statusParams == null ? null : other.statusParams.copy();
        this.statusAttributs = other.statusAttributs == null ? null : other.statusAttributs.copy();
        this.statusStat = other.statusStat == null ? null : other.statusStat.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StatusCriteria copy() {
        return new StatusCriteria(this);
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public LongFilter statusId() {
        if (statusId == null) {
            statusId = new LongFilter();
        }
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public StringFilter getStatusName() {
        return statusName;
    }

    public StringFilter statusName() {
        if (statusName == null) {
            statusName = new StringFilter();
        }
        return statusName;
    }

    public void setStatusName(StringFilter statusName) {
        this.statusName = statusName;
    }

    public StringFilter getStatusAbreviation() {
        return statusAbreviation;
    }

    public StringFilter statusAbreviation() {
        if (statusAbreviation == null) {
            statusAbreviation = new StringFilter();
        }
        return statusAbreviation;
    }

    public void setStatusAbreviation(StringFilter statusAbreviation) {
        this.statusAbreviation = statusAbreviation;
    }

    public StringFilter getStatusColor() {
        return statusColor;
    }

    public StringFilter statusColor() {
        if (statusColor == null) {
            statusColor = new StringFilter();
        }
        return statusColor;
    }

    public void setStatusColor(StringFilter statusColor) {
        this.statusColor = statusColor;
    }

    public StringFilter getStatusDescription() {
        return statusDescription;
    }

    public StringFilter statusDescription() {
        if (statusDescription == null) {
            statusDescription = new StringFilter();
        }
        return statusDescription;
    }

    public void setStatusDescription(StringFilter statusDescription) {
        this.statusDescription = statusDescription;
    }

    public BooleanFilter getStatusUserCanPrint() {
        return statusUserCanPrint;
    }

    public BooleanFilter statusUserCanPrint() {
        if (statusUserCanPrint == null) {
            statusUserCanPrint = new BooleanFilter();
        }
        return statusUserCanPrint;
    }

    public void setStatusUserCanPrint(BooleanFilter statusUserCanPrint) {
        this.statusUserCanPrint = statusUserCanPrint;
    }

    public BooleanFilter getStatusUserCanUpdate() {
        return statusUserCanUpdate;
    }

    public BooleanFilter statusUserCanUpdate() {
        if (statusUserCanUpdate == null) {
            statusUserCanUpdate = new BooleanFilter();
        }
        return statusUserCanUpdate;
    }

    public void setStatusUserCanUpdate(BooleanFilter statusUserCanUpdate) {
        this.statusUserCanUpdate = statusUserCanUpdate;
    }

    public BooleanFilter getStatusUserCanValidate() {
        return statusUserCanValidate;
    }

    public BooleanFilter statusUserCanValidate() {
        if (statusUserCanValidate == null) {
            statusUserCanValidate = new BooleanFilter();
        }
        return statusUserCanValidate;
    }

    public void setStatusUserCanValidate(BooleanFilter statusUserCanValidate) {
        this.statusUserCanValidate = statusUserCanValidate;
    }

    public StringFilter getStatusParams() {
        return statusParams;
    }

    public StringFilter statusParams() {
        if (statusParams == null) {
            statusParams = new StringFilter();
        }
        return statusParams;
    }

    public void setStatusParams(StringFilter statusParams) {
        this.statusParams = statusParams;
    }

    public StringFilter getStatusAttributs() {
        return statusAttributs;
    }

    public StringFilter statusAttributs() {
        if (statusAttributs == null) {
            statusAttributs = new StringFilter();
        }
        return statusAttributs;
    }

    public void setStatusAttributs(StringFilter statusAttributs) {
        this.statusAttributs = statusAttributs;
    }

    public BooleanFilter getStatusStat() {
        return statusStat;
    }

    public BooleanFilter statusStat() {
        if (statusStat == null) {
            statusStat = new BooleanFilter();
        }
        return statusStat;
    }

    public void setStatusStat(BooleanFilter statusStat) {
        this.statusStat = statusStat;
    }

    public LongFilter getAccreditationId() {
        return accreditationId;
    }

    public LongFilter accreditationId() {
        if (accreditationId == null) {
            accreditationId = new LongFilter();
        }
        return accreditationId;
    }

    public void setAccreditationId(LongFilter accreditationId) {
        this.accreditationId = accreditationId;
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
        final StatusCriteria that = (StatusCriteria) o;
        return (
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(statusName, that.statusName) &&
            Objects.equals(statusAbreviation, that.statusAbreviation) &&
            Objects.equals(statusColor, that.statusColor) &&
            Objects.equals(statusDescription, that.statusDescription) &&
            Objects.equals(statusUserCanPrint, that.statusUserCanPrint) &&
            Objects.equals(statusUserCanUpdate, that.statusUserCanUpdate) &&
            Objects.equals(statusUserCanValidate, that.statusUserCanValidate) &&
            Objects.equals(statusParams, that.statusParams) &&
            Objects.equals(statusAttributs, that.statusAttributs) &&
            Objects.equals(statusStat, that.statusStat) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            statusId,
            statusName,
            statusAbreviation,
            statusColor,
            statusDescription,
            statusUserCanPrint,
            statusUserCanUpdate,
            statusUserCanValidate,
            statusParams,
            statusAttributs,
            statusStat,
            accreditationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusCriteria{" +
            (statusId != null ? "statusId=" + statusId + ", " : "") +
            (statusName != null ? "statusName=" + statusName + ", " : "") +
            (statusAbreviation != null ? "statusAbreviation=" + statusAbreviation + ", " : "") +
            (statusColor != null ? "statusColor=" + statusColor + ", " : "") +
            (statusDescription != null ? "statusDescription=" + statusDescription + ", " : "") +
            (statusUserCanPrint != null ? "statusUserCanPrint=" + statusUserCanPrint + ", " : "") +
            (statusUserCanUpdate != null ? "statusUserCanUpdate=" + statusUserCanUpdate + ", " : "") +
            (statusUserCanValidate != null ? "statusUserCanValidate=" + statusUserCanValidate + ", " : "") +
            (statusParams != null ? "statusParams=" + statusParams + ", " : "") +
            (statusAttributs != null ? "statusAttributs=" + statusAttributs + ", " : "") +
            (statusStat != null ? "statusStat=" + statusStat + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

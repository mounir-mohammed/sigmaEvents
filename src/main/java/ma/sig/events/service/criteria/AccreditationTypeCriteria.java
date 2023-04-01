package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.AccreditationType} entity. This class is used
 * in {@link ma.sig.events.web.rest.AccreditationTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /accreditation-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccreditationTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter accreditationTypeId;

    private StringFilter accreditationTypeValue;

    private StringFilter accreditationTypeAbreviation;

    private StringFilter accreditationTypeDescription;

    private StringFilter accreditationTypeParams;

    private StringFilter accreditationTypeAttributs;

    private BooleanFilter accreditationTypeStat;

    private LongFilter accreditationId;

    private LongFilter printingModelId;

    private LongFilter eventId;

    private Boolean distinct;

    public AccreditationTypeCriteria() {}

    public AccreditationTypeCriteria(AccreditationTypeCriteria other) {
        this.accreditationTypeId = other.accreditationTypeId == null ? null : other.accreditationTypeId.copy();
        this.accreditationTypeValue = other.accreditationTypeValue == null ? null : other.accreditationTypeValue.copy();
        this.accreditationTypeAbreviation = other.accreditationTypeAbreviation == null ? null : other.accreditationTypeAbreviation.copy();
        this.accreditationTypeDescription = other.accreditationTypeDescription == null ? null : other.accreditationTypeDescription.copy();
        this.accreditationTypeParams = other.accreditationTypeParams == null ? null : other.accreditationTypeParams.copy();
        this.accreditationTypeAttributs = other.accreditationTypeAttributs == null ? null : other.accreditationTypeAttributs.copy();
        this.accreditationTypeStat = other.accreditationTypeStat == null ? null : other.accreditationTypeStat.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.printingModelId = other.printingModelId == null ? null : other.printingModelId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AccreditationTypeCriteria copy() {
        return new AccreditationTypeCriteria(this);
    }

    public LongFilter getAccreditationTypeId() {
        return accreditationTypeId;
    }

    public LongFilter accreditationTypeId() {
        if (accreditationTypeId == null) {
            accreditationTypeId = new LongFilter();
        }
        return accreditationTypeId;
    }

    public void setAccreditationTypeId(LongFilter accreditationTypeId) {
        this.accreditationTypeId = accreditationTypeId;
    }

    public StringFilter getAccreditationTypeValue() {
        return accreditationTypeValue;
    }

    public StringFilter accreditationTypeValue() {
        if (accreditationTypeValue == null) {
            accreditationTypeValue = new StringFilter();
        }
        return accreditationTypeValue;
    }

    public void setAccreditationTypeValue(StringFilter accreditationTypeValue) {
        this.accreditationTypeValue = accreditationTypeValue;
    }

    public StringFilter getAccreditationTypeAbreviation() {
        return accreditationTypeAbreviation;
    }

    public StringFilter accreditationTypeAbreviation() {
        if (accreditationTypeAbreviation == null) {
            accreditationTypeAbreviation = new StringFilter();
        }
        return accreditationTypeAbreviation;
    }

    public void setAccreditationTypeAbreviation(StringFilter accreditationTypeAbreviation) {
        this.accreditationTypeAbreviation = accreditationTypeAbreviation;
    }

    public StringFilter getAccreditationTypeDescription() {
        return accreditationTypeDescription;
    }

    public StringFilter accreditationTypeDescription() {
        if (accreditationTypeDescription == null) {
            accreditationTypeDescription = new StringFilter();
        }
        return accreditationTypeDescription;
    }

    public void setAccreditationTypeDescription(StringFilter accreditationTypeDescription) {
        this.accreditationTypeDescription = accreditationTypeDescription;
    }

    public StringFilter getAccreditationTypeParams() {
        return accreditationTypeParams;
    }

    public StringFilter accreditationTypeParams() {
        if (accreditationTypeParams == null) {
            accreditationTypeParams = new StringFilter();
        }
        return accreditationTypeParams;
    }

    public void setAccreditationTypeParams(StringFilter accreditationTypeParams) {
        this.accreditationTypeParams = accreditationTypeParams;
    }

    public StringFilter getAccreditationTypeAttributs() {
        return accreditationTypeAttributs;
    }

    public StringFilter accreditationTypeAttributs() {
        if (accreditationTypeAttributs == null) {
            accreditationTypeAttributs = new StringFilter();
        }
        return accreditationTypeAttributs;
    }

    public void setAccreditationTypeAttributs(StringFilter accreditationTypeAttributs) {
        this.accreditationTypeAttributs = accreditationTypeAttributs;
    }

    public BooleanFilter getAccreditationTypeStat() {
        return accreditationTypeStat;
    }

    public BooleanFilter accreditationTypeStat() {
        if (accreditationTypeStat == null) {
            accreditationTypeStat = new BooleanFilter();
        }
        return accreditationTypeStat;
    }

    public void setAccreditationTypeStat(BooleanFilter accreditationTypeStat) {
        this.accreditationTypeStat = accreditationTypeStat;
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

    public LongFilter getPrintingModelId() {
        return printingModelId;
    }

    public LongFilter printingModelId() {
        if (printingModelId == null) {
            printingModelId = new LongFilter();
        }
        return printingModelId;
    }

    public void setPrintingModelId(LongFilter printingModelId) {
        this.printingModelId = printingModelId;
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
        final AccreditationTypeCriteria that = (AccreditationTypeCriteria) o;
        return (
            Objects.equals(accreditationTypeId, that.accreditationTypeId) &&
            Objects.equals(accreditationTypeValue, that.accreditationTypeValue) &&
            Objects.equals(accreditationTypeAbreviation, that.accreditationTypeAbreviation) &&
            Objects.equals(accreditationTypeDescription, that.accreditationTypeDescription) &&
            Objects.equals(accreditationTypeParams, that.accreditationTypeParams) &&
            Objects.equals(accreditationTypeAttributs, that.accreditationTypeAttributs) &&
            Objects.equals(accreditationTypeStat, that.accreditationTypeStat) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(printingModelId, that.printingModelId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            accreditationTypeId,
            accreditationTypeValue,
            accreditationTypeAbreviation,
            accreditationTypeDescription,
            accreditationTypeParams,
            accreditationTypeAttributs,
            accreditationTypeStat,
            accreditationId,
            printingModelId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccreditationTypeCriteria{" +
            (accreditationTypeId != null ? "accreditationTypeId=" + accreditationTypeId + ", " : "") +
            (accreditationTypeValue != null ? "accreditationTypeValue=" + accreditationTypeValue + ", " : "") +
            (accreditationTypeAbreviation != null ? "accreditationTypeAbreviation=" + accreditationTypeAbreviation + ", " : "") +
            (accreditationTypeDescription != null ? "accreditationTypeDescription=" + accreditationTypeDescription + ", " : "") +
            (accreditationTypeParams != null ? "accreditationTypeParams=" + accreditationTypeParams + ", " : "") +
            (accreditationTypeAttributs != null ? "accreditationTypeAttributs=" + accreditationTypeAttributs + ", " : "") +
            (accreditationTypeStat != null ? "accreditationTypeStat=" + accreditationTypeStat + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (printingModelId != null ? "printingModelId=" + printingModelId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

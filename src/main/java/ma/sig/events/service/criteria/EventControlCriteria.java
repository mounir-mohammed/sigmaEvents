package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.EventControl} entity. This class is used
 * in {@link ma.sig.events.web.rest.EventControlResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /event-controls?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventControlCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter controlId;

    private StringFilter controlName;

    private StringFilter controlDescription;

    private StringFilter controlType;

    private StringFilter controlValueString;

    private LongFilter controlValueLong;

    private ZonedDateTimeFilter controlValueDate;

    private StringFilter controlParams;

    private StringFilter controlAttributs;

    private BooleanFilter controlValueStat;

    private LongFilter eventId;

    private LongFilter eventFieldId;

    private Boolean distinct;

    public EventControlCriteria() {}

    public EventControlCriteria(EventControlCriteria other) {
        this.controlId = other.controlId == null ? null : other.controlId.copy();
        this.controlName = other.controlName == null ? null : other.controlName.copy();
        this.controlDescription = other.controlDescription == null ? null : other.controlDescription.copy();
        this.controlType = other.controlType == null ? null : other.controlType.copy();
        this.controlValueString = other.controlValueString == null ? null : other.controlValueString.copy();
        this.controlValueLong = other.controlValueLong == null ? null : other.controlValueLong.copy();
        this.controlValueDate = other.controlValueDate == null ? null : other.controlValueDate.copy();
        this.controlParams = other.controlParams == null ? null : other.controlParams.copy();
        this.controlAttributs = other.controlAttributs == null ? null : other.controlAttributs.copy();
        this.controlValueStat = other.controlValueStat == null ? null : other.controlValueStat.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.eventFieldId = other.eventFieldId == null ? null : other.eventFieldId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EventControlCriteria copy() {
        return new EventControlCriteria(this);
    }

    public LongFilter getControlId() {
        return controlId;
    }

    public LongFilter controlId() {
        if (controlId == null) {
            controlId = new LongFilter();
        }
        return controlId;
    }

    public void setControlId(LongFilter controlId) {
        this.controlId = controlId;
    }

    public StringFilter getControlName() {
        return controlName;
    }

    public StringFilter controlName() {
        if (controlName == null) {
            controlName = new StringFilter();
        }
        return controlName;
    }

    public void setControlName(StringFilter controlName) {
        this.controlName = controlName;
    }

    public StringFilter getControlDescription() {
        return controlDescription;
    }

    public StringFilter controlDescription() {
        if (controlDescription == null) {
            controlDescription = new StringFilter();
        }
        return controlDescription;
    }

    public void setControlDescription(StringFilter controlDescription) {
        this.controlDescription = controlDescription;
    }

    public StringFilter getControlType() {
        return controlType;
    }

    public StringFilter controlType() {
        if (controlType == null) {
            controlType = new StringFilter();
        }
        return controlType;
    }

    public void setControlType(StringFilter controlType) {
        this.controlType = controlType;
    }

    public StringFilter getControlValueString() {
        return controlValueString;
    }

    public StringFilter controlValueString() {
        if (controlValueString == null) {
            controlValueString = new StringFilter();
        }
        return controlValueString;
    }

    public void setControlValueString(StringFilter controlValueString) {
        this.controlValueString = controlValueString;
    }

    public LongFilter getControlValueLong() {
        return controlValueLong;
    }

    public LongFilter controlValueLong() {
        if (controlValueLong == null) {
            controlValueLong = new LongFilter();
        }
        return controlValueLong;
    }

    public void setControlValueLong(LongFilter controlValueLong) {
        this.controlValueLong = controlValueLong;
    }

    public ZonedDateTimeFilter getControlValueDate() {
        return controlValueDate;
    }

    public ZonedDateTimeFilter controlValueDate() {
        if (controlValueDate == null) {
            controlValueDate = new ZonedDateTimeFilter();
        }
        return controlValueDate;
    }

    public void setControlValueDate(ZonedDateTimeFilter controlValueDate) {
        this.controlValueDate = controlValueDate;
    }

    public StringFilter getControlParams() {
        return controlParams;
    }

    public StringFilter controlParams() {
        if (controlParams == null) {
            controlParams = new StringFilter();
        }
        return controlParams;
    }

    public void setControlParams(StringFilter controlParams) {
        this.controlParams = controlParams;
    }

    public StringFilter getControlAttributs() {
        return controlAttributs;
    }

    public StringFilter controlAttributs() {
        if (controlAttributs == null) {
            controlAttributs = new StringFilter();
        }
        return controlAttributs;
    }

    public void setControlAttributs(StringFilter controlAttributs) {
        this.controlAttributs = controlAttributs;
    }

    public BooleanFilter getControlValueStat() {
        return controlValueStat;
    }

    public BooleanFilter controlValueStat() {
        if (controlValueStat == null) {
            controlValueStat = new BooleanFilter();
        }
        return controlValueStat;
    }

    public void setControlValueStat(BooleanFilter controlValueStat) {
        this.controlValueStat = controlValueStat;
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

    public LongFilter getEventFieldId() {
        return eventFieldId;
    }

    public LongFilter eventFieldId() {
        if (eventFieldId == null) {
            eventFieldId = new LongFilter();
        }
        return eventFieldId;
    }

    public void setEventFieldId(LongFilter eventFieldId) {
        this.eventFieldId = eventFieldId;
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
        final EventControlCriteria that = (EventControlCriteria) o;
        return (
            Objects.equals(controlId, that.controlId) &&
            Objects.equals(controlName, that.controlName) &&
            Objects.equals(controlDescription, that.controlDescription) &&
            Objects.equals(controlType, that.controlType) &&
            Objects.equals(controlValueString, that.controlValueString) &&
            Objects.equals(controlValueLong, that.controlValueLong) &&
            Objects.equals(controlValueDate, that.controlValueDate) &&
            Objects.equals(controlParams, that.controlParams) &&
            Objects.equals(controlAttributs, that.controlAttributs) &&
            Objects.equals(controlValueStat, that.controlValueStat) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(eventFieldId, that.eventFieldId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            controlId,
            controlName,
            controlDescription,
            controlType,
            controlValueString,
            controlValueLong,
            controlValueDate,
            controlParams,
            controlAttributs,
            controlValueStat,
            eventId,
            eventFieldId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventControlCriteria{" +
            (controlId != null ? "controlId=" + controlId + ", " : "") +
            (controlName != null ? "controlName=" + controlName + ", " : "") +
            (controlDescription != null ? "controlDescription=" + controlDescription + ", " : "") +
            (controlType != null ? "controlType=" + controlType + ", " : "") +
            (controlValueString != null ? "controlValueString=" + controlValueString + ", " : "") +
            (controlValueLong != null ? "controlValueLong=" + controlValueLong + ", " : "") +
            (controlValueDate != null ? "controlValueDate=" + controlValueDate + ", " : "") +
            (controlParams != null ? "controlParams=" + controlParams + ", " : "") +
            (controlAttributs != null ? "controlAttributs=" + controlAttributs + ", " : "") +
            (controlValueStat != null ? "controlValueStat=" + controlValueStat + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (eventFieldId != null ? "eventFieldId=" + eventFieldId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

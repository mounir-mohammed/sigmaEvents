package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.EventField} entity. This class is used
 * in {@link ma.sig.events.web.rest.EventFieldResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /event-fields?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventFieldCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter fieldId;

    private StringFilter fieldName;

    private StringFilter fieldCategorie;

    private StringFilter fieldDescription;

    private StringFilter fieldType;

    private StringFilter fieldParams;

    private StringFilter fieldAttributs;

    private BooleanFilter fieldStat;

    private LongFilter eventControlId;

    private LongFilter eventId;

    private LongFilter eventFormId;

    private Boolean distinct;

    public EventFieldCriteria() {}

    public EventFieldCriteria(EventFieldCriteria other) {
        this.fieldId = other.fieldId == null ? null : other.fieldId.copy();
        this.fieldName = other.fieldName == null ? null : other.fieldName.copy();
        this.fieldCategorie = other.fieldCategorie == null ? null : other.fieldCategorie.copy();
        this.fieldDescription = other.fieldDescription == null ? null : other.fieldDescription.copy();
        this.fieldType = other.fieldType == null ? null : other.fieldType.copy();
        this.fieldParams = other.fieldParams == null ? null : other.fieldParams.copy();
        this.fieldAttributs = other.fieldAttributs == null ? null : other.fieldAttributs.copy();
        this.fieldStat = other.fieldStat == null ? null : other.fieldStat.copy();
        this.eventControlId = other.eventControlId == null ? null : other.eventControlId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.eventFormId = other.eventFormId == null ? null : other.eventFormId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EventFieldCriteria copy() {
        return new EventFieldCriteria(this);
    }

    public LongFilter getFieldId() {
        return fieldId;
    }

    public LongFilter fieldId() {
        if (fieldId == null) {
            fieldId = new LongFilter();
        }
        return fieldId;
    }

    public void setFieldId(LongFilter fieldId) {
        this.fieldId = fieldId;
    }

    public StringFilter getFieldName() {
        return fieldName;
    }

    public StringFilter fieldName() {
        if (fieldName == null) {
            fieldName = new StringFilter();
        }
        return fieldName;
    }

    public void setFieldName(StringFilter fieldName) {
        this.fieldName = fieldName;
    }

    public StringFilter getFieldCategorie() {
        return fieldCategorie;
    }

    public StringFilter fieldCategorie() {
        if (fieldCategorie == null) {
            fieldCategorie = new StringFilter();
        }
        return fieldCategorie;
    }

    public void setFieldCategorie(StringFilter fieldCategorie) {
        this.fieldCategorie = fieldCategorie;
    }

    public StringFilter getFieldDescription() {
        return fieldDescription;
    }

    public StringFilter fieldDescription() {
        if (fieldDescription == null) {
            fieldDescription = new StringFilter();
        }
        return fieldDescription;
    }

    public void setFieldDescription(StringFilter fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public StringFilter getFieldType() {
        return fieldType;
    }

    public StringFilter fieldType() {
        if (fieldType == null) {
            fieldType = new StringFilter();
        }
        return fieldType;
    }

    public void setFieldType(StringFilter fieldType) {
        this.fieldType = fieldType;
    }

    public StringFilter getFieldParams() {
        return fieldParams;
    }

    public StringFilter fieldParams() {
        if (fieldParams == null) {
            fieldParams = new StringFilter();
        }
        return fieldParams;
    }

    public void setFieldParams(StringFilter fieldParams) {
        this.fieldParams = fieldParams;
    }

    public StringFilter getFieldAttributs() {
        return fieldAttributs;
    }

    public StringFilter fieldAttributs() {
        if (fieldAttributs == null) {
            fieldAttributs = new StringFilter();
        }
        return fieldAttributs;
    }

    public void setFieldAttributs(StringFilter fieldAttributs) {
        this.fieldAttributs = fieldAttributs;
    }

    public BooleanFilter getFieldStat() {
        return fieldStat;
    }

    public BooleanFilter fieldStat() {
        if (fieldStat == null) {
            fieldStat = new BooleanFilter();
        }
        return fieldStat;
    }

    public void setFieldStat(BooleanFilter fieldStat) {
        this.fieldStat = fieldStat;
    }

    public LongFilter getEventControlId() {
        return eventControlId;
    }

    public LongFilter eventControlId() {
        if (eventControlId == null) {
            eventControlId = new LongFilter();
        }
        return eventControlId;
    }

    public void setEventControlId(LongFilter eventControlId) {
        this.eventControlId = eventControlId;
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

    public LongFilter getEventFormId() {
        return eventFormId;
    }

    public LongFilter eventFormId() {
        if (eventFormId == null) {
            eventFormId = new LongFilter();
        }
        return eventFormId;
    }

    public void setEventFormId(LongFilter eventFormId) {
        this.eventFormId = eventFormId;
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
        final EventFieldCriteria that = (EventFieldCriteria) o;
        return (
            Objects.equals(fieldId, that.fieldId) &&
            Objects.equals(fieldName, that.fieldName) &&
            Objects.equals(fieldCategorie, that.fieldCategorie) &&
            Objects.equals(fieldDescription, that.fieldDescription) &&
            Objects.equals(fieldType, that.fieldType) &&
            Objects.equals(fieldParams, that.fieldParams) &&
            Objects.equals(fieldAttributs, that.fieldAttributs) &&
            Objects.equals(fieldStat, that.fieldStat) &&
            Objects.equals(eventControlId, that.eventControlId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(eventFormId, that.eventFormId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            fieldId,
            fieldName,
            fieldCategorie,
            fieldDescription,
            fieldType,
            fieldParams,
            fieldAttributs,
            fieldStat,
            eventControlId,
            eventId,
            eventFormId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventFieldCriteria{" +
            (fieldId != null ? "fieldId=" + fieldId + ", " : "") +
            (fieldName != null ? "fieldName=" + fieldName + ", " : "") +
            (fieldCategorie != null ? "fieldCategorie=" + fieldCategorie + ", " : "") +
            (fieldDescription != null ? "fieldDescription=" + fieldDescription + ", " : "") +
            (fieldType != null ? "fieldType=" + fieldType + ", " : "") +
            (fieldParams != null ? "fieldParams=" + fieldParams + ", " : "") +
            (fieldAttributs != null ? "fieldAttributs=" + fieldAttributs + ", " : "") +
            (fieldStat != null ? "fieldStat=" + fieldStat + ", " : "") +
            (eventControlId != null ? "eventControlId=" + eventControlId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (eventFormId != null ? "eventFormId=" + eventFormId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

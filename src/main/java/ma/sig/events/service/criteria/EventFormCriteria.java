package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.EventForm} entity. This class is used
 * in {@link ma.sig.events.web.rest.EventFormResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /event-forms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventFormCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter formId;

    private StringFilter formName;

    private StringFilter formDescription;

    private StringFilter formParams;

    private StringFilter formAttributs;

    private BooleanFilter formStat;

    private LongFilter eventFieldId;

    private LongFilter eventId;

    private Boolean distinct;

    public EventFormCriteria() {}

    public EventFormCriteria(EventFormCriteria other) {
        this.formId = other.formId == null ? null : other.formId.copy();
        this.formName = other.formName == null ? null : other.formName.copy();
        this.formDescription = other.formDescription == null ? null : other.formDescription.copy();
        this.formParams = other.formParams == null ? null : other.formParams.copy();
        this.formAttributs = other.formAttributs == null ? null : other.formAttributs.copy();
        this.formStat = other.formStat == null ? null : other.formStat.copy();
        this.eventFieldId = other.eventFieldId == null ? null : other.eventFieldId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EventFormCriteria copy() {
        return new EventFormCriteria(this);
    }

    public LongFilter getFormId() {
        return formId;
    }

    public LongFilter formId() {
        if (formId == null) {
            formId = new LongFilter();
        }
        return formId;
    }

    public void setFormId(LongFilter formId) {
        this.formId = formId;
    }

    public StringFilter getFormName() {
        return formName;
    }

    public StringFilter formName() {
        if (formName == null) {
            formName = new StringFilter();
        }
        return formName;
    }

    public void setFormName(StringFilter formName) {
        this.formName = formName;
    }

    public StringFilter getFormDescription() {
        return formDescription;
    }

    public StringFilter formDescription() {
        if (formDescription == null) {
            formDescription = new StringFilter();
        }
        return formDescription;
    }

    public void setFormDescription(StringFilter formDescription) {
        this.formDescription = formDescription;
    }

    public StringFilter getFormParams() {
        return formParams;
    }

    public StringFilter formParams() {
        if (formParams == null) {
            formParams = new StringFilter();
        }
        return formParams;
    }

    public void setFormParams(StringFilter formParams) {
        this.formParams = formParams;
    }

    public StringFilter getFormAttributs() {
        return formAttributs;
    }

    public StringFilter formAttributs() {
        if (formAttributs == null) {
            formAttributs = new StringFilter();
        }
        return formAttributs;
    }

    public void setFormAttributs(StringFilter formAttributs) {
        this.formAttributs = formAttributs;
    }

    public BooleanFilter getFormStat() {
        return formStat;
    }

    public BooleanFilter formStat() {
        if (formStat == null) {
            formStat = new BooleanFilter();
        }
        return formStat;
    }

    public void setFormStat(BooleanFilter formStat) {
        this.formStat = formStat;
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
        final EventFormCriteria that = (EventFormCriteria) o;
        return (
            Objects.equals(formId, that.formId) &&
            Objects.equals(formName, that.formName) &&
            Objects.equals(formDescription, that.formDescription) &&
            Objects.equals(formParams, that.formParams) &&
            Objects.equals(formAttributs, that.formAttributs) &&
            Objects.equals(formStat, that.formStat) &&
            Objects.equals(eventFieldId, that.eventFieldId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(formId, formName, formDescription, formParams, formAttributs, formStat, eventFieldId, eventId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventFormCriteria{" +
            (formId != null ? "formId=" + formId + ", " : "") +
            (formName != null ? "formName=" + formName + ", " : "") +
            (formDescription != null ? "formDescription=" + formDescription + ", " : "") +
            (formParams != null ? "formParams=" + formParams + ", " : "") +
            (formAttributs != null ? "formAttributs=" + formAttributs + ", " : "") +
            (formStat != null ? "formStat=" + formStat + ", " : "") +
            (eventFieldId != null ? "eventFieldId=" + eventFieldId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

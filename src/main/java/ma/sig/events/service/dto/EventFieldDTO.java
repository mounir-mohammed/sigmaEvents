package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ma.sig.events.domain.EventField} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventFieldDTO implements Serializable {

    private Long fieldId;

    private String fieldName;

    private String fieldCategorie;

    private String fieldDescription;

    private String fieldType;

    private String fieldParams;

    private String fieldAttributs;

    private Boolean fieldStat;

    private EventDTO event;

    private EventFormDTO eventForm;

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCategorie() {
        return fieldCategorie;
    }

    public void setFieldCategorie(String fieldCategorie) {
        this.fieldCategorie = fieldCategorie;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldParams() {
        return fieldParams;
    }

    public void setFieldParams(String fieldParams) {
        this.fieldParams = fieldParams;
    }

    public String getFieldAttributs() {
        return fieldAttributs;
    }

    public void setFieldAttributs(String fieldAttributs) {
        this.fieldAttributs = fieldAttributs;
    }

    public Boolean getFieldStat() {
        return fieldStat;
    }

    public void setFieldStat(Boolean fieldStat) {
        this.fieldStat = fieldStat;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public EventFormDTO getEventForm() {
        return eventForm;
    }

    public void setEventForm(EventFormDTO eventForm) {
        this.eventForm = eventForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventFieldDTO)) {
            return false;
        }

        EventFieldDTO eventFieldDTO = (EventFieldDTO) o;
        if (this.fieldId == null) {
            return false;
        }
        return Objects.equals(this.fieldId, eventFieldDTO.fieldId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fieldId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventFieldDTO{" +
            "fieldId=" + getFieldId() +
            ", fieldName='" + getFieldName() + "'" +
            ", fieldCategorie='" + getFieldCategorie() + "'" +
            ", fieldDescription='" + getFieldDescription() + "'" +
            ", fieldType='" + getFieldType() + "'" +
            ", fieldParams='" + getFieldParams() + "'" +
            ", fieldAttributs='" + getFieldAttributs() + "'" +
            ", fieldStat='" + getFieldStat() + "'" +
            ", event=" + getEvent() +
            ", eventForm=" + getEventForm() +
            "}";
    }
}

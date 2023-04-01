package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ma.sig.events.domain.EventForm} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventFormDTO implements Serializable {

    private Long formId;

    private String formName;

    private String formDescription;

    private String formParams;

    private String formAttributs;

    private Boolean formStat;

    private EventDTO event;

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormDescription() {
        return formDescription;
    }

    public void setFormDescription(String formDescription) {
        this.formDescription = formDescription;
    }

    public String getFormParams() {
        return formParams;
    }

    public void setFormParams(String formParams) {
        this.formParams = formParams;
    }

    public String getFormAttributs() {
        return formAttributs;
    }

    public void setFormAttributs(String formAttributs) {
        this.formAttributs = formAttributs;
    }

    public Boolean getFormStat() {
        return formStat;
    }

    public void setFormStat(Boolean formStat) {
        this.formStat = formStat;
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
        if (!(o instanceof EventFormDTO)) {
            return false;
        }

        EventFormDTO eventFormDTO = (EventFormDTO) o;
        if (this.formId == null) {
            return false;
        }
        return Objects.equals(this.formId, eventFormDTO.formId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.formId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventFormDTO{" +
            "formId=" + getFormId() +
            ", formName='" + getFormName() + "'" +
            ", formDescription='" + getFormDescription() + "'" +
            ", formParams='" + getFormParams() + "'" +
            ", formAttributs='" + getFormAttributs() + "'" +
            ", formStat='" + getFormStat() + "'" +
            ", event=" + getEvent() +
            "}";
    }
}

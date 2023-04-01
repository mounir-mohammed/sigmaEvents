package ma.sig.events.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link ma.sig.events.domain.EventControl} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventControlDTO implements Serializable {

    private Long controlId;

    private String controlName;

    private String controlDescription;

    private String controlType;

    private String controlValueString;

    private Long controlValueLong;

    private ZonedDateTime controlValueDate;

    private String controlParams;

    private String controlAttributs;

    private Boolean controlValueStat;

    private EventDTO event;

    private EventFieldDTO eventField;

    public Long getControlId() {
        return controlId;
    }

    public void setControlId(Long controlId) {
        this.controlId = controlId;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getControlDescription() {
        return controlDescription;
    }

    public void setControlDescription(String controlDescription) {
        this.controlDescription = controlDescription;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getControlValueString() {
        return controlValueString;
    }

    public void setControlValueString(String controlValueString) {
        this.controlValueString = controlValueString;
    }

    public Long getControlValueLong() {
        return controlValueLong;
    }

    public void setControlValueLong(Long controlValueLong) {
        this.controlValueLong = controlValueLong;
    }

    public ZonedDateTime getControlValueDate() {
        return controlValueDate;
    }

    public void setControlValueDate(ZonedDateTime controlValueDate) {
        this.controlValueDate = controlValueDate;
    }

    public String getControlParams() {
        return controlParams;
    }

    public void setControlParams(String controlParams) {
        this.controlParams = controlParams;
    }

    public String getControlAttributs() {
        return controlAttributs;
    }

    public void setControlAttributs(String controlAttributs) {
        this.controlAttributs = controlAttributs;
    }

    public Boolean getControlValueStat() {
        return controlValueStat;
    }

    public void setControlValueStat(Boolean controlValueStat) {
        this.controlValueStat = controlValueStat;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public EventFieldDTO getEventField() {
        return eventField;
    }

    public void setEventField(EventFieldDTO eventField) {
        this.eventField = eventField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventControlDTO)) {
            return false;
        }

        EventControlDTO eventControlDTO = (EventControlDTO) o;
        if (this.controlId == null) {
            return false;
        }
        return Objects.equals(this.controlId, eventControlDTO.controlId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.controlId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventControlDTO{" +
            "controlId=" + getControlId() +
            ", controlName='" + getControlName() + "'" +
            ", controlDescription='" + getControlDescription() + "'" +
            ", controlType='" + getControlType() + "'" +
            ", controlValueString='" + getControlValueString() + "'" +
            ", controlValueLong=" + getControlValueLong() +
            ", controlValueDate='" + getControlValueDate() + "'" +
            ", controlParams='" + getControlParams() + "'" +
            ", controlAttributs='" + getControlAttributs() + "'" +
            ", controlValueStat='" + getControlValueStat() + "'" +
            ", event=" + getEvent() +
            ", eventField=" + getEventField() +
            "}";
    }
}

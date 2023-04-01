package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EventControl.
 */
@Entity
@Table(name = "event_control")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventControl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "control_id")
    private Long controlId;

    @Column(name = "control_name")
    private String controlName;

    @Column(name = "control_description")
    private String controlDescription;

    @Column(name = "control_type")
    private String controlType;

    @Column(name = "control_value_string")
    private String controlValueString;

    @Column(name = "control_value_long")
    private Long controlValueLong;

    @Column(name = "control_value_date")
    private ZonedDateTime controlValueDate;

    @Column(name = "control_params")
    private String controlParams;

    @Column(name = "control_attributs")
    private String controlAttributs;

    @Column(name = "control_value_stat")
    private Boolean controlValueStat;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "eventForms",
            "eventFields",
            "eventControls",
            "areas",
            "fonctions",
            "categories",
            "printingModels",
            "codes",
            "infoSupps",
            "attachements",
            "organizs",
            "photoArchives",
            "sites",
            "accreditations",
            "notes",
            "operationHistories",
            "printingCentres",
            "settings",
            "printingServers",
            "checkAccreditationHistories",
            "checkAccreditationReports",
            "accreditationTypes",
            "dayPassInfos",
            "language",
        },
        allowSetters = true
    )
    private Event event;

    @ManyToOne
    @JsonIgnoreProperties(value = { "eventControls", "event", "eventForm" }, allowSetters = true)
    private EventField eventField;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getControlId() {
        return this.controlId;
    }

    public EventControl controlId(Long controlId) {
        this.setControlId(controlId);
        return this;
    }

    public void setControlId(Long controlId) {
        this.controlId = controlId;
    }

    public String getControlName() {
        return this.controlName;
    }

    public EventControl controlName(String controlName) {
        this.setControlName(controlName);
        return this;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getControlDescription() {
        return this.controlDescription;
    }

    public EventControl controlDescription(String controlDescription) {
        this.setControlDescription(controlDescription);
        return this;
    }

    public void setControlDescription(String controlDescription) {
        this.controlDescription = controlDescription;
    }

    public String getControlType() {
        return this.controlType;
    }

    public EventControl controlType(String controlType) {
        this.setControlType(controlType);
        return this;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getControlValueString() {
        return this.controlValueString;
    }

    public EventControl controlValueString(String controlValueString) {
        this.setControlValueString(controlValueString);
        return this;
    }

    public void setControlValueString(String controlValueString) {
        this.controlValueString = controlValueString;
    }

    public Long getControlValueLong() {
        return this.controlValueLong;
    }

    public EventControl controlValueLong(Long controlValueLong) {
        this.setControlValueLong(controlValueLong);
        return this;
    }

    public void setControlValueLong(Long controlValueLong) {
        this.controlValueLong = controlValueLong;
    }

    public ZonedDateTime getControlValueDate() {
        return this.controlValueDate;
    }

    public EventControl controlValueDate(ZonedDateTime controlValueDate) {
        this.setControlValueDate(controlValueDate);
        return this;
    }

    public void setControlValueDate(ZonedDateTime controlValueDate) {
        this.controlValueDate = controlValueDate;
    }

    public String getControlParams() {
        return this.controlParams;
    }

    public EventControl controlParams(String controlParams) {
        this.setControlParams(controlParams);
        return this;
    }

    public void setControlParams(String controlParams) {
        this.controlParams = controlParams;
    }

    public String getControlAttributs() {
        return this.controlAttributs;
    }

    public EventControl controlAttributs(String controlAttributs) {
        this.setControlAttributs(controlAttributs);
        return this;
    }

    public void setControlAttributs(String controlAttributs) {
        this.controlAttributs = controlAttributs;
    }

    public Boolean getControlValueStat() {
        return this.controlValueStat;
    }

    public EventControl controlValueStat(Boolean controlValueStat) {
        this.setControlValueStat(controlValueStat);
        return this;
    }

    public void setControlValueStat(Boolean controlValueStat) {
        this.controlValueStat = controlValueStat;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventControl event(Event event) {
        this.setEvent(event);
        return this;
    }

    public EventField getEventField() {
        return this.eventField;
    }

    public void setEventField(EventField eventField) {
        this.eventField = eventField;
    }

    public EventControl eventField(EventField eventField) {
        this.setEventField(eventField);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventControl)) {
            return false;
        }
        return controlId != null && controlId.equals(((EventControl) o).controlId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventControl{" +
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
            "}";
    }
}

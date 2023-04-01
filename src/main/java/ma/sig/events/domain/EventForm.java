package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EventForm.
 */
@Entity
@Table(name = "event_form")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private Long formId;

    @Column(name = "form_name")
    private String formName;

    @Column(name = "form_description")
    private String formDescription;

    @Column(name = "form_params")
    private String formParams;

    @Column(name = "form_attributs")
    private String formAttributs;

    @Column(name = "form_stat")
    private Boolean formStat;

    @OneToMany(mappedBy = "eventForm")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eventControls", "event", "eventForm" }, allowSetters = true)
    private Set<EventField> eventFields = new HashSet<>();

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getFormId() {
        return this.formId;
    }

    public EventForm formId(Long formId) {
        this.setFormId(formId);
        return this;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return this.formName;
    }

    public EventForm formName(String formName) {
        this.setFormName(formName);
        return this;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormDescription() {
        return this.formDescription;
    }

    public EventForm formDescription(String formDescription) {
        this.setFormDescription(formDescription);
        return this;
    }

    public void setFormDescription(String formDescription) {
        this.formDescription = formDescription;
    }

    public String getFormParams() {
        return this.formParams;
    }

    public EventForm formParams(String formParams) {
        this.setFormParams(formParams);
        return this;
    }

    public void setFormParams(String formParams) {
        this.formParams = formParams;
    }

    public String getFormAttributs() {
        return this.formAttributs;
    }

    public EventForm formAttributs(String formAttributs) {
        this.setFormAttributs(formAttributs);
        return this;
    }

    public void setFormAttributs(String formAttributs) {
        this.formAttributs = formAttributs;
    }

    public Boolean getFormStat() {
        return this.formStat;
    }

    public EventForm formStat(Boolean formStat) {
        this.setFormStat(formStat);
        return this;
    }

    public void setFormStat(Boolean formStat) {
        this.formStat = formStat;
    }

    public Set<EventField> getEventFields() {
        return this.eventFields;
    }

    public void setEventFields(Set<EventField> eventFields) {
        if (this.eventFields != null) {
            this.eventFields.forEach(i -> i.setEventForm(null));
        }
        if (eventFields != null) {
            eventFields.forEach(i -> i.setEventForm(this));
        }
        this.eventFields = eventFields;
    }

    public EventForm eventFields(Set<EventField> eventFields) {
        this.setEventFields(eventFields);
        return this;
    }

    public EventForm addEventField(EventField eventField) {
        this.eventFields.add(eventField);
        eventField.setEventForm(this);
        return this;
    }

    public EventForm removeEventField(EventField eventField) {
        this.eventFields.remove(eventField);
        eventField.setEventForm(null);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventForm event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventForm)) {
            return false;
        }
        return formId != null && formId.equals(((EventForm) o).formId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventForm{" +
            "formId=" + getFormId() +
            ", formName='" + getFormName() + "'" +
            ", formDescription='" + getFormDescription() + "'" +
            ", formParams='" + getFormParams() + "'" +
            ", formAttributs='" + getFormAttributs() + "'" +
            ", formStat='" + getFormStat() + "'" +
            "}";
    }
}

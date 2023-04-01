package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EventField.
 */
@Entity
@Table(name = "event_field")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "field_id")
    private Long fieldId;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_categorie")
    private String fieldCategorie;

    @Column(name = "field_description")
    private String fieldDescription;

    @Column(name = "field_type")
    private String fieldType;

    @Column(name = "field_params")
    private String fieldParams;

    @Column(name = "field_attributs")
    private String fieldAttributs;

    @Column(name = "field_stat")
    private Boolean fieldStat;

    @OneToMany(mappedBy = "eventField")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "event", "eventField" }, allowSetters = true)
    private Set<EventControl> eventControls = new HashSet<>();

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
    @JsonIgnoreProperties(value = { "eventFields", "event" }, allowSetters = true)
    private EventForm eventForm;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getFieldId() {
        return this.fieldId;
    }

    public EventField fieldId(Long fieldId) {
        this.setFieldId(fieldId);
        return this;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public EventField fieldName(String fieldName) {
        this.setFieldName(fieldName);
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCategorie() {
        return this.fieldCategorie;
    }

    public EventField fieldCategorie(String fieldCategorie) {
        this.setFieldCategorie(fieldCategorie);
        return this;
    }

    public void setFieldCategorie(String fieldCategorie) {
        this.fieldCategorie = fieldCategorie;
    }

    public String getFieldDescription() {
        return this.fieldDescription;
    }

    public EventField fieldDescription(String fieldDescription) {
        this.setFieldDescription(fieldDescription);
        return this;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public EventField fieldType(String fieldType) {
        this.setFieldType(fieldType);
        return this;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldParams() {
        return this.fieldParams;
    }

    public EventField fieldParams(String fieldParams) {
        this.setFieldParams(fieldParams);
        return this;
    }

    public void setFieldParams(String fieldParams) {
        this.fieldParams = fieldParams;
    }

    public String getFieldAttributs() {
        return this.fieldAttributs;
    }

    public EventField fieldAttributs(String fieldAttributs) {
        this.setFieldAttributs(fieldAttributs);
        return this;
    }

    public void setFieldAttributs(String fieldAttributs) {
        this.fieldAttributs = fieldAttributs;
    }

    public Boolean getFieldStat() {
        return this.fieldStat;
    }

    public EventField fieldStat(Boolean fieldStat) {
        this.setFieldStat(fieldStat);
        return this;
    }

    public void setFieldStat(Boolean fieldStat) {
        this.fieldStat = fieldStat;
    }

    public Set<EventControl> getEventControls() {
        return this.eventControls;
    }

    public void setEventControls(Set<EventControl> eventControls) {
        if (this.eventControls != null) {
            this.eventControls.forEach(i -> i.setEventField(null));
        }
        if (eventControls != null) {
            eventControls.forEach(i -> i.setEventField(this));
        }
        this.eventControls = eventControls;
    }

    public EventField eventControls(Set<EventControl> eventControls) {
        this.setEventControls(eventControls);
        return this;
    }

    public EventField addEventControl(EventControl eventControl) {
        this.eventControls.add(eventControl);
        eventControl.setEventField(this);
        return this;
    }

    public EventField removeEventControl(EventControl eventControl) {
        this.eventControls.remove(eventControl);
        eventControl.setEventField(null);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventField event(Event event) {
        this.setEvent(event);
        return this;
    }

    public EventForm getEventForm() {
        return this.eventForm;
    }

    public void setEventForm(EventForm eventForm) {
        this.eventForm = eventForm;
    }

    public EventField eventForm(EventForm eventForm) {
        this.setEventForm(eventForm);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventField)) {
            return false;
        }
        return fieldId != null && fieldId.equals(((EventField) o).fieldId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventField{" +
            "fieldId=" + getFieldId() +
            ", fieldName='" + getFieldName() + "'" +
            ", fieldCategorie='" + getFieldCategorie() + "'" +
            ", fieldDescription='" + getFieldDescription() + "'" +
            ", fieldType='" + getFieldType() + "'" +
            ", fieldParams='" + getFieldParams() + "'" +
            ", fieldAttributs='" + getFieldAttributs() + "'" +
            ", fieldStat='" + getFieldStat() + "'" +
            "}";
    }
}

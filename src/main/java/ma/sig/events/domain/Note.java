package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Note.
 */
@Entity
@Table(name = "note")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long noteId;

    @NotNull
    @Size(max = 50)
    @Column(name = "note_value", length = 50, nullable = false)
    private String noteValue;

    @Size(max = 200)
    @Column(name = "note_description", length = 200)
    private String noteDescription;

    @Column(name = "note_type_params")
    private String noteTypeParams;

    @Column(name = "note_type_attributs")
    private String noteTypeAttributs;

    @Column(name = "note_stat")
    private Boolean noteStat;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "photoArchives",
            "infoSupps",
            "notes",
            "checkAccreditationHistories",
            "sites",
            "event",
            "civility",
            "sexe",
            "nationality",
            "country",
            "city",
            "category",
            "fonction",
            "organiz",
            "accreditationType",
            "status",
            "attachement",
            "code",
            "dayPassInfo",
        },
        allowSetters = true
    )
    private Accreditation accreditation;

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

    public Long getNoteId() {
        return this.noteId;
    }

    public Note noteId(Long noteId) {
        this.setNoteId(noteId);
        return this;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getNoteValue() {
        return this.noteValue;
    }

    public Note noteValue(String noteValue) {
        this.setNoteValue(noteValue);
        return this;
    }

    public void setNoteValue(String noteValue) {
        this.noteValue = noteValue;
    }

    public String getNoteDescription() {
        return this.noteDescription;
    }

    public Note noteDescription(String noteDescription) {
        this.setNoteDescription(noteDescription);
        return this;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteTypeParams() {
        return this.noteTypeParams;
    }

    public Note noteTypeParams(String noteTypeParams) {
        this.setNoteTypeParams(noteTypeParams);
        return this;
    }

    public void setNoteTypeParams(String noteTypeParams) {
        this.noteTypeParams = noteTypeParams;
    }

    public String getNoteTypeAttributs() {
        return this.noteTypeAttributs;
    }

    public Note noteTypeAttributs(String noteTypeAttributs) {
        this.setNoteTypeAttributs(noteTypeAttributs);
        return this;
    }

    public void setNoteTypeAttributs(String noteTypeAttributs) {
        this.noteTypeAttributs = noteTypeAttributs;
    }

    public Boolean getNoteStat() {
        return this.noteStat;
    }

    public Note noteStat(Boolean noteStat) {
        this.setNoteStat(noteStat);
        return this;
    }

    public void setNoteStat(Boolean noteStat) {
        this.noteStat = noteStat;
    }

    public Accreditation getAccreditation() {
        return this.accreditation;
    }

    public void setAccreditation(Accreditation accreditation) {
        this.accreditation = accreditation;
    }

    public Note accreditation(Accreditation accreditation) {
        this.setAccreditation(accreditation);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Note event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Note)) {
            return false;
        }
        return noteId != null && noteId.equals(((Note) o).noteId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Note{" +
            "noteId=" + getNoteId() +
            ", noteValue='" + getNoteValue() + "'" +
            ", noteDescription='" + getNoteDescription() + "'" +
            ", noteTypeParams='" + getNoteTypeParams() + "'" +
            ", noteTypeAttributs='" + getNoteTypeAttributs() + "'" +
            ", noteStat='" + getNoteStat() + "'" +
            "}";
    }
}

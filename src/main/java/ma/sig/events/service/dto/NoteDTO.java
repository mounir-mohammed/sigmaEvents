package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Note} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NoteDTO implements Serializable {

    private Long noteId;

    @NotNull
    @Size(max = 50)
    private String noteValue;

    @Size(max = 200)
    private String noteDescription;

    private String noteTypeParams;

    private String noteTypeAttributs;

    private Boolean noteStat;

    private AccreditationDTO accreditation;

    private EventDTO event;

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getNoteValue() {
        return noteValue;
    }

    public void setNoteValue(String noteValue) {
        this.noteValue = noteValue;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteTypeParams() {
        return noteTypeParams;
    }

    public void setNoteTypeParams(String noteTypeParams) {
        this.noteTypeParams = noteTypeParams;
    }

    public String getNoteTypeAttributs() {
        return noteTypeAttributs;
    }

    public void setNoteTypeAttributs(String noteTypeAttributs) {
        this.noteTypeAttributs = noteTypeAttributs;
    }

    public Boolean getNoteStat() {
        return noteStat;
    }

    public void setNoteStat(Boolean noteStat) {
        this.noteStat = noteStat;
    }

    public AccreditationDTO getAccreditation() {
        return accreditation;
    }

    public void setAccreditation(AccreditationDTO accreditation) {
        this.accreditation = accreditation;
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
        if (!(o instanceof NoteDTO)) {
            return false;
        }

        NoteDTO noteDTO = (NoteDTO) o;
        if (this.noteId == null) {
            return false;
        }
        return Objects.equals(this.noteId, noteDTO.noteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.noteId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoteDTO{" +
            "noteId=" + getNoteId() +
            ", noteValue='" + getNoteValue() + "'" +
            ", noteDescription='" + getNoteDescription() + "'" +
            ", noteTypeParams='" + getNoteTypeParams() + "'" +
            ", noteTypeAttributs='" + getNoteTypeAttributs() + "'" +
            ", noteStat='" + getNoteStat() + "'" +
            ", accreditation=" + getAccreditation() +
            ", event=" + getEvent() +
            "}";
    }
}

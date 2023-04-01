package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Note} entity. This class is used
 * in {@link ma.sig.events.web.rest.NoteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NoteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter noteId;

    private StringFilter noteValue;

    private StringFilter noteDescription;

    private StringFilter noteTypeParams;

    private StringFilter noteTypeAttributs;

    private BooleanFilter noteStat;

    private LongFilter accreditationId;

    private LongFilter eventId;

    private Boolean distinct;

    public NoteCriteria() {}

    public NoteCriteria(NoteCriteria other) {
        this.noteId = other.noteId == null ? null : other.noteId.copy();
        this.noteValue = other.noteValue == null ? null : other.noteValue.copy();
        this.noteDescription = other.noteDescription == null ? null : other.noteDescription.copy();
        this.noteTypeParams = other.noteTypeParams == null ? null : other.noteTypeParams.copy();
        this.noteTypeAttributs = other.noteTypeAttributs == null ? null : other.noteTypeAttributs.copy();
        this.noteStat = other.noteStat == null ? null : other.noteStat.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NoteCriteria copy() {
        return new NoteCriteria(this);
    }

    public LongFilter getNoteId() {
        return noteId;
    }

    public LongFilter noteId() {
        if (noteId == null) {
            noteId = new LongFilter();
        }
        return noteId;
    }

    public void setNoteId(LongFilter noteId) {
        this.noteId = noteId;
    }

    public StringFilter getNoteValue() {
        return noteValue;
    }

    public StringFilter noteValue() {
        if (noteValue == null) {
            noteValue = new StringFilter();
        }
        return noteValue;
    }

    public void setNoteValue(StringFilter noteValue) {
        this.noteValue = noteValue;
    }

    public StringFilter getNoteDescription() {
        return noteDescription;
    }

    public StringFilter noteDescription() {
        if (noteDescription == null) {
            noteDescription = new StringFilter();
        }
        return noteDescription;
    }

    public void setNoteDescription(StringFilter noteDescription) {
        this.noteDescription = noteDescription;
    }

    public StringFilter getNoteTypeParams() {
        return noteTypeParams;
    }

    public StringFilter noteTypeParams() {
        if (noteTypeParams == null) {
            noteTypeParams = new StringFilter();
        }
        return noteTypeParams;
    }

    public void setNoteTypeParams(StringFilter noteTypeParams) {
        this.noteTypeParams = noteTypeParams;
    }

    public StringFilter getNoteTypeAttributs() {
        return noteTypeAttributs;
    }

    public StringFilter noteTypeAttributs() {
        if (noteTypeAttributs == null) {
            noteTypeAttributs = new StringFilter();
        }
        return noteTypeAttributs;
    }

    public void setNoteTypeAttributs(StringFilter noteTypeAttributs) {
        this.noteTypeAttributs = noteTypeAttributs;
    }

    public BooleanFilter getNoteStat() {
        return noteStat;
    }

    public BooleanFilter noteStat() {
        if (noteStat == null) {
            noteStat = new BooleanFilter();
        }
        return noteStat;
    }

    public void setNoteStat(BooleanFilter noteStat) {
        this.noteStat = noteStat;
    }

    public LongFilter getAccreditationId() {
        return accreditationId;
    }

    public LongFilter accreditationId() {
        if (accreditationId == null) {
            accreditationId = new LongFilter();
        }
        return accreditationId;
    }

    public void setAccreditationId(LongFilter accreditationId) {
        this.accreditationId = accreditationId;
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
        final NoteCriteria that = (NoteCriteria) o;
        return (
            Objects.equals(noteId, that.noteId) &&
            Objects.equals(noteValue, that.noteValue) &&
            Objects.equals(noteDescription, that.noteDescription) &&
            Objects.equals(noteTypeParams, that.noteTypeParams) &&
            Objects.equals(noteTypeAttributs, that.noteTypeAttributs) &&
            Objects.equals(noteStat, that.noteStat) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            noteId,
            noteValue,
            noteDescription,
            noteTypeParams,
            noteTypeAttributs,
            noteStat,
            accreditationId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoteCriteria{" +
            (noteId != null ? "noteId=" + noteId + ", " : "") +
            (noteValue != null ? "noteValue=" + noteValue + ", " : "") +
            (noteDescription != null ? "noteDescription=" + noteDescription + ", " : "") +
            (noteTypeParams != null ? "noteTypeParams=" + noteTypeParams + ", " : "") +
            (noteTypeAttributs != null ? "noteTypeAttributs=" + noteTypeAttributs + ", " : "") +
            (noteStat != null ? "noteStat=" + noteStat + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

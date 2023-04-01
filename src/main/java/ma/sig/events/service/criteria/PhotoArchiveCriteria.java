package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.PhotoArchive} entity. This class is used
 * in {@link ma.sig.events.web.rest.PhotoArchiveResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /photo-archives?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhotoArchiveCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter photoArchiveId;

    private StringFilter photoArchiveName;

    private StringFilter photoArchivePath;

    private StringFilter photoArchiveDescription;

    private StringFilter photoArchiveParams;

    private StringFilter photoArchiveAttributs;

    private BooleanFilter photoArchiveStat;

    private LongFilter accreditationId;

    private LongFilter eventId;

    private Boolean distinct;

    public PhotoArchiveCriteria() {}

    public PhotoArchiveCriteria(PhotoArchiveCriteria other) {
        this.photoArchiveId = other.photoArchiveId == null ? null : other.photoArchiveId.copy();
        this.photoArchiveName = other.photoArchiveName == null ? null : other.photoArchiveName.copy();
        this.photoArchivePath = other.photoArchivePath == null ? null : other.photoArchivePath.copy();
        this.photoArchiveDescription = other.photoArchiveDescription == null ? null : other.photoArchiveDescription.copy();
        this.photoArchiveParams = other.photoArchiveParams == null ? null : other.photoArchiveParams.copy();
        this.photoArchiveAttributs = other.photoArchiveAttributs == null ? null : other.photoArchiveAttributs.copy();
        this.photoArchiveStat = other.photoArchiveStat == null ? null : other.photoArchiveStat.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PhotoArchiveCriteria copy() {
        return new PhotoArchiveCriteria(this);
    }

    public LongFilter getPhotoArchiveId() {
        return photoArchiveId;
    }

    public LongFilter photoArchiveId() {
        if (photoArchiveId == null) {
            photoArchiveId = new LongFilter();
        }
        return photoArchiveId;
    }

    public void setPhotoArchiveId(LongFilter photoArchiveId) {
        this.photoArchiveId = photoArchiveId;
    }

    public StringFilter getPhotoArchiveName() {
        return photoArchiveName;
    }

    public StringFilter photoArchiveName() {
        if (photoArchiveName == null) {
            photoArchiveName = new StringFilter();
        }
        return photoArchiveName;
    }

    public void setPhotoArchiveName(StringFilter photoArchiveName) {
        this.photoArchiveName = photoArchiveName;
    }

    public StringFilter getPhotoArchivePath() {
        return photoArchivePath;
    }

    public StringFilter photoArchivePath() {
        if (photoArchivePath == null) {
            photoArchivePath = new StringFilter();
        }
        return photoArchivePath;
    }

    public void setPhotoArchivePath(StringFilter photoArchivePath) {
        this.photoArchivePath = photoArchivePath;
    }

    public StringFilter getPhotoArchiveDescription() {
        return photoArchiveDescription;
    }

    public StringFilter photoArchiveDescription() {
        if (photoArchiveDescription == null) {
            photoArchiveDescription = new StringFilter();
        }
        return photoArchiveDescription;
    }

    public void setPhotoArchiveDescription(StringFilter photoArchiveDescription) {
        this.photoArchiveDescription = photoArchiveDescription;
    }

    public StringFilter getPhotoArchiveParams() {
        return photoArchiveParams;
    }

    public StringFilter photoArchiveParams() {
        if (photoArchiveParams == null) {
            photoArchiveParams = new StringFilter();
        }
        return photoArchiveParams;
    }

    public void setPhotoArchiveParams(StringFilter photoArchiveParams) {
        this.photoArchiveParams = photoArchiveParams;
    }

    public StringFilter getPhotoArchiveAttributs() {
        return photoArchiveAttributs;
    }

    public StringFilter photoArchiveAttributs() {
        if (photoArchiveAttributs == null) {
            photoArchiveAttributs = new StringFilter();
        }
        return photoArchiveAttributs;
    }

    public void setPhotoArchiveAttributs(StringFilter photoArchiveAttributs) {
        this.photoArchiveAttributs = photoArchiveAttributs;
    }

    public BooleanFilter getPhotoArchiveStat() {
        return photoArchiveStat;
    }

    public BooleanFilter photoArchiveStat() {
        if (photoArchiveStat == null) {
            photoArchiveStat = new BooleanFilter();
        }
        return photoArchiveStat;
    }

    public void setPhotoArchiveStat(BooleanFilter photoArchiveStat) {
        this.photoArchiveStat = photoArchiveStat;
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
        final PhotoArchiveCriteria that = (PhotoArchiveCriteria) o;
        return (
            Objects.equals(photoArchiveId, that.photoArchiveId) &&
            Objects.equals(photoArchiveName, that.photoArchiveName) &&
            Objects.equals(photoArchivePath, that.photoArchivePath) &&
            Objects.equals(photoArchiveDescription, that.photoArchiveDescription) &&
            Objects.equals(photoArchiveParams, that.photoArchiveParams) &&
            Objects.equals(photoArchiveAttributs, that.photoArchiveAttributs) &&
            Objects.equals(photoArchiveStat, that.photoArchiveStat) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            photoArchiveId,
            photoArchiveName,
            photoArchivePath,
            photoArchiveDescription,
            photoArchiveParams,
            photoArchiveAttributs,
            photoArchiveStat,
            accreditationId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotoArchiveCriteria{" +
            (photoArchiveId != null ? "photoArchiveId=" + photoArchiveId + ", " : "") +
            (photoArchiveName != null ? "photoArchiveName=" + photoArchiveName + ", " : "") +
            (photoArchivePath != null ? "photoArchivePath=" + photoArchivePath + ", " : "") +
            (photoArchiveDescription != null ? "photoArchiveDescription=" + photoArchiveDescription + ", " : "") +
            (photoArchiveParams != null ? "photoArchiveParams=" + photoArchiveParams + ", " : "") +
            (photoArchiveAttributs != null ? "photoArchiveAttributs=" + photoArchiveAttributs + ", " : "") +
            (photoArchiveStat != null ? "photoArchiveStat=" + photoArchiveStat + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

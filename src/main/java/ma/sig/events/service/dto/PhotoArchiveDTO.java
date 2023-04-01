package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.PhotoArchive} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhotoArchiveDTO implements Serializable {

    private Long photoArchiveId;

    @NotNull
    @Size(max = 50)
    private String photoArchiveName;

    private String photoArchivePath;

    @Lob
    private byte[] photoArchivePhoto;

    private String photoArchivePhotoContentType;

    @Size(max = 200)
    private String photoArchiveDescription;

    private String photoArchiveParams;

    private String photoArchiveAttributs;

    private Boolean photoArchiveStat;

    private AccreditationDTO accreditation;

    private EventDTO event;

    public Long getPhotoArchiveId() {
        return photoArchiveId;
    }

    public void setPhotoArchiveId(Long photoArchiveId) {
        this.photoArchiveId = photoArchiveId;
    }

    public String getPhotoArchiveName() {
        return photoArchiveName;
    }

    public void setPhotoArchiveName(String photoArchiveName) {
        this.photoArchiveName = photoArchiveName;
    }

    public String getPhotoArchivePath() {
        return photoArchivePath;
    }

    public void setPhotoArchivePath(String photoArchivePath) {
        this.photoArchivePath = photoArchivePath;
    }

    public byte[] getPhotoArchivePhoto() {
        return photoArchivePhoto;
    }

    public void setPhotoArchivePhoto(byte[] photoArchivePhoto) {
        this.photoArchivePhoto = photoArchivePhoto;
    }

    public String getPhotoArchivePhotoContentType() {
        return photoArchivePhotoContentType;
    }

    public void setPhotoArchivePhotoContentType(String photoArchivePhotoContentType) {
        this.photoArchivePhotoContentType = photoArchivePhotoContentType;
    }

    public String getPhotoArchiveDescription() {
        return photoArchiveDescription;
    }

    public void setPhotoArchiveDescription(String photoArchiveDescription) {
        this.photoArchiveDescription = photoArchiveDescription;
    }

    public String getPhotoArchiveParams() {
        return photoArchiveParams;
    }

    public void setPhotoArchiveParams(String photoArchiveParams) {
        this.photoArchiveParams = photoArchiveParams;
    }

    public String getPhotoArchiveAttributs() {
        return photoArchiveAttributs;
    }

    public void setPhotoArchiveAttributs(String photoArchiveAttributs) {
        this.photoArchiveAttributs = photoArchiveAttributs;
    }

    public Boolean getPhotoArchiveStat() {
        return photoArchiveStat;
    }

    public void setPhotoArchiveStat(Boolean photoArchiveStat) {
        this.photoArchiveStat = photoArchiveStat;
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
        if (!(o instanceof PhotoArchiveDTO)) {
            return false;
        }

        PhotoArchiveDTO photoArchiveDTO = (PhotoArchiveDTO) o;
        if (this.photoArchiveId == null) {
            return false;
        }
        return Objects.equals(this.photoArchiveId, photoArchiveDTO.photoArchiveId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.photoArchiveId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotoArchiveDTO{" +
            "photoArchiveId=" + getPhotoArchiveId() +
            ", photoArchiveName='" + getPhotoArchiveName() + "'" +
            ", photoArchivePath='" + getPhotoArchivePath() + "'" +
            ", photoArchivePhoto='" + getPhotoArchivePhoto() + "'" +
            ", photoArchiveDescription='" + getPhotoArchiveDescription() + "'" +
            ", photoArchiveParams='" + getPhotoArchiveParams() + "'" +
            ", photoArchiveAttributs='" + getPhotoArchiveAttributs() + "'" +
            ", photoArchiveStat='" + getPhotoArchiveStat() + "'" +
            ", accreditation=" + getAccreditation() +
            ", event=" + getEvent() +
            "}";
    }
}

package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Attachement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttachementDTO implements Serializable {

    private Long attachementId;

    @NotNull
    @Size(max = 50)
    private String attachementName;

    private String attachementPath;

    @Lob
    private byte[] attachementBlob;

    private String attachementBlobContentType;

    @Size(max = 200)
    private String attachementDescription;

    @Lob
    private byte[] attachementPhoto;

    private String attachementPhotoContentType;
    private String attachementParams;

    private String attachementAttributs;

    private Boolean attachementStat;

    private AttachementTypeDTO attachementType;

    private EventDTO event;

    public Long getAttachementId() {
        return attachementId;
    }

    public void setAttachementId(Long attachementId) {
        this.attachementId = attachementId;
    }

    public String getAttachementName() {
        return attachementName;
    }

    public void setAttachementName(String attachementName) {
        this.attachementName = attachementName;
    }

    public String getAttachementPath() {
        return attachementPath;
    }

    public void setAttachementPath(String attachementPath) {
        this.attachementPath = attachementPath;
    }

    public byte[] getAttachementBlob() {
        return attachementBlob;
    }

    public void setAttachementBlob(byte[] attachementBlob) {
        this.attachementBlob = attachementBlob;
    }

    public String getAttachementBlobContentType() {
        return attachementBlobContentType;
    }

    public void setAttachementBlobContentType(String attachementBlobContentType) {
        this.attachementBlobContentType = attachementBlobContentType;
    }

    public String getAttachementDescription() {
        return attachementDescription;
    }

    public void setAttachementDescription(String attachementDescription) {
        this.attachementDescription = attachementDescription;
    }

    public byte[] getAttachementPhoto() {
        return attachementPhoto;
    }

    public void setAttachementPhoto(byte[] attachementPhoto) {
        this.attachementPhoto = attachementPhoto;
    }

    public String getAttachementPhotoContentType() {
        return attachementPhotoContentType;
    }

    public void setAttachementPhotoContentType(String attachementPhotoContentType) {
        this.attachementPhotoContentType = attachementPhotoContentType;
    }

    public String getAttachementParams() {
        return attachementParams;
    }

    public void setAttachementParams(String attachementParams) {
        this.attachementParams = attachementParams;
    }

    public String getAttachementAttributs() {
        return attachementAttributs;
    }

    public void setAttachementAttributs(String attachementAttributs) {
        this.attachementAttributs = attachementAttributs;
    }

    public Boolean getAttachementStat() {
        return attachementStat;
    }

    public void setAttachementStat(Boolean attachementStat) {
        this.attachementStat = attachementStat;
    }

    public AttachementTypeDTO getAttachementType() {
        return attachementType;
    }

    public void setAttachementType(AttachementTypeDTO attachementType) {
        this.attachementType = attachementType;
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
        if (!(o instanceof AttachementDTO)) {
            return false;
        }

        AttachementDTO attachementDTO = (AttachementDTO) o;
        if (this.attachementId == null) {
            return false;
        }
        return Objects.equals(this.attachementId, attachementDTO.attachementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.attachementId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachementDTO{" +
            "attachementId=" + getAttachementId() +
            ", attachementName='" + getAttachementName() + "'" +
            ", attachementPath='" + getAttachementPath() + "'" +
            ", attachementBlob='" + getAttachementBlob() + "'" +
            ", attachementDescription='" + getAttachementDescription() + "'" +
            ", attachementPhoto='" + getAttachementPhoto() + "'" +
            ", attachementParams='" + getAttachementParams() + "'" +
            ", attachementAttributs='" + getAttachementAttributs() + "'" +
            ", attachementStat='" + getAttachementStat() + "'" +
            ", attachementType=" + getAttachementType() +
            ", event=" + getEvent() +
            "}";
    }
}

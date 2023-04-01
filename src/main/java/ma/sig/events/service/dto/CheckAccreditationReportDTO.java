package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.CheckAccreditationReport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckAccreditationReportDTO implements Serializable {

    private Long checkAccreditationReportId;

    @Size(max = 200)
    private String checkAccreditationReportDescription;

    @Lob
    private byte[] checkAccreditationReportPersonPhoto;

    private String checkAccreditationReportPersonPhotoContentType;

    @Lob
    private byte[] checkAccreditationReportCINPhoto;

    private String checkAccreditationReportCINPhotoContentType;

    @Lob
    private byte[] checkAccreditationReportAttachment;

    private String checkAccreditationReportAttachmentContentType;
    private String checkAccreditationReportParams;

    private String checkAccreditationReportAttributs;

    private Boolean checkAccreditationReportStat;

    private EventDTO event;

    private CheckAccreditationHistoryDTO checkAccreditationHistory;

    public Long getCheckAccreditationReportId() {
        return checkAccreditationReportId;
    }

    public void setCheckAccreditationReportId(Long checkAccreditationReportId) {
        this.checkAccreditationReportId = checkAccreditationReportId;
    }

    public String getCheckAccreditationReportDescription() {
        return checkAccreditationReportDescription;
    }

    public void setCheckAccreditationReportDescription(String checkAccreditationReportDescription) {
        this.checkAccreditationReportDescription = checkAccreditationReportDescription;
    }

    public byte[] getCheckAccreditationReportPersonPhoto() {
        return checkAccreditationReportPersonPhoto;
    }

    public void setCheckAccreditationReportPersonPhoto(byte[] checkAccreditationReportPersonPhoto) {
        this.checkAccreditationReportPersonPhoto = checkAccreditationReportPersonPhoto;
    }

    public String getCheckAccreditationReportPersonPhotoContentType() {
        return checkAccreditationReportPersonPhotoContentType;
    }

    public void setCheckAccreditationReportPersonPhotoContentType(String checkAccreditationReportPersonPhotoContentType) {
        this.checkAccreditationReportPersonPhotoContentType = checkAccreditationReportPersonPhotoContentType;
    }

    public byte[] getCheckAccreditationReportCINPhoto() {
        return checkAccreditationReportCINPhoto;
    }

    public void setCheckAccreditationReportCINPhoto(byte[] checkAccreditationReportCINPhoto) {
        this.checkAccreditationReportCINPhoto = checkAccreditationReportCINPhoto;
    }

    public String getCheckAccreditationReportCINPhotoContentType() {
        return checkAccreditationReportCINPhotoContentType;
    }

    public void setCheckAccreditationReportCINPhotoContentType(String checkAccreditationReportCINPhotoContentType) {
        this.checkAccreditationReportCINPhotoContentType = checkAccreditationReportCINPhotoContentType;
    }

    public byte[] getCheckAccreditationReportAttachment() {
        return checkAccreditationReportAttachment;
    }

    public void setCheckAccreditationReportAttachment(byte[] checkAccreditationReportAttachment) {
        this.checkAccreditationReportAttachment = checkAccreditationReportAttachment;
    }

    public String getCheckAccreditationReportAttachmentContentType() {
        return checkAccreditationReportAttachmentContentType;
    }

    public void setCheckAccreditationReportAttachmentContentType(String checkAccreditationReportAttachmentContentType) {
        this.checkAccreditationReportAttachmentContentType = checkAccreditationReportAttachmentContentType;
    }

    public String getCheckAccreditationReportParams() {
        return checkAccreditationReportParams;
    }

    public void setCheckAccreditationReportParams(String checkAccreditationReportParams) {
        this.checkAccreditationReportParams = checkAccreditationReportParams;
    }

    public String getCheckAccreditationReportAttributs() {
        return checkAccreditationReportAttributs;
    }

    public void setCheckAccreditationReportAttributs(String checkAccreditationReportAttributs) {
        this.checkAccreditationReportAttributs = checkAccreditationReportAttributs;
    }

    public Boolean getCheckAccreditationReportStat() {
        return checkAccreditationReportStat;
    }

    public void setCheckAccreditationReportStat(Boolean checkAccreditationReportStat) {
        this.checkAccreditationReportStat = checkAccreditationReportStat;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public CheckAccreditationHistoryDTO getCheckAccreditationHistory() {
        return checkAccreditationHistory;
    }

    public void setCheckAccreditationHistory(CheckAccreditationHistoryDTO checkAccreditationHistory) {
        this.checkAccreditationHistory = checkAccreditationHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckAccreditationReportDTO)) {
            return false;
        }

        CheckAccreditationReportDTO checkAccreditationReportDTO = (CheckAccreditationReportDTO) o;
        if (this.checkAccreditationReportId == null) {
            return false;
        }
        return Objects.equals(this.checkAccreditationReportId, checkAccreditationReportDTO.checkAccreditationReportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.checkAccreditationReportId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckAccreditationReportDTO{" +
            "checkAccreditationReportId=" + getCheckAccreditationReportId() +
            ", checkAccreditationReportDescription='" + getCheckAccreditationReportDescription() + "'" +
            ", checkAccreditationReportPersonPhoto='" + getCheckAccreditationReportPersonPhoto() + "'" +
            ", checkAccreditationReportCINPhoto='" + getCheckAccreditationReportCINPhoto() + "'" +
            ", checkAccreditationReportAttachment='" + getCheckAccreditationReportAttachment() + "'" +
            ", checkAccreditationReportParams='" + getCheckAccreditationReportParams() + "'" +
            ", checkAccreditationReportAttributs='" + getCheckAccreditationReportAttributs() + "'" +
            ", checkAccreditationReportStat='" + getCheckAccreditationReportStat() + "'" +
            ", event=" + getEvent() +
            ", checkAccreditationHistory=" + getCheckAccreditationHistory() +
            "}";
    }
}

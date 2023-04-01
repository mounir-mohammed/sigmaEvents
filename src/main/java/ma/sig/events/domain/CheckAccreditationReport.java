package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CheckAccreditationReport.
 */
@Entity
@Table(name = "check_accreditation_report")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckAccreditationReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_accreditation_report_id")
    private Long checkAccreditationReportId;

    @Size(max = 200)
    @Column(name = "check_accreditation_report_description", length = 200)
    private String checkAccreditationReportDescription;

    @Lob
    @Column(name = "check_accreditation_report_person_photo")
    private byte[] checkAccreditationReportPersonPhoto;

    @Column(name = "check_accreditation_report_person_photo_content_type")
    private String checkAccreditationReportPersonPhotoContentType;

    @Lob
    @Column(name = "check_accreditation_report_cin_photo")
    private byte[] checkAccreditationReportCINPhoto;

    @Column(name = "check_accreditation_report_cin_photo_content_type")
    private String checkAccreditationReportCINPhotoContentType;

    @Lob
    @Column(name = "check_accreditation_report_attachment")
    private byte[] checkAccreditationReportAttachment;

    @Column(name = "check_accreditation_report_attachment_content_type")
    private String checkAccreditationReportAttachmentContentType;

    @Column(name = "check_accreditation_report_params")
    private String checkAccreditationReportParams;

    @Column(name = "check_accreditation_report_attributs")
    private String checkAccreditationReportAttributs;

    @Column(name = "check_accreditation_report_stat")
    private Boolean checkAccreditationReportStat;

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
    @JsonIgnoreProperties(value = { "checkAccreditationReports", "event", "accreditation" }, allowSetters = true)
    private CheckAccreditationHistory checkAccreditationHistory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getCheckAccreditationReportId() {
        return this.checkAccreditationReportId;
    }

    public CheckAccreditationReport checkAccreditationReportId(Long checkAccreditationReportId) {
        this.setCheckAccreditationReportId(checkAccreditationReportId);
        return this;
    }

    public void setCheckAccreditationReportId(Long checkAccreditationReportId) {
        this.checkAccreditationReportId = checkAccreditationReportId;
    }

    public String getCheckAccreditationReportDescription() {
        return this.checkAccreditationReportDescription;
    }

    public CheckAccreditationReport checkAccreditationReportDescription(String checkAccreditationReportDescription) {
        this.setCheckAccreditationReportDescription(checkAccreditationReportDescription);
        return this;
    }

    public void setCheckAccreditationReportDescription(String checkAccreditationReportDescription) {
        this.checkAccreditationReportDescription = checkAccreditationReportDescription;
    }

    public byte[] getCheckAccreditationReportPersonPhoto() {
        return this.checkAccreditationReportPersonPhoto;
    }

    public CheckAccreditationReport checkAccreditationReportPersonPhoto(byte[] checkAccreditationReportPersonPhoto) {
        this.setCheckAccreditationReportPersonPhoto(checkAccreditationReportPersonPhoto);
        return this;
    }

    public void setCheckAccreditationReportPersonPhoto(byte[] checkAccreditationReportPersonPhoto) {
        this.checkAccreditationReportPersonPhoto = checkAccreditationReportPersonPhoto;
    }

    public String getCheckAccreditationReportPersonPhotoContentType() {
        return this.checkAccreditationReportPersonPhotoContentType;
    }

    public CheckAccreditationReport checkAccreditationReportPersonPhotoContentType(String checkAccreditationReportPersonPhotoContentType) {
        this.checkAccreditationReportPersonPhotoContentType = checkAccreditationReportPersonPhotoContentType;
        return this;
    }

    public void setCheckAccreditationReportPersonPhotoContentType(String checkAccreditationReportPersonPhotoContentType) {
        this.checkAccreditationReportPersonPhotoContentType = checkAccreditationReportPersonPhotoContentType;
    }

    public byte[] getCheckAccreditationReportCINPhoto() {
        return this.checkAccreditationReportCINPhoto;
    }

    public CheckAccreditationReport checkAccreditationReportCINPhoto(byte[] checkAccreditationReportCINPhoto) {
        this.setCheckAccreditationReportCINPhoto(checkAccreditationReportCINPhoto);
        return this;
    }

    public void setCheckAccreditationReportCINPhoto(byte[] checkAccreditationReportCINPhoto) {
        this.checkAccreditationReportCINPhoto = checkAccreditationReportCINPhoto;
    }

    public String getCheckAccreditationReportCINPhotoContentType() {
        return this.checkAccreditationReportCINPhotoContentType;
    }

    public CheckAccreditationReport checkAccreditationReportCINPhotoContentType(String checkAccreditationReportCINPhotoContentType) {
        this.checkAccreditationReportCINPhotoContentType = checkAccreditationReportCINPhotoContentType;
        return this;
    }

    public void setCheckAccreditationReportCINPhotoContentType(String checkAccreditationReportCINPhotoContentType) {
        this.checkAccreditationReportCINPhotoContentType = checkAccreditationReportCINPhotoContentType;
    }

    public byte[] getCheckAccreditationReportAttachment() {
        return this.checkAccreditationReportAttachment;
    }

    public CheckAccreditationReport checkAccreditationReportAttachment(byte[] checkAccreditationReportAttachment) {
        this.setCheckAccreditationReportAttachment(checkAccreditationReportAttachment);
        return this;
    }

    public void setCheckAccreditationReportAttachment(byte[] checkAccreditationReportAttachment) {
        this.checkAccreditationReportAttachment = checkAccreditationReportAttachment;
    }

    public String getCheckAccreditationReportAttachmentContentType() {
        return this.checkAccreditationReportAttachmentContentType;
    }

    public CheckAccreditationReport checkAccreditationReportAttachmentContentType(String checkAccreditationReportAttachmentContentType) {
        this.checkAccreditationReportAttachmentContentType = checkAccreditationReportAttachmentContentType;
        return this;
    }

    public void setCheckAccreditationReportAttachmentContentType(String checkAccreditationReportAttachmentContentType) {
        this.checkAccreditationReportAttachmentContentType = checkAccreditationReportAttachmentContentType;
    }

    public String getCheckAccreditationReportParams() {
        return this.checkAccreditationReportParams;
    }

    public CheckAccreditationReport checkAccreditationReportParams(String checkAccreditationReportParams) {
        this.setCheckAccreditationReportParams(checkAccreditationReportParams);
        return this;
    }

    public void setCheckAccreditationReportParams(String checkAccreditationReportParams) {
        this.checkAccreditationReportParams = checkAccreditationReportParams;
    }

    public String getCheckAccreditationReportAttributs() {
        return this.checkAccreditationReportAttributs;
    }

    public CheckAccreditationReport checkAccreditationReportAttributs(String checkAccreditationReportAttributs) {
        this.setCheckAccreditationReportAttributs(checkAccreditationReportAttributs);
        return this;
    }

    public void setCheckAccreditationReportAttributs(String checkAccreditationReportAttributs) {
        this.checkAccreditationReportAttributs = checkAccreditationReportAttributs;
    }

    public Boolean getCheckAccreditationReportStat() {
        return this.checkAccreditationReportStat;
    }

    public CheckAccreditationReport checkAccreditationReportStat(Boolean checkAccreditationReportStat) {
        this.setCheckAccreditationReportStat(checkAccreditationReportStat);
        return this;
    }

    public void setCheckAccreditationReportStat(Boolean checkAccreditationReportStat) {
        this.checkAccreditationReportStat = checkAccreditationReportStat;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public CheckAccreditationReport event(Event event) {
        this.setEvent(event);
        return this;
    }

    public CheckAccreditationHistory getCheckAccreditationHistory() {
        return this.checkAccreditationHistory;
    }

    public void setCheckAccreditationHistory(CheckAccreditationHistory checkAccreditationHistory) {
        this.checkAccreditationHistory = checkAccreditationHistory;
    }

    public CheckAccreditationReport checkAccreditationHistory(CheckAccreditationHistory checkAccreditationHistory) {
        this.setCheckAccreditationHistory(checkAccreditationHistory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckAccreditationReport)) {
            return false;
        }
        return (
            checkAccreditationReportId != null &&
            checkAccreditationReportId.equals(((CheckAccreditationReport) o).checkAccreditationReportId)
        );
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckAccreditationReport{" +
            "checkAccreditationReportId=" + getCheckAccreditationReportId() +
            ", checkAccreditationReportDescription='" + getCheckAccreditationReportDescription() + "'" +
            ", checkAccreditationReportPersonPhoto='" + getCheckAccreditationReportPersonPhoto() + "'" +
            ", checkAccreditationReportPersonPhotoContentType='" + getCheckAccreditationReportPersonPhotoContentType() + "'" +
            ", checkAccreditationReportCINPhoto='" + getCheckAccreditationReportCINPhoto() + "'" +
            ", checkAccreditationReportCINPhotoContentType='" + getCheckAccreditationReportCINPhotoContentType() + "'" +
            ", checkAccreditationReportAttachment='" + getCheckAccreditationReportAttachment() + "'" +
            ", checkAccreditationReportAttachmentContentType='" + getCheckAccreditationReportAttachmentContentType() + "'" +
            ", checkAccreditationReportParams='" + getCheckAccreditationReportParams() + "'" +
            ", checkAccreditationReportAttributs='" + getCheckAccreditationReportAttributs() + "'" +
            ", checkAccreditationReportStat='" + getCheckAccreditationReportStat() + "'" +
            "}";
    }
}

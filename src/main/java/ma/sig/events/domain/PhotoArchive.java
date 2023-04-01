package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PhotoArchive.
 */
@Entity
@Table(name = "photo_archive")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhotoArchive implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_archive_id")
    private Long photoArchiveId;

    @NotNull
    @Size(max = 50)
    @Column(name = "photo_archive_name", length = 50, nullable = false)
    private String photoArchiveName;

    @Column(name = "photo_archive_path")
    private String photoArchivePath;

    @Lob
    @Column(name = "photo_archive_photo")
    private byte[] photoArchivePhoto;

    @Column(name = "photo_archive_photo_content_type")
    private String photoArchivePhotoContentType;

    @Size(max = 200)
    @Column(name = "photo_archive_description", length = 200)
    private String photoArchiveDescription;

    @Column(name = "photo_archive_params")
    private String photoArchiveParams;

    @Column(name = "photo_archive_attributs")
    private String photoArchiveAttributs;

    @Column(name = "photo_archive_stat")
    private Boolean photoArchiveStat;

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

    public Long getPhotoArchiveId() {
        return this.photoArchiveId;
    }

    public PhotoArchive photoArchiveId(Long photoArchiveId) {
        this.setPhotoArchiveId(photoArchiveId);
        return this;
    }

    public void setPhotoArchiveId(Long photoArchiveId) {
        this.photoArchiveId = photoArchiveId;
    }

    public String getPhotoArchiveName() {
        return this.photoArchiveName;
    }

    public PhotoArchive photoArchiveName(String photoArchiveName) {
        this.setPhotoArchiveName(photoArchiveName);
        return this;
    }

    public void setPhotoArchiveName(String photoArchiveName) {
        this.photoArchiveName = photoArchiveName;
    }

    public String getPhotoArchivePath() {
        return this.photoArchivePath;
    }

    public PhotoArchive photoArchivePath(String photoArchivePath) {
        this.setPhotoArchivePath(photoArchivePath);
        return this;
    }

    public void setPhotoArchivePath(String photoArchivePath) {
        this.photoArchivePath = photoArchivePath;
    }

    public byte[] getPhotoArchivePhoto() {
        return this.photoArchivePhoto;
    }

    public PhotoArchive photoArchivePhoto(byte[] photoArchivePhoto) {
        this.setPhotoArchivePhoto(photoArchivePhoto);
        return this;
    }

    public void setPhotoArchivePhoto(byte[] photoArchivePhoto) {
        this.photoArchivePhoto = photoArchivePhoto;
    }

    public String getPhotoArchivePhotoContentType() {
        return this.photoArchivePhotoContentType;
    }

    public PhotoArchive photoArchivePhotoContentType(String photoArchivePhotoContentType) {
        this.photoArchivePhotoContentType = photoArchivePhotoContentType;
        return this;
    }

    public void setPhotoArchivePhotoContentType(String photoArchivePhotoContentType) {
        this.photoArchivePhotoContentType = photoArchivePhotoContentType;
    }

    public String getPhotoArchiveDescription() {
        return this.photoArchiveDescription;
    }

    public PhotoArchive photoArchiveDescription(String photoArchiveDescription) {
        this.setPhotoArchiveDescription(photoArchiveDescription);
        return this;
    }

    public void setPhotoArchiveDescription(String photoArchiveDescription) {
        this.photoArchiveDescription = photoArchiveDescription;
    }

    public String getPhotoArchiveParams() {
        return this.photoArchiveParams;
    }

    public PhotoArchive photoArchiveParams(String photoArchiveParams) {
        this.setPhotoArchiveParams(photoArchiveParams);
        return this;
    }

    public void setPhotoArchiveParams(String photoArchiveParams) {
        this.photoArchiveParams = photoArchiveParams;
    }

    public String getPhotoArchiveAttributs() {
        return this.photoArchiveAttributs;
    }

    public PhotoArchive photoArchiveAttributs(String photoArchiveAttributs) {
        this.setPhotoArchiveAttributs(photoArchiveAttributs);
        return this;
    }

    public void setPhotoArchiveAttributs(String photoArchiveAttributs) {
        this.photoArchiveAttributs = photoArchiveAttributs;
    }

    public Boolean getPhotoArchiveStat() {
        return this.photoArchiveStat;
    }

    public PhotoArchive photoArchiveStat(Boolean photoArchiveStat) {
        this.setPhotoArchiveStat(photoArchiveStat);
        return this;
    }

    public void setPhotoArchiveStat(Boolean photoArchiveStat) {
        this.photoArchiveStat = photoArchiveStat;
    }

    public Accreditation getAccreditation() {
        return this.accreditation;
    }

    public void setAccreditation(Accreditation accreditation) {
        this.accreditation = accreditation;
    }

    public PhotoArchive accreditation(Accreditation accreditation) {
        this.setAccreditation(accreditation);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public PhotoArchive event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhotoArchive)) {
            return false;
        }
        return photoArchiveId != null && photoArchiveId.equals(((PhotoArchive) o).photoArchiveId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotoArchive{" +
            "photoArchiveId=" + getPhotoArchiveId() +
            ", photoArchiveName='" + getPhotoArchiveName() + "'" +
            ", photoArchivePath='" + getPhotoArchivePath() + "'" +
            ", photoArchivePhoto='" + getPhotoArchivePhoto() + "'" +
            ", photoArchivePhotoContentType='" + getPhotoArchivePhotoContentType() + "'" +
            ", photoArchiveDescription='" + getPhotoArchiveDescription() + "'" +
            ", photoArchiveParams='" + getPhotoArchiveParams() + "'" +
            ", photoArchiveAttributs='" + getPhotoArchiveAttributs() + "'" +
            ", photoArchiveStat='" + getPhotoArchiveStat() + "'" +
            "}";
    }
}

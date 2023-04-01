package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Attachement.
 */
@Entity
@Table(name = "attachement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Attachement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachement_id")
    private Long attachementId;

    @NotNull
    @Size(max = 50)
    @Column(name = "attachement_name", length = 50, nullable = false)
    private String attachementName;

    @Column(name = "attachement_path")
    private String attachementPath;

    @Lob
    @Column(name = "attachement_blob")
    private byte[] attachementBlob;

    @Column(name = "attachement_blob_content_type")
    private String attachementBlobContentType;

    @Size(max = 200)
    @Column(name = "attachement_description", length = 200)
    private String attachementDescription;

    @Lob
    @Column(name = "attachement_photo")
    private byte[] attachementPhoto;

    @Column(name = "attachement_photo_content_type")
    private String attachementPhotoContentType;

    @Column(name = "attachement_params")
    private String attachementParams;

    @Column(name = "attachement_attributs")
    private String attachementAttributs;

    @Column(name = "attachement_stat")
    private Boolean attachementStat;

    @OneToMany(mappedBy = "attachement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Accreditation> accreditations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "attachements" }, allowSetters = true)
    private AttachementType attachementType;

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

    public Long getAttachementId() {
        return this.attachementId;
    }

    public Attachement attachementId(Long attachementId) {
        this.setAttachementId(attachementId);
        return this;
    }

    public void setAttachementId(Long attachementId) {
        this.attachementId = attachementId;
    }

    public String getAttachementName() {
        return this.attachementName;
    }

    public Attachement attachementName(String attachementName) {
        this.setAttachementName(attachementName);
        return this;
    }

    public void setAttachementName(String attachementName) {
        this.attachementName = attachementName;
    }

    public String getAttachementPath() {
        return this.attachementPath;
    }

    public Attachement attachementPath(String attachementPath) {
        this.setAttachementPath(attachementPath);
        return this;
    }

    public void setAttachementPath(String attachementPath) {
        this.attachementPath = attachementPath;
    }

    public byte[] getAttachementBlob() {
        return this.attachementBlob;
    }

    public Attachement attachementBlob(byte[] attachementBlob) {
        this.setAttachementBlob(attachementBlob);
        return this;
    }

    public void setAttachementBlob(byte[] attachementBlob) {
        this.attachementBlob = attachementBlob;
    }

    public String getAttachementBlobContentType() {
        return this.attachementBlobContentType;
    }

    public Attachement attachementBlobContentType(String attachementBlobContentType) {
        this.attachementBlobContentType = attachementBlobContentType;
        return this;
    }

    public void setAttachementBlobContentType(String attachementBlobContentType) {
        this.attachementBlobContentType = attachementBlobContentType;
    }

    public String getAttachementDescription() {
        return this.attachementDescription;
    }

    public Attachement attachementDescription(String attachementDescription) {
        this.setAttachementDescription(attachementDescription);
        return this;
    }

    public void setAttachementDescription(String attachementDescription) {
        this.attachementDescription = attachementDescription;
    }

    public byte[] getAttachementPhoto() {
        return this.attachementPhoto;
    }

    public Attachement attachementPhoto(byte[] attachementPhoto) {
        this.setAttachementPhoto(attachementPhoto);
        return this;
    }

    public void setAttachementPhoto(byte[] attachementPhoto) {
        this.attachementPhoto = attachementPhoto;
    }

    public String getAttachementPhotoContentType() {
        return this.attachementPhotoContentType;
    }

    public Attachement attachementPhotoContentType(String attachementPhotoContentType) {
        this.attachementPhotoContentType = attachementPhotoContentType;
        return this;
    }

    public void setAttachementPhotoContentType(String attachementPhotoContentType) {
        this.attachementPhotoContentType = attachementPhotoContentType;
    }

    public String getAttachementParams() {
        return this.attachementParams;
    }

    public Attachement attachementParams(String attachementParams) {
        this.setAttachementParams(attachementParams);
        return this;
    }

    public void setAttachementParams(String attachementParams) {
        this.attachementParams = attachementParams;
    }

    public String getAttachementAttributs() {
        return this.attachementAttributs;
    }

    public Attachement attachementAttributs(String attachementAttributs) {
        this.setAttachementAttributs(attachementAttributs);
        return this;
    }

    public void setAttachementAttributs(String attachementAttributs) {
        this.attachementAttributs = attachementAttributs;
    }

    public Boolean getAttachementStat() {
        return this.attachementStat;
    }

    public Attachement attachementStat(Boolean attachementStat) {
        this.setAttachementStat(attachementStat);
        return this;
    }

    public void setAttachementStat(Boolean attachementStat) {
        this.attachementStat = attachementStat;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setAttachement(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setAttachement(this));
        }
        this.accreditations = accreditations;
    }

    public Attachement accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public Attachement addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setAttachement(this);
        return this;
    }

    public Attachement removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setAttachement(null);
        return this;
    }

    public AttachementType getAttachementType() {
        return this.attachementType;
    }

    public void setAttachementType(AttachementType attachementType) {
        this.attachementType = attachementType;
    }

    public Attachement attachementType(AttachementType attachementType) {
        this.setAttachementType(attachementType);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Attachement event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachement)) {
            return false;
        }
        return attachementId != null && attachementId.equals(((Attachement) o).attachementId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attachement{" +
            "attachementId=" + getAttachementId() +
            ", attachementName='" + getAttachementName() + "'" +
            ", attachementPath='" + getAttachementPath() + "'" +
            ", attachementBlob='" + getAttachementBlob() + "'" +
            ", attachementBlobContentType='" + getAttachementBlobContentType() + "'" +
            ", attachementDescription='" + getAttachementDescription() + "'" +
            ", attachementPhoto='" + getAttachementPhoto() + "'" +
            ", attachementPhotoContentType='" + getAttachementPhotoContentType() + "'" +
            ", attachementParams='" + getAttachementParams() + "'" +
            ", attachementAttributs='" + getAttachementAttributs() + "'" +
            ", attachementStat='" + getAttachementStat() + "'" +
            "}";
    }
}

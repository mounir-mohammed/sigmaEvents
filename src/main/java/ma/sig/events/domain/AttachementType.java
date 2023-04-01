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
 * A AttachementType.
 */
@Entity
@Table(name = "attachement_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttachementType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachement_type_id")
    private Long attachementTypeId;

    @NotNull
    @Size(max = 50)
    @Column(name = "attachement_type_name", length = 50, nullable = false)
    private String attachementTypeName;

    @Size(max = 200)
    @Column(name = "attachement_type_description", length = 200)
    private String attachementTypeDescription;

    @Column(name = "attachement_type_params")
    private String attachementTypeParams;

    @Column(name = "attachement_type_attributs")
    private String attachementTypeAttributs;

    @Column(name = "attachement_type_stat")
    private Boolean attachementTypeStat;

    @OneToMany(mappedBy = "attachementType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditations", "attachementType", "event" }, allowSetters = true)
    private Set<Attachement> attachements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getAttachementTypeId() {
        return this.attachementTypeId;
    }

    public AttachementType attachementTypeId(Long attachementTypeId) {
        this.setAttachementTypeId(attachementTypeId);
        return this;
    }

    public void setAttachementTypeId(Long attachementTypeId) {
        this.attachementTypeId = attachementTypeId;
    }

    public String getAttachementTypeName() {
        return this.attachementTypeName;
    }

    public AttachementType attachementTypeName(String attachementTypeName) {
        this.setAttachementTypeName(attachementTypeName);
        return this;
    }

    public void setAttachementTypeName(String attachementTypeName) {
        this.attachementTypeName = attachementTypeName;
    }

    public String getAttachementTypeDescription() {
        return this.attachementTypeDescription;
    }

    public AttachementType attachementTypeDescription(String attachementTypeDescription) {
        this.setAttachementTypeDescription(attachementTypeDescription);
        return this;
    }

    public void setAttachementTypeDescription(String attachementTypeDescription) {
        this.attachementTypeDescription = attachementTypeDescription;
    }

    public String getAttachementTypeParams() {
        return this.attachementTypeParams;
    }

    public AttachementType attachementTypeParams(String attachementTypeParams) {
        this.setAttachementTypeParams(attachementTypeParams);
        return this;
    }

    public void setAttachementTypeParams(String attachementTypeParams) {
        this.attachementTypeParams = attachementTypeParams;
    }

    public String getAttachementTypeAttributs() {
        return this.attachementTypeAttributs;
    }

    public AttachementType attachementTypeAttributs(String attachementTypeAttributs) {
        this.setAttachementTypeAttributs(attachementTypeAttributs);
        return this;
    }

    public void setAttachementTypeAttributs(String attachementTypeAttributs) {
        this.attachementTypeAttributs = attachementTypeAttributs;
    }

    public Boolean getAttachementTypeStat() {
        return this.attachementTypeStat;
    }

    public AttachementType attachementTypeStat(Boolean attachementTypeStat) {
        this.setAttachementTypeStat(attachementTypeStat);
        return this;
    }

    public void setAttachementTypeStat(Boolean attachementTypeStat) {
        this.attachementTypeStat = attachementTypeStat;
    }

    public Set<Attachement> getAttachements() {
        return this.attachements;
    }

    public void setAttachements(Set<Attachement> attachements) {
        if (this.attachements != null) {
            this.attachements.forEach(i -> i.setAttachementType(null));
        }
        if (attachements != null) {
            attachements.forEach(i -> i.setAttachementType(this));
        }
        this.attachements = attachements;
    }

    public AttachementType attachements(Set<Attachement> attachements) {
        this.setAttachements(attachements);
        return this;
    }

    public AttachementType addAttachement(Attachement attachement) {
        this.attachements.add(attachement);
        attachement.setAttachementType(this);
        return this;
    }

    public AttachementType removeAttachement(Attachement attachement) {
        this.attachements.remove(attachement);
        attachement.setAttachementType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachementType)) {
            return false;
        }
        return attachementTypeId != null && attachementTypeId.equals(((AttachementType) o).attachementTypeId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachementType{" +
            "attachementTypeId=" + getAttachementTypeId() +
            ", attachementTypeName='" + getAttachementTypeName() + "'" +
            ", attachementTypeDescription='" + getAttachementTypeDescription() + "'" +
            ", attachementTypeParams='" + getAttachementTypeParams() + "'" +
            ", attachementTypeAttributs='" + getAttachementTypeAttributs() + "'" +
            ", attachementTypeStat='" + getAttachementTypeStat() + "'" +
            "}";
    }
}

package ma.sig.events.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AuthentificationType.
 */
@Entity
@Table(name = "authentification_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuthentificationType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authentification_type_id")
    private Long authentificationTypeId;

    @NotNull
    @Size(max = 50)
    @Column(name = "authentification_type_value", length = 50, nullable = false)
    private String authentificationTypeValue;

    @Size(max = 200)
    @Column(name = "authentification_type_description", length = 200)
    private String authentificationTypeDescription;

    @Column(name = "authentification_type_params")
    private String authentificationTypeParams;

    @Column(name = "authentification_type_attributs")
    private String authentificationTypeAttributs;

    @Column(name = "authentification_type_stat")
    private Boolean authentificationTypeStat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getAuthentificationTypeId() {
        return this.authentificationTypeId;
    }

    public AuthentificationType authentificationTypeId(Long authentificationTypeId) {
        this.setAuthentificationTypeId(authentificationTypeId);
        return this;
    }

    public void setAuthentificationTypeId(Long authentificationTypeId) {
        this.authentificationTypeId = authentificationTypeId;
    }

    public String getAuthentificationTypeValue() {
        return this.authentificationTypeValue;
    }

    public AuthentificationType authentificationTypeValue(String authentificationTypeValue) {
        this.setAuthentificationTypeValue(authentificationTypeValue);
        return this;
    }

    public void setAuthentificationTypeValue(String authentificationTypeValue) {
        this.authentificationTypeValue = authentificationTypeValue;
    }

    public String getAuthentificationTypeDescription() {
        return this.authentificationTypeDescription;
    }

    public AuthentificationType authentificationTypeDescription(String authentificationTypeDescription) {
        this.setAuthentificationTypeDescription(authentificationTypeDescription);
        return this;
    }

    public void setAuthentificationTypeDescription(String authentificationTypeDescription) {
        this.authentificationTypeDescription = authentificationTypeDescription;
    }

    public String getAuthentificationTypeParams() {
        return this.authentificationTypeParams;
    }

    public AuthentificationType authentificationTypeParams(String authentificationTypeParams) {
        this.setAuthentificationTypeParams(authentificationTypeParams);
        return this;
    }

    public void setAuthentificationTypeParams(String authentificationTypeParams) {
        this.authentificationTypeParams = authentificationTypeParams;
    }

    public String getAuthentificationTypeAttributs() {
        return this.authentificationTypeAttributs;
    }

    public AuthentificationType authentificationTypeAttributs(String authentificationTypeAttributs) {
        this.setAuthentificationTypeAttributs(authentificationTypeAttributs);
        return this;
    }

    public void setAuthentificationTypeAttributs(String authentificationTypeAttributs) {
        this.authentificationTypeAttributs = authentificationTypeAttributs;
    }

    public Boolean getAuthentificationTypeStat() {
        return this.authentificationTypeStat;
    }

    public AuthentificationType authentificationTypeStat(Boolean authentificationTypeStat) {
        this.setAuthentificationTypeStat(authentificationTypeStat);
        return this;
    }

    public void setAuthentificationTypeStat(Boolean authentificationTypeStat) {
        this.authentificationTypeStat = authentificationTypeStat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthentificationType)) {
            return false;
        }
        return authentificationTypeId != null && authentificationTypeId.equals(((AuthentificationType) o).authentificationTypeId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthentificationType{" +
            "authentificationTypeId=" + getAuthentificationTypeId() +
            ", authentificationTypeValue='" + getAuthentificationTypeValue() + "'" +
            ", authentificationTypeDescription='" + getAuthentificationTypeDescription() + "'" +
            ", authentificationTypeParams='" + getAuthentificationTypeParams() + "'" +
            ", authentificationTypeAttributs='" + getAuthentificationTypeAttributs() + "'" +
            ", authentificationTypeStat='" + getAuthentificationTypeStat() + "'" +
            "}";
    }
}

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
 * A Sexe.
 */
@Entity
@Table(name = "sexe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sexe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sexe_id")
    private Long sexeId;

    @NotNull
    @Size(max = 50)
    @Column(name = "sexe_value", length = 50, nullable = false)
    private String sexeValue;

    @Size(max = 200)
    @Column(name = "sexe_description", length = 200)
    private String sexeDescription;

    @Column(name = "sexe_type_params")
    private String sexeTypeParams;

    @Column(name = "sexe_type_attributs")
    private String sexeTypeAttributs;

    @Column(name = "sexe_stat")
    private Boolean sexeStat;

    @OneToMany(mappedBy = "sexe")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getSexeId() {
        return this.sexeId;
    }

    public Sexe sexeId(Long sexeId) {
        this.setSexeId(sexeId);
        return this;
    }

    public void setSexeId(Long sexeId) {
        this.sexeId = sexeId;
    }

    public String getSexeValue() {
        return this.sexeValue;
    }

    public Sexe sexeValue(String sexeValue) {
        this.setSexeValue(sexeValue);
        return this;
    }

    public void setSexeValue(String sexeValue) {
        this.sexeValue = sexeValue;
    }

    public String getSexeDescription() {
        return this.sexeDescription;
    }

    public Sexe sexeDescription(String sexeDescription) {
        this.setSexeDescription(sexeDescription);
        return this;
    }

    public void setSexeDescription(String sexeDescription) {
        this.sexeDescription = sexeDescription;
    }

    public String getSexeTypeParams() {
        return this.sexeTypeParams;
    }

    public Sexe sexeTypeParams(String sexeTypeParams) {
        this.setSexeTypeParams(sexeTypeParams);
        return this;
    }

    public void setSexeTypeParams(String sexeTypeParams) {
        this.sexeTypeParams = sexeTypeParams;
    }

    public String getSexeTypeAttributs() {
        return this.sexeTypeAttributs;
    }

    public Sexe sexeTypeAttributs(String sexeTypeAttributs) {
        this.setSexeTypeAttributs(sexeTypeAttributs);
        return this;
    }

    public void setSexeTypeAttributs(String sexeTypeAttributs) {
        this.sexeTypeAttributs = sexeTypeAttributs;
    }

    public Boolean getSexeStat() {
        return this.sexeStat;
    }

    public Sexe sexeStat(Boolean sexeStat) {
        this.setSexeStat(sexeStat);
        return this;
    }

    public void setSexeStat(Boolean sexeStat) {
        this.sexeStat = sexeStat;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setSexe(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setSexe(this));
        }
        this.accreditations = accreditations;
    }

    public Sexe accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public Sexe addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setSexe(this);
        return this;
    }

    public Sexe removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setSexe(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sexe)) {
            return false;
        }
        return sexeId != null && sexeId.equals(((Sexe) o).sexeId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sexe{" +
            "sexeId=" + getSexeId() +
            ", sexeValue='" + getSexeValue() + "'" +
            ", sexeDescription='" + getSexeDescription() + "'" +
            ", sexeTypeParams='" + getSexeTypeParams() + "'" +
            ", sexeTypeAttributs='" + getSexeTypeAttributs() + "'" +
            ", sexeStat='" + getSexeStat() + "'" +
            "}";
    }
}

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
 * A Nationality.
 */
@Entity
@Table(name = "nationality")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nationality implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nationality_id")
    private Long nationalityId;

    @NotNull
    @Size(max = 50)
    @Column(name = "nationality_value", length = 50, nullable = false)
    private String nationalityValue;

    @Size(max = 10)
    @Column(name = "nationality_abreviation", length = 10)
    private String nationalityAbreviation;

    @Size(max = 200)
    @Column(name = "nationality_description", length = 200)
    private String nationalityDescription;

    @Lob
    @Column(name = "nationality_flag")
    private byte[] nationalityFlag;

    @Column(name = "nationality_flag_content_type")
    private String nationalityFlagContentType;

    @Column(name = "nationality_params")
    private String nationalityParams;

    @Column(name = "nationality_attributs")
    private String nationalityAttributs;

    @Column(name = "nationality_stat")
    private Boolean nationalityStat;

    @OneToMany(mappedBy = "nationality")
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

    public Long getNationalityId() {
        return this.nationalityId;
    }

    public Nationality nationalityId(Long nationalityId) {
        this.setNationalityId(nationalityId);
        return this;
    }

    public void setNationalityId(Long nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getNationalityValue() {
        return this.nationalityValue;
    }

    public Nationality nationalityValue(String nationalityValue) {
        this.setNationalityValue(nationalityValue);
        return this;
    }

    public void setNationalityValue(String nationalityValue) {
        this.nationalityValue = nationalityValue;
    }

    public String getNationalityAbreviation() {
        return this.nationalityAbreviation;
    }

    public Nationality nationalityAbreviation(String nationalityAbreviation) {
        this.setNationalityAbreviation(nationalityAbreviation);
        return this;
    }

    public void setNationalityAbreviation(String nationalityAbreviation) {
        this.nationalityAbreviation = nationalityAbreviation;
    }

    public String getNationalityDescription() {
        return this.nationalityDescription;
    }

    public Nationality nationalityDescription(String nationalityDescription) {
        this.setNationalityDescription(nationalityDescription);
        return this;
    }

    public void setNationalityDescription(String nationalityDescription) {
        this.nationalityDescription = nationalityDescription;
    }

    public byte[] getNationalityFlag() {
        return this.nationalityFlag;
    }

    public Nationality nationalityFlag(byte[] nationalityFlag) {
        this.setNationalityFlag(nationalityFlag);
        return this;
    }

    public void setNationalityFlag(byte[] nationalityFlag) {
        this.nationalityFlag = nationalityFlag;
    }

    public String getNationalityFlagContentType() {
        return this.nationalityFlagContentType;
    }

    public Nationality nationalityFlagContentType(String nationalityFlagContentType) {
        this.nationalityFlagContentType = nationalityFlagContentType;
        return this;
    }

    public void setNationalityFlagContentType(String nationalityFlagContentType) {
        this.nationalityFlagContentType = nationalityFlagContentType;
    }

    public String getNationalityParams() {
        return this.nationalityParams;
    }

    public Nationality nationalityParams(String nationalityParams) {
        this.setNationalityParams(nationalityParams);
        return this;
    }

    public void setNationalityParams(String nationalityParams) {
        this.nationalityParams = nationalityParams;
    }

    public String getNationalityAttributs() {
        return this.nationalityAttributs;
    }

    public Nationality nationalityAttributs(String nationalityAttributs) {
        this.setNationalityAttributs(nationalityAttributs);
        return this;
    }

    public void setNationalityAttributs(String nationalityAttributs) {
        this.nationalityAttributs = nationalityAttributs;
    }

    public Boolean getNationalityStat() {
        return this.nationalityStat;
    }

    public Nationality nationalityStat(Boolean nationalityStat) {
        this.setNationalityStat(nationalityStat);
        return this;
    }

    public void setNationalityStat(Boolean nationalityStat) {
        this.nationalityStat = nationalityStat;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setNationality(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setNationality(this));
        }
        this.accreditations = accreditations;
    }

    public Nationality accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public Nationality addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setNationality(this);
        return this;
    }

    public Nationality removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setNationality(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nationality)) {
            return false;
        }
        return nationalityId != null && nationalityId.equals(((Nationality) o).nationalityId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nationality{" +
            "nationalityId=" + getNationalityId() +
            ", nationalityValue='" + getNationalityValue() + "'" +
            ", nationalityAbreviation='" + getNationalityAbreviation() + "'" +
            ", nationalityDescription='" + getNationalityDescription() + "'" +
            ", nationalityFlag='" + getNationalityFlag() + "'" +
            ", nationalityFlagContentType='" + getNationalityFlagContentType() + "'" +
            ", nationalityParams='" + getNationalityParams() + "'" +
            ", nationalityAttributs='" + getNationalityAttributs() + "'" +
            ", nationalityStat='" + getNationalityStat() + "'" +
            "}";
    }
}

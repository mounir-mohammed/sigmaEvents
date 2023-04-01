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
 * A Civility.
 */
@Entity
@Table(name = "civility")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Civility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "civility_id")
    private Long civilityId;

    @NotNull
    @Size(max = 50)
    @Column(name = "civility_value", length = 50, nullable = false)
    private String civilityValue;

    @Size(max = 200)
    @Column(name = "civility_description", length = 200)
    private String civilityDescription;

    @Column(name = "civility_code")
    private String civilityCode;

    @Column(name = "civility_params")
    private String civilityParams;

    @Column(name = "civility_attributs")
    private String civilityAttributs;

    @Column(name = "civility_stat")
    private Boolean civilityStat;

    @OneToMany(mappedBy = "civility")
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

    public Long getCivilityId() {
        return this.civilityId;
    }

    public Civility civilityId(Long civilityId) {
        this.setCivilityId(civilityId);
        return this;
    }

    public void setCivilityId(Long civilityId) {
        this.civilityId = civilityId;
    }

    public String getCivilityValue() {
        return this.civilityValue;
    }

    public Civility civilityValue(String civilityValue) {
        this.setCivilityValue(civilityValue);
        return this;
    }

    public void setCivilityValue(String civilityValue) {
        this.civilityValue = civilityValue;
    }

    public String getCivilityDescription() {
        return this.civilityDescription;
    }

    public Civility civilityDescription(String civilityDescription) {
        this.setCivilityDescription(civilityDescription);
        return this;
    }

    public void setCivilityDescription(String civilityDescription) {
        this.civilityDescription = civilityDescription;
    }

    public String getCivilityCode() {
        return this.civilityCode;
    }

    public Civility civilityCode(String civilityCode) {
        this.setCivilityCode(civilityCode);
        return this;
    }

    public void setCivilityCode(String civilityCode) {
        this.civilityCode = civilityCode;
    }

    public String getCivilityParams() {
        return this.civilityParams;
    }

    public Civility civilityParams(String civilityParams) {
        this.setCivilityParams(civilityParams);
        return this;
    }

    public void setCivilityParams(String civilityParams) {
        this.civilityParams = civilityParams;
    }

    public String getCivilityAttributs() {
        return this.civilityAttributs;
    }

    public Civility civilityAttributs(String civilityAttributs) {
        this.setCivilityAttributs(civilityAttributs);
        return this;
    }

    public void setCivilityAttributs(String civilityAttributs) {
        this.civilityAttributs = civilityAttributs;
    }

    public Boolean getCivilityStat() {
        return this.civilityStat;
    }

    public Civility civilityStat(Boolean civilityStat) {
        this.setCivilityStat(civilityStat);
        return this;
    }

    public void setCivilityStat(Boolean civilityStat) {
        this.civilityStat = civilityStat;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setCivility(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setCivility(this));
        }
        this.accreditations = accreditations;
    }

    public Civility accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public Civility addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setCivility(this);
        return this;
    }

    public Civility removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setCivility(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Civility)) {
            return false;
        }
        return civilityId != null && civilityId.equals(((Civility) o).civilityId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Civility{" +
            "civilityId=" + getCivilityId() +
            ", civilityValue='" + getCivilityValue() + "'" +
            ", civilityDescription='" + getCivilityDescription() + "'" +
            ", civilityCode='" + getCivilityCode() + "'" +
            ", civilityParams='" + getCivilityParams() + "'" +
            ", civilityAttributs='" + getCivilityAttributs() + "'" +
            ", civilityStat='" + getCivilityStat() + "'" +
            "}";
    }
}

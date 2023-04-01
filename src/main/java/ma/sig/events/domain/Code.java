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
 * A Code.
 */
@Entity
@Table(name = "code")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Code implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Long codeId;

    @Column(name = "code_for_entity")
    private String codeForEntity;

    @Column(name = "code_entity_value")
    private String codeEntityValue;

    @NotNull
    @Column(name = "code_value", nullable = false, unique = true)
    private String codeValue;

    @Column(name = "code_used")
    private Boolean codeUsed;

    @Column(name = "code_params")
    private String codeParams;

    @Column(name = "code_attributs")
    private String codeAttributs;

    @Column(name = "code_stat")
    private Boolean codeStat;

    @OneToMany(mappedBy = "code")
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
    @JsonIgnoreProperties(value = { "codes" }, allowSetters = true)
    private CodeType codeType;

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

    public Long getCodeId() {
        return this.codeId;
    }

    public Code codeId(Long codeId) {
        this.setCodeId(codeId);
        return this;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public String getCodeForEntity() {
        return this.codeForEntity;
    }

    public Code codeForEntity(String codeForEntity) {
        this.setCodeForEntity(codeForEntity);
        return this;
    }

    public void setCodeForEntity(String codeForEntity) {
        this.codeForEntity = codeForEntity;
    }

    public String getCodeEntityValue() {
        return this.codeEntityValue;
    }

    public Code codeEntityValue(String codeEntityValue) {
        this.setCodeEntityValue(codeEntityValue);
        return this;
    }

    public void setCodeEntityValue(String codeEntityValue) {
        this.codeEntityValue = codeEntityValue;
    }

    public String getCodeValue() {
        return this.codeValue;
    }

    public Code codeValue(String codeValue) {
        this.setCodeValue(codeValue);
        return this;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public Boolean getCodeUsed() {
        return this.codeUsed;
    }

    public Code codeUsed(Boolean codeUsed) {
        this.setCodeUsed(codeUsed);
        return this;
    }

    public void setCodeUsed(Boolean codeUsed) {
        this.codeUsed = codeUsed;
    }

    public String getCodeParams() {
        return this.codeParams;
    }

    public Code codeParams(String codeParams) {
        this.setCodeParams(codeParams);
        return this;
    }

    public void setCodeParams(String codeParams) {
        this.codeParams = codeParams;
    }

    public String getCodeAttributs() {
        return this.codeAttributs;
    }

    public Code codeAttributs(String codeAttributs) {
        this.setCodeAttributs(codeAttributs);
        return this;
    }

    public void setCodeAttributs(String codeAttributs) {
        this.codeAttributs = codeAttributs;
    }

    public Boolean getCodeStat() {
        return this.codeStat;
    }

    public Code codeStat(Boolean codeStat) {
        this.setCodeStat(codeStat);
        return this;
    }

    public void setCodeStat(Boolean codeStat) {
        this.codeStat = codeStat;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setCode(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setCode(this));
        }
        this.accreditations = accreditations;
    }

    public Code accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public Code addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setCode(this);
        return this;
    }

    public Code removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setCode(null);
        return this;
    }

    public CodeType getCodeType() {
        return this.codeType;
    }

    public void setCodeType(CodeType codeType) {
        this.codeType = codeType;
    }

    public Code codeType(CodeType codeType) {
        this.setCodeType(codeType);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Code event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Code)) {
            return false;
        }
        return codeId != null && codeId.equals(((Code) o).codeId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Code{" +
            "codeId=" + getCodeId() +
            ", codeForEntity='" + getCodeForEntity() + "'" +
            ", codeEntityValue='" + getCodeEntityValue() + "'" +
            ", codeValue='" + getCodeValue() + "'" +
            ", codeUsed='" + getCodeUsed() + "'" +
            ", codeParams='" + getCodeParams() + "'" +
            ", codeAttributs='" + getCodeAttributs() + "'" +
            ", codeStat='" + getCodeStat() + "'" +
            "}";
    }
}

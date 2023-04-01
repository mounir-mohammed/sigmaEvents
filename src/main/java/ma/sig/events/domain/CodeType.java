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
 * A CodeType.
 */
@Entity
@Table(name = "code_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CodeType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_type_id")
    private Long codeTypeId;

    @NotNull
    @Size(max = 50)
    @Column(name = "code_type_value", length = 50, nullable = false)
    private String codeTypeValue;

    @Size(max = 200)
    @Column(name = "code_type_description", length = 200)
    private String codeTypeDescription;

    @Column(name = "code_type_params")
    private String codeTypeParams;

    @Column(name = "code_type_attributs")
    private String codeTypeAttributs;

    @Column(name = "code_type_stat")
    private Boolean codeTypeStat;

    @OneToMany(mappedBy = "codeType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditations", "codeType", "event" }, allowSetters = true)
    private Set<Code> codes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getCodeTypeId() {
        return this.codeTypeId;
    }

    public CodeType codeTypeId(Long codeTypeId) {
        this.setCodeTypeId(codeTypeId);
        return this;
    }

    public void setCodeTypeId(Long codeTypeId) {
        this.codeTypeId = codeTypeId;
    }

    public String getCodeTypeValue() {
        return this.codeTypeValue;
    }

    public CodeType codeTypeValue(String codeTypeValue) {
        this.setCodeTypeValue(codeTypeValue);
        return this;
    }

    public void setCodeTypeValue(String codeTypeValue) {
        this.codeTypeValue = codeTypeValue;
    }

    public String getCodeTypeDescription() {
        return this.codeTypeDescription;
    }

    public CodeType codeTypeDescription(String codeTypeDescription) {
        this.setCodeTypeDescription(codeTypeDescription);
        return this;
    }

    public void setCodeTypeDescription(String codeTypeDescription) {
        this.codeTypeDescription = codeTypeDescription;
    }

    public String getCodeTypeParams() {
        return this.codeTypeParams;
    }

    public CodeType codeTypeParams(String codeTypeParams) {
        this.setCodeTypeParams(codeTypeParams);
        return this;
    }

    public void setCodeTypeParams(String codeTypeParams) {
        this.codeTypeParams = codeTypeParams;
    }

    public String getCodeTypeAttributs() {
        return this.codeTypeAttributs;
    }

    public CodeType codeTypeAttributs(String codeTypeAttributs) {
        this.setCodeTypeAttributs(codeTypeAttributs);
        return this;
    }

    public void setCodeTypeAttributs(String codeTypeAttributs) {
        this.codeTypeAttributs = codeTypeAttributs;
    }

    public Boolean getCodeTypeStat() {
        return this.codeTypeStat;
    }

    public CodeType codeTypeStat(Boolean codeTypeStat) {
        this.setCodeTypeStat(codeTypeStat);
        return this;
    }

    public void setCodeTypeStat(Boolean codeTypeStat) {
        this.codeTypeStat = codeTypeStat;
    }

    public Set<Code> getCodes() {
        return this.codes;
    }

    public void setCodes(Set<Code> codes) {
        if (this.codes != null) {
            this.codes.forEach(i -> i.setCodeType(null));
        }
        if (codes != null) {
            codes.forEach(i -> i.setCodeType(this));
        }
        this.codes = codes;
    }

    public CodeType codes(Set<Code> codes) {
        this.setCodes(codes);
        return this;
    }

    public CodeType addCode(Code code) {
        this.codes.add(code);
        code.setCodeType(this);
        return this;
    }

    public CodeType removeCode(Code code) {
        this.codes.remove(code);
        code.setCodeType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CodeType)) {
            return false;
        }
        return codeTypeId != null && codeTypeId.equals(((CodeType) o).codeTypeId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CodeType{" +
            "codeTypeId=" + getCodeTypeId() +
            ", codeTypeValue='" + getCodeTypeValue() + "'" +
            ", codeTypeDescription='" + getCodeTypeDescription() + "'" +
            ", codeTypeParams='" + getCodeTypeParams() + "'" +
            ", codeTypeAttributs='" + getCodeTypeAttributs() + "'" +
            ", codeTypeStat='" + getCodeTypeStat() + "'" +
            "}";
    }
}

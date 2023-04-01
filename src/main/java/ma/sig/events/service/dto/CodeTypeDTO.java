package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.CodeType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CodeTypeDTO implements Serializable {

    private Long codeTypeId;

    @NotNull
    @Size(max = 50)
    private String codeTypeValue;

    @Size(max = 200)
    private String codeTypeDescription;

    private String codeTypeParams;

    private String codeTypeAttributs;

    private Boolean codeTypeStat;

    public Long getCodeTypeId() {
        return codeTypeId;
    }

    public void setCodeTypeId(Long codeTypeId) {
        this.codeTypeId = codeTypeId;
    }

    public String getCodeTypeValue() {
        return codeTypeValue;
    }

    public void setCodeTypeValue(String codeTypeValue) {
        this.codeTypeValue = codeTypeValue;
    }

    public String getCodeTypeDescription() {
        return codeTypeDescription;
    }

    public void setCodeTypeDescription(String codeTypeDescription) {
        this.codeTypeDescription = codeTypeDescription;
    }

    public String getCodeTypeParams() {
        return codeTypeParams;
    }

    public void setCodeTypeParams(String codeTypeParams) {
        this.codeTypeParams = codeTypeParams;
    }

    public String getCodeTypeAttributs() {
        return codeTypeAttributs;
    }

    public void setCodeTypeAttributs(String codeTypeAttributs) {
        this.codeTypeAttributs = codeTypeAttributs;
    }

    public Boolean getCodeTypeStat() {
        return codeTypeStat;
    }

    public void setCodeTypeStat(Boolean codeTypeStat) {
        this.codeTypeStat = codeTypeStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CodeTypeDTO)) {
            return false;
        }

        CodeTypeDTO codeTypeDTO = (CodeTypeDTO) o;
        if (this.codeTypeId == null) {
            return false;
        }
        return Objects.equals(this.codeTypeId, codeTypeDTO.codeTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codeTypeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CodeTypeDTO{" +
            "codeTypeId=" + getCodeTypeId() +
            ", codeTypeValue='" + getCodeTypeValue() + "'" +
            ", codeTypeDescription='" + getCodeTypeDescription() + "'" +
            ", codeTypeParams='" + getCodeTypeParams() + "'" +
            ", codeTypeAttributs='" + getCodeTypeAttributs() + "'" +
            ", codeTypeStat='" + getCodeTypeStat() + "'" +
            "}";
    }
}

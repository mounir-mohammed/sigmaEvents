package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Code} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CodeDTO implements Serializable {

    private Long codeId;

    private String codeForEntity;

    private String codeEntityValue;

    @NotNull
    private String codeValue;

    private Boolean codeUsed;

    private String codeParams;

    private String codeAttributs;

    private Boolean codeStat;

    private CodeTypeDTO codeType;

    private EventDTO event;

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public String getCodeForEntity() {
        return codeForEntity;
    }

    public void setCodeForEntity(String codeForEntity) {
        this.codeForEntity = codeForEntity;
    }

    public String getCodeEntityValue() {
        return codeEntityValue;
    }

    public void setCodeEntityValue(String codeEntityValue) {
        this.codeEntityValue = codeEntityValue;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public Boolean getCodeUsed() {
        return codeUsed;
    }

    public void setCodeUsed(Boolean codeUsed) {
        this.codeUsed = codeUsed;
    }

    public String getCodeParams() {
        return codeParams;
    }

    public void setCodeParams(String codeParams) {
        this.codeParams = codeParams;
    }

    public String getCodeAttributs() {
        return codeAttributs;
    }

    public void setCodeAttributs(String codeAttributs) {
        this.codeAttributs = codeAttributs;
    }

    public Boolean getCodeStat() {
        return codeStat;
    }

    public void setCodeStat(Boolean codeStat) {
        this.codeStat = codeStat;
    }

    public CodeTypeDTO getCodeType() {
        return codeType;
    }

    public void setCodeType(CodeTypeDTO codeType) {
        this.codeType = codeType;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CodeDTO)) {
            return false;
        }

        CodeDTO codeDTO = (CodeDTO) o;
        if (this.codeId == null) {
            return false;
        }
        return Objects.equals(this.codeId, codeDTO.codeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CodeDTO{" +
            "codeId=" + getCodeId() +
            ", codeForEntity='" + getCodeForEntity() + "'" +
            ", codeEntityValue='" + getCodeEntityValue() + "'" +
            ", codeValue='" + getCodeValue() + "'" +
            ", codeUsed='" + getCodeUsed() + "'" +
            ", codeParams='" + getCodeParams() + "'" +
            ", codeAttributs='" + getCodeAttributs() + "'" +
            ", codeStat='" + getCodeStat() + "'" +
            ", codeType=" + getCodeType() +
            ", event=" + getEvent() +
            "}";
    }
}

package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.AuthentificationType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuthentificationTypeDTO implements Serializable {

    private Long authentificationTypeId;

    @NotNull
    @Size(max = 50)
    private String authentificationTypeValue;

    @Size(max = 200)
    private String authentificationTypeDescription;

    private String authentificationTypeParams;

    private String authentificationTypeAttributs;

    private Boolean authentificationTypeStat;

    public Long getAuthentificationTypeId() {
        return authentificationTypeId;
    }

    public void setAuthentificationTypeId(Long authentificationTypeId) {
        this.authentificationTypeId = authentificationTypeId;
    }

    public String getAuthentificationTypeValue() {
        return authentificationTypeValue;
    }

    public void setAuthentificationTypeValue(String authentificationTypeValue) {
        this.authentificationTypeValue = authentificationTypeValue;
    }

    public String getAuthentificationTypeDescription() {
        return authentificationTypeDescription;
    }

    public void setAuthentificationTypeDescription(String authentificationTypeDescription) {
        this.authentificationTypeDescription = authentificationTypeDescription;
    }

    public String getAuthentificationTypeParams() {
        return authentificationTypeParams;
    }

    public void setAuthentificationTypeParams(String authentificationTypeParams) {
        this.authentificationTypeParams = authentificationTypeParams;
    }

    public String getAuthentificationTypeAttributs() {
        return authentificationTypeAttributs;
    }

    public void setAuthentificationTypeAttributs(String authentificationTypeAttributs) {
        this.authentificationTypeAttributs = authentificationTypeAttributs;
    }

    public Boolean getAuthentificationTypeStat() {
        return authentificationTypeStat;
    }

    public void setAuthentificationTypeStat(Boolean authentificationTypeStat) {
        this.authentificationTypeStat = authentificationTypeStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthentificationTypeDTO)) {
            return false;
        }

        AuthentificationTypeDTO authentificationTypeDTO = (AuthentificationTypeDTO) o;
        if (this.authentificationTypeId == null) {
            return false;
        }
        return Objects.equals(this.authentificationTypeId, authentificationTypeDTO.authentificationTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.authentificationTypeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthentificationTypeDTO{" +
            "authentificationTypeId=" + getAuthentificationTypeId() +
            ", authentificationTypeValue='" + getAuthentificationTypeValue() + "'" +
            ", authentificationTypeDescription='" + getAuthentificationTypeDescription() + "'" +
            ", authentificationTypeParams='" + getAuthentificationTypeParams() + "'" +
            ", authentificationTypeAttributs='" + getAuthentificationTypeAttributs() + "'" +
            ", authentificationTypeStat='" + getAuthentificationTypeStat() + "'" +
            "}";
    }
}

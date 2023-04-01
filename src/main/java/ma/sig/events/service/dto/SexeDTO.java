package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Sexe} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SexeDTO implements Serializable {

    private Long sexeId;

    @NotNull
    @Size(max = 50)
    private String sexeValue;

    @Size(max = 200)
    private String sexeDescription;

    private String sexeTypeParams;

    private String sexeTypeAttributs;

    private Boolean sexeStat;

    public Long getSexeId() {
        return sexeId;
    }

    public void setSexeId(Long sexeId) {
        this.sexeId = sexeId;
    }

    public String getSexeValue() {
        return sexeValue;
    }

    public void setSexeValue(String sexeValue) {
        this.sexeValue = sexeValue;
    }

    public String getSexeDescription() {
        return sexeDescription;
    }

    public void setSexeDescription(String sexeDescription) {
        this.sexeDescription = sexeDescription;
    }

    public String getSexeTypeParams() {
        return sexeTypeParams;
    }

    public void setSexeTypeParams(String sexeTypeParams) {
        this.sexeTypeParams = sexeTypeParams;
    }

    public String getSexeTypeAttributs() {
        return sexeTypeAttributs;
    }

    public void setSexeTypeAttributs(String sexeTypeAttributs) {
        this.sexeTypeAttributs = sexeTypeAttributs;
    }

    public Boolean getSexeStat() {
        return sexeStat;
    }

    public void setSexeStat(Boolean sexeStat) {
        this.sexeStat = sexeStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SexeDTO)) {
            return false;
        }

        SexeDTO sexeDTO = (SexeDTO) o;
        if (this.sexeId == null) {
            return false;
        }
        return Objects.equals(this.sexeId, sexeDTO.sexeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.sexeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SexeDTO{" +
            "sexeId=" + getSexeId() +
            ", sexeValue='" + getSexeValue() + "'" +
            ", sexeDescription='" + getSexeDescription() + "'" +
            ", sexeTypeParams='" + getSexeTypeParams() + "'" +
            ", sexeTypeAttributs='" + getSexeTypeAttributs() + "'" +
            ", sexeStat='" + getSexeStat() + "'" +
            "}";
    }
}

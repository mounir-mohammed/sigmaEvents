package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Civility} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CivilityDTO implements Serializable {

    private Long civilityId;

    @NotNull
    @Size(max = 50)
    private String civilityValue;

    @Size(max = 200)
    private String civilityDescription;

    private String civilityCode;

    private String civilityParams;

    private String civilityAttributs;

    private Boolean civilityStat;

    public Long getCivilityId() {
        return civilityId;
    }

    public void setCivilityId(Long civilityId) {
        this.civilityId = civilityId;
    }

    public String getCivilityValue() {
        return civilityValue;
    }

    public void setCivilityValue(String civilityValue) {
        this.civilityValue = civilityValue;
    }

    public String getCivilityDescription() {
        return civilityDescription;
    }

    public void setCivilityDescription(String civilityDescription) {
        this.civilityDescription = civilityDescription;
    }

    public String getCivilityCode() {
        return civilityCode;
    }

    public void setCivilityCode(String civilityCode) {
        this.civilityCode = civilityCode;
    }

    public String getCivilityParams() {
        return civilityParams;
    }

    public void setCivilityParams(String civilityParams) {
        this.civilityParams = civilityParams;
    }

    public String getCivilityAttributs() {
        return civilityAttributs;
    }

    public void setCivilityAttributs(String civilityAttributs) {
        this.civilityAttributs = civilityAttributs;
    }

    public Boolean getCivilityStat() {
        return civilityStat;
    }

    public void setCivilityStat(Boolean civilityStat) {
        this.civilityStat = civilityStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CivilityDTO)) {
            return false;
        }

        CivilityDTO civilityDTO = (CivilityDTO) o;
        if (this.civilityId == null) {
            return false;
        }
        return Objects.equals(this.civilityId, civilityDTO.civilityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.civilityId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CivilityDTO{" +
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

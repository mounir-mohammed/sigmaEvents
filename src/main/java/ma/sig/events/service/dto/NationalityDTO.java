package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Nationality} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NationalityDTO implements Serializable {

    private Long nationalityId;

    @NotNull
    @Size(max = 50)
    private String nationalityValue;

    @Size(max = 10)
    private String nationalityAbreviation;

    @Size(max = 200)
    private String nationalityDescription;

    @Lob
    private byte[] nationalityFlag;

    private String nationalityFlagContentType;
    private String nationalityParams;

    private String nationalityAttributs;

    private Boolean nationalityStat;

    public Long getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Long nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getNationalityValue() {
        return nationalityValue;
    }

    public void setNationalityValue(String nationalityValue) {
        this.nationalityValue = nationalityValue;
    }

    public String getNationalityAbreviation() {
        return nationalityAbreviation;
    }

    public void setNationalityAbreviation(String nationalityAbreviation) {
        this.nationalityAbreviation = nationalityAbreviation;
    }

    public String getNationalityDescription() {
        return nationalityDescription;
    }

    public void setNationalityDescription(String nationalityDescription) {
        this.nationalityDescription = nationalityDescription;
    }

    public byte[] getNationalityFlag() {
        return nationalityFlag;
    }

    public void setNationalityFlag(byte[] nationalityFlag) {
        this.nationalityFlag = nationalityFlag;
    }

    public String getNationalityFlagContentType() {
        return nationalityFlagContentType;
    }

    public void setNationalityFlagContentType(String nationalityFlagContentType) {
        this.nationalityFlagContentType = nationalityFlagContentType;
    }

    public String getNationalityParams() {
        return nationalityParams;
    }

    public void setNationalityParams(String nationalityParams) {
        this.nationalityParams = nationalityParams;
    }

    public String getNationalityAttributs() {
        return nationalityAttributs;
    }

    public void setNationalityAttributs(String nationalityAttributs) {
        this.nationalityAttributs = nationalityAttributs;
    }

    public Boolean getNationalityStat() {
        return nationalityStat;
    }

    public void setNationalityStat(Boolean nationalityStat) {
        this.nationalityStat = nationalityStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NationalityDTO)) {
            return false;
        }

        NationalityDTO nationalityDTO = (NationalityDTO) o;
        if (this.nationalityId == null) {
            return false;
        }
        return Objects.equals(this.nationalityId, nationalityDTO.nationalityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.nationalityId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NationalityDTO{" +
            "nationalityId=" + getNationalityId() +
            ", nationalityValue='" + getNationalityValue() + "'" +
            ", nationalityAbreviation='" + getNationalityAbreviation() + "'" +
            ", nationalityDescription='" + getNationalityDescription() + "'" +
            ", nationalityFlag='" + getNationalityFlag() + "'" +
            ", nationalityParams='" + getNationalityParams() + "'" +
            ", nationalityAttributs='" + getNationalityAttributs() + "'" +
            ", nationalityStat='" + getNationalityStat() + "'" +
            "}";
    }
}

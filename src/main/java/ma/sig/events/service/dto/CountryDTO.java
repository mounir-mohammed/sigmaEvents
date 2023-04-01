package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Country} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountryDTO implements Serializable {

    private Long countryId;

    @NotNull
    @Size(max = 50)
    private String countryName;

    @NotNull
    private String countryCodeAlpha2;

    private String countryCodeAlpha3;

    private String countryTelCode;

    @Size(max = 200)
    private String countryDescription;

    @Lob
    private byte[] countryFlag;

    private String countryFlagContentType;
    private String countryParams;

    private String countryAttributs;

    private Boolean countryStat;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCodeAlpha2() {
        return countryCodeAlpha2;
    }

    public void setCountryCodeAlpha2(String countryCodeAlpha2) {
        this.countryCodeAlpha2 = countryCodeAlpha2;
    }

    public String getCountryCodeAlpha3() {
        return countryCodeAlpha3;
    }

    public void setCountryCodeAlpha3(String countryCodeAlpha3) {
        this.countryCodeAlpha3 = countryCodeAlpha3;
    }

    public String getCountryTelCode() {
        return countryTelCode;
    }

    public void setCountryTelCode(String countryTelCode) {
        this.countryTelCode = countryTelCode;
    }

    public String getCountryDescription() {
        return countryDescription;
    }

    public void setCountryDescription(String countryDescription) {
        this.countryDescription = countryDescription;
    }

    public byte[] getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(byte[] countryFlag) {
        this.countryFlag = countryFlag;
    }

    public String getCountryFlagContentType() {
        return countryFlagContentType;
    }

    public void setCountryFlagContentType(String countryFlagContentType) {
        this.countryFlagContentType = countryFlagContentType;
    }

    public String getCountryParams() {
        return countryParams;
    }

    public void setCountryParams(String countryParams) {
        this.countryParams = countryParams;
    }

    public String getCountryAttributs() {
        return countryAttributs;
    }

    public void setCountryAttributs(String countryAttributs) {
        this.countryAttributs = countryAttributs;
    }

    public Boolean getCountryStat() {
        return countryStat;
    }

    public void setCountryStat(Boolean countryStat) {
        this.countryStat = countryStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountryDTO)) {
            return false;
        }

        CountryDTO countryDTO = (CountryDTO) o;
        if (this.countryId == null) {
            return false;
        }
        return Objects.equals(this.countryId, countryDTO.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.countryId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryDTO{" +
            "countryId=" + getCountryId() +
            ", countryName='" + getCountryName() + "'" +
            ", countryCodeAlpha2='" + getCountryCodeAlpha2() + "'" +
            ", countryCodeAlpha3='" + getCountryCodeAlpha3() + "'" +
            ", countryTelCode='" + getCountryTelCode() + "'" +
            ", countryDescription='" + getCountryDescription() + "'" +
            ", countryFlag='" + getCountryFlag() + "'" +
            ", countryParams='" + getCountryParams() + "'" +
            ", countryAttributs='" + getCountryAttributs() + "'" +
            ", countryStat='" + getCountryStat() + "'" +
            "}";
    }
}

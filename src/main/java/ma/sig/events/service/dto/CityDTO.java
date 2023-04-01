package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.City} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CityDTO implements Serializable {

    private Long cityId;

    @NotNull
    @Size(max = 50)
    private String cityName;

    @NotNull
    private String cityZipCode;

    @Size(max = 10)
    private String cityAbreviation;

    @Size(max = 200)
    private String cityDescription;

    private String cityParams;

    private String cityAttributs;

    private Boolean cityStat;

    private CountryDTO country;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityZipCode() {
        return cityZipCode;
    }

    public void setCityZipCode(String cityZipCode) {
        this.cityZipCode = cityZipCode;
    }

    public String getCityAbreviation() {
        return cityAbreviation;
    }

    public void setCityAbreviation(String cityAbreviation) {
        this.cityAbreviation = cityAbreviation;
    }

    public String getCityDescription() {
        return cityDescription;
    }

    public void setCityDescription(String cityDescription) {
        this.cityDescription = cityDescription;
    }

    public String getCityParams() {
        return cityParams;
    }

    public void setCityParams(String cityParams) {
        this.cityParams = cityParams;
    }

    public String getCityAttributs() {
        return cityAttributs;
    }

    public void setCityAttributs(String cityAttributs) {
        this.cityAttributs = cityAttributs;
    }

    public Boolean getCityStat() {
        return cityStat;
    }

    public void setCityStat(Boolean cityStat) {
        this.cityStat = cityStat;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityDTO)) {
            return false;
        }

        CityDTO cityDTO = (CityDTO) o;
        if (this.cityId == null) {
            return false;
        }
        return Objects.equals(this.cityId, cityDTO.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cityId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CityDTO{" +
            "cityId=" + getCityId() +
            ", cityName='" + getCityName() + "'" +
            ", cityZipCode='" + getCityZipCode() + "'" +
            ", cityAbreviation='" + getCityAbreviation() + "'" +
            ", cityDescription='" + getCityDescription() + "'" +
            ", cityParams='" + getCityParams() + "'" +
            ", cityAttributs='" + getCityAttributs() + "'" +
            ", cityStat='" + getCityStat() + "'" +
            ", country=" + getCountry() +
            "}";
    }
}

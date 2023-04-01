package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.City} entity. This class is used
 * in {@link ma.sig.events.web.rest.CityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter cityId;

    private StringFilter cityName;

    private StringFilter cityZipCode;

    private StringFilter cityAbreviation;

    private StringFilter cityDescription;

    private StringFilter cityParams;

    private StringFilter cityAttributs;

    private BooleanFilter cityStat;

    private LongFilter printingCentreId;

    private LongFilter siteId;

    private LongFilter organizId;

    private LongFilter accreditationId;

    private LongFilter countryId;

    private Boolean distinct;

    public CityCriteria() {}

    public CityCriteria(CityCriteria other) {
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.cityName = other.cityName == null ? null : other.cityName.copy();
        this.cityZipCode = other.cityZipCode == null ? null : other.cityZipCode.copy();
        this.cityAbreviation = other.cityAbreviation == null ? null : other.cityAbreviation.copy();
        this.cityDescription = other.cityDescription == null ? null : other.cityDescription.copy();
        this.cityParams = other.cityParams == null ? null : other.cityParams.copy();
        this.cityAttributs = other.cityAttributs == null ? null : other.cityAttributs.copy();
        this.cityStat = other.cityStat == null ? null : other.cityStat.copy();
        this.printingCentreId = other.printingCentreId == null ? null : other.printingCentreId.copy();
        this.siteId = other.siteId == null ? null : other.siteId.copy();
        this.organizId = other.organizId == null ? null : other.organizId.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CityCriteria copy() {
        return new CityCriteria(this);
    }

    public LongFilter getCityId() {
        return cityId;
    }

    public LongFilter cityId() {
        if (cityId == null) {
            cityId = new LongFilter();
        }
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }

    public StringFilter getCityName() {
        return cityName;
    }

    public StringFilter cityName() {
        if (cityName == null) {
            cityName = new StringFilter();
        }
        return cityName;
    }

    public void setCityName(StringFilter cityName) {
        this.cityName = cityName;
    }

    public StringFilter getCityZipCode() {
        return cityZipCode;
    }

    public StringFilter cityZipCode() {
        if (cityZipCode == null) {
            cityZipCode = new StringFilter();
        }
        return cityZipCode;
    }

    public void setCityZipCode(StringFilter cityZipCode) {
        this.cityZipCode = cityZipCode;
    }

    public StringFilter getCityAbreviation() {
        return cityAbreviation;
    }

    public StringFilter cityAbreviation() {
        if (cityAbreviation == null) {
            cityAbreviation = new StringFilter();
        }
        return cityAbreviation;
    }

    public void setCityAbreviation(StringFilter cityAbreviation) {
        this.cityAbreviation = cityAbreviation;
    }

    public StringFilter getCityDescription() {
        return cityDescription;
    }

    public StringFilter cityDescription() {
        if (cityDescription == null) {
            cityDescription = new StringFilter();
        }
        return cityDescription;
    }

    public void setCityDescription(StringFilter cityDescription) {
        this.cityDescription = cityDescription;
    }

    public StringFilter getCityParams() {
        return cityParams;
    }

    public StringFilter cityParams() {
        if (cityParams == null) {
            cityParams = new StringFilter();
        }
        return cityParams;
    }

    public void setCityParams(StringFilter cityParams) {
        this.cityParams = cityParams;
    }

    public StringFilter getCityAttributs() {
        return cityAttributs;
    }

    public StringFilter cityAttributs() {
        if (cityAttributs == null) {
            cityAttributs = new StringFilter();
        }
        return cityAttributs;
    }

    public void setCityAttributs(StringFilter cityAttributs) {
        this.cityAttributs = cityAttributs;
    }

    public BooleanFilter getCityStat() {
        return cityStat;
    }

    public BooleanFilter cityStat() {
        if (cityStat == null) {
            cityStat = new BooleanFilter();
        }
        return cityStat;
    }

    public void setCityStat(BooleanFilter cityStat) {
        this.cityStat = cityStat;
    }

    public LongFilter getPrintingCentreId() {
        return printingCentreId;
    }

    public LongFilter printingCentreId() {
        if (printingCentreId == null) {
            printingCentreId = new LongFilter();
        }
        return printingCentreId;
    }

    public void setPrintingCentreId(LongFilter printingCentreId) {
        this.printingCentreId = printingCentreId;
    }

    public LongFilter getSiteId() {
        return siteId;
    }

    public LongFilter siteId() {
        if (siteId == null) {
            siteId = new LongFilter();
        }
        return siteId;
    }

    public void setSiteId(LongFilter siteId) {
        this.siteId = siteId;
    }

    public LongFilter getOrganizId() {
        return organizId;
    }

    public LongFilter organizId() {
        if (organizId == null) {
            organizId = new LongFilter();
        }
        return organizId;
    }

    public void setOrganizId(LongFilter organizId) {
        this.organizId = organizId;
    }

    public LongFilter getAccreditationId() {
        return accreditationId;
    }

    public LongFilter accreditationId() {
        if (accreditationId == null) {
            accreditationId = new LongFilter();
        }
        return accreditationId;
    }

    public void setAccreditationId(LongFilter accreditationId) {
        this.accreditationId = accreditationId;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public LongFilter countryId() {
        if (countryId == null) {
            countryId = new LongFilter();
        }
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CityCriteria that = (CityCriteria) o;
        return (
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(cityName, that.cityName) &&
            Objects.equals(cityZipCode, that.cityZipCode) &&
            Objects.equals(cityAbreviation, that.cityAbreviation) &&
            Objects.equals(cityDescription, that.cityDescription) &&
            Objects.equals(cityParams, that.cityParams) &&
            Objects.equals(cityAttributs, that.cityAttributs) &&
            Objects.equals(cityStat, that.cityStat) &&
            Objects.equals(printingCentreId, that.printingCentreId) &&
            Objects.equals(siteId, that.siteId) &&
            Objects.equals(organizId, that.organizId) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            cityId,
            cityName,
            cityZipCode,
            cityAbreviation,
            cityDescription,
            cityParams,
            cityAttributs,
            cityStat,
            printingCentreId,
            siteId,
            organizId,
            accreditationId,
            countryId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CityCriteria{" +
            (cityId != null ? "cityId=" + cityId + ", " : "") +
            (cityName != null ? "cityName=" + cityName + ", " : "") +
            (cityZipCode != null ? "cityZipCode=" + cityZipCode + ", " : "") +
            (cityAbreviation != null ? "cityAbreviation=" + cityAbreviation + ", " : "") +
            (cityDescription != null ? "cityDescription=" + cityDescription + ", " : "") +
            (cityParams != null ? "cityParams=" + cityParams + ", " : "") +
            (cityAttributs != null ? "cityAttributs=" + cityAttributs + ", " : "") +
            (cityStat != null ? "cityStat=" + cityStat + ", " : "") +
            (printingCentreId != null ? "printingCentreId=" + printingCentreId + ", " : "") +
            (siteId != null ? "siteId=" + siteId + ", " : "") +
            (organizId != null ? "organizId=" + organizId + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (countryId != null ? "countryId=" + countryId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

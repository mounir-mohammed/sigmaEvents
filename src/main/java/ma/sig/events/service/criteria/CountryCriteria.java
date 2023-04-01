package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Country} entity. This class is used
 * in {@link ma.sig.events.web.rest.CountryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /countries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter countryId;

    private StringFilter countryName;

    private StringFilter countryCodeAlpha2;

    private StringFilter countryCodeAlpha3;

    private StringFilter countryTelCode;

    private StringFilter countryDescription;

    private StringFilter countryParams;

    private StringFilter countryAttributs;

    private BooleanFilter countryStat;

    private LongFilter printingCentreId;

    private LongFilter cityId;

    private LongFilter organizId;

    private LongFilter accreditationId;

    private Boolean distinct;

    public CountryCriteria() {}

    public CountryCriteria(CountryCriteria other) {
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.countryName = other.countryName == null ? null : other.countryName.copy();
        this.countryCodeAlpha2 = other.countryCodeAlpha2 == null ? null : other.countryCodeAlpha2.copy();
        this.countryCodeAlpha3 = other.countryCodeAlpha3 == null ? null : other.countryCodeAlpha3.copy();
        this.countryTelCode = other.countryTelCode == null ? null : other.countryTelCode.copy();
        this.countryDescription = other.countryDescription == null ? null : other.countryDescription.copy();
        this.countryParams = other.countryParams == null ? null : other.countryParams.copy();
        this.countryAttributs = other.countryAttributs == null ? null : other.countryAttributs.copy();
        this.countryStat = other.countryStat == null ? null : other.countryStat.copy();
        this.printingCentreId = other.printingCentreId == null ? null : other.printingCentreId.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.organizId = other.organizId == null ? null : other.organizId.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CountryCriteria copy() {
        return new CountryCriteria(this);
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

    public StringFilter getCountryName() {
        return countryName;
    }

    public StringFilter countryName() {
        if (countryName == null) {
            countryName = new StringFilter();
        }
        return countryName;
    }

    public void setCountryName(StringFilter countryName) {
        this.countryName = countryName;
    }

    public StringFilter getCountryCodeAlpha2() {
        return countryCodeAlpha2;
    }

    public StringFilter countryCodeAlpha2() {
        if (countryCodeAlpha2 == null) {
            countryCodeAlpha2 = new StringFilter();
        }
        return countryCodeAlpha2;
    }

    public void setCountryCodeAlpha2(StringFilter countryCodeAlpha2) {
        this.countryCodeAlpha2 = countryCodeAlpha2;
    }

    public StringFilter getCountryCodeAlpha3() {
        return countryCodeAlpha3;
    }

    public StringFilter countryCodeAlpha3() {
        if (countryCodeAlpha3 == null) {
            countryCodeAlpha3 = new StringFilter();
        }
        return countryCodeAlpha3;
    }

    public void setCountryCodeAlpha3(StringFilter countryCodeAlpha3) {
        this.countryCodeAlpha3 = countryCodeAlpha3;
    }

    public StringFilter getCountryTelCode() {
        return countryTelCode;
    }

    public StringFilter countryTelCode() {
        if (countryTelCode == null) {
            countryTelCode = new StringFilter();
        }
        return countryTelCode;
    }

    public void setCountryTelCode(StringFilter countryTelCode) {
        this.countryTelCode = countryTelCode;
    }

    public StringFilter getCountryDescription() {
        return countryDescription;
    }

    public StringFilter countryDescription() {
        if (countryDescription == null) {
            countryDescription = new StringFilter();
        }
        return countryDescription;
    }

    public void setCountryDescription(StringFilter countryDescription) {
        this.countryDescription = countryDescription;
    }

    public StringFilter getCountryParams() {
        return countryParams;
    }

    public StringFilter countryParams() {
        if (countryParams == null) {
            countryParams = new StringFilter();
        }
        return countryParams;
    }

    public void setCountryParams(StringFilter countryParams) {
        this.countryParams = countryParams;
    }

    public StringFilter getCountryAttributs() {
        return countryAttributs;
    }

    public StringFilter countryAttributs() {
        if (countryAttributs == null) {
            countryAttributs = new StringFilter();
        }
        return countryAttributs;
    }

    public void setCountryAttributs(StringFilter countryAttributs) {
        this.countryAttributs = countryAttributs;
    }

    public BooleanFilter getCountryStat() {
        return countryStat;
    }

    public BooleanFilter countryStat() {
        if (countryStat == null) {
            countryStat = new BooleanFilter();
        }
        return countryStat;
    }

    public void setCountryStat(BooleanFilter countryStat) {
        this.countryStat = countryStat;
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
        final CountryCriteria that = (CountryCriteria) o;
        return (
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(countryName, that.countryName) &&
            Objects.equals(countryCodeAlpha2, that.countryCodeAlpha2) &&
            Objects.equals(countryCodeAlpha3, that.countryCodeAlpha3) &&
            Objects.equals(countryTelCode, that.countryTelCode) &&
            Objects.equals(countryDescription, that.countryDescription) &&
            Objects.equals(countryParams, that.countryParams) &&
            Objects.equals(countryAttributs, that.countryAttributs) &&
            Objects.equals(countryStat, that.countryStat) &&
            Objects.equals(printingCentreId, that.printingCentreId) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(organizId, that.organizId) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            countryId,
            countryName,
            countryCodeAlpha2,
            countryCodeAlpha3,
            countryTelCode,
            countryDescription,
            countryParams,
            countryAttributs,
            countryStat,
            printingCentreId,
            cityId,
            organizId,
            accreditationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryCriteria{" +
            (countryId != null ? "countryId=" + countryId + ", " : "") +
            (countryName != null ? "countryName=" + countryName + ", " : "") +
            (countryCodeAlpha2 != null ? "countryCodeAlpha2=" + countryCodeAlpha2 + ", " : "") +
            (countryCodeAlpha3 != null ? "countryCodeAlpha3=" + countryCodeAlpha3 + ", " : "") +
            (countryTelCode != null ? "countryTelCode=" + countryTelCode + ", " : "") +
            (countryDescription != null ? "countryDescription=" + countryDescription + ", " : "") +
            (countryParams != null ? "countryParams=" + countryParams + ", " : "") +
            (countryAttributs != null ? "countryAttributs=" + countryAttributs + ", " : "") +
            (countryStat != null ? "countryStat=" + countryStat + ", " : "") +
            (printingCentreId != null ? "printingCentreId=" + printingCentreId + ", " : "") +
            (cityId != null ? "cityId=" + cityId + ", " : "") +
            (organizId != null ? "organizId=" + organizId + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

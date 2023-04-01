package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Nationality} entity. This class is used
 * in {@link ma.sig.events.web.rest.NationalityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nationalities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NationalityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter nationalityId;

    private StringFilter nationalityValue;

    private StringFilter nationalityAbreviation;

    private StringFilter nationalityDescription;

    private StringFilter nationalityParams;

    private StringFilter nationalityAttributs;

    private BooleanFilter nationalityStat;

    private LongFilter accreditationId;

    private Boolean distinct;

    public NationalityCriteria() {}

    public NationalityCriteria(NationalityCriteria other) {
        this.nationalityId = other.nationalityId == null ? null : other.nationalityId.copy();
        this.nationalityValue = other.nationalityValue == null ? null : other.nationalityValue.copy();
        this.nationalityAbreviation = other.nationalityAbreviation == null ? null : other.nationalityAbreviation.copy();
        this.nationalityDescription = other.nationalityDescription == null ? null : other.nationalityDescription.copy();
        this.nationalityParams = other.nationalityParams == null ? null : other.nationalityParams.copy();
        this.nationalityAttributs = other.nationalityAttributs == null ? null : other.nationalityAttributs.copy();
        this.nationalityStat = other.nationalityStat == null ? null : other.nationalityStat.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NationalityCriteria copy() {
        return new NationalityCriteria(this);
    }

    public LongFilter getNationalityId() {
        return nationalityId;
    }

    public LongFilter nationalityId() {
        if (nationalityId == null) {
            nationalityId = new LongFilter();
        }
        return nationalityId;
    }

    public void setNationalityId(LongFilter nationalityId) {
        this.nationalityId = nationalityId;
    }

    public StringFilter getNationalityValue() {
        return nationalityValue;
    }

    public StringFilter nationalityValue() {
        if (nationalityValue == null) {
            nationalityValue = new StringFilter();
        }
        return nationalityValue;
    }

    public void setNationalityValue(StringFilter nationalityValue) {
        this.nationalityValue = nationalityValue;
    }

    public StringFilter getNationalityAbreviation() {
        return nationalityAbreviation;
    }

    public StringFilter nationalityAbreviation() {
        if (nationalityAbreviation == null) {
            nationalityAbreviation = new StringFilter();
        }
        return nationalityAbreviation;
    }

    public void setNationalityAbreviation(StringFilter nationalityAbreviation) {
        this.nationalityAbreviation = nationalityAbreviation;
    }

    public StringFilter getNationalityDescription() {
        return nationalityDescription;
    }

    public StringFilter nationalityDescription() {
        if (nationalityDescription == null) {
            nationalityDescription = new StringFilter();
        }
        return nationalityDescription;
    }

    public void setNationalityDescription(StringFilter nationalityDescription) {
        this.nationalityDescription = nationalityDescription;
    }

    public StringFilter getNationalityParams() {
        return nationalityParams;
    }

    public StringFilter nationalityParams() {
        if (nationalityParams == null) {
            nationalityParams = new StringFilter();
        }
        return nationalityParams;
    }

    public void setNationalityParams(StringFilter nationalityParams) {
        this.nationalityParams = nationalityParams;
    }

    public StringFilter getNationalityAttributs() {
        return nationalityAttributs;
    }

    public StringFilter nationalityAttributs() {
        if (nationalityAttributs == null) {
            nationalityAttributs = new StringFilter();
        }
        return nationalityAttributs;
    }

    public void setNationalityAttributs(StringFilter nationalityAttributs) {
        this.nationalityAttributs = nationalityAttributs;
    }

    public BooleanFilter getNationalityStat() {
        return nationalityStat;
    }

    public BooleanFilter nationalityStat() {
        if (nationalityStat == null) {
            nationalityStat = new BooleanFilter();
        }
        return nationalityStat;
    }

    public void setNationalityStat(BooleanFilter nationalityStat) {
        this.nationalityStat = nationalityStat;
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
        final NationalityCriteria that = (NationalityCriteria) o;
        return (
            Objects.equals(nationalityId, that.nationalityId) &&
            Objects.equals(nationalityValue, that.nationalityValue) &&
            Objects.equals(nationalityAbreviation, that.nationalityAbreviation) &&
            Objects.equals(nationalityDescription, that.nationalityDescription) &&
            Objects.equals(nationalityParams, that.nationalityParams) &&
            Objects.equals(nationalityAttributs, that.nationalityAttributs) &&
            Objects.equals(nationalityStat, that.nationalityStat) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            nationalityId,
            nationalityValue,
            nationalityAbreviation,
            nationalityDescription,
            nationalityParams,
            nationalityAttributs,
            nationalityStat,
            accreditationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NationalityCriteria{" +
            (nationalityId != null ? "nationalityId=" + nationalityId + ", " : "") +
            (nationalityValue != null ? "nationalityValue=" + nationalityValue + ", " : "") +
            (nationalityAbreviation != null ? "nationalityAbreviation=" + nationalityAbreviation + ", " : "") +
            (nationalityDescription != null ? "nationalityDescription=" + nationalityDescription + ", " : "") +
            (nationalityParams != null ? "nationalityParams=" + nationalityParams + ", " : "") +
            (nationalityAttributs != null ? "nationalityAttributs=" + nationalityAttributs + ", " : "") +
            (nationalityStat != null ? "nationalityStat=" + nationalityStat + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

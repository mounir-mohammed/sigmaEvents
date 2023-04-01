package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Civility} entity. This class is used
 * in {@link ma.sig.events.web.rest.CivilityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /civilities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CivilityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter civilityId;

    private StringFilter civilityValue;

    private StringFilter civilityDescription;

    private StringFilter civilityCode;

    private StringFilter civilityParams;

    private StringFilter civilityAttributs;

    private BooleanFilter civilityStat;

    private LongFilter accreditationId;

    private Boolean distinct;

    public CivilityCriteria() {}

    public CivilityCriteria(CivilityCriteria other) {
        this.civilityId = other.civilityId == null ? null : other.civilityId.copy();
        this.civilityValue = other.civilityValue == null ? null : other.civilityValue.copy();
        this.civilityDescription = other.civilityDescription == null ? null : other.civilityDescription.copy();
        this.civilityCode = other.civilityCode == null ? null : other.civilityCode.copy();
        this.civilityParams = other.civilityParams == null ? null : other.civilityParams.copy();
        this.civilityAttributs = other.civilityAttributs == null ? null : other.civilityAttributs.copy();
        this.civilityStat = other.civilityStat == null ? null : other.civilityStat.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CivilityCriteria copy() {
        return new CivilityCriteria(this);
    }

    public LongFilter getCivilityId() {
        return civilityId;
    }

    public LongFilter civilityId() {
        if (civilityId == null) {
            civilityId = new LongFilter();
        }
        return civilityId;
    }

    public void setCivilityId(LongFilter civilityId) {
        this.civilityId = civilityId;
    }

    public StringFilter getCivilityValue() {
        return civilityValue;
    }

    public StringFilter civilityValue() {
        if (civilityValue == null) {
            civilityValue = new StringFilter();
        }
        return civilityValue;
    }

    public void setCivilityValue(StringFilter civilityValue) {
        this.civilityValue = civilityValue;
    }

    public StringFilter getCivilityDescription() {
        return civilityDescription;
    }

    public StringFilter civilityDescription() {
        if (civilityDescription == null) {
            civilityDescription = new StringFilter();
        }
        return civilityDescription;
    }

    public void setCivilityDescription(StringFilter civilityDescription) {
        this.civilityDescription = civilityDescription;
    }

    public StringFilter getCivilityCode() {
        return civilityCode;
    }

    public StringFilter civilityCode() {
        if (civilityCode == null) {
            civilityCode = new StringFilter();
        }
        return civilityCode;
    }

    public void setCivilityCode(StringFilter civilityCode) {
        this.civilityCode = civilityCode;
    }

    public StringFilter getCivilityParams() {
        return civilityParams;
    }

    public StringFilter civilityParams() {
        if (civilityParams == null) {
            civilityParams = new StringFilter();
        }
        return civilityParams;
    }

    public void setCivilityParams(StringFilter civilityParams) {
        this.civilityParams = civilityParams;
    }

    public StringFilter getCivilityAttributs() {
        return civilityAttributs;
    }

    public StringFilter civilityAttributs() {
        if (civilityAttributs == null) {
            civilityAttributs = new StringFilter();
        }
        return civilityAttributs;
    }

    public void setCivilityAttributs(StringFilter civilityAttributs) {
        this.civilityAttributs = civilityAttributs;
    }

    public BooleanFilter getCivilityStat() {
        return civilityStat;
    }

    public BooleanFilter civilityStat() {
        if (civilityStat == null) {
            civilityStat = new BooleanFilter();
        }
        return civilityStat;
    }

    public void setCivilityStat(BooleanFilter civilityStat) {
        this.civilityStat = civilityStat;
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
        final CivilityCriteria that = (CivilityCriteria) o;
        return (
            Objects.equals(civilityId, that.civilityId) &&
            Objects.equals(civilityValue, that.civilityValue) &&
            Objects.equals(civilityDescription, that.civilityDescription) &&
            Objects.equals(civilityCode, that.civilityCode) &&
            Objects.equals(civilityParams, that.civilityParams) &&
            Objects.equals(civilityAttributs, that.civilityAttributs) &&
            Objects.equals(civilityStat, that.civilityStat) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            civilityId,
            civilityValue,
            civilityDescription,
            civilityCode,
            civilityParams,
            civilityAttributs,
            civilityStat,
            accreditationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CivilityCriteria{" +
            (civilityId != null ? "civilityId=" + civilityId + ", " : "") +
            (civilityValue != null ? "civilityValue=" + civilityValue + ", " : "") +
            (civilityDescription != null ? "civilityDescription=" + civilityDescription + ", " : "") +
            (civilityCode != null ? "civilityCode=" + civilityCode + ", " : "") +
            (civilityParams != null ? "civilityParams=" + civilityParams + ", " : "") +
            (civilityAttributs != null ? "civilityAttributs=" + civilityAttributs + ", " : "") +
            (civilityStat != null ? "civilityStat=" + civilityStat + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

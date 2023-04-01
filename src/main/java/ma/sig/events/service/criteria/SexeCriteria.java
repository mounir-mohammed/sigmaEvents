package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Sexe} entity. This class is used
 * in {@link ma.sig.events.web.rest.SexeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sexes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SexeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter sexeId;

    private StringFilter sexeValue;

    private StringFilter sexeDescription;

    private StringFilter sexeTypeParams;

    private StringFilter sexeTypeAttributs;

    private BooleanFilter sexeStat;

    private LongFilter accreditationId;

    private Boolean distinct;

    public SexeCriteria() {}

    public SexeCriteria(SexeCriteria other) {
        this.sexeId = other.sexeId == null ? null : other.sexeId.copy();
        this.sexeValue = other.sexeValue == null ? null : other.sexeValue.copy();
        this.sexeDescription = other.sexeDescription == null ? null : other.sexeDescription.copy();
        this.sexeTypeParams = other.sexeTypeParams == null ? null : other.sexeTypeParams.copy();
        this.sexeTypeAttributs = other.sexeTypeAttributs == null ? null : other.sexeTypeAttributs.copy();
        this.sexeStat = other.sexeStat == null ? null : other.sexeStat.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SexeCriteria copy() {
        return new SexeCriteria(this);
    }

    public LongFilter getSexeId() {
        return sexeId;
    }

    public LongFilter sexeId() {
        if (sexeId == null) {
            sexeId = new LongFilter();
        }
        return sexeId;
    }

    public void setSexeId(LongFilter sexeId) {
        this.sexeId = sexeId;
    }

    public StringFilter getSexeValue() {
        return sexeValue;
    }

    public StringFilter sexeValue() {
        if (sexeValue == null) {
            sexeValue = new StringFilter();
        }
        return sexeValue;
    }

    public void setSexeValue(StringFilter sexeValue) {
        this.sexeValue = sexeValue;
    }

    public StringFilter getSexeDescription() {
        return sexeDescription;
    }

    public StringFilter sexeDescription() {
        if (sexeDescription == null) {
            sexeDescription = new StringFilter();
        }
        return sexeDescription;
    }

    public void setSexeDescription(StringFilter sexeDescription) {
        this.sexeDescription = sexeDescription;
    }

    public StringFilter getSexeTypeParams() {
        return sexeTypeParams;
    }

    public StringFilter sexeTypeParams() {
        if (sexeTypeParams == null) {
            sexeTypeParams = new StringFilter();
        }
        return sexeTypeParams;
    }

    public void setSexeTypeParams(StringFilter sexeTypeParams) {
        this.sexeTypeParams = sexeTypeParams;
    }

    public StringFilter getSexeTypeAttributs() {
        return sexeTypeAttributs;
    }

    public StringFilter sexeTypeAttributs() {
        if (sexeTypeAttributs == null) {
            sexeTypeAttributs = new StringFilter();
        }
        return sexeTypeAttributs;
    }

    public void setSexeTypeAttributs(StringFilter sexeTypeAttributs) {
        this.sexeTypeAttributs = sexeTypeAttributs;
    }

    public BooleanFilter getSexeStat() {
        return sexeStat;
    }

    public BooleanFilter sexeStat() {
        if (sexeStat == null) {
            sexeStat = new BooleanFilter();
        }
        return sexeStat;
    }

    public void setSexeStat(BooleanFilter sexeStat) {
        this.sexeStat = sexeStat;
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
        final SexeCriteria that = (SexeCriteria) o;
        return (
            Objects.equals(sexeId, that.sexeId) &&
            Objects.equals(sexeValue, that.sexeValue) &&
            Objects.equals(sexeDescription, that.sexeDescription) &&
            Objects.equals(sexeTypeParams, that.sexeTypeParams) &&
            Objects.equals(sexeTypeAttributs, that.sexeTypeAttributs) &&
            Objects.equals(sexeStat, that.sexeStat) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(sexeId, sexeValue, sexeDescription, sexeTypeParams, sexeTypeAttributs, sexeStat, accreditationId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SexeCriteria{" +
            (sexeId != null ? "sexeId=" + sexeId + ", " : "") +
            (sexeValue != null ? "sexeValue=" + sexeValue + ", " : "") +
            (sexeDescription != null ? "sexeDescription=" + sexeDescription + ", " : "") +
            (sexeTypeParams != null ? "sexeTypeParams=" + sexeTypeParams + ", " : "") +
            (sexeTypeAttributs != null ? "sexeTypeAttributs=" + sexeTypeAttributs + ", " : "") +
            (sexeStat != null ? "sexeStat=" + sexeStat + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.AuthentificationType} entity. This class is used
 * in {@link ma.sig.events.web.rest.AuthentificationTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /authentification-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuthentificationTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter authentificationTypeId;

    private StringFilter authentificationTypeValue;

    private StringFilter authentificationTypeDescription;

    private StringFilter authentificationTypeParams;

    private StringFilter authentificationTypeAttributs;

    private BooleanFilter authentificationTypeStat;

    private Boolean distinct;

    public AuthentificationTypeCriteria() {}

    public AuthentificationTypeCriteria(AuthentificationTypeCriteria other) {
        this.authentificationTypeId = other.authentificationTypeId == null ? null : other.authentificationTypeId.copy();
        this.authentificationTypeValue = other.authentificationTypeValue == null ? null : other.authentificationTypeValue.copy();
        this.authentificationTypeDescription =
            other.authentificationTypeDescription == null ? null : other.authentificationTypeDescription.copy();
        this.authentificationTypeParams = other.authentificationTypeParams == null ? null : other.authentificationTypeParams.copy();
        this.authentificationTypeAttributs =
            other.authentificationTypeAttributs == null ? null : other.authentificationTypeAttributs.copy();
        this.authentificationTypeStat = other.authentificationTypeStat == null ? null : other.authentificationTypeStat.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AuthentificationTypeCriteria copy() {
        return new AuthentificationTypeCriteria(this);
    }

    public LongFilter getAuthentificationTypeId() {
        return authentificationTypeId;
    }

    public LongFilter authentificationTypeId() {
        if (authentificationTypeId == null) {
            authentificationTypeId = new LongFilter();
        }
        return authentificationTypeId;
    }

    public void setAuthentificationTypeId(LongFilter authentificationTypeId) {
        this.authentificationTypeId = authentificationTypeId;
    }

    public StringFilter getAuthentificationTypeValue() {
        return authentificationTypeValue;
    }

    public StringFilter authentificationTypeValue() {
        if (authentificationTypeValue == null) {
            authentificationTypeValue = new StringFilter();
        }
        return authentificationTypeValue;
    }

    public void setAuthentificationTypeValue(StringFilter authentificationTypeValue) {
        this.authentificationTypeValue = authentificationTypeValue;
    }

    public StringFilter getAuthentificationTypeDescription() {
        return authentificationTypeDescription;
    }

    public StringFilter authentificationTypeDescription() {
        if (authentificationTypeDescription == null) {
            authentificationTypeDescription = new StringFilter();
        }
        return authentificationTypeDescription;
    }

    public void setAuthentificationTypeDescription(StringFilter authentificationTypeDescription) {
        this.authentificationTypeDescription = authentificationTypeDescription;
    }

    public StringFilter getAuthentificationTypeParams() {
        return authentificationTypeParams;
    }

    public StringFilter authentificationTypeParams() {
        if (authentificationTypeParams == null) {
            authentificationTypeParams = new StringFilter();
        }
        return authentificationTypeParams;
    }

    public void setAuthentificationTypeParams(StringFilter authentificationTypeParams) {
        this.authentificationTypeParams = authentificationTypeParams;
    }

    public StringFilter getAuthentificationTypeAttributs() {
        return authentificationTypeAttributs;
    }

    public StringFilter authentificationTypeAttributs() {
        if (authentificationTypeAttributs == null) {
            authentificationTypeAttributs = new StringFilter();
        }
        return authentificationTypeAttributs;
    }

    public void setAuthentificationTypeAttributs(StringFilter authentificationTypeAttributs) {
        this.authentificationTypeAttributs = authentificationTypeAttributs;
    }

    public BooleanFilter getAuthentificationTypeStat() {
        return authentificationTypeStat;
    }

    public BooleanFilter authentificationTypeStat() {
        if (authentificationTypeStat == null) {
            authentificationTypeStat = new BooleanFilter();
        }
        return authentificationTypeStat;
    }

    public void setAuthentificationTypeStat(BooleanFilter authentificationTypeStat) {
        this.authentificationTypeStat = authentificationTypeStat;
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
        final AuthentificationTypeCriteria that = (AuthentificationTypeCriteria) o;
        return (
            Objects.equals(authentificationTypeId, that.authentificationTypeId) &&
            Objects.equals(authentificationTypeValue, that.authentificationTypeValue) &&
            Objects.equals(authentificationTypeDescription, that.authentificationTypeDescription) &&
            Objects.equals(authentificationTypeParams, that.authentificationTypeParams) &&
            Objects.equals(authentificationTypeAttributs, that.authentificationTypeAttributs) &&
            Objects.equals(authentificationTypeStat, that.authentificationTypeStat) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            authentificationTypeId,
            authentificationTypeValue,
            authentificationTypeDescription,
            authentificationTypeParams,
            authentificationTypeAttributs,
            authentificationTypeStat,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthentificationTypeCriteria{" +
            (authentificationTypeId != null ? "authentificationTypeId=" + authentificationTypeId + ", " : "") +
            (authentificationTypeValue != null ? "authentificationTypeValue=" + authentificationTypeValue + ", " : "") +
            (authentificationTypeDescription != null ? "authentificationTypeDescription=" + authentificationTypeDescription + ", " : "") +
            (authentificationTypeParams != null ? "authentificationTypeParams=" + authentificationTypeParams + ", " : "") +
            (authentificationTypeAttributs != null ? "authentificationTypeAttributs=" + authentificationTypeAttributs + ", " : "") +
            (authentificationTypeStat != null ? "authentificationTypeStat=" + authentificationTypeStat + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.CodeType} entity. This class is used
 * in {@link ma.sig.events.web.rest.CodeTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /code-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CodeTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter codeTypeId;

    private StringFilter codeTypeValue;

    private StringFilter codeTypeDescription;

    private StringFilter codeTypeParams;

    private StringFilter codeTypeAttributs;

    private BooleanFilter codeTypeStat;

    private LongFilter codeId;

    private Boolean distinct;

    public CodeTypeCriteria() {}

    public CodeTypeCriteria(CodeTypeCriteria other) {
        this.codeTypeId = other.codeTypeId == null ? null : other.codeTypeId.copy();
        this.codeTypeValue = other.codeTypeValue == null ? null : other.codeTypeValue.copy();
        this.codeTypeDescription = other.codeTypeDescription == null ? null : other.codeTypeDescription.copy();
        this.codeTypeParams = other.codeTypeParams == null ? null : other.codeTypeParams.copy();
        this.codeTypeAttributs = other.codeTypeAttributs == null ? null : other.codeTypeAttributs.copy();
        this.codeTypeStat = other.codeTypeStat == null ? null : other.codeTypeStat.copy();
        this.codeId = other.codeId == null ? null : other.codeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CodeTypeCriteria copy() {
        return new CodeTypeCriteria(this);
    }

    public LongFilter getCodeTypeId() {
        return codeTypeId;
    }

    public LongFilter codeTypeId() {
        if (codeTypeId == null) {
            codeTypeId = new LongFilter();
        }
        return codeTypeId;
    }

    public void setCodeTypeId(LongFilter codeTypeId) {
        this.codeTypeId = codeTypeId;
    }

    public StringFilter getCodeTypeValue() {
        return codeTypeValue;
    }

    public StringFilter codeTypeValue() {
        if (codeTypeValue == null) {
            codeTypeValue = new StringFilter();
        }
        return codeTypeValue;
    }

    public void setCodeTypeValue(StringFilter codeTypeValue) {
        this.codeTypeValue = codeTypeValue;
    }

    public StringFilter getCodeTypeDescription() {
        return codeTypeDescription;
    }

    public StringFilter codeTypeDescription() {
        if (codeTypeDescription == null) {
            codeTypeDescription = new StringFilter();
        }
        return codeTypeDescription;
    }

    public void setCodeTypeDescription(StringFilter codeTypeDescription) {
        this.codeTypeDescription = codeTypeDescription;
    }

    public StringFilter getCodeTypeParams() {
        return codeTypeParams;
    }

    public StringFilter codeTypeParams() {
        if (codeTypeParams == null) {
            codeTypeParams = new StringFilter();
        }
        return codeTypeParams;
    }

    public void setCodeTypeParams(StringFilter codeTypeParams) {
        this.codeTypeParams = codeTypeParams;
    }

    public StringFilter getCodeTypeAttributs() {
        return codeTypeAttributs;
    }

    public StringFilter codeTypeAttributs() {
        if (codeTypeAttributs == null) {
            codeTypeAttributs = new StringFilter();
        }
        return codeTypeAttributs;
    }

    public void setCodeTypeAttributs(StringFilter codeTypeAttributs) {
        this.codeTypeAttributs = codeTypeAttributs;
    }

    public BooleanFilter getCodeTypeStat() {
        return codeTypeStat;
    }

    public BooleanFilter codeTypeStat() {
        if (codeTypeStat == null) {
            codeTypeStat = new BooleanFilter();
        }
        return codeTypeStat;
    }

    public void setCodeTypeStat(BooleanFilter codeTypeStat) {
        this.codeTypeStat = codeTypeStat;
    }

    public LongFilter getCodeId() {
        return codeId;
    }

    public LongFilter codeId() {
        if (codeId == null) {
            codeId = new LongFilter();
        }
        return codeId;
    }

    public void setCodeId(LongFilter codeId) {
        this.codeId = codeId;
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
        final CodeTypeCriteria that = (CodeTypeCriteria) o;
        return (
            Objects.equals(codeTypeId, that.codeTypeId) &&
            Objects.equals(codeTypeValue, that.codeTypeValue) &&
            Objects.equals(codeTypeDescription, that.codeTypeDescription) &&
            Objects.equals(codeTypeParams, that.codeTypeParams) &&
            Objects.equals(codeTypeAttributs, that.codeTypeAttributs) &&
            Objects.equals(codeTypeStat, that.codeTypeStat) &&
            Objects.equals(codeId, that.codeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            codeTypeId,
            codeTypeValue,
            codeTypeDescription,
            codeTypeParams,
            codeTypeAttributs,
            codeTypeStat,
            codeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CodeTypeCriteria{" +
            (codeTypeId != null ? "codeTypeId=" + codeTypeId + ", " : "") +
            (codeTypeValue != null ? "codeTypeValue=" + codeTypeValue + ", " : "") +
            (codeTypeDescription != null ? "codeTypeDescription=" + codeTypeDescription + ", " : "") +
            (codeTypeParams != null ? "codeTypeParams=" + codeTypeParams + ", " : "") +
            (codeTypeAttributs != null ? "codeTypeAttributs=" + codeTypeAttributs + ", " : "") +
            (codeTypeStat != null ? "codeTypeStat=" + codeTypeStat + ", " : "") +
            (codeId != null ? "codeId=" + codeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

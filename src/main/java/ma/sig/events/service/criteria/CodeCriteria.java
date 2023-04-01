package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Code} entity. This class is used
 * in {@link ma.sig.events.web.rest.CodeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /codes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CodeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter codeId;

    private StringFilter codeForEntity;

    private StringFilter codeEntityValue;

    private StringFilter codeValue;

    private BooleanFilter codeUsed;

    private StringFilter codeParams;

    private StringFilter codeAttributs;

    private BooleanFilter codeStat;

    private LongFilter accreditationId;

    private LongFilter codeTypeId;

    private LongFilter eventId;

    private Boolean distinct;

    public CodeCriteria() {}

    public CodeCriteria(CodeCriteria other) {
        this.codeId = other.codeId == null ? null : other.codeId.copy();
        this.codeForEntity = other.codeForEntity == null ? null : other.codeForEntity.copy();
        this.codeEntityValue = other.codeEntityValue == null ? null : other.codeEntityValue.copy();
        this.codeValue = other.codeValue == null ? null : other.codeValue.copy();
        this.codeUsed = other.codeUsed == null ? null : other.codeUsed.copy();
        this.codeParams = other.codeParams == null ? null : other.codeParams.copy();
        this.codeAttributs = other.codeAttributs == null ? null : other.codeAttributs.copy();
        this.codeStat = other.codeStat == null ? null : other.codeStat.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.codeTypeId = other.codeTypeId == null ? null : other.codeTypeId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CodeCriteria copy() {
        return new CodeCriteria(this);
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

    public StringFilter getCodeForEntity() {
        return codeForEntity;
    }

    public StringFilter codeForEntity() {
        if (codeForEntity == null) {
            codeForEntity = new StringFilter();
        }
        return codeForEntity;
    }

    public void setCodeForEntity(StringFilter codeForEntity) {
        this.codeForEntity = codeForEntity;
    }

    public StringFilter getCodeEntityValue() {
        return codeEntityValue;
    }

    public StringFilter codeEntityValue() {
        if (codeEntityValue == null) {
            codeEntityValue = new StringFilter();
        }
        return codeEntityValue;
    }

    public void setCodeEntityValue(StringFilter codeEntityValue) {
        this.codeEntityValue = codeEntityValue;
    }

    public StringFilter getCodeValue() {
        return codeValue;
    }

    public StringFilter codeValue() {
        if (codeValue == null) {
            codeValue = new StringFilter();
        }
        return codeValue;
    }

    public void setCodeValue(StringFilter codeValue) {
        this.codeValue = codeValue;
    }

    public BooleanFilter getCodeUsed() {
        return codeUsed;
    }

    public BooleanFilter codeUsed() {
        if (codeUsed == null) {
            codeUsed = new BooleanFilter();
        }
        return codeUsed;
    }

    public void setCodeUsed(BooleanFilter codeUsed) {
        this.codeUsed = codeUsed;
    }

    public StringFilter getCodeParams() {
        return codeParams;
    }

    public StringFilter codeParams() {
        if (codeParams == null) {
            codeParams = new StringFilter();
        }
        return codeParams;
    }

    public void setCodeParams(StringFilter codeParams) {
        this.codeParams = codeParams;
    }

    public StringFilter getCodeAttributs() {
        return codeAttributs;
    }

    public StringFilter codeAttributs() {
        if (codeAttributs == null) {
            codeAttributs = new StringFilter();
        }
        return codeAttributs;
    }

    public void setCodeAttributs(StringFilter codeAttributs) {
        this.codeAttributs = codeAttributs;
    }

    public BooleanFilter getCodeStat() {
        return codeStat;
    }

    public BooleanFilter codeStat() {
        if (codeStat == null) {
            codeStat = new BooleanFilter();
        }
        return codeStat;
    }

    public void setCodeStat(BooleanFilter codeStat) {
        this.codeStat = codeStat;
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

    public LongFilter getEventId() {
        return eventId;
    }

    public LongFilter eventId() {
        if (eventId == null) {
            eventId = new LongFilter();
        }
        return eventId;
    }

    public void setEventId(LongFilter eventId) {
        this.eventId = eventId;
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
        final CodeCriteria that = (CodeCriteria) o;
        return (
            Objects.equals(codeId, that.codeId) &&
            Objects.equals(codeForEntity, that.codeForEntity) &&
            Objects.equals(codeEntityValue, that.codeEntityValue) &&
            Objects.equals(codeValue, that.codeValue) &&
            Objects.equals(codeUsed, that.codeUsed) &&
            Objects.equals(codeParams, that.codeParams) &&
            Objects.equals(codeAttributs, that.codeAttributs) &&
            Objects.equals(codeStat, that.codeStat) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(codeTypeId, that.codeTypeId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            codeId,
            codeForEntity,
            codeEntityValue,
            codeValue,
            codeUsed,
            codeParams,
            codeAttributs,
            codeStat,
            accreditationId,
            codeTypeId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CodeCriteria{" +
            (codeId != null ? "codeId=" + codeId + ", " : "") +
            (codeForEntity != null ? "codeForEntity=" + codeForEntity + ", " : "") +
            (codeEntityValue != null ? "codeEntityValue=" + codeEntityValue + ", " : "") +
            (codeValue != null ? "codeValue=" + codeValue + ", " : "") +
            (codeUsed != null ? "codeUsed=" + codeUsed + ", " : "") +
            (codeParams != null ? "codeParams=" + codeParams + ", " : "") +
            (codeAttributs != null ? "codeAttributs=" + codeAttributs + ", " : "") +
            (codeStat != null ? "codeStat=" + codeStat + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (codeTypeId != null ? "codeTypeId=" + codeTypeId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

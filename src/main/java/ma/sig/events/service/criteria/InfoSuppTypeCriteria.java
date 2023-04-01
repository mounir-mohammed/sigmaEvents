package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.InfoSuppType} entity. This class is used
 * in {@link ma.sig.events.web.rest.InfoSuppTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /info-supp-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InfoSuppTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter infoSuppTypeId;

    private StringFilter infoSuppTypeName;

    private StringFilter infoSuppTypeDescription;

    private StringFilter infoSuppTypeParams;

    private StringFilter infoSuppTypeAttributs;

    private BooleanFilter infoSuppTypeStat;

    private LongFilter infoSuppId;

    private Boolean distinct;

    public InfoSuppTypeCriteria() {}

    public InfoSuppTypeCriteria(InfoSuppTypeCriteria other) {
        this.infoSuppTypeId = other.infoSuppTypeId == null ? null : other.infoSuppTypeId.copy();
        this.infoSuppTypeName = other.infoSuppTypeName == null ? null : other.infoSuppTypeName.copy();
        this.infoSuppTypeDescription = other.infoSuppTypeDescription == null ? null : other.infoSuppTypeDescription.copy();
        this.infoSuppTypeParams = other.infoSuppTypeParams == null ? null : other.infoSuppTypeParams.copy();
        this.infoSuppTypeAttributs = other.infoSuppTypeAttributs == null ? null : other.infoSuppTypeAttributs.copy();
        this.infoSuppTypeStat = other.infoSuppTypeStat == null ? null : other.infoSuppTypeStat.copy();
        this.infoSuppId = other.infoSuppId == null ? null : other.infoSuppId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InfoSuppTypeCriteria copy() {
        return new InfoSuppTypeCriteria(this);
    }

    public LongFilter getInfoSuppTypeId() {
        return infoSuppTypeId;
    }

    public LongFilter infoSuppTypeId() {
        if (infoSuppTypeId == null) {
            infoSuppTypeId = new LongFilter();
        }
        return infoSuppTypeId;
    }

    public void setInfoSuppTypeId(LongFilter infoSuppTypeId) {
        this.infoSuppTypeId = infoSuppTypeId;
    }

    public StringFilter getInfoSuppTypeName() {
        return infoSuppTypeName;
    }

    public StringFilter infoSuppTypeName() {
        if (infoSuppTypeName == null) {
            infoSuppTypeName = new StringFilter();
        }
        return infoSuppTypeName;
    }

    public void setInfoSuppTypeName(StringFilter infoSuppTypeName) {
        this.infoSuppTypeName = infoSuppTypeName;
    }

    public StringFilter getInfoSuppTypeDescription() {
        return infoSuppTypeDescription;
    }

    public StringFilter infoSuppTypeDescription() {
        if (infoSuppTypeDescription == null) {
            infoSuppTypeDescription = new StringFilter();
        }
        return infoSuppTypeDescription;
    }

    public void setInfoSuppTypeDescription(StringFilter infoSuppTypeDescription) {
        this.infoSuppTypeDescription = infoSuppTypeDescription;
    }

    public StringFilter getInfoSuppTypeParams() {
        return infoSuppTypeParams;
    }

    public StringFilter infoSuppTypeParams() {
        if (infoSuppTypeParams == null) {
            infoSuppTypeParams = new StringFilter();
        }
        return infoSuppTypeParams;
    }

    public void setInfoSuppTypeParams(StringFilter infoSuppTypeParams) {
        this.infoSuppTypeParams = infoSuppTypeParams;
    }

    public StringFilter getInfoSuppTypeAttributs() {
        return infoSuppTypeAttributs;
    }

    public StringFilter infoSuppTypeAttributs() {
        if (infoSuppTypeAttributs == null) {
            infoSuppTypeAttributs = new StringFilter();
        }
        return infoSuppTypeAttributs;
    }

    public void setInfoSuppTypeAttributs(StringFilter infoSuppTypeAttributs) {
        this.infoSuppTypeAttributs = infoSuppTypeAttributs;
    }

    public BooleanFilter getInfoSuppTypeStat() {
        return infoSuppTypeStat;
    }

    public BooleanFilter infoSuppTypeStat() {
        if (infoSuppTypeStat == null) {
            infoSuppTypeStat = new BooleanFilter();
        }
        return infoSuppTypeStat;
    }

    public void setInfoSuppTypeStat(BooleanFilter infoSuppTypeStat) {
        this.infoSuppTypeStat = infoSuppTypeStat;
    }

    public LongFilter getInfoSuppId() {
        return infoSuppId;
    }

    public LongFilter infoSuppId() {
        if (infoSuppId == null) {
            infoSuppId = new LongFilter();
        }
        return infoSuppId;
    }

    public void setInfoSuppId(LongFilter infoSuppId) {
        this.infoSuppId = infoSuppId;
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
        final InfoSuppTypeCriteria that = (InfoSuppTypeCriteria) o;
        return (
            Objects.equals(infoSuppTypeId, that.infoSuppTypeId) &&
            Objects.equals(infoSuppTypeName, that.infoSuppTypeName) &&
            Objects.equals(infoSuppTypeDescription, that.infoSuppTypeDescription) &&
            Objects.equals(infoSuppTypeParams, that.infoSuppTypeParams) &&
            Objects.equals(infoSuppTypeAttributs, that.infoSuppTypeAttributs) &&
            Objects.equals(infoSuppTypeStat, that.infoSuppTypeStat) &&
            Objects.equals(infoSuppId, that.infoSuppId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            infoSuppTypeId,
            infoSuppTypeName,
            infoSuppTypeDescription,
            infoSuppTypeParams,
            infoSuppTypeAttributs,
            infoSuppTypeStat,
            infoSuppId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfoSuppTypeCriteria{" +
            (infoSuppTypeId != null ? "infoSuppTypeId=" + infoSuppTypeId + ", " : "") +
            (infoSuppTypeName != null ? "infoSuppTypeName=" + infoSuppTypeName + ", " : "") +
            (infoSuppTypeDescription != null ? "infoSuppTypeDescription=" + infoSuppTypeDescription + ", " : "") +
            (infoSuppTypeParams != null ? "infoSuppTypeParams=" + infoSuppTypeParams + ", " : "") +
            (infoSuppTypeAttributs != null ? "infoSuppTypeAttributs=" + infoSuppTypeAttributs + ", " : "") +
            (infoSuppTypeStat != null ? "infoSuppTypeStat=" + infoSuppTypeStat + ", " : "") +
            (infoSuppId != null ? "infoSuppId=" + infoSuppId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

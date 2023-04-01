package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Cloning} entity. This class is used
 * in {@link ma.sig.events.web.rest.CloningResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clonings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CloningCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter cloningId;

    private StringFilter cloningDescription;

    private LongFilter cloningOldEventId;

    private LongFilter cloningNewEventId;

    private LongFilter cloningUserId;

    private ZonedDateTimeFilter cloningDate;

    private StringFilter clonedEntitys;

    private StringFilter clonedParams;

    private StringFilter clonedAttributs;

    private BooleanFilter clonedStat;

    private Boolean distinct;

    public CloningCriteria() {}

    public CloningCriteria(CloningCriteria other) {
        this.cloningId = other.cloningId == null ? null : other.cloningId.copy();
        this.cloningDescription = other.cloningDescription == null ? null : other.cloningDescription.copy();
        this.cloningOldEventId = other.cloningOldEventId == null ? null : other.cloningOldEventId.copy();
        this.cloningNewEventId = other.cloningNewEventId == null ? null : other.cloningNewEventId.copy();
        this.cloningUserId = other.cloningUserId == null ? null : other.cloningUserId.copy();
        this.cloningDate = other.cloningDate == null ? null : other.cloningDate.copy();
        this.clonedEntitys = other.clonedEntitys == null ? null : other.clonedEntitys.copy();
        this.clonedParams = other.clonedParams == null ? null : other.clonedParams.copy();
        this.clonedAttributs = other.clonedAttributs == null ? null : other.clonedAttributs.copy();
        this.clonedStat = other.clonedStat == null ? null : other.clonedStat.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CloningCriteria copy() {
        return new CloningCriteria(this);
    }

    public LongFilter getCloningId() {
        return cloningId;
    }

    public LongFilter cloningId() {
        if (cloningId == null) {
            cloningId = new LongFilter();
        }
        return cloningId;
    }

    public void setCloningId(LongFilter cloningId) {
        this.cloningId = cloningId;
    }

    public StringFilter getCloningDescription() {
        return cloningDescription;
    }

    public StringFilter cloningDescription() {
        if (cloningDescription == null) {
            cloningDescription = new StringFilter();
        }
        return cloningDescription;
    }

    public void setCloningDescription(StringFilter cloningDescription) {
        this.cloningDescription = cloningDescription;
    }

    public LongFilter getCloningOldEventId() {
        return cloningOldEventId;
    }

    public LongFilter cloningOldEventId() {
        if (cloningOldEventId == null) {
            cloningOldEventId = new LongFilter();
        }
        return cloningOldEventId;
    }

    public void setCloningOldEventId(LongFilter cloningOldEventId) {
        this.cloningOldEventId = cloningOldEventId;
    }

    public LongFilter getCloningNewEventId() {
        return cloningNewEventId;
    }

    public LongFilter cloningNewEventId() {
        if (cloningNewEventId == null) {
            cloningNewEventId = new LongFilter();
        }
        return cloningNewEventId;
    }

    public void setCloningNewEventId(LongFilter cloningNewEventId) {
        this.cloningNewEventId = cloningNewEventId;
    }

    public LongFilter getCloningUserId() {
        return cloningUserId;
    }

    public LongFilter cloningUserId() {
        if (cloningUserId == null) {
            cloningUserId = new LongFilter();
        }
        return cloningUserId;
    }

    public void setCloningUserId(LongFilter cloningUserId) {
        this.cloningUserId = cloningUserId;
    }

    public ZonedDateTimeFilter getCloningDate() {
        return cloningDate;
    }

    public ZonedDateTimeFilter cloningDate() {
        if (cloningDate == null) {
            cloningDate = new ZonedDateTimeFilter();
        }
        return cloningDate;
    }

    public void setCloningDate(ZonedDateTimeFilter cloningDate) {
        this.cloningDate = cloningDate;
    }

    public StringFilter getClonedEntitys() {
        return clonedEntitys;
    }

    public StringFilter clonedEntitys() {
        if (clonedEntitys == null) {
            clonedEntitys = new StringFilter();
        }
        return clonedEntitys;
    }

    public void setClonedEntitys(StringFilter clonedEntitys) {
        this.clonedEntitys = clonedEntitys;
    }

    public StringFilter getClonedParams() {
        return clonedParams;
    }

    public StringFilter clonedParams() {
        if (clonedParams == null) {
            clonedParams = new StringFilter();
        }
        return clonedParams;
    }

    public void setClonedParams(StringFilter clonedParams) {
        this.clonedParams = clonedParams;
    }

    public StringFilter getClonedAttributs() {
        return clonedAttributs;
    }

    public StringFilter clonedAttributs() {
        if (clonedAttributs == null) {
            clonedAttributs = new StringFilter();
        }
        return clonedAttributs;
    }

    public void setClonedAttributs(StringFilter clonedAttributs) {
        this.clonedAttributs = clonedAttributs;
    }

    public BooleanFilter getClonedStat() {
        return clonedStat;
    }

    public BooleanFilter clonedStat() {
        if (clonedStat == null) {
            clonedStat = new BooleanFilter();
        }
        return clonedStat;
    }

    public void setClonedStat(BooleanFilter clonedStat) {
        this.clonedStat = clonedStat;
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
        final CloningCriteria that = (CloningCriteria) o;
        return (
            Objects.equals(cloningId, that.cloningId) &&
            Objects.equals(cloningDescription, that.cloningDescription) &&
            Objects.equals(cloningOldEventId, that.cloningOldEventId) &&
            Objects.equals(cloningNewEventId, that.cloningNewEventId) &&
            Objects.equals(cloningUserId, that.cloningUserId) &&
            Objects.equals(cloningDate, that.cloningDate) &&
            Objects.equals(clonedEntitys, that.clonedEntitys) &&
            Objects.equals(clonedParams, that.clonedParams) &&
            Objects.equals(clonedAttributs, that.clonedAttributs) &&
            Objects.equals(clonedStat, that.clonedStat) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            cloningId,
            cloningDescription,
            cloningOldEventId,
            cloningNewEventId,
            cloningUserId,
            cloningDate,
            clonedEntitys,
            clonedParams,
            clonedAttributs,
            clonedStat,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CloningCriteria{" +
            (cloningId != null ? "cloningId=" + cloningId + ", " : "") +
            (cloningDescription != null ? "cloningDescription=" + cloningDescription + ", " : "") +
            (cloningOldEventId != null ? "cloningOldEventId=" + cloningOldEventId + ", " : "") +
            (cloningNewEventId != null ? "cloningNewEventId=" + cloningNewEventId + ", " : "") +
            (cloningUserId != null ? "cloningUserId=" + cloningUserId + ", " : "") +
            (cloningDate != null ? "cloningDate=" + cloningDate + ", " : "") +
            (clonedEntitys != null ? "clonedEntitys=" + clonedEntitys + ", " : "") +
            (clonedParams != null ? "clonedParams=" + clonedParams + ", " : "") +
            (clonedAttributs != null ? "clonedAttributs=" + clonedAttributs + ", " : "") +
            (clonedStat != null ? "clonedStat=" + clonedStat + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.InfoSupp} entity. This class is used
 * in {@link ma.sig.events.web.rest.InfoSuppResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /info-supps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InfoSuppCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter infoSuppId;

    private StringFilter infoSuppName;

    private StringFilter infoSuppDescription;

    private StringFilter infoSuppParams;

    private StringFilter infoSuppAttributs;

    private BooleanFilter infoSuppStat;

    private LongFilter infoSuppTypeId;

    private LongFilter accreditationId;

    private LongFilter eventId;

    private Boolean distinct;

    public InfoSuppCriteria() {}

    public InfoSuppCriteria(InfoSuppCriteria other) {
        this.infoSuppId = other.infoSuppId == null ? null : other.infoSuppId.copy();
        this.infoSuppName = other.infoSuppName == null ? null : other.infoSuppName.copy();
        this.infoSuppDescription = other.infoSuppDescription == null ? null : other.infoSuppDescription.copy();
        this.infoSuppParams = other.infoSuppParams == null ? null : other.infoSuppParams.copy();
        this.infoSuppAttributs = other.infoSuppAttributs == null ? null : other.infoSuppAttributs.copy();
        this.infoSuppStat = other.infoSuppStat == null ? null : other.infoSuppStat.copy();
        this.infoSuppTypeId = other.infoSuppTypeId == null ? null : other.infoSuppTypeId.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InfoSuppCriteria copy() {
        return new InfoSuppCriteria(this);
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

    public StringFilter getInfoSuppName() {
        return infoSuppName;
    }

    public StringFilter infoSuppName() {
        if (infoSuppName == null) {
            infoSuppName = new StringFilter();
        }
        return infoSuppName;
    }

    public void setInfoSuppName(StringFilter infoSuppName) {
        this.infoSuppName = infoSuppName;
    }

    public StringFilter getInfoSuppDescription() {
        return infoSuppDescription;
    }

    public StringFilter infoSuppDescription() {
        if (infoSuppDescription == null) {
            infoSuppDescription = new StringFilter();
        }
        return infoSuppDescription;
    }

    public void setInfoSuppDescription(StringFilter infoSuppDescription) {
        this.infoSuppDescription = infoSuppDescription;
    }

    public StringFilter getInfoSuppParams() {
        return infoSuppParams;
    }

    public StringFilter infoSuppParams() {
        if (infoSuppParams == null) {
            infoSuppParams = new StringFilter();
        }
        return infoSuppParams;
    }

    public void setInfoSuppParams(StringFilter infoSuppParams) {
        this.infoSuppParams = infoSuppParams;
    }

    public StringFilter getInfoSuppAttributs() {
        return infoSuppAttributs;
    }

    public StringFilter infoSuppAttributs() {
        if (infoSuppAttributs == null) {
            infoSuppAttributs = new StringFilter();
        }
        return infoSuppAttributs;
    }

    public void setInfoSuppAttributs(StringFilter infoSuppAttributs) {
        this.infoSuppAttributs = infoSuppAttributs;
    }

    public BooleanFilter getInfoSuppStat() {
        return infoSuppStat;
    }

    public BooleanFilter infoSuppStat() {
        if (infoSuppStat == null) {
            infoSuppStat = new BooleanFilter();
        }
        return infoSuppStat;
    }

    public void setInfoSuppStat(BooleanFilter infoSuppStat) {
        this.infoSuppStat = infoSuppStat;
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
        final InfoSuppCriteria that = (InfoSuppCriteria) o;
        return (
            Objects.equals(infoSuppId, that.infoSuppId) &&
            Objects.equals(infoSuppName, that.infoSuppName) &&
            Objects.equals(infoSuppDescription, that.infoSuppDescription) &&
            Objects.equals(infoSuppParams, that.infoSuppParams) &&
            Objects.equals(infoSuppAttributs, that.infoSuppAttributs) &&
            Objects.equals(infoSuppStat, that.infoSuppStat) &&
            Objects.equals(infoSuppTypeId, that.infoSuppTypeId) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            infoSuppId,
            infoSuppName,
            infoSuppDescription,
            infoSuppParams,
            infoSuppAttributs,
            infoSuppStat,
            infoSuppTypeId,
            accreditationId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfoSuppCriteria{" +
            (infoSuppId != null ? "infoSuppId=" + infoSuppId + ", " : "") +
            (infoSuppName != null ? "infoSuppName=" + infoSuppName + ", " : "") +
            (infoSuppDescription != null ? "infoSuppDescription=" + infoSuppDescription + ", " : "") +
            (infoSuppParams != null ? "infoSuppParams=" + infoSuppParams + ", " : "") +
            (infoSuppAttributs != null ? "infoSuppAttributs=" + infoSuppAttributs + ", " : "") +
            (infoSuppStat != null ? "infoSuppStat=" + infoSuppStat + ", " : "") +
            (infoSuppTypeId != null ? "infoSuppTypeId=" + infoSuppTypeId + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

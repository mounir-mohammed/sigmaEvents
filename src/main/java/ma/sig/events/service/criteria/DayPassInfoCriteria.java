package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.DayPassInfo} entity. This class is used
 * in {@link ma.sig.events.web.rest.DayPassInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /day-pass-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DayPassInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter dayPassInfoId;

    private StringFilter dayPassInfoName;

    private StringFilter dayPassDescription;

    private ZonedDateTimeFilter dayPassInfoCreationDate;

    private ZonedDateTimeFilter dayPassInfoUpdateDate;

    private StringFilter dayPassInfoCreatedByuser;

    private ZonedDateTimeFilter dayPassInfoDateStart;

    private ZonedDateTimeFilter dayPassInfoDateEnd;

    private LongFilter dayPassInfoNumber;

    private StringFilter dayPassParams;

    private StringFilter dayPassAttributs;

    private BooleanFilter dayPassInfoStat;

    private LongFilter accreditationId;

    private LongFilter eventId;

    private Boolean distinct;

    public DayPassInfoCriteria() {}

    public DayPassInfoCriteria(DayPassInfoCriteria other) {
        this.dayPassInfoId = other.dayPassInfoId == null ? null : other.dayPassInfoId.copy();
        this.dayPassInfoName = other.dayPassInfoName == null ? null : other.dayPassInfoName.copy();
        this.dayPassDescription = other.dayPassDescription == null ? null : other.dayPassDescription.copy();
        this.dayPassInfoCreationDate = other.dayPassInfoCreationDate == null ? null : other.dayPassInfoCreationDate.copy();
        this.dayPassInfoUpdateDate = other.dayPassInfoUpdateDate == null ? null : other.dayPassInfoUpdateDate.copy();
        this.dayPassInfoCreatedByuser = other.dayPassInfoCreatedByuser == null ? null : other.dayPassInfoCreatedByuser.copy();
        this.dayPassInfoDateStart = other.dayPassInfoDateStart == null ? null : other.dayPassInfoDateStart.copy();
        this.dayPassInfoDateEnd = other.dayPassInfoDateEnd == null ? null : other.dayPassInfoDateEnd.copy();
        this.dayPassInfoNumber = other.dayPassInfoNumber == null ? null : other.dayPassInfoNumber.copy();
        this.dayPassParams = other.dayPassParams == null ? null : other.dayPassParams.copy();
        this.dayPassAttributs = other.dayPassAttributs == null ? null : other.dayPassAttributs.copy();
        this.dayPassInfoStat = other.dayPassInfoStat == null ? null : other.dayPassInfoStat.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DayPassInfoCriteria copy() {
        return new DayPassInfoCriteria(this);
    }

    public LongFilter getDayPassInfoId() {
        return dayPassInfoId;
    }

    public LongFilter dayPassInfoId() {
        if (dayPassInfoId == null) {
            dayPassInfoId = new LongFilter();
        }
        return dayPassInfoId;
    }

    public void setDayPassInfoId(LongFilter dayPassInfoId) {
        this.dayPassInfoId = dayPassInfoId;
    }

    public StringFilter getDayPassInfoName() {
        return dayPassInfoName;
    }

    public StringFilter dayPassInfoName() {
        if (dayPassInfoName == null) {
            dayPassInfoName = new StringFilter();
        }
        return dayPassInfoName;
    }

    public void setDayPassInfoName(StringFilter dayPassInfoName) {
        this.dayPassInfoName = dayPassInfoName;
    }

    public StringFilter getDayPassDescription() {
        return dayPassDescription;
    }

    public StringFilter dayPassDescription() {
        if (dayPassDescription == null) {
            dayPassDescription = new StringFilter();
        }
        return dayPassDescription;
    }

    public void setDayPassDescription(StringFilter dayPassDescription) {
        this.dayPassDescription = dayPassDescription;
    }

    public ZonedDateTimeFilter getDayPassInfoCreationDate() {
        return dayPassInfoCreationDate;
    }

    public ZonedDateTimeFilter dayPassInfoCreationDate() {
        if (dayPassInfoCreationDate == null) {
            dayPassInfoCreationDate = new ZonedDateTimeFilter();
        }
        return dayPassInfoCreationDate;
    }

    public void setDayPassInfoCreationDate(ZonedDateTimeFilter dayPassInfoCreationDate) {
        this.dayPassInfoCreationDate = dayPassInfoCreationDate;
    }

    public ZonedDateTimeFilter getDayPassInfoUpdateDate() {
        return dayPassInfoUpdateDate;
    }

    public ZonedDateTimeFilter dayPassInfoUpdateDate() {
        if (dayPassInfoUpdateDate == null) {
            dayPassInfoUpdateDate = new ZonedDateTimeFilter();
        }
        return dayPassInfoUpdateDate;
    }

    public void setDayPassInfoUpdateDate(ZonedDateTimeFilter dayPassInfoUpdateDate) {
        this.dayPassInfoUpdateDate = dayPassInfoUpdateDate;
    }

    public StringFilter getDayPassInfoCreatedByuser() {
        return dayPassInfoCreatedByuser;
    }

    public StringFilter dayPassInfoCreatedByuser() {
        if (dayPassInfoCreatedByuser == null) {
            dayPassInfoCreatedByuser = new StringFilter();
        }
        return dayPassInfoCreatedByuser;
    }

    public void setDayPassInfoCreatedByuser(StringFilter dayPassInfoCreatedByuser) {
        this.dayPassInfoCreatedByuser = dayPassInfoCreatedByuser;
    }

    public ZonedDateTimeFilter getDayPassInfoDateStart() {
        return dayPassInfoDateStart;
    }

    public ZonedDateTimeFilter dayPassInfoDateStart() {
        if (dayPassInfoDateStart == null) {
            dayPassInfoDateStart = new ZonedDateTimeFilter();
        }
        return dayPassInfoDateStart;
    }

    public void setDayPassInfoDateStart(ZonedDateTimeFilter dayPassInfoDateStart) {
        this.dayPassInfoDateStart = dayPassInfoDateStart;
    }

    public ZonedDateTimeFilter getDayPassInfoDateEnd() {
        return dayPassInfoDateEnd;
    }

    public ZonedDateTimeFilter dayPassInfoDateEnd() {
        if (dayPassInfoDateEnd == null) {
            dayPassInfoDateEnd = new ZonedDateTimeFilter();
        }
        return dayPassInfoDateEnd;
    }

    public void setDayPassInfoDateEnd(ZonedDateTimeFilter dayPassInfoDateEnd) {
        this.dayPassInfoDateEnd = dayPassInfoDateEnd;
    }

    public LongFilter getDayPassInfoNumber() {
        return dayPassInfoNumber;
    }

    public LongFilter dayPassInfoNumber() {
        if (dayPassInfoNumber == null) {
            dayPassInfoNumber = new LongFilter();
        }
        return dayPassInfoNumber;
    }

    public void setDayPassInfoNumber(LongFilter dayPassInfoNumber) {
        this.dayPassInfoNumber = dayPassInfoNumber;
    }

    public StringFilter getDayPassParams() {
        return dayPassParams;
    }

    public StringFilter dayPassParams() {
        if (dayPassParams == null) {
            dayPassParams = new StringFilter();
        }
        return dayPassParams;
    }

    public void setDayPassParams(StringFilter dayPassParams) {
        this.dayPassParams = dayPassParams;
    }

    public StringFilter getDayPassAttributs() {
        return dayPassAttributs;
    }

    public StringFilter dayPassAttributs() {
        if (dayPassAttributs == null) {
            dayPassAttributs = new StringFilter();
        }
        return dayPassAttributs;
    }

    public void setDayPassAttributs(StringFilter dayPassAttributs) {
        this.dayPassAttributs = dayPassAttributs;
    }

    public BooleanFilter getDayPassInfoStat() {
        return dayPassInfoStat;
    }

    public BooleanFilter dayPassInfoStat() {
        if (dayPassInfoStat == null) {
            dayPassInfoStat = new BooleanFilter();
        }
        return dayPassInfoStat;
    }

    public void setDayPassInfoStat(BooleanFilter dayPassInfoStat) {
        this.dayPassInfoStat = dayPassInfoStat;
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
        final DayPassInfoCriteria that = (DayPassInfoCriteria) o;
        return (
            Objects.equals(dayPassInfoId, that.dayPassInfoId) &&
            Objects.equals(dayPassInfoName, that.dayPassInfoName) &&
            Objects.equals(dayPassDescription, that.dayPassDescription) &&
            Objects.equals(dayPassInfoCreationDate, that.dayPassInfoCreationDate) &&
            Objects.equals(dayPassInfoUpdateDate, that.dayPassInfoUpdateDate) &&
            Objects.equals(dayPassInfoCreatedByuser, that.dayPassInfoCreatedByuser) &&
            Objects.equals(dayPassInfoDateStart, that.dayPassInfoDateStart) &&
            Objects.equals(dayPassInfoDateEnd, that.dayPassInfoDateEnd) &&
            Objects.equals(dayPassInfoNumber, that.dayPassInfoNumber) &&
            Objects.equals(dayPassParams, that.dayPassParams) &&
            Objects.equals(dayPassAttributs, that.dayPassAttributs) &&
            Objects.equals(dayPassInfoStat, that.dayPassInfoStat) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            dayPassInfoId,
            dayPassInfoName,
            dayPassDescription,
            dayPassInfoCreationDate,
            dayPassInfoUpdateDate,
            dayPassInfoCreatedByuser,
            dayPassInfoDateStart,
            dayPassInfoDateEnd,
            dayPassInfoNumber,
            dayPassParams,
            dayPassAttributs,
            dayPassInfoStat,
            accreditationId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DayPassInfoCriteria{" +
            (dayPassInfoId != null ? "dayPassInfoId=" + dayPassInfoId + ", " : "") +
            (dayPassInfoName != null ? "dayPassInfoName=" + dayPassInfoName + ", " : "") +
            (dayPassDescription != null ? "dayPassDescription=" + dayPassDescription + ", " : "") +
            (dayPassInfoCreationDate != null ? "dayPassInfoCreationDate=" + dayPassInfoCreationDate + ", " : "") +
            (dayPassInfoUpdateDate != null ? "dayPassInfoUpdateDate=" + dayPassInfoUpdateDate + ", " : "") +
            (dayPassInfoCreatedByuser != null ? "dayPassInfoCreatedByuser=" + dayPassInfoCreatedByuser + ", " : "") +
            (dayPassInfoDateStart != null ? "dayPassInfoDateStart=" + dayPassInfoDateStart + ", " : "") +
            (dayPassInfoDateEnd != null ? "dayPassInfoDateEnd=" + dayPassInfoDateEnd + ", " : "") +
            (dayPassInfoNumber != null ? "dayPassInfoNumber=" + dayPassInfoNumber + ", " : "") +
            (dayPassParams != null ? "dayPassParams=" + dayPassParams + ", " : "") +
            (dayPassAttributs != null ? "dayPassAttributs=" + dayPassAttributs + ", " : "") +
            (dayPassInfoStat != null ? "dayPassInfoStat=" + dayPassInfoStat + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

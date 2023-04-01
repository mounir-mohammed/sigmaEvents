package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.CheckAccreditationReport} entity. This class is used
 * in {@link ma.sig.events.web.rest.CheckAccreditationReportResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /check-accreditation-reports?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckAccreditationReportCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter checkAccreditationReportId;

    private StringFilter checkAccreditationReportDescription;

    private StringFilter checkAccreditationReportParams;

    private StringFilter checkAccreditationReportAttributs;

    private BooleanFilter checkAccreditationReportStat;

    private LongFilter eventId;

    private LongFilter checkAccreditationHistoryId;

    private Boolean distinct;

    public CheckAccreditationReportCriteria() {}

    public CheckAccreditationReportCriteria(CheckAccreditationReportCriteria other) {
        this.checkAccreditationReportId = other.checkAccreditationReportId == null ? null : other.checkAccreditationReportId.copy();
        this.checkAccreditationReportDescription =
            other.checkAccreditationReportDescription == null ? null : other.checkAccreditationReportDescription.copy();
        this.checkAccreditationReportParams =
            other.checkAccreditationReportParams == null ? null : other.checkAccreditationReportParams.copy();
        this.checkAccreditationReportAttributs =
            other.checkAccreditationReportAttributs == null ? null : other.checkAccreditationReportAttributs.copy();
        this.checkAccreditationReportStat = other.checkAccreditationReportStat == null ? null : other.checkAccreditationReportStat.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.checkAccreditationHistoryId = other.checkAccreditationHistoryId == null ? null : other.checkAccreditationHistoryId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CheckAccreditationReportCriteria copy() {
        return new CheckAccreditationReportCriteria(this);
    }

    public LongFilter getCheckAccreditationReportId() {
        return checkAccreditationReportId;
    }

    public LongFilter checkAccreditationReportId() {
        if (checkAccreditationReportId == null) {
            checkAccreditationReportId = new LongFilter();
        }
        return checkAccreditationReportId;
    }

    public void setCheckAccreditationReportId(LongFilter checkAccreditationReportId) {
        this.checkAccreditationReportId = checkAccreditationReportId;
    }

    public StringFilter getCheckAccreditationReportDescription() {
        return checkAccreditationReportDescription;
    }

    public StringFilter checkAccreditationReportDescription() {
        if (checkAccreditationReportDescription == null) {
            checkAccreditationReportDescription = new StringFilter();
        }
        return checkAccreditationReportDescription;
    }

    public void setCheckAccreditationReportDescription(StringFilter checkAccreditationReportDescription) {
        this.checkAccreditationReportDescription = checkAccreditationReportDescription;
    }

    public StringFilter getCheckAccreditationReportParams() {
        return checkAccreditationReportParams;
    }

    public StringFilter checkAccreditationReportParams() {
        if (checkAccreditationReportParams == null) {
            checkAccreditationReportParams = new StringFilter();
        }
        return checkAccreditationReportParams;
    }

    public void setCheckAccreditationReportParams(StringFilter checkAccreditationReportParams) {
        this.checkAccreditationReportParams = checkAccreditationReportParams;
    }

    public StringFilter getCheckAccreditationReportAttributs() {
        return checkAccreditationReportAttributs;
    }

    public StringFilter checkAccreditationReportAttributs() {
        if (checkAccreditationReportAttributs == null) {
            checkAccreditationReportAttributs = new StringFilter();
        }
        return checkAccreditationReportAttributs;
    }

    public void setCheckAccreditationReportAttributs(StringFilter checkAccreditationReportAttributs) {
        this.checkAccreditationReportAttributs = checkAccreditationReportAttributs;
    }

    public BooleanFilter getCheckAccreditationReportStat() {
        return checkAccreditationReportStat;
    }

    public BooleanFilter checkAccreditationReportStat() {
        if (checkAccreditationReportStat == null) {
            checkAccreditationReportStat = new BooleanFilter();
        }
        return checkAccreditationReportStat;
    }

    public void setCheckAccreditationReportStat(BooleanFilter checkAccreditationReportStat) {
        this.checkAccreditationReportStat = checkAccreditationReportStat;
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

    public LongFilter getCheckAccreditationHistoryId() {
        return checkAccreditationHistoryId;
    }

    public LongFilter checkAccreditationHistoryId() {
        if (checkAccreditationHistoryId == null) {
            checkAccreditationHistoryId = new LongFilter();
        }
        return checkAccreditationHistoryId;
    }

    public void setCheckAccreditationHistoryId(LongFilter checkAccreditationHistoryId) {
        this.checkAccreditationHistoryId = checkAccreditationHistoryId;
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
        final CheckAccreditationReportCriteria that = (CheckAccreditationReportCriteria) o;
        return (
            Objects.equals(checkAccreditationReportId, that.checkAccreditationReportId) &&
            Objects.equals(checkAccreditationReportDescription, that.checkAccreditationReportDescription) &&
            Objects.equals(checkAccreditationReportParams, that.checkAccreditationReportParams) &&
            Objects.equals(checkAccreditationReportAttributs, that.checkAccreditationReportAttributs) &&
            Objects.equals(checkAccreditationReportStat, that.checkAccreditationReportStat) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(checkAccreditationHistoryId, that.checkAccreditationHistoryId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            checkAccreditationReportId,
            checkAccreditationReportDescription,
            checkAccreditationReportParams,
            checkAccreditationReportAttributs,
            checkAccreditationReportStat,
            eventId,
            checkAccreditationHistoryId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckAccreditationReportCriteria{" +
            (checkAccreditationReportId != null ? "checkAccreditationReportId=" + checkAccreditationReportId + ", " : "") +
            (checkAccreditationReportDescription != null ? "checkAccreditationReportDescription=" + checkAccreditationReportDescription + ", " : "") +
            (checkAccreditationReportParams != null ? "checkAccreditationReportParams=" + checkAccreditationReportParams + ", " : "") +
            (checkAccreditationReportAttributs != null ? "checkAccreditationReportAttributs=" + checkAccreditationReportAttributs + ", " : "") +
            (checkAccreditationReportStat != null ? "checkAccreditationReportStat=" + checkAccreditationReportStat + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (checkAccreditationHistoryId != null ? "checkAccreditationHistoryId=" + checkAccreditationHistoryId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.CheckAccreditationHistory} entity. This class is used
 * in {@link ma.sig.events.web.rest.CheckAccreditationHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /check-accreditation-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckAccreditationHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter checkAccreditationHistoryId;

    private StringFilter checkAccreditationHistoryReadedCode;

    private LongFilter checkAccreditationHistoryUserId;

    private BooleanFilter checkAccreditationHistoryResult;

    private StringFilter checkAccreditationHistoryError;

    private ZonedDateTimeFilter checkAccreditationHistoryDate;

    private StringFilter checkAccreditationHistoryLocalisation;

    private StringFilter checkAccreditationHistoryIpAdresse;

    private StringFilter checkAccreditationParams;

    private StringFilter checkAccreditationAttributs;

    private BooleanFilter checkAccreditationHistoryStat;

    private LongFilter checkAccreditationReportId;

    private LongFilter eventId;

    private LongFilter accreditationId;

    private Boolean distinct;

    public CheckAccreditationHistoryCriteria() {}

    public CheckAccreditationHistoryCriteria(CheckAccreditationHistoryCriteria other) {
        this.checkAccreditationHistoryId = other.checkAccreditationHistoryId == null ? null : other.checkAccreditationHistoryId.copy();
        this.checkAccreditationHistoryReadedCode =
            other.checkAccreditationHistoryReadedCode == null ? null : other.checkAccreditationHistoryReadedCode.copy();
        this.checkAccreditationHistoryUserId =
            other.checkAccreditationHistoryUserId == null ? null : other.checkAccreditationHistoryUserId.copy();
        this.checkAccreditationHistoryResult =
            other.checkAccreditationHistoryResult == null ? null : other.checkAccreditationHistoryResult.copy();
        this.checkAccreditationHistoryError =
            other.checkAccreditationHistoryError == null ? null : other.checkAccreditationHistoryError.copy();
        this.checkAccreditationHistoryDate =
            other.checkAccreditationHistoryDate == null ? null : other.checkAccreditationHistoryDate.copy();
        this.checkAccreditationHistoryLocalisation =
            other.checkAccreditationHistoryLocalisation == null ? null : other.checkAccreditationHistoryLocalisation.copy();
        this.checkAccreditationHistoryIpAdresse =
            other.checkAccreditationHistoryIpAdresse == null ? null : other.checkAccreditationHistoryIpAdresse.copy();
        this.checkAccreditationParams = other.checkAccreditationParams == null ? null : other.checkAccreditationParams.copy();
        this.checkAccreditationAttributs = other.checkAccreditationAttributs == null ? null : other.checkAccreditationAttributs.copy();
        this.checkAccreditationHistoryStat =
            other.checkAccreditationHistoryStat == null ? null : other.checkAccreditationHistoryStat.copy();
        this.checkAccreditationReportId = other.checkAccreditationReportId == null ? null : other.checkAccreditationReportId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CheckAccreditationHistoryCriteria copy() {
        return new CheckAccreditationHistoryCriteria(this);
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

    public StringFilter getCheckAccreditationHistoryReadedCode() {
        return checkAccreditationHistoryReadedCode;
    }

    public StringFilter checkAccreditationHistoryReadedCode() {
        if (checkAccreditationHistoryReadedCode == null) {
            checkAccreditationHistoryReadedCode = new StringFilter();
        }
        return checkAccreditationHistoryReadedCode;
    }

    public void setCheckAccreditationHistoryReadedCode(StringFilter checkAccreditationHistoryReadedCode) {
        this.checkAccreditationHistoryReadedCode = checkAccreditationHistoryReadedCode;
    }

    public LongFilter getCheckAccreditationHistoryUserId() {
        return checkAccreditationHistoryUserId;
    }

    public LongFilter checkAccreditationHistoryUserId() {
        if (checkAccreditationHistoryUserId == null) {
            checkAccreditationHistoryUserId = new LongFilter();
        }
        return checkAccreditationHistoryUserId;
    }

    public void setCheckAccreditationHistoryUserId(LongFilter checkAccreditationHistoryUserId) {
        this.checkAccreditationHistoryUserId = checkAccreditationHistoryUserId;
    }

    public BooleanFilter getCheckAccreditationHistoryResult() {
        return checkAccreditationHistoryResult;
    }

    public BooleanFilter checkAccreditationHistoryResult() {
        if (checkAccreditationHistoryResult == null) {
            checkAccreditationHistoryResult = new BooleanFilter();
        }
        return checkAccreditationHistoryResult;
    }

    public void setCheckAccreditationHistoryResult(BooleanFilter checkAccreditationHistoryResult) {
        this.checkAccreditationHistoryResult = checkAccreditationHistoryResult;
    }

    public StringFilter getCheckAccreditationHistoryError() {
        return checkAccreditationHistoryError;
    }

    public StringFilter checkAccreditationHistoryError() {
        if (checkAccreditationHistoryError == null) {
            checkAccreditationHistoryError = new StringFilter();
        }
        return checkAccreditationHistoryError;
    }

    public void setCheckAccreditationHistoryError(StringFilter checkAccreditationHistoryError) {
        this.checkAccreditationHistoryError = checkAccreditationHistoryError;
    }

    public ZonedDateTimeFilter getCheckAccreditationHistoryDate() {
        return checkAccreditationHistoryDate;
    }

    public ZonedDateTimeFilter checkAccreditationHistoryDate() {
        if (checkAccreditationHistoryDate == null) {
            checkAccreditationHistoryDate = new ZonedDateTimeFilter();
        }
        return checkAccreditationHistoryDate;
    }

    public void setCheckAccreditationHistoryDate(ZonedDateTimeFilter checkAccreditationHistoryDate) {
        this.checkAccreditationHistoryDate = checkAccreditationHistoryDate;
    }

    public StringFilter getCheckAccreditationHistoryLocalisation() {
        return checkAccreditationHistoryLocalisation;
    }

    public StringFilter checkAccreditationHistoryLocalisation() {
        if (checkAccreditationHistoryLocalisation == null) {
            checkAccreditationHistoryLocalisation = new StringFilter();
        }
        return checkAccreditationHistoryLocalisation;
    }

    public void setCheckAccreditationHistoryLocalisation(StringFilter checkAccreditationHistoryLocalisation) {
        this.checkAccreditationHistoryLocalisation = checkAccreditationHistoryLocalisation;
    }

    public StringFilter getCheckAccreditationHistoryIpAdresse() {
        return checkAccreditationHistoryIpAdresse;
    }

    public StringFilter checkAccreditationHistoryIpAdresse() {
        if (checkAccreditationHistoryIpAdresse == null) {
            checkAccreditationHistoryIpAdresse = new StringFilter();
        }
        return checkAccreditationHistoryIpAdresse;
    }

    public void setCheckAccreditationHistoryIpAdresse(StringFilter checkAccreditationHistoryIpAdresse) {
        this.checkAccreditationHistoryIpAdresse = checkAccreditationHistoryIpAdresse;
    }

    public StringFilter getCheckAccreditationParams() {
        return checkAccreditationParams;
    }

    public StringFilter checkAccreditationParams() {
        if (checkAccreditationParams == null) {
            checkAccreditationParams = new StringFilter();
        }
        return checkAccreditationParams;
    }

    public void setCheckAccreditationParams(StringFilter checkAccreditationParams) {
        this.checkAccreditationParams = checkAccreditationParams;
    }

    public StringFilter getCheckAccreditationAttributs() {
        return checkAccreditationAttributs;
    }

    public StringFilter checkAccreditationAttributs() {
        if (checkAccreditationAttributs == null) {
            checkAccreditationAttributs = new StringFilter();
        }
        return checkAccreditationAttributs;
    }

    public void setCheckAccreditationAttributs(StringFilter checkAccreditationAttributs) {
        this.checkAccreditationAttributs = checkAccreditationAttributs;
    }

    public BooleanFilter getCheckAccreditationHistoryStat() {
        return checkAccreditationHistoryStat;
    }

    public BooleanFilter checkAccreditationHistoryStat() {
        if (checkAccreditationHistoryStat == null) {
            checkAccreditationHistoryStat = new BooleanFilter();
        }
        return checkAccreditationHistoryStat;
    }

    public void setCheckAccreditationHistoryStat(BooleanFilter checkAccreditationHistoryStat) {
        this.checkAccreditationHistoryStat = checkAccreditationHistoryStat;
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
        final CheckAccreditationHistoryCriteria that = (CheckAccreditationHistoryCriteria) o;
        return (
            Objects.equals(checkAccreditationHistoryId, that.checkAccreditationHistoryId) &&
            Objects.equals(checkAccreditationHistoryReadedCode, that.checkAccreditationHistoryReadedCode) &&
            Objects.equals(checkAccreditationHistoryUserId, that.checkAccreditationHistoryUserId) &&
            Objects.equals(checkAccreditationHistoryResult, that.checkAccreditationHistoryResult) &&
            Objects.equals(checkAccreditationHistoryError, that.checkAccreditationHistoryError) &&
            Objects.equals(checkAccreditationHistoryDate, that.checkAccreditationHistoryDate) &&
            Objects.equals(checkAccreditationHistoryLocalisation, that.checkAccreditationHistoryLocalisation) &&
            Objects.equals(checkAccreditationHistoryIpAdresse, that.checkAccreditationHistoryIpAdresse) &&
            Objects.equals(checkAccreditationParams, that.checkAccreditationParams) &&
            Objects.equals(checkAccreditationAttributs, that.checkAccreditationAttributs) &&
            Objects.equals(checkAccreditationHistoryStat, that.checkAccreditationHistoryStat) &&
            Objects.equals(checkAccreditationReportId, that.checkAccreditationReportId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            checkAccreditationHistoryId,
            checkAccreditationHistoryReadedCode,
            checkAccreditationHistoryUserId,
            checkAccreditationHistoryResult,
            checkAccreditationHistoryError,
            checkAccreditationHistoryDate,
            checkAccreditationHistoryLocalisation,
            checkAccreditationHistoryIpAdresse,
            checkAccreditationParams,
            checkAccreditationAttributs,
            checkAccreditationHistoryStat,
            checkAccreditationReportId,
            eventId,
            accreditationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckAccreditationHistoryCriteria{" +
            (checkAccreditationHistoryId != null ? "checkAccreditationHistoryId=" + checkAccreditationHistoryId + ", " : "") +
            (checkAccreditationHistoryReadedCode != null ? "checkAccreditationHistoryReadedCode=" + checkAccreditationHistoryReadedCode + ", " : "") +
            (checkAccreditationHistoryUserId != null ? "checkAccreditationHistoryUserId=" + checkAccreditationHistoryUserId + ", " : "") +
            (checkAccreditationHistoryResult != null ? "checkAccreditationHistoryResult=" + checkAccreditationHistoryResult + ", " : "") +
            (checkAccreditationHistoryError != null ? "checkAccreditationHistoryError=" + checkAccreditationHistoryError + ", " : "") +
            (checkAccreditationHistoryDate != null ? "checkAccreditationHistoryDate=" + checkAccreditationHistoryDate + ", " : "") +
            (checkAccreditationHistoryLocalisation != null ? "checkAccreditationHistoryLocalisation=" + checkAccreditationHistoryLocalisation + ", " : "") +
            (checkAccreditationHistoryIpAdresse != null ? "checkAccreditationHistoryIpAdresse=" + checkAccreditationHistoryIpAdresse + ", " : "") +
            (checkAccreditationParams != null ? "checkAccreditationParams=" + checkAccreditationParams + ", " : "") +
            (checkAccreditationAttributs != null ? "checkAccreditationAttributs=" + checkAccreditationAttributs + ", " : "") +
            (checkAccreditationHistoryStat != null ? "checkAccreditationHistoryStat=" + checkAccreditationHistoryStat + ", " : "") +
            (checkAccreditationReportId != null ? "checkAccreditationReportId=" + checkAccreditationReportId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

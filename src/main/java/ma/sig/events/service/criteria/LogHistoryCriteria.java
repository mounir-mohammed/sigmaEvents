package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.LogHistory} entity. This class is used
 * in {@link ma.sig.events.web.rest.LogHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /log-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LogHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter logHistory;

    private StringFilter logHistoryDescription;

    private StringFilter logHistoryParams;

    private StringFilter logHistoryAttributs;

    private BooleanFilter logHistoryStat;

    private Boolean distinct;

    public LogHistoryCriteria() {}

    public LogHistoryCriteria(LogHistoryCriteria other) {
        this.logHistory = other.logHistory == null ? null : other.logHistory.copy();
        this.logHistoryDescription = other.logHistoryDescription == null ? null : other.logHistoryDescription.copy();
        this.logHistoryParams = other.logHistoryParams == null ? null : other.logHistoryParams.copy();
        this.logHistoryAttributs = other.logHistoryAttributs == null ? null : other.logHistoryAttributs.copy();
        this.logHistoryStat = other.logHistoryStat == null ? null : other.logHistoryStat.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LogHistoryCriteria copy() {
        return new LogHistoryCriteria(this);
    }

    public LongFilter getLogHistory() {
        return logHistory;
    }

    public LongFilter logHistory() {
        if (logHistory == null) {
            logHistory = new LongFilter();
        }
        return logHistory;
    }

    public void setLogHistory(LongFilter logHistory) {
        this.logHistory = logHistory;
    }

    public StringFilter getLogHistoryDescription() {
        return logHistoryDescription;
    }

    public StringFilter logHistoryDescription() {
        if (logHistoryDescription == null) {
            logHistoryDescription = new StringFilter();
        }
        return logHistoryDescription;
    }

    public void setLogHistoryDescription(StringFilter logHistoryDescription) {
        this.logHistoryDescription = logHistoryDescription;
    }

    public StringFilter getLogHistoryParams() {
        return logHistoryParams;
    }

    public StringFilter logHistoryParams() {
        if (logHistoryParams == null) {
            logHistoryParams = new StringFilter();
        }
        return logHistoryParams;
    }

    public void setLogHistoryParams(StringFilter logHistoryParams) {
        this.logHistoryParams = logHistoryParams;
    }

    public StringFilter getLogHistoryAttributs() {
        return logHistoryAttributs;
    }

    public StringFilter logHistoryAttributs() {
        if (logHistoryAttributs == null) {
            logHistoryAttributs = new StringFilter();
        }
        return logHistoryAttributs;
    }

    public void setLogHistoryAttributs(StringFilter logHistoryAttributs) {
        this.logHistoryAttributs = logHistoryAttributs;
    }

    public BooleanFilter getLogHistoryStat() {
        return logHistoryStat;
    }

    public BooleanFilter logHistoryStat() {
        if (logHistoryStat == null) {
            logHistoryStat = new BooleanFilter();
        }
        return logHistoryStat;
    }

    public void setLogHistoryStat(BooleanFilter logHistoryStat) {
        this.logHistoryStat = logHistoryStat;
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
        final LogHistoryCriteria that = (LogHistoryCriteria) o;
        return (
            Objects.equals(logHistory, that.logHistory) &&
            Objects.equals(logHistoryDescription, that.logHistoryDescription) &&
            Objects.equals(logHistoryParams, that.logHistoryParams) &&
            Objects.equals(logHistoryAttributs, that.logHistoryAttributs) &&
            Objects.equals(logHistoryStat, that.logHistoryStat) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(logHistory, logHistoryDescription, logHistoryParams, logHistoryAttributs, logHistoryStat, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogHistoryCriteria{" +
            (logHistory != null ? "logHistory=" + logHistory + ", " : "") +
            (logHistoryDescription != null ? "logHistoryDescription=" + logHistoryDescription + ", " : "") +
            (logHistoryParams != null ? "logHistoryParams=" + logHistoryParams + ", " : "") +
            (logHistoryAttributs != null ? "logHistoryAttributs=" + logHistoryAttributs + ", " : "") +
            (logHistoryStat != null ? "logHistoryStat=" + logHistoryStat + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

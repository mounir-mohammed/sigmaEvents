package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ma.sig.events.domain.LogHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LogHistoryDTO implements Serializable {

    private Long logHistory;

    private String logHistoryDescription;

    private String logHistoryParams;

    private String logHistoryAttributs;

    private Boolean logHistoryStat;

    public Long getLogHistory() {
        return logHistory;
    }

    public void setLogHistory(Long logHistory) {
        this.logHistory = logHistory;
    }

    public String getLogHistoryDescription() {
        return logHistoryDescription;
    }

    public void setLogHistoryDescription(String logHistoryDescription) {
        this.logHistoryDescription = logHistoryDescription;
    }

    public String getLogHistoryParams() {
        return logHistoryParams;
    }

    public void setLogHistoryParams(String logHistoryParams) {
        this.logHistoryParams = logHistoryParams;
    }

    public String getLogHistoryAttributs() {
        return logHistoryAttributs;
    }

    public void setLogHistoryAttributs(String logHistoryAttributs) {
        this.logHistoryAttributs = logHistoryAttributs;
    }

    public Boolean getLogHistoryStat() {
        return logHistoryStat;
    }

    public void setLogHistoryStat(Boolean logHistoryStat) {
        this.logHistoryStat = logHistoryStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogHistoryDTO)) {
            return false;
        }

        LogHistoryDTO logHistoryDTO = (LogHistoryDTO) o;
        if (this.logHistory == null) {
            return false;
        }
        return Objects.equals(this.logHistory, logHistoryDTO.logHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.logHistory);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogHistoryDTO{" +
            "logHistory=" + getLogHistory() +
            ", logHistoryDescription='" + getLogHistoryDescription() + "'" +
            ", logHistoryParams='" + getLogHistoryParams() + "'" +
            ", logHistoryAttributs='" + getLogHistoryAttributs() + "'" +
            ", logHistoryStat='" + getLogHistoryStat() + "'" +
            "}";
    }
}

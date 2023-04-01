package ma.sig.events.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LogHistory.
 */
@Entity
@Table(name = "log_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LogHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_history")
    private Long logHistory;

    @Column(name = "log_history_description")
    private String logHistoryDescription;

    @Column(name = "log_history_params")
    private String logHistoryParams;

    @Column(name = "log_history_attributs")
    private String logHistoryAttributs;

    @Column(name = "log_history_stat")
    private Boolean logHistoryStat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getLogHistory() {
        return this.logHistory;
    }

    public LogHistory logHistory(Long logHistory) {
        this.setLogHistory(logHistory);
        return this;
    }

    public void setLogHistory(Long logHistory) {
        this.logHistory = logHistory;
    }

    public String getLogHistoryDescription() {
        return this.logHistoryDescription;
    }

    public LogHistory logHistoryDescription(String logHistoryDescription) {
        this.setLogHistoryDescription(logHistoryDescription);
        return this;
    }

    public void setLogHistoryDescription(String logHistoryDescription) {
        this.logHistoryDescription = logHistoryDescription;
    }

    public String getLogHistoryParams() {
        return this.logHistoryParams;
    }

    public LogHistory logHistoryParams(String logHistoryParams) {
        this.setLogHistoryParams(logHistoryParams);
        return this;
    }

    public void setLogHistoryParams(String logHistoryParams) {
        this.logHistoryParams = logHistoryParams;
    }

    public String getLogHistoryAttributs() {
        return this.logHistoryAttributs;
    }

    public LogHistory logHistoryAttributs(String logHistoryAttributs) {
        this.setLogHistoryAttributs(logHistoryAttributs);
        return this;
    }

    public void setLogHistoryAttributs(String logHistoryAttributs) {
        this.logHistoryAttributs = logHistoryAttributs;
    }

    public Boolean getLogHistoryStat() {
        return this.logHistoryStat;
    }

    public LogHistory logHistoryStat(Boolean logHistoryStat) {
        this.setLogHistoryStat(logHistoryStat);
        return this;
    }

    public void setLogHistoryStat(Boolean logHistoryStat) {
        this.logHistoryStat = logHistoryStat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogHistory)) {
            return false;
        }
        return logHistory != null && logHistory.equals(((LogHistory) o).logHistory);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogHistory{" +
            "logHistory=" + getLogHistory() +
            ", logHistoryDescription='" + getLogHistoryDescription() + "'" +
            ", logHistoryParams='" + getLogHistoryParams() + "'" +
            ", logHistoryAttributs='" + getLogHistoryAttributs() + "'" +
            ", logHistoryStat='" + getLogHistoryStat() + "'" +
            "}";
    }
}

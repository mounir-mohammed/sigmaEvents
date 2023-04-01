package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CheckAccreditationHistory.
 */
@Entity
@Table(name = "check_accreditation_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckAccreditationHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_accreditation_history_id")
    private Long checkAccreditationHistoryId;

    @Column(name = "check_accreditation_history_readed_code")
    private String checkAccreditationHistoryReadedCode;

    @Column(name = "check_accreditation_history_user_id")
    private Long checkAccreditationHistoryUserId;

    @Column(name = "check_accreditation_history_result")
    private Boolean checkAccreditationHistoryResult;

    @Column(name = "check_accreditation_history_error")
    private String checkAccreditationHistoryError;

    @Column(name = "check_accreditation_history_date")
    private ZonedDateTime checkAccreditationHistoryDate;

    @Column(name = "check_accreditation_history_localisation")
    private String checkAccreditationHistoryLocalisation;

    @Column(name = "check_accreditation_history_ip_adresse")
    private String checkAccreditationHistoryIpAdresse;

    @Column(name = "check_accreditation_params")
    private String checkAccreditationParams;

    @Column(name = "check_accreditation_attributs")
    private String checkAccreditationAttributs;

    @Column(name = "check_accreditation_history_stat")
    private Boolean checkAccreditationHistoryStat;

    @OneToMany(mappedBy = "checkAccreditationHistory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "event", "checkAccreditationHistory" }, allowSetters = true)
    private Set<CheckAccreditationReport> checkAccreditationReports = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "eventForms",
            "eventFields",
            "eventControls",
            "areas",
            "fonctions",
            "categories",
            "printingModels",
            "codes",
            "infoSupps",
            "attachements",
            "organizs",
            "photoArchives",
            "sites",
            "accreditations",
            "notes",
            "operationHistories",
            "printingCentres",
            "settings",
            "printingServers",
            "checkAccreditationHistories",
            "checkAccreditationReports",
            "accreditationTypes",
            "dayPassInfos",
            "language",
        },
        allowSetters = true
    )
    private Event event;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "photoArchives",
            "infoSupps",
            "notes",
            "checkAccreditationHistories",
            "sites",
            "event",
            "civility",
            "sexe",
            "nationality",
            "country",
            "city",
            "category",
            "fonction",
            "organiz",
            "accreditationType",
            "status",
            "attachement",
            "code",
            "dayPassInfo",
        },
        allowSetters = true
    )
    private Accreditation accreditation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getCheckAccreditationHistoryId() {
        return this.checkAccreditationHistoryId;
    }

    public CheckAccreditationHistory checkAccreditationHistoryId(Long checkAccreditationHistoryId) {
        this.setCheckAccreditationHistoryId(checkAccreditationHistoryId);
        return this;
    }

    public void setCheckAccreditationHistoryId(Long checkAccreditationHistoryId) {
        this.checkAccreditationHistoryId = checkAccreditationHistoryId;
    }

    public String getCheckAccreditationHistoryReadedCode() {
        return this.checkAccreditationHistoryReadedCode;
    }

    public CheckAccreditationHistory checkAccreditationHistoryReadedCode(String checkAccreditationHistoryReadedCode) {
        this.setCheckAccreditationHistoryReadedCode(checkAccreditationHistoryReadedCode);
        return this;
    }

    public void setCheckAccreditationHistoryReadedCode(String checkAccreditationHistoryReadedCode) {
        this.checkAccreditationHistoryReadedCode = checkAccreditationHistoryReadedCode;
    }

    public Long getCheckAccreditationHistoryUserId() {
        return this.checkAccreditationHistoryUserId;
    }

    public CheckAccreditationHistory checkAccreditationHistoryUserId(Long checkAccreditationHistoryUserId) {
        this.setCheckAccreditationHistoryUserId(checkAccreditationHistoryUserId);
        return this;
    }

    public void setCheckAccreditationHistoryUserId(Long checkAccreditationHistoryUserId) {
        this.checkAccreditationHistoryUserId = checkAccreditationHistoryUserId;
    }

    public Boolean getCheckAccreditationHistoryResult() {
        return this.checkAccreditationHistoryResult;
    }

    public CheckAccreditationHistory checkAccreditationHistoryResult(Boolean checkAccreditationHistoryResult) {
        this.setCheckAccreditationHistoryResult(checkAccreditationHistoryResult);
        return this;
    }

    public void setCheckAccreditationHistoryResult(Boolean checkAccreditationHistoryResult) {
        this.checkAccreditationHistoryResult = checkAccreditationHistoryResult;
    }

    public String getCheckAccreditationHistoryError() {
        return this.checkAccreditationHistoryError;
    }

    public CheckAccreditationHistory checkAccreditationHistoryError(String checkAccreditationHistoryError) {
        this.setCheckAccreditationHistoryError(checkAccreditationHistoryError);
        return this;
    }

    public void setCheckAccreditationHistoryError(String checkAccreditationHistoryError) {
        this.checkAccreditationHistoryError = checkAccreditationHistoryError;
    }

    public ZonedDateTime getCheckAccreditationHistoryDate() {
        return this.checkAccreditationHistoryDate;
    }

    public CheckAccreditationHistory checkAccreditationHistoryDate(ZonedDateTime checkAccreditationHistoryDate) {
        this.setCheckAccreditationHistoryDate(checkAccreditationHistoryDate);
        return this;
    }

    public void setCheckAccreditationHistoryDate(ZonedDateTime checkAccreditationHistoryDate) {
        this.checkAccreditationHistoryDate = checkAccreditationHistoryDate;
    }

    public String getCheckAccreditationHistoryLocalisation() {
        return this.checkAccreditationHistoryLocalisation;
    }

    public CheckAccreditationHistory checkAccreditationHistoryLocalisation(String checkAccreditationHistoryLocalisation) {
        this.setCheckAccreditationHistoryLocalisation(checkAccreditationHistoryLocalisation);
        return this;
    }

    public void setCheckAccreditationHistoryLocalisation(String checkAccreditationHistoryLocalisation) {
        this.checkAccreditationHistoryLocalisation = checkAccreditationHistoryLocalisation;
    }

    public String getCheckAccreditationHistoryIpAdresse() {
        return this.checkAccreditationHistoryIpAdresse;
    }

    public CheckAccreditationHistory checkAccreditationHistoryIpAdresse(String checkAccreditationHistoryIpAdresse) {
        this.setCheckAccreditationHistoryIpAdresse(checkAccreditationHistoryIpAdresse);
        return this;
    }

    public void setCheckAccreditationHistoryIpAdresse(String checkAccreditationHistoryIpAdresse) {
        this.checkAccreditationHistoryIpAdresse = checkAccreditationHistoryIpAdresse;
    }

    public String getCheckAccreditationParams() {
        return this.checkAccreditationParams;
    }

    public CheckAccreditationHistory checkAccreditationParams(String checkAccreditationParams) {
        this.setCheckAccreditationParams(checkAccreditationParams);
        return this;
    }

    public void setCheckAccreditationParams(String checkAccreditationParams) {
        this.checkAccreditationParams = checkAccreditationParams;
    }

    public String getCheckAccreditationAttributs() {
        return this.checkAccreditationAttributs;
    }

    public CheckAccreditationHistory checkAccreditationAttributs(String checkAccreditationAttributs) {
        this.setCheckAccreditationAttributs(checkAccreditationAttributs);
        return this;
    }

    public void setCheckAccreditationAttributs(String checkAccreditationAttributs) {
        this.checkAccreditationAttributs = checkAccreditationAttributs;
    }

    public Boolean getCheckAccreditationHistoryStat() {
        return this.checkAccreditationHistoryStat;
    }

    public CheckAccreditationHistory checkAccreditationHistoryStat(Boolean checkAccreditationHistoryStat) {
        this.setCheckAccreditationHistoryStat(checkAccreditationHistoryStat);
        return this;
    }

    public void setCheckAccreditationHistoryStat(Boolean checkAccreditationHistoryStat) {
        this.checkAccreditationHistoryStat = checkAccreditationHistoryStat;
    }

    public Set<CheckAccreditationReport> getCheckAccreditationReports() {
        return this.checkAccreditationReports;
    }

    public void setCheckAccreditationReports(Set<CheckAccreditationReport> checkAccreditationReports) {
        if (this.checkAccreditationReports != null) {
            this.checkAccreditationReports.forEach(i -> i.setCheckAccreditationHistory(null));
        }
        if (checkAccreditationReports != null) {
            checkAccreditationReports.forEach(i -> i.setCheckAccreditationHistory(this));
        }
        this.checkAccreditationReports = checkAccreditationReports;
    }

    public CheckAccreditationHistory checkAccreditationReports(Set<CheckAccreditationReport> checkAccreditationReports) {
        this.setCheckAccreditationReports(checkAccreditationReports);
        return this;
    }

    public CheckAccreditationHistory addCheckAccreditationReport(CheckAccreditationReport checkAccreditationReport) {
        this.checkAccreditationReports.add(checkAccreditationReport);
        checkAccreditationReport.setCheckAccreditationHistory(this);
        return this;
    }

    public CheckAccreditationHistory removeCheckAccreditationReport(CheckAccreditationReport checkAccreditationReport) {
        this.checkAccreditationReports.remove(checkAccreditationReport);
        checkAccreditationReport.setCheckAccreditationHistory(null);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public CheckAccreditationHistory event(Event event) {
        this.setEvent(event);
        return this;
    }

    public Accreditation getAccreditation() {
        return this.accreditation;
    }

    public void setAccreditation(Accreditation accreditation) {
        this.accreditation = accreditation;
    }

    public CheckAccreditationHistory accreditation(Accreditation accreditation) {
        this.setAccreditation(accreditation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckAccreditationHistory)) {
            return false;
        }
        return (
            checkAccreditationHistoryId != null &&
            checkAccreditationHistoryId.equals(((CheckAccreditationHistory) o).checkAccreditationHistoryId)
        );
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckAccreditationHistory{" +
            "checkAccreditationHistoryId=" + getCheckAccreditationHistoryId() +
            ", checkAccreditationHistoryReadedCode='" + getCheckAccreditationHistoryReadedCode() + "'" +
            ", checkAccreditationHistoryUserId=" + getCheckAccreditationHistoryUserId() +
            ", checkAccreditationHistoryResult='" + getCheckAccreditationHistoryResult() + "'" +
            ", checkAccreditationHistoryError='" + getCheckAccreditationHistoryError() + "'" +
            ", checkAccreditationHistoryDate='" + getCheckAccreditationHistoryDate() + "'" +
            ", checkAccreditationHistoryLocalisation='" + getCheckAccreditationHistoryLocalisation() + "'" +
            ", checkAccreditationHistoryIpAdresse='" + getCheckAccreditationHistoryIpAdresse() + "'" +
            ", checkAccreditationParams='" + getCheckAccreditationParams() + "'" +
            ", checkAccreditationAttributs='" + getCheckAccreditationAttributs() + "'" +
            ", checkAccreditationHistoryStat='" + getCheckAccreditationHistoryStat() + "'" +
            "}";
    }
}

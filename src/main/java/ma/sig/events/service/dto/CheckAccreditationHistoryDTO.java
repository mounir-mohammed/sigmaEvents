package ma.sig.events.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link ma.sig.events.domain.CheckAccreditationHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckAccreditationHistoryDTO implements Serializable {

    private Long checkAccreditationHistoryId;

    private String checkAccreditationHistoryReadedCode;

    private Long checkAccreditationHistoryUserId;

    private Boolean checkAccreditationHistoryResult;

    private String checkAccreditationHistoryError;

    private ZonedDateTime checkAccreditationHistoryDate;

    private String checkAccreditationHistoryLocalisation;

    private String checkAccreditationHistoryIpAdresse;

    private String checkAccreditationParams;

    private String checkAccreditationAttributs;

    private Boolean checkAccreditationHistoryStat;

    private EventDTO event;

    private AccreditationDTO accreditation;

    public Long getCheckAccreditationHistoryId() {
        return checkAccreditationHistoryId;
    }

    public void setCheckAccreditationHistoryId(Long checkAccreditationHistoryId) {
        this.checkAccreditationHistoryId = checkAccreditationHistoryId;
    }

    public String getCheckAccreditationHistoryReadedCode() {
        return checkAccreditationHistoryReadedCode;
    }

    public void setCheckAccreditationHistoryReadedCode(String checkAccreditationHistoryReadedCode) {
        this.checkAccreditationHistoryReadedCode = checkAccreditationHistoryReadedCode;
    }

    public Long getCheckAccreditationHistoryUserId() {
        return checkAccreditationHistoryUserId;
    }

    public void setCheckAccreditationHistoryUserId(Long checkAccreditationHistoryUserId) {
        this.checkAccreditationHistoryUserId = checkAccreditationHistoryUserId;
    }

    public Boolean getCheckAccreditationHistoryResult() {
        return checkAccreditationHistoryResult;
    }

    public void setCheckAccreditationHistoryResult(Boolean checkAccreditationHistoryResult) {
        this.checkAccreditationHistoryResult = checkAccreditationHistoryResult;
    }

    public String getCheckAccreditationHistoryError() {
        return checkAccreditationHistoryError;
    }

    public void setCheckAccreditationHistoryError(String checkAccreditationHistoryError) {
        this.checkAccreditationHistoryError = checkAccreditationHistoryError;
    }

    public ZonedDateTime getCheckAccreditationHistoryDate() {
        return checkAccreditationHistoryDate;
    }

    public void setCheckAccreditationHistoryDate(ZonedDateTime checkAccreditationHistoryDate) {
        this.checkAccreditationHistoryDate = checkAccreditationHistoryDate;
    }

    public String getCheckAccreditationHistoryLocalisation() {
        return checkAccreditationHistoryLocalisation;
    }

    public void setCheckAccreditationHistoryLocalisation(String checkAccreditationHistoryLocalisation) {
        this.checkAccreditationHistoryLocalisation = checkAccreditationHistoryLocalisation;
    }

    public String getCheckAccreditationHistoryIpAdresse() {
        return checkAccreditationHistoryIpAdresse;
    }

    public void setCheckAccreditationHistoryIpAdresse(String checkAccreditationHistoryIpAdresse) {
        this.checkAccreditationHistoryIpAdresse = checkAccreditationHistoryIpAdresse;
    }

    public String getCheckAccreditationParams() {
        return checkAccreditationParams;
    }

    public void setCheckAccreditationParams(String checkAccreditationParams) {
        this.checkAccreditationParams = checkAccreditationParams;
    }

    public String getCheckAccreditationAttributs() {
        return checkAccreditationAttributs;
    }

    public void setCheckAccreditationAttributs(String checkAccreditationAttributs) {
        this.checkAccreditationAttributs = checkAccreditationAttributs;
    }

    public Boolean getCheckAccreditationHistoryStat() {
        return checkAccreditationHistoryStat;
    }

    public void setCheckAccreditationHistoryStat(Boolean checkAccreditationHistoryStat) {
        this.checkAccreditationHistoryStat = checkAccreditationHistoryStat;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public AccreditationDTO getAccreditation() {
        return accreditation;
    }

    public void setAccreditation(AccreditationDTO accreditation) {
        this.accreditation = accreditation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckAccreditationHistoryDTO)) {
            return false;
        }

        CheckAccreditationHistoryDTO checkAccreditationHistoryDTO = (CheckAccreditationHistoryDTO) o;
        if (this.checkAccreditationHistoryId == null) {
            return false;
        }
        return Objects.equals(this.checkAccreditationHistoryId, checkAccreditationHistoryDTO.checkAccreditationHistoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.checkAccreditationHistoryId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckAccreditationHistoryDTO{" +
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
            ", event=" + getEvent() +
            ", accreditation=" + getAccreditation() +
            "}";
    }
}

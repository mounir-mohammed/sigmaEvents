package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Status} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StatusDTO implements Serializable {

    private Long statusId;

    @NotNull
    @Size(max = 50)
    private String statusName;

    @Size(max = 10)
    private String statusAbreviation;

    @Size(max = 100)
    private String statusColor;

    @Size(max = 200)
    private String statusDescription;

    private Boolean statusUserCanPrint;

    private Boolean statusUserCanUpdate;

    private Boolean statusUserCanValidate;

    private String statusParams;

    private String statusAttributs;

    private Boolean statusStat;

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusAbreviation() {
        return statusAbreviation;
    }

    public void setStatusAbreviation(String statusAbreviation) {
        this.statusAbreviation = statusAbreviation;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Boolean getStatusUserCanPrint() {
        return statusUserCanPrint;
    }

    public void setStatusUserCanPrint(Boolean statusUserCanPrint) {
        this.statusUserCanPrint = statusUserCanPrint;
    }

    public Boolean getStatusUserCanUpdate() {
        return statusUserCanUpdate;
    }

    public void setStatusUserCanUpdate(Boolean statusUserCanUpdate) {
        this.statusUserCanUpdate = statusUserCanUpdate;
    }

    public Boolean getStatusUserCanValidate() {
        return statusUserCanValidate;
    }

    public void setStatusUserCanValidate(Boolean statusUserCanValidate) {
        this.statusUserCanValidate = statusUserCanValidate;
    }

    public String getStatusParams() {
        return statusParams;
    }

    public void setStatusParams(String statusParams) {
        this.statusParams = statusParams;
    }

    public String getStatusAttributs() {
        return statusAttributs;
    }

    public void setStatusAttributs(String statusAttributs) {
        this.statusAttributs = statusAttributs;
    }

    public Boolean getStatusStat() {
        return statusStat;
    }

    public void setStatusStat(Boolean statusStat) {
        this.statusStat = statusStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusDTO)) {
            return false;
        }

        StatusDTO statusDTO = (StatusDTO) o;
        if (this.statusId == null) {
            return false;
        }
        return Objects.equals(this.statusId, statusDTO.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.statusId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusDTO{" +
            "statusId=" + getStatusId() +
            ", statusName='" + getStatusName() + "'" +
            ", statusAbreviation='" + getStatusAbreviation() + "'" +
            ", statusColor='" + getStatusColor() + "'" +
            ", statusDescription='" + getStatusDescription() + "'" +
            ", statusUserCanPrint='" + getStatusUserCanPrint() + "'" +
            ", statusUserCanUpdate='" + getStatusUserCanUpdate() + "'" +
            ", statusUserCanValidate='" + getStatusUserCanValidate() + "'" +
            ", statusParams='" + getStatusParams() + "'" +
            ", statusAttributs='" + getStatusAttributs() + "'" +
            ", statusStat='" + getStatusStat() + "'" +
            "}";
    }
}

package ma.sig.events.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Cloning} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CloningDTO implements Serializable {

    private Long cloningId;

    @Size(max = 200)
    private String cloningDescription;

    private Long cloningOldEventId;

    private Long cloningNewEventId;

    private Long cloningUserId;

    private ZonedDateTime cloningDate;

    private String clonedEntitys;

    private String clonedParams;

    private String clonedAttributs;

    private Boolean clonedStat;

    public Long getCloningId() {
        return cloningId;
    }

    public void setCloningId(Long cloningId) {
        this.cloningId = cloningId;
    }

    public String getCloningDescription() {
        return cloningDescription;
    }

    public void setCloningDescription(String cloningDescription) {
        this.cloningDescription = cloningDescription;
    }

    public Long getCloningOldEventId() {
        return cloningOldEventId;
    }

    public void setCloningOldEventId(Long cloningOldEventId) {
        this.cloningOldEventId = cloningOldEventId;
    }

    public Long getCloningNewEventId() {
        return cloningNewEventId;
    }

    public void setCloningNewEventId(Long cloningNewEventId) {
        this.cloningNewEventId = cloningNewEventId;
    }

    public Long getCloningUserId() {
        return cloningUserId;
    }

    public void setCloningUserId(Long cloningUserId) {
        this.cloningUserId = cloningUserId;
    }

    public ZonedDateTime getCloningDate() {
        return cloningDate;
    }

    public void setCloningDate(ZonedDateTime cloningDate) {
        this.cloningDate = cloningDate;
    }

    public String getClonedEntitys() {
        return clonedEntitys;
    }

    public void setClonedEntitys(String clonedEntitys) {
        this.clonedEntitys = clonedEntitys;
    }

    public String getClonedParams() {
        return clonedParams;
    }

    public void setClonedParams(String clonedParams) {
        this.clonedParams = clonedParams;
    }

    public String getClonedAttributs() {
        return clonedAttributs;
    }

    public void setClonedAttributs(String clonedAttributs) {
        this.clonedAttributs = clonedAttributs;
    }

    public Boolean getClonedStat() {
        return clonedStat;
    }

    public void setClonedStat(Boolean clonedStat) {
        this.clonedStat = clonedStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CloningDTO)) {
            return false;
        }

        CloningDTO cloningDTO = (CloningDTO) o;
        if (this.cloningId == null) {
            return false;
        }
        return Objects.equals(this.cloningId, cloningDTO.cloningId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cloningId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CloningDTO{" +
            "cloningId=" + getCloningId() +
            ", cloningDescription='" + getCloningDescription() + "'" +
            ", cloningOldEventId=" + getCloningOldEventId() +
            ", cloningNewEventId=" + getCloningNewEventId() +
            ", cloningUserId=" + getCloningUserId() +
            ", cloningDate='" + getCloningDate() + "'" +
            ", clonedEntitys='" + getClonedEntitys() + "'" +
            ", clonedParams='" + getClonedParams() + "'" +
            ", clonedAttributs='" + getClonedAttributs() + "'" +
            ", clonedStat='" + getClonedStat() + "'" +
            "}";
    }
}

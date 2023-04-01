package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.InfoSupp} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InfoSuppDTO implements Serializable {

    private Long infoSuppId;

    @NotNull
    @Size(max = 50)
    private String infoSuppName;

    @Size(max = 200)
    private String infoSuppDescription;

    private String infoSuppParams;

    private String infoSuppAttributs;

    private Boolean infoSuppStat;

    private InfoSuppTypeDTO infoSuppType;

    private AccreditationDTO accreditation;

    private EventDTO event;

    public Long getInfoSuppId() {
        return infoSuppId;
    }

    public void setInfoSuppId(Long infoSuppId) {
        this.infoSuppId = infoSuppId;
    }

    public String getInfoSuppName() {
        return infoSuppName;
    }

    public void setInfoSuppName(String infoSuppName) {
        this.infoSuppName = infoSuppName;
    }

    public String getInfoSuppDescription() {
        return infoSuppDescription;
    }

    public void setInfoSuppDescription(String infoSuppDescription) {
        this.infoSuppDescription = infoSuppDescription;
    }

    public String getInfoSuppParams() {
        return infoSuppParams;
    }

    public void setInfoSuppParams(String infoSuppParams) {
        this.infoSuppParams = infoSuppParams;
    }

    public String getInfoSuppAttributs() {
        return infoSuppAttributs;
    }

    public void setInfoSuppAttributs(String infoSuppAttributs) {
        this.infoSuppAttributs = infoSuppAttributs;
    }

    public Boolean getInfoSuppStat() {
        return infoSuppStat;
    }

    public void setInfoSuppStat(Boolean infoSuppStat) {
        this.infoSuppStat = infoSuppStat;
    }

    public InfoSuppTypeDTO getInfoSuppType() {
        return infoSuppType;
    }

    public void setInfoSuppType(InfoSuppTypeDTO infoSuppType) {
        this.infoSuppType = infoSuppType;
    }

    public AccreditationDTO getAccreditation() {
        return accreditation;
    }

    public void setAccreditation(AccreditationDTO accreditation) {
        this.accreditation = accreditation;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InfoSuppDTO)) {
            return false;
        }

        InfoSuppDTO infoSuppDTO = (InfoSuppDTO) o;
        if (this.infoSuppId == null) {
            return false;
        }
        return Objects.equals(this.infoSuppId, infoSuppDTO.infoSuppId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.infoSuppId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfoSuppDTO{" +
            "infoSuppId=" + getInfoSuppId() +
            ", infoSuppName='" + getInfoSuppName() + "'" +
            ", infoSuppDescription='" + getInfoSuppDescription() + "'" +
            ", infoSuppParams='" + getInfoSuppParams() + "'" +
            ", infoSuppAttributs='" + getInfoSuppAttributs() + "'" +
            ", infoSuppStat='" + getInfoSuppStat() + "'" +
            ", infoSuppType=" + getInfoSuppType() +
            ", accreditation=" + getAccreditation() +
            ", event=" + getEvent() +
            "}";
    }
}

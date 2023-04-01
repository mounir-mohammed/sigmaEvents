package ma.sig.events.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.DayPassInfo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DayPassInfoDTO implements Serializable {

    private Long dayPassInfoId;

    private String dayPassInfoName;

    @Size(max = 200)
    private String dayPassDescription;

    @Lob
    private byte[] dayPassLogo;

    private String dayPassLogoContentType;
    private ZonedDateTime dayPassInfoCreationDate;

    private ZonedDateTime dayPassInfoUpdateDate;

    private String dayPassInfoCreatedByuser;

    private ZonedDateTime dayPassInfoDateStart;

    private ZonedDateTime dayPassInfoDateEnd;

    private Long dayPassInfoNumber;

    private String dayPassParams;

    private String dayPassAttributs;

    private Boolean dayPassInfoStat;

    private EventDTO event;

    public Long getDayPassInfoId() {
        return dayPassInfoId;
    }

    public void setDayPassInfoId(Long dayPassInfoId) {
        this.dayPassInfoId = dayPassInfoId;
    }

    public String getDayPassInfoName() {
        return dayPassInfoName;
    }

    public void setDayPassInfoName(String dayPassInfoName) {
        this.dayPassInfoName = dayPassInfoName;
    }

    public String getDayPassDescription() {
        return dayPassDescription;
    }

    public void setDayPassDescription(String dayPassDescription) {
        this.dayPassDescription = dayPassDescription;
    }

    public byte[] getDayPassLogo() {
        return dayPassLogo;
    }

    public void setDayPassLogo(byte[] dayPassLogo) {
        this.dayPassLogo = dayPassLogo;
    }

    public String getDayPassLogoContentType() {
        return dayPassLogoContentType;
    }

    public void setDayPassLogoContentType(String dayPassLogoContentType) {
        this.dayPassLogoContentType = dayPassLogoContentType;
    }

    public ZonedDateTime getDayPassInfoCreationDate() {
        return dayPassInfoCreationDate;
    }

    public void setDayPassInfoCreationDate(ZonedDateTime dayPassInfoCreationDate) {
        this.dayPassInfoCreationDate = dayPassInfoCreationDate;
    }

    public ZonedDateTime getDayPassInfoUpdateDate() {
        return dayPassInfoUpdateDate;
    }

    public void setDayPassInfoUpdateDate(ZonedDateTime dayPassInfoUpdateDate) {
        this.dayPassInfoUpdateDate = dayPassInfoUpdateDate;
    }

    public String getDayPassInfoCreatedByuser() {
        return dayPassInfoCreatedByuser;
    }

    public void setDayPassInfoCreatedByuser(String dayPassInfoCreatedByuser) {
        this.dayPassInfoCreatedByuser = dayPassInfoCreatedByuser;
    }

    public ZonedDateTime getDayPassInfoDateStart() {
        return dayPassInfoDateStart;
    }

    public void setDayPassInfoDateStart(ZonedDateTime dayPassInfoDateStart) {
        this.dayPassInfoDateStart = dayPassInfoDateStart;
    }

    public ZonedDateTime getDayPassInfoDateEnd() {
        return dayPassInfoDateEnd;
    }

    public void setDayPassInfoDateEnd(ZonedDateTime dayPassInfoDateEnd) {
        this.dayPassInfoDateEnd = dayPassInfoDateEnd;
    }

    public Long getDayPassInfoNumber() {
        return dayPassInfoNumber;
    }

    public void setDayPassInfoNumber(Long dayPassInfoNumber) {
        this.dayPassInfoNumber = dayPassInfoNumber;
    }

    public String getDayPassParams() {
        return dayPassParams;
    }

    public void setDayPassParams(String dayPassParams) {
        this.dayPassParams = dayPassParams;
    }

    public String getDayPassAttributs() {
        return dayPassAttributs;
    }

    public void setDayPassAttributs(String dayPassAttributs) {
        this.dayPassAttributs = dayPassAttributs;
    }

    public Boolean getDayPassInfoStat() {
        return dayPassInfoStat;
    }

    public void setDayPassInfoStat(Boolean dayPassInfoStat) {
        this.dayPassInfoStat = dayPassInfoStat;
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
        if (!(o instanceof DayPassInfoDTO)) {
            return false;
        }

        DayPassInfoDTO dayPassInfoDTO = (DayPassInfoDTO) o;
        if (this.dayPassInfoId == null) {
            return false;
        }
        return Objects.equals(this.dayPassInfoId, dayPassInfoDTO.dayPassInfoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.dayPassInfoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DayPassInfoDTO{" +
            "dayPassInfoId=" + getDayPassInfoId() +
            ", dayPassInfoName='" + getDayPassInfoName() + "'" +
            ", dayPassDescription='" + getDayPassDescription() + "'" +
            ", dayPassLogo='" + getDayPassLogo() + "'" +
            ", dayPassInfoCreationDate='" + getDayPassInfoCreationDate() + "'" +
            ", dayPassInfoUpdateDate='" + getDayPassInfoUpdateDate() + "'" +
            ", dayPassInfoCreatedByuser='" + getDayPassInfoCreatedByuser() + "'" +
            ", dayPassInfoDateStart='" + getDayPassInfoDateStart() + "'" +
            ", dayPassInfoDateEnd='" + getDayPassInfoDateEnd() + "'" +
            ", dayPassInfoNumber=" + getDayPassInfoNumber() +
            ", dayPassParams='" + getDayPassParams() + "'" +
            ", dayPassAttributs='" + getDayPassAttributs() + "'" +
            ", dayPassInfoStat='" + getDayPassInfoStat() + "'" +
            ", event=" + getEvent() +
            "}";
    }
}

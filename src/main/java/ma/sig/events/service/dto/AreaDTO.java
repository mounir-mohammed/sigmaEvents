package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Area} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AreaDTO implements Serializable {

    private Long areaId;

    @NotNull
    @Size(max = 50)
    private String areaName;

    @Size(max = 10)
    private String areaAbreviation;

    @Size(max = 100)
    private String areaColor;

    @Size(max = 200)
    private String areaDescription;

    @Lob
    private byte[] areaLogo;

    private String areaLogoContentType;
    private String areaParams;

    private String areaAttributs;

    private Boolean areaStat;

    private EventDTO event;

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaAbreviation() {
        return areaAbreviation;
    }

    public void setAreaAbreviation(String areaAbreviation) {
        this.areaAbreviation = areaAbreviation;
    }

    public String getAreaColor() {
        return areaColor;
    }

    public void setAreaColor(String areaColor) {
        this.areaColor = areaColor;
    }

    public String getAreaDescription() {
        return areaDescription;
    }

    public void setAreaDescription(String areaDescription) {
        this.areaDescription = areaDescription;
    }

    public byte[] getAreaLogo() {
        return areaLogo;
    }

    public void setAreaLogo(byte[] areaLogo) {
        this.areaLogo = areaLogo;
    }

    public String getAreaLogoContentType() {
        return areaLogoContentType;
    }

    public void setAreaLogoContentType(String areaLogoContentType) {
        this.areaLogoContentType = areaLogoContentType;
    }

    public String getAreaParams() {
        return areaParams;
    }

    public void setAreaParams(String areaParams) {
        this.areaParams = areaParams;
    }

    public String getAreaAttributs() {
        return areaAttributs;
    }

    public void setAreaAttributs(String areaAttributs) {
        this.areaAttributs = areaAttributs;
    }

    public Boolean getAreaStat() {
        return areaStat;
    }

    public void setAreaStat(Boolean areaStat) {
        this.areaStat = areaStat;
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
        if (!(o instanceof AreaDTO)) {
            return false;
        }

        AreaDTO areaDTO = (AreaDTO) o;
        if (this.areaId == null) {
            return false;
        }
        return Objects.equals(this.areaId, areaDTO.areaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.areaId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaDTO{" +
            "areaId=" + getAreaId() +
            ", areaName='" + getAreaName() + "'" +
            ", areaAbreviation='" + getAreaAbreviation() + "'" +
            ", areaColor='" + getAreaColor() + "'" +
            ", areaDescription='" + getAreaDescription() + "'" +
            ", areaLogo='" + getAreaLogo() + "'" +
            ", areaParams='" + getAreaParams() + "'" +
            ", areaAttributs='" + getAreaAttributs() + "'" +
            ", areaStat='" + getAreaStat() + "'" +
            ", event=" + getEvent() +
            "}";
    }
}

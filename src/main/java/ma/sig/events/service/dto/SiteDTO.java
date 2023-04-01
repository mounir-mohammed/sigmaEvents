package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Site} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SiteDTO implements Serializable {

    private Long siteId;

    @NotNull
    @Size(max = 50)
    private String siteName;

    @Size(max = 100)
    private String siteColor;

    @Size(max = 10)
    private String siteAbreviation;

    @Size(max = 200)
    private String siteDescription;

    @Lob
    private byte[] siteLogo;

    private String siteLogoContentType;
    private String siteAdresse;

    private String siteEmail;

    private String siteTel;

    private String siteFax;

    private String siteResponsableName;

    private String siteParams;

    private String siteAttributs;

    private Boolean siteStat;

    private CityDTO city;

    private EventDTO event;

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteColor() {
        return siteColor;
    }

    public void setSiteColor(String siteColor) {
        this.siteColor = siteColor;
    }

    public String getSiteAbreviation() {
        return siteAbreviation;
    }

    public void setSiteAbreviation(String siteAbreviation) {
        this.siteAbreviation = siteAbreviation;
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public byte[] getSiteLogo() {
        return siteLogo;
    }

    public void setSiteLogo(byte[] siteLogo) {
        this.siteLogo = siteLogo;
    }

    public String getSiteLogoContentType() {
        return siteLogoContentType;
    }

    public void setSiteLogoContentType(String siteLogoContentType) {
        this.siteLogoContentType = siteLogoContentType;
    }

    public String getSiteAdresse() {
        return siteAdresse;
    }

    public void setSiteAdresse(String siteAdresse) {
        this.siteAdresse = siteAdresse;
    }

    public String getSiteEmail() {
        return siteEmail;
    }

    public void setSiteEmail(String siteEmail) {
        this.siteEmail = siteEmail;
    }

    public String getSiteTel() {
        return siteTel;
    }

    public void setSiteTel(String siteTel) {
        this.siteTel = siteTel;
    }

    public String getSiteFax() {
        return siteFax;
    }

    public void setSiteFax(String siteFax) {
        this.siteFax = siteFax;
    }

    public String getSiteResponsableName() {
        return siteResponsableName;
    }

    public void setSiteResponsableName(String siteResponsableName) {
        this.siteResponsableName = siteResponsableName;
    }

    public String getSiteParams() {
        return siteParams;
    }

    public void setSiteParams(String siteParams) {
        this.siteParams = siteParams;
    }

    public String getSiteAttributs() {
        return siteAttributs;
    }

    public void setSiteAttributs(String siteAttributs) {
        this.siteAttributs = siteAttributs;
    }

    public Boolean getSiteStat() {
        return siteStat;
    }

    public void setSiteStat(Boolean siteStat) {
        this.siteStat = siteStat;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
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
        if (!(o instanceof SiteDTO)) {
            return false;
        }

        SiteDTO siteDTO = (SiteDTO) o;
        if (this.siteId == null) {
            return false;
        }
        return Objects.equals(this.siteId, siteDTO.siteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.siteId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteDTO{" +
            "siteId=" + getSiteId() +
            ", siteName='" + getSiteName() + "'" +
            ", siteColor='" + getSiteColor() + "'" +
            ", siteAbreviation='" + getSiteAbreviation() + "'" +
            ", siteDescription='" + getSiteDescription() + "'" +
            ", siteLogo='" + getSiteLogo() + "'" +
            ", siteAdresse='" + getSiteAdresse() + "'" +
            ", siteEmail='" + getSiteEmail() + "'" +
            ", siteTel='" + getSiteTel() + "'" +
            ", siteFax='" + getSiteFax() + "'" +
            ", siteResponsableName='" + getSiteResponsableName() + "'" +
            ", siteParams='" + getSiteParams() + "'" +
            ", siteAttributs='" + getSiteAttributs() + "'" +
            ", siteStat='" + getSiteStat() + "'" +
            ", city=" + getCity() +
            ", event=" + getEvent() +
            "}";
    }
}

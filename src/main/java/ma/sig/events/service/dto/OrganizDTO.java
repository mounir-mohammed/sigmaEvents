package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Organiz} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizDTO implements Serializable {

    private Long organizId;

    @NotNull
    @Size(max = 50)
    private String organizName;

    @Size(max = 200)
    private String organizDescription;

    @Lob
    private byte[] organizLogo;

    private String organizLogoContentType;
    private String organizTel;

    private String organizFax;

    private String organizEmail;

    private String organizAdresse;

    private String organizParams;

    private String organizAttributs;

    private Boolean organizStat;

    private CountryDTO country;

    private CityDTO city;

    private EventDTO event;

    public Long getOrganizId() {
        return organizId;
    }

    public void setOrganizId(Long organizId) {
        this.organizId = organizId;
    }

    public String getOrganizName() {
        return organizName;
    }

    public void setOrganizName(String organizName) {
        this.organizName = organizName;
    }

    public String getOrganizDescription() {
        return organizDescription;
    }

    public void setOrganizDescription(String organizDescription) {
        this.organizDescription = organizDescription;
    }

    public byte[] getOrganizLogo() {
        return organizLogo;
    }

    public void setOrganizLogo(byte[] organizLogo) {
        this.organizLogo = organizLogo;
    }

    public String getOrganizLogoContentType() {
        return organizLogoContentType;
    }

    public void setOrganizLogoContentType(String organizLogoContentType) {
        this.organizLogoContentType = organizLogoContentType;
    }

    public String getOrganizTel() {
        return organizTel;
    }

    public void setOrganizTel(String organizTel) {
        this.organizTel = organizTel;
    }

    public String getOrganizFax() {
        return organizFax;
    }

    public void setOrganizFax(String organizFax) {
        this.organizFax = organizFax;
    }

    public String getOrganizEmail() {
        return organizEmail;
    }

    public void setOrganizEmail(String organizEmail) {
        this.organizEmail = organizEmail;
    }

    public String getOrganizAdresse() {
        return organizAdresse;
    }

    public void setOrganizAdresse(String organizAdresse) {
        this.organizAdresse = organizAdresse;
    }

    public String getOrganizParams() {
        return organizParams;
    }

    public void setOrganizParams(String organizParams) {
        this.organizParams = organizParams;
    }

    public String getOrganizAttributs() {
        return organizAttributs;
    }

    public void setOrganizAttributs(String organizAttributs) {
        this.organizAttributs = organizAttributs;
    }

    public Boolean getOrganizStat() {
        return organizStat;
    }

    public void setOrganizStat(Boolean organizStat) {
        this.organizStat = organizStat;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
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
        if (!(o instanceof OrganizDTO)) {
            return false;
        }

        OrganizDTO organizDTO = (OrganizDTO) o;
        if (this.organizId == null) {
            return false;
        }
        return Objects.equals(this.organizId, organizDTO.organizId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.organizId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizDTO{" +
            "organizId=" + getOrganizId() +
            ", organizName='" + getOrganizName() + "'" +
            ", organizDescription='" + getOrganizDescription() + "'" +
            ", organizLogo='" + getOrganizLogo() + "'" +
            ", organizTel='" + getOrganizTel() + "'" +
            ", organizFax='" + getOrganizFax() + "'" +
            ", organizEmail='" + getOrganizEmail() + "'" +
            ", organizAdresse='" + getOrganizAdresse() + "'" +
            ", organizParams='" + getOrganizParams() + "'" +
            ", organizAttributs='" + getOrganizAttributs() + "'" +
            ", organizStat='" + getOrganizStat() + "'" +
            ", country=" + getCountry() +
            ", city=" + getCity() +
            ", event=" + getEvent() +
            "}";
    }
}

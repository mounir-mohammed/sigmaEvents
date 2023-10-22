package ma.sig.events.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Lob;

/**
 * A DTO for the {@link ma.sig.events.domain.MassUpdateAccreditationDTO} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MassUpdateAccreditationDTO implements Serializable {

    private List<Long> accreditationIds;
    private String accreditationOccupation;
    private Boolean accreditationStat;
    private Boolean accreditationActivated;
    private ZonedDateTime accreditationDateStart;
    private ZonedDateTime accreditationDateEnd;
    private CategoryDTO category;
    private FonctionDTO fonction;
    private AccreditationTypeDTO accreditationType;
    private Set<SiteDTO> sites = new HashSet<>();
    private OrganizDTO organiz;
    private CivilityDTO civility;
    private SexeDTO sexe;
    private NationalityDTO nationality;
    private EventDTO event;
    private StatusDTO status;
    private CountryDTO country;
    private CityDTO city;
    private String accreditationDescription;
    private String accreditationParams;
    private String accreditationAttributs;
    private AttachementDTO attachement;
    private CodeDTO code;
    private DayPassInfoDTO dayPassInfo;

    @Lob
    private byte[] accreditationPhoto;

    private String accreditationPhotoContentType;

    private Boolean accreditationUpdateSites;

    public List<Long> getAccreditationIds() {
        return accreditationIds;
    }

    public void setAccreditationIds(List<Long> accreditationIds) {
        this.accreditationIds = accreditationIds;
    }

    public String getAccreditationOccupation() {
        return accreditationOccupation;
    }

    public void setAccreditationOccupation(String accreditationOccupation) {
        this.accreditationOccupation = accreditationOccupation;
    }

    public Boolean getAccreditationStat() {
        return accreditationStat;
    }

    public void setAccreditationStat(Boolean accreditationStat) {
        this.accreditationStat = accreditationStat;
    }

    public Boolean getAccreditationActivated() {
        return accreditationActivated;
    }

    public void setAccreditationActivated(Boolean accreditationActivated) {
        this.accreditationActivated = accreditationActivated;
    }

    public ZonedDateTime getAccreditationDateStart() {
        return accreditationDateStart;
    }

    public void setAccreditationDateStart(ZonedDateTime accreditationDateStart) {
        this.accreditationDateStart = accreditationDateStart;
    }

    public ZonedDateTime getAccreditationDateEnd() {
        return accreditationDateEnd;
    }

    public void setAccreditationDateEnd(ZonedDateTime accreditationDateEnd) {
        this.accreditationDateEnd = accreditationDateEnd;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public FonctionDTO getFonction() {
        return fonction;
    }

    public void setFonction(FonctionDTO fonction) {
        this.fonction = fonction;
    }

    public AccreditationTypeDTO getAccreditationType() {
        return accreditationType;
    }

    public void setAccreditationType(AccreditationTypeDTO accreditationType) {
        this.accreditationType = accreditationType;
    }

    public Set<SiteDTO> getSites() {
        return sites;
    }

    public void setSites(Set<SiteDTO> sites) {
        this.sites = sites;
    }

    public OrganizDTO getOrganiz() {
        return organiz;
    }

    public void setOrganiz(OrganizDTO organiz) {
        this.organiz = organiz;
    }

    public CivilityDTO getCivility() {
        return civility;
    }

    public void setCivility(CivilityDTO civility) {
        this.civility = civility;
    }

    public SexeDTO getSexe() {
        return sexe;
    }

    public void setSexe(SexeDTO sexe) {
        this.sexe = sexe;
    }

    public NationalityDTO getNationality() {
        return nationality;
    }

    public void setNationality(NationalityDTO nationality) {
        this.nationality = nationality;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
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

    public String getAccreditationDescription() {
        return accreditationDescription;
    }

    public void setAccreditationDescription(String accreditationDescription) {
        this.accreditationDescription = accreditationDescription;
    }

    public String getAccreditationParams() {
        return accreditationParams;
    }

    public void setAccreditationParams(String accreditationParams) {
        this.accreditationParams = accreditationParams;
    }

    public String getAccreditationAttributs() {
        return accreditationAttributs;
    }

    public void setAccreditationAttributs(String accreditationAttributs) {
        this.accreditationAttributs = accreditationAttributs;
    }

    public AttachementDTO getAttachement() {
        return attachement;
    }

    public void setAttachement(AttachementDTO attachement) {
        this.attachement = attachement;
    }

    public CodeDTO getCode() {
        return code;
    }

    public void setCode(CodeDTO code) {
        this.code = code;
    }

    public DayPassInfoDTO getDayPassInfo() {
        return dayPassInfo;
    }

    public void setDayPassInfo(DayPassInfoDTO dayPassInfo) {
        this.dayPassInfo = dayPassInfo;
    }

    public byte[] getAccreditationPhoto() {
        return accreditationPhoto;
    }

    public void setAccreditationPhoto(byte[] accreditationPhoto) {
        this.accreditationPhoto = accreditationPhoto;
    }

    public String getAccreditationPhotoContentType() {
        return accreditationPhotoContentType;
    }

    public void setAccreditationPhotoContentType(String accreditationPhotoContentType) {
        this.accreditationPhotoContentType = accreditationPhotoContentType;
    }

    public Boolean getAccreditationUpdateSites() {
        return accreditationUpdateSites;
    }

    public void setAccreditationUpdateSites(Boolean accreditationUpdateSites) {
        this.accreditationUpdateSites = accreditationUpdateSites;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccreditationDTO{" +
            "accreditationIds=" + getAccreditationIds() +
            ", accreditationOccupation='" + getAccreditationOccupation() + "'" +
            ", accreditationDescription='" + getAccreditationDescription() + "'" +
            ", accreditationDateStart='" + getAccreditationDateStart() + "'" +
            ", accreditationDateEnd='" + getAccreditationDateEnd() + "'" +
            ", accreditationParams='" + getAccreditationParams() + "'" +
            ", accreditationAttributs='" + getAccreditationAttributs() + "'" +
            ", accreditationStat='" + getAccreditationStat() + "'" +
            ", accreditationActivated='" + getAccreditationActivated() + "'" +
            ", sites=" + getSites() +
            ", event=" + getEvent() +
            ", civility=" + getCivility() +
            ", sexe=" + getSexe() +
            ", nationality=" + getNationality() +
            ", country=" + getCountry() +
            ", city=" + getCity() +
            ", category=" + getCategory() +
            ", fonction=" + getFonction() +
            ", organiz=" + getOrganiz() +
            ", accreditationType=" + getAccreditationType() +
            ", status=" + getStatus() +
            ", attachement=" + getAttachement() +
            ", code=" + getCode() +
            ", dayPassInfo=" + getDayPassInfo() +
            "}";
    }
}

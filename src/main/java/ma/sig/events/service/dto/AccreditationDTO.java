package ma.sig.events.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Accreditation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccreditationDTO implements Serializable {

    private Long accreditationId;

    @NotNull
    @Size(min = 2, max = 50)
    private String accreditationFirstName;

    @Size(max = 50)
    private String accreditationSecondName;

    @NotNull
    @Size(min = 2, max = 50)
    private String accreditationLastName;

    @NotNull
    private LocalDate accreditationBirthDay;

    private String accreditationSexe;

    private String accreditationOccupation;

    private String accreditationDescription;

    private String accreditationEmail;

    private String accreditationTel;

    @Lob
    private byte[] accreditationPhoto;

    private String accreditationPhotoContentType;

    private Boolean accreditationPhotoStat;
    private String accreditationCinId;

    private String accreditationPasseportId;

    private String accreditationCartePresseId;

    private String accreditationCarteProfessionnelleId;

    private ZonedDateTime accreditationCreationDate;

    private ZonedDateTime accreditationUpdateDate;

    private ZonedDateTime accreditationPrintDate;

    private String accreditationCreatedByuser;

    private String accreditationUpdatedByuser;

    private String accreditationPrintedByuser;

    private ZonedDateTime accreditationDateStart;

    private ZonedDateTime accreditationDateEnd;

    private Boolean accreditationPrintStat;

    private Long accreditationPrintNumber;

    private String accreditationParams;

    private String accreditationAttributs;

    private Boolean accreditationStat;

    private Boolean accreditationActivated;

    private Set<SiteDTO> sites = new HashSet<>();

    private EventDTO event;

    private CivilityDTO civility;

    private SexeDTO sexe;

    private NationalityDTO nationality;

    private CountryDTO country;

    private CityDTO city;

    private CategoryDTO category;

    private FonctionDTO fonction;

    private OrganizDTO organiz;

    private AccreditationTypeDTO accreditationType;

    private StatusDTO status;

    private AttachementDTO attachement;

    private CodeDTO code;

    private DayPassInfoDTO dayPassInfo;

    public Long getAccreditationId() {
        return accreditationId;
    }

    public void setAccreditationId(Long accreditationId) {
        this.accreditationId = accreditationId;
    }

    public String getAccreditationFirstName() {
        return accreditationFirstName;
    }

    public void setAccreditationFirstName(String accreditationFirstName) {
        this.accreditationFirstName = accreditationFirstName;
    }

    public String getAccreditationSecondName() {
        return accreditationSecondName;
    }

    public void setAccreditationSecondName(String accreditationSecondName) {
        this.accreditationSecondName = accreditationSecondName;
    }

    public String getAccreditationLastName() {
        return accreditationLastName;
    }

    public void setAccreditationLastName(String accreditationLastName) {
        this.accreditationLastName = accreditationLastName;
    }

    public LocalDate getAccreditationBirthDay() {
        return accreditationBirthDay;
    }

    public void setAccreditationBirthDay(LocalDate accreditationBirthDay) {
        this.accreditationBirthDay = accreditationBirthDay;
    }

    public String getAccreditationSexe() {
        return accreditationSexe;
    }

    public void setAccreditationSexe(String accreditationSexe) {
        this.accreditationSexe = accreditationSexe;
    }

    public String getAccreditationOccupation() {
        return accreditationOccupation;
    }

    public void setAccreditationOccupation(String accreditationOccupation) {
        this.accreditationOccupation = accreditationOccupation;
    }

    public String getAccreditationDescription() {
        return accreditationDescription;
    }

    public void setAccreditationDescription(String accreditationDescription) {
        this.accreditationDescription = accreditationDescription;
    }

    public String getAccreditationEmail() {
        return accreditationEmail;
    }

    public void setAccreditationEmail(String accreditationEmail) {
        this.accreditationEmail = accreditationEmail;
    }

    public String getAccreditationTel() {
        return accreditationTel;
    }

    public void setAccreditationTel(String accreditationTel) {
        this.accreditationTel = accreditationTel;
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

    public String getAccreditationCinId() {
        return accreditationCinId;
    }

    public void setAccreditationCinId(String accreditationCinId) {
        this.accreditationCinId = accreditationCinId;
    }

    public String getAccreditationPasseportId() {
        return accreditationPasseportId;
    }

    public void setAccreditationPasseportId(String accreditationPasseportId) {
        this.accreditationPasseportId = accreditationPasseportId;
    }

    public String getAccreditationCartePresseId() {
        return accreditationCartePresseId;
    }

    public void setAccreditationCartePresseId(String accreditationCartePresseId) {
        this.accreditationCartePresseId = accreditationCartePresseId;
    }

    public String getAccreditationCarteProfessionnelleId() {
        return accreditationCarteProfessionnelleId;
    }

    public void setAccreditationCarteProfessionnelleId(String accreditationCarteProfessionnelleId) {
        this.accreditationCarteProfessionnelleId = accreditationCarteProfessionnelleId;
    }

    public ZonedDateTime getAccreditationCreationDate() {
        return accreditationCreationDate;
    }

    public void setAccreditationCreationDate(ZonedDateTime accreditationCreationDate) {
        this.accreditationCreationDate = accreditationCreationDate;
    }

    public ZonedDateTime getAccreditationUpdateDate() {
        return accreditationUpdateDate;
    }

    public void setAccreditationUpdateDate(ZonedDateTime accreditationUpdateDate) {
        this.accreditationUpdateDate = accreditationUpdateDate;
    }

    public String getAccreditationCreatedByuser() {
        return accreditationCreatedByuser;
    }

    public void setAccreditationCreatedByuser(String accreditationCreatedByuser) {
        this.accreditationCreatedByuser = accreditationCreatedByuser;
    }

    public ZonedDateTime getAccreditationPrintDate() {
        return accreditationPrintDate;
    }

    public void setAccreditationPrintDate(ZonedDateTime accreditationPrintDate) {
        this.accreditationPrintDate = accreditationPrintDate;
    }

    public String getAccreditationUpdatedByuser() {
        return accreditationUpdatedByuser;
    }

    public void setAccreditationUpdatedByuser(String accreditationUpdatedByuser) {
        this.accreditationUpdatedByuser = accreditationUpdatedByuser;
    }

    public String getAccreditationPrintedByuser() {
        return accreditationPrintedByuser;
    }

    public void setAccreditationPrintedByuser(String accreditationPrintedByuser) {
        this.accreditationPrintedByuser = accreditationPrintedByuser;
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

    public Boolean getAccreditationPrintStat() {
        return accreditationPrintStat;
    }

    public void setAccreditationPrintStat(Boolean accreditationPrintStat) {
        this.accreditationPrintStat = accreditationPrintStat;
    }

    public Long getAccreditationPrintNumber() {
        return accreditationPrintNumber;
    }

    public void setAccreditationPrintNumber(Long accreditationPrintNumber) {
        this.accreditationPrintNumber = accreditationPrintNumber;
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

    public Set<SiteDTO> getSites() {
        return sites;
    }

    public void setSites(Set<SiteDTO> sites) {
        this.sites = sites;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
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

    public OrganizDTO getOrganiz() {
        return organiz;
    }

    public void setOrganiz(OrganizDTO organiz) {
        this.organiz = organiz;
    }

    public AccreditationTypeDTO getAccreditationType() {
        return accreditationType;
    }

    public void setAccreditationType(AccreditationTypeDTO accreditationType) {
        this.accreditationType = accreditationType;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
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

    public Boolean getAccreditationPhotoStat() {
        return accreditationPhotoStat;
    }

    public void setAccreditationPhotoStat(Boolean accreditationPhotoStat) {
        this.accreditationPhotoStat = accreditationPhotoStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccreditationDTO)) {
            return false;
        }

        AccreditationDTO accreditationDTO = (AccreditationDTO) o;
        if (this.accreditationId == null) {
            return false;
        }
        return Objects.equals(this.accreditationId, accreditationDTO.accreditationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.accreditationId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccreditationDTO{" +
            "accreditationId=" + getAccreditationId() +
            ", accreditationFirstName='" + getAccreditationFirstName() + "'" +
            ", accreditationSecondName='" + getAccreditationSecondName() + "'" +
            ", accreditationLastName='" + getAccreditationLastName() + "'" +
            ", accreditationBirthDay='" + getAccreditationBirthDay() + "'" +
            ", accreditationSexe='" + getAccreditationSexe() + "'" +
            ", accreditationOccupation='" + getAccreditationOccupation() + "'" +
            ", accreditationDescription='" + getAccreditationDescription() + "'" +
            ", accreditationEmail='" + getAccreditationEmail() + "'" +
            ", accreditationTel='" + getAccreditationTel() + "'" +
            ", accreditationPhoto='" + getAccreditationPhoto() + "'" +
            ", accreditationPhotoStat='" + getAccreditationPhotoStat() + "'" +
            ", accreditationCinId='" + getAccreditationCinId() + "'" +
            ", accreditationPasseportId='" + getAccreditationPasseportId() + "'" +
            ", accreditationCartePresseId='" + getAccreditationCartePresseId() + "'" +
            ", accreditationCarteProfessionnelleId='" + getAccreditationCarteProfessionnelleId() + "'" +
            ", accreditationCreationDate='" + getAccreditationCreationDate() + "'" +
            ", accreditationUpdateDate='" + getAccreditationUpdateDate() + "'" +
            ", accreditationCreatedByuser='" + getAccreditationCreatedByuser() + "'" +
            ", accreditationUpdatedByuser='" + getAccreditationUpdatedByuser() + "'" +
            ", accreditationPrintedByuser='" + getAccreditationPrintedByuser() + "'" +
            ", accreditationPrintDate='" + getAccreditationPrintDate() + "'" +
            ", accreditationDateStart='" + getAccreditationDateStart() + "'" +
            ", accreditationDateEnd='" + getAccreditationDateEnd() + "'" +
            ", accreditationPrintStat='" + getAccreditationPrintStat() + "'" +
            ", accreditationPrintNumber=" + getAccreditationPrintNumber() +
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

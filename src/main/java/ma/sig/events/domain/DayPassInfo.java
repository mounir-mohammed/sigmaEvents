package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DayPassInfo.
 */
@Entity
@Table(name = "day_pass_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DayPassInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_pass_info_id")
    private Long dayPassInfoId;

    @Column(name = "day_pass_info_name")
    private String dayPassInfoName;

    @Size(max = 200)
    @Column(name = "day_pass_description", length = 200)
    private String dayPassDescription;

    @Lob
    @Column(name = "day_pass_logo")
    private byte[] dayPassLogo;

    @Column(name = "day_pass_logo_content_type")
    private String dayPassLogoContentType;

    @Column(name = "day_pass_info_creation_date")
    private ZonedDateTime dayPassInfoCreationDate;

    @Column(name = "day_pass_info_update_date")
    private ZonedDateTime dayPassInfoUpdateDate;

    @Column(name = "day_pass_info_created_byuser")
    private String dayPassInfoCreatedByuser;

    @Column(name = "day_pass_info_date_start")
    private ZonedDateTime dayPassInfoDateStart;

    @Column(name = "day_pass_info_date_end")
    private ZonedDateTime dayPassInfoDateEnd;

    @Column(name = "day_pass_info_number")
    private Long dayPassInfoNumber;

    @Column(name = "day_pass_params")
    private String dayPassParams;

    @Column(name = "day_pass_attributs")
    private String dayPassAttributs;

    @Column(name = "day_pass_info_stat")
    private Boolean dayPassInfoStat;

    @OneToMany(mappedBy = "dayPassInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "photoArchives",
            "infoSupps",
            "notes",
            "checkAccreditationHistories",
            "sites",
            "event",
            "civility",
            "sexe",
            "nationality",
            "country",
            "city",
            "category",
            "fonction",
            "organiz",
            "accreditationType",
            "status",
            "attachement",
            "code",
            "dayPassInfo",
        },
        allowSetters = true
    )
    private Set<Accreditation> accreditations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "eventForms",
            "eventFields",
            "eventControls",
            "areas",
            "fonctions",
            "categories",
            "printingModels",
            "codes",
            "infoSupps",
            "attachements",
            "organizs",
            "photoArchives",
            "sites",
            "accreditations",
            "notes",
            "operationHistories",
            "printingCentres",
            "settings",
            "printingServers",
            "checkAccreditationHistories",
            "checkAccreditationReports",
            "accreditationTypes",
            "dayPassInfos",
            "language",
        },
        allowSetters = true
    )
    private Event event;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getDayPassInfoId() {
        return this.dayPassInfoId;
    }

    public DayPassInfo dayPassInfoId(Long dayPassInfoId) {
        this.setDayPassInfoId(dayPassInfoId);
        return this;
    }

    public void setDayPassInfoId(Long dayPassInfoId) {
        this.dayPassInfoId = dayPassInfoId;
    }

    public String getDayPassInfoName() {
        return this.dayPassInfoName;
    }

    public DayPassInfo dayPassInfoName(String dayPassInfoName) {
        this.setDayPassInfoName(dayPassInfoName);
        return this;
    }

    public void setDayPassInfoName(String dayPassInfoName) {
        this.dayPassInfoName = dayPassInfoName;
    }

    public String getDayPassDescription() {
        return this.dayPassDescription;
    }

    public DayPassInfo dayPassDescription(String dayPassDescription) {
        this.setDayPassDescription(dayPassDescription);
        return this;
    }

    public void setDayPassDescription(String dayPassDescription) {
        this.dayPassDescription = dayPassDescription;
    }

    public byte[] getDayPassLogo() {
        return this.dayPassLogo;
    }

    public DayPassInfo dayPassLogo(byte[] dayPassLogo) {
        this.setDayPassLogo(dayPassLogo);
        return this;
    }

    public void setDayPassLogo(byte[] dayPassLogo) {
        this.dayPassLogo = dayPassLogo;
    }

    public String getDayPassLogoContentType() {
        return this.dayPassLogoContentType;
    }

    public DayPassInfo dayPassLogoContentType(String dayPassLogoContentType) {
        this.dayPassLogoContentType = dayPassLogoContentType;
        return this;
    }

    public void setDayPassLogoContentType(String dayPassLogoContentType) {
        this.dayPassLogoContentType = dayPassLogoContentType;
    }

    public ZonedDateTime getDayPassInfoCreationDate() {
        return this.dayPassInfoCreationDate;
    }

    public DayPassInfo dayPassInfoCreationDate(ZonedDateTime dayPassInfoCreationDate) {
        this.setDayPassInfoCreationDate(dayPassInfoCreationDate);
        return this;
    }

    public void setDayPassInfoCreationDate(ZonedDateTime dayPassInfoCreationDate) {
        this.dayPassInfoCreationDate = dayPassInfoCreationDate;
    }

    public ZonedDateTime getDayPassInfoUpdateDate() {
        return this.dayPassInfoUpdateDate;
    }

    public DayPassInfo dayPassInfoUpdateDate(ZonedDateTime dayPassInfoUpdateDate) {
        this.setDayPassInfoUpdateDate(dayPassInfoUpdateDate);
        return this;
    }

    public void setDayPassInfoUpdateDate(ZonedDateTime dayPassInfoUpdateDate) {
        this.dayPassInfoUpdateDate = dayPassInfoUpdateDate;
    }

    public String getDayPassInfoCreatedByuser() {
        return this.dayPassInfoCreatedByuser;
    }

    public DayPassInfo dayPassInfoCreatedByuser(String dayPassInfoCreatedByuser) {
        this.setDayPassInfoCreatedByuser(dayPassInfoCreatedByuser);
        return this;
    }

    public void setDayPassInfoCreatedByuser(String dayPassInfoCreatedByuser) {
        this.dayPassInfoCreatedByuser = dayPassInfoCreatedByuser;
    }

    public ZonedDateTime getDayPassInfoDateStart() {
        return this.dayPassInfoDateStart;
    }

    public DayPassInfo dayPassInfoDateStart(ZonedDateTime dayPassInfoDateStart) {
        this.setDayPassInfoDateStart(dayPassInfoDateStart);
        return this;
    }

    public void setDayPassInfoDateStart(ZonedDateTime dayPassInfoDateStart) {
        this.dayPassInfoDateStart = dayPassInfoDateStart;
    }

    public ZonedDateTime getDayPassInfoDateEnd() {
        return this.dayPassInfoDateEnd;
    }

    public DayPassInfo dayPassInfoDateEnd(ZonedDateTime dayPassInfoDateEnd) {
        this.setDayPassInfoDateEnd(dayPassInfoDateEnd);
        return this;
    }

    public void setDayPassInfoDateEnd(ZonedDateTime dayPassInfoDateEnd) {
        this.dayPassInfoDateEnd = dayPassInfoDateEnd;
    }

    public Long getDayPassInfoNumber() {
        return this.dayPassInfoNumber;
    }

    public DayPassInfo dayPassInfoNumber(Long dayPassInfoNumber) {
        this.setDayPassInfoNumber(dayPassInfoNumber);
        return this;
    }

    public void setDayPassInfoNumber(Long dayPassInfoNumber) {
        this.dayPassInfoNumber = dayPassInfoNumber;
    }

    public String getDayPassParams() {
        return this.dayPassParams;
    }

    public DayPassInfo dayPassParams(String dayPassParams) {
        this.setDayPassParams(dayPassParams);
        return this;
    }

    public void setDayPassParams(String dayPassParams) {
        this.dayPassParams = dayPassParams;
    }

    public String getDayPassAttributs() {
        return this.dayPassAttributs;
    }

    public DayPassInfo dayPassAttributs(String dayPassAttributs) {
        this.setDayPassAttributs(dayPassAttributs);
        return this;
    }

    public void setDayPassAttributs(String dayPassAttributs) {
        this.dayPassAttributs = dayPassAttributs;
    }

    public Boolean getDayPassInfoStat() {
        return this.dayPassInfoStat;
    }

    public DayPassInfo dayPassInfoStat(Boolean dayPassInfoStat) {
        this.setDayPassInfoStat(dayPassInfoStat);
        return this;
    }

    public void setDayPassInfoStat(Boolean dayPassInfoStat) {
        this.dayPassInfoStat = dayPassInfoStat;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setDayPassInfo(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setDayPassInfo(this));
        }
        this.accreditations = accreditations;
    }

    public DayPassInfo accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public DayPassInfo addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setDayPassInfo(this);
        return this;
    }

    public DayPassInfo removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setDayPassInfo(null);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public DayPassInfo event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DayPassInfo)) {
            return false;
        }
        return dayPassInfoId != null && dayPassInfoId.equals(((DayPassInfo) o).dayPassInfoId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DayPassInfo{" +
            "dayPassInfoId=" + getDayPassInfoId() +
            ", dayPassInfoName='" + getDayPassInfoName() + "'" +
            ", dayPassDescription='" + getDayPassDescription() + "'" +
            ", dayPassLogo='" + getDayPassLogo() + "'" +
            ", dayPassLogoContentType='" + getDayPassLogoContentType() + "'" +
            ", dayPassInfoCreationDate='" + getDayPassInfoCreationDate() + "'" +
            ", dayPassInfoUpdateDate='" + getDayPassInfoUpdateDate() + "'" +
            ", dayPassInfoCreatedByuser='" + getDayPassInfoCreatedByuser() + "'" +
            ", dayPassInfoDateStart='" + getDayPassInfoDateStart() + "'" +
            ", dayPassInfoDateEnd='" + getDayPassInfoDateEnd() + "'" +
            ", dayPassInfoNumber=" + getDayPassInfoNumber() +
            ", dayPassParams='" + getDayPassParams() + "'" +
            ", dayPassAttributs='" + getDayPassAttributs() + "'" +
            ", dayPassInfoStat='" + getDayPassInfoStat() + "'" +
            "}";
    }
}

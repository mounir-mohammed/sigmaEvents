package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Accreditation} entity. This class is used
 * in {@link ma.sig.events.web.rest.AccreditationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /accreditations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccreditationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter accreditationId;

    private StringFilter accreditationFirstName;

    private StringFilter accreditationSecondName;

    private StringFilter accreditationLastName;

    private LocalDateFilter accreditationBirthDay;

    private StringFilter accreditationSexe;

    private StringFilter accreditationOccupation;

    private StringFilter accreditationDescription;

    private StringFilter accreditationEmail;

    private StringFilter accreditationTel;

    private StringFilter accreditationCinId;

    private StringFilter accreditationPasseportId;

    private StringFilter accreditationCartePresseId;

    private StringFilter accreditationCarteProfessionnelleId;

    private ZonedDateTimeFilter accreditationCreationDate;

    private ZonedDateTimeFilter accreditationUpdateDate;

    private StringFilter accreditationCreatedByuser;

    private ZonedDateTimeFilter accreditationDateStart;

    private ZonedDateTimeFilter accreditationDateEnd;

    private BooleanFilter accreditationPrintStat;

    private LongFilter accreditationPrintNumber;

    private StringFilter accreditationParams;

    private StringFilter accreditationAttributs;

    private BooleanFilter accreditationStat;

    private BooleanFilter accreditationActivated;

    private LongFilter photoArchiveId;

    private LongFilter infoSuppId;

    private LongFilter noteId;

    private LongFilter checkAccreditationHistoryId;

    private LongFilter siteId;

    private LongFilter eventId;

    private LongFilter civilityId;

    private LongFilter sexeId;

    private LongFilter nationalityId;

    private LongFilter countryId;

    private LongFilter cityId;

    private LongFilter categoryId;

    private LongFilter fonctionId;

    private LongFilter organizId;

    private LongFilter accreditationTypeId;

    private LongFilter statusId;

    private LongFilter attachementId;

    private LongFilter codeId;

    private LongFilter dayPassInfoId;

    private Boolean distinct;

    public AccreditationCriteria() {}

    public AccreditationCriteria(AccreditationCriteria other) {
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.accreditationFirstName = other.accreditationFirstName == null ? null : other.accreditationFirstName.copy();
        this.accreditationSecondName = other.accreditationSecondName == null ? null : other.accreditationSecondName.copy();
        this.accreditationLastName = other.accreditationLastName == null ? null : other.accreditationLastName.copy();
        this.accreditationBirthDay = other.accreditationBirthDay == null ? null : other.accreditationBirthDay.copy();
        this.accreditationSexe = other.accreditationSexe == null ? null : other.accreditationSexe.copy();
        this.accreditationOccupation = other.accreditationOccupation == null ? null : other.accreditationOccupation.copy();
        this.accreditationDescription = other.accreditationDescription == null ? null : other.accreditationDescription.copy();
        this.accreditationEmail = other.accreditationEmail == null ? null : other.accreditationEmail.copy();
        this.accreditationTel = other.accreditationTel == null ? null : other.accreditationTel.copy();
        this.accreditationCinId = other.accreditationCinId == null ? null : other.accreditationCinId.copy();
        this.accreditationPasseportId = other.accreditationPasseportId == null ? null : other.accreditationPasseportId.copy();
        this.accreditationCartePresseId = other.accreditationCartePresseId == null ? null : other.accreditationCartePresseId.copy();
        this.accreditationCarteProfessionnelleId =
            other.accreditationCarteProfessionnelleId == null ? null : other.accreditationCarteProfessionnelleId.copy();
        this.accreditationCreationDate = other.accreditationCreationDate == null ? null : other.accreditationCreationDate.copy();
        this.accreditationUpdateDate = other.accreditationUpdateDate == null ? null : other.accreditationUpdateDate.copy();
        this.accreditationCreatedByuser = other.accreditationCreatedByuser == null ? null : other.accreditationCreatedByuser.copy();
        this.accreditationDateStart = other.accreditationDateStart == null ? null : other.accreditationDateStart.copy();
        this.accreditationDateEnd = other.accreditationDateEnd == null ? null : other.accreditationDateEnd.copy();
        this.accreditationPrintStat = other.accreditationPrintStat == null ? null : other.accreditationPrintStat.copy();
        this.accreditationPrintNumber = other.accreditationPrintNumber == null ? null : other.accreditationPrintNumber.copy();
        this.accreditationParams = other.accreditationParams == null ? null : other.accreditationParams.copy();
        this.accreditationAttributs = other.accreditationAttributs == null ? null : other.accreditationAttributs.copy();
        this.accreditationStat = other.accreditationStat == null ? null : other.accreditationStat.copy();
        this.accreditationActivated = other.accreditationActivated == null ? null : other.accreditationActivated.copy();
        this.photoArchiveId = other.photoArchiveId == null ? null : other.photoArchiveId.copy();
        this.infoSuppId = other.infoSuppId == null ? null : other.infoSuppId.copy();
        this.noteId = other.noteId == null ? null : other.noteId.copy();
        this.checkAccreditationHistoryId = other.checkAccreditationHistoryId == null ? null : other.checkAccreditationHistoryId.copy();
        this.siteId = other.siteId == null ? null : other.siteId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.civilityId = other.civilityId == null ? null : other.civilityId.copy();
        this.sexeId = other.sexeId == null ? null : other.sexeId.copy();
        this.nationalityId = other.nationalityId == null ? null : other.nationalityId.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.fonctionId = other.fonctionId == null ? null : other.fonctionId.copy();
        this.organizId = other.organizId == null ? null : other.organizId.copy();
        this.accreditationTypeId = other.accreditationTypeId == null ? null : other.accreditationTypeId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.attachementId = other.attachementId == null ? null : other.attachementId.copy();
        this.codeId = other.codeId == null ? null : other.codeId.copy();
        this.dayPassInfoId = other.dayPassInfoId == null ? null : other.dayPassInfoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AccreditationCriteria copy() {
        return new AccreditationCriteria(this);
    }

    public LongFilter getAccreditationId() {
        return accreditationId;
    }

    public LongFilter accreditationId() {
        if (accreditationId == null) {
            accreditationId = new LongFilter();
        }
        return accreditationId;
    }

    public void setAccreditationId(LongFilter accreditationId) {
        this.accreditationId = accreditationId;
    }

    public StringFilter getAccreditationFirstName() {
        return accreditationFirstName;
    }

    public StringFilter accreditationFirstName() {
        if (accreditationFirstName == null) {
            accreditationFirstName = new StringFilter();
        }
        return accreditationFirstName;
    }

    public void setAccreditationFirstName(StringFilter accreditationFirstName) {
        this.accreditationFirstName = accreditationFirstName;
    }

    public StringFilter getAccreditationSecondName() {
        return accreditationSecondName;
    }

    public StringFilter accreditationSecondName() {
        if (accreditationSecondName == null) {
            accreditationSecondName = new StringFilter();
        }
        return accreditationSecondName;
    }

    public void setAccreditationSecondName(StringFilter accreditationSecondName) {
        this.accreditationSecondName = accreditationSecondName;
    }

    public StringFilter getAccreditationLastName() {
        return accreditationLastName;
    }

    public StringFilter accreditationLastName() {
        if (accreditationLastName == null) {
            accreditationLastName = new StringFilter();
        }
        return accreditationLastName;
    }

    public void setAccreditationLastName(StringFilter accreditationLastName) {
        this.accreditationLastName = accreditationLastName;
    }

    public LocalDateFilter getAccreditationBirthDay() {
        return accreditationBirthDay;
    }

    public LocalDateFilter accreditationBirthDay() {
        if (accreditationBirthDay == null) {
            accreditationBirthDay = new LocalDateFilter();
        }
        return accreditationBirthDay;
    }

    public void setAccreditationBirthDay(LocalDateFilter accreditationBirthDay) {
        this.accreditationBirthDay = accreditationBirthDay;
    }

    public StringFilter getAccreditationSexe() {
        return accreditationSexe;
    }

    public StringFilter accreditationSexe() {
        if (accreditationSexe == null) {
            accreditationSexe = new StringFilter();
        }
        return accreditationSexe;
    }

    public void setAccreditationSexe(StringFilter accreditationSexe) {
        this.accreditationSexe = accreditationSexe;
    }

    public StringFilter getAccreditationOccupation() {
        return accreditationOccupation;
    }

    public StringFilter accreditationOccupation() {
        if (accreditationOccupation == null) {
            accreditationOccupation = new StringFilter();
        }
        return accreditationOccupation;
    }

    public void setAccreditationOccupation(StringFilter accreditationOccupation) {
        this.accreditationOccupation = accreditationOccupation;
    }

    public StringFilter getAccreditationDescription() {
        return accreditationDescription;
    }

    public StringFilter accreditationDescription() {
        if (accreditationDescription == null) {
            accreditationDescription = new StringFilter();
        }
        return accreditationDescription;
    }

    public void setAccreditationDescription(StringFilter accreditationDescription) {
        this.accreditationDescription = accreditationDescription;
    }

    public StringFilter getAccreditationEmail() {
        return accreditationEmail;
    }

    public StringFilter accreditationEmail() {
        if (accreditationEmail == null) {
            accreditationEmail = new StringFilter();
        }
        return accreditationEmail;
    }

    public void setAccreditationEmail(StringFilter accreditationEmail) {
        this.accreditationEmail = accreditationEmail;
    }

    public StringFilter getAccreditationTel() {
        return accreditationTel;
    }

    public StringFilter accreditationTel() {
        if (accreditationTel == null) {
            accreditationTel = new StringFilter();
        }
        return accreditationTel;
    }

    public void setAccreditationTel(StringFilter accreditationTel) {
        this.accreditationTel = accreditationTel;
    }

    public StringFilter getAccreditationCinId() {
        return accreditationCinId;
    }

    public StringFilter accreditationCinId() {
        if (accreditationCinId == null) {
            accreditationCinId = new StringFilter();
        }
        return accreditationCinId;
    }

    public void setAccreditationCinId(StringFilter accreditationCinId) {
        this.accreditationCinId = accreditationCinId;
    }

    public StringFilter getAccreditationPasseportId() {
        return accreditationPasseportId;
    }

    public StringFilter accreditationPasseportId() {
        if (accreditationPasseportId == null) {
            accreditationPasseportId = new StringFilter();
        }
        return accreditationPasseportId;
    }

    public void setAccreditationPasseportId(StringFilter accreditationPasseportId) {
        this.accreditationPasseportId = accreditationPasseportId;
    }

    public StringFilter getAccreditationCartePresseId() {
        return accreditationCartePresseId;
    }

    public StringFilter accreditationCartePresseId() {
        if (accreditationCartePresseId == null) {
            accreditationCartePresseId = new StringFilter();
        }
        return accreditationCartePresseId;
    }

    public void setAccreditationCartePresseId(StringFilter accreditationCartePresseId) {
        this.accreditationCartePresseId = accreditationCartePresseId;
    }

    public StringFilter getAccreditationCarteProfessionnelleId() {
        return accreditationCarteProfessionnelleId;
    }

    public StringFilter accreditationCarteProfessionnelleId() {
        if (accreditationCarteProfessionnelleId == null) {
            accreditationCarteProfessionnelleId = new StringFilter();
        }
        return accreditationCarteProfessionnelleId;
    }

    public void setAccreditationCarteProfessionnelleId(StringFilter accreditationCarteProfessionnelleId) {
        this.accreditationCarteProfessionnelleId = accreditationCarteProfessionnelleId;
    }

    public ZonedDateTimeFilter getAccreditationCreationDate() {
        return accreditationCreationDate;
    }

    public ZonedDateTimeFilter accreditationCreationDate() {
        if (accreditationCreationDate == null) {
            accreditationCreationDate = new ZonedDateTimeFilter();
        }
        return accreditationCreationDate;
    }

    public void setAccreditationCreationDate(ZonedDateTimeFilter accreditationCreationDate) {
        this.accreditationCreationDate = accreditationCreationDate;
    }

    public ZonedDateTimeFilter getAccreditationUpdateDate() {
        return accreditationUpdateDate;
    }

    public ZonedDateTimeFilter accreditationUpdateDate() {
        if (accreditationUpdateDate == null) {
            accreditationUpdateDate = new ZonedDateTimeFilter();
        }
        return accreditationUpdateDate;
    }

    public void setAccreditationUpdateDate(ZonedDateTimeFilter accreditationUpdateDate) {
        this.accreditationUpdateDate = accreditationUpdateDate;
    }

    public StringFilter getAccreditationCreatedByuser() {
        return accreditationCreatedByuser;
    }

    public StringFilter accreditationCreatedByuser() {
        if (accreditationCreatedByuser == null) {
            accreditationCreatedByuser = new StringFilter();
        }
        return accreditationCreatedByuser;
    }

    public void setAccreditationCreatedByuser(StringFilter accreditationCreatedByuser) {
        this.accreditationCreatedByuser = accreditationCreatedByuser;
    }

    public ZonedDateTimeFilter getAccreditationDateStart() {
        return accreditationDateStart;
    }

    public ZonedDateTimeFilter accreditationDateStart() {
        if (accreditationDateStart == null) {
            accreditationDateStart = new ZonedDateTimeFilter();
        }
        return accreditationDateStart;
    }

    public void setAccreditationDateStart(ZonedDateTimeFilter accreditationDateStart) {
        this.accreditationDateStart = accreditationDateStart;
    }

    public ZonedDateTimeFilter getAccreditationDateEnd() {
        return accreditationDateEnd;
    }

    public ZonedDateTimeFilter accreditationDateEnd() {
        if (accreditationDateEnd == null) {
            accreditationDateEnd = new ZonedDateTimeFilter();
        }
        return accreditationDateEnd;
    }

    public void setAccreditationDateEnd(ZonedDateTimeFilter accreditationDateEnd) {
        this.accreditationDateEnd = accreditationDateEnd;
    }

    public BooleanFilter getAccreditationPrintStat() {
        return accreditationPrintStat;
    }

    public BooleanFilter accreditationPrintStat() {
        if (accreditationPrintStat == null) {
            accreditationPrintStat = new BooleanFilter();
        }
        return accreditationPrintStat;
    }

    public void setAccreditationPrintStat(BooleanFilter accreditationPrintStat) {
        this.accreditationPrintStat = accreditationPrintStat;
    }

    public LongFilter getAccreditationPrintNumber() {
        return accreditationPrintNumber;
    }

    public LongFilter accreditationPrintNumber() {
        if (accreditationPrintNumber == null) {
            accreditationPrintNumber = new LongFilter();
        }
        return accreditationPrintNumber;
    }

    public void setAccreditationPrintNumber(LongFilter accreditationPrintNumber) {
        this.accreditationPrintNumber = accreditationPrintNumber;
    }

    public StringFilter getAccreditationParams() {
        return accreditationParams;
    }

    public StringFilter accreditationParams() {
        if (accreditationParams == null) {
            accreditationParams = new StringFilter();
        }
        return accreditationParams;
    }

    public void setAccreditationParams(StringFilter accreditationParams) {
        this.accreditationParams = accreditationParams;
    }

    public StringFilter getAccreditationAttributs() {
        return accreditationAttributs;
    }

    public StringFilter accreditationAttributs() {
        if (accreditationAttributs == null) {
            accreditationAttributs = new StringFilter();
        }
        return accreditationAttributs;
    }

    public void setAccreditationAttributs(StringFilter accreditationAttributs) {
        this.accreditationAttributs = accreditationAttributs;
    }

    public BooleanFilter getAccreditationStat() {
        return accreditationStat;
    }

    public BooleanFilter accreditationStat() {
        if (accreditationStat == null) {
            accreditationStat = new BooleanFilter();
        }
        return accreditationStat;
    }

    public void setAccreditationStat(BooleanFilter accreditationStat) {
        this.accreditationStat = accreditationStat;
    }

    public BooleanFilter getAccreditationActivated() {
        return accreditationActivated;
    }

    public BooleanFilter accreditationActivated() {
        if (accreditationActivated == null) {
            accreditationActivated = new BooleanFilter();
        }
        return accreditationActivated;
    }

    public void setAccreditationActivated(BooleanFilter accreditationActivated) {
        this.accreditationActivated = accreditationActivated;
    }

    public LongFilter getPhotoArchiveId() {
        return photoArchiveId;
    }

    public LongFilter photoArchiveId() {
        if (photoArchiveId == null) {
            photoArchiveId = new LongFilter();
        }
        return photoArchiveId;
    }

    public void setPhotoArchiveId(LongFilter photoArchiveId) {
        this.photoArchiveId = photoArchiveId;
    }

    public LongFilter getInfoSuppId() {
        return infoSuppId;
    }

    public LongFilter infoSuppId() {
        if (infoSuppId == null) {
            infoSuppId = new LongFilter();
        }
        return infoSuppId;
    }

    public void setInfoSuppId(LongFilter infoSuppId) {
        this.infoSuppId = infoSuppId;
    }

    public LongFilter getNoteId() {
        return noteId;
    }

    public LongFilter noteId() {
        if (noteId == null) {
            noteId = new LongFilter();
        }
        return noteId;
    }

    public void setNoteId(LongFilter noteId) {
        this.noteId = noteId;
    }

    public LongFilter getCheckAccreditationHistoryId() {
        return checkAccreditationHistoryId;
    }

    public LongFilter checkAccreditationHistoryId() {
        if (checkAccreditationHistoryId == null) {
            checkAccreditationHistoryId = new LongFilter();
        }
        return checkAccreditationHistoryId;
    }

    public void setCheckAccreditationHistoryId(LongFilter checkAccreditationHistoryId) {
        this.checkAccreditationHistoryId = checkAccreditationHistoryId;
    }

    public LongFilter getSiteId() {
        return siteId;
    }

    public LongFilter siteId() {
        if (siteId == null) {
            siteId = new LongFilter();
        }
        return siteId;
    }

    public void setSiteId(LongFilter siteId) {
        this.siteId = siteId;
    }

    public LongFilter getEventId() {
        return eventId;
    }

    public LongFilter eventId() {
        if (eventId == null) {
            eventId = new LongFilter();
        }
        return eventId;
    }

    public void setEventId(LongFilter eventId) {
        this.eventId = eventId;
    }

    public LongFilter getCivilityId() {
        return civilityId;
    }

    public LongFilter civilityId() {
        if (civilityId == null) {
            civilityId = new LongFilter();
        }
        return civilityId;
    }

    public void setCivilityId(LongFilter civilityId) {
        this.civilityId = civilityId;
    }

    public LongFilter getSexeId() {
        return sexeId;
    }

    public LongFilter sexeId() {
        if (sexeId == null) {
            sexeId = new LongFilter();
        }
        return sexeId;
    }

    public void setSexeId(LongFilter sexeId) {
        this.sexeId = sexeId;
    }

    public LongFilter getNationalityId() {
        return nationalityId;
    }

    public LongFilter nationalityId() {
        if (nationalityId == null) {
            nationalityId = new LongFilter();
        }
        return nationalityId;
    }

    public void setNationalityId(LongFilter nationalityId) {
        this.nationalityId = nationalityId;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public LongFilter countryId() {
        if (countryId == null) {
            countryId = new LongFilter();
        }
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    public LongFilter getCityId() {
        return cityId;
    }

    public LongFilter cityId() {
        if (cityId == null) {
            cityId = new LongFilter();
        }
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public LongFilter categoryId() {
        if (categoryId == null) {
            categoryId = new LongFilter();
        }
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getFonctionId() {
        return fonctionId;
    }

    public LongFilter fonctionId() {
        if (fonctionId == null) {
            fonctionId = new LongFilter();
        }
        return fonctionId;
    }

    public void setFonctionId(LongFilter fonctionId) {
        this.fonctionId = fonctionId;
    }

    public LongFilter getOrganizId() {
        return organizId;
    }

    public LongFilter organizId() {
        if (organizId == null) {
            organizId = new LongFilter();
        }
        return organizId;
    }

    public void setOrganizId(LongFilter organizId) {
        this.organizId = organizId;
    }

    public LongFilter getAccreditationTypeId() {
        return accreditationTypeId;
    }

    public LongFilter accreditationTypeId() {
        if (accreditationTypeId == null) {
            accreditationTypeId = new LongFilter();
        }
        return accreditationTypeId;
    }

    public void setAccreditationTypeId(LongFilter accreditationTypeId) {
        this.accreditationTypeId = accreditationTypeId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public LongFilter statusId() {
        if (statusId == null) {
            statusId = new LongFilter();
        }
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getAttachementId() {
        return attachementId;
    }

    public LongFilter attachementId() {
        if (attachementId == null) {
            attachementId = new LongFilter();
        }
        return attachementId;
    }

    public void setAttachementId(LongFilter attachementId) {
        this.attachementId = attachementId;
    }

    public LongFilter getCodeId() {
        return codeId;
    }

    public LongFilter codeId() {
        if (codeId == null) {
            codeId = new LongFilter();
        }
        return codeId;
    }

    public void setCodeId(LongFilter codeId) {
        this.codeId = codeId;
    }

    public LongFilter getDayPassInfoId() {
        return dayPassInfoId;
    }

    public LongFilter dayPassInfoId() {
        if (dayPassInfoId == null) {
            dayPassInfoId = new LongFilter();
        }
        return dayPassInfoId;
    }

    public void setDayPassInfoId(LongFilter dayPassInfoId) {
        this.dayPassInfoId = dayPassInfoId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AccreditationCriteria that = (AccreditationCriteria) o;
        return (
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(accreditationFirstName, that.accreditationFirstName) &&
            Objects.equals(accreditationSecondName, that.accreditationSecondName) &&
            Objects.equals(accreditationLastName, that.accreditationLastName) &&
            Objects.equals(accreditationBirthDay, that.accreditationBirthDay) &&
            Objects.equals(accreditationSexe, that.accreditationSexe) &&
            Objects.equals(accreditationOccupation, that.accreditationOccupation) &&
            Objects.equals(accreditationDescription, that.accreditationDescription) &&
            Objects.equals(accreditationEmail, that.accreditationEmail) &&
            Objects.equals(accreditationTel, that.accreditationTel) &&
            Objects.equals(accreditationCinId, that.accreditationCinId) &&
            Objects.equals(accreditationPasseportId, that.accreditationPasseportId) &&
            Objects.equals(accreditationCartePresseId, that.accreditationCartePresseId) &&
            Objects.equals(accreditationCarteProfessionnelleId, that.accreditationCarteProfessionnelleId) &&
            Objects.equals(accreditationCreationDate, that.accreditationCreationDate) &&
            Objects.equals(accreditationUpdateDate, that.accreditationUpdateDate) &&
            Objects.equals(accreditationCreatedByuser, that.accreditationCreatedByuser) &&
            Objects.equals(accreditationDateStart, that.accreditationDateStart) &&
            Objects.equals(accreditationDateEnd, that.accreditationDateEnd) &&
            Objects.equals(accreditationPrintStat, that.accreditationPrintStat) &&
            Objects.equals(accreditationPrintNumber, that.accreditationPrintNumber) &&
            Objects.equals(accreditationParams, that.accreditationParams) &&
            Objects.equals(accreditationAttributs, that.accreditationAttributs) &&
            Objects.equals(accreditationStat, that.accreditationStat) &&
            Objects.equals(accreditationActivated, that.accreditationActivated) &&
            Objects.equals(photoArchiveId, that.photoArchiveId) &&
            Objects.equals(infoSuppId, that.infoSuppId) &&
            Objects.equals(noteId, that.noteId) &&
            Objects.equals(checkAccreditationHistoryId, that.checkAccreditationHistoryId) &&
            Objects.equals(siteId, that.siteId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(civilityId, that.civilityId) &&
            Objects.equals(sexeId, that.sexeId) &&
            Objects.equals(nationalityId, that.nationalityId) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(fonctionId, that.fonctionId) &&
            Objects.equals(organizId, that.organizId) &&
            Objects.equals(accreditationTypeId, that.accreditationTypeId) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(attachementId, that.attachementId) &&
            Objects.equals(codeId, that.codeId) &&
            Objects.equals(dayPassInfoId, that.dayPassInfoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            accreditationId,
            accreditationFirstName,
            accreditationSecondName,
            accreditationLastName,
            accreditationBirthDay,
            accreditationSexe,
            accreditationOccupation,
            accreditationDescription,
            accreditationEmail,
            accreditationTel,
            accreditationCinId,
            accreditationPasseportId,
            accreditationCartePresseId,
            accreditationCarteProfessionnelleId,
            accreditationCreationDate,
            accreditationUpdateDate,
            accreditationCreatedByuser,
            accreditationDateStart,
            accreditationDateEnd,
            accreditationPrintStat,
            accreditationPrintNumber,
            accreditationParams,
            accreditationAttributs,
            accreditationStat,
            accreditationActivated,
            photoArchiveId,
            infoSuppId,
            noteId,
            checkAccreditationHistoryId,
            siteId,
            eventId,
            civilityId,
            sexeId,
            nationalityId,
            countryId,
            cityId,
            categoryId,
            fonctionId,
            organizId,
            accreditationTypeId,
            statusId,
            attachementId,
            codeId,
            dayPassInfoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccreditationCriteria{" +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (accreditationFirstName != null ? "accreditationFirstName=" + accreditationFirstName + ", " : "") +
            (accreditationSecondName != null ? "accreditationSecondName=" + accreditationSecondName + ", " : "") +
            (accreditationLastName != null ? "accreditationLastName=" + accreditationLastName + ", " : "") +
            (accreditationBirthDay != null ? "accreditationBirthDay=" + accreditationBirthDay + ", " : "") +
            (accreditationSexe != null ? "accreditationSexe=" + accreditationSexe + ", " : "") +
            (accreditationOccupation != null ? "accreditationOccupation=" + accreditationOccupation + ", " : "") +
            (accreditationDescription != null ? "accreditationDescription=" + accreditationDescription + ", " : "") +
            (accreditationEmail != null ? "accreditationEmail=" + accreditationEmail + ", " : "") +
            (accreditationTel != null ? "accreditationTel=" + accreditationTel + ", " : "") +
            (accreditationCinId != null ? "accreditationCinId=" + accreditationCinId + ", " : "") +
            (accreditationPasseportId != null ? "accreditationPasseportId=" + accreditationPasseportId + ", " : "") +
            (accreditationCartePresseId != null ? "accreditationCartePresseId=" + accreditationCartePresseId + ", " : "") +
            (accreditationCarteProfessionnelleId != null ? "accreditationCarteProfessionnelleId=" + accreditationCarteProfessionnelleId + ", " : "") +
            (accreditationCreationDate != null ? "accreditationCreationDate=" + accreditationCreationDate + ", " : "") +
            (accreditationUpdateDate != null ? "accreditationUpdateDate=" + accreditationUpdateDate + ", " : "") +
            (accreditationCreatedByuser != null ? "accreditationCreatedByuser=" + accreditationCreatedByuser + ", " : "") +
            (accreditationDateStart != null ? "accreditationDateStart=" + accreditationDateStart + ", " : "") +
            (accreditationDateEnd != null ? "accreditationDateEnd=" + accreditationDateEnd + ", " : "") +
            (accreditationPrintStat != null ? "accreditationPrintStat=" + accreditationPrintStat + ", " : "") +
            (accreditationPrintNumber != null ? "accreditationPrintNumber=" + accreditationPrintNumber + ", " : "") +
            (accreditationParams != null ? "accreditationParams=" + accreditationParams + ", " : "") +
            (accreditationAttributs != null ? "accreditationAttributs=" + accreditationAttributs + ", " : "") +
            (accreditationStat != null ? "accreditationStat=" + accreditationStat + ", " : "") +
            (accreditationActivated != null ? "accreditationActivated=" + accreditationActivated + ", " : "") +
            (photoArchiveId != null ? "photoArchiveId=" + photoArchiveId + ", " : "") +
            (infoSuppId != null ? "infoSuppId=" + infoSuppId + ", " : "") +
            (noteId != null ? "noteId=" + noteId + ", " : "") +
            (checkAccreditationHistoryId != null ? "checkAccreditationHistoryId=" + checkAccreditationHistoryId + ", " : "") +
            (siteId != null ? "siteId=" + siteId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (civilityId != null ? "civilityId=" + civilityId + ", " : "") +
            (sexeId != null ? "sexeId=" + sexeId + ", " : "") +
            (nationalityId != null ? "nationalityId=" + nationalityId + ", " : "") +
            (countryId != null ? "countryId=" + countryId + ", " : "") +
            (cityId != null ? "cityId=" + cityId + ", " : "") +
            (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            (fonctionId != null ? "fonctionId=" + fonctionId + ", " : "") +
            (organizId != null ? "organizId=" + organizId + ", " : "") +
            (accreditationTypeId != null ? "accreditationTypeId=" + accreditationTypeId + ", " : "") +
            (statusId != null ? "statusId=" + statusId + ", " : "") +
            (attachementId != null ? "attachementId=" + attachementId + ", " : "") +
            (codeId != null ? "codeId=" + codeId + ", " : "") +
            (dayPassInfoId != null ? "dayPassInfoId=" + dayPassInfoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

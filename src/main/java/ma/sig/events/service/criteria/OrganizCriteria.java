package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Organiz} entity. This class is used
 * in {@link ma.sig.events.web.rest.OrganizResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /organizs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter organizId;

    private StringFilter organizName;

    private StringFilter organizDescription;

    private StringFilter organizTel;

    private StringFilter organizFax;

    private StringFilter organizEmail;

    private StringFilter organizAdresse;

    private StringFilter organizParams;

    private StringFilter organizAttributs;

    private BooleanFilter organizStat;

    private LongFilter printingCentreId;

    private LongFilter accreditationId;

    private LongFilter countryId;

    private LongFilter cityId;

    private LongFilter eventId;

    private Boolean distinct;

    public OrganizCriteria() {}

    public OrganizCriteria(OrganizCriteria other) {
        this.organizId = other.organizId == null ? null : other.organizId.copy();
        this.organizName = other.organizName == null ? null : other.organizName.copy();
        this.organizDescription = other.organizDescription == null ? null : other.organizDescription.copy();
        this.organizTel = other.organizTel == null ? null : other.organizTel.copy();
        this.organizFax = other.organizFax == null ? null : other.organizFax.copy();
        this.organizEmail = other.organizEmail == null ? null : other.organizEmail.copy();
        this.organizAdresse = other.organizAdresse == null ? null : other.organizAdresse.copy();
        this.organizParams = other.organizParams == null ? null : other.organizParams.copy();
        this.organizAttributs = other.organizAttributs == null ? null : other.organizAttributs.copy();
        this.organizStat = other.organizStat == null ? null : other.organizStat.copy();
        this.printingCentreId = other.printingCentreId == null ? null : other.printingCentreId.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OrganizCriteria copy() {
        return new OrganizCriteria(this);
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

    public StringFilter getOrganizName() {
        return organizName;
    }

    public StringFilter organizName() {
        if (organizName == null) {
            organizName = new StringFilter();
        }
        return organizName;
    }

    public void setOrganizName(StringFilter organizName) {
        this.organizName = organizName;
    }

    public StringFilter getOrganizDescription() {
        return organizDescription;
    }

    public StringFilter organizDescription() {
        if (organizDescription == null) {
            organizDescription = new StringFilter();
        }
        return organizDescription;
    }

    public void setOrganizDescription(StringFilter organizDescription) {
        this.organizDescription = organizDescription;
    }

    public StringFilter getOrganizTel() {
        return organizTel;
    }

    public StringFilter organizTel() {
        if (organizTel == null) {
            organizTel = new StringFilter();
        }
        return organizTel;
    }

    public void setOrganizTel(StringFilter organizTel) {
        this.organizTel = organizTel;
    }

    public StringFilter getOrganizFax() {
        return organizFax;
    }

    public StringFilter organizFax() {
        if (organizFax == null) {
            organizFax = new StringFilter();
        }
        return organizFax;
    }

    public void setOrganizFax(StringFilter organizFax) {
        this.organizFax = organizFax;
    }

    public StringFilter getOrganizEmail() {
        return organizEmail;
    }

    public StringFilter organizEmail() {
        if (organizEmail == null) {
            organizEmail = new StringFilter();
        }
        return organizEmail;
    }

    public void setOrganizEmail(StringFilter organizEmail) {
        this.organizEmail = organizEmail;
    }

    public StringFilter getOrganizAdresse() {
        return organizAdresse;
    }

    public StringFilter organizAdresse() {
        if (organizAdresse == null) {
            organizAdresse = new StringFilter();
        }
        return organizAdresse;
    }

    public void setOrganizAdresse(StringFilter organizAdresse) {
        this.organizAdresse = organizAdresse;
    }

    public StringFilter getOrganizParams() {
        return organizParams;
    }

    public StringFilter organizParams() {
        if (organizParams == null) {
            organizParams = new StringFilter();
        }
        return organizParams;
    }

    public void setOrganizParams(StringFilter organizParams) {
        this.organizParams = organizParams;
    }

    public StringFilter getOrganizAttributs() {
        return organizAttributs;
    }

    public StringFilter organizAttributs() {
        if (organizAttributs == null) {
            organizAttributs = new StringFilter();
        }
        return organizAttributs;
    }

    public void setOrganizAttributs(StringFilter organizAttributs) {
        this.organizAttributs = organizAttributs;
    }

    public BooleanFilter getOrganizStat() {
        return organizStat;
    }

    public BooleanFilter organizStat() {
        if (organizStat == null) {
            organizStat = new BooleanFilter();
        }
        return organizStat;
    }

    public void setOrganizStat(BooleanFilter organizStat) {
        this.organizStat = organizStat;
    }

    public LongFilter getPrintingCentreId() {
        return printingCentreId;
    }

    public LongFilter printingCentreId() {
        if (printingCentreId == null) {
            printingCentreId = new LongFilter();
        }
        return printingCentreId;
    }

    public void setPrintingCentreId(LongFilter printingCentreId) {
        this.printingCentreId = printingCentreId;
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
        final OrganizCriteria that = (OrganizCriteria) o;
        return (
            Objects.equals(organizId, that.organizId) &&
            Objects.equals(organizName, that.organizName) &&
            Objects.equals(organizDescription, that.organizDescription) &&
            Objects.equals(organizTel, that.organizTel) &&
            Objects.equals(organizFax, that.organizFax) &&
            Objects.equals(organizEmail, that.organizEmail) &&
            Objects.equals(organizAdresse, that.organizAdresse) &&
            Objects.equals(organizParams, that.organizParams) &&
            Objects.equals(organizAttributs, that.organizAttributs) &&
            Objects.equals(organizStat, that.organizStat) &&
            Objects.equals(printingCentreId, that.printingCentreId) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            organizId,
            organizName,
            organizDescription,
            organizTel,
            organizFax,
            organizEmail,
            organizAdresse,
            organizParams,
            organizAttributs,
            organizStat,
            printingCentreId,
            accreditationId,
            countryId,
            cityId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizCriteria{" +
            (organizId != null ? "organizId=" + organizId + ", " : "") +
            (organizName != null ? "organizName=" + organizName + ", " : "") +
            (organizDescription != null ? "organizDescription=" + organizDescription + ", " : "") +
            (organizTel != null ? "organizTel=" + organizTel + ", " : "") +
            (organizFax != null ? "organizFax=" + organizFax + ", " : "") +
            (organizEmail != null ? "organizEmail=" + organizEmail + ", " : "") +
            (organizAdresse != null ? "organizAdresse=" + organizAdresse + ", " : "") +
            (organizParams != null ? "organizParams=" + organizParams + ", " : "") +
            (organizAttributs != null ? "organizAttributs=" + organizAttributs + ", " : "") +
            (organizStat != null ? "organizStat=" + organizStat + ", " : "") +
            (printingCentreId != null ? "printingCentreId=" + printingCentreId + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (countryId != null ? "countryId=" + countryId + ", " : "") +
            (cityId != null ? "cityId=" + cityId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

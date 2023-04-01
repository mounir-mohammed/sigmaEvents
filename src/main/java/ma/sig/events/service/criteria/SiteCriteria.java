package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Site} entity. This class is used
 * in {@link ma.sig.events.web.rest.SiteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sites?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SiteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter siteId;

    private StringFilter siteName;

    private StringFilter siteColor;

    private StringFilter siteAbreviation;

    private StringFilter siteDescription;

    private StringFilter siteAdresse;

    private StringFilter siteEmail;

    private StringFilter siteTel;

    private StringFilter siteFax;

    private StringFilter siteResponsableName;

    private StringFilter siteParams;

    private StringFilter siteAttributs;

    private BooleanFilter siteStat;

    private LongFilter cityId;

    private LongFilter eventId;

    private LongFilter accreditationId;

    private Boolean distinct;

    public SiteCriteria() {}

    public SiteCriteria(SiteCriteria other) {
        this.siteId = other.siteId == null ? null : other.siteId.copy();
        this.siteName = other.siteName == null ? null : other.siteName.copy();
        this.siteColor = other.siteColor == null ? null : other.siteColor.copy();
        this.siteAbreviation = other.siteAbreviation == null ? null : other.siteAbreviation.copy();
        this.siteDescription = other.siteDescription == null ? null : other.siteDescription.copy();
        this.siteAdresse = other.siteAdresse == null ? null : other.siteAdresse.copy();
        this.siteEmail = other.siteEmail == null ? null : other.siteEmail.copy();
        this.siteTel = other.siteTel == null ? null : other.siteTel.copy();
        this.siteFax = other.siteFax == null ? null : other.siteFax.copy();
        this.siteResponsableName = other.siteResponsableName == null ? null : other.siteResponsableName.copy();
        this.siteParams = other.siteParams == null ? null : other.siteParams.copy();
        this.siteAttributs = other.siteAttributs == null ? null : other.siteAttributs.copy();
        this.siteStat = other.siteStat == null ? null : other.siteStat.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SiteCriteria copy() {
        return new SiteCriteria(this);
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

    public StringFilter getSiteName() {
        return siteName;
    }

    public StringFilter siteName() {
        if (siteName == null) {
            siteName = new StringFilter();
        }
        return siteName;
    }

    public void setSiteName(StringFilter siteName) {
        this.siteName = siteName;
    }

    public StringFilter getSiteColor() {
        return siteColor;
    }

    public StringFilter siteColor() {
        if (siteColor == null) {
            siteColor = new StringFilter();
        }
        return siteColor;
    }

    public void setSiteColor(StringFilter siteColor) {
        this.siteColor = siteColor;
    }

    public StringFilter getSiteAbreviation() {
        return siteAbreviation;
    }

    public StringFilter siteAbreviation() {
        if (siteAbreviation == null) {
            siteAbreviation = new StringFilter();
        }
        return siteAbreviation;
    }

    public void setSiteAbreviation(StringFilter siteAbreviation) {
        this.siteAbreviation = siteAbreviation;
    }

    public StringFilter getSiteDescription() {
        return siteDescription;
    }

    public StringFilter siteDescription() {
        if (siteDescription == null) {
            siteDescription = new StringFilter();
        }
        return siteDescription;
    }

    public void setSiteDescription(StringFilter siteDescription) {
        this.siteDescription = siteDescription;
    }

    public StringFilter getSiteAdresse() {
        return siteAdresse;
    }

    public StringFilter siteAdresse() {
        if (siteAdresse == null) {
            siteAdresse = new StringFilter();
        }
        return siteAdresse;
    }

    public void setSiteAdresse(StringFilter siteAdresse) {
        this.siteAdresse = siteAdresse;
    }

    public StringFilter getSiteEmail() {
        return siteEmail;
    }

    public StringFilter siteEmail() {
        if (siteEmail == null) {
            siteEmail = new StringFilter();
        }
        return siteEmail;
    }

    public void setSiteEmail(StringFilter siteEmail) {
        this.siteEmail = siteEmail;
    }

    public StringFilter getSiteTel() {
        return siteTel;
    }

    public StringFilter siteTel() {
        if (siteTel == null) {
            siteTel = new StringFilter();
        }
        return siteTel;
    }

    public void setSiteTel(StringFilter siteTel) {
        this.siteTel = siteTel;
    }

    public StringFilter getSiteFax() {
        return siteFax;
    }

    public StringFilter siteFax() {
        if (siteFax == null) {
            siteFax = new StringFilter();
        }
        return siteFax;
    }

    public void setSiteFax(StringFilter siteFax) {
        this.siteFax = siteFax;
    }

    public StringFilter getSiteResponsableName() {
        return siteResponsableName;
    }

    public StringFilter siteResponsableName() {
        if (siteResponsableName == null) {
            siteResponsableName = new StringFilter();
        }
        return siteResponsableName;
    }

    public void setSiteResponsableName(StringFilter siteResponsableName) {
        this.siteResponsableName = siteResponsableName;
    }

    public StringFilter getSiteParams() {
        return siteParams;
    }

    public StringFilter siteParams() {
        if (siteParams == null) {
            siteParams = new StringFilter();
        }
        return siteParams;
    }

    public void setSiteParams(StringFilter siteParams) {
        this.siteParams = siteParams;
    }

    public StringFilter getSiteAttributs() {
        return siteAttributs;
    }

    public StringFilter siteAttributs() {
        if (siteAttributs == null) {
            siteAttributs = new StringFilter();
        }
        return siteAttributs;
    }

    public void setSiteAttributs(StringFilter siteAttributs) {
        this.siteAttributs = siteAttributs;
    }

    public BooleanFilter getSiteStat() {
        return siteStat;
    }

    public BooleanFilter siteStat() {
        if (siteStat == null) {
            siteStat = new BooleanFilter();
        }
        return siteStat;
    }

    public void setSiteStat(BooleanFilter siteStat) {
        this.siteStat = siteStat;
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
        final SiteCriteria that = (SiteCriteria) o;
        return (
            Objects.equals(siteId, that.siteId) &&
            Objects.equals(siteName, that.siteName) &&
            Objects.equals(siteColor, that.siteColor) &&
            Objects.equals(siteAbreviation, that.siteAbreviation) &&
            Objects.equals(siteDescription, that.siteDescription) &&
            Objects.equals(siteAdresse, that.siteAdresse) &&
            Objects.equals(siteEmail, that.siteEmail) &&
            Objects.equals(siteTel, that.siteTel) &&
            Objects.equals(siteFax, that.siteFax) &&
            Objects.equals(siteResponsableName, that.siteResponsableName) &&
            Objects.equals(siteParams, that.siteParams) &&
            Objects.equals(siteAttributs, that.siteAttributs) &&
            Objects.equals(siteStat, that.siteStat) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            siteId,
            siteName,
            siteColor,
            siteAbreviation,
            siteDescription,
            siteAdresse,
            siteEmail,
            siteTel,
            siteFax,
            siteResponsableName,
            siteParams,
            siteAttributs,
            siteStat,
            cityId,
            eventId,
            accreditationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteCriteria{" +
            (siteId != null ? "siteId=" + siteId + ", " : "") +
            (siteName != null ? "siteName=" + siteName + ", " : "") +
            (siteColor != null ? "siteColor=" + siteColor + ", " : "") +
            (siteAbreviation != null ? "siteAbreviation=" + siteAbreviation + ", " : "") +
            (siteDescription != null ? "siteDescription=" + siteDescription + ", " : "") +
            (siteAdresse != null ? "siteAdresse=" + siteAdresse + ", " : "") +
            (siteEmail != null ? "siteEmail=" + siteEmail + ", " : "") +
            (siteTel != null ? "siteTel=" + siteTel + ", " : "") +
            (siteFax != null ? "siteFax=" + siteFax + ", " : "") +
            (siteResponsableName != null ? "siteResponsableName=" + siteResponsableName + ", " : "") +
            (siteParams != null ? "siteParams=" + siteParams + ", " : "") +
            (siteAttributs != null ? "siteAttributs=" + siteAttributs + ", " : "") +
            (siteStat != null ? "siteStat=" + siteStat + ", " : "") +
            (cityId != null ? "cityId=" + cityId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

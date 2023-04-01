package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.PrintingCentre} entity. This class is used
 * in {@link ma.sig.events.web.rest.PrintingCentreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /printing-centres?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrintingCentreCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter printingCentreId;

    private StringFilter printingCentreDescription;

    private StringFilter printingCentreName;

    private StringFilter printingCentreAdresse;

    private StringFilter printingCentreEmail;

    private StringFilter printingCentreTel;

    private StringFilter printingCentreFax;

    private StringFilter printingCentreResponsableName;

    private StringFilter printingParams;

    private StringFilter printingAttributs;

    private BooleanFilter printingCentreStat;

    private LongFilter cityId;

    private LongFilter countryId;

    private LongFilter organizId;

    private LongFilter printingTypeId;

    private LongFilter printingServerId;

    private LongFilter printingModelId;

    private LongFilter languageId;

    private LongFilter eventId;

    private Boolean distinct;

    public PrintingCentreCriteria() {}

    public PrintingCentreCriteria(PrintingCentreCriteria other) {
        this.printingCentreId = other.printingCentreId == null ? null : other.printingCentreId.copy();
        this.printingCentreDescription = other.printingCentreDescription == null ? null : other.printingCentreDescription.copy();
        this.printingCentreName = other.printingCentreName == null ? null : other.printingCentreName.copy();
        this.printingCentreAdresse = other.printingCentreAdresse == null ? null : other.printingCentreAdresse.copy();
        this.printingCentreEmail = other.printingCentreEmail == null ? null : other.printingCentreEmail.copy();
        this.printingCentreTel = other.printingCentreTel == null ? null : other.printingCentreTel.copy();
        this.printingCentreFax = other.printingCentreFax == null ? null : other.printingCentreFax.copy();
        this.printingCentreResponsableName =
            other.printingCentreResponsableName == null ? null : other.printingCentreResponsableName.copy();
        this.printingParams = other.printingParams == null ? null : other.printingParams.copy();
        this.printingAttributs = other.printingAttributs == null ? null : other.printingAttributs.copy();
        this.printingCentreStat = other.printingCentreStat == null ? null : other.printingCentreStat.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.organizId = other.organizId == null ? null : other.organizId.copy();
        this.printingTypeId = other.printingTypeId == null ? null : other.printingTypeId.copy();
        this.printingServerId = other.printingServerId == null ? null : other.printingServerId.copy();
        this.printingModelId = other.printingModelId == null ? null : other.printingModelId.copy();
        this.languageId = other.languageId == null ? null : other.languageId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PrintingCentreCriteria copy() {
        return new PrintingCentreCriteria(this);
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

    public StringFilter getPrintingCentreDescription() {
        return printingCentreDescription;
    }

    public StringFilter printingCentreDescription() {
        if (printingCentreDescription == null) {
            printingCentreDescription = new StringFilter();
        }
        return printingCentreDescription;
    }

    public void setPrintingCentreDescription(StringFilter printingCentreDescription) {
        this.printingCentreDescription = printingCentreDescription;
    }

    public StringFilter getPrintingCentreName() {
        return printingCentreName;
    }

    public StringFilter printingCentreName() {
        if (printingCentreName == null) {
            printingCentreName = new StringFilter();
        }
        return printingCentreName;
    }

    public void setPrintingCentreName(StringFilter printingCentreName) {
        this.printingCentreName = printingCentreName;
    }

    public StringFilter getPrintingCentreAdresse() {
        return printingCentreAdresse;
    }

    public StringFilter printingCentreAdresse() {
        if (printingCentreAdresse == null) {
            printingCentreAdresse = new StringFilter();
        }
        return printingCentreAdresse;
    }

    public void setPrintingCentreAdresse(StringFilter printingCentreAdresse) {
        this.printingCentreAdresse = printingCentreAdresse;
    }

    public StringFilter getPrintingCentreEmail() {
        return printingCentreEmail;
    }

    public StringFilter printingCentreEmail() {
        if (printingCentreEmail == null) {
            printingCentreEmail = new StringFilter();
        }
        return printingCentreEmail;
    }

    public void setPrintingCentreEmail(StringFilter printingCentreEmail) {
        this.printingCentreEmail = printingCentreEmail;
    }

    public StringFilter getPrintingCentreTel() {
        return printingCentreTel;
    }

    public StringFilter printingCentreTel() {
        if (printingCentreTel == null) {
            printingCentreTel = new StringFilter();
        }
        return printingCentreTel;
    }

    public void setPrintingCentreTel(StringFilter printingCentreTel) {
        this.printingCentreTel = printingCentreTel;
    }

    public StringFilter getPrintingCentreFax() {
        return printingCentreFax;
    }

    public StringFilter printingCentreFax() {
        if (printingCentreFax == null) {
            printingCentreFax = new StringFilter();
        }
        return printingCentreFax;
    }

    public void setPrintingCentreFax(StringFilter printingCentreFax) {
        this.printingCentreFax = printingCentreFax;
    }

    public StringFilter getPrintingCentreResponsableName() {
        return printingCentreResponsableName;
    }

    public StringFilter printingCentreResponsableName() {
        if (printingCentreResponsableName == null) {
            printingCentreResponsableName = new StringFilter();
        }
        return printingCentreResponsableName;
    }

    public void setPrintingCentreResponsableName(StringFilter printingCentreResponsableName) {
        this.printingCentreResponsableName = printingCentreResponsableName;
    }

    public StringFilter getPrintingParams() {
        return printingParams;
    }

    public StringFilter printingParams() {
        if (printingParams == null) {
            printingParams = new StringFilter();
        }
        return printingParams;
    }

    public void setPrintingParams(StringFilter printingParams) {
        this.printingParams = printingParams;
    }

    public StringFilter getPrintingAttributs() {
        return printingAttributs;
    }

    public StringFilter printingAttributs() {
        if (printingAttributs == null) {
            printingAttributs = new StringFilter();
        }
        return printingAttributs;
    }

    public void setPrintingAttributs(StringFilter printingAttributs) {
        this.printingAttributs = printingAttributs;
    }

    public BooleanFilter getPrintingCentreStat() {
        return printingCentreStat;
    }

    public BooleanFilter printingCentreStat() {
        if (printingCentreStat == null) {
            printingCentreStat = new BooleanFilter();
        }
        return printingCentreStat;
    }

    public void setPrintingCentreStat(BooleanFilter printingCentreStat) {
        this.printingCentreStat = printingCentreStat;
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

    public LongFilter getPrintingTypeId() {
        return printingTypeId;
    }

    public LongFilter printingTypeId() {
        if (printingTypeId == null) {
            printingTypeId = new LongFilter();
        }
        return printingTypeId;
    }

    public void setPrintingTypeId(LongFilter printingTypeId) {
        this.printingTypeId = printingTypeId;
    }

    public LongFilter getPrintingServerId() {
        return printingServerId;
    }

    public LongFilter printingServerId() {
        if (printingServerId == null) {
            printingServerId = new LongFilter();
        }
        return printingServerId;
    }

    public void setPrintingServerId(LongFilter printingServerId) {
        this.printingServerId = printingServerId;
    }

    public LongFilter getPrintingModelId() {
        return printingModelId;
    }

    public LongFilter printingModelId() {
        if (printingModelId == null) {
            printingModelId = new LongFilter();
        }
        return printingModelId;
    }

    public void setPrintingModelId(LongFilter printingModelId) {
        this.printingModelId = printingModelId;
    }

    public LongFilter getLanguageId() {
        return languageId;
    }

    public LongFilter languageId() {
        if (languageId == null) {
            languageId = new LongFilter();
        }
        return languageId;
    }

    public void setLanguageId(LongFilter languageId) {
        this.languageId = languageId;
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
        final PrintingCentreCriteria that = (PrintingCentreCriteria) o;
        return (
            Objects.equals(printingCentreId, that.printingCentreId) &&
            Objects.equals(printingCentreDescription, that.printingCentreDescription) &&
            Objects.equals(printingCentreName, that.printingCentreName) &&
            Objects.equals(printingCentreAdresse, that.printingCentreAdresse) &&
            Objects.equals(printingCentreEmail, that.printingCentreEmail) &&
            Objects.equals(printingCentreTel, that.printingCentreTel) &&
            Objects.equals(printingCentreFax, that.printingCentreFax) &&
            Objects.equals(printingCentreResponsableName, that.printingCentreResponsableName) &&
            Objects.equals(printingParams, that.printingParams) &&
            Objects.equals(printingAttributs, that.printingAttributs) &&
            Objects.equals(printingCentreStat, that.printingCentreStat) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(organizId, that.organizId) &&
            Objects.equals(printingTypeId, that.printingTypeId) &&
            Objects.equals(printingServerId, that.printingServerId) &&
            Objects.equals(printingModelId, that.printingModelId) &&
            Objects.equals(languageId, that.languageId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            printingCentreId,
            printingCentreDescription,
            printingCentreName,
            printingCentreAdresse,
            printingCentreEmail,
            printingCentreTel,
            printingCentreFax,
            printingCentreResponsableName,
            printingParams,
            printingAttributs,
            printingCentreStat,
            cityId,
            countryId,
            organizId,
            printingTypeId,
            printingServerId,
            printingModelId,
            languageId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrintingCentreCriteria{" +
            (printingCentreId != null ? "printingCentreId=" + printingCentreId + ", " : "") +
            (printingCentreDescription != null ? "printingCentreDescription=" + printingCentreDescription + ", " : "") +
            (printingCentreName != null ? "printingCentreName=" + printingCentreName + ", " : "") +
            (printingCentreAdresse != null ? "printingCentreAdresse=" + printingCentreAdresse + ", " : "") +
            (printingCentreEmail != null ? "printingCentreEmail=" + printingCentreEmail + ", " : "") +
            (printingCentreTel != null ? "printingCentreTel=" + printingCentreTel + ", " : "") +
            (printingCentreFax != null ? "printingCentreFax=" + printingCentreFax + ", " : "") +
            (printingCentreResponsableName != null ? "printingCentreResponsableName=" + printingCentreResponsableName + ", " : "") +
            (printingParams != null ? "printingParams=" + printingParams + ", " : "") +
            (printingAttributs != null ? "printingAttributs=" + printingAttributs + ", " : "") +
            (printingCentreStat != null ? "printingCentreStat=" + printingCentreStat + ", " : "") +
            (cityId != null ? "cityId=" + cityId + ", " : "") +
            (countryId != null ? "countryId=" + countryId + ", " : "") +
            (organizId != null ? "organizId=" + organizId + ", " : "") +
            (printingTypeId != null ? "printingTypeId=" + printingTypeId + ", " : "") +
            (printingServerId != null ? "printingServerId=" + printingServerId + ", " : "") +
            (printingModelId != null ? "printingModelId=" + printingModelId + ", " : "") +
            (languageId != null ? "languageId=" + languageId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

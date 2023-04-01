package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.PrintingModel} entity. This class is used
 * in {@link ma.sig.events.web.rest.PrintingModelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /printing-models?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrintingModelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter printingModelId;

    private StringFilter printingModelName;

    private StringFilter printingModelFile;

    private StringFilter printingModelPath;

    private StringFilter printingModelDescription;

    private StringFilter printingModelParams;

    private StringFilter printingModelAttributs;

    private BooleanFilter printingModelStat;

    private LongFilter printingCentreId;

    private LongFilter accreditationTypeId;

    private LongFilter categoryId;

    private LongFilter eventId;

    private Boolean distinct;

    public PrintingModelCriteria() {}

    public PrintingModelCriteria(PrintingModelCriteria other) {
        this.printingModelId = other.printingModelId == null ? null : other.printingModelId.copy();
        this.printingModelName = other.printingModelName == null ? null : other.printingModelName.copy();
        this.printingModelFile = other.printingModelFile == null ? null : other.printingModelFile.copy();
        this.printingModelPath = other.printingModelPath == null ? null : other.printingModelPath.copy();
        this.printingModelDescription = other.printingModelDescription == null ? null : other.printingModelDescription.copy();
        this.printingModelParams = other.printingModelParams == null ? null : other.printingModelParams.copy();
        this.printingModelAttributs = other.printingModelAttributs == null ? null : other.printingModelAttributs.copy();
        this.printingModelStat = other.printingModelStat == null ? null : other.printingModelStat.copy();
        this.printingCentreId = other.printingCentreId == null ? null : other.printingCentreId.copy();
        this.accreditationTypeId = other.accreditationTypeId == null ? null : other.accreditationTypeId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PrintingModelCriteria copy() {
        return new PrintingModelCriteria(this);
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

    public StringFilter getPrintingModelName() {
        return printingModelName;
    }

    public StringFilter printingModelName() {
        if (printingModelName == null) {
            printingModelName = new StringFilter();
        }
        return printingModelName;
    }

    public void setPrintingModelName(StringFilter printingModelName) {
        this.printingModelName = printingModelName;
    }

    public StringFilter getPrintingModelFile() {
        return printingModelFile;
    }

    public StringFilter printingModelFile() {
        if (printingModelFile == null) {
            printingModelFile = new StringFilter();
        }
        return printingModelFile;
    }

    public void setPrintingModelFile(StringFilter printingModelFile) {
        this.printingModelFile = printingModelFile;
    }

    public StringFilter getPrintingModelPath() {
        return printingModelPath;
    }

    public StringFilter printingModelPath() {
        if (printingModelPath == null) {
            printingModelPath = new StringFilter();
        }
        return printingModelPath;
    }

    public void setPrintingModelPath(StringFilter printingModelPath) {
        this.printingModelPath = printingModelPath;
    }

    public StringFilter getPrintingModelDescription() {
        return printingModelDescription;
    }

    public StringFilter printingModelDescription() {
        if (printingModelDescription == null) {
            printingModelDescription = new StringFilter();
        }
        return printingModelDescription;
    }

    public void setPrintingModelDescription(StringFilter printingModelDescription) {
        this.printingModelDescription = printingModelDescription;
    }

    public StringFilter getPrintingModelParams() {
        return printingModelParams;
    }

    public StringFilter printingModelParams() {
        if (printingModelParams == null) {
            printingModelParams = new StringFilter();
        }
        return printingModelParams;
    }

    public void setPrintingModelParams(StringFilter printingModelParams) {
        this.printingModelParams = printingModelParams;
    }

    public StringFilter getPrintingModelAttributs() {
        return printingModelAttributs;
    }

    public StringFilter printingModelAttributs() {
        if (printingModelAttributs == null) {
            printingModelAttributs = new StringFilter();
        }
        return printingModelAttributs;
    }

    public void setPrintingModelAttributs(StringFilter printingModelAttributs) {
        this.printingModelAttributs = printingModelAttributs;
    }

    public BooleanFilter getPrintingModelStat() {
        return printingModelStat;
    }

    public BooleanFilter printingModelStat() {
        if (printingModelStat == null) {
            printingModelStat = new BooleanFilter();
        }
        return printingModelStat;
    }

    public void setPrintingModelStat(BooleanFilter printingModelStat) {
        this.printingModelStat = printingModelStat;
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
        final PrintingModelCriteria that = (PrintingModelCriteria) o;
        return (
            Objects.equals(printingModelId, that.printingModelId) &&
            Objects.equals(printingModelName, that.printingModelName) &&
            Objects.equals(printingModelFile, that.printingModelFile) &&
            Objects.equals(printingModelPath, that.printingModelPath) &&
            Objects.equals(printingModelDescription, that.printingModelDescription) &&
            Objects.equals(printingModelParams, that.printingModelParams) &&
            Objects.equals(printingModelAttributs, that.printingModelAttributs) &&
            Objects.equals(printingModelStat, that.printingModelStat) &&
            Objects.equals(printingCentreId, that.printingCentreId) &&
            Objects.equals(accreditationTypeId, that.accreditationTypeId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            printingModelId,
            printingModelName,
            printingModelFile,
            printingModelPath,
            printingModelDescription,
            printingModelParams,
            printingModelAttributs,
            printingModelStat,
            printingCentreId,
            accreditationTypeId,
            categoryId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrintingModelCriteria{" +
            (printingModelId != null ? "printingModelId=" + printingModelId + ", " : "") +
            (printingModelName != null ? "printingModelName=" + printingModelName + ", " : "") +
            (printingModelFile != null ? "printingModelFile=" + printingModelFile + ", " : "") +
            (printingModelPath != null ? "printingModelPath=" + printingModelPath + ", " : "") +
            (printingModelDescription != null ? "printingModelDescription=" + printingModelDescription + ", " : "") +
            (printingModelParams != null ? "printingModelParams=" + printingModelParams + ", " : "") +
            (printingModelAttributs != null ? "printingModelAttributs=" + printingModelAttributs + ", " : "") +
            (printingModelStat != null ? "printingModelStat=" + printingModelStat + ", " : "") +
            (printingCentreId != null ? "printingCentreId=" + printingCentreId + ", " : "") +
            (accreditationTypeId != null ? "accreditationTypeId=" + accreditationTypeId + ", " : "") +
            (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.PrintingType} entity. This class is used
 * in {@link ma.sig.events.web.rest.PrintingTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /printing-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrintingTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter printingTypeId;

    private StringFilter printingTypeValue;

    private StringFilter printingTypeDescription;

    private StringFilter printingTypeParams;

    private StringFilter printingTypeAttributs;

    private BooleanFilter printingTypeStat;

    private LongFilter printingCentreId;

    private Boolean distinct;

    public PrintingTypeCriteria() {}

    public PrintingTypeCriteria(PrintingTypeCriteria other) {
        this.printingTypeId = other.printingTypeId == null ? null : other.printingTypeId.copy();
        this.printingTypeValue = other.printingTypeValue == null ? null : other.printingTypeValue.copy();
        this.printingTypeDescription = other.printingTypeDescription == null ? null : other.printingTypeDescription.copy();
        this.printingTypeParams = other.printingTypeParams == null ? null : other.printingTypeParams.copy();
        this.printingTypeAttributs = other.printingTypeAttributs == null ? null : other.printingTypeAttributs.copy();
        this.printingTypeStat = other.printingTypeStat == null ? null : other.printingTypeStat.copy();
        this.printingCentreId = other.printingCentreId == null ? null : other.printingCentreId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PrintingTypeCriteria copy() {
        return new PrintingTypeCriteria(this);
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

    public StringFilter getPrintingTypeValue() {
        return printingTypeValue;
    }

    public StringFilter printingTypeValue() {
        if (printingTypeValue == null) {
            printingTypeValue = new StringFilter();
        }
        return printingTypeValue;
    }

    public void setPrintingTypeValue(StringFilter printingTypeValue) {
        this.printingTypeValue = printingTypeValue;
    }

    public StringFilter getPrintingTypeDescription() {
        return printingTypeDescription;
    }

    public StringFilter printingTypeDescription() {
        if (printingTypeDescription == null) {
            printingTypeDescription = new StringFilter();
        }
        return printingTypeDescription;
    }

    public void setPrintingTypeDescription(StringFilter printingTypeDescription) {
        this.printingTypeDescription = printingTypeDescription;
    }

    public StringFilter getPrintingTypeParams() {
        return printingTypeParams;
    }

    public StringFilter printingTypeParams() {
        if (printingTypeParams == null) {
            printingTypeParams = new StringFilter();
        }
        return printingTypeParams;
    }

    public void setPrintingTypeParams(StringFilter printingTypeParams) {
        this.printingTypeParams = printingTypeParams;
    }

    public StringFilter getPrintingTypeAttributs() {
        return printingTypeAttributs;
    }

    public StringFilter printingTypeAttributs() {
        if (printingTypeAttributs == null) {
            printingTypeAttributs = new StringFilter();
        }
        return printingTypeAttributs;
    }

    public void setPrintingTypeAttributs(StringFilter printingTypeAttributs) {
        this.printingTypeAttributs = printingTypeAttributs;
    }

    public BooleanFilter getPrintingTypeStat() {
        return printingTypeStat;
    }

    public BooleanFilter printingTypeStat() {
        if (printingTypeStat == null) {
            printingTypeStat = new BooleanFilter();
        }
        return printingTypeStat;
    }

    public void setPrintingTypeStat(BooleanFilter printingTypeStat) {
        this.printingTypeStat = printingTypeStat;
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
        final PrintingTypeCriteria that = (PrintingTypeCriteria) o;
        return (
            Objects.equals(printingTypeId, that.printingTypeId) &&
            Objects.equals(printingTypeValue, that.printingTypeValue) &&
            Objects.equals(printingTypeDescription, that.printingTypeDescription) &&
            Objects.equals(printingTypeParams, that.printingTypeParams) &&
            Objects.equals(printingTypeAttributs, that.printingTypeAttributs) &&
            Objects.equals(printingTypeStat, that.printingTypeStat) &&
            Objects.equals(printingCentreId, that.printingCentreId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            printingTypeId,
            printingTypeValue,
            printingTypeDescription,
            printingTypeParams,
            printingTypeAttributs,
            printingTypeStat,
            printingCentreId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrintingTypeCriteria{" +
            (printingTypeId != null ? "printingTypeId=" + printingTypeId + ", " : "") +
            (printingTypeValue != null ? "printingTypeValue=" + printingTypeValue + ", " : "") +
            (printingTypeDescription != null ? "printingTypeDescription=" + printingTypeDescription + ", " : "") +
            (printingTypeParams != null ? "printingTypeParams=" + printingTypeParams + ", " : "") +
            (printingTypeAttributs != null ? "printingTypeAttributs=" + printingTypeAttributs + ", " : "") +
            (printingTypeStat != null ? "printingTypeStat=" + printingTypeStat + ", " : "") +
            (printingCentreId != null ? "printingCentreId=" + printingCentreId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

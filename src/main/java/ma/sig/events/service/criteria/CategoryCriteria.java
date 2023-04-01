package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Category} entity. This class is used
 * in {@link ma.sig.events.web.rest.CategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter categoryId;

    private StringFilter categoryName;

    private StringFilter categoryAbreviation;

    private StringFilter categoryColor;

    private StringFilter categoryDescription;

    private StringFilter categoryParams;

    private StringFilter categoryAttributs;

    private BooleanFilter categoryStat;

    private LongFilter fonctionId;

    private LongFilter accreditationId;

    private LongFilter printingModelId;

    private LongFilter eventId;

    private Boolean distinct;

    public CategoryCriteria() {}

    public CategoryCriteria(CategoryCriteria other) {
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.categoryName = other.categoryName == null ? null : other.categoryName.copy();
        this.categoryAbreviation = other.categoryAbreviation == null ? null : other.categoryAbreviation.copy();
        this.categoryColor = other.categoryColor == null ? null : other.categoryColor.copy();
        this.categoryDescription = other.categoryDescription == null ? null : other.categoryDescription.copy();
        this.categoryParams = other.categoryParams == null ? null : other.categoryParams.copy();
        this.categoryAttributs = other.categoryAttributs == null ? null : other.categoryAttributs.copy();
        this.categoryStat = other.categoryStat == null ? null : other.categoryStat.copy();
        this.fonctionId = other.fonctionId == null ? null : other.fonctionId.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.printingModelId = other.printingModelId == null ? null : other.printingModelId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CategoryCriteria copy() {
        return new CategoryCriteria(this);
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

    public StringFilter getCategoryName() {
        return categoryName;
    }

    public StringFilter categoryName() {
        if (categoryName == null) {
            categoryName = new StringFilter();
        }
        return categoryName;
    }

    public void setCategoryName(StringFilter categoryName) {
        this.categoryName = categoryName;
    }

    public StringFilter getCategoryAbreviation() {
        return categoryAbreviation;
    }

    public StringFilter categoryAbreviation() {
        if (categoryAbreviation == null) {
            categoryAbreviation = new StringFilter();
        }
        return categoryAbreviation;
    }

    public void setCategoryAbreviation(StringFilter categoryAbreviation) {
        this.categoryAbreviation = categoryAbreviation;
    }

    public StringFilter getCategoryColor() {
        return categoryColor;
    }

    public StringFilter categoryColor() {
        if (categoryColor == null) {
            categoryColor = new StringFilter();
        }
        return categoryColor;
    }

    public void setCategoryColor(StringFilter categoryColor) {
        this.categoryColor = categoryColor;
    }

    public StringFilter getCategoryDescription() {
        return categoryDescription;
    }

    public StringFilter categoryDescription() {
        if (categoryDescription == null) {
            categoryDescription = new StringFilter();
        }
        return categoryDescription;
    }

    public void setCategoryDescription(StringFilter categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public StringFilter getCategoryParams() {
        return categoryParams;
    }

    public StringFilter categoryParams() {
        if (categoryParams == null) {
            categoryParams = new StringFilter();
        }
        return categoryParams;
    }

    public void setCategoryParams(StringFilter categoryParams) {
        this.categoryParams = categoryParams;
    }

    public StringFilter getCategoryAttributs() {
        return categoryAttributs;
    }

    public StringFilter categoryAttributs() {
        if (categoryAttributs == null) {
            categoryAttributs = new StringFilter();
        }
        return categoryAttributs;
    }

    public void setCategoryAttributs(StringFilter categoryAttributs) {
        this.categoryAttributs = categoryAttributs;
    }

    public BooleanFilter getCategoryStat() {
        return categoryStat;
    }

    public BooleanFilter categoryStat() {
        if (categoryStat == null) {
            categoryStat = new BooleanFilter();
        }
        return categoryStat;
    }

    public void setCategoryStat(BooleanFilter categoryStat) {
        this.categoryStat = categoryStat;
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
        final CategoryCriteria that = (CategoryCriteria) o;
        return (
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(categoryName, that.categoryName) &&
            Objects.equals(categoryAbreviation, that.categoryAbreviation) &&
            Objects.equals(categoryColor, that.categoryColor) &&
            Objects.equals(categoryDescription, that.categoryDescription) &&
            Objects.equals(categoryParams, that.categoryParams) &&
            Objects.equals(categoryAttributs, that.categoryAttributs) &&
            Objects.equals(categoryStat, that.categoryStat) &&
            Objects.equals(fonctionId, that.fonctionId) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(printingModelId, that.printingModelId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            categoryId,
            categoryName,
            categoryAbreviation,
            categoryColor,
            categoryDescription,
            categoryParams,
            categoryAttributs,
            categoryStat,
            fonctionId,
            accreditationId,
            printingModelId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryCriteria{" +
            (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            (categoryName != null ? "categoryName=" + categoryName + ", " : "") +
            (categoryAbreviation != null ? "categoryAbreviation=" + categoryAbreviation + ", " : "") +
            (categoryColor != null ? "categoryColor=" + categoryColor + ", " : "") +
            (categoryDescription != null ? "categoryDescription=" + categoryDescription + ", " : "") +
            (categoryParams != null ? "categoryParams=" + categoryParams + ", " : "") +
            (categoryAttributs != null ? "categoryAttributs=" + categoryAttributs + ", " : "") +
            (categoryStat != null ? "categoryStat=" + categoryStat + ", " : "") +
            (fonctionId != null ? "fonctionId=" + fonctionId + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (printingModelId != null ? "printingModelId=" + printingModelId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

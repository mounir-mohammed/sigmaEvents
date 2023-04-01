package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Category} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoryDTO implements Serializable {

    private Long categoryId;

    @NotNull
    @Size(max = 50)
    private String categoryName;

    @Size(max = 10)
    private String categoryAbreviation;

    @Size(max = 100)
    private String categoryColor;

    @Size(max = 200)
    private String categoryDescription;

    @Lob
    private byte[] categoryLogo;

    private String categoryLogoContentType;
    private String categoryParams;

    private String categoryAttributs;

    private Boolean categoryStat;

    private PrintingModelDTO printingModel;

    private EventDTO event;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryAbreviation() {
        return categoryAbreviation;
    }

    public void setCategoryAbreviation(String categoryAbreviation) {
        this.categoryAbreviation = categoryAbreviation;
    }

    public String getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public byte[] getCategoryLogo() {
        return categoryLogo;
    }

    public void setCategoryLogo(byte[] categoryLogo) {
        this.categoryLogo = categoryLogo;
    }

    public String getCategoryLogoContentType() {
        return categoryLogoContentType;
    }

    public void setCategoryLogoContentType(String categoryLogoContentType) {
        this.categoryLogoContentType = categoryLogoContentType;
    }

    public String getCategoryParams() {
        return categoryParams;
    }

    public void setCategoryParams(String categoryParams) {
        this.categoryParams = categoryParams;
    }

    public String getCategoryAttributs() {
        return categoryAttributs;
    }

    public void setCategoryAttributs(String categoryAttributs) {
        this.categoryAttributs = categoryAttributs;
    }

    public Boolean getCategoryStat() {
        return categoryStat;
    }

    public void setCategoryStat(Boolean categoryStat) {
        this.categoryStat = categoryStat;
    }

    public PrintingModelDTO getPrintingModel() {
        return printingModel;
    }

    public void setPrintingModel(PrintingModelDTO printingModel) {
        this.printingModel = printingModel;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryDTO)) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;
        if (this.categoryId == null) {
            return false;
        }
        return Objects.equals(this.categoryId, categoryDTO.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.categoryId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryDTO{" +
            "categoryId=" + getCategoryId() +
            ", categoryName='" + getCategoryName() + "'" +
            ", categoryAbreviation='" + getCategoryAbreviation() + "'" +
            ", categoryColor='" + getCategoryColor() + "'" +
            ", categoryDescription='" + getCategoryDescription() + "'" +
            ", categoryLogo='" + getCategoryLogo() + "'" +
            ", categoryParams='" + getCategoryParams() + "'" +
            ", categoryAttributs='" + getCategoryAttributs() + "'" +
            ", categoryStat='" + getCategoryStat() + "'" +
            ", printingModel=" + getPrintingModel() +
            ", event=" + getEvent() +
            "}";
    }
}

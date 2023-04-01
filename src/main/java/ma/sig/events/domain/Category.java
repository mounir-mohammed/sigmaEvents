package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @NotNull
    @Size(max = 50)
    @Column(name = "category_name", length = 50, nullable = false)
    private String categoryName;

    @Size(max = 10)
    @Column(name = "category_abreviation", length = 10)
    private String categoryAbreviation;

    @Size(max = 100)
    @Column(name = "category_color", length = 100)
    private String categoryColor;

    @Size(max = 200)
    @Column(name = "category_description", length = 200)
    private String categoryDescription;

    @Lob
    @Column(name = "category_logo")
    private byte[] categoryLogo;

    @Column(name = "category_logo_content_type")
    private String categoryLogoContentType;

    @Column(name = "category_params")
    private String categoryParams;

    @Column(name = "category_attributs")
    private String categoryAttributs;

    @Column(name = "category_stat")
    private Boolean categoryStat;

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditations", "areas", "category", "event" }, allowSetters = true)
    private Set<Fonction> fonctions = new HashSet<>();

    @OneToMany(mappedBy = "category")
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
    @JsonIgnoreProperties(value = { "printingCentres", "accreditationTypes", "categories", "event" }, allowSetters = true)
    private PrintingModel printingModel;

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

    public Long getCategoryId() {
        return this.categoryId;
    }

    public Category categoryId(Long categoryId) {
        this.setCategoryId(categoryId);
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public Category categoryName(String categoryName) {
        this.setCategoryName(categoryName);
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryAbreviation() {
        return this.categoryAbreviation;
    }

    public Category categoryAbreviation(String categoryAbreviation) {
        this.setCategoryAbreviation(categoryAbreviation);
        return this;
    }

    public void setCategoryAbreviation(String categoryAbreviation) {
        this.categoryAbreviation = categoryAbreviation;
    }

    public String getCategoryColor() {
        return this.categoryColor;
    }

    public Category categoryColor(String categoryColor) {
        this.setCategoryColor(categoryColor);
        return this;
    }

    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }

    public String getCategoryDescription() {
        return this.categoryDescription;
    }

    public Category categoryDescription(String categoryDescription) {
        this.setCategoryDescription(categoryDescription);
        return this;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public byte[] getCategoryLogo() {
        return this.categoryLogo;
    }

    public Category categoryLogo(byte[] categoryLogo) {
        this.setCategoryLogo(categoryLogo);
        return this;
    }

    public void setCategoryLogo(byte[] categoryLogo) {
        this.categoryLogo = categoryLogo;
    }

    public String getCategoryLogoContentType() {
        return this.categoryLogoContentType;
    }

    public Category categoryLogoContentType(String categoryLogoContentType) {
        this.categoryLogoContentType = categoryLogoContentType;
        return this;
    }

    public void setCategoryLogoContentType(String categoryLogoContentType) {
        this.categoryLogoContentType = categoryLogoContentType;
    }

    public String getCategoryParams() {
        return this.categoryParams;
    }

    public Category categoryParams(String categoryParams) {
        this.setCategoryParams(categoryParams);
        return this;
    }

    public void setCategoryParams(String categoryParams) {
        this.categoryParams = categoryParams;
    }

    public String getCategoryAttributs() {
        return this.categoryAttributs;
    }

    public Category categoryAttributs(String categoryAttributs) {
        this.setCategoryAttributs(categoryAttributs);
        return this;
    }

    public void setCategoryAttributs(String categoryAttributs) {
        this.categoryAttributs = categoryAttributs;
    }

    public Boolean getCategoryStat() {
        return this.categoryStat;
    }

    public Category categoryStat(Boolean categoryStat) {
        this.setCategoryStat(categoryStat);
        return this;
    }

    public void setCategoryStat(Boolean categoryStat) {
        this.categoryStat = categoryStat;
    }

    public Set<Fonction> getFonctions() {
        return this.fonctions;
    }

    public void setFonctions(Set<Fonction> fonctions) {
        if (this.fonctions != null) {
            this.fonctions.forEach(i -> i.setCategory(null));
        }
        if (fonctions != null) {
            fonctions.forEach(i -> i.setCategory(this));
        }
        this.fonctions = fonctions;
    }

    public Category fonctions(Set<Fonction> fonctions) {
        this.setFonctions(fonctions);
        return this;
    }

    public Category addFonction(Fonction fonction) {
        this.fonctions.add(fonction);
        fonction.setCategory(this);
        return this;
    }

    public Category removeFonction(Fonction fonction) {
        this.fonctions.remove(fonction);
        fonction.setCategory(null);
        return this;
    }

    public Set<Accreditation> getAccreditations() {
        return this.accreditations;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        if (this.accreditations != null) {
            this.accreditations.forEach(i -> i.setCategory(null));
        }
        if (accreditations != null) {
            accreditations.forEach(i -> i.setCategory(this));
        }
        this.accreditations = accreditations;
    }

    public Category accreditations(Set<Accreditation> accreditations) {
        this.setAccreditations(accreditations);
        return this;
    }

    public Category addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setCategory(this);
        return this;
    }

    public Category removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setCategory(null);
        return this;
    }

    public PrintingModel getPrintingModel() {
        return this.printingModel;
    }

    public void setPrintingModel(PrintingModel printingModel) {
        this.printingModel = printingModel;
    }

    public Category printingModel(PrintingModel printingModel) {
        this.setPrintingModel(printingModel);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Category event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return categoryId != null && categoryId.equals(((Category) o).categoryId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "categoryId=" + getCategoryId() +
            ", categoryName='" + getCategoryName() + "'" +
            ", categoryAbreviation='" + getCategoryAbreviation() + "'" +
            ", categoryColor='" + getCategoryColor() + "'" +
            ", categoryDescription='" + getCategoryDescription() + "'" +
            ", categoryLogo='" + getCategoryLogo() + "'" +
            ", categoryLogoContentType='" + getCategoryLogoContentType() + "'" +
            ", categoryParams='" + getCategoryParams() + "'" +
            ", categoryAttributs='" + getCategoryAttributs() + "'" +
            ", categoryStat='" + getCategoryStat() + "'" +
            "}";
    }
}

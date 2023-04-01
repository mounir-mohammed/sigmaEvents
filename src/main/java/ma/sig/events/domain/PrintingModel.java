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
 * A PrintingModel.
 */
@Entity
@Table(name = "printing_model")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrintingModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "printing_model_id")
    private Long printingModelId;

    @NotNull
    @Size(max = 50)
    @Column(name = "printing_model_name", length = 50, nullable = false)
    private String printingModelName;

    @NotNull
    @Column(name = "printing_model_file", nullable = false)
    private String printingModelFile;

    @NotNull
    @Column(name = "printing_model_path", nullable = false)
    private String printingModelPath;

    @Size(max = 200)
    @Column(name = "printing_model_description", length = 200)
    private String printingModelDescription;

    @Lob
    @Column(name = "printing_model_data")
    private byte[] printingModelData;

    @Column(name = "printing_model_data_content_type")
    private String printingModelDataContentType;

    @Column(name = "printing_model_params")
    private String printingModelParams;

    @Column(name = "printing_model_attributs")
    private String printingModelAttributs;

    @Column(name = "printing_model_stat")
    private Boolean printingModelStat;

    @OneToMany(mappedBy = "printingModel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "city", "country", "organiz", "printingType", "printingServer", "printingModel", "language", "event" },
        allowSetters = true
    )
    private Set<PrintingCentre> printingCentres = new HashSet<>();

    @OneToMany(mappedBy = "printingModel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accreditations", "printingModel", "event" }, allowSetters = true)
    private Set<AccreditationType> accreditationTypes = new HashSet<>();

    @OneToMany(mappedBy = "printingModel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fonctions", "accreditations", "printingModel", "event" }, allowSetters = true)
    private Set<Category> categories = new HashSet<>();

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

    public Long getPrintingModelId() {
        return this.printingModelId;
    }

    public PrintingModel printingModelId(Long printingModelId) {
        this.setPrintingModelId(printingModelId);
        return this;
    }

    public void setPrintingModelId(Long printingModelId) {
        this.printingModelId = printingModelId;
    }

    public String getPrintingModelName() {
        return this.printingModelName;
    }

    public PrintingModel printingModelName(String printingModelName) {
        this.setPrintingModelName(printingModelName);
        return this;
    }

    public void setPrintingModelName(String printingModelName) {
        this.printingModelName = printingModelName;
    }

    public String getPrintingModelFile() {
        return this.printingModelFile;
    }

    public PrintingModel printingModelFile(String printingModelFile) {
        this.setPrintingModelFile(printingModelFile);
        return this;
    }

    public void setPrintingModelFile(String printingModelFile) {
        this.printingModelFile = printingModelFile;
    }

    public String getPrintingModelPath() {
        return this.printingModelPath;
    }

    public PrintingModel printingModelPath(String printingModelPath) {
        this.setPrintingModelPath(printingModelPath);
        return this;
    }

    public void setPrintingModelPath(String printingModelPath) {
        this.printingModelPath = printingModelPath;
    }

    public String getPrintingModelDescription() {
        return this.printingModelDescription;
    }

    public PrintingModel printingModelDescription(String printingModelDescription) {
        this.setPrintingModelDescription(printingModelDescription);
        return this;
    }

    public void setPrintingModelDescription(String printingModelDescription) {
        this.printingModelDescription = printingModelDescription;
    }

    public byte[] getPrintingModelData() {
        return this.printingModelData;
    }

    public PrintingModel printingModelData(byte[] printingModelData) {
        this.setPrintingModelData(printingModelData);
        return this;
    }

    public void setPrintingModelData(byte[] printingModelData) {
        this.printingModelData = printingModelData;
    }

    public String getPrintingModelDataContentType() {
        return this.printingModelDataContentType;
    }

    public PrintingModel printingModelDataContentType(String printingModelDataContentType) {
        this.printingModelDataContentType = printingModelDataContentType;
        return this;
    }

    public void setPrintingModelDataContentType(String printingModelDataContentType) {
        this.printingModelDataContentType = printingModelDataContentType;
    }

    public String getPrintingModelParams() {
        return this.printingModelParams;
    }

    public PrintingModel printingModelParams(String printingModelParams) {
        this.setPrintingModelParams(printingModelParams);
        return this;
    }

    public void setPrintingModelParams(String printingModelParams) {
        this.printingModelParams = printingModelParams;
    }

    public String getPrintingModelAttributs() {
        return this.printingModelAttributs;
    }

    public PrintingModel printingModelAttributs(String printingModelAttributs) {
        this.setPrintingModelAttributs(printingModelAttributs);
        return this;
    }

    public void setPrintingModelAttributs(String printingModelAttributs) {
        this.printingModelAttributs = printingModelAttributs;
    }

    public Boolean getPrintingModelStat() {
        return this.printingModelStat;
    }

    public PrintingModel printingModelStat(Boolean printingModelStat) {
        this.setPrintingModelStat(printingModelStat);
        return this;
    }

    public void setPrintingModelStat(Boolean printingModelStat) {
        this.printingModelStat = printingModelStat;
    }

    public Set<PrintingCentre> getPrintingCentres() {
        return this.printingCentres;
    }

    public void setPrintingCentres(Set<PrintingCentre> printingCentres) {
        if (this.printingCentres != null) {
            this.printingCentres.forEach(i -> i.setPrintingModel(null));
        }
        if (printingCentres != null) {
            printingCentres.forEach(i -> i.setPrintingModel(this));
        }
        this.printingCentres = printingCentres;
    }

    public PrintingModel printingCentres(Set<PrintingCentre> printingCentres) {
        this.setPrintingCentres(printingCentres);
        return this;
    }

    public PrintingModel addPrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.add(printingCentre);
        printingCentre.setPrintingModel(this);
        return this;
    }

    public PrintingModel removePrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.remove(printingCentre);
        printingCentre.setPrintingModel(null);
        return this;
    }

    public Set<AccreditationType> getAccreditationTypes() {
        return this.accreditationTypes;
    }

    public void setAccreditationTypes(Set<AccreditationType> accreditationTypes) {
        if (this.accreditationTypes != null) {
            this.accreditationTypes.forEach(i -> i.setPrintingModel(null));
        }
        if (accreditationTypes != null) {
            accreditationTypes.forEach(i -> i.setPrintingModel(this));
        }
        this.accreditationTypes = accreditationTypes;
    }

    public PrintingModel accreditationTypes(Set<AccreditationType> accreditationTypes) {
        this.setAccreditationTypes(accreditationTypes);
        return this;
    }

    public PrintingModel addAccreditationType(AccreditationType accreditationType) {
        this.accreditationTypes.add(accreditationType);
        accreditationType.setPrintingModel(this);
        return this;
    }

    public PrintingModel removeAccreditationType(AccreditationType accreditationType) {
        this.accreditationTypes.remove(accreditationType);
        accreditationType.setPrintingModel(null);
        return this;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        if (this.categories != null) {
            this.categories.forEach(i -> i.setPrintingModel(null));
        }
        if (categories != null) {
            categories.forEach(i -> i.setPrintingModel(this));
        }
        this.categories = categories;
    }

    public PrintingModel categories(Set<Category> categories) {
        this.setCategories(categories);
        return this;
    }

    public PrintingModel addCategory(Category category) {
        this.categories.add(category);
        category.setPrintingModel(this);
        return this;
    }

    public PrintingModel removeCategory(Category category) {
        this.categories.remove(category);
        category.setPrintingModel(null);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public PrintingModel event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrintingModel)) {
            return false;
        }
        return printingModelId != null && printingModelId.equals(((PrintingModel) o).printingModelId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrintingModel{" +
            "printingModelId=" + getPrintingModelId() +
            ", printingModelName='" + getPrintingModelName() + "'" +
            ", printingModelFile='" + getPrintingModelFile() + "'" +
            ", printingModelPath='" + getPrintingModelPath() + "'" +
            ", printingModelDescription='" + getPrintingModelDescription() + "'" +
            ", printingModelData='" + getPrintingModelData() + "'" +
            ", printingModelDataContentType='" + getPrintingModelDataContentType() + "'" +
            ", printingModelParams='" + getPrintingModelParams() + "'" +
            ", printingModelAttributs='" + getPrintingModelAttributs() + "'" +
            ", printingModelStat='" + getPrintingModelStat() + "'" +
            "}";
    }
}

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
 * A PrintingType.
 */
@Entity
@Table(name = "printing_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrintingType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "printing_type_id")
    private Long printingTypeId;

    @NotNull
    @Size(max = 50)
    @Column(name = "printing_type_value", length = 50, nullable = false)
    private String printingTypeValue;

    @Size(max = 200)
    @Column(name = "printing_type_description", length = 200)
    private String printingTypeDescription;

    @Column(name = "printing_type_params")
    private String printingTypeParams;

    @Column(name = "printing_type_attributs")
    private String printingTypeAttributs;

    @Column(name = "printing_type_stat")
    private Boolean printingTypeStat;

    @OneToMany(mappedBy = "printingType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "city", "country", "organiz", "printingType", "printingServer", "printingModel", "language", "event" },
        allowSetters = true
    )
    private Set<PrintingCentre> printingCentres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getPrintingTypeId() {
        return this.printingTypeId;
    }

    public PrintingType printingTypeId(Long printingTypeId) {
        this.setPrintingTypeId(printingTypeId);
        return this;
    }

    public void setPrintingTypeId(Long printingTypeId) {
        this.printingTypeId = printingTypeId;
    }

    public String getPrintingTypeValue() {
        return this.printingTypeValue;
    }

    public PrintingType printingTypeValue(String printingTypeValue) {
        this.setPrintingTypeValue(printingTypeValue);
        return this;
    }

    public void setPrintingTypeValue(String printingTypeValue) {
        this.printingTypeValue = printingTypeValue;
    }

    public String getPrintingTypeDescription() {
        return this.printingTypeDescription;
    }

    public PrintingType printingTypeDescription(String printingTypeDescription) {
        this.setPrintingTypeDescription(printingTypeDescription);
        return this;
    }

    public void setPrintingTypeDescription(String printingTypeDescription) {
        this.printingTypeDescription = printingTypeDescription;
    }

    public String getPrintingTypeParams() {
        return this.printingTypeParams;
    }

    public PrintingType printingTypeParams(String printingTypeParams) {
        this.setPrintingTypeParams(printingTypeParams);
        return this;
    }

    public void setPrintingTypeParams(String printingTypeParams) {
        this.printingTypeParams = printingTypeParams;
    }

    public String getPrintingTypeAttributs() {
        return this.printingTypeAttributs;
    }

    public PrintingType printingTypeAttributs(String printingTypeAttributs) {
        this.setPrintingTypeAttributs(printingTypeAttributs);
        return this;
    }

    public void setPrintingTypeAttributs(String printingTypeAttributs) {
        this.printingTypeAttributs = printingTypeAttributs;
    }

    public Boolean getPrintingTypeStat() {
        return this.printingTypeStat;
    }

    public PrintingType printingTypeStat(Boolean printingTypeStat) {
        this.setPrintingTypeStat(printingTypeStat);
        return this;
    }

    public void setPrintingTypeStat(Boolean printingTypeStat) {
        this.printingTypeStat = printingTypeStat;
    }

    public Set<PrintingCentre> getPrintingCentres() {
        return this.printingCentres;
    }

    public void setPrintingCentres(Set<PrintingCentre> printingCentres) {
        if (this.printingCentres != null) {
            this.printingCentres.forEach(i -> i.setPrintingType(null));
        }
        if (printingCentres != null) {
            printingCentres.forEach(i -> i.setPrintingType(this));
        }
        this.printingCentres = printingCentres;
    }

    public PrintingType printingCentres(Set<PrintingCentre> printingCentres) {
        this.setPrintingCentres(printingCentres);
        return this;
    }

    public PrintingType addPrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.add(printingCentre);
        printingCentre.setPrintingType(this);
        return this;
    }

    public PrintingType removePrintingCentre(PrintingCentre printingCentre) {
        this.printingCentres.remove(printingCentre);
        printingCentre.setPrintingType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrintingType)) {
            return false;
        }
        return printingTypeId != null && printingTypeId.equals(((PrintingType) o).printingTypeId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrintingType{" +
            "printingTypeId=" + getPrintingTypeId() +
            ", printingTypeValue='" + getPrintingTypeValue() + "'" +
            ", printingTypeDescription='" + getPrintingTypeDescription() + "'" +
            ", printingTypeParams='" + getPrintingTypeParams() + "'" +
            ", printingTypeAttributs='" + getPrintingTypeAttributs() + "'" +
            ", printingTypeStat='" + getPrintingTypeStat() + "'" +
            "}";
    }
}

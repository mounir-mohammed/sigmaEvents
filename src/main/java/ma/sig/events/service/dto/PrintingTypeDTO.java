package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.PrintingType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrintingTypeDTO implements Serializable {

    private Long printingTypeId;

    @NotNull
    @Size(max = 50)
    private String printingTypeValue;

    @Size(max = 200)
    private String printingTypeDescription;

    private String printingTypeParams;

    private String printingTypeAttributs;

    private Boolean printingTypeStat;

    public Long getPrintingTypeId() {
        return printingTypeId;
    }

    public void setPrintingTypeId(Long printingTypeId) {
        this.printingTypeId = printingTypeId;
    }

    public String getPrintingTypeValue() {
        return printingTypeValue;
    }

    public void setPrintingTypeValue(String printingTypeValue) {
        this.printingTypeValue = printingTypeValue;
    }

    public String getPrintingTypeDescription() {
        return printingTypeDescription;
    }

    public void setPrintingTypeDescription(String printingTypeDescription) {
        this.printingTypeDescription = printingTypeDescription;
    }

    public String getPrintingTypeParams() {
        return printingTypeParams;
    }

    public void setPrintingTypeParams(String printingTypeParams) {
        this.printingTypeParams = printingTypeParams;
    }

    public String getPrintingTypeAttributs() {
        return printingTypeAttributs;
    }

    public void setPrintingTypeAttributs(String printingTypeAttributs) {
        this.printingTypeAttributs = printingTypeAttributs;
    }

    public Boolean getPrintingTypeStat() {
        return printingTypeStat;
    }

    public void setPrintingTypeStat(Boolean printingTypeStat) {
        this.printingTypeStat = printingTypeStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrintingTypeDTO)) {
            return false;
        }

        PrintingTypeDTO printingTypeDTO = (PrintingTypeDTO) o;
        if (this.printingTypeId == null) {
            return false;
        }
        return Objects.equals(this.printingTypeId, printingTypeDTO.printingTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.printingTypeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrintingTypeDTO{" +
            "printingTypeId=" + getPrintingTypeId() +
            ", printingTypeValue='" + getPrintingTypeValue() + "'" +
            ", printingTypeDescription='" + getPrintingTypeDescription() + "'" +
            ", printingTypeParams='" + getPrintingTypeParams() + "'" +
            ", printingTypeAttributs='" + getPrintingTypeAttributs() + "'" +
            ", printingTypeStat='" + getPrintingTypeStat() + "'" +
            "}";
    }
}

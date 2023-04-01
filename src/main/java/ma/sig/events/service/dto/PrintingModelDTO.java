package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.PrintingModel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrintingModelDTO implements Serializable {

    private Long printingModelId;

    @NotNull
    @Size(max = 50)
    private String printingModelName;

    @NotNull
    private String printingModelFile;

    @NotNull
    private String printingModelPath;

    @Size(max = 200)
    private String printingModelDescription;

    @Lob
    private byte[] printingModelData;

    private String printingModelDataContentType;
    private String printingModelParams;

    private String printingModelAttributs;

    private Boolean printingModelStat;

    private EventDTO event;

    public Long getPrintingModelId() {
        return printingModelId;
    }

    public void setPrintingModelId(Long printingModelId) {
        this.printingModelId = printingModelId;
    }

    public String getPrintingModelName() {
        return printingModelName;
    }

    public void setPrintingModelName(String printingModelName) {
        this.printingModelName = printingModelName;
    }

    public String getPrintingModelFile() {
        return printingModelFile;
    }

    public void setPrintingModelFile(String printingModelFile) {
        this.printingModelFile = printingModelFile;
    }

    public String getPrintingModelPath() {
        return printingModelPath;
    }

    public void setPrintingModelPath(String printingModelPath) {
        this.printingModelPath = printingModelPath;
    }

    public String getPrintingModelDescription() {
        return printingModelDescription;
    }

    public void setPrintingModelDescription(String printingModelDescription) {
        this.printingModelDescription = printingModelDescription;
    }

    public byte[] getPrintingModelData() {
        return printingModelData;
    }

    public void setPrintingModelData(byte[] printingModelData) {
        this.printingModelData = printingModelData;
    }

    public String getPrintingModelDataContentType() {
        return printingModelDataContentType;
    }

    public void setPrintingModelDataContentType(String printingModelDataContentType) {
        this.printingModelDataContentType = printingModelDataContentType;
    }

    public String getPrintingModelParams() {
        return printingModelParams;
    }

    public void setPrintingModelParams(String printingModelParams) {
        this.printingModelParams = printingModelParams;
    }

    public String getPrintingModelAttributs() {
        return printingModelAttributs;
    }

    public void setPrintingModelAttributs(String printingModelAttributs) {
        this.printingModelAttributs = printingModelAttributs;
    }

    public Boolean getPrintingModelStat() {
        return printingModelStat;
    }

    public void setPrintingModelStat(Boolean printingModelStat) {
        this.printingModelStat = printingModelStat;
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
        if (!(o instanceof PrintingModelDTO)) {
            return false;
        }

        PrintingModelDTO printingModelDTO = (PrintingModelDTO) o;
        if (this.printingModelId == null) {
            return false;
        }
        return Objects.equals(this.printingModelId, printingModelDTO.printingModelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.printingModelId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrintingModelDTO{" +
            "printingModelId=" + getPrintingModelId() +
            ", printingModelName='" + getPrintingModelName() + "'" +
            ", printingModelFile='" + getPrintingModelFile() + "'" +
            ", printingModelPath='" + getPrintingModelPath() + "'" +
            ", printingModelDescription='" + getPrintingModelDescription() + "'" +
            ", printingModelData='" + getPrintingModelData() + "'" +
            ", printingModelParams='" + getPrintingModelParams() + "'" +
            ", printingModelAttributs='" + getPrintingModelAttributs() + "'" +
            ", printingModelStat='" + getPrintingModelStat() + "'" +
            ", event=" + getEvent() +
            "}";
    }
}

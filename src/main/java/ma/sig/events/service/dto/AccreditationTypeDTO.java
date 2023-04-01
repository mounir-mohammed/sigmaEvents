package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.AccreditationType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccreditationTypeDTO implements Serializable {

    private Long accreditationTypeId;

    @NotNull
    @Size(max = 50)
    private String accreditationTypeValue;

    @Size(max = 10)
    private String accreditationTypeAbreviation;

    @Size(max = 200)
    private String accreditationTypeDescription;

    private String accreditationTypeParams;

    private String accreditationTypeAttributs;

    private Boolean accreditationTypeStat;

    private PrintingModelDTO printingModel;

    private EventDTO event;

    public Long getAccreditationTypeId() {
        return accreditationTypeId;
    }

    public void setAccreditationTypeId(Long accreditationTypeId) {
        this.accreditationTypeId = accreditationTypeId;
    }

    public String getAccreditationTypeValue() {
        return accreditationTypeValue;
    }

    public void setAccreditationTypeValue(String accreditationTypeValue) {
        this.accreditationTypeValue = accreditationTypeValue;
    }

    public String getAccreditationTypeAbreviation() {
        return accreditationTypeAbreviation;
    }

    public void setAccreditationTypeAbreviation(String accreditationTypeAbreviation) {
        this.accreditationTypeAbreviation = accreditationTypeAbreviation;
    }

    public String getAccreditationTypeDescription() {
        return accreditationTypeDescription;
    }

    public void setAccreditationTypeDescription(String accreditationTypeDescription) {
        this.accreditationTypeDescription = accreditationTypeDescription;
    }

    public String getAccreditationTypeParams() {
        return accreditationTypeParams;
    }

    public void setAccreditationTypeParams(String accreditationTypeParams) {
        this.accreditationTypeParams = accreditationTypeParams;
    }

    public String getAccreditationTypeAttributs() {
        return accreditationTypeAttributs;
    }

    public void setAccreditationTypeAttributs(String accreditationTypeAttributs) {
        this.accreditationTypeAttributs = accreditationTypeAttributs;
    }

    public Boolean getAccreditationTypeStat() {
        return accreditationTypeStat;
    }

    public void setAccreditationTypeStat(Boolean accreditationTypeStat) {
        this.accreditationTypeStat = accreditationTypeStat;
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
        if (!(o instanceof AccreditationTypeDTO)) {
            return false;
        }

        AccreditationTypeDTO accreditationTypeDTO = (AccreditationTypeDTO) o;
        if (this.accreditationTypeId == null) {
            return false;
        }
        return Objects.equals(this.accreditationTypeId, accreditationTypeDTO.accreditationTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.accreditationTypeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccreditationTypeDTO{" +
            "accreditationTypeId=" + getAccreditationTypeId() +
            ", accreditationTypeValue='" + getAccreditationTypeValue() + "'" +
            ", accreditationTypeAbreviation='" + getAccreditationTypeAbreviation() + "'" +
            ", accreditationTypeDescription='" + getAccreditationTypeDescription() + "'" +
            ", accreditationTypeParams='" + getAccreditationTypeParams() + "'" +
            ", accreditationTypeAttributs='" + getAccreditationTypeAttributs() + "'" +
            ", accreditationTypeStat='" + getAccreditationTypeStat() + "'" +
            ", printingModel=" + getPrintingModel() +
            ", event=" + getEvent() +
            "}";
    }
}

package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.OperationType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperationTypeDTO implements Serializable {

    private Long operationTypeId;

    @NotNull
    @Size(max = 50)
    private String operationTypeValue;

    @Size(max = 200)
    private String operationTypeDescription;

    private String operationTypeParams;

    private String operationTypeAttributs;

    private Boolean operationTypeStat;

    public Long getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public String getOperationTypeValue() {
        return operationTypeValue;
    }

    public void setOperationTypeValue(String operationTypeValue) {
        this.operationTypeValue = operationTypeValue;
    }

    public String getOperationTypeDescription() {
        return operationTypeDescription;
    }

    public void setOperationTypeDescription(String operationTypeDescription) {
        this.operationTypeDescription = operationTypeDescription;
    }

    public String getOperationTypeParams() {
        return operationTypeParams;
    }

    public void setOperationTypeParams(String operationTypeParams) {
        this.operationTypeParams = operationTypeParams;
    }

    public String getOperationTypeAttributs() {
        return operationTypeAttributs;
    }

    public void setOperationTypeAttributs(String operationTypeAttributs) {
        this.operationTypeAttributs = operationTypeAttributs;
    }

    public Boolean getOperationTypeStat() {
        return operationTypeStat;
    }

    public void setOperationTypeStat(Boolean operationTypeStat) {
        this.operationTypeStat = operationTypeStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationTypeDTO)) {
            return false;
        }

        OperationTypeDTO operationTypeDTO = (OperationTypeDTO) o;
        if (this.operationTypeId == null) {
            return false;
        }
        return Objects.equals(this.operationTypeId, operationTypeDTO.operationTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.operationTypeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationTypeDTO{" +
            "operationTypeId=" + getOperationTypeId() +
            ", operationTypeValue='" + getOperationTypeValue() + "'" +
            ", operationTypeDescription='" + getOperationTypeDescription() + "'" +
            ", operationTypeParams='" + getOperationTypeParams() + "'" +
            ", operationTypeAttributs='" + getOperationTypeAttributs() + "'" +
            ", operationTypeStat='" + getOperationTypeStat() + "'" +
            "}";
    }
}

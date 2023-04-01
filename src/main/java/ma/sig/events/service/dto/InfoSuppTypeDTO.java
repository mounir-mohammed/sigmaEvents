package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.InfoSuppType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InfoSuppTypeDTO implements Serializable {

    private Long infoSuppTypeId;

    @NotNull
    @Size(max = 50)
    private String infoSuppTypeName;

    @Size(max = 200)
    private String infoSuppTypeDescription;

    private String infoSuppTypeParams;

    private String infoSuppTypeAttributs;

    private Boolean infoSuppTypeStat;

    public Long getInfoSuppTypeId() {
        return infoSuppTypeId;
    }

    public void setInfoSuppTypeId(Long infoSuppTypeId) {
        this.infoSuppTypeId = infoSuppTypeId;
    }

    public String getInfoSuppTypeName() {
        return infoSuppTypeName;
    }

    public void setInfoSuppTypeName(String infoSuppTypeName) {
        this.infoSuppTypeName = infoSuppTypeName;
    }

    public String getInfoSuppTypeDescription() {
        return infoSuppTypeDescription;
    }

    public void setInfoSuppTypeDescription(String infoSuppTypeDescription) {
        this.infoSuppTypeDescription = infoSuppTypeDescription;
    }

    public String getInfoSuppTypeParams() {
        return infoSuppTypeParams;
    }

    public void setInfoSuppTypeParams(String infoSuppTypeParams) {
        this.infoSuppTypeParams = infoSuppTypeParams;
    }

    public String getInfoSuppTypeAttributs() {
        return infoSuppTypeAttributs;
    }

    public void setInfoSuppTypeAttributs(String infoSuppTypeAttributs) {
        this.infoSuppTypeAttributs = infoSuppTypeAttributs;
    }

    public Boolean getInfoSuppTypeStat() {
        return infoSuppTypeStat;
    }

    public void setInfoSuppTypeStat(Boolean infoSuppTypeStat) {
        this.infoSuppTypeStat = infoSuppTypeStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InfoSuppTypeDTO)) {
            return false;
        }

        InfoSuppTypeDTO infoSuppTypeDTO = (InfoSuppTypeDTO) o;
        if (this.infoSuppTypeId == null) {
            return false;
        }
        return Objects.equals(this.infoSuppTypeId, infoSuppTypeDTO.infoSuppTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.infoSuppTypeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfoSuppTypeDTO{" +
            "infoSuppTypeId=" + getInfoSuppTypeId() +
            ", infoSuppTypeName='" + getInfoSuppTypeName() + "'" +
            ", infoSuppTypeDescription='" + getInfoSuppTypeDescription() + "'" +
            ", infoSuppTypeParams='" + getInfoSuppTypeParams() + "'" +
            ", infoSuppTypeAttributs='" + getInfoSuppTypeAttributs() + "'" +
            ", infoSuppTypeStat='" + getInfoSuppTypeStat() + "'" +
            "}";
    }
}

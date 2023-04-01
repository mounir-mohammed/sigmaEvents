package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.AttachementType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttachementTypeDTO implements Serializable {

    private Long attachementTypeId;

    @NotNull
    @Size(max = 50)
    private String attachementTypeName;

    @Size(max = 200)
    private String attachementTypeDescription;

    private String attachementTypeParams;

    private String attachementTypeAttributs;

    private Boolean attachementTypeStat;

    public Long getAttachementTypeId() {
        return attachementTypeId;
    }

    public void setAttachementTypeId(Long attachementTypeId) {
        this.attachementTypeId = attachementTypeId;
    }

    public String getAttachementTypeName() {
        return attachementTypeName;
    }

    public void setAttachementTypeName(String attachementTypeName) {
        this.attachementTypeName = attachementTypeName;
    }

    public String getAttachementTypeDescription() {
        return attachementTypeDescription;
    }

    public void setAttachementTypeDescription(String attachementTypeDescription) {
        this.attachementTypeDescription = attachementTypeDescription;
    }

    public String getAttachementTypeParams() {
        return attachementTypeParams;
    }

    public void setAttachementTypeParams(String attachementTypeParams) {
        this.attachementTypeParams = attachementTypeParams;
    }

    public String getAttachementTypeAttributs() {
        return attachementTypeAttributs;
    }

    public void setAttachementTypeAttributs(String attachementTypeAttributs) {
        this.attachementTypeAttributs = attachementTypeAttributs;
    }

    public Boolean getAttachementTypeStat() {
        return attachementTypeStat;
    }

    public void setAttachementTypeStat(Boolean attachementTypeStat) {
        this.attachementTypeStat = attachementTypeStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachementTypeDTO)) {
            return false;
        }

        AttachementTypeDTO attachementTypeDTO = (AttachementTypeDTO) o;
        if (this.attachementTypeId == null) {
            return false;
        }
        return Objects.equals(this.attachementTypeId, attachementTypeDTO.attachementTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.attachementTypeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachementTypeDTO{" +
            "attachementTypeId=" + getAttachementTypeId() +
            ", attachementTypeName='" + getAttachementTypeName() + "'" +
            ", attachementTypeDescription='" + getAttachementTypeDescription() + "'" +
            ", attachementTypeParams='" + getAttachementTypeParams() + "'" +
            ", attachementTypeAttributs='" + getAttachementTypeAttributs() + "'" +
            ", attachementTypeStat='" + getAttachementTypeStat() + "'" +
            "}";
    }
}

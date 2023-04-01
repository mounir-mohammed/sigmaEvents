package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Fonction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FonctionDTO implements Serializable {

    private Long fonctionId;

    @NotNull
    @Size(max = 50)
    private String fonctionName;

    @Size(max = 10)
    private String fonctionAbreviation;

    @Size(max = 100)
    private String fonctionColor;

    @Size(max = 200)
    private String fonctionDescription;

    @Lob
    private byte[] fonctionLogo;

    private String fonctionLogoContentType;
    private String fonctionParams;

    private String fonctionAttributs;

    private Boolean fonctionStat;

    private Set<AreaDTO> areas = new HashSet<>();

    private CategoryDTO category;

    private EventDTO event;

    public Long getFonctionId() {
        return fonctionId;
    }

    public void setFonctionId(Long fonctionId) {
        this.fonctionId = fonctionId;
    }

    public String getFonctionName() {
        return fonctionName;
    }

    public void setFonctionName(String fonctionName) {
        this.fonctionName = fonctionName;
    }

    public String getFonctionAbreviation() {
        return fonctionAbreviation;
    }

    public void setFonctionAbreviation(String fonctionAbreviation) {
        this.fonctionAbreviation = fonctionAbreviation;
    }

    public String getFonctionColor() {
        return fonctionColor;
    }

    public void setFonctionColor(String fonctionColor) {
        this.fonctionColor = fonctionColor;
    }

    public String getFonctionDescription() {
        return fonctionDescription;
    }

    public void setFonctionDescription(String fonctionDescription) {
        this.fonctionDescription = fonctionDescription;
    }

    public byte[] getFonctionLogo() {
        return fonctionLogo;
    }

    public void setFonctionLogo(byte[] fonctionLogo) {
        this.fonctionLogo = fonctionLogo;
    }

    public String getFonctionLogoContentType() {
        return fonctionLogoContentType;
    }

    public void setFonctionLogoContentType(String fonctionLogoContentType) {
        this.fonctionLogoContentType = fonctionLogoContentType;
    }

    public String getFonctionParams() {
        return fonctionParams;
    }

    public void setFonctionParams(String fonctionParams) {
        this.fonctionParams = fonctionParams;
    }

    public String getFonctionAttributs() {
        return fonctionAttributs;
    }

    public void setFonctionAttributs(String fonctionAttributs) {
        this.fonctionAttributs = fonctionAttributs;
    }

    public Boolean getFonctionStat() {
        return fonctionStat;
    }

    public void setFonctionStat(Boolean fonctionStat) {
        this.fonctionStat = fonctionStat;
    }

    public Set<AreaDTO> getAreas() {
        return areas;
    }

    public void setAreas(Set<AreaDTO> areas) {
        this.areas = areas;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
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
        if (!(o instanceof FonctionDTO)) {
            return false;
        }

        FonctionDTO fonctionDTO = (FonctionDTO) o;
        if (this.fonctionId == null) {
            return false;
        }
        return Objects.equals(this.fonctionId, fonctionDTO.fonctionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fonctionId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FonctionDTO{" +
            "fonctionId=" + getFonctionId() +
            ", fonctionName='" + getFonctionName() + "'" +
            ", fonctionAbreviation='" + getFonctionAbreviation() + "'" +
            ", fonctionColor='" + getFonctionColor() + "'" +
            ", fonctionDescription='" + getFonctionDescription() + "'" +
            ", fonctionLogo='" + getFonctionLogo() + "'" +
            ", fonctionParams='" + getFonctionParams() + "'" +
            ", fonctionAttributs='" + getFonctionAttributs() + "'" +
            ", fonctionStat='" + getFonctionStat() + "'" +
            ", areas=" + getAreas() +
            ", category=" + getCategory() +
            ", event=" + getEvent() +
            "}";
    }
}

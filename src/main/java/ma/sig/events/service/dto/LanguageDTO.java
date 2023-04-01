package ma.sig.events.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Language} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LanguageDTO implements Serializable {

    private Long languageId;

    @NotNull
    @Size(max = 50)
    private String languageCode;

    @NotNull
    @Size(max = 50)
    private String languageName;

    @Size(max = 200)
    private String languageDescription;

    private String languageParams;

    private String languageAttributs;

    private Boolean languageStat;

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageDescription() {
        return languageDescription;
    }

    public void setLanguageDescription(String languageDescription) {
        this.languageDescription = languageDescription;
    }

    public String getLanguageParams() {
        return languageParams;
    }

    public void setLanguageParams(String languageParams) {
        this.languageParams = languageParams;
    }

    public String getLanguageAttributs() {
        return languageAttributs;
    }

    public void setLanguageAttributs(String languageAttributs) {
        this.languageAttributs = languageAttributs;
    }

    public Boolean getLanguageStat() {
        return languageStat;
    }

    public void setLanguageStat(Boolean languageStat) {
        this.languageStat = languageStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LanguageDTO)) {
            return false;
        }

        LanguageDTO languageDTO = (LanguageDTO) o;
        if (this.languageId == null) {
            return false;
        }
        return Objects.equals(this.languageId, languageDTO.languageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.languageId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LanguageDTO{" +
            "languageId=" + getLanguageId() +
            ", languageCode='" + getLanguageCode() + "'" +
            ", languageName='" + getLanguageName() + "'" +
            ", languageDescription='" + getLanguageDescription() + "'" +
            ", languageParams='" + getLanguageParams() + "'" +
            ", languageAttributs='" + getLanguageAttributs() + "'" +
            ", languageStat='" + getLanguageStat() + "'" +
            "}";
    }
}

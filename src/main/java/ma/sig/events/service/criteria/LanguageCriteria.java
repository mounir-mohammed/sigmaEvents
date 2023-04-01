package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Language} entity. This class is used
 * in {@link ma.sig.events.web.rest.LanguageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /languages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LanguageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter languageId;

    private StringFilter languageCode;

    private StringFilter languageName;

    private StringFilter languageDescription;

    private StringFilter languageParams;

    private StringFilter languageAttributs;

    private BooleanFilter languageStat;

    private LongFilter eventId;

    private LongFilter settingId;

    private LongFilter printingCentreId;

    private Boolean distinct;

    public LanguageCriteria() {}

    public LanguageCriteria(LanguageCriteria other) {
        this.languageId = other.languageId == null ? null : other.languageId.copy();
        this.languageCode = other.languageCode == null ? null : other.languageCode.copy();
        this.languageName = other.languageName == null ? null : other.languageName.copy();
        this.languageDescription = other.languageDescription == null ? null : other.languageDescription.copy();
        this.languageParams = other.languageParams == null ? null : other.languageParams.copy();
        this.languageAttributs = other.languageAttributs == null ? null : other.languageAttributs.copy();
        this.languageStat = other.languageStat == null ? null : other.languageStat.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.settingId = other.settingId == null ? null : other.settingId.copy();
        this.printingCentreId = other.printingCentreId == null ? null : other.printingCentreId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LanguageCriteria copy() {
        return new LanguageCriteria(this);
    }

    public LongFilter getLanguageId() {
        return languageId;
    }

    public LongFilter languageId() {
        if (languageId == null) {
            languageId = new LongFilter();
        }
        return languageId;
    }

    public void setLanguageId(LongFilter languageId) {
        this.languageId = languageId;
    }

    public StringFilter getLanguageCode() {
        return languageCode;
    }

    public StringFilter languageCode() {
        if (languageCode == null) {
            languageCode = new StringFilter();
        }
        return languageCode;
    }

    public void setLanguageCode(StringFilter languageCode) {
        this.languageCode = languageCode;
    }

    public StringFilter getLanguageName() {
        return languageName;
    }

    public StringFilter languageName() {
        if (languageName == null) {
            languageName = new StringFilter();
        }
        return languageName;
    }

    public void setLanguageName(StringFilter languageName) {
        this.languageName = languageName;
    }

    public StringFilter getLanguageDescription() {
        return languageDescription;
    }

    public StringFilter languageDescription() {
        if (languageDescription == null) {
            languageDescription = new StringFilter();
        }
        return languageDescription;
    }

    public void setLanguageDescription(StringFilter languageDescription) {
        this.languageDescription = languageDescription;
    }

    public StringFilter getLanguageParams() {
        return languageParams;
    }

    public StringFilter languageParams() {
        if (languageParams == null) {
            languageParams = new StringFilter();
        }
        return languageParams;
    }

    public void setLanguageParams(StringFilter languageParams) {
        this.languageParams = languageParams;
    }

    public StringFilter getLanguageAttributs() {
        return languageAttributs;
    }

    public StringFilter languageAttributs() {
        if (languageAttributs == null) {
            languageAttributs = new StringFilter();
        }
        return languageAttributs;
    }

    public void setLanguageAttributs(StringFilter languageAttributs) {
        this.languageAttributs = languageAttributs;
    }

    public BooleanFilter getLanguageStat() {
        return languageStat;
    }

    public BooleanFilter languageStat() {
        if (languageStat == null) {
            languageStat = new BooleanFilter();
        }
        return languageStat;
    }

    public void setLanguageStat(BooleanFilter languageStat) {
        this.languageStat = languageStat;
    }

    public LongFilter getEventId() {
        return eventId;
    }

    public LongFilter eventId() {
        if (eventId == null) {
            eventId = new LongFilter();
        }
        return eventId;
    }

    public void setEventId(LongFilter eventId) {
        this.eventId = eventId;
    }

    public LongFilter getSettingId() {
        return settingId;
    }

    public LongFilter settingId() {
        if (settingId == null) {
            settingId = new LongFilter();
        }
        return settingId;
    }

    public void setSettingId(LongFilter settingId) {
        this.settingId = settingId;
    }

    public LongFilter getPrintingCentreId() {
        return printingCentreId;
    }

    public LongFilter printingCentreId() {
        if (printingCentreId == null) {
            printingCentreId = new LongFilter();
        }
        return printingCentreId;
    }

    public void setPrintingCentreId(LongFilter printingCentreId) {
        this.printingCentreId = printingCentreId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LanguageCriteria that = (LanguageCriteria) o;
        return (
            Objects.equals(languageId, that.languageId) &&
            Objects.equals(languageCode, that.languageCode) &&
            Objects.equals(languageName, that.languageName) &&
            Objects.equals(languageDescription, that.languageDescription) &&
            Objects.equals(languageParams, that.languageParams) &&
            Objects.equals(languageAttributs, that.languageAttributs) &&
            Objects.equals(languageStat, that.languageStat) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(settingId, that.settingId) &&
            Objects.equals(printingCentreId, that.printingCentreId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            languageId,
            languageCode,
            languageName,
            languageDescription,
            languageParams,
            languageAttributs,
            languageStat,
            eventId,
            settingId,
            printingCentreId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LanguageCriteria{" +
            (languageId != null ? "languageId=" + languageId + ", " : "") +
            (languageCode != null ? "languageCode=" + languageCode + ", " : "") +
            (languageName != null ? "languageName=" + languageName + ", " : "") +
            (languageDescription != null ? "languageDescription=" + languageDescription + ", " : "") +
            (languageParams != null ? "languageParams=" + languageParams + ", " : "") +
            (languageAttributs != null ? "languageAttributs=" + languageAttributs + ", " : "") +
            (languageStat != null ? "languageStat=" + languageStat + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (settingId != null ? "settingId=" + settingId + ", " : "") +
            (printingCentreId != null ? "printingCentreId=" + printingCentreId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

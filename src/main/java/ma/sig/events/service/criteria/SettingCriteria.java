package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Setting} entity. This class is used
 * in {@link ma.sig.events.web.rest.SettingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /settings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SettingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter settingId;

    private LongFilter settingParentId;

    private StringFilter settingType;

    private StringFilter settingNameClass;

    private StringFilter settingDataType;

    private StringFilter settingDescription;

    private StringFilter settingValueString;

    private LongFilter settingValueLong;

    private ZonedDateTimeFilter settingValueDate;

    private BooleanFilter settingValueBoolean;

    private StringFilter settingParams;

    private StringFilter settingAttributs;

    private BooleanFilter settingStat;

    private LongFilter languageId;

    private LongFilter eventId;

    private Boolean distinct;

    public SettingCriteria() {}

    public SettingCriteria(SettingCriteria other) {
        this.settingId = other.settingId == null ? null : other.settingId.copy();
        this.settingParentId = other.settingParentId == null ? null : other.settingParentId.copy();
        this.settingType = other.settingType == null ? null : other.settingType.copy();
        this.settingNameClass = other.settingNameClass == null ? null : other.settingNameClass.copy();
        this.settingDataType = other.settingDataType == null ? null : other.settingDataType.copy();
        this.settingDescription = other.settingDescription == null ? null : other.settingDescription.copy();
        this.settingValueString = other.settingValueString == null ? null : other.settingValueString.copy();
        this.settingValueLong = other.settingValueLong == null ? null : other.settingValueLong.copy();
        this.settingValueDate = other.settingValueDate == null ? null : other.settingValueDate.copy();
        this.settingValueBoolean = other.settingValueBoolean == null ? null : other.settingValueBoolean.copy();
        this.settingParams = other.settingParams == null ? null : other.settingParams.copy();
        this.settingAttributs = other.settingAttributs == null ? null : other.settingAttributs.copy();
        this.settingStat = other.settingStat == null ? null : other.settingStat.copy();
        this.languageId = other.languageId == null ? null : other.languageId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SettingCriteria copy() {
        return new SettingCriteria(this);
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

    public LongFilter getSettingParentId() {
        return settingParentId;
    }

    public LongFilter settingParentId() {
        if (settingParentId == null) {
            settingParentId = new LongFilter();
        }
        return settingParentId;
    }

    public void setSettingParentId(LongFilter settingParentId) {
        this.settingParentId = settingParentId;
    }

    public StringFilter getSettingType() {
        return settingType;
    }

    public StringFilter settingType() {
        if (settingType == null) {
            settingType = new StringFilter();
        }
        return settingType;
    }

    public void setSettingType(StringFilter settingType) {
        this.settingType = settingType;
    }

    public StringFilter getSettingNameClass() {
        return settingNameClass;
    }

    public StringFilter settingNameClass() {
        if (settingNameClass == null) {
            settingNameClass = new StringFilter();
        }
        return settingNameClass;
    }

    public void setSettingNameClass(StringFilter settingNameClass) {
        this.settingNameClass = settingNameClass;
    }

    public StringFilter getSettingDataType() {
        return settingDataType;
    }

    public StringFilter settingDataType() {
        if (settingDataType == null) {
            settingDataType = new StringFilter();
        }
        return settingDataType;
    }

    public void setSettingDataType(StringFilter settingDataType) {
        this.settingDataType = settingDataType;
    }

    public StringFilter getSettingDescription() {
        return settingDescription;
    }

    public StringFilter settingDescription() {
        if (settingDescription == null) {
            settingDescription = new StringFilter();
        }
        return settingDescription;
    }

    public void setSettingDescription(StringFilter settingDescription) {
        this.settingDescription = settingDescription;
    }

    public StringFilter getSettingValueString() {
        return settingValueString;
    }

    public StringFilter settingValueString() {
        if (settingValueString == null) {
            settingValueString = new StringFilter();
        }
        return settingValueString;
    }

    public void setSettingValueString(StringFilter settingValueString) {
        this.settingValueString = settingValueString;
    }

    public LongFilter getSettingValueLong() {
        return settingValueLong;
    }

    public LongFilter settingValueLong() {
        if (settingValueLong == null) {
            settingValueLong = new LongFilter();
        }
        return settingValueLong;
    }

    public void setSettingValueLong(LongFilter settingValueLong) {
        this.settingValueLong = settingValueLong;
    }

    public ZonedDateTimeFilter getSettingValueDate() {
        return settingValueDate;
    }

    public ZonedDateTimeFilter settingValueDate() {
        if (settingValueDate == null) {
            settingValueDate = new ZonedDateTimeFilter();
        }
        return settingValueDate;
    }

    public void setSettingValueDate(ZonedDateTimeFilter settingValueDate) {
        this.settingValueDate = settingValueDate;
    }

    public BooleanFilter getSettingValueBoolean() {
        return settingValueBoolean;
    }

    public BooleanFilter settingValueBoolean() {
        if (settingValueBoolean == null) {
            settingValueBoolean = new BooleanFilter();
        }
        return settingValueBoolean;
    }

    public void setSettingValueBoolean(BooleanFilter settingValueBoolean) {
        this.settingValueBoolean = settingValueBoolean;
    }

    public StringFilter getSettingParams() {
        return settingParams;
    }

    public StringFilter settingParams() {
        if (settingParams == null) {
            settingParams = new StringFilter();
        }
        return settingParams;
    }

    public void setSettingParams(StringFilter settingParams) {
        this.settingParams = settingParams;
    }

    public StringFilter getSettingAttributs() {
        return settingAttributs;
    }

    public StringFilter settingAttributs() {
        if (settingAttributs == null) {
            settingAttributs = new StringFilter();
        }
        return settingAttributs;
    }

    public void setSettingAttributs(StringFilter settingAttributs) {
        this.settingAttributs = settingAttributs;
    }

    public BooleanFilter getSettingStat() {
        return settingStat;
    }

    public BooleanFilter settingStat() {
        if (settingStat == null) {
            settingStat = new BooleanFilter();
        }
        return settingStat;
    }

    public void setSettingStat(BooleanFilter settingStat) {
        this.settingStat = settingStat;
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
        final SettingCriteria that = (SettingCriteria) o;
        return (
            Objects.equals(settingId, that.settingId) &&
            Objects.equals(settingParentId, that.settingParentId) &&
            Objects.equals(settingType, that.settingType) &&
            Objects.equals(settingNameClass, that.settingNameClass) &&
            Objects.equals(settingDataType, that.settingDataType) &&
            Objects.equals(settingDescription, that.settingDescription) &&
            Objects.equals(settingValueString, that.settingValueString) &&
            Objects.equals(settingValueLong, that.settingValueLong) &&
            Objects.equals(settingValueDate, that.settingValueDate) &&
            Objects.equals(settingValueBoolean, that.settingValueBoolean) &&
            Objects.equals(settingParams, that.settingParams) &&
            Objects.equals(settingAttributs, that.settingAttributs) &&
            Objects.equals(settingStat, that.settingStat) &&
            Objects.equals(languageId, that.languageId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            settingId,
            settingParentId,
            settingType,
            settingNameClass,
            settingDataType,
            settingDescription,
            settingValueString,
            settingValueLong,
            settingValueDate,
            settingValueBoolean,
            settingParams,
            settingAttributs,
            settingStat,
            languageId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SettingCriteria{" +
            (settingId != null ? "settingId=" + settingId + ", " : "") +
            (settingParentId != null ? "settingParentId=" + settingParentId + ", " : "") +
            (settingType != null ? "settingType=" + settingType + ", " : "") +
            (settingNameClass != null ? "settingNameClass=" + settingNameClass + ", " : "") +
            (settingDataType != null ? "settingDataType=" + settingDataType + ", " : "") +
            (settingDescription != null ? "settingDescription=" + settingDescription + ", " : "") +
            (settingValueString != null ? "settingValueString=" + settingValueString + ", " : "") +
            (settingValueLong != null ? "settingValueLong=" + settingValueLong + ", " : "") +
            (settingValueDate != null ? "settingValueDate=" + settingValueDate + ", " : "") +
            (settingValueBoolean != null ? "settingValueBoolean=" + settingValueBoolean + ", " : "") +
            (settingParams != null ? "settingParams=" + settingParams + ", " : "") +
            (settingAttributs != null ? "settingAttributs=" + settingAttributs + ", " : "") +
            (settingStat != null ? "settingStat=" + settingStat + ", " : "") +
            (languageId != null ? "languageId=" + languageId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

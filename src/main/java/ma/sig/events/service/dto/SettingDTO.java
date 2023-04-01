package ma.sig.events.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ma.sig.events.domain.Setting} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SettingDTO implements Serializable {

    private Long settingId;

    private Long settingParentId;

    @NotNull
    @Size(min = 2, max = 50)
    private String settingType;

    @NotNull
    @Size(min = 2, max = 50)
    private String settingNameClass;

    @NotNull
    @Size(min = 2, max = 50)
    private String settingDataType;

    @Size(max = 200)
    private String settingDescription;

    private String settingValueString;

    private Long settingValueLong;

    private ZonedDateTime settingValueDate;

    private Boolean settingValueBoolean;

    @Lob
    private byte[] settingValueBlob;

    private String settingValueBlobContentType;
    private String settingParams;

    private String settingAttributs;

    private Boolean settingStat;

    private LanguageDTO language;

    private EventDTO event;

    public Long getSettingId() {
        return settingId;
    }

    public void setSettingId(Long settingId) {
        this.settingId = settingId;
    }

    public Long getSettingParentId() {
        return settingParentId;
    }

    public void setSettingParentId(Long settingParentId) {
        this.settingParentId = settingParentId;
    }

    public String getSettingType() {
        return settingType;
    }

    public void setSettingType(String settingType) {
        this.settingType = settingType;
    }

    public String getSettingNameClass() {
        return settingNameClass;
    }

    public void setSettingNameClass(String settingNameClass) {
        this.settingNameClass = settingNameClass;
    }

    public String getSettingDataType() {
        return settingDataType;
    }

    public void setSettingDataType(String settingDataType) {
        this.settingDataType = settingDataType;
    }

    public String getSettingDescription() {
        return settingDescription;
    }

    public void setSettingDescription(String settingDescription) {
        this.settingDescription = settingDescription;
    }

    public String getSettingValueString() {
        return settingValueString;
    }

    public void setSettingValueString(String settingValueString) {
        this.settingValueString = settingValueString;
    }

    public Long getSettingValueLong() {
        return settingValueLong;
    }

    public void setSettingValueLong(Long settingValueLong) {
        this.settingValueLong = settingValueLong;
    }

    public ZonedDateTime getSettingValueDate() {
        return settingValueDate;
    }

    public void setSettingValueDate(ZonedDateTime settingValueDate) {
        this.settingValueDate = settingValueDate;
    }

    public Boolean getSettingValueBoolean() {
        return settingValueBoolean;
    }

    public void setSettingValueBoolean(Boolean settingValueBoolean) {
        this.settingValueBoolean = settingValueBoolean;
    }

    public byte[] getSettingValueBlob() {
        return settingValueBlob;
    }

    public void setSettingValueBlob(byte[] settingValueBlob) {
        this.settingValueBlob = settingValueBlob;
    }

    public String getSettingValueBlobContentType() {
        return settingValueBlobContentType;
    }

    public void setSettingValueBlobContentType(String settingValueBlobContentType) {
        this.settingValueBlobContentType = settingValueBlobContentType;
    }

    public String getSettingParams() {
        return settingParams;
    }

    public void setSettingParams(String settingParams) {
        this.settingParams = settingParams;
    }

    public String getSettingAttributs() {
        return settingAttributs;
    }

    public void setSettingAttributs(String settingAttributs) {
        this.settingAttributs = settingAttributs;
    }

    public Boolean getSettingStat() {
        return settingStat;
    }

    public void setSettingStat(Boolean settingStat) {
        this.settingStat = settingStat;
    }

    public LanguageDTO getLanguage() {
        return language;
    }

    public void setLanguage(LanguageDTO language) {
        this.language = language;
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
        if (!(o instanceof SettingDTO)) {
            return false;
        }

        SettingDTO settingDTO = (SettingDTO) o;
        if (this.settingId == null) {
            return false;
        }
        return Objects.equals(this.settingId, settingDTO.settingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.settingId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SettingDTO{" +
            "settingId=" + getSettingId() +
            ", settingParentId=" + getSettingParentId() +
            ", settingType='" + getSettingType() + "'" +
            ", settingNameClass='" + getSettingNameClass() + "'" +
            ", settingDataType='" + getSettingDataType() + "'" +
            ", settingDescription='" + getSettingDescription() + "'" +
            ", settingValueString='" + getSettingValueString() + "'" +
            ", settingValueLong=" + getSettingValueLong() +
            ", settingValueDate='" + getSettingValueDate() + "'" +
            ", settingValueBoolean='" + getSettingValueBoolean() + "'" +
            ", settingValueBlob='" + getSettingValueBlob() + "'" +
            ", settingParams='" + getSettingParams() + "'" +
            ", settingAttributs='" + getSettingAttributs() + "'" +
            ", settingStat='" + getSettingStat() + "'" +
            ", language=" + getLanguage() +
            ", event=" + getEvent() +
            "}";
    }
}

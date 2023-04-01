package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Setting.
 */
@Entity
@Table(name = "setting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Setting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "setting_id")
    private Long settingId;

    @Column(name = "setting_parent_id")
    private Long settingParentId;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "setting_type", length = 50, nullable = false)
    private String settingType;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "setting_name_class", length = 50, nullable = false)
    private String settingNameClass;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "setting_data_type", length = 50, nullable = false)
    private String settingDataType;

    @Size(max = 200)
    @Column(name = "setting_description", length = 200)
    private String settingDescription;

    @Column(name = "setting_value_string")
    private String settingValueString;

    @Column(name = "setting_value_long")
    private Long settingValueLong;

    @Column(name = "setting_value_date")
    private ZonedDateTime settingValueDate;

    @Column(name = "setting_value_boolean")
    private Boolean settingValueBoolean;

    @Lob
    @Column(name = "setting_value_blob")
    private byte[] settingValueBlob;

    @Column(name = "setting_value_blob_content_type")
    private String settingValueBlobContentType;

    @Column(name = "setting_params")
    private String settingParams;

    @Column(name = "setting_attributs")
    private String settingAttributs;

    @Column(name = "setting_stat")
    private Boolean settingStat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "events", "settings", "printingCentres" }, allowSetters = true)
    private Language language;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "eventForms",
            "eventFields",
            "eventControls",
            "areas",
            "fonctions",
            "categories",
            "printingModels",
            "codes",
            "infoSupps",
            "attachements",
            "organizs",
            "photoArchives",
            "sites",
            "accreditations",
            "notes",
            "operationHistories",
            "printingCentres",
            "settings",
            "printingServers",
            "checkAccreditationHistories",
            "checkAccreditationReports",
            "accreditationTypes",
            "dayPassInfos",
            "language",
        },
        allowSetters = true
    )
    private Event event;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getSettingId() {
        return this.settingId;
    }

    public Setting settingId(Long settingId) {
        this.setSettingId(settingId);
        return this;
    }

    public void setSettingId(Long settingId) {
        this.settingId = settingId;
    }

    public Long getSettingParentId() {
        return this.settingParentId;
    }

    public Setting settingParentId(Long settingParentId) {
        this.setSettingParentId(settingParentId);
        return this;
    }

    public void setSettingParentId(Long settingParentId) {
        this.settingParentId = settingParentId;
    }

    public String getSettingType() {
        return this.settingType;
    }

    public Setting settingType(String settingType) {
        this.setSettingType(settingType);
        return this;
    }

    public void setSettingType(String settingType) {
        this.settingType = settingType;
    }

    public String getSettingNameClass() {
        return this.settingNameClass;
    }

    public Setting settingNameClass(String settingNameClass) {
        this.setSettingNameClass(settingNameClass);
        return this;
    }

    public void setSettingNameClass(String settingNameClass) {
        this.settingNameClass = settingNameClass;
    }

    public String getSettingDataType() {
        return this.settingDataType;
    }

    public Setting settingDataType(String settingDataType) {
        this.setSettingDataType(settingDataType);
        return this;
    }

    public void setSettingDataType(String settingDataType) {
        this.settingDataType = settingDataType;
    }

    public String getSettingDescription() {
        return this.settingDescription;
    }

    public Setting settingDescription(String settingDescription) {
        this.setSettingDescription(settingDescription);
        return this;
    }

    public void setSettingDescription(String settingDescription) {
        this.settingDescription = settingDescription;
    }

    public String getSettingValueString() {
        return this.settingValueString;
    }

    public Setting settingValueString(String settingValueString) {
        this.setSettingValueString(settingValueString);
        return this;
    }

    public void setSettingValueString(String settingValueString) {
        this.settingValueString = settingValueString;
    }

    public Long getSettingValueLong() {
        return this.settingValueLong;
    }

    public Setting settingValueLong(Long settingValueLong) {
        this.setSettingValueLong(settingValueLong);
        return this;
    }

    public void setSettingValueLong(Long settingValueLong) {
        this.settingValueLong = settingValueLong;
    }

    public ZonedDateTime getSettingValueDate() {
        return this.settingValueDate;
    }

    public Setting settingValueDate(ZonedDateTime settingValueDate) {
        this.setSettingValueDate(settingValueDate);
        return this;
    }

    public void setSettingValueDate(ZonedDateTime settingValueDate) {
        this.settingValueDate = settingValueDate;
    }

    public Boolean getSettingValueBoolean() {
        return this.settingValueBoolean;
    }

    public Setting settingValueBoolean(Boolean settingValueBoolean) {
        this.setSettingValueBoolean(settingValueBoolean);
        return this;
    }

    public void setSettingValueBoolean(Boolean settingValueBoolean) {
        this.settingValueBoolean = settingValueBoolean;
    }

    public byte[] getSettingValueBlob() {
        return this.settingValueBlob;
    }

    public Setting settingValueBlob(byte[] settingValueBlob) {
        this.setSettingValueBlob(settingValueBlob);
        return this;
    }

    public void setSettingValueBlob(byte[] settingValueBlob) {
        this.settingValueBlob = settingValueBlob;
    }

    public String getSettingValueBlobContentType() {
        return this.settingValueBlobContentType;
    }

    public Setting settingValueBlobContentType(String settingValueBlobContentType) {
        this.settingValueBlobContentType = settingValueBlobContentType;
        return this;
    }

    public void setSettingValueBlobContentType(String settingValueBlobContentType) {
        this.settingValueBlobContentType = settingValueBlobContentType;
    }

    public String getSettingParams() {
        return this.settingParams;
    }

    public Setting settingParams(String settingParams) {
        this.setSettingParams(settingParams);
        return this;
    }

    public void setSettingParams(String settingParams) {
        this.settingParams = settingParams;
    }

    public String getSettingAttributs() {
        return this.settingAttributs;
    }

    public Setting settingAttributs(String settingAttributs) {
        this.setSettingAttributs(settingAttributs);
        return this;
    }

    public void setSettingAttributs(String settingAttributs) {
        this.settingAttributs = settingAttributs;
    }

    public Boolean getSettingStat() {
        return this.settingStat;
    }

    public Setting settingStat(Boolean settingStat) {
        this.setSettingStat(settingStat);
        return this;
    }

    public void setSettingStat(Boolean settingStat) {
        this.settingStat = settingStat;
    }

    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Setting language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Setting event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Setting)) {
            return false;
        }
        return settingId != null && settingId.equals(((Setting) o).settingId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Setting{" +
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
            ", settingValueBlobContentType='" + getSettingValueBlobContentType() + "'" +
            ", settingParams='" + getSettingParams() + "'" +
            ", settingAttributs='" + getSettingAttributs() + "'" +
            ", settingStat='" + getSettingStat() + "'" +
            "}";
    }
}

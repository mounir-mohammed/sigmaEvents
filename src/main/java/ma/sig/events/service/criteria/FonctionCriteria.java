package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Fonction} entity. This class is used
 * in {@link ma.sig.events.web.rest.FonctionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fonctions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FonctionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter fonctionId;

    private StringFilter fonctionName;

    private StringFilter fonctionAbreviation;

    private StringFilter fonctionColor;

    private StringFilter fonctionDescription;

    private StringFilter fonctionParams;

    private StringFilter fonctionAttributs;

    private BooleanFilter fonctionStat;

    private LongFilter accreditationId;

    private LongFilter areaId;

    private LongFilter categoryId;

    private LongFilter eventId;

    private Boolean distinct;

    public FonctionCriteria() {}

    public FonctionCriteria(FonctionCriteria other) {
        this.fonctionId = other.fonctionId == null ? null : other.fonctionId.copy();
        this.fonctionName = other.fonctionName == null ? null : other.fonctionName.copy();
        this.fonctionAbreviation = other.fonctionAbreviation == null ? null : other.fonctionAbreviation.copy();
        this.fonctionColor = other.fonctionColor == null ? null : other.fonctionColor.copy();
        this.fonctionDescription = other.fonctionDescription == null ? null : other.fonctionDescription.copy();
        this.fonctionParams = other.fonctionParams == null ? null : other.fonctionParams.copy();
        this.fonctionAttributs = other.fonctionAttributs == null ? null : other.fonctionAttributs.copy();
        this.fonctionStat = other.fonctionStat == null ? null : other.fonctionStat.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FonctionCriteria copy() {
        return new FonctionCriteria(this);
    }

    public LongFilter getFonctionId() {
        return fonctionId;
    }

    public LongFilter fonctionId() {
        if (fonctionId == null) {
            fonctionId = new LongFilter();
        }
        return fonctionId;
    }

    public void setFonctionId(LongFilter fonctionId) {
        this.fonctionId = fonctionId;
    }

    public StringFilter getFonctionName() {
        return fonctionName;
    }

    public StringFilter fonctionName() {
        if (fonctionName == null) {
            fonctionName = new StringFilter();
        }
        return fonctionName;
    }

    public void setFonctionName(StringFilter fonctionName) {
        this.fonctionName = fonctionName;
    }

    public StringFilter getFonctionAbreviation() {
        return fonctionAbreviation;
    }

    public StringFilter fonctionAbreviation() {
        if (fonctionAbreviation == null) {
            fonctionAbreviation = new StringFilter();
        }
        return fonctionAbreviation;
    }

    public void setFonctionAbreviation(StringFilter fonctionAbreviation) {
        this.fonctionAbreviation = fonctionAbreviation;
    }

    public StringFilter getFonctionColor() {
        return fonctionColor;
    }

    public StringFilter fonctionColor() {
        if (fonctionColor == null) {
            fonctionColor = new StringFilter();
        }
        return fonctionColor;
    }

    public void setFonctionColor(StringFilter fonctionColor) {
        this.fonctionColor = fonctionColor;
    }

    public StringFilter getFonctionDescription() {
        return fonctionDescription;
    }

    public StringFilter fonctionDescription() {
        if (fonctionDescription == null) {
            fonctionDescription = new StringFilter();
        }
        return fonctionDescription;
    }

    public void setFonctionDescription(StringFilter fonctionDescription) {
        this.fonctionDescription = fonctionDescription;
    }

    public StringFilter getFonctionParams() {
        return fonctionParams;
    }

    public StringFilter fonctionParams() {
        if (fonctionParams == null) {
            fonctionParams = new StringFilter();
        }
        return fonctionParams;
    }

    public void setFonctionParams(StringFilter fonctionParams) {
        this.fonctionParams = fonctionParams;
    }

    public StringFilter getFonctionAttributs() {
        return fonctionAttributs;
    }

    public StringFilter fonctionAttributs() {
        if (fonctionAttributs == null) {
            fonctionAttributs = new StringFilter();
        }
        return fonctionAttributs;
    }

    public void setFonctionAttributs(StringFilter fonctionAttributs) {
        this.fonctionAttributs = fonctionAttributs;
    }

    public BooleanFilter getFonctionStat() {
        return fonctionStat;
    }

    public BooleanFilter fonctionStat() {
        if (fonctionStat == null) {
            fonctionStat = new BooleanFilter();
        }
        return fonctionStat;
    }

    public void setFonctionStat(BooleanFilter fonctionStat) {
        this.fonctionStat = fonctionStat;
    }

    public LongFilter getAccreditationId() {
        return accreditationId;
    }

    public LongFilter accreditationId() {
        if (accreditationId == null) {
            accreditationId = new LongFilter();
        }
        return accreditationId;
    }

    public void setAccreditationId(LongFilter accreditationId) {
        this.accreditationId = accreditationId;
    }

    public LongFilter getAreaId() {
        return areaId;
    }

    public LongFilter areaId() {
        if (areaId == null) {
            areaId = new LongFilter();
        }
        return areaId;
    }

    public void setAreaId(LongFilter areaId) {
        this.areaId = areaId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public LongFilter categoryId() {
        if (categoryId == null) {
            categoryId = new LongFilter();
        }
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
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
        final FonctionCriteria that = (FonctionCriteria) o;
        return (
            Objects.equals(fonctionId, that.fonctionId) &&
            Objects.equals(fonctionName, that.fonctionName) &&
            Objects.equals(fonctionAbreviation, that.fonctionAbreviation) &&
            Objects.equals(fonctionColor, that.fonctionColor) &&
            Objects.equals(fonctionDescription, that.fonctionDescription) &&
            Objects.equals(fonctionParams, that.fonctionParams) &&
            Objects.equals(fonctionAttributs, that.fonctionAttributs) &&
            Objects.equals(fonctionStat, that.fonctionStat) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(areaId, that.areaId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            fonctionId,
            fonctionName,
            fonctionAbreviation,
            fonctionColor,
            fonctionDescription,
            fonctionParams,
            fonctionAttributs,
            fonctionStat,
            accreditationId,
            areaId,
            categoryId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FonctionCriteria{" +
            (fonctionId != null ? "fonctionId=" + fonctionId + ", " : "") +
            (fonctionName != null ? "fonctionName=" + fonctionName + ", " : "") +
            (fonctionAbreviation != null ? "fonctionAbreviation=" + fonctionAbreviation + ", " : "") +
            (fonctionColor != null ? "fonctionColor=" + fonctionColor + ", " : "") +
            (fonctionDescription != null ? "fonctionDescription=" + fonctionDescription + ", " : "") +
            (fonctionParams != null ? "fonctionParams=" + fonctionParams + ", " : "") +
            (fonctionAttributs != null ? "fonctionAttributs=" + fonctionAttributs + ", " : "") +
            (fonctionStat != null ? "fonctionStat=" + fonctionStat + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (areaId != null ? "areaId=" + areaId + ", " : "") +
            (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Area} entity. This class is used
 * in {@link ma.sig.events.web.rest.AreaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /areas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AreaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter areaId;

    private StringFilter areaName;

    private StringFilter areaAbreviation;

    private StringFilter areaColor;

    private StringFilter areaDescription;

    private StringFilter areaParams;

    private StringFilter areaAttributs;

    private BooleanFilter areaStat;

    private LongFilter eventId;

    private LongFilter fonctionId;

    private Boolean distinct;

    public AreaCriteria() {}

    public AreaCriteria(AreaCriteria other) {
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.areaName = other.areaName == null ? null : other.areaName.copy();
        this.areaAbreviation = other.areaAbreviation == null ? null : other.areaAbreviation.copy();
        this.areaColor = other.areaColor == null ? null : other.areaColor.copy();
        this.areaDescription = other.areaDescription == null ? null : other.areaDescription.copy();
        this.areaParams = other.areaParams == null ? null : other.areaParams.copy();
        this.areaAttributs = other.areaAttributs == null ? null : other.areaAttributs.copy();
        this.areaStat = other.areaStat == null ? null : other.areaStat.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.fonctionId = other.fonctionId == null ? null : other.fonctionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AreaCriteria copy() {
        return new AreaCriteria(this);
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

    public StringFilter getAreaName() {
        return areaName;
    }

    public StringFilter areaName() {
        if (areaName == null) {
            areaName = new StringFilter();
        }
        return areaName;
    }

    public void setAreaName(StringFilter areaName) {
        this.areaName = areaName;
    }

    public StringFilter getAreaAbreviation() {
        return areaAbreviation;
    }

    public StringFilter areaAbreviation() {
        if (areaAbreviation == null) {
            areaAbreviation = new StringFilter();
        }
        return areaAbreviation;
    }

    public void setAreaAbreviation(StringFilter areaAbreviation) {
        this.areaAbreviation = areaAbreviation;
    }

    public StringFilter getAreaColor() {
        return areaColor;
    }

    public StringFilter areaColor() {
        if (areaColor == null) {
            areaColor = new StringFilter();
        }
        return areaColor;
    }

    public void setAreaColor(StringFilter areaColor) {
        this.areaColor = areaColor;
    }

    public StringFilter getAreaDescription() {
        return areaDescription;
    }

    public StringFilter areaDescription() {
        if (areaDescription == null) {
            areaDescription = new StringFilter();
        }
        return areaDescription;
    }

    public void setAreaDescription(StringFilter areaDescription) {
        this.areaDescription = areaDescription;
    }

    public StringFilter getAreaParams() {
        return areaParams;
    }

    public StringFilter areaParams() {
        if (areaParams == null) {
            areaParams = new StringFilter();
        }
        return areaParams;
    }

    public void setAreaParams(StringFilter areaParams) {
        this.areaParams = areaParams;
    }

    public StringFilter getAreaAttributs() {
        return areaAttributs;
    }

    public StringFilter areaAttributs() {
        if (areaAttributs == null) {
            areaAttributs = new StringFilter();
        }
        return areaAttributs;
    }

    public void setAreaAttributs(StringFilter areaAttributs) {
        this.areaAttributs = areaAttributs;
    }

    public BooleanFilter getAreaStat() {
        return areaStat;
    }

    public BooleanFilter areaStat() {
        if (areaStat == null) {
            areaStat = new BooleanFilter();
        }
        return areaStat;
    }

    public void setAreaStat(BooleanFilter areaStat) {
        this.areaStat = areaStat;
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
        final AreaCriteria that = (AreaCriteria) o;
        return (
            Objects.equals(areaId, that.areaId) &&
            Objects.equals(areaName, that.areaName) &&
            Objects.equals(areaAbreviation, that.areaAbreviation) &&
            Objects.equals(areaColor, that.areaColor) &&
            Objects.equals(areaDescription, that.areaDescription) &&
            Objects.equals(areaParams, that.areaParams) &&
            Objects.equals(areaAttributs, that.areaAttributs) &&
            Objects.equals(areaStat, that.areaStat) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(fonctionId, that.fonctionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            areaId,
            areaName,
            areaAbreviation,
            areaColor,
            areaDescription,
            areaParams,
            areaAttributs,
            areaStat,
            eventId,
            fonctionId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaCriteria{" +
            (areaId != null ? "areaId=" + areaId + ", " : "") +
            (areaName != null ? "areaName=" + areaName + ", " : "") +
            (areaAbreviation != null ? "areaAbreviation=" + areaAbreviation + ", " : "") +
            (areaColor != null ? "areaColor=" + areaColor + ", " : "") +
            (areaDescription != null ? "areaDescription=" + areaDescription + ", " : "") +
            (areaParams != null ? "areaParams=" + areaParams + ", " : "") +
            (areaAttributs != null ? "areaAttributs=" + areaAttributs + ", " : "") +
            (areaStat != null ? "areaStat=" + areaStat + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (fonctionId != null ? "fonctionId=" + fonctionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

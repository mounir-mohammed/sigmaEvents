package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.Attachement} entity. This class is used
 * in {@link ma.sig.events.web.rest.AttachementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /attachements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttachementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter attachementId;

    private StringFilter attachementName;

    private StringFilter attachementPath;

    private StringFilter attachementDescription;

    private StringFilter attachementParams;

    private StringFilter attachementAttributs;

    private BooleanFilter attachementStat;

    private LongFilter accreditationId;

    private LongFilter attachementTypeId;

    private LongFilter eventId;

    private Boolean distinct;

    public AttachementCriteria() {}

    public AttachementCriteria(AttachementCriteria other) {
        this.attachementId = other.attachementId == null ? null : other.attachementId.copy();
        this.attachementName = other.attachementName == null ? null : other.attachementName.copy();
        this.attachementPath = other.attachementPath == null ? null : other.attachementPath.copy();
        this.attachementDescription = other.attachementDescription == null ? null : other.attachementDescription.copy();
        this.attachementParams = other.attachementParams == null ? null : other.attachementParams.copy();
        this.attachementAttributs = other.attachementAttributs == null ? null : other.attachementAttributs.copy();
        this.attachementStat = other.attachementStat == null ? null : other.attachementStat.copy();
        this.accreditationId = other.accreditationId == null ? null : other.accreditationId.copy();
        this.attachementTypeId = other.attachementTypeId == null ? null : other.attachementTypeId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AttachementCriteria copy() {
        return new AttachementCriteria(this);
    }

    public LongFilter getAttachementId() {
        return attachementId;
    }

    public LongFilter attachementId() {
        if (attachementId == null) {
            attachementId = new LongFilter();
        }
        return attachementId;
    }

    public void setAttachementId(LongFilter attachementId) {
        this.attachementId = attachementId;
    }

    public StringFilter getAttachementName() {
        return attachementName;
    }

    public StringFilter attachementName() {
        if (attachementName == null) {
            attachementName = new StringFilter();
        }
        return attachementName;
    }

    public void setAttachementName(StringFilter attachementName) {
        this.attachementName = attachementName;
    }

    public StringFilter getAttachementPath() {
        return attachementPath;
    }

    public StringFilter attachementPath() {
        if (attachementPath == null) {
            attachementPath = new StringFilter();
        }
        return attachementPath;
    }

    public void setAttachementPath(StringFilter attachementPath) {
        this.attachementPath = attachementPath;
    }

    public StringFilter getAttachementDescription() {
        return attachementDescription;
    }

    public StringFilter attachementDescription() {
        if (attachementDescription == null) {
            attachementDescription = new StringFilter();
        }
        return attachementDescription;
    }

    public void setAttachementDescription(StringFilter attachementDescription) {
        this.attachementDescription = attachementDescription;
    }

    public StringFilter getAttachementParams() {
        return attachementParams;
    }

    public StringFilter attachementParams() {
        if (attachementParams == null) {
            attachementParams = new StringFilter();
        }
        return attachementParams;
    }

    public void setAttachementParams(StringFilter attachementParams) {
        this.attachementParams = attachementParams;
    }

    public StringFilter getAttachementAttributs() {
        return attachementAttributs;
    }

    public StringFilter attachementAttributs() {
        if (attachementAttributs == null) {
            attachementAttributs = new StringFilter();
        }
        return attachementAttributs;
    }

    public void setAttachementAttributs(StringFilter attachementAttributs) {
        this.attachementAttributs = attachementAttributs;
    }

    public BooleanFilter getAttachementStat() {
        return attachementStat;
    }

    public BooleanFilter attachementStat() {
        if (attachementStat == null) {
            attachementStat = new BooleanFilter();
        }
        return attachementStat;
    }

    public void setAttachementStat(BooleanFilter attachementStat) {
        this.attachementStat = attachementStat;
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

    public LongFilter getAttachementTypeId() {
        return attachementTypeId;
    }

    public LongFilter attachementTypeId() {
        if (attachementTypeId == null) {
            attachementTypeId = new LongFilter();
        }
        return attachementTypeId;
    }

    public void setAttachementTypeId(LongFilter attachementTypeId) {
        this.attachementTypeId = attachementTypeId;
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
        final AttachementCriteria that = (AttachementCriteria) o;
        return (
            Objects.equals(attachementId, that.attachementId) &&
            Objects.equals(attachementName, that.attachementName) &&
            Objects.equals(attachementPath, that.attachementPath) &&
            Objects.equals(attachementDescription, that.attachementDescription) &&
            Objects.equals(attachementParams, that.attachementParams) &&
            Objects.equals(attachementAttributs, that.attachementAttributs) &&
            Objects.equals(attachementStat, that.attachementStat) &&
            Objects.equals(accreditationId, that.accreditationId) &&
            Objects.equals(attachementTypeId, that.attachementTypeId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            attachementId,
            attachementName,
            attachementPath,
            attachementDescription,
            attachementParams,
            attachementAttributs,
            attachementStat,
            accreditationId,
            attachementTypeId,
            eventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachementCriteria{" +
            (attachementId != null ? "attachementId=" + attachementId + ", " : "") +
            (attachementName != null ? "attachementName=" + attachementName + ", " : "") +
            (attachementPath != null ? "attachementPath=" + attachementPath + ", " : "") +
            (attachementDescription != null ? "attachementDescription=" + attachementDescription + ", " : "") +
            (attachementParams != null ? "attachementParams=" + attachementParams + ", " : "") +
            (attachementAttributs != null ? "attachementAttributs=" + attachementAttributs + ", " : "") +
            (attachementStat != null ? "attachementStat=" + attachementStat + ", " : "") +
            (accreditationId != null ? "accreditationId=" + accreditationId + ", " : "") +
            (attachementTypeId != null ? "attachementTypeId=" + attachementTypeId + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

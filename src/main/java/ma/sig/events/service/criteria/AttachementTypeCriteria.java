package ma.sig.events.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ma.sig.events.domain.AttachementType} entity. This class is used
 * in {@link ma.sig.events.web.rest.AttachementTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /attachement-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttachementTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter attachementTypeId;

    private StringFilter attachementTypeName;

    private StringFilter attachementTypeDescription;

    private StringFilter attachementTypeParams;

    private StringFilter attachementTypeAttributs;

    private BooleanFilter attachementTypeStat;

    private LongFilter attachementId;

    private Boolean distinct;

    public AttachementTypeCriteria() {}

    public AttachementTypeCriteria(AttachementTypeCriteria other) {
        this.attachementTypeId = other.attachementTypeId == null ? null : other.attachementTypeId.copy();
        this.attachementTypeName = other.attachementTypeName == null ? null : other.attachementTypeName.copy();
        this.attachementTypeDescription = other.attachementTypeDescription == null ? null : other.attachementTypeDescription.copy();
        this.attachementTypeParams = other.attachementTypeParams == null ? null : other.attachementTypeParams.copy();
        this.attachementTypeAttributs = other.attachementTypeAttributs == null ? null : other.attachementTypeAttributs.copy();
        this.attachementTypeStat = other.attachementTypeStat == null ? null : other.attachementTypeStat.copy();
        this.attachementId = other.attachementId == null ? null : other.attachementId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AttachementTypeCriteria copy() {
        return new AttachementTypeCriteria(this);
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

    public StringFilter getAttachementTypeName() {
        return attachementTypeName;
    }

    public StringFilter attachementTypeName() {
        if (attachementTypeName == null) {
            attachementTypeName = new StringFilter();
        }
        return attachementTypeName;
    }

    public void setAttachementTypeName(StringFilter attachementTypeName) {
        this.attachementTypeName = attachementTypeName;
    }

    public StringFilter getAttachementTypeDescription() {
        return attachementTypeDescription;
    }

    public StringFilter attachementTypeDescription() {
        if (attachementTypeDescription == null) {
            attachementTypeDescription = new StringFilter();
        }
        return attachementTypeDescription;
    }

    public void setAttachementTypeDescription(StringFilter attachementTypeDescription) {
        this.attachementTypeDescription = attachementTypeDescription;
    }

    public StringFilter getAttachementTypeParams() {
        return attachementTypeParams;
    }

    public StringFilter attachementTypeParams() {
        if (attachementTypeParams == null) {
            attachementTypeParams = new StringFilter();
        }
        return attachementTypeParams;
    }

    public void setAttachementTypeParams(StringFilter attachementTypeParams) {
        this.attachementTypeParams = attachementTypeParams;
    }

    public StringFilter getAttachementTypeAttributs() {
        return attachementTypeAttributs;
    }

    public StringFilter attachementTypeAttributs() {
        if (attachementTypeAttributs == null) {
            attachementTypeAttributs = new StringFilter();
        }
        return attachementTypeAttributs;
    }

    public void setAttachementTypeAttributs(StringFilter attachementTypeAttributs) {
        this.attachementTypeAttributs = attachementTypeAttributs;
    }

    public BooleanFilter getAttachementTypeStat() {
        return attachementTypeStat;
    }

    public BooleanFilter attachementTypeStat() {
        if (attachementTypeStat == null) {
            attachementTypeStat = new BooleanFilter();
        }
        return attachementTypeStat;
    }

    public void setAttachementTypeStat(BooleanFilter attachementTypeStat) {
        this.attachementTypeStat = attachementTypeStat;
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
        final AttachementTypeCriteria that = (AttachementTypeCriteria) o;
        return (
            Objects.equals(attachementTypeId, that.attachementTypeId) &&
            Objects.equals(attachementTypeName, that.attachementTypeName) &&
            Objects.equals(attachementTypeDescription, that.attachementTypeDescription) &&
            Objects.equals(attachementTypeParams, that.attachementTypeParams) &&
            Objects.equals(attachementTypeAttributs, that.attachementTypeAttributs) &&
            Objects.equals(attachementTypeStat, that.attachementTypeStat) &&
            Objects.equals(attachementId, that.attachementId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            attachementTypeId,
            attachementTypeName,
            attachementTypeDescription,
            attachementTypeParams,
            attachementTypeAttributs,
            attachementTypeStat,
            attachementId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachementTypeCriteria{" +
            (attachementTypeId != null ? "attachementTypeId=" + attachementTypeId + ", " : "") +
            (attachementTypeName != null ? "attachementTypeName=" + attachementTypeName + ", " : "") +
            (attachementTypeDescription != null ? "attachementTypeDescription=" + attachementTypeDescription + ", " : "") +
            (attachementTypeParams != null ? "attachementTypeParams=" + attachementTypeParams + ", " : "") +
            (attachementTypeAttributs != null ? "attachementTypeAttributs=" + attachementTypeAttributs + ", " : "") +
            (attachementTypeStat != null ? "attachementTypeStat=" + attachementTypeStat + ", " : "") +
            (attachementId != null ? "attachementId=" + attachementId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

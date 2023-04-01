package ma.sig.events.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cloning.
 */
@Entity
@Table(name = "cloning")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cloning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cloning_id")
    private Long cloningId;

    @Size(max = 200)
    @Column(name = "cloning_description", length = 200)
    private String cloningDescription;

    @Column(name = "cloning_old_event_id")
    private Long cloningOldEventId;

    @Column(name = "cloning_new_event_id")
    private Long cloningNewEventId;

    @Column(name = "cloning_user_id")
    private Long cloningUserId;

    @Column(name = "cloning_date")
    private ZonedDateTime cloningDate;

    @Column(name = "cloned_entitys")
    private String clonedEntitys;

    @Column(name = "cloned_params")
    private String clonedParams;

    @Column(name = "cloned_attributs")
    private String clonedAttributs;

    @Column(name = "cloned_stat")
    private Boolean clonedStat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getCloningId() {
        return this.cloningId;
    }

    public Cloning cloningId(Long cloningId) {
        this.setCloningId(cloningId);
        return this;
    }

    public void setCloningId(Long cloningId) {
        this.cloningId = cloningId;
    }

    public String getCloningDescription() {
        return this.cloningDescription;
    }

    public Cloning cloningDescription(String cloningDescription) {
        this.setCloningDescription(cloningDescription);
        return this;
    }

    public void setCloningDescription(String cloningDescription) {
        this.cloningDescription = cloningDescription;
    }

    public Long getCloningOldEventId() {
        return this.cloningOldEventId;
    }

    public Cloning cloningOldEventId(Long cloningOldEventId) {
        this.setCloningOldEventId(cloningOldEventId);
        return this;
    }

    public void setCloningOldEventId(Long cloningOldEventId) {
        this.cloningOldEventId = cloningOldEventId;
    }

    public Long getCloningNewEventId() {
        return this.cloningNewEventId;
    }

    public Cloning cloningNewEventId(Long cloningNewEventId) {
        this.setCloningNewEventId(cloningNewEventId);
        return this;
    }

    public void setCloningNewEventId(Long cloningNewEventId) {
        this.cloningNewEventId = cloningNewEventId;
    }

    public Long getCloningUserId() {
        return this.cloningUserId;
    }

    public Cloning cloningUserId(Long cloningUserId) {
        this.setCloningUserId(cloningUserId);
        return this;
    }

    public void setCloningUserId(Long cloningUserId) {
        this.cloningUserId = cloningUserId;
    }

    public ZonedDateTime getCloningDate() {
        return this.cloningDate;
    }

    public Cloning cloningDate(ZonedDateTime cloningDate) {
        this.setCloningDate(cloningDate);
        return this;
    }

    public void setCloningDate(ZonedDateTime cloningDate) {
        this.cloningDate = cloningDate;
    }

    public String getClonedEntitys() {
        return this.clonedEntitys;
    }

    public Cloning clonedEntitys(String clonedEntitys) {
        this.setClonedEntitys(clonedEntitys);
        return this;
    }

    public void setClonedEntitys(String clonedEntitys) {
        this.clonedEntitys = clonedEntitys;
    }

    public String getClonedParams() {
        return this.clonedParams;
    }

    public Cloning clonedParams(String clonedParams) {
        this.setClonedParams(clonedParams);
        return this;
    }

    public void setClonedParams(String clonedParams) {
        this.clonedParams = clonedParams;
    }

    public String getClonedAttributs() {
        return this.clonedAttributs;
    }

    public Cloning clonedAttributs(String clonedAttributs) {
        this.setClonedAttributs(clonedAttributs);
        return this;
    }

    public void setClonedAttributs(String clonedAttributs) {
        this.clonedAttributs = clonedAttributs;
    }

    public Boolean getClonedStat() {
        return this.clonedStat;
    }

    public Cloning clonedStat(Boolean clonedStat) {
        this.setClonedStat(clonedStat);
        return this;
    }

    public void setClonedStat(Boolean clonedStat) {
        this.clonedStat = clonedStat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cloning)) {
            return false;
        }
        return cloningId != null && cloningId.equals(((Cloning) o).cloningId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cloning{" +
            "cloningId=" + getCloningId() +
            ", cloningDescription='" + getCloningDescription() + "'" +
            ", cloningOldEventId=" + getCloningOldEventId() +
            ", cloningNewEventId=" + getCloningNewEventId() +
            ", cloningUserId=" + getCloningUserId() +
            ", cloningDate='" + getCloningDate() + "'" +
            ", clonedEntitys='" + getClonedEntitys() + "'" +
            ", clonedParams='" + getClonedParams() + "'" +
            ", clonedAttributs='" + getClonedAttributs() + "'" +
            ", clonedStat='" + getClonedStat() + "'" +
            "}";
    }
}

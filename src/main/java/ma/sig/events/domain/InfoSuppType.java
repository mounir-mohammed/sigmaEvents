package ma.sig.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InfoSuppType.
 */
@Entity
@Table(name = "info_supp_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InfoSuppType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_supp_type_id")
    private Long infoSuppTypeId;

    @NotNull
    @Size(max = 50)
    @Column(name = "info_supp_type_name", length = 50, nullable = false)
    private String infoSuppTypeName;

    @Size(max = 200)
    @Column(name = "info_supp_type_description", length = 200)
    private String infoSuppTypeDescription;

    @Column(name = "info_supp_type_params")
    private String infoSuppTypeParams;

    @Column(name = "info_supp_type_attributs")
    private String infoSuppTypeAttributs;

    @Column(name = "info_supp_type_stat")
    private Boolean infoSuppTypeStat;

    @OneToMany(mappedBy = "infoSuppType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "infoSuppType", "accreditation", "event" }, allowSetters = true)
    private Set<InfoSupp> infoSupps = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getInfoSuppTypeId() {
        return this.infoSuppTypeId;
    }

    public InfoSuppType infoSuppTypeId(Long infoSuppTypeId) {
        this.setInfoSuppTypeId(infoSuppTypeId);
        return this;
    }

    public void setInfoSuppTypeId(Long infoSuppTypeId) {
        this.infoSuppTypeId = infoSuppTypeId;
    }

    public String getInfoSuppTypeName() {
        return this.infoSuppTypeName;
    }

    public InfoSuppType infoSuppTypeName(String infoSuppTypeName) {
        this.setInfoSuppTypeName(infoSuppTypeName);
        return this;
    }

    public void setInfoSuppTypeName(String infoSuppTypeName) {
        this.infoSuppTypeName = infoSuppTypeName;
    }

    public String getInfoSuppTypeDescription() {
        return this.infoSuppTypeDescription;
    }

    public InfoSuppType infoSuppTypeDescription(String infoSuppTypeDescription) {
        this.setInfoSuppTypeDescription(infoSuppTypeDescription);
        return this;
    }

    public void setInfoSuppTypeDescription(String infoSuppTypeDescription) {
        this.infoSuppTypeDescription = infoSuppTypeDescription;
    }

    public String getInfoSuppTypeParams() {
        return this.infoSuppTypeParams;
    }

    public InfoSuppType infoSuppTypeParams(String infoSuppTypeParams) {
        this.setInfoSuppTypeParams(infoSuppTypeParams);
        return this;
    }

    public void setInfoSuppTypeParams(String infoSuppTypeParams) {
        this.infoSuppTypeParams = infoSuppTypeParams;
    }

    public String getInfoSuppTypeAttributs() {
        return this.infoSuppTypeAttributs;
    }

    public InfoSuppType infoSuppTypeAttributs(String infoSuppTypeAttributs) {
        this.setInfoSuppTypeAttributs(infoSuppTypeAttributs);
        return this;
    }

    public void setInfoSuppTypeAttributs(String infoSuppTypeAttributs) {
        this.infoSuppTypeAttributs = infoSuppTypeAttributs;
    }

    public Boolean getInfoSuppTypeStat() {
        return this.infoSuppTypeStat;
    }

    public InfoSuppType infoSuppTypeStat(Boolean infoSuppTypeStat) {
        this.setInfoSuppTypeStat(infoSuppTypeStat);
        return this;
    }

    public void setInfoSuppTypeStat(Boolean infoSuppTypeStat) {
        this.infoSuppTypeStat = infoSuppTypeStat;
    }

    public Set<InfoSupp> getInfoSupps() {
        return this.infoSupps;
    }

    public void setInfoSupps(Set<InfoSupp> infoSupps) {
        if (this.infoSupps != null) {
            this.infoSupps.forEach(i -> i.setInfoSuppType(null));
        }
        if (infoSupps != null) {
            infoSupps.forEach(i -> i.setInfoSuppType(this));
        }
        this.infoSupps = infoSupps;
    }

    public InfoSuppType infoSupps(Set<InfoSupp> infoSupps) {
        this.setInfoSupps(infoSupps);
        return this;
    }

    public InfoSuppType addInfoSupp(InfoSupp infoSupp) {
        this.infoSupps.add(infoSupp);
        infoSupp.setInfoSuppType(this);
        return this;
    }

    public InfoSuppType removeInfoSupp(InfoSupp infoSupp) {
        this.infoSupps.remove(infoSupp);
        infoSupp.setInfoSuppType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InfoSuppType)) {
            return false;
        }
        return infoSuppTypeId != null && infoSuppTypeId.equals(((InfoSuppType) o).infoSuppTypeId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfoSuppType{" +
            "infoSuppTypeId=" + getInfoSuppTypeId() +
            ", infoSuppTypeName='" + getInfoSuppTypeName() + "'" +
            ", infoSuppTypeDescription='" + getInfoSuppTypeDescription() + "'" +
            ", infoSuppTypeParams='" + getInfoSuppTypeParams() + "'" +
            ", infoSuppTypeAttributs='" + getInfoSuppTypeAttributs() + "'" +
            ", infoSuppTypeStat='" + getInfoSuppTypeStat() + "'" +
            "}";
    }
}

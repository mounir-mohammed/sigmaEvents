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
 * A OperationType.
 */
@Entity
@Table(name = "operation_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperationType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_type_id")
    private Long operationTypeId;

    @NotNull
    @Size(max = 50)
    @Column(name = "operation_type_value", length = 50, nullable = false)
    private String operationTypeValue;

    @Size(max = 200)
    @Column(name = "operation_type_description", length = 200)
    private String operationTypeDescription;

    @Column(name = "operation_type_params")
    private String operationTypeParams;

    @Column(name = "operation_type_attributs")
    private String operationTypeAttributs;

    @Column(name = "operation_type_stat")
    private Boolean operationTypeStat;

    @OneToMany(mappedBy = "typeoperation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "typeoperation", "event" }, allowSetters = true)
    private Set<OperationHistory> operationHistories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getOperationTypeId() {
        return this.operationTypeId;
    }

    public OperationType operationTypeId(Long operationTypeId) {
        this.setOperationTypeId(operationTypeId);
        return this;
    }

    public void setOperationTypeId(Long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public String getOperationTypeValue() {
        return this.operationTypeValue;
    }

    public OperationType operationTypeValue(String operationTypeValue) {
        this.setOperationTypeValue(operationTypeValue);
        return this;
    }

    public void setOperationTypeValue(String operationTypeValue) {
        this.operationTypeValue = operationTypeValue;
    }

    public String getOperationTypeDescription() {
        return this.operationTypeDescription;
    }

    public OperationType operationTypeDescription(String operationTypeDescription) {
        this.setOperationTypeDescription(operationTypeDescription);
        return this;
    }

    public void setOperationTypeDescription(String operationTypeDescription) {
        this.operationTypeDescription = operationTypeDescription;
    }

    public String getOperationTypeParams() {
        return this.operationTypeParams;
    }

    public OperationType operationTypeParams(String operationTypeParams) {
        this.setOperationTypeParams(operationTypeParams);
        return this;
    }

    public void setOperationTypeParams(String operationTypeParams) {
        this.operationTypeParams = operationTypeParams;
    }

    public String getOperationTypeAttributs() {
        return this.operationTypeAttributs;
    }

    public OperationType operationTypeAttributs(String operationTypeAttributs) {
        this.setOperationTypeAttributs(operationTypeAttributs);
        return this;
    }

    public void setOperationTypeAttributs(String operationTypeAttributs) {
        this.operationTypeAttributs = operationTypeAttributs;
    }

    public Boolean getOperationTypeStat() {
        return this.operationTypeStat;
    }

    public OperationType operationTypeStat(Boolean operationTypeStat) {
        this.setOperationTypeStat(operationTypeStat);
        return this;
    }

    public void setOperationTypeStat(Boolean operationTypeStat) {
        this.operationTypeStat = operationTypeStat;
    }

    public Set<OperationHistory> getOperationHistories() {
        return this.operationHistories;
    }

    public void setOperationHistories(Set<OperationHistory> operationHistories) {
        if (this.operationHistories != null) {
            this.operationHistories.forEach(i -> i.setTypeoperation(null));
        }
        if (operationHistories != null) {
            operationHistories.forEach(i -> i.setTypeoperation(this));
        }
        this.operationHistories = operationHistories;
    }

    public OperationType operationHistories(Set<OperationHistory> operationHistories) {
        this.setOperationHistories(operationHistories);
        return this;
    }

    public OperationType addOperationHistory(OperationHistory operationHistory) {
        this.operationHistories.add(operationHistory);
        operationHistory.setTypeoperation(this);
        return this;
    }

    public OperationType removeOperationHistory(OperationHistory operationHistory) {
        this.operationHistories.remove(operationHistory);
        operationHistory.setTypeoperation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationType)) {
            return false;
        }
        return operationTypeId != null && operationTypeId.equals(((OperationType) o).operationTypeId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationType{" +
            "operationTypeId=" + getOperationTypeId() +
            ", operationTypeValue='" + getOperationTypeValue() + "'" +
            ", operationTypeDescription='" + getOperationTypeDescription() + "'" +
            ", operationTypeParams='" + getOperationTypeParams() + "'" +
            ", operationTypeAttributs='" + getOperationTypeAttributs() + "'" +
            ", operationTypeStat='" + getOperationTypeStat() + "'" +
            "}";
    }
}

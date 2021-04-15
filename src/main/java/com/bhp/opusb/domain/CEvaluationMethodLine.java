package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * A CEvaluationMethodLine.
 */
@Entity
@Table(name = "c_evaluation_method_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CEvaluationMethodLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Referenced from M_Reference
     */
    @NotNull
    @Size(max = 5)
    @Column(name = "evaluation", length = 5, nullable = false)
    private String evaluation;

    @Column(name = "evaluation_type")
    private String evaluationType;

    @DecimalMax(value = "100")
    @Column(name = "weight", precision = 21, scale = 2)
    private BigDecimal weight;

    @Column(name = "passing_grade", precision = 21, scale = 2)
    private BigDecimal passingGrade;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cEvaluationMethodLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cEvaluationMethodLines")
    private CEvaluationMethod evaluationMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public CEvaluationMethodLine evaluation(String evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public CEvaluationMethodLine evaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
        return this;
    }

    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public CEvaluationMethodLine weight(BigDecimal weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPassingGrade() {
        return passingGrade;
    }

    public CEvaluationMethodLine passingGrade(BigDecimal passingGrade) {
        this.passingGrade = passingGrade;
        return this;
    }

    public void setPassingGrade(BigDecimal passingGrade) {
        this.passingGrade = passingGrade;
    }

    public UUID getUid() {
        return uid;
    }

    public CEvaluationMethodLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CEvaluationMethodLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CEvaluationMethodLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CEvaluationMethod getEvaluationMethod() {
        return evaluationMethod;
    }

    public CEvaluationMethodLine evaluationMethod(CEvaluationMethod cEvaluationMethod) {
        this.evaluationMethod = cEvaluationMethod;
        return this;
    }

    public void setEvaluationMethod(CEvaluationMethod cEvaluationMethod) {
        this.evaluationMethod = cEvaluationMethod;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CEvaluationMethodLine)) {
            return false;
        }
        return id != null && id.equals(((CEvaluationMethodLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CEvaluationMethodLine{" +
            "id=" + getId() +
            ", evaluation='" + getEvaluation() + "'" +
            ", evaluationType='" + getEvaluationType() + "'" +
            ", weight=" + getWeight() +
            ", passingGrade=" + getPassingGrade() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

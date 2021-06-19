package com.bhp.opusb.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MVendorEvaluationLine.
 */
@Entity
@Table(name = "m_vendor_evaluation_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVendorEvaluationLine extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "score", precision = 21, scale = 2, nullable = false)
    private BigDecimal score;

    @Column(name = "remark")
    private String remark;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorEvaluationLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorEvaluationLines")
    private MVendorEvaluation vendorEvaluation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorEvaluationLines")
    private CVendorEvaluationLine evaluationLine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getScore() {
        return score;
    }

    public MVendorEvaluationLine score(BigDecimal score) {
        this.score = score;
        return this;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public MVendorEvaluationLine remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UUID getUid() {
        return uid;
    }

    public MVendorEvaluationLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MVendorEvaluationLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MVendorEvaluationLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MVendorEvaluation getVendorEvaluation() {
        return vendorEvaluation;
    }

    public MVendorEvaluationLine vendorEvaluation(MVendorEvaluation mVendorEvaluation) {
        this.vendorEvaluation = mVendorEvaluation;
        return this;
    }

    public void setVendorEvaluation(MVendorEvaluation mVendorEvaluation) {
        this.vendorEvaluation = mVendorEvaluation;
    }

    public CVendorEvaluationLine getEvaluationLine() {
        return evaluationLine;
    }

    public MVendorEvaluationLine evaluationLine(CVendorEvaluationLine cVendorEvaluationLine) {
        this.evaluationLine = cVendorEvaluationLine;
        return this;
    }

    public void setEvaluationLine(CVendorEvaluationLine cVendorEvaluationLine) {
        this.evaluationLine = cVendorEvaluationLine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    void prePersist() {
        uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MVendorEvaluationLine)) {
            return false;
        }
        return id != null && id.equals(((MVendorEvaluationLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MVendorEvaluationLine{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", remark='" + getRemark() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

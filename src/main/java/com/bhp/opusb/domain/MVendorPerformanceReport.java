package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;

/**
 * A MVendorPerformanceReport.
 */
@Entity
@Immutable
@Table(name = "m_vendor_performance_report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVendorPerformanceReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "evaluation_score", precision = 21, scale = 2)
    private BigDecimal evaluationScore;

    @Column(name = "complaints")
    private Integer complaints;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorPerformanceReports")
    private CVendor vendor;

    @ManyToOne
    @JsonIgnoreProperties("mVendorPerformanceReports")
    private CBusinessCategory businessCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getEvaluationScore() {
        return evaluationScore;
    }

    public MVendorPerformanceReport evaluationScore(BigDecimal evaluationScore) {
        this.evaluationScore = evaluationScore;
        return this;
    }

    public void setEvaluationScore(BigDecimal evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public Integer getComplaints() {
        return complaints;
    }

    public MVendorPerformanceReport complaints(Integer complaints) {
        this.complaints = complaints;
        return this;
    }

    public void setComplaints(Integer complaints) {
        this.complaints = complaints;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MVendorPerformanceReport vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public CBusinessCategory getBusinessCategory() {
        return businessCategory;
    }

    public MVendorPerformanceReport businessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
        return this;
    }

    public void setBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MVendorPerformanceReport)) {
            return false;
        }
        return id != null && id.equals(((MVendorPerformanceReport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MVendorPerformanceReport{" +
            "id=" + getId() +
            ", evaluationScore=" + getEvaluationScore() +
            ", complaints=" + getComplaints() +
            "}";
    }
}

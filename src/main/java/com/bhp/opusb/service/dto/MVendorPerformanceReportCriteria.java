package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MVendorPerformanceReport} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MVendorPerformanceReportResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-vendor-performance-reports?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MVendorPerformanceReportCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter evaluationScore;

    private IntegerFilter complaints;

    private LongFilter vendorId;

    private LongFilter businessCategoryId;

    public MVendorPerformanceReportCriteria() {
    }

    public MVendorPerformanceReportCriteria(MVendorPerformanceReportCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.evaluationScore = other.evaluationScore == null ? null : other.evaluationScore.copy();
        this.complaints = other.complaints == null ? null : other.complaints.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.businessCategoryId = other.businessCategoryId == null ? null : other.businessCategoryId.copy();
    }

    @Override
    public MVendorPerformanceReportCriteria copy() {
        return new MVendorPerformanceReportCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(BigDecimalFilter evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public IntegerFilter getComplaints() {
        return complaints;
    }

    public void setComplaints(IntegerFilter complaints) {
        this.complaints = complaints;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }

    public LongFilter getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(LongFilter businessCategoryId) {
        this.businessCategoryId = businessCategoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MVendorPerformanceReportCriteria that = (MVendorPerformanceReportCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(evaluationScore, that.evaluationScore) &&
            Objects.equals(complaints, that.complaints) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(businessCategoryId, that.businessCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        evaluationScore,
        complaints,
        vendorId,
        businessCategoryId
        );
    }

    @Override
    public String toString() {
        return "MVendorPerformanceReportCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (evaluationScore != null ? "evaluationScore=" + evaluationScore + ", " : "") +
                (complaints != null ? "complaints=" + complaints + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (businessCategoryId != null ? "businessCategoryId=" + businessCategoryId + ", " : "") +
            "}";
    }

}

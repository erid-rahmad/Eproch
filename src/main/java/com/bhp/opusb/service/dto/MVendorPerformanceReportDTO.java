package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorPerformanceReport} entity.
 */
public class MVendorPerformanceReportDTO implements Serializable {
    
    private Long id;

    private BigDecimal evaluationScore;

    private Integer complaints;


    private Long vendorId;
    private String vendorName;

    private Long businessCategoryId;
    private String businessCategoryName;
    
    public Long getId() {
        return id;
    }

    public String getBusinessCategoryName() {
        return businessCategoryName;
    }

    public void setBusinessCategoryName(String businessCategoryName) {
        this.businessCategoryName = businessCategoryName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(BigDecimal evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public Integer getComplaints() {
        return complaints;
    }

    public void setComplaints(Integer complaints) {
        this.complaints = complaints;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public Long getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(Long cBusinessCategoryId) {
        this.businessCategoryId = cBusinessCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVendorPerformanceReportDTO mVendorPerformanceReportDTO = (MVendorPerformanceReportDTO) o;
        if (mVendorPerformanceReportDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVendorPerformanceReportDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVendorPerformanceReportDTO{" +
            "id=" + getId() +
            ", evaluationScore=" + getEvaluationScore() +
            ", complaints=" + getComplaints() +
            ", vendorId=" + getVendorId() +
            ", businessCategoryId=" + getBusinessCategoryId() +
            "}";
    }
}

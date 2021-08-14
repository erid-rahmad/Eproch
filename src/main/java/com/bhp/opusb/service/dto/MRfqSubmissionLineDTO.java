package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MRfqSubmissionLine} entity.
 */
public class MRfqSubmissionLineDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    @NotNull
    @Size(max = 10)
    private String documentAction;

    @NotNull
    @Size(max = 10)
    private String documentStatus;

    private Boolean approved;

    private Boolean processed;

    private Integer releaseQty;

    private BigDecimal submissionPrice;

    private BigDecimal totalSubmissionPrice;

    private String remark;

    private LocalDate dateTrx;

    private LocalDate dateRequired;

    private LocalDate dateSubmitted;


    private Long submissionId;

    private Long quotationLineId;

    private Long adOrganizationId;

    private Long productId;

    private Long uomId;

    private Long businessCategoryId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Integer getReleaseQty() {
        return releaseQty;
    }

    public void setReleaseQty(Integer releaseQty) {
        this.releaseQty = releaseQty;
    }

    public BigDecimal getSubmissionPrice() {
        return submissionPrice;
    }

    public void setSubmissionPrice(BigDecimal submissionPrice) {
        this.submissionPrice = submissionPrice;
    }

    public BigDecimal getTotalSubmissionPrice() {
        return totalSubmissionPrice;
    }

    public void setTotalSubmissionPrice(BigDecimal totalSubmissionPrice) {
        this.totalSubmissionPrice = totalSubmissionPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
    }

    public LocalDate getDateRequired() {
        return dateRequired;
    }

    public void setDateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
    }

    public LocalDate getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long mRfqSubmissionId) {
        this.submissionId = mRfqSubmissionId;
    }

    public Long getQuotationLineId() {
        return quotationLineId;
    }

    public void setQuotationLineId(Long mRfqLineId) {
        this.quotationLineId = mRfqLineId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long cProductId) {
        this.productId = cProductId;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long cUnitOfMeasureId) {
        this.uomId = cUnitOfMeasureId;
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

        MRfqSubmissionLineDTO mRfqSubmissionLineDTO = (MRfqSubmissionLineDTO) o;
        if (mRfqSubmissionLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mRfqSubmissionLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MRfqSubmissionLineDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", releaseQty=" + getReleaseQty() +
            ", submissionPrice=" + getSubmissionPrice() +
            ", totalSubmissionPrice=" + getTotalSubmissionPrice() +
            ", remark='" + getRemark() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", dateRequired='" + getDateRequired() + "'" +
            ", dateSubmitted='" + getDateSubmitted() + "'" +
            ", submissionId=" + getSubmissionId() +
            ", quotationLineId=" + getQuotationLineId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", productId=" + getProductId() +
            ", uomId=" + getUomId() +
            ", businessCategoryId=" + getBusinessCategoryId() +
            "}";
    }
}

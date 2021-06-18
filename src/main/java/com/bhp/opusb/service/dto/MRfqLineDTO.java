package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MRfqLine} entity.
 */
public class MRfqLineDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private Integer lineNo;

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

    private LocalDate documentDate;

    private LocalDate dateRequired;

    private String remark;


    private Long quotationId;

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

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
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

    public LocalDate getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public LocalDate getDateRequired() {
        return dateRequired;
    }

    public void setDateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long mRfqId) {
        this.quotationId = mRfqId;
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

        MRfqLineDTO mRfqLineDTO = (MRfqLineDTO) o;
        if (mRfqLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mRfqLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MRfqLineDTO{" +
            "id=" + getId() +
            ", lineNo=" + getLineNo() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", releaseQty=" + getReleaseQty() +
            ", documentDate='" + getDocumentDate() + "'" +
            ", dateRequired='" + getDateRequired() + "'" +
            ", remark='" + getRemark() + "'" +
            ", quotationId=" + getQuotationId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", productId=" + getProductId() +
            ", uomId=" + getUomId() +
            ", businessCategoryId=" + getBusinessCategoryId() +
            "}";
    }
}

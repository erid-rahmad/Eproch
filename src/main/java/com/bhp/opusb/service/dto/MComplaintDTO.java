package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MComplaint} entity.
 */
public class MComplaintDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    @NotNull
    private LocalDate reportDate;

    @Lob
    private String warning;

    @Size(max = 10)
    private String type;

    @NotNull
    private LocalDate dateTrx;

    @Size(max = 30)
    private String documentNo;

    @NotNull
    @Size(max = 10)
    private String documentAction;

    @NotNull
    @Size(max = 10)
    private String documentStatus;

    private Boolean approved;

    private Boolean processed;


    private Long adOrganizationId;

    private Long vendorId;
    private String vendorName;

    private Long costCenterId;
    private String costCenterName;

    private Long contractId;
    private String contractNo;

    private Long businessCategoryId;
    private String businessCategoryName;

    private Long subBusinessCategoryId;
    private String subBusinessCategoryName;
    
    public Long getId() {
        return id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getBusinessCategoryName() {
        return businessCategoryName;
    }

    public void setBusinessCategoryName(String businessCategoryName) {
        this.businessCategoryName = businessCategoryName;
    }

    public String getSubBusinessCategoryName() {
        return subBusinessCategoryName;
    }

    public void setSubBusinessCategoryName(String subBusinessCategoryName) {
        this.subBusinessCategoryName = subBusinessCategoryName;
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

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
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

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public Long getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(Long cCostCenterId) {
        this.costCenterId = cCostCenterId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long mContractId) {
        this.contractId = mContractId;
    }

    public Long getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(Long cBusinessCategoryId) {
        this.businessCategoryId = cBusinessCategoryId;
    }

    public Long getSubBusinessCategoryId() {
        return subBusinessCategoryId;
    }

    public void setSubBusinessCategoryId(Long cBusinessCategoryId) {
        this.subBusinessCategoryId = cBusinessCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MComplaintDTO mComplaintDTO = (MComplaintDTO) o;
        if (mComplaintDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mComplaintDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MComplaintDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", reportDate='" + getReportDate() + "'" +
            ", warning='" + getWarning() + "'" +
            ", type='" + getType() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", vendorId=" + getVendorId() +
            ", costCenterId=" + getCostCenterId() +
            ", contractId=" + getContractId() +
            ", businessCategoryId=" + getBusinessCategoryId() +
            ", subBusinessCategoryId=" + getSubBusinessCategoryId() +
            "}";
    }
}

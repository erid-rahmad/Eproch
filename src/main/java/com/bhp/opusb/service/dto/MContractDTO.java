package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MContract} entity.
 */
public class MContractDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    private String description;

    private Boolean useBanCode;

    private String banCode;

    /**
     * Types could be N (New Contract) or R (Renewal).
     */
    @ApiModelProperty(value = "Types could be N (New Contract) or R (Renewal).")
    private String contractType = "N";

    /**
     * Whether the contract is for Project or Non-project purpose.
     */
    @ApiModelProperty(value = "Whether the contract is for Project or Non-project purpose.")
    private String purpose;

    private Boolean forPriceConfirmation;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate expirationDate;

    private String evaluationPeriod;

    private ZonedDateTime dateTrx = ZonedDateTime.now();

    @Size(max = 30)
    private String documentNo;

    @NotNull
    @Size(max = 10)
    private String documentAction = "SMT";

    @NotNull
    @Size(max = 12)
    private String documentStatus = "DRF";

    private Boolean approved;

    private Boolean processed;

    private ZonedDateTime dateApprove;

    private ZonedDateTime dateReject;

    private String rejectedReason;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long costCenterId;
    private String costCenterName;

    private Long picUserId;
    private String picUserName;

    private Long vendorId;
    private String vendorName;

    private Long vendorEvaluationId;
    private String vendorEvaluationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isUseBanCode() {
        return useBanCode;
    }

    public void setUseBanCode(Boolean useBanCode) {
        this.useBanCode = useBanCode;
    }

    public String getBanCode() {
        return banCode;
    }

    public void setBanCode(String banCode) {
        this.banCode = banCode;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Boolean isForPriceConfirmation() {
        return forPriceConfirmation;
    }

    public void setForPriceConfirmation(Boolean forPriceConfirmation) {
        this.forPriceConfirmation = forPriceConfirmation;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getEvaluationPeriod() {
        return evaluationPeriod;
    }

    public void setEvaluationPeriod(String evaluationPeriod) {
        this.evaluationPeriod = evaluationPeriod;
    }

    public ZonedDateTime getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(ZonedDateTime dateTrx) {
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

    public ZonedDateTime getDateApprove() {
        return dateApprove;
    }

    public void setDateApprove(ZonedDateTime dateApprove) {
        this.dateApprove = dateApprove;
    }

    public ZonedDateTime getDateReject() {
        return dateReject;
    }

    public void setDateReject(ZonedDateTime dateReject) {
        this.dateReject = dateReject;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
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

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(Long cCostCenterId) {
        this.costCenterId = cCostCenterId;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    public Long getPicUserId() {
        return picUserId;
    }

    public void setPicUserId(Long adUserId) {
        this.picUserId = adUserId;
    }

    public String getPicUserName() {
        return picUserName;
    }

    public void setPicUserName(String picUserName) {
        this.picUserName = picUserName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Long getVendorEvaluationId() {
        return vendorEvaluationId;
    }

    public void setVendorEvaluationId(Long cVendorEvaluationId) {
        this.vendorEvaluationId = cVendorEvaluationId;
    }

    public String getVendorEvaluationName() {
        return vendorEvaluationName;
    }

    public void setVendorEvaluationName(String vendorEvaluationName) {
        this.vendorEvaluationName = vendorEvaluationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MContractDTO mContractDTO = (MContractDTO) o;
        if (mContractDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mContractDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MContractDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", useBanCode='" + isUseBanCode() + "'" +
            ", banCode='" + getBanCode() + "'" +
            ", contractType='" + getContractType() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", forPriceConfirmation='" + isForPriceConfirmation() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", evaluationPeriod='" + getEvaluationPeriod() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", dateApprove='" + getDateApprove() + "'" +
            ", dateReject='" + getDateReject() + "'" +
            ", rejectedReason='" + getRejectedReason() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", costCenterId=" + getCostCenterId() +
            ", picId=" + getPicUserId() +
            ", vendorId=" + getVendorId() +
            ", vendorEvaluationId=" + getVendorEvaluationId() +
            "}";
    }
}

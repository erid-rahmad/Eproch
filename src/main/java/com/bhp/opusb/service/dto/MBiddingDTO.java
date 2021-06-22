package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBidding} entity.
 */
public class MBiddingDTO extends AbstractAuditingDTO {

	private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String name;

    private String vendorSelection;

    private BigDecimal ceilingPrice;

    private BigDecimal estimatedPrice;

    @NotNull
    private String biddingStatus = "N";

    private Integer joinedVendorCount;

    private LocalDate dateTrx = LocalDate.now();

    @Size(max = 30)
    private String documentNo;

    @NotNull
    @Size(max = 10)
    private String documentAction = "APV";

    @NotNull
    @Size(max = 10)
    private String documentStatus = "DRF";

    private Boolean approved = false;

    private Boolean processed = false;

    private LocalDate dateApprove;

    private LocalDate dateReject;

    private String rejectedReason;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long costCenterId;
    private String costCenterName;

    private Long currencyId;
    private String currencyName;

    private Long documentTypeId;
    private String documentTypeName;

    private Long requisitionId;
    private String requisitionName;

    private Long quotationId;
    private String quotationName;

    private Long referenceTypeId;
    private String referenceTypeName;

    private Long biddingTypeId;
    private String biddingTypeName;

    private Long eventTypeId;
    private String eventTypeName;

    private Long adUserUserId;
    private String adUserUserName;

    private List<MBiddingLineDTO> biddingLineList;

    private List<MProjectInformationDTO> projectInformationList;

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

    public String getVendorSelection() {
        return vendorSelection;
    }

    public void setVendorSelection(String vendorSelection) {
        this.vendorSelection = vendorSelection;
    }

    public BigDecimal getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public BigDecimal getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(BigDecimal estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(String biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public Integer getJoinedVendorCount() {
        return joinedVendorCount;
    }

    public void setJoinedVendorCount(Integer joinedVendorCount) {
        this.joinedVendorCount = joinedVendorCount;
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

    public LocalDate getDateApprove() {
        return dateApprove;
    }

    public void setDateApprove(LocalDate dateApprove) {
        this.dateApprove = dateApprove;
    }

    public LocalDate getDateReject() {
        return dateReject;
    }

    public void setDateReject(LocalDate dateReject) {
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

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long cCurrencyId) {
        this.currencyId = cCurrencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long cDocumentTypeId) {
        this.documentTypeId = cDocumentTypeId;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public Long getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Long mRequisitionId) {
        this.requisitionId = mRequisitionId;
    }

    public String getRequisitionName() {
        return requisitionName;
    }

    public void setRequisitionName(String requisitionName) {
        this.requisitionName = requisitionName;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long mRfqId) {
        this.quotationId = mRfqId;
    }

    public String getQuotationName() {
        return quotationName;
    }

    public void setQuotationName(String quotationName) {
        this.quotationName = quotationName;
    }

    public Long getReferenceTypeId() {
        return referenceTypeId;
    }

    public void setReferenceTypeId(Long cDocumentTypeId) {
        this.referenceTypeId = cDocumentTypeId;
    }

    public String getReferenceTypeName() {
        return referenceTypeName;
    }

    public void setReferenceTypeName(String referenceTypeName) {
        this.referenceTypeName = referenceTypeName;
    }

    public Long getBiddingTypeId() {
        return biddingTypeId;
    }

    public void setBiddingTypeId(Long cBiddingTypeId) {
        this.biddingTypeId = cBiddingTypeId;
    }

    public String getBiddingTypeName() {
        return biddingTypeName;
    }

    public void setBiddingTypeName(String biddingTypeName) {
        this.biddingTypeName = biddingTypeName;
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long cEventTypeId) {
        this.eventTypeId = cEventTypeId;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    public Long getAdUserUserId() {
        return adUserUserId;
    }

    public void setAdUserUserId(Long adUserUserId) {
        this.adUserUserId = adUserUserId;
    }

    public String getAdUserUserName() {
        return adUserUserName;
    }

    public void setAdUserUserName(String adUserUserName) {
        this.adUserUserName = adUserUserName;
    }

    public List<MBiddingLineDTO> getBiddingLineList() {
        return biddingLineList;
    }

    public void setBiddingLineList(List<MBiddingLineDTO> biddingLineList) {
        this.biddingLineList = biddingLineList;
    }

    public List<MProjectInformationDTO> getProjectInformationList() {
        return projectInformationList;
    }

    public void setProjectInformationList(List<MProjectInformationDTO> projectInformationList) {
        this.projectInformationList = projectInformationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingDTO mBiddingDTO = (MBiddingDTO) o;
        if (mBiddingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", vendorSelection='" + getVendorSelection() + "'" +
            ", ceilingPrice=" + getCeilingPrice() +
            ", estimatedPrice=" + getEstimatedPrice() +
            ", biddingStatus='" + getBiddingStatus() + "'" +
            ", joinedVendorCount=" + getJoinedVendorCount() +
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
            ", currencyId=" + getCurrencyId() +
            ", documentTypeId=" + getDocumentTypeId() +
            ", requisitionId=" + getRequisitionId() +
            ", quotationId=" + getQuotationId() +
            ", referenceTypeId=" + getReferenceTypeId() +
            ", biddingTypeId=" + getBiddingTypeId() +
            ", eventTypeId=" + getEventTypeId() +
            ", adUserUserId=" + getAdUserUserId() +
            "}";
    }
}

package com.bhp.opusb.service.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
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

    private BigDecimal price;

    private BigDecimal priceProposed;

    private String expMailReceipt;

    private String noticePeriod;

    private Integer reminderSent;

    private Integer emailNotification;

    private String termType;

    private String hierarchicalType;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long biddingId;
    private String biddingName;
    private String biddingNo;

    private Long quotationId;

    private Long costCenterId;
    private String costCenterName;


    private Long currencyId;
    private Long warehouseId;
    private Long paymentTermId;

    private List<MContractLineDTO> lineDTOList;

    public List<MContractLineDTO> getLineDTOList() {
        return lineDTOList;
    }

    public List<MRfqSubmissionDTO> getRfqSubmissions() {
        return rfqSubmissions;
    }

    public void setRfqSubmissions(List<MRfqSubmissionDTO> rfqSubmissions) {
        this.rfqSubmissions = rfqSubmissions;
    }

    public void setLineDTOList(List<MContractLineDTO> lineDTOList) {
        this.lineDTOList = lineDTOList;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getPaymentTermId() {
        return paymentTermId;
    }

    public void setPaymentTermId(Long paymentTermId) {
        this.paymentTermId = paymentTermId;
    }

    /**
     * documentType can be New Contract or Contract Renewal.
     */
    @ApiModelProperty(value = "documentType can be New Contract or Contract Renewal.")

    private Long documentTypeId;
    private String documentTypeName;

    private Long picUserId;
    private String picUserName;

    private Long vendorId;
    private String vendorName;

    private Long vendorEvaluationId;
    private String vendorEvaluationName;

    private List<MContractDocumentDTO> contractDocuments = new ArrayList<>();

    private long negoLineId;

    private List<MRfqSubmissionDTO> rfqSubmissions;

    public long getNegoLineId() {
        return negoLineId;
    }

    public void setNegoLineId(long negoLineId) {
        this.negoLineId = negoLineId;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceProposed() {
        return priceProposed;
    }

    public void setPriceProposed(BigDecimal priceProposed) {
        this.priceProposed = priceProposed;
    }

    public String getExpMailReceipt() {
        return expMailReceipt;
    }

    public void setExpMailReceipt(String expMailReceipt) {
        this.expMailReceipt = expMailReceipt;
    }

    public String getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(String noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public Integer getReminderSent() {
        return reminderSent;
    }

    public void setReminderSent(Integer reminderSent) {
        this.reminderSent = reminderSent;
    }

    public Integer getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(Integer emailNotification) {
        this.emailNotification = emailNotification;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getHierarchicalType() {
        return hierarchicalType;
    }

    public void setHierarchicalType(String hierarchicalType) {
        this.hierarchicalType = hierarchicalType;
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

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long mBiddingId) {
        this.biddingId = mBiddingId;
    }

    public String getBiddingName() {
        return biddingName;
    }

    public void setBiddingName(String biddingName) {
        this.biddingName = biddingName;
    }

    public String getBiddingNo() {
        return biddingNo;
    }

    public void setBiddingNo(String biddingNo) {
        this.biddingNo = biddingNo;
    }

    public Long getQuotationId() {
        return quotationId;
    }
    
    public void setQuotationId(Long mRfqId) {
        this.quotationId = mRfqId;
    }

    public Long getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(Long cCostCenterId) {
        this.costCenterId = cCostCenterId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }
    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCurrencyId(Long cCurrencyId) {
        this.currencyId = cCurrencyId;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
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

    public List<MContractDocumentDTO> getContractDocuments() {
        return contractDocuments;
    }

    public void setContractDocuments(List<MContractDocumentDTO> contractDocuments) {
        this.contractDocuments = contractDocuments;
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
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", useBanCode=" + useBanCode +
            ", banCode='" + banCode + '\'' +
            ", purpose='" + purpose + '\'' +
            ", forPriceConfirmation=" + forPriceConfirmation +
            ", startDate=" + startDate +
            ", expirationDate=" + expirationDate +
            ", evaluationPeriod='" + evaluationPeriod + '\'' +
            ", dateTrx=" + dateTrx +
            ", documentNo='" + documentNo + '\'' +
            ", documentAction='" + documentAction + '\'' +
            ", documentStatus='" + documentStatus + '\'' +
            ", approved=" + approved +
            ", processed=" + processed +
            ", dateApprove=" + dateApprove +
            ", dateReject=" + dateReject +
            ", rejectedReason='" + rejectedReason + '\'' +
            ", price=" + price +
            ", priceProposed=" + priceProposed +
            ", expMailReceipt='" + expMailReceipt + '\'' +
            ", noticePeriod='" + noticePeriod + '\'' +
            ", reminderSent=" + reminderSent +
            ", emailNotification=" + emailNotification +
            ", termType='" + termType + '\'' +
            ", hierarchicalType='" + hierarchicalType + '\'' +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", adOrganizationName='" + adOrganizationName + '\'' +
            ", biddingId=" + biddingId +
            ", biddingName='" + biddingName + '\'' +
            ", biddingNo='" + biddingNo + '\'' +
            ", costCenterId=" + costCenterId +
            ", costCenterName='" + costCenterName + '\'' +
            ", currencyId=" + currencyId +
            ", lineDTOList=" + lineDTOList +
            ", documentTypeId=" + documentTypeId +
            ", documentTypeName='" + documentTypeName + '\'' +
            ", picUserId=" + picUserId +
            ", picUserName='" + picUserName + '\'' +
            ", vendorId=" + vendorId +
            ", vendorName='" + vendorName + '\'' +
            ", vendorEvaluationId=" + vendorEvaluationId +
            ", vendorEvaluationName='" + vendorEvaluationName + '\'' +
            ", contractDocuments=" + contractDocuments +
            ", negoLineId=" + negoLineId +
            '}';
    }
}

package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CBudgetPlanLine} entity.
 */
public class CBudgetPlanLineDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal totalDebit;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    @JsonProperty("cCurrencyId")
    private Long cCurrencyId;

    @JsonProperty("cCurrencyName")
    private String cCurrencyName;

    @JsonProperty("cDocumentTypeId")
    private Long cDocumentTypeId;

    @JsonProperty("cDocumentTypeName")
    private String cDocumentTypeName;

    @JsonProperty("cBudgetPlanId")
    private Long cBudgetPlanId;

    @JsonProperty("cBudgetPlanName")
    private Long cBudgetPlanName;

    /**
     * Mandatory for document type Bidding
     */
    @ApiModelProperty(value = "Mandatory for document type Bidding")
    @JsonProperty("mBiddingId")
    private Long mBiddingId;

    @JsonProperty("mBiddingName")
    private String mBiddingName;

    /**
     * Mandatory for document type Purchase Order
     */
    @ApiModelProperty(value = "Mandatory for document type Purchase Order")
    @JsonProperty("mPurchaseOrderId")
    private Long mPurchaseOrderId;

    @JsonProperty("mPurchaseOrderName")
    private String mPurchaseOrderName;

    /**
     * Mandatory for document type Purchase Requisition
     */
    @ApiModelProperty(value = "Mandatory for document type Purchase Requisition")
    @JsonProperty("mRequisitionId")
    private Long mRequisitionId;

    @JsonProperty("mRequisitionName")
    private String mRequisitionName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(BigDecimal totalDebit) {
        this.totalDebit = totalDebit;
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

    public Long getCCurrencyId() {
        return cCurrencyId;
    }

    public void setCCurrencyId(Long cCurrencyId) {
        this.cCurrencyId = cCurrencyId;
    }

    public String getCCurrencyName() {
        return cCurrencyName;
    }

    public void setCCurrencyName(String cCurrencyName) {
        this.cCurrencyName = cCurrencyName;
    }

    public Long getCDocumentTypeId() {
        return cDocumentTypeId;
    }

    public void setCDocumentTypeId(Long cDocumentTypeId) {
        this.cDocumentTypeId = cDocumentTypeId;
    }

    public String getCDocumentTypeName() {
        return cDocumentTypeName;
    }

    public void setCDocumentTypeName(String cDocumentTypeName) {
        this.cDocumentTypeName = cDocumentTypeName;
    }

    public Long getCBudgetPlanId() {
        return cBudgetPlanId;
    }

    public void setCBudgetPlanId(Long cBudgetPlanId) {
        this.cBudgetPlanId = cBudgetPlanId;
    }

    public Long getCBudgetPlanName() {
        return cBudgetPlanName;
    }

    public void setCBudgetPlanName(Long cBudgetPlanName) {
        this.cBudgetPlanName = cBudgetPlanName;
    }

    public Long getMBiddingId() {
        return mBiddingId;
    }

    public void setMBiddingId(Long mBiddingId) {
        this.mBiddingId = mBiddingId;
    }

    public String getMBiddingName() {
        return mBiddingName;
    }

    public void setMBiddingName(String mBiddingName) {
        this.mBiddingName = mBiddingName;
    }

    public Long getMPurchaseOrderId() {
        return mPurchaseOrderId;
    }

    public void setMPurchaseOrderId(Long mPurchaseOrderId) {
        this.mPurchaseOrderId = mPurchaseOrderId;
    }

    public String getMPurchaseOrderName() {
        return mPurchaseOrderName;
    }

    public void setMPurchaseOrderName(String mPurchaseOrderName) {
        this.mPurchaseOrderName = mPurchaseOrderName;
    }

    public Long getMRequisitionId() {
        return mRequisitionId;
    }

    public void setMRequisitionId(Long mRequisitionId) {
        this.mRequisitionId = mRequisitionId;
    }

    public String getMRequisitionName() {
        return mRequisitionName;
    }

    public void setMRequisitionName(String mRequisitionName) {
        this.mRequisitionName = mRequisitionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CBudgetPlanLineDTO cBudgetPlanLineDTO = (CBudgetPlanLineDTO) o;
        if (cBudgetPlanLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cBudgetPlanLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CBudgetPlanLineDTO{" +
            "id=" + getId() +
            ", totalDebit=" + getTotalDebit() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", cCurrencyId=" + getCCurrencyId() +
            ", cDocumentTypeId=" + getCDocumentTypeId() +
            ", cBudgetPlanId=" + getCBudgetPlanId() +
            ", mBiddingId=" + getMBiddingId() +
            ", mPurchaseOrderId=" + getMPurchaseOrderId() +
            ", mRequisitionId=" + getMRequisitionId() +
            "}";
    }
}

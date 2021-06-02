package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingLine} entity.
 */
public class MBiddingLineDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer lineNo;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal ceilingPrice;

    private BigDecimal totalCeilingPrice;

    @NotNull
    private LocalDate deliveryDate;

    private String remark;

    private UUID uid;

    private Boolean active = true;


    private Long subItemId;
    private MBiddingSubItemDTO subItem;

    private Long biddingId;
    private String biddingName;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long costCenterId;
    private String costCenterName;

    private Long productId;
    private String productName;

    private Long uomId;
    private String uomName;

    private BigDecimal proposedPrice, totalPriceSubmission;

    public Long getId() {
        return id;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public BigDecimal getTotalPriceSubmission() {
        return totalPriceSubmission;
    }

    public void setTotalPriceSubmission(BigDecimal totalPriceSubmission) {
        this.totalPriceSubmission = totalPriceSubmission;
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public BigDecimal getTotalCeilingPrice() {
        return totalCeilingPrice;
    }

    public void setTotalCeilingPrice(BigDecimal totalCeilingPrice) {
        this.totalCeilingPrice = totalCeilingPrice;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(Long mBiddingSubItemId) {
        this.subItemId = mBiddingSubItemId;
    }

    public MBiddingSubItemDTO getSubItem() {
        return subItem;
    }

    public void setSubItem(MBiddingSubItemDTO subItem) {
        this.subItem = subItem;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long cProductId) {
        this.productId = cProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long cUnitOfMeasureId) {
        this.uomId = cUnitOfMeasureId;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingLineDTO mBiddingLineDTO = (MBiddingLineDTO) o;
        if (mBiddingLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingLineDTO{" +
            "id=" + getId() +
            ", lineNo=" + getLineNo() +
            ", quantity=" + getQuantity() +
            ", ceilingPrice=" + getCeilingPrice() +
            ", totalCeilingPrice=" + getTotalCeilingPrice() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", remark='" + getRemark() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", subItemId=" + getSubItemId() +
            ", biddingId=" + getBiddingId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", costCenterId=" + getCostCenterId() +
            ", productId=" + getProductId() +
            ", uomId=" + getUomId() +
            "}";
    }
}

package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingLine} entity.
 */
public class MBiddingLineDTO extends AbstractAuditingDTO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal ceilingPrice;

    private BigDecimal totalCeilingPrice;

    @NotNull
    private LocalDate deliveryDate;

    private String remark;

    private BigDecimal grandTotal;

    private UUID uid;

    private Boolean active;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
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
            ", quantity=" + getQuantity() +
            ", ceilingPrice=" + getCeilingPrice() +
            ", totalCeilingPrice=" + getTotalCeilingPrice() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", remark='" + getRemark() + "'" +
            ", grandTotal=" + getGrandTotal() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingId=" + getBiddingId() +
            ", biddingName=" + getBiddingName() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            ", costCenterId=" + getCostCenterId() +
            ", costCenterName=" + getCostCenterName() +
            ", productId=" + getProductId() +
            ", productName=" + getProductName() +
            ", uomId=" + getUomId() +
            ", uomName=" + getUomName() +
            "}";
    }
}

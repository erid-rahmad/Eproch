package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPurchaseOrderLine} entity.
 */
public class MPurchaseOrderLineDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDate dateTrx;

    private LocalDate datePromised;

    @NotNull
    private BigDecimal orderAmount;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal unitPrice;

    private String remark;

    private UUID uid;

    private Boolean active;


    private Long purchaseOrderId;

    private Long requisitionId;
    private String requisitionNo;

    private Long taxId;
    private String taxName, taxCode;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long productId;
    private String productName, productCode;

    private Long warehouseId;
    private String warehouseName;

    private Long costCenterId;
    private String costCenterName;

    private Long uomId;
    private String uomName;

    private Long vendorId;
    private String vendorName;
    
    public Long getId() {
        return id;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getRequisitionNo() {
        return requisitionNo;
    }

    public void setRequisitionNo(String requisitionNo) {
        this.requisitionNo = requisitionNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
    }

    public LocalDate getDatePromised() {
        return datePromised;
    }

    public void setDatePromised(LocalDate datePromised) {
        this.datePromised = datePromised;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long mPurchaseOrderId) {
        this.purchaseOrderId = mPurchaseOrderId;
    }

    public Long getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Long mRequisitionId) {
        this.requisitionId = mRequisitionId;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long cTaxId) {
        this.taxId = cTaxId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String cTaxName) {
        this.taxName = cTaxName;
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long cWarehouseId) {
        this.warehouseId = cWarehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPurchaseOrderLineDTO mPurchaseOrderLineDTO = (MPurchaseOrderLineDTO) o;
        if (mPurchaseOrderLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPurchaseOrderLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPurchaseOrderLineDTO{" +
            "id=" + getId() +
            ", dateTrx='" + getDateTrx() + "'" +
            ", datePromised='" + getDatePromised() + "'" +
            ", orderAmount=" + getOrderAmount() +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", remark='" + getRemark() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", purchaseOrderId=" + getPurchaseOrderId() +
            ", requisitionId=" + getRequisitionId() +
            ", taxId=" + getTaxId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", productId=" + getProductId() +
            ", warehouseId=" + getWarehouseId() +
            ", costCenterId=" + getCostCenterId() +
            ", uomId=" + getUomId() +
            ", vendorId=" + getVendorId() +
            "}";
    }
}

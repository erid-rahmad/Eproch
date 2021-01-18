package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MRequisitionLine} entity.
 */
public class MRequisitionLineDTO extends AbstractAuditingDTO {

    private Long id;

    private LocalDate documentDate;

    private LocalDate datePromised;

    private LocalDate dateRequired;

    @NotNull
    private BigDecimal requisitionAmount;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal unitPrice;

    private String remark;

    private UUID uid;

    private Boolean active;


    private Long requisitionId;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long productId;
    private String productName;

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

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public LocalDate getDatePromised() {
        return datePromised;
    }

    public void setDatePromised(LocalDate datePromised) {
        this.datePromised = datePromised;
    }

    public LocalDate getDateRequired() {
        return dateRequired;
    }

    public void setDateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
    }

    public BigDecimal getRequisitionAmount() {
        return requisitionAmount;
    }

    public void setRequisitionAmount(BigDecimal requisitionAmount) {
        this.requisitionAmount = requisitionAmount;
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

    public Long getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Long mRequisitionId) {
        this.requisitionId = mRequisitionId;
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

        MRequisitionLineDTO mRequisitionLineDTO = (MRequisitionLineDTO) o;
        if (mRequisitionLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mRequisitionLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MRequisitionLineDTO{" +
            "id=" + getId() +
            ", documentDate='" + getDocumentDate() + "'" +
            ", datePromised='" + getDatePromised() + "'" +
            ", dateRequired='" + getDateRequired() + "'" +
            ", requisitionAmount=" + getRequisitionAmount() +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", remark='" + getRemark() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", requisitionId=" + getRequisitionId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            ", productId=" + getProductId() +
            ", productName=" + getProductName() +
            ", warehouseId=" + getWarehouseId() +
            ", warehouseName=" + getWarehouseName() +
            ", costCenterId=" + getCostCenterId() +
            ", costCenterName=" + getCostCenterName() +
            ", uomId=" + getUomId() +
            ", uomName=" + getUomName() +
            ", vendorId=" + getVendorId() +
            ", vendorName=" + getVendorName() +
            "}";
    }
}

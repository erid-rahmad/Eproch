package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MContractLine} entity.
 */
public class MContractLineDTO extends AbstractAuditingDTO {

    private Long id;

    private Integer lineNo;

    @NotNull
    private BigDecimal quantity;

    private BigDecimal quantityBalance;

    @NotNull
    private BigDecimal ceilingPrice;

    private BigDecimal totalCeilingPrice;

    @NotNull
    private LocalDate deliveryDate;

    private String remark;

    private UUID uid;

    private Boolean active;


    private Long contractId;

    private String contractNo;

    private Long adOrganizationId;

    private Long costCenterId;

    private Long productId;
    private String productName;

    private Long uomId;
    private String uomCode;

    private Long vendorId;
    private String vendorName;

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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantityBalance() {
        return quantityBalance;
    }

    public void setQuantityBalance(BigDecimal quantityBalance) {
        this.quantityBalance = quantityBalance;
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

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long mContractId) {
        this.contractId = mContractId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(Long cCostCenterId) {
        this.costCenterId = cCostCenterId;
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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
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

        MContractLineDTO mContractLineDTO = (MContractLineDTO) o;
        if (mContractLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mContractLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MContractLineDTO{" +
            "id=" + getId() +
            ", lineNo=" + getLineNo() +
            ", quantity=" + getQuantity() +
            ", quantityBalance=" + getQuantityBalance() +
            ", ceilingPrice=" + getCeilingPrice() +
            ", totalCeilingPrice=" + getTotalCeilingPrice() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", remark='" + getRemark() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", contractId=" + getContractId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", costCenterId=" + getCostCenterId() +
            ", productId=" + getProductId() +
            ", uomId=" + getUomId() +
            ", vendorId=" + getVendorId() +
            "}";
    }
}

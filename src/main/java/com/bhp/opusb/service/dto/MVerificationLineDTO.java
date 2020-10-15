package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVerificationLine} entity.
 */
public class MVerificationLineDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String orderNo;

    @NotNull
    private String receiptNo;

    @NotNull
    private String deliveryNo;

    private String description;

    @NotNull
    private Long qty;

    @NotNull
    private BigDecimal priceActual;

    @NotNull
    private BigDecimal totalLines;

    @NotNull
    private BigDecimal taxAmount;

    private UUID uid;

    private Boolean active;


    private Long verificationId;

    private Long adOrganizationId;
    private String adOrganizationName;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public BigDecimal getPriceActual() {
        return priceActual;
    }

    public void setPriceActual(BigDecimal priceActual) {
        this.priceActual = priceActual;
    }

    public BigDecimal getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(BigDecimal totalLines) {
        this.totalLines = totalLines;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
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

    public Long getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(Long mVerificationId) {
        this.verificationId = mVerificationId;
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

        MVerificationLineDTO mVerificationLineDTO = (MVerificationLineDTO) o;
        if (mVerificationLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVerificationLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVerificationLineDTO{" +
            "id=" + getId() +
            ", orderNo='" + getOrderNo() + "'" +
            ", receiptNo='" + getReceiptNo() + "'" +
            ", deliveryNo='" + getDeliveryNo() + "'" +
            ", description='" + getDescription() + "'" +
            ", qty=" + getQty() +
            ", priceActual=" + getPriceActual() +
            ", totalLines=" + getTotalLines() +
            ", taxAmount=" + getTaxAmount() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", verificationId=" + getVerificationId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName='" + getAdOrganizationName() + "'" +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            ", uomId=" + getUomId() +
            ", uomName='" + getUomName() + "'" +
            "}";
    }
}

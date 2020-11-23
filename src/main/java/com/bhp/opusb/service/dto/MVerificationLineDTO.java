package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVerificationLine} entity.
 */
public class MVerificationLineDTO extends AbstractAuditingDTO {

    private Long id;

    private String verificationNo;

    @NotNull
    private String poNo;

    @NotNull
    @JsonProperty("receiptNo")
    private String receiveNo;

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

    private String lineNo;

    private String conversionRate;

    private BigDecimal foreignActual;

    private BigDecimal foreignTotalLines;

    private BigDecimal foreignTaxAmount;

    private LocalDate receiveDate;


    private Long verificationId;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long productId;
    private String productName;

    @JsonProperty("cUOM")
    private Long uomId;
    private String uomName;

    @JsonProperty("cElement")
    private Long cElementId;
    private String cElementName;

    @JsonProperty("cCostCenter")
    private Long cCostCenterId;
    private String cCostCenterName;

    @JsonProperty("cCurrency")
    private Long cCurrencyId;
    private String cCurrencyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVerificationNo() {
        return verificationNo;
    }

    public void setVerificationNo(String verificationNo) {
        this.verificationNo = verificationNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getReceiveNo() {
        return receiveNo;
    }

    public void setReceiveNo(String receiveNo) {
        this.receiveNo = receiveNo;
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

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(String conversionRate) {
        this.conversionRate = conversionRate;
    }

    public BigDecimal getForeignActual() {
        return foreignActual;
    }

    public void setForeignActual(BigDecimal foreignActual) {
        this.foreignActual = foreignActual;
    }

    public BigDecimal getForeignTotalLines() {
        return foreignTotalLines;
    }

    public void setForeignTotalLines(BigDecimal foreignTotalLines) {
        this.foreignTotalLines = foreignTotalLines;
    }

    public BigDecimal getForeignTaxAmount() {
        return foreignTaxAmount;
    }

    public void setForeignTaxAmount(BigDecimal foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
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

    public Long getCElementId() {
        return cElementId;
    }

    public void setCElementId(Long cElementValueId) {
        this.cElementId = cElementValueId;
    }

    public String getcElementName() {
        return cElementName;
    }

    public void setcElementName(String cElementName) {
        this.cElementName = cElementName;
    }

    public Long getCCostCenterId() {
        return cCostCenterId;
    }

    public void setCCostCenterId(Long cCostCenterId) {
        this.cCostCenterId = cCostCenterId;
    }

    public String getcCostCenterName() {
        return cCostCenterName;
    }

    public void setcCostCenterName(String cCostCenterName) {
        this.cCostCenterName = cCostCenterName;
    }

    public Long getCCurrencyId() {
        return cCurrencyId;
    }

    public void setCCurrencyId(Long cCurrencyId) {
        this.cCurrencyId = cCurrencyId;
    }

    public String getcCurrencyName() {
        return cCurrencyName;
    }

    public void setcCurrencyName(String cCurrencyName) {
        this.cCurrencyName = cCurrencyName;
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
            ", verificationNo='" + getVerificationNo() + "'" +
            ", poNo='" + getPoNo() + "'" +
            ", receiveNo='" + getReceiveNo() + "'" +
            ", deliveryNo='" + getDeliveryNo() + "'" +
            ", description='" + getDescription() + "'" +
            ", qty=" + getQty() +
            ", priceActual=" + getPriceActual() +
            ", totalLines=" + getTotalLines() +
            ", taxAmount=" + getTaxAmount() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", lineNo='" + getLineNo() + "'" +
            ", conversionRate='" + getConversionRate() + "'" +
            ", foreignActual=" + getForeignActual() +
            ", foreignTotalLines=" + getForeignTotalLines() +
            ", foreignTaxAmount=" + getForeignTaxAmount() +
            ", receiveDate='" + getReceiveDate() + "'" +
            ", verificationId=" + getVerificationId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName='" + getAdOrganizationName() + "'" +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            ", uomId=" + getUomId() +
            ", uomName='" + getUomName() + "'" +
            ", cElementId=" + getCElementId() +
            ", cCostCenterId=" + getCCostCenterId() +
            ", cCurrencyId=" + getCCurrencyId() +
            "}";
    }
}

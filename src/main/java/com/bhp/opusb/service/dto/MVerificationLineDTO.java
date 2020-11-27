package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVerificationLine} entity.
 */
public class MVerificationLineDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String verificationNo;

    @NotNull
    private String poNo;

    @NotNull
    @JsonProperty("receiptNo")
    private String receiveNo;

    @NotNull
    private String deliveryNo;

    /**
     * VDVRMK Supplier's remark.
     */
    @ApiModelProperty(value = "VDVRMK Supplier's remark.")
    private String description;

    @NotNull
    private Long qty;

    /**
     * VDPRRC Unit price.
     */
    @NotNull
    @ApiModelProperty(value = "VDPRRC Unit price.", required = true)
    private BigDecimal priceActual;

    /**
     * VDFRRC Unit price in foreign currency.
     */
    @ApiModelProperty(value = "VDFRRC Unit price in foreign currency.")
    private BigDecimal foreignActual;

    /**
     * VDAREC Receipt amount.
     */
    @NotNull
    @ApiModelProperty(value = "VDAREC Receipt amount.", required = true)
    private BigDecimal totalLines;

    /**
     * VDFREC Receipt amount in foreign currency.
     */
    @ApiModelProperty(value = "VDFREC Receipt amount in foreign currency.")
    private BigDecimal foreignTotalLines;

    /**
     * VDSTAM Tax amount.
     */
    @NotNull
    @ApiModelProperty(value = "VDSTAM Tax amount.", required = true)
    private BigDecimal taxAmount;

    /**
     * VDCTAM Tax amount in foreign currency.
     */
    @ApiModelProperty(value = "VDCTAM Tax amount in foreign currency.")
    private BigDecimal foreignTaxAmount;

    /**
     * VDLINN Invoice verification line no.
     */
    @ApiModelProperty(value = "VDLINN Invoice verification line no.")
    private Integer lineNo;

    /**
     * VDLNID Receipt line no.
     */
    @ApiModelProperty(value = "VDLNID Receipt line no.")
    private Integer lineNoMr;

    private BigDecimal conversionRate;

    /**
     * VDRCDJ Receipt date.
     */
    @ApiModelProperty(value = "VDRCDJ Receipt date.")
    @JsonProperty("receiptDate")
    private LocalDate receiveDate;

    private String payStat;

    private UUID uid;

    private Boolean active;


    private Long verificationId;
    private String verificationName;

    private Long adOrganizationId;
    private String adOrganizationName;

    @JsonProperty("mProductId")
    private Long productId;

    @JsonProperty("mProductCode")
    private String productCode;

    @JsonProperty("mProductShortName")
    private String productShortName;

    @JsonProperty("mProductDescription")
    private String productDescription;

    @JsonProperty("cUomId")
    private Long uomId;

    @JsonProperty("cUomName")
    private String uomName;

    @JsonProperty("cCostCenterId")
    private Long cCostCenterId;

    @JsonProperty("cCostCenterCode")
    private String cCostCenterCode;

    @JsonProperty("cCostCenterShortName")
    private String cCostCenterShortName;

    @JsonProperty("cCurrencyId")
    private Long cCurrencyId;

    @JsonProperty("cCurrencyName")
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

    public BigDecimal getForeignActual() {
        return foreignActual;
    }

    public void setForeignActual(BigDecimal foreignActual) {
        this.foreignActual = foreignActual;
    }

    public BigDecimal getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(BigDecimal totalLines) {
        this.totalLines = totalLines;
    }

    public BigDecimal getForeignTotalLines() {
        return foreignTotalLines;
    }

    public void setForeignTotalLines(BigDecimal foreignTotalLines) {
        this.foreignTotalLines = foreignTotalLines;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getForeignTaxAmount() {
        return foreignTaxAmount;
    }

    public void setForeignTaxAmount(BigDecimal foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public Integer getLineNoMr() {
        return lineNoMr;
    }

    public void setLineNoMr(Integer lineNoMr) {
        this.lineNoMr = lineNoMr;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getPayStat() {
        return payStat;
    }

    public void setPayStat(String payStat) {
        this.payStat = payStat;
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

    public String getVerificationName() {
        return verificationName;
    }

    public void setVerificationName(String verificationName) {
        this.verificationName = verificationName;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductShortName() {
        return productShortName;
    }

    public void setProductShortName(String productShortName) {
        this.productShortName = productShortName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @JsonProperty("mProductName")
    public String getProductName() {
        return productCode + " - " + productShortName;
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

    public Long getCCostCenterId() {
        return cCostCenterId;
    }

    public void setCCostCenterId(Long cCostCenterId) {
        this.cCostCenterId = cCostCenterId;
    }

    public String getCCostCenterCode() {
        return cCostCenterCode;
    }

    public void setCCostCenterCode(String cCostCenterCode) {
        this.cCostCenterCode = cCostCenterCode;
    }

    public String getCCostCenterShortName() {
        return cCostCenterShortName;
    }

    public void setCCostCenterShortName(String cCostCenterShortName) {
        this.cCostCenterShortName = cCostCenterShortName;
    }

    @JsonProperty("cCostCenterName")
    public String getCCostCenterName() {
        return cCostCenterCode + " - " + cCostCenterShortName;
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
            ", foreignActual=" + getForeignActual() +
            ", totalLines=" + getTotalLines() +
            ", foreignTotalLines=" + getForeignTotalLines() +
            ", taxAmount=" + getTaxAmount() +
            ", foreignTaxAmount=" + getForeignTaxAmount() +
            ", lineNo=" + getLineNo() +
            ", lineNoMr=" + getLineNoMr() +
            ", conversionRate=" + getConversionRate() +
            ", receiveDate='" + getReceiveDate() + "'" +
            ", payStat='" + getPayStat() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", verificationId=" + getVerificationId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            ", productId=" + getProductId() +
            ", productName=" + getProductName() +
            ", uomId=" + getUomId() +
            ", uomName=" + getUomName() +
            ", cCostCenterId=" + getCCostCenterId() +
            ", cCostCenterName=" + getCCostCenterName() +
            ", cCurrencyId=" + getCCurrencyId() +
            ", cCurrencyName=" + getCCurrencyName() +
            "}";
    }
}

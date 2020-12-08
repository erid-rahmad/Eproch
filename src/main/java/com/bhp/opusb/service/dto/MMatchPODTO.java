package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MMatchPO} entity.
 */
public class MMatchPODTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * PRSHPN
     */
    @Size(max = 20)
    @ApiModelProperty(value = "PRSHPN")
    private String deliveryNo;

    /**
     * PRDCTO
     */
    @Size(max = 2)
    @ApiModelProperty(value = "PRDCTO")
    @JsonProperty("cDocType")
    private String cDocType;

    /**
     * PRDOCO
     */
    @Size(max = 20)
    @ApiModelProperty(value = "PRDOCO")
    private String poNo;

    /**
     * PRTRDJ
     */
    @ApiModelProperty(value = "PRTRDJ")
    private LocalDate poDate;

    /**
     * PRDOC
     */
    @Size(max = 20)
    @ApiModelProperty(value = "PRDOC")
    private String receiptNo;

    /**
     * PRRCDJ
     */
    @ApiModelProperty(value = "PRRCDJ")
    private LocalDate receiptDate;

    /**
     * PRUREC for Receipt Quantity
     */
    @ApiModelProperty(value = "PRUREC for Receipt Quantity")
    private BigDecimal qty;

    /**
     * PRCRR
     */
    @ApiModelProperty(value = "PRCRR")
    private BigDecimal cConversionRate;

    /**
     * PRUOPN for Open Quantity
     */
    @ApiModelProperty(value = "PRUOPN for Open Quantity")
    private BigDecimal openQty;

    /**
     * PRPRRC for Unit Price
     */
    @ApiModelProperty(value = "PRPRRC for Unit Price")
    private BigDecimal priceActual;

    /**
     * PRFRRC for Unit Price in foreign currency
     */
    @ApiModelProperty(value = "PRFRRC for Unit Price in foreign currency")
    private BigDecimal foreignActual;

    /**
     * PRAOPN for Open Amount
     */
    @ApiModelProperty(value = "PRAOPN for Open Amount")
    private BigDecimal openAmount;

    /**
     * PRFAP for Open Amount in foreign currency
     */
    @ApiModelProperty(value = "PRFAP for Open Amount in foreign currency")
    private BigDecimal openForeignAmount;

    /**
     * PRAREC for Total Line Amount
     */
    @ApiModelProperty(value = "PRAREC for Total Line Amount")
    private BigDecimal totalLines;

    /**
     * PRFREC for Total Line Amount in foreign currency
     */
    @ApiModelProperty(value = "PRFREC for Total Line Amount in foreign currency")
    private BigDecimal foreignTotalLines;

    /**
     * PRSTAM
     */
    @ApiModelProperty(value = "PRSTAM")
    private BigDecimal taxAmount;

    /**
     * PRCTAM
     */
    @ApiModelProperty(value = "PRCTAM")
    private BigDecimal foreignTaxAmount;

    /**
     * PRDGL for GL date
     */
    @ApiModelProperty(value = "PRDGL for GL date")
    private LocalDate dateAccount;

    /**
     * PRDCT
     */
    @Size(max = 2)
    @ApiModelProperty(value = "PRDCT")
    private String cDocTypeMr;

    /**
     * PRSFXO
     */
    @Size(max = 10)
    @ApiModelProperty(value = "PRSFXO")
    private String orderSuffix;

    /**
     * PRLNID for PO line number
     */
    @ApiModelProperty(value = "PRLNID for PO line number")
    private Integer lineNoPo;

    /**
     * PRNLIN for number of lines
     */
    @ApiModelProperty(value = "PRNLIN for number of lines")
    private Integer lineNoMr;

    /**
     * PRTX Y means true, otherwise false
     */
    @ApiModelProperty(value = "PRTX Y means true, otherwise false")
    private Boolean taxable;

    /**
     * PRVRMK
     */
    @Size(max = 30)
    @ApiModelProperty(value = "PRVRMK")
    private String description;

    /**
     * PRMATC
     */
    @Size(max = 1)
    @ApiModelProperty(value = "PRMATC")
    private String mMatchType;

    /**
     * PRKCOO
     */
    @ApiModelProperty(value = "PRKCOO")

    private Long adOrganizationId;
    private String adOrganizationCode;
    private String adOrganizationName;

    @JsonProperty("cCostCenterId")
    private Long cCostCenterId;

    @JsonProperty("cCostCenterName")
    private String cCostCenterName;

    /**
     * PRAN8 is mapped to vendor code.
     */
    @ApiModelProperty(value = "PRAN8 is mapped to vendor code.")
    @JsonProperty("cVendorId")
    private Long cVendorId;

    @JsonProperty("cVendorCode")
    private String cVendorCode;

    @JsonProperty("cVendorShortName")
    private String cVendorShortName;

    /**
     * PRCRCD
     */
    @ApiModelProperty(value = "PRCRCD")
    @JsonProperty("cCurrencyId")
    private Long cCurrencyId;

    @JsonProperty("cCurrencyName")
    private String cCurrencyName;

    /**
     * PREXR1 is mapped to tax category name.
     */
    @ApiModelProperty(value = "PREXR1 is mapped to tax category name.")
    @JsonProperty("cTaxCategoryId")
    private Long cTaxCategoryId;

    @JsonProperty("cTaxCategoryName")
    private String cTaxCategoryName;

    /**
     * PRTXA1 is mapped to tax name.
     */
    @ApiModelProperty(value = "PRTXA1 is mapped to tax name.")
    @JsonProperty("cTaxId")
    private Long cTaxId;

    @JsonProperty("cTaxName")
    private String cTaxName;

    @JsonProperty("cTaxRate")
    private BigDecimal cTaxRate;

    /**
     * PRUOM is mapped to code, name, and symbol.
     */
    @ApiModelProperty(value = "PRUOM is mapped to code, name, and symbol.")
    @JsonProperty("cUomId")
    private Long cUomId;

    @JsonProperty("cUomName")
    private String cUomName;

    /**
     * PRITM is mapped to product code.
     * PRLITM is mapped to product name.
     * PRAITM is mapped to product desc.
     */
    @ApiModelProperty(value = "PRITM is mapped to product code.\nPRLITM is mapped to product name.\nPRAITM is mapped to product desc.")
    @JsonProperty("mProductId")
    private Long mProductId;

    @JsonProperty("mProductCode")
    private String mProductCode;

    @JsonProperty("mProductShortName")
    private String mProductShortName;

    @JsonProperty("mProductDescription")
    private String mProductDescription;

    /**
     * PRMCU is mapped to warehouse code and name.
     */
    @ApiModelProperty(value = "PRMCU is mapped to warehouse code and name.")
    @JsonProperty("mWarehouseId")
    private Long mWarehouseId;

    @JsonProperty("mWarehouseName")
    private String mWarehouseName;

    /**
     * PRLOCN is mapped to locator code.
     */
    @ApiModelProperty(value = "PRLOCN is mapped to locator code.")
    @JsonProperty("mLocatorId")
    private Long mLocatorId;

    @JsonProperty("mLocatorName")
    private String mLocatorName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getcDocType() {
        return cDocType;
    }

    public void setcDocType(String cDocType) {
        this.cDocType = cDocType;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public LocalDate getPoDate() {
        return poDate;
    }

    public void setPoDate(LocalDate poDate) {
        this.poDate = poDate;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getcConversionRate() {
        return cConversionRate;
    }

    public void setcConversionRate(BigDecimal cConversionRate) {
        this.cConversionRate = cConversionRate;
    }

    public BigDecimal getOpenQty() {
        return openQty;
    }

    public void setOpenQty(BigDecimal openQty) {
        this.openQty = openQty;
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

    public BigDecimal getOpenAmount() {
        return openAmount;
    }

    public void setOpenAmount(BigDecimal openAmount) {
        this.openAmount = openAmount;
    }

    public BigDecimal getOpenForeignAmount() {
        return openForeignAmount;
    }

    public void setOpenForeignAmount(BigDecimal openForeignAmount) {
        this.openForeignAmount = openForeignAmount;
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

    public LocalDate getDateAccount() {
        return dateAccount;
    }

    public void setDateAccount(LocalDate dateAccount) {
        this.dateAccount = dateAccount;
    }

    public String getcDocTypeMr() {
        return cDocTypeMr;
    }

    public void setcDocTypeMr(String cDocTypeMr) {
        this.cDocTypeMr = cDocTypeMr;
    }

    public String getOrderSuffix() {
        return orderSuffix;
    }

    public void setOrderSuffix(String orderSuffix) {
        this.orderSuffix = orderSuffix;
    }

    public Integer getLineNoPo() {
        return lineNoPo;
    }

    public void setLineNoPo(Integer lineNoPo) {
        this.lineNoPo = lineNoPo;
    }

    public Integer getLineNoMr() {
        return lineNoMr;
    }

    public void setLineNoMr(Integer lineNoMr) {
        this.lineNoMr = lineNoMr;
    }

    public Boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getmMatchType() {
        return mMatchType;
    }

    public void setmMatchType(String mMatchType) {
        this.mMatchType = mMatchType;
    }

    @JsonProperty("totalAmount")
    public BigDecimal getTotalAmount() {
        if (cTaxRate == null) {
            return totalLines;
        }
        
        BigDecimal tax = totalLines.multiply(cTaxRate).divide(new BigDecimal("100"));
        return totalLines.add(tax);
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public String getAdOrganizationCode() {
        return adOrganizationCode;
    }

    public void setAdOrganizationCode(String adOrganizationCode) {
        this.adOrganizationCode = adOrganizationCode;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getCCostCenterId() {
        return cCostCenterId;
    }

    public void setCCostCenterId(Long cCostCenterId) {
        this.cCostCenterId = cCostCenterId;
    }

    public String getCCostCenterName() {
        return cCostCenterName;
    }

    public void setCCostCenterName(String cCostCenterName) {
        this.cCostCenterName = cCostCenterName;
    }

    public Long getCVendorId() {
        return cVendorId;
    }

    public void setCVendorId(Long cVendorId) {
        this.cVendorId = cVendorId;
    }

    public String getCVendorCode() {
        return cVendorCode;
    }

    public void setCVendorCode(String cVendorCode) {
        this.cVendorCode = cVendorCode;
    }

    public String getCVendorShortName() {
        return cVendorShortName;
    }

    public void setCVendorShortName(String cVendorShortName) {
        this.cVendorShortName = cVendorShortName;
    }

    @JsonProperty("cVendorName")
    public String getCVendorName() {
        return cVendorCode + " - " + cVendorShortName;
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

    public Long getCTaxCategoryId() {
        return cTaxCategoryId;
    }

    public void setCTaxCategoryId(Long cTaxCategoryId) {
        this.cTaxCategoryId = cTaxCategoryId;
    }

    public String getCTaxCategoryName() {
        return cTaxCategoryName;
    }

    public void setCTaxCategoryName(String cTaxCategoryName) {
        this.cTaxCategoryName = cTaxCategoryName;
    }

    public Long getCTaxId() {
        return cTaxId;
    }

    public void setCTaxId(Long cTaxId) {
        this.cTaxId = cTaxId;
    }

    public String getCTaxName() {
        return cTaxName;
    }

    public void setCTaxName(String cTaxName) {
        this.cTaxName = cTaxName;
    }

    public BigDecimal getCTaxRate() {
        return cTaxRate;
    }

    public void setCTaxRate(BigDecimal cTaxRate) {
        this.cTaxRate = cTaxRate;
    }

    public Long getCUomId() {
        return cUomId;
    }

    public void setCUomId(Long cUnitOfMeasureId) {
        this.cUomId = cUnitOfMeasureId;
    }

    public String getCUomName() {
        return cUomName;
    }

    public void setCUomName(String cUomName) {
        this.cUomName = cUomName;
    }

    public Long getMProductId() {
        return mProductId;
    }

    public void setMProductId(Long cProductId) {
        this.mProductId = cProductId;
    }

    public String getMProductCode() {
        return mProductCode;
    }

    public void setMProductCode(String mProductCode) {
        this.mProductCode = mProductCode;
    }

    public String getMProductShortName() {
        return mProductShortName;
    }

    public void setMProductShortName(String mProductShortName) {
        this.mProductShortName = mProductShortName;
    }

    public String getMProductDescription() {
        return mProductDescription;
    }

    public void setMProductDescription(String mProductDescription) {
        this.mProductDescription = mProductDescription;
    }

    @JsonProperty("mProductName")
    public String getMProductName() {
        return mProductCode + " - " + mProductShortName;
    }

    public Long getMWarehouseId() {
        return mWarehouseId;
    }

    public void setMWarehouseId(Long cWarehouseId) {
        this.mWarehouseId = cWarehouseId;
    }

    public String getMWarehouseName() {
        return mWarehouseName;
    }

    public void setMWarehouseName(String mWarehouseName) {
        this.mWarehouseName = mWarehouseName;
    }

    public Long getMLocatorId() {
        return mLocatorId;
    }

    public void setMLocatorId(Long cLocatorId) {
        this.mLocatorId = cLocatorId;
    }

    public String getMLocatorName() {
        return mLocatorName;
    }

    public void setMLocatorName(String mLocatorName) {
        this.mLocatorName = mLocatorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MMatchPODTO mMatchPODTO = (MMatchPODTO) o;
        if (mMatchPODTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mMatchPODTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MMatchPODTO{" +
            "id=" + getId() +
            ", deliveryNo='" + getDeliveryNo() + "'" +
            ", cDocType='" + getcDocType() + "'" +
            ", poNo='" + getPoNo() + "'" +
            ", poDate='" + getPoDate() + "'" +
            ", receiptNo='" + getReceiptNo() + "'" +
            ", receiptDate='" + getReceiptDate() + "'" +
            ", qty=" + getQty() +
            ", cConversionRate=" + getcConversionRate() +
            ", openQty=" + getOpenQty() +
            ", priceActual=" + getPriceActual() +
            ", foreignActual=" + getForeignActual() +
            ", openAmount=" + getOpenAmount() +
            ", openForeignAmount=" + getOpenForeignAmount() +
            ", totalLines=" + getTotalLines() +
            ", foreignTotalLines=" + getForeignTotalLines() +
            ", taxAmount=" + getTaxAmount() +
            ", foreignTaxAmount=" + getForeignTaxAmount() +
            ", dateAccount='" + getDateAccount() + "'" +
            ", cDocTypeMr='" + getcDocTypeMr() + "'" +
            ", orderSuffix='" + getOrderSuffix() + "'" +
            ", lineNoPo=" + getLineNoPo() +
            ", lineNoMr=" + getLineNoMr() +
            ", taxable='" + isTaxable() + "'" +
            ", description='" + getDescription() + "'" +
            ", mMatchType='" + getmMatchType() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", cCostCenterId=" + getCCostCenterId() +
            ", cVendorId=" + getCVendorId() +
            ", cCurrencyId=" + getCCurrencyId() +
            ", cTaxCategoryId=" + getCTaxCategoryId() +
            ", cTaxId=" + getCTaxId() +
            ", cUomId=" + getCUomId() +
            ", mProductId=" + getMProductId() +
            ", mWarehouseId=" + getMWarehouseId() +
            ", mLocatorId=" + getMLocatorId() +
            "}";
    }
}

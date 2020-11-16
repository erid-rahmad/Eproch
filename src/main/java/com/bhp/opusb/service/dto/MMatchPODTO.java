package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MMatchPO} entity.
 */
public class MMatchPODTO extends AbstractAuditingDTO {

    private Long id;

    private String cDocType;

    private String cVendor;

    private String cElement;

    private String cCostCenter;

    private String poNo;

    private LocalDate poDate;

    private String receiptNo;

    private LocalDate receiptDate;

    private String deliveryNo;

    private String mProductCode;

    private String mProductName;

    private String mProductDesc;

    private String cUOM;

    private String qty;

    private String cCurrency;

    private String cConversionRate;

    private String openQty;

    private String priceActual;

    private String foreignActual;

    private String openAmount;

    private String openForeignAmount;

    private String totalLines;

    private String foreignTotalLines;

    private String cTax;

    private String taxAmount;

    private String foreignTaxAmount;

    private String mLocator;

    private String adOrganization;

    private LocalDate dateAccount;

    private String cDocTypeMr;

    private String orderSuffix;

    private String lineNoPo;

    private String lineNoMr;

    private Boolean isTaxable;

    private String cTaxCode;

    private String cTaxName;

    private String description;

    private String mMatchType;

    private String mWarehouse;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getcDocType() {
        return cDocType;
    }

    public void setcDocType(String cDocType) {
        this.cDocType = cDocType;
    }

    public String getcVendor() {
        return cVendor;
    }

    public void setcVendor(String cVendor) {
        this.cVendor = cVendor;
    }

    public String getcElement() {
        return cElement;
    }

    public void setcElement(String cElement) {
        this.cElement = cElement;
    }

    public String getcCostCenter() {
        return cCostCenter;
    }

    public void setcCostCenter(String cCostCenter) {
        this.cCostCenter = cCostCenter;
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

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getmProductCode() {
        return mProductCode;
    }

    public void setmProductCode(String mProductCode) {
        this.mProductCode = mProductCode;
    }

    public String getmProductName() {
        return mProductName;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public String getmProductDesc() {
        return mProductDesc;
    }

    public void setmProductDesc(String mProductDesc) {
        this.mProductDesc = mProductDesc;
    }

    public String getcUOM() {
        return cUOM;
    }

    public void setcUOM(String cUOM) {
        this.cUOM = cUOM;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getcCurrency() {
        return cCurrency;
    }

    public void setcCurrency(String cCurrency) {
        this.cCurrency = cCurrency;
    }

    public String getcConversionRate() {
        return cConversionRate;
    }

    public void setcConversionRate(String cConversionRate) {
        this.cConversionRate = cConversionRate;
    }

    public String getOpenQty() {
        return openQty;
    }

    public void setOpenQty(String openQty) {
        this.openQty = openQty;
    }

    public String getPriceActual() {
        return priceActual;
    }

    public void setPriceActual(String priceActual) {
        this.priceActual = priceActual;
    }

    public String getForeignActual() {
        return foreignActual;
    }

    public void setForeignActual(String foreignActual) {
        this.foreignActual = foreignActual;
    }

    public String getOpenAmount() {
        return openAmount;
    }

    public void setOpenAmount(String openAmount) {
        this.openAmount = openAmount;
    }

    public String getOpenForeignAmount() {
        return openForeignAmount;
    }

    public void setOpenForeignAmount(String openForeignAmount) {
        this.openForeignAmount = openForeignAmount;
    }

    public String getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(String totalLines) {
        this.totalLines = totalLines;
    }

    public String getForeignTotalLines() {
        return foreignTotalLines;
    }

    public void setForeignTotalLines(String foreignTotalLines) {
        this.foreignTotalLines = foreignTotalLines;
    }

    public String getcTax() {
        return cTax;
    }

    public void setcTax(String cTax) {
        this.cTax = cTax;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getForeignTaxAmount() {
        return foreignTaxAmount;
    }

    public void setForeignTaxAmount(String foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
    }

    public String getmLocator() {
        return mLocator;
    }

    public void setmLocator(String mLocator) {
        this.mLocator = mLocator;
    }

    public String getAdOrganization() {
        return adOrganization;
    }

    public void setAdOrganization(String adOrganization) {
        this.adOrganization = adOrganization;
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

    public String getLineNoPo() {
        return lineNoPo;
    }

    public void setLineNoPo(String lineNoPo) {
        this.lineNoPo = lineNoPo;
    }

    public String getLineNoMr() {
        return lineNoMr;
    }

    public void setLineNoMr(String lineNoMr) {
        this.lineNoMr = lineNoMr;
    }

    public Boolean isIsTaxable() {
        return isTaxable;
    }

    public void setIsTaxable(Boolean isTaxable) {
        this.isTaxable = isTaxable;
    }

    public String getcTaxCode() {
        return cTaxCode;
    }

    public void setcTaxCode(String cTaxCode) {
        this.cTaxCode = cTaxCode;
    }

    public String getcTaxName() {
        return cTaxName;
    }

    public void setcTaxName(String cTaxName) {
        this.cTaxName = cTaxName;
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

    public String getmWarehouse() {
        return mWarehouse;
    }

    public void setmWarehouse(String mWarehouse) {
        this.mWarehouse = mWarehouse;
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
            ", cDocType='" + getcDocType() + "'" +
            ", cVendor='" + getcVendor() + "'" +
            ", cElement='" + getcElement() + "'" +
            ", cCostCenter='" + getcCostCenter() + "'" +
            ", poNo='" + getPoNo() + "'" +
            ", poDate='" + getPoDate() + "'" +
            ", receiptNo='" + getReceiptNo() + "'" +
            ", receiptDate='" + getReceiptDate() + "'" +
            ", deliveryNo='" + getDeliveryNo() + "'" +
            ", mProductCode='" + getmProductCode() + "'" +
            ", mProductName='" + getmProductName() + "'" +
            ", mProductDesc='" + getmProductDesc() + "'" +
            ", cUOM='" + getcUOM() + "'" +
            ", qty='" + getQty() + "'" +
            ", cCurrency='" + getcCurrency() + "'" +
            ", cConversionRate='" + getcConversionRate() + "'" +
            ", openQty='" + getOpenQty() + "'" +
            ", priceActual='" + getPriceActual() + "'" +
            ", foreignActual='" + getForeignActual() + "'" +
            ", openAmount='" + getOpenAmount() + "'" +
            ", openForeignAmount='" + getOpenForeignAmount() + "'" +
            ", totalLines='" + getTotalLines() + "'" +
            ", foreignTotalLines='" + getForeignTotalLines() + "'" +
            ", cTax='" + getcTax() + "'" +
            ", taxAmount='" + getTaxAmount() + "'" +
            ", foreignTaxAmount='" + getForeignTaxAmount() + "'" +
            ", mLocator='" + getmLocator() + "'" +
            ", adOrganization='" + getAdOrganization() + "'" +
            ", dateAccount='" + getDateAccount() + "'" +
            ", cDocTypeMr='" + getcDocTypeMr() + "'" +
            ", orderSuffix='" + getOrderSuffix() + "'" +
            ", lineNoPo='" + getLineNoPo() + "'" +
            ", lineNoMr='" + getLineNoMr() + "'" +
            ", isTaxable='" + isIsTaxable() + "'" +
            ", cTaxCode='" + getcTaxCode() + "'" +
            ", cTaxName='" + getcTaxName() + "'" +
            ", description='" + getDescription() + "'" +
            ", mMatchType='" + getmMatchType() + "'" +
            ", mWarehouse='" + getmWarehouse() + "'" +
            "}";
    }
}

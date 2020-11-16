package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MMatchPO} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MMatchPOResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-match-pos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MMatchPOCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cDocType;

    private StringFilter cVendor;

    private StringFilter cElement;

    private StringFilter cCostCenter;

    private StringFilter poNo;

    private LocalDateFilter poDate;

    private StringFilter receiptNo;

    private LocalDateFilter receiptDate;

    private StringFilter deliveryNo;

    private StringFilter mProductCode;

    private StringFilter mProductName;

    private StringFilter mProductDesc;

    private StringFilter cUOM;

    private StringFilter qty;

    private StringFilter cCurrency;

    private StringFilter cConversionRate;

    private StringFilter openQty;

    private StringFilter priceActual;

    private StringFilter foreignActual;

    private StringFilter openAmount;

    private StringFilter openForeignAmount;

    private StringFilter totalLines;

    private StringFilter foreignTotalLines;

    private StringFilter cTax;

    private StringFilter taxAmount;

    private StringFilter foreignTaxAmount;

    private StringFilter mLocator;

    private StringFilter adOrganization;

    private LocalDateFilter dateAccount;

    private StringFilter cDocTypeMr;

    private StringFilter orderSuffix;

    private StringFilter lineNoPo;

    private StringFilter lineNoMr;

    private BooleanFilter isTaxable;

    private StringFilter cTaxCode;

    private StringFilter cTaxName;

    private StringFilter description;

    private StringFilter mMatchType;

    private StringFilter mWarehouse;

    public MMatchPOCriteria() {
    }

    public MMatchPOCriteria(MMatchPOCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cDocType = other.cDocType == null ? null : other.cDocType.copy();
        this.cVendor = other.cVendor == null ? null : other.cVendor.copy();
        this.cElement = other.cElement == null ? null : other.cElement.copy();
        this.cCostCenter = other.cCostCenter == null ? null : other.cCostCenter.copy();
        this.poNo = other.poNo == null ? null : other.poNo.copy();
        this.poDate = other.poDate == null ? null : other.poDate.copy();
        this.receiptNo = other.receiptNo == null ? null : other.receiptNo.copy();
        this.receiptDate = other.receiptDate == null ? null : other.receiptDate.copy();
        this.deliveryNo = other.deliveryNo == null ? null : other.deliveryNo.copy();
        this.mProductCode = other.mProductCode == null ? null : other.mProductCode.copy();
        this.mProductName = other.mProductName == null ? null : other.mProductName.copy();
        this.mProductDesc = other.mProductDesc == null ? null : other.mProductDesc.copy();
        this.cUOM = other.cUOM == null ? null : other.cUOM.copy();
        this.qty = other.qty == null ? null : other.qty.copy();
        this.cCurrency = other.cCurrency == null ? null : other.cCurrency.copy();
        this.cConversionRate = other.cConversionRate == null ? null : other.cConversionRate.copy();
        this.openQty = other.openQty == null ? null : other.openQty.copy();
        this.priceActual = other.priceActual == null ? null : other.priceActual.copy();
        this.foreignActual = other.foreignActual == null ? null : other.foreignActual.copy();
        this.openAmount = other.openAmount == null ? null : other.openAmount.copy();
        this.openForeignAmount = other.openForeignAmount == null ? null : other.openForeignAmount.copy();
        this.totalLines = other.totalLines == null ? null : other.totalLines.copy();
        this.foreignTotalLines = other.foreignTotalLines == null ? null : other.foreignTotalLines.copy();
        this.cTax = other.cTax == null ? null : other.cTax.copy();
        this.taxAmount = other.taxAmount == null ? null : other.taxAmount.copy();
        this.foreignTaxAmount = other.foreignTaxAmount == null ? null : other.foreignTaxAmount.copy();
        this.mLocator = other.mLocator == null ? null : other.mLocator.copy();
        this.adOrganization = other.adOrganization == null ? null : other.adOrganization.copy();
        this.dateAccount = other.dateAccount == null ? null : other.dateAccount.copy();
        this.cDocTypeMr = other.cDocTypeMr == null ? null : other.cDocTypeMr.copy();
        this.orderSuffix = other.orderSuffix == null ? null : other.orderSuffix.copy();
        this.lineNoPo = other.lineNoPo == null ? null : other.lineNoPo.copy();
        this.lineNoMr = other.lineNoMr == null ? null : other.lineNoMr.copy();
        this.isTaxable = other.isTaxable == null ? null : other.isTaxable.copy();
        this.cTaxCode = other.cTaxCode == null ? null : other.cTaxCode.copy();
        this.cTaxName = other.cTaxName == null ? null : other.cTaxName.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.mMatchType = other.mMatchType == null ? null : other.mMatchType.copy();
        this.mWarehouse = other.mWarehouse == null ? null : other.mWarehouse.copy();
    }

    @Override
    public MMatchPOCriteria copy() {
        return new MMatchPOCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getcDocType() {
        return cDocType;
    }

    public void setcDocType(StringFilter cDocType) {
        this.cDocType = cDocType;
    }

    public StringFilter getcVendor() {
        return cVendor;
    }

    public void setcVendor(StringFilter cVendor) {
        this.cVendor = cVendor;
    }

    public StringFilter getcElement() {
        return cElement;
    }

    public void setcElement(StringFilter cElement) {
        this.cElement = cElement;
    }

    public StringFilter getcCostCenter() {
        return cCostCenter;
    }

    public void setcCostCenter(StringFilter cCostCenter) {
        this.cCostCenter = cCostCenter;
    }

    public StringFilter getPoNo() {
        return poNo;
    }

    public void setPoNo(StringFilter poNo) {
        this.poNo = poNo;
    }

    public LocalDateFilter getPoDate() {
        return poDate;
    }

    public void setPoDate(LocalDateFilter poDate) {
        this.poDate = poDate;
    }

    public StringFilter getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(StringFilter receiptNo) {
        this.receiptNo = receiptNo;
    }

    public LocalDateFilter getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDateFilter receiptDate) {
        this.receiptDate = receiptDate;
    }

    public StringFilter getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(StringFilter deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public StringFilter getmProductCode() {
        return mProductCode;
    }

    public void setmProductCode(StringFilter mProductCode) {
        this.mProductCode = mProductCode;
    }

    public StringFilter getmProductName() {
        return mProductName;
    }

    public void setmProductName(StringFilter mProductName) {
        this.mProductName = mProductName;
    }

    public StringFilter getmProductDesc() {
        return mProductDesc;
    }

    public void setmProductDesc(StringFilter mProductDesc) {
        this.mProductDesc = mProductDesc;
    }

    public StringFilter getcUOM() {
        return cUOM;
    }

    public void setcUOM(StringFilter cUOM) {
        this.cUOM = cUOM;
    }

    public StringFilter getQty() {
        return qty;
    }

    public void setQty(StringFilter qty) {
        this.qty = qty;
    }

    public StringFilter getcCurrency() {
        return cCurrency;
    }

    public void setcCurrency(StringFilter cCurrency) {
        this.cCurrency = cCurrency;
    }

    public StringFilter getcConversionRate() {
        return cConversionRate;
    }

    public void setcConversionRate(StringFilter cConversionRate) {
        this.cConversionRate = cConversionRate;
    }

    public StringFilter getOpenQty() {
        return openQty;
    }

    public void setOpenQty(StringFilter openQty) {
        this.openQty = openQty;
    }

    public StringFilter getPriceActual() {
        return priceActual;
    }

    public void setPriceActual(StringFilter priceActual) {
        this.priceActual = priceActual;
    }

    public StringFilter getForeignActual() {
        return foreignActual;
    }

    public void setForeignActual(StringFilter foreignActual) {
        this.foreignActual = foreignActual;
    }

    public StringFilter getOpenAmount() {
        return openAmount;
    }

    public void setOpenAmount(StringFilter openAmount) {
        this.openAmount = openAmount;
    }

    public StringFilter getOpenForeignAmount() {
        return openForeignAmount;
    }

    public void setOpenForeignAmount(StringFilter openForeignAmount) {
        this.openForeignAmount = openForeignAmount;
    }

    public StringFilter getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(StringFilter totalLines) {
        this.totalLines = totalLines;
    }

    public StringFilter getForeignTotalLines() {
        return foreignTotalLines;
    }

    public void setForeignTotalLines(StringFilter foreignTotalLines) {
        this.foreignTotalLines = foreignTotalLines;
    }

    public StringFilter getcTax() {
        return cTax;
    }

    public void setcTax(StringFilter cTax) {
        this.cTax = cTax;
    }

    public StringFilter getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(StringFilter taxAmount) {
        this.taxAmount = taxAmount;
    }

    public StringFilter getForeignTaxAmount() {
        return foreignTaxAmount;
    }

    public void setForeignTaxAmount(StringFilter foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
    }

    public StringFilter getmLocator() {
        return mLocator;
    }

    public void setmLocator(StringFilter mLocator) {
        this.mLocator = mLocator;
    }

    public StringFilter getAdOrganization() {
        return adOrganization;
    }

    public void setAdOrganization(StringFilter adOrganization) {
        this.adOrganization = adOrganization;
    }

    public LocalDateFilter getDateAccount() {
        return dateAccount;
    }

    public void setDateAccount(LocalDateFilter dateAccount) {
        this.dateAccount = dateAccount;
    }

    public StringFilter getcDocTypeMr() {
        return cDocTypeMr;
    }

    public void setcDocTypeMr(StringFilter cDocTypeMr) {
        this.cDocTypeMr = cDocTypeMr;
    }

    public StringFilter getOrderSuffix() {
        return orderSuffix;
    }

    public void setOrderSuffix(StringFilter orderSuffix) {
        this.orderSuffix = orderSuffix;
    }

    public StringFilter getLineNoPo() {
        return lineNoPo;
    }

    public void setLineNoPo(StringFilter lineNoPo) {
        this.lineNoPo = lineNoPo;
    }

    public StringFilter getLineNoMr() {
        return lineNoMr;
    }

    public void setLineNoMr(StringFilter lineNoMr) {
        this.lineNoMr = lineNoMr;
    }

    public BooleanFilter getIsTaxable() {
        return isTaxable;
    }

    public void setIsTaxable(BooleanFilter isTaxable) {
        this.isTaxable = isTaxable;
    }

    public StringFilter getcTaxCode() {
        return cTaxCode;
    }

    public void setcTaxCode(StringFilter cTaxCode) {
        this.cTaxCode = cTaxCode;
    }

    public StringFilter getcTaxName() {
        return cTaxName;
    }

    public void setcTaxName(StringFilter cTaxName) {
        this.cTaxName = cTaxName;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getmMatchType() {
        return mMatchType;
    }

    public void setmMatchType(StringFilter mMatchType) {
        this.mMatchType = mMatchType;
    }

    public StringFilter getmWarehouse() {
        return mWarehouse;
    }

    public void setmWarehouse(StringFilter mWarehouse) {
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
        final MMatchPOCriteria that = (MMatchPOCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cDocType, that.cDocType) &&
            Objects.equals(cVendor, that.cVendor) &&
            Objects.equals(cElement, that.cElement) &&
            Objects.equals(cCostCenter, that.cCostCenter) &&
            Objects.equals(poNo, that.poNo) &&
            Objects.equals(poDate, that.poDate) &&
            Objects.equals(receiptNo, that.receiptNo) &&
            Objects.equals(receiptDate, that.receiptDate) &&
            Objects.equals(deliveryNo, that.deliveryNo) &&
            Objects.equals(mProductCode, that.mProductCode) &&
            Objects.equals(mProductName, that.mProductName) &&
            Objects.equals(mProductDesc, that.mProductDesc) &&
            Objects.equals(cUOM, that.cUOM) &&
            Objects.equals(qty, that.qty) &&
            Objects.equals(cCurrency, that.cCurrency) &&
            Objects.equals(cConversionRate, that.cConversionRate) &&
            Objects.equals(openQty, that.openQty) &&
            Objects.equals(priceActual, that.priceActual) &&
            Objects.equals(foreignActual, that.foreignActual) &&
            Objects.equals(openAmount, that.openAmount) &&
            Objects.equals(openForeignAmount, that.openForeignAmount) &&
            Objects.equals(totalLines, that.totalLines) &&
            Objects.equals(foreignTotalLines, that.foreignTotalLines) &&
            Objects.equals(cTax, that.cTax) &&
            Objects.equals(taxAmount, that.taxAmount) &&
            Objects.equals(foreignTaxAmount, that.foreignTaxAmount) &&
            Objects.equals(mLocator, that.mLocator) &&
            Objects.equals(adOrganization, that.adOrganization) &&
            Objects.equals(dateAccount, that.dateAccount) &&
            Objects.equals(cDocTypeMr, that.cDocTypeMr) &&
            Objects.equals(orderSuffix, that.orderSuffix) &&
            Objects.equals(lineNoPo, that.lineNoPo) &&
            Objects.equals(lineNoMr, that.lineNoMr) &&
            Objects.equals(isTaxable, that.isTaxable) &&
            Objects.equals(cTaxCode, that.cTaxCode) &&
            Objects.equals(cTaxName, that.cTaxName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(mMatchType, that.mMatchType) &&
            Objects.equals(mWarehouse, that.mWarehouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cDocType,
        cVendor,
        cElement,
        cCostCenter,
        poNo,
        poDate,
        receiptNo,
        receiptDate,
        deliveryNo,
        mProductCode,
        mProductName,
        mProductDesc,
        cUOM,
        qty,
        cCurrency,
        cConversionRate,
        openQty,
        priceActual,
        foreignActual,
        openAmount,
        openForeignAmount,
        totalLines,
        foreignTotalLines,
        cTax,
        taxAmount,
        foreignTaxAmount,
        mLocator,
        adOrganization,
        dateAccount,
        cDocTypeMr,
        orderSuffix,
        lineNoPo,
        lineNoMr,
        isTaxable,
        cTaxCode,
        cTaxName,
        description,
        mMatchType,
        mWarehouse
        );
    }

    @Override
    public String toString() {
        return "MMatchPOCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cDocType != null ? "cDocType=" + cDocType + ", " : "") +
                (cVendor != null ? "cVendor=" + cVendor + ", " : "") +
                (cElement != null ? "cElement=" + cElement + ", " : "") +
                (cCostCenter != null ? "cCostCenter=" + cCostCenter + ", " : "") +
                (poNo != null ? "poNo=" + poNo + ", " : "") +
                (poDate != null ? "poDate=" + poDate + ", " : "") +
                (receiptNo != null ? "receiptNo=" + receiptNo + ", " : "") +
                (receiptDate != null ? "receiptDate=" + receiptDate + ", " : "") +
                (deliveryNo != null ? "deliveryNo=" + deliveryNo + ", " : "") +
                (mProductCode != null ? "mProductCode=" + mProductCode + ", " : "") +
                (mProductName != null ? "mProductName=" + mProductName + ", " : "") +
                (mProductDesc != null ? "mProductDesc=" + mProductDesc + ", " : "") +
                (cUOM != null ? "cUOM=" + cUOM + ", " : "") +
                (qty != null ? "qty=" + qty + ", " : "") +
                (cCurrency != null ? "cCurrency=" + cCurrency + ", " : "") +
                (cConversionRate != null ? "cConversionRate=" + cConversionRate + ", " : "") +
                (openQty != null ? "openQty=" + openQty + ", " : "") +
                (priceActual != null ? "priceActual=" + priceActual + ", " : "") +
                (foreignActual != null ? "foreignActual=" + foreignActual + ", " : "") +
                (openAmount != null ? "openAmount=" + openAmount + ", " : "") +
                (openForeignAmount != null ? "openForeignAmount=" + openForeignAmount + ", " : "") +
                (totalLines != null ? "totalLines=" + totalLines + ", " : "") +
                (foreignTotalLines != null ? "foreignTotalLines=" + foreignTotalLines + ", " : "") +
                (cTax != null ? "cTax=" + cTax + ", " : "") +
                (taxAmount != null ? "taxAmount=" + taxAmount + ", " : "") +
                (foreignTaxAmount != null ? "foreignTaxAmount=" + foreignTaxAmount + ", " : "") +
                (mLocator != null ? "mLocator=" + mLocator + ", " : "") +
                (adOrganization != null ? "adOrganization=" + adOrganization + ", " : "") +
                (dateAccount != null ? "dateAccount=" + dateAccount + ", " : "") +
                (cDocTypeMr != null ? "cDocTypeMr=" + cDocTypeMr + ", " : "") +
                (orderSuffix != null ? "orderSuffix=" + orderSuffix + ", " : "") +
                (lineNoPo != null ? "lineNoPo=" + lineNoPo + ", " : "") +
                (lineNoMr != null ? "lineNoMr=" + lineNoMr + ", " : "") +
                (isTaxable != null ? "isTaxable=" + isTaxable + ", " : "") +
                (cTaxCode != null ? "cTaxCode=" + cTaxCode + ", " : "") +
                (cTaxName != null ? "cTaxName=" + cTaxName + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (mMatchType != null ? "mMatchType=" + mMatchType + ", " : "") +
                (mWarehouse != null ? "mWarehouse=" + mWarehouse + ", " : "") +
            "}";
    }

}

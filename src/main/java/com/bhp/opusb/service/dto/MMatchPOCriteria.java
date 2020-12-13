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
import io.github.jhipster.service.filter.BigDecimalFilter;
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

    private StringFilter deliveryNo;

    private StringFilter cDocType;

    private StringFilter poNo;

    private LocalDateFilter poDate;

    private StringFilter receiptNo;

    private LocalDateFilter receiptDate;

    private BigDecimalFilter qty;

    private BigDecimalFilter cConversionRate;

    private BigDecimalFilter openQty;

    private BigDecimalFilter priceActual;

    private BigDecimalFilter foreignActual;

    private BigDecimalFilter openAmount;

    private BigDecimalFilter openForeignAmount;

    private BigDecimalFilter totalLines;

    private BigDecimalFilter foreignTotalLines;

    private BigDecimalFilter taxAmount;

    private BigDecimalFilter foreignTaxAmount;

    private LocalDateFilter dateAccount;

    private StringFilter cDocTypeMr;

    private StringFilter orderSuffix;

    private IntegerFilter lineNoPo;

    private IntegerFilter lineNoMr;

    private BooleanFilter taxable;

    private StringFilter description;

    private StringFilter mMatchType;

    private StringFilter itemDesc1;

    private StringFilter itemDesc2;

    private LongFilter adOrganizationId;

    private LongFilter cCostCenterId;

    private LongFilter cVendorId;

    private LongFilter cCurrencyId;

    private LongFilter cTaxCategoryId;

    private LongFilter cTaxId;

    private LongFilter cUomId;

    private LongFilter mProductId;

    private LongFilter mWarehouseId;

    private LongFilter mLocatorId;

    public MMatchPOCriteria() {
    }

    public MMatchPOCriteria(MMatchPOCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.deliveryNo = other.deliveryNo == null ? null : other.deliveryNo.copy();
        this.cDocType = other.cDocType == null ? null : other.cDocType.copy();
        this.poNo = other.poNo == null ? null : other.poNo.copy();
        this.poDate = other.poDate == null ? null : other.poDate.copy();
        this.receiptNo = other.receiptNo == null ? null : other.receiptNo.copy();
        this.receiptDate = other.receiptDate == null ? null : other.receiptDate.copy();
        this.qty = other.qty == null ? null : other.qty.copy();
        this.cConversionRate = other.cConversionRate == null ? null : other.cConversionRate.copy();
        this.openQty = other.openQty == null ? null : other.openQty.copy();
        this.priceActual = other.priceActual == null ? null : other.priceActual.copy();
        this.foreignActual = other.foreignActual == null ? null : other.foreignActual.copy();
        this.openAmount = other.openAmount == null ? null : other.openAmount.copy();
        this.openForeignAmount = other.openForeignAmount == null ? null : other.openForeignAmount.copy();
        this.totalLines = other.totalLines == null ? null : other.totalLines.copy();
        this.foreignTotalLines = other.foreignTotalLines == null ? null : other.foreignTotalLines.copy();
        this.taxAmount = other.taxAmount == null ? null : other.taxAmount.copy();
        this.foreignTaxAmount = other.foreignTaxAmount == null ? null : other.foreignTaxAmount.copy();
        this.dateAccount = other.dateAccount == null ? null : other.dateAccount.copy();
        this.cDocTypeMr = other.cDocTypeMr == null ? null : other.cDocTypeMr.copy();
        this.orderSuffix = other.orderSuffix == null ? null : other.orderSuffix.copy();
        this.lineNoPo = other.lineNoPo == null ? null : other.lineNoPo.copy();
        this.lineNoMr = other.lineNoMr == null ? null : other.lineNoMr.copy();
        this.taxable = other.taxable == null ? null : other.taxable.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.mMatchType = other.mMatchType == null ? null : other.mMatchType.copy();
        this.itemDesc1 = other.itemDesc1 == null ? null : other.itemDesc1.copy();
        this.itemDesc2 = other.itemDesc2 == null ? null : other.itemDesc2.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.cCostCenterId = other.cCostCenterId == null ? null : other.cCostCenterId.copy();
        this.cVendorId = other.cVendorId == null ? null : other.cVendorId.copy();
        this.cCurrencyId = other.cCurrencyId == null ? null : other.cCurrencyId.copy();
        this.cTaxCategoryId = other.cTaxCategoryId == null ? null : other.cTaxCategoryId.copy();
        this.cTaxId = other.cTaxId == null ? null : other.cTaxId.copy();
        this.cUomId = other.cUomId == null ? null : other.cUomId.copy();
        this.mProductId = other.mProductId == null ? null : other.mProductId.copy();
        this.mWarehouseId = other.mWarehouseId == null ? null : other.mWarehouseId.copy();
        this.mLocatorId = other.mLocatorId == null ? null : other.mLocatorId.copy();
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

    public StringFilter getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(StringFilter deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public StringFilter getcDocType() {
        return cDocType;
    }

    public void setcDocType(StringFilter cDocType) {
        this.cDocType = cDocType;
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

    public BigDecimalFilter getQty() {
        return qty;
    }

    public void setQty(BigDecimalFilter qty) {
        this.qty = qty;
    }

    public BigDecimalFilter getcConversionRate() {
        return cConversionRate;
    }

    public void setcConversionRate(BigDecimalFilter cConversionRate) {
        this.cConversionRate = cConversionRate;
    }

    public BigDecimalFilter getOpenQty() {
        return openQty;
    }

    public void setOpenQty(BigDecimalFilter openQty) {
        this.openQty = openQty;
    }

    public BigDecimalFilter getPriceActual() {
        return priceActual;
    }

    public void setPriceActual(BigDecimalFilter priceActual) {
        this.priceActual = priceActual;
    }

    public BigDecimalFilter getForeignActual() {
        return foreignActual;
    }

    public void setForeignActual(BigDecimalFilter foreignActual) {
        this.foreignActual = foreignActual;
    }

    public BigDecimalFilter getOpenAmount() {
        return openAmount;
    }

    public void setOpenAmount(BigDecimalFilter openAmount) {
        this.openAmount = openAmount;
    }

    public BigDecimalFilter getOpenForeignAmount() {
        return openForeignAmount;
    }

    public void setOpenForeignAmount(BigDecimalFilter openForeignAmount) {
        this.openForeignAmount = openForeignAmount;
    }

    public BigDecimalFilter getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(BigDecimalFilter totalLines) {
        this.totalLines = totalLines;
    }

    public BigDecimalFilter getForeignTotalLines() {
        return foreignTotalLines;
    }

    public void setForeignTotalLines(BigDecimalFilter foreignTotalLines) {
        this.foreignTotalLines = foreignTotalLines;
    }

    public BigDecimalFilter getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimalFilter taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimalFilter getForeignTaxAmount() {
        return foreignTaxAmount;
    }

    public void setForeignTaxAmount(BigDecimalFilter foreignTaxAmount) {
        this.foreignTaxAmount = foreignTaxAmount;
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

    public IntegerFilter getLineNoPo() {
        return lineNoPo;
    }

    public void setLineNoPo(IntegerFilter lineNoPo) {
        this.lineNoPo = lineNoPo;
    }

    public IntegerFilter getLineNoMr() {
        return lineNoMr;
    }

    public void setLineNoMr(IntegerFilter lineNoMr) {
        this.lineNoMr = lineNoMr;
    }

    public BooleanFilter getTaxable() {
        return taxable;
    }

    public void setTaxable(BooleanFilter taxable) {
        this.taxable = taxable;
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

    public StringFilter getItemDesc1() {
        return itemDesc1;
    }

    public void setItemDesc1(StringFilter itemDesc1) {
        this.itemDesc1 = itemDesc1;
    }

    public StringFilter getItemDesc2() {
        return itemDesc2;
    }

    public void setItemDesc2(StringFilter itemDesc2) {
        this.itemDesc2 = itemDesc2;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getCCostCenterId() {
        return cCostCenterId;
    }

    public void setCCostCenterId(LongFilter cCostCenterId) {
        this.cCostCenterId = cCostCenterId;
    }

    public LongFilter getCVendorId() {
        return cVendorId;
    }

    public void setCVendorId(LongFilter cVendorId) {
        this.cVendorId = cVendorId;
    }

    public LongFilter getCCurrencyId() {
        return cCurrencyId;
    }

    public void setCCurrencyId(LongFilter cCurrencyId) {
        this.cCurrencyId = cCurrencyId;
    }

    public LongFilter getCTaxCategoryId() {
        return cTaxCategoryId;
    }

    public void setCTaxCategoryId(LongFilter cTaxCategoryId) {
        this.cTaxCategoryId = cTaxCategoryId;
    }

    public LongFilter getCTaxId() {
        return cTaxId;
    }

    public void setCTaxId(LongFilter cTaxId) {
        this.cTaxId = cTaxId;
    }

    public LongFilter getCUomId() {
        return cUomId;
    }

    public void setCUomId(LongFilter cUomId) {
        this.cUomId = cUomId;
    }

    public LongFilter getMProductId() {
        return mProductId;
    }

    public void setMProductId(LongFilter mProductId) {
        this.mProductId = mProductId;
    }

    public LongFilter getMWarehouseId() {
        return mWarehouseId;
    }

    public void setMWarehouseId(LongFilter mWarehouseId) {
        this.mWarehouseId = mWarehouseId;
    }

    public LongFilter getMLocatorId() {
        return mLocatorId;
    }

    public void setMLocatorId(LongFilter mLocatorId) {
        this.mLocatorId = mLocatorId;
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
            Objects.equals(deliveryNo, that.deliveryNo) &&
            Objects.equals(cDocType, that.cDocType) &&
            Objects.equals(poNo, that.poNo) &&
            Objects.equals(poDate, that.poDate) &&
            Objects.equals(receiptNo, that.receiptNo) &&
            Objects.equals(receiptDate, that.receiptDate) &&
            Objects.equals(qty, that.qty) &&
            Objects.equals(cConversionRate, that.cConversionRate) &&
            Objects.equals(openQty, that.openQty) &&
            Objects.equals(priceActual, that.priceActual) &&
            Objects.equals(foreignActual, that.foreignActual) &&
            Objects.equals(openAmount, that.openAmount) &&
            Objects.equals(openForeignAmount, that.openForeignAmount) &&
            Objects.equals(totalLines, that.totalLines) &&
            Objects.equals(foreignTotalLines, that.foreignTotalLines) &&
            Objects.equals(taxAmount, that.taxAmount) &&
            Objects.equals(foreignTaxAmount, that.foreignTaxAmount) &&
            Objects.equals(dateAccount, that.dateAccount) &&
            Objects.equals(cDocTypeMr, that.cDocTypeMr) &&
            Objects.equals(orderSuffix, that.orderSuffix) &&
            Objects.equals(lineNoPo, that.lineNoPo) &&
            Objects.equals(lineNoMr, that.lineNoMr) &&
            Objects.equals(taxable, that.taxable) &&
            Objects.equals(description, that.description) &&
            Objects.equals(mMatchType, that.mMatchType) &&
            Objects.equals(itemDesc1, that.itemDesc1) &&
            Objects.equals(itemDesc2, that.itemDesc2) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(cCostCenterId, that.cCostCenterId) &&
            Objects.equals(cVendorId, that.cVendorId) &&
            Objects.equals(cCurrencyId, that.cCurrencyId) &&
            Objects.equals(cTaxCategoryId, that.cTaxCategoryId) &&
            Objects.equals(cTaxId, that.cTaxId) &&
            Objects.equals(cUomId, that.cUomId) &&
            Objects.equals(mProductId, that.mProductId) &&
            Objects.equals(mWarehouseId, that.mWarehouseId) &&
            Objects.equals(mLocatorId, that.mLocatorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        deliveryNo,
        cDocType,
        poNo,
        poDate,
        receiptNo,
        receiptDate,
        qty,
        cConversionRate,
        openQty,
        priceActual,
        foreignActual,
        openAmount,
        openForeignAmount,
        totalLines,
        foreignTotalLines,
        taxAmount,
        foreignTaxAmount,
        dateAccount,
        cDocTypeMr,
        orderSuffix,
        lineNoPo,
        lineNoMr,
        taxable,
        description,
        mMatchType,
        itemDesc1,
        itemDesc2,
        adOrganizationId,
        cCostCenterId,
        cVendorId,
        cCurrencyId,
        cTaxCategoryId,
        cTaxId,
        cUomId,
        mProductId,
        mWarehouseId,
        mLocatorId
        );
    }

    @Override
    public String toString() {
        return "MMatchPOCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (deliveryNo != null ? "deliveryNo=" + deliveryNo + ", " : "") +
                (cDocType != null ? "cDocType=" + cDocType + ", " : "") +
                (poNo != null ? "poNo=" + poNo + ", " : "") +
                (poDate != null ? "poDate=" + poDate + ", " : "") +
                (receiptNo != null ? "receiptNo=" + receiptNo + ", " : "") +
                (receiptDate != null ? "receiptDate=" + receiptDate + ", " : "") +
                (qty != null ? "qty=" + qty + ", " : "") +
                (cConversionRate != null ? "cConversionRate=" + cConversionRate + ", " : "") +
                (openQty != null ? "openQty=" + openQty + ", " : "") +
                (priceActual != null ? "priceActual=" + priceActual + ", " : "") +
                (foreignActual != null ? "foreignActual=" + foreignActual + ", " : "") +
                (openAmount != null ? "openAmount=" + openAmount + ", " : "") +
                (openForeignAmount != null ? "openForeignAmount=" + openForeignAmount + ", " : "") +
                (totalLines != null ? "totalLines=" + totalLines + ", " : "") +
                (foreignTotalLines != null ? "foreignTotalLines=" + foreignTotalLines + ", " : "") +
                (taxAmount != null ? "taxAmount=" + taxAmount + ", " : "") +
                (foreignTaxAmount != null ? "foreignTaxAmount=" + foreignTaxAmount + ", " : "") +
                (dateAccount != null ? "dateAccount=" + dateAccount + ", " : "") +
                (cDocTypeMr != null ? "cDocTypeMr=" + cDocTypeMr + ", " : "") +
                (orderSuffix != null ? "orderSuffix=" + orderSuffix + ", " : "") +
                (lineNoPo != null ? "lineNoPo=" + lineNoPo + ", " : "") +
                (lineNoMr != null ? "lineNoMr=" + lineNoMr + ", " : "") +
                (taxable != null ? "taxable=" + taxable + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (mMatchType != null ? "mMatchType=" + mMatchType + ", " : "") +
                (itemDesc1 != null ? "itemDesc1=" + itemDesc1 + ", " : "") +
                (itemDesc2 != null ? "itemDesc2=" + itemDesc2 + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (cCostCenterId != null ? "cCostCenterId=" + cCostCenterId + ", " : "") +
                (cVendorId != null ? "cVendorId=" + cVendorId + ", " : "") +
                (cCurrencyId != null ? "cCurrencyId=" + cCurrencyId + ", " : "") +
                (cTaxCategoryId != null ? "cTaxCategoryId=" + cTaxCategoryId + ", " : "") +
                (cTaxId != null ? "cTaxId=" + cTaxId + ", " : "") +
                (cUomId != null ? "cUomId=" + cUomId + ", " : "") +
                (mProductId != null ? "mProductId=" + mProductId + ", " : "") +
                (mWarehouseId != null ? "mWarehouseId=" + mWarehouseId + ", " : "") +
                (mLocatorId != null ? "mLocatorId=" + mLocatorId + ", " : "") +
            "}";
    }

}

package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MVerificationLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MVerificationLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-verification-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MVerificationLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter verificationNo;

    private StringFilter matchType;

    private StringFilter poNo;

    private StringFilter receiveNo;

    private StringFilter deliveryNo;

    private StringFilter description;

    private StringFilter orderSuffix;

    private LongFilter qty;

    private BigDecimalFilter priceActual;

    private BigDecimalFilter foreignActual;

    private BigDecimalFilter totalLines;

    private BigDecimalFilter foreignTotalLines;

    private BigDecimalFilter taxAmount;

    private BigDecimalFilter foreignTaxAmount;

    private IntegerFilter lineNo;

    private IntegerFilter lineNoPo;

    private IntegerFilter lineNoMr;

    private StringFilter itemDesc1;

    private StringFilter itemDesc2;

    private BigDecimalFilter conversionRate;

    private LocalDateFilter receiveDate;

    private StringFilter payStat;

    private BooleanFilter taxable;

    private StringFilter cDocType;

    private StringFilter cDocTypeMr;

    private BooleanFilter receiptReversed;

    private BooleanFilter apReversed;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter verificationId;

    private LongFilter adOrganizationId;

    private LongFilter productId;

    private LongFilter uomId;

    private LongFilter cCostCenterId;

    private LongFilter cCurrencyId;

    private LongFilter cTaxCategoryId;

    private LongFilter cTaxId;

    public MVerificationLineCriteria() {
    }

    public MVerificationLineCriteria(MVerificationLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.verificationNo = other.verificationNo == null ? null : other.verificationNo.copy();
        this.matchType = other.matchType == null ? null : other.matchType.copy();
        this.poNo = other.poNo == null ? null : other.poNo.copy();
        this.receiveNo = other.receiveNo == null ? null : other.receiveNo.copy();
        this.deliveryNo = other.deliveryNo == null ? null : other.deliveryNo.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.orderSuffix = other.orderSuffix == null ? null : other.orderSuffix.copy();
        this.qty = other.qty == null ? null : other.qty.copy();
        this.priceActual = other.priceActual == null ? null : other.priceActual.copy();
        this.foreignActual = other.foreignActual == null ? null : other.foreignActual.copy();
        this.totalLines = other.totalLines == null ? null : other.totalLines.copy();
        this.foreignTotalLines = other.foreignTotalLines == null ? null : other.foreignTotalLines.copy();
        this.taxAmount = other.taxAmount == null ? null : other.taxAmount.copy();
        this.foreignTaxAmount = other.foreignTaxAmount == null ? null : other.foreignTaxAmount.copy();
        this.lineNo = other.lineNo == null ? null : other.lineNo.copy();
        this.lineNoPo = other.lineNoPo == null ? null : other.lineNoPo.copy();
        this.lineNoMr = other.lineNoMr == null ? null : other.lineNoMr.copy();
        this.itemDesc1 = other.itemDesc1 == null ? null : other.itemDesc1.copy();
        this.itemDesc2 = other.itemDesc2 == null ? null : other.itemDesc2.copy();
        this.conversionRate = other.conversionRate == null ? null : other.conversionRate.copy();
        this.receiveDate = other.receiveDate == null ? null : other.receiveDate.copy();
        this.payStat = other.payStat == null ? null : other.payStat.copy();
        this.taxable = other.taxable == null ? null : other.taxable.copy();
        this.cDocType = other.cDocType == null ? null : other.cDocType.copy();
        this.cDocTypeMr = other.cDocTypeMr == null ? null : other.cDocTypeMr.copy();
        this.receiptReversed = other.receiptReversed == null ? null : other.receiptReversed.copy();
        this.apReversed = other.apReversed == null ? null : other.apReversed.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.verificationId = other.verificationId == null ? null : other.verificationId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.uomId = other.uomId == null ? null : other.uomId.copy();
        this.cCostCenterId = other.cCostCenterId == null ? null : other.cCostCenterId.copy();
        this.cCurrencyId = other.cCurrencyId == null ? null : other.cCurrencyId.copy();
        this.cTaxCategoryId = other.cTaxCategoryId == null ? null : other.cTaxCategoryId.copy();
        this.cTaxId = other.cTaxId == null ? null : other.cTaxId.copy();
    }

    @Override
    public MVerificationLineCriteria copy() {
        return new MVerificationLineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVerificationNo() {
        return verificationNo;
    }

    public void setVerificationNo(StringFilter verificationNo) {
        this.verificationNo = verificationNo;
    }

    public StringFilter getMatchType() {
        return matchType;
    }

    public void setMatchType(StringFilter matchType) {
        this.matchType = matchType;
    }

    public StringFilter getPoNo() {
        return poNo;
    }

    public void setPoNo(StringFilter poNo) {
        this.poNo = poNo;
    }

    public StringFilter getReceiveNo() {
        return receiveNo;
    }

    public void setReceiveNo(StringFilter receiveNo) {
        this.receiveNo = receiveNo;
    }

    public StringFilter getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(StringFilter deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getOrderSuffix() {
        return orderSuffix;
    }

    public void setOrderSuffix(StringFilter orderSuffix) {
        this.orderSuffix = orderSuffix;
    }

    public LongFilter getQty() {
        return qty;
    }

    public void setQty(LongFilter qty) {
        this.qty = qty;
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

    public IntegerFilter getLineNo() {
        return lineNo;
    }

    public void setLineNo(IntegerFilter lineNo) {
        this.lineNo = lineNo;
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

    public BigDecimalFilter getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(BigDecimalFilter conversionRate) {
        this.conversionRate = conversionRate;
    }

    public LocalDateFilter getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDateFilter receiveDate) {
        this.receiveDate = receiveDate;
    }

    public StringFilter getPayStat() {
        return payStat;
    }

    public void setPayStat(StringFilter payStat) {
        this.payStat = payStat;
    }

    public BooleanFilter getTaxable() {
        return taxable;
    }

    public void setTaxable(BooleanFilter taxable) {
        this.taxable = taxable;
    }

    public StringFilter getcDocType() {
        return cDocType;
    }

    public void setcDocType(StringFilter cDocType) {
        this.cDocType = cDocType;
    }

    public StringFilter getcDocTypeMr() {
        return cDocTypeMr;
    }

    public void setcDocTypeMr(StringFilter cDocTypeMr) {
        this.cDocTypeMr = cDocTypeMr;
    }

    public BooleanFilter getReceiptReversed() {
        return receiptReversed;
    }

    public void setReceiptReversed(BooleanFilter receiptReversed) {
        this.receiptReversed = receiptReversed;
    }

    public BooleanFilter getApReversed() {
        return apReversed;
    }

    public void setApReversed(BooleanFilter apReversed) {
        this.apReversed = apReversed;
    }

    public UUIDFilter getUid() {
        return uid;
    }

    public void setUid(UUIDFilter uid) {
        this.uid = uid;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(LongFilter verificationId) {
        this.verificationId = verificationId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getUomId() {
        return uomId;
    }

    public void setUomId(LongFilter uomId) {
        this.uomId = uomId;
    }

    public LongFilter getCCostCenterId() {
        return cCostCenterId;
    }

    public void setCCostCenterId(LongFilter cCostCenterId) {
        this.cCostCenterId = cCostCenterId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MVerificationLineCriteria that = (MVerificationLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(verificationNo, that.verificationNo) &&
            Objects.equals(matchType, that.matchType) &&
            Objects.equals(poNo, that.poNo) &&
            Objects.equals(receiveNo, that.receiveNo) &&
            Objects.equals(deliveryNo, that.deliveryNo) &&
            Objects.equals(description, that.description) &&
            Objects.equals(orderSuffix, that.orderSuffix) &&
            Objects.equals(qty, that.qty) &&
            Objects.equals(priceActual, that.priceActual) &&
            Objects.equals(foreignActual, that.foreignActual) &&
            Objects.equals(totalLines, that.totalLines) &&
            Objects.equals(foreignTotalLines, that.foreignTotalLines) &&
            Objects.equals(taxAmount, that.taxAmount) &&
            Objects.equals(foreignTaxAmount, that.foreignTaxAmount) &&
            Objects.equals(lineNo, that.lineNo) &&
            Objects.equals(lineNoPo, that.lineNoPo) &&
            Objects.equals(lineNoMr, that.lineNoMr) &&
            Objects.equals(itemDesc1, that.itemDesc1) &&
            Objects.equals(itemDesc2, that.itemDesc2) &&
            Objects.equals(conversionRate, that.conversionRate) &&
            Objects.equals(receiveDate, that.receiveDate) &&
            Objects.equals(payStat, that.payStat) &&
            Objects.equals(taxable, that.taxable) &&
            Objects.equals(cDocType, that.cDocType) &&
            Objects.equals(cDocTypeMr, that.cDocTypeMr) &&
            Objects.equals(receiptReversed, that.receiptReversed) &&
            Objects.equals(apReversed, that.apReversed) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(verificationId, that.verificationId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(uomId, that.uomId) &&
            Objects.equals(cCostCenterId, that.cCostCenterId) &&
            Objects.equals(cCurrencyId, that.cCurrencyId) &&
            Objects.equals(cTaxCategoryId, that.cTaxCategoryId) &&
            Objects.equals(cTaxId, that.cTaxId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        verificationNo,
        matchType,
        poNo,
        receiveNo,
        deliveryNo,
        description,
        orderSuffix,
        qty,
        priceActual,
        foreignActual,
        totalLines,
        foreignTotalLines,
        taxAmount,
        foreignTaxAmount,
        lineNo,
        lineNoPo,
        lineNoMr,
        itemDesc1,
        itemDesc2,
        conversionRate,
        receiveDate,
        payStat,
        taxable,
        cDocType,
        cDocTypeMr,
        receiptReversed,
        apReversed,
        uid,
        active,
        verificationId,
        adOrganizationId,
        productId,
        uomId,
        cCostCenterId,
        cCurrencyId,
        cTaxCategoryId,
        cTaxId
        );
    }

    @Override
    public String toString() {
        return "MVerificationLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (verificationNo != null ? "verificationNo=" + verificationNo + ", " : "") +
                (matchType != null ? "matchType=" + matchType + ", " : "") +
                (poNo != null ? "poNo=" + poNo + ", " : "") +
                (receiveNo != null ? "receiveNo=" + receiveNo + ", " : "") +
                (deliveryNo != null ? "deliveryNo=" + deliveryNo + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (orderSuffix != null ? "orderSuffix=" + orderSuffix + ", " : "") +
                (qty != null ? "qty=" + qty + ", " : "") +
                (priceActual != null ? "priceActual=" + priceActual + ", " : "") +
                (foreignActual != null ? "foreignActual=" + foreignActual + ", " : "") +
                (totalLines != null ? "totalLines=" + totalLines + ", " : "") +
                (foreignTotalLines != null ? "foreignTotalLines=" + foreignTotalLines + ", " : "") +
                (taxAmount != null ? "taxAmount=" + taxAmount + ", " : "") +
                (foreignTaxAmount != null ? "foreignTaxAmount=" + foreignTaxAmount + ", " : "") +
                (lineNo != null ? "lineNo=" + lineNo + ", " : "") +
                (lineNoPo != null ? "lineNoPo=" + lineNoPo + ", " : "") +
                (lineNoMr != null ? "lineNoMr=" + lineNoMr + ", " : "") +
                (itemDesc1 != null ? "itemDesc1=" + itemDesc1 + ", " : "") +
                (itemDesc2 != null ? "itemDesc2=" + itemDesc2 + ", " : "") +
                (conversionRate != null ? "conversionRate=" + conversionRate + ", " : "") +
                (receiveDate != null ? "receiveDate=" + receiveDate + ", " : "") +
                (payStat != null ? "payStat=" + payStat + ", " : "") +
                (taxable != null ? "taxable=" + taxable + ", " : "") +
                (cDocType != null ? "cDocType=" + cDocType + ", " : "") +
                (cDocTypeMr != null ? "cDocTypeMr=" + cDocTypeMr + ", " : "") +
                (receiptReversed != null ? "receiptReversed=" + receiptReversed + ", " : "") +
                (apReversed != null ? "apReversed=" + apReversed + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (verificationId != null ? "verificationId=" + verificationId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (uomId != null ? "uomId=" + uomId + ", " : "") +
                (cCostCenterId != null ? "cCostCenterId=" + cCostCenterId + ", " : "") +
                (cCurrencyId != null ? "cCurrencyId=" + cCurrencyId + ", " : "") +
                (cTaxCategoryId != null ? "cTaxCategoryId=" + cTaxCategoryId + ", " : "") +
                (cTaxId != null ? "cTaxId=" + cTaxId + ", " : "") +
            "}";
    }

}

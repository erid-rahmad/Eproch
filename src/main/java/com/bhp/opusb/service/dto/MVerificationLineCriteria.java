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

    private StringFilter orderNo;

    private StringFilter receiptNo;

    private StringFilter deliveryNo;

    private StringFilter description;

    private LongFilter qty;

    private BigDecimalFilter priceActual;

    private BigDecimalFilter totalLines;

    private BigDecimalFilter taxAmount;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter verificationId;

    private LongFilter adOrganizationId;

    private LongFilter productId;

    private LongFilter uomId;

    public MVerificationLineCriteria() {
    }

    public MVerificationLineCriteria(MVerificationLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderNo = other.orderNo == null ? null : other.orderNo.copy();
        this.receiptNo = other.receiptNo == null ? null : other.receiptNo.copy();
        this.deliveryNo = other.deliveryNo == null ? null : other.deliveryNo.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.qty = other.qty == null ? null : other.qty.copy();
        this.priceActual = other.priceActual == null ? null : other.priceActual.copy();
        this.totalLines = other.totalLines == null ? null : other.totalLines.copy();
        this.taxAmount = other.taxAmount == null ? null : other.taxAmount.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.verificationId = other.verificationId == null ? null : other.verificationId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.uomId = other.uomId == null ? null : other.uomId.copy();
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

    public StringFilter getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(StringFilter orderNo) {
        this.orderNo = orderNo;
    }

    public StringFilter getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(StringFilter receiptNo) {
        this.receiptNo = receiptNo;
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

    public BigDecimalFilter getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(BigDecimalFilter totalLines) {
        this.totalLines = totalLines;
    }

    public BigDecimalFilter getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimalFilter taxAmount) {
        this.taxAmount = taxAmount;
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
            Objects.equals(orderNo, that.orderNo) &&
            Objects.equals(receiptNo, that.receiptNo) &&
            Objects.equals(deliveryNo, that.deliveryNo) &&
            Objects.equals(description, that.description) &&
            Objects.equals(qty, that.qty) &&
            Objects.equals(priceActual, that.priceActual) &&
            Objects.equals(totalLines, that.totalLines) &&
            Objects.equals(taxAmount, that.taxAmount) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(verificationId, that.verificationId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(uomId, that.uomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orderNo,
        receiptNo,
        deliveryNo,
        description,
        qty,
        priceActual,
        totalLines,
        taxAmount,
        uid,
        active,
        verificationId,
        adOrganizationId,
        productId,
        uomId
        );
    }

    @Override
    public String toString() {
        return "MVerificationLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orderNo != null ? "orderNo=" + orderNo + ", " : "") +
                (receiptNo != null ? "receiptNo=" + receiptNo + ", " : "") +
                (deliveryNo != null ? "deliveryNo=" + deliveryNo + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (qty != null ? "qty=" + qty + ", " : "") +
                (priceActual != null ? "priceActual=" + priceActual + ", " : "") +
                (totalLines != null ? "totalLines=" + totalLines + ", " : "") +
                (taxAmount != null ? "taxAmount=" + taxAmount + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (verificationId != null ? "verificationId=" + verificationId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (uomId != null ? "uomId=" + uomId + ", " : "") +
            "}";
    }

}

package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MAuctionItem} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MAuctionItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-auction-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MAuctionItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter auctionStatus;

    private BigDecimalFilter quantity;

    private BigDecimalFilter ceilingPrice;

    private BigDecimalFilter amount;

    private BigDecimalFilter bidDecrement;

    private BigDecimalFilter protectBackBuffer;

    private BigDecimalFilter protectFrontBuffer;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter auctionId;

    private LongFilter productId;

    private LongFilter uomId;

    public MAuctionItemCriteria() {
    }

    public MAuctionItemCriteria(MAuctionItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.auctionStatus = other.auctionStatus == null ? null : other.auctionStatus.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.ceilingPrice = other.ceilingPrice == null ? null : other.ceilingPrice.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.bidDecrement = other.bidDecrement == null ? null : other.bidDecrement.copy();
        this.protectBackBuffer = other.protectBackBuffer == null ? null : other.protectBackBuffer.copy();
        this.protectFrontBuffer = other.protectFrontBuffer == null ? null : other.protectFrontBuffer.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.auctionId = other.auctionId == null ? null : other.auctionId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.uomId = other.uomId == null ? null : other.uomId.copy();
    }

    @Override
    public MAuctionItemCriteria copy() {
        return new MAuctionItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(StringFilter auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
    }

    public BigDecimalFilter getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(BigDecimalFilter ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public BigDecimalFilter getBidDecrement() {
        return bidDecrement;
    }

    public void setBidDecrement(BigDecimalFilter bidDecrement) {
        this.bidDecrement = bidDecrement;
    }

    public BigDecimalFilter getProtectBackBuffer() {
        return protectBackBuffer;
    }

    public void setProtectBackBuffer(BigDecimalFilter protectBackBuffer) {
        this.protectBackBuffer = protectBackBuffer;
    }

    public BigDecimalFilter getProtectFrontBuffer() {
        return protectFrontBuffer;
    }

    public void setProtectFrontBuffer(BigDecimalFilter protectFrontBuffer) {
        this.protectFrontBuffer = protectFrontBuffer;
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

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(LongFilter auctionId) {
        this.auctionId = auctionId;
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
        final MAuctionItemCriteria that = (MAuctionItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(auctionStatus, that.auctionStatus) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(ceilingPrice, that.ceilingPrice) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(bidDecrement, that.bidDecrement) &&
            Objects.equals(protectBackBuffer, that.protectBackBuffer) &&
            Objects.equals(protectFrontBuffer, that.protectFrontBuffer) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(auctionId, that.auctionId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(uomId, that.uomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        auctionStatus,
        quantity,
        ceilingPrice,
        amount,
        bidDecrement,
        protectBackBuffer,
        protectFrontBuffer,
        uid,
        active,
        adOrganizationId,
        auctionId,
        productId,
        uomId
        );
    }

    @Override
    public String toString() {
        return "MAuctionItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (auctionStatus != null ? "auctionStatus=" + auctionStatus + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (ceilingPrice != null ? "ceilingPrice=" + ceilingPrice + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (bidDecrement != null ? "bidDecrement=" + bidDecrement + ", " : "") +
                (protectBackBuffer != null ? "protectBackBuffer=" + protectBackBuffer + ", " : "") +
                (protectFrontBuffer != null ? "protectFrontBuffer=" + protectFrontBuffer + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (auctionId != null ? "auctionId=" + auctionId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (uomId != null ? "uomId=" + uomId + ", " : "") +
            "}";
    }

}

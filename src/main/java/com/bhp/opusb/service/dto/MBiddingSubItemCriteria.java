package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MBiddingSubItem} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBiddingSubItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-bidding-sub-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBiddingSubItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter totalAmount;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter mBiddingSubItemLineId;

    private LongFilter adOrganizationId;

    private LongFilter productId;

    private LongFilter biddingLineId;

    public MBiddingSubItemCriteria() {
    }

    public MBiddingSubItemCriteria(MBiddingSubItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.totalAmount = other.totalAmount == null ? null : other.totalAmount.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.mBiddingSubItemLineId = other.mBiddingSubItemLineId == null ? null : other.mBiddingSubItemLineId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.biddingLineId = other.biddingLineId == null ? null : other.biddingLineId.copy();
    }

    @Override
    public MBiddingSubItemCriteria copy() {
        return new MBiddingSubItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimalFilter totalAmount) {
        this.totalAmount = totalAmount;
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

    public LongFilter getMBiddingSubItemLineId() {
        return mBiddingSubItemLineId;
    }

    public void setMBiddingSubItemLineId(LongFilter mBiddingSubItemLineId) {
        this.mBiddingSubItemLineId = mBiddingSubItemLineId;
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

    public LongFilter getBiddingLineId() {
        return biddingLineId;
    }

    public void setBiddingLineId(LongFilter biddingLineId) {
        this.biddingLineId = biddingLineId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MBiddingSubItemCriteria that = (MBiddingSubItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(mBiddingSubItemLineId, that.mBiddingSubItemLineId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(biddingLineId, that.biddingLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        totalAmount,
        uid,
        active,
        mBiddingSubItemLineId,
        adOrganizationId,
        productId,
        biddingLineId
        );
    }

    @Override
    public String toString() {
        return "MBiddingSubItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (mBiddingSubItemLineId != null ? "mBiddingSubItemLineId=" + mBiddingSubItemLineId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (biddingLineId != null ? "biddingLineId=" + biddingLineId + ", " : "") +
            "}";
    }

}

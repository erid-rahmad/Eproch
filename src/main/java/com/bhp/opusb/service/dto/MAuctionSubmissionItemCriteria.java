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
 * Criteria class for the {@link com.bhp.opusb.domain.MAuctionSubmissionItem} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MAuctionSubmissionItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-auction-submission-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MAuctionSubmissionItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter price;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter auctionSubmissionId;

    private LongFilter auctionItemId;

    private LongFilter auctionId;

    private LongFilter vendorId;

    public MAuctionSubmissionItemCriteria() {
    }

    public MAuctionSubmissionItemCriteria(MAuctionSubmissionItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.auctionSubmissionId = other.auctionSubmissionId == null ? null : other.auctionSubmissionId.copy();
        this.auctionItemId = other.auctionItemId == null ? null : other.auctionItemId.copy();
        this.auctionId = other.auctionId == null ? null : other.auctionId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
    }

    @Override
    public MAuctionSubmissionItemCriteria copy() {
        return new MAuctionSubmissionItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
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

    public LongFilter getAuctionSubmissionId() {
        return auctionSubmissionId;
    }

    public void setAuctionSubmissionId(LongFilter auctionSubmissionId) {
        this.auctionSubmissionId = auctionSubmissionId;
    }

    public LongFilter getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(LongFilter auctionItemId) {
        this.auctionItemId = auctionItemId;
    }

    public LongFilter getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(LongFilter auctionId) {
        this.auctionId = auctionId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MAuctionSubmissionItemCriteria that = (MAuctionSubmissionItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(price, that.price) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(auctionSubmissionId, that.auctionSubmissionId) &&
            Objects.equals(auctionItemId, that.auctionItemId) &&
            Objects.equals(auctionId, that.auctionId) &&
            Objects.equals(vendorId, that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        price,
        uid,
        active,
        adOrganizationId,
        auctionSubmissionId,
        auctionItemId,
        auctionId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "MAuctionSubmissionItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (auctionSubmissionId != null ? "auctionSubmissionId=" + auctionSubmissionId + ", " : "") +
                (auctionItemId != null ? "auctionItemId=" + auctionItemId + ", " : "") +
                (auctionId != null ? "auctionId=" + auctionId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}

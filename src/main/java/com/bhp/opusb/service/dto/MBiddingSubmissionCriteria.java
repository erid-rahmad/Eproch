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
 * Criteria class for the {@link com.bhp.opusb.domain.MBiddingSubmission} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBiddingSubmissionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-bidding-submissions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBiddingSubmissionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter joinBidding;

    private BigDecimalFilter proposedPrice;

    private BigDecimalFilter ceilingPrice;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter mBiddingSubmissionLineId;

    private LongFilter biddingId;

    private LongFilter biddingScheduleId;

    private LongFilter vendorId;

    private LongFilter adOrganizationId;

    public MBiddingSubmissionCriteria() {
    }

    public MBiddingSubmissionCriteria(MBiddingSubmissionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.joinBidding = other.joinBidding == null ? null : other.joinBidding.copy();
        this.proposedPrice = other.proposedPrice == null ? null : other.proposedPrice.copy();
        this.ceilingPrice = other.ceilingPrice == null ? null : other.ceilingPrice.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.mBiddingSubmissionLineId = other.mBiddingSubmissionLineId == null ? null : other.mBiddingSubmissionLineId.copy();
        this.biddingId = other.biddingId == null ? null : other.biddingId.copy();
        this.biddingScheduleId = other.biddingScheduleId == null ? null : other.biddingScheduleId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public MBiddingSubmissionCriteria copy() {
        return new MBiddingSubmissionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getJoinBidding() {
        return joinBidding;
    }

    public void setJoinBidding(StringFilter joinBidding) {
        this.joinBidding = joinBidding;
    }

    public BigDecimalFilter getProposedPrice() {
        return proposedPrice;
    }

    public void setProposedPrice(BigDecimalFilter proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public BigDecimalFilter getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(BigDecimalFilter ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
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

    public LongFilter getMBiddingSubmissionLineId() {
        return mBiddingSubmissionLineId;
    }

    public void setMBiddingSubmissionLineId(LongFilter mBiddingSubmissionLineId) {
        this.mBiddingSubmissionLineId = mBiddingSubmissionLineId;
    }

    public LongFilter getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(LongFilter biddingId) {
        this.biddingId = biddingId;
    }

    public LongFilter getBiddingScheduleId() {
        return biddingScheduleId;
    }

    public void setBiddingScheduleId(LongFilter biddingScheduleId) {
        this.biddingScheduleId = biddingScheduleId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MBiddingSubmissionCriteria that = (MBiddingSubmissionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(joinBidding, that.joinBidding) &&
            Objects.equals(proposedPrice, that.proposedPrice) &&
            Objects.equals(ceilingPrice, that.ceilingPrice) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(mBiddingSubmissionLineId, that.mBiddingSubmissionLineId) &&
            Objects.equals(biddingId, that.biddingId) &&
            Objects.equals(biddingScheduleId, that.biddingScheduleId) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        joinBidding,
        proposedPrice,
        ceilingPrice,
        uid,
        active,
        mBiddingSubmissionLineId,
        biddingId,
        biddingScheduleId,
        vendorId,
        adOrganizationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MBiddingSubmissionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (joinBidding != null ? "joinBidding=" + joinBidding + ", " : "") +
                (proposedPrice != null ? "proposedPrice=" + proposedPrice + ", " : "") +
                (ceilingPrice != null ? "ceilingPrice=" + ceilingPrice + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (mBiddingSubmissionLineId != null ? "mBiddingSubmissionLineId=" + mBiddingSubmissionLineId + ", " : "") +
                (biddingId != null ? "biddingId=" + biddingId + ", " : "") +
                (biddingScheduleId != null ? "biddingScheduleId=" + biddingScheduleId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

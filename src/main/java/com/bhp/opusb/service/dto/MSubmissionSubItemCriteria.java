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
 * Criteria class for the {@link com.bhp.opusb.domain.MSubmissionSubItem} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MSubmissionSubItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-submission-sub-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MSubmissionSubItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter proposedPrice;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter biddingSubItemId;

    private LongFilter adOrganizationId;

    private LongFilter mBiddingSubmissionLineId;

    public MSubmissionSubItemCriteria() {
    }

    public MSubmissionSubItemCriteria(MSubmissionSubItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.proposedPrice = other.proposedPrice == null ? null : other.proposedPrice.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.biddingSubItemId = other.biddingSubItemId == null ? null : other.biddingSubItemId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.mBiddingSubmissionLineId = other.mBiddingSubmissionLineId == null ? null : other.mBiddingSubmissionLineId.copy();
    }

    @Override
    public MSubmissionSubItemCriteria copy() {
        return new MSubmissionSubItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getProposedPrice() {
        return proposedPrice;
    }

    public void setProposedPrice(BigDecimalFilter proposedPrice) {
        this.proposedPrice = proposedPrice;
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

    public LongFilter getBiddingSubItemId() {
        return biddingSubItemId;
    }

    public void setBiddingSubItemId(LongFilter biddingSubItemId) {
        this.biddingSubItemId = biddingSubItemId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getMBiddingSubmissionLineId() {
        return mBiddingSubmissionLineId;
    }

    public void setMBiddingSubmissionLineId(LongFilter mBiddingSubmissionLineId) {
        this.mBiddingSubmissionLineId = mBiddingSubmissionLineId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MSubmissionSubItemCriteria that = (MSubmissionSubItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(proposedPrice, that.proposedPrice) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(biddingSubItemId, that.biddingSubItemId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(mBiddingSubmissionLineId, that.mBiddingSubmissionLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        proposedPrice,
        uid,
        active,
        biddingSubItemId,
        adOrganizationId,
        mBiddingSubmissionLineId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MSubmissionSubItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (proposedPrice != null ? "proposedPrice=" + proposedPrice + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (biddingSubItemId != null ? "biddingSubItemId=" + biddingSubItemId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (mBiddingSubmissionLineId != null ? "mBiddingSubmissionLineId=" + mBiddingSubmissionLineId + ", " : "") +
            "}";
    }

}

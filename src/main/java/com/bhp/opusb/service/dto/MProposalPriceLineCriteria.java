package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MProposalPriceLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MProposalPriceLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-proposal-price-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MProposalPriceLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter proposedPrice;

    private BigDecimalFilter totalPriceSubmission;

    private LocalDateFilter deliveryDate;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter mProposalPriceSubItemId;

    private LongFilter adOrganizationId;

    private LongFilter proposalPriceId;

    private LongFilter biddingLineId;

    public MProposalPriceLineCriteria() {
    }

    public MProposalPriceLineCriteria(MProposalPriceLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.proposedPrice = other.proposedPrice == null ? null : other.proposedPrice.copy();
        this.totalPriceSubmission = other.totalPriceSubmission == null ? null : other.totalPriceSubmission.copy();
        this.deliveryDate = other.deliveryDate == null ? null : other.deliveryDate.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.mProposalPriceSubItemId = other.mProposalPriceSubItemId == null ? null : other.mProposalPriceSubItemId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.proposalPriceId = other.proposalPriceId == null ? null : other.proposalPriceId.copy();
        this.biddingLineId = other.biddingLineId == null ? null : other.biddingLineId.copy();
    }

    @Override
    public MProposalPriceLineCriteria copy() {
        return new MProposalPriceLineCriteria(this);
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

    public BigDecimalFilter getTotalPriceSubmission() {
        return totalPriceSubmission;
    }

    public void setTotalPriceSubmission(BigDecimalFilter totalPriceSubmission) {
        this.totalPriceSubmission = totalPriceSubmission;
    }

    public LocalDateFilter getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateFilter deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public LongFilter getMProposalPriceSubItemId() {
        return mProposalPriceSubItemId;
    }

    public void setMProposalPriceSubItemId(LongFilter mProposalPriceSubItemId) {
        this.mProposalPriceSubItemId = mProposalPriceSubItemId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getProposalPriceId() {
        return proposalPriceId;
    }

    public void setProposalPriceId(LongFilter proposalPriceId) {
        this.proposalPriceId = proposalPriceId;
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
        final MProposalPriceLineCriteria that = (MProposalPriceLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(proposedPrice, that.proposedPrice) &&
            Objects.equals(totalPriceSubmission, that.totalPriceSubmission) &&
            Objects.equals(deliveryDate, that.deliveryDate) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(mProposalPriceSubItemId, that.mProposalPriceSubItemId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(proposalPriceId, that.proposalPriceId) &&
            Objects.equals(biddingLineId, that.biddingLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        proposedPrice,
        totalPriceSubmission,
        deliveryDate,
        uid,
        active,
        mProposalPriceSubItemId,
        adOrganizationId,
        proposalPriceId,
        biddingLineId
        );
    }

    @Override
    public String toString() {
        return "MProposalPriceLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (proposedPrice != null ? "proposedPrice=" + proposedPrice + ", " : "") +
                (totalPriceSubmission != null ? "totalPriceSubmission=" + totalPriceSubmission + ", " : "") +
                (deliveryDate != null ? "deliveryDate=" + deliveryDate + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (mProposalPriceSubItemId != null ? "mProposalPriceSubItemId=" + mProposalPriceSubItemId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (proposalPriceId != null ? "proposalPriceId=" + proposalPriceId + ", " : "") +
                (biddingLineId != null ? "biddingLineId=" + biddingLineId + ", " : "") +
            "}";
    }

}

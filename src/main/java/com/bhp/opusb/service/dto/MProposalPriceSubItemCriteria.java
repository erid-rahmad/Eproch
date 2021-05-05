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
 * Criteria class for the {@link com.bhp.opusb.domain.MProposalPriceSubItem} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MProposalPriceSubItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-proposal-price-sub-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MProposalPriceSubItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter proposedPrice;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter biddingSubItemLineId;

    private LongFilter proposalPriceLineId;

    public MProposalPriceSubItemCriteria() {
    }

    public MProposalPriceSubItemCriteria(MProposalPriceSubItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.proposedPrice = other.proposedPrice == null ? null : other.proposedPrice.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.biddingSubItemLineId = other.biddingSubItemLineId == null ? null : other.biddingSubItemLineId.copy();
        this.proposalPriceLineId = other.proposalPriceLineId == null ? null : other.proposalPriceLineId.copy();
    }

    @Override
    public MProposalPriceSubItemCriteria copy() {
        return new MProposalPriceSubItemCriteria(this);
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

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getBiddingSubItemLineId() {
        return biddingSubItemLineId;
    }

    public void setBiddingSubItemLineId(LongFilter biddingSubItemLineId) {
        this.biddingSubItemLineId = biddingSubItemLineId;
    }

    public LongFilter getProposalPriceLineId() {
        return proposalPriceLineId;
    }

    public void setProposalPriceLineId(LongFilter proposalPriceLineId) {
        this.proposalPriceLineId = proposalPriceLineId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MProposalPriceSubItemCriteria that = (MProposalPriceSubItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(proposedPrice, that.proposedPrice) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(biddingSubItemLineId, that.biddingSubItemLineId) &&
            Objects.equals(proposalPriceLineId, that.proposalPriceLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        proposedPrice,
        uid,
        active,
        adOrganizationId,
        biddingSubItemLineId,
        proposalPriceLineId
        );
    }

    @Override
    public String toString() {
        return "MProposalPriceSubItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (proposedPrice != null ? "proposedPrice=" + proposedPrice + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (biddingSubItemLineId != null ? "biddingSubItemLineId=" + biddingSubItemLineId + ", " : "") +
                (proposalPriceLineId != null ? "proposalPriceLineId=" + proposalPriceLineId + ", " : "") +
            "}";
    }

}

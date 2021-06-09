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
 * Criteria class for the {@link com.bhp.opusb.domain.MBidNegoPrice} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBidNegoPriceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-bid-nego-prices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBidNegoPriceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private BigDecimalFilter negotiationPrice;

    private LongFilter attachmentId;

    private LongFilter biddingId;

    private LongFilter priceProposalId;

    private LongFilter negotiationLineId;

    public MBidNegoPriceCriteria() {
    }

    public MBidNegoPriceCriteria(MBidNegoPriceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.negotiationPrice = other.negotiationPrice == null ? null : other.negotiationPrice.copy();
        this.attachmentId = other.attachmentId == null ? null : other.attachmentId.copy();
        this.biddingId = other.biddingId == null ? null : other.biddingId.copy();
        this.priceProposalId = other.priceProposalId == null ? null : other.priceProposalId.copy();
        this.negotiationLineId = other.negotiationLineId == null ? null : other.negotiationLineId.copy();
    }

    @Override
    public MBidNegoPriceCriteria copy() {
        return new MBidNegoPriceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public BigDecimalFilter getNegotiationPrice() {
        return negotiationPrice;
    }

    public void setNegotiationPrice(BigDecimalFilter negotiationPrice) {
        this.negotiationPrice = negotiationPrice;
    }

    public LongFilter getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(LongFilter attachmentId) {
        this.attachmentId = attachmentId;
    }

    public LongFilter getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(LongFilter biddingId) {
        this.biddingId = biddingId;
    }

    public LongFilter getPriceProposalId() {
        return priceProposalId;
    }

    public void setPriceProposalId(LongFilter priceProposalId) {
        this.priceProposalId = priceProposalId;
    }

    public LongFilter getNegotiationLineId() {
        return negotiationLineId;
    }

    public void setNegotiationLineId(LongFilter negotiationLineId) {
        this.negotiationLineId = negotiationLineId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MBidNegoPriceCriteria that = (MBidNegoPriceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(negotiationPrice, that.negotiationPrice) &&
            Objects.equals(attachmentId, that.attachmentId) &&
            Objects.equals(biddingId, that.biddingId) &&
            Objects.equals(priceProposalId, that.priceProposalId) &&
            Objects.equals(negotiationLineId, that.negotiationLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        negotiationPrice,
        attachmentId,
        biddingId,
        priceProposalId,
        negotiationLineId
        );
    }

    @Override
    public String toString() {
        return "MBidNegoPriceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (negotiationPrice != null ? "negotiationPrice=" + negotiationPrice + ", " : "") +
                (attachmentId != null ? "attachmentId=" + attachmentId + ", " : "") +
                (biddingId != null ? "biddingId=" + biddingId + ", " : "") +
                (priceProposalId != null ? "priceProposalId=" + priceProposalId + ", " : "") +
                (negotiationLineId != null ? "negotiationLineId=" + negotiationLineId + ", " : "") +
            "}";
    }

}

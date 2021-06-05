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
 * Criteria class for the {@link com.bhp.opusb.domain.MBidNegoPriceLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBidNegoPriceLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-bid-nego-price-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBidNegoPriceLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private BigDecimalFilter priceNegotiation;

    private BigDecimalFilter totalNegotiationPrice;

    private BigDecimalFilter negotiationPercentage;

    private LongFilter bidNegoPriceId;

    private LongFilter biddingLineId;

    private LongFilter proposalLineId;

    public MBidNegoPriceLineCriteria() {
    }

    public MBidNegoPriceLineCriteria(MBidNegoPriceLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.priceNegotiation = other.priceNegotiation == null ? null : other.priceNegotiation.copy();
        this.totalNegotiationPrice = other.totalNegotiationPrice == null ? null : other.totalNegotiationPrice.copy();
        this.negotiationPercentage = other.negotiationPercentage == null ? null : other.negotiationPercentage.copy();
        this.bidNegoPriceId = other.bidNegoPriceId == null ? null : other.bidNegoPriceId.copy();
        this.biddingLineId = other.biddingLineId == null ? null : other.biddingLineId.copy();
        this.proposalLineId = other.proposalLineId == null ? null : other.proposalLineId.copy();
    }

    @Override
    public MBidNegoPriceLineCriteria copy() {
        return new MBidNegoPriceLineCriteria(this);
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

    public BigDecimalFilter getPriceNegotiation() {
        return priceNegotiation;
    }

    public void setPriceNegotiation(BigDecimalFilter priceNegotiation) {
        this.priceNegotiation = priceNegotiation;
    }

    public BigDecimalFilter getTotalNegotiationPrice() {
        return totalNegotiationPrice;
    }

    public void setTotalNegotiationPrice(BigDecimalFilter totalNegotiationPrice) {
        this.totalNegotiationPrice = totalNegotiationPrice;
    }

    public BigDecimalFilter getNegotiationPercentage() {
        return negotiationPercentage;
    }

    public void setNegotiationPercentage(BigDecimalFilter negotiationPercentage) {
        this.negotiationPercentage = negotiationPercentage;
    }

    public LongFilter getBidNegoPriceId() {
        return bidNegoPriceId;
    }

    public void setBidNegoPriceId(LongFilter bidNegoPriceId) {
        this.bidNegoPriceId = bidNegoPriceId;
    }

    public LongFilter getBiddingLineId() {
        return biddingLineId;
    }

    public void setBiddingLineId(LongFilter biddingLineId) {
        this.biddingLineId = biddingLineId;
    }

    public LongFilter getProposalLineId() {
        return proposalLineId;
    }

    public void setProposalLineId(LongFilter proposalLineId) {
        this.proposalLineId = proposalLineId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MBidNegoPriceLineCriteria that = (MBidNegoPriceLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(priceNegotiation, that.priceNegotiation) &&
            Objects.equals(totalNegotiationPrice, that.totalNegotiationPrice) &&
            Objects.equals(negotiationPercentage, that.negotiationPercentage) &&
            Objects.equals(bidNegoPriceId, that.bidNegoPriceId) &&
            Objects.equals(biddingLineId, that.biddingLineId) &&
            Objects.equals(proposalLineId, that.proposalLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        priceNegotiation,
        totalNegotiationPrice,
        negotiationPercentage,
        bidNegoPriceId,
        biddingLineId,
        proposalLineId
        );
    }

    @Override
    public String toString() {
        return "MBidNegoPriceLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (priceNegotiation != null ? "priceNegotiation=" + priceNegotiation + ", " : "") +
                (totalNegotiationPrice != null ? "totalNegotiationPrice=" + totalNegotiationPrice + ", " : "") +
                (negotiationPercentage != null ? "negotiationPercentage=" + negotiationPercentage + ", " : "") +
                (bidNegoPriceId != null ? "bidNegoPriceId=" + bidNegoPriceId + ", " : "") +
                (biddingLineId != null ? "biddingLineId=" + biddingLineId + ", " : "") +
                (proposalLineId != null ? "proposalLineId=" + proposalLineId + ", " : "") +
            "}";
    }

}

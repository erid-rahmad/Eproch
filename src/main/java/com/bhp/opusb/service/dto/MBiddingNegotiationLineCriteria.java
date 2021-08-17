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
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MBiddingNegotiationLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBiddingNegotiationLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-bidding-negotiation-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBiddingNegotiationLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter negotiationStatus;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter negotiationId;

    private LongFilter biddingEvalResultId;

    private LongFilter biddingId;

    public MBiddingNegotiationLineCriteria() {
    }

    public LongFilter getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(LongFilter biddingId) {
        this.biddingId = biddingId;
    }

    public MBiddingNegotiationLineCriteria(MBiddingNegotiationLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.negotiationStatus = other.negotiationStatus == null ? null : other.negotiationStatus.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.negotiationId = other.negotiationId == null ? null : other.negotiationId.copy();
        this.biddingEvalResultId = other.biddingEvalResultId == null ? null : other.biddingEvalResultId.copy();
        this.biddingId = other.biddingId == null ? null : other.biddingId.copy();
    }

    @Override
    public MBiddingNegotiationLineCriteria copy() {
        return new MBiddingNegotiationLineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNegotiationStatus() {
        return negotiationStatus;
    }

    public void setNegotiationStatus(StringFilter negotiationStatus) {
        this.negotiationStatus = negotiationStatus;
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

    public LongFilter getNegotiationId() {
        return negotiationId;
    }

    public void setNegotiationId(LongFilter negotiationId) {
        this.negotiationId = negotiationId;
    }

    public LongFilter getBiddingEvalResultId() {
        return biddingEvalResultId;
    }

    public void setBiddingEvalResultId(LongFilter biddingEvalResultId) {
        this.biddingEvalResultId = biddingEvalResultId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MBiddingNegotiationLineCriteria that = (MBiddingNegotiationLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(negotiationStatus, that.negotiationStatus) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(negotiationId, that.negotiationId) &&
            Objects.equals(biddingEvalResultId, that.biddingEvalResultId) &&
            Objects.equals(biddingId, that.biddingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        negotiationStatus,
        uid,
        active,
        adOrganizationId,
        negotiationId,
        biddingEvalResultId,
        biddingId
        );
    }

    @Override
    public String toString() {
        return "MBiddingNegotiationLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (negotiationStatus != null ? "negotiationStatus=" + negotiationStatus + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (negotiationId != null ? "negotiationId=" + negotiationId + ", " : "") +
                (biddingEvalResultId != null ? "biddingEvalResultId=" + biddingEvalResultId + ", " : "") +
                (biddingId != null ? "biddingId=" + biddingId + ", " : "") +
            "}";
    }

}

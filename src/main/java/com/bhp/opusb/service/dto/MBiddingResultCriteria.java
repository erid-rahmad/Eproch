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
 * Criteria class for the {@link com.bhp.opusb.domain.MBiddingResult} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBiddingResultResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-bidding-results?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBiddingResultCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter announcementResultId;

    private LongFilter biddingId;

    private LongFilter vendorId;

    private LongFilter biddingEvalResultId;

    public MBiddingResultCriteria() {
    }

    public MBiddingResultCriteria(MBiddingResultCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.announcementResultId = other.announcementResultId == null ? null : other.announcementResultId.copy();
        this.biddingId = other.biddingId == null ? null : other.biddingId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.biddingEvalResultId = other.biddingEvalResultId == null ? null : other.biddingEvalResultId.copy();
    }

    @Override
    public MBiddingResultCriteria copy() {
        return new MBiddingResultCriteria(this);
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

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getAnnouncementResultId() {
        return announcementResultId;
    }

    public void setAnnouncementResultId(LongFilter announcementResultId) {
        this.announcementResultId = announcementResultId;
    }

    public LongFilter getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(LongFilter biddingId) {
        this.biddingId = biddingId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
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
        final MBiddingResultCriteria that = (MBiddingResultCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(announcementResultId, that.announcementResultId) &&
            Objects.equals(biddingId, that.biddingId) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(biddingEvalResultId, that.biddingEvalResultId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        adOrganizationId,
        announcementResultId,
        biddingId,
        vendorId,
        biddingEvalResultId
        );
    }

    @Override
    public String toString() {
        return "MBiddingResultCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (announcementResultId != null ? "announcementResultId=" + announcementResultId + ", " : "") +
                (biddingId != null ? "biddingId=" + biddingId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (biddingEvalResultId != null ? "biddingEvalResultId=" + biddingEvalResultId + ", " : "") +
            "}";
    }

}

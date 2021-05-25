package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MAuctionParticipant} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MAuctionParticipantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-auction-participants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MAuctionParticipantCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter auctionId;

    private LongFilter userUserId;

    private LongFilter vendorId;

    public MAuctionParticipantCriteria() {
    }

    public MAuctionParticipantCriteria(MAuctionParticipantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.auctionId = other.auctionId == null ? null : other.auctionId.copy();
        this.userUserId = other.userUserId == null ? null : other.userUserId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
    }

    @Override
    public MAuctionParticipantCriteria copy() {
        return new MAuctionParticipantCriteria(this);
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

    public LongFilter getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(LongFilter auctionId) {
        this.auctionId = auctionId;
    }

    public LongFilter getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(LongFilter userId) {
        this.userUserId = userId;
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
        final MAuctionParticipantCriteria that = (MAuctionParticipantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(auctionId, that.auctionId) &&
            Objects.equals(userUserId, that.userUserId) &&
            Objects.equals(vendorId, that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        adOrganizationId,
        auctionId,
        userUserId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "MAuctionParticipantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (auctionId != null ? "auctionId=" + auctionId + ", " : "") +
                (userUserId != null ? "userId=" + userUserId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}

package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.UUIDFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MPreBidMeetingParticipant} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MPreBidMeetingParticipantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-pre-bid-meeting-participants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MPreBidMeetingParticipantCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter attended;

    private ZonedDateTimeFilter registerDate;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter preBidMeetingId;

    private LongFilter vendorId;

    public MPreBidMeetingParticipantCriteria() {
    }

    public MPreBidMeetingParticipantCriteria(MPreBidMeetingParticipantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.attended = other.attended == null ? null : other.attended.copy();
        this.registerDate = other.registerDate == null ? null : other.registerDate.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.preBidMeetingId = other.preBidMeetingId == null ? null : other.preBidMeetingId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
    }

    @Override
    public MPreBidMeetingParticipantCriteria copy() {
        return new MPreBidMeetingParticipantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getAttended() {
        return attended;
    }

    public void setAttended(BooleanFilter attended) {
        this.attended = attended;
    }

    public ZonedDateTimeFilter getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(ZonedDateTimeFilter registerDate) {
        this.registerDate = registerDate;
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

    public LongFilter getPreBidMeetingId() {
        return preBidMeetingId;
    }

    public void setPreBidMeetingId(LongFilter preBidMeetingId) {
        this.preBidMeetingId = preBidMeetingId;
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
        final MPreBidMeetingParticipantCriteria that = (MPreBidMeetingParticipantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(attended, that.attended) &&
            Objects.equals(registerDate, that.registerDate) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(preBidMeetingId, that.preBidMeetingId) &&
            Objects.equals(vendorId, that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        attended,
        registerDate,
        uid,
        active,
        adOrganizationId,
        preBidMeetingId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "MPreBidMeetingParticipantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (attended != null ? "attended=" + attended + ", " : "") +
                (registerDate != null ? "registerDate=" + registerDate + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (preBidMeetingId != null ? "preBidMeetingId=" + preBidMeetingId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}

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
 * Criteria class for the {@link com.bhp.opusb.domain.MVendorConfirmationLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MVendorConfirmationLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-vendor-confirmation-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MVendorConfirmationLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter status;

    private LongFilter adOrganizationId;

    private LongFilter vendorId;

    private LongFilter biddingEvalResultId;

    private LongFilter vendorConfirmationId;

    public MVendorConfirmationLineCriteria() {
    }

    public MVendorConfirmationLineCriteria(MVendorConfirmationLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.biddingEvalResultId = other.biddingEvalResultId == null ? null : other.biddingEvalResultId.copy();
        this.vendorConfirmationId = other.vendorConfirmationId == null ? null : other.vendorConfirmationId.copy();
    }

    @Override
    public MVendorConfirmationLineCriteria copy() {
        return new MVendorConfirmationLineCriteria(this);
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

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
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

    public LongFilter getVendorConfirmationId() {
        return vendorConfirmationId;
    }

    public void setVendorConfirmationId(LongFilter vendorConfirmationId) {
        this.vendorConfirmationId = vendorConfirmationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MVendorConfirmationLineCriteria that = (MVendorConfirmationLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(status, that.status) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(biddingEvalResultId, that.biddingEvalResultId) &&
            Objects.equals(vendorConfirmationId, that.vendorConfirmationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        status,
        adOrganizationId,
        vendorId,
        biddingEvalResultId,
        vendorConfirmationId
        );
    }

    @Override
    public String toString() {
        return "MVendorConfirmationLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (biddingEvalResultId != null ? "biddingEvalResultId=" + biddingEvalResultId + ", " : "") +
                (vendorConfirmationId != null ? "vendorConfirmationId=" + vendorConfirmationId + ", " : "") +
            "}";
    }

}

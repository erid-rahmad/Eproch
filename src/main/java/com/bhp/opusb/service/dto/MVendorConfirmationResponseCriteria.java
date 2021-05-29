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
 * Criteria class for the {@link com.bhp.opusb.domain.MVendorConfirmationResponse} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MVendorConfirmationResponseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-vendor-confirmation-responses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MVendorConfirmationResponseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter vendorConfirmationLineId;

    private LongFilter vendorConfirmationContractId;

    public MVendorConfirmationResponseCriteria() {
    }

    public MVendorConfirmationResponseCriteria(MVendorConfirmationResponseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.vendorConfirmationLineId = other.vendorConfirmationLineId == null ? null : other.vendorConfirmationLineId.copy();
        this.vendorConfirmationContractId = other.vendorConfirmationContractId == null ? null : other.vendorConfirmationContractId.copy();
    }

    @Override
    public MVendorConfirmationResponseCriteria copy() {
        return new MVendorConfirmationResponseCriteria(this);
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

    public LongFilter getVendorConfirmationLineId() {
        return vendorConfirmationLineId;
    }

    public void setVendorConfirmationLineId(LongFilter vendorConfirmationLineId) {
        this.vendorConfirmationLineId = vendorConfirmationLineId;
    }

    public LongFilter getVendorConfirmationContractId() {
        return vendorConfirmationContractId;
    }

    public void setVendorConfirmationContractId(LongFilter vendorConfirmationContractId) {
        this.vendorConfirmationContractId = vendorConfirmationContractId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MVendorConfirmationResponseCriteria that = (MVendorConfirmationResponseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(vendorConfirmationLineId, that.vendorConfirmationLineId) &&
            Objects.equals(vendorConfirmationContractId, that.vendorConfirmationContractId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        adOrganizationId,
        vendorConfirmationLineId,
        vendorConfirmationContractId
        );
    }

    @Override
    public String toString() {
        return "MVendorConfirmationResponseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (vendorConfirmationLineId != null ? "vendorConfirmationLineId=" + vendorConfirmationLineId + ", " : "") +
                (vendorConfirmationContractId != null ? "vendorConfirmationContractId=" + vendorConfirmationContractId + ", " : "") +
            "}";
    }

}

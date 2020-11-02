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
 * Criteria class for the {@link com.bhp.opusb.domain.CVendorLocation} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CVendorLocationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-vendor-locations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CVendorLocationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter taxInvoiceAddress;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter vendorId;

    private LongFilter locationId;

    private LongFilter adOrganizationId;

    public CVendorLocationCriteria() {
    }

    public CVendorLocationCriteria(CVendorLocationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.taxInvoiceAddress = other.taxInvoiceAddress == null ? null : other.taxInvoiceAddress.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public CVendorLocationCriteria copy() {
        return new CVendorLocationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getTaxInvoiceAddress() {
        return taxInvoiceAddress;
    }

    public void setTaxInvoiceAddress(BooleanFilter taxInvoiceAddress) {
        this.taxInvoiceAddress = taxInvoiceAddress;
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

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CVendorLocationCriteria that = (CVendorLocationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(taxInvoiceAddress, that.taxInvoiceAddress) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        taxInvoiceAddress,
        uid,
        active,
        vendorId,
        locationId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "CVendorLocationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (taxInvoiceAddress != null ? "taxInvoiceAddress=" + taxInvoiceAddress + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

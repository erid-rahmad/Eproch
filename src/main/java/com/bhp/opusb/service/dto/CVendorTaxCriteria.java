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
 * Criteria class for the {@link com.bhp.opusb.domain.CVendorTax} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CVendorTaxResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-vendor-taxes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CVendorTaxCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter eInvoice;

    private BooleanFilter taxableEmployers;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter vendorId;

    private LongFilter taxCategoryId;

    private LongFilter adOrganizationId;

    public CVendorTaxCriteria() {
    }

    public CVendorTaxCriteria(CVendorTaxCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.eInvoice = other.eInvoice == null ? null : other.eInvoice.copy();
        this.taxableEmployers = other.taxableEmployers == null ? null : other.taxableEmployers.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.taxCategoryId = other.taxCategoryId == null ? null : other.taxCategoryId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public CVendorTaxCriteria copy() {
        return new CVendorTaxCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter geteInvoice() {
        return eInvoice;
    }

    public void seteInvoice(BooleanFilter eInvoice) {
        this.eInvoice = eInvoice;
    }

    public BooleanFilter getTaxableEmployers() {
        return taxableEmployers;
    }

    public void setTaxableEmployers(BooleanFilter taxableEmployers) {
        this.taxableEmployers = taxableEmployers;
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

    public LongFilter getTaxCategoryId() {
        return taxCategoryId;
    }

    public void setTaxCategoryId(LongFilter taxCategoryId) {
        this.taxCategoryId = taxCategoryId;
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
        final CVendorTaxCriteria that = (CVendorTaxCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(eInvoice, that.eInvoice) &&
            Objects.equals(taxableEmployers, that.taxableEmployers) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(taxCategoryId, that.taxCategoryId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        eInvoice,
        taxableEmployers,
        uid,
        active,
        vendorId,
        taxCategoryId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "CVendorTaxCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (eInvoice != null ? "eInvoice=" + eInvoice + ", " : "") +
                (taxableEmployers != null ? "taxableEmployers=" + taxableEmployers + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (taxCategoryId != null ? "taxCategoryId=" + taxCategoryId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

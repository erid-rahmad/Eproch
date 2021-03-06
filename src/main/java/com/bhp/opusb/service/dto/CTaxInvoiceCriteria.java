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
 * Criteria class for the {@link com.bhp.opusb.domain.CTaxInvoice} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CTaxInvoiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-tax-invoices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CTaxInvoiceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter startNo;

    private StringFilter endNo;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter vendorId;

    public CTaxInvoiceCriteria() {
    }

    public CTaxInvoiceCriteria(CTaxInvoiceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startNo = other.startNo == null ? null : other.startNo.copy();
        this.endNo = other.endNo == null ? null : other.endNo.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
    }

    @Override
    public CTaxInvoiceCriteria copy() {
        return new CTaxInvoiceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStartNo() {
        return startNo;
    }

    public void setStartNo(StringFilter startNo) {
        this.startNo = startNo;
    }

    public StringFilter getEndNo() {
        return endNo;
    }

    public void setEndNo(StringFilter endNo) {
        this.endNo = endNo;
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
        final CTaxInvoiceCriteria that = (CTaxInvoiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(startNo, that.startNo) &&
            Objects.equals(endNo, that.endNo) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(vendorId, that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        startNo,
        endNo,
        uid,
        active,
        adOrganizationId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "CTaxInvoiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startNo != null ? "startNo=" + startNo + ", " : "") +
                (endNo != null ? "endNo=" + endNo + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}

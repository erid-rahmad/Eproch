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
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MQuoteSupplier} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MQuoteSupplierResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-quote-suppliers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MQuoteSupplierCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LocalDateFilter dateRequired;

    private LongFilter quotationId;

    private LongFilter businessClassificationId;

    private LongFilter businessCategoryId;

    private LongFilter businessSubCategoryId;

    private LongFilter vendorId;

    public MQuoteSupplierCriteria() {
    }

    public MQuoteSupplierCriteria(MQuoteSupplierCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.dateRequired = other.dateRequired == null ? null : other.dateRequired.copy();
        this.quotationId = other.quotationId == null ? null : other.quotationId.copy();
        this.businessClassificationId = other.businessClassificationId == null ? null : other.businessClassificationId.copy();
        this.businessCategoryId = other.businessCategoryId == null ? null : other.businessCategoryId.copy();
        this.businessSubCategoryId = other.businessSubCategoryId == null ? null : other.businessSubCategoryId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
    }

    @Override
    public MQuoteSupplierCriteria copy() {
        return new MQuoteSupplierCriteria(this);
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

    public LocalDateFilter getDateRequired() {
        return dateRequired;
    }

    public void setDateRequired(LocalDateFilter dateRequired) {
        this.dateRequired = dateRequired;
    }

    public LongFilter getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(LongFilter quotationId) {
        this.quotationId = quotationId;
    }

    public LongFilter getBusinessClassificationId() {
        return businessClassificationId;
    }

    public void setBusinessClassificationId(LongFilter businessClassificationId) {
        this.businessClassificationId = businessClassificationId;
    }

    public LongFilter getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(LongFilter businessCategoryId) {
        this.businessCategoryId = businessCategoryId;
    }

    public LongFilter getBusinessSubCategoryId() {
        return businessSubCategoryId;
    }

    public void setBusinessSubCategoryId(LongFilter businessSubCategoryId) {
        this.businessSubCategoryId = businessSubCategoryId;
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
        final MQuoteSupplierCriteria that = (MQuoteSupplierCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(dateRequired, that.dateRequired) &&
            Objects.equals(quotationId, that.quotationId) &&
            Objects.equals(businessClassificationId, that.businessClassificationId) &&
            Objects.equals(businessCategoryId, that.businessCategoryId) &&
            Objects.equals(businessSubCategoryId, that.businessSubCategoryId) &&
            Objects.equals(vendorId, that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        dateRequired,
        quotationId,
        businessClassificationId,
        businessCategoryId,
        businessSubCategoryId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "MQuoteSupplierCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (dateRequired != null ? "dateRequired=" + dateRequired + ", " : "") +
                (quotationId != null ? "quotationId=" + quotationId + ", " : "") +
                (businessClassificationId != null ? "businessClassificationId=" + businessClassificationId + ", " : "") +
                (businessCategoryId != null ? "businessCategoryId=" + businessCategoryId + ", " : "") +
                (businessSubCategoryId != null ? "businessSubCategoryId=" + businessSubCategoryId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}

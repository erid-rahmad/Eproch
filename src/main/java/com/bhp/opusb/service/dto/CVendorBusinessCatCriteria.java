package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CVendorBusinessCat} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CVendorBusinessCatResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-vendor-business-cats?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CVendorBusinessCatCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter vendorId;
    private StringFilter vendorName;

    private LongFilter businessClassificationId;

    private LongFilter businessCategoryId;

    private LongFilter subBusinessCategoryId;

    private LongFilter adOrganizationId;

    public CVendorBusinessCatCriteria() {
    }

    public CVendorBusinessCatCriteria(CVendorBusinessCatCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.vendorName = other.vendorName == null ? null : other.vendorName.copy();
        this.businessClassificationId = other.businessClassificationId == null ? null : other.businessClassificationId.copy();
        this.businessCategoryId = other.businessCategoryId == null ? null : other.businessCategoryId.copy();
        this.subBusinessCategoryId = other.subBusinessCategoryId == null ? null : other.subBusinessCategoryId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public CVendorBusinessCatCriteria copy() {
        return new CVendorBusinessCatCriteria(this);
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

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }

    public StringFilter getVendorName() {
        return vendorName;
    }

    public void setVendorName(StringFilter vendorName) {
        this.vendorName = vendorName;
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

    public LongFilter getSubBusinessCategoryId() {
        return subBusinessCategoryId;
    }

    public void setSubBusinessCategoryId(LongFilter subBusinessCategoryId) {
        this.subBusinessCategoryId = subBusinessCategoryId;
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
        final CVendorBusinessCatCriteria that = (CVendorBusinessCatCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(vendorName, that.vendorName) &&
            Objects.equals(businessClassificationId, that.businessClassificationId) &&
            Objects.equals(businessCategoryId, that.businessCategoryId) &&
            Objects.equals(subBusinessCategoryId, that.subBusinessCategoryId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        vendorId,
        vendorName,
        businessClassificationId,
        businessCategoryId,
        subBusinessCategoryId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "CVendorBusinessCatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (vendorName != null ? "vendorName=" + vendorName + ", " : "") +
                (businessClassificationId != null ? "businessClassificationId=" + businessClassificationId + ", " : "") +
                (businessCategoryId != null ? "businessCategoryId=" + businessCategoryId + ", " : "") +
                (subBusinessCategoryId != null ? "subBusinessCategoryId=" + subBusinessCategoryId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

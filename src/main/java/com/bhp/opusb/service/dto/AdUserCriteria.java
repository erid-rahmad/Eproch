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
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.AdUser} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.AdUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdUserCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter phone;

    private StringFilter position;

    private BooleanFilter vendor;

    private IntegerFilter failedLoginCount;

    private InstantFilter lastLoginDate;

    private LongFilter userId;

    private LongFilter cVendorId;

    private LongFilter adOrganizationId;

    public AdUserCriteria() {
    }

    public AdUserCriteria(AdUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.vendor = other.vendor == null ? null : other.vendor.copy();
        this.failedLoginCount = other.failedLoginCount == null ? null : other.failedLoginCount.copy();
        this.lastLoginDate = other.lastLoginDate == null ? null : other.lastLoginDate.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.cVendorId = other.cVendorId == null ? null : other.cVendorId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public AdUserCriteria copy() {
        return new AdUserCriteria(this);
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

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getPosition() {
        return position;
    }

    public void setPosition(StringFilter position) {
        this.position = position;
    }

    public BooleanFilter getVendor() {
        return vendor;
    }

    public void setVendor(BooleanFilter vendor) {
        this.vendor = vendor;
    }

    public IntegerFilter getFailedLoginCount() {
        return failedLoginCount;
    }

    public void setFailedLoginCount(IntegerFilter failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    public InstantFilter getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(InstantFilter lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getCVendorId() {
        return cVendorId;
    }

    public void setCVendorId(LongFilter cVendorId) {
        this.cVendorId = cVendorId;
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
        final AdUserCriteria that = (AdUserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(position, that.position) &&
            Objects.equals(vendor, that.vendor) &&
            Objects.equals(failedLoginCount, that.failedLoginCount) &&
            Objects.equals(lastLoginDate, that.lastLoginDate) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(cVendorId, that.cVendorId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        phone,
        position,
        vendor,
        failedLoginCount,
        lastLoginDate,
        userId,
        cVendorId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "AdUserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (position != null ? "position=" + position + ", " : "") +
                (vendor != null ? "vendor=" + vendor + ", " : "") +
                (failedLoginCount != null ? "failedLoginCount=" + failedLoginCount + ", " : "") +
                (lastLoginDate != null ? "lastLoginDate=" + lastLoginDate + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (cVendorId != null ? "cVendorId=" + cVendorId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

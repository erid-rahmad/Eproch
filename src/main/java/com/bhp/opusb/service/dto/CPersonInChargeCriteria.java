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
 * Criteria class for the {@link com.bhp.opusb.domain.CPersonInCharge} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CPersonInChargeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-person-in-charges?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CPersonInChargeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private StringFilter position;

    private StringFilter phone;

    private BooleanFilter functionary;

    private BooleanFilter active;

    private LongFilter userId;

    private LongFilter vendorId;

    private LongFilter adOrganizationId;

    public CPersonInChargeCriteria() {
    }

    public CPersonInChargeCriteria(CPersonInChargeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.functionary = other.functionary == null ? null : other.functionary.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public CPersonInChargeCriteria copy() {
        return new CPersonInChargeCriteria(this);
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

    public StringFilter getPosition() {
        return position;
    }

    public void setPosition(StringFilter position) {
        this.position = position;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public BooleanFilter getFunctionary() {
        return functionary;
    }

    public void setFunctionary(BooleanFilter functionary) {
        this.functionary = functionary;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
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
        final CPersonInChargeCriteria that = (CPersonInChargeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(position, that.position) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(functionary, that.functionary) &&
            Objects.equals(active, that.active) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        position,
        phone,
        functionary,
        active,
        userId,
        vendorId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "CPersonInChargeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (position != null ? "position=" + position + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (functionary != null ? "functionary=" + functionary + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

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
 * Criteria class for the {@link com.bhp.opusb.domain.CLocation} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CLocationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-locations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CLocationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter address1;

    private StringFilter address2;

    private StringFilter address3;

    private StringFilter address4;

    private StringFilter postalCode;

    private StringFilter phone;

    private StringFilter fax;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter cityId;

    private LongFilter adOrganizationId;

    public CLocationCriteria() {
    }

    public CLocationCriteria(CLocationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.address1 = other.address1 == null ? null : other.address1.copy();
        this.address2 = other.address2 == null ? null : other.address2.copy();
        this.address3 = other.address3 == null ? null : other.address3.copy();
        this.address4 = other.address4 == null ? null : other.address4.copy();
        this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public CLocationCriteria copy() {
        return new CLocationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAddress1() {
        return address1;
    }

    public void setAddress1(StringFilter address1) {
        this.address1 = address1;
    }

    public StringFilter getAddress2() {
        return address2;
    }

    public void setAddress2(StringFilter address2) {
        this.address2 = address2;
    }

    public StringFilter getAddress3() {
        return address3;
    }

    public void setAddress3(StringFilter address3) {
        this.address3 = address3;
    }

    public StringFilter getAddress4() {
        return address4;
    }

    public void setAddress4(StringFilter address4) {
        this.address4 = address4;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getFax() {
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
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

    public LongFilter getCityId() {
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
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
        final CLocationCriteria that = (CLocationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(address1, that.address1) &&
            Objects.equals(address2, that.address2) &&
            Objects.equals(address3, that.address3) &&
            Objects.equals(address4, that.address4) &&
            Objects.equals(postalCode, that.postalCode) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        address1,
        address2,
        address3,
        address4,
        postalCode,
        phone,
        fax,
        uid,
        active,
        cityId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "CLocationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (address1 != null ? "address1=" + address1 + ", " : "") +
                (address2 != null ? "address2=" + address2 + ", " : "") +
                (address3 != null ? "address3=" + address3 + ", " : "") +
                (address4 != null ? "address4=" + address4 + ", " : "") +
                (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (fax != null ? "fax=" + fax + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

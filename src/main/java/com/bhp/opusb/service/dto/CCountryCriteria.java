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
 * Criteria class for the {@link com.bhp.opusb.domain.CCountry} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CCountryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-countries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CCountryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private BooleanFilter withRegion;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter currencyId;
    private StringFilter currencyName;

    private LongFilter cRegionId;

    private LongFilter cCityId;

    private LongFilter adOrganizationId;

    public CCountryCriteria() {
    }

    public CCountryCriteria(CCountryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.withRegion = other.withRegion == null ? null : other.withRegion.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
        this.currencyName = other.currencyName == null ? null : other.currencyName.copy();
        this.cRegionId = other.cRegionId == null ? null : other.cRegionId.copy();
        this.cCityId = other.cCityId == null ? null : other.cCityId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public CCountryCriteria copy() {
        return new CCountryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public BooleanFilter getWithRegion() {
        return withRegion;
    }

    public void setWithRegion(BooleanFilter withRegion) {
        this.withRegion = withRegion;
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

    public LongFilter getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(LongFilter currencyId) {
        this.currencyId = currencyId;
    }

    public StringFilter getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(StringFilter currencyName) {
        this.currencyName = currencyName;
    }





    public LongFilter getCRegionId() {
        return cRegionId;
    }

    public void setCRegionId(LongFilter cRegionId) {
        this.cRegionId = cRegionId;
    }

    public LongFilter getCCityId() {
        return cCityId;
    }

    public void setCCityId(LongFilter cCityId) {
        this.cCityId = cCityId;
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
        final CCountryCriteria that = (CCountryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(withRegion, that.withRegion) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(currencyId, that.currencyId) &&
            Objects.equals(currencyName, that.currencyName) &&
            Objects.equals(cRegionId, that.cRegionId) &&
            Objects.equals(cCityId, that.cCityId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        withRegion,
        uid,
        active,
        currencyId,
        currencyName,
        cRegionId,
        cCityId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "CCountryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (withRegion != null ? "withRegion=" + withRegion + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
                (currencyName != null ? "currencyName=" + currencyName + ", " : "") +
                (cRegionId != null ? "cRegionId=" + cRegionId + ", " : "") +
                (cCityId != null ? "cCityId=" + cCityId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

    

}

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
 * Criteria class for the {@link com.bhp.opusb.domain.CLocator} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CLocatorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-locators?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CLocatorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter locatorType;

    private StringFilter aisle;

    private StringFilter bin;

    private StringFilter level;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter warehouseId;

    public CLocatorCriteria() {
    }

    public CLocatorCriteria(CLocatorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.locatorType = other.locatorType == null ? null : other.locatorType.copy();
        this.aisle = other.aisle == null ? null : other.aisle.copy();
        this.bin = other.bin == null ? null : other.bin.copy();
        this.level = other.level == null ? null : other.level.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.warehouseId = other.warehouseId == null ? null : other.warehouseId.copy();
    }

    @Override
    public CLocatorCriteria copy() {
        return new CLocatorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getLocatorType() {
        return locatorType;
    }

    public void setLocatorType(StringFilter locatorType) {
        this.locatorType = locatorType;
    }

    public StringFilter getAisle() {
        return aisle;
    }

    public void setAisle(StringFilter aisle) {
        this.aisle = aisle;
    }

    public StringFilter getBin() {
        return bin;
    }

    public void setBin(StringFilter bin) {
        this.bin = bin;
    }

    public StringFilter getLevel() {
        return level;
    }

    public void setLevel(StringFilter level) {
        this.level = level;
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

    public LongFilter getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(LongFilter warehouseId) {
        this.warehouseId = warehouseId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CLocatorCriteria that = (CLocatorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(locatorType, that.locatorType) &&
            Objects.equals(aisle, that.aisle) &&
            Objects.equals(bin, that.bin) &&
            Objects.equals(level, that.level) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(warehouseId, that.warehouseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        locatorType,
        aisle,
        bin,
        level,
        uid,
        active,
        adOrganizationId,
        warehouseId
        );
    }

    @Override
    public String toString() {
        return "CLocatorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (locatorType != null ? "locatorType=" + locatorType + ", " : "") +
                (aisle != null ? "aisle=" + aisle + ", " : "") +
                (bin != null ? "bin=" + bin + ", " : "") +
                (level != null ? "level=" + level + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (warehouseId != null ? "warehouseId=" + warehouseId + ", " : "") +
            "}";
    }

}

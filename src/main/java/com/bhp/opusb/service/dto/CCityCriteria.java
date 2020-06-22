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

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CCity} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CCityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-cities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CCityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter active;

    private LongFilter countryId;
    private StringFilter countryName;

    private LongFilter regionId;
    private StringFilter regionName;

    public CCityCriteria() {
    }

    public CCityCriteria(CCityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.countryName = other.countryName == null ? null : other.countryName.copy();
        this.regionId = other.regionId == null ? null : other.regionId.copy();
        this.regionName = other.regionName == null ? null : other.regionName.copy();
    }

    @Override
    public CCityCriteria copy() {
        return new CCityCriteria(this);
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

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    public LongFilter getRegionId() {
        return regionId;
    }

    public void setRegionId(LongFilter regionId) {
        this.regionId = regionId;
    }

    public StringFilter getCountryName() {
        return countryName;
    }

    public void setCountryName(StringFilter countryName) {
        this.countryName = countryName;
    }

    public StringFilter getRegionName() {
        return regionName;
    }

    public void setRegionName(StringFilter regionName) {
        this.regionName = regionName;
    }
    





    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CCityCriteria that = (CCityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(active, that.active) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(countryName, that.countryName) &&
            Objects.equals(regionId, that.regionId) &&
            Objects.equals(regionName, that.regionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        active,
        countryId,
        countryName,
        regionId,
        regionName
        );
    }

    @Override
    public String toString() {
        return "CCityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
                (countryName != null ? "countryName=" + countryName + ", " : "") +
                (regionId != null ? "regionId=" + regionId + ", " : "") +
                (regionName != null ? "regionName=" + regionName + ", " : "") +
            "}";
    }

    

}

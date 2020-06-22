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
 * Criteria class for the {@link com.bhp.opusb.domain.CRegion} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CRegionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-regions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CRegionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter active;

    private LongFilter cCityId;

    private LongFilter countryId;
    private StringFilter countryName;

    public CRegionCriteria() {
    }

    public CRegionCriteria(CRegionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.cCityId = other.cCityId == null ? null : other.cCityId.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.countryName = other.countryName == null ? null : other.countryName.copy();
    }

    @Override
    public CRegionCriteria copy() {
        return new CRegionCriteria(this);
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

    public LongFilter getCCityId() {
        return cCityId;
    }

    public void setCCityId(LongFilter cCityId) {
        this.cCityId = cCityId;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    public StringFilter getCountryName() {
        return countryName;
    }

    public void setCountryName(StringFilter countryName) {
        this.countryName = countryName;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CRegionCriteria that = (CRegionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(active, that.active) &&
            Objects.equals(cCityId, that.cCityId) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(countryName, that.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        active,
        cCityId,
        countryId,
        countryName
        );
    }

    @Override
    public String toString() {
        return "CRegionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (cCityId != null ? "cCityId=" + cCityId + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
                (countryName != null ? "countryName=" + countryName + ", " : "") +
            "}";
    }

    

}

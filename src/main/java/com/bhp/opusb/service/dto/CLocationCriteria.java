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

    private StringFilter streetAddress;

    private StringFilter postalCode;

    private BooleanFilter taxInvoiceAddress;

    private BooleanFilter active;

    private LongFilter cityId;
    private StringFilter cityName;

    public CLocationCriteria() {
    }

    public CLocationCriteria(CLocationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.streetAddress = other.streetAddress == null ? null : other.streetAddress.copy();
        this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
        this.taxInvoiceAddress = other.taxInvoiceAddress == null ? null : other.taxInvoiceAddress.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.cityName = other.cityName == null ? null : other.cityName.copy();
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

    public StringFilter getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(StringFilter streetAddress) {
        this.streetAddress = streetAddress;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public BooleanFilter getTaxInvoiceAddress() {
        return taxInvoiceAddress;
    }

    public void setTaxInvoiceAddress(BooleanFilter taxInvoiceAddress) {
        this.taxInvoiceAddress = taxInvoiceAddress;
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

    public StringFilter getCityName() {
        return cityName;
    }

    public void setCityName(StringFilter cityName) {
        this.cityName = cityName;
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
            Objects.equals(streetAddress, that.streetAddress) &&
            Objects.equals(postalCode, that.postalCode) &&
            Objects.equals(taxInvoiceAddress, that.taxInvoiceAddress) &&
            Objects.equals(active, that.active) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(cityName, that.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        streetAddress,
        postalCode,
        taxInvoiceAddress,
        active,
        cityId,
        cityName
        );
    }

    @Override
    public String toString() {
        return "CLocationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (streetAddress != null ? "streetAddress=" + streetAddress + ", " : "") +
                (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
                (taxInvoiceAddress != null ? "taxInvoiceAddress=" + taxInvoiceAddress + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
                (cityName != null ? "cityName=" + cityName + ", " : "") +
            "}";
    }

}

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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MProductPrice} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MProductPriceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-product-prices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MProductPriceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter minQty;

    private BigDecimalFilter maxQty;

    private BigDecimalFilter price;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    public MProductPriceCriteria() {
    }

    public MProductPriceCriteria(MProductPriceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.minQty = other.minQty == null ? null : other.minQty.copy();
        this.maxQty = other.maxQty == null ? null : other.maxQty.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public MProductPriceCriteria copy() {
        return new MProductPriceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getMinQty() {
        return minQty;
    }

    public void setMinQty(BigDecimalFilter minQty) {
        this.minQty = minQty;
    }

    public BigDecimalFilter getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(BigDecimalFilter maxQty) {
        this.maxQty = maxQty;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MProductPriceCriteria that = (MProductPriceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(minQty, that.minQty) &&
            Objects.equals(maxQty, that.maxQty) &&
            Objects.equals(price, that.price) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        minQty,
        maxQty,
        price,
        uid,
        active,
        adOrganizationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MProductPriceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (minQty != null ? "minQty=" + minQty + ", " : "") +
                (maxQty != null ? "maxQty=" + maxQty + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

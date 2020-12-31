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
 * Criteria class for the {@link com.bhp.opusb.domain.MShoppingCartItem} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MShoppingCartItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-shopping-cart-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MShoppingCartItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter quantity;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter mProductId;

    private LongFilter mShoppingCartId;

    public MShoppingCartItemCriteria() {
    }

    public MShoppingCartItemCriteria(MShoppingCartItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.mProductId = other.mProductId == null ? null : other.mProductId.copy();
        this.mShoppingCartId = other.mShoppingCartId == null ? null : other.mShoppingCartId.copy();
    }

    @Override
    public MShoppingCartItemCriteria copy() {
        return new MShoppingCartItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
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

    public LongFilter getMProductId() {
        return mProductId;
    }

    public void setMProductId(LongFilter mProductId) {
        this.mProductId = mProductId;
    }

    public LongFilter getMShoppingCartId() {
        return mShoppingCartId;
    }

    public void setMShoppingCartId(LongFilter mShoppingCartId) {
        this.mShoppingCartId = mShoppingCartId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MShoppingCartItemCriteria that = (MShoppingCartItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(mProductId, that.mProductId) &&
            Objects.equals(mShoppingCartId, that.mShoppingCartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantity,
        uid,
        active,
        adOrganizationId,
        mProductId,
        mShoppingCartId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MShoppingCartItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (mProductId != null ? "mProductId=" + mProductId + ", " : "") +
                (mShoppingCartId != null ? "mShoppingCartId=" + mShoppingCartId + ", " : "") +
            "}";
    }

}

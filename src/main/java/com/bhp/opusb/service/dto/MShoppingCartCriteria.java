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
 * Criteria class for the {@link com.bhp.opusb.domain.MShoppingCart} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MShoppingCartResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-shopping-carts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MShoppingCartCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter mShoppingCartItemId;

    private LongFilter adOrganizationId;

    private LongFilter adUserId;

    public MShoppingCartCriteria() {
    }

    public MShoppingCartCriteria(MShoppingCartCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.mShoppingCartItemId = other.mShoppingCartItemId == null ? null : other.mShoppingCartItemId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adUserId = other.adUserId == null ? null : other.adUserId.copy();
    }

    @Override
    public MShoppingCartCriteria copy() {
        return new MShoppingCartCriteria(this);
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

    public LongFilter getMShoppingCartItemId() {
        return mShoppingCartItemId;
    }

    public void setMShoppingCartItemId(LongFilter mShoppingCartItemId) {
        this.mShoppingCartItemId = mShoppingCartItemId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getAdUserId() {
        return adUserId;
    }

    public void setAdUserId(LongFilter adUserId) {
        this.adUserId = adUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MShoppingCartCriteria that = (MShoppingCartCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(mShoppingCartItemId, that.mShoppingCartItemId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adUserId, that.adUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        mShoppingCartItemId,
        adOrganizationId,
        adUserId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MShoppingCartCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (mShoppingCartItemId != null ? "mShoppingCartItemId=" + mShoppingCartItemId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adUserId != null ? "adUserId=" + adUserId + ", " : "") +
            "}";
    }

}

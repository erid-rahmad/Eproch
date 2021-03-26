package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CBudgetPlanLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CBudgetPlanLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-budget-plan-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CBudgetPlanLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter totalDebit;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter cCurrencyId;

    private LongFilter cDocumentTypeId;

    private LongFilter mBiddingId;

    private LongFilter mPurchaseOrderId;

    private LongFilter mRequisitionId;

    public CBudgetPlanLineCriteria() {
    }

    public CBudgetPlanLineCriteria(CBudgetPlanLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.totalDebit = other.totalDebit == null ? null : other.totalDebit.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.cCurrencyId = other.cCurrencyId == null ? null : other.cCurrencyId.copy();
        this.cDocumentTypeId = other.cDocumentTypeId == null ? null : other.cDocumentTypeId.copy();
        this.mBiddingId = other.mBiddingId == null ? null : other.mBiddingId.copy();
        this.mPurchaseOrderId = other.mPurchaseOrderId == null ? null : other.mPurchaseOrderId.copy();
        this.mRequisitionId = other.mRequisitionId == null ? null : other.mRequisitionId.copy();
    }

    @Override
    public CBudgetPlanLineCriteria copy() {
        return new CBudgetPlanLineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(BigDecimalFilter totalDebit) {
        this.totalDebit = totalDebit;
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

    public LongFilter getCCurrencyId() {
        return cCurrencyId;
    }

    public void setCCurrencyId(LongFilter cCurrencyId) {
        this.cCurrencyId = cCurrencyId;
    }

    public LongFilter getCDocumentTypeId() {
        return cDocumentTypeId;
    }

    public void setCDocumentTypeId(LongFilter cDocumentTypeId) {
        this.cDocumentTypeId = cDocumentTypeId;
    }

    public LongFilter getMBiddingId() {
        return mBiddingId;
    }

    public void setMBiddingId(LongFilter mBiddingId) {
        this.mBiddingId = mBiddingId;
    }

    public LongFilter getMPurchaseOrderId() {
        return mPurchaseOrderId;
    }

    public void setMPurchaseOrderId(LongFilter mPurchaseOrderId) {
        this.mPurchaseOrderId = mPurchaseOrderId;
    }

    public LongFilter getMRequisitionId() {
        return mRequisitionId;
    }

    public void setMRequisitionId(LongFilter mRequisitionId) {
        this.mRequisitionId = mRequisitionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CBudgetPlanLineCriteria that = (CBudgetPlanLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(totalDebit, that.totalDebit) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(cCurrencyId, that.cCurrencyId) &&
            Objects.equals(cDocumentTypeId, that.cDocumentTypeId) &&
            Objects.equals(mBiddingId, that.mBiddingId) &&
            Objects.equals(mPurchaseOrderId, that.mPurchaseOrderId) &&
            Objects.equals(mRequisitionId, that.mRequisitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        totalDebit,
        uid,
        active,
        adOrganizationId,
        cCurrencyId,
        cDocumentTypeId,
        mBiddingId,
        mPurchaseOrderId,
        mRequisitionId
        );
    }

    @Override
    public String toString() {
        return "CBudgetPlanLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (totalDebit != null ? "totalDebit=" + totalDebit + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (cCurrencyId != null ? "cCurrencyId=" + cCurrencyId + ", " : "") +
                (cDocumentTypeId != null ? "cDocumentTypeId=" + cDocumentTypeId + ", " : "") +
                (mBiddingId != null ? "mBiddingId=" + mBiddingId + ", " : "") +
                (mPurchaseOrderId != null ? "mPurchaseOrderId=" + mPurchaseOrderId + ", " : "") +
                (mRequisitionId != null ? "mRequisitionId=" + mRequisitionId + ", " : "") +
            "}";
    }

}

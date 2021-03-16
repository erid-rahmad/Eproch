package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CPaymentSchedule} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CPaymentScheduleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-payment-schedules?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CPaymentScheduleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter discount;

    private LongFilter discountDays;

    private LongFilter graceDays;

    private IntegerFilter netDay;

    private LongFilter netDays;

    private BigDecimalFilter percentage;

    private BooleanFilter valid;

    private LongFilter adOrganizationId;

    private LongFilter cPaymentTermId;

    public CPaymentScheduleCriteria() {
    }

    public CPaymentScheduleCriteria(CPaymentScheduleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.discount = other.discount == null ? null : other.discount.copy();
        this.discountDays = other.discountDays == null ? null : other.discountDays.copy();
        this.graceDays = other.graceDays == null ? null : other.graceDays.copy();
        this.netDay = other.netDay == null ? null : other.netDay.copy();
        this.netDays = other.netDays == null ? null : other.netDays.copy();
        this.percentage = other.percentage == null ? null : other.percentage.copy();
        this.valid = other.valid == null ? null : other.valid.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.cPaymentTermId = other.cPaymentTermId == null ? null : other.cPaymentTermId.copy();
    }

    @Override
    public CPaymentScheduleCriteria copy() {
        return new CPaymentScheduleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimalFilter discount) {
        this.discount = discount;
    }

    public LongFilter getDiscountDays() {
        return discountDays;
    }

    public void setDiscountDays(LongFilter discountDays) {
        this.discountDays = discountDays;
    }

    public LongFilter getGraceDays() {
        return graceDays;
    }

    public void setGraceDays(LongFilter graceDays) {
        this.graceDays = graceDays;
    }

    public IntegerFilter getNetDay() {
        return netDay;
    }

    public void setNetDay(IntegerFilter netDay) {
        this.netDay = netDay;
    }

    public LongFilter getNetDays() {
        return netDays;
    }

    public void setNetDays(LongFilter netDays) {
        this.netDays = netDays;
    }

    public BigDecimalFilter getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimalFilter percentage) {
        this.percentage = percentage;
    }

    public BooleanFilter getValid() {
        return valid;
    }

    public void setValid(BooleanFilter valid) {
        this.valid = valid;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getCPaymentTermId() {
        return cPaymentTermId;
    }

    public void setCPaymentTermId(LongFilter cPaymentTermId) {
        this.cPaymentTermId = cPaymentTermId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CPaymentScheduleCriteria that = (CPaymentScheduleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(discountDays, that.discountDays) &&
            Objects.equals(graceDays, that.graceDays) &&
            Objects.equals(netDay, that.netDay) &&
            Objects.equals(netDays, that.netDays) &&
            Objects.equals(percentage, that.percentage) &&
            Objects.equals(valid, that.valid) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(cPaymentTermId, that.cPaymentTermId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        discount,
        discountDays,
        graceDays,
        netDay,
        netDays,
        percentage,
        valid,
        adOrganizationId,
        cPaymentTermId
        );
    }

    @Override
    public String toString() {
        return "CPaymentScheduleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (discount != null ? "discount=" + discount + ", " : "") +
                (discountDays != null ? "discountDays=" + discountDays + ", " : "") +
                (graceDays != null ? "graceDays=" + graceDays + ", " : "") +
                (netDay != null ? "netDay=" + netDay + ", " : "") +
                (netDays != null ? "netDays=" + netDays + ", " : "") +
                (percentage != null ? "percentage=" + percentage + ", " : "") +
                (valid != null ? "valid=" + valid + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (cPaymentTermId != null ? "cPaymentTermId=" + cPaymentTermId + ", " : "") +
            "}";
    }

}

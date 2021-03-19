package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.bhp.opusb.domain.enumeration.CTransactionType;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CPaymentTerm} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CPaymentTermResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-payment-terms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CPaymentTermCriteria implements Serializable, Criteria {
    /**
     * Class for filtering CTransactionType
     */
    public static class CTransactionTypeFilter extends Filter<CTransactionType> {

        public CTransactionTypeFilter() {
        }

        public CTransactionTypeFilter(CTransactionTypeFilter filter) {
            super(filter);
        }

        @Override
        public CTransactionTypeFilter copy() {
            return new CTransactionTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private BooleanFilter afterDelivery;

    private BooleanFilter asDefault;

    private BooleanFilter calculateBusinessDay;

    private BigDecimalFilter discount;

    private BigDecimalFilter discount2;

    private LongFilter discountDays;

    private LongFilter discountDays2;

    private StringFilter documentNote;

    private LongFilter fixMonthCutOff;

    private LongFilter fixMonthDay;

    private LongFilter fixMonthOffset;

    private BooleanFilter fixedDueDate;

    private LongFilter graceDays;

    private StringFilter netDay;

    private LongFilter netDays;

    private BooleanFilter onNextBusinessDay;

    private CTransactionTypeFilter transactionType;

    private BooleanFilter valid;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    public CPaymentTermCriteria() {
    }

    public CPaymentTermCriteria(CPaymentTermCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.afterDelivery = other.afterDelivery == null ? null : other.afterDelivery.copy();
        this.asDefault = other.asDefault == null ? null : other.asDefault.copy();
        this.calculateBusinessDay = other.calculateBusinessDay == null ? null : other.calculateBusinessDay.copy();
        this.discount = other.discount == null ? null : other.discount.copy();
        this.discount2 = other.discount2 == null ? null : other.discount2.copy();
        this.discountDays = other.discountDays == null ? null : other.discountDays.copy();
        this.discountDays2 = other.discountDays2 == null ? null : other.discountDays2.copy();
        this.documentNote = other.documentNote == null ? null : other.documentNote.copy();
        this.fixMonthCutOff = other.fixMonthCutOff == null ? null : other.fixMonthCutOff.copy();
        this.fixMonthDay = other.fixMonthDay == null ? null : other.fixMonthDay.copy();
        this.fixMonthOffset = other.fixMonthOffset == null ? null : other.fixMonthOffset.copy();
        this.fixedDueDate = other.fixedDueDate == null ? null : other.fixedDueDate.copy();
        this.graceDays = other.graceDays == null ? null : other.graceDays.copy();
        this.netDay = other.netDay == null ? null : other.netDay.copy();
        this.netDays = other.netDays == null ? null : other.netDays.copy();
        this.onNextBusinessDay = other.onNextBusinessDay == null ? null : other.onNextBusinessDay.copy();
        this.transactionType = other.transactionType == null ? null : other.transactionType.copy();
        this.valid = other.valid == null ? null : other.valid.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public CPaymentTermCriteria copy() {
        return new CPaymentTermCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getAfterDelivery() {
        return afterDelivery;
    }

    public void setAfterDelivery(BooleanFilter afterDelivery) {
        this.afterDelivery = afterDelivery;
    }

    public BooleanFilter getAsDefault() {
        return asDefault;
    }

    public void setAsDefault(BooleanFilter asDefault) {
        this.asDefault = asDefault;
    }

    public BooleanFilter getCalculateBusinessDay() {
        return calculateBusinessDay;
    }

    public void setCalculateBusinessDay(BooleanFilter calculateBusinessDay) {
        this.calculateBusinessDay = calculateBusinessDay;
    }

    public BigDecimalFilter getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimalFilter discount) {
        this.discount = discount;
    }

    public BigDecimalFilter getDiscount2() {
        return discount2;
    }

    public void setDiscount2(BigDecimalFilter discount2) {
        this.discount2 = discount2;
    }

    public LongFilter getDiscountDays() {
        return discountDays;
    }

    public void setDiscountDays(LongFilter discountDays) {
        this.discountDays = discountDays;
    }

    public LongFilter getDiscountDays2() {
        return discountDays2;
    }

    public void setDiscountDays2(LongFilter discountDays2) {
        this.discountDays2 = discountDays2;
    }

    public StringFilter getDocumentNote() {
        return documentNote;
    }

    public void setDocumentNote(StringFilter documentNote) {
        this.documentNote = documentNote;
    }

    public LongFilter getFixMonthCutOff() {
        return fixMonthCutOff;
    }

    public void setFixMonthCutOff(LongFilter fixMonthCutOff) {
        this.fixMonthCutOff = fixMonthCutOff;
    }

    public LongFilter getFixMonthDay() {
        return fixMonthDay;
    }

    public void setFixMonthDay(LongFilter fixMonthDay) {
        this.fixMonthDay = fixMonthDay;
    }

    public LongFilter getFixMonthOffset() {
        return fixMonthOffset;
    }

    public void setFixMonthOffset(LongFilter fixMonthOffset) {
        this.fixMonthOffset = fixMonthOffset;
    }

    public BooleanFilter getFixedDueDate() {
        return fixedDueDate;
    }

    public void setFixedDueDate(BooleanFilter fixedDueDate) {
        this.fixedDueDate = fixedDueDate;
    }

    public LongFilter getGraceDays() {
        return graceDays;
    }

    public void setGraceDays(LongFilter graceDays) {
        this.graceDays = graceDays;
    }

    public StringFilter getNetDay() {
        return netDay;
    }

    public void setNetDay(StringFilter netDay) {
        this.netDay = netDay;
    }

    public LongFilter getNetDays() {
        return netDays;
    }

    public void setNetDays(LongFilter netDays) {
        this.netDays = netDays;
    }

    public BooleanFilter getOnNextBusinessDay() {
        return onNextBusinessDay;
    }

    public void setOnNextBusinessDay(BooleanFilter onNextBusinessDay) {
        this.onNextBusinessDay = onNextBusinessDay;
    }

    public CTransactionTypeFilter getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(CTransactionTypeFilter transactionType) {
        this.transactionType = transactionType;
    }

    public BooleanFilter getValid() {
        return valid;
    }

    public void setValid(BooleanFilter valid) {
        this.valid = valid;
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
        final CPaymentTermCriteria that = (CPaymentTermCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(afterDelivery, that.afterDelivery) &&
            Objects.equals(asDefault, that.asDefault) &&
            Objects.equals(calculateBusinessDay, that.calculateBusinessDay) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(discount2, that.discount2) &&
            Objects.equals(discountDays, that.discountDays) &&
            Objects.equals(discountDays2, that.discountDays2) &&
            Objects.equals(documentNote, that.documentNote) &&
            Objects.equals(fixMonthCutOff, that.fixMonthCutOff) &&
            Objects.equals(fixMonthDay, that.fixMonthDay) &&
            Objects.equals(fixMonthOffset, that.fixMonthOffset) &&
            Objects.equals(fixedDueDate, that.fixedDueDate) &&
            Objects.equals(graceDays, that.graceDays) &&
            Objects.equals(netDay, that.netDay) &&
            Objects.equals(netDays, that.netDays) &&
            Objects.equals(onNextBusinessDay, that.onNextBusinessDay) &&
            Objects.equals(transactionType, that.transactionType) &&
            Objects.equals(valid, that.valid) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        description,
        afterDelivery,
        asDefault,
        calculateBusinessDay,
        discount,
        discount2,
        discountDays,
        discountDays2,
        documentNote,
        fixMonthCutOff,
        fixMonthDay,
        fixMonthOffset,
        fixedDueDate,
        graceDays,
        netDay,
        netDays,
        onNextBusinessDay,
        transactionType,
        valid,
        uid,
        active,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "CPaymentTermCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (afterDelivery != null ? "afterDelivery=" + afterDelivery + ", " : "") +
                (asDefault != null ? "asDefault=" + asDefault + ", " : "") +
                (calculateBusinessDay != null ? "calculateBusinessDay=" + calculateBusinessDay + ", " : "") +
                (discount != null ? "discount=" + discount + ", " : "") +
                (discount2 != null ? "discount2=" + discount2 + ", " : "") +
                (discountDays != null ? "discountDays=" + discountDays + ", " : "") +
                (discountDays2 != null ? "discountDays2=" + discountDays2 + ", " : "") +
                (documentNote != null ? "documentNote=" + documentNote + ", " : "") +
                (fixMonthCutOff != null ? "fixMonthCutOff=" + fixMonthCutOff + ", " : "") +
                (fixMonthDay != null ? "fixMonthDay=" + fixMonthDay + ", " : "") +
                (fixMonthOffset != null ? "fixMonthOffset=" + fixMonthOffset + ", " : "") +
                (fixedDueDate != null ? "fixedDueDate=" + fixedDueDate + ", " : "") +
                (graceDays != null ? "graceDays=" + graceDays + ", " : "") +
                (netDay != null ? "netDay=" + netDay + ", " : "") +
                (netDays != null ? "netDays=" + netDays + ", " : "") +
                (onNextBusinessDay != null ? "onNextBusinessDay=" + onNextBusinessDay + ", " : "") +
                (transactionType != null ? "transactionType=" + transactionType + ", " : "") +
                (valid != null ? "valid=" + valid + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MRfqView} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MRfqViewResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-rfq-views?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MRfqViewCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documentNo;

    private StringFilter title;

    private LocalDateFilter dateRequired;

    private StringFilter selectionMethod;

    private IntegerFilter joinedVendorCount;

    private LongFilter quotationId;

    public MRfqViewCriteria() {
    }

    public MRfqViewCriteria(MRfqViewCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentNo = other.documentNo == null ? null : other.documentNo.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.dateRequired = other.dateRequired == null ? null : other.dateRequired.copy();
        this.selectionMethod = other.selectionMethod == null ? null : other.selectionMethod.copy();
        this.joinedVendorCount = other.joinedVendorCount == null ? null : other.joinedVendorCount.copy();
        this.quotationId = other.quotationId == null ? null : other.quotationId.copy();
    }

    @Override
    public MRfqViewCriteria copy() {
        return new MRfqViewCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(StringFilter documentNo) {
        this.documentNo = documentNo;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public LocalDateFilter getDateRequired() {
        return dateRequired;
    }

    public void setDateRequired(LocalDateFilter dateRequired) {
        this.dateRequired = dateRequired;
    }

    public StringFilter getSelectionMethod() {
        return selectionMethod;
    }

    public void setSelectionMethod(StringFilter selectionMethod) {
        this.selectionMethod = selectionMethod;
    }

    public IntegerFilter getJoinedVendorCount() {
        return joinedVendorCount;
    }

    public void setJoinedVendorCount(IntegerFilter joinedVendorCount) {
        this.joinedVendorCount = joinedVendorCount;
    }

    public LongFilter getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(LongFilter quotationId) {
        this.quotationId = quotationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MRfqViewCriteria that = (MRfqViewCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(documentNo, that.documentNo) &&
            Objects.equals(title, that.title) &&
            Objects.equals(dateRequired, that.dateRequired) &&
            Objects.equals(selectionMethod, that.selectionMethod) &&
            Objects.equals(joinedVendorCount, that.joinedVendorCount) &&
            Objects.equals(quotationId, that.quotationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        documentNo,
        title,
        dateRequired,
        selectionMethod,
        joinedVendorCount,
        quotationId
        );
    }

    @Override
    public String toString() {
        return "MRfqViewCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentNo != null ? "documentNo=" + documentNo + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (dateRequired != null ? "dateRequired=" + dateRequired + ", " : "") +
                (selectionMethod != null ? "selectionMethod=" + selectionMethod + ", " : "") +
                (joinedVendorCount != null ? "joinedVendorCount=" + joinedVendorCount + ", " : "") +
                (quotationId != null ? "quotationId=" + quotationId + ", " : "") +
            "}";
    }

}

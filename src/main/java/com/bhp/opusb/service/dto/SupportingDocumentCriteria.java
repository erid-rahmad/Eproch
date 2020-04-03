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
 * Criteria class for the {@link com.bhp.opusb.domain.SupportingDocument} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.SupportingDocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /supporting-documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupportingDocumentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documentNo;

    private LocalDateFilter expirationDate;

    private LongFilter typeId;

    private LongFilter vendorId;

    public SupportingDocumentCriteria() {
    }

    public SupportingDocumentCriteria(SupportingDocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentNo = other.documentNo == null ? null : other.documentNo.copy();
        this.expirationDate = other.expirationDate == null ? null : other.expirationDate.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
    }

    @Override
    public SupportingDocumentCriteria copy() {
        return new SupportingDocumentCriteria(this);
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

    public LocalDateFilter getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateFilter expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LongFilter getTypeId() {
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupportingDocumentCriteria that = (SupportingDocumentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(documentNo, that.documentNo) &&
            Objects.equals(expirationDate, that.expirationDate) &&
            Objects.equals(typeId, that.typeId) &&
            Objects.equals(vendorId, that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        documentNo,
        expirationDate,
        typeId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "SupportingDocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentNo != null ? "documentNo=" + documentNo + ", " : "") +
                (expirationDate != null ? "expirationDate=" + expirationDate + ", " : "") +
                (typeId != null ? "typeId=" + typeId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}

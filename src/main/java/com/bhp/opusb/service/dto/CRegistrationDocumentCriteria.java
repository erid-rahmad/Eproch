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
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CRegistrationDocument} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CRegistrationDocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-registration-documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CRegistrationDocumentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documentNo;

    private LocalDateFilter expirationDate;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter typeId;

    private LongFilter fileId;

    private LongFilter vendorId;

    private LongFilter adOrganizationId;

    public CRegistrationDocumentCriteria() {
    }

    public CRegistrationDocumentCriteria(CRegistrationDocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentNo = other.documentNo == null ? null : other.documentNo.copy();
        this.expirationDate = other.expirationDate == null ? null : other.expirationDate.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
        this.fileId = other.fileId == null ? null : other.fileId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public CRegistrationDocumentCriteria copy() {
        return new CRegistrationDocumentCriteria(this);
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

    public LongFilter getTypeId() {
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }

    public LongFilter getFileId() {
        return fileId;
    }

    public void setFileId(LongFilter fileId) {
        this.fileId = fileId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
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
        final CRegistrationDocumentCriteria that = (CRegistrationDocumentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(documentNo, that.documentNo) &&
            Objects.equals(expirationDate, that.expirationDate) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(typeId, that.typeId) &&
            Objects.equals(fileId, that.fileId) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        documentNo,
        expirationDate,
        uid,
        active,
        typeId,
        fileId,
        vendorId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "CRegistrationDocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentNo != null ? "documentNo=" + documentNo + ", " : "") +
                (expirationDate != null ? "expirationDate=" + expirationDate + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (typeId != null ? "typeId=" + typeId + ", " : "") +
                (fileId != null ? "fileId=" + fileId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

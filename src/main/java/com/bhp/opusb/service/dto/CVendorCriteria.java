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
 * Criteria class for the {@link com.bhp.opusb.domain.CVendor} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CVendorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-vendors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CVendorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter type;

    private StringFilter location;

    private StringFilter idNo;

    private StringFilter tin;

    private StringFilter taxIdNo;

    private StringFilter taxIdName;

    private BooleanFilter branch;

    private StringFilter email;

    private StringFilter phone;

    private StringFilter fax;

    private StringFilter website;

    private StringFilter paymentCategory;

    private DoubleFilter rating;

    private LocalDateFilter dateTrx;

    private StringFilter documentNo;

    private StringFilter documentAction;

    private StringFilter documentStatus;

    private BooleanFilter approved;

    private BooleanFilter processed;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter taxIdFileId;

    private LongFilter adOrganizationId;

    private LongFilter documentTypeId;

    private LongFilter vendorGroupId;

    public CVendorCriteria() {
    }

    public CVendorCriteria(CVendorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.idNo = other.idNo == null ? null : other.idNo.copy();
        this.tin = other.tin == null ? null : other.tin.copy();
        this.taxIdNo = other.taxIdNo == null ? null : other.taxIdNo.copy();
        this.taxIdName = other.taxIdName == null ? null : other.taxIdName.copy();
        this.branch = other.branch == null ? null : other.branch.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.website = other.website == null ? null : other.website.copy();
        this.paymentCategory = other.paymentCategory == null ? null : other.paymentCategory.copy();
        this.rating = other.rating == null ? null : other.rating.copy();
        this.dateTrx = other.dateTrx == null ? null : other.dateTrx.copy();
        this.documentNo = other.documentNo == null ? null : other.documentNo.copy();
        this.documentAction = other.documentAction == null ? null : other.documentAction.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.approved = other.approved == null ? null : other.approved.copy();
        this.processed = other.processed == null ? null : other.processed.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.taxIdFileId = other.taxIdFileId == null ? null : other.taxIdFileId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.documentTypeId = other.documentTypeId == null ? null : other.documentTypeId.copy();
        this.vendorGroupId = other.vendorGroupId == null ? null : other.vendorGroupId.copy();
    }

    @Override
    public CVendorCriteria copy() {
        return new CVendorCriteria(this);
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

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getLocation() {
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public StringFilter getIdNo() {
        return idNo;
    }

    public void setIdNo(StringFilter idNo) {
        this.idNo = idNo;
    }

    public StringFilter getTin() {
        return tin;
    }

    public void setTin(StringFilter tin) {
        this.tin = tin;
    }

    public StringFilter getTaxIdNo() {
        return taxIdNo;
    }

    public void setTaxIdNo(StringFilter taxIdNo) {
        this.taxIdNo = taxIdNo;
    }

    public StringFilter getTaxIdName() {
        return taxIdName;
    }

    public void setTaxIdName(StringFilter taxIdName) {
        this.taxIdName = taxIdName;
    }

    public BooleanFilter getBranch() {
        return branch;
    }

    public void setBranch(BooleanFilter branch) {
        this.branch = branch;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getFax() {
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
    }

    public StringFilter getWebsite() {
        return website;
    }

    public void setWebsite(StringFilter website) {
        this.website = website;
    }

    public StringFilter getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(StringFilter paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public DoubleFilter getRating() {
        return rating;
    }

    public void setRating(DoubleFilter rating) {
        this.rating = rating;
    }

    public LocalDateFilter getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(LocalDateFilter dateTrx) {
        this.dateTrx = dateTrx;
    }

    public StringFilter getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(StringFilter documentNo) {
        this.documentNo = documentNo;
    }

    public StringFilter getDocumentAction() {
        return documentAction;
    }

    public void setDocumentAction(StringFilter documentAction) {
        this.documentAction = documentAction;
    }

    public StringFilter getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(StringFilter documentStatus) {
        this.documentStatus = documentStatus;
    }

    public BooleanFilter getApproved() {
        return approved;
    }

    public void setApproved(BooleanFilter approved) {
        this.approved = approved;
    }

    public BooleanFilter getProcessed() {
        return processed;
    }

    public void setProcessed(BooleanFilter processed) {
        this.processed = processed;
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

    public LongFilter getTaxIdFileId() {
        return taxIdFileId;
    }

    public void setTaxIdFileId(LongFilter taxIdFileId) {
        this.taxIdFileId = taxIdFileId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(LongFilter documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public LongFilter getVendorGroupId() {
        return vendorGroupId;
    }

    public void setVendorGroupId(LongFilter vendorGroupId) {
        this.vendorGroupId = vendorGroupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CVendorCriteria that = (CVendorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(location, that.location) &&
            Objects.equals(idNo, that.idNo) &&
            Objects.equals(tin, that.tin) &&
            Objects.equals(taxIdNo, that.taxIdNo) &&
            Objects.equals(taxIdName, that.taxIdName) &&
            Objects.equals(branch, that.branch) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(website, that.website) &&
            Objects.equals(paymentCategory, that.paymentCategory) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(dateTrx, that.dateTrx) &&
            Objects.equals(documentNo, that.documentNo) &&
            Objects.equals(documentAction, that.documentAction) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(approved, that.approved) &&
            Objects.equals(processed, that.processed) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(taxIdFileId, that.taxIdFileId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(documentTypeId, that.documentTypeId) &&
            Objects.equals(vendorGroupId, that.vendorGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        type,
        location,
        idNo,
        tin,
        taxIdNo,
        taxIdName,
        branch,
        email,
        phone,
        fax,
        website,
        paymentCategory,
        rating,
        dateTrx,
        documentNo,
        documentAction,
        documentStatus,
        approved,
        processed,
        uid,
        active,
        taxIdFileId,
        adOrganizationId,
        documentTypeId,
        vendorGroupId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CVendorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (location != null ? "location=" + location + ", " : "") +
                (idNo != null ? "idNo=" + idNo + ", " : "") +
                (tin != null ? "tin=" + tin + ", " : "") +
                (taxIdNo != null ? "taxIdNo=" + taxIdNo + ", " : "") +
                (taxIdName != null ? "taxIdName=" + taxIdName + ", " : "") +
                (branch != null ? "branch=" + branch + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (fax != null ? "fax=" + fax + ", " : "") +
                (website != null ? "website=" + website + ", " : "") +
                (paymentCategory != null ? "paymentCategory=" + paymentCategory + ", " : "") +
                (rating != null ? "rating=" + rating + ", " : "") +
                (dateTrx != null ? "dateTrx=" + dateTrx + ", " : "") +
                (documentNo != null ? "documentNo=" + documentNo + ", " : "") +
                (documentAction != null ? "documentAction=" + documentAction + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (approved != null ? "approved=" + approved + ", " : "") +
                (processed != null ? "processed=" + processed + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (taxIdFileId != null ? "taxIdFileId=" + taxIdFileId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (documentTypeId != null ? "documentTypeId=" + documentTypeId + ", " : "") +
                (vendorGroupId != null ? "vendorGroupId=" + vendorGroupId + ", " : "") +
            "}";
    }

}

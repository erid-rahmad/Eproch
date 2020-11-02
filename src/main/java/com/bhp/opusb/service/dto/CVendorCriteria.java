package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.bhp.opusb.domain.enumeration.VendorType;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
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
    /**
     * Class for filtering VendorType
     */
    public static class VendorTypeFilter extends Filter<VendorType> {

        public VendorTypeFilter() {
        }

        public VendorTypeFilter(VendorTypeFilter filter) {
            super(filter);
        }

        @Override
        public VendorTypeFilter copy() {
            return new VendorTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter taxIdNo;

    private StringFilter taxIdName;

    private BooleanFilter branch;

    private StringFilter email;

    private StringFilter phone;

    private StringFilter fax;

    private StringFilter website;

    private VendorTypeFilter type;

    private StringFilter paymentCategory;

    private StringFilter approvalStatus;

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

    public CVendorCriteria() {
    }

    public CVendorCriteria(CVendorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.taxIdNo = other.taxIdNo == null ? null : other.taxIdNo.copy();
        this.taxIdName = other.taxIdName == null ? null : other.taxIdName.copy();
        this.branch = other.branch == null ? null : other.branch.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.website = other.website == null ? null : other.website.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.paymentCategory = other.paymentCategory == null ? null : other.paymentCategory.copy();
        this.approvalStatus = other.approvalStatus == null ? null : other.approvalStatus.copy();
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

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public VendorTypeFilter getType() {
        return type;
    }

    public void setType(VendorTypeFilter type) {
        this.type = type;
    }

    public StringFilter getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(StringFilter paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public StringFilter getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(StringFilter approvalStatus) {
        this.approvalStatus = approvalStatus;
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
            Objects.equals(name, that.name) &&
            Objects.equals(taxIdNo, that.taxIdNo) &&
            Objects.equals(taxIdName, that.taxIdName) &&
            Objects.equals(branch, that.branch) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(website, that.website) &&
            Objects.equals(type, that.type) &&
            Objects.equals(paymentCategory, that.paymentCategory) &&
            Objects.equals(approvalStatus, that.approvalStatus) &&
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
            Objects.equals(documentTypeId, that.documentTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        taxIdNo,
        taxIdName,
        branch,
        email,
        phone,
        fax,
        website,
        type,
        paymentCategory,
        approvalStatus,
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
        documentTypeId
        );
    }

    @Override
    public String toString() {
        return "CVendorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (taxIdNo != null ? "taxIdNo=" + taxIdNo + ", " : "") +
                (taxIdName != null ? "taxIdName=" + taxIdName + ", " : "") +
                (branch != null ? "branch=" + branch + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (fax != null ? "fax=" + fax + ", " : "") +
                (website != null ? "website=" + website + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (paymentCategory != null ? "paymentCategory=" + paymentCategory + ", " : "") +
                (approvalStatus != null ? "approvalStatus=" + approvalStatus + ", " : "") +
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
            "}";
    }

}

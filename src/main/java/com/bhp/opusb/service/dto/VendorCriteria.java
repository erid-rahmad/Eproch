package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.bhp.opusb.domain.enumeration.VendorType;
import com.bhp.opusb.domain.enumeration.PaymentCategory;
import com.bhp.opusb.domain.enumeration.VendorApprovalStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.Vendor} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.VendorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vendors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VendorCriteria implements Serializable, Criteria {
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
    /**
     * Class for filtering PaymentCategory
     */
    public static class PaymentCategoryFilter extends Filter<PaymentCategory> {

        public PaymentCategoryFilter() {
        }

        public PaymentCategoryFilter(PaymentCategoryFilter filter) {
            super(filter);
        }

        @Override
        public PaymentCategoryFilter copy() {
            return new PaymentCategoryFilter(this);
        }

    }
    /**
     * Class for filtering VendorApprovalStatus
     */
    public static class VendorApprovalStatusFilter extends Filter<VendorApprovalStatus> {

        public VendorApprovalStatusFilter() {
        }

        public VendorApprovalStatusFilter(VendorApprovalStatusFilter filter) {
            super(filter);
        }

        @Override
        public VendorApprovalStatusFilter copy() {
            return new VendorApprovalStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private LongFilter npwp;

    private BooleanFilter branch;

    private StringFilter email;

    private StringFilter phone;

    private StringFilter fax;

    private StringFilter website;

    private VendorTypeFilter type;

    private PaymentCategoryFilter paymentCategory;

    private VendorApprovalStatusFilter approvalStatus;

    private LongFilter locationId;

    private LongFilter companyFunctionaryId;

    private LongFilter personInChargeId;

    private LongFilter supportingDocumentId;

    private LongFilter businessCategoryId;

    public VendorCriteria() {
    }

    public VendorCriteria(VendorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.npwp = other.npwp == null ? null : other.npwp.copy();
        this.branch = other.branch == null ? null : other.branch.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.website = other.website == null ? null : other.website.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.paymentCategory = other.paymentCategory == null ? null : other.paymentCategory.copy();
        this.approvalStatus = other.approvalStatus == null ? null : other.approvalStatus.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.companyFunctionaryId = other.companyFunctionaryId == null ? null : other.companyFunctionaryId.copy();
        this.personInChargeId = other.personInChargeId == null ? null : other.personInChargeId.copy();
        this.supportingDocumentId = other.supportingDocumentId == null ? null : other.supportingDocumentId.copy();
        this.businessCategoryId = other.businessCategoryId == null ? null : other.businessCategoryId.copy();
    }

    @Override
    public VendorCriteria copy() {
        return new VendorCriteria(this);
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

    public LongFilter getNpwp() {
        return npwp;
    }

    public void setNpwp(LongFilter npwp) {
        this.npwp = npwp;
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

    public PaymentCategoryFilter getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(PaymentCategoryFilter paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public VendorApprovalStatusFilter getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(VendorApprovalStatusFilter approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getCompanyFunctionaryId() {
        return companyFunctionaryId;
    }

    public void setCompanyFunctionaryId(LongFilter companyFunctionaryId) {
        this.companyFunctionaryId = companyFunctionaryId;
    }

    public LongFilter getPersonInChargeId() {
        return personInChargeId;
    }

    public void setPersonInChargeId(LongFilter personInChargeId) {
        this.personInChargeId = personInChargeId;
    }

    public LongFilter getSupportingDocumentId() {
        return supportingDocumentId;
    }

    public void setSupportingDocumentId(LongFilter supportingDocumentId) {
        this.supportingDocumentId = supportingDocumentId;
    }

    public LongFilter getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(LongFilter businessCategoryId) {
        this.businessCategoryId = businessCategoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VendorCriteria that = (VendorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(npwp, that.npwp) &&
            Objects.equals(branch, that.branch) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(website, that.website) &&
            Objects.equals(type, that.type) &&
            Objects.equals(paymentCategory, that.paymentCategory) &&
            Objects.equals(approvalStatus, that.approvalStatus) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(companyFunctionaryId, that.companyFunctionaryId) &&
            Objects.equals(personInChargeId, that.personInChargeId) &&
            Objects.equals(supportingDocumentId, that.supportingDocumentId) &&
            Objects.equals(businessCategoryId, that.businessCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        npwp,
        branch,
        email,
        phone,
        fax,
        website,
        type,
        paymentCategory,
        approvalStatus,
        locationId,
        companyFunctionaryId,
        personInChargeId,
        supportingDocumentId,
        businessCategoryId
        );
    }

    @Override
    public String toString() {
        return "VendorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (npwp != null ? "npwp=" + npwp + ", " : "") +
                (branch != null ? "branch=" + branch + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (fax != null ? "fax=" + fax + ", " : "") +
                (website != null ? "website=" + website + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (paymentCategory != null ? "paymentCategory=" + paymentCategory + ", " : "") +
                (approvalStatus != null ? "approvalStatus=" + approvalStatus + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
                (companyFunctionaryId != null ? "companyFunctionaryId=" + companyFunctionaryId + ", " : "") +
                (personInChargeId != null ? "personInChargeId=" + personInChargeId + ", " : "") +
                (supportingDocumentId != null ? "supportingDocumentId=" + supportingDocumentId + ", " : "") +
                (businessCategoryId != null ? "businessCategoryId=" + businessCategoryId + ", " : "") +
            "}";
    }

}

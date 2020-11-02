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
 * Criteria class for the {@link com.bhp.opusb.domain.CVendorBankAcct} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CVendorBankAcctResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-vendor-bank-accts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CVendorBankAcctCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter accountNo;

    private StringFilter name;

    private StringFilter branch;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter vendorId;

    private LongFilter bankId;

    private LongFilter currencyId;

    private LongFilter fileId;

    private LongFilter adOrganizationId;

    public CVendorBankAcctCriteria() {
    }

    public CVendorBankAcctCriteria(CVendorBankAcctCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.accountNo = other.accountNo == null ? null : other.accountNo.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.branch = other.branch == null ? null : other.branch.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.bankId = other.bankId == null ? null : other.bankId.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
        this.fileId = other.fileId == null ? null : other.fileId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public CVendorBankAcctCriteria copy() {
        return new CVendorBankAcctCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(StringFilter accountNo) {
        this.accountNo = accountNo;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getBranch() {
        return branch;
    }

    public void setBranch(StringFilter branch) {
        this.branch = branch;
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

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }

    public LongFilter getBankId() {
        return bankId;
    }

    public void setBankId(LongFilter bankId) {
        this.bankId = bankId;
    }

    public LongFilter getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(LongFilter currencyId) {
        this.currencyId = currencyId;
    }

    public LongFilter getFileId() {
        return fileId;
    }

    public void setFileId(LongFilter fileId) {
        this.fileId = fileId;
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
        final CVendorBankAcctCriteria that = (CVendorBankAcctCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(accountNo, that.accountNo) &&
            Objects.equals(name, that.name) &&
            Objects.equals(branch, that.branch) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(bankId, that.bankId) &&
            Objects.equals(currencyId, that.currencyId) &&
            Objects.equals(fileId, that.fileId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        accountNo,
        name,
        branch,
        uid,
        active,
        vendorId,
        bankId,
        currencyId,
        fileId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "CVendorBankAcctCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (accountNo != null ? "accountNo=" + accountNo + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (branch != null ? "branch=" + branch + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (bankId != null ? "bankId=" + bankId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
                (fileId != null ? "fileId=" + fileId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

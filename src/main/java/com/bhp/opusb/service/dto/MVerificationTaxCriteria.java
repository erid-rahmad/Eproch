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
 * Criteria class for the {@link com.bhp.opusb.domain.MVerificationTax} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MVerificationTaxResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-verification-taxes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MVerificationTaxCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter taxPeriod;

    private StringFilter traxCode;

    private StringFilter statusCode;

    private StringFilter docCode;

    private LongFilter year;

    private StringFilter returnDocType;

    private StringFilter repSerNo;

    private StringFilter taxExpCode;

    private LocalDateFilter dateSSP;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter currencyId;

    private LongFilter taxCategoryId;

    private LongFilter costCenterId;

    public MVerificationTaxCriteria() {
    }

    public MVerificationTaxCriteria(MVerificationTaxCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.taxPeriod = other.taxPeriod == null ? null : other.taxPeriod.copy();
        this.traxCode = other.traxCode == null ? null : other.traxCode.copy();
        this.statusCode = other.statusCode == null ? null : other.statusCode.copy();
        this.docCode = other.docCode == null ? null : other.docCode.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.returnDocType = other.returnDocType == null ? null : other.returnDocType.copy();
        this.repSerNo = other.repSerNo == null ? null : other.repSerNo.copy();
        this.taxExpCode = other.taxExpCode == null ? null : other.taxExpCode.copy();
        this.dateSSP = other.dateSSP == null ? null : other.dateSSP.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
        this.taxCategoryId = other.taxCategoryId == null ? null : other.taxCategoryId.copy();
        this.costCenterId = other.costCenterId == null ? null : other.costCenterId.copy();
    }

    @Override
    public MVerificationTaxCriteria copy() {
        return new MVerificationTaxCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getTaxPeriod() {
        return taxPeriod;
    }

    public void setTaxPeriod(LongFilter taxPeriod) {
        this.taxPeriod = taxPeriod;
    }

    public StringFilter getTraxCode() {
        return traxCode;
    }

    public void setTraxCode(StringFilter traxCode) {
        this.traxCode = traxCode;
    }

    public StringFilter getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StringFilter statusCode) {
        this.statusCode = statusCode;
    }

    public StringFilter getDocCode() {
        return docCode;
    }

    public void setDocCode(StringFilter docCode) {
        this.docCode = docCode;
    }

    public LongFilter getYear() {
        return year;
    }

    public void setYear(LongFilter year) {
        this.year = year;
    }

    public StringFilter getReturnDocType() {
        return returnDocType;
    }

    public void setReturnDocType(StringFilter returnDocType) {
        this.returnDocType = returnDocType;
    }

    public StringFilter getRepSerNo() {
        return repSerNo;
    }

    public void setRepSerNo(StringFilter repSerNo) {
        this.repSerNo = repSerNo;
    }

    public StringFilter getTaxExpCode() {
        return taxExpCode;
    }

    public void setTaxExpCode(StringFilter taxExpCode) {
        this.taxExpCode = taxExpCode;
    }

    public LocalDateFilter getDateSSP() {
        return dateSSP;
    }

    public void setDateSSP(LocalDateFilter dateSSP) {
        this.dateSSP = dateSSP;
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

    public LongFilter getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(LongFilter currencyId) {
        this.currencyId = currencyId;
    }

    public LongFilter getTaxCategoryId() {
        return taxCategoryId;
    }

    public void setTaxCategoryId(LongFilter taxCategoryId) {
        this.taxCategoryId = taxCategoryId;
    }

    public LongFilter getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(LongFilter costCenterId) {
        this.costCenterId = costCenterId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MVerificationTaxCriteria that = (MVerificationTaxCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(taxPeriod, that.taxPeriod) &&
            Objects.equals(traxCode, that.traxCode) &&
            Objects.equals(statusCode, that.statusCode) &&
            Objects.equals(docCode, that.docCode) &&
            Objects.equals(year, that.year) &&
            Objects.equals(returnDocType, that.returnDocType) &&
            Objects.equals(repSerNo, that.repSerNo) &&
            Objects.equals(taxExpCode, that.taxExpCode) &&
            Objects.equals(dateSSP, that.dateSSP) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(currencyId, that.currencyId) &&
            Objects.equals(taxCategoryId, that.taxCategoryId) &&
            Objects.equals(costCenterId, that.costCenterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        taxPeriod,
        traxCode,
        statusCode,
        docCode,
        year,
        returnDocType,
        repSerNo,
        taxExpCode,
        dateSSP,
        uid,
        active,
        adOrganizationId,
        currencyId,
        taxCategoryId,
        costCenterId
        );
    }

    @Override
    public String toString() {
        return "MVerificationTaxCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (taxPeriod != null ? "taxPeriod=" + taxPeriod + ", " : "") +
                (traxCode != null ? "traxCode=" + traxCode + ", " : "") +
                (statusCode != null ? "statusCode=" + statusCode + ", " : "") +
                (docCode != null ? "docCode=" + docCode + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (returnDocType != null ? "returnDocType=" + returnDocType + ", " : "") +
                (repSerNo != null ? "repSerNo=" + repSerNo + ", " : "") +
                (taxExpCode != null ? "taxExpCode=" + taxExpCode + ", " : "") +
                (dateSSP != null ? "dateSSP=" + dateSSP + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
                (taxCategoryId != null ? "taxCategoryId=" + taxCategoryId + ", " : "") +
                (costCenterId != null ? "costCenterId=" + costCenterId + ", " : "") +
            "}";
    }

}

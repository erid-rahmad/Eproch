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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MRfqSubmission} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MRfqSubmissionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-rfq-submissions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MRfqSubmissionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter documentAction;

    private StringFilter documentStatus;

    private BooleanFilter approved;

    private BooleanFilter processed;

    private LocalDateFilter dateTrx;

    private LocalDateFilter dateRequired;

    private LocalDateFilter dateSubmitted;

    private BigDecimalFilter submissionGrandTotal;

    private LongFilter quotationId;

    private LongFilter quoteSupplierId;

    private LongFilter adOrganizationId;

    private LongFilter businessClassificationId;

    private LongFilter businessCategoryId;

    private LongFilter currencyId;

    private LongFilter warehouseId;

    private LongFilter costCenterId;

    public MRfqSubmissionCriteria() {
    }

    public MRfqSubmissionCriteria(MRfqSubmissionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.documentAction = other.documentAction == null ? null : other.documentAction.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.approved = other.approved == null ? null : other.approved.copy();
        this.processed = other.processed == null ? null : other.processed.copy();
        this.dateTrx = other.dateTrx == null ? null : other.dateTrx.copy();
        this.dateRequired = other.dateRequired == null ? null : other.dateRequired.copy();
        this.dateSubmitted = other.dateSubmitted == null ? null : other.dateSubmitted.copy();
        this.submissionGrandTotal = other.submissionGrandTotal == null ? null : other.submissionGrandTotal.copy();
        this.quotationId = other.quotationId == null ? null : other.quotationId.copy();
        this.quoteSupplierId = other.quoteSupplierId == null ? null : other.quoteSupplierId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.businessClassificationId = other.businessClassificationId == null ? null : other.businessClassificationId.copy();
        this.businessCategoryId = other.businessCategoryId == null ? null : other.businessCategoryId.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
        this.warehouseId = other.warehouseId == null ? null : other.warehouseId.copy();
        this.costCenterId = other.costCenterId == null ? null : other.costCenterId.copy();
    }

    @Override
    public MRfqSubmissionCriteria copy() {
        return new MRfqSubmissionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LocalDateFilter getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(LocalDateFilter dateTrx) {
        this.dateTrx = dateTrx;
    }

    public LocalDateFilter getDateRequired() {
        return dateRequired;
    }

    public void setDateRequired(LocalDateFilter dateRequired) {
        this.dateRequired = dateRequired;
    }

    public LocalDateFilter getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(LocalDateFilter dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public BigDecimalFilter getSubmissionGrandTotal() {
        return submissionGrandTotal;
    }

    public void setSubmissionGrandTotal(BigDecimalFilter submissionGrandTotal) {
        this.submissionGrandTotal = submissionGrandTotal;
    }

    public LongFilter getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(LongFilter quotationId) {
        this.quotationId = quotationId;
    }

    public LongFilter getQuoteSupplierId() {
        return quoteSupplierId;
    }

    public void setQuoteSupplierId(LongFilter quoteSupplierId) {
        this.quoteSupplierId = quoteSupplierId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getBusinessClassificationId() {
        return businessClassificationId;
    }

    public void setBusinessClassificationId(LongFilter businessClassificationId) {
        this.businessClassificationId = businessClassificationId;
    }

    public LongFilter getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(LongFilter businessCategoryId) {
        this.businessCategoryId = businessCategoryId;
    }

    public LongFilter getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(LongFilter currencyId) {
        this.currencyId = currencyId;
    }

    public LongFilter getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(LongFilter warehouseId) {
        this.warehouseId = warehouseId;
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
        final MRfqSubmissionCriteria that = (MRfqSubmissionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(documentAction, that.documentAction) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(approved, that.approved) &&
            Objects.equals(processed, that.processed) &&
            Objects.equals(dateTrx, that.dateTrx) &&
            Objects.equals(dateRequired, that.dateRequired) &&
            Objects.equals(dateSubmitted, that.dateSubmitted) &&
            Objects.equals(submissionGrandTotal, that.submissionGrandTotal) &&
            Objects.equals(quotationId, that.quotationId) &&
            Objects.equals(quoteSupplierId, that.quoteSupplierId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(businessClassificationId, that.businessClassificationId) &&
            Objects.equals(businessCategoryId, that.businessCategoryId) &&
            Objects.equals(currencyId, that.currencyId) &&
            Objects.equals(warehouseId, that.warehouseId) &&
            Objects.equals(costCenterId, that.costCenterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        documentAction,
        documentStatus,
        approved,
        processed,
        dateTrx,
        dateRequired,
        dateSubmitted,
        submissionGrandTotal,
        quotationId,
        quoteSupplierId,
        adOrganizationId,
        businessClassificationId,
        businessCategoryId,
        currencyId,
        warehouseId,
        costCenterId
        );
    }

    @Override
    public String toString() {
        return "MRfqSubmissionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (documentAction != null ? "documentAction=" + documentAction + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (approved != null ? "approved=" + approved + ", " : "") +
                (processed != null ? "processed=" + processed + ", " : "") +
                (dateTrx != null ? "dateTrx=" + dateTrx + ", " : "") +
                (dateRequired != null ? "dateRequired=" + dateRequired + ", " : "") +
                (dateSubmitted != null ? "dateSubmitted=" + dateSubmitted + ", " : "") +
                (submissionGrandTotal != null ? "submissionGrandTotal=" + submissionGrandTotal + ", " : "") +
                (quotationId != null ? "quotationId=" + quotationId + ", " : "") +
                (quoteSupplierId != null ? "quoteSupplierId=" + quoteSupplierId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (businessClassificationId != null ? "businessClassificationId=" + businessClassificationId + ", " : "") +
                (businessCategoryId != null ? "businessCategoryId=" + businessCategoryId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
                (warehouseId != null ? "warehouseId=" + warehouseId + ", " : "") +
                (costCenterId != null ? "costCenterId=" + costCenterId + ", " : "") +
            "}";
    }

}

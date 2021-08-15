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
 * Criteria class for the {@link com.bhp.opusb.domain.MRfqSubmissionLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MRfqSubmissionLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-rfq-submission-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MRfqSubmissionLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter documentAction;

    private StringFilter documentStatus;

    private BooleanFilter approved;

    private BooleanFilter processed;

    private IntegerFilter releaseQty;

    private BigDecimalFilter submissionPrice;

    private BigDecimalFilter totalSubmissionPrice;

    private StringFilter remark;

    private LocalDateFilter dateTrx;

    private LocalDateFilter dateRequired;

    private LocalDateFilter dateSubmitted;

    private LongFilter submissionId;

    private LongFilter quotationLineId;

    private LongFilter adOrganizationId;

    private LongFilter productId;

    private LongFilter uomId;

    private LongFilter businessCategoryId;

    public MRfqSubmissionLineCriteria() {
    }

    public MRfqSubmissionLineCriteria(MRfqSubmissionLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.documentAction = other.documentAction == null ? null : other.documentAction.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.approved = other.approved == null ? null : other.approved.copy();
        this.processed = other.processed == null ? null : other.processed.copy();
        this.releaseQty = other.releaseQty == null ? null : other.releaseQty.copy();
        this.submissionPrice = other.submissionPrice == null ? null : other.submissionPrice.copy();
        this.totalSubmissionPrice = other.totalSubmissionPrice == null ? null : other.totalSubmissionPrice.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.dateTrx = other.dateTrx == null ? null : other.dateTrx.copy();
        this.dateRequired = other.dateRequired == null ? null : other.dateRequired.copy();
        this.dateSubmitted = other.dateSubmitted == null ? null : other.dateSubmitted.copy();
        this.submissionId = other.submissionId == null ? null : other.submissionId.copy();
        this.quotationLineId = other.quotationLineId == null ? null : other.quotationLineId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.uomId = other.uomId == null ? null : other.uomId.copy();
        this.businessCategoryId = other.businessCategoryId == null ? null : other.businessCategoryId.copy();
    }

    @Override
    public MRfqSubmissionLineCriteria copy() {
        return new MRfqSubmissionLineCriteria(this);
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

    public IntegerFilter getReleaseQty() {
        return releaseQty;
    }

    public void setReleaseQty(IntegerFilter releaseQty) {
        this.releaseQty = releaseQty;
    }

    public BigDecimalFilter getSubmissionPrice() {
        return submissionPrice;
    }

    public void setSubmissionPrice(BigDecimalFilter submissionPrice) {
        this.submissionPrice = submissionPrice;
    }

    public BigDecimalFilter getTotalSubmissionPrice() {
        return totalSubmissionPrice;
    }

    public void setTotalSubmissionPrice(BigDecimalFilter totalSubmissionPrice) {
        this.totalSubmissionPrice = totalSubmissionPrice;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
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

    public LongFilter getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(LongFilter submissionId) {
        this.submissionId = submissionId;
    }

    public LongFilter getQuotationLineId() {
        return quotationLineId;
    }

    public void setQuotationLineId(LongFilter quotationLineId) {
        this.quotationLineId = quotationLineId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getUomId() {
        return uomId;
    }

    public void setUomId(LongFilter uomId) {
        this.uomId = uomId;
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
        final MRfqSubmissionLineCriteria that = (MRfqSubmissionLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(documentAction, that.documentAction) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(approved, that.approved) &&
            Objects.equals(processed, that.processed) &&
            Objects.equals(releaseQty, that.releaseQty) &&
            Objects.equals(submissionPrice, that.submissionPrice) &&
            Objects.equals(totalSubmissionPrice, that.totalSubmissionPrice) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(dateTrx, that.dateTrx) &&
            Objects.equals(dateRequired, that.dateRequired) &&
            Objects.equals(dateSubmitted, that.dateSubmitted) &&
            Objects.equals(submissionId, that.submissionId) &&
            Objects.equals(quotationLineId, that.quotationLineId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(uomId, that.uomId) &&
            Objects.equals(businessCategoryId, that.businessCategoryId);
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
        releaseQty,
        submissionPrice,
        totalSubmissionPrice,
        remark,
        dateTrx,
        dateRequired,
        dateSubmitted,
        submissionId,
        quotationLineId,
        adOrganizationId,
        productId,
        uomId,
        businessCategoryId
        );
    }

    @Override
    public String toString() {
        return "MRfqSubmissionLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (documentAction != null ? "documentAction=" + documentAction + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (approved != null ? "approved=" + approved + ", " : "") +
                (processed != null ? "processed=" + processed + ", " : "") +
                (releaseQty != null ? "releaseQty=" + releaseQty + ", " : "") +
                (submissionPrice != null ? "submissionPrice=" + submissionPrice + ", " : "") +
                (totalSubmissionPrice != null ? "totalSubmissionPrice=" + totalSubmissionPrice + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (dateTrx != null ? "dateTrx=" + dateTrx + ", " : "") +
                (dateRequired != null ? "dateRequired=" + dateRequired + ", " : "") +
                (dateSubmitted != null ? "dateSubmitted=" + dateSubmitted + ", " : "") +
                (submissionId != null ? "submissionId=" + submissionId + ", " : "") +
                (quotationLineId != null ? "quotationLineId=" + quotationLineId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (uomId != null ? "uomId=" + uomId + ", " : "") +
                (businessCategoryId != null ? "businessCategoryId=" + businessCategoryId + ", " : "") +
            "}";
    }

}

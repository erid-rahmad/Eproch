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
 * Criteria class for the {@link com.bhp.opusb.domain.MRfq} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MRfqResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-rfqs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MRfqCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dateTrx;

    private LocalDateFilter dateRequired;

    private StringFilter documentNo;

    private StringFilter documentAction;

    private StringFilter documentStatus;

    private BooleanFilter approved;

    private BooleanFilter processed;

    private LocalDateFilter datePromised;

    private StringFilter description;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter businessClassificationId;

    private LongFilter businessCategoryId;

    private LongFilter currencyId;

    private LongFilter warehouseId;

    private LongFilter costCenterId;

    private LongFilter documentTypeId;

    public MRfqCriteria() {
    }

    public MRfqCriteria(MRfqCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateTrx = other.dateTrx == null ? null : other.dateTrx.copy();
        this.dateRequired = other.dateRequired == null ? null : other.dateRequired.copy();
        this.documentNo = other.documentNo == null ? null : other.documentNo.copy();
        this.documentAction = other.documentAction == null ? null : other.documentAction.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.approved = other.approved == null ? null : other.approved.copy();
        this.processed = other.processed == null ? null : other.processed.copy();
        this.datePromised = other.datePromised == null ? null : other.datePromised.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.businessClassificationId = other.businessClassificationId == null ? null : other.businessClassificationId.copy();
        this.businessCategoryId = other.businessCategoryId == null ? null : other.businessCategoryId.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
        this.warehouseId = other.warehouseId == null ? null : other.warehouseId.copy();
        this.costCenterId = other.costCenterId == null ? null : other.costCenterId.copy();
        this.documentTypeId = other.documentTypeId == null ? null : other.documentTypeId.copy();
    }

    @Override
    public MRfqCriteria copy() {
        return new MRfqCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LocalDateFilter getDatePromised() {
        return datePromised;
    }

    public void setDatePromised(LocalDateFilter datePromised) {
        this.datePromised = datePromised;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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
        final MRfqCriteria that = (MRfqCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dateTrx, that.dateTrx) &&
            Objects.equals(dateRequired, that.dateRequired) &&
            Objects.equals(documentNo, that.documentNo) &&
            Objects.equals(documentAction, that.documentAction) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(approved, that.approved) &&
            Objects.equals(processed, that.processed) &&
            Objects.equals(datePromised, that.datePromised) &&
            Objects.equals(description, that.description) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(businessClassificationId, that.businessClassificationId) &&
            Objects.equals(businessCategoryId, that.businessCategoryId) &&
            Objects.equals(currencyId, that.currencyId) &&
            Objects.equals(warehouseId, that.warehouseId) &&
            Objects.equals(costCenterId, that.costCenterId) &&
            Objects.equals(documentTypeId, that.documentTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dateTrx,
        dateRequired,
        documentNo,
        documentAction,
        documentStatus,
        approved,
        processed,
        datePromised,
        description,
        uid,
        active,
        adOrganizationId,
        businessClassificationId,
        businessCategoryId,
        currencyId,
        warehouseId,
        costCenterId,
        documentTypeId
        );
    }

    @Override
    public String toString() {
        return "MRfqCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateTrx != null ? "dateTrx=" + dateTrx + ", " : "") +
                (dateRequired != null ? "dateRequired=" + dateRequired + ", " : "") +
                (documentNo != null ? "documentNo=" + documentNo + ", " : "") +
                (documentAction != null ? "documentAction=" + documentAction + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (approved != null ? "approved=" + approved + ", " : "") +
                (processed != null ? "processed=" + processed + ", " : "") +
                (datePromised != null ? "datePromised=" + datePromised + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (businessClassificationId != null ? "businessClassificationId=" + businessClassificationId + ", " : "") +
                (businessCategoryId != null ? "businessCategoryId=" + businessCategoryId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
                (warehouseId != null ? "warehouseId=" + warehouseId + ", " : "") +
                (costCenterId != null ? "costCenterId=" + costCenterId + ", " : "") +
                (documentTypeId != null ? "documentTypeId=" + documentTypeId + ", " : "") +
            "}";
    }

}

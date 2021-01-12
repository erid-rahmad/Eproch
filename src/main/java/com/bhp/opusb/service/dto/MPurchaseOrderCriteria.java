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
 * Criteria class for the {@link com.bhp.opusb.domain.MPurchaseOrder} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MPurchaseOrderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-purchase-orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MPurchaseOrderCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documentAction;

    private StringFilter documentStatus;

    private BooleanFilter isApproved;

    private BooleanFilter isProcessed;

    private BooleanFilter tax;

    private LocalDateFilter documentDate;

    private LocalDateFilter dateRequired;

    private StringFilter description;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter documentTypeId;

    private LongFilter vendorId;

    private LongFilter currencyId;

    private LongFilter warehouseId;

    private LongFilter costCenterId;

    public MPurchaseOrderCriteria() {
    }

    public MPurchaseOrderCriteria(MPurchaseOrderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentAction = other.documentAction == null ? null : other.documentAction.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.isApproved = other.isApproved == null ? null : other.isApproved.copy();
        this.isProcessed = other.isProcessed == null ? null : other.isProcessed.copy();
        this.tax = other.tax == null ? null : other.tax.copy();
        this.documentDate = other.documentDate == null ? null : other.documentDate.copy();
        this.dateRequired = other.dateRequired == null ? null : other.dateRequired.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.documentTypeId = other.documentTypeId == null ? null : other.documentTypeId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
        this.warehouseId = other.warehouseId == null ? null : other.warehouseId.copy();
        this.costCenterId = other.costCenterId == null ? null : other.costCenterId.copy();
    }

    @Override
    public MPurchaseOrderCriteria copy() {
        return new MPurchaseOrderCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public BooleanFilter getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(BooleanFilter isApproved) {
        this.isApproved = isApproved;
    }

    public BooleanFilter getIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(BooleanFilter isProcessed) {
        this.isProcessed = isProcessed;
    }

    public BooleanFilter getTax() {
        return tax;
    }

    public void setTax(BooleanFilter tax) {
        this.tax = tax;
    }

    public LocalDateFilter getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateFilter documentDate) {
        this.documentDate = documentDate;
    }

    public LocalDateFilter getDateRequired() {
        return dateRequired;
    }

    public void setDateRequired(LocalDateFilter dateRequired) {
        this.dateRequired = dateRequired;
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

    public LongFilter getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(LongFilter documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
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
        final MPurchaseOrderCriteria that = (MPurchaseOrderCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(documentAction, that.documentAction) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(isApproved, that.isApproved) &&
            Objects.equals(isProcessed, that.isProcessed) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(documentDate, that.documentDate) &&
            Objects.equals(dateRequired, that.dateRequired) &&
            Objects.equals(description, that.description) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(documentTypeId, that.documentTypeId) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(currencyId, that.currencyId) &&
            Objects.equals(warehouseId, that.warehouseId) &&
            Objects.equals(costCenterId, that.costCenterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        documentAction,
        documentStatus,
        isApproved,
        isProcessed,
        tax,
        documentDate,
        dateRequired,
        description,
        uid,
        active,
        adOrganizationId,
        documentTypeId,
        vendorId,
        currencyId,
        warehouseId,
        costCenterId
        );
    }

    @Override
    public String toString() {
        return "MPurchaseOrderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentAction != null ? "documentAction=" + documentAction + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (isApproved != null ? "isApproved=" + isApproved + ", " : "") +
                (isProcessed != null ? "isProcessed=" + isProcessed + ", " : "") +
                (tax != null ? "tax=" + tax + ", " : "") +
                (documentDate != null ? "documentDate=" + documentDate + ", " : "") +
                (dateRequired != null ? "dateRequired=" + dateRequired + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (documentTypeId != null ? "documentTypeId=" + documentTypeId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
                (warehouseId != null ? "warehouseId=" + warehouseId + ", " : "") +
                (costCenterId != null ? "costCenterId=" + costCenterId + ", " : "") +
            "}";
    }

}

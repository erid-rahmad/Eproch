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

    private LocalDateFilter dateTrx;

    private StringFilter documentNo;

    private StringFilter documentAction;

    private StringFilter documentStatus;

    private BooleanFilter approved;

    private BooleanFilter processed;

    private BooleanFilter confirmed;

    private BigDecimalFilter grandTotal;

    private BooleanFilter tax;

    private LocalDateFilter datePromised;

    private LocalDateFilter dateDelivered;

    private LocalDateFilter dateShipped;

    private StringFilter confirmation;

    private StringFilter description;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter documentTypeId;

    private LongFilter vendorId;

    private LongFilter currencyId;

    private LongFilter warehouseId;

    private LongFilter costCenterId;

    private LongFilter paymentTermId;

    public MPurchaseOrderCriteria() {
    }

    public MPurchaseOrderCriteria(MPurchaseOrderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateTrx = other.dateTrx == null ? null : other.dateTrx.copy();
        this.documentNo = other.documentNo == null ? null : other.documentNo.copy();
        this.documentAction = other.documentAction == null ? null : other.documentAction.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.approved = other.approved == null ? null : other.approved.copy();
        this.processed = other.processed == null ? null : other.processed.copy();
        this.confirmed = other.confirmed == null ? null : other.confirmed.copy();
        this.grandTotal = other.grandTotal == null ? null : other.grandTotal.copy();
        this.tax = other.tax == null ? null : other.tax.copy();
        this.datePromised = other.datePromised == null ? null : other.datePromised.copy();
        this.dateDelivered = other.dateDelivered == null ? null : other.dateDelivered.copy();
        this.dateShipped = other.dateShipped == null ? null : other.dateShipped.copy();
        this.confirmation = other.confirmation == null ? null : other.confirmation.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.documentTypeId = other.documentTypeId == null ? null : other.documentTypeId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
        this.warehouseId = other.warehouseId == null ? null : other.warehouseId.copy();
        this.costCenterId = other.costCenterId == null ? null : other.costCenterId.copy();
        this.paymentTermId = other.paymentTermId == null ? null : other.paymentTermId.copy();
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

    public BooleanFilter getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(BooleanFilter confirmed) {
        this.confirmed = confirmed;
    }

    public BigDecimalFilter getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimalFilter grandTotal) {
        this.grandTotal = grandTotal;
    }

    public BooleanFilter getTax() {
        return tax;
    }

    public void setTax(BooleanFilter tax) {
        this.tax = tax;
    }

    public LocalDateFilter getDatePromised() {
        return datePromised;
    }

    public void setDatePromised(LocalDateFilter datePromised) {
        this.datePromised = datePromised;
    }

    public LocalDateFilter getDateDelivered() {
        return dateDelivered;
    }

    public void setDateDelivered(LocalDateFilter dateDelivered) {
        this.dateDelivered = dateDelivered;
    }

    public LocalDateFilter getDateShipped() {
        return dateShipped;
    }

    public void setDateShipped(LocalDateFilter dateShipped) {
        this.dateShipped = dateShipped;
    }

    public StringFilter getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(StringFilter confirmation) {
        this.confirmation = confirmation;
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

    public LongFilter getPaymentTermId() {
        return paymentTermId;
    }

    public void setPaymentTermId(LongFilter paymentTermId) {
        this.paymentTermId = paymentTermId;
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
            Objects.equals(dateTrx, that.dateTrx) &&
            Objects.equals(documentNo, that.documentNo) &&
            Objects.equals(documentAction, that.documentAction) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(approved, that.approved) &&
            Objects.equals(processed, that.processed) &&
            Objects.equals(confirmed, that.confirmed) &&
            Objects.equals(grandTotal, that.grandTotal) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(datePromised, that.datePromised) &&
            Objects.equals(dateDelivered, that.dateDelivered) &&
            Objects.equals(dateShipped, that.dateShipped) &&
            Objects.equals(confirmation, that.confirmation) &&
            Objects.equals(description, that.description) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(documentTypeId, that.documentTypeId) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(currencyId, that.currencyId) &&
            Objects.equals(warehouseId, that.warehouseId) &&
            Objects.equals(costCenterId, that.costCenterId) &&
            Objects.equals(paymentTermId, that.paymentTermId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dateTrx,
        documentNo,
        documentAction,
        documentStatus,
        approved,
        processed,
        confirmed,
        grandTotal,
        tax,
        datePromised,
        dateDelivered,
        dateShipped,
        confirmation,
        description,
        uid,
        active,
        adOrganizationId,
        documentTypeId,
        vendorId,
        currencyId,
        warehouseId,
        costCenterId,
        paymentTermId
        );
    }

    @Override
    public String toString() {
        return "MPurchaseOrderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateTrx != null ? "dateTrx=" + dateTrx + ", " : "") +
                (documentNo != null ? "documentNo=" + documentNo + ", " : "") +
                (documentAction != null ? "documentAction=" + documentAction + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (approved != null ? "approved=" + approved + ", " : "") +
                (processed != null ? "processed=" + processed + ", " : "") +
                (confirmed != null ? "confirmed=" + confirmed + ", " : "") +
                (grandTotal != null ? "grandTotal=" + grandTotal + ", " : "") +
                (tax != null ? "tax=" + tax + ", " : "") +
                (datePromised != null ? "datePromised=" + datePromised + ", " : "") +
                (dateDelivered != null ? "dateDelivered=" + dateDelivered + ", " : "") +
                (dateShipped != null ? "dateShipped=" + dateShipped + ", " : "") +
                (confirmation != null ? "confirmation=" + confirmation + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (documentTypeId != null ? "documentTypeId=" + documentTypeId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
                (warehouseId != null ? "warehouseId=" + warehouseId + ", " : "") +
                (costCenterId != null ? "costCenterId=" + costCenterId + ", " : "") +
                (paymentTermId != null ? "paymentTermId=" + paymentTermId + ", " : "") +
            "}";
    }

}

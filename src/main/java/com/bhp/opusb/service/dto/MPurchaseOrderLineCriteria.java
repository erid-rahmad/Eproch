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
 * Criteria class for the {@link com.bhp.opusb.domain.MPurchaseOrderLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MPurchaseOrderLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-purchase-order-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MPurchaseOrderLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter documentDate;

    private LocalDateFilter datePromised;

    private LocalDateFilter dateRequired;

    private BigDecimalFilter orderAmount;

    private BigDecimalFilter quantity;

    private BigDecimalFilter unitPrice;

    private StringFilter remark;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter purchaseOrderId;

    private LongFilter requisitionId;

    private LongFilter taxId;

    private LongFilter adOrganizationId;

    private LongFilter productId;

    private LongFilter warehouseId;

    private LongFilter costCenterId;

    private LongFilter uomId;

    private LongFilter vendorId;

    public MPurchaseOrderLineCriteria() {
    }

    public MPurchaseOrderLineCriteria(MPurchaseOrderLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentDate = other.documentDate == null ? null : other.documentDate.copy();
        this.datePromised = other.datePromised == null ? null : other.datePromised.copy();
        this.dateRequired = other.dateRequired == null ? null : other.dateRequired.copy();
        this.orderAmount = other.orderAmount == null ? null : other.orderAmount.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.unitPrice = other.unitPrice == null ? null : other.unitPrice.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.purchaseOrderId = other.purchaseOrderId == null ? null : other.purchaseOrderId.copy();
        this.requisitionId = other.requisitionId == null ? null : other.requisitionId.copy();
        this.taxId = other.taxId == null ? null : other.taxId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.warehouseId = other.warehouseId == null ? null : other.warehouseId.copy();
        this.costCenterId = other.costCenterId == null ? null : other.costCenterId.copy();
        this.uomId = other.uomId == null ? null : other.uomId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
    }

    @Override
    public MPurchaseOrderLineCriteria copy() {
        return new MPurchaseOrderLineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateFilter documentDate) {
        this.documentDate = documentDate;
    }

    public LocalDateFilter getDatePromised() {
        return datePromised;
    }

    public void setDatePromised(LocalDateFilter datePromised) {
        this.datePromised = datePromised;
    }

    public LocalDateFilter getDateRequired() {
        return dateRequired;
    }

    public void setDateRequired(LocalDateFilter dateRequired) {
        this.dateRequired = dateRequired;
    }

    public BigDecimalFilter getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimalFilter orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
    }

    public BigDecimalFilter getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimalFilter unitPrice) {
        this.unitPrice = unitPrice;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
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

    public LongFilter getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(LongFilter purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public LongFilter getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(LongFilter requisitionId) {
        this.requisitionId = requisitionId;
    }

    public LongFilter getTaxId() {
        return taxId;
    }

    public void setTaxId(LongFilter taxId) {
        this.taxId = taxId;
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

    public LongFilter getUomId() {
        return uomId;
    }

    public void setUomId(LongFilter uomId) {
        this.uomId = uomId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MPurchaseOrderLineCriteria that = (MPurchaseOrderLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(documentDate, that.documentDate) &&
            Objects.equals(datePromised, that.datePromised) &&
            Objects.equals(dateRequired, that.dateRequired) &&
            Objects.equals(orderAmount, that.orderAmount) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(purchaseOrderId, that.purchaseOrderId) &&
            Objects.equals(requisitionId, that.requisitionId) &&
            Objects.equals(taxId, that.taxId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(warehouseId, that.warehouseId) &&
            Objects.equals(costCenterId, that.costCenterId) &&
            Objects.equals(uomId, that.uomId) &&
            Objects.equals(vendorId, that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        documentDate,
        datePromised,
        dateRequired,
        orderAmount,
        quantity,
        unitPrice,
        remark,
        uid,
        active,
        purchaseOrderId,
        requisitionId,
        taxId,
        adOrganizationId,
        productId,
        warehouseId,
        costCenterId,
        uomId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "MPurchaseOrderLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentDate != null ? "documentDate=" + documentDate + ", " : "") +
                (datePromised != null ? "datePromised=" + datePromised + ", " : "") +
                (dateRequired != null ? "dateRequired=" + dateRequired + ", " : "") +
                (orderAmount != null ? "orderAmount=" + orderAmount + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (purchaseOrderId != null ? "purchaseOrderId=" + purchaseOrderId + ", " : "") +
                (requisitionId != null ? "requisitionId=" + requisitionId + ", " : "") +
                (taxId != null ? "taxId=" + taxId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (warehouseId != null ? "warehouseId=" + warehouseId + ", " : "") +
                (costCenterId != null ? "costCenterId=" + costCenterId + ", " : "") +
                (uomId != null ? "uomId=" + uomId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}

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
 * Criteria class for the {@link com.bhp.opusb.domain.MRequisitionLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MRequisitionLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-requisition-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MRequisitionLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter documentDate;

    private LocalDateFilter datePromised;

    private LocalDateFilter dateRequired;

    private BigDecimalFilter requisitionAmount;

    private BigDecimalFilter quantity;

    private BigDecimalFilter unitPrice;

    private StringFilter remark;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter requisitionId;

    private LongFilter adOrganizationId;

    private LongFilter productId;

    private LongFilter warehouseId;

    private LongFilter costCenterId;

    private LongFilter uomId;

    private LongFilter vendorId;

    public MRequisitionLineCriteria() {
    }

    public MRequisitionLineCriteria(MRequisitionLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentDate = other.documentDate == null ? null : other.documentDate.copy();
        this.datePromised = other.datePromised == null ? null : other.datePromised.copy();
        this.dateRequired = other.dateRequired == null ? null : other.dateRequired.copy();
        this.requisitionAmount = other.requisitionAmount == null ? null : other.requisitionAmount.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.unitPrice = other.unitPrice == null ? null : other.unitPrice.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.requisitionId = other.requisitionId == null ? null : other.requisitionId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.warehouseId = other.warehouseId == null ? null : other.warehouseId.copy();
        this.costCenterId = other.costCenterId == null ? null : other.costCenterId.copy();
        this.uomId = other.uomId == null ? null : other.uomId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
    }

    @Override
    public MRequisitionLineCriteria copy() {
        return new MRequisitionLineCriteria(this);
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

    public BigDecimalFilter getRequisitionAmount() {
        return requisitionAmount;
    }

    public void setRequisitionAmount(BigDecimalFilter requisitionAmount) {
        this.requisitionAmount = requisitionAmount;
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

    public LongFilter getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(LongFilter requisitionId) {
        this.requisitionId = requisitionId;
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
        final MRequisitionLineCriteria that = (MRequisitionLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(documentDate, that.documentDate) &&
            Objects.equals(datePromised, that.datePromised) &&
            Objects.equals(dateRequired, that.dateRequired) &&
            Objects.equals(requisitionAmount, that.requisitionAmount) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(requisitionId, that.requisitionId) &&
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
        requisitionAmount,
        quantity,
        unitPrice,
        remark,
        uid,
        active,
        requisitionId,
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
        return "MRequisitionLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentDate != null ? "documentDate=" + documentDate + ", " : "") +
                (datePromised != null ? "datePromised=" + datePromised + ", " : "") +
                (dateRequired != null ? "dateRequired=" + dateRequired + ", " : "") +
                (requisitionAmount != null ? "requisitionAmount=" + requisitionAmount + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (requisitionId != null ? "requisitionId=" + requisitionId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (warehouseId != null ? "warehouseId=" + warehouseId + ", " : "") +
                (costCenterId != null ? "costCenterId=" + costCenterId + ", " : "") +
                (uomId != null ? "uomId=" + uomId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}

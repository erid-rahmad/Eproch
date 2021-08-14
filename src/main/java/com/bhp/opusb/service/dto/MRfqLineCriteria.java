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
 * Criteria class for the {@link com.bhp.opusb.domain.MRfqLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MRfqLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-rfq-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MRfqLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter lineNo;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter documentAction;

    private StringFilter documentStatus;

    private BooleanFilter approved;

    private BooleanFilter processed;

    private IntegerFilter releaseQty;

    private BigDecimalFilter unitPrice;

    private BigDecimalFilter orderAmount;

    private LocalDateFilter documentDate;

    private LocalDateFilter datePromised;

    private LocalDateFilter dateRequired;

    private StringFilter remark;

    private IntegerFilter quantityBalance;

    private LongFilter quotationId;
    private StringFilter quotationNo;
    private StringFilter quotationMethod;

    private LongFilter adOrganizationId;

    private LongFilter productId;

    private LongFilter uomId;

    private LongFilter businessCategoryId;

    private LongFilter businessClassificationId;

    private LongFilter warehouseId;

    private LongFilter costCenterId;

    public MRfqLineCriteria() {
    }

    public MRfqLineCriteria(MRfqLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.lineNo = other.lineNo == null ? null : other.lineNo.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.documentAction = other.documentAction == null ? null : other.documentAction.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.approved = other.approved == null ? null : other.approved.copy();
        this.processed = other.processed == null ? null : other.processed.copy();
        this.releaseQty = other.releaseQty == null ? null : other.releaseQty.copy();
        this.unitPrice = other.unitPrice == null ? null : other.unitPrice.copy();
        this.orderAmount = other.orderAmount == null ? null : other.orderAmount.copy();
        this.documentDate = other.documentDate == null ? null : other.documentDate.copy();
        this.datePromised = other.datePromised == null ? null : other.datePromised.copy();
        this.dateRequired = other.dateRequired == null ? null : other.dateRequired.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.quantityBalance = other.quantityBalance == null ? null : other.quantityBalance.copy();
        this.quotationId = other.quotationId == null ? null : other.quotationId.copy();
        this.quotationNo = other.quotationNo == null ? null : other.quotationNo.copy();
        this.quotationMethod = other.quotationMethod == null ? null : other.quotationMethod.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.uomId = other.uomId == null ? null : other.uomId.copy();
        this.businessCategoryId = other.businessCategoryId == null ? null : other.businessCategoryId.copy();
        this.businessClassificationId = other.businessClassificationId == null ? null : other.businessClassificationId.copy();
        this.warehouseId = other.warehouseId == null ? null : other.warehouseId.copy();
        this.costCenterId = other.costCenterId == null ? null : other.costCenterId.copy();
    }

    @Override
    public MRfqLineCriteria copy() {
        return new MRfqLineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getLineNo() {
        return lineNo;
    }

    public void setLineNo(IntegerFilter lineNo) {
        this.lineNo = lineNo;
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

    public BigDecimalFilter getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimalFilter unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimalFilter getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimalFilter orderAmount) {
        this.orderAmount = orderAmount;
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

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }

    public IntegerFilter getQuantityBalance() {
        return quantityBalance;
    }

    public void setQuantityBalance(IntegerFilter quantityBalance) {
        this.quantityBalance = quantityBalance;
    }

    public LongFilter getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(LongFilter quotationId) {
        this.quotationId = quotationId;
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

    public LongFilter getBusinessClassificationId() {
        return businessClassificationId;
    }

    public void setBusinessClassificationId(LongFilter businessClassificationId) {
        this.businessClassificationId = businessClassificationId;
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

    public StringFilter getQuotationNo() {
        return quotationNo;
    }

    public void setQuotationNo(StringFilter quotationNo) {
        this.quotationNo = quotationNo;
    }

    public StringFilter getQuotationMethod() {
        return quotationMethod;
    }

    public void setQuotationMethod(StringFilter quotationMethod) {
        this.quotationMethod = quotationMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MRfqLineCriteria that = (MRfqLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(lineNo, that.lineNo) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(documentAction, that.documentAction) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(approved, that.approved) &&
            Objects.equals(processed, that.processed) &&
            Objects.equals(releaseQty, that.releaseQty) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(orderAmount, that.orderAmount) &&
            Objects.equals(documentDate, that.documentDate) &&
            Objects.equals(datePromised, that.datePromised) &&
            Objects.equals(dateRequired, that.dateRequired) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(quantityBalance, that.quantityBalance) &&
            Objects.equals(quotationId, that.quotationId) &&
            Objects.equals(quotationNo, that.quotationNo) &&
            Objects.equals(quotationMethod, that.quotationMethod) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(uomId, that.uomId) &&
            Objects.equals(businessCategoryId, that.businessCategoryId) &&
            Objects.equals(businessClassificationId, that.businessClassificationId) &&
            Objects.equals(warehouseId, that.warehouseId) &&
            Objects.equals(costCenterId, that.costCenterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        lineNo,
        uid,
        active,
        documentAction,
        documentStatus,
        approved,
        processed,
        releaseQty,
        unitPrice,
        orderAmount,
        documentDate,
        datePromised,
        dateRequired,
        remark,
        quantityBalance,
        quotationId,
            quotationNo,
            quotationMethod,
        adOrganizationId,
        productId,
        uomId,
        businessCategoryId,
        businessClassificationId,
        warehouseId,
        costCenterId
        );
    }

    @Override
    public String toString() {
        return "MRfqLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (lineNo != null ? "lineNo=" + lineNo + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (documentAction != null ? "documentAction=" + documentAction + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (approved != null ? "approved=" + approved + ", " : "") +
                (processed != null ? "processed=" + processed + ", " : "") +
                (releaseQty != null ? "releaseQty=" + releaseQty + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (orderAmount != null ? "orderAmount=" + orderAmount + ", " : "") +
                (documentDate != null ? "documentDate=" + documentDate + ", " : "") +
                (datePromised != null ? "datePromised=" + datePromised + ", " : "") +
                (dateRequired != null ? "dateRequired=" + dateRequired + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (quantityBalance != null ? "quantityBalance=" + quantityBalance + ", " : "") +
                (quotationId != null ? "quotationId=" + quotationId + ", " : "") +
                (quotationNo != null ? "quotationNo=" + quotationNo + ", " : "") +
                (quotationMethod != null ? "quotationMethod=" + quotationMethod + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (uomId != null ? "uomId=" + uomId + ", " : "") +
                (businessCategoryId != null ? "businessCategoryId=" + businessCategoryId + ", " : "") +
                (businessClassificationId != null ? "businessClassificationId=" + businessClassificationId + ", " : "") +
                (warehouseId != null ? "warehouseId=" + warehouseId + ", " : "") +
                (costCenterId != null ? "costCenterId=" + costCenterId + ", " : "") +
            "}";
    }

}

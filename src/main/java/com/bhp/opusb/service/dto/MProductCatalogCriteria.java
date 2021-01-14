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
 * Criteria class for the {@link com.bhp.opusb.domain.MProductCatalog} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MProductCatalogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-product-catalogs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MProductCatalogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter shortDescription;

    private StringFilter sku;

    private DoubleFilter height;

    private DoubleFilter length;

    private DoubleFilter width;

    private DoubleFilter weight;

    private BigDecimalFilter price;

    private LocalDateFilter expiredDate;

    private BooleanFilter preOrder;

    private IntegerFilter preOrderDuration;

    private StringFilter warranty;

    private BooleanFilter sold;

    private LongFilter stockAvailable;

    private StringFilter documentAction;

    private StringFilter documentStatus;

    private BooleanFilter approved;

    private BooleanFilter processed;

    private StringFilter rejectedReason;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter cGalleryId;

    private LongFilter mProductPriceId;

    private LongFilter adOrganizationId;

    private LongFilter cDocumentTypeId;

    private LongFilter cCurrencyId;

    private LongFilter cUomId;

    private LongFilter cVendorId;

    private LongFilter mBrandId;

    private LongFilter mProductId;

    public MProductCatalogCriteria() {
    }

    public MProductCatalogCriteria(MProductCatalogCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.shortDescription = other.shortDescription == null ? null : other.shortDescription.copy();
        this.sku = other.sku == null ? null : other.sku.copy();
        this.height = other.height == null ? null : other.height.copy();
        this.length = other.length == null ? null : other.length.copy();
        this.width = other.width == null ? null : other.width.copy();
        this.weight = other.weight == null ? null : other.weight.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.expiredDate = other.expiredDate == null ? null : other.expiredDate.copy();
        this.preOrder = other.preOrder == null ? null : other.preOrder.copy();
        this.preOrderDuration = other.preOrderDuration == null ? null : other.preOrderDuration.copy();
        this.warranty = other.warranty == null ? null : other.warranty.copy();
        this.sold = other.sold == null ? null : other.sold.copy();
        this.stockAvailable = other.stockAvailable == null ? null : other.stockAvailable.copy();
        this.documentAction = other.documentAction == null ? null : other.documentAction.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.approved = other.approved == null ? null : other.approved.copy();
        this.processed = other.processed == null ? null : other.processed.copy();
        this.rejectedReason = other.rejectedReason == null ? null : other.rejectedReason.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.cGalleryId = other.cGalleryId == null ? null : other.cGalleryId.copy();
        this.mProductPriceId = other.mProductPriceId == null ? null : other.mProductPriceId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.cDocumentTypeId = other.cDocumentTypeId == null ? null : other.cDocumentTypeId.copy();
        this.cCurrencyId = other.cCurrencyId == null ? null : other.cCurrencyId.copy();
        this.cUomId = other.cUomId == null ? null : other.cUomId.copy();
        this.cVendorId = other.cVendorId == null ? null : other.cVendorId.copy();
        this.mBrandId = other.mBrandId == null ? null : other.mBrandId.copy();
        this.mProductId = other.mProductId == null ? null : other.mProductId.copy();
    }

    @Override
    public MProductCatalogCriteria copy() {
        return new MProductCatalogCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(StringFilter shortDescription) {
        this.shortDescription = shortDescription;
    }

    public StringFilter getSku() {
        return sku;
    }

    public void setSku(StringFilter sku) {
        this.sku = sku;
    }

    public DoubleFilter getHeight() {
        return height;
    }

    public void setHeight(DoubleFilter height) {
        this.height = height;
    }

    public DoubleFilter getLength() {
        return length;
    }

    public void setLength(DoubleFilter length) {
        this.length = length;
    }

    public DoubleFilter getWidth() {
        return width;
    }

    public void setWidth(DoubleFilter width) {
        this.width = width;
    }

    public DoubleFilter getWeight() {
        return weight;
    }

    public void setWeight(DoubleFilter weight) {
        this.weight = weight;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public LocalDateFilter getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDateFilter expiredDate) {
        this.expiredDate = expiredDate;
    }

    public BooleanFilter getPreOrder() {
        return preOrder;
    }

    public void setPreOrder(BooleanFilter preOrder) {
        this.preOrder = preOrder;
    }

    public IntegerFilter getPreOrderDuration() {
        return preOrderDuration;
    }

    public void setPreOrderDuration(IntegerFilter preOrderDuration) {
        this.preOrderDuration = preOrderDuration;
    }

    public StringFilter getWarranty() {
        return warranty;
    }

    public void setWarranty(StringFilter warranty) {
        this.warranty = warranty;
    }

    public BooleanFilter getSold() {
        return sold;
    }

    public void setSold(BooleanFilter sold) {
        this.sold = sold;
    }

    public LongFilter getStockAvailable() {
        return stockAvailable;
    }

    public void setStockAvailable(LongFilter stockAvailable) {
        this.stockAvailable = stockAvailable;
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

    public StringFilter getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(StringFilter rejectedReason) {
        this.rejectedReason = rejectedReason;
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

    public LongFilter getCGalleryId() {
        return cGalleryId;
    }

    public void setCGalleryId(LongFilter cGalleryId) {
        this.cGalleryId = cGalleryId;
    }

    public LongFilter getMProductPriceId() {
        return mProductPriceId;
    }

    public void setMProductPriceId(LongFilter mProductPriceId) {
        this.mProductPriceId = mProductPriceId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getCDocumentTypeId() {
        return cDocumentTypeId;
    }

    public void setCDocumentTypeId(LongFilter cDocumentTypeId) {
        this.cDocumentTypeId = cDocumentTypeId;
    }

    public LongFilter getCCurrencyId() {
        return cCurrencyId;
    }

    public void setCCurrencyId(LongFilter cCurrencyId) {
        this.cCurrencyId = cCurrencyId;
    }

    public LongFilter getCUomId() {
        return cUomId;
    }

    public void setCUomId(LongFilter cUomId) {
        this.cUomId = cUomId;
    }

    public LongFilter getCVendorId() {
        return cVendorId;
    }

    public void setCVendorId(LongFilter cVendorId) {
        this.cVendorId = cVendorId;
    }

    public LongFilter getMBrandId() {
        return mBrandId;
    }

    public void setMBrandId(LongFilter mBrandId) {
        this.mBrandId = mBrandId;
    }

    public LongFilter getMProductId() {
        return mProductId;
    }

    public void setMProductId(LongFilter mProductId) {
        this.mProductId = mProductId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MProductCatalogCriteria that = (MProductCatalogCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(shortDescription, that.shortDescription) &&
            Objects.equals(sku, that.sku) &&
            Objects.equals(height, that.height) &&
            Objects.equals(length, that.length) &&
            Objects.equals(width, that.width) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(price, that.price) &&
            Objects.equals(expiredDate, that.expiredDate) &&
            Objects.equals(preOrder, that.preOrder) &&
            Objects.equals(preOrderDuration, that.preOrderDuration) &&
            Objects.equals(warranty, that.warranty) &&
            Objects.equals(sold, that.sold) &&
            Objects.equals(stockAvailable, that.stockAvailable) &&
            Objects.equals(documentAction, that.documentAction) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(approved, that.approved) &&
            Objects.equals(processed, that.processed) &&
            Objects.equals(rejectedReason, that.rejectedReason) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(cGalleryId, that.cGalleryId) &&
            Objects.equals(mProductPriceId, that.mProductPriceId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(cDocumentTypeId, that.cDocumentTypeId) &&
            Objects.equals(cCurrencyId, that.cCurrencyId) &&
            Objects.equals(cUomId, that.cUomId) &&
            Objects.equals(cVendorId, that.cVendorId) &&
            Objects.equals(mBrandId, that.mBrandId) &&
            Objects.equals(mProductId, that.mProductId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        shortDescription,
        sku,
        height,
        length,
        width,
        weight,
        price,
        expiredDate,
        preOrder,
        preOrderDuration,
        warranty,
        sold,
        stockAvailable,
        documentAction,
        documentStatus,
        approved,
        processed,
        rejectedReason,
        uid,
        active,
        cGalleryId,
        mProductPriceId,
        adOrganizationId,
        cDocumentTypeId,
        cCurrencyId,
        cUomId,
        cVendorId,
        mBrandId,
        mProductId
        );
    }

    @Override
    public String toString() {
        return "MProductCatalogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (shortDescription != null ? "shortDescription=" + shortDescription + ", " : "") +
                (sku != null ? "sku=" + sku + ", " : "") +
                (height != null ? "height=" + height + ", " : "") +
                (length != null ? "length=" + length + ", " : "") +
                (width != null ? "width=" + width + ", " : "") +
                (weight != null ? "weight=" + weight + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (expiredDate != null ? "expiredDate=" + expiredDate + ", " : "") +
                (preOrder != null ? "preOrder=" + preOrder + ", " : "") +
                (preOrderDuration != null ? "preOrderDuration=" + preOrderDuration + ", " : "") +
                (warranty != null ? "warranty=" + warranty + ", " : "") +
                (sold != null ? "sold=" + sold + ", " : "") +
                (stockAvailable != null ? "stockAvailable=" + stockAvailable + ", " : "") +
                (documentAction != null ? "documentAction=" + documentAction + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (approved != null ? "approved=" + approved + ", " : "") +
                (processed != null ? "processed=" + processed + ", " : "") +
                (rejectedReason != null ? "rejectedReason=" + rejectedReason + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (cGalleryId != null ? "cGalleryId=" + cGalleryId + ", " : "") +
                (mProductPriceId != null ? "mProductPriceId=" + mProductPriceId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (cDocumentTypeId != null ? "cDocumentTypeId=" + cDocumentTypeId + ", " : "") +
                (cCurrencyId != null ? "cCurrencyId=" + cCurrencyId + ", " : "") +
                (cUomId != null ? "cUomId=" + cUomId + ", " : "") +
                (cVendorId != null ? "cVendorId=" + cVendorId + ", " : "") +
                (mBrandId != null ? "mBrandId=" + mBrandId + ", " : "") +
                (mProductId != null ? "mProductId=" + mProductId + ", " : "") +
            "}";
    }

}

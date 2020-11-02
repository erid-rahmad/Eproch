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
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CProduct} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private StringFilter type;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter productClassificationId;

    private LongFilter productCategoryId;

    private LongFilter productSubCategoryId;

    private LongFilter assetAcctId;

    private LongFilter expenseAcctId;

    private LongFilter uomId;

    public CProductCriteria() {
    }

    public CProductCriteria(CProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.productClassificationId = other.productClassificationId == null ? null : other.productClassificationId.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
        this.productSubCategoryId = other.productSubCategoryId == null ? null : other.productSubCategoryId.copy();
        this.assetAcctId = other.assetAcctId == null ? null : other.assetAcctId.copy();
        this.expenseAcctId = other.expenseAcctId == null ? null : other.expenseAcctId.copy();
        this.uomId = other.uomId == null ? null : other.uomId.copy();
    }

    @Override
    public CProductCriteria copy() {
        return new CProductCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
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

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
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

    public LongFilter getProductClassificationId() {
        return productClassificationId;
    }

    public void setProductClassificationId(LongFilter productClassificationId) {
        this.productClassificationId = productClassificationId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public LongFilter getProductSubCategoryId() {
        return productSubCategoryId;
    }

    public void setProductSubCategoryId(LongFilter productSubCategoryId) {
        this.productSubCategoryId = productSubCategoryId;
    }

    public LongFilter getAssetAcctId() {
        return assetAcctId;
    }

    public void setAssetAcctId(LongFilter assetAcctId) {
        this.assetAcctId = assetAcctId;
    }

    public LongFilter getExpenseAcctId() {
        return expenseAcctId;
    }

    public void setExpenseAcctId(LongFilter expenseAcctId) {
        this.expenseAcctId = expenseAcctId;
    }

    public LongFilter getUomId() {
        return uomId;
    }

    public void setUomId(LongFilter uomId) {
        this.uomId = uomId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CProductCriteria that = (CProductCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(productClassificationId, that.productClassificationId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(productSubCategoryId, that.productSubCategoryId) &&
            Objects.equals(assetAcctId, that.assetAcctId) &&
            Objects.equals(expenseAcctId, that.expenseAcctId) &&
            Objects.equals(uomId, that.uomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        description,
        type,
        uid,
        active,
        adOrganizationId,
        productClassificationId,
        productCategoryId,
        productSubCategoryId,
        assetAcctId,
        expenseAcctId,
        uomId
        );
    }

    @Override
    public String toString() {
        return "CProductCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (productClassificationId != null ? "productClassificationId=" + productClassificationId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (productSubCategoryId != null ? "productSubCategoryId=" + productSubCategoryId + ", " : "") +
                (assetAcctId != null ? "assetAcctId=" + assetAcctId + ", " : "") +
                (expenseAcctId != null ? "expenseAcctId=" + expenseAcctId + ", " : "") +
                (uomId != null ? "uomId=" + uomId + ", " : "") +
            "}";
    }

}

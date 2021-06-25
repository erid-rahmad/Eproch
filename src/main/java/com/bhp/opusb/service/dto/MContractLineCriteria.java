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
 * Criteria class for the {@link com.bhp.opusb.domain.MContractLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MContractLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-contract-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MContractLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter lineNo;

    private BigDecimalFilter quantity;

    private BigDecimalFilter ceilingPrice;

    private BigDecimalFilter totalCeilingPrice;

    private LocalDateFilter deliveryDate;

    private StringFilter remark;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter contractId;

    private LongFilter adOrganizationId;

    private LongFilter costCenterId;

    private LongFilter productId;

    private LongFilter uomId;

    public MContractLineCriteria() {
    }

    public MContractLineCriteria(MContractLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.lineNo = other.lineNo == null ? null : other.lineNo.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.ceilingPrice = other.ceilingPrice == null ? null : other.ceilingPrice.copy();
        this.totalCeilingPrice = other.totalCeilingPrice == null ? null : other.totalCeilingPrice.copy();
        this.deliveryDate = other.deliveryDate == null ? null : other.deliveryDate.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.contractId = other.contractId == null ? null : other.contractId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.costCenterId = other.costCenterId == null ? null : other.costCenterId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.uomId = other.uomId == null ? null : other.uomId.copy();
    }

    @Override
    public MContractLineCriteria copy() {
        return new MContractLineCriteria(this);
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

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
    }

    public BigDecimalFilter getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(BigDecimalFilter ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public BigDecimalFilter getTotalCeilingPrice() {
        return totalCeilingPrice;
    }

    public void setTotalCeilingPrice(BigDecimalFilter totalCeilingPrice) {
        this.totalCeilingPrice = totalCeilingPrice;
    }

    public LocalDateFilter getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateFilter deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public LongFilter getContractId() {
        return contractId;
    }

    public void setContractId(LongFilter contractId) {
        this.contractId = contractId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(LongFilter costCenterId) {
        this.costCenterId = costCenterId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MContractLineCriteria that = (MContractLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(lineNo, that.lineNo) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(ceilingPrice, that.ceilingPrice) &&
            Objects.equals(totalCeilingPrice, that.totalCeilingPrice) &&
            Objects.equals(deliveryDate, that.deliveryDate) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(contractId, that.contractId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(costCenterId, that.costCenterId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(uomId, that.uomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        lineNo,
        quantity,
        ceilingPrice,
        totalCeilingPrice,
        deliveryDate,
        remark,
        uid,
        active,
        contractId,
        adOrganizationId,
        costCenterId,
        productId,
        uomId
        );
    }

    @Override
    public String toString() {
        return "MContractLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (lineNo != null ? "lineNo=" + lineNo + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (ceilingPrice != null ? "ceilingPrice=" + ceilingPrice + ", " : "") +
                (totalCeilingPrice != null ? "totalCeilingPrice=" + totalCeilingPrice + ", " : "") +
                (deliveryDate != null ? "deliveryDate=" + deliveryDate + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (contractId != null ? "contractId=" + contractId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (costCenterId != null ? "costCenterId=" + costCenterId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (uomId != null ? "uomId=" + uomId + ", " : "") +
            "}";
    }

}

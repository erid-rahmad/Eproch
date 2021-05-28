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
 * Criteria class for the {@link com.bhp.opusb.domain.MVendorConfirmationContract} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MVendorConfirmationContractResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-vendor-confirmation-contracts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MVendorConfirmationContractCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LocalDateFilter contractStartDate;

    private LocalDateFilter contractEndDate;

    private LongFilter adOrganizationId;

    private LongFilter attachmentId;

    private LongFilter vendorConfirmationLineId;

    public MVendorConfirmationContractCriteria() {
    }

    public MVendorConfirmationContractCriteria(MVendorConfirmationContractCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.contractStartDate = other.contractStartDate == null ? null : other.contractStartDate.copy();
        this.contractEndDate = other.contractEndDate == null ? null : other.contractEndDate.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.attachmentId = other.attachmentId == null ? null : other.attachmentId.copy();
        this.vendorConfirmationLineId = other.vendorConfirmationLineId == null ? null : other.vendorConfirmationLineId.copy();
    }

    @Override
    public MVendorConfirmationContractCriteria copy() {
        return new MVendorConfirmationContractCriteria(this);
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

    public LocalDateFilter getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(LocalDateFilter contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public LocalDateFilter getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(LocalDateFilter contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(LongFilter attachmentId) {
        this.attachmentId = attachmentId;
    }

    public LongFilter getVendorConfirmationLineId() {
        return vendorConfirmationLineId;
    }

    public void setVendorConfirmationLineId(LongFilter vendorConfirmationLineId) {
        this.vendorConfirmationLineId = vendorConfirmationLineId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MVendorConfirmationContractCriteria that = (MVendorConfirmationContractCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(contractStartDate, that.contractStartDate) &&
            Objects.equals(contractEndDate, that.contractEndDate) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(attachmentId, that.attachmentId) &&
            Objects.equals(vendorConfirmationLineId, that.vendorConfirmationLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        contractStartDate,
        contractEndDate,
        adOrganizationId,
        attachmentId,
        vendorConfirmationLineId
        );
    }

    @Override
    public String toString() {
        return "MVendorConfirmationContractCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (contractStartDate != null ? "contractStartDate=" + contractStartDate + ", " : "") +
                (contractEndDate != null ? "contractEndDate=" + contractEndDate + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (attachmentId != null ? "attachmentId=" + attachmentId + ", " : "") +
                (vendorConfirmationLineId != null ? "vendorConfirmationLineId=" + vendorConfirmationLineId + ", " : "") +
            "}";
    }

}

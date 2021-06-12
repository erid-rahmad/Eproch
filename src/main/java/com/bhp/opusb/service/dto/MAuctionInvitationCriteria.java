package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MAuctionInvitation} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MAuctionInvitationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-auction-invitations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MAuctionInvitationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter dateTrx;

    private StringFilter documentNo;

    private StringFilter documentAction;

    private StringFilter documentStatus;

    private BooleanFilter approved;

    private BooleanFilter processed;

    private ZonedDateTimeFilter dateApprove;

    private ZonedDateTimeFilter dateReject;

    private StringFilter rejectedReason;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter auctionId;

    private LongFilter documentTypeId;

    private LongFilter vendorId;

    public MAuctionInvitationCriteria() {
    }

    public MAuctionInvitationCriteria(MAuctionInvitationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateTrx = other.dateTrx == null ? null : other.dateTrx.copy();
        this.documentNo = other.documentNo == null ? null : other.documentNo.copy();
        this.documentAction = other.documentAction == null ? null : other.documentAction.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.approved = other.approved == null ? null : other.approved.copy();
        this.processed = other.processed == null ? null : other.processed.copy();
        this.dateApprove = other.dateApprove == null ? null : other.dateApprove.copy();
        this.dateReject = other.dateReject == null ? null : other.dateReject.copy();
        this.rejectedReason = other.rejectedReason == null ? null : other.rejectedReason.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.auctionId = other.auctionId == null ? null : other.auctionId.copy();
        this.documentTypeId = other.documentTypeId == null ? null : other.documentTypeId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
    }

    @Override
    public MAuctionInvitationCriteria copy() {
        return new MAuctionInvitationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(ZonedDateTimeFilter dateTrx) {
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

    public ZonedDateTimeFilter getDateApprove() {
        return dateApprove;
    }

    public void setDateApprove(ZonedDateTimeFilter dateApprove) {
        this.dateApprove = dateApprove;
    }

    public ZonedDateTimeFilter getDateReject() {
        return dateReject;
    }

    public void setDateReject(ZonedDateTimeFilter dateReject) {
        this.dateReject = dateReject;
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

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(LongFilter auctionId) {
        this.auctionId = auctionId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MAuctionInvitationCriteria that = (MAuctionInvitationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dateTrx, that.dateTrx) &&
            Objects.equals(documentNo, that.documentNo) &&
            Objects.equals(documentAction, that.documentAction) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(approved, that.approved) &&
            Objects.equals(processed, that.processed) &&
            Objects.equals(dateApprove, that.dateApprove) &&
            Objects.equals(dateReject, that.dateReject) &&
            Objects.equals(rejectedReason, that.rejectedReason) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(auctionId, that.auctionId) &&
            Objects.equals(documentTypeId, that.documentTypeId) &&
            Objects.equals(vendorId, that.vendorId);
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
        dateApprove,
        dateReject,
        rejectedReason,
        uid,
        active,
        adOrganizationId,
        auctionId,
        documentTypeId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "MAuctionInvitationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateTrx != null ? "dateTrx=" + dateTrx + ", " : "") +
                (documentNo != null ? "documentNo=" + documentNo + ", " : "") +
                (documentAction != null ? "documentAction=" + documentAction + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (approved != null ? "approved=" + approved + ", " : "") +
                (processed != null ? "processed=" + processed + ", " : "") +
                (dateApprove != null ? "dateApprove=" + dateApprove + ", " : "") +
                (dateReject != null ? "dateReject=" + dateReject + ", " : "") +
                (rejectedReason != null ? "rejectedReason=" + rejectedReason + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (auctionId != null ? "auctionId=" + auctionId + ", " : "") +
                (documentTypeId != null ? "documentTypeId=" + documentTypeId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}

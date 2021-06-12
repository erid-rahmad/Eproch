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
 * Criteria class for the {@link com.bhp.opusb.domain.MBiddingSubmission} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBiddingSubmissionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-bidding-submissions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBiddingSubmissionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter joined;

    private ZonedDateTimeFilter dateTrx;

    private StringFilter documentNo;

    private StringFilter documentAction;

    private StringFilter documentStatus;

    private BooleanFilter approved;

    private BooleanFilter processed;

    private ZonedDateTimeFilter dateApprove;

    private ZonedDateTimeFilter dateReject;

    private StringFilter rejectedReason;

    private ZonedDateTimeFilter dateSubmit;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter biddingId;

    private LongFilter biddingScheduleId;

    private LongFilter documentTypeId;

    private LongFilter vendorId;

    public MBiddingSubmissionCriteria() {
    }

    public MBiddingSubmissionCriteria(MBiddingSubmissionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.joined = other.joined == null ? null : other.joined.copy();
        this.dateTrx = other.dateTrx == null ? null : other.dateTrx.copy();
        this.documentNo = other.documentNo == null ? null : other.documentNo.copy();
        this.documentAction = other.documentAction == null ? null : other.documentAction.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.approved = other.approved == null ? null : other.approved.copy();
        this.processed = other.processed == null ? null : other.processed.copy();
        this.dateApprove = other.dateApprove == null ? null : other.dateApprove.copy();
        this.dateReject = other.dateReject == null ? null : other.dateReject.copy();
        this.rejectedReason = other.rejectedReason == null ? null : other.rejectedReason.copy();
        this.dateSubmit = other.dateSubmit == null ? null : other.dateSubmit.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.biddingId = other.biddingId == null ? null : other.biddingId.copy();
        this.biddingScheduleId = other.biddingScheduleId == null ? null : other.biddingScheduleId.copy();
        this.documentTypeId = other.documentTypeId == null ? null : other.documentTypeId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
    }

    @Override
    public MBiddingSubmissionCriteria copy() {
        return new MBiddingSubmissionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getJoined() {
        return joined;
    }

    public void setJoined(BooleanFilter joined) {
        this.joined = joined;
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

    public ZonedDateTimeFilter getDateSubmit() {
        return dateSubmit;
    }

    public void setDateSubmit(ZonedDateTimeFilter dateSubmit) {
        this.dateSubmit = dateSubmit;
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

    public LongFilter getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(LongFilter biddingId) {
        this.biddingId = biddingId;
    }

    public LongFilter getBiddingScheduleId() {
        return biddingScheduleId;
    }

    public void setBiddingScheduleId(LongFilter biddingScheduleId) {
        this.biddingScheduleId = biddingScheduleId;
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
        final MBiddingSubmissionCriteria that = (MBiddingSubmissionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(joined, that.joined) &&
            Objects.equals(dateTrx, that.dateTrx) &&
            Objects.equals(documentNo, that.documentNo) &&
            Objects.equals(documentAction, that.documentAction) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(approved, that.approved) &&
            Objects.equals(processed, that.processed) &&
            Objects.equals(dateApprove, that.dateApprove) &&
            Objects.equals(dateReject, that.dateReject) &&
            Objects.equals(rejectedReason, that.rejectedReason) &&
            Objects.equals(dateSubmit, that.dateSubmit) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(biddingId, that.biddingId) &&
            Objects.equals(biddingScheduleId, that.biddingScheduleId) &&
            Objects.equals(documentTypeId, that.documentTypeId) &&
            Objects.equals(vendorId, that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        joined,
        dateTrx,
        documentNo,
        documentAction,
        documentStatus,
        approved,
        processed,
        dateApprove,
        dateReject,
        rejectedReason,
        dateSubmit,
        uid,
        active,
        adOrganizationId,
        biddingId,
        biddingScheduleId,
        documentTypeId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "MBiddingSubmissionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (joined != null ? "joined=" + joined + ", " : "") +
                (dateTrx != null ? "dateTrx=" + dateTrx + ", " : "") +
                (documentNo != null ? "documentNo=" + documentNo + ", " : "") +
                (documentAction != null ? "documentAction=" + documentAction + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (approved != null ? "approved=" + approved + ", " : "") +
                (processed != null ? "processed=" + processed + ", " : "") +
                (dateApprove != null ? "dateApprove=" + dateApprove + ", " : "") +
                (dateReject != null ? "dateReject=" + dateReject + ", " : "") +
                (rejectedReason != null ? "rejectedReason=" + rejectedReason + ", " : "") +
                (dateSubmit != null ? "dateSubmit=" + dateSubmit + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (biddingId != null ? "biddingId=" + biddingId + ", " : "") +
                (biddingScheduleId != null ? "biddingScheduleId=" + biddingScheduleId + ", " : "") +
                (documentTypeId != null ? "documentTypeId=" + documentTypeId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}

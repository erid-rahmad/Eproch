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
 * Criteria class for the {@link com.bhp.opusb.domain.MDocumentSchedule} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MDocumentScheduleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-document-schedules?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MDocumentScheduleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter docEvent;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter biddingId;

    private LongFilter adOrganizationId;

    private LongFilter vendorSubmissionId;

    private LongFilter vendorEvaluationId;

    public MDocumentScheduleCriteria() {
    }

    public MDocumentScheduleCriteria(MDocumentScheduleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.docEvent = other.docEvent == null ? null : other.docEvent.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.biddingId = other.biddingId == null ? null : other.biddingId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.vendorSubmissionId = other.vendorSubmissionId == null ? null : other.vendorSubmissionId.copy();
        this.vendorEvaluationId = other.vendorEvaluationId == null ? null : other.vendorEvaluationId.copy();
    }

    @Override
    public MDocumentScheduleCriteria copy() {
        return new MDocumentScheduleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDocEvent() {
        return docEvent;
    }

    public void setDocEvent(StringFilter docEvent) {
        this.docEvent = docEvent;
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

    public LongFilter getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(LongFilter biddingId) {
        this.biddingId = biddingId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getVendorSubmissionId() {
        return vendorSubmissionId;
    }

    public void setVendorSubmissionId(LongFilter vendorSubmissionId) {
        this.vendorSubmissionId = vendorSubmissionId;
    }

    public LongFilter getVendorEvaluationId() {
        return vendorEvaluationId;
    }

    public void setVendorEvaluationId(LongFilter vendorEvaluationId) {
        this.vendorEvaluationId = vendorEvaluationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MDocumentScheduleCriteria that = (MDocumentScheduleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(docEvent, that.docEvent) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(biddingId, that.biddingId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(vendorSubmissionId, that.vendorSubmissionId) &&
            Objects.equals(vendorEvaluationId, that.vendorEvaluationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        docEvent,
        uid,
        active,
        biddingId,
        adOrganizationId,
        vendorSubmissionId,
        vendorEvaluationId
        );
    }

    @Override
    public String toString() {
        return "MDocumentScheduleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (docEvent != null ? "docEvent=" + docEvent + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (biddingId != null ? "biddingId=" + biddingId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (vendorSubmissionId != null ? "vendorSubmissionId=" + vendorSubmissionId + ", " : "") +
                (vendorEvaluationId != null ? "vendorEvaluationId=" + vendorEvaluationId + ", " : "") +
            "}";
    }

}

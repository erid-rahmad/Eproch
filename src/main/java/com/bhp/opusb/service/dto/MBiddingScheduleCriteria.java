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
 * Criteria class for the {@link com.bhp.opusb.domain.MBiddingSchedule} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBiddingScheduleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-bidding-schedules?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBiddingScheduleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter startDate;

    private ZonedDateTimeFilter endDate;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter dateSetId;
    private ZonedDateTimeFilter actualStartDate;
    private ZonedDateTimeFilter actualEndDate;
    private StringFilter status;

    private LongFilter mBiddingScheduleAttachmentId;

    private LongFilter biddingId;

    private LongFilter adOrganizationId;

    private LongFilter eventTypeLineId;
    private StringFilter formType;

    public MBiddingScheduleCriteria() {
    }

    public MBiddingScheduleCriteria(MBiddingScheduleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.dateSetId = other.dateSetId == null ? null : other.dateSetId.copy();
        this.actualStartDate = other.actualStartDate == null ? null : other.actualStartDate.copy();
        this.actualEndDate = other.actualEndDate == null ? null : other.actualEndDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.mBiddingScheduleAttachmentId = other.mBiddingScheduleAttachmentId == null ? null : other.mBiddingScheduleAttachmentId.copy();
        this.biddingId = other.biddingId == null ? null : other.biddingId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.eventTypeLineId = other.eventTypeLineId == null ? null : other.eventTypeLineId.copy();
        this.formType = other.formType == null ? null : other.formType.copy();
    }

    @Override
    public MBiddingScheduleCriteria copy() {
        return new MBiddingScheduleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTimeFilter startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTimeFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTimeFilter endDate) {
        this.endDate = endDate;
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

    public LongFilter getDateSetId() {
        return dateSetId;
    }

    public void setDateSetId(LongFilter dateSetId) {
        this.dateSetId = dateSetId;
    }

    public ZonedDateTimeFilter getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(ZonedDateTimeFilter actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public ZonedDateTimeFilter getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(ZonedDateTimeFilter actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getMBiddingScheduleAttachmentId() {
        return mBiddingScheduleAttachmentId;
    }

    public void setMBiddingScheduleAttachmentId(LongFilter mBiddingScheduleAttachmentId) {
        this.mBiddingScheduleAttachmentId = mBiddingScheduleAttachmentId;
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

    public LongFilter getEventTypeLineId() {
        return eventTypeLineId;
    }

    public void setEventTypeLineId(LongFilter eventTypeLineId) {
        this.eventTypeLineId = eventTypeLineId;
    }

    public StringFilter getFormType() {
        return formType;
    }

    public void setFormType(StringFilter formType) {
        this.formType = formType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MBiddingScheduleCriteria that = (MBiddingScheduleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(dateSetId, that.dateSetId) &&
            Objects.equals(actualStartDate, that.actualStartDate) &&
            Objects.equals(actualEndDate, that.actualEndDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(mBiddingScheduleAttachmentId, that.mBiddingScheduleAttachmentId) &&
            Objects.equals(biddingId, that.biddingId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(eventTypeLineId, that.eventTypeLineId) &&
            Objects.equals(formType, that.formType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        startDate,
        endDate,
        uid,
        active,
        dateSetId,
        actualStartDate,
        actualEndDate,
        status,
        mBiddingScheduleAttachmentId,
        biddingId,
        adOrganizationId,
        eventTypeLineId,
        formType
        );
    }

    @Override
    public String toString() {
        return "MBiddingScheduleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (dateSetId != null ? "dateSetId=" + dateSetId + ", " : "") +
                (actualStartDate != null ? "actualStartDate=" + actualStartDate + ", " : "") +
                (actualEndDate != null ? "actualEndDate=" + actualEndDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (mBiddingScheduleAttachmentId != null ? "mBiddingScheduleAttachmentId=" + mBiddingScheduleAttachmentId + ", " : "") +
                (biddingId != null ? "biddingId=" + biddingId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (eventTypeLineId != null ? "eventTypeLineId=" + eventTypeLineId + ", " : "") +
                (formType != null ? "formType=" + formType + ", " : "") +
            "}";
    }

}

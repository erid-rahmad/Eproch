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
 * Criteria class for the {@link com.bhp.opusb.domain.MPrequalificationDateSet} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MPrequalificationDateSetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-prequalification-date-sets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MPrequalificationDateSetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter startDate;

    private ZonedDateTimeFilter endDate;

    private StringFilter status;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter biddingScheduleId;

    private LongFilter prequalificationScheduleId;

    public MPrequalificationDateSetCriteria() {
    }

    public MPrequalificationDateSetCriteria(MPrequalificationDateSetCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.biddingScheduleId = other.biddingScheduleId == null ? null : other.biddingScheduleId.copy();
        this.prequalificationScheduleId = other.prequalificationScheduleId == null ? null : other.prequalificationScheduleId.copy();
    }

    @Override
    public MPrequalificationDateSetCriteria copy() {
        return new MPrequalificationDateSetCriteria(this);
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

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
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

    public LongFilter getBiddingScheduleId() {
        return biddingScheduleId;
    }

    public void setBiddingScheduleId(LongFilter biddingScheduleId) {
        this.biddingScheduleId = biddingScheduleId;
    }

    public LongFilter getPrequalificationScheduleId() {
        return prequalificationScheduleId;
    }

    public void setPrequalificationScheduleId(LongFilter prequalificationScheduleId) {
        this.prequalificationScheduleId = prequalificationScheduleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MPrequalificationDateSetCriteria that = (MPrequalificationDateSetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(biddingScheduleId, that.biddingScheduleId) &&
            Objects.equals(prequalificationScheduleId, that.prequalificationScheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        startDate,
        endDate,
        status,
        uid,
        active,
        adOrganizationId,
        biddingScheduleId,
        prequalificationScheduleId
        );
    }

    @Override
    public String toString() {
        return "MPrequalificationDateSetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (biddingScheduleId != null ? "biddingScheduleId=" + biddingScheduleId + ", " : "") +
                (prequalificationScheduleId != null ? "prequalificationScheduleId=" + prequalificationScheduleId + ", " : "") +
            "}";
    }

}

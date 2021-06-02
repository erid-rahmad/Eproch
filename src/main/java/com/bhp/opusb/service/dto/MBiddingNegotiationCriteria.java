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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MBiddingNegotiation} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBiddingNegotiationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-bidding-negotiations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBiddingNegotiationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter biddingStatus;

    private StringFilter evaluationStatus;

    private ZonedDateTimeFilter startDate;

    private ZonedDateTimeFilter endDate;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter biddingEvalId;

    private LongFilter adOrganizationId;

    private LongFilter biddingScheduleId;

    public MBiddingNegotiationCriteria() {
    }

    public MBiddingNegotiationCriteria(MBiddingNegotiationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.biddingStatus = other.biddingStatus == null ? null : other.biddingStatus.copy();
        this.evaluationStatus = other.evaluationStatus == null ? null : other.evaluationStatus.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.biddingEvalId = other.biddingEvalId == null ? null : other.biddingEvalId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.biddingScheduleId = other.biddingScheduleId == null ? null : other.biddingScheduleId.copy();
    }

    @Override
    public MBiddingNegotiationCriteria copy() {
        return new MBiddingNegotiationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(StringFilter biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public StringFilter getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(StringFilter evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
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

    public LongFilter getBiddingEvalId() {
        return biddingEvalId;
    }

    public void setBiddingEvalId(LongFilter biddingEvalId) {
        this.biddingEvalId = biddingEvalId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MBiddingNegotiationCriteria that = (MBiddingNegotiationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(biddingStatus, that.biddingStatus) &&
            Objects.equals(evaluationStatus, that.evaluationStatus) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(biddingEvalId, that.biddingEvalId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(biddingScheduleId, that.biddingScheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        biddingStatus,
        evaluationStatus,
        startDate,
        endDate,
        uid,
        active,
        biddingEvalId,
        adOrganizationId,
        biddingScheduleId
        );
    }

    @Override
    public String toString() {
        return "MBiddingNegotiationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (biddingStatus != null ? "biddingStatus=" + biddingStatus + ", " : "") +
                (evaluationStatus != null ? "evaluationStatus=" + evaluationStatus + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (biddingEvalId != null ? "biddingEvalId=" + biddingEvalId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (biddingScheduleId != null ? "biddingScheduleId=" + biddingScheduleId + ", " : "") +
            "}";
    }

}

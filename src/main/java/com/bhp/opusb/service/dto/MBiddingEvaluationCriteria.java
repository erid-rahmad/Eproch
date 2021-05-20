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
 * Criteria class for the {@link com.bhp.opusb.domain.MBiddingEvaluation} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBiddingEvaluationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-bidding-evaluations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBiddingEvaluationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter evaluationStatus;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter biddingSubmissionId;

    private LongFilter biddingScheduleId;

    private LongFilter adOrganizationId;

    public MBiddingEvaluationCriteria() {
    }

    public MBiddingEvaluationCriteria(MBiddingEvaluationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.evaluationStatus = other.evaluationStatus == null ? null : other.evaluationStatus.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.biddingSubmissionId = other.biddingSubmissionId == null ? null : other.biddingSubmissionId.copy();
        this.biddingScheduleId = other.biddingScheduleId == null ? null : other.biddingScheduleId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public MBiddingEvaluationCriteria copy() {
        return new MBiddingEvaluationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(StringFilter evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
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

    public LongFilter getBiddingSubmissionId() {
        return biddingSubmissionId;
    }

    public void setBiddingSubmissionId(LongFilter biddingSubmissionId) {
        this.biddingSubmissionId = biddingSubmissionId;
    }

    public LongFilter getBiddingScheduleId() {
        return biddingScheduleId;
    }

    public void setBiddingScheduleId(LongFilter biddingScheduleId) {
        this.biddingScheduleId = biddingScheduleId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MBiddingEvaluationCriteria that = (MBiddingEvaluationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(evaluationStatus, that.evaluationStatus) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(biddingSubmissionId, that.biddingSubmissionId) &&
            Objects.equals(biddingScheduleId, that.biddingScheduleId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        evaluationStatus,
        uid,
        active,
        biddingSubmissionId,
        biddingScheduleId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "MBiddingEvaluationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (evaluationStatus != null ? "evaluationStatus=" + evaluationStatus + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (biddingSubmissionId != null ? "biddingSubmissionId=" + biddingSubmissionId + ", " : "") +
                (biddingScheduleId != null ? "biddingScheduleId=" + biddingScheduleId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

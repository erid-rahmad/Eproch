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
 * Criteria class for the {@link com.bhp.opusb.domain.MBiddingEvalResult} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBiddingEvalResultResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-bidding-eval-results?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBiddingEvalResultCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter evaluationStatus;

    private StringFilter status;

    private IntegerFilter score;

    private IntegerFilter rank;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter biddingSubmissionId;

    public MBiddingEvalResultCriteria() {
    }

    public MBiddingEvalResultCriteria(MBiddingEvalResultCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.evaluationStatus = other.evaluationStatus == null ? null : other.evaluationStatus.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.score = other.score == null ? null : other.score.copy();
        this.rank = other.rank == null ? null : other.rank.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.biddingSubmissionId = other.biddingSubmissionId == null ? null : other.biddingSubmissionId.copy();
    }

    @Override
    public MBiddingEvalResultCriteria copy() {
        return new MBiddingEvalResultCriteria(this);
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

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public IntegerFilter getScore() {
        return score;
    }

    public void setScore(IntegerFilter score) {
        this.score = score;
    }

    public IntegerFilter getRank() {
        return rank;
    }

    public void setRank(IntegerFilter rank) {
        this.rank = rank;
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

    public LongFilter getBiddingSubmissionId() {
        return biddingSubmissionId;
    }

    public void setBiddingSubmissionId(LongFilter biddingSubmissionId) {
        this.biddingSubmissionId = biddingSubmissionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MBiddingEvalResultCriteria that = (MBiddingEvalResultCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(evaluationStatus, that.evaluationStatus) &&
            Objects.equals(status, that.status) &&
            Objects.equals(score, that.score) &&
            Objects.equals(rank, that.rank) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(biddingSubmissionId, that.biddingSubmissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        evaluationStatus,
        status,
        score,
        rank,
        uid,
        active,
        adOrganizationId,
        biddingSubmissionId
        );
    }

    @Override
    public String toString() {
        return "MBiddingEvalResultCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (evaluationStatus != null ? "evaluationStatus=" + evaluationStatus + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (score != null ? "score=" + score + ", " : "") +
                (rank != null ? "rank=" + rank + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (biddingSubmissionId != null ? "biddingSubmissionId=" + biddingSubmissionId + ", " : "") +
            "}";
    }

}

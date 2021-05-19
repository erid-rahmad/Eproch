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
 * Criteria class for the {@link com.bhp.opusb.domain.MBiddingEvaluationLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBiddingEvaluationLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-bidding-evaluation-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBiddingEvaluationLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter status;

    private StringFilter evaluationStatus;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter biddingEvaluationId;

    public MBiddingEvaluationLineCriteria() {
    }

    public MBiddingEvaluationLineCriteria(MBiddingEvaluationLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.evaluationStatus = other.evaluationStatus == null ? null : other.evaluationStatus.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.biddingEvaluationId = other.biddingEvaluationId == null ? null : other.biddingEvaluationId.copy();
    }

    @Override
    public MBiddingEvaluationLineCriteria copy() {
        return new MBiddingEvaluationLineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
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

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getBiddingEvaluationId() {
        return biddingEvaluationId;
    }

    public void setBiddingEvaluationId(LongFilter biddingEvaluationId) {
        this.biddingEvaluationId = biddingEvaluationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MBiddingEvaluationLineCriteria that = (MBiddingEvaluationLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(evaluationStatus, that.evaluationStatus) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(biddingEvaluationId, that.biddingEvaluationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        status,
        evaluationStatus,
        uid,
        active,
        adOrganizationId,
        biddingEvaluationId
        );
    }

    @Override
    public String toString() {
        return "MBiddingEvaluationLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (evaluationStatus != null ? "evaluationStatus=" + evaluationStatus + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (biddingEvaluationId != null ? "biddingEvaluationId=" + biddingEvaluationId + ", " : "") +
            "}";
    }

}

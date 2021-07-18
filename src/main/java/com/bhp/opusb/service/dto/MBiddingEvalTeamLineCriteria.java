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
 * Criteria class for the {@link com.bhp.opusb.domain.MBiddingEvalTeamLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MBiddingEvalTeamLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-bidding-eval-team-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MBiddingEvalTeamLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter position;

    private LongFilter adOrganizationId;

    private LongFilter evaluationTeamId;

    private LongFilter adUserId;

    public MBiddingEvalTeamLineCriteria() {
    }

    public MBiddingEvalTeamLineCriteria(MBiddingEvalTeamLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.evaluationTeamId = other.evaluationTeamId == null ? null : other.evaluationTeamId.copy();
        this.adUserId = other.adUserId == null ? null : other.adUserId.copy();
    }

    @Override
    public MBiddingEvalTeamLineCriteria copy() {
        return new MBiddingEvalTeamLineCriteria(this);
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

    public StringFilter getPosition() {
        return position;
    }

    public void setPosition(StringFilter position) {
        this.position = position;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getEvaluationTeamId() {
        return evaluationTeamId;
    }

    public void setEvaluationTeamId(LongFilter evaluationTeamId) {
        this.evaluationTeamId = evaluationTeamId;
    }

    public LongFilter getAdUserId() {
        return adUserId;
    }

    public void setAdUserId(LongFilter adUserId) {
        this.adUserId = adUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MBiddingEvalTeamLineCriteria that = (MBiddingEvalTeamLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(position, that.position) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(evaluationTeamId, that.evaluationTeamId) &&
            Objects.equals(adUserId, that.adUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        position,
        adOrganizationId,
        evaluationTeamId,
        adUserId
        );
    }

    @Override
    public String toString() {
        return "MBiddingEvalTeamLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (position != null ? "position=" + position + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (evaluationTeamId != null ? "evaluationTeamId=" + evaluationTeamId + ", " : "") +
                (adUserId != null ? "adUserId=" + adUserId + ", " : "") +
            "}";
    }

}

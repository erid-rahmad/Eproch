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
 * Criteria class for the {@link com.bhp.opusb.domain.MTechProposalEvaluation} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MTechProposalEvaluationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-tech-proposal-evaluations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MTechProposalEvaluationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter notes;

    private StringFilter evaluation;

    private IntegerFilter averageScore;

    private StringFilter passFail;

    private StringFilter requirement;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter biddingId;

    private LongFilter evaluationMethodCriteriaId;

    private LongFilter evalMethodSubCriteriaId;

    private LongFilter biddingSubCriteriaLineId;

    public MTechProposalEvaluationCriteria() {
    }

    public MTechProposalEvaluationCriteria(MTechProposalEvaluationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.evaluation = other.evaluation == null ? null : other.evaluation.copy();
        this.averageScore = other.averageScore == null ? null : other.averageScore.copy();
        this.passFail = other.passFail == null ? null : other.passFail.copy();
        this.requirement = other.requirement == null ? null : other.requirement.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.biddingId = other.biddingId == null ? null : other.biddingId.copy();
        this.evaluationMethodCriteriaId = other.evaluationMethodCriteriaId == null ? null : other.evaluationMethodCriteriaId.copy();
        this.evalMethodSubCriteriaId = other.evalMethodSubCriteriaId == null ? null : other.evalMethodSubCriteriaId.copy();
        this.biddingSubCriteriaLineId = other.biddingSubCriteriaLineId == null ? null : other.biddingSubCriteriaLineId.copy();
    }

    @Override
    public MTechProposalEvaluationCriteria copy() {
        return new MTechProposalEvaluationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public StringFilter getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(StringFilter evaluation) {
        this.evaluation = evaluation;
    }

    public IntegerFilter getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(IntegerFilter averageScore) {
        this.averageScore = averageScore;
    }

    public StringFilter getPassFail() {
        return passFail;
    }

    public void setPassFail(StringFilter passFail) {
        this.passFail = passFail;
    }

    public StringFilter getRequirement() {
        return requirement;
    }

    public void setRequirement(StringFilter requirement) {
        this.requirement = requirement;
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

    public LongFilter getEvaluationMethodCriteriaId() {
        return evaluationMethodCriteriaId;
    }

    public void setEvaluationMethodCriteriaId(LongFilter evaluationMethodCriteriaId) {
        this.evaluationMethodCriteriaId = evaluationMethodCriteriaId;
    }

    public LongFilter getEvalMethodSubCriteriaId() {
        return evalMethodSubCriteriaId;
    }

    public void setEvalMethodSubCriteriaId(LongFilter evalMethodSubCriteriaId) {
        this.evalMethodSubCriteriaId = evalMethodSubCriteriaId;
    }

    public LongFilter getBiddingSubCriteriaLineId() {
        return biddingSubCriteriaLineId;
    }

    public void setBiddingSubCriteriaLineId(LongFilter biddingSubCriteriaLineId) {
        this.biddingSubCriteriaLineId = biddingSubCriteriaLineId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MTechProposalEvaluationCriteria that = (MTechProposalEvaluationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(evaluation, that.evaluation) &&
            Objects.equals(averageScore, that.averageScore) &&
            Objects.equals(passFail, that.passFail) &&
            Objects.equals(requirement, that.requirement) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(biddingId, that.biddingId) &&
            Objects.equals(evaluationMethodCriteriaId, that.evaluationMethodCriteriaId) &&
            Objects.equals(evalMethodSubCriteriaId, that.evalMethodSubCriteriaId) &&
            Objects.equals(biddingSubCriteriaLineId, that.biddingSubCriteriaLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        notes,
        evaluation,
        averageScore,
        passFail,
        requirement,
        uid,
        active,
        adOrganizationId,
        biddingId,
        evaluationMethodCriteriaId,
        evalMethodSubCriteriaId,
        biddingSubCriteriaLineId
        );
    }

    @Override
    public String toString() {
        return "MTechProposalEvaluationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (notes != null ? "notes=" + notes + ", " : "") +
                (evaluation != null ? "evaluation=" + evaluation + ", " : "") +
                (averageScore != null ? "averageScore=" + averageScore + ", " : "") +
                (passFail != null ? "passFail=" + passFail + ", " : "") +
                (requirement != null ? "requirement=" + requirement + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (biddingId != null ? "biddingId=" + biddingId + ", " : "") +
                (evaluationMethodCriteriaId != null ? "evaluationMethodCriteriaId=" + evaluationMethodCriteriaId + ", " : "") +
                (evalMethodSubCriteriaId != null ? "evalMethodSubCriteriaId=" + evalMethodSubCriteriaId + ", " : "") +
                (biddingSubCriteriaLineId != null ? "biddingSubCriteriaLineId=" + biddingSubCriteriaLineId + ", " : "") +
            "}";
    }

}

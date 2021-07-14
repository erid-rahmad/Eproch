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
 * Criteria class for the {@link com.bhp.opusb.domain.MProposalTechnical} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MProposalTechnicalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-proposal-technicals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MProposalTechnicalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documentAction;

    private StringFilter documentStatus;

    private StringFilter notes;

    private StringFilter evaluation;

    private IntegerFilter averageScore;

    private StringFilter passFail;

    private StringFilter answer;

    private BooleanFilter documentEvaluation;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter biddingSubmissionId;

    private LongFilter biddingSubCriteriaLineId;

    public MProposalTechnicalCriteria() {
    }

    public MProposalTechnicalCriteria(MProposalTechnicalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentAction = other.documentAction == null ? null : other.documentAction.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.evaluation = other.evaluation == null ? null : other.evaluation.copy();
        this.averageScore = other.averageScore == null ? null : other.averageScore.copy();
        this.passFail = other.passFail == null ? null : other.passFail.copy();
        this.answer = other.answer == null ? null : other.answer.copy();
        this.documentEvaluation = other.documentEvaluation == null ? null : other.documentEvaluation.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.biddingSubmissionId = other.biddingSubmissionId == null ? null : other.biddingSubmissionId.copy();
        this.biddingSubCriteriaLineId = other.biddingSubCriteriaLineId == null ? null : other.biddingSubCriteriaLineId.copy();
    }

    @Override
    public MProposalTechnicalCriteria copy() {
        return new MProposalTechnicalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public StringFilter getAnswer() {
        return answer;
    }

    public void setAnswer(StringFilter answer) {
        this.answer = answer;
    }

    public BooleanFilter getDocumentEvaluation() {
        return documentEvaluation;
    }

    public void setDocumentEvaluation(BooleanFilter documentEvaluation) {
        this.documentEvaluation = documentEvaluation;
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
        final MProposalTechnicalCriteria that = (MProposalTechnicalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(documentAction, that.documentAction) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(evaluation, that.evaluation) &&
            Objects.equals(averageScore, that.averageScore) &&
            Objects.equals(passFail, that.passFail) &&
            Objects.equals(answer, that.answer) &&
            Objects.equals(documentEvaluation, that.documentEvaluation) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(biddingSubmissionId, that.biddingSubmissionId) &&
            Objects.equals(biddingSubCriteriaLineId, that.biddingSubCriteriaLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        documentAction,
        documentStatus,
        notes,
        evaluation,
        averageScore,
        passFail,
        answer,
        documentEvaluation,
        uid,
        active,
        adOrganizationId,
        biddingSubmissionId,
        biddingSubCriteriaLineId
        );
    }

    @Override
    public String toString() {
        return "MProposalTechnicalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentAction != null ? "documentAction=" + documentAction + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (notes != null ? "notes=" + notes + ", " : "") +
                (evaluation != null ? "evaluation=" + evaluation + ", " : "") +
                (averageScore != null ? "averageScore=" + averageScore + ", " : "") +
                (passFail != null ? "passFail=" + passFail + ", " : "") +
                (answer != null ? "answer=" + answer + ", " : "") +
                (documentEvaluation != null ? "documentEvaluation=" + documentEvaluation + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (biddingSubmissionId != null ? "biddingSubmissionId=" + biddingSubmissionId + ", " : "") +
                (biddingSubCriteriaLineId != null ? "biddingSubCriteriaLineId=" + biddingSubCriteriaLineId + ", " : "") +
            "}";
    }

}

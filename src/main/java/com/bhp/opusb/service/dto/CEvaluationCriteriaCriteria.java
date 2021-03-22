package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CEvaluationCriteria} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CEvaluationCriteriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-evaluation-criteria?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CEvaluationCriteriaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter scoringPercentage;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter evaluationMethodLineId;

    private LongFilter biddingCriteriaId;

    private LongFilter biddingSubCriteriaId;

    private LongFilter picId;

    public CEvaluationCriteriaCriteria() {
    }

    public CEvaluationCriteriaCriteria(CEvaluationCriteriaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.scoringPercentage = other.scoringPercentage == null ? null : other.scoringPercentage.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.evaluationMethodLineId = other.evaluationMethodLineId == null ? null : other.evaluationMethodLineId.copy();
        this.biddingCriteriaId = other.biddingCriteriaId == null ? null : other.biddingCriteriaId.copy();
        this.biddingSubCriteriaId = other.biddingSubCriteriaId == null ? null : other.biddingSubCriteriaId.copy();
        this.picId = other.picId == null ? null : other.picId.copy();
    }

    @Override
    public CEvaluationCriteriaCriteria copy() {
        return new CEvaluationCriteriaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getScoringPercentage() {
        return scoringPercentage;
    }

    public void setScoringPercentage(BigDecimalFilter scoringPercentage) {
        this.scoringPercentage = scoringPercentage;
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

    public LongFilter getEvaluationMethodLineId() {
        return evaluationMethodLineId;
    }

    public void setEvaluationMethodLineId(LongFilter evaluationMethodLineId) {
        this.evaluationMethodLineId = evaluationMethodLineId;
    }

    public LongFilter getBiddingCriteriaId() {
        return biddingCriteriaId;
    }

    public void setBiddingCriteriaId(LongFilter biddingCriteriaId) {
        this.biddingCriteriaId = biddingCriteriaId;
    }

    public LongFilter getBiddingSubCriteriaId() {
        return biddingSubCriteriaId;
    }

    public void setBiddingSubCriteriaId(LongFilter biddingSubCriteriaId) {
        this.biddingSubCriteriaId = biddingSubCriteriaId;
    }

    public LongFilter getPicId() {
        return picId;
    }

    public void setPicId(LongFilter picId) {
        this.picId = picId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CEvaluationCriteriaCriteria that = (CEvaluationCriteriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(scoringPercentage, that.scoringPercentage) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(evaluationMethodLineId, that.evaluationMethodLineId) &&
            Objects.equals(biddingCriteriaId, that.biddingCriteriaId) &&
            Objects.equals(biddingSubCriteriaId, that.biddingSubCriteriaId) &&
            Objects.equals(picId, that.picId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        scoringPercentage,
        uid,
        active,
        adOrganizationId,
        evaluationMethodLineId,
        biddingCriteriaId,
        biddingSubCriteriaId,
        picId
        );
    }

    @Override
    public String toString() {
        return "CEvaluationCriteriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (scoringPercentage != null ? "scoringPercentage=" + scoringPercentage + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (evaluationMethodLineId != null ? "evaluationMethodLineId=" + evaluationMethodLineId + ", " : "") +
                (biddingCriteriaId != null ? "biddingCriteriaId=" + biddingCriteriaId + ", " : "") +
                (biddingSubCriteriaId != null ? "biddingSubCriteriaId=" + biddingSubCriteriaId + ", " : "") +
                (picId != null ? "picId=" + picId + ", " : "") +
            "}";
    }

}

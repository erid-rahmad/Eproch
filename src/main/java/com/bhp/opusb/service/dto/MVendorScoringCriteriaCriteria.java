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
 * Criteria class for the {@link com.bhp.opusb.domain.MVendorScoringCriteria} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MVendorScoringCriteriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-vendor-scoring-criteria?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MVendorScoringCriteriaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter requirement;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter evaluationMethodCriteriaId;

    private LongFilter evalMethodSubCriteriaId;

    private LongFilter vendorScoringLineId;

    private LongFilter biddingSubCriteriaLineId;

    public MVendorScoringCriteriaCriteria() {
    }

    public MVendorScoringCriteriaCriteria(MVendorScoringCriteriaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.requirement = other.requirement == null ? null : other.requirement.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.evaluationMethodCriteriaId = other.evaluationMethodCriteriaId == null ? null : other.evaluationMethodCriteriaId.copy();
        this.evalMethodSubCriteriaId = other.evalMethodSubCriteriaId == null ? null : other.evalMethodSubCriteriaId.copy();
        this.vendorScoringLineId = other.vendorScoringLineId == null ? null : other.vendorScoringLineId.copy();
        this.biddingSubCriteriaLineId = other.biddingSubCriteriaLineId == null ? null : other.biddingSubCriteriaLineId.copy();
    }

    @Override
    public MVendorScoringCriteriaCriteria copy() {
        return new MVendorScoringCriteriaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LongFilter getVendorScoringLineId() {
        return vendorScoringLineId;
    }

    public void setVendorScoringLineId(LongFilter vendorScoringLineId) {
        this.vendorScoringLineId = vendorScoringLineId;
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
        final MVendorScoringCriteriaCriteria that = (MVendorScoringCriteriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(requirement, that.requirement) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(evaluationMethodCriteriaId, that.evaluationMethodCriteriaId) &&
            Objects.equals(evalMethodSubCriteriaId, that.evalMethodSubCriteriaId) &&
            Objects.equals(vendorScoringLineId, that.vendorScoringLineId) &&
            Objects.equals(biddingSubCriteriaLineId, that.biddingSubCriteriaLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        requirement,
        uid,
        active,
        adOrganizationId,
        evaluationMethodCriteriaId,
        evalMethodSubCriteriaId,
        vendorScoringLineId,
        biddingSubCriteriaLineId
        );
    }

    @Override
    public String toString() {
        return "MVendorScoringCriteriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (requirement != null ? "requirement=" + requirement + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (evaluationMethodCriteriaId != null ? "evaluationMethodCriteriaId=" + evaluationMethodCriteriaId + ", " : "") +
                (evalMethodSubCriteriaId != null ? "evalMethodSubCriteriaId=" + evalMethodSubCriteriaId + ", " : "") +
                (vendorScoringLineId != null ? "vendorScoringLineId=" + vendorScoringLineId + ", " : "") +
                (biddingSubCriteriaLineId != null ? "biddingSubCriteriaLineId=" + biddingSubCriteriaLineId + ", " : "") +
            "}";
    }

}

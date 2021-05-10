package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MVendorScoringLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MVendorScoringLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-vendor-scoring-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MVendorScoringLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter evaluation;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter evaluationMethodLineId;

    private LongFilter vendorScoringId;

    private LongFilter biddingId;

    private StringFilter formType;

    public MVendorScoringLineCriteria() {
    }

    public MVendorScoringLineCriteria(MVendorScoringLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.evaluation = other.evaluation == null ? null : other.evaluation.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.evaluationMethodLineId = other.evaluationMethodLineId == null ? null : other.evaluationMethodLineId.copy();
        this.vendorScoringId = other.vendorScoringId == null ? null : other.vendorScoringId.copy();
        this.biddingId = other.biddingId == null ? null : other.biddingId.copy();
        this.formType = other.formType == null ? null : other.formType.copy();
    }

    @Override
    public MVendorScoringLineCriteria copy() {
        return new MVendorScoringLineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(StringFilter evaluation) {
        this.evaluation = evaluation;
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

    public LongFilter getVendorScoringId() {
        return vendorScoringId;
    }

    public void setVendorScoringId(LongFilter vendorScoringId) {
        this.vendorScoringId = vendorScoringId;
    }


    public LongFilter getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(LongFilter biddingId) {
        this.biddingId = biddingId;
    }

    public StringFilter getFormType() {
        return formType;
    }

    public void setFormType(StringFilter formType) {
        this.formType = formType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MVendorScoringLineCriteria that = (MVendorScoringLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(evaluation, that.evaluation) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(evaluationMethodLineId, that.evaluationMethodLineId) &&
            Objects.equals(vendorScoringId, that.vendorScoringId) &&
            Objects.equals(biddingId, that.biddingId) &&
            Objects.equals(formType, that.formType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        evaluation,
        uid,
        active,
        adOrganizationId,
        evaluationMethodLineId,
        vendorScoringId,
        biddingId,
        formType
        );
    }

    @Override
    public String toString() {
        return "MVendorScoringLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (evaluation != null ? "evaluation=" + evaluation + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (evaluationMethodLineId != null ? "evaluationMethodLineId=" + evaluationMethodLineId + ", " : "") +
                (vendorScoringId != null ? "vendorScoringId=" + vendorScoringId + ", " : "") +
                (biddingId != null ? "biddingId=" + biddingId + ", " : "") +
                (formType != null ? "formType=" + formType + ", " : "") +
            "}";
    }

}

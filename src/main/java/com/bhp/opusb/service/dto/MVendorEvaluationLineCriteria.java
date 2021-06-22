package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MVendorEvaluationLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MVendorEvaluationLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-vendor-evaluation-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MVendorEvaluationLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter score;

    private StringFilter remark;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter vendorEvaluationId;

    private LongFilter evaluationLineId;

    public MVendorEvaluationLineCriteria() {
    }

    public MVendorEvaluationLineCriteria(MVendorEvaluationLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.score = other.score == null ? null : other.score.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.vendorEvaluationId = other.vendorEvaluationId == null ? null : other.vendorEvaluationId.copy();
        this.evaluationLineId = other.evaluationLineId == null ? null : other.evaluationLineId.copy();
    }

    @Override
    public MVendorEvaluationLineCriteria copy() {
        return new MVendorEvaluationLineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getScore() {
        return score;
    }

    public void setScore(BigDecimalFilter score) {
        this.score = score;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
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

    public LongFilter getVendorEvaluationId() {
        return vendorEvaluationId;
    }

    public void setVendorEvaluationId(LongFilter vendorEvaluationId) {
        this.vendorEvaluationId = vendorEvaluationId;
    }

    public LongFilter getEvaluationLineId() {
        return evaluationLineId;
    }

    public void setEvaluationLineId(LongFilter evaluationLineId) {
        this.evaluationLineId = evaluationLineId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MVendorEvaluationLineCriteria that = (MVendorEvaluationLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(score, that.score) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(vendorEvaluationId, that.vendorEvaluationId) &&
            Objects.equals(evaluationLineId, that.evaluationLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        score,
        remark,
        uid,
        active,
        adOrganizationId,
        vendorEvaluationId,
        evaluationLineId
        );
    }

    @Override
    public String toString() {
        return "MVendorEvaluationLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (score != null ? "score=" + score + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (vendorEvaluationId != null ? "vendorEvaluationId=" + vendorEvaluationId + ", " : "") +
                (evaluationLineId != null ? "evaluationLineId=" + evaluationLineId + ", " : "") +
            "}";
    }

}

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
 * Criteria class for the {@link com.bhp.opusb.domain.MPreqRegistEvaluation} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MPreqRegistEvaluationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-preq-regist-evaluations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MPreqRegistEvaluationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter evaluation;

    private LongFilter adOrganizationId;

    private LongFilter vendorId;

    private LongFilter prequalificationId;

    public MPreqRegistEvaluationCriteria() {
    }

    public MPreqRegistEvaluationCriteria(MPreqRegistEvaluationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.evaluation = other.evaluation == null ? null : other.evaluation.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.prequalificationId = other.prequalificationId == null ? null : other.prequalificationId.copy();
    }

    @Override
    public MPreqRegistEvaluationCriteria copy() {
        return new MPreqRegistEvaluationCriteria(this);
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

    public StringFilter getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(StringFilter evaluation) {
        this.evaluation = evaluation;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }

    public LongFilter getPrequalificationId() {
        return prequalificationId;
    }

    public void setPrequalificationId(LongFilter prequalificationId) {
        this.prequalificationId = prequalificationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MPreqRegistEvaluationCriteria that = (MPreqRegistEvaluationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(evaluation, that.evaluation) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(prequalificationId, that.prequalificationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        evaluation,
        adOrganizationId,
        vendorId,
        prequalificationId
        );
    }

    @Override
    public String toString() {
        return "MPreqRegistEvaluationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (evaluation != null ? "evaluation=" + evaluation + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (prequalificationId != null ? "prequalificationId=" + prequalificationId + ", " : "") +
            "}";
    }

}

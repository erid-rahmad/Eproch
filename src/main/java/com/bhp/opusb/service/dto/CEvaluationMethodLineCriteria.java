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
 * Criteria class for the {@link com.bhp.opusb.domain.CEvaluationMethodLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CEvaluationMethodLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-evaluation-method-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CEvaluationMethodLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter evaluation;

    private BigDecimalFilter weight;

    private BigDecimalFilter passingGrade;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter evaluationMethodId;

    public CEvaluationMethodLineCriteria() {
    }

    public CEvaluationMethodLineCriteria(CEvaluationMethodLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.evaluation = other.evaluation == null ? null : other.evaluation.copy();
        this.weight = other.weight == null ? null : other.weight.copy();
        this.passingGrade = other.passingGrade == null ? null : other.passingGrade.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.evaluationMethodId = other.evaluationMethodId == null ? null : other.evaluationMethodId.copy();
    }

    @Override
    public CEvaluationMethodLineCriteria copy() {
        return new CEvaluationMethodLineCriteria(this);
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

    public BigDecimalFilter getWeight() {
        return weight;
    }

    public void setWeight(BigDecimalFilter weight) {
        this.weight = weight;
    }

    public BigDecimalFilter getPassingGrade() {
        return passingGrade;
    }

    public void setPassingGrade(BigDecimalFilter passingGrade) {
        this.passingGrade = passingGrade;
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

    public LongFilter getEvaluationMethodId() {
        return evaluationMethodId;
    }

    public void setEvaluationMethodId(LongFilter evaluationMethodId) {
        this.evaluationMethodId = evaluationMethodId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CEvaluationMethodLineCriteria that = (CEvaluationMethodLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(evaluation, that.evaluation) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(passingGrade, that.passingGrade) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(evaluationMethodId, that.evaluationMethodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        evaluation,
        weight,
        passingGrade,
        uid,
        active,
        adOrganizationId,
        evaluationMethodId
        );
    }

    @Override
    public String toString() {
        return "CEvaluationMethodLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (evaluation != null ? "evaluation=" + evaluation + ", " : "") +
                (weight != null ? "weight=" + weight + ", " : "") +
                (passingGrade != null ? "passingGrade=" + passingGrade + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (evaluationMethodId != null ? "evaluationMethodId=" + evaluationMethodId + ", " : "") +
            "}";
    }

}

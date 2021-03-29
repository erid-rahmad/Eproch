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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CVendorEvaluationLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CVendorEvaluationLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-vendor-evaluation-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CVendorEvaluationLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter weight;

    private BigDecimalFilter userWeight;

    private BigDecimalFilter procurementWeight;

    private StringFilter question;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter cQuestionCategoryId;

    public CVendorEvaluationLineCriteria() {
    }

    public CVendorEvaluationLineCriteria(CVendorEvaluationLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.weight = other.weight == null ? null : other.weight.copy();
        this.userWeight = other.userWeight == null ? null : other.userWeight.copy();
        this.procurementWeight = other.procurementWeight == null ? null : other.procurementWeight.copy();
        this.question = other.question == null ? null : other.question.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.cQuestionCategoryId = other.cQuestionCategoryId == null ? null : other.cQuestionCategoryId.copy();
    }

    @Override
    public CVendorEvaluationLineCriteria copy() {
        return new CVendorEvaluationLineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getWeight() {
        return weight;
    }

    public void setWeight(BigDecimalFilter weight) {
        this.weight = weight;
    }

    public BigDecimalFilter getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(BigDecimalFilter userWeight) {
        this.userWeight = userWeight;
    }

    public BigDecimalFilter getProcurementWeight() {
        return procurementWeight;
    }

    public void setProcurementWeight(BigDecimalFilter procurementWeight) {
        this.procurementWeight = procurementWeight;
    }

    public StringFilter getQuestion() {
        return question;
    }

    public void setQuestion(StringFilter question) {
        this.question = question;
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

    public LongFilter getCQuestionCategoryId() {
        return cQuestionCategoryId;
    }

    public void setCQuestionCategoryId(LongFilter cQuestionCategoryId) {
        this.cQuestionCategoryId = cQuestionCategoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CVendorEvaluationLineCriteria that = (CVendorEvaluationLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(userWeight, that.userWeight) &&
            Objects.equals(procurementWeight, that.procurementWeight) &&
            Objects.equals(question, that.question) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(cQuestionCategoryId, that.cQuestionCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        weight,
        userWeight,
        procurementWeight,
        question,
        uid,
        active,
        adOrganizationId,
        cQuestionCategoryId
        );
    }

    @Override
    public String toString() {
        return "CVendorEvaluationLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (weight != null ? "weight=" + weight + ", " : "") +
                (userWeight != null ? "userWeight=" + userWeight + ", " : "") +
                (procurementWeight != null ? "procurementWeight=" + procurementWeight + ", " : "") +
                (question != null ? "question=" + question + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (cQuestionCategoryId != null ? "cQuestionCategoryId=" + cQuestionCategoryId + ", " : "") +
            "}";
    }

}

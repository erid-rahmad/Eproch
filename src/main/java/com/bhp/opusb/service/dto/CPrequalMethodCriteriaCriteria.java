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
 * Criteria class for the {@link com.bhp.opusb.domain.CPrequalMethodCriteria} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CPrequalMethodCriteriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-prequal-method-criteria?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CPrequalMethodCriteriaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter weight;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter biddingCriteriaId;

    private LongFilter adOrganizationId;

    private LongFilter prequalMethodLineId;

    public CPrequalMethodCriteriaCriteria() {
    }

    public CPrequalMethodCriteriaCriteria(CPrequalMethodCriteriaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.weight = other.weight == null ? null : other.weight.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.biddingCriteriaId = other.biddingCriteriaId == null ? null : other.biddingCriteriaId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.prequalMethodLineId = other.prequalMethodLineId == null ? null : other.prequalMethodLineId.copy();
    }

    @Override
    public CPrequalMethodCriteriaCriteria copy() {
        return new CPrequalMethodCriteriaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getWeight() {
        return weight;
    }

    public void setWeight(IntegerFilter weight) {
        this.weight = weight;
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

    public LongFilter getBiddingCriteriaId() {
        return biddingCriteriaId;
    }

    public void setBiddingCriteriaId(LongFilter biddingCriteriaId) {
        this.biddingCriteriaId = biddingCriteriaId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getPrequalMethodLineId() {
        return prequalMethodLineId;
    }

    public void setPrequalMethodLineId(LongFilter prequalMethodLineId) {
        this.prequalMethodLineId = prequalMethodLineId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CPrequalMethodCriteriaCriteria that = (CPrequalMethodCriteriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(biddingCriteriaId, that.biddingCriteriaId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(prequalMethodLineId, that.prequalMethodLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        weight,
        uid,
        active,
        biddingCriteriaId,
        adOrganizationId,
        prequalMethodLineId
        );
    }

    @Override
    public String toString() {
        return "CPrequalMethodCriteriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (weight != null ? "weight=" + weight + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (biddingCriteriaId != null ? "biddingCriteriaId=" + biddingCriteriaId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (prequalMethodLineId != null ? "prequalMethodLineId=" + prequalMethodLineId + ", " : "") +
            "}";
    }

}

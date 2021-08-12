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
 * Criteria class for the {@link com.bhp.opusb.domain.MPrequalificationCriteria} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MPrequalificationCriteriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-prequalification-criteria?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MPrequalificationCriteriaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter requirement;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter prequalificationId;

    private LongFilter prequalMethodCriteriaId;

    private LongFilter prequalMethodSubCriteriaId;

    private LongFilter biddingSubCriteriaLineId;

    public MPrequalificationCriteriaCriteria() {
    }

    public MPrequalificationCriteriaCriteria(MPrequalificationCriteriaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.requirement = other.requirement == null ? null : other.requirement.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.prequalificationId = other.prequalificationId == null ? null : other.prequalificationId.copy();
        this.prequalMethodCriteriaId = other.prequalMethodCriteriaId == null ? null : other.prequalMethodCriteriaId.copy();
        this.prequalMethodSubCriteriaId = other.prequalMethodSubCriteriaId == null ? null : other.prequalMethodSubCriteriaId.copy();
        this.biddingSubCriteriaLineId = other.biddingSubCriteriaLineId == null ? null : other.biddingSubCriteriaLineId.copy();
    }

    @Override
    public MPrequalificationCriteriaCriteria copy() {
        return new MPrequalificationCriteriaCriteria(this);
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

    public LongFilter getPrequalificationId() {
        return prequalificationId;
    }

    public void setPrequalificationId(LongFilter prequalificationId) {
        this.prequalificationId = prequalificationId;
    }

    public LongFilter getPrequalMethodCriteriaId() {
        return prequalMethodCriteriaId;
    }

    public void setPrequalMethodCriteriaId(LongFilter prequalMethodCriteriaId) {
        this.prequalMethodCriteriaId = prequalMethodCriteriaId;
    }

    public LongFilter getPrequalMethodSubCriteriaId() {
        return prequalMethodSubCriteriaId;
    }

    public void setPrequalMethodSubCriteriaId(LongFilter prequalMethodSubCriteriaId) {
        this.prequalMethodSubCriteriaId = prequalMethodSubCriteriaId;
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
        final MPrequalificationCriteriaCriteria that = (MPrequalificationCriteriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(requirement, that.requirement) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(prequalificationId, that.prequalificationId) &&
            Objects.equals(prequalMethodCriteriaId, that.prequalMethodCriteriaId) &&
            Objects.equals(prequalMethodSubCriteriaId, that.prequalMethodSubCriteriaId) &&
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
        prequalificationId,
        prequalMethodCriteriaId,
        prequalMethodSubCriteriaId,
        biddingSubCriteriaLineId
        );
    }

    @Override
    public String toString() {
        return "MPrequalificationCriteriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (requirement != null ? "requirement=" + requirement + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (prequalificationId != null ? "prequalificationId=" + prequalificationId + ", " : "") +
                (prequalMethodCriteriaId != null ? "prequalMethodCriteriaId=" + prequalMethodCriteriaId + ", " : "") +
                (prequalMethodSubCriteriaId != null ? "prequalMethodSubCriteriaId=" + prequalMethodSubCriteriaId + ", " : "") +
                (biddingSubCriteriaLineId != null ? "biddingSubCriteriaLineId=" + biddingSubCriteriaLineId + ", " : "") +
            "}";
    }

}

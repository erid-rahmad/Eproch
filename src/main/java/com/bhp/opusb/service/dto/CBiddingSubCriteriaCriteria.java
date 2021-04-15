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
 * Criteria class for the {@link com.bhp.opusb.domain.CBiddingSubCriteria} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CBiddingSubCriteriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-bidding-sub-criteria?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CBiddingSubCriteriaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter evaluationType;

    private BooleanFilter multipleOptions;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter biddingCriteriaId;

    public CBiddingSubCriteriaCriteria() {
    }

    public CBiddingSubCriteriaCriteria(CBiddingSubCriteriaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.evaluationType = other.evaluationType == null ? null : other.evaluationType.copy();
        this.multipleOptions = other.multipleOptions == null ? null : other.multipleOptions.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.biddingCriteriaId = other.biddingCriteriaId == null ? null : other.biddingCriteriaId.copy();
    }

    @Override
    public CBiddingSubCriteriaCriteria copy() {
        return new CBiddingSubCriteriaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(StringFilter evaluationType) {
        this.evaluationType = evaluationType;
    }

    public BooleanFilter getMultipleOptions() {
        return multipleOptions;
    }

    public void setMultipleOptions(BooleanFilter multipleOptions) {
        this.multipleOptions = multipleOptions;
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

    public LongFilter getBiddingCriteriaId() {
        return biddingCriteriaId;
    }

    public void setBiddingCriteriaId(LongFilter biddingCriteriaId) {
        this.biddingCriteriaId = biddingCriteriaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CBiddingSubCriteriaCriteria that = (CBiddingSubCriteriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(evaluationType, that.evaluationType) &&
            Objects.equals(multipleOptions, that.multipleOptions) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(biddingCriteriaId, that.biddingCriteriaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        evaluationType,
        multipleOptions,
        uid,
        active,
        adOrganizationId,
        biddingCriteriaId
        );
    }

    @Override
    public String toString() {
        return "CBiddingSubCriteriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (evaluationType != null ? "evaluationType=" + evaluationType + ", " : "") +
                (multipleOptions != null ? "multipleOptions=" + multipleOptions + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (biddingCriteriaId != null ? "biddingCriteriaId=" + biddingCriteriaId + ", " : "") +
            "}";
    }

}

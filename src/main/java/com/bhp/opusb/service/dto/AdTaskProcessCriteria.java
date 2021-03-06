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
 * Criteria class for the {@link com.bhp.opusb.domain.AdTaskProcess} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.AdTaskProcessResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-task-processes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdTaskProcessCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private IntegerFilter runSequence;

    private BooleanFilter parallel;

    private LongFilter adOrganizationId;

    private LongFilter adTaskApplicationId;

    private LongFilter adTaskId;

    public AdTaskProcessCriteria() {
    }

    public AdTaskProcessCriteria(AdTaskProcessCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.runSequence = other.runSequence == null ? null : other.runSequence.copy();
        this.parallel = other.parallel == null ? null : other.parallel.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adTaskApplicationId = other.adTaskApplicationId == null ? null : other.adTaskApplicationId.copy();
        this.adTaskId = other.adTaskId == null ? null : other.adTaskId.copy();
    }

    @Override
    public AdTaskProcessCriteria copy() {
        return new AdTaskProcessCriteria(this);
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

    public IntegerFilter getRunSequence() {
        return runSequence;
    }

    public void setRunSequence(IntegerFilter runSequence) {
        this.runSequence = runSequence;
    }

    public BooleanFilter getParallel() {
        return parallel;
    }

    public void setParallel(BooleanFilter parallel) {
        this.parallel = parallel;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getAdTaskApplicationId() {
        return adTaskApplicationId;
    }

    public void setAdTaskApplicationId(LongFilter adTaskApplicationId) {
        this.adTaskApplicationId = adTaskApplicationId;
    }

    public LongFilter getAdTaskId() {
        return adTaskId;
    }

    public void setAdTaskId(LongFilter adTaskId) {
        this.adTaskId = adTaskId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdTaskProcessCriteria that = (AdTaskProcessCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(runSequence, that.runSequence) &&
            Objects.equals(parallel, that.parallel) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adTaskApplicationId, that.adTaskApplicationId) &&
            Objects.equals(adTaskId, that.adTaskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        runSequence,
        parallel,
        adOrganizationId,
        adTaskApplicationId,
        adTaskId
        );
    }

    @Override
    public String toString() {
        return "AdTaskProcessCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (runSequence != null ? "runSequence=" + runSequence + ", " : "") +
                (parallel != null ? "parallel=" + parallel + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adTaskApplicationId != null ? "adTaskApplicationId=" + adTaskApplicationId + ", " : "") +
                (adTaskId != null ? "adTaskId=" + adTaskId + ", " : "") +
            "}";
    }

}

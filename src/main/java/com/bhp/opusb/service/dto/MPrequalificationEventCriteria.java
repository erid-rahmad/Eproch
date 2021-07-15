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
 * Criteria class for the {@link com.bhp.opusb.domain.MPrequalificationEvent} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MPrequalificationEventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-prequalification-events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MPrequalificationEventCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter prequalificationId;

    private LongFilter adOrganizationId;

    private LongFilter eventId;

    private LongFilter methodId;

    public MPrequalificationEventCriteria() {
    }

    public MPrequalificationEventCriteria(MPrequalificationEventCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.prequalificationId = other.prequalificationId == null ? null : other.prequalificationId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.methodId = other.methodId == null ? null : other.methodId.copy();
    }

    @Override
    public MPrequalificationEventCriteria copy() {
        return new MPrequalificationEventCriteria(this);
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

    public LongFilter getPrequalificationId() {
        return prequalificationId;
    }

    public void setPrequalificationId(LongFilter prequalificationId) {
        this.prequalificationId = prequalificationId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getEventId() {
        return eventId;
    }

    public void setEventId(LongFilter eventId) {
        this.eventId = eventId;
    }

    public LongFilter getMethodId() {
        return methodId;
    }

    public void setMethodId(LongFilter methodId) {
        this.methodId = methodId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MPrequalificationEventCriteria that = (MPrequalificationEventCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(prequalificationId, that.prequalificationId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(methodId, that.methodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        prequalificationId,
        adOrganizationId,
        eventId,
        methodId
        );
    }

    @Override
    public String toString() {
        return "MPrequalificationEventCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (prequalificationId != null ? "prequalificationId=" + prequalificationId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (eventId != null ? "eventId=" + eventId + ", " : "") +
                (methodId != null ? "methodId=" + methodId + ", " : "") +
            "}";
    }

}

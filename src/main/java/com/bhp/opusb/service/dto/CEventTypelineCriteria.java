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
 * Criteria class for the {@link com.bhp.opusb.domain.CEventTypeline} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CEventTypelineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-event-typelines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CEventTypelineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter event;

    private StringFilter description;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter eventTypeId;

    public CEventTypelineCriteria() {
    }

    public CEventTypelineCriteria(CEventTypelineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.event = other.event == null ? null : other.event.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.eventTypeId = other.eventTypeId == null ? null : other.eventTypeId.copy();
    }

    @Override
    public CEventTypelineCriteria copy() {
        return new CEventTypelineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEvent() {
        return event;
    }

    public void setEvent(StringFilter event) {
        this.event = event;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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

    public LongFilter getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(LongFilter eventTypeId) {
        this.eventTypeId = eventTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CEventTypelineCriteria that = (CEventTypelineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(event, that.event) &&
            Objects.equals(description, that.description) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(eventTypeId, that.eventTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        event,
        description,
        uid,
        active,
        adOrganizationId,
        eventTypeId
        );
    }

    @Override
    public String toString() {
        return "CEventTypelineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (event != null ? "event=" + event + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (eventTypeId != null ? "eventTypeId=" + eventTypeId + ", " : "") +
            "}";
    }

}

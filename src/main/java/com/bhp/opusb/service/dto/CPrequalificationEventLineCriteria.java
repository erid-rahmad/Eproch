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
 * Criteria class for the {@link com.bhp.opusb.domain.CPrequalificationEventLine} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CPrequalificationEventLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-prequalification-event-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CPrequalificationEventLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private IntegerFilter sequence;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter prequalificationEventId;

    private LongFilter prequalificationStepId;

    private LongFilter adOrganizationId;

    public CPrequalificationEventLineCriteria() {
    }

    public CPrequalificationEventLineCriteria(CPrequalificationEventLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.sequence = other.sequence == null ? null : other.sequence.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.prequalificationEventId = other.prequalificationEventId == null ? null : other.prequalificationEventId.copy();
        this.prequalificationStepId = other.prequalificationStepId == null ? null : other.prequalificationStepId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public CPrequalificationEventLineCriteria copy() {
        return new CPrequalificationEventLineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public IntegerFilter getSequence() {
        return sequence;
    }

    public void setSequence(IntegerFilter sequence) {
        this.sequence = sequence;
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

    public LongFilter getPrequalificationEventId() {
        return prequalificationEventId;
    }

    public void setPrequalificationEventId(LongFilter prequalificationEventId) {
        this.prequalificationEventId = prequalificationEventId;
    }

    public LongFilter getPrequalificationStepId() {
        return prequalificationStepId;
    }

    public void setPrequalificationStepId(LongFilter prequalificationStepId) {
        this.prequalificationStepId = prequalificationStepId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CPrequalificationEventLineCriteria that = (CPrequalificationEventLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(sequence, that.sequence) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(prequalificationEventId, that.prequalificationEventId) &&
            Objects.equals(prequalificationStepId, that.prequalificationStepId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        sequence,
        uid,
        active,
        prequalificationEventId,
        prequalificationStepId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "CPrequalificationEventLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (sequence != null ? "sequence=" + sequence + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (prequalificationEventId != null ? "prequalificationEventId=" + prequalificationEventId + ", " : "") +
                (prequalificationStepId != null ? "prequalificationStepId=" + prequalificationStepId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

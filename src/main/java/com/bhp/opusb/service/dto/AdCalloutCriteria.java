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
 * Criteria class for the {@link com.bhp.opusb.domain.AdCallout} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.AdCalloutResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-callouts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdCalloutCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter name;

    private StringFilter description;

    private StringFilter type;

    private LongFilter adCalloutTargetId;

    private LongFilter adOrganizationId;

    private LongFilter triggerId;

    private LongFilter fieldId;

    public AdCalloutCriteria() {
    }

    public AdCalloutCriteria(AdCalloutCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.adCalloutTargetId = other.adCalloutTargetId == null ? null : other.adCalloutTargetId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.triggerId = other.triggerId == null ? null : other.triggerId.copy();
        this.fieldId = other.fieldId == null ? null : other.fieldId.copy();
    }

    @Override
    public AdCalloutCriteria copy() {
        return new AdCalloutCriteria(this);
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

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public LongFilter getAdCalloutTargetId() {
        return adCalloutTargetId;
    }

    public void setAdCalloutTargetId(LongFilter adCalloutTargetId) {
        this.adCalloutTargetId = adCalloutTargetId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(LongFilter triggerId) {
        this.triggerId = triggerId;
    }

    public LongFilter getFieldId() {
        return fieldId;
    }

    public void setFieldId(LongFilter fieldId) {
        this.fieldId = fieldId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdCalloutCriteria that = (AdCalloutCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type) &&
            Objects.equals(adCalloutTargetId, that.adCalloutTargetId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(triggerId, that.triggerId) &&
            Objects.equals(fieldId, that.fieldId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        name,
        description,
        type,
        adCalloutTargetId,
        adOrganizationId,
        triggerId,
        fieldId
        );
    }

    @Override
    public String toString() {
        return "AdCalloutCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (adCalloutTargetId != null ? "adCalloutTargetId=" + adCalloutTargetId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (triggerId != null ? "triggerId=" + triggerId + ", " : "") +
                (fieldId != null ? "fieldId=" + fieldId + ", " : "") +
            "}";
    }

}

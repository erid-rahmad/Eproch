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
 * Criteria class for the {@link com.bhp.opusb.domain.AdCalloutTarget} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.AdCalloutTargetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-callout-targets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdCalloutTargetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter sourceField;

    private StringFilter targetName;

    private LongFilter adOrganizationId;

    private LongFilter calloutId;

    public AdCalloutTargetCriteria() {
    }

    public AdCalloutTargetCriteria(AdCalloutTargetCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.sourceField = other.sourceField == null ? null : other.sourceField.copy();
        this.targetName = other.targetName == null ? null : other.targetName.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.calloutId = other.calloutId == null ? null : other.calloutId.copy();
    }

    @Override
    public AdCalloutTargetCriteria copy() {
        return new AdCalloutTargetCriteria(this);
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

    public StringFilter getSourceField() {
        return sourceField;
    }

    public void setSourceField(StringFilter sourceField) {
        this.sourceField = sourceField;
    }

    public StringFilter getTargetName() {
        return targetName;
    }

    public void setTargetName(StringFilter targetName) {
        this.targetName = targetName;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getCalloutId() {
        return calloutId;
    }

    public void setCalloutId(LongFilter calloutId) {
        this.calloutId = calloutId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdCalloutTargetCriteria that = (AdCalloutTargetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(sourceField, that.sourceField) &&
            Objects.equals(targetName, that.targetName) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(calloutId, that.calloutId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        sourceField,
        targetName,
        adOrganizationId,
        calloutId
        );
    }

    @Override
    public String toString() {
        return "AdCalloutTargetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (sourceField != null ? "sourceField=" + sourceField + ", " : "") +
                (targetName != null ? "targetName=" + targetName + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (calloutId != null ? "calloutId=" + calloutId + ", " : "") +
            "}";
    }

}

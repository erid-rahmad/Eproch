package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.ADTable} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.ADTableResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-tables?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ADTableCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private StringFilter name;

    private BooleanFilter view;

    private BooleanFilter highVolume;

    private StringFilter targetEndpoint;

    private BooleanFilter active;

    private LongFilter aDColumnId;

    private LongFilter adOrganizationId;

    public ADTableCriteria() {
    }

    public ADTableCriteria(ADTableCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.view = other.view == null ? null : other.view.copy();
        this.highVolume = other.highVolume == null ? null : other.highVolume.copy();
        this.targetEndpoint = other.targetEndpoint == null ? null : other.targetEndpoint.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.aDColumnId = other.aDColumnId == null ? null : other.aDColumnId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public ADTableCriteria copy() {
        return new ADTableCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getView() {
        return view;
    }

    public void setView(BooleanFilter view) {
        this.view = view;
    }

    public BooleanFilter getHighVolume() {
        return highVolume;
    }

    public void setHighVolume(BooleanFilter highVolume) {
        this.highVolume = highVolume;
    }

    public StringFilter getTargetEndpoint() {
        return targetEndpoint;
    }

    public void setTargetEndpoint(StringFilter targetEndpoint) {
        this.targetEndpoint = targetEndpoint;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getADColumnId() {
        return aDColumnId;
    }

    public void setADColumnId(LongFilter aDColumnId) {
        this.aDColumnId = aDColumnId;
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
        final ADTableCriteria that = (ADTableCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(name, that.name) &&
            Objects.equals(view, that.view) &&
            Objects.equals(highVolume, that.highVolume) &&
            Objects.equals(targetEndpoint, that.targetEndpoint) &&
            Objects.equals(active, that.active) &&
            Objects.equals(aDColumnId, that.aDColumnId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        name,
        view,
        highVolume,
        targetEndpoint,
        active,
        aDColumnId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "ADTableCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (view != null ? "view=" + view + ", " : "") +
                (highVolume != null ? "highVolume=" + highVolume + ", " : "") +
                (targetEndpoint != null ? "targetEndpoint=" + targetEndpoint + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (aDColumnId != null ? "aDColumnId=" + aDColumnId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

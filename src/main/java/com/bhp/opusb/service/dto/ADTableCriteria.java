package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

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

    private StringFilter name;

    private BooleanFilter view;

    private BooleanFilter active;

    private LongFilter aDColumnId;

    private LongFilter adClientId;

    private LongFilter adOrganizationId;

    public ADTableCriteria() {
    }

    public ADTableCriteria(ADTableCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.view = other.view == null ? null : other.view.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.aDColumnId = other.aDColumnId == null ? null : other.aDColumnId.copy();
        this.adClientId = other.adClientId == null ? null : other.adClientId.copy();
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

    public LongFilter getAdClientId() {
        return adClientId;
    }

    public void setAdClientId(LongFilter adClientId) {
        this.adClientId = adClientId;
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
            Objects.equals(name, that.name) &&
            Objects.equals(view, that.view) &&
            Objects.equals(active, that.active) &&
            Objects.equals(aDColumnId, that.aDColumnId) &&
            Objects.equals(adClientId, that.adClientId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        view,
        active,
        aDColumnId,
        adClientId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "ADTableCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (view != null ? "view=" + view + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (aDColumnId != null ? "aDColumnId=" + aDColumnId + ", " : "") +
                (adClientId != null ? "adClientId=" + adClientId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

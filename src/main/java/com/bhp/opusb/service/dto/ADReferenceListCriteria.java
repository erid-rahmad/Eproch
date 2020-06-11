package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.ADReferenceList} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.ADReferenceListResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-reference-lists?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ADReferenceListCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter value;

    private StringFilter description;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter adReferenceId;

    public ADReferenceListCriteria() {
    }

    public ADReferenceListCriteria(ADReferenceListCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adReferenceId = other.adReferenceId == null ? null : other.adReferenceId.copy();
    }

    @Override
    public ADReferenceListCriteria copy() {
        return new ADReferenceListCriteria(this);
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

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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

    public LongFilter getAdReferenceId() {
        return adReferenceId;
    }

    public void setAdReferenceId(LongFilter adReferenceId) {
        this.adReferenceId = adReferenceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ADReferenceListCriteria that = (ADReferenceListCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(value, that.value) &&
            Objects.equals(description, that.description) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adReferenceId, that.adReferenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        value,
        description,
        active,
        adOrganizationId,
        adReferenceId
        );
    }

    @Override
    public String toString() {
        return "ADReferenceListCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adReferenceId != null ? "adReferenceId=" + adReferenceId + ", " : "") +
            "}";
    }

}

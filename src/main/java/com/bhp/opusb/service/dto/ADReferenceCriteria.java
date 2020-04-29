package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.bhp.opusb.domain.enumeration.ADReferenceType;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.ADReference} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.ADReferenceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-references?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ADReferenceCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ADReferenceType
     */
    public static class ADReferenceTypeFilter extends Filter<ADReferenceType> {

        public ADReferenceTypeFilter() {
        }

        public ADReferenceTypeFilter(ADReferenceTypeFilter filter) {
            super(filter);
        }

        @Override
        public ADReferenceTypeFilter copy() {
            return new ADReferenceTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter value;

    private StringFilter description;

    private ADReferenceTypeFilter referenceType;

    private BooleanFilter active;

    private LongFilter aDReferenceListId;

    public ADReferenceCriteria() {
    }

    public ADReferenceCriteria(ADReferenceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.referenceType = other.referenceType == null ? null : other.referenceType.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.aDReferenceListId = other.aDReferenceListId == null ? null : other.aDReferenceListId.copy();
    }

    @Override
    public ADReferenceCriteria copy() {
        return new ADReferenceCriteria(this);
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

    public ADReferenceTypeFilter getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ADReferenceTypeFilter referenceType) {
        this.referenceType = referenceType;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getADReferenceListId() {
        return aDReferenceListId;
    }

    public void setADReferenceListId(LongFilter aDReferenceListId) {
        this.aDReferenceListId = aDReferenceListId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ADReferenceCriteria that = (ADReferenceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(value, that.value) &&
            Objects.equals(description, that.description) &&
            Objects.equals(referenceType, that.referenceType) &&
            Objects.equals(active, that.active) &&
            Objects.equals(aDReferenceListId, that.aDReferenceListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        value,
        description,
        referenceType,
        active,
        aDReferenceListId
        );
    }

    @Override
    public String toString() {
        return "ADReferenceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (referenceType != null ? "referenceType=" + referenceType + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (aDReferenceListId != null ? "aDReferenceListId=" + aDReferenceListId + ", " : "") +
            "}";
    }

}

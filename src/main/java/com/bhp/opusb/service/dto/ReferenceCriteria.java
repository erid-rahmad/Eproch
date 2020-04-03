package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.bhp.opusb.domain.enumeration.ReferenceType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.Reference} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.ReferenceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /references?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReferenceCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ReferenceType
     */
    public static class ReferenceTypeFilter extends Filter<ReferenceType> {

        public ReferenceTypeFilter() {
        }

        public ReferenceTypeFilter(ReferenceTypeFilter filter) {
            super(filter);
        }

        @Override
        public ReferenceTypeFilter copy() {
            return new ReferenceTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private ReferenceTypeFilter referenceType;

    private LongFilter referenceListId;

    public ReferenceCriteria() {
    }

    public ReferenceCriteria(ReferenceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.referenceType = other.referenceType == null ? null : other.referenceType.copy();
        this.referenceListId = other.referenceListId == null ? null : other.referenceListId.copy();
    }

    @Override
    public ReferenceCriteria copy() {
        return new ReferenceCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public ReferenceTypeFilter getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceTypeFilter referenceType) {
        this.referenceType = referenceType;
    }

    public LongFilter getReferenceListId() {
        return referenceListId;
    }

    public void setReferenceListId(LongFilter referenceListId) {
        this.referenceListId = referenceListId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReferenceCriteria that = (ReferenceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(referenceType, that.referenceType) &&
            Objects.equals(referenceListId, that.referenceListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        referenceType,
        referenceListId
        );
    }

    @Override
    public String toString() {
        return "ReferenceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (referenceType != null ? "referenceType=" + referenceType + ", " : "") +
                (referenceListId != null ? "referenceListId=" + referenceListId + ", " : "") +
            "}";
    }

}

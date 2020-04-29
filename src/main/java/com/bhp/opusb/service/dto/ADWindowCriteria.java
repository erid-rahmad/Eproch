package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.bhp.opusb.domain.enumeration.ADWindowType;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.ADWindow} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.ADWindowResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-windows?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ADWindowCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ADWindowType
     */
    public static class ADWindowTypeFilter extends Filter<ADWindowType> {

        public ADWindowTypeFilter() {
        }

        public ADWindowTypeFilter(ADWindowTypeFilter filter) {
            super(filter);
        }

        @Override
        public ADWindowTypeFilter copy() {
            return new ADWindowTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private ADWindowTypeFilter type;

    private BooleanFilter active;

    private LongFilter aDTabId;

    private LongFilter adClientId;

    private LongFilter adOrganizationId;

    public ADWindowCriteria() {
    }

    public ADWindowCriteria(ADWindowCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.aDTabId = other.aDTabId == null ? null : other.aDTabId.copy();
        this.adClientId = other.adClientId == null ? null : other.adClientId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public ADWindowCriteria copy() {
        return new ADWindowCriteria(this);
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

    public ADWindowTypeFilter getType() {
        return type;
    }

    public void setType(ADWindowTypeFilter type) {
        this.type = type;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getADTabId() {
        return aDTabId;
    }

    public void setADTabId(LongFilter aDTabId) {
        this.aDTabId = aDTabId;
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
        final ADWindowCriteria that = (ADWindowCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type) &&
            Objects.equals(active, that.active) &&
            Objects.equals(aDTabId, that.aDTabId) &&
            Objects.equals(adClientId, that.adClientId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        type,
        active,
        aDTabId,
        adClientId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "ADWindowCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (aDTabId != null ? "aDTabId=" + aDTabId + ", " : "") +
                (adClientId != null ? "adClientId=" + adClientId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

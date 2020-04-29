package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.ADClient} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.ADClientResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-clients?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ADClientCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private StringFilter description;

    private BooleanFilter active;

    private LongFilter aDOrganizationId;

    public ADClientCriteria() {
    }

    public ADClientCriteria(ADClientCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.aDOrganizationId = other.aDOrganizationId == null ? null : other.aDOrganizationId.copy();
    }

    @Override
    public ADClientCriteria copy() {
        return new ADClientCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
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

    public LongFilter getADOrganizationId() {
        return aDOrganizationId;
    }

    public void setADOrganizationId(LongFilter aDOrganizationId) {
        this.aDOrganizationId = aDOrganizationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ADClientCriteria that = (ADClientCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(description, that.description) &&
            Objects.equals(active, that.active) &&
            Objects.equals(aDOrganizationId, that.aDOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        description,
        active,
        aDOrganizationId
        );
    }

    @Override
    public String toString() {
        return "ADClientCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (aDOrganizationId != null ? "aDOrganizationId=" + aDOrganizationId + ", " : "") +
            "}";
    }

}

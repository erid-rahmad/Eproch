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
 * Criteria class for the {@link com.bhp.opusb.domain.ADFieldGroup} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.ADFieldGroupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-field-groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ADFieldGroupCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private StringFilter name;

    private BooleanFilter collapsible;

    private BooleanFilter collapseByDefault;

    private BooleanFilter active;

    public ADFieldGroupCriteria() {
    }

    public ADFieldGroupCriteria(ADFieldGroupCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.collapsible = other.collapsible == null ? null : other.collapsible.copy();
        this.collapseByDefault = other.collapseByDefault == null ? null : other.collapseByDefault.copy();
        this.active = other.active == null ? null : other.active.copy();
    }

    @Override
    public ADFieldGroupCriteria copy() {
        return new ADFieldGroupCriteria(this);
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

    public BooleanFilter getCollapsible() {
        return collapsible;
    }

    public void setCollapsible(BooleanFilter collapsible) {
        this.collapsible = collapsible;
    }

    public BooleanFilter getCollapseByDefault() {
        return collapseByDefault;
    }

    public void setCollapseByDefault(BooleanFilter collapseByDefault) {
        this.collapseByDefault = collapseByDefault;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ADFieldGroupCriteria that = (ADFieldGroupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(name, that.name) &&
            Objects.equals(collapsible, that.collapsible) &&
            Objects.equals(collapseByDefault, that.collapseByDefault) &&
            Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        name,
        collapsible,
        collapseByDefault,
        active
        );
    }

    @Override
    public String toString() {
        return "ADFieldGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (collapsible != null ? "collapsible=" + collapsible + ", " : "") +
                (collapseByDefault != null ? "collapseByDefault=" + collapseByDefault + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
            "}";
    }

}

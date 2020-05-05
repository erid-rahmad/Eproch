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

/**
 * Criteria class for the {@link com.bhp.opusb.domain.ADTab} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.ADTabResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-tabs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ADTabCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter targetEndpoint;

    private BooleanFilter writable;

    private StringFilter displayLogic;

    private StringFilter readOnlyLogic;

    private StringFilter filterQuery;

    private StringFilter orderQuery;

    private BooleanFilter active;

    private LongFilter aDTabId;

    private LongFilter aDFieldId;

    private LongFilter adClientId;

    private LongFilter adOrganizationId;

    private LongFilter adTableId;

    private LongFilter adWindowId;

    private LongFilter parentTabId;

    public ADTabCriteria() {
    }

    public ADTabCriteria(ADTabCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.targetEndpoint = other.targetEndpoint == null ? null : other.targetEndpoint.copy();
        this.writable = other.writable == null ? null : other.writable.copy();
        this.displayLogic = other.displayLogic == null ? null : other.displayLogic.copy();
        this.readOnlyLogic = other.readOnlyLogic == null ? null : other.readOnlyLogic.copy();
        this.filterQuery = other.filterQuery == null ? null : other.filterQuery.copy();
        this.orderQuery = other.orderQuery == null ? null : other.orderQuery.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.aDTabId = other.aDTabId == null ? null : other.aDTabId.copy();
        this.aDFieldId = other.aDFieldId == null ? null : other.aDFieldId.copy();
        this.adClientId = other.adClientId == null ? null : other.adClientId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adTableId = other.adTableId == null ? null : other.adTableId.copy();
        this.adWindowId = other.adWindowId == null ? null : other.adWindowId.copy();
        this.parentTabId = other.parentTabId == null ? null : other.parentTabId.copy();
    }

    @Override
    public ADTabCriteria copy() {
        return new ADTabCriteria(this);
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

    public StringFilter getTargetEndpoint() {
        return targetEndpoint;
    }

    public void setTargetEndpoint(StringFilter targetEndpoint) {
        this.targetEndpoint = targetEndpoint;
    }

    public BooleanFilter getWritable() {
        return writable;
    }

    public void setWritable(BooleanFilter writable) {
        this.writable = writable;
    }

    public StringFilter getDisplayLogic() {
        return displayLogic;
    }

    public void setDisplayLogic(StringFilter displayLogic) {
        this.displayLogic = displayLogic;
    }

    public StringFilter getReadOnlyLogic() {
        return readOnlyLogic;
    }

    public void setReadOnlyLogic(StringFilter readOnlyLogic) {
        this.readOnlyLogic = readOnlyLogic;
    }

    public StringFilter getFilterQuery() {
        return filterQuery;
    }

    public void setFilterQuery(StringFilter filterQuery) {
        this.filterQuery = filterQuery;
    }

    public StringFilter getOrderQuery() {
        return orderQuery;
    }

    public void setOrderQuery(StringFilter orderQuery) {
        this.orderQuery = orderQuery;
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

    public LongFilter getADFieldId() {
        return aDFieldId;
    }

    public void setADFieldId(LongFilter aDFieldId) {
        this.aDFieldId = aDFieldId;
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

    public LongFilter getAdTableId() {
        return adTableId;
    }

    public void setAdTableId(LongFilter adTableId) {
        this.adTableId = adTableId;
    }

    public LongFilter getAdWindowId() {
        return adWindowId;
    }

    public void setAdWindowId(LongFilter adWindowId) {
        this.adWindowId = adWindowId;
    }

    public LongFilter getParentTabId() {
        return parentTabId;
    }

    public void setParentTabId(LongFilter parentTabId) {
        this.parentTabId = parentTabId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ADTabCriteria that = (ADTabCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(targetEndpoint, that.targetEndpoint) &&
            Objects.equals(writable, that.writable) &&
            Objects.equals(displayLogic, that.displayLogic) &&
            Objects.equals(readOnlyLogic, that.readOnlyLogic) &&
            Objects.equals(filterQuery, that.filterQuery) &&
            Objects.equals(orderQuery, that.orderQuery) &&
            Objects.equals(active, that.active) &&
            Objects.equals(aDTabId, that.aDTabId) &&
            Objects.equals(aDFieldId, that.aDFieldId) &&
            Objects.equals(adClientId, that.adClientId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adTableId, that.adTableId) &&
            Objects.equals(adWindowId, that.adWindowId) &&
            Objects.equals(parentTabId, that.parentTabId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        targetEndpoint,
        writable,
        displayLogic,
        readOnlyLogic,
        filterQuery,
        orderQuery,
        active,
        aDTabId,
        aDFieldId,
        adClientId,
        adOrganizationId,
        adTableId,
        adWindowId,
        parentTabId
        );
    }

    @Override
    public String toString() {
        return "ADTabCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (targetEndpoint != null ? "targetEndpoint=" + targetEndpoint + ", " : "") +
                (writable != null ? "writable=" + writable + ", " : "") +
                (displayLogic != null ? "displayLogic=" + displayLogic + ", " : "") +
                (readOnlyLogic != null ? "readOnlyLogic=" + readOnlyLogic + ", " : "") +
                (filterQuery != null ? "filterQuery=" + filterQuery + ", " : "") +
                (orderQuery != null ? "orderQuery=" + orderQuery + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (aDTabId != null ? "aDTabId=" + aDTabId + ", " : "") +
                (aDFieldId != null ? "aDFieldId=" + aDFieldId + ", " : "") +
                (adClientId != null ? "adClientId=" + adClientId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adTableId != null ? "adTableId=" + adTableId + ", " : "") +
                (adWindowId != null ? "adWindowId=" + adWindowId + ", " : "") +
                (parentTabId != null ? "parentTabId=" + parentTabId + ", " : "") +
            "}";
    }

}

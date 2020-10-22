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

    private UUIDFilter uid;

    private StringFilter name;

    private StringFilter description;

    private StringFilter iconName;

    private StringFilter targetEndpoint;

    private BooleanFilter singleRow;

    private BooleanFilter deletable;

    private BooleanFilter writable;

    private StringFilter displayLogic;

    private StringFilter readOnlyLogic;

    private StringFilter filterQuery;

    private StringFilter orderQuery;

    private IntegerFilter tabSequence;

    private BooleanFilter active;

    private LongFilter aDTabId;

    private LongFilter aDFieldId;

    private LongFilter adOrganizationId;

    private LongFilter adTableId;

    private LongFilter parentColumnId;

    private LongFilter foreignColumnId;

    private LongFilter adWindowId;

    private LongFilter parentTabId;

    public ADTabCriteria() {
    }

    public ADTabCriteria(ADTabCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.iconName = other.iconName == null ? null : other.iconName.copy();
        this.targetEndpoint = other.targetEndpoint == null ? null : other.targetEndpoint.copy();
        this.singleRow = other.singleRow == null ? null : other.singleRow.copy();
        this.deletable = other.deletable == null ? null : other.deletable.copy();
        this.writable = other.writable == null ? null : other.writable.copy();
        this.displayLogic = other.displayLogic == null ? null : other.displayLogic.copy();
        this.readOnlyLogic = other.readOnlyLogic == null ? null : other.readOnlyLogic.copy();
        this.filterQuery = other.filterQuery == null ? null : other.filterQuery.copy();
        this.orderQuery = other.orderQuery == null ? null : other.orderQuery.copy();
        this.tabSequence = other.tabSequence == null ? null : other.tabSequence.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.aDTabId = other.aDTabId == null ? null : other.aDTabId.copy();
        this.aDFieldId = other.aDFieldId == null ? null : other.aDFieldId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adTableId = other.adTableId == null ? null : other.adTableId.copy();
        this.parentColumnId = other.parentColumnId == null ? null : other.parentColumnId.copy();
        this.foreignColumnId = other.foreignColumnId == null ? null : other.foreignColumnId.copy();
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getIconName() {
        return iconName;
    }

    public void setIconName(StringFilter iconName) {
        this.iconName = iconName;
    }

    public StringFilter getTargetEndpoint() {
        return targetEndpoint;
    }

    public void setTargetEndpoint(StringFilter targetEndpoint) {
        this.targetEndpoint = targetEndpoint;
    }

    public BooleanFilter getSingleRow() {
        return singleRow;
    }

    public void setSingleRow(BooleanFilter singleRow) {
        this.singleRow = singleRow;
    }

    public BooleanFilter getDeletable() {
        return deletable;
    }

    public void setDeletable(BooleanFilter deletable) {
        this.deletable = deletable;
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

    public IntegerFilter getTabSequence() {
        return tabSequence;
    }

    public void setTabSequence(IntegerFilter tabSequence) {
        this.tabSequence = tabSequence;
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

    public LongFilter getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(LongFilter parentColumnId) {
        this.parentColumnId = parentColumnId;
    }

    public LongFilter getForeignColumnId() {
        return foreignColumnId;
    }

    public void setForeignColumnId(LongFilter foreignColumnId) {
        this.foreignColumnId = foreignColumnId;
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
            Objects.equals(uid, that.uid) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(iconName, that.iconName) &&
            Objects.equals(targetEndpoint, that.targetEndpoint) &&
            Objects.equals(singleRow, that.singleRow) &&
            Objects.equals(deletable, that.deletable) &&
            Objects.equals(writable, that.writable) &&
            Objects.equals(displayLogic, that.displayLogic) &&
            Objects.equals(readOnlyLogic, that.readOnlyLogic) &&
            Objects.equals(filterQuery, that.filterQuery) &&
            Objects.equals(orderQuery, that.orderQuery) &&
            Objects.equals(tabSequence, that.tabSequence) &&
            Objects.equals(active, that.active) &&
            Objects.equals(aDTabId, that.aDTabId) &&
            Objects.equals(aDFieldId, that.aDFieldId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adTableId, that.adTableId) &&
            Objects.equals(parentColumnId, that.parentColumnId) &&
            Objects.equals(foreignColumnId, that.foreignColumnId) &&
            Objects.equals(adWindowId, that.adWindowId) &&
            Objects.equals(parentTabId, that.parentTabId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        name,
        description,
        iconName,
        targetEndpoint,
        singleRow,
        deletable,
        writable,
        displayLogic,
        readOnlyLogic,
        filterQuery,
        orderQuery,
        tabSequence,
        active,
        aDTabId,
        aDFieldId,
        adOrganizationId,
        adTableId,
        parentColumnId,
        foreignColumnId,
        adWindowId,
        parentTabId
        );
    }

    @Override
    public String toString() {
        return "ADTabCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (iconName != null ? "iconName=" + iconName + ", " : "") +
                (targetEndpoint != null ? "targetEndpoint=" + targetEndpoint + ", " : "") +
                (singleRow != null ? "singleRow=" + singleRow + ", " : "") +
                (deletable != null ? "deletable=" + deletable + ", " : "") +
                (writable != null ? "writable=" + writable + ", " : "") +
                (displayLogic != null ? "displayLogic=" + displayLogic + ", " : "") +
                (readOnlyLogic != null ? "readOnlyLogic=" + readOnlyLogic + ", " : "") +
                (filterQuery != null ? "filterQuery=" + filterQuery + ", " : "") +
                (orderQuery != null ? "orderQuery=" + orderQuery + ", " : "") +
                (tabSequence != null ? "tabSequence=" + tabSequence + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (aDTabId != null ? "aDTabId=" + aDTabId + ", " : "") +
                (aDFieldId != null ? "aDFieldId=" + aDFieldId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adTableId != null ? "adTableId=" + adTableId + ", " : "") +
                (parentColumnId != null ? "parentColumnId=" + parentColumnId + ", " : "") +
                (foreignColumnId != null ? "foreignColumnId=" + foreignColumnId + ", " : "") +
                (adWindowId != null ? "adWindowId=" + adWindowId + ", " : "") +
                (parentTabId != null ? "parentTabId=" + parentTabId + ", " : "") +
            "}";
    }

}

package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.bhp.opusb.domain.enumeration.PaDashboardItemType;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.PaDashboardItem} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.PaDashboardItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pa-dashboard-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaDashboardItemCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PaDashboardItemType
     */
    public static class PaDashboardItemTypeFilter extends Filter<PaDashboardItemType> {

        public PaDashboardItemTypeFilter() {
        }

        public PaDashboardItemTypeFilter(PaDashboardItemTypeFilter filter) {
            super(filter);
        }

        @Override
        public PaDashboardItemTypeFilter copy() {
            return new PaDashboardItemTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private IntegerFilter columnNo;

    private IntegerFilter rowNo;

    private PaDashboardItemTypeFilter type;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter adWatchListId;

    private LongFilter paDashboardId;

    public PaDashboardItemCriteria() {
    }

    public PaDashboardItemCriteria(PaDashboardItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.columnNo = other.columnNo == null ? null : other.columnNo.copy();
        this.rowNo = other.rowNo == null ? null : other.rowNo.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adWatchListId = other.adWatchListId == null ? null : other.adWatchListId.copy();
        this.paDashboardId = other.paDashboardId == null ? null : other.paDashboardId.copy();
    }

    @Override
    public PaDashboardItemCriteria copy() {
        return new PaDashboardItemCriteria(this);
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

    public IntegerFilter getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(IntegerFilter columnNo) {
        this.columnNo = columnNo;
    }

    public IntegerFilter getRowNo() {
        return rowNo;
    }

    public void setRowNo(IntegerFilter rowNo) {
        this.rowNo = rowNo;
    }

    public PaDashboardItemTypeFilter getType() {
        return type;
    }

    public void setType(PaDashboardItemTypeFilter type) {
        this.type = type;
    }

    public UUIDFilter getUid() {
        return uid;
    }

    public void setUid(UUIDFilter uid) {
        this.uid = uid;
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

    public LongFilter getAdWatchListId() {
        return adWatchListId;
    }

    public void setAdWatchListId(LongFilter adWatchListId) {
        this.adWatchListId = adWatchListId;
    }

    public LongFilter getPaDashboardId() {
        return paDashboardId;
    }

    public void setPaDashboardId(LongFilter paDashboardId) {
        this.paDashboardId = paDashboardId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaDashboardItemCriteria that = (PaDashboardItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(columnNo, that.columnNo) &&
            Objects.equals(rowNo, that.rowNo) &&
            Objects.equals(type, that.type) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adWatchListId, that.adWatchListId) &&
            Objects.equals(paDashboardId, that.paDashboardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        columnNo,
        rowNo,
        type,
        uid,
        active,
        adOrganizationId,
        adWatchListId,
        paDashboardId
        );
    }

    @Override
    public String toString() {
        return "PaDashboardItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (columnNo != null ? "columnNo=" + columnNo + ", " : "") +
                (rowNo != null ? "rowNo=" + rowNo + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adWatchListId != null ? "adWatchListId=" + adWatchListId + ", " : "") +
                (paDashboardId != null ? "paDashboardId=" + paDashboardId + ", " : "") +
            "}";
    }

}

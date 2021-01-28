package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.PaDashboardPreference} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.PaDashboardPreferenceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pa-dashboard-preferences?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaDashboardPreferenceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter columnNo;

    private IntegerFilter rowNo;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter adUserId;

    private LongFilter paDashboardItemId;

    public PaDashboardPreferenceCriteria() {
    }

    public PaDashboardPreferenceCriteria(PaDashboardPreferenceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.columnNo = other.columnNo == null ? null : other.columnNo.copy();
        this.rowNo = other.rowNo == null ? null : other.rowNo.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adUserId = other.adUserId == null ? null : other.adUserId.copy();
        this.paDashboardItemId = other.paDashboardItemId == null ? null : other.paDashboardItemId.copy();
    }

    @Override
    public PaDashboardPreferenceCriteria copy() {
        return new PaDashboardPreferenceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LongFilter getAdUserId() {
        return adUserId;
    }

    public void setAdUserId(LongFilter adUserId) {
        this.adUserId = adUserId;
    }

    public LongFilter getPaDashboardItemId() {
        return paDashboardItemId;
    }

    public void setPaDashboardItemId(LongFilter paDashboardItemId) {
        this.paDashboardItemId = paDashboardItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaDashboardPreferenceCriteria that = (PaDashboardPreferenceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(columnNo, that.columnNo) &&
            Objects.equals(rowNo, that.rowNo) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adUserId, that.adUserId) &&
            Objects.equals(paDashboardItemId, that.paDashboardItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        columnNo,
        rowNo,
        uid,
        active,
        adOrganizationId,
        adUserId,
        paDashboardItemId
        );
    }

    @Override
    public String toString() {
        return "PaDashboardPreferenceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (columnNo != null ? "columnNo=" + columnNo + ", " : "") +
                (rowNo != null ? "rowNo=" + rowNo + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adUserId != null ? "adUserId=" + adUserId + ", " : "") +
                (paDashboardItemId != null ? "paDashboardItemId=" + paDashboardItemId + ", " : "") +
            "}";
    }

}

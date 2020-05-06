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
 * Criteria class for the {@link com.bhp.opusb.domain.ADField} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.ADFieldResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ad-fields?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ADFieldCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter hint;

    private StringFilter staticText;

    private BooleanFilter staticField;

    private BooleanFilter labelOnly;

    private BooleanFilter showLabel;

    private BooleanFilter showInGrid;

    private BooleanFilter showInDetail;

    private IntegerFilter gridSequence;

    private IntegerFilter detailSequence;

    private StringFilter displayLogic;

    private BooleanFilter writable;

    private IntegerFilter columnNo;

    private IntegerFilter columnSpan;

    private BooleanFilter active;

    private LongFilter adClientId;

    private LongFilter adOrganizationId;

    private LongFilter adReferenceId;

    private LongFilter adColumnId;

    private LongFilter adTabId;

    public ADFieldCriteria() {
    }

    public ADFieldCriteria(ADFieldCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.hint = other.hint == null ? null : other.hint.copy();
        this.staticText = other.staticText == null ? null : other.staticText.copy();
        this.staticField = other.staticField == null ? null : other.staticField.copy();
        this.labelOnly = other.labelOnly == null ? null : other.labelOnly.copy();
        this.showLabel = other.showLabel == null ? null : other.showLabel.copy();
        this.showInGrid = other.showInGrid == null ? null : other.showInGrid.copy();
        this.showInDetail = other.showInDetail == null ? null : other.showInDetail.copy();
        this.gridSequence = other.gridSequence == null ? null : other.gridSequence.copy();
        this.detailSequence = other.detailSequence == null ? null : other.detailSequence.copy();
        this.displayLogic = other.displayLogic == null ? null : other.displayLogic.copy();
        this.writable = other.writable == null ? null : other.writable.copy();
        this.columnNo = other.columnNo == null ? null : other.columnNo.copy();
        this.columnSpan = other.columnSpan == null ? null : other.columnSpan.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adClientId = other.adClientId == null ? null : other.adClientId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.adReferenceId = other.adReferenceId == null ? null : other.adReferenceId.copy();
        this.adColumnId = other.adColumnId == null ? null : other.adColumnId.copy();
        this.adTabId = other.adTabId == null ? null : other.adTabId.copy();
    }

    @Override
    public ADFieldCriteria copy() {
        return new ADFieldCriteria(this);
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

    public StringFilter getHint() {
        return hint;
    }

    public void setHint(StringFilter hint) {
        this.hint = hint;
    }

    public StringFilter getStaticText() {
        return staticText;
    }

    public void setStaticText(StringFilter staticText) {
        this.staticText = staticText;
    }

    public BooleanFilter getStaticField() {
        return staticField;
    }

    public void setStaticField(BooleanFilter staticField) {
        this.staticField = staticField;
    }

    public BooleanFilter getLabelOnly() {
        return labelOnly;
    }

    public void setLabelOnly(BooleanFilter labelOnly) {
        this.labelOnly = labelOnly;
    }

    public BooleanFilter getShowLabel() {
        return showLabel;
    }

    public void setShowLabel(BooleanFilter showLabel) {
        this.showLabel = showLabel;
    }

    public BooleanFilter getShowInGrid() {
        return showInGrid;
    }

    public void setShowInGrid(BooleanFilter showInGrid) {
        this.showInGrid = showInGrid;
    }

    public BooleanFilter getShowInDetail() {
        return showInDetail;
    }

    public void setShowInDetail(BooleanFilter showInDetail) {
        this.showInDetail = showInDetail;
    }

    public IntegerFilter getGridSequence() {
        return gridSequence;
    }

    public void setGridSequence(IntegerFilter gridSequence) {
        this.gridSequence = gridSequence;
    }

    public IntegerFilter getDetailSequence() {
        return detailSequence;
    }

    public void setDetailSequence(IntegerFilter detailSequence) {
        this.detailSequence = detailSequence;
    }

    public StringFilter getDisplayLogic() {
        return displayLogic;
    }

    public void setDisplayLogic(StringFilter displayLogic) {
        this.displayLogic = displayLogic;
    }

    public BooleanFilter getWritable() {
        return writable;
    }

    public void setWritable(BooleanFilter writable) {
        this.writable = writable;
    }

    public IntegerFilter getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(IntegerFilter columnNo) {
        this.columnNo = columnNo;
    }

    public IntegerFilter getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(IntegerFilter columnSpan) {
        this.columnSpan = columnSpan;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
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

    public LongFilter getAdReferenceId() {
        return adReferenceId;
    }

    public void setAdReferenceId(LongFilter adReferenceId) {
        this.adReferenceId = adReferenceId;
    }

    public LongFilter getAdColumnId() {
        return adColumnId;
    }

    public void setAdColumnId(LongFilter adColumnId) {
        this.adColumnId = adColumnId;
    }

    public LongFilter getAdTabId() {
        return adTabId;
    }

    public void setAdTabId(LongFilter adTabId) {
        this.adTabId = adTabId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ADFieldCriteria that = (ADFieldCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(hint, that.hint) &&
            Objects.equals(staticText, that.staticText) &&
            Objects.equals(staticField, that.staticField) &&
            Objects.equals(labelOnly, that.labelOnly) &&
            Objects.equals(showLabel, that.showLabel) &&
            Objects.equals(showInGrid, that.showInGrid) &&
            Objects.equals(showInDetail, that.showInDetail) &&
            Objects.equals(gridSequence, that.gridSequence) &&
            Objects.equals(detailSequence, that.detailSequence) &&
            Objects.equals(displayLogic, that.displayLogic) &&
            Objects.equals(writable, that.writable) &&
            Objects.equals(columnNo, that.columnNo) &&
            Objects.equals(columnSpan, that.columnSpan) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adClientId, that.adClientId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(adReferenceId, that.adReferenceId) &&
            Objects.equals(adColumnId, that.adColumnId) &&
            Objects.equals(adTabId, that.adTabId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        hint,
        staticText,
        staticField,
        labelOnly,
        showLabel,
        showInGrid,
        showInDetail,
        gridSequence,
        detailSequence,
        displayLogic,
        writable,
        columnNo,
        columnSpan,
        active,
        adClientId,
        adOrganizationId,
        adReferenceId,
        adColumnId,
        adTabId
        );
    }

    @Override
    public String toString() {
        return "ADFieldCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (hint != null ? "hint=" + hint + ", " : "") +
                (staticText != null ? "staticText=" + staticText + ", " : "") +
                (staticField != null ? "staticField=" + staticField + ", " : "") +
                (labelOnly != null ? "labelOnly=" + labelOnly + ", " : "") +
                (showLabel != null ? "showLabel=" + showLabel + ", " : "") +
                (showInGrid != null ? "showInGrid=" + showInGrid + ", " : "") +
                (showInDetail != null ? "showInDetail=" + showInDetail + ", " : "") +
                (gridSequence != null ? "gridSequence=" + gridSequence + ", " : "") +
                (detailSequence != null ? "detailSequence=" + detailSequence + ", " : "") +
                (displayLogic != null ? "displayLogic=" + displayLogic + ", " : "") +
                (writable != null ? "writable=" + writable + ", " : "") +
                (columnNo != null ? "columnNo=" + columnNo + ", " : "") +
                (columnSpan != null ? "columnSpan=" + columnSpan + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adClientId != null ? "adClientId=" + adClientId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (adReferenceId != null ? "adReferenceId=" + adReferenceId + ", " : "") +
                (adColumnId != null ? "adColumnId=" + adColumnId + ", " : "") +
                (adTabId != null ? "adTabId=" + adTabId + ", " : "") +
            "}";
    }

}

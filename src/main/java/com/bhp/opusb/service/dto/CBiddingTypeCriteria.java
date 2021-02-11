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
 * Criteria class for the {@link com.bhp.opusb.domain.CBiddingType} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CBiddingTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-bidding-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CBiddingTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private BooleanFilter costEvaluationSelection;

    private BooleanFilter selectedWinner;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter productClassificationId;

    public CBiddingTypeCriteria() {
    }

    public CBiddingTypeCriteria(CBiddingTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.costEvaluationSelection = other.costEvaluationSelection == null ? null : other.costEvaluationSelection.copy();
        this.selectedWinner = other.selectedWinner == null ? null : other.selectedWinner.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.productClassificationId = other.productClassificationId == null ? null : other.productClassificationId.copy();
    }

    @Override
    public CBiddingTypeCriteria copy() {
        return new CBiddingTypeCriteria(this);
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

    public BooleanFilter getCostEvaluationSelection() {
        return costEvaluationSelection;
    }

    public void setCostEvaluationSelection(BooleanFilter costEvaluationSelection) {
        this.costEvaluationSelection = costEvaluationSelection;
    }

    public BooleanFilter getSelectedWinner() {
        return selectedWinner;
    }

    public void setSelectedWinner(BooleanFilter selectedWinner) {
        this.selectedWinner = selectedWinner;
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

    public LongFilter getProductClassificationId() {
        return productClassificationId;
    }

    public void setProductClassificationId(LongFilter productClassificationId) {
        this.productClassificationId = productClassificationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CBiddingTypeCriteria that = (CBiddingTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(costEvaluationSelection, that.costEvaluationSelection) &&
            Objects.equals(selectedWinner, that.selectedWinner) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(productClassificationId, that.productClassificationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        costEvaluationSelection,
        selectedWinner,
        uid,
        active,
        adOrganizationId,
        productClassificationId
        );
    }

    @Override
    public String toString() {
        return "CBiddingTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (costEvaluationSelection != null ? "costEvaluationSelection=" + costEvaluationSelection + ", " : "") +
                (selectedWinner != null ? "selectedWinner=" + selectedWinner + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (productClassificationId != null ? "productClassificationId=" + productClassificationId + ", " : "") +
            "}";
    }

}

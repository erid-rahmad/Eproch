package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.CBudgetPlan} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.CBudgetPlanResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-budget-plans?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CBudgetPlanCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private StringFilter budgetDeduction;

    private BigDecimalFilter budgetAmount;

    private BigDecimalFilter amountAvailable;

    private LocalDateFilter documentDate;

    private BooleanFilter preventOverBudget;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter cCostCenterId;

    private LongFilter cCurrencyId;

    public CBudgetPlanCriteria() {
    }

    public CBudgetPlanCriteria(CBudgetPlanCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.budgetDeduction = other.budgetDeduction == null ? null : other.budgetDeduction.copy();
        this.budgetAmount = other.budgetAmount == null ? null : other.budgetAmount.copy();
        this.amountAvailable = other.amountAvailable == null ? null : other.amountAvailable.copy();
        this.documentDate = other.documentDate == null ? null : other.documentDate.copy();
        this.preventOverBudget = other.preventOverBudget == null ? null : other.preventOverBudget.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.cCostCenterId = other.cCostCenterId == null ? null : other.cCostCenterId.copy();
        this.cCurrencyId = other.cCurrencyId == null ? null : other.cCurrencyId.copy();
    }

    @Override
    public CBudgetPlanCriteria copy() {
        return new CBudgetPlanCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
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

    public StringFilter getBudgetDeduction() {
        return budgetDeduction;
    }

    public void setBudgetDeduction(StringFilter budgetDeduction) {
        this.budgetDeduction = budgetDeduction;
    }

    public BigDecimalFilter getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(BigDecimalFilter budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public BigDecimalFilter getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(BigDecimalFilter amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public LocalDateFilter getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateFilter documentDate) {
        this.documentDate = documentDate;
    }

    public BooleanFilter getPreventOverBudget() {
        return preventOverBudget;
    }

    public void setPreventOverBudget(BooleanFilter preventOverBudget) {
        this.preventOverBudget = preventOverBudget;
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

    public LongFilter getCCostCenterId() {
        return cCostCenterId;
    }

    public void setCCostCenterId(LongFilter cCostCenterId) {
        this.cCostCenterId = cCostCenterId;
    }

    public LongFilter getCCurrencyId() {
        return cCurrencyId;
    }

    public void setCCurrencyId(LongFilter cCurrencyId) {
        this.cCurrencyId = cCurrencyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CBudgetPlanCriteria that = (CBudgetPlanCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(budgetDeduction, that.budgetDeduction) &&
            Objects.equals(budgetAmount, that.budgetAmount) &&
            Objects.equals(amountAvailable, that.amountAvailable) &&
            Objects.equals(documentDate, that.documentDate) &&
            Objects.equals(preventOverBudget, that.preventOverBudget) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(cCostCenterId, that.cCostCenterId) &&
            Objects.equals(cCurrencyId, that.cCurrencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        description,
        budgetDeduction,
        budgetAmount,
        amountAvailable,
        documentDate,
        preventOverBudget,
        uid,
        active,
        adOrganizationId,
        cCostCenterId,
        cCurrencyId
        );
    }

    @Override
    public String toString() {
        return "CBudgetPlanCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (budgetDeduction != null ? "budgetDeduction=" + budgetDeduction + ", " : "") +
                (budgetAmount != null ? "budgetAmount=" + budgetAmount + ", " : "") +
                (amountAvailable != null ? "amountAvailable=" + amountAvailable + ", " : "") +
                (documentDate != null ? "documentDate=" + documentDate + ", " : "") +
                (preventOverBudget != null ? "preventOverBudget=" + preventOverBudget + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (cCostCenterId != null ? "cCostCenterId=" + cCostCenterId + ", " : "") +
                (cCurrencyId != null ? "cCurrencyId=" + cCurrencyId + ", " : "") +
            "}";
    }

}

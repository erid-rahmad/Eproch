package com.bhp.opusb.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CBudgetPlan.
 */
@Entity
@Table(name = "c_budget_plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CBudgetPlan extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    /**
     * Deduct from purchase requisition, purchase order, and/or bidding result
     */
    @Column(name = "budget_deduction")
    private String budgetDeduction;

    @NotNull
    @Column(name = "budget_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal budgetAmount;

    @NotNull
    @Column(name = "amount_available", precision = 21, scale = 2, nullable = false)
    private BigDecimal amountAvailable;

    @NotNull
    @Column(name = "document_date", nullable = false)
    private LocalDate documentDate;

    @Column(name = "prevent_over_budget")
    private Boolean preventOverBudget;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active = true;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cBudgetPlans")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cBudgetPlans")
    private CCostCenter cCostCenter;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cBudgetPlans")
    private CCurrency cCurrency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public CBudgetPlan code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public CBudgetPlan name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CBudgetPlan description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBudgetDeduction() {
        return budgetDeduction;
    }

    public CBudgetPlan budgetDeduction(String budgetDeduction) {
        this.budgetDeduction = budgetDeduction;
        return this;
    }

    public void setBudgetDeduction(String budgetDeduction) {
        this.budgetDeduction = budgetDeduction;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public CBudgetPlan budgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
        return this;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public BigDecimal getAmountAvailable() {
        return amountAvailable;
    }

    public CBudgetPlan amountAvailable(BigDecimal amountAvailable) {
        this.amountAvailable = amountAvailable;
        return this;
    }

    public void setAmountAvailable(BigDecimal amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public LocalDate getDocumentDate() {
        return documentDate;
    }

    public CBudgetPlan documentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
        return this;
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public Boolean isPreventOverBudget() {
        return preventOverBudget;
    }

    public CBudgetPlan preventOverBudget(Boolean preventOverBudget) {
        this.preventOverBudget = preventOverBudget;
        return this;
    }

    public void setPreventOverBudget(Boolean preventOverBudget) {
        this.preventOverBudget = preventOverBudget;
    }

    public UUID getUid() {
        return uid;
    }

    public CBudgetPlan uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CBudgetPlan active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CBudgetPlan adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CCostCenter getCCostCenter() {
        return cCostCenter;
    }

    public CBudgetPlan cCostCenter(CCostCenter cCostCenter) {
        this.cCostCenter = cCostCenter;
        return this;
    }

    public void setCCostCenter(CCostCenter cCostCenter) {
        this.cCostCenter = cCostCenter;
    }

    public CCurrency getCCurrency() {
        return cCurrency;
    }

    public CBudgetPlan cCurrency(CCurrency cCurrency) {
        this.cCurrency = cCurrency;
        return this;
    }

    public void setCCurrency(CCurrency cCurrency) {
        this.cCurrency = cCurrency;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void prePersist() {
        uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CBudgetPlan)) {
            return false;
        }
        return id != null && id.equals(((CBudgetPlan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CBudgetPlan{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", budgetDeduction='" + getBudgetDeduction() + "'" +
            ", budgetAmount=" + getBudgetAmount() +
            ", amountAvailable=" + getAmountAvailable() +
            ", documentDate='" + getDocumentDate() + "'" +
            ", preventOverBudget='" + isPreventOverBudget() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

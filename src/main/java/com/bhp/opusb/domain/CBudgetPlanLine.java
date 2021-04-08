package com.bhp.opusb.domain;

import java.math.BigDecimal;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CBudgetPlanLine.
 */
@Entity
@Table(name = "c_budget_plan_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CBudgetPlanLine extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "total_debit", precision = 21, scale = 2)
    private BigDecimal totalDebit;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active = true;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cBudgetPlanLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cBudgetPlanLines")
    private CCurrency cCurrency;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cBudgetPlanLines")
    private CDocumentType cDocumentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cBudgetPlanLines")
    private CBudgetPlan cBudgetPlan;

    /**
     * Mandatory for document type Bidding
     */
    @ManyToOne
    @JsonIgnoreProperties("cBudgetPlanLines")
    private MBidding mBidding;

    /**
     * Mandatory for document type Purchase Order
     */
    @ManyToOne
    @JsonIgnoreProperties("cBudgetPlanLines")
    private MPurchaseOrder mPurchaseOrder;

    /**
     * Mandatory for document type Purchase Requisition
     */
    @ManyToOne
    @JsonIgnoreProperties("cBudgetPlanLines")
    private MRequisition mRequisition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalDebit() {
        return totalDebit;
    }

    public CBudgetPlanLine totalDebit(BigDecimal totalDebit) {
        this.totalDebit = totalDebit;
        return this;
    }

    public void setTotalDebit(BigDecimal totalDebit) {
        this.totalDebit = totalDebit;
    }

    public UUID getUid() {
        return uid;
    }

    public CBudgetPlanLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CBudgetPlanLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CBudgetPlanLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CCurrency getCCurrency() {
        return cCurrency;
    }

    public CBudgetPlanLine cCurrency(CCurrency cCurrency) {
        this.cCurrency = cCurrency;
        return this;
    }

    public void setCCurrency(CCurrency cCurrency) {
        this.cCurrency = cCurrency;
    }

    public CDocumentType getCDocumentType() {
        return cDocumentType;
    }

    public CBudgetPlanLine cDocumentType(CDocumentType cDocumentType) {
        this.cDocumentType = cDocumentType;
        return this;
    }

    public void setCDocumentType(CDocumentType cDocumentType) {
        this.cDocumentType = cDocumentType;
    }

    public CBudgetPlan getCBudgetPlan() {
        return cBudgetPlan;
    }

    public CBudgetPlanLine cBudgetPlan(CBudgetPlan cBudgetPlan) {
        this.cBudgetPlan = cBudgetPlan;
        return this;
    }

    public void setCBudgetPlan(CBudgetPlan cBudgetPlan) {
        this.cBudgetPlan = cBudgetPlan;
    }

    public MBidding getMBidding() {
        return mBidding;
    }

    public CBudgetPlanLine mBidding(MBidding mBidding) {
        this.mBidding = mBidding;
        return this;
    }

    public void setMBidding(MBidding mBidding) {
        this.mBidding = mBidding;
    }

    public MPurchaseOrder getMPurchaseOrder() {
        return mPurchaseOrder;
    }

    public CBudgetPlanLine mPurchaseOrder(MPurchaseOrder mPurchaseOrder) {
        this.mPurchaseOrder = mPurchaseOrder;
        return this;
    }

    public void setMPurchaseOrder(MPurchaseOrder mPurchaseOrder) {
        this.mPurchaseOrder = mPurchaseOrder;
    }

    public MRequisition getMRequisition() {
        return mRequisition;
    }

    public CBudgetPlanLine mRequisition(MRequisition mRequisition) {
        this.mRequisition = mRequisition;
        return this;
    }

    public void setMRequisition(MRequisition mRequisition) {
        this.mRequisition = mRequisition;
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
        if (!(o instanceof CBudgetPlanLine)) {
            return false;
        }
        return id != null && id.equals(((CBudgetPlanLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CBudgetPlanLine{" +
            "id=" + getId() +
            ", totalDebit=" + getTotalDebit() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A MPurchaseOrder.
 */
@Entity
@Table(name = "m_purchase_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPurchaseOrder extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Next action for the document.
     */
    @NotNull
    @Size(max = 10)
    @Column(name = "document_action", length = 10, nullable = false)
    private String documentAction;

    /**
     * Current document status.
     */
    @NotNull
    @Size(max = 10)
    @Column(name = "document_status", length = 10, nullable = false)
    private String documentStatus;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "is_processed")
    private Boolean isProcessed;

    @Column(name = "tax")
    private Boolean tax;

    @Column(name = "document_date")
    private LocalDate documentDate;

    @Column(name = "date_required")
    private LocalDate dateRequired;

    @Column(name = "description")
    private String description;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    /**
     * Purchase Order
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPurchaseOrders")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPurchaseOrders")
    private CDocumentType documentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPurchaseOrders")
    private CVendor vendor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPurchaseOrders")
    private CCurrency currency;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPurchaseOrders")
    private CWarehouse warehouse;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPurchaseOrders")
    private CCostCenter costCenter;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public MPurchaseOrder documentAction(String documentAction) {
        this.documentAction = documentAction;
        return this;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public MPurchaseOrder documentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isIsApproved() {
        return isApproved;
    }

    public MPurchaseOrder isApproved(Boolean isApproved) {
        this.isApproved = isApproved;
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean isIsProcessed() {
        return isProcessed;
    }

    public MPurchaseOrder isProcessed(Boolean isProcessed) {
        this.isProcessed = isProcessed;
        return this;
    }

    public void setIsProcessed(Boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    public Boolean isTax() {
        return tax;
    }

    public MPurchaseOrder tax(Boolean tax) {
        this.tax = tax;
        return this;
    }

    public void setTax(Boolean tax) {
        this.tax = tax;
    }

    public LocalDate getDocumentDate() {
        return documentDate;
    }

    public MPurchaseOrder documentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
        return this;
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public LocalDate getDateRequired() {
        return dateRequired;
    }

    public MPurchaseOrder dateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
        return this;
    }

    public void setDateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
    }

    public String getDescription() {
        return description;
    }

    public MPurchaseOrder description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getUid() {
        return uid;
    }

    public MPurchaseOrder uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MPurchaseOrder active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MPurchaseOrder adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CDocumentType getDocumentType() {
        return documentType;
    }

    public MPurchaseOrder documentType(CDocumentType cDocumentType) {
        this.documentType = cDocumentType;
        return this;
    }

    public void setDocumentType(CDocumentType cDocumentType) {
        this.documentType = cDocumentType;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MPurchaseOrder vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public CCurrency getCurrency() {
        return currency;
    }

    public MPurchaseOrder currency(CCurrency cCurrency) {
        this.currency = cCurrency;
        return this;
    }

    public void setCurrency(CCurrency cCurrency) {
        this.currency = cCurrency;
    }

    public CWarehouse getWarehouse() {
        return warehouse;
    }

    public MPurchaseOrder warehouse(CWarehouse cWarehouse) {
        this.warehouse = cWarehouse;
        return this;
    }

    public void setWarehouse(CWarehouse cWarehouse) {
        this.warehouse = cWarehouse;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public MPurchaseOrder costCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
        return this;
    }

    public void setCostCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MPurchaseOrder)) {
            return false;
        }
        return id != null && id.equals(((MPurchaseOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPurchaseOrder{" +
            "id=" + getId() +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", isApproved='" + isIsApproved() + "'" +
            ", isProcessed='" + isIsProcessed() + "'" +
            ", tax='" + isTax() + "'" +
            ", documentDate='" + getDocumentDate() + "'" +
            ", dateRequired='" + getDateRequired() + "'" +
            ", description='" + getDescription() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

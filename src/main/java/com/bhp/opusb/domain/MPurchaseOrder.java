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

    @Column(name = "date_trx")
    private LocalDate dateTrx;

    @Size(max = 30)
    @Column(name = "document_no", length = 30)
    private String documentNo;

    @NotNull
    @Size(max = 10)
    @Column(name = "document_action", length = 10, nullable = false)
    private String documentAction;

    @NotNull
    @Size(max = 10)
    @Column(name = "document_status", length = 10, nullable = false)
    private String documentStatus;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "confirmed")
    private Boolean confirmed;

    @Column(name = "grand_total", precision = 21, scale = 2)
    private BigDecimal grandTotal;

    @Column(name = "tax")
    private Boolean tax;

    @Column(name = "date_promised")
    private LocalDate datePromised;

    @Column(name = "date_promised")
    private LocalDate dateDelivered;

    @Column(name = "date_shipped")
    private LocalDate dateShipped;

    @Column(name = "confirmation")
    private String confirmation;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPurchaseOrders")
    private CPaymentTerm paymentTerm;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateTrx() {
        return dateTrx;
    }

    public MPurchaseOrder dateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
        return this;
    }

    public void setDateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public MPurchaseOrder documentNo(String documentNo) {
        this.documentNo = documentNo;
        return this;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
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

    public Boolean isApproved() {
        return approved;
    }

    public MPurchaseOrder approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public MPurchaseOrder processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Boolean isConfirmed() {
        return confirmed;
    }

    public MPurchaseOrder confirmed(Boolean confirmed) {
        this.confirmed = confirmed;
        return this;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public MPurchaseOrder grandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
        return this;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
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

    public LocalDate getDatePromised() {
        return datePromised;
    }

    public MPurchaseOrder datePromised(LocalDate datePromised) {
        this.datePromised = datePromised;
        return this;
    }

    public void setDatePromised(LocalDate datePromised) {
        this.datePromised = datePromised;
    }

    public LocalDate getDateDelivered() {
        return dateDelivered;
    }

    public MPurchaseOrder dateDelivered(LocalDate dateDelivered) {
        this.dateDelivered = dateDelivered;
        return this;
    }

    public void setDateDelivered(LocalDate dateDelivered) {
        this.dateDelivered = dateDelivered;
    }

    public LocalDate getDateShipped() {
        return dateShipped;
    }

    public MPurchaseOrder dateShipped(LocalDate dateShipped) {
        this.dateShipped = dateShipped;
        return this;
    }

    public void setDateShipped(LocalDate dateShipped) {
        this.dateShipped = dateShipped;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public MPurchaseOrder confirmation(String confirmation) {
        this.confirmation = confirmation;
        return this;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
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

    public CPaymentTerm getPaymentTerm() {
        return paymentTerm;
    }

    public MPurchaseOrder paymentTerm(CPaymentTerm cPaymentTerm) {
        this.paymentTerm = cPaymentTerm;
        return this;
    }

    public void setPaymentTerm(CPaymentTerm cPaymentTerm) {
        this.paymentTerm = cPaymentTerm;
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
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", confirmed='" + isConfirmed() + "'" +
            ", grandTotal=" + getGrandTotal() +
            ", tax='" + isTax() + "'" +
            ", datePromised='" + getDatePromised() + "'" +
            ", dateDelivered='" + getDateDelivered() + "'" +
            ", dateShipped='" + getDateShipped() + "'" +
            ", confirmation='" + getConfirmation() + "'" +
            ", description='" + getDescription() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

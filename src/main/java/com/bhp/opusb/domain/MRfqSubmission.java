package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A MRfqSubmission.
 */
@Entity
@Table(name = "m_rfq_submission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MRfqSubmission extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

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

    @Column(name = "date_trx")
    private LocalDate dateTrx;

    @Column(name = "date_required")
    private LocalDate dateRequired;

    @Column(name = "date_submitted")
    private LocalDate dateSubmitted;

    @Column(name = "submission_grand_total", precision = 21, scale = 2)
    private BigDecimal submissionGrandTotal;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqSubmissions")
    private MRfq quotation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqSubmissions")
    private MQuoteSupplier quoteSupplier;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqSubmissions")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqSubmissions")
    private CBusinessCategory businessClassification;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqSubmissions")
    private CBusinessCategory businessCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqSubmissions")
    private CCurrency currency;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqSubmissions")
    private CWarehouse warehouse;

    @ManyToOne
    @JsonIgnoreProperties("mRfqSubmissions")
    private CCostCenter costCenter;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public MRfqSubmission uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MRfqSubmission active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public MRfqSubmission documentAction(String documentAction) {
        this.documentAction = documentAction;
        return this;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public MRfqSubmission documentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public MRfqSubmission approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public MRfqSubmission processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public LocalDate getDateTrx() {
        return dateTrx;
    }

    public MRfqSubmission dateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
        return this;
    }

    public void setDateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
    }

    public LocalDate getDateRequired() {
        return dateRequired;
    }

    public MRfqSubmission dateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
        return this;
    }

    public void setDateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
    }

    public LocalDate getDateSubmitted() {
        return dateSubmitted;
    }

    public MRfqSubmission dateSubmitted(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
        return this;
    }

    public void setDateSubmitted(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public BigDecimal getSubmissionGrandTotal() {
        return submissionGrandTotal;
    }

    public MRfqSubmission submissionGrandTotal(BigDecimal submissionGrandTotal) {
        this.submissionGrandTotal = submissionGrandTotal;
        return this;
    }

    public void setSubmissionGrandTotal(BigDecimal submissionGrandTotal) {
        this.submissionGrandTotal = submissionGrandTotal;
    }

    public MRfq getQuotation() {
        return quotation;
    }

    public MRfqSubmission quotation(MRfq mRfq) {
        this.quotation = mRfq;
        return this;
    }

    public void setQuotation(MRfq mRfq) {
        this.quotation = mRfq;
    }

    public MQuoteSupplier getQuoteSupplier() {
        return quoteSupplier;
    }

    public MRfqSubmission quoteSupplier(MQuoteSupplier mQuoteSupplier) {
        this.quoteSupplier = mQuoteSupplier;
        return this;
    }

    public void setQuoteSupplier(MQuoteSupplier mQuoteSupplier) {
        this.quoteSupplier = mQuoteSupplier;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MRfqSubmission adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CBusinessCategory getBusinessClassification() {
        return businessClassification;
    }

    public MRfqSubmission businessClassification(CBusinessCategory cBusinessCategory) {
        this.businessClassification = cBusinessCategory;
        return this;
    }

    public void setBusinessClassification(CBusinessCategory cBusinessCategory) {
        this.businessClassification = cBusinessCategory;
    }

    public CBusinessCategory getBusinessCategory() {
        return businessCategory;
    }

    public MRfqSubmission businessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
        return this;
    }

    public void setBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
    }

    public CCurrency getCurrency() {
        return currency;
    }

    public MRfqSubmission currency(CCurrency cCurrency) {
        this.currency = cCurrency;
        return this;
    }

    public void setCurrency(CCurrency cCurrency) {
        this.currency = cCurrency;
    }

    public CWarehouse getWarehouse() {
        return warehouse;
    }

    public MRfqSubmission warehouse(CWarehouse cWarehouse) {
        this.warehouse = cWarehouse;
        return this;
    }

    public void setWarehouse(CWarehouse cWarehouse) {
        this.warehouse = cWarehouse;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public MRfqSubmission costCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
        return this;
    }

    public void setCostCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MRfqSubmission)) {
            return false;
        }
        return id != null && id.equals(((MRfqSubmission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MRfqSubmission{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", dateRequired='" + getDateRequired() + "'" +
            ", dateSubmitted='" + getDateSubmitted() + "'" +
            ", submissionGrandTotal=" + getSubmissionGrandTotal() +
            "}";
    }

    @PrePersist
    private void prePersist(){
        this.uid = UUID.randomUUID();
        this.active = true;
        this.documentAction = "SMT";
        this.documentStatus = "DRF";
        this.approved = false;
        this.processed = false;
    }
}

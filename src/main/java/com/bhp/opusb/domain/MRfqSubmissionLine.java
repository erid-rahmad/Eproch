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
 * A MRfqSubmissionLine.
 */
@Entity
@Table(name = "m_rfq_submission_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MRfqSubmissionLine extends AbstractAuditingEntity {

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

    @Column(name = "release_qty")
    private Integer releaseQty;

    @Column(name = "submission_price", precision = 21, scale = 2)
    private BigDecimal submissionPrice;

    @Column(name = "total_submission_price", precision = 21, scale = 2)
    private BigDecimal totalSubmissionPrice;

    @Column(name = "remark")
    private String remark;

    @Column(name = "date_trx")
    private LocalDate dateTrx;

    @Column(name = "date_required")
    private LocalDate dateRequired;

    @Column(name = "date_submitted")
    private LocalDate dateSubmitted;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqSubmissionLines")
    private MRfqSubmission submission;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqSubmissionLines")
    private MRfqLine quotationLine;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqSubmissionLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqSubmissionLines")
    private CProduct product;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqSubmissionLines")
    private CUnitOfMeasure uom;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqSubmissionLines")
    private CBusinessCategory businessCategory;

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

    public MRfqSubmissionLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MRfqSubmissionLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public MRfqSubmissionLine documentAction(String documentAction) {
        this.documentAction = documentAction;
        return this;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public MRfqSubmissionLine documentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public MRfqSubmissionLine approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public MRfqSubmissionLine processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Integer getReleaseQty() {
        return releaseQty;
    }

    public MRfqSubmissionLine releaseQty(Integer releaseQty) {
        this.releaseQty = releaseQty;
        return this;
    }

    public void setReleaseQty(Integer releaseQty) {
        this.releaseQty = releaseQty;
    }

    public BigDecimal getSubmissionPrice() {
        return submissionPrice;
    }

    public MRfqSubmissionLine submissionPrice(BigDecimal submissionPrice) {
        this.submissionPrice = submissionPrice;
        return this;
    }

    public void setSubmissionPrice(BigDecimal submissionPrice) {
        this.submissionPrice = submissionPrice;
    }

    public BigDecimal getTotalSubmissionPrice() {
        return totalSubmissionPrice;
    }

    public MRfqSubmissionLine totalSubmissionPrice(BigDecimal totalSubmissionPrice) {
        this.totalSubmissionPrice = totalSubmissionPrice;
        return this;
    }

    public void setTotalSubmissionPrice(BigDecimal totalSubmissionPrice) {
        this.totalSubmissionPrice = totalSubmissionPrice;
    }

    public String getRemark() {
        return remark;
    }

    public MRfqSubmissionLine remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getDateTrx() {
        return dateTrx;
    }

    public MRfqSubmissionLine dateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
        return this;
    }

    public void setDateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
    }

    public LocalDate getDateRequired() {
        return dateRequired;
    }

    public MRfqSubmissionLine dateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
        return this;
    }

    public void setDateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
    }

    public LocalDate getDateSubmitted() {
        return dateSubmitted;
    }

    public MRfqSubmissionLine dateSubmitted(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
        return this;
    }

    public void setDateSubmitted(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public MRfqSubmission getSubmission() {
        return submission;
    }

    public MRfqSubmissionLine submission(MRfqSubmission mRfqSubmission) {
        this.submission = mRfqSubmission;
        return this;
    }

    public void setSubmission(MRfqSubmission mRfqSubmission) {
        this.submission = mRfqSubmission;
    }

    public MRfqLine getQuotationLine() {
        return quotationLine;
    }

    public MRfqSubmissionLine quotationLine(MRfqLine mRfqLine) {
        this.quotationLine = mRfqLine;
        return this;
    }

    public void setQuotationLine(MRfqLine mRfqLine) {
        this.quotationLine = mRfqLine;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MRfqSubmissionLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CProduct getProduct() {
        return product;
    }

    public MRfqSubmissionLine product(CProduct cProduct) {
        this.product = cProduct;
        return this;
    }

    public void setProduct(CProduct cProduct) {
        this.product = cProduct;
    }

    public CUnitOfMeasure getUom() {
        return uom;
    }

    public MRfqSubmissionLine uom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
        return this;
    }

    public void setUom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
    }

    public CBusinessCategory getBusinessCategory() {
        return businessCategory;
    }

    public MRfqSubmissionLine businessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
        return this;
    }

    public void setBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MRfqSubmissionLine)) {
            return false;
        }
        return id != null && id.equals(((MRfqSubmissionLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MRfqSubmissionLine{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", releaseQty=" + getReleaseQty() +
            ", submissionPrice=" + getSubmissionPrice() +
            ", totalSubmissionPrice=" + getTotalSubmissionPrice() +
            ", remark='" + getRemark() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", dateRequired='" + getDateRequired() + "'" +
            ", dateSubmitted='" + getDateSubmitted() + "'" +
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

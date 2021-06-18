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
 * A MRfqLine.
 */
@Entity
@Table(name = "m_rfq_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MRfqLine extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "line_no")
    private Integer lineNo;

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

    @Column(name = "document_date")
    private LocalDate documentDate;

    @Column(name = "date_required")
    private LocalDate dateRequired;

    @Column(name = "remark")
    private String remark;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqLines")
    private MRfq quotation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqLines")
    private CProduct product;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqLines")
    private CUnitOfMeasure uom;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqLines")
    private CBusinessCategory businessCategory;

    @PrePersist
    public void assignUid(){
        this.uid = UUID.randomUUID();
        this.active = true;
        this.documentDate = LocalDate.now();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public MRfqLine lineNo(Integer lineNo) {
        this.lineNo = lineNo;
        return this;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public UUID getUid() {
        return uid;
    }

    public MRfqLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MRfqLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public MRfqLine documentAction(String documentAction) {
        this.documentAction = documentAction;
        return this;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public MRfqLine documentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public MRfqLine approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public MRfqLine processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Integer getReleaseQty() {
        return releaseQty;
    }

    public MRfqLine releaseQty(Integer releaseQty) {
        this.releaseQty = releaseQty;
        return this;
    }

    public void setReleaseQty(Integer releaseQty) {
        this.releaseQty = releaseQty;
    }

    public LocalDate getDocumentDate() {
        return documentDate;
    }

    public MRfqLine documentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
        return this;
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public LocalDate getDateRequired() {
        return dateRequired;
    }

    public MRfqLine dateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
        return this;
    }

    public void setDateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
    }

    public String getRemark() {
        return remark;
    }

    public MRfqLine remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public MRfq getQuotation() {
        return quotation;
    }

    public MRfqLine quotation(MRfq mRfq) {
        this.quotation = mRfq;
        return this;
    }

    public void setQuotation(MRfq mRfq) {
        this.quotation = mRfq;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MRfqLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CProduct getProduct() {
        return product;
    }

    public MRfqLine product(CProduct cProduct) {
        this.product = cProduct;
        return this;
    }

    public void setProduct(CProduct cProduct) {
        this.product = cProduct;
    }

    public CUnitOfMeasure getUom() {
        return uom;
    }

    public MRfqLine uom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
        return this;
    }

    public void setUom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
    }

    public CBusinessCategory getBusinessCategory() {
        return businessCategory;
    }

    public MRfqLine businessCategory(CBusinessCategory cBusinessCategory) {
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
        if (!(o instanceof MRfqLine)) {
            return false;
        }
        return id != null && id.equals(((MRfqLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MRfqLine{" +
            "id=" + getId() +
            ", lineNo=" + getLineNo() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", releaseQty=" + getReleaseQty() +
            ", documentDate='" + getDocumentDate() + "'" +
            ", dateRequired='" + getDateRequired() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}

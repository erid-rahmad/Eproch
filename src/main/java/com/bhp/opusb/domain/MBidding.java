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

import com.bhp.opusb.workflow.WorkflowDoc;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

/**
 * A MBidding.
 */
@Entity
@Table(name = "m_bidding")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBidding extends AbstractAuditingEntity implements WorkflowDoc{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "vendor_selection")
    private String vendorSelection;

    @Column(name = "ceiling_price", precision = 21, scale = 2)
    private BigDecimal ceilingPrice;

    @Column(name = "estimated_price", precision = 21, scale = 2)
    private BigDecimal estimatedPrice;

    @NotNull
    @Column(name = "bidding_status", nullable = false)
    private String biddingStatus;

    @Formula("(SELECT COUNT(i.vendor_id) " +
        "FROM m_bidding_invitation i " +
        "WHERE i.bidding_id = id " +
        "AND i.invitation_status = 'R')")
    private Integer joinedVendorCount;

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

    @Column(name = "date_approve")
    private LocalDate dateApprove;

    @Column(name = "date_reject")
    private LocalDate dateReject;

    @Column(name = "rejected_reason")
    private String rejectedReason;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddings")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddings")
    private CCostCenter costCenter;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddings")
    private CCurrency currency;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddings")
    private CDocumentType documentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddings")
    private MRequisition requisition;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddings")
    private CDocumentType referenceType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddings")
    private CBiddingType biddingType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddings")
    private CEventType eventType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddings")
    private AdUser adUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MBidding name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendorSelection() {
        return vendorSelection;
    }

    public MBidding vendorSelection(String vendorSelection) {
        this.vendorSelection = vendorSelection;
        return this;
    }

    public void setVendorSelection(String vendorSelection) {
        this.vendorSelection = vendorSelection;
    }

    public BigDecimal getCeilingPrice() {
        return ceilingPrice;
    }

    public MBidding ceilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
        return this;
    }

    public void setCeilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public BigDecimal getEstimatedPrice() {
        return estimatedPrice;
    }

    public MBidding estimatedPrice(BigDecimal estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
        return this;
    }

    public void setEstimatedPrice(BigDecimal estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getBiddingStatus() {
        return biddingStatus;
    }

    public MBidding biddingStatus(String biddingStatus) {
        this.biddingStatus = biddingStatus;
        return this;
    }

    public void setBiddingStatus(String biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public Integer getJoinedVendorCount() {
        return joinedVendorCount;
    }

    public MBidding joinedVendorCount(Integer joinedVendorCount) {
        this.joinedVendorCount = joinedVendorCount;
        return this;
    }

    public void setJoinedVendorCount(Integer joinedVendorCount) {
        this.joinedVendorCount = joinedVendorCount;
    }

    public LocalDate getDateTrx() {
        return dateTrx;
    }

    public MBidding dateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
        return this;
    }

    public void setDateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public MBidding documentNo(String documentNo) {
        this.documentNo = documentNo;
        return this;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public MBidding documentAction(String documentAction) {
        this.documentAction = documentAction;
        return this;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public MBidding documentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public MBidding approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public MBidding processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public LocalDate getDateApprove() {
        return dateApprove;
    }

    public MBidding dateApprove(LocalDate dateApprove) {
        this.dateApprove = dateApprove;
        return this;
    }

    public void setDateApprove(LocalDate dateApprove) {
        this.dateApprove = dateApprove;
    }

    public LocalDate getDateReject() {
        return dateReject;
    }

    public MBidding dateReject(LocalDate dateReject) {
        this.dateReject = dateReject;
        return this;
    }

    public void setDateReject(LocalDate dateReject) {
        this.dateReject = dateReject;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public MBidding rejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
        return this;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public UUID getUid() {
        return uid;
    }

    public MBidding uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBidding active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBidding adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public MBidding costCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
        return this;
    }

    public void setCostCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
    }

    public CCurrency getCurrency() {
        return currency;
    }

    public MBidding currency(CCurrency cCurrency) {
        this.currency = cCurrency;
        return this;
    }

    public void setCurrency(CCurrency cCurrency) {
        this.currency = cCurrency;
    }

    public CDocumentType getDocumentType() {
        return documentType;
    }

    public MBidding documentType(CDocumentType cDocumentType) {
        this.documentType = cDocumentType;
        return this;
    }

    public void setDocumentType(CDocumentType cDocumentType) {
        this.documentType = cDocumentType;
    }

    public MRequisition getRequisition() {
        return requisition;
    }

    public MBidding requisition(MRequisition mRequisition) {
        this.requisition = mRequisition;
        return this;
    }

    public void setRequisition(MRequisition mRequisition) {
        this.requisition = mRequisition;
    }

    public CDocumentType getReferenceType() {
        return referenceType;
    }

    public MBidding referenceType(CDocumentType cDocumentType) {
        this.referenceType = cDocumentType;
        return this;
    }

    public void setReferenceType(CDocumentType cDocumentType) {
        this.referenceType = cDocumentType;
    }

    public CBiddingType getBiddingType() {
        return biddingType;
    }

    public MBidding biddingType(CBiddingType cBiddingType) {
        this.biddingType = cBiddingType;
        return this;
    }

    public void setBiddingType(CBiddingType cBiddingType) {
        this.biddingType = cBiddingType;
    }

    public CEventType getEventType() {
        return eventType;
    }

    public MBidding eventType(CEventType cEventType) {
        this.eventType = cEventType;
        return this;
    }

    public void setEventType(CEventType cEventType) {
        this.eventType = cEventType;
    }

    public AdUser getAdUser() {
        return adUser;
    }

    public MBidding adUser(AdUser adUser) {
        this.adUser = adUser;
        return this;
    }

    public void setAdUser(AdUser adUser) {
        this.adUser = adUser;
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
        if (!(o instanceof MBidding)) {
            return false;
        }
        return id != null && id.equals(((MBidding) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBidding{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", vendorSelection='" + getVendorSelection() + "'" +
            ", ceilingPrice=" + getCeilingPrice() +
            ", estimatedPrice=" + getEstimatedPrice() +
            ", biddingStatus='" + getBiddingStatus() + "'" +
            ", joinedVendorCount=" + getJoinedVendorCount() +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", dateApprove='" + getDateApprove() + "'" +
            ", dateReject='" + getDateReject() + "'" +
            ", rejectedReason='" + getRejectedReason() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }

    @Override
    public void setProcessing(Boolean isProcessing) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Boolean isProcessing() {
        return false;
    }

    @Override
    public String getSummary() {
        return null;
    }

    @Override
    public BigDecimal getApprovalAmount() {
        return new BigDecimal("0");
    }
}

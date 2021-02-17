package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A MBidding.
 */
@Entity
@Table(name = "m_bidding")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBidding extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "bidding_no", nullable = false, unique = true)
    private String biddingNo;

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

    @Column(name = "date_reject")
    private LocalDate dateReject;

    @Column(name = "date_approve")
    private LocalDate dateApprove;

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
    private MRequisition requisition;

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

    public String getBiddingNo() {
        return biddingNo;
    }

    public MBidding biddingNo(String biddingNo) {
        this.biddingNo = biddingNo;
        return this;
    }

    public void setBiddingNo(String biddingNo) {
        this.biddingNo = biddingNo;
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
            ", biddingNo='" + getBiddingNo() + "'" +
            ", name='" + getName() + "'" +
            ", vendorSelection='" + getVendorSelection() + "'" +
            ", ceilingPrice=" + getCeilingPrice() +
            ", estimatedPrice=" + getEstimatedPrice() +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", dateReject='" + getDateReject() + "'" +
            ", dateApprove='" + getDateApprove() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

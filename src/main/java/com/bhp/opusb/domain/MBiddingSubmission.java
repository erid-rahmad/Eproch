package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A MBiddingSubmission.
 */
@Entity
@Table(name = "m_bidding_submission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MBiddingSubmission extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "join_bidding")
    private String joinBidding;

    @NotNull
    @Column(name = "proposed_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal proposedPrice;

    @NotNull
    @Column(name = "ceiling_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal ceilingPrice;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "mBiddingSubmission")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MBiddingSubmissionLine> mBiddingSubmissionLines = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mBiddingSubmissions", allowSetters = true)
    private MBidding bidding;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mBiddingSubmissions", allowSetters = true)
    private MBiddingSchedule biddingSchedule;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mBiddingSubmissions", allowSetters = true)
    private CVendor vendor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mBiddingSubmissions", allowSetters = true)
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJoinBidding() {
        return joinBidding;
    }

    public MBiddingSubmission joinBidding(String joinBidding) {
        this.joinBidding = joinBidding;
        return this;
    }

    public void setJoinBidding(String joinBidding) {
        this.joinBidding = joinBidding;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public MBiddingSubmission proposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
        return this;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public BigDecimal getCeilingPrice() {
        return ceilingPrice;
    }

    public MBiddingSubmission ceilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
        return this;
    }

    public void setCeilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public UUID getUid() {
        return uid;
    }

    public MBiddingSubmission uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingSubmission active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<MBiddingSubmissionLine> getMBiddingSubmissionLines() {
        return mBiddingSubmissionLines;
    }

    public MBiddingSubmission mBiddingSubmissionLines(Set<MBiddingSubmissionLine> mBiddingSubmissionLines) {
        this.mBiddingSubmissionLines = mBiddingSubmissionLines;
        return this;
    }

    public MBiddingSubmission addMBiddingSubmissionLine(MBiddingSubmissionLine mBiddingSubmissionLine) {
        this.mBiddingSubmissionLines.add(mBiddingSubmissionLine);
        mBiddingSubmissionLine.setMBiddingSubmission(this);
        return this;
    }

    public MBiddingSubmission removeMBiddingSubmissionLine(MBiddingSubmissionLine mBiddingSubmissionLine) {
        this.mBiddingSubmissionLines.remove(mBiddingSubmissionLine);
        mBiddingSubmissionLine.setMBiddingSubmission(null);
        return this;
    }

    public void setMBiddingSubmissionLines(Set<MBiddingSubmissionLine> mBiddingSubmissionLines) {
        this.mBiddingSubmissionLines = mBiddingSubmissionLines;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MBiddingSubmission bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public MBiddingSchedule getBiddingSchedule() {
        return biddingSchedule;
    }

    public MBiddingSubmission biddingSchedule(MBiddingSchedule mBiddingSchedule) {
        this.biddingSchedule = mBiddingSchedule;
        return this;
    }

    public void setBiddingSchedule(MBiddingSchedule mBiddingSchedule) {
        this.biddingSchedule = mBiddingSchedule;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MBiddingSubmission vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingSubmission adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingSubmission)) {
            return false;
        }
        return id != null && id.equals(((MBiddingSubmission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MBiddingSubmission{" +
            "id=" + getId() +
            ", joinBidding='" + getJoinBidding() + "'" +
            ", proposedPrice=" + getProposedPrice() +
            ", ceilingPrice=" + getCeilingPrice() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

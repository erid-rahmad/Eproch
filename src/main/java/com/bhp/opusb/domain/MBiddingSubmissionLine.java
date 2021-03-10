package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A MBiddingSubmissionLine.
 */
@Entity
@Table(name = "m_bidding_submission_line")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MBiddingSubmissionLine extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "proposed_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal proposedPrice;

    @NotNull
    @Column(name = "total_price_submission", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalPriceSubmission;

    @NotNull
    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "mBiddingSubmissionLine")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MSubmissionSubItem> mSubmissionSubItems = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mBiddingSubmissionLines", allowSetters = true)
    private MBiddingLine biddingLine;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mBiddingSubmissionLines", allowSetters = true)
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mBiddingSubmissionLines", allowSetters = true)
    private MBiddingSubmission mBiddingSubmission;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public MBiddingSubmissionLine proposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
        return this;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public BigDecimal getTotalPriceSubmission() {
        return totalPriceSubmission;
    }

    public MBiddingSubmissionLine totalPriceSubmission(BigDecimal totalPriceSubmission) {
        this.totalPriceSubmission = totalPriceSubmission;
        return this;
    }

    public void setTotalPriceSubmission(BigDecimal totalPriceSubmission) {
        this.totalPriceSubmission = totalPriceSubmission;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public MBiddingSubmissionLine deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public UUID getUid() {
        return uid;
    }

    public MBiddingSubmissionLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingSubmissionLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<MSubmissionSubItem> getMSubmissionSubItems() {
        return mSubmissionSubItems;
    }

    public MBiddingSubmissionLine mSubmissionSubItems(Set<MSubmissionSubItem> mSubmissionSubItems) {
        this.mSubmissionSubItems = mSubmissionSubItems;
        return this;
    }

    public MBiddingSubmissionLine addMSubmissionSubItem(MSubmissionSubItem mSubmissionSubItem) {
        this.mSubmissionSubItems.add(mSubmissionSubItem);
        mSubmissionSubItem.setMBiddingSubmissionLine(this);
        return this;
    }

    public MBiddingSubmissionLine removeMSubmissionSubItem(MSubmissionSubItem mSubmissionSubItem) {
        this.mSubmissionSubItems.remove(mSubmissionSubItem);
        mSubmissionSubItem.setMBiddingSubmissionLine(null);
        return this;
    }

    public void setMSubmissionSubItems(Set<MSubmissionSubItem> mSubmissionSubItems) {
        this.mSubmissionSubItems = mSubmissionSubItems;
    }

    public MBiddingLine getBiddingLine() {
        return biddingLine;
    }

    public MBiddingSubmissionLine biddingLine(MBiddingLine mBiddingLine) {
        this.biddingLine = mBiddingLine;
        return this;
    }

    public void setBiddingLine(MBiddingLine mBiddingLine) {
        this.biddingLine = mBiddingLine;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingSubmissionLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBiddingSubmission getMBiddingSubmission() {
        return mBiddingSubmission;
    }

    public MBiddingSubmissionLine mBiddingSubmission(MBiddingSubmission mBiddingSubmission) {
        this.mBiddingSubmission = mBiddingSubmission;
        return this;
    }

    public void setMBiddingSubmission(MBiddingSubmission mBiddingSubmission) {
        this.mBiddingSubmission = mBiddingSubmission;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingSubmissionLine)) {
            return false;
        }
        return id != null && id.equals(((MBiddingSubmissionLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MBiddingSubmissionLine{" +
            "id=" + getId() +
            ", proposedPrice=" + getProposedPrice() +
            ", totalPriceSubmission=" + getTotalPriceSubmission() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

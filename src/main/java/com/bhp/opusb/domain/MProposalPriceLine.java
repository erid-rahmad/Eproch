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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A MProposalPriceLine.
 */
@Entity
@Table(name = "m_proposal_price_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MProposalPriceLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "document")
    private Boolean document;

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

    @OneToMany(mappedBy = "proposalPriceLine")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MProposalPriceSubItem> mProposalPriceSubItems = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mProposalPriceLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mProposalPriceLines")
    private MProposalPrice proposalPrice;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mProposalPriceLines")
    private MBiddingLine biddingLine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDocument() {
        return document;
    }

    public MProposalPriceLine document(Boolean document) {
        this.document = document;
        return this;
    }

    public void setDocument(Boolean document) {
        this.document = document;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public MProposalPriceLine proposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
        return this;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public BigDecimal getTotalPriceSubmission() {
        return totalPriceSubmission;
    }

    public MProposalPriceLine totalPriceSubmission(BigDecimal totalPriceSubmission) {
        this.totalPriceSubmission = totalPriceSubmission;
        return this;
    }

    public void setTotalPriceSubmission(BigDecimal totalPriceSubmission) {
        this.totalPriceSubmission = totalPriceSubmission;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public MProposalPriceLine deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public UUID getUid() {
        return uid;
    }

    public MProposalPriceLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MProposalPriceLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<MProposalPriceSubItem> getMProposalPriceSubItems() {
        return mProposalPriceSubItems;
    }

    public MProposalPriceLine mProposalPriceSubItems(Set<MProposalPriceSubItem> mProposalPriceSubItems) {
        this.mProposalPriceSubItems = mProposalPriceSubItems;
        return this;
    }

    public MProposalPriceLine addMProposalPriceSubItem(MProposalPriceSubItem mProposalPriceSubItem) {
        this.mProposalPriceSubItems.add(mProposalPriceSubItem);
        mProposalPriceSubItem.setProposalPriceLine(this);
        return this;
    }

    public MProposalPriceLine removeMProposalPriceSubItem(MProposalPriceSubItem mProposalPriceSubItem) {
        this.mProposalPriceSubItems.remove(mProposalPriceSubItem);
        mProposalPriceSubItem.setProposalPriceLine(null);
        return this;
    }

    public void setMProposalPriceSubItems(Set<MProposalPriceSubItem> mProposalPriceSubItems) {
        this.mProposalPriceSubItems = mProposalPriceSubItems;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MProposalPriceLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MProposalPrice getProposalPrice() {
        return proposalPrice;
    }

    public MProposalPriceLine proposalPrice(MProposalPrice mProposalPrice) {
        this.proposalPrice = mProposalPrice;
        return this;
    }

    public void setProposalPrice(MProposalPrice mProposalPrice) {
        this.proposalPrice = mProposalPrice;
    }

    public MBiddingLine getBiddingLine() {
        return biddingLine;
    }

    public MProposalPriceLine biddingLine(MBiddingLine mBiddingLine) {
        this.biddingLine = mBiddingLine;
        return this;
    }

    public void setBiddingLine(MBiddingLine mBiddingLine) {
        this.biddingLine = mBiddingLine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MProposalPriceLine)) {
            return false;
        }
        return id != null && id.equals(((MProposalPriceLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MProposalPriceLine{" +
            "id=" + getId() +
            ", document='" + isDocument() + "'" +
            ", proposedPrice=" + getProposedPrice() +
            ", totalPriceSubmission=" + getTotalPriceSubmission() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

package com.bhp.opusb.domain;

import java.math.BigDecimal;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MProposalPriceSubItem.
 */
@Entity
@Table(name = "m_proposal_price_sub_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MProposalPriceSubItem extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "proposed_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal proposedPrice;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mProposalPriceSubItems")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mProposalPriceSubItems")
    private MBiddingSubItemLine biddingSubItemLine;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mProposalPriceSubItems")
    private MProposalPriceLine proposalPriceLine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public MProposalPriceSubItem proposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
        return this;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public UUID getUid() {
        return uid;
    }

    public MProposalPriceSubItem uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MProposalPriceSubItem active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MProposalPriceSubItem adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBiddingSubItemLine getBiddingSubItemLine() {
        return biddingSubItemLine;
    }

    public MProposalPriceSubItem biddingSubItemLine(MBiddingSubItemLine mBiddingSubItemLine) {
        this.biddingSubItemLine = mBiddingSubItemLine;
        return this;
    }

    public void setBiddingSubItemLine(MBiddingSubItemLine mBiddingSubItemLine) {
        this.biddingSubItemLine = mBiddingSubItemLine;
    }

    public MProposalPriceLine getProposalPriceLine() {
        return proposalPriceLine;
    }

    public MProposalPriceSubItem proposalPriceLine(MProposalPriceLine mProposalPriceLine) {
        this.proposalPriceLine = mProposalPriceLine;
        return this;
    }

    public void setProposalPriceLine(MProposalPriceLine mProposalPriceLine) {
        this.proposalPriceLine = mProposalPriceLine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void prePersist() {
        uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MProposalPriceSubItem)) {
            return false;
        }
        return id != null && id.equals(((MProposalPriceSubItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MProposalPriceSubItem{" +
            "id=" + getId() +
            ", proposedPrice=" + getProposedPrice() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * A MBidNegoPrice.
 */
@Entity
@Table(name = "m_bid_nego_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBidNegoPrice extends AbstractAuditingEntity implements Serializable {

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
    @Column(name = "negotiation_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal negotiationPrice;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBidNegoPrices")
    private MBidding bidding;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBidNegoPrices")
    private MVendorInvitation vendorInvitation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBidNegoPrices")
    private MProposalPrice priceProposal;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBidNegoPrices")
    private MBiddingNegotiationLine negotiationLine;

    @PrePersist
    public void assignUUID(){
        this.uid = UUID.randomUUID();
    }

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

    public MBidNegoPrice uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBidNegoPrice active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getNegotiationPrice() {
        return negotiationPrice;
    }

    public MBidNegoPrice negotiationPrice(BigDecimal negotiationPrice) {
        this.negotiationPrice = negotiationPrice;
        return this;
    }

    public void setNegotiationPrice(BigDecimal negotiationPrice) {
        this.negotiationPrice = negotiationPrice;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MBidNegoPrice bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public MVendorInvitation getVendorInvitation() {
        return vendorInvitation;
    }

    public MBidNegoPrice vendorInvitation(MVendorInvitation mVendorInvitation) {
        this.vendorInvitation = mVendorInvitation;
        return this;
    }

    public void setVendorInvitation(MVendorInvitation mVendorInvitation) {
        this.vendorInvitation = mVendorInvitation;
    }

    public MProposalPrice getPriceProposal() {
        return priceProposal;
    }

    public MBidNegoPrice priceProposal(MProposalPrice mProposalPrice) {
        this.priceProposal = mProposalPrice;
        return this;
    }

    public void setPriceProposal(MProposalPrice mProposalPrice) {
        this.priceProposal = mProposalPrice;
    }

    public MBiddingNegotiationLine getNegotiationLine() {
        return negotiationLine;
    }

    public MBidNegoPrice negotiationLine(MBiddingNegotiationLine mBiddingNegotiationLine) {
        this.negotiationLine = mBiddingNegotiationLine;
        return this;
    }

    public void setNegotiationLine(MBiddingNegotiationLine mBiddingNegotiationLine) {
        this.negotiationLine = mBiddingNegotiationLine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBidNegoPrice)) {
            return false;
        }
        return id != null && id.equals(((MBidNegoPrice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBidNegoPrice{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", negotiationPrice=" + getNegotiationPrice() +
            "}";
    }
}

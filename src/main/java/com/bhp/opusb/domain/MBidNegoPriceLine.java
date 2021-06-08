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
 * A MBidNegoPriceLine.
 */
@Entity
@Table(name = "m_bid_nego_price_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBidNegoPriceLine extends AbstractAuditingEntity implements Serializable {

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
    @Column(name = "price_negotiation", precision = 21, scale = 2, nullable = false)
    private BigDecimal priceNegotiation;

    @NotNull
    @Column(name = "total_negotiation_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalNegotiationPrice;

    @NotNull
    @Column(name = "negotiation_percentage", precision = 21, scale = 2, nullable = false)
    private BigDecimal negotiationPercentage;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBidNegoPriceLines")
    private MBidNegoPrice bidNegoPrice;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBidNegoPriceLines")
    private MBiddingLine biddingLine;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBidNegoPriceLines")
    private MProposalPriceLine proposalLine;

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

    public MBidNegoPriceLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBidNegoPriceLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getPriceNegotiation() {
        return priceNegotiation;
    }

    public MBidNegoPriceLine priceNegotiation(BigDecimal priceNegotiation) {
        this.priceNegotiation = priceNegotiation;
        return this;
    }

    public void setPriceNegotiation(BigDecimal priceNegotiation) {
        this.priceNegotiation = priceNegotiation;
    }

    public BigDecimal getTotalNegotiationPrice() {
        return totalNegotiationPrice;
    }

    public MBidNegoPriceLine totalNegotiationPrice(BigDecimal totalNegotiationPrice) {
        this.totalNegotiationPrice = totalNegotiationPrice;
        return this;
    }

    public void setTotalNegotiationPrice(BigDecimal totalNegotiationPrice) {
        this.totalNegotiationPrice = totalNegotiationPrice;
    }

    public BigDecimal getNegotiationPercentage() {
        return negotiationPercentage;
    }

    public MBidNegoPriceLine negotiationPercentage(BigDecimal negotiationPercentage) {
        this.negotiationPercentage = negotiationPercentage;
        return this;
    }

    public void setNegotiationPercentage(BigDecimal negotiationPercentage) {
        this.negotiationPercentage = negotiationPercentage;
    }

    public MBidNegoPrice getBidNegoPrice() {
        return bidNegoPrice;
    }

    public MBidNegoPriceLine bidNegoPrice(MBidNegoPrice mBidNegoPrice) {
        this.bidNegoPrice = mBidNegoPrice;
        return this;
    }

    public void setBidNegoPrice(MBidNegoPrice mBidNegoPrice) {
        this.bidNegoPrice = mBidNegoPrice;
    }

    public MBiddingLine getBiddingLine() {
        return biddingLine;
    }

    public MBidNegoPriceLine biddingLine(MBiddingLine mBiddingLine) {
        this.biddingLine = mBiddingLine;
        return this;
    }

    public void setBiddingLine(MBiddingLine mBiddingLine) {
        this.biddingLine = mBiddingLine;
    }

    public MProposalPriceLine getProposalLine() {
        return proposalLine;
    }

    public MBidNegoPriceLine proposalLine(MProposalPriceLine mProposalPriceLine) {
        this.proposalLine = mProposalPriceLine;
        return this;
    }

    public void setProposalLine(MProposalPriceLine mProposalPriceLine) {
        this.proposalLine = mProposalPriceLine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBidNegoPriceLine)) {
            return false;
        }
        return id != null && id.equals(((MBidNegoPriceLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBidNegoPriceLine{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", priceNegotiation=" + getPriceNegotiation() +
            ", totalNegotiationPrice=" + getTotalNegotiationPrice() +
            ", negotiationPercentage=" + getNegotiationPercentage() +
            "}";
    }
}

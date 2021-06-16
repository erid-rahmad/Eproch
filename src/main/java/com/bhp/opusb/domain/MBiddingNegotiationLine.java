package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A MBiddingNegotiationLine.
 */
@Entity
@Table(name = "m_bidding_negotiation_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBiddingNegotiationLine extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "negotiation_status")
    private String negotiationStatus;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingNegotiationLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingNegotiationLines")
    private MBiddingNegotiation negotiation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingNegotiationLines")
    private MBiddingEvalResult biddingEvalResult;

    @Formula("(select mpp.proposed_price from m_proposal_price mpp where mpp.bidding_submission_id = " +
    "(select mber.bidding_submission_id from m_bidding_eval_result mber where mber.id = " +
    "(select mbnl.bidding_eval_result_id from m_bidding_negotiation_line mbnl where mbnl.id = id)))")
    private BigDecimal proposedPrice;

    @Formula("(select mbnp.negotiation_price from m_bid_nego_price mbnp where mbnp.negotiation_line_id = id)")
    private BigDecimal negotiationPrice;

    @Formula("(select mbnp.id from m_bid_nego_price mbnp where mbnp.negotiation_line_id = id)")
    private Long negoPriceId;

    @Formula("(select concat(cl.address_1,' ',cl.address_2,' ',cl.address_3,' ',cl.address_4) from c_location cl where cl.id = ("+
    "select cvl.location_id from c_vendor_location cvl where cvl.vendor_id = ("+
    "select mbs.vendor_id from m_bidding_submission mbs where mbs.id = ("+
    "select mber.bidding_submission_id from m_bidding_eval_result mber where mber.id = ("+
    "select mbnl.bidding_eval_result_id from m_bidding_negotiation_line mbnl where mbnl.id = id))) order by cvl.tax_invoice_address desc limit 1))")
    private String vendorAddress;

    @PrePersist
    public void assignUUID() {
        this.active=true;
        this.uid = UUID.randomUUID();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getNegotiationStatus() {
        return negotiationStatus;
    }

    public MBiddingNegotiationLine negotiationStatus(String negotiationStatus) {
        this.negotiationStatus = negotiationStatus;
        return this;
    }

    public void setNegotiationStatus(String negotiationStatus) {
        this.negotiationStatus = negotiationStatus;
    }

    public UUID getUid() {
        return uid;
    }

    public MBiddingNegotiationLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingNegotiationLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingNegotiationLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBiddingNegotiation getNegotiation() {
        return negotiation;
    }

    public MBiddingNegotiationLine negotiation(MBiddingNegotiation mBiddingNegotiation) {
        this.negotiation = mBiddingNegotiation;
        return this;
    }

    public void setNegotiation(MBiddingNegotiation mBiddingNegotiation) {
        this.negotiation = mBiddingNegotiation;
    }

    public MBiddingEvalResult getBiddingEvalResult() {
        return biddingEvalResult;
    }

    public MBiddingNegotiationLine biddingEvalResult(MBiddingEvalResult mBiddingEvalResult) {
        this.biddingEvalResult = mBiddingEvalResult;
        return this;
    }

    public void setBiddingEvalResult(MBiddingEvalResult mBiddingEvalResult) {
        this.biddingEvalResult = mBiddingEvalResult;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public MBiddingNegotiationLine proposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
        return this;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public BigDecimal getNegotiationPrice() {
        return negotiationPrice;
    }

    public MBiddingNegotiationLine negotiationPrice(BigDecimal negotiationPrice) {
        this.negotiationPrice = negotiationPrice;
        return this;
    }

    public void setNegotiationPrice(BigDecimal negotiationPrice) {
        this.negotiationPrice = negotiationPrice;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingNegotiationLine)) {
            return false;
        }
        return id != null && id.equals(((MBiddingNegotiationLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBiddingNegotiationLine{" +
            "id=" + getId() +
            ", negotiationStatus='" + getNegotiationStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

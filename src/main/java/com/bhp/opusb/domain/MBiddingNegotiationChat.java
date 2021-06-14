package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A MBiddingNegotiationChat.
 */
@Entity
@Table(name = "m_bidding_negotiation_chat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBiddingNegotiationChat extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "vendor_text")
    private String vendorText;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "buyer_text")
    private String buyerText;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingNegotiationChats")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingNegotiationChats")
    private MBiddingNegotiationLine negotiationLine;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingNegotiationChats")
    private MBidding bidding;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingNegotiationChats")
    private CVendor vendor;

    @ManyToOne
    @JsonIgnoreProperties("mBiddingNegotiationChats")
    private CAttachment attachment;

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

    public UUID getUid() {
        return uid;
    }

    public MBiddingNegotiationChat uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingNegotiationChat active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getVendorText() {
        return vendorText;
    }

    public MBiddingNegotiationChat vendorText(String vendorText) {
        this.vendorText = vendorText;
        return this;
    }

    public void setVendorText(String vendorText) {
        this.vendorText = vendorText;
    }

    public String getBuyerText() {
        return buyerText;
    }

    public MBiddingNegotiationChat buyerText(String buyerText) {
        this.buyerText = buyerText;
        return this;
    }

    public void setBuyerText(String buyerText) {
        this.buyerText = buyerText;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingNegotiationChat adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBiddingNegotiationLine getNegotiationLine() {
        return negotiationLine;
    }

    public MBiddingNegotiationChat negotiationLine(MBiddingNegotiationLine mBiddingNegotiationLine) {
        this.negotiationLine = mBiddingNegotiationLine;
        return this;
    }

    public void setNegotiationLine(MBiddingNegotiationLine mBiddingNegotiationLine) {
        this.negotiationLine = mBiddingNegotiationLine;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MBiddingNegotiationChat bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MBiddingNegotiationChat vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public CAttachment getAttachment() {
        return attachment;
    }

    public MBiddingNegotiationChat attachment(CAttachment cAttachment) {
        this.attachment = cAttachment;
        return this;
    }

    public void setAttachment(CAttachment cAttachment) {
        this.attachment = cAttachment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingNegotiationChat)) {
            return false;
        }
        return id != null && id.equals(((MBiddingNegotiationChat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBiddingNegotiationChat{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", vendorText='" + getVendorText() + "'" +
            ", buyerText='" + getBuyerText() + "'" +
            "}";
    }
}

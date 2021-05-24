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
 * A MAuctionItem.
 */
@Entity
@Table(name = "m_auction_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MAuctionItem extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantity", precision = 21, scale = 2)
    private BigDecimal quantity;

    @Column(name = "ceiling_price", precision = 21, scale = 2)
    private BigDecimal ceilingPrice;

    /**
     * Is quantity * ceilingPrice
     */
    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    /**
     * The multiple of the amount allowed to bid.
     */
    @Column(name = "bid_decrement", precision = 21, scale = 2)
    private BigDecimal bidDecrement;

    /**
     * The minimum difference to bottom that is allowed in making bids.\nie. When the participant #1 bids at the price of 1000, and protectBackBuffer\nis set to 500, then the other participants can only bids at least started from 1500.
     */
    @Column(name = "protect_back_buffer", precision = 21, scale = 2)
    private BigDecimal protectBackBuffer;

    /**
     * The minimum difference to top that is allowed in making bids.\nie. When the participant #1 bids at the price of 1000, and protectBackBuffer\nis set to 500, then the other participants can only bids at least started from 8500.
     */
    @Column(name = "protect_front_buffer", precision = 21, scale = 2)
    private BigDecimal protectFrontBuffer;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mAuctionItems")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mAuctionItems")
    private MAuction auction;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mAuctionItems")
    private CProduct product;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mAuctionItems")
    private CUnitOfMeasure uom;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public MAuctionItem quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCeilingPrice() {
        return ceilingPrice;
    }

    public MAuctionItem ceilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
        return this;
    }

    public void setCeilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public MAuctionItem amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBidDecrement() {
        return bidDecrement;
    }

    public MAuctionItem bidDecrement(BigDecimal bidDecrement) {
        this.bidDecrement = bidDecrement;
        return this;
    }

    public void setBidDecrement(BigDecimal bidDecrement) {
        this.bidDecrement = bidDecrement;
    }

    public BigDecimal getProtectBackBuffer() {
        return protectBackBuffer;
    }

    public MAuctionItem protectBackBuffer(BigDecimal protectBackBuffer) {
        this.protectBackBuffer = protectBackBuffer;
        return this;
    }

    public void setProtectBackBuffer(BigDecimal protectBackBuffer) {
        this.protectBackBuffer = protectBackBuffer;
    }

    public BigDecimal getProtectFrontBuffer() {
        return protectFrontBuffer;
    }

    public MAuctionItem protectFrontBuffer(BigDecimal protectFrontBuffer) {
        this.protectFrontBuffer = protectFrontBuffer;
        return this;
    }

    public void setProtectFrontBuffer(BigDecimal protectFrontBuffer) {
        this.protectFrontBuffer = protectFrontBuffer;
    }

    public UUID getUid() {
        return uid;
    }

    public MAuctionItem uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MAuctionItem active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MAuctionItem adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MAuction getAuction() {
        return auction;
    }

    public MAuctionItem auction(MAuction mAuction) {
        this.auction = mAuction;
        return this;
    }

    public void setAuction(MAuction mAuction) {
        this.auction = mAuction;
    }

    public CProduct getProduct() {
        return product;
    }

    public MAuctionItem product(CProduct cProduct) {
        this.product = cProduct;
        return this;
    }

    public void setProduct(CProduct cProduct) {
        this.product = cProduct;
    }

    public CUnitOfMeasure getUom() {
        return uom;
    }

    public MAuctionItem uom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
        return this;
    }

    public void setUom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
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
        if (!(o instanceof MAuctionItem)) {
            return false;
        }
        return id != null && id.equals(((MAuctionItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MAuctionItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", ceilingPrice=" + getCeilingPrice() +
            ", amount=" + getAmount() +
            ", bidDecrement=" + getBidDecrement() +
            ", protectBackBuffer=" + getProtectBackBuffer() +
            ", protectFrontBuffer=" + getProtectFrontBuffer() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

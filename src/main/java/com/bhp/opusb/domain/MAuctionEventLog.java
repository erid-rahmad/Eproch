package com.bhp.opusb.domain;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MAuctionEventLog.
 */
@Entity
@Table(name = "m_auction_event_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MAuctionEventLog extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "action", length = 10, nullable = false)
    private String action;

    @NotNull
    @Column(name = "date_trx", nullable = false)
    private Instant dateTrx;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "note")
    private String note;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mAuctionEventLogs")
    private MAuction auction;

    @ManyToOne
    @JsonIgnoreProperties("mAuctionEventLogs")
    private MAuctionItem auctionItem;

    @ManyToOne
    @JsonIgnoreProperties("mAuctionEventLogs")
    private CVendor vendor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public MAuctionEventLog action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Instant getDateTrx() {
        return dateTrx;
    }

    public MAuctionEventLog dateTrx(Instant dateTrx) {
        this.dateTrx = dateTrx;
        return this;
    }

    public void setDateTrx(Instant dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getUsername() {
        return username;
    }

    public MAuctionEventLog username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MAuctionEventLog price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public MAuctionEventLog note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public MAuction getAuction() {
        return auction;
    }

    public MAuctionEventLog auction(MAuction mAuction) {
        this.auction = mAuction;
        return this;
    }

    public void setAuction(MAuction mAuction) {
        this.auction = mAuction;
    }

    public MAuctionItem getAuctionItem() {
        return auctionItem;
    }

    public MAuctionEventLog auctionItem(MAuctionItem mAuctionItem) {
        this.auctionItem = mAuctionItem;
        return this;
    }

    public void setAuctionItem(MAuctionItem mAuctionItem) {
        this.auctionItem = mAuctionItem;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MAuctionEventLog vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MAuctionEventLog)) {
            return false;
        }
        return id != null && id.equals(((MAuctionEventLog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MAuctionEventLog{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", username='" + getUsername() + "'" +
            ", price=" + getPrice() +
            ", note='" + getNote() + "'" +
            "}";
    }
}

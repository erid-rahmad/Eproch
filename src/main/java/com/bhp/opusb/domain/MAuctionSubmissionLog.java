package com.bhp.opusb.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

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
 * A MAuctionSubmissionLog.
 */
@Entity
@Table(name = "m_auction_submission_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MAuctionSubmissionLog extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "action", length = 10, nullable = false)
    private String action;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @NotNull
    @Column(name = "date_trx", nullable = false)
    private ZonedDateTime dateTrx;

    @Column(name = "message")
    private String message;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mAuctionSubmissionLogs")
    private MAuctionItem auctionItem;

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

    public MAuctionSubmissionLog action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUserName() {
        return userName;
    }

    public MAuctionSubmissionLog userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MAuctionSubmissionLog price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ZonedDateTime getDateTrx() {
        return dateTrx;
    }

    public MAuctionSubmissionLog dateTrx(ZonedDateTime dateTrx) {
        this.dateTrx = dateTrx;
        return this;
    }

    public void setDateTrx(ZonedDateTime dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getMessage() {
        return message;
    }

    public MAuctionSubmissionLog message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MAuctionItem getAuctionItem() {
        return auctionItem;
    }

    public MAuctionSubmissionLog auctionItem(MAuctionItem mAuctionItem) {
        this.auctionItem = mAuctionItem;
        return this;
    }

    public void setAuctionItem(MAuctionItem mAuctionItem) {
        this.auctionItem = mAuctionItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MAuctionSubmissionLog)) {
            return false;
        }
        return id != null && id.equals(((MAuctionSubmissionLog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MAuctionSubmissionLog{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", userName='" + getUserName() + "'" +
            ", price=" + getPrice() +
            ", dateTrx='" + getDateTrx() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}

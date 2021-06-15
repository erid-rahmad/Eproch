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
 * A MAuctionSubmissionItem.
 */
@Entity
@Table(name = "m_auction_submission_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MAuctionSubmissionItem extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mAuctionSubmissionItems")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mAuctionSubmissionItems")
    private MAuctionSubmission auctionSubmission;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mAuctionSubmissionItems")
    private MAuctionItem auctionItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MAuctionSubmissionItem price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID getUid() {
        return uid;
    }

    public MAuctionSubmissionItem uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MAuctionSubmissionItem active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MAuctionSubmissionItem adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MAuctionSubmission getAuctionSubmission() {
        return auctionSubmission;
    }

    public MAuctionSubmissionItem auctionSubmission(MAuctionSubmission mAuctionSubmission) {
        this.auctionSubmission = mAuctionSubmission;
        return this;
    }

    public void setAuctionSubmission(MAuctionSubmission mAuctionSubmission) {
        this.auctionSubmission = mAuctionSubmission;
    }

    public MAuctionItem getAuctionItem() {
        return auctionItem;
    }

    public MAuctionSubmissionItem auctionItem(MAuctionItem mAuctionItem) {
        this.auctionItem = mAuctionItem;
        return this;
    }

    public void setAuctionItem(MAuctionItem mAuctionItem) {
        this.auctionItem = mAuctionItem;
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
        if (!(o instanceof MAuctionSubmissionItem)) {
            return false;
        }
        return id != null && id.equals(((MAuctionSubmissionItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MAuctionSubmissionItem{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

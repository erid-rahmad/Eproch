package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * A MVendorScoring.
 */
@Entity
@Table(name = "m_vendor_scoring")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVendorScoring extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "percentage", precision = 21, scale = 2)
    private BigDecimal percentage;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorScorings")
    private MBidding bidding;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorScorings")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorScorings")
    private CBiddingCriteria biddingCriteria;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorScorings")
    private CBiddingSubCriteria biddingSubCriteria;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorScorings")
    private AdUser adUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public MVendorScoring percentage(BigDecimal percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public UUID getUid() {
        return uid;
    }

    public MVendorScoring uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MVendorScoring active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MVendorScoring bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MVendorScoring adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CBiddingCriteria getBiddingCriteria() {
        return biddingCriteria;
    }

    public MVendorScoring biddingCriteria(CBiddingCriteria cBiddingCriteria) {
        this.biddingCriteria = cBiddingCriteria;
        return this;
    }

    public void setBiddingCriteria(CBiddingCriteria cBiddingCriteria) {
        this.biddingCriteria = cBiddingCriteria;
    }

    public CBiddingSubCriteria getBiddingSubCriteria() {
        return biddingSubCriteria;
    }

    public MVendorScoring biddingSubCriteria(CBiddingSubCriteria cBiddingSubCriteria) {
        this.biddingSubCriteria = cBiddingSubCriteria;
        return this;
    }

    public void setBiddingSubCriteria(CBiddingSubCriteria cBiddingSubCriteria) {
        this.biddingSubCriteria = cBiddingSubCriteria;
    }

    public AdUser getAdUser() {
        return adUser;
    }

    public MVendorScoring adUser(AdUser adUser) {
        this.adUser = adUser;
        return this;
    }

    public void setAdUser(AdUser adUser) {
        this.adUser = adUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MVendorScoring)) {
            return false;
        }
        return id != null && id.equals(((MVendorScoring) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MVendorScoring{" +
            "id=" + getId() +
            ", percentage=" + getPercentage() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

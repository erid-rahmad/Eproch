package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A MBiddingResult.
 */
@Entity
@Table(name = "m_bidding_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBiddingResult extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingResults")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingResults")
    private CAnnouncementResult announcementResult;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingResults")
    private MBidding bidding;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingResults")
    private CVendor vendor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingResults")
    private MBiddingEvalResult biddingEvalResult;

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

    public MBiddingResult uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingResult active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingResult adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CAnnouncementResult getAnnouncementResult() {
        return announcementResult;
    }

    public MBiddingResult announcementResult(CAnnouncementResult cAnnouncementResult) {
        this.announcementResult = cAnnouncementResult;
        return this;
    }

    public void setAnnouncementResult(CAnnouncementResult cAnnouncementResult) {
        this.announcementResult = cAnnouncementResult;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MBiddingResult bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MBiddingResult vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public MBiddingEvalResult getBiddingEvalResult() {
        return biddingEvalResult;
    }

    public MBiddingResult biddingEvalResult(MBiddingEvalResult mBiddingEvalResult) {
        this.biddingEvalResult = mBiddingEvalResult;
        return this;
    }

    public void setBiddingEvalResult(MBiddingEvalResult mBiddingEvalResult) {
        this.biddingEvalResult = mBiddingEvalResult;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingResult)) {
            return false;
        }
        return id != null && id.equals(((MBiddingResult) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
    @PrePersist
    public void prePersist() {
        uid = UUID.randomUUID();
    }


    @Override
    public String toString() {
        return "MBiddingResult{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.UUID;

/**
 * A MDocumentSchedule.
 */
@Entity
@Table(name = "m_document_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MDocumentSchedule extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "doc_event", nullable = false)
    private String docEvent;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mDocumentSchedules")
    private MBidding bidding;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mDocumentSchedules")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mDocumentSchedules")
    private MBiddingSchedule vendorSubmission;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mDocumentSchedules")
    private MBiddingSchedule vendorEvaluation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocEvent() {
        return docEvent;
    }

    public MDocumentSchedule docEvent(String docEvent) {
        this.docEvent = docEvent;
        return this;
    }

    public void setDocEvent(String docEvent) {
        this.docEvent = docEvent;
    }

    public UUID getUid() {
        return uid;
    }

    public MDocumentSchedule uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MDocumentSchedule active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MDocumentSchedule bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MDocumentSchedule adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBiddingSchedule getVendorSubmission() {
        return vendorSubmission;
    }

    public MDocumentSchedule vendorSubmission(MBiddingSchedule mBiddingSchedule) {
        this.vendorSubmission = mBiddingSchedule;
        return this;
    }

    public void setVendorSubmission(MBiddingSchedule mBiddingSchedule) {
        this.vendorSubmission = mBiddingSchedule;
    }

    public MBiddingSchedule getVendorEvaluation() {
        return vendorEvaluation;
    }

    public MDocumentSchedule vendorEvaluation(MBiddingSchedule mBiddingSchedule) {
        this.vendorEvaluation = mBiddingSchedule;
        return this;
    }

    public void setVendorEvaluation(MBiddingSchedule mBiddingSchedule) {
        this.vendorEvaluation = mBiddingSchedule;
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
        if (!(o instanceof MDocumentSchedule)) {
            return false;
        }
        return id != null && id.equals(((MDocumentSchedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MDocumentSchedule{" +
            "id=" + getId() +
            ", docEvent='" + getDocEvent() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

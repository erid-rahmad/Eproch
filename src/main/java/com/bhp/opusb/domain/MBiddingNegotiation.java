package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A MBiddingNegotiation.
 */
@Entity
@Table(name = "m_bidding_negotiation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBiddingNegotiation extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "bidding_status")
    private String biddingStatus;

    @Column(name = "evaluation_status")
    private String evaluationStatus;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingNegotiations")
    private MBiddingEvaluation biddingEval;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingNegotiations")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingNegotiations")
    private MBiddingSchedule biddingSchedule;

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBiddingStatus() {
        return biddingStatus;
    }

    public MBiddingNegotiation biddingStatus(String biddingStatus) {
        this.biddingStatus = biddingStatus;
        return this;
    }

    public void setBiddingStatus(String biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public String getEvaluationStatus() {
        return evaluationStatus;
    }

    public MBiddingNegotiation evaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
        return this;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public MBiddingNegotiation startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public MBiddingNegotiation endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public UUID getUid() {
        return uid;
    }

    public MBiddingNegotiation uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingNegotiation active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MBiddingEvaluation getBiddingEval() {
        return biddingEval;
    }

    public MBiddingNegotiation biddingEval(MBiddingEvaluation mBiddingEvaluation) {
        this.biddingEval = mBiddingEvaluation;
        return this;
    }

    public void setBiddingEval(MBiddingEvaluation mBiddingEvaluation) {
        this.biddingEval = mBiddingEvaluation;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingNegotiation adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBiddingSchedule getBiddingSchedule() {
        return biddingSchedule;
    }

    public MBiddingNegotiation biddingSchedule(MBiddingSchedule mBiddingSchedule) {
        this.biddingSchedule = mBiddingSchedule;
        return this;
    }

    public void setBiddingSchedule(MBiddingSchedule mBiddingSchedule) {
        this.biddingSchedule = mBiddingSchedule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingNegotiation)) {
            return false;
        }
        return id != null && id.equals(((MBiddingNegotiation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBiddingNegotiation{" +
            "id=" + getId() +
            ", biddingStatus='" + getBiddingStatus() + "'" +
            ", evaluationStatus='" + getEvaluationStatus() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

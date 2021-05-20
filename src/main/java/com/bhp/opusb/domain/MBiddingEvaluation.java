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
 * A MBiddingEvaluation.
 */
@Entity
@Table(name = "m_bidding_evaluation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBiddingEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "evaluation_status")
    private String evaluationStatus;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingEvaluations")
    private MBiddingSubmission biddingSubmission;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingEvaluations")
    private MBiddingSchedule biddingSchedule;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingEvaluations")
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvaluationStatus() {
        return evaluationStatus;
    }

    public MBiddingEvaluation evaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
        return this;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public UUID getUid() {
        return uid;
    }

    public MBiddingEvaluation uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingEvaluation active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MBiddingSubmission getBiddingSubmission() {
        return biddingSubmission;
    }

    public MBiddingEvaluation biddingSubmission(MBiddingSubmission mBiddingSubmission) {
        this.biddingSubmission = mBiddingSubmission;
        return this;
    }

    public void setBiddingSubmission(MBiddingSubmission mBiddingSubmission) {
        this.biddingSubmission = mBiddingSubmission;
    }

    public MBiddingSchedule getBiddingSchedule() {
        return biddingSchedule;
    }

    public MBiddingEvaluation biddingSchedule(MBiddingSchedule mBiddingSchedule) {
        this.biddingSchedule = mBiddingSchedule;
        return this;
    }

    public void setBiddingSchedule(MBiddingSchedule mBiddingSchedule) {
        this.biddingSchedule = mBiddingSchedule;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingEvaluation adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingEvaluation)) {
            return false;
        }
        return id != null && id.equals(((MBiddingEvaluation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBiddingEvaluation{" +
            "id=" + getId() +
            ", evaluationStatus='" + getEvaluationStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

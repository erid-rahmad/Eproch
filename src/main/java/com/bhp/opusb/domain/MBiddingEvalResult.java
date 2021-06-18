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
 * A MBiddingEvalResult.
 */
@Entity
@Table(name = "m_bidding_eval_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBiddingEvalResult extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "evaluation_status")
    private String evaluationStatus;

    @Column(name = "status")
    private String status;

    @Column(name = "winner_status")
    private Boolean winnerStatus;

    @Column(name = "score")
    private Integer score;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingEvalResults")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingEvalResults")
    private MBiddingSubmission biddingSubmission;

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

    public MBiddingEvalResult evaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
        return this;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public String getStatus() {
        return status;
    }

    public MBiddingEvalResult status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean isWinnerStatus() {
        return winnerStatus;
    }

    public MBiddingEvalResult winnerStatus(Boolean winnerStatus) {
        this.winnerStatus = winnerStatus;
        return this;
    }

    public void setWinnerStatus(Boolean winnerStatus) {
        this.winnerStatus = winnerStatus;
    }

    public Integer getScore() {
        return score;
    }

    public MBiddingEvalResult score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getRank() {
        return rank;
    }

    public MBiddingEvalResult rank(Integer rank) {
        this.rank = rank;
        return this;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public UUID getUid() {
        return uid;
    }

    public MBiddingEvalResult uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingEvalResult active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingEvalResult adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBiddingSubmission getBiddingSubmission() {
        return biddingSubmission;
    }

    public MBiddingEvalResult biddingSubmission(MBiddingSubmission mBiddingSubmission) {
        this.biddingSubmission = mBiddingSubmission;
        return this;
    }

    public void setBiddingSubmission(MBiddingSubmission mBiddingSubmission) {
        this.biddingSubmission = mBiddingSubmission;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingEvalResult)) {
            return false;
        }
        return id != null && id.equals(((MBiddingEvalResult) o).id);
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
        return "MBiddingEvalResult{" +
            "id=" + getId() +
            ", evaluationStatus='" + getEvaluationStatus() + "'" +
            ", status='" + getStatus() + "'" +
            ", winnerStatus='" + isWinnerStatus() + "'" +
            ", score=" + getScore() +
            ", rank=" + getRank() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

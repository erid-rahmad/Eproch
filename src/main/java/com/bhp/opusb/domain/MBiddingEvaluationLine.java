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
 * A MBiddingEvaluationLine.
 */
@Entity
@Table(name = "m_bidding_evaluation_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBiddingEvaluationLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "evaluation_status")
    private String evaluationStatus;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingEvaluationLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingEvaluationLines")
    private MBiddingEvaluation biddingEvaluation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public MBiddingEvaluationLine status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEvaluationStatus() {
        return evaluationStatus;
    }

    public MBiddingEvaluationLine evaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
        return this;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public UUID getUid() {
        return uid;
    }

    public MBiddingEvaluationLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingEvaluationLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingEvaluationLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBiddingEvaluation getBiddingEvaluation() {
        return biddingEvaluation;
    }

    public MBiddingEvaluationLine biddingEvaluation(MBiddingEvaluation mBiddingEvaluation) {
        this.biddingEvaluation = mBiddingEvaluation;
        return this;
    }

    public void setBiddingEvaluation(MBiddingEvaluation mBiddingEvaluation) {
        this.biddingEvaluation = mBiddingEvaluation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingEvaluationLine)) {
            return false;
        }
        return id != null && id.equals(((MBiddingEvaluationLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBiddingEvaluationLine{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", evaluationStatus='" + getEvaluationStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

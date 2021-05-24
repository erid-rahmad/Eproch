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
 * A MTechProposalEvaluation.
 */
@Entity
@Table(name = "m_tech_proposal_evaluation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MTechProposalEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "notes")
    private String notes;

    @NotNull
    @Column(name = "evaluation", nullable = false)
    private String evaluation;

    @Column(name = "average_score")
    private Integer averageScore;

    @Column(name = "pass_fail")
    private String passFail;

    @Column(name = "requirement")
    private String requirement;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mTechProposalEvaluations")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("mTechProposalEvaluations")
    private MBidding bidding;

    @ManyToOne
    @JsonIgnoreProperties("mTechProposalEvaluations")
    private CEvaluationMethodCriteria evaluationMethodCriteria;

    @ManyToOne
    @JsonIgnoreProperties("mTechProposalEvaluations")
    private CEvalMethodSubCriteria evalMethodSubCriteria;

    @ManyToOne
    @JsonIgnoreProperties("mTechProposalEvaluations")
    private CBiddingSubCriteriaLine biddingSubCriteriaLine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public MTechProposalEvaluation notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public MTechProposalEvaluation evaluation(String evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getAverageScore() {
        return averageScore;
    }

    public MTechProposalEvaluation averageScore(Integer averageScore) {
        this.averageScore = averageScore;
        return this;
    }

    public void setAverageScore(Integer averageScore) {
        this.averageScore = averageScore;
    }

    public String getPassFail() {
        return passFail;
    }

    public MTechProposalEvaluation passFail(String passFail) {
        this.passFail = passFail;
        return this;
    }

    public void setPassFail(String passFail) {
        this.passFail = passFail;
    }

    public String getRequirement() {
        return requirement;
    }

    public MTechProposalEvaluation requirement(String requirement) {
        this.requirement = requirement;
        return this;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public UUID getUid() {
        return uid;
    }

    public MTechProposalEvaluation uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MTechProposalEvaluation active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MTechProposalEvaluation adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MTechProposalEvaluation bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public CEvaluationMethodCriteria getEvaluationMethodCriteria() {
        return evaluationMethodCriteria;
    }

    public MTechProposalEvaluation evaluationMethodCriteria(CEvaluationMethodCriteria cEvaluationMethodCriteria) {
        this.evaluationMethodCriteria = cEvaluationMethodCriteria;
        return this;
    }

    public void setEvaluationMethodCriteria(CEvaluationMethodCriteria cEvaluationMethodCriteria) {
        this.evaluationMethodCriteria = cEvaluationMethodCriteria;
    }

    public CEvalMethodSubCriteria getEvalMethodSubCriteria() {
        return evalMethodSubCriteria;
    }

    public MTechProposalEvaluation evalMethodSubCriteria(CEvalMethodSubCriteria cEvalMethodSubCriteria) {
        this.evalMethodSubCriteria = cEvalMethodSubCriteria;
        return this;
    }

    public void setEvalMethodSubCriteria(CEvalMethodSubCriteria cEvalMethodSubCriteria) {
        this.evalMethodSubCriteria = cEvalMethodSubCriteria;
    }

    public CBiddingSubCriteriaLine getBiddingSubCriteriaLine() {
        return biddingSubCriteriaLine;
    }

    public MTechProposalEvaluation biddingSubCriteriaLine(CBiddingSubCriteriaLine cBiddingSubCriteriaLine) {
        this.biddingSubCriteriaLine = cBiddingSubCriteriaLine;
        return this;
    }

    public void setBiddingSubCriteriaLine(CBiddingSubCriteriaLine cBiddingSubCriteriaLine) {
        this.biddingSubCriteriaLine = cBiddingSubCriteriaLine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MTechProposalEvaluation)) {
            return false;
        }
        return id != null && id.equals(((MTechProposalEvaluation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MTechProposalEvaluation{" +
            "id=" + getId() +
            ", notes='" + getNotes() + "'" +
            ", evaluation='" + getEvaluation() + "'" +
            ", averageScore=" + getAverageScore() +
            ", passFail='" + getPassFail() + "'" +
            ", requirement='" + getRequirement() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

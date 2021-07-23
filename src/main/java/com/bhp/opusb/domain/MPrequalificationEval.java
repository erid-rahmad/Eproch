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
 * A MPrequalificationEval.
 */
@Entity
@Table(name = "m_prequalification_eval")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPrequalificationEval extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "document_evaluation")
    private Boolean documentEvaluation;

    @Size(max = 10)
    @Column(name = "document_action", length = 10)
    private String documentAction;

    @Size(max = 12)
    @Column(name = "document_status", length = 12)
    private String documentStatus;

    @Column(name = "notes")
    private String notes;

    @Column(name = "evaluation")
    private String evaluation;

    @Column(name = "average_score")
    private Integer averageScore;

    @Column(name = "pass_fail")
    private String passFail;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalificationEvals")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalificationEvals")
    private MPrequalificationSubmission prequalificationSubmission;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalificationEvals")
    private CBiddingSubCriteriaLine biddingSubCriteriaLine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public MPrequalificationEval answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean isDocumentEvaluation() {
        return documentEvaluation;
    }

    public MPrequalificationEval documentEvaluation(Boolean documentEvaluation) {
        this.documentEvaluation = documentEvaluation;
        return this;
    }

    public void setDocumentEvaluation(Boolean documentEvaluation) {
        this.documentEvaluation = documentEvaluation;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public MPrequalificationEval documentAction(String documentAction) {
        this.documentAction = documentAction;
        return this;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public MPrequalificationEval documentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getNotes() {
        return notes;
    }

    public MPrequalificationEval notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public MPrequalificationEval evaluation(String evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getAverageScore() {
        return averageScore;
    }

    public MPrequalificationEval averageScore(Integer averageScore) {
        this.averageScore = averageScore;
        return this;
    }

    public void setAverageScore(Integer averageScore) {
        this.averageScore = averageScore;
    }

    public String getPassFail() {
        return passFail;
    }

    public MPrequalificationEval passFail(String passFail) {
        this.passFail = passFail;
        return this;
    }

    public void setPassFail(String passFail) {
        this.passFail = passFail;
    }

    public UUID getUid() {
        return uid;
    }

    public MPrequalificationEval uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MPrequalificationEval active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MPrequalificationEval adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MPrequalificationSubmission getPrequalificationSubmission() {
        return prequalificationSubmission;
    }

    public MPrequalificationEval prequalificationSubmission(MPrequalificationSubmission mPrequalificationSubmission) {
        this.prequalificationSubmission = mPrequalificationSubmission;
        return this;
    }

    public void setPrequalificationSubmission(MPrequalificationSubmission mPrequalificationSubmission) {
        this.prequalificationSubmission = mPrequalificationSubmission;
    }

    public CBiddingSubCriteriaLine getBiddingSubCriteriaLine() {
        return biddingSubCriteriaLine;
    }

    public MPrequalificationEval biddingSubCriteriaLine(CBiddingSubCriteriaLine cBiddingSubCriteriaLine) {
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
        if (!(o instanceof MPrequalificationEval)) {
            return false;
        }
        return id != null && id.equals(((MPrequalificationEval) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPrequalificationEval{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            ", documentEvaluation='" + isDocumentEvaluation() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", notes='" + getNotes() + "'" +
            ", evaluation='" + getEvaluation() + "'" +
            ", averageScore=" + getAverageScore() +
            ", passFail='" + getPassFail() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }

    @PrePersist
    public void prePersist(){
        this.uid = UUID.randomUUID();
        this.active = true;
        this.documentStatus = "DRF";
        this.documentAction = "SMT";
    }
}

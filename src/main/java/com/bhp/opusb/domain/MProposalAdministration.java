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
 * A MProposalAdministration.
 */
@Entity
@Table(name = "m_proposal_administration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MProposalAdministration extends AbstractAuditingEntity {

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
    @JsonIgnoreProperties("mProposalAdministrations")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mProposalAdministrations")
    private MBiddingSubmission biddingSubmission;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mProposalAdministrations")
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

    public MProposalAdministration answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean isDocumentEvaluation() {
        return documentEvaluation;
    }

    public MProposalAdministration documentEvaluation(Boolean documentEvaluation) {
        this.documentEvaluation = documentEvaluation;
        return this;
    }

    public void setDocumentEvaluation(Boolean documentEvaluation) {
        this.documentEvaluation = documentEvaluation;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public MProposalAdministration documentAction(String documentAction) {
        this.documentAction = documentAction;
        return this;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public MProposalAdministration documentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getNotes() {
        return notes;
    }

    public MProposalAdministration notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public MProposalAdministration evaluation(String evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getAverageScore() {
        return averageScore;
    }

    public MProposalAdministration averageScore(Integer averageScore) {
        this.averageScore = averageScore;
        return this;
    }

    public void setAverageScore(Integer averageScore) {
        this.averageScore = averageScore;
    }

    public String getPassFail() {
        return passFail;
    }

    public MProposalAdministration passFail(String passFail) {
        this.passFail = passFail;
        return this;
    }

    public void setPassFail(String passFail) {
        this.passFail = passFail;
    }

    public UUID getUid() {
        return uid;
    }

    public MProposalAdministration uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MProposalAdministration active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MProposalAdministration adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBiddingSubmission getBiddingSubmission() {
        return biddingSubmission;
    }

    public MProposalAdministration biddingSubmission(MBiddingSubmission mBiddingSubmission) {
        this.biddingSubmission = mBiddingSubmission;
        return this;
    }

    public void setBiddingSubmission(MBiddingSubmission mBiddingSubmission) {
        this.biddingSubmission = mBiddingSubmission;
    }

    public CBiddingSubCriteriaLine getBiddingSubCriteriaLine() {
        return biddingSubCriteriaLine;
    }

    public MProposalAdministration biddingSubCriteriaLine(CBiddingSubCriteriaLine cBiddingSubCriteriaLine) {
        this.biddingSubCriteriaLine = cBiddingSubCriteriaLine;
        return this;
    }

    public void setBiddingSubCriteriaLine(CBiddingSubCriteriaLine cBiddingSubCriteriaLine) {
        this.biddingSubCriteriaLine = cBiddingSubCriteriaLine;
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
        if (!(o instanceof MProposalAdministration)) {
            return false;
        }
        return id != null && id.equals(((MProposalAdministration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MProposalAdministration{" +
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
}

package com.bhp.opusb.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MProposalTechnical.
 */
@Entity
@Table(name = "m_proposal_technical")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MProposalTechnical extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "document_evaluation")
    private Boolean documentEvaluation;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mProposalTechnicals")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mProposalTechnicals")
    private MBiddingSubmission biddingSubmission;

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

    public MProposalTechnical answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean isDocumentEvaluation() {
        return documentEvaluation;
    }

    public MProposalTechnical documentEvaluation(Boolean documentEvaluation) {
        this.documentEvaluation = documentEvaluation;
        return this;
    }

    public void setDocumentEvaluation(Boolean documentEvaluation) {
        this.documentEvaluation = documentEvaluation;
    }

    public UUID getUid() {
        return uid;
    }

    public MProposalTechnical uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MProposalTechnical active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MProposalTechnical adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBiddingSubmission getBiddingSubmission() {
        return biddingSubmission;
    }

    public MProposalTechnical biddingSubmission(MBiddingSubmission mBiddingSubmission) {
        this.biddingSubmission = mBiddingSubmission;
        return this;
    }

    public void setBiddingSubmission(MBiddingSubmission mBiddingSubmission) {
        this.biddingSubmission = mBiddingSubmission;
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
        if (!(o instanceof MProposalTechnical)) {
            return false;
        }
        return id != null && id.equals(((MProposalTechnical) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MProposalTechnical{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            ", documentEvaluation='" + isDocumentEvaluation() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalificationEval} entity.
 */
public class MPrequalificationEvalDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private String answer;

    private Boolean documentEvaluation;

    @Size(max = 10)
    private String documentAction;

    @Size(max = 12)
    private String documentStatus;

    private String notes;

    private String evaluation;

    private Integer averageScore;

    private String passFail;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long prequalificationSubmissionId;

    private Long biddingSubCriteriaLineId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean isDocumentEvaluation() {
        return documentEvaluation;
    }

    public void setDocumentEvaluation(Boolean documentEvaluation) {
        this.documentEvaluation = documentEvaluation;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Integer averageScore) {
        this.averageScore = averageScore;
    }

    public String getPassFail() {
        return passFail;
    }

    public void setPassFail(String passFail) {
        this.passFail = passFail;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getPrequalificationSubmissionId() {
        return prequalificationSubmissionId;
    }

    public void setPrequalificationSubmissionId(Long mPrequalificationSubmissionId) {
        this.prequalificationSubmissionId = mPrequalificationSubmissionId;
    }

    public Long getBiddingSubCriteriaLineId() {
        return biddingSubCriteriaLineId;
    }

    public void setBiddingSubCriteriaLineId(Long cBiddingSubCriteriaLineId) {
        this.biddingSubCriteriaLineId = cBiddingSubCriteriaLineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPrequalificationEvalDTO mPrequalificationEvalDTO = (MPrequalificationEvalDTO) o;
        if (mPrequalificationEvalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalificationEvalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalificationEvalDTO{" +
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
            ", adOrganizationId=" + getAdOrganizationId() +
            ", prequalificationSubmissionId=" + getPrequalificationSubmissionId() +
            ", biddingSubCriteriaLineId=" + getBiddingSubCriteriaLineId() +
            "}";
    }
}

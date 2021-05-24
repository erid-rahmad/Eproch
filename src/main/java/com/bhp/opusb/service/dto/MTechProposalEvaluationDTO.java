package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MTechProposalEvaluation} entity.
 */
public class MTechProposalEvaluationDTO implements Serializable {

    private Long id;

    private String notes;

    @NotNull
    private String evaluation;

    private Integer averageScore;

    private String passFail;

    private String requirement;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long biddingId;

    private Long evaluationMethodCriteriaId;

    private Long evalMethodSubCriteriaId;

    private Long biddingSubCriteriaLineId;
    private List<CEvalMethodSubCriteriaDTO> evalMethodSubCriteriaList ;

    public List<CEvalMethodSubCriteriaDTO> getEvalMethodSubCriteriaList() {
        return evalMethodSubCriteriaList;
    }

    public void setEvalMethodSubCriteriaList(List<CEvalMethodSubCriteriaDTO> evalMethodSubCriteriaList) {
        this.evalMethodSubCriteriaList = evalMethodSubCriteriaList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
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

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long mBiddingId) {
        this.biddingId = mBiddingId;
    }

    public Long getEvaluationMethodCriteriaId() {
        return evaluationMethodCriteriaId;
    }

    public void setEvaluationMethodCriteriaId(Long cEvaluationMethodCriteriaId) {
        this.evaluationMethodCriteriaId = cEvaluationMethodCriteriaId;
    }

    public Long getEvalMethodSubCriteriaId() {
        return evalMethodSubCriteriaId;
    }

    public void setEvalMethodSubCriteriaId(Long cEvalMethodSubCriteriaId) {
        this.evalMethodSubCriteriaId = cEvalMethodSubCriteriaId;
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

        MTechProposalEvaluationDTO mTechProposalEvaluationDTO = (MTechProposalEvaluationDTO) o;
        if (mTechProposalEvaluationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mTechProposalEvaluationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MTechProposalEvaluationDTO{" +
            "id=" + getId() +
            ", notes='" + getNotes() + "'" +
            ", evaluation='" + getEvaluation() + "'" +
            ", averageScore=" + getAverageScore() +
            ", passFail='" + getPassFail() + "'" +
            ", requirement='" + getRequirement() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingId=" + getBiddingId() +
            ", evaluationMethodCriteriaId=" + getEvaluationMethodCriteriaId() +
            ", evalMethodSubCriteriaId=" + getEvalMethodSubCriteriaId() +
            ", biddingSubCriteriaLineId=" + getBiddingSubCriteriaLineId() +
            "}";
    }
}

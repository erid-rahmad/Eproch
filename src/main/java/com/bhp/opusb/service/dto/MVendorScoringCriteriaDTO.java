package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorScoringCriteria} entity.
 */
public class MVendorScoringCriteriaDTO extends AbstractAuditingDTO {

    private Long id;

    @NotNull
    private String requirement;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;

    private Long evaluationMethodCriteriaId;

    private Long evalMethodSubCriteriaId;

    private Long vendorScoringLineId;

    private Long biddingSubCriteriaLineId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getVendorScoringLineId() {
        return vendorScoringLineId;
    }

    public void setVendorScoringLineId(Long mVendorScoringLineId) {
        this.vendorScoringLineId = mVendorScoringLineId;
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

        MVendorScoringCriteriaDTO mVendorScoringCriteriaDTO = (MVendorScoringCriteriaDTO) o;
        if (mVendorScoringCriteriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVendorScoringCriteriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVendorScoringCriteriaDTO{" +
            "id=" + getId() +
            ", requirement='" + getRequirement() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", evaluationMethodCriteriaId=" + getEvaluationMethodCriteriaId() +
            ", evalMethodSubCriteriaId=" + getEvalMethodSubCriteriaId() +
            ", vendorScoringLineId=" + getVendorScoringLineId() +
            ", biddingSubCriteriaLineId=" + getBiddingSubCriteriaLineId() +
            "}";
    }
}

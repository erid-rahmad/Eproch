package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingEvaluation} entity.
 */
public class MBiddingEvaluationDTO implements Serializable {
    
    private Long id;

    private String evaluationStatus;

    private UUID uid;

    private Boolean active;


    private Long biddingSubmissionId;

    private Long biddingScheduleId;

    private Long adOrganizationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
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

    public Long getBiddingSubmissionId() {
        return biddingSubmissionId;
    }

    public void setBiddingSubmissionId(Long mBiddingSubmissionId) {
        this.biddingSubmissionId = mBiddingSubmissionId;
    }

    public Long getBiddingScheduleId() {
        return biddingScheduleId;
    }

    public void setBiddingScheduleId(Long mBiddingScheduleId) {
        this.biddingScheduleId = mBiddingScheduleId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingEvaluationDTO mBiddingEvaluationDTO = (MBiddingEvaluationDTO) o;
        if (mBiddingEvaluationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingEvaluationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingEvaluationDTO{" +
            "id=" + getId() +
            ", evaluationStatus='" + getEvaluationStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingSubmissionId=" + getBiddingSubmissionId() +
            ", biddingScheduleId=" + getBiddingScheduleId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}

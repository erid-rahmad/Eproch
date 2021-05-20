package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingEvaluationLine} entity.
 */
public class MBiddingEvaluationLineDTO implements Serializable {
    
    private Long id;

    private String status;

    private String evaluationStatus;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long biddingEvaluationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getBiddingEvaluationId() {
        return biddingEvaluationId;
    }

    public void setBiddingEvaluationId(Long mBiddingEvaluationId) {
        this.biddingEvaluationId = mBiddingEvaluationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingEvaluationLineDTO mBiddingEvaluationLineDTO = (MBiddingEvaluationLineDTO) o;
        if (mBiddingEvaluationLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingEvaluationLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingEvaluationLineDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", evaluationStatus='" + getEvaluationStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingEvaluationId=" + getBiddingEvaluationId() +
            "}";
    }
}

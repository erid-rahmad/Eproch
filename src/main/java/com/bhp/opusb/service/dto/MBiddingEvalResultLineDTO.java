package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingEvalResultLine} entity.
 */
public class MBiddingEvalResultLineDTO extends AbstractAuditingDTO {

    private Long id;

    private String status;

    private Integer score;

    @Size(max = 10)
    private String documentAction;

    @Size(max = 12)
    private String documentStatus;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long evaluationMethodLineId;
    private String evaluationMethodLineName;
    private  BigDecimal evaluationMethodLineWeight;

    private Long biddingEvalResultId;

    public BigDecimal getEvaluationMethodLineWeight() {
        return evaluationMethodLineWeight;
    }

    public void setEvaluationMethodLineWeight(BigDecimal evaluationMethodLineWeight) {
        this.evaluationMethodLineWeight = evaluationMethodLineWeight;
    }

    public String getEvaluationMethodLineName() {
        return evaluationMethodLineName;
    }

    public void setEvaluationMethodLineName(String evaluationMethodLineName) {
        this.evaluationMethodLineName = evaluationMethodLineName;
    }

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public Long getEvaluationMethodLineId() {
        return evaluationMethodLineId;
    }

    public void setEvaluationMethodLineId(Long cEvaluationMethodLineId) {
        this.evaluationMethodLineId = cEvaluationMethodLineId;
    }

    public Long getBiddingEvalResultId() {
        return biddingEvalResultId;
    }

    public void setBiddingEvalResultId(Long mBiddingEvalResultId) {
        this.biddingEvalResultId = mBiddingEvalResultId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingEvalResultLineDTO mBiddingEvalResultLineDTO = (MBiddingEvalResultLineDTO) o;
        if (mBiddingEvalResultLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingEvalResultLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingEvalResultLineDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", score=" + getScore() +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", evaluationMethodLineId=" + getEvaluationMethodLineId() +
            ", biddingEvalResultId=" + getBiddingEvalResultId() +
            "}";
    }
}

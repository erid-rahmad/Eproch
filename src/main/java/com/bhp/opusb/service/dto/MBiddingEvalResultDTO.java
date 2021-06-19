package com.bhp.opusb.service.dto;

import javax.persistence.PrePersist;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingEvalResult} entity.
 */
public class MBiddingEvalResultDTO extends AbstractAuditingDTO {

    private Long id;

    private String evaluationStatus;

    private String status;

    private Boolean winnerStatus;

    private Integer score;

    private Integer rank;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long biddingSubmissionId;
    private Long vendorId;
    private String vendorName;
    private String biddingNo;
    private String biddingType;
    private ZonedDateTime submitDate;

    private Long biddingId;
    private String biddingName;

    public ZonedDateTime getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(ZonedDateTime submitDate) {
        this.submitDate = submitDate;
    }

    public String getBiddingNo() {
        return biddingNo;
    }

    public void setBiddingNo(String biddingNo) {
        this.biddingNo = biddingNo;
    }

    public String getBiddingType() {
        return biddingType;
    }

    public void setBiddingType(String biddingType) {
        this.biddingType = biddingType;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getBiddingName() {
        return biddingName;
    }

    public void setBiddingName(String biddingName) {
        this.biddingName = biddingName;
    }

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long biddingId) {
        this.biddingId = biddingId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean isWinnerStatus() {
        return winnerStatus;
    }

    public void setWinnerStatus(Boolean winnerStatus) {
        this.winnerStatus = winnerStatus;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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

    public Long getBiddingSubmissionId() {
        return biddingSubmissionId;
    }

    public void setBiddingSubmissionId(Long mBiddingSubmissionId) {
        this.biddingSubmissionId = mBiddingSubmissionId;
    }

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingEvalResultDTO mBiddingEvalResultDTO = (MBiddingEvalResultDTO) o;
        if (mBiddingEvalResultDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingEvalResultDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingEvalResultDTO{" +
            "id=" + id +
            ", evaluationStatus='" + evaluationStatus + '\'' +
            ", status='" + status + '\'' +
            ", winnerStatus=" + winnerStatus +
            ", score=" + score +
            ", rank=" + rank +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", biddingSubmissionId=" + biddingSubmissionId +
            ", vendorId=" + vendorId +
            ", vendorName='" + vendorName + '\'' +
            ", biddingNo='" + biddingNo + '\'' +
            ", biddingType='" + biddingType + '\'' +
            ", submitDate=" + submitDate +
            ", biddingId=" + biddingId +
            ", biddingName='" + biddingName + '\'' +
            '}';
    }
}

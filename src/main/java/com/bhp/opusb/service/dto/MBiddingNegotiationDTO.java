package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingNegotiation} entity.
 */
public class MBiddingNegotiationDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private String biddingStatus;

    private String evaluationStatus = "submitted";

    @NotNull
    private ZonedDateTime startDate;

    @NotNull
    private ZonedDateTime endDate;

    private UUID uid;

    private Boolean active;


    private Long biddingEvalId;
    private Long biddingId, finishedCount, vendorCount;
    private String biddingNo, biddingTitle, biddingType, eventType; 

    private Long adOrganizationId;

    private Long biddingScheduleId;

    private List<MBiddingNegotiationLineDTO> line;
    
    public Long getId() {
        return id;
    }

    public Long getVendorCount() {
        return vendorCount;
    }

    public void setVendorCount(Long vendorCount) {
        this.vendorCount = vendorCount;
    }

    public Long getFinishedCount() {
        return finishedCount;
    }

    public void setFinishedCount(Long finishedCount) {
        this.finishedCount = finishedCount;
    }

    public List<MBiddingNegotiationLineDTO> getLine() {
        return line;
    }

    public void setLine(List<MBiddingNegotiationLineDTO> line) {
        this.line = line;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getBiddingTitle() {
        return biddingTitle;
    }

    public void setBiddingTitle(String biddingTitle) {
        this.biddingTitle = biddingTitle;
    }

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long biddingId) {
        this.biddingId = biddingId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(String biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public String getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
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

    public Long getBiddingEvalId() {
        return biddingEvalId;
    }

    public void setBiddingEvalId(Long mBiddingEvaluationId) {
        this.biddingEvalId = mBiddingEvaluationId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getBiddingScheduleId() {
        return biddingScheduleId;
    }

    public void setBiddingScheduleId(Long mBiddingScheduleId) {
        this.biddingScheduleId = mBiddingScheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingNegotiationDTO mBiddingNegotiationDTO = (MBiddingNegotiationDTO) o;
        if (mBiddingNegotiationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingNegotiationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingNegotiationDTO{" +
            "id=" + getId() +
            ", biddingStatus='" + getBiddingStatus() + "'" +
            ", evaluationStatus='" + getEvaluationStatus() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingEvalId=" + getBiddingEvalId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingScheduleId=" + getBiddingScheduleId() +
            "}";
    }
}

package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingResult} entity.
 */
public class MBiddingResultDTO extends AbstractAuditingDTO {

    private Long id;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long announcementResultId;
    private String announcementResultName;
    private String attachmentName;
    private String attachmentId;
    private String attachmentUrl;

    private Long biddingId;
    private String biddingName;
    private String biddingStatus;
    private String biddingNo;
    private String biddingType;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
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

    public String getAnnouncementResultName() {
        return announcementResultName;
    }

    public void setAnnouncementResultName(String announcementResultName) {
        this.announcementResultName = announcementResultName;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getBiddingName() {
        return biddingName;
    }

    public void setBiddingName(String biddingName) {
        this.biddingName = biddingName;
    }

    public String getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(String biddingStatus) {
        this.biddingStatus = biddingStatus;
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

    private Long vendorId;

    private Long biddingEvalResultId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAnnouncementResultId() {
        return announcementResultId;
    }

    public void setAnnouncementResultId(Long cAnnouncementResultId) {
        this.announcementResultId = cAnnouncementResultId;
    }

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long mBiddingId) {
        this.biddingId = mBiddingId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
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

        MBiddingResultDTO mBiddingResultDTO = (MBiddingResultDTO) o;
        if (mBiddingResultDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingResultDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingResultDTO{" +
            "id=" + id +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", announcementResultId=" + announcementResultId +
            ", announcementResultName='" + announcementResultName + '\'' +
            ", attachmentName='" + attachmentName + '\'' +
            ", attachmentId='" + attachmentId + '\'' +
            ", biddingId=" + biddingId +
            ", biddingName='" + biddingName + '\'' +
            ", biddingStatus='" + biddingStatus + '\'' +
            ", biddingNo='" + biddingNo + '\'' +
            ", biddingType='" + biddingType + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", vendorId=" + vendorId +
            ", biddingEvalResultId=" + biddingEvalResultId +
            '}';
    }
}

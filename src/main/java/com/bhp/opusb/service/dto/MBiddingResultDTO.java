package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
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
    private String uploadDir;


    private Long biddingId;
    private String biddingName;
    private String biddingStatus;
    private String biddingNo;
    private String biddingType;

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

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
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
            ", biddingId=" + biddingId +
            ", biddingName='" + biddingName + '\'' +
            ", biddingStatus='" + biddingStatus + '\'' +
            ", biddingNo='" + biddingNo + '\'' +
            ", biddingType='" + biddingType + '\'' +
            ", vendorId=" + vendorId +
            ", biddingEvalResultId=" + biddingEvalResultId +
            '}';
    }
}

package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CAnnouncement} entity.
 */
public class CAnnouncementDTO extends AbstractAuditingDTO {

    private Long id;

    
    @Lob
    private String description;

    private ZonedDateTime publishDate;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long biddingId;
    private String biddingName;
    private String biddingDocNo;

    private Long biddingScheduleId;
    private String biddingScheduleName;

    private Long attachmentId;
    private String attachmentName;
    private String attachmentUrl;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(ZonedDateTime publishDate) {
        this.publishDate = publishDate;
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

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long mBiddingId) {
        this.biddingId = mBiddingId;
    }

    public String getBiddingName() {
        return biddingName;
    }

    public void setBiddingName(String biddingName) {
        this.biddingName = biddingName;
    }

    public String getBiddingDocNo() {
        return biddingDocNo;
    }

    public void setBiddingDocNo(String biddingDocNo) {
        this.biddingDocNo = biddingDocNo;
    }

    public Long getBiddingScheduleId() {
        return biddingScheduleId;
    }

    public void setBiddingScheduleId(Long mBiddingScheduleId) {
        this.biddingScheduleId = mBiddingScheduleId;
    }

    public String getBiddingScheduleName() {
        return biddingScheduleName;
    }

    public void setBiddingScheduleName(String biddingScheduleName) {
        this.biddingScheduleName = biddingScheduleName;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long cAttachmentId) {
        this.attachmentId = cAttachmentId;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CAnnouncementDTO cAnnouncementDTO = (CAnnouncementDTO) o;
        if (cAnnouncementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cAnnouncementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CAnnouncementDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingId=" + getBiddingId() +
            ", biddingScheduleId=" + getBiddingScheduleId() +
            ", attachmentId=" + getAttachmentId() +
            "}";
    }
}

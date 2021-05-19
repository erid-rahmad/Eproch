package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CAnnouncementResult} entity.
 */
public class CAnnouncementResultDTO extends AbstractAuditingDTO {

    private Long id;


    @Lob
    private String description;

    private ZonedDateTime publishDate;

    private String documentNo;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long biddingId;

    private Long biddingScheduleId;

    private Long attachmentId;
    private String attachmentName;

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

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

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
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

    public Long getBiddingScheduleId() {
        return biddingScheduleId;
    }

    public void setBiddingScheduleId(Long mBiddingScheduleId) {
        this.biddingScheduleId = mBiddingScheduleId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long cAttachmentId) {
        this.attachmentId = cAttachmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CAnnouncementResultDTO cAnnouncementResultDTO = (CAnnouncementResultDTO) o;
        if (cAnnouncementResultDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cAnnouncementResultDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CAnnouncementResultDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingId=" + getBiddingId() +
            ", biddingScheduleId=" + getBiddingScheduleId() +
            ", attachmentId=" + getAttachmentId() +
            "}";
    }
}

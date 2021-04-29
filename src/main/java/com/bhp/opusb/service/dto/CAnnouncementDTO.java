package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CAnnouncement} entity.
 */
public class CAnnouncementDTO implements Serializable {

    private Long id;


    @Lob
    private String description;

    private ZonedDateTime publishDate;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long biddingId;
    private String biddingName;
    private String biddingDocNo;
    private List<Map<String,Object>> emaillist ;

    public List<Map<String, Object>> getEmaillist() {
        return emaillist;
    }

    public void setEmaillist(List<Map<String, Object>> emaillist) {
        this.emaillist = emaillist;
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

    private Long biddingScheduleId;

    private Long attachmentId;

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
            "id=" + id +
            ", description='" + description + '\'' +
            ", publishDate=" + publishDate +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", biddingId=" + biddingId +
            ", biddingName='" + biddingName + '\'' +
            ", biddingDocNo='" + biddingDocNo + '\'' +
            ", emaillist=" + emaillist +
            ", biddingScheduleId=" + biddingScheduleId +
            ", attachmentId=" + attachmentId +
            '}';
    }
}

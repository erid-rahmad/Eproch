package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalAnnouncement} entity.
 */
public class MPrequalAnnouncementDTO extends AbstractAuditingDTO {
    
    private Long id;

    
    @Lob
    private String description;

    private ZonedDateTime publishDate;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long prequalificationId;
    private String prequalificationName, preqDocumentNo;

    private Long prequalificationScheduleId;
    private String prequalificationScheduleName;

    private Long attachmentId;
    private String attachmentName, attachmentUrl;
    
    public Long getId() {
        return id;
    }

    public String getPrequalificationScheduleName() {
        return prequalificationScheduleName;
    }

    public void setPrequalificationScheduleName(String prequalificationScheduleName) {
        this.prequalificationScheduleName = prequalificationScheduleName;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getPreqDocumentNo() {
        return preqDocumentNo;
    }

    public void setPreqDocumentNo(String preqDocumentNo) {
        this.preqDocumentNo = preqDocumentNo;
    }

    public String getPrequalificationName() {
        return prequalificationName;
    }

    public void setPrequalificationName(String prequalificationName) {
        this.prequalificationName = prequalificationName;
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

    public Long getPrequalificationId() {
        return prequalificationId;
    }

    public void setPrequalificationId(Long mPrequalificationInformationId) {
        this.prequalificationId = mPrequalificationInformationId;
    }

    public Long getPrequalificationScheduleId() {
        return prequalificationScheduleId;
    }

    public void setPrequalificationScheduleId(Long mPrequalificationScheduleId) {
        this.prequalificationScheduleId = mPrequalificationScheduleId;
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

        MPrequalAnnouncementDTO mPrequalAnnouncementDTO = (MPrequalAnnouncementDTO) o;
        if (mPrequalAnnouncementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalAnnouncementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalAnnouncementDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", prequalificationId=" + getPrequalificationId() +
            ", prequalificationScheduleId=" + getPrequalificationScheduleId() +
            ", attachmentId=" + getAttachmentId() +
            "}";
    }
}

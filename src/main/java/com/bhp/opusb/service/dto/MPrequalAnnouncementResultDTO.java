package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalAnnouncementResult} entity.
 */
public class MPrequalAnnouncementResultDTO extends AbstractAuditingDTO {
    
    private Long id;

    
    @Lob
    private String descriptionPass;

    
    @Lob
    private String descriptionFail;

    private ZonedDateTime publishDate;

    private String documentNo;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long prequalificationId;

    private Long prequalificationScheduleId;

    private Long attachmentId;
    private String attachmentName;
    
    public Long getId() {
        return id;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescriptionPass() {
        return descriptionPass;
    }

    public void setDescriptionPass(String descriptionPass) {
        this.descriptionPass = descriptionPass;
    }

    public String getDescriptionFail() {
        return descriptionFail;
    }

    public void setDescriptionFail(String descriptionFail) {
        this.descriptionFail = descriptionFail;
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

        MPrequalAnnouncementResultDTO mPrequalAnnouncementResultDTO = (MPrequalAnnouncementResultDTO) o;
        if (mPrequalAnnouncementResultDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalAnnouncementResultDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalAnnouncementResultDTO{" +
            "id=" + getId() +
            ", descriptionPass='" + getDescriptionPass() + "'" +
            ", descriptionFail='" + getDescriptionFail() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", prequalificationId=" + getPrequalificationId() +
            ", prequalificationScheduleId=" + getPrequalificationScheduleId() +
            ", attachmentId=" + getAttachmentId() +
            "}";
    }
}

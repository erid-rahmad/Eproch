package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalificationResult} entity.
 */
public class MPrequalificationResultDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long announcementResultId;
    private String announcementResultName;
    private String attachmentName;
    private String attachmentId;
    private String attachmentUrl;

    private Long prequalificationId;
    private String prequalificationName;
    private String prequalificationStatus;
    private String prequalificationNo;
    private String prequalificationType;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    private Long vendorId;

    private Long submissionId;
    
    public Long getId() {
        return id;
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

    public String getPrequalificationType() {
        return prequalificationType;
    }

    public void setPrequalificationType(String prequalificationType) {
        this.prequalificationType = prequalificationType;
    }

    public String getPrequalificationNo() {
        return prequalificationNo;
    }

    public void setPrequalificationNo(String prequalificationNo) {
        this.prequalificationNo = prequalificationNo;
    }

    public String getPrequalificationStatus() {
        return prequalificationStatus;
    }

    public void setPrequalificationStatus(String prequalificationStatus) {
        this.prequalificationStatus = prequalificationStatus;
    }

    public String getPrequalificationName() {
        return prequalificationName;
    }

    public void setPrequalificationName(String prequalificationName) {
        this.prequalificationName = prequalificationName;
    }

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

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAnnouncementResultName() {
        return announcementResultName;
    }

    public void setAnnouncementResultName(String announcementResultName) {
        this.announcementResultName = announcementResultName;
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

    public void setAnnouncementResultId(Long mPrequalAnnouncementResultId) {
        this.announcementResultId = mPrequalAnnouncementResultId;
    }

    public Long getPrequalificationId() {
        return prequalificationId;
    }

    public void setPrequalificationId(Long mPrequalificationInformationId) {
        this.prequalificationId = mPrequalificationInformationId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long mPrequalificationSubmissionId) {
        this.submissionId = mPrequalificationSubmissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPrequalificationResultDTO mPrequalificationResultDTO = (MPrequalificationResultDTO) o;
        if (mPrequalificationResultDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalificationResultDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalificationResultDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", announcementResultId=" + getAnnouncementResultId() +
            ", prequalificationId=" + getPrequalificationId() +
            ", vendorId=" + getVendorId() +
            ", submissionId=" + getSubmissionId() +
            "}";
    }
}

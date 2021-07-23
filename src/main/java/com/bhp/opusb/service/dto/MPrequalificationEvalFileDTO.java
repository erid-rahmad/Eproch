package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalificationEvalFile} entity.
 */
public class MPrequalificationEvalFileDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean active;


    private Long attachmentId;
    private String attachmentName, downloadUrl;

    private Long adOrganizationId;

    private Long prequalificationSubmissionId;

    private Long biddingSubCriteriaId;
    
    public Long getId() {
        return id;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
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

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long cAttachmentId) {
        this.attachmentId = cAttachmentId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getPrequalificationSubmissionId() {
        return prequalificationSubmissionId;
    }

    public void setPrequalificationSubmissionId(Long mPrequalificationSubmissionId) {
        this.prequalificationSubmissionId = mPrequalificationSubmissionId;
    }

    public Long getBiddingSubCriteriaId() {
        return biddingSubCriteriaId;
    }

    public void setBiddingSubCriteriaId(Long cBiddingSubCriteriaId) {
        this.biddingSubCriteriaId = cBiddingSubCriteriaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPrequalificationEvalFileDTO mPrequalificationEvalFileDTO = (MPrequalificationEvalFileDTO) o;
        if (mPrequalificationEvalFileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalificationEvalFileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalificationEvalFileDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", attachmentId=" + getAttachmentId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", prequalificationSubmissionId=" + getPrequalificationSubmissionId() +
            ", biddingSubCriteriaId=" + getBiddingSubCriteriaId() +
            "}";
    }
}

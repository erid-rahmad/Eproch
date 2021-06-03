package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MProposalAdministrationFile} entity.
 */
public class MProposalAdministrationFileDTO implements Serializable {

    private Long id;

    private UUID uid;

    private Boolean active = true;

    @JsonProperty("cAttachmentId")
    private Long cAttachmentId;

    @JsonProperty("cAttachmentName")
    private String cAttachmentName;
    private String attachmentUrl;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long biddingSubmissionId;
    private String biddingSubmissionName;

    private Long biddingSubCriteriaId;
    private String biddingSubCriteriaName;

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

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

    public Long getCAttachmentId() {
        return cAttachmentId;
    }

    public void setCAttachmentId(Long cAttachmentId) {
        this.cAttachmentId = cAttachmentId;
    }

    public String getCAttachmentName() {
        return cAttachmentName;
    }

    public void setCAttachmentName(String cAttachmentName) {
        this.cAttachmentName = cAttachmentName;
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

    public Long getBiddingSubmissionId() {
        return biddingSubmissionId;
    }

    public void setBiddingSubmissionId(Long mBiddingSubmissionId) {
        this.biddingSubmissionId = mBiddingSubmissionId;
    }

    public String getBiddingSubmissionName() {
        return biddingSubmissionName;
    }

    public void setBiddingSubmissionName(String biddingSubmissionName) {
        this.biddingSubmissionName = biddingSubmissionName;
    }

    public Long getBiddingSubCriteriaId() {
        return biddingSubCriteriaId;
    }

    public void setBiddingSubCriteriaId(Long cBiddingSubCriteriaId) {
        this.biddingSubCriteriaId = cBiddingSubCriteriaId;
    }

    public String getBiddingSubCriteriaName() {
        return biddingSubCriteriaName;
    }

    public void setBiddingSubCriteriaName(String biddingSubCriteriaName) {
        this.biddingSubCriteriaName = biddingSubCriteriaName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MProposalAdministrationFileDTO mProposalAdministrationFileDTO = (MProposalAdministrationFileDTO) o;
        if (mProposalAdministrationFileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mProposalAdministrationFileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MProposalAdministrationFileDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", cAttachmentId=" + getCAttachmentId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingSubmissionId=" + getBiddingSubmissionId() +
            ", biddingSubCriteriaId=" + getBiddingSubCriteriaId() +
            "}";
    }
}

package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPreBidMeetingAttachment} entity.
 */
public class MPreBidMeetingAttachmentDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean active = true;

    @JsonProperty("cAttachmentId")
    private Long cAttachmentId;

    @JsonProperty("cAttachmentName")
    private String cAttachmentName;

    @JsonProperty("cAttachmentUrl")
    private String cAttachmentUrl;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long preBidMeetingId;
    private String preBidMeetingName;
    
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

    public String getCAttachmentUrl() {
        return cAttachmentUrl;
    }

    public void setCAttachmentUrl(String cAttachmentUrl) {
        this.cAttachmentUrl = cAttachmentUrl;
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

    public Long getPreBidMeetingId() {
        return preBidMeetingId;
    }

    public void setPreBidMeetingId(Long mPreBidMeetingId) {
        this.preBidMeetingId = mPreBidMeetingId;
    }

    public String getPreBidMeetingName() {
        return preBidMeetingName;
    }

    public void setPreBidMeetingName(String preBidMeetingName) {
        this.preBidMeetingName = preBidMeetingName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPreBidMeetingAttachmentDTO mPreBidMeetingAttachmentDTO = (MPreBidMeetingAttachmentDTO) o;
        if (mPreBidMeetingAttachmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPreBidMeetingAttachmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPreBidMeetingAttachmentDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", cAttachmentId=" + getCAttachmentId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", preBidMeetingId=" + getPreBidMeetingId() +
            "}";
    }
}

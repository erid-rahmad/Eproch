package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingScheduleAttachment} entity.
 */
public class MBiddingScheduleAttachmentDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active = true;


    private Long cAttachmentId;
    private Long cAttachmentName;

    private Long adOrganizationId;
    private Long adOrganizationName;

    private Long biddingScheduleId;
    private Long biddingScheduleName;
    
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

    public Long getCAttachmentName() {
        return cAttachmentName;
    }

    public void setCAttachmentName(Long cAttachmentName) {
        this.cAttachmentName = cAttachmentName;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(Long adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getBiddingScheduleId() {
        return biddingScheduleId;
    }

    public void setBiddingScheduleId(Long mBiddingScheduleId) {
        this.biddingScheduleId = mBiddingScheduleId;
    }

    public Long getBiddingScheduleName() {
        return biddingScheduleName;
    }

    public void setBiddingScheduleName(Long biddingScheduleName) {
        this.biddingScheduleName = biddingScheduleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingScheduleAttachmentDTO mBiddingScheduleAttachmentDTO = (MBiddingScheduleAttachmentDTO) o;
        if (mBiddingScheduleAttachmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingScheduleAttachmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingScheduleAttachmentDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", cAttachmentId=" + getCAttachmentId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingScheduleId=" + getBiddingScheduleId() +
            "}";
    }
}

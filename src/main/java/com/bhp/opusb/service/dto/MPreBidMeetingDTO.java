package com.bhp.opusb.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPreBidMeeting} entity.
 */
public class MPreBidMeetingDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean active = true;


    private Long biddingScheduleId;
    private String biddingScheduleName;

    private Long adOrganizationId;
    private String adOrganizationName;
    
    @JsonProperty("mPreBidMeetingAttachments")
    private List<MPreBidMeetingAttachmentDTO> mPreBidMeetingAttachments = new ArrayList<>();

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

    public List<MPreBidMeetingAttachmentDTO> getMPreBidMeetingAttachments() {
        return mPreBidMeetingAttachments;
    }

    public void setMPreBidMeetingAttachments(List<MPreBidMeetingAttachmentDTO> mPreBidMeetingAttachments) {
        this.mPreBidMeetingAttachments = mPreBidMeetingAttachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPreBidMeetingDTO mPreBidMeetingDTO = (MPreBidMeetingDTO) o;
        if (mPreBidMeetingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPreBidMeetingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPreBidMeetingDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingScheduleId=" + getBiddingScheduleId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingInvitation} entity.
 */
public class MBiddingInvitationDTO implements Serializable {
    
    private Long id;

    private String invitationStatus;

    private String reason;

    private ZonedDateTime answerDate;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long announcementId;

    private Long biddingId;

    private Long vendorId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ZonedDateTime getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(ZonedDateTime answerDate) {
        this.answerDate = answerDate;
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

    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long cAnnouncementId) {
        this.announcementId = cAnnouncementId;
    }

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long mBiddingId) {
        this.biddingId = mBiddingId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingInvitationDTO mBiddingInvitationDTO = (MBiddingInvitationDTO) o;
        if (mBiddingInvitationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingInvitationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingInvitationDTO{" +
            "id=" + getId() +
            ", invitationStatus='" + getInvitationStatus() + "'" +
            ", reason='" + getReason() + "'" +
            ", answerDate='" + getAnswerDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", announcementId=" + getAnnouncementId() +
            ", biddingId=" + getBiddingId() +
            ", vendorId=" + getVendorId() +
            "}";
    }
}

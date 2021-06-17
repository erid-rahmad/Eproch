package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingInvitation} entity.
 */
public class MBiddingInvitationDTO extends AbstractAuditingDTO {

    private Long id;

    private String invitationStatus;

    private String reason;

    private ZonedDateTime answerDate;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;

    private Long announcementId;
    private ZonedDateTime announcementEndDate;

    private String announcementDescription;

    private ZonedDateTime announcementPublishDate;

    private Long biddingId;
    private String biddingName;


    private Long vendorId;
    private String vendorCode;
    private String vendorName;
    private String vendorType;

    private String location;

    public ZonedDateTime getAnnouncementEndDate() {
        return announcementEndDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAnnouncementEndDate(ZonedDateTime announcementEndDate) {
        this.announcementEndDate = announcementEndDate;
    }

    public String getAnnouncementDescription() {
        return announcementDescription;
    }

    public void setAnnouncementDescription(String announcementDescription) {
        this.announcementDescription = announcementDescription;
    }

    public ZonedDateTime getAnnouncementPublishDate() {
        return announcementPublishDate;
    }

    public void setAnnouncementPublishDate(ZonedDateTime announcementPublishDate) {
        this.announcementPublishDate = announcementPublishDate;
    }

    public String getBiddingName() {
        return biddingName;
    }

    public void setBiddingName(String biddingName) {
        this.biddingName = biddingName;
    }

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

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
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

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
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
            "id=" + id +
            ", invitationStatus='" + invitationStatus + '\'' +
            ", reason='" + reason + '\'' +
            ", answerDate=" + answerDate +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", announcementId=" + announcementId +
            ", announcementEndDate=" + announcementEndDate +
            ", announcementDescription='" + announcementDescription + '\'' +
            ", announcementPublishDate=" + announcementPublishDate +
            ", biddingId=" + biddingId +
            ", biddingName='" + biddingName + '\'' +
            ", vendorId=" + vendorId +
            ", vendorName='" + vendorName + '\'' +
            '}';
    }
}

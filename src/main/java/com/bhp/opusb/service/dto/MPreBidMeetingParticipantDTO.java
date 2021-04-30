package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPreBidMeetingParticipant} entity.
 */
public class MPreBidMeetingParticipantDTO extends AbstractAuditingDTO {
    
    private Long id;

    private Boolean attended;

    private ZonedDateTime registerDate;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long vendorId;
    private String vendorName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAttended() {
        return attended;
    }

    public void setAttended(Boolean attended) {
        this.attended = attended;
    }

    public ZonedDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(ZonedDateTime registerDate) {
        this.registerDate = registerDate;
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

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPreBidMeetingParticipantDTO mPreBidMeetingParticipantDTO = (MPreBidMeetingParticipantDTO) o;
        if (mPreBidMeetingParticipantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPreBidMeetingParticipantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPreBidMeetingParticipantDTO{" +
            "id=" + getId() +
            ", attended='" + isAttended() + "'" +
            ", registerDate='" + getRegisterDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", vendorId=" + getVendorId() +
            "}";
    }
}

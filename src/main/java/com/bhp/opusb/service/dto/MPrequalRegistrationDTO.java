package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalRegistration} entity.
 */
public class MPrequalRegistrationDTO extends AbstractAuditingDTO {
    
    private Long id;

    private String registrationStatus;

    private String reason;

    private ZonedDateTime answerDate;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long announcementId;
    private ZonedDateTime announcementEndDate;
    private String announcementDescription;
    private ZonedDateTime announcementPublishDate;

    private Long prequalificationId;
    private String prequalificationName;
    private String prequalificationType;

    private Long vendorId;
    private String vendorCode;
    private String vendorName;
    private String vendorType;
    
    public Long getId() {
        return id;
    }

    public String getPrequalificationType() {
        return prequalificationType;
    }

    public void setPrequalificationType(String prequalificationType) {
        this.prequalificationType = prequalificationType;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getPrequalificationName() {
        return prequalificationName;
    }

    public void setPrequalificationName(String prequalificationName) {
        this.prequalificationName = prequalificationName;
    }

    public ZonedDateTime getAnnouncementPublishDate() {
        return announcementPublishDate;
    }

    public void setAnnouncementPublishDate(ZonedDateTime announcementPublishDate) {
        this.announcementPublishDate = announcementPublishDate;
    }

    public String getAnnouncementDescription() {
        return announcementDescription;
    }

    public void setAnnouncementDescription(String announcementDescription) {
        this.announcementDescription = announcementDescription;
    }

    public ZonedDateTime getAnnouncementEndDate() {
        return announcementEndDate;
    }

    public void setAnnouncementEndDate(ZonedDateTime announcementEndDate) {
        this.announcementEndDate = announcementEndDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
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

    public void setAnnouncementId(Long mPrequalAnnouncementId) {
        this.announcementId = mPrequalAnnouncementId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPrequalRegistrationDTO mPrequalRegistrationDTO = (MPrequalRegistrationDTO) o;
        if (mPrequalRegistrationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalRegistrationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalRegistrationDTO{" +
            "id=" + getId() +
            ", registrationStatus='" + getRegistrationStatus() + "'" +
            ", reason='" + getReason() + "'" +
            ", answerDate='" + getAnswerDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", announcementId=" + getAnnouncementId() +
            ", prequalificationId=" + getPrequalificationId() +
            ", vendorId=" + getVendorId() +
            "}";
    }
}

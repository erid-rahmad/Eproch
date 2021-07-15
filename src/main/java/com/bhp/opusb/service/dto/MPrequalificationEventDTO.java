package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalificationEvent} entity.
 */
public class MPrequalificationEventDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;


    private Long prequalificationId;

    private Long adOrganizationId;

    private Long eventId;

    private Long methodId;
    
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

    public Long getPrequalificationId() {
        return prequalificationId;
    }

    public void setPrequalificationId(Long mPrequalificationInformationId) {
        this.prequalificationId = mPrequalificationInformationId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long cPrequalificationEventId) {
        this.eventId = cPrequalificationEventId;
    }

    public Long getMethodId() {
        return methodId;
    }

    public void setMethodId(Long cPrequalificationMethodId) {
        this.methodId = cPrequalificationMethodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPrequalificationEventDTO mPrequalificationEventDTO = (MPrequalificationEventDTO) o;
        if (mPrequalificationEventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalificationEventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalificationEventDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", prequalificationId=" + getPrequalificationId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", eventId=" + getEventId() +
            ", methodId=" + getMethodId() +
            "}";
    }
}

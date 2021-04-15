package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CEvent} entity.
 */
public class CEventDTO extends AbstractAuditingDTO {

    private Long id;

    @NotNull
    private String event;

    private String description;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long cProductClassificationId;

    public String getcProductClassificationName() {
        return cProductClassificationName;
    }

    public void setcProductClassificationName(String cProductClassificationName) {
        this.cProductClassificationName = cProductClassificationName;
    }

    private String cProductClassificationName;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getcProductClassificationId() {
        return cProductClassificationId;
    }

    public void setcProductClassificationId(Long cProductClassificationId) {
        this.cProductClassificationId = cProductClassificationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CEventDTO cEventDTO = (CEventDTO) o;
        if (cEventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cEventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CEventDTO{" +
            "id=" + id +
            ", event='" + event + '\'' +
            ", description='" + description + '\'' +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", cProductClassificationId=" + cProductClassificationId +
            ", cProductClassificationName='" + cProductClassificationName + '\'' +
            '}';
    }
}

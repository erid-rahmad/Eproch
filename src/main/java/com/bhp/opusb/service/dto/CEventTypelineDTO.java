package com.bhp.opusb.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CEventTypeline} entity.
 */
public class CEventTypelineDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String description;

    @NotNull
    @Min(value = 0)
    private Integer sequence;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long eventTypeId;
//    @JsonProperty("cEventId")
    private Long cEventId;
//    @JsonProperty("cEventName")
    private String cEventName;

    public Long getcEventId() {
        return cEventId;
    }

    public void setcEventId(Long cEventId) {
        this.cEventId = cEventId;
    }

    public String getcEventName() {
        return cEventName;
    }

    public void setcEventName(String cEventName) {
        this.cEventName = cEventName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long cEventTypeId) {
        this.eventTypeId = cEventTypeId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CEventTypelineDTO cEventTypelineDTO = (CEventTypelineDTO) o;
        if (cEventTypelineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cEventTypelineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CEventTypelineDTO{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", sequence=" + sequence +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", adOrganizationName='" + adOrganizationName + '\'' +
            ", eventTypeId=" + eventTypeId +
            ", cEventId=" + cEventId +
            '}';
    }
}

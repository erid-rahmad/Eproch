package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalificationSchedule} entity.
 */
public class MPrequalificationScheduleDTO extends AbstractAuditingDTO {
    
    private Long id;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private UUID uid;

    private Boolean active;

    private Long dateSetId;
    private ZonedDateTime actualStartDate;
    private ZonedDateTime actualEndDate;
    private String status;

    private Long prequalificationId;

    private Long adOrganizationId;

    private Long eventLineId;
    private String eventLineName;
    private Long adFormId;
    private String adFormName;
    
    public Long getId() {
        return id;
    }

    public String getAdFormName() {
        return adFormName;
    }

    public void setAdFormName(String adFormName) {
        this.adFormName = adFormName;
    }

    public Long getAdFormId() {
        return adFormId;
    }

    public void setAdFormId(Long adFormId) {
        this.adFormId = adFormId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventLineName() {
        return eventLineName;
    }

    public void setEventLineName(String eventLineName) {
        this.eventLineName = eventLineName;
    }

    public ZonedDateTime getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(ZonedDateTime actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public ZonedDateTime getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(ZonedDateTime actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Long getDateSetId() {
        return dateSetId;
    }

    public void setDateSetId(Long dateSetId) {
        this.dateSetId = dateSetId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
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

    public Long getEventLineId() {
        return eventLineId;
    }

    public void setEventLineId(Long cPrequalificationEventLineId) {
        this.eventLineId = cPrequalificationEventLineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPrequalificationScheduleDTO mPrequalificationScheduleDTO = (MPrequalificationScheduleDTO) o;
        if (mPrequalificationScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPrequalificationScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPrequalificationScheduleDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", prequalificationId=" + getPrequalificationId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", eventLineId=" + getEventLineId() +
            "}";
    }
}

package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.AdSchedulerTrigger;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AdTaskScheduler} entity.
 */
public class AdTaskSchedulerDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active;

    @NotNull
    private String name;

    @NotNull
    private AdSchedulerTrigger trigger;

    private String cronExpression;

    private Integer periodicCount;

    private String periodicUnit;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long adTaskId;
    private String adTaskName;
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdSchedulerTrigger getTrigger() {
        return trigger;
    }

    public void setTrigger(AdSchedulerTrigger trigger) {
        this.trigger = trigger;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getPeriodicCount() {
        return periodicCount;
    }

    public void setPeriodicCount(Integer periodicCount) {
        this.periodicCount = periodicCount;
    }

    public String getPeriodicUnit() {
        return periodicUnit;
    }

    public void setPeriodicUnit(String periodicUnit) {
        this.periodicUnit = periodicUnit;
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

    public Long getAdTaskId() {
        return adTaskId;
    }

    public void setAdTaskId(Long adTaskId) {
        this.adTaskId = adTaskId;
    }

    public String getAdTaskName() {
        return adTaskName;
    }

    public void setAdTaskName(String adTaskName) {
        this.adTaskName = adTaskName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdTaskSchedulerDTO adTaskSchedulerDTO = (AdTaskSchedulerDTO) o;
        if (adTaskSchedulerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adTaskSchedulerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdTaskSchedulerDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", trigger='" + getTrigger() + "'" +
            ", cronExpression='" + getCronExpression() + "'" +
            ", periodicCount=" + getPeriodicCount() +
            ", periodicUnit='" + getPeriodicUnit() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adTaskId=" + getAdTaskId() +
            "}";
    }
}

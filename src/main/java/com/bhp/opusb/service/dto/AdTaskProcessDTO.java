package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AdTaskProcess} entity.
 */
public class AdTaskProcessDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active;

    private Integer runSequence;

    private Boolean parallel;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long adTaskApplicationId;
    private String adTaskApplicationName;

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

    public Integer getRunSequence() {
        return runSequence;
    }

    public void setRunSequence(Integer runSequence) {
        this.runSequence = runSequence;
    }

    public Boolean isParallel() {
        return parallel;
    }

    public void setParallel(Boolean parallel) {
        this.parallel = parallel;
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

    public Long getAdTaskApplicationId() {
        return adTaskApplicationId;
    }

    public void setAdTaskApplicationId(Long adTaskApplicationId) {
        this.adTaskApplicationId = adTaskApplicationId;
    }

    public String getAdTaskApplicationName() {
        return adTaskApplicationName;
    }

    public void setAdTaskApplicationName(String adTaskApplicationName) {
        this.adTaskApplicationName = adTaskApplicationName;
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

        AdTaskProcessDTO adTaskProcessDTO = (AdTaskProcessDTO) o;
        if (adTaskProcessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adTaskProcessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdTaskProcessDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", runSequence=" + getRunSequence() +
            ", parallel='" + isParallel() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adTaskApplicationId=" + getAdTaskApplicationId() +
            ", adTaskId=" + getAdTaskId() +
            "}";
    }
}

package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CPrequalificationEventLine} entity.
 */
public class CPrequalificationEventLineDTO extends AbstractAuditingDTO {

    private Long id;

    private String description;

    @NotNull
    @Min(value = 0)
    private Integer sequence;

    private UUID uid;

    private Boolean active;


    private Long prequalificationEventId;

    private Long prequalificationStepId;

    private Long adOrganizationId;

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

    public Long getPrequalificationEventId() {
        return prequalificationEventId;
    }

    public void setPrequalificationEventId(Long cPrequalificationEventId) {
        this.prequalificationEventId = cPrequalificationEventId;
    }

    public Long getPrequalificationStepId() {
        return prequalificationStepId;
    }

    public void setPrequalificationStepId(Long cPrequalificationStepId) {
        this.prequalificationStepId = cPrequalificationStepId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CPrequalificationEventLineDTO cPrequalificationEventLineDTO = (CPrequalificationEventLineDTO) o;
        if (cPrequalificationEventLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cPrequalificationEventLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CPrequalificationEventLineDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", sequence=" + getSequence() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", prequalificationEventId=" + getPrequalificationEventId() +
            ", prequalificationStepId=" + getPrequalificationStepId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}

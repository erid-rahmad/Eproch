package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MContractTaskReviewers} entity.
 */
public class MContractTaskReviewersDTO extends AbstractAuditingDTO {

    private Long id;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long contractTaskId;

    private Long picId;

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

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getContractTaskId() {
        return contractTaskId;
    }

    public void setContractTaskId(Long mContractTaskId) {
        this.contractTaskId = mContractTaskId;
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long adUserId) {
        this.picId = adUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MContractTaskReviewersDTO mContractTaskReviewersDTO = (MContractTaskReviewersDTO) o;
        if (mContractTaskReviewersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mContractTaskReviewersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MContractTaskReviewersDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", contractTaskId=" + getContractTaskId() +
            ", picId=" + getPicId() +
            "}";
    }
}

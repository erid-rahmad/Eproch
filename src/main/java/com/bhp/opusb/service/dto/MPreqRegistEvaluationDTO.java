package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPreqRegistEvaluation} entity.
 */
public class MPreqRegistEvaluationDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    private String evaluation;


    private Long adOrganizationId;

    private Long vendorId;

    private Long prequalificationId;
    
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

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public Long getPrequalificationId() {
        return prequalificationId;
    }

    public void setPrequalificationId(Long mPrequalificationInformationId) {
        this.prequalificationId = mPrequalificationInformationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPreqRegistEvaluationDTO mPreqRegistEvaluationDTO = (MPreqRegistEvaluationDTO) o;
        if (mPreqRegistEvaluationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mPreqRegistEvaluationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MPreqRegistEvaluationDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", evaluation='" + getEvaluation() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", vendorId=" + getVendorId() +
            ", prequalificationId=" + getPrequalificationId() +
            "}";
    }
}

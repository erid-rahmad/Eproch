package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MContractTeamLine} entity.
 */
public class MContractTeamLineDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    @NotNull
    @Size(max = 10)
    private String position;


    private Long adOrganizationId;

    private Long contractTeamId;

    private Long adUserId;
    private String adUserName, adUserPosition, adUserEmail, adUserLastName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAdUserEmail() {
        return adUserEmail;
    }

    public void setAdUserEmail(String adUserEmail) {
        this.adUserEmail = adUserEmail;
    }

    public String getAdUserLastName() {
        return adUserLastName;
    }

    public void setAdUserLastName(String adUserLastName) {
        this.adUserLastName = adUserLastName;
    }

    public String getAdUserPosition() {
        return adUserPosition;
    }

    public void setAdUserPosition(String adUserPosition) {
        this.adUserPosition = adUserPosition;
    }

    public String getAdUserName() {
        return adUserName;
    }

    public void setAdUserName(String adUserName) {
        this.adUserName = adUserName;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getContractTeamId() {
        return contractTeamId;
    }

    public void setContractTeamId(Long mContractTeamId) {
        this.contractTeamId = mContractTeamId;
    }

    public Long getAdUserId() {
        return adUserId;
    }

    public void setAdUserId(Long adUserId) {
        this.adUserId = adUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MContractTeamLineDTO mContractTeamLineDTO = (MContractTeamLineDTO) o;
        if (mContractTeamLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mContractTeamLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MContractTeamLineDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", position='" + getPosition() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", contractTeamId=" + getContractTeamId() +
            ", adUserId=" + getAdUserId() +
            "}";
    }
}

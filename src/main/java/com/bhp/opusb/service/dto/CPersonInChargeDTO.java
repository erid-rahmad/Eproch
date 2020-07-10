package com.bhp.opusb.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CPersonInCharge} entity.
 */
@ApiModel(description = "The PIC entity.")
public class CPersonInChargeDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    @NotNull
    private String position;

    @NotNull
    private String phone;

    private Boolean functionary;

    private Boolean active;


    private Long userId;

    private String userLogin;

    private Long vendorId;

    private Long adOrganizationId;
    
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isFunctionary() {
        return functionary;
    }

    public void setFunctionary(Boolean functionary) {
        this.functionary = functionary;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
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

        CPersonInChargeDTO cPersonInChargeDTO = (CPersonInChargeDTO) o;
        if (cPersonInChargeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cPersonInChargeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CPersonInChargeDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", position='" + getPosition() + "'" +
            ", phone='" + getPhone() + "'" +
            ", functionary='" + isFunctionary() + "'" +
            ", active='" + isActive() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", vendorId=" + getVendorId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}

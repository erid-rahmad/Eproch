package com.bhp.opusb.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhp.opusb.domain.PersonInCharge} entity.
 */
@ApiModel(description = "The PIC entity.")
public class PersonInChargeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String position;

    @NotNull
    private String phone;


    private Long userId;

    private String userLogin;

    private Long vendorId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonInChargeDTO personInChargeDTO = (PersonInChargeDTO) o;
        if (personInChargeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personInChargeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonInChargeDTO{" +
            "id=" + getId() +
            ", position='" + getPosition() + "'" +
            ", phone='" + getPhone() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", vendorId=" + getVendorId() +
            "}";
    }
}

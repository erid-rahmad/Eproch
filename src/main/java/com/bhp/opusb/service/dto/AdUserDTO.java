package com.bhp.opusb.service.dto;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AdUser} entity.
 */
@ApiModel(description = "The extended User entity.")
public class AdUserDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active;

    @Size(max = 20)
    private String phone;

    @Size(max = 30)
    private String position;

    /**
     * Whether it is a vendor or not
     */
    @ApiModelProperty(value = "Whether it is a vendor or not")
    private Boolean vendor;

    private Integer failedLoginCount;

    private Instant lastLoginDate;

    private Long userId;

    /**
     * Virtual field that's referenced from jhi_user.login
     */
    private String userLogin;

    /**
     * This field is used for displaying the title in a dropdown item.
     */
    private String name;

    /**
     * Virtual field that's referenced from jhi_user.password
     */
    @Size(min = 8, max = 100)
    private String password;

    /**
     * Virtual field that's referenced from jhi_user.email
     */
    @Email
    @Size(min = 5, max = 254)
    private String email;

    /**
     * This field should not empty for a supplier's user
     */
    @ApiModelProperty(value = "This field should not empty for a supplier's user")
    private Long cVendorId;
    private String cVendorName;

    private Long adOrganizationId;
    private String adOrganizationName;
    
    private List<Long> businessCategoryIds;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean isVendor() {
        return vendor;
    }

    public void setVendor(Boolean vendor) {
        this.vendor = vendor;
    }

    public Integer getFailedLoginCount() {
        return failedLoginCount;
    }

    public void setFailedLoginCount(Integer failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    public Instant getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Instant lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getcVendorId() {
        return cVendorId;
    }

    public void setcVendorId(Long cVendorId) {
        this.cVendorId = cVendorId;
    }

    public String getcVendorName() {
        return cVendorName;
    }

    public void setcVendorName(String cVendorName) {
        this.cVendorName = cVendorName;
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

    public List<Long> getBusinessCategoryIds() {
        return businessCategoryIds;
    }

    public void setBusinessCategoryIds(List<Long> businessCategoryIds) {
        this.businessCategoryIds = businessCategoryIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdUserDTO adUserDTO = (AdUserDTO) o;
        if (adUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdUserDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", phone='" + getPhone() + "'" +
            ", position='" + getPosition() + "'" +
            ", vendor='" + isVendor() + "'" +
            ", failedLoginCount=" + getFailedLoginCount() +
            ", lastLoginDate='" + getLastLoginDate() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", cVendorId=" + getcVendorId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}

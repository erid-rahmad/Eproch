package com.bhp.opusb.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.bhp.opusb.domain.enumeration.VendorType;
import com.bhp.opusb.domain.enumeration.PaymentCategory;
import com.bhp.opusb.domain.enumeration.VendorApprovalStatus;

/**
 * A DTO for the {@link com.bhp.opusb.domain.Vendor} entity.
 */
@ApiModel(description = "The Vendor entity.")
public class VendorDTO implements Serializable {
    
    private Long id;

    private String code;

    @NotNull
    private String name;

    @NotNull
    private Long npwp;

    private Boolean branch;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    private String fax;

    private String website;

    @NotNull
    private VendorType type;

    @NotNull
    private PaymentCategory paymentCategory;

    @NotNull
    private VendorApprovalStatus approvalStatus;

    private Set<BusinessCategoryDTO> businessCategories = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNpwp() {
        return npwp;
    }

    public void setNpwp(Long npwp) {
        this.npwp = npwp;
    }

    public Boolean isBranch() {
        return branch;
    }

    public void setBranch(Boolean branch) {
        this.branch = branch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public VendorType getType() {
        return type;
    }

    public void setType(VendorType type) {
        this.type = type;
    }

    public PaymentCategory getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(PaymentCategory paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public VendorApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(VendorApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Set<BusinessCategoryDTO> getBusinessCategories() {
        return businessCategories;
    }

    public void setBusinessCategories(Set<BusinessCategoryDTO> businessCategories) {
        this.businessCategories = businessCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VendorDTO vendorDTO = (VendorDTO) o;
        if (vendorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vendorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VendorDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", npwp=" + getNpwp() +
            ", branch='" + isBranch() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", fax='" + getFax() + "'" +
            ", website='" + getWebsite() + "'" +
            ", type='" + getType() + "'" +
            ", paymentCategory='" + getPaymentCategory() + "'" +
            ", approvalStatus='" + getApprovalStatus() + "'" +
            ", businessCategories='" + getBusinessCategories() + "'" +
            "}";
    }
}

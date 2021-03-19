package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CVendorBusinessCat} entity.
 */
public class CVendorBusinessCatDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active;

    private CVendorDTO vendor;
    private Long vendorId;
    private String vendorName;

    private Long businessClassificationId;
    private String businessClassificationName;

    private Long businessCategoryId;
    private String businessCategoryName;

    private Long subBusinessCategoryId;
    private String subBusinessCategoryName;

    private Long adOrganizationId;
    private String adOrganizationName;

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

    public CVendorDTO getVendor() {
        return vendor;
    }

    public void setVendor(CVendorDTO vendor) {
        this.vendor = vendor;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Long getBusinessClassificationId() {
        return businessClassificationId;
    }

    public void setBusinessClassificationId(Long cBusinessCategoryId) {
        this.businessClassificationId = cBusinessCategoryId;
    }

    public String getBusinessClassificationName() {
        return businessClassificationName;
    }

    public void setBusinessClassificationName(String businessClassificationName) {
        this.businessClassificationName = businessClassificationName;
    }

    public Long getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(Long cBusinessCategoryId) {
        this.businessCategoryId = cBusinessCategoryId;
    }

    public String getBusinessCategoryName() {
        return businessCategoryName;
    }

    public void setBusinessCategoryName(String businessCategoryName) {
        this.businessCategoryName = businessCategoryName;
    }

    public Long getSubBusinessCategoryId() {
        return subBusinessCategoryId;
    }

    public void setSubBusinessCategoryId(Long cBusinessCategoryId) {
        this.subBusinessCategoryId = cBusinessCategoryId;
    }

    public String getSubBusinessCategoryName() {
        return subBusinessCategoryName;
    }

    public void setSubBusinessCategoryName(String subBusinessCategoryName) {
        this.subBusinessCategoryName = subBusinessCategoryName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CVendorBusinessCatDTO cVendorBusinessCatDTO = (CVendorBusinessCatDTO) o;
        if (cVendorBusinessCatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cVendorBusinessCatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CVendorBusinessCatDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", vendorId=" + getVendorId() +
            ", vendorName=" + getVendorName() +
            ", businessClassificationId=" + getBusinessClassificationId() +
            ", businessClassificationName=" + getBusinessClassificationName() +
            ", businessCategoryId=" + getBusinessCategoryId() +
            ", businessCategoryName=" + getBusinessCategoryName() +
            ", subBusinessCategoryId=" + getSubBusinessCategoryId() +
            ", subBusinessCategoryName=" + getSubBusinessCategoryName() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            "}";
    }
}

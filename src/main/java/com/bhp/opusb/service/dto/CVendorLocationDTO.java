package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CVendorLocation} entity.
 */
public class CVendorLocationDTO extends AbstractAuditingDTO {
    
    private Long id;

    private Boolean taxInvoiceAddress;

    private UUID uid;

    private Boolean active;


    private Long vendorId;

    private Long locationId;

    private Long adOrganizationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isTaxInvoiceAddress() {
        return taxInvoiceAddress;
    }

    public void setTaxInvoiceAddress(Boolean taxInvoiceAddress) {
        this.taxInvoiceAddress = taxInvoiceAddress;
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

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long cLocationId) {
        this.locationId = cLocationId;
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

        CVendorLocationDTO cVendorLocationDTO = (CVendorLocationDTO) o;
        if (cVendorLocationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cVendorLocationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CVendorLocationDTO{" +
            "id=" + getId() +
            ", taxInvoiceAddress='" + isTaxInvoiceAddress() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", vendorId=" + getVendorId() +
            ", locationId=" + getLocationId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}

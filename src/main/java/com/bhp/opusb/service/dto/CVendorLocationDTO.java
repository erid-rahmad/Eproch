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

    private Boolean shipAddress;

    private Boolean invoiceAddress;

    private Boolean payFromAddress;

    private UUID uid;

    private Boolean active;


    private Long vendorId;

    private Long locationId;
    private String locationName;
    private String locationAddress;
    private String locationPostalCode;
    private String locationCityName;
    private String locationRegionName;
    private String locationCountryName;

    private Long adOrganizationId;
    private String adOrganizationName;

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

    public Boolean isShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(Boolean shipAddress) {
        this.shipAddress = shipAddress;
    }

    public Boolean isInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(Boolean invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public Boolean isPayFromAddress() {
        return payFromAddress;
    }

    public void setPayFromAddress(Boolean payFromAddress) {
        this.payFromAddress = payFromAddress;
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

    public String getLocationName() {
        //return locationName;
        return (locationAddress + ", " + locationCityName + ", " + locationRegionName + ", " + locationCountryName + ", " + locationPostalCode);
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationPostalCode() {
        return locationPostalCode;
    }

    public void setLocationPostalCode(String locationPostalCode) {
        this.locationPostalCode = locationPostalCode;
    }

    public String getLocationCityName() {
        return locationCityName;
    }

    public void setLocationCityName(String locationCityName) {
        this.locationCityName = locationCityName;
    }

    public String getLocationRegionName() {
        return locationRegionName;
    }

    public void setLocationRegionName(String locationRegionName) {
        this.locationRegionName = locationRegionName;
    }

    public String getLocationCountryName() {
        return locationCountryName;
    }

    public void setLocationCountryName(String locationCountryName) {
        this.locationCountryName = locationCountryName;
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
            ", shipAddress='" + isShipAddress() + "'" +
            ", invoiceAddress='" + isInvoiceAddress() + "'" +
            ", payFromAddress='" + isPayFromAddress() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", vendorId=" + getVendorId() +
            ", locationId=" + getLocationId() +
            ", locationName=" + getLocationName() +
            ", locationAddress=" + getLocationAddress() +
            ", locationPostalCode=" + getLocationPostalCode() +
            ", locationCityName=" + getLocationCityName() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            "}";
    }
}

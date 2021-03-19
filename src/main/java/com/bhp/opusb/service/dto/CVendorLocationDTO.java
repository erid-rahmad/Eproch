package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CVendorLocation} entity.
 */
public class CVendorLocationDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Boolean taxInvoiceAddress;

    private Boolean shipAddress;

    private Boolean invoiceAddress;

    private Boolean payFromAddress;

    private UUID uid;

    private Boolean active;

    private Long vendorId;
    private String vendorCode;
    private String vendorRegisteredName;

    private Long locationId;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String postalCode;
    private String phone;
    private String fax;
    private String cityName;
    private String regionName;
    private String countryWithRegionName;
    private String countryWithoutRegionName;

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

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorRegisteredName() {
        return vendorRegisteredName;
    }

    public void setVendorRegisteredName(String vendorRegisteredName) {
        this.vendorRegisteredName = vendorRegisteredName;
    }

    public String getVendorName() {
        return vendorCode + " - " + vendorRegisteredName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long cLocationId) {
        this.locationId = cLocationId;
    }

    public String getLocationName() {
        if (address1 == null) {
            return null;
        }
        StringBuilder address = new StringBuilder(address1);
        if (address2 != null) {
            address.append(", ").append(address2);
        }
        if (address3 != null) {
            address.append(", ").append(address3);
        }
        if (address4 != null) {
            address.append(", ").append(address4);
        }
        if (postalCode != null) {
            address.append(", ").append(postalCode);
        }
        return address.toString();
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCountryName() {
        return regionName == null ? countryWithoutRegionName : countryWithRegionName;
    }

    public void setCountryWithRegionName(String countryWithRegionName) {
        this.countryWithRegionName = countryWithRegionName;
    }

    public void setCountryWithoutRegionName(String countryWithoutRegionName) {
        this.countryWithoutRegionName = countryWithoutRegionName;
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
            ", postalCode=" + postalCode +
            ", phone=" + getPhone() +
            ", fax=" + getFax() +
            ", cityName=" + getCityName() +
            ", regionName=" + getRegionName() +
            ", countryName=" + getCountryName() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            "}";
    }
}

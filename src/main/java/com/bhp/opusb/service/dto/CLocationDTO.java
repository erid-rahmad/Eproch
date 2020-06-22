package com.bhp.opusb.service.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CLocation} entity.
 */
public class CLocationDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private String streetAddress;

    private String postalCode;

    private Boolean taxInvoiceAddress;

    private Boolean active;


    private Long cityId;
    private String cityName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Boolean isTaxInvoiceAddress() {
        return taxInvoiceAddress;
    }

    public void setTaxInvoiceAddress(Boolean taxInvoiceAddress) {
        this.taxInvoiceAddress = taxInvoiceAddress;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cCityId) {
        this.cityId = cCityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CLocationDTO cLocationDTO = (CLocationDTO) o;
        if (cLocationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cLocationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CLocationDTO{" +
            "id=" + getId() +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", taxInvoiceAddress='" + isTaxInvoiceAddress() + "'" +
            ", active='" + isActive() + "'" +
            ", cityId='" + getCityId() + "'" +
            ", cityName=" + getCityName() +
            "}";
    }

    
}

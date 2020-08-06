package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CVendorTax} entity.
 */
public class CVendorTaxDTO extends AbstractAuditingDTO {
    
    private Long id;

    private Boolean isFaktur;

    private Boolean isPkp;

    private String rate;

    private UUID uid;

    private Boolean active;


    private Long vendorId;

    private Long taxCategoryId;

    private Long taxRateId;

    private Long adOrganizationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsFaktur() {
        return isFaktur;
    }

    public void setIsFaktur(Boolean isFaktur) {
        this.isFaktur = isFaktur;
    }

    public Boolean isIsPkp() {
        return isPkp;
    }

    public void setIsPkp(Boolean isPkp) {
        this.isPkp = isPkp;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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

    public Long getTaxCategoryId() {
        return taxCategoryId;
    }

    public void setTaxCategoryId(Long cTaxCategoryId) {
        this.taxCategoryId = cTaxCategoryId;
    }

    public Long getTaxRateId() {
        return taxRateId;
    }

    public void setTaxRateId(Long cTaxRateId) {
        this.taxRateId = cTaxRateId;
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

        CVendorTaxDTO cVendorTaxDTO = (CVendorTaxDTO) o;
        if (cVendorTaxDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cVendorTaxDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CVendorTaxDTO{" +
            "id=" + getId() +
            ", isFaktur='" + isIsFaktur() + "'" +
            ", isPkp='" + isIsPkp() + "'" +
            ", rate='" + getRate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", vendorId=" + getVendorId() +
            ", taxCategoryId=" + getTaxCategoryId() +
            ", taxRateId=" + getTaxRateId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}

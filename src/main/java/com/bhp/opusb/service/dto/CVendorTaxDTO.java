package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CVendorTax} entity.
 */
public class CVendorTaxDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private Boolean eInvoice;

    private Boolean taxableEmployers;

    private BigDecimal rate;

    private UUID uid;

    private Boolean active;

    private Long vendorId;
    private String vendorName;

    private Long taxId;
    private String taxName;

    private Long adOrganizationId;
    private String adOrganizationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEInvoice() {
        return eInvoice;
    }

    public void setEInvoice(Boolean eInvoice) {
        this.eInvoice = eInvoice;
    }

    public Boolean isTaxableEmployers() {
        return taxableEmployers;
    }

    public void setTaxableEmployers(Boolean taxableEmployers) {
        this.taxableEmployers = taxableEmployers;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
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

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long cTaxId) {
        this.taxId = cTaxId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
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
            ", eInvoice='" + isEInvoice() + "'" +
            ", taxableEmployers='" + isTaxableEmployers() + "'" +
            ", rate=" + getRate() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", vendorId=" + getVendorId() +
            ", taxId=" + getTaxId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}

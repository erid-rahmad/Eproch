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

    private UUID uid;

    private Boolean active;

    private Long vendorId;
    private String vendorName;

    private Long taxCategoryId;
    private String taxCategoryName;

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

    public Long getTaxCategoryId() {
        return taxCategoryId;
    }

    public void setTaxCategoryId(Long cTaxCategoryId) {
        this.taxCategoryId = cTaxCategoryId;
    }

    public String getTaxCategoryName() {
        return taxCategoryName;
    }

    public void setTaxCategoryName(String taxCategoryName) {
        this.taxCategoryName = taxCategoryName;
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
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", vendorId=" + getVendorId() +
            ", vendorName=" + getVendorName() +
            ", taxCategoryId=" + getTaxCategoryId() +
            ", taxCategoryName=" + getTaxCategoryName() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            "}";
    }
}

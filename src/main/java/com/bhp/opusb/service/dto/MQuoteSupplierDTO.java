package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MQuoteSupplier} entity.
 */
public class MQuoteSupplierDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    private LocalDate dateRequired;


    private Long quotationId;

    private Long businessClassificationId;

    private Long businessCategoryId;

    private Long businessSubCategoryId;

    private Long vendorId;
    
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

    public LocalDate getDateRequired() {
        return dateRequired;
    }

    public void setDateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long mRfqId) {
        this.quotationId = mRfqId;
    }

    public Long getBusinessClassificationId() {
        return businessClassificationId;
    }

    public void setBusinessClassificationId(Long cBusinessCategoryId) {
        this.businessClassificationId = cBusinessCategoryId;
    }

    public Long getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(Long cBusinessCategoryId) {
        this.businessCategoryId = cBusinessCategoryId;
    }

    public Long getBusinessSubCategoryId() {
        return businessSubCategoryId;
    }

    public void setBusinessSubCategoryId(Long cBusinessCategoryId) {
        this.businessSubCategoryId = cBusinessCategoryId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MQuoteSupplierDTO mQuoteSupplierDTO = (MQuoteSupplierDTO) o;
        if (mQuoteSupplierDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mQuoteSupplierDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MQuoteSupplierDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", dateRequired='" + getDateRequired() + "'" +
            ", quotationId=" + getQuotationId() +
            ", businessClassificationId=" + getBusinessClassificationId() +
            ", businessCategoryId=" + getBusinessCategoryId() +
            ", businessSubCategoryId=" + getBusinessSubCategoryId() +
            ", vendorId=" + getVendorId() +
            "}";
    }
}

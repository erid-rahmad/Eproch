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
    private String quotationNo, description, costCenterName, warehouseCode, currencyCode;
    private LocalDate dateTrx;

    private Long businessClassificationId;

    private Long businessCategoryId;

    private Long businessSubCategoryId;

    private Long vendorId;

    private String subDocStatus;
    
    public Long getId() {
        return id;
    }

    public String getSubDocStatus() {
        return subDocStatus;
    }

    public void setSubDocStatus(String subDocStatus) {
        this.subDocStatus = subDocStatus;
    }

    public LocalDate getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuotationNo() {
        return quotationNo;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
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

package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CVendorBankAcct} entity.
 */
public class CVendorBankAcctDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private String accountNo;

    private String name;

    private String branch;

    private UUID uid;

    private Boolean active;


    private Long vendorId;

    private Long bankId;

    private Long currencyId;

    private Long fileId;

    private Long adOrganizationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long cBankId) {
        this.bankId = cBankId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long cCurrencyId) {
        this.currencyId = cCurrencyId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long cAttachmentId) {
        this.fileId = cAttachmentId;
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

        CVendorBankAcctDTO cVendorBankAcctDTO = (CVendorBankAcctDTO) o;
        if (cVendorBankAcctDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cVendorBankAcctDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CVendorBankAcctDTO{" +
            "id=" + getId() +
            ", accountNo='" + getAccountNo() + "'" +
            ", name='" + getName() + "'" +
            ", branch='" + getBranch() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", vendorId=" + getVendorId() +
            ", bankId=" + getBankId() +
            ", currencyId=" + getCurrencyId() +
            ", fileId=" + getFileId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}

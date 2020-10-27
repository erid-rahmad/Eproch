package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CVendorBankAcct} entity.
 */
public class CVendorBankAcctDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String accountNo;

    @NotNull
    private String accountName;

    private String name;

    private String branch;

    private String bban;

    private String iban;

    private String description;

    private UUID uid;

    private Boolean active;


    private Long vendorId;
    private String vendorName;

    private Long bankId;
    private String bankName;

    private Long currencyId;
    private String currencyName;

    private Long fileId;
    private String fileName;

    private Long adOrganizationId;
    private String adOrganizationName;

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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public String getBban() {
        return bban;
    }

    public void setBban(String bban) {
        this.bban = bban;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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


    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
            ", accountName='" + getAccountName() + "'" +
            ", name='" + getName() + "'" +
            ", branch='" + getBranch() + "'" +
            ", bban='" + getBban() + "'" +
            ", iban='" + getIban() + "'" +
            ", description='" + getDescription() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", vendorId=" + getVendorId() +
            ", vendorName=" + getVendorName() +
            ", bankId=" + getBankId() +
            ", bankName=" + getBankName() +
            ", currencyId=" + getCurrencyId() +
            ", currencyName=" + getCurrencyName() +
            ", fileId=" + getFileId() +
            ", fileName=" + getFileName() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            "}";
    }
}

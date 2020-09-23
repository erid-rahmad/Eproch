package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.COrganizationInfo} entity.
 */
public class COrganizationInfoDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private String address;

    @NotNull
    private String taxId;

    private String bankAccount;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long parentOrganizationId;
    private String parentOrganizationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
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

    public Long getParentOrganizationId() {
        return parentOrganizationId;
    }

    public void setParentOrganizationId(Long cOrganizationId) {
        this.parentOrganizationId = cOrganizationId;
    }

    public String getParentOrganizationName() {
        return parentOrganizationName;
    }

    public void setParentOrganizationName(String parentOrganizationName) {
        this.parentOrganizationName = parentOrganizationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        COrganizationInfoDTO cOrganizationInfoDTO = (COrganizationInfoDTO) o;
        if (cOrganizationInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cOrganizationInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "COrganizationInfoDTO{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", taxId='" + getTaxId() + "'" +
            ", bankAccount='" + getBankAccount() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            ", parentOrganizationId=" + getParentOrganizationId() +
            ", parentOrganizationName=" + getParentOrganizationName() +
            "}";
    }

    
}

package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CProductCategoryAccount} entity.
 */
public class CProductCategoryAccountDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long assetAcctId;
    private String assetAcctName;

    private Long expenseAcctId;
    private String expenseAcctName;

    private Long productCategoryId;
    private String productCategoryName;
    
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

    public Long getAssetAcctId() {
        return assetAcctId;
    }

    public void setAssetAcctId(Long cElementValueId) {
        this.assetAcctId = cElementValueId;
    }

    public String getAssetAcctName() {
        return assetAcctName;
    }

    public void setAssetAcctName(String assetAcctName) {
        this.assetAcctName = assetAcctName;
    }

    public Long getExpenseAcctId() {
        return expenseAcctId;
    }

    public void setExpenseAcctId(Long cElementValueId) {
        this.expenseAcctId = cElementValueId;
    }

    public String getExpenseAcctName() {
        return expenseAcctName;
    }

    public void setExpenseAcctName(String expenseAcctName) {
        this.expenseAcctName = expenseAcctName;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long cProductCategoryId) {
        this.productCategoryId = cProductCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CProductCategoryAccountDTO cProductCategoryAccountDTO = (CProductCategoryAccountDTO) o;
        if (cProductCategoryAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cProductCategoryAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CProductCategoryAccountDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            ", assetAcctId=" + getAssetAcctId() +
            ", assetAcctName=" + getAssetAcctName() +
            ", expenseAcctId=" + getExpenseAcctId() +
            ", expenseAcctName=" + getExpenseAcctName() +
            ", productCategoryId=" + getProductCategoryId() +
            ", productCategoryName=" + getProductCategoryName() +
            "}";
    }

    
}

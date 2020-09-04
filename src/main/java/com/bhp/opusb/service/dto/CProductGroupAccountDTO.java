package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import com.bhp.opusb.domain.enumeration.Depreciation;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CProductGroupAccount} entity.
 */
public class CProductGroupAccountDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private Depreciation depreciation;

    @NotNull
    private Integer lifeYear;

    @NotNull
    private Integer lifeMonth;

    private UUID uid;

    private Boolean active;


    private Long assetAccountId;
    private String assetAccountName;

    private Long depreciationAccountId;
    private String depreciationAccountName;

    private Long productGroupId;
    private String productGroupName;

    private Long adOrganizationId;
    private String adOrganizationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Depreciation getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(Depreciation depreciation) {
        this.depreciation = depreciation;
    }

    public Integer getLifeYear() {
        return lifeYear;
    }

    public void setLifeYear(Integer lifeYear) {
        this.lifeYear = lifeYear;
    }

    public Integer getLifeMonth() {
        return lifeMonth;
    }

    public void setLifeMonth(Integer lifeMonth) {
        this.lifeMonth = lifeMonth;
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

    public Long getAssetAccountId() {
        return assetAccountId;
    }

    public void setAssetAccountId(Long cElementValueId) {
        this.assetAccountId = cElementValueId;
    }

    public String getAssetAccountName() {
        return assetAccountName;
    }

    public void setAssetAccountName(String assetAccountName) {
        this.assetAccountName = assetAccountName;
    }
    
    public Long getDepreciationAccountId() {
        return depreciationAccountId;
    }

    public void setDepreciationAccountId(Long cElementValueId) {
        this.depreciationAccountId = cElementValueId;
    }

    public String getDepreciationAccountName() {
        return depreciationAccountName;
    }

    public void setDepreciationAccountName(String depreciationAccountName) {
        this.depreciationAccountName = depreciationAccountName;
    }
    
    public Long getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(Long cProductGroupId) {
        this.productGroupId = cProductGroupId;
    }

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
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

        CProductGroupAccountDTO cProductGroupAccountDTO = (CProductGroupAccountDTO) o;
        if (cProductGroupAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cProductGroupAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CProductGroupAccountDTO{" +
            "id=" + getId() +
            ", depreciation='" + getDepreciation() + "'" +
            ", lifeYear=" + getLifeYear() +
            ", lifeMonth=" + getLifeMonth() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", assetAccountId=" + getAssetAccountId() +
            ", assetAccountName='" + getAssetAccountName() + "'" +
            ", depreciationAccountId=" + getDepreciationAccountId() +
            ", depreciationAccountName='" + getDepreciationAccountName() + "'" +
            ", productGroupId=" + getProductGroupId() +
            ", productGroupName='" + getProductGroupName() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName='" + getAdOrganizationName() + "'" +
            "}";
    }

    
}

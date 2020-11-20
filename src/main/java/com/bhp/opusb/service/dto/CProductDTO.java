package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CProduct} entity.
 */
public class CProductDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 50)
    private String code;

    @NotNull
    private String name;

    private String description;

    @NotNull
    @Size(max = 1)
    private String type;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long productClassificationId;
    private String productClassificationName;

    private Long productCategoryId;
    private String productCategoryName;

    private Long productSubCategoryId;
    private String productSubCategoryName;

    private Long assetAcctId;
    private String assetAcctName;

    private Long expenseAcctId;
    private String expenseAcctName;

    private Long uomId;
    private String uomName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getProductClassificationId() {
        return productClassificationId;
    }

    public void setProductClassificationId(Long cProductClassificationId) {
        this.productClassificationId = cProductClassificationId;
    }

    public String getProductClassificationName() {
        return productClassificationName;
    }

    public void setProductClassificationName(String productClassificationName) {
        this.productClassificationName = productClassificationName;
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

    public Long getProductSubCategoryId() {
        return productSubCategoryId;
    }

    public void setProductSubCategoryId(Long cProductCategoryId) {
        this.productSubCategoryId = cProductCategoryId;
    }

    public String getProductSubCategoryName() {
        return productSubCategoryName;
    }

    public void setProductSubCategoryName(String productSubCategoryName) {
        this.productSubCategoryName = productSubCategoryName;
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

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long cUnitOfMeasureId) {
        this.uomId = cUnitOfMeasureId;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CProductDTO cProductDTO = (CProductDTO) o;
        if (cProductDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cProductDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CProductDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", productClassificationId=" + getProductClassificationId() +
            ", productCategoryId=" + getProductCategoryId() +
            ", productSubCategoryId=" + getProductSubCategoryId() +
            ", assetAcctId=" + getAssetAcctId() +
            ", expenseAcctId=" + getExpenseAcctId() +
            ", uomId=" + getUomId() +
            "}";
    }
}

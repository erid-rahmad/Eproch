package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MShoppingCartItem} entity.
 */
public class MShoppingCartItemDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal quantity;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long mProductId;
    private String mProductCode;
    private String mProductShortName;
    private String mProductDescription;

    private Long mShoppingCartId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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

    public Long getMProductId() {
        return mProductId;
    }

    public void setMProductId(Long cProductId) {
        this.mProductId = cProductId;
    }

    public String getMProductCode() {
        return mProductCode;
    }

    public void setMProductCode(String mProductCode) {
        this.mProductCode = mProductCode;
    }

    public String getMProductShortName() {
        return mProductShortName;
    }

    public void setMProductShortName(String mProductShortName) {
        this.mProductShortName = mProductShortName;
    }

    public String getMProductDescription() {
        return mProductDescription;
    }

    public void setMProductDescription(String mProductDescription) {
        this.mProductDescription = mProductDescription;
    }

    public String getMProductName() {
        return mProductCode + " - " + mProductShortName;
    }

    public Long getMShoppingCartId() {
        return mShoppingCartId;
    }

    public void setMShoppingCartId(Long mShoppingCartId) {
        this.mShoppingCartId = mShoppingCartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MShoppingCartItemDTO)) {
            return false;
        }

        return id != null && id.equals(((MShoppingCartItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MShoppingCartItemDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", mProductId=" + getMProductId() +
            ", mShoppingCartId=" + getMShoppingCartId() +
            "}";
    }
}

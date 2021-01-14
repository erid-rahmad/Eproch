package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty("mProductCatalogId")
    private Long mProductCatalogId;

    @JsonProperty("mProductCatalogName")
    private String mProductCatalogName;

    @JsonProperty("mShoppingCartId")
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

    public Long getMProductCatalogId() {
        return mProductCatalogId;
    }

    public void setMProductCatalogId(Long mProductCatalogId) {
        this.mProductCatalogId = mProductCatalogId;
    }

    public String getMProductCatalogName() {
        return mProductCatalogName;
    }

    public void setMProductCatalogName(String mProductCatalogName) {
        this.mProductCatalogName = mProductCatalogName;
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
            ", mProductCatalogId=" + getMProductCatalogId() +
            ", mShoppingCartId=" + getMShoppingCartId() +
            "}";
    }
}

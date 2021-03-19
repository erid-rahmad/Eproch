package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingSubItemLine} entity.
 */
public class MBiddingSubItemLineDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal price;

    private BigDecimal amount;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long productId;
    private String productName;

    private Long uomId;
    private String uomName;

    private Long biddingSubItemId;
    
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long cProductId) {
        this.productId = cProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Long getBiddingSubItemId() {
        return biddingSubItemId;
    }

    public void setBiddingSubItemId(Long mBiddingSubItemId) {
        this.biddingSubItemId = mBiddingSubItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingSubItemLineDTO mBiddingSubItemLineDTO = (MBiddingSubItemLineDTO) o;
        if (mBiddingSubItemLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingSubItemLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingSubItemLineDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            ", amount=" + getAmount() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", productId=" + getProductId() +
            ", uomId=" + getUomId() +
            ", biddingSubItemId=" + getBiddingSubItemId() +
            "}";
    }
}

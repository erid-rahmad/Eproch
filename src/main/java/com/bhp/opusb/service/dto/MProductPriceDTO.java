package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MProductPrice} entity.
 */
public class MProductPriceDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private BigDecimal minQty;

    private BigDecimal maxQty;

    private BigDecimal price;

    private UUID uid;

    private Boolean active;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMinQty() {
        return minQty;
    }

    public void setMinQty(BigDecimal minQty) {
        this.minQty = minQty;
    }

    public BigDecimal getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(BigDecimal maxQty) {
        this.maxQty = maxQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MProductPriceDTO)) {
            return false;
        }

        return id != null && id.equals(((MProductPriceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MProductPriceDTO{" +
            "id=" + getId() +
            ", minQty=" + getMinQty() +
            ", maxQty=" + getMaxQty() +
            ", price=" + getPrice() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

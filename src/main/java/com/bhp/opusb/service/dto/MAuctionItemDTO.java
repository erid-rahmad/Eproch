package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MAuctionItem} entity.
 */
public class MAuctionItemDTO extends AbstractAuditingDTO {
    
    private Long id;

    private BigDecimal quantity;

    private BigDecimal ceilingPrice;

    /**
     * Is quantity * ceilingPrice
     */
    @ApiModelProperty(value = "Is quantity * ceilingPrice")
    private BigDecimal amount;

    /**
     * The multiple of the amount allowed to bid.
     */
    @ApiModelProperty(value = "The multiple of the amount allowed to bid.")
    private BigDecimal bidDecrement;

    /**
     * The minimum difference to bottom that is allowed in making bids.
     * ie. When the participant #1 bids at the price of 1000, and protectBackBuffer
     * is set to 500, then the other participants can only bids at least started from 1500.
     */
    @ApiModelProperty(value = "The minimum difference to bottom that is allowed in making bids.\nie. When the participant #1 bids at the price of 1000, and protectBackBuffer\nis set to 500, then the other participants can only bids at least started from 1500.")
    private BigDecimal protectBackBuffer;

    /**
     * The minimum difference to top that is allowed in making bids.
     * ie. When the participant #1 bids at the price of 1000, and protectBackBuffer
     * is set to 500, then the other participants can only bids at least started from 8500.
     */
    @ApiModelProperty(value = "The minimum difference to top that is allowed in making bids.\nie. When the participant #1 bids at the price of 1000, and protectBackBuffer\nis set to 500, then the other participants can only bids at least started from 8500.")
    private BigDecimal protectFrontBuffer;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long auctionId;
    private String auctionName;

    private Long productId;
    private String productCode;
    private String productName;

    private Long uomId;
    private String uomName;
    
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

    public BigDecimal getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBidDecrement() {
        return bidDecrement;
    }

    public void setBidDecrement(BigDecimal bidDecrement) {
        this.bidDecrement = bidDecrement;
    }

    public BigDecimal getProtectBackBuffer() {
        return protectBackBuffer;
    }

    public void setProtectBackBuffer(BigDecimal protectBackBuffer) {
        this.protectBackBuffer = protectBackBuffer;
    }

    public BigDecimal getProtectFrontBuffer() {
        return protectFrontBuffer;
    }

    public void setProtectFrontBuffer(BigDecimal protectFrontBuffer) {
        this.protectFrontBuffer = protectFrontBuffer;
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

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long mAuctionId) {
        this.auctionId = mAuctionId;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long cProductId) {
        this.productId = cProductId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MAuctionItemDTO mAuctionItemDTO = (MAuctionItemDTO) o;
        if (mAuctionItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mAuctionItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MAuctionItemDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", ceilingPrice=" + getCeilingPrice() +
            ", amount=" + getAmount() +
            ", bidDecrement=" + getBidDecrement() +
            ", protectBackBuffer=" + getProtectBackBuffer() +
            ", protectFrontBuffer=" + getProtectFrontBuffer() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", auctionId=" + getAuctionId() +
            ", productId=" + getProductId() +
            ", uomId=" + getUomId() +
            "}";
    }
}

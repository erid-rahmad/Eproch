package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MAuctionSubmission} entity.
 */
public class MAuctionSubmissionDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private BigDecimal price;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long auctionItemId;
    private String auctionItemName;
    private String auctionItemUom;
    private BigDecimal auctionItemQuantity;
    private BigDecimal auctionItemCeilingPrice;
    private BigDecimal auctionItemBidDecrement;
    private BigDecimal auctionItemBackBuffer;
    private BigDecimal auctionItemFrontBuffer;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(Long mAuctionItemId) {
        this.auctionItemId = mAuctionItemId;
    }

    public String getAuctionItemName() {
        return auctionItemName;
    }

    public void setAuctionItemName(String auctionItemName) {
        this.auctionItemName = auctionItemName;
    }

    public String getAuctionItemUom() {
        return auctionItemUom;
    }

    public void setAuctionItemUom(String auctionItemUom) {
        this.auctionItemUom = auctionItemUom;
    }

    public BigDecimal getAuctionItemQuantity() {
        return auctionItemQuantity;
    }

    public void setAuctionItemQuantity(BigDecimal auctionItemQuantity) {
        this.auctionItemQuantity = auctionItemQuantity;
    }

    public BigDecimal getAuctionItemCeilingPrice() {
        return auctionItemCeilingPrice;
    }

    public void setAuctionItemCeilingPrice(BigDecimal auctionItemCeilingPrice) {
        this.auctionItemCeilingPrice = auctionItemCeilingPrice;
    }

    public BigDecimal getAuctionItemBidDecrement() {
        return auctionItemBidDecrement;
    }

    public void setAuctionItemBidDecrement(BigDecimal auctionItemBidDecrement) {
        this.auctionItemBidDecrement = auctionItemBidDecrement;
    }

    public BigDecimal getAuctionItemBackBuffer() {
        return auctionItemBackBuffer;
    }

    public void setAuctionItemBackBuffer(BigDecimal auctionItemBackBuffer) {
        this.auctionItemBackBuffer = auctionItemBackBuffer;
    }

    public BigDecimal getAuctionItemFrontBuffer() {
        return auctionItemFrontBuffer;
    }

    public void setAuctionItemFrontBuffer(BigDecimal auctionItemFrontBuffer) {
        this.auctionItemFrontBuffer = auctionItemFrontBuffer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MAuctionSubmissionDTO mAuctionSubmissionDTO = (MAuctionSubmissionDTO) o;
        if (mAuctionSubmissionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mAuctionSubmissionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MAuctionSubmissionDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", auctionItemId=" + getAuctionItemId() +
            "}";
    }
}

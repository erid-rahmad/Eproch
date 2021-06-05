package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBidNegoPriceLine} entity.
 */
public class MBidNegoPriceLineDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    @NotNull
    private BigDecimal priceNegotiation;

    @NotNull
    private BigDecimal totalNegotiationPrice;

    @NotNull
    private BigDecimal negotiationPercentage;


    private Long bidNegoPriceId;

    private Long biddingLineId,quantity;
    private String uomName,productName;
    private BigDecimal ceilingPrice,totalCeilingPrice;

    private Long proposalLineId;
    private BigDecimal proposedPrice,totalPriceSubmission;
    
    public Long getId() {
        return id;
    }

    public BigDecimal getTotalPriceSubmission() {
        return totalPriceSubmission;
    }

    public void setTotalPriceSubmission(BigDecimal totalPriceSubmission) {
        this.totalPriceSubmission = totalPriceSubmission;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public BigDecimal getTotalCeilingPrice() {
        return totalCeilingPrice;
    }

    public void setTotalCeilingPrice(BigDecimal totalCeilingPrice) {
        this.totalCeilingPrice = totalCeilingPrice;
    }

    public BigDecimal getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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

    public BigDecimal getPriceNegotiation() {
        return priceNegotiation;
    }

    public void setPriceNegotiation(BigDecimal priceNegotiation) {
        this.priceNegotiation = priceNegotiation;
    }

    public BigDecimal getTotalNegotiationPrice() {
        return totalNegotiationPrice;
    }

    public void setTotalNegotiationPrice(BigDecimal totalNegotiationPrice) {
        this.totalNegotiationPrice = totalNegotiationPrice;
    }

    public BigDecimal getNegotiationPercentage() {
        return negotiationPercentage;
    }

    public void setNegotiationPercentage(BigDecimal negotiationPercentage) {
        this.negotiationPercentage = negotiationPercentage;
    }

    public Long getBidNegoPriceId() {
        return bidNegoPriceId;
    }

    public void setBidNegoPriceId(Long mBidNegoPriceId) {
        this.bidNegoPriceId = mBidNegoPriceId;
    }

    public Long getBiddingLineId() {
        return biddingLineId;
    }

    public void setBiddingLineId(Long mBiddingLineId) {
        this.biddingLineId = mBiddingLineId;
    }

    public Long getProposalLineId() {
        return proposalLineId;
    }

    public void setProposalLineId(Long mProposalPriceLineId) {
        this.proposalLineId = mProposalPriceLineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBidNegoPriceLineDTO mBidNegoPriceLineDTO = (MBidNegoPriceLineDTO) o;
        if (mBidNegoPriceLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBidNegoPriceLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBidNegoPriceLineDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", priceNegotiation=" + getPriceNegotiation() +
            ", totalNegotiationPrice=" + getTotalNegotiationPrice() +
            ", negotiationPercentage=" + getNegotiationPercentage() +
            ", bidNegoPriceId=" + getBidNegoPriceId() +
            ", biddingLineId=" + getBiddingLineId() +
            ", proposalLineId=" + getProposalLineId() +
            "}";
    }
}

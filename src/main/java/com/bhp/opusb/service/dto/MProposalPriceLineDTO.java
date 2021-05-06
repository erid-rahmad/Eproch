package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MProposalPriceLine} entity.
 */
public class MProposalPriceLineDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private BigDecimal proposedPrice;

    @NotNull
    private BigDecimal totalPriceSubmission;

    @NotNull
    private LocalDate deliveryDate;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long proposalPriceId;
    private String proposalPriceName;

    private Long biddingLineId;
    private String biddingLineName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public BigDecimal getTotalPriceSubmission() {
        return totalPriceSubmission;
    }

    public void setTotalPriceSubmission(BigDecimal totalPriceSubmission) {
        this.totalPriceSubmission = totalPriceSubmission;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public Long getProposalPriceId() {
        return proposalPriceId;
    }

    public void setProposalPriceId(Long mProposalPriceId) {
        this.proposalPriceId = mProposalPriceId;
    }

    public String getProposalPriceName() {
        return proposalPriceName;
    }

    public void setProposalPriceName(String proposalPriceName) {
        this.proposalPriceName = proposalPriceName;
    }

    public Long getBiddingLineId() {
        return biddingLineId;
    }

    public void setBiddingLineId(Long mBiddingLineId) {
        this.biddingLineId = mBiddingLineId;
    }

    public String getBiddingLineName() {
        return biddingLineName;
    }

    public void setBiddingLineName(String biddingLineName) {
        this.biddingLineName = biddingLineName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MProposalPriceLineDTO mProposalPriceLineDTO = (MProposalPriceLineDTO) o;
        if (mProposalPriceLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mProposalPriceLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MProposalPriceLineDTO{" +
            "id=" + getId() +
            ", proposedPrice=" + getProposedPrice() +
            ", totalPriceSubmission=" + getTotalPriceSubmission() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", proposalPriceId=" + getProposalPriceId() +
            ", biddingLineId=" + getBiddingLineId() +
            "}";
    }
}

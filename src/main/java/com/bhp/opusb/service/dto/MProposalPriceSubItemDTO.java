package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MProposalPriceSubItem} entity.
 */
public class MProposalPriceSubItemDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private BigDecimal proposedPrice;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long biddingSubItemLineId;
    private String biddingSubItemLineName;

    private Long proposalPriceLineId;
    private String proposalPriceLineName;
    
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

    public Long getBiddingSubItemLineId() {
        return biddingSubItemLineId;
    }

    public void setBiddingSubItemLineId(Long mBiddingSubItemLineId) {
        this.biddingSubItemLineId = mBiddingSubItemLineId;
    }

    public String getBiddingSubItemLineName() {
        return biddingSubItemLineName;
    }

    public void setBiddingSubItemLineName(String biddingSubItemLineName) {
        this.biddingSubItemLineName = biddingSubItemLineName;
    }

    public Long getProposalPriceLineId() {
        return proposalPriceLineId;
    }

    public void setProposalPriceLineId(Long mProposalPriceLineId) {
        this.proposalPriceLineId = mProposalPriceLineId;
    }

    public String getProposalPriceLineName() {
        return proposalPriceLineName;
    }

    public void setProposalPriceLineName(String proposalPriceLineName) {
        this.proposalPriceLineName = proposalPriceLineName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MProposalPriceSubItemDTO mProposalPriceSubItemDTO = (MProposalPriceSubItemDTO) o;
        if (mProposalPriceSubItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mProposalPriceSubItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MProposalPriceSubItemDTO{" +
            "id=" + getId() +
            ", proposedPrice=" + getProposedPrice() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingSubItemLineId=" + getBiddingSubItemLineId() +
            ", proposalPriceLineId=" + getProposalPriceLineId() +
            "}";
    }
}

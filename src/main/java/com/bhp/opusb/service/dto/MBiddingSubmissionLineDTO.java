package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingSubmissionLine} entity.
 */
public class MBiddingSubmissionLineDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private BigDecimal proposedPrice;

    @NotNull
    private BigDecimal totalPriceSubmission;

    @NotNull
    private LocalDate deliveryDate;

    private UUID uid;

    private Boolean active;


    private Long biddingLineId;

    private Long adOrganizationId;

    private Long mBiddingSubmissionId;
    
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

    public Long getBiddingLineId() {
        return biddingLineId;
    }

    public void setBiddingLineId(Long mBiddingLineId) {
        this.biddingLineId = mBiddingLineId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getMBiddingSubmissionId() {
        return mBiddingSubmissionId;
    }

    public void setMBiddingSubmissionId(Long mBiddingSubmissionId) {
        this.mBiddingSubmissionId = mBiddingSubmissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingSubmissionLineDTO)) {
            return false;
        }

        return id != null && id.equals(((MBiddingSubmissionLineDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MBiddingSubmissionLineDTO{" +
            "id=" + getId() +
            ", proposedPrice=" + getProposedPrice() +
            ", totalPriceSubmission=" + getTotalPriceSubmission() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingLineId=" + getBiddingLineId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", mBiddingSubmissionId=" + getMBiddingSubmissionId() +
            "}";
    }
}

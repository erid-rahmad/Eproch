package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MSubmissionSubItem} entity.
 */
public class MSubmissionSubItemDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private BigDecimal proposedPrice;

    private UUID uid;

    private Boolean active;


    private Long biddingSubItemId;

    private Long adOrganizationId;

    private Long mBiddingSubmissionLineId;
    
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

    public Long getBiddingSubItemId() {
        return biddingSubItemId;
    }

    public void setBiddingSubItemId(Long mBiddingSubItemId) {
        this.biddingSubItemId = mBiddingSubItemId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getMBiddingSubmissionLineId() {
        return mBiddingSubmissionLineId;
    }

    public void setMBiddingSubmissionLineId(Long mBiddingSubmissionLineId) {
        this.mBiddingSubmissionLineId = mBiddingSubmissionLineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MSubmissionSubItemDTO)) {
            return false;
        }

        return id != null && id.equals(((MSubmissionSubItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MSubmissionSubItemDTO{" +
            "id=" + getId() +
            ", proposedPrice=" + getProposedPrice() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingSubItemId=" + getBiddingSubItemId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", mBiddingSubmissionLineId=" + getMBiddingSubmissionLineId() +
            "}";
    }
}

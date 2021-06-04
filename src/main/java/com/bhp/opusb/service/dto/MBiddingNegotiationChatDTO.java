package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingNegotiationChat} entity.
 */
public class MBiddingNegotiationChatDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    @Lob
    private String vendorText;

    @Lob
    private String buyerText;


    private Long adOrganizationId;

    private Long negotiationLineId;

    private Long biddingId;

    private Long vendorId;

    private Long attachmentId;
    
    public Long getId() {
        return id;
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

    public String getVendorText() {
        return vendorText;
    }

    public void setVendorText(String vendorText) {
        this.vendorText = vendorText;
    }

    public String getBuyerText() {
        return buyerText;
    }

    public void setBuyerText(String buyerText) {
        this.buyerText = buyerText;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getNegotiationLineId() {
        return negotiationLineId;
    }

    public void setNegotiationLineId(Long mBiddingNegotiationLineId) {
        this.negotiationLineId = mBiddingNegotiationLineId;
    }

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long mBiddingId) {
        this.biddingId = mBiddingId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long cAttachmentId) {
        this.attachmentId = cAttachmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingNegotiationChatDTO mBiddingNegotiationChatDTO = (MBiddingNegotiationChatDTO) o;
        if (mBiddingNegotiationChatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingNegotiationChatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingNegotiationChatDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", vendorText='" + getVendorText() + "'" +
            ", buyerText='" + getBuyerText() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", negotiationLineId=" + getNegotiationLineId() +
            ", biddingId=" + getBiddingId() +
            ", vendorId=" + getVendorId() +
            ", attachmentId=" + getAttachmentId() +
            "}";
    }
}

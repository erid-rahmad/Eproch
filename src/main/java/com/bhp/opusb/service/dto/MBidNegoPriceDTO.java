package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBidNegoPrice} entity.
 */
public class MBidNegoPriceDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    @NotNull
    private BigDecimal negotiationPrice;


    private Long attachmentId;
    private String fileName, downloadUrl;

    private Long biddingId;

    private Long priceProposalId;

    private Long negotiationLineId;

    private List<MBidNegoPriceLineDTO> line;
    
    public Long getId() {
        return id;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<MBidNegoPriceLineDTO> getLine() {
        return line;
    }

    public void setLine(List<MBidNegoPriceLineDTO> line) {
        this.line = line;
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

    public BigDecimal getNegotiationPrice() {
        return negotiationPrice;
    }

    public void setNegotiationPrice(BigDecimal negotiationPrice) {
        this.negotiationPrice = negotiationPrice;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long cAttachmentId) {
        this.attachmentId = cAttachmentId;
    }

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long mBiddingId) {
        this.biddingId = mBiddingId;
    }

    public Long getPriceProposalId() {
        return priceProposalId;
    }

    public void setPriceProposalId(Long mProposalPriceId) {
        this.priceProposalId = mProposalPriceId;
    }

    public Long getNegotiationLineId() {
        return negotiationLineId;
    }

    public void setNegotiationLineId(Long mBiddingNegotiationLineId) {
        this.negotiationLineId = mBiddingNegotiationLineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBidNegoPriceDTO mBidNegoPriceDTO = (MBidNegoPriceDTO) o;
        if (mBidNegoPriceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBidNegoPriceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBidNegoPriceDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", negotiationPrice=" + getNegotiationPrice() +
            ", attachmentId=" + getAttachmentId() +
            ", biddingId=" + getBiddingId() +
            ", priceProposalId=" + getPriceProposalId() +
            ", negotiationLineId=" + getNegotiationLineId() +
            "}";
    }
}

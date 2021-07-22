package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MProposalPrice} entity.
 */
public class MProposalPriceDTO extends AbstractAuditingDTO {

    private Long id;

    @Size(max = 10)
    private String documentAction="DRF";

    @Size(max = 12)
    private String documentStatus="DRF";

    @NotNull
    private BigDecimal proposedPrice;

    @NotNull
    private BigDecimal ceilingPrice;

    private UUID uid;

    private Boolean active = true;


    private Long biddingSubmissionId;
    private String biddingSubmissionName;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long attachmentId;
    private String attachmentName;
    private String attachmentUrl;

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public BigDecimal getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
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

    public Long getBiddingSubmissionId() {
        return biddingSubmissionId;
    }

    public void setBiddingSubmissionId(Long mBiddingSubmissionId) {
        this.biddingSubmissionId = mBiddingSubmissionId;
    }

    public String getBiddingSubmissionName() {
        return biddingSubmissionName;
    }

    public void setBiddingSubmissionName(String biddingSubmissionName) {
        this.biddingSubmissionName = biddingSubmissionName;
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

        MProposalPriceDTO mProposalPriceDTO = (MProposalPriceDTO) o;
        if (mProposalPriceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mProposalPriceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MProposalPriceDTO{" +
            "id=" + getId() +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", proposedPrice=" + getProposedPrice() +
            ", ceilingPrice=" + getCeilingPrice() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingSubmissionId=" + getBiddingSubmissionId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", attachmentId=" + getAttachmentId() +
            "}";
    }
}

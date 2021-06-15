package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MAuctionInvitation} entity.
 */
public class MAuctionInvitationDTO extends AbstractAuditingDTO {
    
    private Long id;

    private ZonedDateTime dateTrx = ZonedDateTime.now();

    @Size(max = 30)
    private String documentNo;

    @Size(max = 10)
    private String documentAction;

    /**
     * By default is SMT (submit). Available statuses are: ACC (Accept) and DCL (Decline).
     */
    @NotNull
    @Size(max = 12)
    @ApiModelProperty(value = "By default is DRF (draft). Available statuses are: ACC (Accept) and DCL (Decline).", required = true)
    private String documentStatus = "DRF";

    /**
     * Whether the supplier accept the invitation or not.
     */
    @ApiModelProperty(value = "Whether the supplier accept the invitation or not.")
    private Boolean approved;

    private Boolean processed;

    private ZonedDateTime dateApprove;

    private ZonedDateTime dateReject;

    private String rejectedReason;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long auctionId;
    private String auctionDocumentNo;
    private String auctionDocumentStatus;
    private String auctionName;
    private Long auctionCurrencyId;
    private Long auctionContentId;
    private Long auctionPrerequisiteId;
    private Long auctionRuleId;
    private ZonedDateTime auctionRuleStartDate;

    private Long documentTypeId;
    private String documentTypeName;

    private Long vendorId;
    private String vendorName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(ZonedDateTime dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
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

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public ZonedDateTime getDateApprove() {
        return dateApprove;
    }

    public void setDateApprove(ZonedDateTime dateApprove) {
        this.dateApprove = dateApprove;
    }

    public ZonedDateTime getDateReject() {
        return dateReject;
    }

    public void setDateReject(ZonedDateTime dateReject) {
        this.dateReject = dateReject;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
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

    public String getAuctionDocumentNo() {
        return auctionDocumentNo;
    }

    public void setAuctionDocumentNo(String auctionDocumentNo) {
        this.auctionDocumentNo = auctionDocumentNo;
    }

    public String getAuctionDocumentStatus() {
        return auctionDocumentStatus;
    }

    public void setAuctionDocumentStatus(String auctionDocumentStatus) {
        this.auctionDocumentStatus = auctionDocumentStatus;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public Long getAuctionCurrencyId() {
        return auctionCurrencyId;
    }

    public void setAuctionCurrencyId(Long auctionCurrencyId) {
        this.auctionCurrencyId = auctionCurrencyId;
    }

    public Long getAuctionContentId() {
        return auctionContentId;
    }

    public void setAuctionContentId(Long auctionContentId) {
        this.auctionContentId = auctionContentId;
    }

    public Long getAuctionPrerequisiteId() {
        return auctionPrerequisiteId;
    }

    public void setAuctionPrerequisiteId(Long auctionPrerequisiteId) {
        this.auctionPrerequisiteId = auctionPrerequisiteId;
    }

    public Long getAuctionRuleId() {
        return auctionRuleId;
    }

    public void setAuctionRuleId(Long auctionRuleId) {
        this.auctionRuleId = auctionRuleId;
    }

    public ZonedDateTime getAuctionRuleStartDate() {
        return auctionRuleStartDate;
    }

    public void setAuctionRuleStartDate(ZonedDateTime auctionRuleStartDate) {
        this.auctionRuleStartDate = auctionRuleStartDate;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long cDocumentTypeId) {
        this.documentTypeId = cDocumentTypeId;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MAuctionInvitationDTO mAuctionInvitationDTO = (MAuctionInvitationDTO) o;
        if (mAuctionInvitationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mAuctionInvitationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MAuctionInvitationDTO{" +
            "id=" + getId() +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", dateApprove='" + getDateApprove() + "'" +
            ", dateReject='" + getDateReject() + "'" +
            ", rejectedReason='" + getRejectedReason() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", auctionId=" + getAuctionId() +
            ", documentTypeId=" + getDocumentTypeId() +
            ", vendorId=" + getVendorId() +
            "}";
    }
}

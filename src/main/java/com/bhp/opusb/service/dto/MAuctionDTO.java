package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MAuction} entity.
 */
public class MAuctionDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    private String description;

    private ZonedDateTime dateTrx = ZonedDateTime.now();

    @Size(max = 30)
    private String documentNo;

    @NotNull
    @Size(max = 10)
    private String documentAction = "PBS";

    @NotNull
    @Size(max = 12)
    private String documentStatus = "DRF";

    private Boolean approved;

    private Boolean processed;

    private ZonedDateTime dateApprove;

    private ZonedDateTime dateReject;

    private String rejectedReason;

    private UUID uid;

    private Boolean active = true;


    private Long ruleId;
    private ZonedDateTime ruleStartDate;
    private String ruleBidImprovementUnit;
    private String ruleTieBidsRule;

    private Long contentId;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long currencyId;
    private String currencyName;

    private Long costCenterId;
    private String costCenterName;

    private Long ownerUserId;
    private String ownerName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long mAuctionRuleId) {
        this.ruleId = mAuctionRuleId;
    }

    public ZonedDateTime getRuleStartDate() {
        return ruleStartDate;
    }

    public void setRuleStartDate(ZonedDateTime ruleStartDate) {
        this.ruleStartDate = ruleStartDate;
    }

    public String getRuleBidImprovementUnit() {
        return ruleBidImprovementUnit;
    }

    public void setRuleBidImprovementUnit(String ruleBidImprovementUnit) {
        this.ruleBidImprovementUnit = ruleBidImprovementUnit;
    }

    public String getRuleTieBidsRule() {
        return ruleTieBidsRule;
    }

    public void setRuleTieBidsRule(String ruleTieBidsRule) {
        this.ruleTieBidsRule = ruleTieBidsRule;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long mAuctionContentId) {
        this.contentId = mAuctionContentId;
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

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long cCurrencyId) {
        this.currencyId = cCurrencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Long getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(Long cCostCenterId) {
        this.costCenterId = cCostCenterId;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MAuctionDTO mAuctionDTO = (MAuctionDTO) o;
        if (mAuctionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mAuctionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MAuctionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
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
            ", ruleId=" + getRuleId() +
            ", contentId=" + getContentId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", currencyId=" + getCurrencyId() +
            ", costCenterId=" + getCostCenterId() +
            ", ownerUserId=" + getOwnerUserId() +
            "}";
    }
}

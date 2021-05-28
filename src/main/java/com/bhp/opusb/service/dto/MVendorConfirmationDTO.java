package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorConfirmation} entity.
 */
public class MVendorConfirmationDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    private Long biddingId;
    private String biddingNo;
    private String biddingTitle;
    private String biddingTypeName;

    private Long adOrganizationId;

    private Long currencyId;
    private String currencyName;
    private Long amount;

    private Long costCenterId;
    private String costCenterName;

    private Long picId;
    private String picName;

    private Integer selectedWinners;
    private String status;

    private Long latestContractId;
    
    public Long getId() {
        return id;
    }

    public Long getLatestContractId() {
        return latestContractId;
    }

    public void setLatestContractId(Long latestContractId) {
        this.latestContractId = latestContractId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getSelectedWinners() {
        return selectedWinners;
    }

    public void setSelectedWinners(Integer selectedWinners) {
        this.selectedWinners = selectedWinners;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getBiddingTypeName() {
        return biddingTypeName;
    }

    public void setBiddingTypeName(String biddingTypeName) {
        this.biddingTypeName = biddingTypeName;
    }

    public String getBiddingTitle() {
        return biddingTitle;
    }

    public void setBiddingTitle(String biddingTitle) {
        this.biddingTitle = biddingTitle;
    }

    public String getBiddingNo() {
        return biddingNo;
    }

    public void setBiddingNo(String biddingNo) {
        this.biddingNo = biddingNo;
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

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long mBiddingId) {
        this.biddingId = mBiddingId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long cCurrencyId) {
        this.currencyId = cCurrencyId;
    }

    public Long getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(Long cCostCenterId) {
        this.costCenterId = cCostCenterId;
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long adUserId) {
        this.picId = adUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVendorConfirmationDTO mVendorConfirmationDTO = (MVendorConfirmationDTO) o;
        if (mVendorConfirmationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVendorConfirmationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVendorConfirmationDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingId=" + getBiddingId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", currencyId=" + getCurrencyId() +
            ", costCenterId=" + getCostCenterId() +
            ", picId=" + getPicId() +
            "}";
    }
}

package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MAuctionParticipant} entity.
 */
public class MAuctionParticipantDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long auctionId;
    private String auctionName;
    private String auctionDocumentNo;
    private Long auctionCurrencyId;
    private String auctionCurrencyName;
    private String auctionStartDate;

    private Long userUserId;
    private String userName;
    private String userEmail;

    private Long vendorId;
    private String vendorName;
    
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

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public String getAuctionDocumentNo() {
        return auctionDocumentNo;
    }

    public void setAuctionDocumentNo(String auctionDocumentNo) {
        this.auctionDocumentNo = auctionDocumentNo;
    }

    public Long getAuctionCurrencyId() {
        return auctionCurrencyId;
    }

    public void setAuctionCurrencyId(Long auctionCurrencyId) {
        this.auctionCurrencyId = auctionCurrencyId;
    }

    public String getAuctionCurrencyName() {
        return auctionCurrencyName;
    }

    public void setAuctionCurrencyName(String auctionCurrencyName) {
        this.auctionCurrencyName = auctionCurrencyName;
    }

    public String getAuctionStartDate() {
        return auctionStartDate;
    }

    public void setAuctionStartDate(String auctionStartDate) {
        this.auctionStartDate = auctionStartDate;
    }

    public Long getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(Long userUserId) {
        this.userUserId = userUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

        MAuctionParticipantDTO mAuctionParticipantDTO = (MAuctionParticipantDTO) o;
        if (mAuctionParticipantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mAuctionParticipantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MAuctionParticipantDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", auctionId=" + getAuctionId() +
            ", userId=" + getUserUserId() +
            ", vendorId=" + getVendorId() +
            "}";
    }
}

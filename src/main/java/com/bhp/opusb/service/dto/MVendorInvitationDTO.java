package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorInvitation} entity.
 */
public class MVendorInvitationDTO extends AbstractAuditingDTO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active = true;


    private Long biddingId;
    private String biddingName;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long businessClassificationId;
    private String businessClassificationName;

    private Long businessCategoryId;
    private String businessCategoryName;

    private Long businessSubCategoryId;
    private String businessSubCategoryName;

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

    public Long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Long mBiddingId) {
        this.biddingId = mBiddingId;
    }

    public String getBiddingName() {
        return biddingName;
    }

    public void setBiddingName(String biddingName) {
        this.biddingName = biddingName;
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

    public Long getBusinessClassificationId() {
        return businessClassificationId;
    }

    public void setBusinessClassificationId(Long cBusinessCategoryId) {
        this.businessClassificationId = cBusinessCategoryId;
    }

    public String getBusinessClassificationName() {
        return businessClassificationName;
    }

    public void setBusinessClassificationName(String businessClassificationName) {
        this.businessClassificationName = businessClassificationName;
    }

    public Long getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(Long cBusinessCategoryId) {
        this.businessCategoryId = cBusinessCategoryId;
    }

    public String getBusinessCategoryName() {
        return businessCategoryName;
    }

    public void setBusinessCategoryName(String businessCategoryName) {
        this.businessCategoryName = businessCategoryName;
    }

    public Long getBusinessSubCategoryId() {
        return businessSubCategoryId;
    }

    public void setBusinessSubCategoryId(Long cBusinessCategoryId) {
        this.businessSubCategoryId = cBusinessCategoryId;
    }

    public String getBusinessSubCategoryName() {
        return businessSubCategoryName;
    }

    public void setBusinessSubCategoryName(String businessSubCategoryName) {
        this.businessSubCategoryName = businessSubCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVendorInvitationDTO mVendorInvitationDTO = (MVendorInvitationDTO) o;
        if (mVendorInvitationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVendorInvitationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVendorInvitationDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingId=" + getBiddingId() +
            ", biddingName=" + getBiddingName() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            ", businessClassificationId=" + getBusinessClassificationId() +
            ", businessClassificationName=" + getBusinessClassificationName() +
            ", businessCategoryId=" + getBusinessCategoryId() +
            ", businessCategoryName=" + getBusinessCategoryName() +
            ", businessSubCategoryId=" + getBusinessSubCategoryId() +
            ", businessSubCategoryName=" + getBusinessSubCategoryName() +
            "}";
    }
}

package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorScoring} entity.
 */
public class MVendorScoringDTO extends AbstractAuditingDTO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal percentage;

    private UUID uid;

    private Boolean active;


    private Long biddingId;
    private String biddingName;

    private Long adOrganizationId;
    private String adOrganizationName;

    private Long biddingCriteriaId;
    private String biddingCriteriaName;

    private Long biddingSubCriteriaId;
    private String biddingSubCriteriaName;

    private Long adUserUserId;
    private String adUserUserName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
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

    public Long getBiddingCriteriaId() {
        return biddingCriteriaId;
    }

    public void setBiddingCriteriaId(Long cBiddingCriteriaId) {
        this.biddingCriteriaId = cBiddingCriteriaId;
    }

    public String getBiddingCriteriaName() {
        return biddingCriteriaName;
    }

    public void setBiddingCriteriaName(String biddingCriteriaName) {
        this.biddingCriteriaName = biddingCriteriaName;
    }

    public Long getBiddingSubCriteriaId() {
        return biddingSubCriteriaId;
    }

    public void setBiddingSubCriteriaId(Long cBiddingSubCriteriaId) {
        this.biddingSubCriteriaId = cBiddingSubCriteriaId;
    }

    public String getBiddingSubCriteriaName() {
        return biddingSubCriteriaName;
    }

    public void setBiddingSubCriteriaName(String biddingSubCriteriaName) {
        this.biddingSubCriteriaName = biddingSubCriteriaName;
    }

    public Long getAdUserUserId() {
        return adUserUserId;
    }

    public void setAdUserUserId(Long adUserUserId) {
        this.adUserUserId = adUserUserId;
    }

    public String getAdUserUserName() {
        return adUserUserName;
    }

    public void setAdUserUserName(String adUserUserName) {
        this.adUserUserName = adUserUserName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVendorScoringDTO mVendorScoringDTO = (MVendorScoringDTO) o;
        if (mVendorScoringDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVendorScoringDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVendorScoringDTO{" +
            "id=" + getId() +
            ", percentage=" + getPercentage() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", biddingId=" + getBiddingId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingCriteriaId=" + getBiddingCriteriaId() +
            ", biddingSubCriteriaId=" + getBiddingSubCriteriaId() +
            ", adUserUserId=" + getAdUserUserId() +
            ", adUserUserName=" + getAdUserUserName() +
            "}";
    }
}

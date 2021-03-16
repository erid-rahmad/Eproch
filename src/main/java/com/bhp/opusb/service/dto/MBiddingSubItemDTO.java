package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingSubItem} entity.
 */
public class MBiddingSubItemDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal totalAmount;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long biddingLineId;
    private String biddingLineName;

    private Long productId;
    private String productName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public Long getBiddingLineId() {
        return biddingLineId;
    }

    public void setBiddingLineId(Long mBiddingLineId) {
        this.biddingLineId = mBiddingLineId;
    }

    public String getBiddingLineName() {
        return biddingLineName;
    }

    public void setBiddingLineName(String biddingLineName) {
        this.biddingLineName = biddingLineName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long cProductId) {
        this.productId = cProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingSubItemDTO mBiddingSubItemDTO = (MBiddingSubItemDTO) o;
        if (mBiddingSubItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingSubItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingSubItemDTO{" +
            "id=" + getId() +
            ", totalAmount=" + getTotalAmount() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adOrganizationName=" + getAdOrganizationName() +
            ", biddingLineId=" + getBiddingLineId() +
            ", biddingLineName=" + getBiddingLineName() +
            ", productId=" + getProductId() +
            ", productName=" + getProductName() +
            "}";
    }
}
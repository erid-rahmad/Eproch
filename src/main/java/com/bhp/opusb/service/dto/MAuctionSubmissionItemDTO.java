package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MAuctionSubmissionItem} entity.
 */
public class MAuctionSubmissionItemDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private BigDecimal price = new BigDecimal(0);

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long auctionSubmissionId;

    private Long auctionItemId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Long getAuctionSubmissionId() {
        return auctionSubmissionId;
    }

    public void setAuctionSubmissionId(Long mAuctionSubmissionId) {
        this.auctionSubmissionId = mAuctionSubmissionId;
    }

    public Long getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(Long mAuctionItemId) {
        this.auctionItemId = mAuctionItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MAuctionSubmissionItemDTO mAuctionSubmissionItemDTO = (MAuctionSubmissionItemDTO) o;
        if (mAuctionSubmissionItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mAuctionSubmissionItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MAuctionSubmissionItemDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", auctionSubmissionId=" + getAuctionSubmissionId() +
            ", auctionItemId=" + getAuctionItemId() +
            "}";
    }
}

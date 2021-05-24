package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MAuctionContent} entity.
 */
public class MAuctionContentDTO extends AbstractAuditingDTO {
    
    private Long id;

    @Lob
    private String description;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long auctionId;
    private String auctionName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MAuctionContentDTO mAuctionContentDTO = (MAuctionContentDTO) o;
        if (mAuctionContentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mAuctionContentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MAuctionContentDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", auctionId=" + getAuctionId() +
            "}";
    }
}

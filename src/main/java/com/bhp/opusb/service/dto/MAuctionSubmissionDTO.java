package com.bhp.opusb.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MAuctionSubmission} entity.
 */
public class MAuctionSubmissionDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long auctionId;
    private String auctionName;
    private String auctionDocumentNo;
    private String auctionDocumentStatus;

    private Long vendorId;
    private String vendorName;

    private List<MAuctionSubmissionItemDTO> submissionItems = new ArrayList<>();
    
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

    public String getAuctionDocumentStatus() {
        return auctionDocumentStatus;
    }

    public void setAuctionDocumentStatus(String auctionDocumentStatus) {
        this.auctionDocumentStatus = auctionDocumentStatus;
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

    public List<MAuctionSubmissionItemDTO> getSubmissionItems() {
        return submissionItems;
    }

    public void setSubmissionItems(List<MAuctionSubmissionItemDTO> submissionItems) {
        this.submissionItems = submissionItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MAuctionSubmissionDTO mAuctionSubmissionDTO = (MAuctionSubmissionDTO) o;
        if (mAuctionSubmissionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mAuctionSubmissionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MAuctionSubmissionDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", auctionId=" + getAuctionId() +
            ", vendorId=" + getVendorId() +
            "}";
    }
}

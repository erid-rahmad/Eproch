package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.Size;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorConfirmationLine} entity.
 */
public class MVendorConfirmationLineDTO extends AbstractAuditingDTO {
    
    private Long id;

    private UUID uid;

    private Boolean active = true;

    @Size(max = 10)
    private String status;


    private Long adOrganizationId;

    private Long vendorId;
    private String vendorName;

    private Long biddingEvalResultId;

    private Long vendorConfirmationId;
    private Long vendorConfirmationBiddingId;
    private String vendorConfirmationBiddingName;
    private String vendorConfirmationBiddingNo;
    private Long vendorConfirmationCostCenterId;
    private Long vendorConfirmationPicId;
    
    private Long negoLineId;
    private BigDecimal negoAmount;
    
    public Long getId() {
        return id;
    }

    public Long getNegoLineId() {
        return negoLineId;
    }

    public void setNegoLineId(Long negoLineId) {
        this.negoLineId = negoLineId;
    }

    public BigDecimal getNegoAmount() {
        return negoAmount;
    }

    public void setNegoAmount(BigDecimal negoAmount) {
        this.negoAmount = negoAmount;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public Long getBiddingEvalResultId() {
        return biddingEvalResultId;
    }

    public void setBiddingEvalResultId(Long mBiddingEvalResultId) {
        this.biddingEvalResultId = mBiddingEvalResultId;
    }

    public Long getVendorConfirmationId() {
        return vendorConfirmationId;
    }

    public void setVendorConfirmationId(Long mVendorConfirmationId) {
        this.vendorConfirmationId = mVendorConfirmationId;
    }

    public Long getVendorConfirmationBiddingId() {
        return vendorConfirmationBiddingId;
    }

    public void setVendorConfirmationBiddingId(Long vendorConfirmationBiddingId) {
        this.vendorConfirmationBiddingId = vendorConfirmationBiddingId;
    }

    public String getVendorConfirmationBiddingName() {
        return vendorConfirmationBiddingName;
    }

    public void setVendorConfirmationBiddingName(String vendorConfirmationBiddingName) {
        this.vendorConfirmationBiddingName = vendorConfirmationBiddingName;
    }

    public String getVendorConfirmationBiddingNo() {
        return vendorConfirmationBiddingNo;
    }

    public void setVendorConfirmationBiddingNo(String vendorConfirmationBiddingNo) {
        this.vendorConfirmationBiddingNo = vendorConfirmationBiddingNo;
    }

    public Long getVendorConfirmationCostCenterId() {
        return vendorConfirmationCostCenterId;
    }

    public void setVendorConfirmationCostCenterId(Long vendorConfirmationCostCenterId) {
        this.vendorConfirmationCostCenterId = vendorConfirmationCostCenterId;
    }

    public Long getVendorConfirmationPicId() {
        return vendorConfirmationPicId;
    }

    public void setVendorConfirmationPicId(Long vendorConfirmationPicId) {
        this.vendorConfirmationPicId = vendorConfirmationPicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVendorConfirmationLineDTO mVendorConfirmationLineDTO = (MVendorConfirmationLineDTO) o;
        if (mVendorConfirmationLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVendorConfirmationLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVendorConfirmationLineDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", status='" + getStatus() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", vendorId=" + getVendorId() +
            ", biddingEvalResultId=" + getBiddingEvalResultId() +
            ", vendorConfirmationId=" + getVendorConfirmationId() +
            "}";
    }
}

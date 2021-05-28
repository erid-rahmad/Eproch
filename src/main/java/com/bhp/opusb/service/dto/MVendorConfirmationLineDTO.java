package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorConfirmationLine} entity.
 */
public class MVendorConfirmationLineDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    @Size(max = 10)
    private String status;


    private Long adOrganizationId;

    private Long vendorId;
    private String vendorName;

    private Long biddingEvalResultId;

    private Long vendorConfirmationId;
    
    public Long getId() {
        return id;
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

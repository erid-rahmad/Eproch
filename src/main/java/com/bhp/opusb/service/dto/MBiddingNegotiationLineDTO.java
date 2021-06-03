package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingNegotiationLine} entity.
 */
public class MBiddingNegotiationLineDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private String negotiationStatus;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long negotiationId;

    private Long biddingEvalResultId;
    private String evalStatus;

    private Long vendorId;
    private String vendorName;
    
    public Long getId() {
        return id;
    }

    public String getEvalStatus() {
        return evalStatus;
    }

    public void setEvalStatus(String evalStatus) {
        this.evalStatus = evalStatus;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNegotiationStatus() {
        return negotiationStatus;
    }

    public void setNegotiationStatus(String negotiationStatus) {
        this.negotiationStatus = negotiationStatus;
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

    public Long getNegotiationId() {
        return negotiationId;
    }

    public void setNegotiationId(Long mBiddingNegotiationId) {
        this.negotiationId = mBiddingNegotiationId;
    }

    public Long getBiddingEvalResultId() {
        return biddingEvalResultId;
    }

    public void setBiddingEvalResultId(Long mBiddingEvalResultId) {
        this.biddingEvalResultId = mBiddingEvalResultId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MBiddingNegotiationLineDTO mBiddingNegotiationLineDTO = (MBiddingNegotiationLineDTO) o;
        if (mBiddingNegotiationLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mBiddingNegotiationLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MBiddingNegotiationLineDTO{" +
            "id=" + getId() +
            ", negotiationStatus='" + getNegotiationStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", negotiationId=" + getNegotiationId() +
            ", biddingEvalResultId=" + getBiddingEvalResultId() +
            "}";
    }
}

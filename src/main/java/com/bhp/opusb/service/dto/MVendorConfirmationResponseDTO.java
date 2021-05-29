package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorConfirmationResponse} entity.
 */
public class MVendorConfirmationResponseDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    @Lob
    private String needRevision;

    @Lob
    private String accept;


    private Long adOrganizationId;

    private Long vendorConfirmationLineId;

    private Long vendorConfirmationContractId;
    private String confirmationNo;
    
    public Long getId() {
        return id;
    }

    public String getConfirmationNo() {
        return confirmationNo;
    }

    public void setConfirmationNo(String confirmationNo) {
        this.confirmationNo = confirmationNo;
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

    public String getNeedRevision() {
        return needRevision;
    }

    public void setNeedRevision(String needRevision) {
        this.needRevision = needRevision;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getVendorConfirmationLineId() {
        return vendorConfirmationLineId;
    }

    public void setVendorConfirmationLineId(Long mVendorConfirmationLineId) {
        this.vendorConfirmationLineId = mVendorConfirmationLineId;
    }

    public Long getVendorConfirmationContractId() {
        return vendorConfirmationContractId;
    }

    public void setVendorConfirmationContractId(Long mVendorConfirmationContractId) {
        this.vendorConfirmationContractId = mVendorConfirmationContractId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVendorConfirmationResponseDTO mVendorConfirmationResponseDTO = (MVendorConfirmationResponseDTO) o;
        if (mVendorConfirmationResponseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVendorConfirmationResponseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVendorConfirmationResponseDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", needRevision='" + getNeedRevision() + "'" +
            ", accept='" + getAccept() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", vendorConfirmationLineId=" + getVendorConfirmationLineId() +
            ", vendorConfirmationContractId=" + getVendorConfirmationContractId() +
            "}";
    }
}

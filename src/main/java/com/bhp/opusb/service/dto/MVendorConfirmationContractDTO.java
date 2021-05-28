package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorConfirmationContract} entity.
 */
public class MVendorConfirmationContractDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    private UUID uid;

    private Boolean active;

    @Lob
    private String contractDetail;

    private LocalDate contractStartDate;

    private LocalDate contractEndDate;


    private Long adOrganizationId;

    private Long attachmentId;

    private Long vendorConfirmationLineId;

    private Boolean publish;
    
    public Long getId() {
        return id;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
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

    public String getContractDetail() {
        return contractDetail;
    }

    public void setContractDetail(String contractDetail) {
        this.contractDetail = contractDetail;
    }

    public LocalDate getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long cAttachmentId) {
        this.attachmentId = cAttachmentId;
    }

    public Long getVendorConfirmationLineId() {
        return vendorConfirmationLineId;
    }

    public void setVendorConfirmationLineId(Long mVendorConfirmationLineId) {
        this.vendorConfirmationLineId = mVendorConfirmationLineId;
    }

    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVendorConfirmationContractDTO mVendorConfirmationContractDTO = (MVendorConfirmationContractDTO) o;
        if (mVendorConfirmationContractDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVendorConfirmationContractDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVendorConfirmationContractDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", contractDetail='" + getContractDetail() + "'" +
            ", contractStartDate='" + getContractStartDate() + "'" +
            ", contractEndDate='" + getContractEndDate() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", attachmentId=" + getAttachmentId() +
            ", vendorConfirmationLineId=" + getVendorConfirmationLineId() +
            "}";
    }
}

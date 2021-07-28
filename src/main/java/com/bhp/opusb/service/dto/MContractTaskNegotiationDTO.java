package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MContractTaskNegotiation} entity.
 */
public class MContractTaskNegotiationDTO extends AbstractAuditingDTO {

    private Long id;

    @NotNull
    private String description;

    @Lob
    private String contractDocument;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long contractTaskId;

    private Long cAttachmentId;

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

    public String getContractDocument() {
        return contractDocument;
    }

    public void setContractDocument(String contractDocument) {
        this.contractDocument = contractDocument;
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

    public Long getContractTaskId() {
        return contractTaskId;
    }

    public void setContractTaskId(Long mContractTaskId) {
        this.contractTaskId = mContractTaskId;
    }

    public Long getCAttachmentId() {
        return cAttachmentId;
    }

    public void setCAttachmentId(Long cAttachmentId) {
        this.cAttachmentId = cAttachmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MContractTaskNegotiationDTO mContractTaskNegotiationDTO = (MContractTaskNegotiationDTO) o;
        if (mContractTaskNegotiationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mContractTaskNegotiationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MContractTaskNegotiationDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", contractDocument='" + getContractDocument() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", contractTaskId=" + getContractTaskId() +
            ", cAttachmentId=" + getCAttachmentId() +
            "}";
    }
}

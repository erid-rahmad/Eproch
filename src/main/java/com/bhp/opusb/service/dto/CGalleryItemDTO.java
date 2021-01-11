package com.bhp.opusb.service.dto;

import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.CGalleryItemType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CGalleryItem} entity.
 */
public class CGalleryItemDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private CGalleryItemType type;

    @NotNull
    @Min(value = 0)
    private Integer sequence;

    private Boolean preview;

    private UUID uid;

    private Boolean active;

    @JsonProperty("cAttachment")
    private CAttachmentDTO cAttachment;

    @JsonProperty("cAttachmentId")
    private Long cAttachmentId;

    @JsonProperty("cAttachmentName")
    private String cAttachmentName;

    private Long adOrganizationId;
    private String adOrganizationName;

    @JsonProperty("cGalleryId")
    private Long cGalleryId;

    @JsonProperty("cGalleryName")
    private String cGalleryName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CGalleryItemType getType() {
        return type;
    }

    public void setType(CGalleryItemType type) {
        this.type = type;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Boolean isPreview() {
        return preview;
    }

    public void setPreview(Boolean preview) {
        this.preview = preview;
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

    public CAttachmentDTO getCAttachment() {
        return cAttachment;
    }

    public void setCAttachment(CAttachmentDTO cAttachment) {
        this.cAttachment = cAttachment;
    }

    public Long getCAttachmentId() {
        return cAttachmentId;
    }

    public void setCAttachmentId(Long cAttachmentId) {
        this.cAttachmentId = cAttachmentId;
    }

    public String getCAttachmentName() {
        return cAttachmentName;
    }

    public void setCAttachmentName(String cAttachmentName) {
        this.cAttachmentName = cAttachmentName;
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

    public Long getCGalleryId() {
        return cGalleryId;
    }

    public void setCGalleryId(Long cGalleryId) {
        this.cGalleryId = cGalleryId;
    }

    public String getCGalleryName() {
        return cGalleryName;
    }

    public void setCGalleryName(String cGalleryName) {
        this.cGalleryName = cGalleryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CGalleryItemDTO)) {
            return false;
        }

        return id != null && id.equals(((CGalleryItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CGalleryItemDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", sequence=" + getSequence() +
            ", preview='" + isPreview() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", cAttachmentId=" + getCAttachmentId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", cGalleryId=" + getCGalleryId() +
            "}";
    }
}

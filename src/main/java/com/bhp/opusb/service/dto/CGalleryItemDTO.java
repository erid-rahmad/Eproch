package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import com.bhp.opusb.domain.enumeration.CGalleryItemType;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CGalleryItem} entity.
 */
public class CGalleryItemDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private CGalleryItemType type;

    @NotNull
    @Min(value = 0)
    private Integer sequence;

    private Boolean preview;

    private UUID uid;

    private Boolean active;


    private Long cAttachmentId;

    private Long adOrganizationId;

    private Long cGalleryId;
    
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

    public Long getCAttachmentId() {
        return cAttachmentId;
    }

    public void setCAttachmentId(Long cAttachmentId) {
        this.cAttachmentId = cAttachmentId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getCGalleryId() {
        return cGalleryId;
    }

    public void setCGalleryId(Long cGalleryId) {
        this.cGalleryId = cGalleryId;
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

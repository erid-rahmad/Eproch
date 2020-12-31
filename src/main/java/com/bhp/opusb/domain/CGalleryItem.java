package com.bhp.opusb.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.CGalleryItemType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CGalleryItem.
 */
@Entity
@Table(name = "c_gallery_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CGalleryItem extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CGalleryItemType type;

    @NotNull
    @Min(value = 0)
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "preview")
    private Boolean preview;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private CAttachment cAttachment;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "cGalleryItems", allowSetters = true)
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonBackReference
    @JsonIgnoreProperties(value = "cGalleryItems", allowSetters = true)
    private CGallery cGallery;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CGalleryItemType getType() {
        return type;
    }

    public CGalleryItem type(CGalleryItemType type) {
        this.type = type;
        return this;
    }

    public void setType(CGalleryItemType type) {
        this.type = type;
    }

    public Integer getSequence() {
        return sequence;
    }

    public CGalleryItem sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Boolean isPreview() {
        return preview;
    }

    public CGalleryItem preview(Boolean preview) {
        this.preview = preview;
        return this;
    }

    public void setPreview(Boolean preview) {
        this.preview = preview;
    }

    public UUID getUid() {
        return uid;
    }

    public CGalleryItem uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CGalleryItem active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CAttachment getCAttachment() {
        return cAttachment;
    }

    public CGalleryItem cAttachment(CAttachment cAttachment) {
        this.cAttachment = cAttachment;
        return this;
    }

    public void setCAttachment(CAttachment cAttachment) {
        this.cAttachment = cAttachment;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CGalleryItem adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CGallery getCGallery() {
        return cGallery;
    }

    public CGalleryItem cGallery(CGallery cGallery) {
        this.cGallery = cGallery;
        return this;
    }

    public void setCGallery(CGallery cGallery) {
        this.cGallery = cGallery;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CGalleryItem)) {
            return false;
        }
        return id != null && id.equals(((CGalleryItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CGalleryItem{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", sequence=" + getSequence() +
            ", preview='" + isPreview() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

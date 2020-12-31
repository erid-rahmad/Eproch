package com.bhp.opusb.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The CGallery entity.\n@author A true hipster
 */
@Entity
@Table(name = "c_gallery")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CGallery extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "cGallery")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Fetch(FetchMode.JOIN)
    @JsonManagedReference
    @OrderBy("sequence ASC, id ASC")
    private List<CGalleryItem> cGalleryItems = new ArrayList<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "cGalleries", allowSetters = true)
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CGallery name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CGallery description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getUid() {
        return uid;
    }

    public CGallery uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CGallery active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<CGalleryItem> getCGalleryItems() {
        return cGalleryItems;
    }

    public CGallery cGalleryItems(List<CGalleryItem> cGalleryItems) {
        this.cGalleryItems = cGalleryItems;
        return this;
    }

    public CGallery addCGalleryItem(CGalleryItem cGalleryItem) {
        this.cGalleryItems.add(cGalleryItem);
        cGalleryItem.setCGallery(this);
        return this;
    }

    public CGallery removeCGalleryItem(CGalleryItem cGalleryItem) {
        this.cGalleryItems.remove(cGalleryItem);
        cGalleryItem.setCGallery(null);
        return this;
    }

    public void setCGalleryItems(List<CGalleryItem> cGalleryItems) {
        this.cGalleryItems = cGalleryItems;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CGallery adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
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
        if (!(o instanceof CGallery)) {
            return false;
        }
        return id != null && id.equals(((CGallery) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CGallery{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

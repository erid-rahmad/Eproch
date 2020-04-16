package com.bhp.opusb.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BusinessCategory.
 */
@Entity
@Table(name = "business_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusinessCategory extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "parentCategory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BusinessCategory> businessCategories = new HashSet<>();

    @OneToMany(mappedBy = "businessCategory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DocumentTypeBusinessCategory> documentTypeBusinessCategories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("businessCategories")
    private BusinessCategory parentCategory;

    @ManyToMany(mappedBy = "businessCategories")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Vendor> vendors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BusinessCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public BusinessCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<BusinessCategory> getBusinessCategories() {
        return businessCategories;
    }

    public BusinessCategory businessCategories(Set<BusinessCategory> businessCategories) {
        this.businessCategories = businessCategories;
        return this;
    }

    public BusinessCategory addBusinessCategory(BusinessCategory businessCategory) {
        this.businessCategories.add(businessCategory);
        businessCategory.setParentCategory(this);
        return this;
    }

    public BusinessCategory removeBusinessCategory(BusinessCategory businessCategory) {
        this.businessCategories.remove(businessCategory);
        businessCategory.setParentCategory(null);
        return this;
    }

    public void setBusinessCategories(Set<BusinessCategory> businessCategories) {
        this.businessCategories = businessCategories;
    }

    public Set<DocumentTypeBusinessCategory> getDocumentTypeBusinessCategories() {
        return documentTypeBusinessCategories;
    }

    public BusinessCategory documentTypeBusinessCategories(Set<DocumentTypeBusinessCategory> documentTypeBusinessCategories) {
        this.documentTypeBusinessCategories = documentTypeBusinessCategories;
        return this;
    }

    public BusinessCategory addDocumentTypeBusinessCategory(DocumentTypeBusinessCategory documentTypeBusinessCategory) {
        this.documentTypeBusinessCategories.add(documentTypeBusinessCategory);
        documentTypeBusinessCategory.setBusinessCategory(this);
        return this;
    }

    public BusinessCategory removeDocumentTypeBusinessCategory(DocumentTypeBusinessCategory documentTypeBusinessCategory) {
        this.documentTypeBusinessCategories.remove(documentTypeBusinessCategory);
        documentTypeBusinessCategory.setBusinessCategory(null);
        return this;
    }

    public void setDocumentTypeBusinessCategories(Set<DocumentTypeBusinessCategory> documentTypeBusinessCategories) {
        this.documentTypeBusinessCategories = documentTypeBusinessCategories;
    }

    public BusinessCategory getParentCategory() {
        return parentCategory;
    }

    public BusinessCategory parentCategory(BusinessCategory businessCategory) {
        this.parentCategory = businessCategory;
        return this;
    }

    public void setParentCategory(BusinessCategory businessCategory) {
        this.parentCategory = businessCategory;
    }

    public Set<Vendor> getVendors() {
        return vendors;
    }

    public BusinessCategory vendors(Set<Vendor> vendors) {
        this.vendors = vendors;
        return this;
    }

    public BusinessCategory addVendor(Vendor vendor) {
        this.vendors.add(vendor);
        vendor.getBusinessCategories().add(this);
        return this;
    }

    public BusinessCategory removeVendor(Vendor vendor) {
        this.vendors.remove(vendor);
        vendor.getBusinessCategories().remove(this);
        return this;
    }

    public void setVendors(Set<Vendor> vendors) {
        this.vendors = vendors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessCategory)) {
            return false;
        }
        return id != null && id.equals(((BusinessCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BusinessCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.bhp.opusb.domain.enumeration.CBusinessCategorySector;

/**
 * A CBusinessCategory.
 */
@Entity
@Table(name = "c_business_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CBusinessCategory extends AbstractAuditingEntity {

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sector", nullable = false)
    private CBusinessCategorySector sector;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "parentCategory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CBusinessCategory> cBusinessCategories = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cBusinessCategories")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("cBusinessCategories")
    private CBusinessCategory parentCategory;

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

    public CBusinessCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CBusinessCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CBusinessCategorySector getSector() {
        return sector;
    }

    public CBusinessCategory sector(CBusinessCategorySector sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(CBusinessCategorySector sector) {
        this.sector = sector;
    }

    public UUID getUid() {
        return uid;
    }

    public CBusinessCategory uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CBusinessCategory active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<CBusinessCategory> getCBusinessCategories() {
        return cBusinessCategories;
    }

    public CBusinessCategory cBusinessCategories(Set<CBusinessCategory> cBusinessCategories) {
        this.cBusinessCategories = cBusinessCategories;
        return this;
    }

    public CBusinessCategory addCBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.cBusinessCategories.add(cBusinessCategory);
        cBusinessCategory.setParentCategory(this);
        return this;
    }

    public CBusinessCategory removeCBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.cBusinessCategories.remove(cBusinessCategory);
        cBusinessCategory.setParentCategory(null);
        return this;
    }

    public void setCBusinessCategories(Set<CBusinessCategory> cBusinessCategories) {
        this.cBusinessCategories = cBusinessCategories;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CBusinessCategory adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CBusinessCategory getParentCategory() {
        return parentCategory;
    }

    public CBusinessCategory parentCategory(CBusinessCategory cBusinessCategory) {
        this.parentCategory = cBusinessCategory;
        return this;
    }

    public void setParentCategory(CBusinessCategory cBusinessCategory) {
        this.parentCategory = cBusinessCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CBusinessCategory)) {
            return false;
        }
        return id != null && id.equals(((CBusinessCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CBusinessCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", sector='" + getSector() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

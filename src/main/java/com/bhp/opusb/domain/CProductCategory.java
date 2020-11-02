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

/**
 * A CProductCategory.
 */
@Entity
@Table(name = "c_product_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CProductCategory extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "parentCategory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CProductCategory> cProductCategories = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProductCategories")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("cProductCategories")
    private CProductCategory parentCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public CProductCategory code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public CProductCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CProductCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getUid() {
        return uid;
    }

    public CProductCategory uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CProductCategory active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<CProductCategory> getCProductCategories() {
        return cProductCategories;
    }

    public CProductCategory cProductCategories(Set<CProductCategory> cProductCategories) {
        this.cProductCategories = cProductCategories;
        return this;
    }

    public CProductCategory addCProductCategory(CProductCategory cProductCategory) {
        this.cProductCategories.add(cProductCategory);
        cProductCategory.setParentCategory(this);
        return this;
    }

    public CProductCategory removeCProductCategory(CProductCategory cProductCategory) {
        this.cProductCategories.remove(cProductCategory);
        cProductCategory.setParentCategory(null);
        return this;
    }

    public void setCProductCategories(Set<CProductCategory> cProductCategories) {
        this.cProductCategories = cProductCategories;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CProductCategory adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CProductCategory getParentCategory() {
        return parentCategory;
    }

    public CProductCategory parentCategory(CProductCategory cProductCategory) {
        this.parentCategory = cProductCategory;
        return this;
    }

    public void setParentCategory(CProductCategory cProductCategory) {
        this.parentCategory = cProductCategory;
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
        if (!(o instanceof CProductCategory)) {
            return false;
        }
        return id != null && id.equals(((CProductCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CProductCategory{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

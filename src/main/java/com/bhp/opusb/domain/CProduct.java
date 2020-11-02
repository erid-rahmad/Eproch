package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A CProduct.
 */
@Entity
@Table(name = "c_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CProduct implements Serializable {

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

    @Column(name = "type")
    private String type;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProducts")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProducts")
    private CProductClassification productClassification;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProducts")
    private CProductCategory productCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProducts")
    private CProductCategory productSubCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProducts")
    private CProductCategoryAccount assetAcct;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProducts")
    private CProductCategoryAccount expenseAcct;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProducts")
    private CUnitOfMeasure uom;

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

    public CProduct code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public CProduct name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CProduct description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public CProduct type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getUid() {
        return uid;
    }

    public CProduct uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CProduct active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CProduct adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CProductClassification getProductClassification() {
        return productClassification;
    }

    public CProduct productClassification(CProductClassification cProductClassification) {
        this.productClassification = cProductClassification;
        return this;
    }

    public void setProductClassification(CProductClassification cProductClassification) {
        this.productClassification = cProductClassification;
    }

    public CProductCategory getProductCategory() {
        return productCategory;
    }

    public CProduct productCategory(CProductCategory cProductCategory) {
        this.productCategory = cProductCategory;
        return this;
    }

    public void setProductCategory(CProductCategory cProductCategory) {
        this.productCategory = cProductCategory;
    }

    public CProductCategory getProductSubCategory() {
        return productSubCategory;
    }

    public CProduct productSubCategory(CProductCategory cProductCategory) {
        this.productSubCategory = cProductCategory;
        return this;
    }

    public void setProductSubCategory(CProductCategory cProductCategory) {
        this.productSubCategory = cProductCategory;
    }

    public CProductCategoryAccount getAssetAcct() {
        return assetAcct;
    }

    public CProduct assetAcct(CProductCategoryAccount cProductCategoryAccount) {
        this.assetAcct = cProductCategoryAccount;
        return this;
    }

    public void setAssetAcct(CProductCategoryAccount cProductCategoryAccount) {
        this.assetAcct = cProductCategoryAccount;
    }

    public CProductCategoryAccount getExpenseAcct() {
        return expenseAcct;
    }

    public CProduct expenseAcct(CProductCategoryAccount cProductCategoryAccount) {
        this.expenseAcct = cProductCategoryAccount;
        return this;
    }

    public void setExpenseAcct(CProductCategoryAccount cProductCategoryAccount) {
        this.expenseAcct = cProductCategoryAccount;
    }

    public CUnitOfMeasure getUom() {
        return uom;
    }

    public CProduct uom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
        return this;
    }

    public void setUom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CProduct)) {
            return false;
        }
        return id != null && id.equals(((CProduct) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CProduct{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

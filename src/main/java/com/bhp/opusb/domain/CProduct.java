package com.bhp.opusb.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CProduct.
 */
@Entity
@Table(name = "c_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CProduct extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Size(max = 1)
    @Column(name = "type", length = 1, nullable = false)
    private String type;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProducts")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("cProducts")
    private CProductClassification productClassification;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProducts")
    private CProductCategory productCategory;

    @ManyToOne
    @JsonIgnoreProperties("cProducts")
    private CProductCategory productSubCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProducts")
    private CElementValue assetAcct;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProducts")
    private CElementValue expenseAcct;

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

    public CElementValue getAssetAcct() {
        return assetAcct;
    }

    public CProduct assetAcct(CElementValue cElementValue) {
        this.assetAcct = cElementValue;
        return this;
    }

    public void setAssetAcct(CElementValue cElementValue) {
        this.assetAcct = cElementValue;
    }

    public CElementValue getExpenseAcct() {
        return expenseAcct;
    }

    public CProduct expenseAcct(CElementValue cElementValue) {
        this.expenseAcct = cElementValue;
        return this;
    }

    public void setExpenseAcct(CElementValue cElementValue) {
        this.expenseAcct = cElementValue;
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

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }
    
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

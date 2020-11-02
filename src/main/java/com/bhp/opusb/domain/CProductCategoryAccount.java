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
 * A CProductCategoryAccount.
 */
@Entity
@Table(name = "c_product_category_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CProductCategoryAccount extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProductCategoryAccounts")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProductCategoryAccounts")
    private CElementValue assetAcct;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProductCategoryAccounts")
    private CElementValue expenseAcct;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProductCategoryAccounts")
    private CProductCategory productCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public CProductCategoryAccount uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CProductCategoryAccount active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CProductCategoryAccount adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CElementValue getAssetAcct() {
        return assetAcct;
    }

    public CProductCategoryAccount assetAcct(CElementValue cElementValue) {
        this.assetAcct = cElementValue;
        return this;
    }

    public void setAssetAcct(CElementValue cElementValue) {
        this.assetAcct = cElementValue;
    }

    public CElementValue getExpenseAcct() {
        return expenseAcct;
    }

    public CProductCategoryAccount expenseAcct(CElementValue cElementValue) {
        this.expenseAcct = cElementValue;
        return this;
    }

    public void setExpenseAcct(CElementValue cElementValue) {
        this.expenseAcct = cElementValue;
    }

    public CProductCategory getProductCategory() {
        return productCategory;
    }

    public CProductCategoryAccount productCategory(CProductCategory cProductCategory) {
        this.productCategory = cProductCategory;
        return this;
    }

    public void setProductCategory(CProductCategory cProductCategory) {
        this.productCategory = cProductCategory;
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
        if (!(o instanceof CProductCategoryAccount)) {
            return false;
        }
        return id != null && id.equals(((CProductCategoryAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CProductCategoryAccount{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

package com.bhp.opusb.domain;

import java.math.BigDecimal;
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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MShoppingCartItem.
 */
@Entity
@Table(name = "m_shopping_cart_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MShoppingCartItem extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "quantity", precision = 21, scale = 2, nullable = false)
    private BigDecimal quantity;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mShoppingCartItems", allowSetters = true)
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mShoppingCartItems", allowSetters = true)
    private MProductCatalog mProductCatalog;

    @ManyToOne(optional = false)
    @NotNull
    @JsonBackReference
    @JsonIgnoreProperties(value = "mShoppingCartItems", allowSetters = true)
    private MShoppingCart mShoppingCart;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public MShoppingCartItem quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public UUID getUid() {
        return uid;
    }

    public MShoppingCartItem uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MShoppingCartItem active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MShoppingCartItem adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MProductCatalog getMProductCatalog() {
        return mProductCatalog;
    }

    public MShoppingCartItem mProductCatalog(MProductCatalog mProductCatalog) {
        this.mProductCatalog = mProductCatalog;
        return this;
    }

    public void setMProductCatalog(MProductCatalog mProductCatalog) {
        this.mProductCatalog = mProductCatalog;
    }

    public MShoppingCart getMShoppingCart() {
        return mShoppingCart;
    }

    public MShoppingCartItem mShoppingCart(MShoppingCart mShoppingCart) {
        this.mShoppingCart = mShoppingCart;
        return this;
    }

    public void setMShoppingCart(MShoppingCart mShoppingCart) {
        this.mShoppingCart = mShoppingCart;
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
        if (!(o instanceof MShoppingCartItem)) {
            return false;
        }
        return id != null && id.equals(((MShoppingCartItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MShoppingCartItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

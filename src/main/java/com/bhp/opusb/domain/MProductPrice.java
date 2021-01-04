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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MProductPrice.
 */
@Entity
@Table(name = "m_product_price")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MProductPrice extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "min_qty", precision = 21, scale = 2)
    private BigDecimal minQty;

    @Column(name = "max_qty", precision = 21, scale = 2)
    private BigDecimal maxQty;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mProductPrices", allowSetters = true)
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mProductPrices")
    @JsonBackReference
    private MProductCatalog mProductCatalog;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMinQty() {
        return minQty;
    }

    public MProductPrice minQty(BigDecimal minQty) {
        this.minQty = minQty;
        return this;
    }

    public void setMinQty(BigDecimal minQty) {
        this.minQty = minQty;
    }

    public BigDecimal getMaxQty() {
        return maxQty;
    }

    public MProductPrice maxQty(BigDecimal maxQty) {
        this.maxQty = maxQty;
        return this;
    }

    public void setMaxQty(BigDecimal maxQty) {
        this.maxQty = maxQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MProductPrice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID getUid() {
        return uid;
    }

    public MProductPrice uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MProductPrice active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MProductPrice adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MProductCatalog getMProductCatalog() {
        return mProductCatalog;
    }

    public MProductPrice mProductCatalog(MProductCatalog mProductCatalog) {
        this.mProductCatalog = mProductCatalog;
        return this;
    }

    public void setMProductCatalog(MProductCatalog mProductCatalog) {
        this.mProductCatalog = mProductCatalog;
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
        if (!(o instanceof MProductPrice)) {
            return false;
        }
        return id != null && id.equals(((MProductPrice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MProductPrice{" +
            "id=" + getId() +
            ", minQty=" + getMinQty() +
            ", maxQty=" + getMaxQty() +
            ", price=" + getPrice() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

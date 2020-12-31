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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * A MShoppingCart.
 */
@Entity
@Table(name = "m_shopping_cart")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MShoppingCart extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "mShoppingCart")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Fetch(FetchMode.JOIN)
    @OrderBy("id ASC")
    @JsonManagedReference
    private List<MShoppingCartItem> mShoppingCartItems = new ArrayList<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mShoppingCarts", allowSetters = true)
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mShoppingCarts", allowSetters = true)
    private AdUser adUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public MShoppingCart uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MShoppingCart active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<MShoppingCartItem> getMShoppingCartItems() {
        return mShoppingCartItems;
    }

    public MShoppingCart mShoppingCartItems(List<MShoppingCartItem> mShoppingCartItems) {
        this.mShoppingCartItems = mShoppingCartItems;
        return this;
    }

    public MShoppingCart addMShoppingCartItem(MShoppingCartItem mShoppingCartItem) {
        this.mShoppingCartItems.add(mShoppingCartItem);
        mShoppingCartItem.setMShoppingCart(this);
        return this;
    }

    public MShoppingCart removeMShoppingCartItem(MShoppingCartItem mShoppingCartItem) {
        this.mShoppingCartItems.remove(mShoppingCartItem);
        mShoppingCartItem.setMShoppingCart(null);
        return this;
    }

    public void setMShoppingCartItems(List<MShoppingCartItem> mShoppingCartItems) {
        this.mShoppingCartItems = mShoppingCartItems;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MShoppingCart adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public AdUser getAdUser() {
        return adUser;
    }

    public MShoppingCart adUser(AdUser adUser) {
        this.adUser = adUser;
        return this;
    }

    public void setAdUser(AdUser adUser) {
        this.adUser = adUser;
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
        if (!(o instanceof MShoppingCart)) {
            return false;
        }
        return id != null && id.equals(((MShoppingCart) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MShoppingCart{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

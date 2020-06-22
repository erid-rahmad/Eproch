package com.bhp.opusb.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A CCountry.
 */
@Entity
@Table(name = "c_country")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CCountry extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Pattern(regexp = "^[A-Z]{2}$")
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "with_region")
    private Boolean withRegion;

    @Column(name = "active")
    private Boolean active;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private CCurrency currency;

    @OneToMany(mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CRegion> cRegions = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CCity> cCities = new HashSet<>();

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

    public CCountry name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public CCountry code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isWithRegion() {
        return withRegion;
    }

    public CCountry withRegion(Boolean withRegion) {
        this.withRegion = withRegion;
        return this;
    }

    public void setWithRegion(Boolean withRegion) {
        this.withRegion = withRegion;
    }

    public Boolean isActive() {
        return active;
    }

    public CCountry active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CCurrency getCurrency() {
        return currency;
    }

    public CCountry currency(CCurrency cCurrency) {
        this.currency = cCurrency;
        return this;
    }

    public void setCurrency(CCurrency cCurrency) {
        this.currency = cCurrency;
    }

    public Set<CRegion> getCRegions() {
        return cRegions;
    }

    public CCountry cRegions(Set<CRegion> cRegions) {
        this.cRegions = cRegions;
        return this;
    }

    public CCountry addCRegion(CRegion cRegion) {
        this.cRegions.add(cRegion);
        cRegion.setCountry(this);
        return this;
    }

    public CCountry removeCRegion(CRegion cRegion) {
        this.cRegions.remove(cRegion);
        cRegion.setCountry(null);
        return this;
    }

    public void setCRegions(Set<CRegion> cRegions) {
        this.cRegions = cRegions;
    }

    public Set<CCity> getCCities() {
        return cCities;
    }

    public CCountry cCities(Set<CCity> cCities) {
        this.cCities = cCities;
        return this;
    }

    public CCountry addCCity(CCity cCity) {
        this.cCities.add(cCity);
        cCity.setCountry(this);
        return this;
    }

    public CCountry removeCCity(CCity cCity) {
        this.cCities.remove(cCity);
        cCity.setCountry(null);
        return this;
    }

    public void setCCities(Set<CCity> cCities) {
        this.cCities = cCities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CCountry)) {
            return false;
        }
        return id != null && id.equals(((CCountry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CCountry{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", withRegion='" + isWithRegion() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

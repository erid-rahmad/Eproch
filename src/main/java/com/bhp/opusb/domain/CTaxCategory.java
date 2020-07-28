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
 * The Tax Category entity.
 */
@Entity
@Table(name = "c_tax_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CTaxCategory extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_withholding")
    private Boolean isWithholding;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "taxCategory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CTaxRate> cTaxRates = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cTaxCategories")
    private ADOrganization adOrganization;

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

    public CTaxCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CTaxCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsWithholding() {
        return isWithholding;
    }

    public CTaxCategory isWithholding(Boolean isWithholding) {
        this.isWithholding = isWithholding;
        return this;
    }

    public void setIsWithholding(Boolean isWithholding) {
        this.isWithholding = isWithholding;
    }

    public UUID getUid() {
        return uid;
    }

    public CTaxCategory uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CTaxCategory active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<CTaxRate> getCTaxRates() {
        return cTaxRates;
    }

    public CTaxCategory cTaxRates(Set<CTaxRate> cTaxRates) {
        this.cTaxRates = cTaxRates;
        return this;
    }

    public CTaxCategory addCTaxRate(CTaxRate cTaxRate) {
        this.cTaxRates.add(cTaxRate);
        cTaxRate.setTaxCategory(this);
        return this;
    }

    public CTaxCategory removeCTaxRate(CTaxRate cTaxRate) {
        this.cTaxRates.remove(cTaxRate);
        cTaxRate.setTaxCategory(null);
        return this;
    }

    public void setCTaxRates(Set<CTaxRate> cTaxRates) {
        this.cTaxRates = cTaxRates;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CTaxCategory adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
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
        if (!(o instanceof CTaxCategory)) {
            return false;
        }
        return id != null && id.equals(((CTaxCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CTaxCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", isWithholding='" + isIsWithholding() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

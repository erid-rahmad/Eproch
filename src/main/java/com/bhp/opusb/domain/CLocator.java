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
 * A CLocator.
 */
@Entity
@Table(name = "c_locator")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CLocator extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "locator_type")
    private String locatorType;

    @Column(name = "aisle")
    private String aisle;

    @Column(name = "bin")
    private String bin;

    @Column(name = "level")
    private String level;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cLocators")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cLocators")
    private CWarehouse warehouse;

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

    public CLocator code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocatorType() {
        return locatorType;
    }

    public CLocator locatorType(String locatorType) {
        this.locatorType = locatorType;
        return this;
    }

    public void setLocatorType(String locatorType) {
        this.locatorType = locatorType;
    }

    public String getAisle() {
        return aisle;
    }

    public CLocator aisle(String aisle) {
        this.aisle = aisle;
        return this;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public String getBin() {
        return bin;
    }

    public CLocator bin(String bin) {
        this.bin = bin;
        return this;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getLevel() {
        return level;
    }

    public CLocator level(String level) {
        this.level = level;
        return this;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public UUID getUid() {
        return uid;
    }

    public CLocator uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CLocator active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CLocator adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CWarehouse getWarehouse() {
        return warehouse;
    }

    public CLocator warehouse(CWarehouse cWarehouse) {
        this.warehouse = cWarehouse;
        return this;
    }

    public void setWarehouse(CWarehouse cWarehouse) {
        this.warehouse = cWarehouse;
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
        if (!(o instanceof CLocator)) {
            return false;
        }
        return id != null && id.equals(((CLocator) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CLocator{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", locatorType='" + getLocatorType() + "'" +
            ", aisle='" + getAisle() + "'" +
            ", bin='" + getBin() + "'" +
            ", level='" + getLevel() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

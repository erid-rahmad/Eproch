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
 * A CPicBusinessCat.
 */
@Entity
@Table(name = "c_pic_business_cat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CPicBusinessCat implements Serializable {

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
    @JsonIgnoreProperties("cPicBusinessCats")
    private CPersonInCharge contact;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPicBusinessCats")
    private CBusinessCategory businessCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPicBusinessCats")
    private ADOrganization adOrganization;

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

    public CPicBusinessCat uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CPicBusinessCat active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CPersonInCharge getContact() {
        return contact;
    }

    public CPicBusinessCat contact(CPersonInCharge cPersonInCharge) {
        this.contact = cPersonInCharge;
        return this;
    }

    public void setContact(CPersonInCharge cPersonInCharge) {
        this.contact = cPersonInCharge;
    }

    public CBusinessCategory getBusinessCategory() {
        return businessCategory;
    }

    public CPicBusinessCat businessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
        return this;
    }

    public void setBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CPicBusinessCat adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CPicBusinessCat)) {
            return false;
        }
        return id != null && id.equals(((CPicBusinessCat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CPicBusinessCat{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

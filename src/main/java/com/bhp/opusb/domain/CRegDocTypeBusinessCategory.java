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
 * A CRegDocTypeBusinessCategory.
 */
@Entity
@Table(name = "c_regdoctype_businesses")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CRegDocTypeBusinessCategory extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "mandatory")
    private Boolean mandatory;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("cRegDocTypeBusinessCategories")
    private CRegistrationDocType documentType;

    @ManyToOne
    @JsonIgnoreProperties("cRegDocTypeBusinessCategories")
    private CBusinessCategory businessCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cRegDocTypeBusinessCategories")
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

    public CRegDocTypeBusinessCategory uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public CRegDocTypeBusinessCategory mandatory(Boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Boolean isActive() {
        return active;
    }

    public CRegDocTypeBusinessCategory active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CRegistrationDocType getDocumentType() {
        return documentType;
    }

    public CRegDocTypeBusinessCategory documentType(CRegistrationDocType cRegistrationDocType) {
        this.documentType = cRegistrationDocType;
        return this;
    }

    public void setDocumentType(CRegistrationDocType cRegistrationDocType) {
        this.documentType = cRegistrationDocType;
    }

    public CBusinessCategory getBusinessCategory() {
        return businessCategory;
    }

    public CRegDocTypeBusinessCategory businessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
        return this;
    }

    public void setBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CRegDocTypeBusinessCategory adOrganization(ADOrganization aDOrganization) {
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
        if (!(o instanceof CRegDocTypeBusinessCategory)) {
            return false;
        }
        return id != null && id.equals(((CRegDocTypeBusinessCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CRegDocTypeBusinessCategory{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", mandatory='" + isMandatory() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

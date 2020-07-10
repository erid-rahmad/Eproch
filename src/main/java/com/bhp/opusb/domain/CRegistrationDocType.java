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
 * A CRegistrationDocType.
 */
@Entity
@Table(name = "c_registration_doc_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CRegistrationDocType extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "has_expiration_date")
    private Boolean hasExpirationDate;

    @Column(name = "mandatory_business_categories")
    private String mandatoryBusinessCategories;

    @Column(name = "additional_business_categories")
    private String additionalBusinessCategories;

    @Column(name = "mandatory_for_company")
    private Boolean mandatoryForCompany;

    @Column(name = "additional_for_company")
    private Boolean additionalForCompany;

    @Column(name = "mandatory_for_professional")
    private Boolean mandatoryForProfessional;

    @Column(name = "additional_for_professional")
    private Boolean additionalForProfessional;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cRegistrationDocTypes")
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

    public CRegistrationDocType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CRegistrationDocType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isHasExpirationDate() {
        return hasExpirationDate;
    }

    public CRegistrationDocType hasExpirationDate(Boolean hasExpirationDate) {
        this.hasExpirationDate = hasExpirationDate;
        return this;
    }

    public void setHasExpirationDate(Boolean hasExpirationDate) {
        this.hasExpirationDate = hasExpirationDate;
    }

    public String getMandatoryBusinessCategories() {
        return mandatoryBusinessCategories;
    }

    public CRegistrationDocType mandatoryBusinessCategories(String mandatoryBusinessCategories) {
        this.mandatoryBusinessCategories = mandatoryBusinessCategories;
        return this;
    }

    public void setMandatoryBusinessCategories(String mandatoryBusinessCategories) {
        this.mandatoryBusinessCategories = mandatoryBusinessCategories;
    }

    public String getAdditionalBusinessCategories() {
        return additionalBusinessCategories;
    }

    public CRegistrationDocType additionalBusinessCategories(String additionalBusinessCategories) {
        this.additionalBusinessCategories = additionalBusinessCategories;
        return this;
    }

    public void setAdditionalBusinessCategories(String additionalBusinessCategories) {
        this.additionalBusinessCategories = additionalBusinessCategories;
    }

    public Boolean isMandatoryForCompany() {
        return mandatoryForCompany;
    }

    public CRegistrationDocType mandatoryForCompany(Boolean mandatoryForCompany) {
        this.mandatoryForCompany = mandatoryForCompany;
        return this;
    }

    public void setMandatoryForCompany(Boolean mandatoryForCompany) {
        this.mandatoryForCompany = mandatoryForCompany;
    }

    public Boolean isAdditionalForCompany() {
        return additionalForCompany;
    }

    public CRegistrationDocType additionalForCompany(Boolean additionalForCompany) {
        this.additionalForCompany = additionalForCompany;
        return this;
    }

    public void setAdditionalForCompany(Boolean additionalForCompany) {
        this.additionalForCompany = additionalForCompany;
    }

    public Boolean isMandatoryForProfessional() {
        return mandatoryForProfessional;
    }

    public CRegistrationDocType mandatoryForProfessional(Boolean mandatoryForProfessional) {
        this.mandatoryForProfessional = mandatoryForProfessional;
        return this;
    }

    public void setMandatoryForProfessional(Boolean mandatoryForProfessional) {
        this.mandatoryForProfessional = mandatoryForProfessional;
    }

    public Boolean isAdditionalForProfessional() {
        return additionalForProfessional;
    }

    public CRegistrationDocType additionalForProfessional(Boolean additionalForProfessional) {
        this.additionalForProfessional = additionalForProfessional;
        return this;
    }

    public void setAdditionalForProfessional(Boolean additionalForProfessional) {
        this.additionalForProfessional = additionalForProfessional;
    }

    public UUID getUid() {
        return uid;
    }

    public CRegistrationDocType uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CRegistrationDocType active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CRegistrationDocType adOrganization(ADOrganization aDOrganization) {
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
        if (!(o instanceof CRegistrationDocType)) {
            return false;
        }
        return id != null && id.equals(((CRegistrationDocType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CRegistrationDocType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", hasExpirationDate='" + isHasExpirationDate() + "'" +
            ", mandatoryBusinessCategories='" + getMandatoryBusinessCategories() + "'" +
            ", additionalBusinessCategories='" + getAdditionalBusinessCategories() + "'" +
            ", mandatoryForCompany='" + isMandatoryForCompany() + "'" +
            ", additionalForCompany='" + isAdditionalForCompany() + "'" +
            ", mandatoryForProfessional='" + isMandatoryForProfessional() + "'" +
            ", additionalForProfessional='" + isAdditionalForProfessional() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

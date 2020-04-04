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
 * A DocumentType.
 */
@Entity
@Table(name = "document_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DocumentType implements Serializable {

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

    @Column(name = "mandatory_for_professional")
    private Boolean mandatoryForProfessional;

    @Column(name = "additional_for_company")
    private Boolean additionalForCompany;

    @Column(name = "additional_for_professional")
    private Boolean additionalForProfessional;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "documentType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DocumentTypeBusinessCategory> documentTypeBusinessCategories = new HashSet<>();

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

    public DocumentType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public DocumentType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isHasExpirationDate() {
        return hasExpirationDate;
    }

    public DocumentType hasExpirationDate(Boolean hasExpirationDate) {
        this.hasExpirationDate = hasExpirationDate;
        return this;
    }

    public void setHasExpirationDate(Boolean hasExpirationDate) {
        this.hasExpirationDate = hasExpirationDate;
    }

    public String getMandatoryBusinessCategories() {
        return mandatoryBusinessCategories;
    }

    public DocumentType mandatoryBusinessCategories(String mandatoryBusinessCategories) {
        this.mandatoryBusinessCategories = mandatoryBusinessCategories;
        return this;
    }

    public void setMandatoryBusinessCategories(String mandatoryBusinessCategories) {
        this.mandatoryBusinessCategories = mandatoryBusinessCategories;
    }

    public String getAdditionalBusinessCategories() {
        return additionalBusinessCategories;
    }

    public DocumentType additionalBusinessCategories(String additionalBusinessCategories) {
        this.additionalBusinessCategories = additionalBusinessCategories;
        return this;
    }

    public void setAdditionalBusinessCategories(String additionalBusinessCategories) {
        this.additionalBusinessCategories = additionalBusinessCategories;
    }

    public Boolean isMandatoryForCompany() {
        return mandatoryForCompany;
    }

    public DocumentType mandatoryForCompany(Boolean mandatoryForCompany) {
        this.mandatoryForCompany = mandatoryForCompany;
        return this;
    }

    public void setMandatoryForCompany(Boolean mandatoryForCompany) {
        this.mandatoryForCompany = mandatoryForCompany;
    }

    public Boolean isMandatoryForProfessional() {
        return mandatoryForProfessional;
    }

    public DocumentType mandatoryForProfessional(Boolean mandatoryForProfessional) {
        this.mandatoryForProfessional = mandatoryForProfessional;
        return this;
    }

    public void setMandatoryForProfessional(Boolean mandatoryForProfessional) {
        this.mandatoryForProfessional = mandatoryForProfessional;
    }

    public Boolean isAdditionalForCompany() {
        return additionalForCompany;
    }

    public DocumentType additionalForCompany(Boolean additionalForCompany) {
        this.additionalForCompany = additionalForCompany;
        return this;
    }

    public void setAdditionalForCompany(Boolean additionalForCompany) {
        this.additionalForCompany = additionalForCompany;
    }

    public Boolean isAdditionalForProfessional() {
        return additionalForProfessional;
    }

    public DocumentType additionalForProfessional(Boolean additionalForProfessional) {
        this.additionalForProfessional = additionalForProfessional;
        return this;
    }

    public void setAdditionalForProfessional(Boolean additionalForProfessional) {
        this.additionalForProfessional = additionalForProfessional;
    }

    public Boolean isActive() {
        return active;
    }

    public DocumentType active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<DocumentTypeBusinessCategory> getDocumentTypeBusinessCategories() {
        return documentTypeBusinessCategories;
    }

    public DocumentType documentTypeBusinessCategories(Set<DocumentTypeBusinessCategory> documentTypeBusinessCategories) {
        this.documentTypeBusinessCategories = documentTypeBusinessCategories;
        return this;
    }

    public DocumentType addDocumentTypeBusinessCategory(DocumentTypeBusinessCategory documentTypeBusinessCategory) {
        this.documentTypeBusinessCategories.add(documentTypeBusinessCategory);
        documentTypeBusinessCategory.setDocumentType(this);
        return this;
    }

    public DocumentType removeDocumentTypeBusinessCategory(DocumentTypeBusinessCategory documentTypeBusinessCategory) {
        this.documentTypeBusinessCategories.remove(documentTypeBusinessCategory);
        documentTypeBusinessCategory.setDocumentType(null);
        return this;
    }

    public void setDocumentTypeBusinessCategories(Set<DocumentTypeBusinessCategory> documentTypeBusinessCategories) {
        this.documentTypeBusinessCategories = documentTypeBusinessCategories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentType)) {
            return false;
        }
        return id != null && id.equals(((DocumentType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DocumentType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", hasExpirationDate='" + isHasExpirationDate() + "'" +
            ", mandatoryBusinessCategories='" + getMandatoryBusinessCategories() + "'" +
            ", additionalBusinessCategories='" + getAdditionalBusinessCategories() + "'" +
            ", mandatoryForCompany='" + isMandatoryForCompany() + "'" +
            ", mandatoryForProfessional='" + isMandatoryForProfessional() + "'" +
            ", additionalForCompany='" + isAdditionalForCompany() + "'" +
            ", additionalForProfessional='" + isAdditionalForProfessional() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

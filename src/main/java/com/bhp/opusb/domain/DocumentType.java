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

    @Column(name = "for_company")
    private Boolean forCompany;

    @Column(name = "for_professional")
    private Boolean forProfessional;

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

    public Boolean isForCompany() {
        return forCompany;
    }

    public DocumentType forCompany(Boolean forCompany) {
        this.forCompany = forCompany;
        return this;
    }

    public void setForCompany(Boolean forCompany) {
        this.forCompany = forCompany;
    }

    public Boolean isForProfessional() {
        return forProfessional;
    }

    public DocumentType forProfessional(Boolean forProfessional) {
        this.forProfessional = forProfessional;
        return this;
    }

    public void setForProfessional(Boolean forProfessional) {
        this.forProfessional = forProfessional;
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
            ", forCompany='" + isForCompany() + "'" +
            ", forProfessional='" + isForProfessional() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

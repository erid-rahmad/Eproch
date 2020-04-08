package com.bhp.opusb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DocumentTypeBusinessCategory.
 */
@Entity
@Table(name = "doctype_businesscat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DocumentTypeBusinessCategory extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "mandatory")
    private Boolean mandatory;

    @Column(name = "additional")
    private Boolean additional;

    @ManyToOne
    @JsonIgnoreProperties("documentTypeBusinessCategories")
    private DocumentType documentType;

    @ManyToOne
    @JsonIgnoreProperties("documentTypeBusinessCategories")
    private BusinessCategory businessCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public DocumentTypeBusinessCategory mandatory(Boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Boolean isAdditional() {
        return additional;
    }

    public DocumentTypeBusinessCategory additional(Boolean additional) {
        this.additional = additional;
        return this;
    }

    public void setAdditional(Boolean additional) {
        this.additional = additional;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public DocumentTypeBusinessCategory documentType(DocumentType documentType) {
        this.documentType = documentType;
        return this;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public BusinessCategory getBusinessCategory() {
        return businessCategory;
    }

    public DocumentTypeBusinessCategory businessCategory(BusinessCategory businessCategory) {
        this.businessCategory = businessCategory;
        return this;
    }

    public void setBusinessCategory(BusinessCategory businessCategory) {
        this.businessCategory = businessCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentTypeBusinessCategory)) {
            return false;
        }
        return id != null && id.equals(((DocumentTypeBusinessCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DocumentTypeBusinessCategory{" +
            "id=" + getId() +
            ", mandatory='" + isMandatory() + "'" +
            ", additional='" + isAdditional() + "'" +
            "}";
    }
}

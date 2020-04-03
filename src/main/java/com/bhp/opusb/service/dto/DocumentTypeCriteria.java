package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.DocumentType} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.DocumentTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /document-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DocumentTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private BooleanFilter hasExpirationDate;

    private BooleanFilter forCompany;

    private BooleanFilter forProfessional;

    private BooleanFilter active;

    private LongFilter documentTypeBusinessCategoryId;

    public DocumentTypeCriteria() {
    }

    public DocumentTypeCriteria(DocumentTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.hasExpirationDate = other.hasExpirationDate == null ? null : other.hasExpirationDate.copy();
        this.forCompany = other.forCompany == null ? null : other.forCompany.copy();
        this.forProfessional = other.forProfessional == null ? null : other.forProfessional.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.documentTypeBusinessCategoryId = other.documentTypeBusinessCategoryId == null ? null : other.documentTypeBusinessCategoryId.copy();
    }

    @Override
    public DocumentTypeCriteria copy() {
        return new DocumentTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getHasExpirationDate() {
        return hasExpirationDate;
    }

    public void setHasExpirationDate(BooleanFilter hasExpirationDate) {
        this.hasExpirationDate = hasExpirationDate;
    }

    public BooleanFilter getForCompany() {
        return forCompany;
    }

    public void setForCompany(BooleanFilter forCompany) {
        this.forCompany = forCompany;
    }

    public BooleanFilter getForProfessional() {
        return forProfessional;
    }

    public void setForProfessional(BooleanFilter forProfessional) {
        this.forProfessional = forProfessional;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getDocumentTypeBusinessCategoryId() {
        return documentTypeBusinessCategoryId;
    }

    public void setDocumentTypeBusinessCategoryId(LongFilter documentTypeBusinessCategoryId) {
        this.documentTypeBusinessCategoryId = documentTypeBusinessCategoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DocumentTypeCriteria that = (DocumentTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(hasExpirationDate, that.hasExpirationDate) &&
            Objects.equals(forCompany, that.forCompany) &&
            Objects.equals(forProfessional, that.forProfessional) &&
            Objects.equals(active, that.active) &&
            Objects.equals(documentTypeBusinessCategoryId, that.documentTypeBusinessCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        hasExpirationDate,
        forCompany,
        forProfessional,
        active,
        documentTypeBusinessCategoryId
        );
    }

    @Override
    public String toString() {
        return "DocumentTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (hasExpirationDate != null ? "hasExpirationDate=" + hasExpirationDate + ", " : "") +
                (forCompany != null ? "forCompany=" + forCompany + ", " : "") +
                (forProfessional != null ? "forProfessional=" + forProfessional + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (documentTypeBusinessCategoryId != null ? "documentTypeBusinessCategoryId=" + documentTypeBusinessCategoryId + ", " : "") +
            "}";
    }

}

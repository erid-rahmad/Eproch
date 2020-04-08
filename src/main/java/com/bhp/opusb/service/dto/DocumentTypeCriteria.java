package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
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

    private StringFilter mandatoryBusinessCategories;

    private StringFilter additionalBusinessCategories;

    private BooleanFilter mandatoryForCompany;

    private BooleanFilter mandatoryForProfessional;

    private BooleanFilter additionalForCompany;

    private BooleanFilter additionalForProfessional;

    private BooleanFilter active;

    private LongFilter businessCategoryId;

    public DocumentTypeCriteria() {
    }

    public DocumentTypeCriteria(DocumentTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.hasExpirationDate = other.hasExpirationDate == null ? null : other.hasExpirationDate.copy();
        this.mandatoryBusinessCategories = other.mandatoryBusinessCategories == null ? null : other.mandatoryBusinessCategories.copy();
        this.additionalBusinessCategories = other.additionalBusinessCategories == null ? null : other.additionalBusinessCategories.copy();
        this.mandatoryForCompany = other.mandatoryForCompany == null ? null : other.mandatoryForCompany.copy();
        this.mandatoryForProfessional = other.mandatoryForProfessional == null ? null : other.mandatoryForProfessional.copy();
        this.additionalForCompany = other.additionalForCompany == null ? null : other.additionalForCompany.copy();
        this.additionalForProfessional = other.additionalForProfessional == null ? null : other.additionalForProfessional.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.businessCategoryId = other.businessCategoryId == null ? null : other.businessCategoryId.copy();
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

    public StringFilter getMandatoryBusinessCategories() {
        return mandatoryBusinessCategories;
    }

    public void setMandatoryBusinessCategories(StringFilter mandatoryBusinessCategories) {
        this.mandatoryBusinessCategories = mandatoryBusinessCategories;
    }

    public StringFilter getAdditionalBusinessCategories() {
        return additionalBusinessCategories;
    }

    public void setAdditionalBusinessCategories(StringFilter additionalBusinessCategories) {
        this.additionalBusinessCategories = additionalBusinessCategories;
    }

    public BooleanFilter getMandatoryForCompany() {
        return mandatoryForCompany;
    }

    public void setMandatoryForCompany(BooleanFilter mandatoryForCompany) {
        this.mandatoryForCompany = mandatoryForCompany;
    }

    public BooleanFilter getMandatoryForProfessional() {
        return mandatoryForProfessional;
    }

    public void setMandatoryForProfessional(BooleanFilter mandatoryForProfessional) {
        this.mandatoryForProfessional = mandatoryForProfessional;
    }

    public BooleanFilter getAdditionalForCompany() {
        return additionalForCompany;
    }

    public void setAdditionalForCompany(BooleanFilter additionalForCompany) {
        this.additionalForCompany = additionalForCompany;
    }

    public BooleanFilter getAdditionalForProfessional() {
        return additionalForProfessional;
    }

    public void setAdditionalForProfessional(BooleanFilter additionalForProfessional) {
        this.additionalForProfessional = additionalForProfessional;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(LongFilter businessCategoryId) {
        this.businessCategoryId = businessCategoryId;
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
            Objects.equals(mandatoryBusinessCategories, that.mandatoryBusinessCategories) &&
            Objects.equals(additionalBusinessCategories, that.additionalBusinessCategories) &&
            Objects.equals(mandatoryForCompany, that.mandatoryForCompany) &&
            Objects.equals(mandatoryForProfessional, that.mandatoryForProfessional) &&
            Objects.equals(additionalForCompany, that.additionalForCompany) &&
            Objects.equals(additionalForProfessional, that.additionalForProfessional) &&
            Objects.equals(active, that.active) &&
            Objects.equals(businessCategoryId, that.businessCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        hasExpirationDate,
        mandatoryBusinessCategories,
        additionalBusinessCategories,
        mandatoryForCompany,
        mandatoryForProfessional,
        additionalForCompany,
        additionalForProfessional,
        active,
        businessCategoryId
        );
    }

    @Override
    public String toString() {
        return "DocumentTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (hasExpirationDate != null ? "hasExpirationDate=" + hasExpirationDate + ", " : "") +
                (mandatoryBusinessCategories != null ? "mandatoryBusinessCategories=" + mandatoryBusinessCategories + ", " : "") +
                (additionalBusinessCategories != null ? "additionalBusinessCategories=" + additionalBusinessCategories + ", " : "") +
                (mandatoryForCompany != null ? "mandatoryForCompany=" + mandatoryForCompany + ", " : "") +
                (mandatoryForProfessional != null ? "mandatoryForProfessional=" + mandatoryForProfessional + ", " : "") +
                (additionalForCompany != null ? "additionalForCompany=" + additionalForCompany + ", " : "") +
                (additionalForProfessional != null ? "additionalForProfessional=" + additionalForProfessional + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (businessCategoryId != null ? "businessCategoryId=" + businessCategoryId + ", " : "") +
            "}";
    }

}

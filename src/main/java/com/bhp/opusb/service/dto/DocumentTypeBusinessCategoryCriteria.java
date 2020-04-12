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
 * Criteria class for the {@link com.bhp.opusb.domain.DocumentTypeBusinessCategory} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.DocumentTypeBusinessCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /document-type-business-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DocumentTypeBusinessCategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter mandatory;

    private BooleanFilter additional;

    private LongFilter documentTypeId;

    private LongFilter businessCategoryId;

    public DocumentTypeBusinessCategoryCriteria() {
    }

    public DocumentTypeBusinessCategoryCriteria(DocumentTypeBusinessCategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.mandatory = other.mandatory == null ? null : other.mandatory.copy();
        this.additional = other.additional == null ? null : other.additional.copy();
        this.documentTypeId = other.documentTypeId == null ? null : other.documentTypeId.copy();
        this.businessCategoryId = other.businessCategoryId == null ? null : other.businessCategoryId.copy();
    }

    @Override
    public DocumentTypeBusinessCategoryCriteria copy() {
        return new DocumentTypeBusinessCategoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getMandatory() {
        return mandatory;
    }

    public void setMandatory(BooleanFilter mandatory) {
        this.mandatory = mandatory;
    }

    public BooleanFilter getAdditional() {
        return additional;
    }

    public void setAdditional(BooleanFilter additional) {
        this.additional = additional;
    }

    public LongFilter getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(LongFilter documentTypeId) {
        this.documentTypeId = documentTypeId;
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
        final DocumentTypeBusinessCategoryCriteria that = (DocumentTypeBusinessCategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(mandatory, that.mandatory) &&
            Objects.equals(additional, that.additional) &&
            Objects.equals(documentTypeId, that.documentTypeId) &&
            Objects.equals(businessCategoryId, that.businessCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        mandatory,
        additional,
        documentTypeId,
        businessCategoryId
        );
    }

    @Override
    public String toString() {
        return "DocumentTypeBusinessCategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (mandatory != null ? "mandatory=" + mandatory + ", " : "") +
                (additional != null ? "additional=" + additional + ", " : "") +
                (documentTypeId != null ? "documentTypeId=" + documentTypeId + ", " : "") +
                (businessCategoryId != null ? "businessCategoryId=" + businessCategoryId + ", " : "") +
            "}";
    }

}

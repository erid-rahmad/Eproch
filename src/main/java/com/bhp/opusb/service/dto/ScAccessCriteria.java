package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.ScAccess} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.ScAccessResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sc-accesses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ScAccessCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter name;

    private StringFilter description;

    private BooleanFilter canWrite;

    private LongFilter adOrganizationId;

    private LongFilter typeId;
    private StringFilter typeName;

    private LongFilter windowId;

    private LongFilter formId;

    private LongFilter paDashboardId;

    private LongFilter paDashboardItemId;

    private LongFilter documentTypeId;

    private LongFilter referenceListId;

    private LongFilter authorityId;
    private StringFilter authorityName;

    public ScAccessCriteria() {
    }

    public ScAccessCriteria(ScAccessCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.canWrite = other.canWrite == null ? null : other.canWrite.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
        this.typeName = other.typeName == null ? null : other.typeName.copy();
        this.windowId = other.windowId == null ? null : other.windowId.copy();
        this.formId = other.formId == null ? null : other.formId.copy();
        this.paDashboardId = other.paDashboardId == null ? null : other.paDashboardId.copy();
        this.paDashboardItemId = other.paDashboardItemId == null ? null : other.paDashboardItemId.copy();
        this.documentTypeId = other.documentTypeId == null ? null : other.documentTypeId.copy();
        this.referenceListId = other.referenceListId == null ? null : other.referenceListId.copy();
        this.authorityId = other.authorityId == null ? null : other.authorityId.copy();
        this.authorityName = other.authorityName == null ? null : other.authorityName.copy();
    }

    @Override
    public ScAccessCriteria copy() {
        return new ScAccessCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public UUIDFilter getUid() {
        return uid;
    }

    public void setUid(UUIDFilter uid) {
        this.uid = uid;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
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

    public BooleanFilter getCanWrite() {
        return canWrite;
    }

    public void setCanWrite(BooleanFilter canWrite) {
        this.canWrite = canWrite;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getTypeId() {
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }

    public StringFilter getTypeName() {
        return typeName;
    }

    public void setTypeName(StringFilter typeName) {
        this.typeName = typeName;
    }

    public LongFilter getWindowId() {
        return windowId;
    }

    public void setWindowId(LongFilter windowId) {
        this.windowId = windowId;
    }

    public LongFilter getFormId() {
        return formId;
    }

    public void setFormId(LongFilter formId) {
        this.formId = formId;
    }

    public LongFilter getPaDashboardId() {
        return paDashboardId;
    }

    public void setPaDashboardId(LongFilter paDashboardId) {
        this.paDashboardId = paDashboardId;
    }

    public LongFilter getPaDashboardItemId() {
        return paDashboardItemId;
    }

    public void setPaDashboardItemId(LongFilter paDashboardItemId) {
        this.paDashboardItemId = paDashboardItemId;
    }

    public LongFilter getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(LongFilter documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public LongFilter getReferenceListId() {
        return referenceListId;
    }

    public void setReferenceListId(LongFilter referenceListId) {
        this.referenceListId = referenceListId;
    }

    public LongFilter getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(LongFilter authorityId) {
        this.authorityId = authorityId;
    }

    public StringFilter getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(StringFilter authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ScAccessCriteria that = (ScAccessCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(canWrite, that.canWrite) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(typeId, that.typeId) &&
            Objects.equals(typeName, that.typeName) &&
            Objects.equals(windowId, that.windowId) &&
            Objects.equals(formId, that.formId) &&
            Objects.equals(paDashboardId, that.paDashboardId) &&
            Objects.equals(paDashboardItemId, that.paDashboardItemId) &&
            Objects.equals(documentTypeId, that.documentTypeId) &&
            Objects.equals(referenceListId, that.referenceListId) &&
            Objects.equals(authorityId, that.authorityId) &&
            Objects.equals(authorityName, that.authorityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        name,
        description,
        canWrite,
        adOrganizationId,
        typeId,
        typeName,
        windowId,
        formId,
        paDashboardId,
        paDashboardItemId,
        documentTypeId,
        referenceListId,
        authorityId,
        authorityName
        );
    }

    @Override
    public String toString() {
        return "ScAccessCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (canWrite != null ? "canWrite=" + canWrite + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (typeId != null ? "typeId=" + typeId + ", " : "") +
                (typeName != null ? "typeName=" + typeName + ", " : "") +
                (windowId != null ? "windowId=" + windowId + ", " : "") +
                (formId != null ? "formId=" + formId + ", " : "") +
                (paDashboardId != null ? "paDashboardId=" + paDashboardId + ", " : "") +
                (paDashboardItemId != null ? "paDashboardItemId=" + paDashboardItemId + ", " : "") +
                (documentTypeId != null ? "documentTypeId=" + documentTypeId + ", " : "") +
                (referenceListId != null ? "referenceListId=" + referenceListId + ", " : "") +
                (authorityId != null ? "authorityId=" + authorityId + ", " : "") +
                (authorityName != null ? "authorityName=" + authorityName + ", " : "") +
            "}";
    }

}

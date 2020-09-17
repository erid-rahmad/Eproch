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
 * Criteria class for the {@link com.bhp.opusb.domain.ScAuthority} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.ScAuthorityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sc-authorities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ScAuthorityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private StringFilter description;

    private BooleanFilter master;

    private StringFilter authorityName;

    private LongFilter scAccessId;

    private LongFilter adOrganizationId;

    public ScAuthorityCriteria() {
    }

    public ScAuthorityCriteria(ScAuthorityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.master = other.master == null ? null : other.master.copy();
        this.authorityName = other.authorityName == null ? null : other.authorityName.copy();
        this.scAccessId = other.scAccessId == null ? null : other.scAccessId.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
    }

    @Override
    public ScAuthorityCriteria copy() {
        return new ScAuthorityCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getMaster() {
        return master;
    }

    public void setMaster(BooleanFilter master) {
        this.master = master;
    }

    public StringFilter getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(StringFilter authorityName) {
        this.authorityName = authorityName;
    }

    public LongFilter getScAccessId() {
        return scAccessId;
    }

    public void setScAccessId(LongFilter scAccessId) {
        this.scAccessId = scAccessId;
    }

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ScAuthorityCriteria that = (ScAuthorityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(description, that.description) &&
            Objects.equals(master, that.master) &&
            Objects.equals(authorityName, that.authorityName) &&
            Objects.equals(scAccessId, that.scAccessId) &&
            Objects.equals(adOrganizationId, that.adOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        description,
        master,
        authorityName,
        scAccessId,
        adOrganizationId
        );
    }

    @Override
    public String toString() {
        return "ScAuthorityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (master != null ? "master=" + master + ", " : "") +
                (authorityName != null ? "authorityName=" + authorityName + ", " : "") +
                (scAccessId != null ? "scAccessId=" + scAccessId + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
            "}";
    }

}

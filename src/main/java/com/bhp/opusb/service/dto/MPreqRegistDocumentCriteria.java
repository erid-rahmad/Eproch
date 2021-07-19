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
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MPreqRegistDocument} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MPreqRegistDocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-preq-regist-documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MPreqRegistDocumentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter registrationId;

    private LongFilter siupDocumentId;

    private LongFilter spdaDocumentId;

    public MPreqRegistDocumentCriteria() {
    }

    public MPreqRegistDocumentCriteria(MPreqRegistDocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.registrationId = other.registrationId == null ? null : other.registrationId.copy();
        this.siupDocumentId = other.siupDocumentId == null ? null : other.siupDocumentId.copy();
        this.spdaDocumentId = other.spdaDocumentId == null ? null : other.spdaDocumentId.copy();
    }

    @Override
    public MPreqRegistDocumentCriteria copy() {
        return new MPreqRegistDocumentCriteria(this);
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

    public LongFilter getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(LongFilter adOrganizationId) {
        this.adOrganizationId = adOrganizationId;
    }

    public LongFilter getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(LongFilter registrationId) {
        this.registrationId = registrationId;
    }

    public LongFilter getSiupDocumentId() {
        return siupDocumentId;
    }

    public void setSiupDocumentId(LongFilter siupDocumentId) {
        this.siupDocumentId = siupDocumentId;
    }

    public LongFilter getSpdaDocumentId() {
        return spdaDocumentId;
    }

    public void setSpdaDocumentId(LongFilter spdaDocumentId) {
        this.spdaDocumentId = spdaDocumentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MPreqRegistDocumentCriteria that = (MPreqRegistDocumentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(registrationId, that.registrationId) &&
            Objects.equals(siupDocumentId, that.siupDocumentId) &&
            Objects.equals(spdaDocumentId, that.spdaDocumentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uid,
        active,
        adOrganizationId,
        registrationId,
        siupDocumentId,
        spdaDocumentId
        );
    }

    @Override
    public String toString() {
        return "MPreqRegistDocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (registrationId != null ? "registrationId=" + registrationId + ", " : "") +
                (siupDocumentId != null ? "siupDocumentId=" + siupDocumentId + ", " : "") +
                (spdaDocumentId != null ? "spdaDocumentId=" + spdaDocumentId + ", " : "") +
            "}";
    }

}

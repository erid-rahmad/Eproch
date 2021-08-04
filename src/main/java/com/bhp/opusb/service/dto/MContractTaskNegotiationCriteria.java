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
 * Criteria class for the {@link com.bhp.opusb.domain.MContractTaskNegotiation} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MContractTaskNegotiationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-contract-task-negotiations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MContractTaskNegotiationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter contractTaskId;

    private LongFilter cAttachmentId;

    public MContractTaskNegotiationCriteria() {
    }

    public MContractTaskNegotiationCriteria(MContractTaskNegotiationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.contractTaskId = other.contractTaskId == null ? null : other.contractTaskId.copy();
        this.cAttachmentId = other.cAttachmentId == null ? null : other.cAttachmentId.copy();
    }

    @Override
    public MContractTaskNegotiationCriteria copy() {
        return new MContractTaskNegotiationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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

    public LongFilter getContractTaskId() {
        return contractTaskId;
    }

    public void setContractTaskId(LongFilter contractTaskId) {
        this.contractTaskId = contractTaskId;
    }

    public LongFilter getCAttachmentId() {
        return cAttachmentId;
    }

    public void setCAttachmentId(LongFilter cAttachmentId) {
        this.cAttachmentId = cAttachmentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MContractTaskNegotiationCriteria that = (MContractTaskNegotiationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(contractTaskId, that.contractTaskId) &&
            Objects.equals(cAttachmentId, that.cAttachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        uid,
        active,
        adOrganizationId,
        contractTaskId,
        cAttachmentId
        );
    }

    @Override
    public String toString() {
        return "MContractTaskNegotiationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (contractTaskId != null ? "contractTaskId=" + contractTaskId + ", " : "") +
                (cAttachmentId != null ? "cAttachmentId=" + cAttachmentId + ", " : "") +
            "}";
    }

}

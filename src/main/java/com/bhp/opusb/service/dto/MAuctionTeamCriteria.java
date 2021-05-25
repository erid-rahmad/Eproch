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
 * Criteria class for the {@link com.bhp.opusb.domain.MAuctionTeam} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MAuctionTeamResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-auction-teams?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MAuctionTeamCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter role;

    private UUIDFilter uid;

    private BooleanFilter active;

    private LongFilter adOrganizationId;

    private LongFilter userUserId;

    private LongFilter auctionId;

    public MAuctionTeamCriteria() {
    }

    public MAuctionTeamCriteria(MAuctionTeamCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.uid = other.uid == null ? null : other.uid.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.adOrganizationId = other.adOrganizationId == null ? null : other.adOrganizationId.copy();
        this.userUserId = other.userUserId == null ? null : other.userUserId.copy();
        this.auctionId = other.auctionId == null ? null : other.auctionId.copy();
    }

    @Override
    public MAuctionTeamCriteria copy() {
        return new MAuctionTeamCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRole() {
        return role;
    }

    public void setRole(StringFilter role) {
        this.role = role;
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

    public LongFilter getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(LongFilter userId) {
        this.userUserId = userId;
    }

    public LongFilter getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(LongFilter auctionId) {
        this.auctionId = auctionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MAuctionTeamCriteria that = (MAuctionTeamCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(role, that.role) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(active, that.active) &&
            Objects.equals(adOrganizationId, that.adOrganizationId) &&
            Objects.equals(userUserId, that.userUserId) &&
            Objects.equals(auctionId, that.auctionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        role,
        uid,
        active,
        adOrganizationId,
        userUserId,
        auctionId
        );
    }

    @Override
    public String toString() {
        return "MAuctionTeamCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (role != null ? "role=" + role + ", " : "") +
                (uid != null ? "uid=" + uid + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (adOrganizationId != null ? "adOrganizationId=" + adOrganizationId + ", " : "") +
                (userUserId != null ? "userId=" + userUserId + ", " : "") +
                (auctionId != null ? "auctionId=" + auctionId + ", " : "") +
            "}";
    }

}

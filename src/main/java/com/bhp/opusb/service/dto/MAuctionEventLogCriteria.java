package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.bhp.opusb.domain.MAuctionEventLog} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MAuctionEventLogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-auction-event-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MAuctionEventLogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter action;

    private InstantFilter dateTrx;

    private StringFilter username;

    private BigDecimalFilter price;

    private StringFilter note;

    private LongFilter auctionId;

    private LongFilter auctionItemId;

    private LongFilter vendorId;

    public MAuctionEventLogCriteria() {
    }

    public MAuctionEventLogCriteria(MAuctionEventLogCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.action = other.action == null ? null : other.action.copy();
        this.dateTrx = other.dateTrx == null ? null : other.dateTrx.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.auctionId = other.auctionId == null ? null : other.auctionId.copy();
        this.auctionItemId = other.auctionItemId == null ? null : other.auctionItemId.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
    }

    @Override
    public MAuctionEventLogCriteria copy() {
        return new MAuctionEventLogCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAction() {
        return action;
    }

    public void setAction(StringFilter action) {
        this.action = action;
    }

    public InstantFilter getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(InstantFilter dateTrx) {
        this.dateTrx = dateTrx;
    }

    public StringFilter getUsername() {
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public LongFilter getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(LongFilter auctionId) {
        this.auctionId = auctionId;
    }

    public LongFilter getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(LongFilter auctionItemId) {
        this.auctionItemId = auctionItemId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MAuctionEventLogCriteria that = (MAuctionEventLogCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(action, that.action) &&
            Objects.equals(dateTrx, that.dateTrx) &&
            Objects.equals(username, that.username) &&
            Objects.equals(price, that.price) &&
            Objects.equals(note, that.note) &&
            Objects.equals(auctionId, that.auctionId) &&
            Objects.equals(auctionItemId, that.auctionItemId) &&
            Objects.equals(vendorId, that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        action,
        dateTrx,
        username,
        price,
        note,
        auctionId,
        auctionItemId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "MAuctionEventLogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (action != null ? "action=" + action + ", " : "") +
                (dateTrx != null ? "dateTrx=" + dateTrx + ", " : "") +
                (username != null ? "username=" + username + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (auctionId != null ? "auctionId=" + auctionId + ", " : "") +
                (auctionItemId != null ? "auctionItemId=" + auctionItemId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}

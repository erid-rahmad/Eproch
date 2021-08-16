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
 * Criteria class for the {@link com.bhp.opusb.domain.MAuctionSubmissionLog} entity. This class is used
 * in {@link com.bhp.opusb.web.rest.MAuctionSubmissionLogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-auction-submission-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MAuctionSubmissionLogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter action;

    private StringFilter userName;

    private BigDecimalFilter price;

    private InstantFilter dateTrx;

    private StringFilter message;

    private LongFilter auctionItemId;

    public MAuctionSubmissionLogCriteria() {
    }

    public MAuctionSubmissionLogCriteria(MAuctionSubmissionLogCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.action = other.action == null ? null : other.action.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.dateTrx = other.dateTrx == null ? null : other.dateTrx.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.auctionItemId = other.auctionItemId == null ? null : other.auctionItemId.copy();
    }

    @Override
    public MAuctionSubmissionLogCriteria copy() {
        return new MAuctionSubmissionLogCriteria(this);
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

    public StringFilter getUserName() {
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public InstantFilter getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(InstantFilter dateTrx) {
        this.dateTrx = dateTrx;
    }

    public StringFilter getMessage() {
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public LongFilter getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(LongFilter auctionItemId) {
        this.auctionItemId = auctionItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MAuctionSubmissionLogCriteria that = (MAuctionSubmissionLogCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(action, that.action) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(price, that.price) &&
            Objects.equals(dateTrx, that.dateTrx) &&
            Objects.equals(message, that.message) &&
            Objects.equals(auctionItemId, that.auctionItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        action,
        userName,
        price,
        dateTrx,
        message,
        auctionItemId
        );
    }

    @Override
    public String toString() {
        return "MAuctionSubmissionLogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (action != null ? "action=" + action + ", " : "") +
                (userName != null ? "userName=" + userName + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (dateTrx != null ? "dateTrx=" + dateTrx + ", " : "") +
                (message != null ? "message=" + message + ", " : "") +
                (auctionItemId != null ? "auctionItemId=" + auctionItemId + ", " : "") +
            "}";
    }

}

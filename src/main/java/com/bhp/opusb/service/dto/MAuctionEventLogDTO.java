package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MAuctionEventLog} entity.
 */
public class MAuctionEventLogDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    @Size(max = 10)
    private String action;

    @NotNull
    private Instant dateTrx;

    @NotNull
    private String username;

    private BigDecimal price;

    private String note;


    private Long auctionId;
    private String auctionName;

    private Long auctionItemId;
    private String auctionItemName;

    private Long vendorId;
    private String vendorName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Instant getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(Instant dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long mAuctionId) {
        this.auctionId = mAuctionId;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public Long getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(Long mAuctionItemId) {
        this.auctionItemId = mAuctionItemId;
    }

    public String getAuctionItemName() {
        return auctionItemName;
    }

    public void setAuctionItemName(String auctionItemName) {
        this.auctionItemName = auctionItemName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long cVendorId) {
        this.vendorId = cVendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MAuctionEventLogDTO mAuctionEventLogDTO = (MAuctionEventLogDTO) o;
        if (mAuctionEventLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mAuctionEventLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MAuctionEventLogDTO{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", username='" + getUsername() + "'" +
            ", price=" + getPrice() +
            ", note='" + getNote() + "'" +
            ", auctionId=" + getAuctionId() +
            ", auctionItemId=" + getAuctionItemId() +
            ", vendorId=" + getVendorId() +
            "}";
    }
}

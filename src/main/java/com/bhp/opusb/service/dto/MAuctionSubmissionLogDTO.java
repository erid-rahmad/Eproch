package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MAuctionSubmissionLog} entity.
 */
public class MAuctionSubmissionLogDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    @Size(max = 10)
    private String action;

    @NotNull
    private String userName;

    private BigDecimal price;

    @NotNull
    private Instant dateTrx = Instant.now();

    private String message;


    private Long auctionItemId;
    
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Instant getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(Instant dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(Long mAuctionItemId) {
        this.auctionItemId = mAuctionItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO = (MAuctionSubmissionLogDTO) o;
        if (mAuctionSubmissionLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mAuctionSubmissionLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MAuctionSubmissionLogDTO{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", userName='" + getUserName() + "'" +
            ", price=" + getPrice() +
            ", dateTrx='" + getDateTrx() + "'" +
            ", message='" + getMessage() + "'" +
            ", auctionItemId=" + getAuctionItemId() +
            "}";
    }
}

package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MRfqView} entity.
 */
public class MRfqViewDTO implements Serializable {
    
    private Long id;

    private String documentNo;

    private String title;

    private LocalDate dateRequired;

    @Size(max = 10)
    private String selectionMethod;

    private Integer joinedVendorCount;


    private Long quotationId;
    private BigDecimal grandTotal;
    
    public Long getId() {
        return id;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDateRequired() {
        return dateRequired;
    }

    public void setDateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
    }

    public String getSelectionMethod() {
        return selectionMethod;
    }

    public void setSelectionMethod(String selectionMethod) {
        this.selectionMethod = selectionMethod;
    }

    public Integer getJoinedVendorCount() {
        return joinedVendorCount;
    }

    public void setJoinedVendorCount(Integer joinedVendorCount) {
        this.joinedVendorCount = joinedVendorCount;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long mRfqId) {
        this.quotationId = mRfqId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MRfqViewDTO mRfqViewDTO = (MRfqViewDTO) o;
        if (mRfqViewDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mRfqViewDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MRfqViewDTO{" +
            "id=" + getId() +
            ", documentNo='" + getDocumentNo() + "'" +
            ", title='" + getTitle() + "'" +
            ", dateRequired='" + getDateRequired() + "'" +
            ", selectionMethod='" + getSelectionMethod() + "'" +
            ", joinedVendorCount=" + getJoinedVendorCount() +
            ", quotationId=" + getQuotationId() +
            "}";
    }
}

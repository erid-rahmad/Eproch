package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A MRfqView.
 */
@Entity
@Immutable
@Table(name = "m_rfq_view")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MRfqView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "document_no")
    private String documentNo;

    @Column(name = "title")
    private String title;

    @Column(name = "date_required")
    private LocalDate dateRequired;

    @Size(max = 10)
    @Column(name = "selection_method", length = 10)
    private String selectionMethod;

    @Column(name = "joined_vendor_count")
    private Integer joinedVendorCount;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRfqViews")
    private MRfq quotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public MRfqView documentNo(String documentNo) {
        this.documentNo = documentNo;
        return this;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getTitle() {
        return title;
    }

    public MRfqView title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDateRequired() {
        return dateRequired;
    }

    public MRfqView dateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
        return this;
    }

    public void setDateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
    }

    public String getSelectionMethod() {
        return selectionMethod;
    }

    public MRfqView selectionMethod(String selectionMethod) {
        this.selectionMethod = selectionMethod;
        return this;
    }

    public void setSelectionMethod(String selectionMethod) {
        this.selectionMethod = selectionMethod;
    }

    public Integer getJoinedVendorCount() {
        return joinedVendorCount;
    }

    public MRfqView joinedVendorCount(Integer joinedVendorCount) {
        this.joinedVendorCount = joinedVendorCount;
        return this;
    }

    public void setJoinedVendorCount(Integer joinedVendorCount) {
        this.joinedVendorCount = joinedVendorCount;
    }

    public MRfq getQuotation() {
        return quotation;
    }

    public MRfqView quotation(MRfq mRfq) {
        this.quotation = mRfq;
        return this;
    }

    public void setQuotation(MRfq mRfq) {
        this.quotation = mRfq;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MRfqView)) {
            return false;
        }
        return id != null && id.equals(((MRfqView) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MRfqView{" +
            "id=" + getId() +
            ", documentNo='" + getDocumentNo() + "'" +
            ", title='" + getTitle() + "'" +
            ", dateRequired='" + getDateRequired() + "'" +
            ", selectionMethod='" + getSelectionMethod() + "'" +
            ", joinedVendorCount=" + getJoinedVendorCount() +
            "}";
    }
}

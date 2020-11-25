package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * A MVerificationTax.
 */
@Entity
@Table(name = "m_verification_tax")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVerificationTax extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tax_period", nullable = false)
    private Long taxPeriod;

    @NotNull
    @Column(name = "trax_code", nullable = false)
    private String traxCode;

    @NotNull
    @Column(name = "status_code", nullable = false)
    private String statusCode;

    @NotNull
    @Column(name = "doc_code", nullable = false)
    private String docCode;

    @NotNull
    @Column(name = "year", nullable = false)
    private Long year;

    @Column(name = "return_doc_type")
    private String returnDocType;

    @Column(name = "rep_ser_no")
    private String repSerNo;

    @Column(name = "tax_exp_code")
    private String taxExpCode;

    @Column(name = "date_ssp")
    private LocalDate dateSSP;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerificationTaxes")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerificationTaxes")
    private CCurrency currency;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerificationTaxes")
    private CTaxCategory taxCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVerificationTaxes")
    private CCostCenter costCenter;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaxPeriod() {
        return taxPeriod;
    }

    public MVerificationTax taxPeriod(Long taxPeriod) {
        this.taxPeriod = taxPeriod;
        return this;
    }

    public void setTaxPeriod(Long taxPeriod) {
        this.taxPeriod = taxPeriod;
    }

    public String getTraxCode() {
        return traxCode;
    }

    public MVerificationTax traxCode(String traxCode) {
        this.traxCode = traxCode;
        return this;
    }

    public void setTraxCode(String traxCode) {
        this.traxCode = traxCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public MVerificationTax statusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDocCode() {
        return docCode;
    }

    public MVerificationTax docCode(String docCode) {
        this.docCode = docCode;
        return this;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public Long getYear() {
        return year;
    }

    public MVerificationTax year(Long year) {
        this.year = year;
        return this;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getReturnDocType() {
        return returnDocType;
    }

    public MVerificationTax returnDocType(String returnDocType) {
        this.returnDocType = returnDocType;
        return this;
    }

    public void setReturnDocType(String returnDocType) {
        this.returnDocType = returnDocType;
    }

    public String getRepSerNo() {
        return repSerNo;
    }

    public MVerificationTax repSerNo(String repSerNo) {
        this.repSerNo = repSerNo;
        return this;
    }

    public void setRepSerNo(String repSerNo) {
        this.repSerNo = repSerNo;
    }

    public String getTaxExpCode() {
        return taxExpCode;
    }

    public MVerificationTax taxExpCode(String taxExpCode) {
        this.taxExpCode = taxExpCode;
        return this;
    }

    public void setTaxExpCode(String taxExpCode) {
        this.taxExpCode = taxExpCode;
    }

    public LocalDate getDateSSP() {
        return dateSSP;
    }

    public MVerificationTax dateSSP(LocalDate dateSSP) {
        this.dateSSP = dateSSP;
        return this;
    }

    public void setDateSSP(LocalDate dateSSP) {
        this.dateSSP = dateSSP;
    }

    public UUID getUid() {
        return uid;
    }

    public MVerificationTax uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MVerificationTax active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MVerificationTax adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CCurrency getCurrency() {
        return currency;
    }

    public MVerificationTax currency(CCurrency cCurrency) {
        this.currency = cCurrency;
        return this;
    }

    public void setCurrency(CCurrency cCurrency) {
        this.currency = cCurrency;
    }

    public CTaxCategory getTaxCategory() {
        return taxCategory;
    }

    public MVerificationTax taxCategory(CTaxCategory cTaxCategory) {
        this.taxCategory = cTaxCategory;
        return this;
    }

    public void setTaxCategory(CTaxCategory cTaxCategory) {
        this.taxCategory = cTaxCategory;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public MVerificationTax costCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
        return this;
    }

    public void setCostCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MVerificationTax)) {
            return false;
        }
        return id != null && id.equals(((MVerificationTax) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MVerificationTax{" +
            "id=" + getId() +
            ", taxPeriod=" + getTaxPeriod() +
            ", traxCode='" + getTraxCode() + "'" +
            ", statusCode='" + getStatusCode() + "'" +
            ", docCode='" + getDocCode() + "'" +
            ", year=" + getYear() +
            ", returnDocType='" + getReturnDocType() + "'" +
            ", repSerNo='" + getRepSerNo() + "'" +
            ", taxExpCode='" + getTaxExpCode() + "'" +
            ", dateSSP='" + getDateSSP() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

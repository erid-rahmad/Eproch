package com.bhp.opusb.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bhp.opusb.domain.enumeration.CTransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The CPaymentTerm entity.\n@author A true hipster
 */
@Entity
@Table(name = "c_payment_term")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CPaymentTerm extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "after_delivery")
    private Boolean afterDelivery = false;

    @Column(name = "as_default")
    private Boolean asDefault = false;

    @Column(name = "calculate_business_day")
    private Boolean calculateBusinessDay = true;

    @NotNull
    @Column(name = "discount", precision = 21, scale = 2, nullable = false)
    private BigDecimal discount = new BigDecimal(0);

    @NotNull
    @Column(name = "discount_2", precision = 21, scale = 2, nullable = false)
    private BigDecimal discount2 = new BigDecimal(0);

    @NotNull
    @Column(name = "discount_days", nullable = false)
    private Long discountDays = 0L;

    @NotNull
    @Column(name = "discount_days_2", nullable = false)
    private Long discountDays2 = 0L;

    @Size(max = 1000)
    @Column(name = "document_note", length = 1000)
    private String documentNote;

    @Column(name = "fix_month_cut_off")
    private Long fixMonthCutOff;

    @Column(name = "fix_month_day")
    private Long fixMonthDay;

    @Column(name = "fix_month_offset")
    private Long fixMonthOffset;

    @Column(name = "fixed_due_date")
    private Boolean fixedDueDate;

    @Column(name = "grace_days")
    private Long graceDays;

    @Size(max = 1)
    @Column(name = "net_day", length = 1)
    private String netDay;

    @NotNull
    @Column(name = "net_days", nullable = false)
    private Long netDays = 0L;

    @Column(name = "on_next_business_day")
    private Boolean onNextBusinessDay = true;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private CTransactionType transactionType = CTransactionType.BOTH;

    @Column(name = "valid")
    private Boolean valid = false;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active = true;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPaymentTerms")
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public CPaymentTerm code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public CPaymentTerm name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CPaymentTerm description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isAfterDelivery() {
        return afterDelivery;
    }

    public CPaymentTerm afterDelivery(Boolean afterDelivery) {
        this.afterDelivery = afterDelivery;
        return this;
    }

    public void setAfterDelivery(Boolean afterDelivery) {
        this.afterDelivery = afterDelivery;
    }

    public Boolean isAsDefault() {
        return asDefault;
    }

    public CPaymentTerm asDefault(Boolean asDefault) {
        this.asDefault = asDefault;
        return this;
    }

    public void setAsDefault(Boolean asDefault) {
        this.asDefault = asDefault;
    }

    public Boolean isCalculateBusinessDay() {
        return calculateBusinessDay;
    }

    public CPaymentTerm calculateBusinessDay(Boolean calculateBusinessDay) {
        this.calculateBusinessDay = calculateBusinessDay;
        return this;
    }

    public void setCalculateBusinessDay(Boolean calculateBusinessDay) {
        this.calculateBusinessDay = calculateBusinessDay;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public CPaymentTerm discount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscount2() {
        return discount2;
    }

    public CPaymentTerm discount2(BigDecimal discount2) {
        this.discount2 = discount2;
        return this;
    }

    public void setDiscount2(BigDecimal discount2) {
        this.discount2 = discount2;
    }

    public Long getDiscountDays() {
        return discountDays;
    }

    public CPaymentTerm discountDays(Long discountDays) {
        this.discountDays = discountDays;
        return this;
    }

    public void setDiscountDays(Long discountDays) {
        this.discountDays = discountDays;
    }

    public Long getDiscountDays2() {
        return discountDays2;
    }

    public CPaymentTerm discountDays2(Long discountDays2) {
        this.discountDays2 = discountDays2;
        return this;
    }

    public void setDiscountDays2(Long discountDays2) {
        this.discountDays2 = discountDays2;
    }

    public String getDocumentNote() {
        return documentNote;
    }

    public CPaymentTerm documentNote(String documentNote) {
        this.documentNote = documentNote;
        return this;
    }

    public void setDocumentNote(String documentNote) {
        this.documentNote = documentNote;
    }

    public Long getFixMonthCutOff() {
        return fixMonthCutOff;
    }

    public CPaymentTerm fixMonthCutOff(Long fixMonthCutOff) {
        this.fixMonthCutOff = fixMonthCutOff;
        return this;
    }

    public void setFixMonthCutOff(Long fixMonthCutOff) {
        this.fixMonthCutOff = fixMonthCutOff;
    }

    public Long getFixMonthDay() {
        return fixMonthDay;
    }

    public CPaymentTerm fixMonthDay(Long fixMonthDay) {
        this.fixMonthDay = fixMonthDay;
        return this;
    }

    public void setFixMonthDay(Long fixMonthDay) {
        this.fixMonthDay = fixMonthDay;
    }

    public Long getFixMonthOffset() {
        return fixMonthOffset;
    }

    public CPaymentTerm fixMonthOffset(Long fixMonthOffset) {
        this.fixMonthOffset = fixMonthOffset;
        return this;
    }

    public void setFixMonthOffset(Long fixMonthOffset) {
        this.fixMonthOffset = fixMonthOffset;
    }

    public Boolean isFixedDueDate() {
        return fixedDueDate;
    }

    public CPaymentTerm fixedDueDate(Boolean fixedDueDate) {
        this.fixedDueDate = fixedDueDate;
        return this;
    }

    public void setFixedDueDate(Boolean fixedDueDate) {
        this.fixedDueDate = fixedDueDate;
    }

    public Long getGraceDays() {
        return graceDays;
    }

    public CPaymentTerm graceDays(Long graceDays) {
        this.graceDays = graceDays;
        return this;
    }

    public void setGraceDays(Long graceDays) {
        this.graceDays = graceDays;
    }

    public String getNetDay() {
        return netDay;
    }

    public CPaymentTerm netDay(String netDay) {
        this.netDay = netDay;
        return this;
    }

    public void setNetDay(String netDay) {
        this.netDay = netDay;
    }

    public Long getNetDays() {
        return netDays;
    }

    public CPaymentTerm netDays(Long netDays) {
        this.netDays = netDays;
        return this;
    }

    public void setNetDays(Long netDays) {
        this.netDays = netDays;
    }

    public Boolean isOnNextBusinessDay() {
        return onNextBusinessDay;
    }

    public CPaymentTerm onNextBusinessDay(Boolean onNextBusinessDay) {
        this.onNextBusinessDay = onNextBusinessDay;
        return this;
    }

    public void setOnNextBusinessDay(Boolean onNextBusinessDay) {
        this.onNextBusinessDay = onNextBusinessDay;
    }

    public CTransactionType getTransactionType() {
        return transactionType;
    }

    public CPaymentTerm transactionType(CTransactionType transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(CTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Boolean isValid() {
        return valid;
    }

    public CPaymentTerm valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public UUID getUid() {
        return uid;
    }

    public CPaymentTerm uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CPaymentTerm active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CPaymentTerm adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
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
        if (!(o instanceof CPaymentTerm)) {
            return false;
        }
        return id != null && id.equals(((CPaymentTerm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CPaymentTerm{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", afterDelivery='" + isAfterDelivery() + "'" +
            ", asDefault='" + isAsDefault() + "'" +
            ", calculateBusinessDay='" + isCalculateBusinessDay() + "'" +
            ", discount=" + getDiscount() +
            ", discount2=" + getDiscount2() +
            ", discountDays=" + getDiscountDays() +
            ", discountDays2=" + getDiscountDays2() +
            ", documentNote='" + getDocumentNote() + "'" +
            ", fixMonthCutOff=" + getFixMonthCutOff() +
            ", fixMonthDay=" + getFixMonthDay() +
            ", fixMonthOffset=" + getFixMonthOffset() +
            ", fixedDueDate='" + isFixedDueDate() + "'" +
            ", graceDays=" + getGraceDays() +
            ", netDay='" + getNetDay() + "'" +
            ", netDays=" + getNetDays() +
            ", onNextBusinessDay='" + isOnNextBusinessDay() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", valid='" + isValid() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

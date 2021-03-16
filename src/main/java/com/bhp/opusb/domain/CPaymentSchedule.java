package com.bhp.opusb.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The CPaymentSchedule entity.\n@author A true hipster
 */
@Entity
@Table(name = "c_payment_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CPaymentSchedule extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "discount", precision = 21, scale = 2, nullable = false)
    private BigDecimal discount = new BigDecimal(0);

    @NotNull
    @Max(value = 10L)
    @Column(name = "discount_days", nullable = false)
    private Long discountDays = 0L;

    @Max(value = 10L)
    @Column(name = "grace_days")
    private Long graceDays = 0L;

    @Max(value = 1)
    @Column(name = "net_day")
    private Integer netDay;

    @NotNull
    @Max(value = 10L)
    @Column(name = "net_days", nullable = false)
    private Long netDays = 0L;

    @NotNull
    @Column(name = "percentage", precision = 21, scale = 2, nullable = false)
    private BigDecimal percentage = new BigDecimal(0);

    @Column(name = "valid")
    private Boolean valid = false;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active = true;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPaymentSchedules")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPaymentSchedules")
    private CPaymentTerm cPaymentTerm;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public CPaymentSchedule discount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Long getDiscountDays() {
        return discountDays;
    }

    public CPaymentSchedule discountDays(Long discountDays) {
        this.discountDays = discountDays;
        return this;
    }

    public void setDiscountDays(Long discountDays) {
        this.discountDays = discountDays;
    }

    public Long getGraceDays() {
        return graceDays;
    }

    public CPaymentSchedule graceDays(Long graceDays) {
        this.graceDays = graceDays;
        return this;
    }

    public void setGraceDays(Long graceDays) {
        this.graceDays = graceDays;
    }

    public Integer getNetDay() {
        return netDay;
    }

    public CPaymentSchedule netDay(Integer netDay) {
        this.netDay = netDay;
        return this;
    }

    public void setNetDay(Integer netDay) {
        this.netDay = netDay;
    }

    public Long getNetDays() {
        return netDays;
    }

    public CPaymentSchedule netDays(Long netDays) {
        this.netDays = netDays;
        return this;
    }

    public void setNetDays(Long netDays) {
        this.netDays = netDays;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public CPaymentSchedule percentage(BigDecimal percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Boolean isValid() {
        return valid;
    }

    public CPaymentSchedule valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public UUID getUid() {
        return uid;
    }

    public CPaymentSchedule uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CPaymentSchedule active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CPaymentSchedule adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CPaymentTerm getCPaymentTerm() {
        return cPaymentTerm;
    }

    public CPaymentSchedule cPaymentTerm(CPaymentTerm cPaymentTerm) {
        this.cPaymentTerm = cPaymentTerm;
        return this;
    }

    public void setCPaymentTerm(CPaymentTerm cPaymentTerm) {
        this.cPaymentTerm = cPaymentTerm;
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
        if (!(o instanceof CPaymentSchedule)) {
            return false;
        }
        return id != null && id.equals(((CPaymentSchedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CPaymentSchedule{" +
            "id=" + getId() +
            ", discount=" + getDiscount() +
            ", discountDays=" + getDiscountDays() +
            ", graceDays=" + getGraceDays() +
            ", netDay=" + getNetDay() +
            ", netDays=" + getNetDays() +
            ", percentage=" + getPercentage() +
            ", valid='" + isValid() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

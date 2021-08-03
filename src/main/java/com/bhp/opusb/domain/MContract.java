package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A MContract.
 */
@Entity
@Table(name = "m_contract")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MContract extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "use_ban_code")
    private Boolean useBanCode;

    @Column(name = "ban_code")
    private String banCode;

    /**
     * Whether the contract is for Project or Non-project purpose.
     */
    @Column(name = "purpose")
    private String purpose;

    @Column(name = "for_price_confirmation")
    private Boolean forPriceConfirmation;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "evaluation_period")
    private String evaluationPeriod;

    @Column(name = "date_trx")
    private ZonedDateTime dateTrx;

    @Size(max = 30)
    @Column(name = "document_no", length = 30)
    private String documentNo;

    @NotNull
    @Size(max = 10)
    @Column(name = "document_action", length = 10, nullable = false)
    private String documentAction;

    @NotNull
    @Size(max = 12)
    @Column(name = "document_status", length = 12, nullable = false)
    private String documentStatus;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "date_approve")
    private ZonedDateTime dateApprove;

    @Column(name = "date_reject")
    private ZonedDateTime dateReject;

    @Column(name = "rejected_reason")
    private String rejectedReason;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "price_proposed", precision = 21, scale = 2)
    private BigDecimal priceProposed;

    @Column(name = "exp_mail_receipt")
    private String expMailReceipt;

    @Column(name = "notice_period")
    private String noticePeriod;

    @Column(name = "reminder_sent")
    private Integer reminderSent;

    @Column(name = "email_notification")
    private Integer emailNotification;

    @Column(name = "term_type")
    private String termType;

    @Column(name = "hierarchical_type")
    private String hierarchicalType;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContracts")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)

    @JsonIgnoreProperties("mContracts")
    private MBidding bidding;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContracts")
    private CCostCenter costCenter;

    @ManyToOne
    @JsonIgnoreProperties("mContracts")
    private CCurrency currency;

    /**
     * documentType can be New Contract or Contract Renewal.
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContracts")
    private CDocumentType documentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContracts")
    private AdUser pic;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContracts")
    private CVendor vendor;

    @ManyToOne
    @JsonIgnoreProperties("mContracts")
    private CVendorEvaluation vendorEvaluation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MContract name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public MContract description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isUseBanCode() {
        return useBanCode;
    }

    public MContract useBanCode(Boolean useBanCode) {
        this.useBanCode = useBanCode;
        return this;
    }

    public void setUseBanCode(Boolean useBanCode) {
        this.useBanCode = useBanCode;
    }

    public String getBanCode() {
        return banCode;
    }

    public MContract banCode(String banCode) {
        this.banCode = banCode;
        return this;
    }

    public void setBanCode(String banCode) {
        this.banCode = banCode;
    }

    public String getPurpose() {
        return purpose;
    }

    public MContract purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Boolean isForPriceConfirmation() {
        return forPriceConfirmation;
    }

    public MContract forPriceConfirmation(Boolean forPriceConfirmation) {
        this.forPriceConfirmation = forPriceConfirmation;
        return this;
    }

    public void setForPriceConfirmation(Boolean forPriceConfirmation) {
        this.forPriceConfirmation = forPriceConfirmation;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public MContract startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public MContract expirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getEvaluationPeriod() {
        return evaluationPeriod;
    }

    public MContract evaluationPeriod(String evaluationPeriod) {
        this.evaluationPeriod = evaluationPeriod;
        return this;
    }

    public void setEvaluationPeriod(String evaluationPeriod) {
        this.evaluationPeriod = evaluationPeriod;
    }

    public ZonedDateTime getDateTrx() {
        return dateTrx;
    }

    public MContract dateTrx(ZonedDateTime dateTrx) {
        this.dateTrx = dateTrx;
        return this;
    }

    public void setDateTrx(ZonedDateTime dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public MContract documentNo(String documentNo) {
        this.documentNo = documentNo;
        return this;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public MContract documentAction(String documentAction) {
        this.documentAction = documentAction;
        return this;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public MContract documentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public MContract approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public MContract processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public ZonedDateTime getDateApprove() {
        return dateApprove;
    }

    public MContract dateApprove(ZonedDateTime dateApprove) {
        this.dateApprove = dateApprove;
        return this;
    }

    public void setDateApprove(ZonedDateTime dateApprove) {
        this.dateApprove = dateApprove;
    }

    public ZonedDateTime getDateReject() {
        return dateReject;
    }

    public MContract dateReject(ZonedDateTime dateReject) {
        this.dateReject = dateReject;
        return this;
    }

    public void setDateReject(ZonedDateTime dateReject) {
        this.dateReject = dateReject;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public MContract rejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
        return this;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MContract price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceProposed() {
        return priceProposed;
    }

    public MContract priceProposed(BigDecimal priceProposed) {
        this.priceProposed = priceProposed;
        return this;
    }

    public void setPriceProposed(BigDecimal priceProposed) {
        this.priceProposed = priceProposed;
    }

    public String getExpMailReceipt() {
        return expMailReceipt;
    }

    public MContract expMailReceipt(String expMailReceipt) {
        this.expMailReceipt = expMailReceipt;
        return this;
    }

    public void setExpMailReceipt(String expMailReceipt) {
        this.expMailReceipt = expMailReceipt;
    }

    public String getNoticePeriod() {
        return noticePeriod;
    }

    public MContract noticePeriod(String noticePeriod) {
        this.noticePeriod = noticePeriod;
        return this;
    }

    public void setNoticePeriod(String noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public Integer getReminderSent() {
        return reminderSent;
    }

    public MContract reminderSent(Integer reminderSent) {
        this.reminderSent = reminderSent;
        return this;
    }

    public void setReminderSent(Integer reminderSent) {
        this.reminderSent = reminderSent;
    }

    public Integer getEmailNotification() {
        return emailNotification;
    }

    public MContract emailNotification(Integer emailNotification) {
        this.emailNotification = emailNotification;
        return this;
    }

    public void setEmailNotification(Integer emailNotification) {
        this.emailNotification = emailNotification;
    }

    public String getTermType() {
        return termType;
    }

    public MContract termType(String termType) {
        this.termType = termType;
        return this;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getHierarchicalType() {
        return hierarchicalType;
    }

    public MContract hierarchicalType(String hierarchicalType) {
        this.hierarchicalType = hierarchicalType;
        return this;
    }

    public void setHierarchicalType(String hierarchicalType) {
        this.hierarchicalType = hierarchicalType;
    }

    public UUID getUid() {
        return uid;
    }

    public MContract uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MContract active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MContract adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MContract bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public MContract costCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
        return this;
    }

    public void setCostCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
    }

    public CCurrency getCurrency() {
        return currency;
    }

    public MContract currency(CCurrency cCurrency) {
        this.currency = cCurrency;
        return this;
    }

    public void setCurrency(CCurrency cCurrency) {
        this.currency = cCurrency;
    }

    public CDocumentType getDocumentType() {
        return documentType;
    }

    public MContract documentType(CDocumentType cDocumentType) {
        this.documentType = cDocumentType;
        return this;
    }

    public void setDocumentType(CDocumentType cDocumentType) {
        this.documentType = cDocumentType;
    }

    public AdUser getPic() {
        return pic;
    }

    public MContract pic(AdUser adUser) {
        this.pic = adUser;
        return this;
    }

    public void setPic(AdUser adUser) {
        this.pic = adUser;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MContract vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public CVendorEvaluation getVendorEvaluation() {
        return vendorEvaluation;
    }

    public MContract vendorEvaluation(CVendorEvaluation cVendorEvaluation) {
        this.vendorEvaluation = cVendorEvaluation;
        return this;
    }

    public void setVendorEvaluation(CVendorEvaluation cVendorEvaluation) {
        this.vendorEvaluation = cVendorEvaluation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    void prePersist() {
        uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MContract)) {
            return false;
        }
        return id != null && id.equals(((MContract) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MContract{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", useBanCode='" + isUseBanCode() + "'" +
            ", banCode='" + getBanCode() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", forPriceConfirmation='" + isForPriceConfirmation() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", evaluationPeriod='" + getEvaluationPeriod() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", dateApprove='" + getDateApprove() + "'" +
            ", dateReject='" + getDateReject() + "'" +
            ", rejectedReason='" + getRejectedReason() + "'" +
            ", price=" + getPrice() +
            ", priceProposed=" + getPriceProposed() +
            ", expMailReceipt='" + getExpMailReceipt() + "'" +
            ", noticePeriod='" + getNoticePeriod() + "'" +
            ", reminderSent=" + getReminderSent() +
            ", emailNotification=" + getEmailNotification() +
            ", termType='" + getTermType() + "'" +
            ", hierarchicalType='" + getHierarchicalType() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

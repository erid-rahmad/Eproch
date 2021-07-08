package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A MComplaint.
 */
@Entity
@Table(name = "m_complaint")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MComplaint extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @NotNull
    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "warning")
    private String warning;

    @Size(max = 10)
    @Column(name = "type", length = 10)
    private String type;

    @NotNull
    @Column(name = "date_trx", nullable = false)
    private LocalDate dateTrx;

    @Size(max = 30)
    @Column(name = "document_no", length = 30)
    private String documentNo;

    @NotNull
    @Size(max = 10)
    @Column(name = "document_action", length = 10, nullable = false)
    private String documentAction;

    @NotNull
    @Size(max = 10)
    @Column(name = "document_status", length = 10, nullable = false)
    private String documentStatus;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "processed")
    private Boolean processed;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mComplaints")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mComplaints")
    private CVendor vendor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mComplaints")
    private CCostCenter costCenter;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mComplaints")
    private MContract contract;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mComplaints")
    private CBusinessCategory businessCategory;

    @ManyToOne
    @JsonIgnoreProperties("mComplaints")
    private CBusinessCategory subBusinessCategory;

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
        this.active = true;
        this.documentStatus = "DRF";
        this.documentAction = "SMT";
        this.approved = false;
        this.processed = false;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public MComplaint uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MComplaint active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public MComplaint reportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getWarning() {
        return warning;
    }

    public MComplaint warning(String warning) {
        this.warning = warning;
        return this;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getType() {
        return type;
    }

    public MComplaint type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDateTrx() {
        return dateTrx;
    }

    public MComplaint dateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
        return this;
    }

    public void setDateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public MComplaint documentNo(String documentNo) {
        this.documentNo = documentNo;
        return this;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public MComplaint documentAction(String documentAction) {
        this.documentAction = documentAction;
        return this;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public MComplaint documentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public MComplaint approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public MComplaint processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MComplaint adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MComplaint vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public MComplaint costCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
        return this;
    }

    public void setCostCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
    }

    public MContract getContract() {
        return contract;
    }

    public MComplaint contract(MContract mContract) {
        this.contract = mContract;
        return this;
    }

    public void setContract(MContract mContract) {
        this.contract = mContract;
    }

    public CBusinessCategory getBusinessCategory() {
        return businessCategory;
    }

    public MComplaint businessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
        return this;
    }

    public void setBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.businessCategory = cBusinessCategory;
    }

    public CBusinessCategory getSubBusinessCategory() {
        return subBusinessCategory;
    }

    public MComplaint subBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.subBusinessCategory = cBusinessCategory;
        return this;
    }

    public void setSubBusinessCategory(CBusinessCategory cBusinessCategory) {
        this.subBusinessCategory = cBusinessCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MComplaint)) {
            return false;
        }
        return id != null && id.equals(((MComplaint) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MComplaint{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", reportDate='" + getReportDate() + "'" +
            ", warning='" + getWarning() + "'" +
            ", type='" + getType() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            "}";
    }
}

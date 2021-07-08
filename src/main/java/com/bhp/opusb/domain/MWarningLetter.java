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
 * A MWarningLetter.
 */
@Entity
@Table(name = "m_warning_letter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MWarningLetter extends AbstractAuditingEntity implements Serializable {

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

    @Size(max = 12)
    @Column(name = "warning_type", length = 12)
    private String warningType;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "warning")
    private String warning;

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
    @JsonIgnoreProperties("mWarningLetters")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mWarningLetters")
    private CVendor vendor;

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

    public MWarningLetter uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MWarningLetter active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public MWarningLetter reportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getWarningType() {
        return warningType;
    }

    public MWarningLetter warningType(String warningType) {
        this.warningType = warningType;
        return this;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public MWarningLetter startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public MWarningLetter endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public MWarningLetter location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWarning() {
        return warning;
    }

    public MWarningLetter warning(String warning) {
        this.warning = warning;
        return this;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public LocalDate getDateTrx() {
        return dateTrx;
    }

    public MWarningLetter dateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
        return this;
    }

    public void setDateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public MWarningLetter documentNo(String documentNo) {
        this.documentNo = documentNo;
        return this;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public MWarningLetter documentAction(String documentAction) {
        this.documentAction = documentAction;
        return this;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public MWarningLetter documentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public MWarningLetter approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public MWarningLetter processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MWarningLetter adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MWarningLetter vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MWarningLetter)) {
            return false;
        }
        return id != null && id.equals(((MWarningLetter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MWarningLetter{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", reportDate='" + getReportDate() + "'" +
            ", warningType='" + getWarningType() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", location='" + getLocation() + "'" +
            ", warning='" + getWarning() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            "}";
    }
}

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
 * A MVendorConfirmationContract.
 */
@Entity
@Table(name = "m_vendor_confirmation_contract")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVendorConfirmationContract extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "confirmation_no")
    private String confirmationNo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "contract_detail")
    private String contractDetail;

    @Column(name = "contract_start_date")
    private LocalDate contractStartDate;

    @Column(name = "contract_end_date")
    private LocalDate contractEndDate;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorConfirmationContracts")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorConfirmationContracts")
    private CAttachment attachment;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorConfirmationContracts")
    private MVendorConfirmationLine vendorConfirmationLine;

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
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

    public MVendorConfirmationContract uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MVendorConfirmationContract active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getConfirmationNo() {
        return confirmationNo;
    }

    public MVendorConfirmationContract confirmationNo(String confirmationNo) {
        this.confirmationNo = confirmationNo;
        return this;
    }

    public void setConfirmationNo(String confirmationNo) {
        this.confirmationNo = confirmationNo;
    }

    public String getContractDetail() {
        return contractDetail;
    }

    public MVendorConfirmationContract contractDetail(String contractDetail) {
        this.contractDetail = contractDetail;
        return this;
    }

    public void setContractDetail(String contractDetail) {
        this.contractDetail = contractDetail;
    }

    public LocalDate getContractStartDate() {
        return contractStartDate;
    }

    public MVendorConfirmationContract contractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
        return this;
    }

    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public MVendorConfirmationContract contractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
        return this;
    }

    public void setContractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public MVendorConfirmationContract publishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MVendorConfirmationContract adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CAttachment getAttachment() {
        return attachment;
    }

    public MVendorConfirmationContract attachment(CAttachment cAttachment) {
        this.attachment = cAttachment;
        return this;
    }

    public void setAttachment(CAttachment cAttachment) {
        this.attachment = cAttachment;
    }

    public MVendorConfirmationLine getVendorConfirmationLine() {
        return vendorConfirmationLine;
    }

    public MVendorConfirmationContract vendorConfirmationLine(MVendorConfirmationLine mVendorConfirmationLine) {
        this.vendorConfirmationLine = mVendorConfirmationLine;
        return this;
    }

    public void setVendorConfirmationLine(MVendorConfirmationLine mVendorConfirmationLine) {
        this.vendorConfirmationLine = mVendorConfirmationLine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MVendorConfirmationContract)) {
            return false;
        }
        return id != null && id.equals(((MVendorConfirmationContract) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MVendorConfirmationContract{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", contractDetail='" + getContractDetail() + "'" +
            ", contractStartDate='" + getContractStartDate() + "'" +
            ", contractEndDate='" + getContractEndDate() + "'" +
            "}";
    }
}

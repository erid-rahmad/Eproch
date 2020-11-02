package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A CRegistrationDocument.
 */
@Entity
@Table(name = "c_registration_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CRegistrationDocument extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "document_no", nullable = false)
    private String documentNo;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cRegistrationDocuments")
    private CRegistrationDocType type;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cRegistrationDocuments")
    private CAttachment file;

    @ManyToOne
    @JsonIgnoreProperties("cRegistrationDocuments")
    private CVendor vendor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cRegistrationDocuments")
    private ADOrganization adOrganization;

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

    public CRegistrationDocument documentNo(String documentNo) {
        this.documentNo = documentNo;
        return this;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public CRegistrationDocument expirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public UUID getUid() {
        return uid;
    }

    public CRegistrationDocument uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CRegistrationDocument active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CRegistrationDocType getType() {
        return type;
    }

    public CRegistrationDocument type(CRegistrationDocType cRegistrationDocType) {
        this.type = cRegistrationDocType;
        return this;
    }

    public void setType(CRegistrationDocType cRegistrationDocType) {
        this.type = cRegistrationDocType;
    }

    public CAttachment getFile() {
        return file;
    }

    public CRegistrationDocument file(CAttachment cAttachment) {
        this.file = cAttachment;
        return this;
    }

    public void setFile(CAttachment cAttachment) {
        this.file = cAttachment;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public CRegistrationDocument vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CRegistrationDocument adOrganization(ADOrganization aDOrganization) {
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
        if (!(o instanceof CRegistrationDocument)) {
            return false;
        }
        return id != null && id.equals(((CRegistrationDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CRegistrationDocument{" +
            "id=" + getId() +
            ", documentNo='" + getDocumentNo() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

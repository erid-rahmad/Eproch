package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A MPreqRegistDocument.
 */
@Entity
@Table(name = "m_preq_regist_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPreqRegistDocument extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPreqRegistDocuments")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPreqRegistDocuments")
    private MPrequalRegistration registration;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPreqRegistDocuments")
    private CAttachment siupDocument;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPreqRegistDocuments")
    private CAttachment spdaDocument;

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

    public MPreqRegistDocument uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MPreqRegistDocument active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MPreqRegistDocument adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MPrequalRegistration getRegistration() {
        return registration;
    }

    public MPreqRegistDocument registration(MPrequalRegistration mPrequalRegistration) {
        this.registration = mPrequalRegistration;
        return this;
    }

    public void setRegistration(MPrequalRegistration mPrequalRegistration) {
        this.registration = mPrequalRegistration;
    }

    public CAttachment getSiupDocument() {
        return siupDocument;
    }

    public MPreqRegistDocument siupDocument(CAttachment cAttachment) {
        this.siupDocument = cAttachment;
        return this;
    }

    public void setSiupDocument(CAttachment cAttachment) {
        this.siupDocument = cAttachment;
    }

    public CAttachment getSpdaDocument() {
        return spdaDocument;
    }

    public MPreqRegistDocument spdaDocument(CAttachment cAttachment) {
        this.spdaDocument = cAttachment;
        return this;
    }

    public void setSpdaDocument(CAttachment cAttachment) {
        this.spdaDocument = cAttachment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MPreqRegistDocument)) {
            return false;
        }
        return id != null && id.equals(((MPreqRegistDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPreqRegistDocument{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }

    @PrePersist
    public void prePersist(){
        this.uid = UUID.randomUUID();
        this.active = true;
    }
}

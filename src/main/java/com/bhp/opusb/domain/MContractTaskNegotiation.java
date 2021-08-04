package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A MContractTaskNegotiation.
 */
@Entity
@Table(name = "m_contract_task_negotiation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MContractTaskNegotiation extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "contract_document")
    private String contractDocument;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractTaskNegotiations")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractTaskNegotiations")
    private MContractTask contractTask;

    @ManyToOne
    @JsonIgnoreProperties("mContractTaskNegotiations")
    private CAttachment cAttachment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public MContractTaskNegotiation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContractDocument() {
        return contractDocument;
    }

    public MContractTaskNegotiation contractDocument(String contractDocument) {
        this.contractDocument = contractDocument;
        return this;
    }

    public void setContractDocument(String contractDocument) {
        this.contractDocument = contractDocument;
    }

    public UUID getUid() {
        return uid;
    }

    public MContractTaskNegotiation uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MContractTaskNegotiation active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MContractTaskNegotiation adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MContractTask getContractTask() {
        return contractTask;
    }

    public MContractTaskNegotiation contractTask(MContractTask mContractTask) {
        this.contractTask = mContractTask;
        return this;
    }

    public void setContractTask(MContractTask mContractTask) {
        this.contractTask = mContractTask;
    }

    public CAttachment getCAttachment() {
        return cAttachment;
    }

    public MContractTaskNegotiation cAttachment(CAttachment cAttachment) {
        this.cAttachment = cAttachment;
        return this;
    }

    public void setCAttachment(CAttachment cAttachment) {
        this.cAttachment = cAttachment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MContractTaskNegotiation)) {
            return false;
        }
        return id != null && id.equals(((MContractTaskNegotiation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @PrePersist
    public void prePersist() {
          uid = UUID.randomUUID();
    }


    @Override
    public String toString() {
        return "MContractTaskNegotiation{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", contractDocument='" + getContractDocument() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

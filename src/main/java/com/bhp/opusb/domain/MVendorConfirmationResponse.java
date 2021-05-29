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
 * A MVendorConfirmationResponse.
 */
@Entity
@Table(name = "m_vendor_confirmation_response")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVendorConfirmationResponse extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "need_revision")
    private String needRevision;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "accept")
    private String accept;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorConfirmationResponses")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorConfirmationResponses")
    private MVendorConfirmationLine vendorConfirmationLine;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorConfirmationResponses")
    private MVendorConfirmationContract vendorConfirmationContract;

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

    public MVendorConfirmationResponse uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MVendorConfirmationResponse active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getNeedRevision() {
        return needRevision;
    }

    public MVendorConfirmationResponse needRevision(String needRevision) {
        this.needRevision = needRevision;
        return this;
    }

    public void setNeedRevision(String needRevision) {
        this.needRevision = needRevision;
    }

    public String getAccept() {
        return accept;
    }

    public MVendorConfirmationResponse accept(String accept) {
        this.accept = accept;
        return this;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MVendorConfirmationResponse adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MVendorConfirmationLine getVendorConfirmationLine() {
        return vendorConfirmationLine;
    }

    public MVendorConfirmationResponse vendorConfirmationLine(MVendorConfirmationLine mVendorConfirmationLine) {
        this.vendorConfirmationLine = mVendorConfirmationLine;
        return this;
    }

    public void setVendorConfirmationLine(MVendorConfirmationLine mVendorConfirmationLine) {
        this.vendorConfirmationLine = mVendorConfirmationLine;
    }

    public MVendorConfirmationContract getVendorConfirmationContract() {
        return vendorConfirmationContract;
    }

    public MVendorConfirmationResponse vendorConfirmationContract(MVendorConfirmationContract mVendorConfirmationContract) {
        this.vendorConfirmationContract = mVendorConfirmationContract;
        return this;
    }

    public void setVendorConfirmationContract(MVendorConfirmationContract mVendorConfirmationContract) {
        this.vendorConfirmationContract = mVendorConfirmationContract;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MVendorConfirmationResponse)) {
            return false;
        }
        return id != null && id.equals(((MVendorConfirmationResponse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MVendorConfirmationResponse{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", needRevision='" + getNeedRevision() + "'" +
            ", accept='" + getAccept() + "'" +
            "}";
    }
}

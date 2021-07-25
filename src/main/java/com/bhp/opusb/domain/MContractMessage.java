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
 * A MContractMessage.
 */
@Entity
@Table(name = "m_contract_message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MContractMessage extends AbstractAuditingEntity {

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
    @Column(name = "vendor_text")
    private String vendorText;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "buyer_text")
    private String buyerText;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractMessages")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractMessages")
    private MContract contract;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractMessages")
    private CVendor vendor;

    @ManyToOne
    @JsonIgnoreProperties("mContractMessages")
    private CAttachment attachment;

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

    public MContractMessage uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MContractMessage active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getVendorText() {
        return vendorText;
    }

    public MContractMessage vendorText(String vendorText) {
        this.vendorText = vendorText;
        return this;
    }

    public void setVendorText(String vendorText) {
        this.vendorText = vendorText;
    }

    public String getBuyerText() {
        return buyerText;
    }

    public MContractMessage buyerText(String buyerText) {
        this.buyerText = buyerText;
        return this;
    }

    public void setBuyerText(String buyerText) {
        this.buyerText = buyerText;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MContractMessage adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MContract getContract() {
        return contract;
    }

    public MContractMessage contract(MContract mContract) {
        this.contract = mContract;
        return this;
    }

    public void setContract(MContract mContract) {
        this.contract = mContract;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MContractMessage vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public CAttachment getAttachment() {
        return attachment;
    }

    public MContractMessage attachment(CAttachment cAttachment) {
        this.attachment = cAttachment;
        return this;
    }

    public void setAttachment(CAttachment cAttachment) {
        this.attachment = cAttachment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MContractMessage)) {
            return false;
        }
        return id != null && id.equals(((MContractMessage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MContractMessage{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", vendorText='" + getVendorText() + "'" +
            ", buyerText='" + getBuyerText() + "'" +
            "}";
    }

    @PrePersist
    public void prePersist() {
        this.uid = UUID.randomUUID();
        this.active = true;
    }
}

package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.bhp.opusb.domain.enumeration.Depreciation;

/**
 * A CProductGroupAccount.
 */
@Entity
@Table(name = "c_product_group_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CProductGroupAccount extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "depreciation", nullable = false)
    private Depreciation depreciation;

    @NotNull
    @Column(name = "life_year", nullable = false)
    private Integer lifeYear;

    @NotNull
    @Column(name = "life_month", nullable = false)
    private Integer lifeMonth;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("cProductGroupAccounts")
    private CElementValue assetAccount;

    @ManyToOne
    @JsonIgnoreProperties("cProductGroupAccounts")
    private CElementValue depreciationAccount;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProductGroupAccounts")
    private CProductGroup productGroup;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cProductGroupAccounts")
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Depreciation getDepreciation() {
        return depreciation;
    }

    public CProductGroupAccount depreciation(Depreciation depreciation) {
        this.depreciation = depreciation;
        return this;
    }

    public void setDepreciation(Depreciation depreciation) {
        this.depreciation = depreciation;
    }

    public Integer getLifeYear() {
        return lifeYear;
    }

    public CProductGroupAccount lifeYear(Integer lifeYear) {
        this.lifeYear = lifeYear;
        return this;
    }

    public void setLifeYear(Integer lifeYear) {
        this.lifeYear = lifeYear;
    }

    public Integer getLifeMonth() {
        return lifeMonth;
    }

    public CProductGroupAccount lifeMonth(Integer lifeMonth) {
        this.lifeMonth = lifeMonth;
        return this;
    }

    public void setLifeMonth(Integer lifeMonth) {
        this.lifeMonth = lifeMonth;
    }

    public UUID getUid() {
        return uid;
    }

    public CProductGroupAccount uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CProductGroupAccount active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CElementValue getAssetAccount() {
        return assetAccount;
    }

    public CProductGroupAccount assetAccount(CElementValue cElementValue) {
        this.assetAccount = cElementValue;
        return this;
    }

    public void setAssetAccount(CElementValue cElementValue) {
        this.assetAccount = cElementValue;
    }

    public CElementValue getDepreciationAccount() {
        return depreciationAccount;
    }

    public CProductGroupAccount depreciationAccount(CElementValue cElementValue) {
        this.depreciationAccount = cElementValue;
        return this;
    }

    public void setDepreciationAccount(CElementValue cElementValue) {
        this.depreciationAccount = cElementValue;
    }

    public CProductGroup getProductGroup() {
        return productGroup;
    }

    public CProductGroupAccount productGroup(CProductGroup cProductGroup) {
        this.productGroup = cProductGroup;
        return this;
    }

    public void setProductGroup(CProductGroup cProductGroup) {
        this.productGroup = cProductGroup;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CProductGroupAccount adOrganization(ADOrganization aDOrganization) {
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
        if (!(o instanceof CProductGroupAccount)) {
            return false;
        }
        return id != null && id.equals(((CProductGroupAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CProductGroupAccount{" +
            "id=" + getId() +
            ", depreciation='" + getDepreciation() + "'" +
            ", lifeYear=" + getLifeYear() +
            ", lifeMonth=" + getLifeMonth() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

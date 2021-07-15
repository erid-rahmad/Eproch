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
 * A CPrequalMethodSubCriteria.
 */
@Entity
@Table(name = "c_prequal_method_sub_criteria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CPrequalMethodSubCriteria extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPrequalMethodSubCriteria")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPrequalMethodSubCriteria")
    private CBiddingCriteria biddingCriteria;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPrequalMethodSubCriteria")
    private CBiddingSubCriteria biddingSubCriteria;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPrequalMethodSubCriteria")
    private CPrequalMethodCriteria prequalMethodCriteria;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public CPrequalMethodSubCriteria weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public UUID getUid() {
        return uid;
    }

    public CPrequalMethodSubCriteria uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CPrequalMethodSubCriteria active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CPrequalMethodSubCriteria adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CBiddingCriteria getBiddingCriteria() {
        return biddingCriteria;
    }

    public CPrequalMethodSubCriteria biddingCriteria(CBiddingCriteria cBiddingCriteria) {
        this.biddingCriteria = cBiddingCriteria;
        return this;
    }

    public void setBiddingCriteria(CBiddingCriteria cBiddingCriteria) {
        this.biddingCriteria = cBiddingCriteria;
    }

    public CBiddingSubCriteria getBiddingSubCriteria() {
        return biddingSubCriteria;
    }

    public CPrequalMethodSubCriteria biddingSubCriteria(CBiddingSubCriteria cBiddingSubCriteria) {
        this.biddingSubCriteria = cBiddingSubCriteria;
        return this;
    }

    public void setBiddingSubCriteria(CBiddingSubCriteria cBiddingSubCriteria) {
        this.biddingSubCriteria = cBiddingSubCriteria;
    }

    public CPrequalMethodCriteria getPrequalMethodCriteria() {
        return prequalMethodCriteria;
    }

    public CPrequalMethodSubCriteria prequalMethodCriteria(CPrequalMethodCriteria cPrequalMethodCriteria) {
        this.prequalMethodCriteria = cPrequalMethodCriteria;
        return this;
    }

    public void setPrequalMethodCriteria(CPrequalMethodCriteria cPrequalMethodCriteria) {
        this.prequalMethodCriteria = cPrequalMethodCriteria;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CPrequalMethodSubCriteria)) {
            return false;
        }
        return id != null && id.equals(((CPrequalMethodSubCriteria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CPrequalMethodSubCriteria{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }

    @PrePersist
    public void assignUid(){
        this.uid = UUID.randomUUID();
    }
}

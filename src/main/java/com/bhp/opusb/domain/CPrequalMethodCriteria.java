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
 * A CPrequalMethodCriteria.
 */
@Entity
@Table(name = "c_prequal_method_criteria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CPrequalMethodCriteria extends AbstractAuditingEntity implements Serializable {

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
    @JsonIgnoreProperties("cPrequalMethodCriteria")
    private CBiddingCriteria biddingCriteria;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPrequalMethodCriteria")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("cPrequalMethodCriteria")
    private CPrequalMethodLine prequalMethodLine;

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

    public CPrequalMethodCriteria weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public UUID getUid() {
        return uid;
    }

    public CPrequalMethodCriteria uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CPrequalMethodCriteria active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CBiddingCriteria getBiddingCriteria() {
        return biddingCriteria;
    }

    public CPrequalMethodCriteria biddingCriteria(CBiddingCriteria cBiddingCriteria) {
        this.biddingCriteria = cBiddingCriteria;
        return this;
    }

    public void setBiddingCriteria(CBiddingCriteria cBiddingCriteria) {
        this.biddingCriteria = cBiddingCriteria;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CPrequalMethodCriteria adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CPrequalMethodLine getPrequalMethodLine() {
        return prequalMethodLine;
    }

    public CPrequalMethodCriteria prequalMethodLine(CPrequalMethodLine cPrequalMethodLine) {
        this.prequalMethodLine = cPrequalMethodLine;
        return this;
    }

    public void setPrequalMethodLine(CPrequalMethodLine cPrequalMethodLine) {
        this.prequalMethodLine = cPrequalMethodLine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CPrequalMethodCriteria)) {
            return false;
        }
        return id != null && id.equals(((CPrequalMethodCriteria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CPrequalMethodCriteria{" +
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

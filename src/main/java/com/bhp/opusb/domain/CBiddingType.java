package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.UUID;

/**
 * A CBiddingType.
 */
@Entity
@Table(name = "c_bidding_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CBiddingType extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "cost_evaluation_selection")
    private Boolean costEvaluationSelection;

    @Column(name = "selected_winner")
    private Boolean selectedWinner;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cBiddingTypes")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cBiddingTypes")
    private CProductClassification productClassification;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CBiddingType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CBiddingType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isCostEvaluationSelection() {
        return costEvaluationSelection;
    }

    public CBiddingType costEvaluationSelection(Boolean costEvaluationSelection) {
        this.costEvaluationSelection = costEvaluationSelection;
        return this;
    }

    public void setCostEvaluationSelection(Boolean costEvaluationSelection) {
        this.costEvaluationSelection = costEvaluationSelection;
    }

    public Boolean isSelectedWinner() {
        return selectedWinner;
    }

    public CBiddingType selectedWinner(Boolean selectedWinner) {
        this.selectedWinner = selectedWinner;
        return this;
    }

    public void setSelectedWinner(Boolean selectedWinner) {
        this.selectedWinner = selectedWinner;
    }

    public UUID getUid() {
        return uid;
    }

    public CBiddingType uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CBiddingType active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CBiddingType adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CProductClassification getProductClassification() {
        return productClassification;
    }

    public CBiddingType productClassification(CProductClassification cProductClassification) {
        this.productClassification = cProductClassification;
        return this;
    }

    public void setProductClassification(CProductClassification cProductClassification) {
        this.productClassification = cProductClassification;
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
        if (!(o instanceof CBiddingType)) {
            return false;
        }
        return id != null && id.equals(((CBiddingType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CBiddingType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", costEvaluationSelection='" + isCostEvaluationSelection() + "'" +
            ", selectedWinner='" + isSelectedWinner() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

package com.bhp.opusb.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdTaskProcess.
 */
@Entity
@Table(name = "ad_task_process")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdTaskProcess extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "run_sequence")
    private Integer runSequence;

    @Column(name = "parallel")
    private Boolean parallel;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adTaskProcesses")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adTaskProcesses")
    private AdTaskApplication adTaskApplication;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adTaskProcesses")
    @JsonBackReference
    private AdTask adTask;

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

    public AdTaskProcess uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public AdTaskProcess active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getRunSequence() {
        return runSequence;
    }

    public AdTaskProcess runSequence(Integer runSequence) {
        this.runSequence = runSequence;
        return this;
    }

    public void setRunSequence(Integer runSequence) {
        this.runSequence = runSequence;
    }

    public Boolean isParallel() {
        return parallel;
    }

    public AdTaskProcess parallel(Boolean parallel) {
        this.parallel = parallel;
        return this;
    }

    public void setParallel(Boolean parallel) {
        this.parallel = parallel;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public AdTaskProcess adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public AdTaskApplication getAdTaskApplication() {
        return adTaskApplication;
    }

    public AdTaskProcess adTaskApplication(AdTaskApplication adTaskApplication) {
        this.adTaskApplication = adTaskApplication;
        return this;
    }

    public void setAdTaskApplication(AdTaskApplication adTaskApplication) {
        this.adTaskApplication = adTaskApplication;
    }

    public AdTask getAdTask() {
        return adTask;
    }

    public AdTaskProcess adTask(AdTask adTask) {
        this.adTask = adTask;
        return this;
    }

    public void setAdTask(AdTask adTask) {
        this.adTask = adTask;
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
        if (!(o instanceof AdTaskProcess)) {
            return false;
        }
        return id != null && id.equals(((AdTaskProcess) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdTaskProcess{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", runSequence=" + getRunSequence() +
            ", parallel='" + isParallel() + "'" +
            "}";
    }
}

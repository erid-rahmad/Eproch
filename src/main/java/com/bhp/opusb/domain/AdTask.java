package com.bhp.opusb.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdTask.
 */
@Entity
@Table(name = "ad_task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdTask extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z_\\-]+$")
    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "async")
    private Boolean async;

    @OneToMany(mappedBy = "adTask")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AdTaskProcess> adTaskProcesses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("adTasks")
    private ADOrganization adOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public AdTask() {}
    public AdTask(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public AdTask uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public AdTask active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public AdTask name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public AdTask value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean isAsync() {
        return async;
    }

    public AdTask async(Boolean async) {
        this.async = async;
        return this;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public Set<AdTaskProcess> getAdTaskProcesses() {
        return adTaskProcesses;
    }

    public AdTask adTaskProcesses(Set<AdTaskProcess> adTaskProcesses) {
        this.adTaskProcesses = adTaskProcesses;
        return this;
    }

    public AdTask addAdTaskProcess(AdTaskProcess adTaskProcess) {
        this.adTaskProcesses.add(adTaskProcess);
        adTaskProcess.setAdTask(this);
        return this;
    }

    public AdTask removeAdTaskProcess(AdTaskProcess adTaskProcess) {
        this.adTaskProcesses.remove(adTaskProcess);
        adTaskProcess.setAdTask(null);
        return this;
    }

    public void setAdTaskProcesses(Set<AdTaskProcess> adTaskProcesses) {
        this.adTaskProcesses = adTaskProcesses;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public AdTask adOrganization(ADOrganization aDOrganization) {
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
        if (!(o instanceof AdTask)) {
            return false;
        }
        return id != null && id.equals(((AdTask) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdTask{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", async='" + isAsync() + "'" +
            "}";
    }
}

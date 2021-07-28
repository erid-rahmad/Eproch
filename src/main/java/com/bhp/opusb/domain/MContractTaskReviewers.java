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
 * A MContractTaskReviewers.
 */
@Entity
@Table(name = "m_contract_task_reviewers")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MContractTaskReviewers extends AbstractAuditingEntity {

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
    @JsonIgnoreProperties("mContractTaskReviewers")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractTaskReviewers")
    private MContractTask contractTask;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractTaskReviewers")
    private AdUser pic;

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

    public MContractTaskReviewers uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MContractTaskReviewers active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MContractTaskReviewers adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MContractTask getContractTask() {
        return contractTask;
    }

    public MContractTaskReviewers contractTask(MContractTask mContractTask) {
        this.contractTask = mContractTask;
        return this;
    }

    public void setContractTask(MContractTask mContractTask) {
        this.contractTask = mContractTask;
    }

    public AdUser getPic() {
        return pic;
    }

    public MContractTaskReviewers pic(AdUser adUser) {
        this.pic = adUser;
        return this;
    }

    public void setPic(AdUser adUser) {
        this.pic = adUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MContractTaskReviewers)) {
            return false;
        }
        return id != null && id.equals(((MContractTaskReviewers) o).id);
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
        return "MContractTaskReviewers{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}

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
 * A MContractTeamLine.
 */
@Entity
@Table(name = "m_contract_team_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MContractTeamLine implements Serializable {

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
    @Size(max = 10)
    @Column(name = "position", length = 10, nullable = false)
    private String position;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractTeamLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractTeamLines")
    private MContractTeam contractTeam;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractTeamLines")
    private AdUser adUser;

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

    public MContractTeamLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MContractTeamLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPosition() {
        return position;
    }

    public MContractTeamLine position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MContractTeamLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MContractTeam getContractTeam() {
        return contractTeam;
    }

    public MContractTeamLine contractTeam(MContractTeam mContractTeam) {
        this.contractTeam = mContractTeam;
        return this;
    }

    public void setContractTeam(MContractTeam mContractTeam) {
        this.contractTeam = mContractTeam;
    }

    public AdUser getAdUser() {
        return adUser;
    }

    public MContractTeamLine adUser(AdUser adUser) {
        this.adUser = adUser;
        return this;
    }

    public void setAdUser(AdUser adUser) {
        this.adUser = adUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MContractTeamLine)) {
            return false;
        }
        return id != null && id.equals(((MContractTeamLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MContractTeamLine{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", position='" + getPosition() + "'" +
            "}";
    }

    @PrePersist
    public void prePersist(){
        this.active = true;
        this.uid = UUID.randomUUID();
    }
}

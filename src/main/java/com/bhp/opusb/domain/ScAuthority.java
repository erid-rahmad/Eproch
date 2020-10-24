package com.bhp.opusb.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Extends the JHipster's Authority entity
 */
@Entity
@Table(name = "sc_authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ScAuthority extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "description")
    private String description;

    @Column(name = "master")
    private Boolean master;

    @Column(name = "access_all_orgs")
    private Boolean accessAllOrgs;

    @Column(name = "use_user_orgs")
    private Boolean useUserOrgs;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Authority authority;

    @OneToMany(mappedBy = "authority")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ScAccess> scAccesses = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("scAuthorities")
    private ADOrganization adOrganization;

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

    public ScAuthority uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public ScAuthority active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public ScAuthority description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isMaster() {
        return master;
    }

    public ScAuthority master(Boolean master) {
        this.master = master;
        return this;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    public Boolean isAccessAllOrgs() {
        return accessAllOrgs;
    }

    public ScAuthority accessAllOrgs(Boolean accessAllOrgs) {
        this.accessAllOrgs = accessAllOrgs;
        return this;
    }

    public void setAccessAllOrgs(Boolean accessAllOrgs) {
        this.accessAllOrgs = accessAllOrgs;
    }

    public Boolean isUseUserOrgs() {
        return useUserOrgs;
    }

    public ScAuthority useUserOrgs(Boolean useUserOrgs) {
        this.useUserOrgs = useUserOrgs;
        return this;
    }

    public void setUseUserOrgs(Boolean useUserOrgs) {
        this.useUserOrgs = useUserOrgs;
    }

    public Authority getAuthority() {
        return authority;
    }

    public ScAuthority authority(Authority authority) {
        this.authority = authority;
        return this;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public Set<ScAccess> getScAccesses() {
        return scAccesses;
    }

    public ScAuthority scAccesses(Set<ScAccess> scAccesses) {
        this.scAccesses = scAccesses;
        return this;
    }

    public ScAuthority addScAccess(ScAccess scAccess) {
        this.scAccesses.add(scAccess);
        scAccess.setAuthority(this);
        return this;
    }

    public ScAuthority removeScAccess(ScAccess scAccess) {
        this.scAccesses.remove(scAccess);
        scAccess.setAuthority(null);
        return this;
    }

    public void setScAccesses(Set<ScAccess> scAccesses) {
        this.scAccesses = scAccesses;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public ScAuthority adOrganization(ADOrganization aDOrganization) {
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
        if (!(o instanceof ScAuthority)) {
            return false;
        }
        return id != null && id.equals(((ScAuthority) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ScAuthority{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", description='" + getDescription() + "'" +
            ", master='" + isMaster() + "'" +
            ", accessAllOrgs='" + isAccessAllOrgs() + "'" +
            ", useUserOrgs='" + isUseUserOrgs() + "'" +
            "}";
    }
}

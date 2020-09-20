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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ScAccess.
 */
@Entity
@Table(name = "sc_access")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ScAccess extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("scAccesses")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("scAccesses")
    private ScAccessType type;

    @ManyToOne
    @JsonIgnoreProperties("scAccesses")
    private ADWindow window;

    @ManyToOne
    @JsonIgnoreProperties("scAccesses")
    private AdForm form;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("scAccesses")
    private ScAuthority authority;

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

    public ScAccess uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public ScAccess active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public ScAccess name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ScAccess description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public ScAccess adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public ScAccessType getType() {
        return type;
    }

    public ScAccess type(ScAccessType scAccessType) {
        this.type = scAccessType;
        return this;
    }

    public void setType(ScAccessType scAccessType) {
        this.type = scAccessType;
    }

    public ADWindow getWindow() {
        return window;
    }

    public ScAccess window(ADWindow aDWindow) {
        this.window = aDWindow;
        return this;
    }

    public void setWindow(ADWindow aDWindow) {
        this.window = aDWindow;
    }

    public AdForm getForm() {
        return form;
    }

    public ScAccess form(AdForm adForm) {
        this.form = adForm;
        return this;
    }

    public void setForm(AdForm adForm) {
        this.form = adForm;
    }

    public ScAuthority getAuthority() {
        return authority;
    }

    public ScAccess authority(ScAuthority scAuthority) {
        this.authority = scAuthority;
        return this;
    }

    public void setAuthority(ScAuthority scAuthority) {
        this.authority = scAuthority;
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
        if (!(o instanceof ScAccess)) {
            return false;
        }
        return id != null && id.equals(((ScAccess) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ScAccess{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

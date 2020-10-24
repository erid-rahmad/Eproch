package com.bhp.opusb.domain;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The extended User entity.
 */
@Entity
@Table(name = "ad_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdUser extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active = true;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "failed_login_count")
    private Integer failedLoginCount;

    @Column(name = "last_login_date")
    private Instant lastLoginDate;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adUsers")
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

    public AdUser uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public AdUser active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public AdUser code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public AdUser phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getFailedLoginCount() {
        return failedLoginCount;
    }

    public AdUser failedLoginCount(Integer failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
        return this;
    }

    public void setFailedLoginCount(Integer failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    public Instant getLastLoginDate() {
        return lastLoginDate;
    }

    public AdUser lastLoginDate(Instant lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
        return this;
    }

    public void setLastLoginDate(Instant lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public User getUser() {
        return user;
    }

    public AdUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public AdUser adOrganization(ADOrganization aDOrganization) {
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
        if (!(o instanceof AdUser)) {
            return false;
        }
        return id != null && id.equals(((AdUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdUser{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", code='" + getCode() + "'" +
            ", phone='" + getPhone() + "'" +
            ", failedLoginCount=" + getFailedLoginCount() +
            ", lastLoginDate='" + getLastLoginDate() + "'" +
            "}";
    }
}

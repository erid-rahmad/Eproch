package com.bhp.opusb.domain;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 30)
    @Column(name = "position", length = 30)
    private String position;

    /**
     * Whether it is an employee or not
     */
    @Column(name = "employee")
    private Boolean employee;

    /**
     * Whether it is a vendor or not
     */
    @Column(name = "vendor")
    private Boolean vendor;

    @Column(name = "failed_login_count")
    private Integer failedLoginCount;

    @Column(name = "last_login_date")
    private Instant lastLoginDate;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    @MapsId
    private User user;

    /**
     * This field should not empty for a supplier's user
     */
    @ManyToOne
    @JsonIgnoreProperties("adUsers")
    private CVendor cVendor;

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

    public String getPosition() {
        return position;
    }

    public AdUser position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean isEmployee() {
        return employee;
    }

    public AdUser employee(Boolean employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Boolean employee) {
        this.employee = employee;
    }

    public Boolean isVendor() {
        return vendor;
    }

    public AdUser vendor(Boolean vendor) {
        this.vendor = vendor;
        return this;
    }

    public void setVendor(Boolean vendor) {
        this.vendor = vendor;
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

    public CVendor getCVendor() {
        return cVendor;
    }

    public AdUser cVendor(CVendor cVendor) {
        this.cVendor = cVendor;
        return this;
    }

    public void setCVendor(CVendor cVendor) {
        this.cVendor = cVendor;
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
            ", phone='" + getPhone() + "'" +
            ", position='" + getPosition() + "'" +
            ", employee='" + isEmployee() + "'" +
            ", vendor='" + isVendor() + "'" +
            ", failedLoginCount=" + getFailedLoginCount() +
            ", lastLoginDate='" + getLastLoginDate() + "'" +
            "}";
    }
}

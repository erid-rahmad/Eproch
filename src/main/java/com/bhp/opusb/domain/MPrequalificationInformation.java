package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A MPrequalificationInformation.
 */
@Entity
@Table(name = "m_prequalification_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPrequalificationInformation extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "name")
    private String name;

    @NotNull
    @Size(max = 10)
    @Column(name = "type", length = 10, nullable = false)
    private String type;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "announcement_text")
    private String announcementText;

    @Column(name = "date_trx")
    private ZonedDateTime dateTrx;

    @Size(max = 30)
    @Column(name = "document_no", length = 30)
    private String documentNo;

    @NotNull
    @Size(max = 10)
    @Column(name = "document_action", length = 10, nullable = false)
    private String documentAction;

    @NotNull
    @Size(max = 12)
    @Column(name = "document_status", length = 12, nullable = false)
    private String documentStatus;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "date_approve")
    private ZonedDateTime dateApprove;

    @Column(name = "date_reject")
    private ZonedDateTime dateReject;

    @Column(name = "rejected_reason")
    private String rejectedReason;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private CAttachment attachment;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalificationInformations")
    private ADOrganization adOrganization;

    @Formula("(select mpe.event_id from m_prequalification_event mpe where mpe.prequalification_id = id)")
    private Long preqEventId;

    @Formula("(select cpe.name from c_prequalification_event cpe where cpe.id = (select mpe.event_id from m_prequalification_event mpe where mpe.prequalification_id = id))")
    private String preqEventName;

    @Formula("(select mpe.method_id from m_prequalification_event mpe where mpe.prequalification_id = id)")
    private Long preqMethodId;

    @Formula("(select cpm.name from c_prequalification_method cpm where cpm.id = (select mpe.method_id from m_prequalification_event mpe where mpe.prequalification_id = id))")
    private String preqMethodName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public String getPreqMethodName() {
        return preqMethodName;
    }

    public void setPreqMethodName(String preqMethodName) {
        this.preqMethodName = preqMethodName;
    }

    public Long getPreqMethodId() {
        return preqMethodId;
    }

    public void setPreqMethodId(Long preqMethodId) {
        this.preqMethodId = preqMethodId;
    }

    public String getPreqEventName() {
        return preqEventName;
    }

    public void setPreqEventName(String preqEventName) {
        this.preqEventName = preqEventName;
    }

    public Long getPreqEventId() {
        return preqEventId;
    }

    public void setPreqEventId(Long preqEventId) {
        this.preqEventId = preqEventId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public MPrequalificationInformation uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MPrequalificationInformation active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public MPrequalificationInformation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public MPrequalificationInformation type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnnouncementText() {
        return announcementText;
    }

    public MPrequalificationInformation announcementText(String announcementText) {
        this.announcementText = announcementText;
        return this;
    }

    public void setAnnouncementText(String announcementText) {
        this.announcementText = announcementText;
    }

    public ZonedDateTime getDateTrx() {
        return dateTrx;
    }

    public MPrequalificationInformation dateTrx(ZonedDateTime dateTrx) {
        this.dateTrx = dateTrx;
        return this;
    }

    public void setDateTrx(ZonedDateTime dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public MPrequalificationInformation documentNo(String documentNo) {
        this.documentNo = documentNo;
        return this;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public MPrequalificationInformation documentAction(String documentAction) {
        this.documentAction = documentAction;
        return this;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public MPrequalificationInformation documentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public MPrequalificationInformation approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public MPrequalificationInformation processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public ZonedDateTime getDateApprove() {
        return dateApprove;
    }

    public MPrequalificationInformation dateApprove(ZonedDateTime dateApprove) {
        this.dateApprove = dateApprove;
        return this;
    }

    public void setDateApprove(ZonedDateTime dateApprove) {
        this.dateApprove = dateApprove;
    }

    public ZonedDateTime getDateReject() {
        return dateReject;
    }

    public MPrequalificationInformation dateReject(ZonedDateTime dateReject) {
        this.dateReject = dateReject;
        return this;
    }

    public void setDateReject(ZonedDateTime dateReject) {
        this.dateReject = dateReject;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public MPrequalificationInformation rejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
        return this;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public CAttachment getAttachment() {
        return attachment;
    }

    public MPrequalificationInformation attachment(CAttachment cAttachment) {
        this.attachment = cAttachment;
        return this;
    }

    public void setAttachment(CAttachment cAttachment) {
        this.attachment = cAttachment;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MPrequalificationInformation adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MPrequalificationInformation)) {
            return false;
        }
        return id != null && id.equals(((MPrequalificationInformation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPrequalificationInformation{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", announcementText='" + getAnnouncementText() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", dateApprove='" + getDateApprove() + "'" +
            ", dateReject='" + getDateReject() + "'" +
            ", rejectedReason='" + getRejectedReason() + "'" +
            "}";
    }

    @PrePersist
    public void assignUid(){
        this.uid = UUID.randomUUID();
        this.active = true;
        this.documentStatus = "DRF";
        this.documentAction = "SMT";
        this.approved = false;
        this.processed = false;
    }
}

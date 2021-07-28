package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MContractTask} entity.
 */
public class MContractTaskDTO extends AbstractAuditingDTO {

    private Long id;

    @NotNull
    private String name;

    @Lob
    private String contractDocument;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    @Size(max = 10)
    private String documentAction="DRF";

    @NotNull
    @Size(max = 12)
    private String documentStatus="DRF";

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long contractId;

    private Long taskId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContractDocument() {
        return contractDocument;
    }

    public void setContractDocument(String contractDocument) {
        this.contractDocument = contractDocument;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long mContractId) {
        this.contractId = mContractId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long cTaskId) {
        this.taskId = cTaskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MContractTaskDTO mContractTaskDTO = (MContractTaskDTO) o;
        if (mContractTaskDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mContractTaskDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MContractTaskDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contractDocument='" + getContractDocument() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", contractId=" + getContractId() +
            ", taskId=" + getTaskId() +
            "}";
    }
}

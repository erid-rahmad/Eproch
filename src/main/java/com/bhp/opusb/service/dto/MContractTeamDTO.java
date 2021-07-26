package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MContractTeam} entity.
 */
public class MContractTeamDTO extends AbstractAuditingDTO {
    
    private Long id;

    private String status;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;

    private Long contractId, vendorId;
    private String contractNo, contractName, contractTypeName, vendorName, costCenterName;

    private List<MContractTeamLineDTO> members = new ArrayList<>(), deletedLines = new ArrayList<>();
    
    public Long getId() {
        return id;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public List<MContractTeamLineDTO> getDeletedLines() {
        return deletedLines;
    }

    public void setDeletedLines(List<MContractTeamLineDTO> deletedLines) {
        this.deletedLines = deletedLines;
    }

    public List<MContractTeamLineDTO> getMembers() {
        return members;
    }

    public void setMembers(List<MContractTeamLineDTO> members) {
        this.members = members;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MContractTeamDTO mContractTeamDTO = (MContractTeamDTO) o;
        if (mContractTeamDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mContractTeamDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MContractTeamDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", contractId=" + getContractId() +
            "}";
    }
}

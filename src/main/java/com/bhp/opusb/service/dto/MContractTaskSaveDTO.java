package com.bhp.opusb.service.dto;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MContractTask} entity.
 */
public class MContractTaskSaveDTO extends AbstractAuditingDTO {


    private MContractTaskDTO contractTask;
    private List<MContractTaskReviewersDTO> reviewers;
    private List<MContractTaskNegotiationDTO> massage;

    public MContractTaskDTO getContractTask() {
        return contractTask;
    }

    public void setContractTask(MContractTaskDTO contractTask) {
        this.contractTask = contractTask;
    }

    public List<MContractTaskReviewersDTO> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<MContractTaskReviewersDTO> reviewers) {
        this.reviewers = reviewers;
    }

    public List<MContractTaskNegotiationDTO> getMassage() {
        return massage;
    }

    public void setMassage(List<MContractTaskNegotiationDTO> massage) {
        this.massage = massage;
    }
}

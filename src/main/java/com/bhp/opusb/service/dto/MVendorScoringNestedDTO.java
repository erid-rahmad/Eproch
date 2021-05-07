package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorScoring} entity.
 */
public class MVendorScoringNestedDTO implements Serializable {

   private List<CEvaluationMethodCriteriaDTO> evaluationMethodCriteriaNested;
   private MVendorScoringDTO vendorScoringNested;
   private Long evaluationMethodLineId;

    public Long getEvaluationMethodLineId() {
        return evaluationMethodLineId;
    }

    public void setEvaluationMethodLineId(Long evaluationMethodLineId) {
        this.evaluationMethodLineId = evaluationMethodLineId;
    }

    public List<CEvaluationMethodCriteriaDTO> getEvaluationMethodCriteriaNested() {
        return evaluationMethodCriteriaNested;
    }

    public void setEvaluationMethodCriteriaNested(List<CEvaluationMethodCriteriaDTO> evaluationMethodCriteriaNested) {
        this.evaluationMethodCriteriaNested = evaluationMethodCriteriaNested;
    }

    public MVendorScoringDTO getVendorScoringNested() {
        return vendorScoringNested;
    }

    public void setVendorScoringNested(MVendorScoringDTO vendorScoringNested) {
        this.vendorScoringNested = vendorScoringNested;
    }

    @Override
    public String toString() {
        return "MVendorScoringNestedDTO{" +
            "evaluationMethodCriteriaNested=" + evaluationMethodCriteriaNested +
            ", vendorScoringNested=" + vendorScoringNested +
            ", evaluationMethodLineId=" + evaluationMethodLineId +
            '}';
    }
}

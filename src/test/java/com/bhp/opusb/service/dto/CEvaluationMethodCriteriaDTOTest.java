package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEvaluationMethodCriteriaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEvaluationMethodCriteriaDTO.class);
        CEvaluationMethodCriteriaDTO cEvaluationMethodCriteriaDTO1 = new CEvaluationMethodCriteriaDTO();
        cEvaluationMethodCriteriaDTO1.setId(1L);
        CEvaluationMethodCriteriaDTO cEvaluationMethodCriteriaDTO2 = new CEvaluationMethodCriteriaDTO();
        assertThat(cEvaluationMethodCriteriaDTO1).isNotEqualTo(cEvaluationMethodCriteriaDTO2);
        cEvaluationMethodCriteriaDTO2.setId(cEvaluationMethodCriteriaDTO1.getId());
        assertThat(cEvaluationMethodCriteriaDTO1).isEqualTo(cEvaluationMethodCriteriaDTO2);
        cEvaluationMethodCriteriaDTO2.setId(2L);
        assertThat(cEvaluationMethodCriteriaDTO1).isNotEqualTo(cEvaluationMethodCriteriaDTO2);
        cEvaluationMethodCriteriaDTO1.setId(null);
        assertThat(cEvaluationMethodCriteriaDTO1).isNotEqualTo(cEvaluationMethodCriteriaDTO2);
    }
}

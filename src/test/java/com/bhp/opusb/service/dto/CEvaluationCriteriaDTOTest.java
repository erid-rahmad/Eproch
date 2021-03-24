package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEvaluationCriteriaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEvaluationCriteriaDTO.class);
        CEvaluationCriteriaDTO cEvaluationCriteriaDTO1 = new CEvaluationCriteriaDTO();
        cEvaluationCriteriaDTO1.setId(1L);
        CEvaluationCriteriaDTO cEvaluationCriteriaDTO2 = new CEvaluationCriteriaDTO();
        assertThat(cEvaluationCriteriaDTO1).isNotEqualTo(cEvaluationCriteriaDTO2);
        cEvaluationCriteriaDTO2.setId(cEvaluationCriteriaDTO1.getId());
        assertThat(cEvaluationCriteriaDTO1).isEqualTo(cEvaluationCriteriaDTO2);
        cEvaluationCriteriaDTO2.setId(2L);
        assertThat(cEvaluationCriteriaDTO1).isNotEqualTo(cEvaluationCriteriaDTO2);
        cEvaluationCriteriaDTO1.setId(null);
        assertThat(cEvaluationCriteriaDTO1).isNotEqualTo(cEvaluationCriteriaDTO2);
    }
}

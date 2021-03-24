package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEvaluationMethodLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEvaluationMethodLineDTO.class);
        CEvaluationMethodLineDTO cEvaluationMethodLineDTO1 = new CEvaluationMethodLineDTO();
        cEvaluationMethodLineDTO1.setId(1L);
        CEvaluationMethodLineDTO cEvaluationMethodLineDTO2 = new CEvaluationMethodLineDTO();
        assertThat(cEvaluationMethodLineDTO1).isNotEqualTo(cEvaluationMethodLineDTO2);
        cEvaluationMethodLineDTO2.setId(cEvaluationMethodLineDTO1.getId());
        assertThat(cEvaluationMethodLineDTO1).isEqualTo(cEvaluationMethodLineDTO2);
        cEvaluationMethodLineDTO2.setId(2L);
        assertThat(cEvaluationMethodLineDTO1).isNotEqualTo(cEvaluationMethodLineDTO2);
        cEvaluationMethodLineDTO1.setId(null);
        assertThat(cEvaluationMethodLineDTO1).isNotEqualTo(cEvaluationMethodLineDTO2);
    }
}

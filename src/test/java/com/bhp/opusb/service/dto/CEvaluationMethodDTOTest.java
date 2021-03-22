package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEvaluationMethodDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEvaluationMethodDTO.class);
        CEvaluationMethodDTO cEvaluationMethodDTO1 = new CEvaluationMethodDTO();
        cEvaluationMethodDTO1.setId(1L);
        CEvaluationMethodDTO cEvaluationMethodDTO2 = new CEvaluationMethodDTO();
        assertThat(cEvaluationMethodDTO1).isNotEqualTo(cEvaluationMethodDTO2);
        cEvaluationMethodDTO2.setId(cEvaluationMethodDTO1.getId());
        assertThat(cEvaluationMethodDTO1).isEqualTo(cEvaluationMethodDTO2);
        cEvaluationMethodDTO2.setId(2L);
        assertThat(cEvaluationMethodDTO1).isNotEqualTo(cEvaluationMethodDTO2);
        cEvaluationMethodDTO1.setId(null);
        assertThat(cEvaluationMethodDTO1).isNotEqualTo(cEvaluationMethodDTO2);
    }
}

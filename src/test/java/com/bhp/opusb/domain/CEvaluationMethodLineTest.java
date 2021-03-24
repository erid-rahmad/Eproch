package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEvaluationMethodLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEvaluationMethodLine.class);
        CEvaluationMethodLine cEvaluationMethodLine1 = new CEvaluationMethodLine();
        cEvaluationMethodLine1.setId(1L);
        CEvaluationMethodLine cEvaluationMethodLine2 = new CEvaluationMethodLine();
        cEvaluationMethodLine2.setId(cEvaluationMethodLine1.getId());
        assertThat(cEvaluationMethodLine1).isEqualTo(cEvaluationMethodLine2);
        cEvaluationMethodLine2.setId(2L);
        assertThat(cEvaluationMethodLine1).isNotEqualTo(cEvaluationMethodLine2);
        cEvaluationMethodLine1.setId(null);
        assertThat(cEvaluationMethodLine1).isNotEqualTo(cEvaluationMethodLine2);
    }
}

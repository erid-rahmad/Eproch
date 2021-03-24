package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEvaluationMethodTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEvaluationMethod.class);
        CEvaluationMethod cEvaluationMethod1 = new CEvaluationMethod();
        cEvaluationMethod1.setId(1L);
        CEvaluationMethod cEvaluationMethod2 = new CEvaluationMethod();
        cEvaluationMethod2.setId(cEvaluationMethod1.getId());
        assertThat(cEvaluationMethod1).isEqualTo(cEvaluationMethod2);
        cEvaluationMethod2.setId(2L);
        assertThat(cEvaluationMethod1).isNotEqualTo(cEvaluationMethod2);
        cEvaluationMethod1.setId(null);
        assertThat(cEvaluationMethod1).isNotEqualTo(cEvaluationMethod2);
    }
}

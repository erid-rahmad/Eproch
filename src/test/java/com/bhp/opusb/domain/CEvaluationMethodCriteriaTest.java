package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEvaluationMethodCriteriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEvaluationMethodCriteria.class);
        CEvaluationMethodCriteria cEvaluationMethodCriteria1 = new CEvaluationMethodCriteria();
        cEvaluationMethodCriteria1.setId(1L);
        CEvaluationMethodCriteria cEvaluationMethodCriteria2 = new CEvaluationMethodCriteria();
        cEvaluationMethodCriteria2.setId(cEvaluationMethodCriteria1.getId());
        assertThat(cEvaluationMethodCriteria1).isEqualTo(cEvaluationMethodCriteria2);
        cEvaluationMethodCriteria2.setId(2L);
        assertThat(cEvaluationMethodCriteria1).isNotEqualTo(cEvaluationMethodCriteria2);
        cEvaluationMethodCriteria1.setId(null);
        assertThat(cEvaluationMethodCriteria1).isNotEqualTo(cEvaluationMethodCriteria2);
    }
}

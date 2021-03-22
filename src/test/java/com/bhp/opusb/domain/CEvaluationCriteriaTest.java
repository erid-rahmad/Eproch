package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEvaluationCriteriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEvaluationCriteria.class);
        CEvaluationCriteria cEvaluationCriteria1 = new CEvaluationCriteria();
        cEvaluationCriteria1.setId(1L);
        CEvaluationCriteria cEvaluationCriteria2 = new CEvaluationCriteria();
        cEvaluationCriteria2.setId(cEvaluationCriteria1.getId());
        assertThat(cEvaluationCriteria1).isEqualTo(cEvaluationCriteria2);
        cEvaluationCriteria2.setId(2L);
        assertThat(cEvaluationCriteria1).isNotEqualTo(cEvaluationCriteria2);
        cEvaluationCriteria1.setId(null);
        assertThat(cEvaluationCriteria1).isNotEqualTo(cEvaluationCriteria2);
    }
}

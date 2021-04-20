package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEvalMethodCriteriaLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEvalMethodCriteriaLine.class);
        CEvalMethodCriteriaLine cEvalMethodCriteriaLine1 = new CEvalMethodCriteriaLine();
        cEvalMethodCriteriaLine1.setId(1L);
        CEvalMethodCriteriaLine cEvalMethodCriteriaLine2 = new CEvalMethodCriteriaLine();
        cEvalMethodCriteriaLine2.setId(cEvalMethodCriteriaLine1.getId());
        assertThat(cEvalMethodCriteriaLine1).isEqualTo(cEvalMethodCriteriaLine2);
        cEvalMethodCriteriaLine2.setId(2L);
        assertThat(cEvalMethodCriteriaLine1).isNotEqualTo(cEvalMethodCriteriaLine2);
        cEvalMethodCriteriaLine1.setId(null);
        assertThat(cEvalMethodCriteriaLine1).isNotEqualTo(cEvalMethodCriteriaLine2);
    }
}

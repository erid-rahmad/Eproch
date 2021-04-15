package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEvalMethodSubCriteriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEvalMethodSubCriteria.class);
        CEvalMethodSubCriteria cEvalMethodSubCriteria1 = new CEvalMethodSubCriteria();
        cEvalMethodSubCriteria1.setId(1L);
        CEvalMethodSubCriteria cEvalMethodSubCriteria2 = new CEvalMethodSubCriteria();
        cEvalMethodSubCriteria2.setId(cEvalMethodSubCriteria1.getId());
        assertThat(cEvalMethodSubCriteria1).isEqualTo(cEvalMethodSubCriteria2);
        cEvalMethodSubCriteria2.setId(2L);
        assertThat(cEvalMethodSubCriteria1).isNotEqualTo(cEvalMethodSubCriteria2);
        cEvalMethodSubCriteria1.setId(null);
        assertThat(cEvalMethodSubCriteria1).isNotEqualTo(cEvalMethodSubCriteria2);
    }
}

package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorEvaluationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorEvaluation.class);
        CVendorEvaluation cVendorEvaluation1 = new CVendorEvaluation();
        cVendorEvaluation1.setId(1L);
        CVendorEvaluation cVendorEvaluation2 = new CVendorEvaluation();
        cVendorEvaluation2.setId(cVendorEvaluation1.getId());
        assertThat(cVendorEvaluation1).isEqualTo(cVendorEvaluation2);
        cVendorEvaluation2.setId(2L);
        assertThat(cVendorEvaluation1).isNotEqualTo(cVendorEvaluation2);
        cVendorEvaluation1.setId(null);
        assertThat(cVendorEvaluation1).isNotEqualTo(cVendorEvaluation2);
    }
}

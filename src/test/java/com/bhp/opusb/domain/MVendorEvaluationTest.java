package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorEvaluationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorEvaluation.class);
        MVendorEvaluation mVendorEvaluation1 = new MVendorEvaluation();
        mVendorEvaluation1.setId(1L);
        MVendorEvaluation mVendorEvaluation2 = new MVendorEvaluation();
        mVendorEvaluation2.setId(mVendorEvaluation1.getId());
        assertThat(mVendorEvaluation1).isEqualTo(mVendorEvaluation2);
        mVendorEvaluation2.setId(2L);
        assertThat(mVendorEvaluation1).isNotEqualTo(mVendorEvaluation2);
        mVendorEvaluation1.setId(null);
        assertThat(mVendorEvaluation1).isNotEqualTo(mVendorEvaluation2);
    }
}

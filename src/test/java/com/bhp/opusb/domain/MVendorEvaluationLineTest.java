package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorEvaluationLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorEvaluationLine.class);
        MVendorEvaluationLine mVendorEvaluationLine1 = new MVendorEvaluationLine();
        mVendorEvaluationLine1.setId(1L);
        MVendorEvaluationLine mVendorEvaluationLine2 = new MVendorEvaluationLine();
        mVendorEvaluationLine2.setId(mVendorEvaluationLine1.getId());
        assertThat(mVendorEvaluationLine1).isEqualTo(mVendorEvaluationLine2);
        mVendorEvaluationLine2.setId(2L);
        assertThat(mVendorEvaluationLine1).isNotEqualTo(mVendorEvaluationLine2);
        mVendorEvaluationLine1.setId(null);
        assertThat(mVendorEvaluationLine1).isNotEqualTo(mVendorEvaluationLine2);
    }
}
